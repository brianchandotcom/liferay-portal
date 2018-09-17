/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.fabric.netty.fileserver.handlers;

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.fabric.netty.fileserver.CompressionLevel;
import com.liferay.portal.fabric.netty.fileserver.FileHelperUtil;
import com.liferay.portal.fabric.netty.fileserver.FileResponse;
import com.liferay.portal.kernel.nio.FileChannelWrapper;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.EventExecutor;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FileUploadChannelHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@After
	public void tearDown() {
		FileServerTestUtil.cleanUp();
	}

	@Test
	public void testConstructor() throws IOException {
		FileResponse fileResponse = new FileResponse(
			Paths.get("testFile"), 1, -1, false);

		FileUploadChannelHandler fileUploadChannelHandler =
			new FileUploadChannelHandler(
				_asyncBroker, fileResponse, _embeddedChannel.eventLoop());

		Assert.assertSame(_asyncBroker, fileUploadChannelHandler.asyncBroker);
		Assert.assertSame(fileResponse, fileUploadChannelHandler.fileResponse);
		Assert.assertSame(
			_embeddedChannel.eventLoop(),
			fileUploadChannelHandler.eventExecutor);

		Path file = FileServerTestUtil.registerForCleanUp(
			fileResponse.getLocalFile());

		Assert.assertTrue(Files.isRegularFile(file));

		try (FileChannel fileChannel = fileUploadChannelHandler.fileChannel) {
			Assert.assertTrue(fileChannel.isOpen());
		}
	}

	@Test
	public void testConstructorParameterValidation() throws IOException {
		try {
			new FileUploadChannelHandler(null, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Async broker is null", npe.getMessage());
		}

		try {
			new FileUploadChannelHandler(_asyncBroker, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("File response is null", npe.getMessage());
		}

		FileResponse fileResponse = new FileResponse(
			Paths.get("testFile"), FileResponse.FILE_NOT_FOUND, -1, false);

		try {
			new FileUploadChannelHandler(_asyncBroker, fileResponse, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Event executor is null", npe.getMessage());
		}

		try {
			new FileUploadChannelHandler(
				_asyncBroker, fileResponse, _embeddedChannel.eventLoop());

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"File response has no content for uploading", iae.getMessage());
		}
	}

	@Test
	public void testFileUpload() throws Exception {
		doTestFileUpload(false, false, false);
		doTestFileUpload(false, false, true);
		doTestFileUpload(false, true, false);
		doTestFileUpload(false, true, true);
		doTestFileUpload(true, false, false);
		doTestFileUpload(true, false, true);
		doTestFileUpload(true, true, false);
		doTestFileUpload(true, true, true);
	}

	@AdviseWith(adviceClasses = FileHelperUtilAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testFolderUpload() throws Exception {
		doTestFolderUpload(false, false, false);
		doTestFolderUpload(false, false, true);
		doTestFolderUpload(false, true, false);
		doTestFolderUpload(false, true, true);
		doTestFolderUpload(true, false, false);
		doTestFolderUpload(true, false, true);
		doTestFolderUpload(true, true, false);
		doTestFolderUpload(true, true, true);
	}

	@Test
	public void testReceive() throws IOException {
		byte[] data = FileServerTestUtil.createRandomData(20);

		FileResponse fileResponse = new FileResponse(
			Paths.get("testFile"), data.length, -1, false);

		FileUploadChannelHandler fileUploadChannelHandler =
			new FileUploadChannelHandler(
				_asyncBroker, fileResponse, _embeddedChannel.eventLoop());

		FileServerTestUtil.registerForCleanUp(fileResponse.getLocalFile());

		final UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ReflectionTestUtil.setFieldValue(
			fileUploadChannelHandler, "fileChannel",
			new FileChannelWrapper(fileUploadChannelHandler.fileChannel) {

				@Override
				public long position() {
					return unsyncByteArrayOutputStream.size();
				}

				@Override
				public int write(ByteBuffer byteBuffer) {
					unsyncByteArrayOutputStream.write(byteBuffer.get());

					return 1;
				}

			});

		ByteBuf byteBuf = FileServerTestUtil.wrapFirstHalf(data);

		Assert.assertEquals(1, byteBuf.refCnt());
		Assert.assertFalse(fileUploadChannelHandler.receive(byteBuf));
		Assert.assertEquals(0, byteBuf.refCnt());

		byteBuf = Unpooled.buffer();

		byteBuf.writeBytes(FileServerTestUtil.wrapSecondHalf(data));
		byteBuf.writeBytes(data);

		Assert.assertEquals(1, byteBuf.refCnt());
		Assert.assertTrue(fileUploadChannelHandler.receive(byteBuf));
		Assert.assertEquals(1, byteBuf.refCnt());

		Assert.assertArrayEquals(
			data, unsyncByteArrayOutputStream.toByteArray());
		Assert.assertEquals(Unpooled.wrappedBuffer(data), byteBuf);
	}

	@Aspect
	public static class FileHelperUtilAdvice {

		@Around(
			"execution(public static java.nio.file.Path " +
				"com.liferay.portal.fabric.netty.fileserver.FileHelperUtil." +
					"unzip(java.nio.file.Path, java.nio.file.Path))"
		)
		public Object unzip(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			if (_throwException) {
				_throwException = false;

				throw new IOException("Forced Exception");
			}

			return proceedingJoinPoint.proceed();
		}

		private static boolean _throwException;

	}

	protected void doTestFileUpload(
			boolean inEventLoop, boolean fail, boolean postAsyncBroker)
		throws Exception {

		byte[] data = FileServerTestUtil.createRandomData(1024);

		long lastModified = FileServerTestUtil.getFileSystemTime(
			System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));

		Path file = doTestUpload(
			data, lastModified, false, inEventLoop, fail, postAsyncBroker);

		if (!fail) {
			Assert.assertArrayEquals(data, Files.readAllBytes(file));
		}
	}

	protected void doTestFolderUpload(
			boolean inEventLoop, boolean fail, boolean postAsyncBroker)
		throws Exception {

		Path testFolder = FileServerTestUtil.createFolderWithFiles(
			Paths.get("testFolder"));

		long lastModified = FileServerTestUtil.getFileSystemTime(
			System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));

		Files.setLastModifiedTime(
			testFolder, FileTime.fromMillis(lastModified));

		Path zipFile = FileHelperUtil.zip(
			testFolder, FileHelperUtil.TEMP_DIR_PATH,
			CompressionLevel.BEST_SPEED);

		try {
			Path folder = doTestUpload(
				Files.readAllBytes(zipFile), lastModified, true, inEventLoop,
				fail, postAsyncBroker);

			if (!fail) {
				FileServerTestUtil.assertFileEquals(testFolder, folder);
			}
		}
		finally {
			FileHelperUtil.delete(zipFile);
		}
	}

	protected Path doTestUpload(
			byte[] data, long lastModified, boolean folder, boolean inEventloop,
			boolean fail, boolean postAsyncBroker)
		throws Exception {

		FileResponse fileResponse = new FileResponse(
			Paths.get("testFile"), data.length, lastModified, folder);

		NoticeableFuture<FileResponse> noticeableFuture = null;

		if (postAsyncBroker) {
			noticeableFuture = _asyncBroker.post(fileResponse.getPath());
		}

		final FileUploadChannelHandler fileUploadChannelHandler =
			new FileUploadChannelHandler(
				_asyncBroker, fileResponse, getEventLoop(inEventloop));

		if (folder) {
			FileServerTestUtil.registerForCleanUp(fileResponse.getLocalFile());
		}

		final ChannelPipeline channelPipeline = _embeddedChannel.pipeline();

		channelPipeline.addFirst(
			FileUploadChannelHandler.class.getName(), fileUploadChannelHandler);

		if (fail) {
			if (folder) {
				FileHelperUtilAdvice._throwException = true;
			}
			else {
				try (FileChannel fileChannel =
						fileUploadChannelHandler.fileChannel) {

					Assert.assertTrue(fileChannel.isOpen());
				}
			}
		}
		else if (inEventloop) {
			channelPipeline.addAfter(
				FileUploadChannelHandler.class.getName(),
				"Assert Auto Removal Channel Handler",
				new ChannelInboundHandlerAdapter() {

					@Override
					public void channelRead(
						ChannelHandlerContext channelHandlerContext,
						Object object) {

						Assert.assertSame(this, channelPipeline.first());

						channelHandlerContext.fireChannelRead(object);

						channelPipeline.removeFirst();
					}

				});
		}

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					FileUploadChannelHandler.class.getName(), Level.SEVERE)) {

			try {
				if (inEventloop) {
					_embeddedChannel.writeInbound(
						FileServerTestUtil.wrapFirstHalf(data),
						Unpooled.copiedBuffer(
							FileServerTestUtil.wrapSecondHalf(data),
							Unpooled.wrappedBuffer(data)));

					if (!fail) {
						Queue<Object> queue =
							_embeddedChannel.inboundMessages();

						Assert.assertEquals(1, queue.size());
						Assert.assertEquals(
							Unpooled.wrappedBuffer(data), queue.poll());
					}
				}
				else {
					fileUploadChannelHandler.channelRead(
						channelPipeline.firstContext(),
						FileServerTestUtil.wrapFirstHalf(data));
					fileUploadChannelHandler.channelRead(
						channelPipeline.firstContext(),
						FileServerTestUtil.wrapSecondHalf(data));
				}
			}
			catch (Exception e) {
				fileUploadChannelHandler.exceptionCaught(
					channelPipeline.firstContext(), e);
			}

			if (postAsyncBroker) {
				if (fail) {
					try {
						noticeableFuture.get();

						Assert.fail();
					}
					catch (ExecutionException ee) {
						Throwable throwable = ee.getCause();

						if (folder) {
							Assert.assertEquals(
								"Forced Exception", throwable.getMessage());
						}
						else {
							Assert.assertTrue(
								throwable instanceof ClosedChannelException);
						}
					}
				}
				else {
					Assert.assertSame(fileResponse, noticeableFuture.get());
				}
			}

			shutdown(inEventloop, fileUploadChannelHandler.eventExecutor);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			if (fail) {
				LogRecord logRecord = logRecords.remove(0);

				Assert.assertEquals(
					"File upload failure", logRecord.getMessage());

				Throwable throwable = logRecord.getThrown();

				if (folder) {
					Assert.assertEquals(
						"Forced Exception", throwable.getMessage());
				}
				else {
					Assert.assertTrue(
						throwable instanceof ClosedChannelException);
				}
			}

			if (!postAsyncBroker) {
				LogRecord logRecord = logRecords.remove(0);

				if (fail) {
					Assert.assertEquals(
						"Unable to place exception because no future exists " +
							"with ID " + fileResponse.getPath(),
						logRecord.getMessage());

					Throwable throwable = logRecord.getThrown();

					if (folder) {
						Assert.assertEquals(
							"Forced Exception", throwable.getMessage());
					}
					else {
						Assert.assertTrue(
							throwable instanceof ClosedChannelException);
					}
				}
				else {
					Assert.assertEquals(
						StringBundler.concat(
							"Unable to place result ", fileResponse,
							" because no future exists with ID ",
							fileResponse.getPath()),
						logRecord.getMessage());
				}
			}

			Assert.assertTrue(logRecords.toString(), logRecords.isEmpty());
			Assert.assertSame(channelPipeline.first(), channelPipeline.last());
		}

		Path file = FileServerTestUtil.registerForCleanUp(
			fileResponse.getLocalFile());

		if (!fail) {
			FileTime fileTime = Files.getLastModifiedTime(file);

			Assert.assertEquals(lastModified, fileTime.toMillis());
		}

		return file;
	}

	protected EventLoop getEventLoop(boolean inEventloop) {
		if (inEventloop) {
			return _embeddedChannel.eventLoop();
		}

		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(1);

		return nioEventLoopGroup.next();
	}

	protected void shutdown(boolean inEventloop, EventExecutor eventExecutor)
		throws Exception {

		if (inEventloop) {
			_embeddedChannel.runPendingTasks();
		}
		else {
			Future<?> future = eventExecutor.shutdownGracefully();

			future.get();
		}
	}

	private final AsyncBroker<Path, FileResponse> _asyncBroker =
		new AsyncBroker<>();
	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();

}