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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Shuyang Zhou
 */
public class FabricAgentRegistry {

	public FabricAgentRegistry(FabricAgent defaultFabricAgent) {
		if (defaultFabricAgent == null) {
			throw new NullPointerException("Default fabric agent is null");
		}

		_defaultFabricAgent = defaultFabricAgent;
	}

	public FabricAgent getDefaultFabricAgent() {
		return _defaultFabricAgent;
	}

	public List<FabricAgentListener> getFabricAgentListeners() {
		return new ArrayList<>(_fabricAgentListeners);
	}

	public List<FabricAgent> getFabricAgents() {
		return new ArrayList<>(_fabricAgents);
	}

	public boolean registerFabricAgent(
		FabricAgent fabricAgent, Runnable runnable) {

		if (_fabricAgents.addIfAbsent(fabricAgent)) {
			if (runnable != null) {
				runnable.run();
			}

			for (FabricAgentListener fabricAgentListener :
					_fabricAgentListeners) {

				fabricAgentListener.registered(fabricAgent);
			}

			return true;
		}

		return false;
	}

	public boolean registerFabricAgentListener(
		FabricAgentListener fabricAgentListener) {

		return _fabricAgentListeners.addIfAbsent(fabricAgentListener);
	}

	public boolean unregisterFabricAgent(
		FabricAgent fabricAgent, Runnable runnable) {

		if (_fabricAgents.remove(fabricAgent)) {
			if (runnable != null) {
				runnable.run();
			}

			for (FabricAgentListener fabricAgentListener :
					_fabricAgentListeners) {

				fabricAgentListener.unregistered(fabricAgent);
			}

			return true;
		}

		return false;
	}

	public boolean unregisterFabricAgentListener(
		FabricAgentListener fabricAgentListener) {

		return _fabricAgentListeners.remove(fabricAgentListener);
	}

	private final FabricAgent _defaultFabricAgent;
	private final CopyOnWriteArrayList<FabricAgentListener>
		_fabricAgentListeners = new CopyOnWriteArrayList<>();
	private final CopyOnWriteArrayList<FabricAgent> _fabricAgents =
		new CopyOnWriteArrayList<>();

}