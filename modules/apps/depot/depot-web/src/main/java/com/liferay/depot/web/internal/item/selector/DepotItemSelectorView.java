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

package com.liferay.depot.web.internal.item.selector;

import com.liferay.depot.web.internal.util.DepotAdminGroupSearchProvider;
import com.liferay.depot.web.internal.util.DepotSupportChecker;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.GroupItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.item.selector.criteria.group.criterion.GroupItemSelectorCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.item.selector.display.context.SitesItemSelectorViewDisplayContext;
import com.liferay.site.item.selector.renderer.SiteItemSelectorViewRenderer;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "item.selector.view.order:Integer=400",
	service = ItemSelectorView.class
)
public class DepotItemSelectorView
	implements ItemSelectorView<GroupItemSelectorCriterion> {

	@Override
	public Class<GroupItemSelectorCriterion> getItemSelectorCriterionClass() {
		return GroupItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return ResourceBundleUtil.getString(
			_portal.getResourceBundle(locale), "repositories");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return _depotSupportChecker.isEnabled();
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			GroupItemSelectorCriterion groupItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		SitesItemSelectorViewDisplayContext
			depotSiteItemSelectorViewDisplayContext =
				new DepotSiteItemSelectorViewDisplayContext(
					(HttpServletRequest)servletRequest, itemSelectedEventName,
					portletURL, groupItemSelectorCriterion);

		_siteItemSelectorViewRenderer.renderHTML(
			depotSiteItemSelectorViewDisplayContext);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new GroupItemSelectorReturnType(),
				new URLItemSelectorReturnType(),
				new UUIDItemSelectorReturnType()));

	@Reference
	private DepotAdminGroupSearchProvider _depotAdminGroupSearchProvider;

	@Reference
	private DepotSupportChecker _depotSupportChecker;

	@Reference
	private Portal _portal;

	@Reference
	private SiteItemSelectorViewRenderer _siteItemSelectorViewRenderer;

	private class DepotSiteItemSelectorViewDisplayContext
		implements SitesItemSelectorViewDisplayContext {

		public DepotSiteItemSelectorViewDisplayContext(
			HttpServletRequest httpServletRequest, String itemSelectedEventName,
			PortletURL portletURL,
			GroupItemSelectorCriterion groupItemSelectorCriterion) {

			_httpServletRequest = httpServletRequest;
			_itemSelectedEventName = itemSelectedEventName;
			_portletURL = portletURL;
			_groupItemSelectorCriterion = groupItemSelectorCriterion;
		}

		@Override
		public String getDisplayStyle() {
			return ParamUtil.getString(
				_httpServletRequest, "displayStyle", "icon");
		}

		@Override
		public GroupItemSelectorCriterion getGroupItemSelectorCriterion() {
			return _groupItemSelectorCriterion;
		}

		@Override
		public String getGroupName(Group group) throws PortalException {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return group.getDescriptiveName(themeDisplay.getLocale());
		}

		@Override
		public GroupSearch getGroupSearch() {
			return _depotAdminGroupSearchProvider.getGroupSearch(
				getPortletRequest(), getPortletURL());
		}

		@Override
		public String getItemSelectedEventName() {
			return _itemSelectedEventName;
		}

		@Override
		public PortletRequest getPortletRequest() {
			return (PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		}

		@Override
		public PortletResponse getPortletResponse() {
			return (PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);
		}

		@Override
		public PortletURL getPortletURL() {
			return _portletURL;
		}

		@Override
		public boolean isShowChildSitesLink() {
			return false;
		}

		@Override
		public boolean isShowSearch() {
			return true;
		}

		@Override
		public boolean isShowSortFilter() {
			return true;
		}

		private final GroupItemSelectorCriterion _groupItemSelectorCriterion;
		private final HttpServletRequest _httpServletRequest;
		private final String _itemSelectedEventName;
		private final PortletURL _portletURL;

	}

}