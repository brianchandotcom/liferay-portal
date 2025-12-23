/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.cluster;

import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.elasticsearch.indices.PutIndicesSettingsRequest;
import co.elastic.clients.elasticsearch.indices.PutIndicesSettingsResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class ReplicasManagerImpl implements ReplicasManager {

	public ReplicasManagerImpl(
		ElasticsearchIndicesClient elasticsearchIndicesClient) {

		_elasticsearchIndicesClient = elasticsearchIndicesClient;
	}

	@Override
	public void updateNumberOfReplicas(
		int numberOfReplicas, String... indices) {

		try {
			PutIndicesSettingsResponse putIndicesSettingsResponse =
				_elasticsearchIndicesClient.putSettings(
					PutIndicesSettingsRequest.of(
						putIndicesSettingsRequest ->
							putIndicesSettingsRequest.index(
								ListUtil.fromArray(indices)
							).settings(
								IndexSettings.of(
									indexSettings ->
										indexSettings.numberOfReplicas(
											String.valueOf(numberOfReplicas)))
							)));

			JsonpUtil.logInfoResponse(putIndicesSettingsResponse, _log);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update number of replicas", exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReplicasManagerImpl.class);

	private final ElasticsearchIndicesClient _elasticsearchIndicesClient;

}