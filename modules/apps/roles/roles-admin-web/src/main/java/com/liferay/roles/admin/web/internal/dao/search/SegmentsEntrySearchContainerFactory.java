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

package com.liferay.roles.admin.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Pei-Jung Lan
 */
public class SegmentsEntrySearchContainerFactory {

	public static SearchContainer create(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		SearchContainer searchContainer = new SearchContainer(
			renderRequest,
			PortletURLUtil.getCurrent(renderRequest, renderResponse), null,
			"no-segments-were-found");

		searchContainer.setId("segmentsEntries");

		String orderByCol = ParamUtil.getString(
			renderRequest, "orderByCol", "name");

		searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			renderRequest, "orderByType", "asc");

		searchContainer.setOrderByType(orderByType);

		long roleId = ParamUtil.getLong(renderRequest, "roleId");
		String tabs3 = ParamUtil.getString(renderRequest, "tabs3", "current");

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		if (tabs3.equals("current")) {
			params.put("roleIds", new long[] {roleId});

			searchContainer.setRowChecker(
				new EmptyOnClickRowChecker(renderResponse));
		}
		else {
			searchContainer.setRowChecker(
				new SegmentsEntryRoleChecker(renderResponse, roleId));
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		BaseModelSearchResult baseModelSearchResult =
			SegmentsEntryLocalServiceUtil.searchSegmentsEntries(
				_buildSearchContext(
					themeDisplay.getCompanyId(),
					ParamUtil.getString(renderRequest, "keywords"), params,
					searchContainer.getStart(), searchContainer.getEnd(),
					_getSort(orderByCol, orderByType, themeDisplay)));

		searchContainer.setResults(baseModelSearchResult.getBaseModels());
		searchContainer.setTotal(baseModelSearchResult.getLength());

		return searchContainer;
	}

	private static SearchContext _buildSearchContext(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				Field.NAME, keywords
			).build();

		params.put("keywords", keywords);

		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	private static Sort _getSort(
		String orderByCol, String orderByType, ThemeDisplay themeDisplay) {

		if (Objects.equals(orderByCol, "name")) {
			String sortFieldName = Field.getSortableFieldName(
				"localized_name_".concat(themeDisplay.getLanguageId()));

			return new Sort(
				sortFieldName, Sort.STRING_TYPE,
				!Objects.equals(orderByType, "asc"));
		}

		return new Sort(
			Field.MODIFIED_DATE, Sort.LONG_TYPE,
			!Objects.equals(orderByType, "asc"));
	}

}