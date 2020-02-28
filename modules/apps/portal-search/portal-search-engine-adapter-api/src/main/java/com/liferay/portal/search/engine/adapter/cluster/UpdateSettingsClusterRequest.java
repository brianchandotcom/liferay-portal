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

package com.liferay.portal.search.engine.adapter.cluster;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bryan Engler
 */
public class UpdateSettingsClusterRequest
	extends CrossClusterRequest
	implements ClusterRequest<UpdateSettingsClusterResponse> {

	@Override
	public UpdateSettingsClusterResponse accept(
		ClusterRequestExecutor clusterRequestExecutor) {

		return clusterRequestExecutor.executeClusterRequest(this);
	}

	@Override
	public String[] getIndexNames() {
		return new String[0];
	}

	public Map<String, String> getPersistentSettings() {
		return _persistentSettings;
	}

	public Map<String, String> getTransientSettings() {
		return _transientSettings;
	}

	public void setPersistentSettings(Map<String, String> persistentSettings) {
		_persistentSettings = persistentSettings;
	}

	public void setTransientSettings(Map<String, String> transientSettings) {
		_transientSettings = transientSettings;
	}

	private Map<String, String> _persistentSettings = new HashMap<>();
	private Map<String, String> _transientSettings = new HashMap<>();

}