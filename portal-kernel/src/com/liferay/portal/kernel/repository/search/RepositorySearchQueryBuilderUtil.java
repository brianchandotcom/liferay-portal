/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.search;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Mika Koivisto
 */
public class RepositorySearchQueryBuilderUtil {

	public static BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException {

		return _repositorySearchQueryBuilder.getFullQuery(searchContext);
	}

	public static RepositorySearchQueryBuilder
		getRepositorySearchQueryBuilder() {

		return _repositorySearchQueryBuilder;
	}

	private static volatile RepositorySearchQueryBuilder
		_repositorySearchQueryBuilder =
			ServiceProxyFactory.newServiceTrackedInstance(
				RepositorySearchQueryBuilder.class,
				RepositorySearchQueryBuilderUtil.class,
				"_repositorySearchQueryBuilder", null, false, true);

}