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

package com.liferay.site.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public SiteAdminManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest request,
			SiteAdminDisplayContext siteAdminDisplayContext)
		throws PortalException {

		super(
			liferayPortletRequest, liferayPortletResponse, request,
			siteAdminDisplayContext.getSearchContainer());

		_siteAdminDisplayContext = siteAdminDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteSites");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter("orderByCol", getOrderByCol());
		clearResultsURL.setParameter("orderByType", getOrderByType());

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "siteAdminWebManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			PortletURL addSiteURL = liferayPortletResponse.createRenderURL();

			addSiteURL.setParameter("jspPage", "/select_site_initializer.jsp");
			addSiteURL.setParameter("redirect", themeDisplay.getURLCurrent());

			Group group = _siteAdminDisplayContext.getGroup();

			if ((group != null) &&
				_siteAdminDisplayContext.hasAddChildSitePermission(group)) {

				addSiteURL.setParameter(
					"parentGroupSearchContainerPrimaryKeys",
					String.valueOf(group.getGroupId()));
			}

			return new CreationMenu() {
				{
					addPrimaryDropdownItem(
						dropdownItem -> {
							dropdownItem.setHref(addSiteURL.toString());
							dropdownItem.setLabel(
								LanguageUtil.get(request, "add"));
						});
				}
			};
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return "sitesManagementToolbarDefaultEventHandler";
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchTagURL = getPortletURL();

		searchTagURL.setParameter("orderByCol", getOrderByCol());
		searchTagURL.setParameter("orderByType", getOrderByType());

		return searchTagURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "sites";
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "changeDisplayStyle");
		portletURL.setParameter("redirect", PortalUtil.getCurrentURL(request));

		return new ViewTypeItemList(
			portletURL, _siteAdminDisplayContext.getDisplayStyle()) {

			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}

		};
	}

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (PortalPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				ActionKeys.ADD_COMMUNITY)) {

			return true;
		}

		return false;
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"name"};
	}

	private final SiteAdminDisplayContext _siteAdminDisplayContext;

}