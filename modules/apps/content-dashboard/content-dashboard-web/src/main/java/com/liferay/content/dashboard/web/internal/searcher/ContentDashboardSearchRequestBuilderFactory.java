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

package com.liferay.content.dashboard.web.internal.searcher;

import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.util.ContentDashboardSearchClassNameUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = ContentDashboardSearchRequestBuilderFactory.class)
public class ContentDashboardSearchRequestBuilderFactory {

	public SearchRequestBuilder builder(SearchContext searchContext) {
		Collection<String> classNames = _getClassNames();

		return _searchRequestBuilderFactory.builder(
			searchContext
		).emptySearchEnabled(
			true
		).entryClassNames(
			classNames.toArray(new String[0])
		).fields(
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.UID
		).highlightEnabled(
			false
		);
	}

	private Collection<String> _getClassNames() {
		Collection<String> classNames =
			_contentDashboardItemFactoryTracker.getClassNames();

		Stream<String> stream = classNames.stream();

		return stream.map(
			ContentDashboardSearchClassNameUtil::getSearchClassName
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}