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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.resource.manager.ResourceManager;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.settings.impl.ConfigurationBeanSettings;
import com.liferay.portal.kernel.settings.impl.LocationVariableResolver;
import com.liferay.portal.kernel.settings.impl.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.impl.PropertiesSettings;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Ivan Zaera
 * @author Jorge Ferrer
 */
@DoPrivileged
public class SettingsLocatorHelper {

	public SettingsLocatorHelper() {
	}

	public PortletPreferences getCompanyPortletPreferences(
		long companyId, String settingsId) {

		return PortletPreferencesLocalServiceUtil.getStrictPreferences(
			companyId, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
			settingsId);
	}

	public Settings getCompanyPortletPreferencesSettings(
		long companyId, String settingsId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			getCompanyPortletPreferences(companyId, settingsId),
			parentSettings);
	}

	public Settings getConfigurationBeanSettings(
		String settingsId, Settings parentSettings) {

		return new ConfigurationBeanSettings(
			new LocationVariableResolver(
				getResourceManager(settingsId),
				SettingsFactoryUtil.getSettingsFactory()),
			getConfigurationBean(settingsId), parentSettings);
	}

	public PortletPreferences getGroupPortletPreferences(
			long groupId, String settingsId)
		throws SettingsException {

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			long companyId = group.getCompanyId();

			return PortletPreferencesLocalServiceUtil.getStrictPreferences(
				companyId, groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, 0,
				settingsId);
		}
		catch (PortalException pe) {
			throw new SettingsException(pe);
		}
	}

	public Settings getGroupPortletPreferencesSettings(
			long groupId, String settingsId, Settings parentSettings)
		throws SettingsException {

		return new PortletPreferencesSettings(
			getGroupPortletPreferences(groupId, settingsId), parentSettings);
	}

	public PortletPreferences getPortalPreferences(long companyId) {
		return PortalPreferencesLocalServiceUtil.getPreferences(
			companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);
	}

	public Settings getPortalPreferencesSettings(
		long companyId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			getPortalPreferences(companyId), parentSettings);
	}

	public Properties getPortalProperties() {
		return PropsUtil.getProperties();
	}

	public Settings getPortalPropertiesSettings() {
		return new PropertiesSettings(
			new LocationVariableResolver(
				new ClassLoaderResourceManager(
					PortalClassLoaderUtil.getClassLoader()),
				SettingsFactoryUtil.getSettingsFactory()),
			getPortalProperties());
	}

	public PortletPreferences getPortletInstancePortletPreferences(
		Layout layout, String portletId) {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		if (PortletConstants.hasUserId(portletId)) {
			ownerId = PortletConstants.getUserId(portletId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		return PortletPreferencesLocalServiceUtil.getStrictPreferences(
			layout.getCompanyId(), ownerId, ownerType, layout.getPlid(),
			portletId);
	}

	public Settings getPortletInstancePortletPreferencesSettings(
		Layout layout, String portletId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			getPortletInstancePortletPreferences(layout, portletId),
			parentSettings);
	}

	protected Object getConfigurationBean(String settingsId) {
		settingsId = PortletConstants.getRootPortletId(settingsId);

		SettingsDescriptor settingsDescriptor =
			SettingsFactoryUtil.getSettingsDescriptor(settingsId);

		if (settingsDescriptor == null) {
			return null;
		}

		return settingsDescriptor.getConfigurationBean();
	}

	protected ResourceManager getResourceManager(String settingsId) {
		settingsId = PortletConstants.getRootPortletId(settingsId);

		SettingsDescriptor settingsDescriptor =
			SettingsFactoryUtil.getSettingsDescriptor(settingsId);

		if (settingsDescriptor == null) {
			return null;
		}

		return settingsDescriptor.getResourceManager();
	}

}