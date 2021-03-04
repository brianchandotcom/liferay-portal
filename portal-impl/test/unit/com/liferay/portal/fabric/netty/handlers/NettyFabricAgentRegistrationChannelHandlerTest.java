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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricAgentRegistrationChannelHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

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
		catch (NullPointerException nullPointerException) {
			Assert.assertEquals(
				"Fabric agent registry is null",
				nullPointerException.getMessage());
		}

		try {
			new NettyFabricAgentRegistrationChannelHandler(
				new FabricAgentRegistry(
					new LocalFabricAgent(new EmbeddedProcessExecutor())),
				null, null, 0, 0, 0);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
			Assert.assertEquals(
				"Repository parent path is null",
				nullPointerException.getMessage());
		}

		try {
			new NettyFabricAgentRegistrationChannelHandler(
				new FabricAgentRegistry(
					new LocalFabricAgent(new EmbeddedProcessExecutor())),
				Paths.get("RepositoryParentPath"), null, 0, 0, 0);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
			Assert.assertEquals(
				"Event executor group is null",
				nullPointerException.getMessage());
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

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyFabricAgentRegistrationChannelHandler.class.getName(),
				Level.INFO)) {

			String embeddedChannelToString = embeddedChannel.toString();

			embeddedChannel.writeInbound(
				new NettyFabricAgentConfig(new File("RepositoryFolder")));

			Assert.assertFalse(embeddedChannel.isOpen());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Closing " + embeddedChannelToString + " due to:",
				logEntry.getMessage());

			Throwable throwable = logEntry.getThrowable();

			Assert.assertTrue(throwable instanceof IOException);

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				embeddedChannel + " is closed", logEntry.getMessage());
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

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
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

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
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

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.remove(0);

			Assert.assertEquals(
				"Registered fabric agent on " + embeddedChannel,
				logEntry.getMessage());

			embeddedChannel.close();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			logEntry = logEntries.remove(0);

			Assert.assertEquals(
				"Unregistered fabric agent on " + embeddedChannel,
				logEntry.getMessage());

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

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
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

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				NettyFabricAgentRegistrationChannelHandler.class.getName(),
				Level.WARNING)) {

			embeddedChannel.writeInbound(
				new NettyFabricAgentConfig(new File("RepositoryFolder")));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Rejected duplicated fabric agent on " + embeddedChannel,
				logEntry.getMessage());

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

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
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

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
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

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to unregister fabric agent on " + embeddedChannel,
				logEntry.getMessage());

			fabricAgents = fabricAgentRegistry.getFabricAgents();

			Assert.assertTrue(fabricAgents.toString(), fabricAgents.isEmpty());
		}
	}

}