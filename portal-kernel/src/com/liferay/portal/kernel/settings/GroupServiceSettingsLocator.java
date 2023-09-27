/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (PortalException portalException) {
			throw new SettingsException(portalException);
		}
	}

	private final String _configurationPid;
	private final long _groupId;
	private final String _settingsId;
	private final SettingsLocatorHelper _settingsLocatorHelper =
		SettingsLocatorHelperUtil.getSettingsLocatorHelper();

}