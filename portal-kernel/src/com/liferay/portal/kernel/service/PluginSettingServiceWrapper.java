/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link PluginSettingService}.
 *
 * @author Brian Wing Shun Chan
 * @see PluginSettingService
 * @generated
 */
public class PluginSettingServiceWrapper
	implements PluginSettingService, ServiceWrapper<PluginSettingService> {

	public PluginSettingServiceWrapper() {
		this(null);
	}

	public PluginSettingServiceWrapper(
		PluginSettingService pluginSettingService) {

		_pluginSettingService = pluginSettingService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _pluginSettingService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PluginSetting updatePluginSetting(
			long companyId, java.lang.String pluginId,
			java.lang.String pluginType, java.lang.String roles, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _pluginSettingService.updatePluginSetting(
			companyId, pluginId, pluginType, roles, active);
	}

	@Override
	public PluginSettingService getWrappedService() {
		return _pluginSettingService;
	}

	@Override
	public void setWrappedService(PluginSettingService pluginSettingService) {
		_pluginSettingService = pluginSettingService;
	}

	private PluginSettingService _pluginSettingService;

}