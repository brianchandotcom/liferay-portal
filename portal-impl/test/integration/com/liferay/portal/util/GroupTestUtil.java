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

package com.liferay.portal.util;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Manuel de la Peña
 */
public class GroupTestUtil {

	public static Group addGroup() throws Exception {
		return addGroup(ServiceTestUtil.randomString());
	}

	public static Group addGroup(long userId, Layout layout) throws Exception {
		return addGroup(userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, layout);
	}

	public static Group addGroup(long userId, long parentGroupId, Layout layout)
		throws Exception {

		Group scopeGroup = layout.getScopeGroup();

		if (scopeGroup != null) {
			return scopeGroup;
		}

		return GroupLocalServiceUtil.addGroup(
			userId, parentGroupId, Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			String.valueOf(layout.getPlid()), null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);
	}

	public static Group addGroup(
			long companyId, long userId, long parentGroupId, String name,
			String description)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(companyId, name);

		if (group != null) {
			return group;
		}

		int type = GroupConstants.TYPE_SITE_OPEN;
		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);
		boolean site = true;
		boolean active = true;
		boolean manualMembership = true;
		int membershipRestriction =
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;

		return GroupLocalServiceUtil.addGroup(
			userId, parentGroupId, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, description, type,
			manualMembership, membershipRestriction, friendlyURL, site, active,
			ServiceTestUtil.getServiceContext());
	}

	public static Group addGroup(long parentGroupId, String name)
		throws Exception {

		return addGroup(parentGroupId, name, "This is a test group.");
	}

	public static Group addGroup(
			long parentGroupId, String name, String description)
		throws Exception {

		return addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				parentGroupId, name, description);
	}

	public static Group addGroup(String name) throws Exception {
		return addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID, name);
	}

	public static Group addGroup(
			String name, long parentGroupId, ServiceContext serviceContext)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(
			TestPropsValues.getCompanyId(), name);

		if (group != null) {
			return group;
		}

		String description = "This is a test group.";
		int type = GroupConstants.TYPE_SITE_OPEN;
		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);
		boolean site = true;
		boolean active = true;
		boolean manualMembership = true;
		int membershipRestriction =
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;

		if (serviceContext == null) {
			serviceContext = ServiceTestUtil.getServiceContext();
		}

		return GroupServiceUtil.addGroup(
			parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID, name,
			description, type, manualMembership, membershipRestriction,
			friendlyURL, site, active, serviceContext);
	}

	public static void enableLocalStaging(Group group) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(group.getGroupId());

		Map<String, String[]> parameters = StagingUtil.getStagingParameters();

		parameters.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.FALSE.toString()});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.FALSE.toString()});

		for (String parameterName : parameters.keySet()) {
			serviceContext.setAttribute(
				parameterName, parameters.get(parameterName)[0]);
		}

		StagingUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, group, false, false,
			serviceContext);
	}

	public static Group updateDisplaySettings(
			long groupId, Locale[] availableLocales, Locale defaultLocale)
		throws Exception {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		boolean inheritLocales = false;

		if ((availableLocales == null) && (defaultLocale == null)) {
			inheritLocales = true;
		}

		typeSettingsProperties.put(
			"inheritLocales", String.valueOf(inheritLocales));

		if (availableLocales != null) {
			typeSettingsProperties.put(
				PropsKeys.LOCALES,
				StringUtil.merge(LocaleUtil.toLanguageIds(availableLocales)));
		}

		if (defaultLocale != null) {
			typeSettingsProperties.put(
				"languageId", LocaleUtil.toLanguageId(defaultLocale));
		}

		Group group = GroupLocalServiceUtil.updateGroup(
			groupId, typeSettingsProperties.toString());

		ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);

		return group;
	}

}