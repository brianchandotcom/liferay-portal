/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.GetAliasRequest;
import co.elastic.clients.elasticsearch.indices.GetAliasResponse;
import co.elastic.clients.elasticsearch.indices.UpdateAliasesRequest;
import co.elastic.clients.elasticsearch.indices.get_alias.IndexAliases;
import co.elastic.clients.elasticsearch.indices.update_aliases.Action;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.ccr.CrossClusterReplicationHelper;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.index.ConcurrentReindexManager;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author João Victor Alves
 */
@Component(service = ConcurrentReindexManager.class)
public class CompanyConcurrentReindexManager
	implements ConcurrentReindexManager {

	@Override
	public void createNextIndex(long companyId) throws Exception {
		if (companyId == CompanyConstants.SYSTEM) {
			return;
		}

		String baseIndexName = _indexNameBuilder.getIndexName(companyId);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		String timeStampSuffix = dateFormat.format(new Date());

		String newIndexName = baseIndexName + "-" + timeStampSuffix;

		ElasticsearchClient elasticsearchClient =
			_elasticsearchConnectionManager.getElasticsearchClient();

		if (_companyIndexHelper.hasIndex(
				elasticsearchClient.indices(), newIndexName)) {

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Creating next index " + newIndexName);
		}

		_companyIndexHelper.createIndex(
			companyId, elasticsearchClient.indices(), newIndexName);

		_companyLocalService.updateIndexNameNext(companyId, newIndexName);
	}

	@Override
	public void deleteNextIndex(long companyId) {
		Company company = _companyLocalService.fetchCompany(companyId);

		if (company == null) {
			return;
		}

		String indexName = company.getIndexNameNext();

		if (!Validator.isBlank(indexName)) {
			ElasticsearchClient elasticsearchClient =
				_elasticsearchConnectionManager.getElasticsearchClient();

			if (_log.isInfoEnabled()) {
				_log.info("Deleting next index " + indexName);
			}

			_companyIndexHelper.deleteIndex(
				companyId, elasticsearchClient.indices(), indexName, false);
		}
	}

	@Override
	public void replaceCurrentIndexWithNextIndex(long companyId)
		throws Exception {

		if (companyId == CompanyConstants.SYSTEM) {
			return;
		}

		String baseIndexName = _indexNameBuilder.getIndexName(companyId);
		Company company = _companyLocalService.getCompany(companyId);

		ElasticsearchClient elasticsearchClient =
			_elasticsearchConnectionManager.getElasticsearchClient();

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

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

		_updateAliases(baseIndexName, company, elasticsearchIndicesClient);

		_companyLocalService.updateIndexNames(
			companyId, company.getIndexNameNext(), null);

		if (crossClusterReplicationHelper != null) {
			crossClusterReplicationHelper.follow(company.getIndexNameNext());
		}
	}

	private Set<String> _getBaseIndexAliasIndexNames(
			String baseIndexName,
			ElasticsearchIndicesClient elasticsearchIndicesClient)
		throws Exception {

		Set<String> baseIndexAliasIndexNames = new HashSet<>();

		GetAliasResponse getAliasResponse = elasticsearchIndicesClient.getAlias(
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
			ElasticsearchIndicesClient elasticsearchIndicesClient)
		throws Exception {

		Set<String> removeIndexNames = _getBaseIndexAliasIndexNames(
			baseIndexName, elasticsearchIndicesClient);

		if (removeIndexNames.isEmpty() &&
			_companyIndexHelper.hasIndex(
				elasticsearchIndicesClient, baseIndexName)) {

			removeIndexNames.add(baseIndexName);
		}

		return removeIndexNames;
	}

	private void _updateAliases(
			String baseIndexName, Company company,
			ElasticsearchIndicesClient elasticsearchIndicesClient)
		throws Exception {

		UpdateAliasesRequest.Builder builder =
			new UpdateAliasesRequest.Builder();

		Set<String> removeIndexNames = _getRemoveIndexNames(
			baseIndexName, elasticsearchIndicesClient);

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

		elasticsearchIndicesClient.updateAliases(builder.build());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyConcurrentReindexManager.class);

	private static final Snapshot<CrossClusterReplicationHelper>
		_crossClusterReplicationHelperSnapshot = new Snapshot(
			CompanyConcurrentReindexManager.class,
			CrossClusterReplicationHelper.class, null, true);

	@Reference
	private CompanyIndexHelper _companyIndexHelper;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

}