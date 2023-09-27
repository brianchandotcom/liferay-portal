/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.expando.helper;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;

import java.util.Collection;

/**
 * @author André de Oliveira
 */
public interface ExpandoQueryContributorHelper {

	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		Collection<String> classNames, SearchContext searchContext);

}