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

package com.liferay.site.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;
import com.liferay.site.item.selector.web.internal.constants.SitesItemSelectorWebKeys;
import com.liferay.site.item.selector.web.internal.display.context.RecentSitesItemSelectorViewDisplayContext;
import com.liferay.site.util.GroupURLProvider;
import com.liferay.site.util.RecentGroupManager;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	property = "item.selector.view.order:Integer=100",
	service = ItemSelectorView.class
)
public class RecentSitesItemSelectorView
	implements ItemSelectorView<SiteItemSelectorCriterion> {

	@Override
	public Class<SiteItemSelectorCriterion> getItemSelectorCriterionClass() {
		return SiteItemSelectorCriterion.class;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return ResourceBundleUtil.getString(
			_portal.getResourceBundle(locale), "recent[site-item-selector]");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			SiteItemSelectorCriterion siteItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		servletRequest.setAttribute(
			SiteWebKeys.GROUP_URL_PROVIDER, _groupURLProvider);

		RecentSitesItemSelectorViewDisplayContext
			siteItemSelectorViewDisplayContext =
				new RecentSitesItemSelectorViewDisplayContext(
					(HttpServletRequest)servletRequest,
					siteItemSelectorCriterion, itemSelectedEventName,
					portletURL, _recentGroupManager);

		servletRequest.setAttribute(
			SitesItemSelectorWebKeys.SITES_ITEM_SELECTOR_DISPLAY_CONTEXT,
			siteItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/view_sites.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Reference(unbind = "-")
	public void setGroupURLProvider(GroupURLProvider groupURLProvider) {
		_groupURLProvider = groupURLProvider;
	}

	@Reference(unbind = "-")
	public void setRecentGroupManager(RecentGroupManager recentGroupManager) {
		_recentGroupManager = recentGroupManager;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.item.selector.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new URLItemSelectorReturnType(),
				new UUIDItemSelectorReturnType()));

	private GroupURLProvider _groupURLProvider;

	@Reference
	private Portal _portal;

	private RecentGroupManager _recentGroupManager;
	private ServletContext _servletContext;

}