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

import com.liferay.portal.fabric.agent.FabricAgent;
import com.liferay.portal.fabric.agent.FabricAgentRegistry;
import com.liferay.portal.fabric.local.agent.EmbeddedProcessExecutor;
import com.liferay.portal.fabric.local.agent.LocalFabricAgent;
import com.liferay.portal.fabric.netty.agent.NettyFabricAgentConfig;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileServerTestUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricAgentRegistrationChannelHandlerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@After
	public void tearDown() {
		FileServerTestUtil.cleanUp();
	}

	@Test
	public void testConstructor() {
		try {
			new NettyFabricAgentRegistrationChannelHandler(
				null, null, null, 0, 0, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Fabric agent registry is null", npe.getMessage());
		}

		try {
			new NettyFabricAgentRegistrationChannelHandler(
				new FabricAgentRegistry(
					new LocalFabricAgent(new EmbeddedProcessExecutor())),
				null, null, 0, 0, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Repository parent path is null", npe.getMessage());
		}

		try {
			new NettyFabricAgentRegistrationChannelHandler(
				new FabricAgentRegistry(
					new LocalFabricAgent(new EmbeddedProcessExecutor())),
				Paths.get("RepositoryParentPath"), null, 0, 0, 0);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Event executor group is null", npe.getMessage());
		}

		new NettyFabricAgentRegistrationChannelHandler(
			new FabricAgentRegistry(
				new LocalFabricAgent(new EmbeddedProcessExecutor())),
			Paths.get("RepositoryParentPath"), new DefaultEventExecutorGroup(1),
			0, 0, 0);
	}

	@Test
	public void testExceptionCaught() throws IOException {
		FabricAgentRegistry fabricAgentRegistry = new FabricAgentRegistry(
			new LocalFabricAgent(new EmbeddedProcessExecutor()));

		Path repositoryParentPath = FileServerTestUtil.registerForCleanUp(
			Files.createFile(Paths.get("RepositoryParentPath")));

		EmbeddedChannel embeddedChannel = new EmbeddedChannel(
			new NettyFabricAgentRegistrationChannelHandler(
				fabricAgentRegistry, repositoryParentPath,
				new DefaultEventExecutorGroup(1), 0, 0, 0));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					NettyFabricAgentRegistrationChannelHandler.class.getName(),
					Level.INFO)) {

			String embeddedChannelToString = embeddedChannel.toString();

			embeddedChannel.writeInbound(
				new NettyFabricAgentConfig(new File("RepositoryFolder")));

			Assert.assertFalse(embeddedChannel.isOpen());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Closing " + embeddedChannelToString + " due to:",
				logRecord.getMessage());

			Throwable throwable = logRecord.getThrown();

			Assert.assertTrue(throwable instanceof IOException);

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				embeddedChannel + " is closed", logRecord.getMessage());
		}
	}

	@Test
	public void testRegister() throws IOException {

		// Without log

		FabricAgentRegistry fabricAgentRegistry = new FabricAgentRegistry(
			new LocalFabricAgent(new EmbeddedProcessExecutor()));

		Path repositoryParentPath = FileServerTestUtil.registerForCleanUp(
			Files.createDirectory(Paths.get("RepositoryParentPath")));

		EmbeddedChannel embeddedChannel = new EmbeddedChannel(
			new NettyFabricAgentRegistrationChannelHandler(
				fabricAgentRegistry, repositoryParentPath,
				new DefaultEventExecutorGroup(1), 0, 0, 0));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					NettyFabricAgentRegistrationChannelHandler.class.getName(),
					Level.OFF)) {

			embeddedChannel.writeInbound(
				new NettyFabricAgentConfig(new File("RepositoryFolder")));

			List<FabricAgent> fabricAgents =
				fabricAgentRegistry.getFabricAgents();

			Assert.assertEquals(
				fabricAgents.toString(), 1, fabricAgents.size());
			Assert.assertSame(
				fabricAgents.get(0),
				NettyChannelAttributes.getNettyFabricAgentStub(
					embeddedChannel));

			embeddedChannel.close();

			fabricAgents = fabricAgentRegistry.getFabricAgents();

			Assert.assertTrue(fabricAgents.toString(), fabricAgents.isEmpty());
		}

		// With log

		embeddedChannel = new EmbeddedChannel(
			new NettyFabricAgentRegistrationChannelHandler(
				fabricAgentRegistry, repositoryParentPath,
				new DefaultEventExecutorGroup(1), 0, 0, 0));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					NettyFabricAgentRegistrationChannelHandler.class.getName(),
					Level.INFO)) {

			embeddedChannel.writeInbound(
				new NettyFabricAgentConfig(new File("RepositoryFolder")));

			List<FabricAgent> fabricAgents =
				fabricAgentRegistry.getFabricAgents();

			Assert.assertEquals(
				fabricAgents.toString(), 1, fabricAgents.size());
			Assert.assertSame(
				fabricAgents.get(0),
				NettyChannelAttributes.getNettyFabricAgentStub(
					embeddedChannel));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.remove(0);

			Assert.assertEquals(
				"Registered fabric agent on " + embeddedChannel,
				logRecord.getMessage());

			embeddedChannel.close();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			logRecord = logRecords.remove(0);

			Assert.assertEquals(
				"Unregistered fabric agent on " + embeddedChannel,
				logRecord.getMessage());

			fabricAgents = fabricAgentRegistry.getFabricAgents();

			Assert.assertTrue(fabricAgents.toString(), fabricAgents.isEmpty());
		}
	}

	@Test
	public void testRegisterReject() throws IOException {

		// Without log

		FabricAgentRegistry fabricAgentRegistry = new FabricAgentRegistry(
			new LocalFabricAgent(new EmbeddedProcessExecutor()));

		Path repositoryParentPath = FileServerTestUtil.registerForCleanUp(
			Files.createDirectory(Paths.get("RepositoryParentPath")));

		EmbeddedChannel embeddedChannel = new EmbeddedChannel(
			new NettyFabricAgentRegistrationChannelHandler(
				fabricAgentRegistry, repositoryParentPath,
				new DefaultEventExecutorGroup(1), 0, 0, 0));

		embeddedChannel.writeInbound(
			new NettyFabricAgentConfig(new File("RepositoryFolder")));

		List<FabricAgent> fabricAgents = fabricAgentRegistry.getFabricAgents();

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());
		Assert.assertSame(
			fabricAgents.get(0),
			NettyChannelAttributes.getNettyFabricAgentStub(embeddedChannel));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					NettyFabricAgentRegistrationChannelHandler.class.getName(),
					Level.OFF)) {

			embeddedChannel.writeInbound(
				new NettyFabricAgentConfig(new File("RepositoryFolder")));

			fabricAgents = fabricAgentRegistry.getFabricAgents();

			Assert.assertEquals(
				fabricAgents.toString(), 1, fabricAgents.size());
			Assert.assertSame(
				fabricAgents.get(0),
				NettyChannelAttributes.getNettyFabricAgentStub(
					embeddedChannel));
		}

		// With log

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					NettyFabricAgentRegistrationChannelHandler.class.getName(),
					Level.WARNING)) {

			embeddedChannel.writeInbound(
				new NettyFabricAgentConfig(new File("RepositoryFolder")));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Rejected duplicated fabric agent on " + embeddedChannel,
				logRecord.getMessage());

			fabricAgents = fabricAgentRegistry.getFabricAgents();

			Assert.assertEquals(
				fabricAgents.toString(), 1, fabricAgents.size());
			Assert.assertSame(
				fabricAgents.get(0),
				NettyChannelAttributes.getNettyFabricAgentStub(
					embeddedChannel));
		}
	}

	@Test
	public void testUnableToUnregister() throws IOException {

		// Without log

		FabricAgentRegistry fabricAgentRegistry = new FabricAgentRegistry(
			new LocalFabricAgent(new EmbeddedProcessExecutor()));

		Path repositoryParentPath = FileServerTestUtil.registerForCleanUp(
			Files.createDirectory(Paths.get("RepositoryParentPath")));

		EmbeddedChannel embeddedChannel = new EmbeddedChannel(
			new NettyFabricAgentRegistrationChannelHandler(
				fabricAgentRegistry, repositoryParentPath,
				new DefaultEventExecutorGroup(1), 0, 0, 0));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					NettyFabricAgentRegistrationChannelHandler.class.getName(),
					Level.OFF)) {

			embeddedChannel.writeInbound(
				new NettyFabricAgentConfig(new File("RepositoryFolder")));

			List<FabricAgent> fabricAgents =
				fabricAgentRegistry.getFabricAgents();

			Assert.assertEquals(
				fabricAgents.toString(), 1, fabricAgents.size());
			Assert.assertSame(
				fabricAgents.get(0),
				NettyChannelAttributes.getNettyFabricAgentStub(
					embeddedChannel));

			fabricAgentRegistry.unregisterFabricAgent(
				fabricAgents.get(0), null);

			embeddedChannel.close();

			fabricAgents = fabricAgentRegistry.getFabricAgents();

			Assert.assertTrue(fabricAgents.toString(), fabricAgents.isEmpty());
		}

		// With log

		embeddedChannel = new EmbeddedChannel(
			new NettyFabricAgentRegistrationChannelHandler(
				fabricAgentRegistry, repositoryParentPath,
				new DefaultEventExecutorGroup(1), 0, 0, 0));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					NettyFabricAgentRegistrationChannelHandler.class.getName(),
					Level.WARNING)) {

			embeddedChannel.writeInbound(
				new NettyFabricAgentConfig(new File("RepositoryFolder")));

			List<FabricAgent> fabricAgents =
				fabricAgentRegistry.getFabricAgents();

			Assert.assertEquals(
				fabricAgents.toString(), 1, fabricAgents.size());
			Assert.assertSame(
				fabricAgents.get(0),
				NettyChannelAttributes.getNettyFabricAgentStub(
					embeddedChannel));

			fabricAgentRegistry.unregisterFabricAgent(
				fabricAgents.get(0), null);

			embeddedChannel.close();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to unregister fabric agent on " + embeddedChannel,
				logRecord.getMessage());

			fabricAgents = fabricAgentRegistry.getFabricAgents();

			Assert.assertTrue(fabricAgents.toString(), fabricAgents.isEmpty());
		}
	}

}