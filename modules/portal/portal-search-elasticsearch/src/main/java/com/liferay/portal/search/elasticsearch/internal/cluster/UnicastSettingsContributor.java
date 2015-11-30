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

package com.liferay.portal.search.elasticsearch.internal.cluster;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import java.util.Map;

import org.elasticsearch.common.settings.ImmutableSettings;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, property = {"operation.mode=EMBEDDED"},
	service = SettingsContributor.class
)
public class UnicastSettingsContributor extends BaseSettingsContributor {

	public UnicastSettingsContributor() {
		super(1);
	}

	@Override
	public void populate(ImmutableSettings.Builder builder) {
		if (!_clusterSettingsContext.isClusterEnabled()) {
			return;
		}

		builder.putArray("discovery.zen.ping.unicast.hosts", _getHosts());

		builder.put("node.local", false);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);
	}

	@Reference(unbind = "-")
	protected void setClusterSettingsContext(
		ClusterSettingsContext clusterSettingsContext) {

		_clusterSettingsContext = clusterSettingsContext;
	}

	protected volatile ElasticsearchConfiguration elasticsearchConfiguration;

	private String[] _getHosts() {
		String[] hosts = _clusterSettingsContext.getHosts();

		String port =
			elasticsearchConfiguration.discoveryZenPingUnicastHostsPort();

		int pos = port.indexOf(CharPool.MINUS);

		if (pos == -1) {
			port = CharPool.COLON + port;
		}
		else {
			port = CharPool.OPEN_BRACKET + port + CharPool.CLOSE_BRACKET;
		}

		for (int i = 0; i < hosts.length; i++) {
			hosts[i] = hosts[i] + port;
		}

		return hosts;
	}

	private volatile ClusterSettingsContext _clusterSettingsContext;

}