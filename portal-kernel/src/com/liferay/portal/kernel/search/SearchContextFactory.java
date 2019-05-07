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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchContextFactory {

	public static SearchContext getInstance(
		HttpServletRequest httpServletRequest) {

		SearchContext searchContext = new SearchContext();

		// Theme display

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContext.setCompanyId(themeDisplay.getCompanyId());
		searchContext.setGroupIds(new long[] {themeDisplay.getScopeGroupId()});
		searchContext.setLayout(themeDisplay.getLayout());
		searchContext.setLocale(themeDisplay.getLocale());
		searchContext.setTimeZone(themeDisplay.getTimeZone());
		searchContext.setUserId(themeDisplay.getUserId());

		// Attributes

		Map<String, Serializable> attributes = new HashMap<>();

		Map<String, String[]> parameters = httpServletRequest.getParameterMap();

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
					groupId = String.valueOf(themeDisplay.getScopeGroupId());
				}

				attributes.put("groupId", groupId);
			}
		}

		searchContext.setAttributes(attributes);

		// Asset

		long[] assetCategoryIds = StringUtil.split(
			ParamUtil.getString(httpServletRequest, "assetCategoryIds"), 0L);

		String[] assetTagNames = StringUtil.split(
			ParamUtil.getString(httpServletRequest, "assetTagNames"));

		searchContext.setAssetCategoryIds(assetCategoryIds);
		searchContext.setAssetTagNames(assetTagNames);

		// Keywords

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		searchContext.setKeywords(keywords);

		// Query config

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setLocale(themeDisplay.getLocale());

		return searchContext;
	}

	public static SearchContext getInstance(
		long[] assetCategoryIds, String[] assetTagNames,
		Map<String, Serializable> attributes, long companyId, String keywords,
		Layout layout, Locale locale, long scopeGroupId, TimeZone timeZone,
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

		if (attributes != null) {
			searchContext.setAttributes(attributes);
		}
		else {
			searchContext.setAttributes(new HashMap<String, Serializable>());
		}

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