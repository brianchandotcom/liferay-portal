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

package com.liferay.portal.fabric.agent;

import com.liferay.portal.fabric.local.agent.EmbeddedProcessExecutor;
import com.liferay.portal.fabric.local.agent.LocalFabricAgent;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FabricAgentRegistryTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		try {
			new FabricAgentRegistry(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Default fabric agent is null", npe.getMessage());
		}

		FabricAgent fabricAgent = new LocalFabricAgent(
			new EmbeddedProcessExecutor());

		FabricAgentRegistry fabricAgentRegistry = new FabricAgentRegistry(
			fabricAgent);

		Assert.assertSame(
			fabricAgent, fabricAgentRegistry.getDefaultFabricAgent());
	}

	@Test
	public void testRegisterAndUnregisterFabricAgent() {
		FabricAgentRegistry fabricAgentRegistry = new FabricAgentRegistry(
			new LocalFabricAgent(new EmbeddedProcessExecutor()));

		Recorder recorder = new Recorder();

		fabricAgentRegistry.registerFabricAgentListener(recorder);

		FabricAgent fabricAgent1 = new LocalFabricAgent(
			new EmbeddedProcessExecutor());

		Assert.assertTrue(
			fabricAgentRegistry.registerFabricAgent(fabricAgent1, recorder));

		recorder.validate(2, fabricAgent1, 0);

		Assert.assertFalse(
			fabricAgentRegistry.registerFabricAgent(fabricAgent1, recorder));

		recorder.validate();

		List<FabricAgent> fabricAgents = fabricAgentRegistry.getFabricAgents();

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());
		Assert.assertTrue(
			fabricAgents.toString(), fabricAgents.contains(fabricAgent1));

		FabricAgent fabricAgent2 = new LocalFabricAgent(
			new EmbeddedProcessExecutor());

		Assert.assertTrue(
			fabricAgentRegistry.registerFabricAgent(fabricAgent2, null));

		recorder.validate(fabricAgent2, 0);

		Assert.assertFalse(
			fabricAgentRegistry.registerFabricAgent(fabricAgent2, null));

		recorder.validate();

		fabricAgents = fabricAgentRegistry.getFabricAgents();

		Assert.assertEquals(fabricAgents.toString(), 2, fabricAgents.size());
		Assert.assertTrue(
			fabricAgents.toString(), fabricAgents.contains(fabricAgent1));
		Assert.assertTrue(
			fabricAgents.toString(), fabricAgents.contains(fabricAgent2));

		Assert.assertTrue(
			fabricAgentRegistry.unregisterFabricAgent(fabricAgent1, recorder));

		recorder.validate(2, fabricAgent1, 1);

		Assert.assertFalse(
			fabricAgentRegistry.unregisterFabricAgent(fabricAgent1, recorder));

		recorder.validate();

		fabricAgents = fabricAgentRegistry.getFabricAgents();

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());
		Assert.assertTrue(
			fabricAgents.toString(), fabricAgents.contains(fabricAgent2));

		Assert.assertTrue(
			fabricAgentRegistry.unregisterFabricAgent(fabricAgent2, null));

		recorder.validate(fabricAgent2, 1);

		Assert.assertFalse(
			fabricAgentRegistry.unregisterFabricAgent(fabricAgent2, null));

		recorder.validate();

		fabricAgents = fabricAgentRegistry.getFabricAgents();

		Assert.assertTrue(fabricAgents.toString(), fabricAgents.isEmpty());
	}

	@Test
	public void testRegisterAndUnregisterFabricAgentListener() {
		FabricAgentRegistry fabricAgentRegistry = new FabricAgentRegistry(
			new LocalFabricAgent(new EmbeddedProcessExecutor()));

		Recorder recorder1 = new Recorder();

		Assert.assertTrue(
			fabricAgentRegistry.registerFabricAgentListener(recorder1));
		Assert.assertFalse(
			fabricAgentRegistry.registerFabricAgentListener(recorder1));

		List<FabricAgentListener> fabricAgentListeners =
			fabricAgentRegistry.getFabricAgentListeners();

		Assert.assertEquals(
			fabricAgentListeners.toString(), 1, fabricAgentListeners.size());
		Assert.assertTrue(
			fabricAgentListeners.toString(),
			fabricAgentListeners.contains(recorder1));

		Recorder recorder2 = new Recorder();

		Assert.assertTrue(
			fabricAgentRegistry.registerFabricAgentListener(recorder2));
		Assert.assertFalse(
			fabricAgentRegistry.registerFabricAgentListener(recorder2));

		fabricAgentListeners = fabricAgentRegistry.getFabricAgentListeners();

		Assert.assertEquals(
			fabricAgentListeners.toString(), 2, fabricAgentListeners.size());
		Assert.assertTrue(
			fabricAgentListeners.toString(),
			fabricAgentListeners.contains(recorder1));
		Assert.assertTrue(
			fabricAgentListeners.toString(),
			fabricAgentListeners.contains(recorder2));

		Assert.assertTrue(
			fabricAgentRegistry.unregisterFabricAgentListener(recorder1));
		Assert.assertFalse(
			fabricAgentRegistry.unregisterFabricAgentListener(recorder1));

		fabricAgentListeners = fabricAgentRegistry.getFabricAgentListeners();

		Assert.assertEquals(
			fabricAgentListeners.toString(), 1, fabricAgentListeners.size());
		Assert.assertTrue(
			fabricAgentListeners.toString(),
			fabricAgentListeners.contains(recorder2));

		Assert.assertTrue(
			fabricAgentRegistry.unregisterFabricAgentListener(recorder2));
		Assert.assertFalse(
			fabricAgentRegistry.unregisterFabricAgentListener(recorder2));

		fabricAgentListeners = fabricAgentRegistry.getFabricAgentListeners();

		Assert.assertTrue(
			fabricAgentListeners.toString(), fabricAgentListeners.isEmpty());
	}

	private static class Recorder implements FabricAgentListener, Runnable {

		@Override
		public void registered(FabricAgent fabricAgent) {
			_objects.add(fabricAgent);
			_objects.add(0);
		}

		@Override
		public void run() {
			_objects.add(2);
		}

		@Override
		public void unregistered(FabricAgent fabricAgent) {
			_objects.add(fabricAgent);
			_objects.add(1);
		}

		public void validate(Object... objects) {
			Assert.assertArrayEquals(objects, _objects.toArray(new Object[0]));

			_objects.clear();
		}

		private final List<Object> _objects = new ArrayList<>();

	}

}