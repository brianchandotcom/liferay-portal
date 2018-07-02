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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link ThemeLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ThemeLocalService
 * @generated
 */
@ProviderType
public class ThemeLocalServiceWrapper implements ThemeLocalService,
	ServiceWrapper<ThemeLocalService> {
	public ThemeLocalServiceWrapper(ThemeLocalService themeLocalService) {
		_themeLocalService = themeLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.ColorScheme fetchColorScheme(
		long companyId, String themeId, String colorSchemeId) {
		return _themeLocalService.fetchColorScheme(companyId, themeId,
			colorSchemeId);
	}

	@Override
	public com.liferay.portal.kernel.model.PortletDecorator fetchPortletDecorator(
		long companyId, String themeId, String colorSchemeId) {
		return _themeLocalService.fetchPortletDecorator(companyId, themeId,
			colorSchemeId);
	}

	@Override
	public com.liferay.portal.kernel.model.Theme fetchTheme(long companyId,
		String themeId) {
		return _themeLocalService.fetchTheme(companyId, themeId);
	}

	@Override
	public com.liferay.portal.kernel.model.ColorScheme getColorScheme(
		long companyId, String themeId, String colorSchemeId) {
		return _themeLocalService.getColorScheme(companyId, themeId,
			colorSchemeId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Theme> getControlPanelThemes(
		long companyId, long userId) {
		return _themeLocalService.getControlPanelThemes(companyId, userId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _themeLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Theme> getPageThemes(
		long companyId, long groupId, long userId) {
		return _themeLocalService.getPageThemes(companyId, groupId, userId);
	}

	@Override
	public com.liferay.portal.kernel.model.PortletDecorator getPortletDecorator(
		long companyId, String themeId, String portletDecoratorId) {
		return _themeLocalService.getPortletDecorator(companyId, themeId,
			portletDecoratorId);
	}

	@Override
	public com.liferay.portal.kernel.model.Theme getTheme(long companyId,
		String themeId) {
		return _themeLocalService.getTheme(companyId, themeId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Theme> getThemes(
		long companyId) {
		return _themeLocalService.getThemes(companyId);
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link #getPageThemes}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Theme> getThemes(
		long companyId, long groupId, long userId, boolean wapTheme) {
		return _themeLocalService.getThemes(companyId, groupId, userId, wapTheme);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Theme> getWARThemes() {
		return _themeLocalService.getWARThemes();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Theme> init(
		javax.servlet.ServletContext servletContext, String themesPath,
		boolean loadFromServletContext, String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _themeLocalService.init(servletContext, themesPath,
			loadFromServletContext, xmls, pluginPackage);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Theme> init(
		String servletContextName, javax.servlet.ServletContext servletContext,
		String themesPath, boolean loadFromServletContext, String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _themeLocalService.init(servletContextName, servletContext,
			themesPath, loadFromServletContext, xmls, pluginPackage);
	}

	@Override
	public void uninstallThemes(
		java.util.List<com.liferay.portal.kernel.model.Theme> themes) {
		_themeLocalService.uninstallThemes(themes);
	}

	@Override
	public ThemeLocalService getWrappedService() {
		return _themeLocalService;
	}

	@Override
	public void setWrappedService(ThemeLocalService themeLocalService) {
		_themeLocalService = themeLocalService;
	}

	private ThemeLocalService _themeLocalService;
}