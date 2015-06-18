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

package com.liferay.portal.search.elasticsearch.internal.connection.transport;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import java.util.Map;

import org.elasticsearch.common.settings.ImmutableSettings;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"operation.mode=REMOTE"},
	service = SettingsContributor.class
)
public class ClientTransportSettingsContributor
	extends BaseSettingsContributor {

	public ClientTransportSettingsContributor() {
		super(1);
	}

	@Override
	public void populate(ImmutableSettings.Builder builder) {
		builder.put(
			"client.transport.ignore_cluster_name",
			_elasticsearchConfiguration.clientTransportIgnoreClusterName());
		builder.put(
			"client.transport.nodes_sampler_interval",
			_elasticsearchConfiguration.clientTransportNodesSamplerInterval());
		builder.put(
			"client.transport.sniff",
			_elasticsearchConfiguration.clientTransportSniff());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);
	}

	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;

}