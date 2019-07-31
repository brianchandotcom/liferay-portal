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

package com.liferay.portal.search.elasticsearch6.internal.cluster;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.elasticsearch6.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch6.internal.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch6.settings.ClientSettingsHelper;
import com.liferay.portal.search.elasticsearch6.settings.SettingsContributor;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch6.configuration.ElasticsearchConfiguration",
	immediate = true, property = "operation.mode=EMBEDDED",
	service = SettingsContributor.class
)
public class UnicastSettingsContributor extends BaseSettingsContributor {

	public UnicastSettingsContributor() {
		super(1);
	}

	@Override
	public void populate(ClientSettingsHelper clientSettingsHelper) {
		if (!_clusterSettingsContext.isClusterEnabled()) {
			return;
		}

		clientSettingsHelper.put("discovery.type", "zen");
		clientSettingsHelper.putArray(
			"discovery.zen.ping.unicast.hosts", _getHosts());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);
	}

	protected void setClusterSettingsContext(
		ClusterSettingsContext clusterSettingsContext) {

		_clusterSettingsContext = clusterSettingsContext;
	}

	protected volatile ElasticsearchConfiguration elasticsearchConfiguration;

	private String[] _getHosts() {
		String[] hosts = _clusterSettingsContext.getHosts();

		String port =
			elasticsearchConfiguration.discoveryZenPingUnicastHostsPort();

		port = CharPool.COLON + port;

		for (int i = 0; i < hosts.length; i++) {
			hosts[i] = hosts[i] + port;
		}

		return hosts;
	}

	@Reference
	private ClusterSettingsContext _clusterSettingsContext;

}