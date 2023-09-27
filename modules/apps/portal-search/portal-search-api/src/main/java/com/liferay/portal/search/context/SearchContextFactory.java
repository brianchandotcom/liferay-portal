/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.context;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.SearchContext;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ProviderType
public interface SearchContextFactory {

	public SearchContext getSearchContext(
		long[] assetCategoryIds, String[] assetTagNames, long companyId,
		String keywords, Layout layout, Locale locale,
		Map<String, String[]> parameters, long scopeGroupId, TimeZone timeZone,
		long userId);

}