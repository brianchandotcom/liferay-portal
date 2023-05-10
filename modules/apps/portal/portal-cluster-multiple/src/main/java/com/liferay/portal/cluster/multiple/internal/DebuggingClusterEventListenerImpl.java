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

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.cluster.multiple.configuration.ClusterExecutorConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.cluster.multiple.configuration.ClusterExecutorConfiguration",
	enabled = false, service = ClusterEventListener.class
)
public class DebuggingClusterEventListenerImpl implements ClusterEventListener {

	@Override
	public void processClusterEvent(ClusterEvent clusterEvent) {
		if (!_clusterExecutorConfiguration.debugEnabled() ||
			!_log.isInfoEnabled()) {

			return;
		}

		ClusterEventType clusterEventType = clusterEvent.getClusterEventType();

		List<ClusterNode> clusterNodes = clusterEvent.getClusterNodes();

		StringBundler sb = new StringBundler((clusterNodes.size() * 3) + 3);

		sb.append("Cluster event ");
		sb.append(clusterEventType);
		sb.append(StringPool.NEW_LINE);

		for (ClusterNode clusterNode : clusterNodes) {
			sb.append("Cluster node ");
			sb.append(clusterNode);
			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		_log.info(sb.toString());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_clusterExecutorConfiguration = ConfigurableUtil.createConfigurable(
			ClusterExecutorConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DebuggingClusterEventListenerImpl.class);

	private volatile ClusterExecutorConfiguration _clusterExecutorConfiguration;

}