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

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public class BeanPortletMergedImpl extends BaseBeanPortletImpl {

	public BeanPortletMergedImpl(
		BeanPortlet annotatedBeanPortlet, BeanPortlet descriptorBeanPortlet) {

		super(
			_mergeBeanMethods(
				annotatedBeanPortlet.getBeanMethods(),
				descriptorBeanPortlet.getBeanMethods()));

		if (annotatedBeanPortlet.isAsyncSupported() ||
			descriptorBeanPortlet.isAsyncSupported()) {

			_asyncSupported = true;
		}
		else {
			_asyncSupported = false;
		}

		_containerRuntimeOptions = new HashMap<>(
			annotatedBeanPortlet.getContainerRuntimeOptions());

		Map<String, List<String>> descriptorContainerRuntimeOptions =
			descriptorBeanPortlet.getContainerRuntimeOptions();

		for (Map.Entry<String, List<String>> entry :
				descriptorContainerRuntimeOptions.entrySet()) {

			if (entry.getValue() != null) {
				String optionName = entry.getKey();

				List<String> existingOptionValues =
					_containerRuntimeOptions.get(optionName);

				if (existingOptionValues == null) {
					_containerRuntimeOptions.put(optionName, entry.getValue());
				}
				else {
					List<String> mergedOptions = new ArrayList<>(
						existingOptionValues);

					mergedOptions.addAll(entry.getValue());

					_containerRuntimeOptions.put(optionName, mergedOptions);
				}
			}
		}

		_descriptions = new HashMap<>(annotatedBeanPortlet.getDescriptions());

		_descriptions.putAll(descriptorBeanPortlet.getDescriptions());

		if (descriptorBeanPortlet.getDisplayCategory() == null) {
			_displayCategory = annotatedBeanPortlet.getDisplayCategory();
		}
		else {
			_displayCategory = descriptorBeanPortlet.getDisplayCategory();
		}

		_displayNames = new HashMap<>(annotatedBeanPortlet.getDisplayNames());

		_displayNames.putAll(descriptorBeanPortlet.getDisplayNames());

		if (descriptorBeanPortlet.getExpirationCache() <= 0) {
			_expirationCache = annotatedBeanPortlet.getExpirationCache();
		}
		else {
			_expirationCache = descriptorBeanPortlet.getExpirationCache();
		}

		_initParams = new HashMap<>(annotatedBeanPortlet.getInitParams());

		_initParams.putAll(descriptorBeanPortlet.getInitParams());

		_keywords = new HashMap<>(annotatedBeanPortlet.getKeywords());

		_keywords.putAll(descriptorBeanPortlet.getKeywords());

		_liferayConfiguration = new HashMap<>(
			annotatedBeanPortlet.getLiferayConfiguration());

		Map<String, Set<String>> descriptorLiferayConfiguration =
			descriptorBeanPortlet.getLiferayConfiguration();

		if (descriptorLiferayConfiguration != null) {
			_liferayConfiguration.putAll(descriptorLiferayConfiguration);
		}

		if (annotatedBeanPortlet.isMultiPartSupported() ||
			descriptorBeanPortlet.isMultiPartSupported()) {

			_multiPartSupported = true;

			if (descriptorBeanPortlet.getMultiPartFileSizeThreshold() <= 0) {
				_multiPartFileSizeThreshold =
					annotatedBeanPortlet.getMultiPartFileSizeThreshold();
			}
			else {
				_multiPartFileSizeThreshold =
					descriptorBeanPortlet.getMultiPartFileSizeThreshold();
			}

			if (Validator.isNull(
					descriptorBeanPortlet.getMultiPartLocation())) {

				_multiPartLocation =
					annotatedBeanPortlet.getMultiPartLocation();
			}
			else {
				_multiPartLocation =
					descriptorBeanPortlet.getMultiPartLocation();
			}

			if (descriptorBeanPortlet.getMultiPartMaxFileSize() <= 0) {
				_multiPartMaxFileSize =
					annotatedBeanPortlet.getMultiPartMaxFileSize();
			}
			else {
				_multiPartMaxFileSize =
					descriptorBeanPortlet.getMultiPartMaxFileSize();
			}

			if (descriptorBeanPortlet.getMultiPartMaxRequestSize() <= 0) {
				_multiPartMaxRequestSize =
					annotatedBeanPortlet.getMultiPartMaxRequestSize();
			}
			else {
				_multiPartMaxRequestSize =
					descriptorBeanPortlet.getMultiPartMaxRequestSize();
			}
		}
		else {
			_multiPartFileSizeThreshold = 0;
			_multiPartLocation = null;
			_multiPartMaxFileSize = -1L;
			_multiPartMaxRequestSize = -1L;
			_multiPartSupported = false;
		}

		if (Validator.isNull(descriptorBeanPortlet.getPortletClassName())) {
			_portletClassName = annotatedBeanPortlet.getPortletClassName();
		}
		else {
			_portletClassName = descriptorBeanPortlet.getPortletClassName();
		}

		_portletDependencies = new HashSet<>(
			annotatedBeanPortlet.getPortletDependencies());

		_portletDependencies.addAll(
			descriptorBeanPortlet.getPortletDependencies());

		if (Validator.isNull(descriptorBeanPortlet.getPortletName())) {
			_portletName = annotatedBeanPortlet.getPortletName();
		}
		else {
			_portletName = descriptorBeanPortlet.getPortletName();
		}

		_preferences = new HashMap<>(annotatedBeanPortlet.getPreferences());

		_preferences.putAll(descriptorBeanPortlet.getPreferences());

		if (Validator.isNull(descriptorBeanPortlet.getPreferencesValidator())) {
			_preferencesValidator =
				annotatedBeanPortlet.getPreferencesValidator();
		}
		else {
			_preferencesValidator =
				descriptorBeanPortlet.getPreferencesValidator();
		}

		if (Validator.isNull(descriptorBeanPortlet.getResourceBundle())) {
			_resourceBundle = annotatedBeanPortlet.getResourceBundle();
		}
		else {
			_resourceBundle = descriptorBeanPortlet.getResourceBundle();
		}

		_securityRoleRefs = new HashMap<>(
			annotatedBeanPortlet.getSecurityRoleRefs());

		_securityRoleRefs.putAll(descriptorBeanPortlet.getSecurityRoleRefs());

		_shortTitles = new HashMap<>(annotatedBeanPortlet.getShortTitles());

		_shortTitles.putAll(descriptorBeanPortlet.getShortTitles());

		_supportedLocales = new LinkedHashSet<>(
			annotatedBeanPortlet.getSupportedLocales());

		_supportedLocales.addAll(descriptorBeanPortlet.getSupportedLocales());

		_supportedPortletModes = new HashMap<>(
			annotatedBeanPortlet.getSupportedPortletModes());

		_supportedPortletModes.putAll(
			descriptorBeanPortlet.getSupportedPortletModes());

		_supportedProcessingEvents = new LinkedHashSet<>(
			annotatedBeanPortlet.getSupportedProcessingEvents());

		_supportedProcessingEvents.addAll(
			descriptorBeanPortlet.getSupportedProcessingEvents());

		_supportedPublicRenderParameters = new LinkedHashSet<>(
			annotatedBeanPortlet.getSupportedPublicRenderParameters());

		_supportedPublicRenderParameters.addAll(
			descriptorBeanPortlet.getSupportedPublicRenderParameters());

		_supportedPublishingEvents = new LinkedHashSet<>(
			annotatedBeanPortlet.getSupportedPublishingEvents());

		_supportedPublishingEvents.addAll(
			descriptorBeanPortlet.getSupportedPublishingEvents());

		_supportedWindowStates = new HashMap<>(
			annotatedBeanPortlet.getSupportedWindowStates());

		_supportedWindowStates.putAll(
			descriptorBeanPortlet.getSupportedWindowStates());

		_titles = new HashMap<>(annotatedBeanPortlet.getTitles());

		_titles.putAll(descriptorBeanPortlet.getTitles());
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
	public Set<QName> getSupportedProcessingEvents() {
		return _supportedProcessingEvents;
	}

	@Override
	public Set<String> getSupportedPublicRenderParameters() {
		return _supportedPublicRenderParameters;
	}

	@Override
	public Set<QName> getSupportedPublishingEvents() {
		return _supportedPublishingEvents;
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

	private static Set<BeanMethod> _mergeBeanMethods(
		Map<MethodType, List<BeanMethod>> annotatedBeanMethods,
		Map<MethodType, List<BeanMethod>> descriptorBeanMethods) {

		Set<BeanMethod> beanMethods = new HashSet<>();

		for (Map.Entry<MethodType, List<BeanMethod>>
				entry: annotatedBeanMethods.entrySet()) {

			beanMethods.addAll(entry.getValue());
		}

		for (Map.Entry<MethodType, List<BeanMethod>>
				entry: descriptorBeanMethods.entrySet()) {

			beanMethods.addAll(entry.getValue());
		}

		return beanMethods;
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
	private final Set<QName> _supportedProcessingEvents;
	private final Set<String> _supportedPublicRenderParameters;
	private final Set<QName> _supportedPublishingEvents;
	private final Map<String, Set<String>> _supportedWindowStates;
	private final Map<String, String> _titles;

}