/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.StagingConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserPersonalSite;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Represents either a site or a generic resource container.
 *
 * <p>
 * Groups are most used in Liferay as a resource container for permissioning and
 * content scoping purposes. For instance, an site is group, meaning that it can
 * contain layouts, web content, wiki entries, etc. However, a single layout can
 * also be a group containing its own unique set of resources. An example of
 * this would be a site that has several distinct wikis on different layouts.
 * Each of these layouts would have its own group, and all of the nodes in the
 * wiki for a certain layout would be associated with that layout's group. This
 * allows users to be given different permissions on each of the wikis, even
 * though they are all within the same site. In addition to sites and layouts,
 * users and organizations are also groups.
 * </p>
 *
 * <p>
 * Groups also have a second, partially conflicting purpose in Liferay. For
 * legacy reasons, groups are also the model used to represent sites (known as
 * communities before Liferay v6.1). Confusion may arise from the fact that a
 * site group is both the resource container and the site itself, whereas a
 * layout or organization would have both a primary model and an associated
 * group.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class GroupImpl extends GroupBaseImpl {

	public GroupImpl() {
	}

	@Override
	public String buildTreePath() throws PortalException, SystemException {
		StringBundler sb = new StringBundler();

		buildTreePath(sb, this);

		return sb.toString();
	}

	@Override
	public List<Group> getAncestors() throws PortalException, SystemException {
		Group group = null;

		if (isStagingGroup()) {
			group = getLiveGroup();
		}
		else {
			group = this;
		}

		List<Group> groups = new ArrayList<Group>();

		while (!group.isRoot()) {
			group = group.getParentGroup();

			groups.add(group);
		}

		return groups;
	}

	@Override
	public List<Group> getChildren(boolean site) throws SystemException {
		return GroupLocalServiceUtil.getGroups(
			getCompanyId(), getGroupId(), site);
	}

	@Override
	public List<Group> getChildrenWithLayouts(boolean site, int start, int end)
		throws SystemException {

		return GroupLocalServiceUtil.getLayoutsGroups(
			getCompanyId(), getGroupId(), site, start, end);
	}

	@Override
	public int getChildrenWithLayoutsCount(boolean site)
		throws SystemException {

		return GroupLocalServiceUtil.getLayoutsGroupsCount(
			getCompanyId(), getGroupId(), site);
	}

	@Override
	public long getDefaultPrivatePlid() {
		return getDefaultPlid(true);
	}

	@Override
	public long getDefaultPublicPlid() {
		return getDefaultPlid(false);
	}

	@Override
	public String getDescriptiveName() throws PortalException, SystemException {
		return getDescriptiveName(LocaleUtil.getDefault());
	}

	@Override
	public String getDescriptiveName(Locale locale)
		throws PortalException, SystemException {

		return GroupLocalServiceUtil.getGroupDescriptiveName(this, locale);
	}

	@Override
	public String getIconURL(ThemeDisplay themeDisplay) {
		String iconURL = themeDisplay.getPathThemeImages() + "/common/";

		if (isCompany()) {
			iconURL = iconURL.concat("global.png");
		}
		else if (isLayout()) {
			iconURL = iconURL.concat("page.png");
		}
		else if (isOrganization()) {
			iconURL = iconURL.concat("organization_icon.png");
		}
		else if (isUser()) {
			iconURL = iconURL.concat("user_icon.png");
		}
		else {
			iconURL = iconURL.concat("site_icon.png");
		}

		return iconURL;
	}

	@Override
	public Group getLiveGroup() {
		if (!isStagingGroup()) {
			return null;
		}

		try {
			if (_liveGroup == null) {
				_liveGroup = GroupLocalServiceUtil.getGroup(getLiveGroupId());
			}

			return _liveGroup;
		}
		catch (Exception e) {
			_log.error("Error getting live group for " + getLiveGroupId(), e);

			return null;
		}
	}

	@Override
	public String getLiveParentTypeSettingsProperty(String key) {
		UnicodeProperties typeSettingsProperties =
			getParentLiveGroupTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key);
	}

	@Override
	public long getOrganizationId() {
		if (isOrganization()) {
			if (isStagingGroup()) {
				Group liveGroup = getLiveGroup();

				return liveGroup.getClassPK();
			}
			else {
				return getClassPK();
			}
		}

		return 0;
	}

	@Override
	public Group getParentGroup() throws PortalException, SystemException {
		long parentGroupId = getParentGroupId();

		if (parentGroupId <= 0) {
			return null;
		}

		return GroupLocalServiceUtil.getGroup(parentGroupId);
	}

	@Override
	public UnicodeProperties getParentLiveGroupTypeSettingsProperties() {
		try {
			if (isLayout()) {
				Group parentGroup = GroupLocalServiceUtil.getGroup(
					getParentGroupId());

				return parentGroup.getParentLiveGroupTypeSettingsProperties();
			}

			if (isStagingGroup()) {
				Group liveGroup = getLiveGroup();

				return liveGroup.getTypeSettingsProperties();
			}
		}
		catch (Exception e) {
		}

		return getTypeSettingsProperties();
	}

	@Override
	public String getPathFriendlyURL(
		boolean privateLayout, ThemeDisplay themeDisplay) {

		if (privateLayout) {
			if (isUser()) {
				return themeDisplay.getPathFriendlyURLPrivateUser();
			}
			else {
				return themeDisplay.getPathFriendlyURLPrivateGroup();
			}
		}
		else {
			return themeDisplay.getPathFriendlyURLPublic();
		}
	}

	@Override
	public LayoutSet getPrivateLayoutSet() {
		LayoutSet layoutSet = null;

		try {
			layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				getGroupId(), true);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return layoutSet;
	}

	@Override
	public int getPrivateLayoutsPageCount() {
		try {
			return LayoutLocalServiceUtil.getLayoutsCount(this, true);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	@Override
	public LayoutSet getPublicLayoutSet() {
		LayoutSet layoutSet = null;

		try {
			layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				getGroupId(), false);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return layoutSet;
	}

	@Override
	public int getPublicLayoutsPageCount() {
		try {
			return LayoutLocalServiceUtil.getLayoutsCount(this, false);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	@Override
	public String getScopeDescriptiveName(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		if (getGroupId() == themeDisplay.getScopeGroupId()) {
			StringBundler sb = new StringBundler(5);

			sb.append(themeDisplay.translate("current-site"));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(
				HtmlUtil.escape(getDescriptiveName(themeDisplay.getLocale())));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}
		else if (isLayout() && (getClassPK() == themeDisplay.getPlid())) {
			StringBundler sb = new StringBundler(5);

			sb.append(themeDisplay.translate("current-page"));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(
				HtmlUtil.escape(getDescriptiveName(themeDisplay.getLocale())));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}
		else if (isLayoutPrototype()) {
			return themeDisplay.translate("default");
		}
		else {
			return HtmlUtil.escape(
				getDescriptiveName(themeDisplay.getLocale()));
		}
	}

	@Override
	public String getScopeLabel(ThemeDisplay themeDisplay) {
		String label = "site";

		if (getGroupId() == themeDisplay.getScopeGroupId()) {
			label = "current-site";
		}
		else if (getGroupId() == themeDisplay.getCompanyGroupId()) {
			label = "global";
		}
		else if (isLayout()) {
			label = "page";
		}
		else {
			Group scopeGroup = themeDisplay.getScopeGroup();

			if (scopeGroup.hasAncestor(getGroupId())) {
				label = "parent-site";
			}
			else if (hasAncestor(scopeGroup.getGroupId())) {
				label = "child-site";
			}
		}

		return label;
	}

	@Override
	public Group getStagingGroup() {
		if (isStagingGroup()) {
			return null;
		}

		try {
			if (_stagingGroup == null) {
				_stagingGroup = GroupLocalServiceUtil.getStagingGroup(
					getGroupId());
			}

			return _stagingGroup;
		}
		catch (Exception e) {
			_log.error("Error getting staging group for " + getGroupId(), e);

			return null;
		}
	}

	@Override
	public String getTypeLabel() {
		return GroupConstants.getTypeLabel(getType());
	}

	@Override
	public String getTypeSettings() {
		if (_typeSettingsProperties == null) {
			return super.getTypeSettings();
		}
		else {
			return _typeSettingsProperties.toString();
		}
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			try {
				_typeSettingsProperties.load(super.getTypeSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _typeSettingsProperties;
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key);
	}

	@Override
	public boolean hasAncestor(long groupId) {
		Group group = null;

		if (isStagingGroup()) {
			group = getLiveGroup();
		}
		else {
			group = this;
		}

		String treePath = group.getTreePath();

		if ((groupId != group.getGroupId()) &&
			treePath.contains(StringPool.SLASH + groupId + StringPool.SLASH)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasStagingGroup() {
		if (isStagingGroup()) {
			return false;
		}

		if (_stagingGroup != null) {
			return true;
		}

		try {
			return GroupLocalServiceUtil.hasStagingGroup(getGroupId());
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isRegularSite}
	 */
	@Override
	public boolean isCommunity() {
		return isRegularSite();
	}

	@Override
	public boolean isCompany() {
		return hasClassName(Company.class);
	}

	@Override
	public boolean isControlPanel() {
		String name = getName();

		if (name.equals(GroupConstants.CONTROL_PANEL)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isGuest() {
		String name = getName();

		if (name.equals(GroupConstants.GUEST)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isInStagingPortlet(String portletId) {
		Group liveGroup = getLiveGroup();

		if (liveGroup == null) {
			return false;
		}

		return liveGroup.isStagedPortlet(portletId);
	}

	@Override
	public boolean isLayout() {
		return hasClassName(Layout.class);
	}

	@Override
	public boolean isLayoutPrototype() {
		return hasClassName(LayoutPrototype.class);
	}

	@Override
	public boolean isLayoutSetPrototype() {
		return hasClassName(LayoutSetPrototype.class);
	}

	@Override
	public boolean isLimitedToParentSiteMembers() {
		if ((getParentGroupId() != GroupConstants.DEFAULT_PARENT_GROUP_ID) &&
			(getType() ==
				GroupConstants.TYPE_SITE_LIMITED_TO_PARENT_SITE_MEMBERS)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isOrganization() {
		return hasClassName(Organization.class);
	}

	@Override
	public boolean isRegularSite() {
		return hasClassName(Group.class);
	}

	@Override
	public boolean isRoot() {
		if (getParentGroupId() ==
				GroupConstants.DEFAULT_PARENT_GROUP_ID) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isShowSite(
			PermissionChecker permissionChecker, boolean privateSite)
		throws PortalException, SystemException {

		if (!isControlPanel() && !isSite() && !isUser()) {
			return false;
		}

		boolean showSite = true;

		Layout defaultLayout = null;

		int siteLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			this, true);

		if (siteLayoutsCount == 0) {
			boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(
				permissionChecker.getUserId(), permissionChecker.getCompanyId(),
				RoleConstants.POWER_USER, true);

			if (isSite()) {
				if (privateSite) {
					showSite =
						PropsValues.MY_SITES_SHOW_PRIVATE_SITES_WITH_NO_LAYOUTS;
				}
				else {
					showSite =
						PropsValues.MY_SITES_SHOW_PUBLIC_SITES_WITH_NO_LAYOUTS;
				}
			}
			else if (isOrganization()) {
				showSite = false;
			}
			else if (isUser()) {
				if (privateSite) {
					showSite =
						PropsValues.
							MY_SITES_SHOW_USER_PRIVATE_SITES_WITH_NO_LAYOUTS;

					if (PropsValues.
							LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED &&
						!hasPowerUserRole) {

						showSite = false;
					}
				}
				else {
					showSite =
						PropsValues.
							MY_SITES_SHOW_USER_PUBLIC_SITES_WITH_NO_LAYOUTS;

					if (PropsValues.
							LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED &&
						!hasPowerUserRole) {

						showSite = false;
					}
				}
			}
		}
		else {
			defaultLayout = LayoutLocalServiceUtil.fetchFirstLayout(
				getGroupId(), privateSite,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if ((defaultLayout != null ) &&
				!LayoutPermissionUtil.contains(
					permissionChecker, defaultLayout, true, ActionKeys.VIEW)) {

				showSite = false;
			}
			else if (isOrganization() && !isSite()) {
				_log.error(
					"Group " + getGroupId() +
						" is an organization site that does not have pages");
			}
		}

		return showSite;
	}

	@Override
	public boolean isStaged() {
		return GetterUtil.getBoolean(
			getLiveParentTypeSettingsProperty("staged"));
	}

	@Override
	public boolean isStagedPortlet(String portletId) {
		UnicodeProperties typeSettingsProperties =
			getParentLiveGroupTypeSettingsProperties();

		portletId = PortletConstants.getRootPortletId(portletId);

		String typeSettingsProperty = typeSettingsProperties.getProperty(
			StagingConstants.STAGED_PORTLET.concat(portletId));

		if (Validator.isNotNull(typeSettingsProperty)) {
			return GetterUtil.getBoolean(typeSettingsProperty);
		}

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

			String portletDataHandlerClass =
				portlet.getPortletDataHandlerClass();

			if (Validator.isNull(portletDataHandlerClass)) {
				return true;
			}

			for (Map.Entry<String, String> entry :
					typeSettingsProperties.entrySet()) {

				String key = entry.getKey();

				if (!key.contains(StagingConstants.STAGED_PORTLET)) {
					continue;
				}

				String stagedPortletId = StringUtil.replace(
					key, StagingConstants.STAGED_PORTLET, StringPool.BLANK);

				Portlet stagedPortlet = PortletLocalServiceUtil.getPortletById(
					stagedPortletId);

				if (portletDataHandlerClass.equals(
						stagedPortlet.getPortletDataHandlerClass())) {

					return GetterUtil.getBoolean(entry.getValue());
				}
			}
		}
		catch (Exception e) {
		}

		return true;
	}

	@Override
	public boolean isStagedRemotely() {
		return GetterUtil.getBoolean(
			getLiveParentTypeSettingsProperty("stagedRemotely"));
	}

	@Override
	public boolean isStagingGroup() {
		if (getLiveGroupId() == GroupConstants.DEFAULT_LIVE_GROUP_ID) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean isUser() {
		return hasClassName(User.class);
	}

	@Override
	public boolean isUserGroup() {
		return hasClassName(UserGroup.class);
	}

	@Override
	public boolean isUserPersonalSite() {
		return hasClassName(UserPersonalSite.class);
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		_typeSettingsProperties = null;

		super.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;

		super.setTypeSettings(_typeSettingsProperties.toString());
	}

	protected void buildTreePath(StringBundler sb, Group group)
		throws PortalException, SystemException {

		if (group == null) {
			sb.append(StringPool.SLASH);
		}
		else {
			buildTreePath(sb, group.getParentGroup());

			sb.append(group.getGroupId());
			sb.append(StringPool.SLASH);
		}
	}

	protected long getDefaultPlid(boolean privateLayout) {
		try {
			Layout firstLayout = LayoutLocalServiceUtil.fetchFirstLayout(
				getGroupId(), privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (firstLayout != null) {
				return firstLayout.getPlid();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	protected boolean hasClassName(Class<?> clazz) {
		long classNameId = getClassNameId();

		if (classNameId == PortalUtil.getClassNameId(clazz)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(GroupImpl.class);

	private Group _liveGroup;
	private Group _stagingGroup;
	private UnicodeProperties _typeSettingsProperties;

}