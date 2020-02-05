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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalServiceUtil;
import com.liferay.change.tracking.service.CTPreferencesLocalServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class SelectChangeListDisplayContext {

	public SelectChangeListDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			CTPreferencesLocalServiceUtil.fetchCTPreferences(
				_themeDisplay.getCompanyId(), _themeDisplay.getUserId());

		long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

		if (ctPreferences != null) {
			ctCollectionId = ctPreferences.getCtCollectionId();
		}

		_ctCollectionId = ctCollectionId;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public String getEventName() {
		return _renderResponse.getNamespace() + "selectChangeList";
	}

	public SearchContainer<CTCollection> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<CTCollection> searchContainer = new SearchContainer(
			_renderRequest, new DisplayTerms(_renderRequest), null,
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			PortletURLUtil.getCurrent(_renderRequest, _renderResponse), null,
			LanguageUtil.get(
				_httpServletRequest, "no-publications-were-found"));

		searchContainer.setId("selectChangeList");
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByType(_getOrderByType());

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		String keywords = displayTerms.getKeywords();

		int count = (int)CTCollectionLocalServiceUtil.dynamicQueryCount(
			_getSearchDynamicQuery(_themeDisplay.getCompanyId(), keywords));

		searchContainer.setTotal(count);

		String column = searchContainer.getOrderByCol();

		if (column.equals("modified-date")) {
			column = "modifiedDate";
		}

		OrderByComparator<CTCollection> orderByComparator =
			OrderByComparatorFactoryUtil.create(
				"CTCollection", column,
				Objects.equals(searchContainer.getOrderByType(), "asc"));

		List<CTCollection> results = CTCollectionLocalServiceUtil.dynamicQuery(
			_getSearchDynamicQuery(_themeDisplay.getCompanyId(), keywords),
			searchContainer.getStart(), searchContainer.getEnd(),
			orderByComparator);

		searchContainer.setResults(results);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private String _getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modified-date");
	}

	private String _getOrderByType() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			"desc");
	}

	private DynamicQuery _getSearchDynamicQuery(
		long companyId, String keywords) {

		DynamicQuery dynamicQuery = CTCollectionLocalServiceUtil.dynamicQuery();

		if (companyId > 0) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq("companyId", companyId));
		}

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"status", WorkflowConstants.STATUS_DRAFT));

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		for (String keyword : StringUtil.split(keywords, CharPool.SPACE)) {
			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"name", StringUtil.quote(keyword, StringPool.PERCENT)));
			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"description",
					StringUtil.quote(keyword, StringPool.PERCENT)));
		}

		return dynamicQuery.add(disjunction);
	}

	private final long _ctCollectionId;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CTCollection> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}