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

import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryRegistry;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = ContentDashboardSearchRequestBuilderFactory.class)
public class ContentDashboardSearchRequestBuilderFactory {

	public SearchRequestBuilder builder(SearchContext searchContext) {
		if (ArrayUtil.isEmpty(searchContext.getEntryClassNames())) {
			searchContext.setEntryClassNames(
				TransformUtil.transformToArray(
					_contentDashboardItemFactoryRegistry.getClassNames(),
					className ->
						_infoSearchClassMapperRegistry.getSearchClassName(
							className),
					String.class));
		}
		else {
			searchContext.setEntryClassNames(
				TransformUtil.transform(
					searchContext.getEntryClassNames(),
					className ->
						_infoSearchClassMapperRegistry.getSearchClassName(
							className),
					String.class));
		}

		return _searchRequestBuilderFactory.builder(
			searchContext
		).emptySearchEnabled(
			true
		).entryClassNames(
			searchContext.getEntryClassNames()
		).fields(
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.UID
		).highlightEnabled(
			false
		);
	}

	@Reference
	private ContentDashboardItemFactoryRegistry
		_contentDashboardItemFactoryRegistry;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}