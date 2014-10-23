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
import com.liferay.portal.fabric.status.FabricStatus;
import com.liferay.portal.kernel.process.ProcessCallable;

import java.lang.management.RuntimeMXBean;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public abstract class SystemPropertiesFilterFabricAgentSelector
	implements FabricAgentSelector {

	@Override
	public Collection<FabricAgent> select(
		Collection<FabricAgent> fabricAgents,
		ProcessCallable<?> processCallable) {

		Iterator<FabricAgent> iterator = fabricAgents.iterator();

		while (iterator.hasNext()) {
			FabricAgent fabricAgent = iterator.next();

			FabricStatus fabricStatus = fabricAgent.getFabricStatus();

			RuntimeMXBean runtimeMXBean = fabricStatus.getRuntimeMXBean();

			if (!accept(runtimeMXBean.getSystemProperties(), processCallable)) {
				iterator.remove();
			}
		}

		return fabricAgents;
	}

	protected abstract boolean accept(
		Map<String, String> systemProperties,
		ProcessCallable<?> processCallable);

}