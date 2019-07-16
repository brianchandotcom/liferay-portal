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

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author José Manuel Navarro
 */
public class BreadcrumbUtil {

	public static final int ENTRY_TYPE_ANY = 0;

	public static final int ENTRY_TYPE_CURRENT_GROUP = 1;

	public static final int ENTRY_TYPE_GUEST_GROUP = 2;

	public static final int ENTRY_TYPE_LAYOUT = 3;

	public static final int ENTRY_TYPE_PARENT_GROUP = 4;

	public static final int ENTRY_TYPE_PORTLET = 5;

	public static List<BreadcrumbEntry> getBreadcrumbEntries(
			HttpServletRequest httpServletRequest, int[] types)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		boolean hasAll = ArrayUtil.contains(types, ENTRY_TYPE_ANY);

		if (hasAll || ArrayUtil.contains(types, ENTRY_TYPE_GUEST_GROUP)) {
			BreadcrumbEntry breadcrumbEntry = getGuestGroupBreadcrumbEntry(
				themeDisplay);

			if (breadcrumbEntry != null) {
				breadcrumbEntries.add(breadcrumbEntry);
			}
		}

		if (hasAll || ArrayUtil.contains(types, ENTRY_TYPE_PARENT_GROUP)) {
			breadcrumbEntries.addAll(
				getParentGroupBreadcrumbEntries(themeDisplay));
		}

		if (hasAll || ArrayUtil.contains(types, ENTRY_TYPE_CURRENT_GROUP)) {
			BreadcrumbEntry breadcrumbEntry = getScopeGroupBreadcrumbEntry(
				themeDisplay);

			if (breadcrumbEntry != null) {
				breadcrumbEntries.add(breadcrumbEntry);
			}
		}

		if (hasAll || ArrayUtil.contains(types, ENTRY_TYPE_LAYOUT)) {
			breadcrumbEntries.addAll(getLayoutBreadcrumbEntries(themeDisplay));
		}

		if (hasAll || ArrayUtil.contains(types, ENTRY_TYPE_PORTLET)) {
			breadcrumbEntries.addAll(
				getPortletBreadcrumbEntries(httpServletRequest));
		}

		return breadcrumbEntries;
	}

	public static BreadcrumbEntry getGuestGroupBreadcrumbEntry(
			ThemeDisplay themeDisplay)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			themeDisplay.getCompanyId(), GroupConstants.GUEST);

		if (group.getPublicLayoutsPageCount() == 0) {
			return null;
		}

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			group.getGroupId(), false);

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		Account account = themeDisplay.getAccount();

		breadcrumbEntry.setTitle(account.getName());

		String layoutSetFriendlyURL = PortalUtil.getLayoutSetFriendlyURL(
			layoutSet, themeDisplay);

		if (themeDisplay.isAddSessionIdToURL()) {
			layoutSetFriendlyURL = PortalUtil.getURLWithSessionId(
				layoutSetFriendlyURL, themeDisplay.getSessionId());
		}

		breadcrumbEntry.setURL(layoutSetFriendlyURL);

		return breadcrumbEntry;
	}

	public static List<BreadcrumbEntry> getLayoutBreadcrumbEntries(
			ThemeDisplay themeDisplay)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		if (!group.isLayoutPrototype()) {
			_addLayoutBreadcrumbEntries(
				breadcrumbEntries, themeDisplay, layout);
		}

		return breadcrumbEntries;
	}

	public static List<BreadcrumbEntry> getParentGroupBreadcrumbEntries(
			ThemeDisplay themeDisplay)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		Layout layout = themeDisplay.getLayout();

		LayoutSet parentLayoutSet = _getParentLayoutSet(layout.getLayoutSet());

		if (parentLayoutSet != null) {
			_addGroupsBreadcrumbEntries(
				breadcrumbEntries, themeDisplay, parentLayoutSet, true);
		}

		return breadcrumbEntries;
	}

	public static List<BreadcrumbEntry> getPortletBreadcrumbEntries(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String name = WebKeys.PORTLET_BREADCRUMBS;

		List<BreadcrumbEntry> breadcrumbEntries =
			(List<BreadcrumbEntry>)httpServletRequest.getAttribute(name);

		if (Validator.isNotNull(portletDisplay.getId()) &&
			!portletDisplay.isFocused() &&
			!Objects.equals(
				portletDisplay.getId(),
				PortletProviderUtil.getPortletId(
					BreadcrumbEntry.class.getName(),
					PortletProvider.Action.VIEW))) {

			name = name.concat(
				StringPool.UNDERLINE.concat(portletDisplay.getId()));

			List<BreadcrumbEntry> portletBreadcrumbEntries =
				(List<BreadcrumbEntry>)httpServletRequest.getAttribute(name);

			if (portletBreadcrumbEntries != null) {
				breadcrumbEntries = portletBreadcrumbEntries;
			}
		}

		if (breadcrumbEntries == null) {
			return Collections.emptyList();
		}

		for (int i = 0; i < (breadcrumbEntries.size() - 1); i++) {
			BreadcrumbEntry portletBreadcrumbEntry = breadcrumbEntries.get(i);

			String url = portletBreadcrumbEntry.getURL();

			if (Validator.isNotNull(url) &&
				!CookieKeys.hasSessionId(httpServletRequest)) {

				HttpSession session = httpServletRequest.getSession();

				portletBreadcrumbEntry.setURL(
					PortalUtil.getURLWithSessionId(url, session.getId()));
			}
		}

		return breadcrumbEntries;
	}

	public static BreadcrumbEntry getScopeGroupBreadcrumbEntry(
			ThemeDisplay themeDisplay)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		Layout layout = themeDisplay.getLayout();

		_addGroupsBreadcrumbEntries(
			breadcrumbEntries, themeDisplay, layout.getLayoutSet(), false);

		if (breadcrumbEntries.isEmpty()) {
			return null;
		}

		return breadcrumbEntries.get(0);
	}

	private static void _addGroupsBreadcrumbEntries(
			List<BreadcrumbEntry> breadcrumbEntries, ThemeDisplay themeDisplay,
			LayoutSet layoutSet, boolean includeParentGroups)
		throws Exception {

		Group group = layoutSet.getGroup();

		if (group.isControlPanel()) {
			return;
		}

		if (includeParentGroups) {
			LayoutSet parentLayoutSet = _getParentLayoutSet(layoutSet);

			if (parentLayoutSet != null) {
				_addGroupsBreadcrumbEntries(
					breadcrumbEntries, themeDisplay, parentLayoutSet, true);
			}
		}

		int layoutsPageCount = 0;

		if (layoutSet.isPrivateLayout()) {
			layoutsPageCount = group.getPrivateLayoutsPageCount();
		}
		else {
			layoutsPageCount = group.getPublicLayoutsPageCount();
		}

		if ((layoutsPageCount > 0) && !group.isGuest()) {
			String layoutSetFriendlyURL = PortalUtil.getLayoutSetFriendlyURL(
				layoutSet, themeDisplay);

			if (themeDisplay.isAddSessionIdToURL()) {
				layoutSetFriendlyURL = PortalUtil.getURLWithSessionId(
					layoutSetFriendlyURL, themeDisplay.getSessionId());
			}

			BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

			breadcrumbEntry.setTitle(
				group.getDescriptiveName(themeDisplay.getLocale()));
			breadcrumbEntry.setURL(layoutSetFriendlyURL);

			breadcrumbEntries.add(breadcrumbEntry);
		}
	}

	private static void _addLayoutBreadcrumbEntries(
			List<BreadcrumbEntry> breadcrumbEntries, ThemeDisplay themeDisplay,
			Layout layout)
		throws Exception {

		if (layout.getParentLayoutId() !=
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			_addLayoutBreadcrumbEntries(
				breadcrumbEntries, themeDisplay,
				LayoutLocalServiceUtil.getParentLayout(layout));
		}

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setBaseModel(layout);

		LayoutType layoutType = layout.getLayoutType();

		if (!layoutType.isBrowsable()) {
			breadcrumbEntry.setBrowsable(false);
		}

		String layoutName = layout.getName(themeDisplay.getLocale());

		if (layout.isTypeControlPanel() &&
			layoutName.equals(LayoutConstants.NAME_CONTROL_PANEL_DEFAULT)) {

			layoutName = LanguageUtil.get(
				themeDisplay.getLocale(), "control-panel");
		}

		breadcrumbEntry.setTitle(layoutName);

		String layoutURL = PortalUtil.getLayoutFullURL(layout, themeDisplay);

		if (themeDisplay.isAddSessionIdToURL()) {
			layoutURL = PortalUtil.getURLWithSessionId(
				layoutURL, themeDisplay.getSessionId());
		}

		breadcrumbEntry.setURL(layoutURL);

		breadcrumbEntries.add(breadcrumbEntry);
	}

	private static LayoutSet _getParentLayoutSet(LayoutSet layoutSet)
		throws Exception {

		Group group = layoutSet.getGroup();

		if (group.isSite()) {
			Group parentGroup = group.getParentGroup();

			if (parentGroup != null) {
				return LayoutSetLocalServiceUtil.getLayoutSet(
					parentGroup.getGroupId(), layoutSet.isPrivateLayout());
			}
		}
		else if (group.isUser()) {
			User user = UserLocalServiceUtil.getUser(group.getClassPK());

			List<Organization> organizations =
				OrganizationLocalServiceUtil.getUserOrganizations(
					user.getUserId());

			if (!organizations.isEmpty()) {
				Organization organization = organizations.get(0);

				Group parentGroup = organization.getGroup();

				return LayoutSetLocalServiceUtil.getLayoutSet(
					parentGroup.getGroupId(), layoutSet.isPrivateLayout());
			}
		}

		return null;
	}

}