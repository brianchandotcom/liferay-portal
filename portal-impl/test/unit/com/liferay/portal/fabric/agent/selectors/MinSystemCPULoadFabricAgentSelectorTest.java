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

package com.liferay.portal.fabric.agent.selectors;

import com.liferay.portal.fabric.agent.FabricAgent;
import com.liferay.portal.fabric.status.AdvancedOperatingSystemMXBean;
import com.liferay.portal.fabric.status.FabricStatus;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class MinSystemCPULoadFabricAgentSelectorTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testSelect() {

		// Empty collection

		FabricAgentSelector fabricAgentSelector =
			new MinSystemCPULoadFabricAgentSelector();

		Assert.assertSame(
			Collections.emptySet(),
			fabricAgentSelector.select(
				Collections.<FabricAgent>emptySet(), null));

		// null vs. null

		FabricAgent fabricAgent1 = createFabricAgent(null);
		FabricAgent fabricAgent2 = createFabricAgent(null);

		Collection<FabricAgent> fabricAgents = fabricAgentSelector.select(
			new ArrayList<FabricAgent>(
				Arrays.asList(fabricAgent1, fabricAgent2)),
			null);

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());

		Iterator<FabricAgent> iterator = fabricAgents.iterator();

		Assert.assertSame(fabricAgent1, iterator.next());

		// null vs. 1

		fabricAgent1 = createFabricAgent(null);
		fabricAgent2 = createFabricAgent(1D);

		fabricAgents = fabricAgentSelector.select(
			new ArrayList<FabricAgent>(
				Arrays.asList(fabricAgent1, fabricAgent2)),
			null);

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());

		iterator = fabricAgents.iterator();

		Assert.assertSame(fabricAgent2, iterator.next());

		// 1 vs. null

		fabricAgent1 = createFabricAgent(1D);
		fabricAgent2 = createFabricAgent(null);

		fabricAgents = fabricAgentSelector.select(
			new ArrayList<FabricAgent>(
				Arrays.asList(fabricAgent1, fabricAgent2)),
			null);

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());

		iterator = fabricAgents.iterator();

		Assert.assertSame(fabricAgent1, iterator.next());

		// 1 vs. 2

		fabricAgent1 = createFabricAgent(1D);
		fabricAgent2 = createFabricAgent(2D);

		fabricAgents = fabricAgentSelector.select(
			new ArrayList<FabricAgent>(
				Arrays.asList(fabricAgent1, fabricAgent2)),
			null);

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());

		iterator = fabricAgents.iterator();

		Assert.assertSame(fabricAgent1, iterator.next());
	}

	protected FabricAgent createFabricAgent(Double systemCpuLoad) {
		return (FabricAgent)ProxyUtil.newProxyInstance(
			FabricAgent.class.getClassLoader(),
			new Class<?>[] {FabricAgent.class},
			new FabricAgentInvocationHandler(systemCpuLoad));
	}

	protected static class AdvancedOperatingSystemMXBeanInvocationHandler
		implements InvocationHandler {

		public AdvancedOperatingSystemMXBeanInvocationHandler(
			Double systemCpuLoad) {

			_systemCpuLoad = systemCpuLoad;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			String methodName = method.getName();

			if (methodName.equals("getSystemCpuLoad")) {
				return _systemCpuLoad;
			}

			if (methodName.equals("toString")) {
				return String.valueOf(_systemCpuLoad);
			}

			throw new UnsupportedOperationException();
		}

		private final Double _systemCpuLoad;

	}

	protected static class FabricAgentInvocationHandler
		implements InvocationHandler {

		public FabricAgentInvocationHandler(Double systemCpuLoad) {
			_systemCpuLoad = systemCpuLoad;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			String methodName = method.getName();

			if (methodName.equals("getFabricStatus")) {
				return ProxyUtil.newProxyInstance(
					FabricStatus.class.getClassLoader(),
					new Class<?>[] {FabricStatus.class},
					new FabricStatusInvocationHandler(_systemCpuLoad));
			}

			if (methodName.equals("toString")) {
				return String.valueOf(_systemCpuLoad);
			}

			throw new UnsupportedOperationException();
		}

		private final Double _systemCpuLoad;

	}

	protected static class FabricStatusInvocationHandler
		implements InvocationHandler {

		public FabricStatusInvocationHandler(Double systemCpuLoad) {
			_systemCpuLoad = systemCpuLoad;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			String methodName = method.getName();

			if (methodName.equals("getAdvancedOperatingSystemMXBean")) {
				return ProxyUtil.newProxyInstance(
					AdvancedOperatingSystemMXBean.class.getClassLoader(),
					new Class<?>[] {AdvancedOperatingSystemMXBean.class},
					new AdvancedOperatingSystemMXBeanInvocationHandler(
						_systemCpuLoad));
			}

			if (methodName.equals("toString")) {
				return String.valueOf(_systemCpuLoad);
			}

			throw new UnsupportedOperationException();
		}

		private final Double _systemCpuLoad;

	}

}