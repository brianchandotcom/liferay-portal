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

package com.liferay.portal.search.elasticsearch.internal.cluster;

import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterNode;

import java.net.InetAddress;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = ClusterSettingsContext.class)
public class ClusterExecutorClusterSettingsContext
	implements ClusterSettingsContext {

	@Override
	public String[] getHosts() {
		List<ClusterNode> clusterNodes = _clusterExecutor.getClusterNodes();

		String[] addresses = new String[clusterNodes.size()];

		for (int i = 0; i < addresses.length; i++) {
			ClusterNode clusterNode = clusterNodes.get(i);

			InetAddress bindInetAddress = clusterNode.getBindInetAddress();

			addresses[i] = bindInetAddress.getHostAddress() + _PORT_RANGES;
		}

		return addresses;
	}

	@Override
	public boolean isClusterEnabled() {
		return _clusterExecutor.isEnabled();
	}

	@Reference(unbind = "-")
	protected void setClusterExecutor(ClusterExecutor clusterExecutor) {
		_clusterExecutor = clusterExecutor;
	}

	private static final String _PORT_RANGES = "[9300-9350]";

	private ClusterExecutor _clusterExecutor;

}