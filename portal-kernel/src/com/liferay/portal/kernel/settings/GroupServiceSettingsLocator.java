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
 * @author Iván Zaera
 * @author Jorge Ferrer
 */
public class GroupServiceSettingsLocator implements SettingsLocator {

	public GroupServiceSettingsLocator(long groupId, String settingsId) {
		this(groupId, settingsId, settingsId);
	}

	public GroupServiceSettingsLocator(
		long groupId, String settingsId, String configurationPid) {

		_groupId = groupId;
		_settingsId = settingsId;
		_configurationPid = configurationPid;
	}

	@Override
	public Settings getSettings() throws SettingsException {
		CompanyServiceSettingsLocator companyServiceSettingsLocator =
			new CompanyServiceSettingsLocator(
				getCompanyId(_groupId), _settingsId, _configurationPid);

		Settings groupConfigurationBeanSettings =
			_settingsLocatorHelper.getGroupConfigurationBeanSettings(
				_groupId, _configurationPid,
				companyServiceSettingsLocator.getSettings());

		return _settingsLocatorHelper.getGroupPortletPreferencesSettings(
			_groupId, _settingsId, groupConfigurationBeanSettings);
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