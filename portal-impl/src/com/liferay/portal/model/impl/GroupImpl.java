/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.StagingConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
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
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserPersonalSite;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

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

	public long getDefaultPrivatePlid() {
		return getDefaultPlid(true);
	}

	public long getDefaultPublicPlid() {
		return getDefaultPlid(false);
	}

	public String getDescriptiveName() throws PortalException, SystemException {
		return getDescriptiveName(LocaleUtil.getDefault());
	}

	public String getDescriptiveName(Locale locale)
		throws PortalException, SystemException {

		String name = getName();

		if (isCompany()) {
			name = LanguageUtil.get(locale, "global");
		}
		else if (isControlPanel()) {
			name = LanguageUtil.get(locale, "control-panel");
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
			long organizationId = getOrganizationId();

			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(organizationId);

			name = organization.getName();

			Group group = organization.getGroup();

			if (group.isStaged() && isStagingGroup()) {
				name = name.concat(" (Staging)");
			}
		}
		else if (isUser()) {
			long userId = getClassPK();

			User user = UserLocalServiceUtil.getUserById(userId);

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
		else if (name.equals(GroupConstants.GUEST)) {
			Company company = CompanyLocalServiceUtil.getCompany(
				getCompanyId());

			Account account = company.getAccount();

			name = account.getName();
		}

		return name;
	}

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

	public Group getParentGroup() throws SystemException, PortalException {
		long parentGroupId = getParentGroupId();

		if (parentGroupId <= 0) {
			return null;
		}

		return GroupLocalServiceUtil.getGroup(parentGroupId);
	}

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

	public int getPrivateLayoutsPageCount() {
		try {
			return LayoutLocalServiceUtil.getLayoutsCount(this, true);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

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

	public int getPublicLayoutsPageCount() {
		try {
			return LayoutLocalServiceUtil.getLayoutsCount(this, false);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

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

	public String getTypeSettingsProperty(String key) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key);
	}

	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

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
	 * @deprecated As of 6.1, renamed to {@link #isRegularSite}
	 */
	public boolean isCommunity() {
		return isRegularSite();
	}

	public boolean isCompany() {
		return hasClassName(Company.class);
	}

	public boolean isControlPanel() {
		String name = getName();

		if (name.equals(GroupConstants.CONTROL_PANEL)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isGuest() {
		String name = getName();

		if (name.equals(GroupConstants.GUEST)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isLayout() {
		return hasClassName(Layout.class);
	}

	public boolean isLayoutPrototype() {
		return hasClassName(LayoutPrototype.class);
	}

	public boolean isLayoutSetPrototype() {
		return hasClassName(LayoutSetPrototype.class);
	}

	public boolean isOrganization() {
		return hasClassName(Organization.class);
	}

	public boolean isRegularSite() {
		return hasClassName(Group.class);
	}

	public boolean isStaged() {
		return GetterUtil.getBoolean(getTypeSettingsProperty("staged"));
	}

	public boolean isStagedPortlet(String portletId) {
		try {
			if (isLayout()) {
				Group parentGroup = GroupLocalServiceUtil.getGroup(
					getParentGroupId());

				return parentGroup.isStagedPortlet(portletId);
			}
		}
		catch (Exception e) {
		}

		portletId = PortletConstants.getRootPortletId(portletId);

		String typeSettingsProperty = getTypeSettingsProperty(
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

			UnicodeProperties typeSettingsProperties =
				getTypeSettingsProperties();

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

	public boolean isStagedRemotely() {
		return GetterUtil.getBoolean(getTypeSettingsProperty("stagedRemotely"));
	}

	public boolean isStagingGroup() {
		if (getLiveGroupId() == GroupConstants.DEFAULT_LIVE_GROUP_ID) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isUser() {
		return hasClassName(User.class);
	}

	public boolean isUserGroup() {
		return hasClassName(UserGroup.class);
	}

	public boolean isUserPersonalSite() {
		return hasClassName(UserPersonalSite.class);
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		_typeSettingsProperties = null;

		super.setTypeSettings(typeSettings);
	}

	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;

		super.setTypeSettings(_typeSettingsProperties.toString());
	}

	protected long getDefaultPlid(boolean privateLayout) {
		try {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				getGroupId(), privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, true, 0, 1);

			if (layouts.size() > 0) {
				Layout layout = layouts.get(0);

				return layout.getPlid();
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