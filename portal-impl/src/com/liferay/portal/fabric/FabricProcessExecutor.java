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

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.portal.fabric.agent.FabricAgent;
import com.liferay.portal.fabric.agent.FabricAgentRegistry;
import com.liferay.portal.fabric.agent.selectors.FabricAgentSelector;
import com.liferay.portal.fabric.worker.FabricWorker;

import java.io.Serializable;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Shuyang Zhou
 */
public class FabricProcessExecutor implements ProcessExecutor {

	public FabricProcessExecutor(
		FabricAgentRegistry fabricAgentRegistry,
		FabricAgentSelector fabricAgentSelector) {

		if (fabricAgentRegistry == null) {
			throw new NullPointerException("Fabric agent registry is null");
		}

		if (fabricAgentSelector == null) {
			throw new NullPointerException("Fabric agent selector is null");
		}

		_fabricAgentRegistry = fabricAgentRegistry;
		_fabricAgentSelector = fabricAgentSelector;
	}

	@Override
	public <T extends Serializable> FabricWorker<T> execute(
			ProcessConfig processConfig, ProcessCallable<T> processCallable)
		throws ProcessException {

		FabricAgent fabricAgent = getFabricAgent(processCallable);

		return fabricAgent.execute(processConfig, processCallable);
	}

	protected FabricAgent getFabricAgent(ProcessCallable<?> processCallable) {
		Collection<FabricAgent> fabricAgents = _fabricAgentSelector.select(
			_fabricAgentRegistry.getFabricAgents(), processCallable);

		if (fabricAgents.isEmpty()) {
			return _fabricAgentRegistry.getDefaultFabricAgent();
		}

		Iterator<FabricAgent> iterator = fabricAgents.iterator();

		return iterator.next();
	}

	private final FabricAgentRegistry _fabricAgentRegistry;
	private final FabricAgentSelector _fabricAgentSelector;

}