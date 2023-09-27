/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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