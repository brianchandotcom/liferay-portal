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
 * Provides a wrapper for {@link LayoutTemplateLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutTemplateLocalService
 * @generated
 */
@ProviderType
public class LayoutTemplateLocalServiceWrapper
	implements LayoutTemplateLocalService,
		ServiceWrapper<LayoutTemplateLocalService> {
	public LayoutTemplateLocalServiceWrapper(
		LayoutTemplateLocalService layoutTemplateLocalService) {
		_layoutTemplateLocalService = layoutTemplateLocalService;
	}

	@Override
	public String getContent(String layoutTemplateId, boolean standard,
		String themeId) {
		return _layoutTemplateLocalService.getContent(layoutTemplateId,
			standard, themeId);
	}

	@Override
	public String getLangType(String layoutTemplateId, boolean standard,
		String themeId) {
		return _layoutTemplateLocalService.getLangType(layoutTemplateId,
			standard, themeId);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutTemplate getLayoutTemplate(
		String layoutTemplateId, boolean standard, String themeId) {
		return _layoutTemplateLocalService.getLayoutTemplate(layoutTemplateId,
			standard, themeId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutTemplate> getLayoutTemplates() {
		return _layoutTemplateLocalService.getLayoutTemplates();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutTemplate> getLayoutTemplates(
		String themeId) {
		return _layoutTemplateLocalService.getLayoutTemplates(themeId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutTemplateLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutTemplate> init(
		javax.servlet.ServletContext servletContext, String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _layoutTemplateLocalService.init(servletContext, xmls,
			pluginPackage);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutTemplate> init(
		String servletContextName, javax.servlet.ServletContext servletContext,
		String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _layoutTemplateLocalService.init(servletContextName,
			servletContext, xmls, pluginPackage);
	}

	@Override
	public void readLayoutTemplate(String servletContextName,
		javax.servlet.ServletContext servletContext,
		java.util.Set<com.liferay.portal.kernel.model.LayoutTemplate> layoutTemplates,
		com.liferay.portal.kernel.xml.Element element, boolean standard,
		String themeId,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		_layoutTemplateLocalService.readLayoutTemplate(servletContextName,
			servletContext, layoutTemplates, element, standard, themeId,
			pluginPackage);
	}

	@Override
	public void uninstallLayoutTemplate(String layoutTemplateId,
		boolean standard) {
		_layoutTemplateLocalService.uninstallLayoutTemplate(layoutTemplateId,
			standard);
	}

	@Override
	public void uninstallLayoutTemplates(String themeId) {
		_layoutTemplateLocalService.uninstallLayoutTemplates(themeId);
	}

	@Override
	public LayoutTemplateLocalService getWrappedService() {
		return _layoutTemplateLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutTemplateLocalService layoutTemplateLocalService) {
		_layoutTemplateLocalService = layoutTemplateLocalService;
	}

	private LayoutTemplateLocalService _layoutTemplateLocalService;
}