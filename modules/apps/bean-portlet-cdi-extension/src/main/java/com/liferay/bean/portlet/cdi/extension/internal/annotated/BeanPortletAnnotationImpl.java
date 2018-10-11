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

package com.liferay.bean.portlet.cdi.extension.internal.annotated;

import com.liferay.bean.portlet.cdi.extension.internal.BaseBeanPortletImpl;
import com.liferay.bean.portlet.cdi.extension.internal.BeanMethod;
import com.liferay.bean.portlet.cdi.extension.internal.PortletDependency;
import com.liferay.bean.portlet.cdi.extension.internal.Preference;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 * @author Raymond Augé
 */
public class BeanPortletAnnotationImpl extends BaseBeanPortletImpl {

	public BeanPortletAnnotationImpl(
		String portletName, Set<BeanMethod> beanMethods,
		Map<String, String> displayNames, String portletClassName,
		Map<String, String> initParams, int expirationCache,
		Map<String, Set<String>> supportedPortletModes,
		Map<String, Set<String>> supportedWindowStates,
		Set<String> supportedLocales, String resourceBundle,
		Map<String, String> titles, Map<String, String> shortTitles,
		Map<String, String> keywords, Map<String, String> descriptions,
		Map<String, Preference> preferences, String preferencesValidator,
		Map<String, String> securityRoleRefs,
		Set<String> supportedPublicRenderParameters,
		Map<String, List<String>> containerRuntimeOptions,
		Set<PortletDependency> portletDependencies, boolean asyncSupported,
		boolean multiPartSupported, int multiPartFileSizeThreshold,
		String multiPartLocation, long multiPartMaxFileSize,
		long multiPartMaxRequestSize, String displayCategory,
		Map<String, Set<String>> liferayConfiguration) {

		super(beanMethods, new HashSet<QName>(), new HashSet<QName>());

		_portletName = portletName;
		_displayNames = displayNames;
		_portletClassName = portletClassName;
		_initParams = initParams;
		_expirationCache = expirationCache;
		_supportedPortletModes = supportedPortletModes;
		_supportedWindowStates = supportedWindowStates;
		_supportedLocales = supportedLocales;
		_resourceBundle = resourceBundle;
		_titles = titles;
		_shortTitles = shortTitles;
		_keywords = keywords;
		_descriptions = descriptions;
		_preferences = preferences;
		_preferencesValidator = preferencesValidator;
		_securityRoleRefs = securityRoleRefs;
		_supportedPublicRenderParameters = supportedPublicRenderParameters;
		_containerRuntimeOptions = containerRuntimeOptions;
		_portletDependencies = portletDependencies;
		_asyncSupported = asyncSupported;
		_multiPartSupported = multiPartSupported;
		_multiPartFileSizeThreshold = multiPartFileSizeThreshold;
		_multiPartLocation = multiPartLocation;
		_multiPartMaxFileSize = multiPartMaxFileSize;
		_multiPartMaxRequestSize = multiPartMaxRequestSize;
		_displayCategory = displayCategory;
		_liferayConfiguration = liferayConfiguration;
	}

	@Override
	public Map<String, List<String>> getContainerRuntimeOptions() {
		return _containerRuntimeOptions;
	}

	@Override
	public Map<String, String> getDescriptions() {
		return _descriptions;
	}

	@Override
	public String getDisplayCategory() {
		return _displayCategory;
	}

	@Override
	public Map<String, String> getDisplayNames() {
		return _displayNames;
	}

	@Override
	public int getExpirationCache() {
		return _expirationCache;
	}

	@Override
	public Map<String, String> getInitParams() {
		return _initParams;
	}

	@Override
	public Map<String, String> getKeywords() {
		return _keywords;
	}

	@Override
	public Map<String, Set<String>> getLiferayConfiguration() {
		return _liferayConfiguration;
	}

	@Override
	public int getMultiPartFileSizeThreshold() {
		return _multiPartFileSizeThreshold;
	}

	@Override
	public String getMultiPartLocation() {
		return _multiPartLocation;
	}

	@Override
	public long getMultiPartMaxFileSize() {
		return _multiPartMaxFileSize;
	}

	@Override
	public long getMultiPartMaxRequestSize() {
		return _multiPartMaxRequestSize;
	}

	@Override
	public String getPortletClassName() {
		return _portletClassName;
	}

	@Override
	public Set<PortletDependency> getPortletDependencies() {
		return _portletDependencies;
	}

	@Override
	public String getPortletName() {
		return _portletName;
	}

	@Override
	public Map<String, Preference> getPreferences() {
		return _preferences;
	}

	@Override
	public String getPreferencesValidator() {
		return _preferencesValidator;
	}

	@Override
	public String getResourceBundle() {
		return _resourceBundle;
	}

	@Override
	public Map<String, String> getSecurityRoleRefs() {
		return _securityRoleRefs;
	}

	@Override
	public Map<String, String> getShortTitles() {
		return _shortTitles;
	}

	@Override
	public Set<String> getSupportedLocales() {
		return _supportedLocales;
	}

	@Override
	public Map<String, Set<String>> getSupportedPortletModes() {
		return _supportedPortletModes;
	}

	@Override
	public Set<String> getSupportedPublicRenderParameters() {
		return _supportedPublicRenderParameters;
	}

	@Override
	public Map<String, Set<String>> getSupportedWindowStates() {
		return _supportedWindowStates;
	}

	@Override
	public Map<String, String> getTitles() {
		return _titles;
	}

	@Override
	public boolean isAsyncSupported() {
		return _asyncSupported;
	}

	@Override
	public boolean isMultiPartSupported() {
		return _multiPartSupported;
	}

	private final boolean _asyncSupported;
	private final Map<String, List<String>> _containerRuntimeOptions;
	private final Map<String, String> _descriptions;
	private final String _displayCategory;
	private final Map<String, String> _displayNames;
	private final int _expirationCache;
	private final Map<String, String> _initParams;
	private final Map<String, String> _keywords;
	private final Map<String, Set<String>> _liferayConfiguration;
	private final int _multiPartFileSizeThreshold;
	private final String _multiPartLocation;
	private final long _multiPartMaxFileSize;
	private final long _multiPartMaxRequestSize;
	private final boolean _multiPartSupported;
	private final String _portletClassName;
	private final Set<PortletDependency> _portletDependencies;
	private final String _portletName;
	private final Map<String, Preference> _preferences;
	private final String _preferencesValidator;
	private final String _resourceBundle;
	private final Map<String, String> _securityRoleRefs;
	private final Map<String, String> _shortTitles;
	private final Set<String> _supportedLocales;
	private final Map<String, Set<String>> _supportedPortletModes;
	private final Set<String> _supportedPublicRenderParameters;
	private final Map<String, Set<String>> _supportedWindowStates;
	private final Map<String, String> _titles;

}