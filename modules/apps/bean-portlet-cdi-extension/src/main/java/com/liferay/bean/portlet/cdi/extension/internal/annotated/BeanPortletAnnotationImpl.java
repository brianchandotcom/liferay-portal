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
import com.liferay.bean.portlet.cdi.extension.internal.MethodType;
import com.liferay.bean.portlet.cdi.extension.internal.PortletDependency;
import com.liferay.bean.portlet.cdi.extension.internal.Preference;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.Dependency;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.InitParameter;
import javax.portlet.annotations.LocaleString;
import javax.portlet.annotations.Multipart;
import javax.portlet.annotations.PortletConfiguration;
import javax.portlet.annotations.PortletQName;
import javax.portlet.annotations.RuntimeOption;
import javax.portlet.annotations.SecurityRoleRef;
import javax.portlet.annotations.Supports;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 * @author Raymond Augé
 */
public class BeanPortletAnnotationImpl extends BaseBeanPortletImpl {

	public BeanPortletAnnotationImpl(
		String portletClassName, PortletConfiguration portletConfiguration,
		LiferayPortletConfiguration liferayPortletConfiguration,
		Map<String, String> descriptorLiferayConfiguration,
		String descriptorDisplayCategory) {

		_portletClassName = portletClassName;
		_portletConfiguration = portletConfiguration;

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

		_supportedLocales = new HashSet<>(
			Arrays.asList(portletConfiguration.supportedLocales()));

		_supportedPortletModes = new HashMap<>();
		_supportedWindowStates = new HashMap<>();

		for (Supports supports : _portletConfiguration.supports()) {
			_supportedPortletModes.put(
				supports.mimeType(),
				new HashSet<>(Arrays.asList(supports.portletModes())));
			_supportedWindowStates.put(
				supports.mimeType(),
				new HashSet<>(Arrays.asList(supports.windowStates())));
		}

		_supportedPublicRenderParameters = new HashSet<>(
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
	public Set<QName> getSupportedProcessingEvents() {

		// Must use lazy-initialization since it is necessary for all the
		// bean methods to be added first, which happens after construction.

		if (_supportedProcessingEvents == null) {
			_supportedProcessingEvents = new HashSet<>();

			Map<MethodType, List<BeanMethod>> beanMethods = getBeanMethods();

			List<BeanMethod> eventBeanMethods = beanMethods.get(
				MethodType.EVENT);

			if (eventBeanMethods != null) {
				for (BeanMethod beanMethod : eventBeanMethods) {
					Method beanEventMethod = beanMethod.getMethod();

					EventMethod eventMethod = beanEventMethod.getAnnotation(
						EventMethod.class);

					if (eventMethod == null) {
						continue;
					}

					for (PortletQName portletQName :
							eventMethod.processingEvents()) {

						_supportedProcessingEvents.add(
							new QName(
								portletQName.namespaceURI(),
								portletQName.localPart()));
					}
				}
			}
		}

		return _supportedProcessingEvents;
	}

	@Override
	public Set<String> getSupportedPublicRenderParameters() {
		return _supportedPublicRenderParameters;
	}

	@Override
	public Set<QName> getSupportedPublishingEvents() {

		// Must use lazy-initialization since it is necessary for all the
		// bean methods to be added first, which happens after construction.

		if (_supportedPublishingEvents == null) {
			_supportedPublishingEvents = new HashSet<>();

			Map<MethodType, List<BeanMethod>> beanMethods = getBeanMethods();

			List<BeanMethod> actionMethods = beanMethods.get(MethodType.ACTION);

			if (actionMethods != null) {
				for (BeanMethod beanMethod : actionMethods) {
					Method method = beanMethod.getMethod();

					ActionMethod actionMethod = method.getAnnotation(
						ActionMethod.class);

					if (actionMethod == null) {
						continue;
					}

					for (PortletQName portletQName :
							actionMethod.publishingEvents()) {

						_supportedPublishingEvents.add(
							new QName(
								portletQName.namespaceURI(),
								portletQName.localPart()));
					}
				}
			}

			List<BeanMethod> eventMethods = beanMethods.get(MethodType.EVENT);

			if (eventMethods != null) {
				for (BeanMethod beanMethod : eventMethods) {
					Method method = beanMethod.getMethod();

					EventMethod eventMethod = method.getAnnotation(
						EventMethod.class);

					if (eventMethod == null) {
						continue;
					}

					for (PortletQName portletQName :
							eventMethod.publishingEvents()) {

						_supportedPublishingEvents.add(
							new QName(
								portletQName.namespaceURI(),
								portletQName.localPart()));
					}
				}
			}
		}

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
	private final Map<String, String> _securityRoleRefs;
	private final Map<String, String> _shortTitles;
	private final Set<String> _supportedLocales;
	private final Map<String, Set<String>> _supportedPortletModes;
	private Set<QName> _supportedProcessingEvents;
	private final Set<String> _supportedPublicRenderParameters;
	private Set<QName> _supportedPublishingEvents;
	private final Map<String, Set<String>> _supportedWindowStates;
	private final Map<String, String> _titles;

}