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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentCollectionNameComparator;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectFragmentCollectionDisplayContext {

	public SelectFragmentCollectionDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_httpServletRequest = httpServletRequest;
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_renderResponse.getNamespace() + "selectFragmentCollection");

		return _eventName;
	}

	public SearchContainer getFragmentCollectionsSearchContainer() {
		if (_fragmentCollectionsSearchContainer != null) {
			return _fragmentCollectionsSearchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer fragmentCollectionsSearchContainer =
			new SearchContainer(
				_renderRequest, getPortletURL(), null,
				"there-are-no-collections");

		fragmentCollectionsSearchContainer.setOrderByCol(_getOrderByCol());

		OrderByComparator<FragmentCollection> orderByComparator =
			_getFragmentCollectionOrderByComparator();

		fragmentCollectionsSearchContainer.setOrderByComparator(
			orderByComparator);

		fragmentCollectionsSearchContainer.setOrderByType(_getOrderByType());
		fragmentCollectionsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		List<FragmentCollection> fragmentCollections = null;
		int fragmentCollectionsCount = 0;

		if (_isSearch()) {
			fragmentCollections =
				FragmentCollectionServiceUtil.getFragmentCollections(
					themeDisplay.getScopeGroupId(), _getKeywords(),
					fragmentCollectionsSearchContainer.getStart(),
					fragmentCollectionsSearchContainer.getEnd(),
					orderByComparator);

			fragmentCollectionsCount =
				FragmentCollectionServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId(), _getKeywords());
		}
		else {
			fragmentCollections =
				FragmentCollectionServiceUtil.getFragmentCollections(
					themeDisplay.getScopeGroupId(),
					fragmentCollectionsSearchContainer.getStart(),
					fragmentCollectionsSearchContainer.getEnd(),
					orderByComparator);

			fragmentCollectionsCount =
				FragmentCollectionServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId());
		}

		fragmentCollectionsSearchContainer.setResults(fragmentCollections);
		fragmentCollectionsSearchContainer.setTotal(fragmentCollectionsCount);

		_fragmentCollectionsSearchContainer =
			fragmentCollectionsSearchContainer;

		return _fragmentCollectionsSearchContainer;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/fragment/select_fragment_collection");
		portletURL.setParameter("eventName", getEventName());

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = _getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = _getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	private OrderByComparator<FragmentCollection>
		_getFragmentCollectionOrderByComparator() {

		boolean orderByAsc = false;

		if (Objects.equals(_getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		OrderByComparator<FragmentCollection> orderByComparator = null;

		if (Objects.equals(_getOrderByCol(), "create-date")) {
			orderByComparator = new FragmentCollectionCreateDateComparator(
				orderByAsc);
		}
		else if (Objects.equals(_getOrderByCol(), "name")) {
			orderByComparator = new FragmentCollectionNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private String _eventName;
	private SearchContainer _fragmentCollectionsSearchContainer;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}