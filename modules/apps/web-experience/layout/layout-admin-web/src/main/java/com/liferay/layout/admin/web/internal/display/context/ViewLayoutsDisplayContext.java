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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.layout.admin.web.internal.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class ViewLayoutsDisplayContext extends BaseLayoutDisplayContext {

	public ViewLayoutsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);
	}

	public PortletURL getAddLayoutURL() {
		return super.getAddLayoutURL(
			LayoutConstants.DEFAULT_PLID, isPrivatePages());
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				liferayPortletRequest);

		_displayStyle = portalPreferences.getValue(
			LayoutAdminPortletKeys.GROUP_PAGES, "display-style", "list");

		return _displayStyle;
	}

	public SearchContainer getLayoutsSearchContainer() throws PortalException {
		if (_layoutsSearchContainer != null) {
			return _layoutsSearchContainer;
		}

		String emptyResultMessage = "there-are-no-public-pages";

		if (isPrivatePages()) {
			emptyResultMessage = "there-are-no-private-pages";
		}

		SearchContainer layoutsSearchContainer = new SearchContainer(
			liferayPortletRequest, getPortletURL(), null, emptyResultMessage);

		if (isShowAddRootLayoutButton()) {
			layoutsSearchContainer.setEmptyResultsMessageCssClass(
				"there-are-no-layouts.-you-can-add-a-layout-by-clicking-the-" +
					"plus-button-on-the-bottom-right-corner");
			layoutsSearchContainer.setEmptyResultsMessageCssClass(
				"taglib-empty-result-message-header-has-plus-btn");
		}

		EmptyOnClickRowChecker emptyOnClickRowChecker =
			new EmptyOnClickRowChecker(liferayPortletResponse);

		layoutsSearchContainer.setRowChecker(emptyOnClickRowChecker);

		int layoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			getSelGroup(), isPrivatePages());
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			getSelGroupId(), isPrivatePages());

		layoutsSearchContainer.setTotal(layoutsCount);
		layoutsSearchContainer.setResults(layouts);

		_layoutsSearchContainer = layoutsSearchContainer;

		return _layoutsSearchContainer;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		String defaultNavigation = "public-pages";

		if (!isShowPublicPages()) {
			defaultNavigation = "private-pages";
		}

		_navigation = ParamUtil.getString(
			liferayPortletRequest, "navigation", defaultNavigation);

		return _navigation;
	}

	public String[] getNavigationKeys() {
		if (_navigationKeys != null) {
			return _navigationKeys;
		}

		_navigationKeys = new String[] {"public-pages", "private-pages"};

		if (!isShowPublicPages()) {
			_navigationKeys = new String[] {"private-pages"};
		}

		return _navigationKeys;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			liferayPortletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			liferayPortletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public String[] getOrderColumns() {
		return new String[] {"create-date"};
	}

	public String getPath(Layout layout, Locale locale) throws PortalException {
		List<Layout> layouts = layout.getAncestors();

		StringBundler sb = new StringBundler(layouts.size() * 4);

		for (Layout curLayout : layouts) {
			sb.append(curLayout.getName(locale));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		return sb.toString();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_layouts.jsp");
		portletURL.setParameter("navigation", getNavigation());
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public boolean isPrivatePages() {
		if (Objects.equals(getNavigation(), "private-pages")) {
			return true;
		}

		return false;
	}

	public boolean isPublicPages() {
		if (Objects.equals(getNavigation(), "public-pages")) {
			return true;
		}

		return false;
	}

	public boolean isShowPublicPages() {
		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype() || selGroup.isLayoutPrototype()) {
			return false;
		}

		return true;
	}

	private String _displayStyle;
	private SearchContainer _layoutsSearchContainer;
	private String _navigation;
	private String[] _navigationKeys;
	private String _orderByCol;
	private String _orderByType;

}