/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.cluster;

import com.liferay.portal.search.engine.adapter.cluster.ClusterHealthStatus;

import org.opensearch.client.opensearch._types.HealthStatus;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public interface ClusterHealthStatusTranslator {

	public HealthStatus translate(ClusterHealthStatus clusterHealthStatus);

	public ClusterHealthStatus translate(HealthStatus healthStatus);

	public ClusterHealthStatus translate(String status);

}