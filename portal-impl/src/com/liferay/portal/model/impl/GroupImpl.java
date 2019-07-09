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

package com.liferay.portal.model.impl;

import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupWrapper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserPersonalSite;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
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
@JSON(strict = true)
public class GroupImpl extends GroupBaseImpl {

	@Override
	public void clearStagingGroup() {
		_stagingGroup = null;
	}

	@Override
	public List<Group> getAncestors() {
		Group group = null;

		if (isStagingGroup()) {
			group = getLiveGroup();
		}
		else {
			group = this;
		}

		List<Group> groups = null;

		while (!group.isRoot()) {
			group = group.getParentGroup();

			if (groups == null) {
				groups = new ArrayList<>();
			}

			groups.add(group);
		}

		if (groups == null) {
			return Collections.emptyList();
		}

		return groups;
	}

	@Override
	public List<Group> getChildren(boolean site) {
		return GroupLocalServiceUtil.getGroups(
			getCompanyId(), getGroupId(), site);
	}

	@Override
	public List<Group> getChildrenWithLayouts(
		boolean site, int start, int end, OrderByComparator<Group> obc) {

		return GroupLocalServiceUtil.getLayoutsGroups(
			getCompanyId(), getGroupId(), site, start, end, obc);
	}

	@Override
	public int getChildrenWithLayoutsCount(boolean site) {
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
	public List<Group> getDescendants(boolean site) {
		return GroupLocalServiceUtil.getGroups(
			getCompanyId(), getTreePath().concat("_%"), site);
	}

	@JSON
	@Override
	public String getDescriptiveName() throws PortalException {
		return getDescriptiveName(LocaleUtil.getMostRelevantLocale());
	}

	@Override
	public String getDescriptiveName(Locale locale) throws PortalException {
		Group curGroup = this;

		String name = getName(locale);

		if (Validator.isNull(name)) {
			Locale siteDefaultLocale = PortalUtil.getSiteDefaultLocale(
				getGroupId());

			name = getName(siteDefaultLocale);
		}

		if (isCompany() && !isCompanyStagingGroup()) {
			name = LanguageUtil.get(locale, "global");
		}
		else if (isControlPanel()) {
			name = LanguageUtil.get(locale, "control-panel");
		}
		else if (isGuest()) {
			Company company = CompanyLocalServiceUtil.getCompany(
				getCompanyId());

			Account account = company.getAccount();

			name = account.getName();
		}
		else if (isLayout()) {
			Layout layout = LayoutLocalServiceUtil.getLayout(getClassPK());

			name = layout.getName(locale);
		}
		else if (isLayoutPrototype()) {
			LayoutPrototype layoutPrototype =
				LayoutPrototypeLocalServiceUtil.getLayoutPrototype(
					getClassPK());

			name = layoutPrototype.getName(locale);
		}
		else if (isLayoutSetPrototype()) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					getClassPK());

			name = layoutSetPrototype.getName(locale);
		}
		else if (isOrganization()) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(
					getOrganizationId());

			name = organization.getName();

			curGroup = organization.getGroup();
		}
		else if (isUser()) {
			long userId = getClassPK();

			User user = UserLocalServiceUtil.getUser(userId);

			name = user.getFullName();
		}
		else if (isUserGroup()) {
			long userGroupId = getClassPK();

			UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
				userGroupId);

			name = userGroup.getName();
		}
		else if (isUserPersonalSite()) {
			name = LanguageUtil.get(locale, "user-personal-site");
		}

		if (curGroup.isStaged() && !curGroup.isStagedRemotely() &&
			curGroup.isStagingGroup()) {

			Group liveGroup = getLiveGroup();

			name = liveGroup.getDescriptiveName(locale);
		}

		return name;
	}

	@Override
	public String getDisplayURL(ThemeDisplay themeDisplay) {
		return getDisplayURL(themeDisplay, false);
	}

	@Override
	public String getDisplayURL(
		ThemeDisplay themeDisplay, boolean privateLayout) {

		try {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				getGroupId(), privateLayout);

			if ((layoutSet.getPageCount() > 0) ||
				(isUser() &&
				 (LayoutLocalServiceUtil.getLayoutsCount(this, privateLayout) >
					 0))) {

				String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
					layoutSet, themeDisplay);

				if (isUser()) {
					return PortalUtil.addPreservedParameters(
						themeDisplay, groupFriendlyURL, false, true);
				}

				return PortalUtil.addPreservedParameters(
					themeDisplay, groupFriendlyURL);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return StringPool.BLANK;
	}

	@Override
	public String getIconCssClass() {
		String iconCss = "sites";

		if (isCompany()) {
			iconCss = "sites";
		}
		else if (isLayout()) {
			iconCss = "edit-layout";
		}
		else if (isOrganization()) {
			iconCss = "sites";
		}
		else if (isUser()) {
			iconCss = "user";
		}

		return iconCss;
	}

	@Override
	public String getIconURL(ThemeDisplay themeDisplay) {
		String iconURL = StringPool.BLANK;

		if (isCompany()) {
			iconURL = "../aui/globe";
		}
		else if (isLayout()) {
			iconURL = "../aui/file";
		}
		else if (isOrganization()) {
			iconURL = "../aui/globe";
		}
		else if (isUser()) {
			iconURL = "../aui/user";
		}
		else {
			iconURL = "../aui/globe";
		}

		return iconURL;
	}

	@Override
	public String getLayoutRootNodeName(boolean privateLayout, Locale locale) {
		String pagesName = null;

		if (isLayoutPrototype() || isLayoutSetPrototype()) {
			pagesName = "pages";
		}
		else if (privateLayout) {
			if (isUser() || isUserGroup()) {
				pagesName = "my-dashboard";
			}
			else {
				pagesName = "private-pages";
			}
		}
		else {
			if (isUser() || isUserGroup()) {
				pagesName = "my-profile";
			}
			else {
				pagesName = "public-pages";
			}
		}

		return LanguageUtil.get(locale, pagesName);
	}

	@Override
	public Group getLiveGroup() {
		if (!isStagingGroup()) {
			return null;
		}

		try {
			if (_liveGroup == null) {
				_liveGroup = GroupLocalServiceUtil.getGroup(getLiveGroupId());

				if (_liveGroup instanceof GroupImpl) {
					GroupImpl groupImpl = (GroupImpl)_liveGroup;

					groupImpl._stagingGroup = this;
				}
				else {
					_liveGroup = new GroupWrapper(_liveGroup) {

						@Override
						public Group getStagingGroup() {
							return GroupImpl.this;
						}

					};
				}
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
	public String getLogoURL(ThemeDisplay themeDisplay, boolean useDefault) {
		long logoId = 0;

		LayoutSet publicLayoutSet = getPublicLayoutSet();

		if (publicLayoutSet.getLogoId() > 0) {
			logoId = publicLayoutSet.getLogoId();
		}
		else {
			LayoutSet privateLayoutSet = getPrivateLayoutSet();

			if (privateLayoutSet.getLogoId() > 0) {
				logoId = privateLayoutSet.getLogoId();
			}
		}

		if ((logoId == 0) && !useDefault) {
			return null;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/layout_set_logo?img_id=");
		sb.append(logoId);
		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(logoId));

		return sb.toString();
	}

	@Override
	public long getOrganizationId() {
		if (isOrganization()) {
			if (isStagingGroup()) {
				Group liveGroup = getLiveGroup();

				return liveGroup.getClassPK();
			}

			return getClassPK();
		}

		return 0;
	}

	@Override
	public Group getParentGroup() {
		long parentGroupId = getParentGroupId();

		if (parentGroupId <= 0) {
			return null;
		}

		return GroupLocalServiceUtil.fetchGroup(parentGroupId);
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

			return themeDisplay.getPathFriendlyURLPrivateGroup();
		}

		return themeDisplay.getPathFriendlyURLPublic();
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
	public long getRemoteLiveGroupId() {
		if (!isStagedRemotely()) {
			return GroupConstants.DEFAULT_LIVE_GROUP_ID;
		}

		return GetterUtil.getLong(getTypeSettingsProperty("remoteGroupId"));
	}

	@Override
	public String getScopeDescriptiveName(ThemeDisplay themeDisplay)
		throws PortalException {

		if (getGroupId() == themeDisplay.getScopeGroupId()) {
			return StringUtil.appendParentheticalSuffix(
				themeDisplay.translate("current-site"),
				HtmlUtil.escape(getDescriptiveName(themeDisplay.getLocale())));
		}
		else if (isLayout() && (getClassPK() == themeDisplay.getPlid())) {
			return StringUtil.appendParentheticalSuffix(
				themeDisplay.translate("current-page"),
				HtmlUtil.escape(getDescriptiveName(themeDisplay.getLocale())));
		}
		else if (isLayoutPrototype()) {
			return themeDisplay.translate("default");
		}

		return HtmlUtil.escape(getDescriptiveName(themeDisplay.getLocale()));
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
			if ((_stagingGroup == null) ||
				(_stagingGroup == _NULL_STAGING_GROUP)) {

				_stagingGroup = GroupLocalServiceUtil.getStagingGroup(
					getGroupId());

				if (_stagingGroup instanceof GroupImpl) {
					GroupImpl groupImpl = (GroupImpl)_stagingGroup;

					groupImpl._liveGroup = this;
				}
				else {
					_stagingGroup = new GroupWrapper(_stagingGroup) {

						@Override
						public Group getLiveGroup() {
							return GroupImpl.this;
						}

					};
				}
			}

			return _stagingGroup;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get staging group for group " + getGroupId(), e);
			}

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

		return _typeSettingsProperties.toString();
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
	public String getUnambiguousName(String name, Locale locale) {
		try {
			return StringUtil.appendParentheticalSuffix(
				name, getDescriptiveName(locale));
		}
		catch (Exception e) {
			return name;
		}
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
	public boolean hasLocalOrRemoteStagingGroup() {
		if (hasRemoteStagingGroup() || hasStagingGroup()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasRemoteStagingGroup() {
		if (getRemoteStagingGroupCount() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasStagingGroup() {
		if (isStagingGroup() || (_stagingGroup == _NULL_STAGING_GROUP)) {
			return false;
		}

		if (_stagingGroup != null) {
			return true;
		}

		Group stagingGroup = GroupLocalServiceUtil.fetchStagingGroup(
			getGroupId());

		if (stagingGroup == null) {
			_stagingGroup = _NULL_STAGING_GROUP;

			return false;
		}

		_stagingGroup = stagingGroup;

		return true;
	}

	@Override
	public boolean isCompany() {
		if ((getClassNameId() == ClassNameIds._COMPANY_CLASS_NAME_ID) ||
			isCompanyStagingGroup()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isCompanyStagingGroup() {
		Group liveGroup = getLiveGroup();

		if (liveGroup == null) {
			return false;
		}

		return liveGroup.isCompany();
	}

	@Override
	public boolean isControlPanel() {
		String groupKey = getGroupKey();

		if (groupKey.equals(GroupConstants.CONTROL_PANEL)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isGuest() {
		String groupKey = getGroupKey();

		if (groupKey.equals(GroupConstants.GUEST)) {
			return true;
		}

		return false;
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
		if (getClassNameId() == ClassNameIds._LAYOUT_CLASS_NAME_ID) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLayoutPrototype() {
		if (getClassNameId() == ClassNameIds._LAYOUT_PROTOTYPE_CLASS_NAME_ID) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLayoutSetPrototype() {
		if (getClassNameId() ==
				ClassNameIds._LAYOUT_SET_PROTOTYPE_CLASS_NAME_ID) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isLimitedToParentSiteMembers() {
		if ((getParentGroupId() != GroupConstants.DEFAULT_PARENT_GROUP_ID) &&
			(getMembershipRestriction() ==
				GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isOrganization() {
		if (getClassNameId() == ClassNameIds._ORGANIZATION_CLASS_NAME_ID) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRegularSite() {
		if (getClassNameId() == ClassNameIds._GROUP_CLASS_NAME_ID) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoot() {
		if (getParentGroupId() == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isShowSite(
			PermissionChecker permissionChecker, boolean privateSite)
		throws PortalException {

		if (!isControlPanel() && !isSite() && !isUser()) {
			return false;
		}

		boolean showSite = true;

		int siteLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			this, privateSite);

		if (siteLayoutsCount == 0) {
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
				boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(
					permissionChecker.getUserId(),
					permissionChecker.getCompanyId(), RoleConstants.POWER_USER,
					true);

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
			Layout defaultLayout = LayoutLocalServiceUtil.fetchFirstLayout(
				getGroupId(), privateSite,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if ((defaultLayout != null) &&
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

		portletId = PortletIdCodec.decodePortletName(portletId);

		String typeSettingsProperty = typeSettingsProperties.getProperty(
			StagingUtil.getStagedPortletId(portletId));

		if (Validator.isNotNull(typeSettingsProperty)) {
			return GetterUtil.getBoolean(typeSettingsProperty);
		}

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if (portletDataHandler == null) {
				return false;
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

				if (stagedPortlet == null) {
					continue;
				}

				PortletDataHandler stagedPortletDataHandler =
					stagedPortlet.getPortletDataHandlerInstance();

				if (stagedPortletDataHandler == null) {
					continue;
				}

				String serviceName = portletDataHandler.getServiceName();

				if (serviceName == null) {
					continue;
				}

				if (serviceName.equals(
						stagedPortletDataHandler.getServiceName())) {

					return GetterUtil.getBoolean(entry.getValue());
				}
			}

			return portletDataHandler.isStaged();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
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

		return true;
	}

	@Override
	public boolean isUser() {
		if (getClassNameId() == ClassNameIds._USER_CLASS_NAME_ID) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isUserGroup() {
		if (getClassNameId() == ClassNameIds._USER_GROUP_CLASS_NAME_ID) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isUserPersonalSite() {
		if (getClassNameId() ==
				ClassNameIds._USER_PERSONAL_SITE_CLASS_NAME_ID) {

			return true;
		}

		return false;
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

	private static final Group _NULL_STAGING_GROUP = new GroupImpl();

	private static final Log _log = LogFactoryUtil.getLog(GroupImpl.class);

	private Group _liveGroup;
	private Group _stagingGroup;
	private UnicodeProperties _typeSettingsProperties;

	private static class ClassNameIds {

		private ClassNameIds() {
		}

		private static final long _COMPANY_CLASS_NAME_ID =
			PortalUtil.getClassNameId(Company.class);

		private static final long _GROUP_CLASS_NAME_ID =
			PortalUtil.getClassNameId(Group.class);

		private static final long _LAYOUT_CLASS_NAME_ID =
			PortalUtil.getClassNameId(Layout.class);

		private static final long _LAYOUT_PROTOTYPE_CLASS_NAME_ID =
			PortalUtil.getClassNameId(LayoutPrototype.class);

		private static final long _LAYOUT_SET_PROTOTYPE_CLASS_NAME_ID =
			PortalUtil.getClassNameId(LayoutSetPrototype.class);

		private static final long _ORGANIZATION_CLASS_NAME_ID =
			PortalUtil.getClassNameId(Organization.class);

		private static final long _USER_CLASS_NAME_ID =
			PortalUtil.getClassNameId(User.class);

		private static final long _USER_GROUP_CLASS_NAME_ID =
			PortalUtil.getClassNameId(UserGroup.class);

		private static final long _USER_PERSONAL_SITE_CLASS_NAME_ID =
			PortalUtil.getClassNameId(UserPersonalSite.class);

	}

}