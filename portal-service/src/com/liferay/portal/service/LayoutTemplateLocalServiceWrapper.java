/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

/**
 * Provides a wrapper for {@link LayoutTemplateLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutTemplateLocalService
 * @generated
 */
public class LayoutTemplateLocalServiceWrapper
	implements LayoutTemplateLocalService,
		ServiceWrapper<LayoutTemplateLocalService> {
	public LayoutTemplateLocalServiceWrapper(
		LayoutTemplateLocalService layoutTemplateLocalService) {
		_layoutTemplateLocalService = layoutTemplateLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _layoutTemplateLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_layoutTemplateLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.String getContent(java.lang.String layoutTemplateId,
		boolean standard, java.lang.String themeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutTemplateLocalService.getContent(layoutTemplateId,
			standard, themeId);
	}

	public com.liferay.portal.model.LayoutTemplate getLayoutTemplate(
		java.lang.String layoutTemplateId, boolean standard,
		java.lang.String themeId) {
		return _layoutTemplateLocalService.getLayoutTemplate(layoutTemplateId,
			standard, themeId);
	}

	public java.util.List<com.liferay.portal.model.LayoutTemplate> getLayoutTemplates() {
		return _layoutTemplateLocalService.getLayoutTemplates();
	}

	public java.util.List<com.liferay.portal.model.LayoutTemplate> getLayoutTemplates(
		java.lang.String themeId) {
		return _layoutTemplateLocalService.getLayoutTemplates(themeId);
	}

	public java.lang.String getWapContent(java.lang.String layoutTemplateId,
		boolean standard, java.lang.String themeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutTemplateLocalService.getWapContent(layoutTemplateId,
			standard, themeId);
	}

	public java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.lang.Boolean>> init(
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _layoutTemplateLocalService.init(servletContext, xmls,
			pluginPackage);
	}

	public java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.lang.Boolean>> init(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _layoutTemplateLocalService.init(servletContextName,
			servletContext, xmls, pluginPackage);
	}

	public void readLayoutTemplate(java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext,
		java.util.Set<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.lang.Boolean>> layoutTemplateIdOVPs,
		com.liferay.portal.kernel.xml.Element element, boolean standard,
		java.lang.String themeId,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		_layoutTemplateLocalService.readLayoutTemplate(servletContextName,
			servletContext, layoutTemplateIdOVPs, element, standard, themeId,
			pluginPackage);
	}

	public void uninstallLayoutTemplate(java.lang.String layoutTemplateId,
		boolean standard) {
		_layoutTemplateLocalService.uninstallLayoutTemplate(layoutTemplateId,
			standard);
	}

	public void uninstallLayoutTemplates(java.lang.String themeId) {
		_layoutTemplateLocalService.uninstallLayoutTemplates(themeId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public LayoutTemplateLocalService getWrappedLayoutTemplateLocalService() {
		return _layoutTemplateLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedLayoutTemplateLocalService(
		LayoutTemplateLocalService layoutTemplateLocalService) {
		_layoutTemplateLocalService = layoutTemplateLocalService;
	}

	public LayoutTemplateLocalService getWrappedService() {
		return _layoutTemplateLocalService;
	}

	public void setWrappedService(
		LayoutTemplateLocalService layoutTemplateLocalService) {
		_layoutTemplateLocalService = layoutTemplateLocalService;
	}

	private LayoutTemplateLocalService _layoutTemplateLocalService;
}