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

import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import org.elasticsearch.common.settings.ImmutableSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = SettingsContributor.class)
public class UnicastSettingsContributor implements SettingsContributor {

	@Override
	public void populate(ImmutableSettings.Builder builder) {
		if (!_clusterSettingsContext.isClusterEnabled()) {
			return;
		}

		builder.putArray(
			"discovery.zen.ping.unicast.hosts",
			_clusterSettingsContext.getHosts());

		builder.put("node.local", false);
	}

	@Reference(unbind = "-")
	public void setClusterSettingsContext(
		ClusterSettingsContext clusterSettingsContext) {

		_clusterSettingsContext = clusterSettingsContext;
	}

	private ClusterSettingsContext _clusterSettingsContext;

}