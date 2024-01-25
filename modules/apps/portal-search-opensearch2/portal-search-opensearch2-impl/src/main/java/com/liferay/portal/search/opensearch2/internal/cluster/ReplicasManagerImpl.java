/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.cluster;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;

import org.opensearch.client.opensearch.indices.IndexSettings;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;
import org.opensearch.client.opensearch.indices.PutIndicesSettingsRequest;
import org.opensearch.client.opensearch.indices.PutIndicesSettingsResponse;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class ReplicasManagerImpl implements ReplicasManager {

	public ReplicasManagerImpl(
		OpenSearchIndicesClient openSearchIndicesClient) {

		_openSearchIndicesClient = openSearchIndicesClient;
	}

	@Override
	public void updateNumberOfReplicas(
		int numberOfReplicas, String... indices) {

		try {
			PutIndicesSettingsResponse putIndicesSettingsResponse =
				_openSearchIndicesClient.putSettings(
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

	private final OpenSearchIndicesClient _openSearchIndicesClient;

}