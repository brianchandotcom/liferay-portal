/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.cluster;

/**
 * @author Dylan Rebelak
 */
public class StateClusterResponse implements ClusterResponse {

	public StateClusterResponse(String stateMessage) {
		_stateMessage = stateMessage;
	}

	public String getStateMessage() {
		return _stateMessage;
	}

	private final String _stateMessage;

}