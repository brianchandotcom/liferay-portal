/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.concurrent.ExecutorService;

/**
 * @author Michael C. Han
 */
public class SearchEngineHelperUtil {

	public static ExecutorService getDocumentsConsumerExecutorService() {
		SearchEngineHelper searchEngineHelper =
			_searchEngineHelperSnapshot.get();

		return searchEngineHelper.getDocumentsConsumerExecutorService();
	}

	public static ExecutorService getDocumentsProducerExecutorService() {
		SearchEngineHelper searchEngineHelper =
			_searchEngineHelperSnapshot.get();

		return searchEngineHelper.getDocumentsProducerExecutorService();
	}

	public static String[] getEntryClassNames() {
		SearchEngineHelper searchEngineHelper =
			_searchEngineHelperSnapshot.get();

		return searchEngineHelper.getEntryClassNames();
	}

	public static SearchEngine getSearchEngine() {
		SearchEngineHelper searchEngineHelper =
			_searchEngineHelperSnapshot.get();

		return searchEngineHelper.getSearchEngine();
	}

	public static SearchEngineHelper getSearchEngineHelper() {
		return _searchEngineHelperSnapshot.get();
	}

	public static void initialize(long companyId) {
		SearchEngineHelper searchEngineHelper =
			_searchEngineHelperSnapshot.get();

		searchEngineHelper.initialize(companyId);
	}

	public static void removeCompany(long companyId) {
		SearchEngineHelper searchEngineHelper =
			_searchEngineHelperSnapshot.get();

		searchEngineHelper.removeCompany(companyId);
	}

	private static final Snapshot<SearchEngineHelper>
		_searchEngineHelperSnapshot = new Snapshot<>(
			SearchEngineHelperUtil.class, SearchEngineHelper.class);

}