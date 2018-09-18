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

import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public class BeanPortletDefaultImpl extends BaseBeanPortletImpl {

	public BeanPortletDefaultImpl(
		String portletName, String displayCategory,
		Map<String, String> liferayConfiguration) {

		_portletName = portletName;
		_displayCategory = displayCategory;
		_liferayConfiguration = liferayConfiguration;
	}

	@Override
	public Map<String, List<String>> getContainerRuntimeOptions() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, String> getDescriptions() {
		return Collections.emptyMap();
	}

	@Override
	public String getDisplayCategory() {
		return _displayCategory;
	}

	@Override
	public Map<String, String> getDisplayNames() {
		return Collections.emptyMap();
	}

	@Override
	public int getExpirationCache() {
		return 0;
	}

	@Override
	public Map<String, String> getInitParams() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, String> getKeywords() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, String> getLiferayConfiguration() {
		return _liferayConfiguration;
	}

	@Override
	public int getMultiPartFileSizeThreshold() {
		return 0;
	}

	@Override
	public String getMultiPartLocation() {
		return null;
	}

	@Override
	public long getMultiPartMaxFileSize() {
		return -1L;
	}

	@Override
	public long getMultiPartMaxRequestSize() {
		return -1L;
	}

	@Override
	public String getPortletClassName() {
		return null;
	}

	@Override
	public Set<PortletDependency> getPortletDependencies() {
		return Collections.emptySet();
	}

	@Override
	public String getPortletName() {
		return _portletName;
	}

	@Override
	public Map<String, Preference> getPreferences() {
		return Collections.emptyMap();
	}

	@Override
	public String getResourceBundle() {
		return null;
	}

	@Override
	public Map<String, String> getSecurityRoleRefs() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, String> getShortTitles() {
		return Collections.emptyMap();
	}

	@Override
	public Set<String> getSupportedLocales() {
		return Collections.emptySet();
	}

	@Override
	public Map<String, Set<String>> getSupportedPortletModes() {
		return Collections.emptyMap();
	}

	@Override
	public Set<QName> getSupportedProcessingEvents() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getSupportedPublicRenderParameters() {
		return Collections.emptySet();
	}

	@Override
	public Set<QName> getSupportedPublishingEvents() {
		return Collections.emptySet();
	}

	@Override
	public Map<String, Set<String>> getSupportedWindowStates() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, String> getTitles() {
		return Collections.emptyMap();
	}

	@Override
	public boolean isAsyncSupported() {
		return false;
	}

	@Override
	public boolean isMultiPartSupported() {
		return false;
	}

	@Override
	public Dictionary<String, Object> toDictionary(BeanApp beanApp) {
		Dictionary<String, Object> dictionary = super.toDictionary(beanApp);

		dictionary.put("javax.portlet.info.title", _portletName);

		return dictionary;
	}

	private final String _displayCategory;
	private final Map<String, String> _liferayConfiguration;
	private final String _portletName;

}