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

package com.liferay.asset.tags.selector.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagServiceUtil;
import com.liferay.asset.tags.selector.web.internal.search.EntriesChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetTagsSelectorDisplayContext {

	public AssetTagsSelectorDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_httpServletRequest = httpServletRequest;
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_renderResponse.getNamespace() + "selectTag");

		return _eventName;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public String[] getSelectedTagNames() {
		if (ArrayUtil.isNotEmpty(_selectedTagNames)) {
			return _selectedTagNames;
		}

		_selectedTagNames = ParamUtil.getStringValues(
			_renderRequest, "selectedTagNames");

		return _selectedTagNames;
	}

	public SearchContainer getTagsSearchContainer() {
		if (_tagsSearchContainer != null) {
			return _tagsSearchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer tagsSearchContainer = new SearchContainer(
			_renderRequest, _getPortletURL(), null, "there-are-no-tags");

		String orderByCol = _getOrderByCol();

		tagsSearchContainer.setOrderByCol(orderByCol);

		boolean orderByAsc = false;

		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		tagsSearchContainer.setOrderByComparator(
			new AssetTagNameComparator(orderByAsc));

		tagsSearchContainer.setOrderByType(orderByType);

		tagsSearchContainer.setRowChecker(
			new EntriesChecker(_renderRequest, _renderResponse));

		int tagsCount = AssetTagServiceUtil.getTagsCount(
			themeDisplay.getScopeGroupId(), _getKeywords());

		tagsSearchContainer.setTotal(tagsCount);

		List<AssetTag> tags = AssetTagServiceUtil.getTags(
			_getGroupIds(), _getKeywords(), tagsSearchContainer.getStart(),
			tagsSearchContainer.getEnd(),
			tagsSearchContainer.getOrderByComparator());

		tagsSearchContainer.setResults(tags);

		_tagsSearchContainer = tagsSearchContainer;

		return _tagsSearchContainer;
	}

	private long[] _getGroupIds() {
		if (ArrayUtil.isNotEmpty(_groupIds)) {
			return _groupIds;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_groupIds = StringUtil.split(
			ParamUtil.getString(_httpServletRequest, "groupIds"), 0L);

		if (ArrayUtil.isEmpty(_groupIds)) {
			_groupIds = new long[] {themeDisplay.getScopeGroupId()};
		}

		return _groupIds;
	}

	private String _getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords", null);

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "name");

		return _orderByCol;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("eventName", getEventName());
		portletURL.setParameter(
			"selectedTagNames", StringUtil.merge(getSelectedTagNames()));

		return portletURL;
	}

	private String _eventName;
	private long[] _groupIds;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private String[] _selectedTagNames;
	private SearchContainer _tagsSearchContainer;

}