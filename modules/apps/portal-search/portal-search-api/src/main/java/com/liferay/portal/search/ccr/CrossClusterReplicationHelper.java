/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.ccr;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ProviderType
public interface CrossClusterReplicationHelper {

	public void addRemoteCluster(
		String remoteClusterAlias, String remoteClusterSeedNodeTransportAddress,
		String localClusterConnectionId);

	public void deleteRemoteCluster(
		String remoteClusterAlias, String localClusterConnectionId);

	public void follow(String indexName);

	public void follow(
		String remoteClusterAlias, String indexName,
		String localClusterConnectionId);

	public void unfollow(String indexName);

	public void unfollow(String indexName, String localClusterConnectionId);

}