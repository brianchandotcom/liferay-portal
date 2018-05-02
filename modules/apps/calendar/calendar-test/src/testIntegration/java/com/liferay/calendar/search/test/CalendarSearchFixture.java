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

package com.liferay.calendar.search.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.util.HitsAssert;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class CalendarSearchFixture {

	public CalendarSearchFixture(IndexerRegistry indexerRegistry) {
		_indexerRegistry = indexerRegistry;
	}

	public SearchContext getSearchContext(String keywords, Locale locale) {
		SearchContext searchContext = new SearchContext();

		try {
			searchContext.setCompanyId(TestPropsValues.getCompanyId());
			searchContext.setUserId(getUserId());
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}

		searchContext.setGroupIds(new long[] {_group.getGroupId()});
		searchContext.setKeywords(keywords);
		searchContext.setLocale(Objects.requireNonNull(locale));

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(StringPool.STAR);

		return searchContext;
	}

	public Hits search(SearchContext searchContext) {
		try {
			return _indexer.search(searchContext);
		}
		catch (SearchException se) {
			throw new RuntimeException(se);
		}
	}

	public Document searchOnlyOne(String keywords, Locale locale) {
		return HitsAssert.assertOnlyOne(
			search(getSearchContext(keywords, locale)));
	}

	public void setGroup(Group group) {
		_group = group;
	}

	public void setIndexerClass(Class<?> clazz) {
		_indexer = _indexerRegistry.getIndexer(clazz);
	}

	public void setUser(User user) {
		_user = user;
	}

	protected long getUserId() throws PortalException {
		if (_user != null) {
			return _user.getUserId();
		}

		return TestPropsValues.getUserId();
	}

	private Group _group;
	private Indexer<?> _indexer;
	private final IndexerRegistry _indexerRegistry;
	private User _user;

}