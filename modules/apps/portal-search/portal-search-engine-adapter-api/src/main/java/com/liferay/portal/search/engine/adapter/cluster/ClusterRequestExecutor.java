/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.cluster;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Dylan Rebelak
 */
@ProviderType
public interface ClusterRequestExecutor {

	public <T extends ClusterResponse> T execute(
		ClusterRequest<T> clusterRequest);

	public HealthClusterResponse executeClusterRequest(
		HealthClusterRequest healthClusterRequest);

	public StateClusterResponse executeClusterRequest(
		StateClusterRequest stateClusterRequest);

	public StatsClusterResponse executeClusterRequest(
		StatsClusterRequest statsClusterRequest);

	public UpdateSettingsClusterResponse executeClusterRequest(
		UpdateSettingsClusterRequest updateSettingsClusterRequest);

}