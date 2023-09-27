/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.ccr;

/**
 * @author Bryan Engler
 */
public class CrossClusterRequest {

	public String getConnectionId() {
		return _connectionId;
	}

	public boolean isPreferLocalCluster() {
		return _preferLocalCluster;
	}

	public void setConnectionId(String connectionId) {
		_connectionId = connectionId;
	}

	public void setPreferLocalCluster(boolean preferLocalCluster) {
		_preferLocalCluster = preferLocalCluster;
	}

	private String _connectionId;
	private boolean _preferLocalCluster;

}