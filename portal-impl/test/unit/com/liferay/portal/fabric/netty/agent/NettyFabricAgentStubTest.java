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

import com.liferay.portal.fabric.OutputResource;
import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerConfig;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerStub;
import com.liferay.portal.fabric.repository.MockRepository;
import com.liferay.portal.fabric.status.FabricStatus;
import com.liferay.portal.fabric.status.RemoteFabricStatus;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.local.ReturnProcessCallable;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

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
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricAgentStubTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() {
		try {
			new NettyFabricAgentStub(null, null, null, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Channel is null", npe.getMessage());
		}

		try {
			new NettyFabricAgentStub(_embeddedChannel, null, null, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Repository is null", npe.getMessage());
		}

		try {
			new NettyFabricAgentStub(
				_embeddedChannel, new MockRepository(), null, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Remote repository path is null", npe.getMessage());
		}

		new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository(), Paths.get("RepositoryPath"),
			0);
	}

	@Test
	public void testEquals() {
		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository(), Paths.get("RepositoryPath"),
			0);

		Assert.assertTrue(nettyFabricAgentStub.equals(nettyFabricAgentStub));
		Assert.assertFalse(nettyFabricAgentStub.equals(new Object()));

		NettyFabricAgentStub anotherNettyFabricAgentStub =
			new NettyFabricAgentStub(
				NettyTestUtil.createEmptyEmbeddedChannel(),
				new MockRepository(), Paths.get("AnotherRepositoryPath"), 0);

		Assert.assertFalse(
			nettyFabricAgentStub.equals(anotherNettyFabricAgentStub));

		anotherNettyFabricAgentStub =
			new NettyFabricAgentStub(
				_embeddedChannel, new MockRepository(),
				Paths.get("AnotherRepositoryPath"), 0);

		Assert.assertTrue(
			nettyFabricAgentStub.equals(anotherNettyFabricAgentStub));
	}

	@Test
	public void testExecute() throws Exception {
		final NettyFabricAgentStub nettyFabricAgentStub =
			new NettyFabricAgentStub(
				_embeddedChannel, new MockRepository(),
				Paths.get("RepositoryPath"), 0);

		AtomicLong idGenerator = ReflectionTestUtil.getFieldValue(
			nettyFabricAgentStub, "_idGenerator");

		long id = idGenerator.get();

		Builder builder = new Builder();

		ProcessConfig processConfig = builder.build();

		final File testFile1 = new File("TestFile1");
		final File testFile2 = new File("TestFile2");
		final File testFile3 = new File("TestFile3");

		ProcessCallable<String> processCallable =
			new ProcessCallable<String>() {

				@Override
				public String call() {
					return "Test Result";
				}

				@OutputResource
				private final File _testOutput1 = testFile1;
				private final File _testOutput2 = testFile2;
				@OutputResource
				private final File _testOutput3 = testFile3;
				@OutputResource
				private final String _notAFile = "Not a File";

			};

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

					NettyFabricWorkerConfig nettyFabricWorkerConfig =
						(NettyFabricWorkerConfig)obj;

					nettyFabricAgentStub.finsihStartup(
						nettyFabricWorkerConfig.getId());
				}

			}
		);
		FabricWorker<String> fabricWorker = ReflectionTestUtil.invokeBridge(
			nettyFabricAgentStub, "execute",
			new Class<?>[] {ProcessConfig.class, ProcessCallable.class},
			processConfig, processCallable);

		Queue<Object> messages = _embeddedChannel.outboundMessages();

		Assert.assertEquals(1, messages.size());

		NettyFabricWorkerConfig<String> nettyFabricWorkerConfig =
			(NettyFabricWorkerConfig<String>)messages.poll();

		Assert.assertEquals(id, nettyFabricWorkerConfig.getId());
		Assert.assertSame(
			processConfig, nettyFabricWorkerConfig.getProcessConfig());
		Assert.assertSame(
			processCallable, nettyFabricWorkerConfig.getProcessCallable());

		Collection<? extends FabricWorker<?>> fabricWorkers =
			nettyFabricAgentStub.getFabricWorkers();

		Assert.assertEquals(1, fabricWorkers.size());
		Assert.assertTrue(fabricWorkers.contains(fabricWorker));

		NoticeableFuture<String> noticeableFuture =
			fabricWorker.getProcessNoticeableFuture();

		Assert.assertFalse(noticeableFuture.isDone());

		NettyFabricWorkerStub<String> nettyFabricWorkerStub =
			(NettyFabricWorkerStub<String>)
				nettyFabricAgentStub.takeNettyStubFabricWorker(id);

		Assert.assertTrue(fabricWorkers.isEmpty());

		Map<Path, Path> outputResourceMap = ReflectionTestUtil.getFieldValue(
			nettyFabricWorkerStub, "_outputResourceMap");

		Assert.assertEquals(2, outputResourceMap.size());

		Path path1 = testFile1.toPath();

		path1 = path1.toAbsolutePath();

		Path mappedPath1 = outputResourceMap.get(path1);

		Assert.assertNotNull(mappedPath1);
		Assert.assertEquals(
			ReflectionTestUtil.getFieldValue(processCallable, "_testOutput1"),
			mappedPath1.toFile());

		Path path3 = testFile3.toPath();

		path3 = path3.toAbsolutePath();

		Path mappedPath3 = outputResourceMap.get(path3);

		Assert.assertNotNull(mappedPath3);
		Assert.assertEquals(
			ReflectionTestUtil.getFieldValue(processCallable, "_testOutput3"),
			mappedPath3.toFile());

		nettyFabricWorkerStub.setResult(processCallable.call());

		Assert.assertEquals(processCallable.call(), noticeableFuture.get());

		// Ensure no side affect to finish an already finished startup

		nettyFabricAgentStub.finsihStartup(id);
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
					_embeddedChannel, new MockRepository(),
					Paths.get("RepositoryPath"), 0);

			Builder builder = new Builder();

			FabricWorker<String> fabricWorker =  nettyFabricAgentStub.execute(
				builder.build(),
				new ReturnProcessCallable<String>("Test result"));

			NoticeableFuture<String> noticeableFuture =
				fabricWorker.getProcessNoticeableFuture();

			Assert.assertTrue(noticeableFuture.isCancelled());

			Collection<? extends FabricWorker<?>> fabricWorkers =
				nettyFabricAgentStub.getFabricWorkers();

			Assert.assertTrue(fabricWorkers.isEmpty());
		}
		finally {
			channelPipeline.removeFirst();
		}
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
					_embeddedChannel, new MockRepository(),
					Paths.get("RepositoryPath"), 0);

			Builder builder = new Builder();

			FabricWorker<String> fabricWorker =  nettyFabricAgentStub.execute(
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

			Assert.assertTrue(fabricWorkers.isEmpty());
		}
		finally {
			channelPipeline.removeFirst();
		}
	}

	@Test
	public void testExecuteWithInterruption() throws Exception {
		NettyFabricAgentStub nettyFabricAgentStub =
			new NettyFabricAgentStub(
				_embeddedChannel, new MockRepository(),
				Paths.get("RepositoryPath"), 0);

		Thread currentThread = Thread.currentThread();

		currentThread.interrupt();

		Builder builder = new Builder();

		FabricWorker<String> fabricWorker =  nettyFabricAgentStub.execute(
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

		Assert.assertTrue(fabricWorkers.isEmpty());
	}

	@Test
	public void testGetFabricStatus() {
		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository(), Paths.get("RepositoryPath"),
			0);

		FabricStatus fabricStatus = nettyFabricAgentStub.getFabricStatus();

		Assert.assertSame(RemoteFabricStatus.class, fabricStatus.getClass());
	}

	@Test
	public void testHashCode() {
		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository(), Paths.get("RepositoryPath"),
			0);

		Assert.assertEquals(
			_embeddedChannel.hashCode(), nettyFabricAgentStub.hashCode());
	}

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();

}