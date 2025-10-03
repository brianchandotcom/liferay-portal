/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.connection;

import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author André de Oliveira
 */
public interface ElasticsearchClientResolver {

	public RestHighLevelClient getRestHighLevelClient();

	public RestHighLevelClient getRestHighLevelClient(String connectionId);

	public RestHighLevelClient getRestHighLevelClient(
		String connectionId, boolean preferLocalCluster);

}