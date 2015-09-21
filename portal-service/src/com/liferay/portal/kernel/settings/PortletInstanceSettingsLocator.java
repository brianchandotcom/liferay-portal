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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Ivan Zaera
 * @author Jorge Ferrer
 */
public class PortletInstanceSettingsLocator implements SettingsLocator {

	public PortletInstanceSettingsLocator(Layout layout, String settingsId) {
		_layout = layout;
		_settingsId = settingsId;
	}

	@Override
	public Settings getSettings() throws SettingsException {
		long companyId = getCompanyId(_layout.getGroupId());

		Settings portalPropertiesSettings =
			_settingsLocatorHelper.getPortalPropertiesSettings();

		Settings configurationBeanSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				_settingsId, portalPropertiesSettings);

		Settings portalPreferencesSettings =
			_settingsLocatorHelper.getPortalPreferencesSettings(
				companyId, configurationBeanSettings);

		Settings companyPortletPreferencesSettings =
			_settingsLocatorHelper.getCompanyPortletPreferencesSettings(
				companyId, _settingsId, portalPreferencesSettings);

		Settings groupPortletPreferencesSettings =
			_settingsLocatorHelper.getGroupPortletPreferencesSettings(
				_layout.getGroupId(), _settingsId,
				companyPortletPreferencesSettings);

		return
			_settingsLocatorHelper.getPortletInstancePortletPreferencesSettings(
				_layout.getCompanyId(), getPlid(), _settingsId,
				groupPortletPreferencesSettings);
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

	protected long getPlid() {
		if (isEmbeddedPortlet()) {
			return PortletKeys.PREFS_PLID_SHARED;
		}

		return _layout.getPlid();
	}

	protected boolean isEmbeddedPortlet() {
		if (_embeddedPortlet != null) {
			return _embeddedPortlet;
		}

		_embeddedPortlet = false;

		if (_layout.isSupportsEmbeddedPortlets()) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)_layout.getLayoutType();

			_embeddedPortlet = layoutTypePortlet.isPortletEmbedded(_settingsId);
		}

		return _embeddedPortlet;
	}

	private Boolean _embeddedPortlet = null;
	private final Layout _layout;
	private final String _settingsId;
	private final SettingsLocatorHelper _settingsLocatorHelper =
		SettingsLocatorHelperUtil.getSettingsLocatorHelper();

}