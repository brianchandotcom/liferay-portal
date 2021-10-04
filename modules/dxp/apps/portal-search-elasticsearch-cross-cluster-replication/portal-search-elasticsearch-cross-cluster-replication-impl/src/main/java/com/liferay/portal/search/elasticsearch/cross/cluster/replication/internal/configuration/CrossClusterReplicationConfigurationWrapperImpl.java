/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	enabled = false, immediate = true,
	service = CrossClusterReplicationConfigurationWrapper.class
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