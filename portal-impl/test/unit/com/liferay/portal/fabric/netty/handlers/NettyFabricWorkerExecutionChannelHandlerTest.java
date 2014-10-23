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

package com.liferay.portal.fabric.netty.handlers;

import com.liferay.portal.fabric.InputResource;
import com.liferay.portal.fabric.agent.FabricAgent;
import com.liferay.portal.fabric.local.agent.EmbeddedProcessExecutor;
import com.liferay.portal.fabric.local.worker.EmbeddedProcessChannel;
import com.liferay.portal.fabric.local.worker.LocalFabricWorker;
import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.fabric.netty.agent.NettyFabricAgentStub;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileServerTestUtil;
import com.liferay.portal.fabric.netty.handlers.NettyFabricWorkerExecutionChannelHandler.FabricAgentFinishStartupProcessCallable;
import com.liferay.portal.fabric.netty.handlers.NettyFabricWorkerExecutionChannelHandler.FabricWorkerResultProcessCallable;
import com.liferay.portal.fabric.netty.handlers.NettyFabricWorkerExecutionChannelHandler.LoadedResources;
import com.liferay.portal.fabric.netty.handlers.NettyFabricWorkerExecutionChannelHandler.PostFabricWorkerExecutionFutureListener;
import com.liferay.portal.fabric.netty.handlers.NettyFabricWorkerExecutionChannelHandler.PostFabricWorkerFinishFutureListener;
import com.liferay.portal.fabric.netty.handlers.NettyFabricWorkerExecutionChannelHandler.PostLoadResourcesFutureListener;
import com.liferay.portal.fabric.netty.rpc.ChannelThreadLocal;
import com.liferay.portal.fabric.netty.rpc.RPCSerializable;
import com.liferay.portal.fabric.netty.util.NettyUtilAdvice;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerConfig;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerStub;
import com.liferay.portal.fabric.repository.MockRepository;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.runners.AspectJMockingNewClassLoaderJUnitTestRunner;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.concurrent.DefaultPromise;

import java.io.File;
import java.io.Serializable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class NettyFabricWorkerExecutionChannelHandlerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		ChannelThreadLocal.setChannel(_embeddedChannel);
	}

	@After
	public void tearDown() {
		FileServerTestUtil.cleanUp();

		ChannelThreadLocal.removeChannel();
	}

	@Test
	public void testConstructor() {
		try {
			new NettyFabricWorkerExecutionChannelHandler(null, null, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Repository is null", npe.getMessage());
		}

		try {
			new NettyFabricWorkerExecutionChannelHandler(
				new MockRepository(), null, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Process executor is null", npe.getMessage());
		}

		new NettyFabricWorkerExecutionChannelHandler(
			new MockRepository(), new EmbeddedProcessExecutor(), 0);
	}

	@Test
	public void testFabricAgentFinishStartupProcessCallable()
		throws ProcessException {

		FabricAgentFinishStartupProcessCallable
			fabricWorkerFinishStartupProcessCallable =
				new FabricAgentFinishStartupProcessCallable(0);

		try {
			fabricWorkerFinishStartupProcessCallable.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Assert.assertEquals(
				"Unable to locate fabric agent on channel " + _embeddedChannel,
				pe.getMessage());
		}

		NettyFabricAgentStub nettyFabricAgentStub =
			installNettyFabricAgentStub();

		Map<Long, DefaultNoticeableFuture<?>> startupNoticeableFutures =
			ReflectionTestUtil.getFieldValue(
				nettyFabricAgentStub, "_startupNoticeableFutures");

		DefaultNoticeableFuture<?> defaultNoticeableFuture =
			new DefaultNoticeableFuture<Object>();

		startupNoticeableFutures.put(0L, defaultNoticeableFuture);

		Assert.assertFalse(defaultNoticeableFuture.isDone());
		Assert.assertNull(fabricWorkerFinishStartupProcessCallable.call());
		Assert.assertTrue(defaultNoticeableFuture.isDone());
	}

	@Test
	public void testFabricWorkerResultProcessCallable() throws Exception {

		// Unable to locate fabric agent

		FabricWorkerResultProcessCallable fabricWorkerResultProcessCallable =
			new FabricWorkerResultProcessCallable(0, StringPool.BLANK, null);

		try {
			fabricWorkerResultProcessCallable.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Assert.assertEquals(
				"Unable to locate fabric agent on channel " + _embeddedChannel,
				pe.getMessage());
		}

		// Unable to locate fabric worker

		installNettyFabricAgentStub();

		try {
			fabricWorkerResultProcessCallable.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Assert.assertEquals(
				"Unable to locate fabric worker on channel " +
					_embeddedChannel + ", with fabric worker id 0",
				pe.getMessage());
		}

		// Finish with result

		NettyFabricWorkerStub<Serializable> nettyFabricWorkerStub =
			installNettyFabricWorkerStub();

		NoticeableFuture<Serializable> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		Assert.assertNull(fabricWorkerResultProcessCallable.call());
		Assert.assertEquals(StringPool.BLANK, noticeableFuture.get());

		// Finish with exception

		Throwable throwable = new Throwable();

		fabricWorkerResultProcessCallable =
			new FabricWorkerResultProcessCallable(0, null, throwable);

		nettyFabricWorkerStub = installNettyFabricWorkerStub();

		noticeableFuture = nettyFabricWorkerStub.getProcessNoticeableFuture();

		Assert.assertNull(fabricWorkerResultProcessCallable.call());

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(throwable, ee.getCause());
		}
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testIntegration() {
		NettyFabricWorkerExecutionChannelHandler
			nettyFabricWorkerExecutionChannelHandler =
				new NettyFabricWorkerExecutionChannelHandler(
					new MockRepository(), new EmbeddedProcessExecutor(),
					Long.MAX_VALUE);

		ChannelPipeline channelPipeline = _embeddedChannel.pipeline();

		channelPipeline.addFirst(nettyFabricWorkerExecutionChannelHandler);

		_embeddedChannel.writeInbound(createNettyFabricWorkerConfig());

		FabricWorker<Serializable> fabricWorker =
			NettyChannelAttributes.getFabricWorker(_embeddedChannel, 0);

		DefaultNoticeableFuture<?> defaultNoticeableFuture =
			(DefaultNoticeableFuture<?>)
				fabricWorker.getProcessNoticeableFuture();

		defaultNoticeableFuture.run();

		Assert.assertNull(
			NettyChannelAttributes.getFabricWorker(_embeddedChannel, 0));
	}

	@Test
	public void testLoadedResources() {
		Map<Path, Path> inputResources = Collections.emptyMap();

		String newBootstrapClassPath = "newBootstrapClassPath";
		String newRuntimeClassPath = "newRuntimeClassPath";

		LoadedResources loadedResources = new LoadedResources(
			inputResources, newBootstrapClassPath, newRuntimeClassPath);

		Assert.assertSame(inputResources, loadedResources.getInputResources());

		List<String> arguments = Collections.emptyList();

		Builder builder = new Builder();

		builder.setArguments(arguments);
		builder.setBootstrapClassPath("oldBootstrapClassPath");
		builder.setRuntimeClassPath("oldRuntimeClassPath");

		ProcessConfig processConfig = loadedResources.toProcessConfig(
			builder.build());

		Assert.assertSame(arguments, processConfig.getArguments());
		Assert.assertEquals(
			newBootstrapClassPath, processConfig.getBootstrapClassPath());
		Assert.assertEquals(
			newRuntimeClassPath, processConfig.getRuntimeClassPath());
	}

	@Test
	public void testLoadResources() throws Exception {
		final Map<Path, Path> mergedResources = new HashMap<Path, Path>();

		final Path inputResource1 = getAbsolutePath("inputResources1");
		Path mappedInputResource1 = getAbsolutePath("mappedInputResource1");
		final Path inputResource2 = getAbsolutePath("inputResources2");

		mergedResources.put(inputResource1, mappedInputResource1);

		Path bootstrapResource1 = getAbsolutePath("bootstrapResource1");
		Path mappedBootstrapResource1 = getAbsolutePath(
			"mappedBootstrapResource1");
		Path bootstrapResource2 = getAbsolutePath("bootstrapResource2");
		Path bootstrapResource3 = getAbsolutePath("bootstrapResource3");
		Path mappedBootstrapResource3 = getAbsolutePath(
			"mappedBootstrapResource3");

		mergedResources.put(bootstrapResource1, mappedBootstrapResource1);
		mergedResources.put(bootstrapResource3, mappedBootstrapResource3);

		Path runtimeResource1 = getAbsolutePath("runtimeResource1");
		Path mappedRuntimeResource1 = getAbsolutePath("mappedRuntimeResource1");
		Path runtimeResource2 = getAbsolutePath("runtimeResource2");
		Path runtimeResource3 = getAbsolutePath("runtimeResource3");
		Path mappedRuntimeResource3 = getAbsolutePath("mappedRuntimeResource3");

		mergedResources.put(runtimeResource1, mappedRuntimeResource1);
		mergedResources.put(runtimeResource3, mappedRuntimeResource3);

		NettyFabricWorkerExecutionChannelHandler
			nettyFabricWorkerExecutionChannelHandler =
				new NettyFabricWorkerExecutionChannelHandler(
					new MockRepository() {

						@Override
						public NoticeableFuture<Map<Path, Path>> getFiles(
							Map<Path, Path> pathMap, boolean deleteAfterFetch) {

							DefaultNoticeableFuture<Map<Path, Path>>
								defaultNoticeableFuture =
									new DefaultNoticeableFuture
										<Map<Path, Path>>();

							defaultNoticeableFuture.set(mergedResources);

							return defaultNoticeableFuture;
						}

						@Override
						public Path getRepositoryPath() {
							return getAbsolutePath("repository");
						}

					},
					new EmbeddedProcessExecutor(), 0);

		Builder builder = new Builder();

		builder.setBootstrapClassPath(
			bootstrapResource1 + File.pathSeparator + bootstrapResource2 +
				File.pathSeparator + bootstrapResource3);
		builder.setRuntimeClassPath(
			runtimeResource1 + File.pathSeparator + runtimeResource2 +
				File.pathSeparator + runtimeResource3);

		ProcessConfig processConfig = builder.build();

		ProcessCallable<Serializable> processCallable =
			new ProcessCallable<Serializable>() {

				@Override
				public Serializable call() {
					return null;
				}

				@InputResource
				private final File _inputFile1 = inputResource1.toFile();
				@InputResource
				private final File _inputFile2 = inputResource2.toFile();

			};

		NoticeableFuture<LoadedResources> noticeableFuture =
			nettyFabricWorkerExecutionChannelHandler.loadResources(
				new NettyFabricWorkerConfig<Serializable>(
					0, processConfig, processCallable));

		LoadedResources loadedResources = noticeableFuture.get();

		Map<Path, Path> loadedInputResources =
			loadedResources.getInputResources();

		Assert.assertEquals(1, loadedInputResources.size());
		Assert.assertEquals(
			mappedInputResource1, loadedInputResources.get(inputResource1));

		processConfig = loadedResources.toProcessConfig(processConfig);

		Assert.assertEquals(
			mappedBootstrapResource1 + File.pathSeparator +
				mappedBootstrapResource3,
			processConfig.getBootstrapClassPath());
		Assert.assertEquals(
			mappedRuntimeResource1 + File.pathSeparator +
				mappedRuntimeResource3,
			processConfig.getRuntimeClassPath());
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testPostFabricWorkerExecutionFutureListener() throws Exception {

		// Execution failure

		NettyFabricWorkerExecutionChannelHandler
			nettyFabricWorkerExecutionChannelHandler =
				new NettyFabricWorkerExecutionChannelHandler(
					new MockRepository(), new EmbeddedProcessExecutor(),
					Long.MAX_VALUE);

		PostFabricWorkerExecutionFutureListener
			postFabricWorkerExecutionFutureListener =
				nettyFabricWorkerExecutionChannelHandler.
					new PostFabricWorkerExecutionFutureListener(
						_embeddedChannel, null,
						createNettyFabricWorkerConfig());

		DefaultPromise<FabricWorker<Serializable>> defaultPromise =
			new DefaultPromise<FabricWorker<Serializable>>(
				_embeddedChannel.eventLoop());

		Throwable throwable = new Throwable();

		defaultPromise.setFailure(throwable);

		installNettyFabricAgentStub();

		NettyFabricWorkerStub<Serializable> nettyFabricWorkerStub =
			installNettyFabricWorkerStub();

		NoticeableFuture<Serializable> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		defaultPromise.addListener(postFabricWorkerExecutionFutureListener);

		invokeRPC();

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertEquals(throwable, ee.getCause());
		}

		// Finish startup failure

		_embeddedChannel.close();

		postFabricWorkerExecutionFutureListener =
			nettyFabricWorkerExecutionChannelHandler.
				new PostFabricWorkerExecutionFutureListener(
					_embeddedChannel, null, createNettyFabricWorkerConfig());

		defaultPromise = new DefaultPromise<FabricWorker<Serializable>>(
			_embeddedChannel.eventLoop());

		DefaultNoticeableFuture<Serializable> processNoticeableFuture =
			new DefaultNoticeableFuture<Serializable>();

		FabricWorker<Serializable> fabricWorker =
			new LocalFabricWorker<Serializable>(
				new EmbeddedProcessChannel<Serializable>(
					processNoticeableFuture));

		defaultPromise.setSuccess(fabricWorker);

		nettyFabricWorkerStub = installNettyFabricWorkerStub();

		noticeableFuture = nettyFabricWorkerStub.getProcessNoticeableFuture();

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			NettyFabricWorkerExecutionChannelHandler.class.getName(),
			Level.SEVERE);

		try {
			defaultPromise.addListener(postFabricWorkerExecutionFutureListener);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to finish fabric worker startup",
				logRecord.getMessage());
		}
		finally {
			captureHandler.close();
		}

		Assert.assertSame(
			fabricWorker,
			NettyChannelAttributes.getFabricWorker(_embeddedChannel, 0));

		Set<FutureListener<Serializable>> futureListeners =
			ReflectionTestUtil.getFieldValue(
				processNoticeableFuture, "_futureListeners");

		Assert.assertEquals(2, futureListeners.size());

		Iterator<FutureListener<Serializable>> iterator =
			futureListeners.iterator();

		iterator.next();

		FutureListener<Serializable> futureListener = iterator.next();

		futureListener = ReflectionTestUtil.getFieldValue(
			futureListener, "_futureListener");

		Assert.assertSame(
			PostFabricWorkerFinishFutureListener.class,
			futureListener.getClass());
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testPostFabricWorkerFinishFutureListener() throws Exception {

		// Finish with execution exception

		NettyFabricWorkerExecutionChannelHandler
			nettyFabricWorkerExecutionChannelHandler =
				new NettyFabricWorkerExecutionChannelHandler(
					new MockRepository(), new EmbeddedProcessExecutor(),
					Long.MAX_VALUE);

		Path inputResource1 = FileServerTestUtil.createEmptyFile(
			Paths.get("inputResource1"));
		Path inputResource2 = FileServerTestUtil.createEmptyFile(
			Paths.get("inputResource2"));

		Map<Path, Path> inputResources = new HashMap<Path, Path>();

		inputResources.put(inputResource1, inputResource1);
		inputResources.put(inputResource2, inputResource2);

		PostFabricWorkerFinishFutureListener
			postFabricWorkerFinishFutureListener =
				nettyFabricWorkerExecutionChannelHandler.
					new PostFabricWorkerFinishFutureListener(
						_embeddedChannel, createNettyFabricWorkerConfig(),
						new LoadedResources(inputResources, null, null));

		DefaultNoticeableFuture<Serializable> defaultNoticeableFuture =
			new DefaultNoticeableFuture<Serializable>();

		Throwable throwable = new Throwable();

		defaultNoticeableFuture.setException(throwable);

		installNettyFabricAgentStub();

		NettyFabricWorkerStub<Serializable> nettyFabricWorkerStub =
			installNettyFabricWorkerStub();

		NoticeableFuture<?> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		defaultNoticeableFuture.addFutureListener(
			postFabricWorkerFinishFutureListener);

		Assert.assertTrue(Files.notExists(inputResource1));
		Assert.assertTrue(Files.notExists(inputResource2));

		invokeRPC();

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertEquals(throwable, ee.getCause());
		}

		// Finish with null pointer exception

		nettyFabricWorkerStub = installNettyFabricWorkerStub();

		noticeableFuture = nettyFabricWorkerStub.getProcessNoticeableFuture();

		postFabricWorkerFinishFutureListener.complete(null);

		invokeRPC();

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable t = ee.getCause();

			Assert.assertSame(NullPointerException.class, t.getClass());
		}

		// Finish with result

		inputResource1 = FileServerTestUtil.createEmptyFile(
			Paths.get("inputResource1"));
		inputResource2 = FileServerTestUtil.createEmptyFile(
			Paths.get("inputResource2"));

		inputResources = new HashMap<Path, Path>();

		inputResources.put(inputResource1, inputResource1);
		inputResources.put(inputResource2, inputResource2);

		postFabricWorkerFinishFutureListener =
			nettyFabricWorkerExecutionChannelHandler.
				new PostFabricWorkerFinishFutureListener(
					_embeddedChannel, createNettyFabricWorkerConfig(),
					new LoadedResources(inputResources, null, null));

		defaultNoticeableFuture = new DefaultNoticeableFuture<Serializable>();

		defaultNoticeableFuture.set(StringPool.BLANK);

		nettyFabricWorkerStub = installNettyFabricWorkerStub();

		noticeableFuture = nettyFabricWorkerStub.getProcessNoticeableFuture();

		defaultNoticeableFuture.addFutureListener(
			postFabricWorkerFinishFutureListener);

		Assert.assertTrue(Files.notExists(inputResource1));
		Assert.assertTrue(Files.notExists(inputResource2));

		invokeRPC();

		Assert.assertEquals(StringPool.BLANK, noticeableFuture.get());
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testPostLoadResourcesFutureListener() throws Exception {

		// Load resources failure

		ChannelPipeline channelPipeline = _embeddedChannel.pipeline();

		NettyFabricWorkerExecutionChannelHandler
			nettyFabricWorkerExecutionChannelHandler =
				new NettyFabricWorkerExecutionChannelHandler(
					new MockRepository(), new EmbeddedProcessExecutor(),
					Long.MAX_VALUE);

		channelPipeline.addLast(nettyFabricWorkerExecutionChannelHandler);

		ChannelHandlerContext channelHandlerContext =
			channelPipeline.lastContext();

		PostLoadResourcesFutureListener postLoadResourcesFutureListener =
			nettyFabricWorkerExecutionChannelHandler.
				new PostLoadResourcesFutureListener(
					channelHandlerContext, createNettyFabricWorkerConfig());

		DefaultNoticeableFuture<LoadedResources> defaultNoticeableFuture =
			new DefaultNoticeableFuture<LoadedResources>();

		Throwable throwable = new Throwable();

		defaultNoticeableFuture.setException(throwable);

		installNettyFabricAgentStub();

		NettyFabricWorkerStub<Serializable> nettyFabricWorkerStub =
			installNettyFabricWorkerStub();

		NoticeableFuture<Serializable> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		defaultNoticeableFuture.addFutureListener(
			postLoadResourcesFutureListener);

		invokeRPC();

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(throwable, ee.getCause());
		}

		// Loaded resources

		defaultNoticeableFuture =
			new DefaultNoticeableFuture<LoadedResources>();

		defaultNoticeableFuture.set(
			new LoadedResources(
				Collections.<Path, Path>emptyMap(), null, null));

		nettyFabricWorkerStub = installNettyFabricWorkerStub();

		noticeableFuture = nettyFabricWorkerStub.getProcessNoticeableFuture();

		defaultNoticeableFuture.addFutureListener(
			postLoadResourcesFutureListener);

		_embeddedChannel.runPendingTasks();

		FabricAgent fabricAgent = ReflectionTestUtil.getFieldValue(
			nettyFabricWorkerExecutionChannelHandler, "_fabricAgent");

		Collection<? extends FabricWorker<?>> fabricWorkers =
			fabricAgent.getFabricWorkers();

		Assert.assertEquals(1, fabricWorkers.size());
		Assert.assertFalse(noticeableFuture.isDone());
	}

	@AdviseWith(adviceClasses = NettyUtilAdvice.class)
	@Test
	public void testSendResult() throws Exception {

		// Unable to send back fabric worker result

		NettyFabricWorkerExecutionChannelHandler
			nettyFabricWorkerExecutionChannelHandler =
				new NettyFabricWorkerExecutionChannelHandler(
					new MockRepository(), new EmbeddedProcessExecutor(),
					Long.MAX_VALUE);

		Channel channel = NettyTestUtil.createEmptyEmbeddedChannel();

		channel.close();

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			NettyFabricWorkerExecutionChannelHandler.class.getName(),
			Level.SEVERE);

		try {
			nettyFabricWorkerExecutionChannelHandler.sendResult(
				channel, 0, StringPool.BLANK, null);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to send back fabric worker result " +
					"{id=0, result=, throwable=null}",
				logRecord.getMessage());
		}
		finally {
			captureHandler.close();
		}

		// Send back result

		installNettyFabricAgentStub();

		NettyFabricWorkerStub<Serializable> nettyFabricWorkerStub =
			installNettyFabricWorkerStub();

		NoticeableFuture<Serializable> noticeableFuture =
			nettyFabricWorkerStub.getProcessNoticeableFuture();

		nettyFabricWorkerExecutionChannelHandler.sendResult(
			_embeddedChannel, 0, StringPool.BLANK, null);

		invokeRPC();

		Assert.assertEquals(StringPool.BLANK, noticeableFuture.get());

		// Send back exception

		nettyFabricWorkerStub = installNettyFabricWorkerStub();

		noticeableFuture = nettyFabricWorkerStub.getProcessNoticeableFuture();

		Throwable throwable = new Throwable();

		nettyFabricWorkerExecutionChannelHandler.sendResult(
			_embeddedChannel, 0, null, throwable);

		invokeRPC();

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(throwable, ee.getCause());
		}
	}

	protected static Path getAbsolutePath(String pathString) {
		Path path = Paths.get(pathString);

		return path.toAbsolutePath();
	}

	protected NettyFabricWorkerConfig<Serializable>
		createNettyFabricWorkerConfig() {

		Builder builder = new Builder();

		builder.setBootstrapClassPath(StringPool.BLANK);
		builder.setRuntimeClassPath(StringPool.BLANK);

		return new NettyFabricWorkerConfig<Serializable>(
			0, builder.build(),
			new ProcessCallable<Serializable>() {

				@Override
				public Serializable call() {
					return null;
				}

			});
	}

	protected NettyFabricAgentStub installNettyFabricAgentStub() {
		NettyFabricAgentStub nettyFabricAgentStub = new NettyFabricAgentStub(
			_embeddedChannel, new MockRepository(),
			Paths.get("remoteRepositoryPath"), 0);

		NettyChannelAttributes.setNettyFabricAgentStub(
			_embeddedChannel, nettyFabricAgentStub);

		return nettyFabricAgentStub;
	}

	protected NettyFabricWorkerStub<Serializable>
		installNettyFabricWorkerStub() {

		NettyFabricAgentStub nettyFabricAgentStub =
			NettyChannelAttributes.getNettyFabricAgentStub(_embeddedChannel);

		Map<Long, NettyFabricWorkerStub<?>> nettyFabricWorkerStubs =
			ReflectionTestUtil.getFieldValue(
				nettyFabricAgentStub, "_nettyFabricWorkerStubs");

		NettyFabricWorkerStub<Serializable> nettyFabricWorkerStub =
			new NettyFabricWorkerStub<Serializable>(
				0, _embeddedChannel, new MockRepository(),
				Collections.<Path, Path>emptyMap(), 0);

		nettyFabricWorkerStubs.put(0L, nettyFabricWorkerStub);

		return nettyFabricWorkerStub;
	}

	protected void invokeRPC() {
		RPCSerializable rpcSerializable =
			(RPCSerializable)_embeddedChannel.readOutbound();

		rpcSerializable.execute(_embeddedChannel);

		Queue<Object> messages = _embeddedChannel.outboundMessages();

		messages.clear();
	}

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();

}