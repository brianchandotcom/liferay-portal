/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationObserver;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.index.util.IndexFactoryCompanyIdRegistryUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.spi.model.index.contributor.IndexContributor;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = IndexFactory.class)
public class CompanyIndexFactory
	implements ElasticsearchConfigurationObserver, IndexFactory {

	@Override
	public int compareTo(
		ElasticsearchConfigurationObserver elasticsearchConfigurationObserver) {

		return _elasticsearchConfigurationWrapper.compare(
			this, elasticsearchConfigurationObserver);
	}

	@Override
	public void createIndices(IndicesClient indicesClient, long companyId) {
		String indexName = _companyIndexFactoryHelper.getIndexName(companyId);

		if (_companyIndexFactoryHelper.hasIndex(indicesClient, indexName)) {
			return;
		}

		_companyIndexFactoryHelper.createIndex(indexName, indicesClient);
	}

	@Override
	public void deleteIndices(IndicesClient indicesClient, long companyId) {
		String indexName = _companyIndexFactoryHelper.getIndexName(companyId);

		if (FeatureFlagManagerUtil.isEnabled("LPS-183661")) {
			Company company = _companyLocalService.fetchCompany(companyId);

			if ((company != null) &&
				!Validator.isBlank(company.getIndexNameCurrent())) {

				indexName = company.getIndexNameCurrent();
			}
		}

		if (!_companyIndexFactoryHelper.hasIndex(indicesClient, indexName)) {
			return;
		}

		_executeIndexContributorsBeforeRemove(indexName);

		_companyIndexFactoryHelper.deleteIndex(
			indexName, indicesClient, companyId, true);
	}

	@Override
	public int getPriority() {
		return 3;
	}

	@Override
	public void onElasticsearchConfigurationUpdate() {
		_createCompanyIndexes();

		_updateMaxResultWindow();
	}

	@Override
	public synchronized void registerCompanyId(long companyId) {
		IndexFactoryCompanyIdRegistryUtil.registerCompanyId(companyId);
	}

	@Override
	public synchronized void unregisterCompanyId(long companyId) {
		IndexFactoryCompanyIdRegistryUtil.unregisterCompanyId(companyId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_elasticsearchConfigurationWrapper.register(this);

		_createCompanyIndexes();
	}

	@Deactivate
	protected void deactivate() {
		_elasticsearchConfigurationWrapper.unregister(this);
	}

	private synchronized void _createCompanyIndexes() {
		for (Long companyId :
				IndexFactoryCompanyIdRegistryUtil.getCompanyIds()) {

			try {
				RestHighLevelClient restHighLevelClient =
					_elasticsearchConnectionManager.getRestHighLevelClient();

				createIndices(restHighLevelClient.indices(), companyId);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to reinitialize index for company " + companyId,
						exception);
				}
			}
		}
	}

	private void _executeIndexContributorBeforeRemove(
		IndexContributor indexContributor, String indexName) {

		try {
			indexContributor.onBeforeRemove(indexName);
		}
		catch (Throwable throwable) {
			_log.error(
				StringBundler.concat(
					"Unable to apply contributor ", indexContributor,
					" when removing index ", indexName),
				throwable);
		}
	}

	private void _executeIndexContributorsBeforeRemove(String indexName) {
		for (IndexContributor indexContributor :
				_companyIndexFactoryHelper.getIndexContributors()) {

			_executeIndexContributorBeforeRemove(indexContributor, indexName);
		}
	}

	private void _updateMaxResultWindow() {
		int maxResultWindow =
			_elasticsearchConfigurationWrapper.indexMaxResultWindow();

		for (Long companyId :
				IndexFactoryCompanyIdRegistryUtil.getCompanyIds()) {

			String indexName = _indexNameBuilder.getIndexName(companyId);

			UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest =
				new UpdateIndexSettingsIndexRequest(indexName);

			updateIndexSettingsIndexRequest.setSettings(
				"{\"index.max_result_window\": " + maxResultWindow + "}");

			_searchEngineAdapter.execute(updateIndexSettingsIndexRequest);

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Updated index.max_result_window to ", maxResultWindow,
						" for index ", indexName));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyIndexFactory.class);

	@Reference
	private CompanyIndexFactoryHelper _companyIndexFactoryHelper;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private volatile ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;

	@Reference
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}