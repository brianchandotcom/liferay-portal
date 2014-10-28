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

package com.liferay.portal.fabric;

import com.liferay.portal.fabric.agent.FabricAgentRegistry;
import com.liferay.portal.fabric.agent.PortalClassPathWarmupFabricAgentListener;
import com.liferay.portal.fabric.agent.selectors.FabricAgentSelector;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.util.PropsValues;

/**
 * @author Shuyang Zhou
 */
public class FabricProcessExecutorFactory {

	public static ProcessExecutor createFabricProcessExecutor(
			FabricAgentRegistry fabricAgentRegistry,
			ProcessExecutor processExecutor)
		throws Exception {

		if (!PropsValues.PORTAL_FABRIC_ENABLED) {
			return processExecutor;
		}

		if (PropsValues.PORTAL_FABRIC_SERVER_WARMUP_AGENT_ON_REGISTER) {
			fabricAgentRegistry.registerFabricAgentListener(
				new PortalClassPathWarmupFabricAgentListener());
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		Class<? extends FabricAgentSelector> fabricAgentSelectorClass =
			(Class<? extends FabricAgentSelector>)classLoader.loadClass(
				PropsValues.PORTAL_FABRIC_AGENT_SELECTOR_CLASS);

		return new FabricProcessExecutor(
			fabricAgentRegistry, fabricAgentSelectorClass.newInstance());
	}

}