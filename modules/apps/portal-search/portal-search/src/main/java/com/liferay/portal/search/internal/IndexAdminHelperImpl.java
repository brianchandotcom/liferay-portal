/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.IndexAdminHelper;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = IndexAdminHelper.class)
public class IndexAdminHelperImpl implements IndexAdminHelper {

	@Override
	public synchronized String backup(long companyId, String backupName)
		throws SearchException {

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		return searchEngine.backup(companyId, backupName);
	}

	@Override
	public synchronized void backup(String backupName) throws SearchException {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		for (Company company : _companyLocalService.getCompanies()) {
			searchEngine.backup(company.getCompanyId(), backupName);
		}
	}

	@Override
	public synchronized void removeBackup(long companyId, String backupName)
		throws SearchException {

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		searchEngine.removeBackup(companyId, backupName);
	}

	@Override
	public synchronized void removeBackup(String backupName)
		throws SearchException {

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		for (Company company : _companyLocalService.getCompanies()) {
			searchEngine.removeBackup(company.getCompanyId(), backupName);
		}
	}

	@Override
	public synchronized void restore(long companyId, String backupName)
		throws SearchException {

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		searchEngine.restore(companyId, backupName);
	}

	@Override
	public synchronized void restore(String backupName) throws SearchException {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		for (Company company : _companyLocalService.getCompanies()) {
			searchEngine.restore(company.getCompanyId(), backupName);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private SearchEngineHelper _searchEngineHelper;

}