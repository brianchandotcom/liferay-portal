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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.PortletDecorator;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import javax.servlet.ServletContext;

/**
 * Provides the local service interface for Theme. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ThemeLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ThemeLocalService extends BaseLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ThemeLocalServiceUtil} to access the theme local service. Add custom service methods to <code>com.liferay.portal.service.impl.ThemeLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ColorScheme fetchColorScheme(
		long companyId, String themeId, String colorSchemeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PortletDecorator fetchPortletDecorator(
		long companyId, String themeId, String colorSchemeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Theme fetchTheme(long companyId, String themeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ColorScheme getColorScheme(
		long companyId, String themeId, String colorSchemeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Theme> getControlPanelThemes(long companyId, long userId);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Theme> getPageThemes(long companyId, long groupId, long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PortletDecorator getPortletDecorator(
		long companyId, String themeId, String portletDecoratorId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Theme getTheme(long companyId, String themeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Theme> getThemes(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Theme> getWARThemes();

	public List<Theme> init(
		ServletContext servletContext, String themesPath,
		boolean loadFromServletContext, String[] xmls,
		PluginPackage pluginPackage);

	public List<Theme> init(
		String servletContextName, ServletContext servletContext,
		String themesPath, boolean loadFromServletContext, String[] xmls,
		PluginPackage pluginPackage);

	public void uninstallThemes(List<Theme> themes);

}