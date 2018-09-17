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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectDecoder;
import com.liferay.portal.fabric.netty.fileserver.FileResponse;
import com.liferay.portal.fabric.netty.util.NettyUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FileResponseChannelHandlerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		_channelPipeline.addFirst(new AnnotatedObjectDecoder());

		Channel channel = _channelPipeline.channel();

		_fileResponseChannelHandler = new FileResponseChannelHandler(
			_asyncBroker, channel.eventLoop());

		_channelPipeline.addFirst(_fileResponseChannelHandler);

		_channelHandlerContext = _channelPipeline.firstContext();
	}

	@Test
	public void testFile() throws Exception {
		byte[] data = FileServerTestUtil.createRandomData(1024);

		long lastModified = FileServerTestUtil.getFileSystemTime(
			System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));

		FileResponse fileResponse = new FileResponse(
			_path, data.length, lastModified, false);

		NoticeableFuture<FileResponse> noticeableFuture = _asyncBroker.post(
			_path);

		_fileResponseChannelHandler.channelRead(
			_channelHandlerContext, fileResponse,
			FileServerTestUtil.wrapFirstHalf(data));

		ChannelHandler channelHandler = _channelPipeline.first();

		Assert.assertTrue(channelHandler instanceof FileUploadChannelHandler);

		FileUploadChannelHandler fileUploadChannelHandler =
			(FileUploadChannelHandler)channelHandler;

		_channelPipeline.fireChannelRead(
			FileServerTestUtil.wrapSecondHalf(data));

		channelHandler = _channelPipeline.first();

		Assert.assertFalse(channelHandler instanceof FileUploadChannelHandler);

		Assert.assertSame(fileResponse, fileUploadChannelHandler.fileResponse);
		Assert.assertSame(fileResponse, noticeableFuture.get());

		Path localFile = fileResponse.getLocalFile();

		Assert.assertNotNull(localFile);

		FileTime fileTime = Files.getLastModifiedTime(localFile);

		Assert.assertEquals(lastModified, fileTime.toMillis());

		Assert.assertArrayEquals(data, Files.readAllBytes(localFile));

		Files.delete(localFile);
	}

	@Test
	public void testFileNotFound() throws Exception {
		Future<FileResponse> future = _asyncBroker.post(_path);

		FileResponse fileResponse = new FileResponse(
			_path, FileResponse.FILE_NOT_FOUND, -1, false);

		_fileResponseChannelHandler.channelRead(
			_channelHandlerContext, fileResponse, null);

		Assert.assertSame(fileResponse, future.get());
	}

	@Test
	public void testFileNotFoundNoMatchKey() throws Exception {
		FileResponse fileResponse = new FileResponse(
			_path, FileResponse.FILE_NOT_FOUND, -1, false);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					FileResponseChannelHandler.class.getName(), Level.SEVERE)) {

			_fileResponseChannelHandler.channelRead(
				_channelHandlerContext, fileResponse, null);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Unable to place result ", fileResponse,
					" because no future exists with ID ",
					fileResponse.getPath()),
				logRecord.getMessage());
		}
	}

	@Test
	public void testFileNotModified() throws Exception {
		Future<FileResponse> future = _asyncBroker.post(_path);

		FileResponse fileResponse = new FileResponse(
			_path, FileResponse.FILE_NOT_MODIFIED, -1, false);

		_fileResponseChannelHandler.channelRead(
			_channelHandlerContext, fileResponse, null);

		Assert.assertSame(fileResponse, future.get());
	}

	@Test
	public void testFileNotModifiedNoMatchKey() throws Exception {
		FileResponse fileResponse = new FileResponse(
			_path, FileResponse.FILE_NOT_MODIFIED, -1, false);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					FileResponseChannelHandler.class.getName(), Level.SEVERE)) {

			_fileResponseChannelHandler.channelRead(
				_channelHandlerContext, fileResponse, null);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Unable to place result ", fileResponse,
					" because no future exists with ID ",
					fileResponse.getPath()),
				logRecord.getMessage());
		}
	}

	private static final Path _path = Paths.get("testFile");

	private final AsyncBroker<Path, FileResponse> _asyncBroker =
		new AsyncBroker<>();
	private ChannelHandlerContext _channelHandlerContext;
	private final ChannelPipeline _channelPipeline =
		NettyUtil.createEmptyChannelPipeline();
	private FileResponseChannelHandler _fileResponseChannelHandler;

}