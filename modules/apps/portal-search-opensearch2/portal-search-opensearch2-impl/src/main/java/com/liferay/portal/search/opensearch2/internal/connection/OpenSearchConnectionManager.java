/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.connection;

import org.opensearch.client.json.JsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;

/**
 * @author Petteri Karttunen
 */
public interface OpenSearchConnectionManager {

	public void addOpenSearchConnection(
		OpenSearchConnection openSearchConnection);

	public JsonpMapper getJsonpMapper(String connectionId);

	public String getLocalClusterConnectionId();

	public OpenSearchClient getOpenSearchClient();

	public OpenSearchClient getOpenSearchClient(String connectionId);

	public OpenSearchClient getOpenSearchClient(
		String connectionId, boolean preferLocalCluster);

	public OpenSearchConnection getOpenSearchConnection();

	public OpenSearchConnection getOpenSearchConnection(
		boolean preferLocalCluster);

	public OpenSearchConnection getOpenSearchConnection(String connectionId);

	public boolean isCrossClusterReplicationEnabled();

	public void removeOpenSearchConnection(String connectionId);

}