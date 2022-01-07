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
import com.liferay.asset.tags.selector.web.internal.constants.AssetTagsSelectorPortletKeys;
import com.liferay.asset.tags.selector.web.internal.search.EntriesChecker;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetTagsSelectorDisplayContext {

	public AssetTagsSelectorDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		this(httpServletRequest, renderRequest, renderResponse, true);
	}

	public AssetTagsSelectorDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse, boolean rowChecker) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_rowChecker = rowChecker;
	}

	public String getAssetTagGroupName(AssetTag assetTag, Locale locale)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(assetTag.getGroupId());

		return group.getDescriptiveName(locale);
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

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest,
			AssetTagsSelectorPortletKeys.ASSET_TAGS_SELECTOR, "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			_getMvcPath()
		).setParameter(
			"eventName", getEventName()
		).setParameter(
			"groupIds", StringUtil.merge(_getGroupIds())
		).setParameter(
			"selectedTagNames", StringUtil.merge(getSelectedTagNames())
		).buildPortletURL();
	}

	public String[] getSelectedTagNames() {
		if (ArrayUtil.isNotEmpty(_selectedTagNames)) {
			return _selectedTagNames;
		}

		_selectedTagNames = ParamUtil.getStringValues(
			_renderRequest, "selectedTagNames");

		return _selectedTagNames;
	}

	public SearchContainer<AssetTag> getTagsSearchContainer() {
		if (_tagsSearchContainer != null) {
			return _tagsSearchContainer;
		}

		SearchContainer<AssetTag> tagsSearchContainer = new SearchContainer(
			_renderRequest, getPortletURL(), null, "there-are-no-tags");

		tagsSearchContainer.setOrderByCol(_getOrderByCol());

		boolean orderByAsc = false;

		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		tagsSearchContainer.setOrderByComparator(
			new AssetTagNameComparator(orderByAsc));
		tagsSearchContainer.setOrderByType(orderByType);

		tagsSearchContainer.setResultsAndTotal(
			() -> AssetTagServiceUtil.getTags(
				_getGroupIds(), _getKeywords(), tagsSearchContainer.getStart(),
				tagsSearchContainer.getEnd(),
				tagsSearchContainer.getOrderByComparator()),
			AssetTagServiceUtil.getTagsCount(_getGroupIds(), _getKeywords()));

		if (_rowChecker) {
			tagsSearchContainer.setRowChecker(
				new EntriesChecker(_renderRequest, _renderResponse));
		}

		_tagsSearchContainer = tagsSearchContainer;

		return _tagsSearchContainer;
	}

	private long[] _getGroupIds() {
		if (ArrayUtil.isNotEmpty(_groupIds)) {
			return _groupIds;
		}

		_groupIds = StringUtil.split(
			ParamUtil.getString(_httpServletRequest, "groupIds"), 0L);

		if (ArrayUtil.isEmpty(_groupIds)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

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

	private String _getMvcPath() {
		if (Validator.isNotNull(_mvcPath)) {
			return _mvcPath;
		}

		_mvcPath = ParamUtil.getString(
			_httpServletRequest, "mvcPath", "/view.jsp");

		return _mvcPath;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest,
			AssetTagsSelectorPortletKeys.ASSET_TAGS_SELECTOR, "name");

		return _orderByCol;
	}

	private String _eventName;
	private long[] _groupIds;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _mvcPath;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final boolean _rowChecker;
	private String[] _selectedTagNames;
	private SearchContainer<AssetTag> _tagsSearchContainer;

}