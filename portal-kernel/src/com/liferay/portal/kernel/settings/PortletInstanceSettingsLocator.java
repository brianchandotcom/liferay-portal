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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.PortletKeys;

/**
 * @author Ivan Zaera
 * @author Jorge Ferrer
 */
public class PortletInstanceSettingsLocator implements SettingsLocator {

	public PortletInstanceSettingsLocator(
		Layout layout, String portletInstanceKey) {

		_layout = layout;
		_portletInstanceKey = portletInstanceKey;

		_configurationPid = PortletIdCodec.decodePortletName(
			portletInstanceKey);
	}

	public PortletInstanceSettingsLocator(
		Layout layout, String portletInstanceKey, String configurationPid) {

		_layout = layout;
		_portletInstanceKey = portletInstanceKey;
		_configurationPid = configurationPid;
	}

	@Override
	public Settings getSettings() throws SettingsException {
		long companyId = getCompanyId(_layout.getGroupId());

		Settings configurationBeanSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				_configurationPid);

		Settings portalPreferencesSettings =
			_settingsLocatorHelper.getPortalPreferencesSettings(
				companyId, configurationBeanSettings);

		Settings companyPortletPreferencesSettings =
			_settingsLocatorHelper.getCompanyPortletPreferencesSettings(
				companyId, _portletInstanceKey, portalPreferencesSettings);

		Settings groupPortletPreferencesSettings =
			_settingsLocatorHelper.getGroupPortletPreferencesSettings(
				_layout.getGroupId(), _portletInstanceKey,
				companyPortletPreferencesSettings);

		return
			_settingsLocatorHelper.getPortletInstancePortletPreferencesSettings(
				_layout.getCompanyId(), getOwnerId(),
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, getPlid(),
				_portletInstanceKey, groupPortletPreferencesSettings);
	}

	@Override
	public String getSettingsId() {
		return _portletInstanceKey;
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

	protected long getOwnerId() {
		if (isEmbeddedPortlet()) {
			return _layout.getGroupId();
		}

		return PortletKeys.PREFS_OWNER_ID_DEFAULT;
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
			_embeddedPortlet = _layout.isPortletEmbedded(
				_portletInstanceKey, _layout.getGroupId());
		}

		return _embeddedPortlet;
	}

	private final String _configurationPid;
	private Boolean _embeddedPortlet;
	private final Layout _layout;
	private final String _portletInstanceKey;
	private final SettingsLocatorHelper _settingsLocatorHelper =
		SettingsLocatorHelperUtil.getSettingsLocatorHelper();

}