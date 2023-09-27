/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration;

/**
 * @author Bryan Engler
 */
public interface CrossClusterReplicationConfigurationWrapper {

	public String[] getCCRLocalClusterConnectionConfigurations();

	public String getRemoteClusterAlias();

	public boolean isCCREnabled();

}