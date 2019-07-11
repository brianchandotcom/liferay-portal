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

package com.liferay.portal.search.elasticsearch7.internal.cluster;

import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author André de Oliveira
 */
public class ReplicasClusterListener
	implements ClusterEventListener, ClusterMasterTokenTransitionListener {

	public ReplicasClusterListener(
		ReplicasClusterContext replicasClusterContext) {

		_replicasClusterContext = replicasClusterContext;
	}

	@Override
	public void masterTokenAcquired() {
		updateNumberOfReplicas();
	}

	@Override
	public void masterTokenReleased() {
	}

	@Override
	public void processClusterEvent(ClusterEvent clusterEvent) {
		if (_replicasClusterContext.isMaster()) {
			updateNumberOfReplicas();
		}
	}

	protected int getNumberOfReplicas() {
		int liferayClusterSize = _replicasClusterContext.getClusterSize();

		if (liferayClusterSize > 0) {
			return liferayClusterSize - 1;
		}

		return 0;
	}

	protected synchronized void updateNumberOfReplicas() {
		if (!_replicasClusterContext.isEmbeddedOperationMode()) {
			return;
		}

		try {
			ReplicasManager replicasManager =
				_replicasClusterContext.getReplicasManager();

			replicasManager.updateNumberOfReplicas(
				getNumberOfReplicas(),
				_replicasClusterContext.getTargetIndexNames());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update number of replicas", e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReplicasClusterListener.class);

	private final ReplicasClusterContext _replicasClusterContext;

}