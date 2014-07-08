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

package com.liferay.portal.service;

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

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _themeLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_themeLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.ColorScheme fetchColorScheme(
		long companyId, java.lang.String themeId, java.lang.String colorSchemeId) {
		return _themeLocalService.fetchColorScheme(companyId, themeId,
			colorSchemeId);
	}

	@Override
	public com.liferay.portal.model.Theme fetchTheme(long companyId,
		java.lang.String themeId) {
		return _themeLocalService.fetchTheme(companyId, themeId);
	}

	@Override
	public com.liferay.portal.model.ColorScheme getColorScheme(long companyId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		boolean wapTheme) {
		return _themeLocalService.getColorScheme(companyId, themeId,
			colorSchemeId, wapTheme);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Theme> getControlPanelThemes(
		long companyId, long userId, boolean wapTheme) {
		return _themeLocalService.getControlPanelThemes(companyId, userId,
			wapTheme);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Theme> getPageThemes(
		long companyId, long groupId, long userId, boolean wapTheme) {
		return _themeLocalService.getPageThemes(companyId, groupId, userId,
			wapTheme);
	}

	@Override
	public com.liferay.portal.model.Theme getTheme(long companyId,
		java.lang.String themeId, boolean wapTheme) {
		return _themeLocalService.getTheme(companyId, themeId, wapTheme);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId) {
		return _themeLocalService.getThemes(companyId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getPageThemes}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId, long groupId, long userId, boolean wapTheme) {
		return _themeLocalService.getThemes(companyId, groupId, userId, wapTheme);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Theme> getWARThemes() {
		return _themeLocalService.getWARThemes();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Theme> init(
		javax.servlet.ServletContext servletContext,
		java.lang.String themesPath, boolean loadFromServletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _themeLocalService.init(servletContext, themesPath,
			loadFromServletContext, xmls, pluginPackage);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Theme> init(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext,
		java.lang.String themesPath, boolean loadFromServletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _themeLocalService.init(servletContextName, servletContext,
			themesPath, loadFromServletContext, xmls, pluginPackage);
	}

	@Override
	public void uninstallThemes(
		java.util.List<com.liferay.portal.model.Theme> themes) {
		_themeLocalService.uninstallThemes(themes);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ThemeLocalService getWrappedThemeLocalService() {
		return _themeLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedThemeLocalService(ThemeLocalService themeLocalService) {
		_themeLocalService = themeLocalService;
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