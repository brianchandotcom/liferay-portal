/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Adam Brandizzi
 */
public class SearchContextTestUtil {

	public static SearchContext getSearchContext() throws Exception {
		return getSearchContext(TestPropsValues.getGroupId());
	}

	public static SearchContext getSearchContext(long groupId)
		throws Exception {

		return getSearchContext(
			TestPropsValues.getUserId(), new long[] {groupId}, StringPool.BLANK,
			null, null);
	}

	public static SearchContext getSearchContext(
			long userId, long[] groupIds, String keywords, Locale locale)
		throws PortalException {

		return getSearchContext(userId, groupIds, keywords, locale, false);
	}

	public static SearchContext getSearchContext(
			long userId, long[] groupIds, String keywords, Locale locale,
			boolean highlightEnabled)
		throws PortalException {

		return getSearchContext(
			userId, groupIds, keywords, locale, highlightEnabled, null);
	}

	public static SearchContext getSearchContext(
			long userId, long[] groupIds, String keywords, Locale locale,
			boolean highlightEnabled, Map<String, Serializable> attributes)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(attributes);
		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setGroupIds(groupIds);
		searchContext.setKeywords(keywords);
		searchContext.setLocale(locale);
		searchContext.setUserId(userId);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(highlightEnabled);
		queryConfig.setSelectedFieldNames(StringPool.STAR);

		return searchContext;
	}

	public static SearchContext getSearchContext(
			long userId, long[] groupIds, String keywords, Locale locale,
			Map<String, Serializable> attributes)
		throws PortalException {

		return getSearchContext(
			userId, groupIds, keywords, locale, false, attributes);
	}

	public static SearchContext getSearchContext(
			long userId, String keywords, Locale locale)
		throws PortalException {

		return getSearchContext(userId, null, keywords, locale, null);
	}

}