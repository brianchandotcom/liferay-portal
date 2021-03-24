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

package com.liferay.layout.item.selector.web.internal.display.context;

import com.liferay.item.selector.constants.ItemSelectorPortletKeys;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class LayoutItemSelectorViewDisplayContext {

	public LayoutItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest,
		LayoutItemSelectorCriterion layoutItemSelectorCriterion,
		PortletURL portletURL, String itemSelectedEventName,
		boolean privateLayout) {

		_httpServletRequest = httpServletRequest;
		_layoutItemSelectorCriterion = layoutItemSelectorCriterion;
		_portletURL = portletURL;
		_itemSelectedEventName = itemSelectedEventName;
		_privateLayout = privateLayout;

		_renderResponse = (RenderResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public List<BreadcrumbEntry> getPortletBreadcrumbEntries()
		throws PortalException, PortletException {

		return Arrays.asList(
			_getSitesAndAssetLibrariesBreadcrumbEntry(),
			_getHomeBreadcrumbEntry());
	}

	public boolean isCheckDisplayPage() {
		return _layoutItemSelectorCriterion.isCheckDisplayPage();
	}

	public boolean isEnableCurrentPage() {
		return _layoutItemSelectorCriterion.isEnableCurrentPage();
	}

	public boolean isFollowURLOnTitleClick() {
		return _layoutItemSelectorCriterion.isFollowURLOnTitleClick();
	}

	public boolean isMultiSelection() {
		return _layoutItemSelectorCriterion.isMultiSelection();
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public boolean isShowBreadcrumb() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		boolean showBreadcrumb = ParamUtil.getBoolean(
			_httpServletRequest, "showBreadcrumb");

		if (Objects.equals(
				ItemSelectorPortletKeys.ITEM_SELECTOR,
				portletDisplay.getPortletName()) ||
			showBreadcrumb) {

			return true;
		}

		return false;
	}

	public boolean isShowHiddenPages() {
		return _layoutItemSelectorCriterion.isShowHiddenPages();
	}

	private BreadcrumbEntry _getHomeBreadcrumbEntry() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(themeDisplay.getSiteGroupName());

		return breadcrumbEntry;
	}

	private BreadcrumbEntry _getSitesAndAssetLibrariesBreadcrumbEntry()
		throws PortletException {

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			LanguageUtil.get(_httpServletRequest, "sites-and-libraries"));

		breadcrumbEntry.setURL(
			PortletURLBuilder.create(
				PortletURLUtil.clone(
					_portletURL,
					PortalUtil.getLiferayPortletResponse(_renderResponse))
			).setParameter(
				"groupType", "site"
			).setParameter(
				"showGroupSelector", Boolean.TRUE.toString()
			).buildString());

		return breadcrumbEntry;
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final LayoutItemSelectorCriterion _layoutItemSelectorCriterion;
	private final PortletURL _portletURL;
	private final boolean _privateLayout;
	private final RenderResponse _renderResponse;

}