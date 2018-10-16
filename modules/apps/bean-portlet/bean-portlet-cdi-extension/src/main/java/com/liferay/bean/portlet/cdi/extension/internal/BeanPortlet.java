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

package com.liferay.bean.portlet.cdi.extension.internal;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public interface BeanPortlet {

	public Map<MethodType, List<BeanMethod>> getBeanMethods();

	public Map<String, List<String>> getContainerRuntimeOptions();

	public Map<String, String> getDescriptions();

	public String getDisplayCategory();

	public Map<String, String> getDisplayNames();

	public int getExpirationCache();

	public Map<String, String> getInitParams();

	public Map<String, String> getKeywords();

	public Map<String, Set<String>> getLiferayConfiguration();

	public MultipartConfig getMultipartConfig();

	public String getPortletClassName();

	public Set<PortletDependency> getPortletDependencies();

	public String getPortletName();

	public Map<String, Preference> getPreferences();

	public String getPreferencesValidator();

	public String getResourceBundle();

	public Map<String, String> getSecurityRoleRefs();

	public Map<String, String> getShortTitles();

	public Set<String> getSupportedLocales();

	public Map<String, Set<String>> getSupportedPortletModes();

	public Set<String> getSupportedPublicRenderParameters();

	public Map<String, Set<String>> getSupportedWindowStates();

	public Map<String, String> getTitles();

	public boolean isAsyncSupported();

	public Dictionary<String, Object> toDictionary(BeanApp beanApp);

}