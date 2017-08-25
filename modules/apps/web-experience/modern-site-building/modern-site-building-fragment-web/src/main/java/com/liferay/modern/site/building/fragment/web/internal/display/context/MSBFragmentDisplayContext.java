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

package com.liferay.modern.site.building.fragment.web.internal.display.context;

import com.liferay.modern.site.building.fragment.constants.MSBFragmentPortletKeys;
import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;
import com.liferay.modern.site.building.fragment.service.MSBFragmentCollectionServiceUtil;
import com.liferay.modern.site.building.fragment.web.util.MSBFragmentPortletUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class MSBFragmentDisplayContext {

	public MSBFragmentDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			MSBFragmentPortletKeys.MSB_FRAGMENT, "display-style", "icon");

		return _displayStyle;
	}

	public SearchContainer getMSBFragmentCollectionsSearchContainer()
		throws PortalException {

		if (_msbFragmentCollectionsSearchContainer != null) {
			return _msbFragmentCollectionsSearchContainer;
		}

		SearchContainer msbFragmentCollectionsSearchContainer =
			new SearchContainer(
				_renderRequest, _renderResponse.createRenderURL(), null,
				"there-are-no-fragment-collections");

		msbFragmentCollectionsSearchContainer.setEmptyResultsMessage(
			"there-are-no-fragment-collections.-you-can-add-a-fragment-" +
				"collection-by-clicking-the-plus-button-on-the-bottom-right-" +
					"corner");

		msbFragmentCollectionsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		OrderByComparator<MSBFragmentCollection> orderByComparator =
			MSBFragmentPortletUtil.getMSBFragmentCollectionOrderByComparator(
				getOrderByCol(), getOrderByType());

		msbFragmentCollectionsSearchContainer.setOrderByCol(getOrderByCol());
		msbFragmentCollectionsSearchContainer.setOrderByComparator(
			orderByComparator);
		msbFragmentCollectionsSearchContainer.setOrderByType(getOrderByType());

		msbFragmentCollectionsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = themeDisplay.getScopeGroupId();

		List<MSBFragmentCollection> msbFragmentCollections =
			MSBFragmentCollectionServiceUtil.getMSBFragmentCollections(
				scopeGroupId, msbFragmentCollectionsSearchContainer.getStart(),
				msbFragmentCollectionsSearchContainer.getEnd(),
				orderByComparator);

		msbFragmentCollectionsSearchContainer.setTotal(
			msbFragmentCollections.size());

		msbFragmentCollectionsSearchContainer.setResults(
			msbFragmentCollections);

		_msbFragmentCollectionsSearchContainer =
			msbFragmentCollectionsSearchContainer;

		return _msbFragmentCollectionsSearchContainer;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	private String _displayStyle;
	private SearchContainer _msbFragmentCollectionsSearchContainer;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}