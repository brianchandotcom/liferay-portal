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

import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequestBuilder;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;

/**
 * @author André de Oliveira
 */
public class ReplicasManagerImpl implements ReplicasManager {

	public ReplicasManagerImpl(IndicesAdminClient indicesAdminClient) {
		_indicesAdminClient = indicesAdminClient;
	}

	@Override
	public void updateNumberOfReplicas(
		int numberOfReplicas, String... indices) {

		Builder builder = ImmutableSettings.settingsBuilder();

		builder.put("number_of_replicas", numberOfReplicas);

		UpdateSettingsRequestBuilder updateSettingsRequestBuilder =
			_indicesAdminClient.prepareUpdateSettings(indices);

		updateSettingsRequestBuilder.setSettings(builder);

		updateSettingsRequestBuilder.get();
	}

	private final IndicesAdminClient _indicesAdminClient;

}