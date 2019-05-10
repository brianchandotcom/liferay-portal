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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.xml.QName;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface PortletApp extends Serializable {

	public void addEventDefinition(EventDefinition eventDefinition);

	public void addPortlet(Portlet portlet);

	public void addPortletFilter(PortletFilter portletFilter);

	public void addPortletURLListener(PortletURLListener portletURLListener);

	public void addPublicRenderParameter(
		PublicRenderParameter publicRenderParameter);

	public void addPublicRenderParameter(String identifier, QName qName);

	public void addServletURLPatterns(Set<String> servletURLPatterns);

	public Map<String, String[]> getContainerRuntimeOptions();

	public String getContextPath();

	public Map<String, String> getCustomUserAttributes();

	public String getDefaultNamespace();

	public Set<EventDefinition> getEventDefinitions();

	public PortletFilter getPortletFilter(String filterName);

	public Set<PortletFilter> getPortletFilters();

	public List<Portlet> getPortlets();

	public PortletURLListener getPortletURLListener(String listenerClass);

	public Set<PortletURLListener> getPortletURLListeners();

	public PublicRenderParameter getPublicRenderParameter(String identifier);

	public ServletContext getServletContext();

	public String getServletContextName();

	public Set<String> getServletURLPatterns();

	public int getSpecMajorVersion();

	public int getSpecMinorVersion();

	public SpriteImage getSpriteImage(String fileName);

	public Set<String> getUserAttributes();

	public boolean isWARFile();

	public void removePortlet(Portlet portletModel);

	public void setDefaultNamespace(String defaultNamespace);

	public void setServletContext(ServletContext servletContext);

	public void setSpecMajorVersion(int specMajorVersion);

	public void setSpecMinorVersion(int specMinorVersion);

	public void setSpriteImages(String spriteFileName, Properties properties);

	public void setWARFile(boolean warFile);

}