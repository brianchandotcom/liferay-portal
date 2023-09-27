/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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