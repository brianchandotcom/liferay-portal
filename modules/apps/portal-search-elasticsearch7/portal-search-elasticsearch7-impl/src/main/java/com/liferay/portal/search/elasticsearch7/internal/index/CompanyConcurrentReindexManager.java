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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.ccr.CrossClusterReplicationHelper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.index.ConcurrentReindexManager;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.AliasMetadata;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(service = ConcurrentReindexManager.class)
public class CompanyConcurrentReindexManager
	implements ConcurrentReindexManager {

	@Override
	public void createNextIndex(long companyId) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-177664") ||
			(companyId == CompanyConstants.SYSTEM)) {

			return;
		}

		String baseIndexName = _indexNameBuilder.getIndexName(companyId);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		String timeStampSuffix = dateFormat.format(new Date());

		String newIndexName = baseIndexName + "-" + timeStampSuffix;

		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnectionManager.getRestHighLevelClient();

		if (_companyIndexFactoryHelper.hasIndex(
				restHighLevelClient.indices(), newIndexName)) {

			return;
		}

		_companyIndexFactoryHelper.createIndex(
			newIndexName, restHighLevelClient.indices());

		_companyLocalService.updateIndexNameNext(companyId, newIndexName);
	}

	@Override
	public void deleteNextIndex(long companyId) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-177664")) {
			return;
		}

		Company company = _companyLocalService.fetchCompany(companyId);

		if (company == null) {
			return;
		}

		String indexName = company.getIndexNameNext();

		if (!Validator.isBlank(indexName)) {
			RestHighLevelClient restHighLevelClient =
				_elasticsearchConnectionManager.getRestHighLevelClient();

			_companyIndexFactoryHelper.deleteIndex(
				indexName, restHighLevelClient.indices(), companyId, false);
		}
	}

	@Override
	public void replaceCurrentIndexWithNextIndex(long companyId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-177664") ||
			(companyId == CompanyConstants.SYSTEM)) {

			return;
		}

		Company company = _companyLocalService.getCompany(companyId);

		String baseIndexName = _indexNameBuilder.getIndexName(companyId);

		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnectionManager.getRestHighLevelClient();

		IndicesClient indicesClient = restHighLevelClient.indices();

		CrossClusterReplicationHelper crossClusterReplicationHelper =
			_crossClusterReplicationHelperSnapshot.get();

		if (crossClusterReplicationHelper != null) {
			if (!Validator.isBlank(company.getIndexNameCurrent())) {
				crossClusterReplicationHelper.unfollow(
					company.getIndexNameCurrent());
			}
			else {
				crossClusterReplicationHelper.unfollow(baseIndexName);
			}
		}

		_updateAliases(baseIndexName, company, indicesClient);

		_companyLocalService.updateIndexNames(
			companyId, company.getIndexNameNext(), null);

		if (crossClusterReplicationHelper != null) {
			crossClusterReplicationHelper.follow(company.getIndexNameNext());
		}
	}

	private Set<String> _getBaseIndexAliasIndexNames(
			String baseIndexName, IndicesClient indicesClient)
		throws Exception {

		GetAliasesResponse getAliasesResponse = indicesClient.getAlias(
			new GetAliasesRequest(baseIndexName), RequestOptions.DEFAULT);

		Map<String, Set<AliasMetadata>> aliases =
			getAliasesResponse.getAliases();

		Set<String> baseIndexAliasIndexNames = new HashSet<>();

		if (MapUtil.isNotEmpty(aliases)) {
			baseIndexAliasIndexNames.addAll(aliases.keySet());
		}

		return baseIndexAliasIndexNames;
	}

	private Set<String> _getRemoveIndexNames(
			String baseIndexName, IndicesClient indicesClient)
		throws Exception {

		Set<String> removeIndexNames = _getBaseIndexAliasIndexNames(
			baseIndexName, indicesClient);

		if (removeIndexNames.isEmpty() &&
			_companyIndexFactoryHelper.hasIndex(indicesClient, baseIndexName)) {

			removeIndexNames.add(baseIndexName);
		}

		return removeIndexNames;
	}

	private void _updateAliases(
			String baseIndexName, Company company, IndicesClient indicesClient)
		throws Exception {

		IndicesAliasesRequest indicesAliasesRequest =
			new IndicesAliasesRequest();

		Set<String> removeIndexNames = _getRemoveIndexNames(
			baseIndexName, indicesClient);

		if (!removeIndexNames.isEmpty()) {
			indicesAliasesRequest.addAliasAction(
				new IndicesAliasesRequest.AliasActions(
					IndicesAliasesRequest.AliasActions.Type.REMOVE_INDEX
				).indices(
					ArrayUtil.toStringArray(removeIndexNames)
				));
		}

		indicesAliasesRequest.addAliasAction(
			new IndicesAliasesRequest.AliasActions(
				IndicesAliasesRequest.AliasActions.Type.ADD
			).alias(
				baseIndexName
			).index(
				company.getIndexNameNext()
			));

		indicesClient.updateAliases(
			indicesAliasesRequest, RequestOptions.DEFAULT);
	}

	private static final Snapshot<CrossClusterReplicationHelper>
		_crossClusterReplicationHelperSnapshot = new Snapshot(
			CompanyIndexFactory.class, CrossClusterReplicationHelper.class,
			null, true);

	@Reference
	private CompanyIndexFactoryHelper _companyIndexFactoryHelper;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

}