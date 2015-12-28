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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Theme. This utility wraps
 * {@link com.liferay.portal.service.impl.ThemeLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ThemeLocalService
 * @see com.liferay.portal.service.base.ThemeLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ThemeLocalServiceImpl
 * @generated
 */
@ProviderType
public class ThemeLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ThemeLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.model.ColorScheme fetchColorScheme(
		long companyId, java.lang.String themeId, java.lang.String colorSchemeId) {
		return getService().fetchColorScheme(companyId, themeId, colorSchemeId);
	}

	public static com.liferay.portal.model.PortletDecorator fetchPortletDecorator(
		long companyId, java.lang.String themeId, java.lang.String colorSchemeId) {
		return getService()
				   .fetchPortletDecorator(companyId, themeId, colorSchemeId);
	}

	public static com.liferay.portal.model.Theme fetchTheme(long companyId,
		java.lang.String themeId) {
		return getService().fetchTheme(companyId, themeId);
	}

	public static com.liferay.portal.model.ColorScheme getColorScheme(
		long companyId, java.lang.String themeId,
		java.lang.String colorSchemeId, boolean wapTheme) {
		return getService()
				   .getColorScheme(companyId, themeId, colorSchemeId, wapTheme);
	}

	public static java.util.List<com.liferay.portal.model.Theme> getControlPanelThemes(
		long companyId, long userId, boolean wapTheme) {
		return getService().getControlPanelThemes(companyId, userId, wapTheme);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.portal.model.Theme> getPageThemes(
		long companyId, long groupId, long userId, boolean wapTheme) {
		return getService().getPageThemes(companyId, groupId, userId, wapTheme);
	}

	public static com.liferay.portal.model.PortletDecorator getPortletDecorator(
		long companyId, java.lang.String themeId,
		java.lang.String portletDecoratorId) {
		return getService()
				   .getPortletDecorator(companyId, themeId, portletDecoratorId);
	}

	public static com.liferay.portal.model.Theme getTheme(long companyId,
		java.lang.String themeId, boolean wapTheme) {
		return getService().getTheme(companyId, themeId, wapTheme);
	}

	public static java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId) {
		return getService().getThemes(companyId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getPageThemes}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId, long groupId, long userId, boolean wapTheme) {
		return getService().getThemes(companyId, groupId, userId, wapTheme);
	}

	public static java.util.List<com.liferay.portal.model.Theme> getWARThemes() {
		return getService().getWARThemes();
	}

	public static java.util.List<com.liferay.portal.model.Theme> init(
		javax.servlet.ServletContext servletContext,
		java.lang.String themesPath, boolean loadFromServletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return getService()
				   .init(servletContext, themesPath, loadFromServletContext,
			xmls, pluginPackage);
	}

	public static java.util.List<com.liferay.portal.model.Theme> init(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext,
		java.lang.String themesPath, boolean loadFromServletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return getService()
				   .init(servletContextName, servletContext, themesPath,
			loadFromServletContext, xmls, pluginPackage);
	}

	public static void uninstallThemes(
		java.util.List<com.liferay.portal.model.Theme> themes) {
		getService().uninstallThemes(themes);
	}

	public static ThemeLocalService getService() {
		if (_service == null) {
			_service = (ThemeLocalService)PortalBeanLocatorUtil.locate(ThemeLocalService.class.getName());

			ReferenceRegistry.registerReference(ThemeLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static ThemeLocalService _service;
}