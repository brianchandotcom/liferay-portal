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
import com.liferay.bean.portlet.cdi.extension.internal.BeanApp;
import com.liferay.bean.portlet.cdi.extension.internal.PortletDependency;
import com.liferay.bean.portlet.cdi.extension.internal.URLGenerationListener;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.annotations.Dependency;
import javax.portlet.annotations.InitParameter;
import javax.portlet.annotations.LocaleString;
import javax.portlet.annotations.Multipart;
import javax.portlet.annotations.PortletConfiguration;
import javax.portlet.annotations.Preference;
import javax.portlet.annotations.RuntimeOption;
import javax.portlet.annotations.SecurityRoleRef;
import javax.portlet.annotations.Supports;

/**
 * @author Neil Griffin
 * @author Raymond Augé
 */
public class BeanPortletAnnotationImpl extends BaseBeanPortletImpl {

	public BeanPortletAnnotationImpl(
		String portletClassName, PortletConfiguration portletConfiguration,
		LiferayPortletConfiguration liferayPortletConfiguration,
		Map<String, String> descriptorLiferayConfiguration,
		String descriptorDisplayCategory,
		List<URLGenerationListener> urlGenerationListeners, BeanApp beanApp) {

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

		_portletDependencies = new HashSet<>();

		for (Dependency dependency : portletConfiguration.dependencies()) {
			_portletDependencies.add(
				new PortletDependency(
					dependency.name(), dependency.scope(),
					dependency.version()));
		}

		_portletModes = new HashSet<>(liferayPortletModes);

		_portletModes.addAll(beanApp.getCustomPortletModes());
	}

	@Override
	public String getDisplayCategory() {
		return _displayCategory;
	}

	@Override
	public Map<String, String> getLiferayConfiguration() {
		return _liferayConfiguration;
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
	public String getResourceBundle() {
		return _portletConfiguration.resourceBundle();
	}

	@Override
	public Dictionary<String, Object> toDictionary(BeanApp beanApp) {
		HashMapDictionary<String, Object> dictionary =
			(HashMapDictionary<String, Object>)toDictionary(beanApp);

		dictionary.put(
			"javax.portlet.async-supported",
			_portletConfiguration.asyncSupported());

		Map<String, List<String>> containerRuntimeOptions = new HashMap<>(
			beanApp.getContainerRuntimeOptions());

		for (RuntimeOption runtimeOption :
				_portletConfiguration.runtimeOptions()) {

			containerRuntimeOptions.put(
				runtimeOption.name(), Arrays.asList(runtimeOption.values()));
		}

		for (Map.Entry<String, List<String>> entry :
				containerRuntimeOptions.entrySet()) {

			dictionary.put(
				"javax.portlet.container-runtime-option.".concat(
					entry.getKey()),
				entry.getValue());
		}

		dictionary.put(
			"javax.portlet.expiration-cache",
			_portletConfiguration.cacheExpirationTime());

		for (InitParameter initParameter : _portletConfiguration.initParams()) {
			String value = initParameter.value();

			if (value != null) {
				dictionary.put(
					"javax.portlet.init-param.".concat(initParameter.name()),
					value);
			}
		}

		_putEnglishText(
			dictionary, "javax.portlet.description",
			_portletConfiguration.description());

		_putEnglishText(
			dictionary, "javax.portlet.display-name",
			_portletConfiguration.displayName());

		_putEnglishText(
			dictionary, "javax.portlet.info.keywords",
			_portletConfiguration.keywords());

		Multipart multipart = _portletConfiguration.multipart();

		if (multipart.supported()) {
			dictionary.put(
				"javax.portlet.multipart.file-size-threshold",
				multipart.fileSizeThreshold());

			dictionary.put(
				"javax.portlet.multipart.location", multipart.location());

			dictionary.put(
				"javax.portlet.multipart.max-file-size",
				multipart.maxFileSize());

			dictionary.put(
				"javax.portlet.multipart.max-request-size",
				multipart.maxRequestSize());
		}

		_putEnglishText(
			dictionary, "javax.portlet.info.short-title",
			_portletConfiguration.shortTitle());

		_putEnglishText(
			dictionary, "javax.portlet.info.title",
			_portletConfiguration.title(), getPortletName());

		List<String> supportedPortletModes = new ArrayList<>();

		for (Supports supports : _portletConfiguration.supports()) {
			StringBundler portletModesSB = new StringBundler();

			String[] portletModes = supports.portletModes();

			boolean first = true;

			for (String portletMode : portletModes) {
				if (_portletModes.contains(portletMode)) {
					if (first) {
						first = false;
					}
					else {
						portletModesSB.append(",");
					}

					portletModesSB.append(portletMode);
				}
			}

			supportedPortletModes.add(
				toNameValuePair(
					supports.mimeType(), portletModesSB.toString()));
		}

		if (!supportedPortletModes.isEmpty()) {
			dictionary.put("javax.portlet.portlet-mode", supportedPortletModes);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<portlet-preferences>");

		for (Preference preference : _portletConfiguration.prefs()) {
			sb.append("<preference>");
			sb.append("<name>");
			sb.append(preference.name());
			sb.append("</name>");

			for (String value : preference.values()) {
				sb.append("<value>");
				sb.append(value);
				sb.append("</value>");
			}

			sb.append("<read-only>");
			sb.append(preference.isReadOnly());
			sb.append("</read-only>");

			sb.append("</preference>");
		}

		sb.append("</portlet-preferences>");

		dictionary.put("javax.portlet.preferences", sb.toString());

		String resourceBundle = _portletConfiguration.resourceBundle();

		if ((resourceBundle != null) && !resourceBundle.isEmpty()) {
			dictionary.put("javax.portlet.resource-bundle", resourceBundle);
		}

		StringBundler securityRoleRefSB = new StringBundler();

		boolean first = true;

		for (SecurityRoleRef securityRoleRef :
				_portletConfiguration.roleRefs()) {

			if (first) {
				first = false;
			}
			else {
				securityRoleRefSB.append(",");
			}

			securityRoleRefSB.append(securityRoleRef.roleName());
		}

		if (securityRoleRefSB.length() > 0) {
			dictionary.put(
				"javax.portlet.security-role-ref",
				securityRoleRefSB.toString());
		}

		dictionary.put(
			"javax.portlet.supported-locale",
			_portletConfiguration.supportedLocales());

		List<String> supportedPublicRenderParameters = new ArrayList<>();

		for (String identifier : _portletConfiguration.publicParams()) {
			supportedPublicRenderParameters.add(
				toNameValuePair(
					identifier,
					getPublicRenderParameterNamespaceURI(beanApp, identifier)));
		}

		dictionary.put(
			"javax.portlet.supported-public-render-parameter",
			supportedPublicRenderParameters);

		List<String> supportedWindowStates = new ArrayList<>();

		for (Supports supports : _portletConfiguration.supports()) {
			StringBundler windowStatesSB = new StringBundler();

			String[] windowStates = supports.windowStates();

			first = true;

			for (String windowState : windowStates) {
				if (first) {
					first = false;
				}
				else {
					windowStatesSB.append(",");
				}

				windowStatesSB.append(windowState);
			}

			supportedWindowStates.add(
				toNameValuePair(
					supports.mimeType(), windowStatesSB.toString()));
		}

		if (!supportedWindowStates.isEmpty()) {
			dictionary.put("javax.portlet.window-state", supportedWindowStates);
		}

		dictionary.putAll(_liferayConfiguration);

		return dictionary;
	}

	private static void _putEnglishText(
		Dictionary<String, Object> dictionary, String key,
		LocaleString[] localeStrings) {

		_putEnglishText(dictionary, key, localeStrings, null);
	}

	private static void _putEnglishText(
		Dictionary<String, Object> dictionary, String key,
		LocaleString[] localeStrings, String defaultValue) {

		for (LocaleString localeString : localeStrings) {
			if (_ENGLISH_EN.equals(localeString.locale())) {
				dictionary.put(key, localeString.value());

				return;
			}
		}

		if (defaultValue != null) {
			dictionary.put(key, defaultValue);
		}
	}

	private static final String _ENGLISH_EN = Locale.ENGLISH.getLanguage();

	private final String _displayCategory;
	private final Map<String, String> _liferayConfiguration;
	private final String _portletClassName;
	private final PortletConfiguration _portletConfiguration;
	private final Set<PortletDependency> _portletDependencies;
	private final Set<String> _portletModes;

}