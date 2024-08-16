/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.IOException;

import java.util.HashSet;

/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import java.util.Map;
import java.util.Set;

import org.opensearch.client.opensearch.indices.GetAliasRequest;
import org.opensearch.client.opensearch.indices.GetAliasResponse;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;
import org.opensearch.client.opensearch.indices.UpdateAliasesRequest;
import org.opensearch.client.opensearch.indices.get_alias.IndexAliases;
import org.opensearch.client.opensearch.indices.update_aliases.Action;

/**
 * @author Petteri Karttunen
 */
public class AliasesFactory {

	public AliasesFactory(
		CompanyIndexHelper companyIndexHelper,
		OpenSearchIndicesClient openSearchIndicesClient) {

		_companyIndexHelper = companyIndexHelper;
		_openSearchIndicesClient = openSearchIndicesClient;
	}

	public void updateConcurrentReindexingAliases(
			String baseIndexName, Company company,
			OpenSearchIndicesClient openSearchIndicesClient)
		throws Exception {

		_updateAliases(
			_createUpdateAliasesRequest(
				baseIndexName, company, openSearchIndicesClient));
	}

	private UpdateAliasesRequest _createUpdateAliasesRequest(
			String baseIndexName, Company company,
			OpenSearchIndicesClient openSearchIndicesClient)
		throws Exception {

		UpdateAliasesRequest.Builder builder =
			new UpdateAliasesRequest.Builder();

		Set<String> removeIndexNames = _getRemoveIndexNames(
			baseIndexName, openSearchIndicesClient);

		if (!removeIndexNames.isEmpty()) {
			if (_log.isInfoEnabled()) {
				_log.info("Removing indexes " + removeIndexNames);
			}

			builder.actions(
				Action.of(
					action -> action.removeIndex(
						removeIndexAction -> removeIndexAction.indices(
							ListUtil.fromCollection(removeIndexNames)))));
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Adding alias ", baseIndexName, " for index ",
					company.getIndexNameNext()));
		}

		builder.actions(
			Action.of(
				action -> action.add(
					addAction -> addAction.alias(
						baseIndexName
					).index(
						company.getIndexNameNext()
					))));

		return builder.build();
	}

	private Set<String> _getBaseIndexAliasIndexNames(
			String baseIndexName,
			OpenSearchIndicesClient openSearchIndicesClient)
		throws Exception {

		Set<String> baseIndexAliasIndexNames = new HashSet<>();

		GetAliasResponse getAliasResponse = openSearchIndicesClient.getAlias(
			GetAliasRequest.of(
				getAliasRequest -> getAliasRequest.index(baseIndexName)));

		Map<String, IndexAliases> aliases = getAliasResponse.result();

		if (MapUtil.isNotEmpty(aliases)) {
			baseIndexAliasIndexNames.addAll(aliases.keySet());
		}

		return baseIndexAliasIndexNames;
	}

	private Set<String> _getRemoveIndexNames(
			String baseIndexName,
			OpenSearchIndicesClient openSearchIndicesClient)
		throws Exception {

		Set<String> removeIndexNames = _getBaseIndexAliasIndexNames(
			baseIndexName, openSearchIndicesClient);

		if (removeIndexNames.isEmpty() &&
			_companyIndexHelper.hasIndex(
				baseIndexName, openSearchIndicesClient)) {

			removeIndexNames.add(baseIndexName);
		}

		return removeIndexNames;
	}

	private void _updateAliases(UpdateAliasesRequest updateAliasesRequest) {
		try {
			_openSearchIndicesClient.updateAliases(updateAliasesRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(AliasesFactory.class);

	private final CompanyIndexHelper _companyIndexHelper;
	private final OpenSearchIndicesClient _openSearchIndicesClient;

}