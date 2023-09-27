/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration.portlet;

import com.liferay.bean.portlet.extension.BeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodType;
import com.liferay.bean.portlet.registration.portlet.app.BeanApp;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public interface BeanPortlet {

	public Map<BeanPortletMethodType, List<BeanPortletMethod>> getBeanMethods();

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