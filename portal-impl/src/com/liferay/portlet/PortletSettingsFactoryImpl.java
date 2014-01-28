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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
@DoPrivileged
public class PortletSettingsFactoryImpl implements PortletSettingsFactory {

	@Override
	public PortletSettings getPortletCompanySettings(
			long companyId, String portletId)
		throws SystemException {

		PortletPreferences companySettings =
			PortletPreferencesLocalServiceUtil.getPreferences(
				companyId, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
				portletId);

		Properties portalProperties = PropsUtil.getProperties(portletId, false);

		CompanyPortletSettings companyPortletSettings =
			new CompanyPortletSettings(companySettings);

		companyPortletSettings.setPortalProperties(portalProperties);

		return companyPortletSettings;
	}

	@Override
	public PortletSettings getPortletInstanceSettings(
			Layout layout, String portletId)
		throws SystemException {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		if (PortletConstants.hasUserId(portletId)) {
			ownerId = PortletConstants.getUserId(portletId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		PortletPreferences instanceSettings =
			PortletPreferencesLocalServiceUtil.getPreferences(
				layout.getCompanyId(), ownerId, ownerType, layout.getPlid(),
				portletId);

		PortletPreferences siteSettings =
			PortletPreferencesLocalServiceUtil.getPreferences(
				layout.getCompanyId(), layout.getGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT_DEFAULTS_GROUP, 0,
				portletId);

		PortletPreferences companySettings =
			PortletPreferencesLocalServiceUtil.getPreferences(
				layout.getCompanyId(), layout.getCompanyId(),
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT_DEFAULTS_COMPANY, 0,
				portletId);

		Properties portalProperties = PropsUtil.getProperties(portletId, false);

		InstancePortletSettings instancePortletSettings =
			new InstancePortletSettings(instanceSettings);

		instancePortletSettings.setSiteDefaultSettings(siteSettings);
		instancePortletSettings.setCompanyDefaultSettings(companySettings);
		instancePortletSettings.setPortalProperties(portalProperties);

		return instancePortletSettings;
	}

	@Override
	public PortletSettings getPortletSiteSettings(
			long groupId, String portletId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		PortletPreferences siteSettings =
			PortletPreferencesLocalServiceUtil.getPreferences(
				group.getCompanyId(), groupId,
				PortletKeys.PREFS_OWNER_TYPE_GROUP, 0, portletId);

		PortletPreferences companySettings =
			PortletPreferencesLocalServiceUtil.getPreferences(
				group.getCompanyId(), group.getCompanyId(),
				PortletKeys.PREFS_OWNER_TYPE_GROUP_DEFAULTS_COMPANY, 0,
				portletId);

		Properties portalProperties = PropsUtil.getProperties(portletId, false);

		InstancePortletSettings sitePortletSettings =
			new InstancePortletSettings(siteSettings);

		sitePortletSettings.setCompanyDefaultSettings(companySettings);
		sitePortletSettings.setPortalProperties(portalProperties);

		return sitePortletSettings;
	}

}