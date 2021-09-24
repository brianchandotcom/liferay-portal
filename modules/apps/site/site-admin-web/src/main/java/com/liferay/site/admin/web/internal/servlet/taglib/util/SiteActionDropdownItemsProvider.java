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

package com.liferay.site.admin.web.internal.servlet.taglib.util;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.admin.web.internal.display.context.SiteAdminDisplayContext;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteActionDropdownItemsProvider {

	public SiteActionDropdownItemsProvider(
		Group group, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SiteAdminDisplayContext siteAdminDisplayContext) {

		_group = group;
		_liferayPortletResponse = liferayPortletResponse;
		_siteAdminDisplayContext = siteAdminDisplayContext;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		int count = GroupLocalServiceUtil.getGroupsCount(
			_themeDisplay.getCompanyId(), _group.getGroupId(), true);
		boolean hasUpdatePermission = GroupPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), _group, ActionKeys.UPDATE);

		return DropdownItemListBuilder.add(
			() -> hasUpdatePermission && (count > 0),
			_getViewChildSitesActionUnsafeConsumer()
		).add(
			() ->
				hasUpdatePermission &&
				_siteAdminDisplayContext.hasAddChildSitePermission(_group),
			_getAddChildSiteActionUnsafeConsumer()
		).add(
			() -> hasUpdatePermission,
			_getViewSiteSettingsActionUnsafeConsumer()
		).add(
			() -> _group.isActive() && (_group.getPublicLayoutsPageCount() > 0),
			_getViewSitePublicPagesActionUnsafeConsumer()
		).add(
			() ->
				_group.isActive() && (_group.getPrivateLayoutsPageCount() > 0),
			_getViewSitePrivatePagesActionUnsafeConsumer()
		).add(
			() -> _hasEditAssignmentsPermission(),
			_getLeaveSiteActionUnsafeConsumer()
		).add(
			() ->
				hasUpdatePermission && _group.isActive() &&
				!_group.isCompany() && !_group.isGuest(),
			_getDeactivateSiteActionUnsafeConsumer()
		).add(
			() ->
				hasUpdatePermission && !_group.isActive() &&
				!_group.isCompany(),
			_getActivateSiteActionUnsafeConsumer()
		).add(
			() -> _hasDeleteGroupPermission(),
			_getDeleteSiteActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getActivateSiteActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "activateSite");
			dropdownItem.putData(
				"activateSiteURL",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setActionName(
					"/site_admin/activate_group"
				).setRedirect(
					_getRedirect()
				).setParameter(
					"groupId", _group.getGroupId()
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "activate"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAddChildSiteActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(),
				"mvcRenderCommandName", "/site_admin/select_site_initializer",
				"redirect", _themeDisplay.getURLCurrent(), "parentGroupId",
				String.valueOf(_group.getGroupId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "add-child-site"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeactivateSiteActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "deactivateSite");
			dropdownItem.putData(
				"deactivateSiteURL",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setActionName(
					"/site_admin/deactivate_group"
				).setRedirect(
					_getRedirect()
				).setParameter(
					"groupId", _group.getGroupId()
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "deactivate"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteSiteActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteSite");
			dropdownItem.putData(
				"deleteSiteURL",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setActionName(
					"/site_admin/delete_groups"
				).setRedirect(
					_getRedirect()
				).setParameter(
					"groupId", _group.getGroupId()
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getLeaveSiteActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "leaveSite");
			dropdownItem.putData(
				"leaveSiteURL",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setActionName(
					"/site_admin/edit_group_assignments"
				).setRedirect(
					_getRedirect()
				).setParameter(
					"groupId", _group.getGroupId()
				).setParameter(
					"removeUserIds", _themeDisplay.getUserId()
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "leave"));
		};
	}

	private String _getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(
			_httpServletRequest, "redirect", _themeDisplay.getURLCurrent());

		return _redirect;
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewChildSitesActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "backURL",
				_getRedirect(), "groupId", String.valueOf(_group.getGroupId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-child-sites"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewSitePrivatePagesActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(_group.getDisplayURL(_themeDisplay, true));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "go-to-private-pages"));
			dropdownItem.setTarget("_blank");
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewSitePublicPagesActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_group.getDisplayURL(_themeDisplay, false, true));
			dropdownItem.setIcon("shortcut");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "go-to-public-pages"));
			dropdownItem.setTarget("_blank");
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewSiteSettingsActionUnsafeConsumer() {

		PortletURL viewSiteSettingsURL = PortalUtil.getControlPanelPortletURL(
			_httpServletRequest, _group,
			ConfigurationAdminPortletKeys.SITE_SETTINGS, 0, 0,
			PortletRequest.RENDER_PHASE);

		return dropdownItem -> {
			dropdownItem.setHref(viewSiteSettingsURL);
			dropdownItem.setIcon("shortcut");
			dropdownItem.setLabel(
				LanguageUtil.format(
					_httpServletRequest, "go-to-x", "site-settings"));
			dropdownItem.setTarget("_blank");
		};
	}

	private boolean _hasDeleteGroupPermission() throws PortalException {
		if (_group.isCompany() ||
			!GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _group,
				ActionKeys.DELETE) ||
			PortalUtil.isSystemGroup(_group.getGroupKey())) {

			return false;
		}

		return true;
	}

	private boolean _hasEditAssignmentsPermission() throws Exception {
		if (_group.isCompany()) {
			return false;
		}

		List<String> organizationNames = SitesUtil.getOrganizationNames(
			_group, _themeDisplay.getUser());

		if (!organizationNames.isEmpty()) {
			return false;
		}

		List<String> userGroupNames = SitesUtil.getUserGroupNames(
			_group, _themeDisplay.getUser());

		if (!userGroupNames.isEmpty() ||
			((_group.getType() != GroupConstants.TYPE_SITE_OPEN) &&
			 (_group.getType() != GroupConstants.TYPE_SITE_RESTRICTED))) {

			return false;
		}

		if (!GroupLocalServiceUtil.hasUserGroup(
				_themeDisplay.getUserId(), _group.getGroupId()) ||
			SiteMembershipPolicyUtil.isMembershipRequired(
				_themeDisplay.getUserId(), _group.getGroupId())) {

			return false;
		}

		return true;
	}

	private final Group _group;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private final SiteAdminDisplayContext _siteAdminDisplayContext;
	private final ThemeDisplay _themeDisplay;

}