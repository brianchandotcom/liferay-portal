/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Michael C. Han
 */
public class SearchEngineHelperUtil {

	public static String[] getEntryClassNames() {
		return _searchEngineHelper.getEntryClassNames();
	}

	public static SearchEngine getSearchEngine() {
		return _searchEngineHelper.getSearchEngine();
	}

	public static SearchEngineHelper getSearchEngineHelper() {
		return _searchEngineHelper;
	}

	public static void initialize(long companyId) {
		_searchEngineHelper.initialize(companyId);
	}

	public static void removeCompany(long companyId) {
		_searchEngineHelper.removeCompany(companyId);
	}

	private static volatile SearchEngineHelper _searchEngineHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			SearchEngineHelper.class, SearchEngineHelperUtil.class,
			"_searchEngineHelper", false);

}