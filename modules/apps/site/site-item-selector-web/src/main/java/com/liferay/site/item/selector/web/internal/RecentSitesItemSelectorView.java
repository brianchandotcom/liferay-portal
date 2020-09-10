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
import com.liferay.item.selector.criteria.GroupItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;
import com.liferay.site.item.selector.web.internal.renderer.RecentGroupItemSelectorViewRenderer;
import com.liferay.site.util.GroupURLProvider;
import com.liferay.site.util.RecentGroupManager;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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
	public boolean isVisible(
		SiteItemSelectorCriterion siteItemSelectorCriterion,
		ThemeDisplay themeDisplay) {

		if (siteItemSelectorCriterion.isIncludeRecentSites()) {
			return true;
		}

		return false;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			SiteItemSelectorCriterion siteItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_recentGroupItemSelectorViewRender.renderHTML(
			servletRequest, servletResponse, siteItemSelectorCriterion,
			portletURL, itemSelectedEventName, search);
	}

	@Activate
	protected void activate() {
		_recentGroupItemSelectorViewRender =
			new RecentGroupItemSelectorViewRenderer(
				_groupURLProvider, _recentGroupManager, _servletContext);
	}

	@Deactivate
	protected void deactivate() {
		_recentGroupItemSelectorViewRender = null;
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new GroupItemSelectorReturnType(),
				new URLItemSelectorReturnType(),
				new UUIDItemSelectorReturnType()));

	@Reference
	private GroupURLProvider _groupURLProvider;

	@Reference
	private Portal _portal;

	private RecentGroupItemSelectorViewRenderer
		_recentGroupItemSelectorViewRender;

	@Reference
	private RecentGroupManager _recentGroupManager;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.item.selector.web)"
	)
	private ServletContext _servletContext;

}