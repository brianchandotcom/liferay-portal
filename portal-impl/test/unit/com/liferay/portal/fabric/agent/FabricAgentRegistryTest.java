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
import com.liferay.portal.kernel.test.CodeCoverageAssertor;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FabricAgentRegistryTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

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

		RecordFabricAgentListener recordFabricAgentListener =
			new RecordFabricAgentListener();

		fabricAgentRegistry.registerFabricAgentListener(
			recordFabricAgentListener);

		FabricAgent fabricAgent1 = new LocalFabricAgent(
			new EmbeddedProcessExecutor());

		Assert.assertTrue(
			fabricAgentRegistry.registerFabricAgent(fabricAgent1));
		Assert.assertSame(
			fabricAgent1,
			recordFabricAgentListener.takeRegisteredFabricAgent());
		Assert.assertNull(
			recordFabricAgentListener.takeUnregisteredFabricAgent());
		Assert.assertFalse(
			fabricAgentRegistry.registerFabricAgent(fabricAgent1));
		Assert.assertNull(
			recordFabricAgentListener.takeRegisteredFabricAgent());
		Assert.assertNull(
			recordFabricAgentListener.takeUnregisteredFabricAgent());

		List<FabricAgent> fabricAgents = fabricAgentRegistry.getFabricAgents();

		Assert.assertEquals(1, fabricAgents.size());
		Assert.assertTrue(fabricAgents.contains(fabricAgent1));

		FabricAgent fabricAgent2 = new LocalFabricAgent(
			new EmbeddedProcessExecutor());

		Assert.assertTrue(
			fabricAgentRegistry.registerFabricAgent(fabricAgent2));
		Assert.assertSame(
			fabricAgent2,
			recordFabricAgentListener.takeRegisteredFabricAgent());
		Assert.assertNull(
			recordFabricAgentListener.takeUnregisteredFabricAgent());
		Assert.assertFalse(
			fabricAgentRegistry.registerFabricAgent(fabricAgent2));
		Assert.assertNull(
			recordFabricAgentListener.takeRegisteredFabricAgent());
		Assert.assertNull(
			recordFabricAgentListener.takeUnregisteredFabricAgent());

		fabricAgents = fabricAgentRegistry.getFabricAgents();

		Assert.assertEquals(2, fabricAgents.size());
		Assert.assertTrue(fabricAgents.contains(fabricAgent1));
		Assert.assertTrue(fabricAgents.contains(fabricAgent2));
		Assert.assertTrue(
			fabricAgentRegistry.unregisterFabricAgent(fabricAgent1));
		Assert.assertNull(
			recordFabricAgentListener.takeRegisteredFabricAgent());
		Assert.assertSame(
			fabricAgent1,
			recordFabricAgentListener.takeUnregisteredFabricAgent());
		Assert.assertFalse(
			fabricAgentRegistry.unregisterFabricAgent(fabricAgent1));
		Assert.assertNull(
			recordFabricAgentListener.takeRegisteredFabricAgent());
		Assert.assertNull(
			recordFabricAgentListener.takeUnregisteredFabricAgent());

		fabricAgents = fabricAgentRegistry.getFabricAgents();

		Assert.assertEquals(1, fabricAgents.size());
		Assert.assertTrue(fabricAgents.contains(fabricAgent2));
		Assert.assertTrue(
			fabricAgentRegistry.unregisterFabricAgent(fabricAgent2));
		Assert.assertNull(
			recordFabricAgentListener.takeRegisteredFabricAgent());
		Assert.assertSame(
			fabricAgent2,
			recordFabricAgentListener.takeUnregisteredFabricAgent());
		Assert.assertFalse(
			fabricAgentRegistry.unregisterFabricAgent(fabricAgent2));
		Assert.assertNull(
			recordFabricAgentListener.takeRegisteredFabricAgent());
		Assert.assertNull(
			recordFabricAgentListener.takeUnregisteredFabricAgent());

		fabricAgents = fabricAgentRegistry.getFabricAgents();

		Assert.assertTrue(fabricAgents.isEmpty());
	}

	@Test
	public void testRegisterAndUnregisterFabricAgentListener() {
		FabricAgentRegistry fabricAgentRegistry = new FabricAgentRegistry(
			new LocalFabricAgent(new EmbeddedProcessExecutor()));

		RecordFabricAgentListener recordFabricAgentListener1 =
			new RecordFabricAgentListener();

		Assert.assertTrue(
			fabricAgentRegistry.registerFabricAgentListener(
				recordFabricAgentListener1));
		Assert.assertFalse(
			fabricAgentRegistry.registerFabricAgentListener(
				recordFabricAgentListener1));

		List<FabricAgentListener> fabricAgentListeners =
			fabricAgentRegistry.getFabricAgentListeners();

		Assert.assertEquals(1, fabricAgentListeners.size());
		Assert.assertTrue(
			fabricAgentListeners.contains(recordFabricAgentListener1));

		RecordFabricAgentListener recordFabricAgentListener2 =
			new RecordFabricAgentListener();

		Assert.assertTrue(
			fabricAgentRegistry.registerFabricAgentListener(
				recordFabricAgentListener2));
		Assert.assertFalse(
			fabricAgentRegistry.registerFabricAgentListener(
				recordFabricAgentListener2));

		fabricAgentListeners = fabricAgentRegistry.getFabricAgentListeners();

		Assert.assertEquals(2, fabricAgentListeners.size());
		Assert.assertTrue(
			fabricAgentListeners.contains(recordFabricAgentListener1));
		Assert.assertTrue(
			fabricAgentListeners.contains(recordFabricAgentListener2));
		Assert.assertTrue(
			fabricAgentRegistry.unregisterFabricAgentListener(
				recordFabricAgentListener1));
		Assert.assertFalse(
			fabricAgentRegistry.unregisterFabricAgentListener(
				recordFabricAgentListener1));

		fabricAgentListeners = fabricAgentRegistry.getFabricAgentListeners();

		Assert.assertEquals(1, fabricAgentListeners.size());
		Assert.assertTrue(
			fabricAgentListeners.contains(recordFabricAgentListener2));
		Assert.assertTrue(
			fabricAgentRegistry.unregisterFabricAgentListener(
				recordFabricAgentListener2));
		Assert.assertFalse(
			fabricAgentRegistry.unregisterFabricAgentListener(
				recordFabricAgentListener2));

		fabricAgentListeners = fabricAgentRegistry.getFabricAgentListeners();

		Assert.assertTrue(fabricAgentListeners.isEmpty());
	}

	private static class RecordFabricAgentListener
		implements FabricAgentListener {

		@Override
		public void registered(FabricAgent fabricAgent) {
			_registeredFabricAgent = fabricAgent;
		}

		public FabricAgent takeRegisteredFabricAgent() {
			FabricAgent fabricAgent = _registeredFabricAgent;

			_registeredFabricAgent = null;

			return fabricAgent;
		}

		public FabricAgent takeUnregisteredFabricAgent() {
			FabricAgent fabricAgent = _unregisteredFabricAgent;

			_unregisteredFabricAgent = null;

			return fabricAgent;
		}

		@Override
		public void unregistered(FabricAgent fabricAgent) {
			_unregisteredFabricAgent = fabricAgent;
		}

		private FabricAgent _registeredFabricAgent;
		private FabricAgent _unregisteredFabricAgent;

	}

}