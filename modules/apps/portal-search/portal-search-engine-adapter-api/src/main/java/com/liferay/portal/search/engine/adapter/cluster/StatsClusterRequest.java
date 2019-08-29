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

/**
 * @author Dylan Rebelak
 */
public class StatsClusterRequest
	implements ClusterRequest<StatsClusterResponse> {

	public StatsClusterRequest(String[] nodeIds) {
		_nodeIds = nodeIds;
	}

	@Override
	public StatsClusterResponse accept(
		ClusterRequestExecutor clusterRequestExecutor) {

		return clusterRequestExecutor.executeClusterRequest(this);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getNodeIds()}
	 */
	@Deprecated
	@Override
	public String[] getIndexNames() {
		return _nodeIds;
	}

	public String[] getNodeIds() {
		return _nodeIds;
	}

	private final String[] _nodeIds;

}