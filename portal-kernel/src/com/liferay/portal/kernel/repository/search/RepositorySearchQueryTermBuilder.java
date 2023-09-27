/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.search;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;

/**
 * @author Michael C. Han
 */
public interface RepositorySearchQueryTermBuilder {

	public void addTerm(
			BooleanQuery booleanQuery, SearchContext searchContext,
			String field, String value)
		throws ParseException;

}