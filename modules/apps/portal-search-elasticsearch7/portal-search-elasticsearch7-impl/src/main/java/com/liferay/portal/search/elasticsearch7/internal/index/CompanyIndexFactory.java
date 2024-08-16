/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationObserver;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.index.util.IndexFactoryCompanyIdRegistryUtil;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestHighLevelClient;

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
	public boolean deleteIndex(long companyId, IndicesClient indicesClient) {
		String indexName = _companyIndexHelper.getIndexName(companyId);

		Company company = _companyLocalService.fetchCompany(companyId);

		if ((company != null) &&
			!Validator.isBlank(company.getIndexNameCurrent())) {

			indexName = company.getIndexNameCurrent();
		}

		if (!_companyIndexHelper.hasIndex(indexName, indicesClient)) {
			return false;
		}

		_companyIndexHelper.deleteIndex(
			companyId, indexName, indicesClient, true);

		return true;
	}

	@Override
	public int getPriority() {
		return 3;
	}

	@Override
	public boolean initializeIndex(
		long companyId, IndicesClient indicesClient) {

		String indexName = _companyIndexHelper.getIndexName(companyId);

		if (_companyIndexHelper.hasIndex(indexName, indicesClient)) {
			if ((companyId != CompanyConstants.SYSTEM) &&
				FeatureFlagManagerUtil.isEnabled(companyId, "LPD-7822")) {

				_companyIndexHelper.updateIndex(
					companyId, indexName, indicesClient);
			}

			return false;
		}

		_companyIndexHelper.createIndex(companyId, indexName, indicesClient);

		return true;
	}

	@Override
	public void onElasticsearchConfigurationUpdate() {
		_initializeCompanyIndexes();
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
	protected void activate() {
		_elasticsearchConfigurationWrapper.register(this);

		_initializeCompanyIndexes();
	}

	@Deactivate
	protected void deactivate() {
		_elasticsearchConfigurationWrapper.unregister(this);
	}

	private synchronized void _initializeCompanyIndexes() {
		_companyLocalService.forEachCompanyId(
			companyId -> _initializeIndex(companyId),
			IndexFactoryCompanyIdRegistryUtil.getCompanyIds());
	}

	private void _initializeIndex(long companyId) {
		try {
			RestHighLevelClient restHighLevelClient =
				_elasticsearchConnectionManager.getRestHighLevelClient();

			initializeIndex(companyId, restHighLevelClient.indices());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to initialize index for company " + companyId,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyIndexFactory.class);

	@Reference
	private CompanyIndexHelper _companyIndexHelper;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;

	@Reference
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;

}