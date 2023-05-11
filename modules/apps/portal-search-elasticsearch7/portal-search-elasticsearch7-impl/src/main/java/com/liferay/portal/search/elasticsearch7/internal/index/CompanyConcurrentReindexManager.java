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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.ccr.CrossClusterReplicationHelper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.index.ConcurrentReindexManager;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

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

		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnectionManager.getRestHighLevelClient();

		IndicesAliasesRequest indicesAliasesRequest =
			new IndicesAliasesRequest();

		IndicesAliasesRequest.AliasActions addAliasActions =
			IndicesAliasesRequest.AliasActions.add();

		String baseIndexName = _indexNameBuilder.getIndexName(companyId);

		addAliasActions.alias(baseIndexName);

		Company company = _companyLocalService.getCompany(companyId);

		String indexNameNext = company.getIndexNameNext();

		addAliasActions.index(indexNameNext);

		indicesAliasesRequest.addAliasAction(addAliasActions);

		String removeIndex = baseIndexName;

		if (!Validator.isBlank(company.getIndexNameCurrent())) {
			removeIndex = company.getIndexNameCurrent();
		}

		IndicesAliasesRequest.AliasActions removeIndexAliasActions =
			IndicesAliasesRequest.AliasActions.removeIndex();

		removeIndexAliasActions.index(removeIndex);

		indicesAliasesRequest.addAliasAction(removeIndexAliasActions);

		IndicesClient indicesClient = restHighLevelClient.indices();

		CrossClusterReplicationHelper crossClusterReplicationHelper =
			_crossClusterReplicationHelperSnapshot.get();

		if (crossClusterReplicationHelper != null) {
			crossClusterReplicationHelper.unfollow(removeIndex);
		}

		indicesClient.updateAliases(
			indicesAliasesRequest, RequestOptions.DEFAULT);

		_companyLocalService.updateIndexNames(companyId, indexNameNext, null);

		if (crossClusterReplicationHelper != null) {
			crossClusterReplicationHelper.follow(indexNameNext);
		}
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