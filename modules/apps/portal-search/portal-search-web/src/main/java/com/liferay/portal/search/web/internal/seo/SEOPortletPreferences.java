/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.seo;

/**
 * @author Amos Fong
 */
public interface SEOPortletPreferences {

	public static final String PREFERENCE_KEY_WEB_CRAWLER_INDEXING_ENABLED =
		"webCrawlerIndexingEnabled";

	public String getSEOParameterName();

	public boolean isWebCrawlerIndexingEnabled();

}