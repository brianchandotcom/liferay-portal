/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.cluster;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch7.internal.helper.SearchLogHelperUtil;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.settings.Settings;

/**
 * @author André de Oliveira
 */
public class ReplicasManagerImpl implements ReplicasManager {

	public ReplicasManagerImpl(IndicesClient indicesClient) {
		_indicesClient = indicesClient;
	}

	@Override
	public void updateNumberOfReplicas(
		int numberOfReplicas, String... indices) {

		UpdateSettingsRequest updateSettingsRequest = new UpdateSettingsRequest(
			indices);

		Settings.Builder builder = Settings.builder();

		builder.put("number_of_replicas", numberOfReplicas);

		updateSettingsRequest.settings(builder);

		try {
			ActionResponse actionResponse = _indicesClient.putSettings(
				updateSettingsRequest, RequestOptions.DEFAULT);

			SearchLogHelperUtil.logActionResponse(_log, actionResponse);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update number of replicas", exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReplicasManagerImpl.class);

	private final IndicesClient _indicesClient;

}