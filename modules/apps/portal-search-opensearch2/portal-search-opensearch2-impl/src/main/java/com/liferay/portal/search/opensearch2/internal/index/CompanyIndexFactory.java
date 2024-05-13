/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationObserver;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapper;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.index.util.IndexFactoryCompanyIdRegistryUtil;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
@Component(service = IndexFactory.class)
public class CompanyIndexFactory
	implements IndexFactory, OpenSearchConfigurationObserver {

	@Override
	public int compareTo(
		OpenSearchConfigurationObserver openSearchConfigurationObserver) {

		return _openSearchConfigurationWrapper.compare(
			this, openSearchConfigurationObserver);
	}

	@Override
	public boolean createIndices(
		long companyId, OpenSearchIndicesClient openSearchIndicesClient) {

		String indexName = _indexHelper.getIndexName(companyId);

		if (_indexHelper.hasIndex(indexName, openSearchIndicesClient)) {
			return false;
		}

		_indexHelper.createIndex(indexName, openSearchIndicesClient);

		return true;
	}

	@Override
	public boolean deleteIndices(
		long companyId, OpenSearchIndicesClient openSearchIndicesClient) {

		Company company = _companyLocalService.fetchCompany(companyId);

		String indexName = _indexHelper.getIndexName(companyId);

		if ((company != null) &&
			!Validator.isBlank(company.getIndexNameCurrent())) {

			indexName = company.getIndexNameCurrent();
		}

		if (!_indexHelper.hasIndex(indexName, openSearchIndicesClient)) {
			return false;
		}

		_indexHelper.deleteIndex(
			companyId, indexName, openSearchIndicesClient, true);

		return true;
	}

	@Override
	public int getPriority() {
		return 3;
	}

	@Override
	public void onOpenSearchConfigurationUpdate() {
		_createCompanyIndexes();

		_indexHelper.updateMaxResultWindow();
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
		_openSearchConfigurationWrapper.register(this);

		_createCompanyIndexes();
	}

	@Deactivate
	protected void deactivate() {
		_openSearchConfigurationWrapper.unregister(this);
	}

	private void _createCompanyIndex(long companyId) {
		try {
			OpenSearchClient openSearchClient =
				_openSearchConnectionManager.getOpenSearchClient();

			createIndices(companyId, openSearchClient.indices());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to reinitialize index for company " + companyId,
					exception);
			}
		}
	}

	private synchronized void _createCompanyIndexes() {
		_companyLocalService.forEachCompanyId(
			companyId -> _createCompanyIndex(companyId),
			IndexFactoryCompanyIdRegistryUtil.getCompanyIds());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyIndexFactory.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private IndexHelper _indexHelper;

	@Reference
	private OpenSearchConfigurationWrapper _openSearchConfigurationWrapper;

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

}