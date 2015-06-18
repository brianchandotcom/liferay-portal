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

package com.liferay.portal.search.elasticsearch.internal.connection.embedded;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.internal.cluster.ClusterSettingsContext;
import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import java.net.InetAddress;

import java.util.Map;

import org.elasticsearch.common.settings.ImmutableSettings;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, property = {"operation.mode=EMBEDDED"},
	service = SettingsContributor.class
)
public class NetworkSettingsContributor extends BaseSettingsContributor {

	public NetworkSettingsContributor() {
		super(1);
	}

	@Override
	public void populate(ImmutableSettings.Builder builder) {
		String networkBindHost = _elasticsearchConfiguration.networkBindHost();

		if (Validator.isNotNull(networkBindHost)) {
			builder.put("network.bind.host", networkBindHost);
		}

		String networkPublishHost =
			_elasticsearchConfiguration.networkPublishHost();

		if (Validator.isNotNull(networkPublishHost)) {
			builder.put("network.publish.host", networkPublishHost);
		}

		String networkHost = _elasticsearchConfiguration.networkHost();

		if (Validator.isNull(networkBindHost) &&
			Validator.isNull(networkHost) &&
			Validator.isNull(networkPublishHost)) {

			InetAddress localBindInetAddress =
				_clusterSettingsContext.getLocalBindInetAddress();

			networkHost = localBindInetAddress.getHostAddress();
		}

		if (Validator.isNotNull(networkHost)) {
			builder.put("network.host", networkHost);
		}

		String transportTcpPort =
			_elasticsearchConfiguration.transportTcpPort();

		if (Validator.isNotNull(transportTcpPort)) {
			builder.put("transport.tcp.port", transportTcpPort);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);
	}

	@Reference(unbind = "-")
	protected void setClusterSettingsContext(
		ClusterSettingsContext clusterSettingsContext) {

		_clusterSettingsContext = clusterSettingsContext;
	}

	private ClusterSettingsContext _clusterSettingsContext;
	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;

}