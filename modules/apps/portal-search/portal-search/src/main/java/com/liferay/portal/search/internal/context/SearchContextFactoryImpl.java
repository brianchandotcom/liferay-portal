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

package com.liferay.portal.search.internal.context;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.context.SearchContextFactory;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = SearchContextFactory.class)
public class SearchContextFactoryImpl implements SearchContextFactory {

	@Override
	public SearchContext getSearchContext(
		long[] assetCategoryIds, String[] assetTagNames, long companyId,
		String keywords, Layout layout, Locale locale,
		Map<String, String[]> parameters, long scopeGroupId, TimeZone timeZone,
		long userId) {

		SearchContext searchContext = new SearchContext();

		// Theme display

		searchContext.setCompanyId(companyId);
		searchContext.setGroupIds(new long[] {scopeGroupId});
		searchContext.setLayout(layout);
		searchContext.setLocale(locale);
		searchContext.setTimeZone(timeZone);
		searchContext.setUserId(userId);

		// Attributes

		Map<String, Serializable> attributes = new HashMap<>();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String[] values = entry.getValue();

			if (ArrayUtil.isNotEmpty(values)) {
				String name = entry.getKey();

				if (values.length == 1) {
					attributes.put(name, values[0]);
				}
				else {
					attributes.put(name, values);
				}
			}
		}

		if (!parameters.containsKey("groupId")) {
			String[] scopes = parameters.get("scope");

			if (scopes != null) {
				String groupId = "0";

				if (Objects.equals(scopes[0], "this-site")) {
					groupId = String.valueOf(scopeGroupId);
				}

				attributes.put("groupId", groupId);
			}
		}

		searchContext.setAttributes(attributes);

		// Asset

		searchContext.setAssetCategoryIds(assetCategoryIds);
		searchContext.setAssetTagNames(assetTagNames);

		// Keywords

		searchContext.setKeywords(keywords);

		// Query config

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setLocale(locale);

		return searchContext;
	}

}