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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.Account;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author José Manuel Navarro
 */
@ProviderType
public class BreadcrumbUtil {

	public static BreadcrumbEntry getCurrentGroupBreadcrumbEntry(
			ThemeDisplay themeDisplay)
		throws Exception {

		List<BreadcrumbEntry> entries = new ArrayList<BreadcrumbEntry>();

		_addGroupsBreadcrumbEntries(
			entries, themeDisplay, themeDisplay.getLayout().getLayoutSet(),
			false);

		BreadcrumbEntry entry = null;

		if (entries.size() > 0) {
			entry = entries.get(0);
		}

		return entry;
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

		BreadcrumbEntry entry = new BreadcrumbEntry();

		// Entry URL

		String layoutSetFriendlyURL = PortalUtil.getLayoutSetFriendlyURL(
			layoutSet, themeDisplay);

		if (themeDisplay.isAddSessionIdToURL()) {
			layoutSetFriendlyURL = PortalUtil.getURLWithSessionId(
				layoutSetFriendlyURL, themeDisplay.getSessionId());
		}

		entry.setURL(layoutSetFriendlyURL);

		// Entry title

		Account account = themeDisplay.getAccount();

		entry.setTitle(account.getName());

		return entry;
	}

	public static List<BreadcrumbEntry> getLayoutBreadcrumbEntries(
			ThemeDisplay themeDisplay)
		throws Exception {

		List<BreadcrumbEntry> entries = new ArrayList<BreadcrumbEntry>();

		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		if (!group.isLayoutPrototype()) {
			_addLayoutBreadcrumbEntries(entries, themeDisplay, layout);
		}

		return entries;
	}

	public static List<BreadcrumbEntry> getParentGroupBreadcrumbEntries(
			ThemeDisplay themeDisplay)
		throws Exception {

		List<BreadcrumbEntry> entries = new ArrayList<BreadcrumbEntry>();

		LayoutSet parentLayoutSet = _getParentLayoutSet(
			themeDisplay.getLayout().getLayoutSet());

		if (parentLayoutSet != null) {
			_addGroupsBreadcrumbEntries(
				entries, themeDisplay, parentLayoutSet, true);
		}

		return entries;
	}

	public static List<BreadcrumbEntry> getPortletBreadcrumbEntries(
		HttpServletRequest request) {

		List<BreadcrumbEntry> entries = new ArrayList<BreadcrumbEntry>();

		List<BreadcrumbEntry> portletBreadcrumbEntries =
			PortalUtil.getPortletBreadcrumbs(request);

		if (portletBreadcrumbEntries == null) {
			return entries;
		}

		for (int i = 0; i < portletBreadcrumbEntries.size(); i++) {
			BreadcrumbEntry portletBreadcrumbEntry =
				portletBreadcrumbEntries.get(i);

			BreadcrumbEntry entry = new BreadcrumbEntry();

			String portletBreadcrumbURL = portletBreadcrumbEntry.getURL();

			boolean isLastEntry = (i == (portletBreadcrumbEntries.size() - 1));

			if (Validator.isNotNull(portletBreadcrumbURL) && !isLastEntry) {
				if (!CookieKeys.hasSessionId(request)) {
					HttpSession session = request.getSession();

					portletBreadcrumbURL = PortalUtil.getURLWithSessionId(
						portletBreadcrumbURL, session.getId());
				}

				entry.setURL(portletBreadcrumbURL);
			}

			entry.setData(portletBreadcrumbEntry.getData());
			entry.setEntity(portletBreadcrumbEntry.getEntity());
			entry.setTitle(portletBreadcrumbEntry.getTitle());

			entries.add(entry);
		}

		return entries;
	}

	private static void _addGroupsBreadcrumbEntries(
			List<BreadcrumbEntry> entries, ThemeDisplay themeDisplay,
			LayoutSet layoutSet, boolean includeParentGroups)
		throws Exception {

		Group group = layoutSet.getGroup();

		if (group.isControlPanel()) {
			return;
		}

		BreadcrumbEntry entry = null;

		if (includeParentGroups) {
			LayoutSet parentLayoutSet = _getParentLayoutSet(layoutSet);

			if (parentLayoutSet != null) {
				_addGroupsBreadcrumbEntries(
					entries, themeDisplay, parentLayoutSet, true);
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

			entry = new BreadcrumbEntry();

			entry.setURL(layoutSetFriendlyURL);
			entry.setTitle(group.getDescriptiveName());

			entries.add(entry);
		}
	}

	private static void _addLayoutBreadcrumbEntries(
			List<BreadcrumbEntry> entries, ThemeDisplay themeDisplay,
			Layout layout)
		throws Exception {

		if (layout.getParentLayoutId() !=
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			Layout parentLayout = LayoutLocalServiceUtil.getParentLayout(
				layout);

			_addLayoutBreadcrumbEntries(entries, themeDisplay, parentLayout);
		}

		BreadcrumbEntry entry = new BreadcrumbEntry();

		// Entry entity

		entry.setEntity(layout);

		// Entry URL

		String layoutURL = PortalUtil.getLayoutFullURL(layout, themeDisplay);

		if (themeDisplay.isAddSessionIdToURL()) {
			layoutURL = PortalUtil.getURLWithSessionId(
				layoutURL, themeDisplay.getSessionId());
		}

		if (layout.isTypeControlPanel()) {
			layoutURL = HttpUtil.removeParameter(
				layoutURL, "controlPanelCategory");
		}

		entry.setURL(layoutURL);

		// Entry title

		String layoutName = layout.getName(themeDisplay.getLocale());

		if (layout.isTypeControlPanel()) {
			if (layoutName.equals(LayoutConstants.NAME_CONTROL_PANEL_DEFAULT)) {
				layoutName = LanguageUtil.get(
					themeDisplay.getLocale(), "control-panel");
			}
		}

		entry.setTitle(layoutName);

		entries.add(entry);
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