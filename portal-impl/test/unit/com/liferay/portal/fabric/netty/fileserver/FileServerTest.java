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

package com.liferay.portal.fabric.netty.fileserver;

import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectDecoder;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectEncoder;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileRequestChannelHandler;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileResponseChannelHandler;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileServerTestUtil;
import com.liferay.portal.kernel.concurrent.AsyncBroker;
import com.liferay.portal.kernel.util.NamedThreadFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.BindException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FileServerTest {

	@Before
	public void setUp() throws Exception {
		_port = _startServer();

		_connectClient();
	}

	@After
	public void tearDown() throws Exception {
		_disconnectClient();

		_stopServer();

		FileServerTestUtil.cleanUp();
	}

	@Test
	public void testFileTransfer() throws Exception {
		doTestFileTransfer(true);
		doTestFileTransfer(false);
	}

	@Test
	public void testFileTransferNotFound() throws Exception {
		_sourceFilePath = FileServerTestUtil.createNotExistFile(
			Paths.get("testFile"));

		Future<FileResponse> future = _asyncBroker.post(
			_sourceFilePath.toAbsolutePath());

		_clientChannel.writeAndFlush(
			new FileRequest(_sourceFilePath, 0, false));

		FileResponse fileResponse = future.get(_TIME_OUT, TimeUnit.MINUTES);

		Assert.assertTrue(fileResponse.isFileNotFound());
	}

	@Test
	public void testFileTransferNotModified() throws Exception {
		_sourceFilePath = FileServerTestUtil.createFileWithData(
			Paths.get("testMissingFile"));

		Future<FileResponse> future = _asyncBroker.post(
			_sourceFilePath.toAbsolutePath());

		FileTime fileTime = Files.getLastModifiedTime(_sourceFilePath);

		_clientChannel.writeAndFlush(
			new FileRequest(_sourceFilePath, fileTime.toMillis(), false));

		FileResponse fileResponse = future.get(_TIME_OUT, TimeUnit.MINUTES);

		Assert.assertTrue(fileResponse.isFileNotModified());
	}

	@Test
	public void testFolderTransfer() throws Exception {
		doTestFolderTransfer(true);
		doTestFolderTransfer(false);
	}

	protected void doTestFileTransfer(boolean deleteAfterFetch)
		throws Exception {

		_sourceFilePath = FileServerTestUtil.createFileWithData(
			Paths.get("testFile"));

		FileTime sourceFileTime = Files.getLastModifiedTime(_sourceFilePath);

		byte[] data = Files.readAllBytes(_sourceFilePath);

		Future<FileResponse> future = _asyncBroker.post(
			_sourceFilePath.toAbsolutePath());

		_clientChannel.writeAndFlush(
			new FileRequest(_sourceFilePath, 0, deleteAfterFetch));

		FileResponse fileResponse = future.get(_TIME_OUT, TimeUnit.MINUTES);

		_destFile = fileResponse.getLocalFile();

		Assert.assertTrue(Files.exists(_destFile));

		if (deleteAfterFetch) {
			Assert.assertTrue(Files.notExists(_sourceFilePath));
		}

		FileTime destFileTime = Files.getLastModifiedTime(_destFile);

		Assert.assertEquals(sourceFileTime.toMillis(), destFileTime.toMillis());
		Assert.assertArrayEquals(data, Files.readAllBytes(_destFile));
	}

	protected void doTestFolderTransfer(boolean deleteAfterFetch)
		throws Exception {

		_sourceFilePath = FileServerTestUtil.createFolderWithFiles(
			Paths.get("testFolder"));

		FileTime sourceFileTime = Files.getLastModifiedTime(_sourceFilePath);

		Future<FileResponse> future = _asyncBroker.post(
			_sourceFilePath.toAbsolutePath());

		_clientChannel.writeAndFlush(
			new FileRequest(_sourceFilePath, 0, deleteAfterFetch));

		FileResponse fileResponse = future.get(_TIME_OUT, TimeUnit.MINUTES);

		_destFile = fileResponse.getLocalFile();

		Assert.assertTrue(Files.exists(_destFile));

		if (deleteAfterFetch) {
			Assert.assertTrue(Files.notExists(_sourceFilePath));
		}

		FileTime destFileTime = Files.getLastModifiedTime(_destFile);

		Assert.assertEquals(sourceFileTime.toMillis(), destFileTime.toMillis());

		if (!deleteAfterFetch) {
			FileServerTestUtil.assertFileEquals(_sourceFilePath, _destFile);
		}
	}

	private void _connectClient() throws InterruptedException {
		Bootstrap bootstrap = new Bootstrap();

		bootstrap.group(_nioEventLoopGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(
			new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel socketChannel) {
					ChannelPipeline channelPipeline = socketChannel.pipeline();

					channelPipeline.addLast(
						AnnotatedObjectEncoder.NAME,
						AnnotatedObjectEncoder.INSTANCE);
					channelPipeline.addLast(new AnnotatedObjectDecoder());
					channelPipeline.addLast(
						new FileResponseChannelHandler(
							_asyncBroker, _fileServerEventExecutorGroup));
				}

			});

		ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", _port);

		channelFuture.sync();

		_clientChannel = channelFuture.channel();
	}

	private void _disconnectClient() throws InterruptedException {
		ChannelFuture channelFuture = _clientChannel.close();

		channelFuture.sync();
	}

	private int _startServer() throws Exception {
		ServerBootstrap serverBootstrap = new ServerBootstrap();

		serverBootstrap.group(_nioEventLoopGroup);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.childHandler(
			new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel socketChannel) {
					ChannelPipeline channelPipeline = socketChannel.pipeline();

					channelPipeline.addLast(
						AnnotatedObjectEncoder.NAME,
						AnnotatedObjectEncoder.INSTANCE);
					channelPipeline.addLast(new AnnotatedObjectDecoder());
					channelPipeline.addLast(
						_fileServerEventExecutorGroup,
						FileRequestChannelHandler.NAME,
						new FileRequestChannelHandler(
							CompressionLevel.BEST_SPEED));
				}

			});

		int port = _START_PORT;

		while (port < 65536) {
			try {
				ChannelFuture channelFuture = serverBootstrap.bind(
					"127.0.0.1", port);

				channelFuture.sync();

				_serverChannel = channelFuture.channel();

				return port;
			}
			catch (Exception e) {
				if (!(e instanceof BindException)) {
					throw e;
				}

				System.err.println(
					"Unable to bind to " + (port++) + ", trying " + port);
			}
		}

		throw new IllegalStateException("Unable to start server");
	}

	private void _stopServer() throws InterruptedException {
		try {
			ChannelFuture channelFuture = _serverChannel.close();

			channelFuture.sync();
		}
		finally {
			_nioEventLoopGroup.shutdownGracefully();
			_fileServerEventExecutorGroup.shutdownGracefully();
		}
	}

	private static final int _START_PORT = 12758;

	private static final long _TIME_OUT = 10;

	private final AsyncBroker<Path, FileResponse> _asyncBroker =
		new AsyncBroker<Path, FileResponse>();
	private Channel _clientChannel;
	private Path _destFile;
	private final EventExecutorGroup _fileServerEventExecutorGroup =
		new NioEventLoopGroup(
			1,
			new NamedThreadFactory(
				"FileServer-EventLoop", Thread.MAX_PRIORITY,
				FileServerTest.class.getClassLoader()));
	private final EventLoopGroup _nioEventLoopGroup = new NioEventLoopGroup(
		1,
		new NamedThreadFactory(
			"IO-EventLoop", Thread.MAX_PRIORITY,
			FileServerTest.class.getClassLoader()));
	private int _port;
	private Channel _serverChannel;
	private Path _sourceFilePath;

}