/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bryan Engler
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration.CrossClusterReplicationConfiguration",
	enabled = false, service = CrossClusterReplicationConfigurationWrapper.class
)
public class CrossClusterReplicationConfigurationWrapperImpl
	implements CrossClusterReplicationConfigurationWrapper {

	@Override
	public String[] getCCRLocalClusterConnectionConfigurations() {
		return crossClusterReplicationConfiguration.
			ccrLocalClusterConnectionConfigurations();
	}

	@Override
	public String getRemoteClusterAlias() {
		return crossClusterReplicationConfiguration.remoteClusterAlias();
	}

	@Override
	public boolean isCCREnabled() {
		return crossClusterReplicationConfiguration.ccrEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		crossClusterReplicationConfiguration =
			ConfigurableUtil.createConfigurable(
				CrossClusterReplicationConfiguration.class, properties);
	}

	protected volatile CrossClusterReplicationConfiguration
		crossClusterReplicationConfiguration;

}