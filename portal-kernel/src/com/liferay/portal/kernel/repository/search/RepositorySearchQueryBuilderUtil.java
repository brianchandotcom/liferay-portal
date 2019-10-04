/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.repository.search;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Mika Koivisto
 */
public class RepositorySearchQueryBuilderUtil {

	public static BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException {

		return getRepositorySearchQueryBuilder().getFullQuery(searchContext);
	}

	public static RepositorySearchQueryBuilder
		getRepositorySearchQueryBuilder() {

		return _repositorySearchQueryBuilderUtil._serviceTracker.getService();
	}

	public RepositorySearchQueryBuilderUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			RepositorySearchQueryBuilder.class);

		_serviceTracker.open();
	}

	private static final RepositorySearchQueryBuilderUtil
		_repositorySearchQueryBuilderUtil =
			new RepositorySearchQueryBuilderUtil();

	private final ServiceTracker
		<RepositorySearchQueryBuilder, RepositorySearchQueryBuilder>
			_serviceTracker;

}