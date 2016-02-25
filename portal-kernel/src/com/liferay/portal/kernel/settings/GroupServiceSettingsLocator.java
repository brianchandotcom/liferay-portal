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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author Ivan Zaera
 * @author Jorge Ferrer
 */
public class GroupServiceSettingsLocator implements SettingsLocator {

	public GroupServiceSettingsLocator(long groupId, String settingsId) {
		_groupId = groupId;
		_settingsId = settingsId;

		_configurationPid = settingsId;
	}

	public GroupServiceSettingsLocator(
		long groupId, String settingsId, String configurationPid) {

		_groupId = groupId;
		_settingsId = settingsId;
		_configurationPid = configurationPid;
	}

	@Override
	public Settings getSettings() throws SettingsException {
		long companyId = getCompanyId(_groupId);

		Settings portalPropertiesSettings =
			_settingsLocatorHelper.getPortalPropertiesSettings();

		Settings configurationBeanSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				_configurationPid, portalPropertiesSettings);

		Settings portalPreferencesSettings =
			_settingsLocatorHelper.getPortalPreferencesSettings(
				companyId, configurationBeanSettings);

		Settings companyPortletPreferencesSettings =
			_settingsLocatorHelper.getCompanyPortletPreferencesSettings(
				companyId, _settingsId, portalPreferencesSettings);

		return _settingsLocatorHelper.getGroupPortletPreferencesSettings(
			_groupId, _settingsId, companyPortletPreferencesSettings);
	}

	@Override
	public String getSettingsId() {
		return _settingsId;
	}

	protected long getCompanyId(long groupId) throws SettingsException {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			return group.getCompanyId();
		}
		catch (PortalException pe) {
			throw new SettingsException(pe);
		}
	}

	private final String _configurationPid;
	private final long _groupId;
	private final String _settingsId;
	private final SettingsLocatorHelper _settingsLocatorHelper =
		SettingsLocatorHelperUtil.getSettingsLocatorHelper();

}