/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.taglib.servlet.taglib.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.taglib.internal.servlet.ServletContextUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
			breadcrumbEntries.addAll(
				getLayoutBreadcrumbEntries(httpServletRequest, themeDisplay));
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

		Company company = themeDisplay.getCompany();

		breadcrumbEntry.setTitle(company.getName());

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
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		if (!group.isLayoutPrototype()) {
			_addLayoutBreadcrumbEntries(
				breadcrumbEntries, httpServletRequest, layout, themeDisplay);
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
				!CookiesManagerUtil.hasSessionId(httpServletRequest)) {

				HttpSession httpSession = httpServletRequest.getSession();

				portletBreadcrumbEntry.setURL(
					PortalUtil.getURLWithSessionId(url, httpSession.getId()));
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

		if (group.isControlPanel() || group.isDepot()) {
			return;
		}

		if (includeParentGroups) {
			LayoutSet parentLayoutSet = _getParentLayoutSet(layoutSet);

			if (parentLayoutSet != null) {
				_addGroupsBreadcrumbEntries(
					breadcrumbEntries, themeDisplay, parentLayoutSet, true);
			}
		}

		if (_isGuestGroup(group)) {
			return;
		}

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			group.getDescriptiveName(themeDisplay.getLocale()));

		int layoutsPageCount = 0;

		if (layoutSet.isPrivateLayout()) {
			layoutsPageCount = LayoutServiceUtil.getLayoutsCount(
				group.getGroupId(), true);
		}
		else {
			layoutsPageCount = LayoutServiceUtil.getLayoutsCount(
				group.getGroupId(), false);
		}

		if (layoutsPageCount <= 0) {
			breadcrumbEntries.add(breadcrumbEntry);

			return;
		}

		if (group.isActive() && _hasViewPermissions(group, themeDisplay)) {
			String layoutSetFriendlyURL = PortalUtil.getLayoutSetFriendlyURL(
				layoutSet, themeDisplay);

			if (themeDisplay.isAddSessionIdToURL()) {
				layoutSetFriendlyURL = PortalUtil.getURLWithSessionId(
					layoutSetFriendlyURL, themeDisplay.getSessionId());
			}

			breadcrumbEntry.setURL(layoutSetFriendlyURL);
		}
		else {
			breadcrumbEntry.setBrowsable(false);
		}

		breadcrumbEntries.add(breadcrumbEntry);
	}

	private static void _addLayoutBreadcrumbEntries(
			List<BreadcrumbEntry> breadcrumbEntries,
			HttpServletRequest httpServletRequest, Layout layout,
			ThemeDisplay themeDisplay)
		throws Exception {

		if (layout.getParentLayoutId() !=
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			_addLayoutBreadcrumbEntries(
				breadcrumbEntries, httpServletRequest,
				LayoutLocalServiceUtil.getParentLayout(layout), themeDisplay);
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

		if (layout.isTypeAssetDisplay()) {
			String infoItemName = _getInfoItemName(
				httpServletRequest, themeDisplay.getLocale());

			if (Validator.isNotNull(infoItemName)) {
				layoutName = infoItemName;
			}
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

	private static String _getInfoItemName(
		HttpServletRequest httpServletRequest, Locale locale) {

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider != null) {
			InfoItemServiceRegistry infoItemServiceRegistry =
				ServletContextUtil.getInfoItemServiceRegistry();

			InfoItemFieldValuesProvider infoItemFieldValuesProvider =
				infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					layoutDisplayPageObjectProvider.getClassName());

			InfoItemFieldValues infoItemFieldValues =
				infoItemFieldValuesProvider.getInfoItemFieldValues(
					layoutDisplayPageObjectProvider.getDisplayObject());

			InfoFieldValue<Object> titleInfoFieldValue =
				infoItemFieldValues.getInfoFieldValue("title");

			if (titleInfoFieldValue != null) {
				return String.valueOf(titleInfoFieldValue.getValue(locale));
			}

			InfoFieldValue<Object> nameInfoFieldValue =
				infoItemFieldValues.getInfoFieldValue("name");

			if (nameInfoFieldValue != null) {
				return String.valueOf(nameInfoFieldValue.getValue(locale));
			}
		}

		AssetEntry assetEntry = (AssetEntry)httpServletRequest.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);

		if (assetEntry != null) {
			return assetEntry.getTitle(locale);
		}

		return StringPool.BLANK;
	}

	private static LayoutSet _getParentLayoutSet(LayoutSet layoutSet)
		throws Exception {

		Group group = layoutSet.getGroup();

		if (group.isSite()) {
			Group parentGroup = group.getParentGroup();

			if (parentGroup != null) {
				return LayoutSetLocalServiceUtil.fetchLayoutSet(
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

				return LayoutSetLocalServiceUtil.fetchLayoutSet(
					parentGroup.getGroupId(), layoutSet.isPrivateLayout());
			}
		}

		return null;
	}

	private static boolean _hasViewPermissions(
		Group group, ThemeDisplay themeDisplay) {

		try {
			if (GroupPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), group,
					ActionKeys.VIEW)) {

				return true;
			}

			return false;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return false;
		}
	}

	private static boolean _isGuestGroup(Group group) {
		if (group.isGuest()) {
			return true;
		}

		if (group.isStaged()) {
			Group liveGroup = group.getLiveGroup();

			if (liveGroup != null) {
				String groupKey = liveGroup.getGroupKey();

				if (groupKey.equals(GroupConstants.GUEST)) {
					return true;
				}
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(BreadcrumbUtil.class);

}