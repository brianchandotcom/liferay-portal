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
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * Provides the local service interface for LayoutTemplate. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutTemplateLocalServiceUtil
 * @see com.liferay.portal.service.base.LayoutTemplateLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutTemplateLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutTemplateLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutTemplateLocalServiceUtil} to access the layout template local service. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutTemplateLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getContent(String layoutTemplateId, boolean standard,
		String themeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getLangType(String layoutTemplateId, boolean standard,
		String themeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutTemplate getLayoutTemplate(String layoutTemplateId,
		boolean standard, String themeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutTemplate> getLayoutTemplates();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutTemplate> getLayoutTemplates(String themeId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public List<LayoutTemplate> init(ServletContext servletContext,
		String[] xmls, PluginPackage pluginPackage);

	public List<LayoutTemplate> init(String servletContextName,
		ServletContext servletContext, String[] xmls,
		PluginPackage pluginPackage);

	public void readLayoutTemplate(String servletContextName,
		ServletContext servletContext, Set<LayoutTemplate> layoutTemplates,
		Element element, boolean standard, String themeId,
		PluginPackage pluginPackage);

	public void uninstallLayoutTemplate(String layoutTemplateId,
		boolean standard);

	public void uninstallLayoutTemplates(String themeId);
}