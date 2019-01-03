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

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsPropsUtil;

/**
 * @author Iván Zaera
 * @author Jorge Ferrer
 */
public class PortletInstanceSettingsLocator implements SettingsLocator {

	public PortletInstanceSettingsLocator(
		Layout layout, String portletInstanceKey) {

		this(
			layout, portletInstanceKey,
			PortletIdCodec.decodePortletName(portletInstanceKey));
	}

	public PortletInstanceSettingsLocator(
		Layout layout, String portletInstanceKey, String configurationPid) {

		_layout = layout;
		_portletInstanceKey = portletInstanceKey;
		_configurationPid = configurationPid;
	}

	public String getConfigurationPid() {
		return _configurationPid;
	}

	public long getOwnerId() {
		if (isEmbeddedPortlet()) {
			return _layout.getGroupId();
		}

		return PortletKeys.PREFS_OWNER_ID_DEFAULT;
	}

	public long getPlid() {
		if (isEmbeddedPortlet()) {
			return PortletKeys.PREFS_PLID_SHARED;
		}

		return _layout.getPlid();
	}

	@Override
	public Settings getSettings() throws SettingsException {
		SystemSettingsLocator systemSettingsLocator = new SystemSettingsLocator(
			_configurationPid);

		Settings portalPreferencesSettings = new PortletPreferencesSettings(
			PrefsPropsUtil.getPreferences(_layout.getCompanyId()),
			systemSettingsLocator.getSettings());

		return PortletPreferencesLocalServiceUtil.getPortletInstanceSettings(
			_layout.getCompanyId(), _layout.getGroupId(), _portletInstanceKey,
			this, portalPreferencesSettings);
	}

	@Override
	public String getSettingsId() {
		return _portletInstanceKey;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected long getCompanyId(long groupId) {
		return _layout.getCompanyId();
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

}