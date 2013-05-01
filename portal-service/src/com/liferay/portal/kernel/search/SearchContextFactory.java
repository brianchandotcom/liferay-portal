/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchContextFactory {

	public static SearchContext getInstance(HttpServletRequest request) {
		SearchContext searchContext = new SearchContext();

		// Theme display

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		searchContext.setCompanyId(themeDisplay.getCompanyId());
		searchContext.setGroupIds(new long[] {themeDisplay.getScopeGroupId()});
		searchContext.setLayout(themeDisplay.getLayout());
		searchContext.setLocale(themeDisplay.getLocale());
		searchContext.setTimeZone(themeDisplay.getTimeZone());
		searchContext.setUserId(themeDisplay.getUserId());

		// Attributes

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		Map<String, String[]> parameters = request.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			if ((values != null) && (values.length > 0)) {
				if (values.length == 1) {
					attributes.put(name, values[0]);
				}
				else {
					attributes.put(name, values);
				}
			}
		}

		searchContext.setAttributes(attributes);

		// Asset

		long[] assetCategoryIds = StringUtil.split(
			ParamUtil.getString(request, "assetCategoryIds"), 0L);

		String[] assetTagNames = StringUtil.split(
			ParamUtil.getString(request, "assetTagNames"));

		searchContext.setAssetCategoryIds(assetCategoryIds);
		searchContext.setAssetTagNames(assetTagNames);

		// Keywords

		String keywords = ParamUtil.getString(request, "keywords");

		searchContext.setKeywords(keywords);

		// Query config

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setLocale(themeDisplay.getLocale());

		searchContext.setQueryConfig(queryConfig);

		return searchContext;
	}

}