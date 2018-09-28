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

import com.liferay.bean.portlet.LiferayPortletConfiguration;
import com.liferay.bean.portlet.cdi.extension.internal.BaseBeanPortletImpl;
import com.liferay.bean.portlet.cdi.extension.internal.BeanMethod;
import com.liferay.bean.portlet.cdi.extension.internal.PortletDependency;
import com.liferay.bean.portlet.cdi.extension.internal.Preference;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.annotations.Dependency;
import javax.portlet.annotations.InitParameter;
import javax.portlet.annotations.LocaleString;
import javax.portlet.annotations.Multipart;
import javax.portlet.annotations.PortletConfiguration;
import javax.portlet.annotations.RuntimeOption;
import javax.portlet.annotations.SecurityRoleRef;
import javax.portlet.annotations.Supports;

/**
 * @author Neil Griffin
 * @author Raymond Augé
 */
public class BeanPortletAnnotationImpl extends BaseBeanPortletImpl {

	public BeanPortletAnnotationImpl(
		Set<BeanMethod> beanMethods, Set<BeanMethod> wildcardBeanMethods,
		String portletClassName, PortletConfiguration portletConfiguration,
		String preferencesValidator,
		LiferayPortletConfiguration liferayPortletConfiguration,
		Map<String, String> descriptorLiferayConfiguration,
		String descriptorDisplayCategory) {

		super(beanMethods, wildcardBeanMethods);

		_portletClassName = portletClassName;
		_portletConfiguration = portletConfiguration;
		_preferencesValidator = preferencesValidator;

		String displayCategory = descriptorDisplayCategory;

		String[] propertyNames = null;

		if (liferayPortletConfiguration != null) {
			propertyNames = liferayPortletConfiguration.properties();
		}

		_liferayConfiguration = new HashMap<>();

		if ((propertyNames != null) && (propertyNames.length > 0)) {
			for (String propertyName : propertyNames) {
				String propertyValue = null;

				int equalsPos = propertyName.indexOf(CharPool.EQUAL);

				if (equalsPos > 0) {
					propertyName = propertyName.substring(0, equalsPos);

					propertyValue = propertyName.substring(equalsPos + 1);

					if (Validator.isNull(displayCategory) &&
						propertyName.equals(
							"com.liferay.portlet.display-category")) {

						displayCategory = propertyValue;

						continue;
					}
				}

				_liferayConfiguration.put(propertyName, propertyValue);
			}
		}

		if (descriptorLiferayConfiguration != null) {
			_liferayConfiguration.putAll(descriptorLiferayConfiguration);
		}

		_displayCategory = displayCategory;

		_containerRuntimeOptions = new HashMap<>();

		for (RuntimeOption runtimeOption :
				_portletConfiguration.runtimeOptions()) {

			_containerRuntimeOptions.put(
				runtimeOption.name(), Arrays.asList(runtimeOption.values()));
		}

		_descriptions = new HashMap<>();

		for (LocaleString localeString : portletConfiguration.description()) {
			_descriptions.put(localeString.locale(), localeString.value());
		}

		_displayNames = new HashMap<>();

		for (LocaleString localeString : portletConfiguration.displayName()) {
			_displayNames.put(localeString.locale(), localeString.value());
		}

		_initParams = new HashMap<>();

		for (InitParameter initParameter : _portletConfiguration.initParams()) {
			String value = initParameter.value();

			if (value != null) {
				_initParams.put(initParameter.name(), value);
			}
		}

		_keywords = new HashMap<>();

		for (LocaleString localeString : _portletConfiguration.keywords()) {
			_keywords.put(localeString.locale(), localeString.value());
		}

		_portletDependencies = new HashSet<>();

		for (Dependency dependency : portletConfiguration.dependencies()) {
			_portletDependencies.add(
				new PortletDependency(
					dependency.name(), dependency.scope(),
					dependency.version()));
		}

		Multipart multipart = portletConfiguration.multipart();

		if (multipart.supported()) {
			_multiPartFileSizeThreshold = multipart.fileSizeThreshold();
			_multiPartLocation = multipart.location();
			_multiPartMaxFileSize = multipart.maxFileSize();
			_multiPartMaxRequestSize = multipart.maxRequestSize();
			_multiPartSupported = true;
		}
		else {
			_multiPartFileSizeThreshold = 0;
			_multiPartLocation = null;
			_multiPartMaxFileSize = -1L;
			_multiPartMaxRequestSize = -1L;
			_multiPartSupported = false;
		}

		_preferences = new HashMap<>();

		for (javax.portlet.annotations.Preference preference :
				portletConfiguration.prefs()) {

			_preferences.put(
				preference.name(),
				new Preference(
					Arrays.asList(preference.values()),
					preference.isReadOnly()));
		}

		_securityRoleRefs = new HashMap<>();

		for (SecurityRoleRef securityRoleRef :
				portletConfiguration.roleRefs()) {

			_securityRoleRefs.put(
				securityRoleRef.roleName(), securityRoleRef.roleLink());
		}

		_shortTitles = new HashMap<>();

		for (LocaleString localeString : portletConfiguration.shortTitle()) {
			_shortTitles.put(localeString.locale(), localeString.value());
		}

		_supportedLocales = new LinkedHashSet<>(
			Arrays.asList(portletConfiguration.supportedLocales()));

		_supportedPortletModes = new HashMap<>();
		_supportedWindowStates = new HashMap<>();

		for (Supports supports : _portletConfiguration.supports()) {
			_supportedPortletModes.put(
				supports.mimeType(),
				new LinkedHashSet<>(Arrays.asList(supports.portletModes())));
			_supportedWindowStates.put(
				supports.mimeType(),
				new LinkedHashSet<>(Arrays.asList(supports.windowStates())));
		}

		_supportedPublicRenderParameters = new LinkedHashSet<>(
			Arrays.asList(portletConfiguration.publicParams()));

		_titles = new HashMap<>();

		for (LocaleString localeString : portletConfiguration.title()) {
			_titles.put(localeString.locale(), localeString.value());
		}
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
		return _portletConfiguration.cacheExpirationTime();
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
	public Map<String, String> getLiferayConfiguration() {
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
		return _portletConfiguration.portletName();
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
		return _portletConfiguration.resourceBundle();
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
		return _portletConfiguration.asyncSupported();
	}

	@Override
	public boolean isMultiPartSupported() {
		return _multiPartSupported;
	}

	private final Map<String, List<String>> _containerRuntimeOptions;
	private final Map<String, String> _descriptions;
	private final String _displayCategory;
	private final Map<String, String> _displayNames;
	private final Map<String, String> _initParams;
	private final Map<String, String> _keywords;
	private final Map<String, String> _liferayConfiguration;
	private final int _multiPartFileSizeThreshold;
	private final String _multiPartLocation;
	private final long _multiPartMaxFileSize;
	private final long _multiPartMaxRequestSize;
	private final boolean _multiPartSupported;
	private final String _portletClassName;
	private final PortletConfiguration _portletConfiguration;
	private final Set<PortletDependency> _portletDependencies;
	private final Map<String, Preference> _preferences;
	private final String _preferencesValidator;
	private final Map<String, String> _securityRoleRefs;
	private final Map<String, String> _shortTitles;
	private final Set<String> _supportedLocales;
	private final Map<String, Set<String>> _supportedPortletModes;
	private final Set<String> _supportedPublicRenderParameters;
	private final Map<String, Set<String>> _supportedWindowStates;
	private final Map<String, String> _titles;

}