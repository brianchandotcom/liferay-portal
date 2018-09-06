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
import com.liferay.bean.portlet.cdi.extension.internal.PortletDictionary;
import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import javax.portlet.annotations.PortletApplication;
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
		PortletApplication portletApplication,
		PortletConfiguration portletConfiguration,
		LiferayPortletConfiguration liferayPortletConfiguration,
		String portletClassName) {

		super(new BeanAppAnnotationImpl(portletApplication));

		_portletConfiguration = portletConfiguration;
		_portletClassName = portletClassName;

		String[] propertyNames = null;

		if (liferayPortletConfiguration != null) {
			propertyNames = liferayPortletConfiguration.properties();
		}

		if ((propertyNames == null) || (propertyNames.length == 0)) {
			_liferayPortletConfigurationProperties = Collections.emptyMap();
		}
		else {
			_liferayPortletConfigurationProperties = new HashMap<>();

			for (String propertyName : propertyNames) {
				String propertyValue = null;

				int equalsPos = propertyName.indexOf("=");

				if (equalsPos > 0) {
					propertyName = propertyName.substring(0, equalsPos);

					propertyValue = propertyName.substring(equalsPos + 1);
				}

				_liferayPortletConfigurationProperties.put(
					propertyName, propertyValue);
			}
		}

		for (Dependency dependency : portletConfiguration.dependencies()) {
			addPortletDependency(
				new PortletDependency(
					dependency.name(), dependency.scope(),
					dependency.version()));
		}
	}

	@Override
	public String getPortletClassName() {
		return _portletClassName;
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
	public Dictionary<String, Object> toDictionary(String portletId) {
		PortletDictionary portletDictionary =
			(PortletDictionary)super.toDictionary(portletId);

		portletDictionary.put(
			"javax.portlet.async-supported",
			_portletConfiguration.asyncSupported());

		BeanApp beanApp = getBeanApp();

		Map<String, List<String>> containerRuntimeOptions = new HashMap<>(
			beanApp.getContainerRuntimeOptions());

		for (RuntimeOption runtimeOption :
				_portletConfiguration.runtimeOptions()) {

			containerRuntimeOptions.put(
				runtimeOption.name(), Arrays.asList(runtimeOption.values()));
		}

		for (Map.Entry<String, List<String>> entry :
				containerRuntimeOptions.entrySet()) {

			portletDictionary.put(
				"javax.portlet.container-runtime-option.".concat(
					entry.getKey()),
				entry.getValue());
		}

		portletDictionary.put(
			"javax.portlet.expiration-cache",
			_portletConfiguration.cacheExpirationTime());

		for (InitParameter initParameter : _portletConfiguration.initParams()) {
			portletDictionary.putIfNotNull(
				"javax.portlet.init-param." + initParameter.name(),
				initParameter.value());
		}

		portletDictionary.putIfNotNull(
			"javax.portlet.description",
			getEnglishText(_portletConfiguration.description()));

		portletDictionary.putIfNotNull(
			"javax.portlet.display-name",
			getEnglishText(_portletConfiguration.displayName()));

		portletDictionary.putIfNotNull(
			"javax.portlet.info.keywords",
			getEnglishText(_portletConfiguration.keywords()));

		portletDictionary.putIfNotNull(
			"javax.portlet.info.short-title",
			getEnglishText(_portletConfiguration.shortTitle()));

		portletDictionary.put(
			"javax.portlet.info.title",
			getEnglishText(_portletConfiguration.title()), getPortletName());

		List<String> supportedPortletModes = new ArrayList<>();

		Set<String> applicationSupportedPortletModes = new HashSet<>(
			getLiferayPortletModes());

		applicationSupportedPortletModes.addAll(
			beanApp.getCustomPortletModes());

		for (Supports supports : _portletConfiguration.supports()) {
			StringBundler portletModesSB = new StringBundler();

			String[] portletModes = supports.portletModes();

			boolean first = true;

			for (String portletMode : portletModes) {
				if (applicationSupportedPortletModes.contains(portletMode)) {
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

		portletDictionary.putIfNotEmpty(
			"javax.portlet.portlet-mode", supportedPortletModes);

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

		portletDictionary.putIfNotNull(
			"javax.portlet.preferences", sb.toString());

		portletDictionary.putIfNotEmpty(
			"javax.portlet.resource-bundle",
			_portletConfiguration.resourceBundle());

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

		portletDictionary.putIfNotEmpty(
			"javax.portlet.security-role-ref", securityRoleRefSB.toString());

		portletDictionary.put(
			"javax.portlet.supported-locale",
			_portletConfiguration.supportedLocales());

		List<String> supportedPublicRenderParameters = new ArrayList<>();

		for (String identifier : _portletConfiguration.publicParams()) {
			supportedPublicRenderParameters.add(
				toNameValuePair(
					identifier,
					getPublicRenderParameterNamespaceURI(identifier)));
		}

		portletDictionary.put(
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

		portletDictionary.putIfNotEmpty(
			"javax.portlet.window-state", supportedWindowStates);

		portletDictionary.putAll(_liferayPortletConfigurationProperties);
		portletDictionary.putAll(getLiferayConfiguration());

		return portletDictionary;
	}

	protected String getEnglishText(LocaleString[] localeStrings) {
		String english = Locale.ENGLISH.getLanguage();

		for (LocaleString localeString : localeStrings) {
			if (english.equals(localeString.locale())) {
				return localeString.value();
			}
		}

		return null;
	}

	private final Map<String, String> _liferayPortletConfigurationProperties;
	private final String _portletClassName;
	private final PortletConfiguration _portletConfiguration;

}