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

package com.liferay.portal.search.elasticsearch7.internal.cluster;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch7.internal.util.LogUtil;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequestBuilder;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;

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

		UpdateSettingsRequestBuilder updateSettingsRequestBuilder =
			_indicesAdminClient.prepareUpdateSettings(indices);

		Settings.Builder builder = Settings.builder();

		builder.put("number_of_replicas", numberOfReplicas);

		updateSettingsRequestBuilder.setSettings(builder);

		try {
			ActionResponse actionResponse = updateSettingsRequestBuilder.get();

			LogUtil.logActionResponse(_log, actionResponse);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update number of replicas", e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReplicasManagerImpl.class);

	private final IndicesAdminClient _indicesAdminClient;

}