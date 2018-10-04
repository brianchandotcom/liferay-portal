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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
public class MaxFreePhysicalMemoryFabricAgentSelectorTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testSelect() {

		// Empty collection

		FabricAgentSelector fabricAgentSelector =
			new MaxFreePhysicalMemoryFabricAgentSelector();

		Assert.assertSame(
			Collections.emptySet(),
			fabricAgentSelector.select(
				Collections.<FabricAgent>emptySet(), null));

		// null VS null

		FabricAgent fabricAgent1 = createFabricAgent(null);
		FabricAgent fabricAgent2 = createFabricAgent(null);

		Collection<FabricAgent> fabricAgents = fabricAgentSelector.select(
			new ArrayList<FabricAgent>(
				Arrays.asList(fabricAgent1, fabricAgent2)),
			null);

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());

		Iterator<FabricAgent> iterator = fabricAgents.iterator();

		Assert.assertSame(fabricAgent1, iterator.next());

		// null VS 1

		fabricAgent1 = createFabricAgent(null);
		fabricAgent2 = createFabricAgent(1L);

		fabricAgents = fabricAgentSelector.select(
			new ArrayList<FabricAgent>(
				Arrays.asList(fabricAgent1, fabricAgent2)),
			null);

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());

		iterator = fabricAgents.iterator();

		Assert.assertSame(fabricAgent2, iterator.next());

		// 1 VS null

		fabricAgent1 = createFabricAgent(1L);
		fabricAgent2 = createFabricAgent(null);

		fabricAgents = fabricAgentSelector.select(
			new ArrayList<FabricAgent>(
				Arrays.asList(fabricAgent1, fabricAgent2)),
			null);

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());

		iterator = fabricAgents.iterator();

		Assert.assertSame(fabricAgent1, iterator.next());

		// 1 VS 2

		fabricAgent1 = createFabricAgent(1L);
		fabricAgent2 = createFabricAgent(2L);

		fabricAgents = fabricAgentSelector.select(
			new ArrayList<FabricAgent>(
				Arrays.asList(fabricAgent1, fabricAgent2)),
			null);

		Assert.assertEquals(fabricAgents.toString(), 1, fabricAgents.size());

		iterator = fabricAgents.iterator();

		Assert.assertSame(fabricAgent2, iterator.next());
	}

	protected FabricAgent createFabricAgent(Long freePhysicalMemorySize) {
		return (FabricAgent)Proxy.newProxyInstance(
			FabricAgent.class.getClassLoader(),
			new Class<?>[] {FabricAgent.class},
			new FabricAgentInvocationHandler(freePhysicalMemorySize));
	}

	protected static class AdvancedOperatingSystemMXBeanInvocationHandler
		implements InvocationHandler {

		public AdvancedOperatingSystemMXBeanInvocationHandler(
			Long freePhysicalMemorySize) {

			_freePhysicalMemorySize = freePhysicalMemorySize;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			String methodName = method.getName();

			if (methodName.equals("getFreePhysicalMemorySize")) {
				return _freePhysicalMemorySize;
			}

			if (methodName.equals("toString")) {
				return String.valueOf(_freePhysicalMemorySize);
			}

			throw new UnsupportedOperationException();
		}

		private final Long _freePhysicalMemorySize;

	}

	protected static class FabricAgentInvocationHandler
		implements InvocationHandler {

		public FabricAgentInvocationHandler(Long freePhysicalMemorySize) {
			_freePhysicalMemorySize = freePhysicalMemorySize;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			String methodName = method.getName();

			if (methodName.equals("getFabricStatus")) {
				return Proxy.newProxyInstance(
					FabricStatus.class.getClassLoader(),
					new Class<?>[] {FabricStatus.class},
					new FabricStatusInvocationHandler(_freePhysicalMemorySize));
			}

			if (methodName.equals("toString")) {
				return String.valueOf(_freePhysicalMemorySize);
			}

			throw new UnsupportedOperationException();
		}

		private final Long _freePhysicalMemorySize;

	}

	protected static class FabricStatusInvocationHandler
		implements InvocationHandler {

		public FabricStatusInvocationHandler(Long freePhysicalMemorySize) {
			_freePhysicalMemorySize = freePhysicalMemorySize;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			String methodName = method.getName();

			if (methodName.equals("getAdvancedOperatingSystemMXBean")) {
				return Proxy.newProxyInstance(
					AdvancedOperatingSystemMXBean.class.getClassLoader(),
					new Class<?>[] {AdvancedOperatingSystemMXBean.class},
					new AdvancedOperatingSystemMXBeanInvocationHandler(
						_freePhysicalMemorySize));
			}

			if (methodName.equals("toString")) {
				return String.valueOf(_freePhysicalMemorySize);
			}

			throw new UnsupportedOperationException();
		}

		private final Long _freePhysicalMemorySize;

	}

}