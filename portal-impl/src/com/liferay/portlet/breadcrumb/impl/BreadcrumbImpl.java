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

package com.liferay.portlet.breadcrumb.impl;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
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
import com.liferay.portlet.breadcrumb.Breadcrumb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author José Manuel Navarro
 */
public class BreadcrumbImpl implements Breadcrumb {

	@Override
	public BreadcrumbEntry getCurrentGroupEntry(ThemeDisplay themeDisplay)
		throws Exception {

		List<BreadcrumbEntry> entries = new ArrayList<BreadcrumbEntry>();

		_addGroupsEntries(
			entries, themeDisplay, themeDisplay.getLayout().getLayoutSet(),
			false);

		BreadcrumbEntry entry = null;

		if (entries.size() > 0) {
			entry = entries.get(0);
		}

		return entry;
	}

	@Override
	public BreadcrumbEntry getGuestGroupEntry(ThemeDisplay themeDisplay)
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

	@Override
	public List<BreadcrumbEntry> getLayoutEntries(ThemeDisplay themeDisplay)
		throws Exception {

		List<BreadcrumbEntry> entries = new ArrayList<BreadcrumbEntry>();

		Layout selLayout = themeDisplay.getLayout();
		Group group = selLayout.getGroup();

		if (!group.isLayoutPrototype()) {
			_addLayoutEntries(entries, themeDisplay, selLayout);
		}

		return entries;
	}

	@Override
	public List<BreadcrumbEntry> getParentGroupEntries(
			ThemeDisplay themeDisplay)
		throws Exception {

		List<BreadcrumbEntry> entries = new ArrayList<BreadcrumbEntry>();

		LayoutSet parentLayoutSet = _getParentLayoutSet(
			themeDisplay.getLayout().getLayoutSet());

		if (parentLayoutSet != null) {
			_addGroupsEntries(entries, themeDisplay, parentLayoutSet, true);
		}

		return entries;
	}

	@Override
	public List<BreadcrumbEntry> getPortletEntries(HttpServletRequest request) {
		List<BreadcrumbEntry> entries = new ArrayList<BreadcrumbEntry>();

		List<BreadcrumbEntry> portletBreadcrumbEntries =
			PortalUtil.getPortletBreadcrumbs(request);

		if (portletBreadcrumbEntries == null) {
			return entries;
		}

		for (int i = 0; i < portletBreadcrumbEntries.size(); i++) {
			BreadcrumbEntry portletBreadcrumbEntry =
				portletBreadcrumbEntries.get(i);

			BreadcrumbEntry newEntry = new BreadcrumbEntry();

			String portletBreadcrumbURL = portletBreadcrumbEntry.getURL();

			boolean isLastEntry = (i == (portletBreadcrumbEntries.size() - 1));

			if (Validator.isNotNull(portletBreadcrumbURL) && !isLastEntry) {
				if (!CookieKeys.hasSessionId(request)) {
					HttpSession session = request.getSession();

					portletBreadcrumbURL = PortalUtil.getURLWithSessionId(
						portletBreadcrumbURL, session.getId());
				}

				newEntry.setURL(portletBreadcrumbEntry.getURL());
				newEntry.setData(portletBreadcrumbEntry.getData());
			}

			newEntry.setTitle(portletBreadcrumbEntry.getTitle());

			entries.add(newEntry);
		}

		return entries;
	}

	private void _addGroupsEntries(
			List<BreadcrumbEntry> entries, ThemeDisplay themeDisplay,
			LayoutSet layoutSet, boolean includeParentGroups)
		throws Exception {

		Group group = layoutSet.getGroup();

		if (group.isControlPanel()) {
			return;
		}

		BreadcrumbEntry newEntry = null;

		if (includeParentGroups) {
			LayoutSet parentLayoutSet = _getParentLayoutSet(layoutSet);

			if (parentLayoutSet != null) {
				_addGroupsEntries(entries, themeDisplay, parentLayoutSet, true);
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

			newEntry = new BreadcrumbEntry();

			newEntry.setURL(layoutSetFriendlyURL);
			newEntry.setTitle(group.getDescriptiveName());

			entries.add(newEntry);
		}
	}

	private void _addLayoutEntries(
			List<BreadcrumbEntry> entries, ThemeDisplay themeDisplay,
			Layout selLayout)
		throws Exception {

		if (selLayout.getParentLayoutId() !=
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			Layout parentLayout = LayoutLocalServiceUtil.getParentLayout(
				selLayout);

			_addLayoutEntries(entries, themeDisplay, parentLayout);
		}

		BreadcrumbEntry newEntry = new BreadcrumbEntry();

		// Entry additional data

		Map<String, Object> entryData = new HashMap<String, Object>();
		entryData.put("entity", selLayout);
		newEntry.setData(entryData);

		// Entry URL

		String layoutURL = PortalUtil.getLayoutFullURL(selLayout, themeDisplay);

		if (themeDisplay.isAddSessionIdToURL()) {
			layoutURL = PortalUtil.getURLWithSessionId(
				layoutURL, themeDisplay.getSessionId());
		}

		if (selLayout.isTypeControlPanel()) {
			layoutURL = HttpUtil.removeParameter(
				layoutURL, "controlPanelCategory");
		}

		newEntry.setURL(layoutURL);

		// Entry title

		String layoutName = selLayout.getName(themeDisplay.getLocale());

		if (selLayout.isTypeControlPanel()) {
			if (layoutName.equals(LayoutConstants.NAME_CONTROL_PANEL_DEFAULT)) {
				layoutName = LanguageUtil.get(
					themeDisplay.getLocale(), "control-panel");
			}
		}

		newEntry.setTitle(layoutName);

		entries.add(newEntry);
	}

	private LayoutSet _getParentLayoutSet(LayoutSet layoutSet)
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