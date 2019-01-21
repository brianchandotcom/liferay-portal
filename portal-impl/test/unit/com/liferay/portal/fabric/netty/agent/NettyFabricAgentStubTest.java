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

package com.liferay.portal.fabric.netty.agent;

import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.portal.fabric.OutputResource;
import com.liferay.portal.fabric.ReturnProcessCallable;
import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerConfig;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerStub;
import com.liferay.portal.fabric.repository.MockRepository;
import com.liferay.portal.fabric.status.FabricStatus;
import com.liferay.portal.fabric.status.RemoteFabricStatus;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricAgentStubTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		try {
			new NettyFabricAgentStub(null, null, null, 0, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Channel is null", npe.getMessage());
		}

		try {
			new NettyFabricAgentStub(_embeddedChannel, null, null, 0, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Repository is null", npe.getMessage());
		}

		try {
			new NettyFabricAgentStub(
				_embeddedChannel, new MockRepository<Channel>(), null, 0, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Remote repository path is null", npe.getMessage());
		}

		new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository<Channel>(),
			Paths.get("RepositoryPath"), 0, 0);
	}

	@Test
	public void testEquals() {
		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository<Channel>(),
			Paths.get("RepositoryPath"), 0, 0);

		Assert.assertTrue(nettyFabricAgentStub.equals(nettyFabricAgentStub));
		Assert.assertFalse(nettyFabricAgentStub.equals(new Object()));

		NettyFabricAgentStub anotherNettyFabricAgentStub =
			new NettyFabricAgentStub(
				NettyTestUtil.createEmptyEmbeddedChannel(),
				new MockRepository<Channel>(),
				Paths.get("AnotherRepositoryPath"), 0, 0);

		Assert.assertFalse(
			nettyFabricAgentStub.equals(anotherNettyFabricAgentStub));

		anotherNettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository<Channel>(),
			Paths.get("AnotherRepositoryPath"), 0, 0);

		Assert.assertTrue(
			nettyFabricAgentStub.equals(anotherNettyFabricAgentStub));
	}

	@Test
	public void testExecute() throws Exception {
		final NettyFabricAgentStub nettyFabricAgentStub =
			new NettyFabricAgentStub(
				_embeddedChannel, new MockRepository<Channel>(),
				Paths.get("RepositoryPath"), 0, Long.MAX_VALUE);

		AtomicLong idGenerator = ReflectionTestUtil.getFieldValue(
			nettyFabricAgentStub, "_idGenerator");

		long id = idGenerator.get();

		ProcessConfig.Builder builder = new ProcessConfig.Builder();

		ProcessConfig processConfig = builder.build();

		File testFile1 = new File("TestFile1");
		File testFile2 = new File("TestFile2");
		File testFile3 = new File("TestFile3");

		ProcessCallable<String> processCallable = new TestProcessCallable(
			testFile1, testFile2, testFile3);

		ChannelPipeline channelPipeline = _embeddedChannel.pipeline();

		channelPipeline.addLast(
			new ChannelOutboundHandlerAdapter() {

				@Override
				public void write(
						ChannelHandlerContext channelHandlerContext, Object obj,
						ChannelPromise channelPromise)
					throws Exception {

					super.write(channelHandlerContext, obj, channelPromise);

					if (!(obj instanceof NettyFabricWorkerConfig)) {
						return;
					}

					NettyFabricWorkerConfig<?> nettyFabricWorkerConfig =
						(NettyFabricWorkerConfig<?>)obj;

					nettyFabricAgentStub.finishStartup(
						nettyFabricWorkerConfig.getId());
				}

			});

		FabricWorker<String> fabricWorker = nettyFabricAgentStub.execute(
			processConfig, processCallable);

		Queue<Object> messages = _embeddedChannel.outboundMessages();

		Assert.assertEquals(1, messages.size());

		NettyFabricWorkerConfig<String> nettyFabricWorkerConfig =
			(NettyFabricWorkerConfig<String>)messages.poll();

		Assert.assertEquals(id, nettyFabricWorkerConfig.getId());
		Assert.assertSame(
			processConfig, nettyFabricWorkerConfig.getProcessConfig());

		ProcessCallable<String> nettyFabricWorkerProcessCallable =
			nettyFabricWorkerConfig.getProcessCallable();

		Assert.assertNotSame(processCallable, nettyFabricWorkerProcessCallable);
		Assert.assertEquals(
			processCallable.toString(),
			nettyFabricWorkerProcessCallable.toString());
		Assert.assertEquals(
			processCallable.call(), nettyFabricWorkerProcessCallable.call());

		Collection<? extends FabricWorker<?>> fabricWorkers =
			nettyFabricAgentStub.getFabricWorkers();

		Assert.assertEquals(fabricWorkers.toString(), 1, fabricWorkers.size());
		Assert.assertTrue(
			fabricWorkers.toString(), fabricWorkers.contains(fabricWorker));

		NoticeableFuture<String> noticeableFuture =
			fabricWorker.getProcessNoticeableFuture();

		Assert.assertFalse(noticeableFuture.isDone());

		NettyFabricWorkerStub<String> nettyFabricWorkerStub =
			(NettyFabricWorkerStub<String>)
				nettyFabricAgentStub.takeNettyStubFabricWorker(id);

		Assert.assertTrue(fabricWorkers.toString(), fabricWorkers.isEmpty());

		Map<Path, Path> outputPathMap = ReflectionTestUtil.getFieldValue(
			nettyFabricWorkerStub, "_outputPathMap");

		Assert.assertEquals(outputPathMap.toString(), 2, outputPathMap.size());

		Path path1 = testFile1.toPath();

		File testOutput1 = ReflectionTestUtil.getFieldValue(
			processCallable, "_testOutput1");

		Assert.assertEquals(path1, outputPathMap.get(testOutput1.toPath()));

		Path path3 = testFile3.toPath();

		File testOutput3 = ReflectionTestUtil.getFieldValue(
			processCallable, "_testOutput3");

		Assert.assertEquals(path3, outputPathMap.get(testOutput3.toPath()));

		nettyFabricWorkerStub.setResult(processCallable.call());

		Assert.assertEquals(processCallable.call(), noticeableFuture.get());

		// Ensure no side effect to finish an already finished startup

		nettyFabricAgentStub.finishStartup(id);
	}

	@Test
	public void testExecuteWithCancellation() {
		ChannelPipeline channelPipeline = _embeddedChannel.pipeline();

		channelPipeline.addFirst(
			new ChannelOutboundHandlerAdapter() {

				@Override
				public void write(
					ChannelHandlerContext channelHandlerContext, Object object,
					ChannelPromise channelPromise) {

					channelPromise.cancel(true);
				}

			});

		try {
			NettyFabricAgentStub nettyFabricAgentStub =
				new NettyFabricAgentStub(
					_embeddedChannel, new MockRepository<Channel>(),
					Paths.get("RepositoryPath"), 0, Long.MAX_VALUE);

			ProcessConfig.Builder builder = new ProcessConfig.Builder();

			FabricWorker<String> fabricWorker = nettyFabricAgentStub.execute(
				builder.build(),
				new ReturnProcessCallable<String>("Test result"));

			NoticeableFuture<String> noticeableFuture =
				fabricWorker.getProcessNoticeableFuture();

			Assert.assertTrue(noticeableFuture.isCancelled());

			Collection<? extends FabricWorker<?>> fabricWorkers =
				nettyFabricAgentStub.getFabricWorkers();

			Assert.assertTrue(
				fabricWorkers.toString(), fabricWorkers.isEmpty());
		}
		finally {
			channelPipeline.removeFirst();
		}
	}

	@Test
	public void testExecuteWithClosedChannel() {
		ChannelPipeline channelPipeline = _embeddedChannel.pipeline();

		channelPipeline.addFirst(
			new ChannelOutboundHandlerAdapter() {

				@Override
				public void write(
					ChannelHandlerContext channelHandlerContext, Object object,
					ChannelPromise channelPromise) {

					_embeddedChannel.close();
				}

			});

		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository<Channel>(),
			Paths.get("RepositoryPath"), 0, Long.MAX_VALUE);

		ProcessConfig.Builder builder = new ProcessConfig.Builder();

		FabricWorker<String> fabricWorker = nettyFabricAgentStub.execute(
			builder.build(), new ReturnProcessCallable<String>("Test result"));

		NoticeableFuture<String> noticeableFuture =
			fabricWorker.getProcessNoticeableFuture();

		Assert.assertTrue(noticeableFuture.isCancelled());

		Collection<? extends FabricWorker<?>> fabricWorkers =
			nettyFabricAgentStub.getFabricWorkers();

		Assert.assertTrue(fabricWorkers.toString(), fabricWorkers.isEmpty());
	}

	@Test
	public void testExecuteWithFailure() throws Exception {
		final Throwable throwable = new Throwable();

		ChannelPipeline channelPipeline = _embeddedChannel.pipeline();

		channelPipeline.addFirst(
			new ChannelOutboundHandlerAdapter() {

				@Override
				public void write(
					ChannelHandlerContext channelHandlerContext, Object object,
					ChannelPromise channelPromise) {

					channelPromise.setFailure(throwable);
				}

			});

		try {
			NettyFabricAgentStub nettyFabricAgentStub =
				new NettyFabricAgentStub(
					_embeddedChannel, new MockRepository<Channel>(),
					Paths.get("RepositoryPath"), 0, Long.MAX_VALUE);

			ProcessConfig.Builder builder = new ProcessConfig.Builder();

			FabricWorker<String> fabricWorker = nettyFabricAgentStub.execute(
				builder.build(),
				new ReturnProcessCallable<String>("Test result"));

			NoticeableFuture<String> noticeableFuture =
				fabricWorker.getProcessNoticeableFuture();

			try {
				noticeableFuture.get();

				Assert.fail();
			}
			catch (ExecutionException ee) {
				Assert.assertSame(throwable, ee.getCause());
			}

			Collection<? extends FabricWorker<?>> fabricWorkers =
				nettyFabricAgentStub.getFabricWorkers();

			Assert.assertTrue(
				fabricWorkers.toString(), fabricWorkers.isEmpty());
		}
		finally {
			channelPipeline.removeFirst();
		}
	}

	@Test
	public void testExecuteWithInterruption() throws Exception {
		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository<Channel>(),
			Paths.get("RepositoryPath"), 0, Long.MAX_VALUE);

		Thread currentThread = Thread.currentThread();

		currentThread.interrupt();

		ProcessConfig.Builder builder = new ProcessConfig.Builder();

		FabricWorker<String> fabricWorker = nettyFabricAgentStub.execute(
			builder.build(), new ReturnProcessCallable<String>("Test result"));

		NoticeableFuture<String> noticeableFuture =
			fabricWorker.getProcessNoticeableFuture();

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertSame(InterruptedException.class, throwable.getClass());
		}

		Collection<? extends FabricWorker<?>> fabricWorkers =
			nettyFabricAgentStub.getFabricWorkers();

		Assert.assertTrue(fabricWorkers.toString(), fabricWorkers.isEmpty());
	}

	@Test
	public void testExecuteWithTimeout() throws InterruptedException {
		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository<Channel>(),
			Paths.get("RepositoryPath"), 0, 0);

		ProcessConfig.Builder builder = new ProcessConfig.Builder();

		FabricWorker<String> fabricWorker = nettyFabricAgentStub.execute(
			builder.build(), new ReturnProcessCallable<String>("Test result"));

		NoticeableFuture<String> noticeableFuture =
			fabricWorker.getProcessNoticeableFuture();

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertSame(TimeoutException.class, throwable.getClass());
		}

		Collection<? extends FabricWorker<?>> fabricWorkers =
			nettyFabricAgentStub.getFabricWorkers();

		Assert.assertTrue(fabricWorkers.toString(), fabricWorkers.isEmpty());
	}

	@Test
	public void testGetFabricStatus() {
		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository<Channel>(),
			Paths.get("RepositoryPath"), 0, 0);

		FabricStatus fabricStatus = nettyFabricAgentStub.getFabricStatus();

		Assert.assertSame(RemoteFabricStatus.class, fabricStatus.getClass());
	}

	@Test
	public void testHashCode() {
		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository<Channel>(),
			Paths.get("RepositoryPath"), 0, 0);

		Assert.assertEquals(
			_embeddedChannel.hashCode(), nettyFabricAgentStub.hashCode());
	}

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();

	private static class TestProcessCallable
		implements ProcessCallable<String> {

		public TestProcessCallable(
			File testOutput1, File testOutput2, File testOutput3) {

			_testOutput1 = testOutput1;
			_testOutput2 = testOutput2;
			_testOutput3 = testOutput3;
		}

		@Override
		public String call() {
			return "Test Result";
		}

		@OutputResource
		private static final String _NOT_AFILE = "Not a File";

		private static final long serialVersionUID = 1L;

		@OutputResource
		private final File _testOutput1;

		@SuppressWarnings("unused")
		private final File _testOutput2;

		@OutputResource
		private final File _testOutput3;

	}

}