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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.cluster;

import com.liferay.portal.search.engine.adapter.cluster.ClusterHealthStatus;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = ClusterHealthStatusTranslator.class)
public class ClusterHealthStatusTranslatorImpl
	implements ClusterHealthStatusTranslator {

	@Override
	public org.elasticsearch.cluster.health.ClusterHealthStatus translate(
		ClusterHealthStatus clusterHealthStatus) {

		if (clusterHealthStatus == ClusterHealthStatus.GREEN) {
			return org.elasticsearch.cluster.health.ClusterHealthStatus.GREEN;
		}

		if (clusterHealthStatus == ClusterHealthStatus.RED) {
			return org.elasticsearch.cluster.health.ClusterHealthStatus.RED;
		}

		if (clusterHealthStatus == ClusterHealthStatus.YELLOW) {
			return org.elasticsearch.cluster.health.ClusterHealthStatus.YELLOW;
		}

		throw new IllegalArgumentException(
			"Unknown status: " + clusterHealthStatus);
	}

	@Override
	public ClusterHealthStatus translate(
		org.elasticsearch.cluster.health.ClusterHealthStatus
			clusterHealthStatus) {

		if (clusterHealthStatus ==
				org.elasticsearch.cluster.health.ClusterHealthStatus.GREEN) {

			return ClusterHealthStatus.GREEN;
		}

		if (clusterHealthStatus ==
				org.elasticsearch.cluster.health.ClusterHealthStatus.RED) {

			return ClusterHealthStatus.RED;
		}

		if (clusterHealthStatus ==
				org.elasticsearch.cluster.health.ClusterHealthStatus.YELLOW) {

			return ClusterHealthStatus.YELLOW;
		}

		throw new IllegalArgumentException(
			"Unknown status: " + clusterHealthStatus);
	}

}