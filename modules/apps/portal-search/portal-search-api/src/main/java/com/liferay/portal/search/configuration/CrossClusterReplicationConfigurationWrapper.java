/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.configuration;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration.CrossClusterReplicationConfigurationWrapper}
 */
@Deprecated
@ProviderType
public interface CrossClusterReplicationConfigurationWrapper {

	public String[] getCCRLocalClusterConnectionConfigurations();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getCCRLocalClusterConnectionConfigurations()}
	 */
	@Deprecated
	public String getCCRLocalClusterConnectionId();

	public String getRemoteClusterAlias();

	public boolean isCCREnabled();

}