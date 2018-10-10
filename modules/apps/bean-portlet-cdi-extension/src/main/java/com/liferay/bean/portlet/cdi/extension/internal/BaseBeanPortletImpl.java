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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.PortletQName;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public abstract class BaseBeanPortletImpl implements BeanPortlet {

	public BaseBeanPortletImpl(Set<BeanMethod> beanMethods) {
		for (BeanMethod beanMethod : beanMethods) {
			_addBeanMethod(beanMethod);
		}

		List<BeanMethod> eventBeanMethods = _beanMethods.get(MethodType.EVENT);

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

		List<BeanMethod> actionMethods = _beanMethods.get(MethodType.ACTION);

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

		List<BeanMethod> eventMethods = _beanMethods.get(MethodType.EVENT);

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

	@Override
	public Map<MethodType, List<BeanMethod>> getBeanMethods() {
		return _beanMethods;
	}

	@Override
	public Set<QName> getSupportedProcessingEvents() {
		return _supportedProcessingEvents;
	}

	@Override
	public Set<QName> getSupportedPublishingEvents() {
		return _supportedPublishingEvents;
	}

	public Dictionary<String, Object> toDictionary(BeanApp beanApp) {
		HashMapDictionary<String, Object> dictionary =
			new HashMapDictionary<>();

		// com.liferay

		String displayCategory = getDisplayCategory();

		if (displayCategory != null) {
			dictionary.put(
				"com.liferay.portlet.display-category", displayCategory);
		}

		Map<String, Set<String>> liferayConfiguration =
			getLiferayConfiguration();

		if (liferayConfiguration != null) {
			for (Map.Entry<String, Set<String>> entry :
					liferayConfiguration.entrySet()) {

				Set<String> value = entry.getValue();

				if (value.size() == 1) {
					Iterator<String> iterator = value.iterator();

					dictionary.put(entry.getKey(), iterator.next());

					continue;
				}

				dictionary.put(entry.getKey(), value);
			}
		}

		// javax.portlet.async-supported

		dictionary.put("javax.portlet.async-supported", isAsyncSupported());

		// javax.portlet.container-runtime-options

		Map<String, List<String>> containerRuntimeOptions = new HashMap<>(
			beanApp.getContainerRuntimeOptions());

		containerRuntimeOptions.putAll(getContainerRuntimeOptions());

		for (Map.Entry<String, List<String>> entry :
				containerRuntimeOptions.entrySet()) {

			dictionary.put(
				"javax.portlet.container-runtime-option.".concat(
					entry.getKey()),
				entry.getValue());
		}

		// javax.portlet.default-namespace

		String defaultNamespace = beanApp.getDefaultNamespace();

		if (defaultNamespace != null) {
			dictionary.put("javax.portlet.default-namespace", defaultNamespace);
		}

		// javax.portlet.dependency

		Set<PortletDependency> portletDependencies = getPortletDependencies();

		if (!portletDependencies.isEmpty()) {
			List<String> tokenizedPortletDependencies = new ArrayList<>();

			for (PortletDependency portletDependency : portletDependencies) {
				tokenizedPortletDependencies.add(portletDependency.toString());
			}

			dictionary.put(
				"javax.portlet.dependency", tokenizedPortletDependencies);
		}

		// javax.portlet.event-definition

		List<Event> events = beanApp.getEvents();

		List<String> eventDefinitions = new ArrayList<>();

		for (Event event : events) {
			QName eventQName = event.getQName();

			StringBundler eventDefinitionSB = new StringBundler(
				_toNameValuePair(
					eventQName.getLocalPart(), eventQName.getNamespaceURI()));

			String valueType = event.getValueType();

			if (valueType != null) {
				eventDefinitionSB.append(";");
				eventDefinitionSB.append(valueType);
			}

			for (QName aliasQName : event.getAliasQNames()) {
				eventDefinitionSB.append(",");
				eventDefinitionSB.append(
					_toNameValuePair(
						aliasQName.getLocalPart(),
						aliasQName.getNamespaceURI()));
			}

			eventDefinitions.add(eventDefinitionSB.toString());
		}

		if (!eventDefinitions.isEmpty()) {
			dictionary.put("javax.portlet.event-definition", eventDefinitions);
		}

		// javax.portlet.expiration-cache

		dictionary.put("javax.portlet.expiration-cache", getExpirationCache());

		// javax.portlet.init-param

		Map<String, String> initParams = getInitParams();

		for (Map.Entry<String, String> entry : initParams.entrySet()) {
			String value = entry.getValue();

			if (value != null) {
				dictionary.put(
					"javax.portlet.init-param." + entry.getKey(), value);
			}
		}

		// javax.portlet.description

		_putEnglishText(
			dictionary, "javax.portlet.description", getDescriptions());

		// javax.portlet.display-name

		_putEnglishText(
			dictionary, "javax.portlet.display-name", getDisplayNames());

		// javax.portlet.info.keywords

		_putEnglishText(
			dictionary, "javax.portlet.info.keywords", getKeywords());

		// javax.portlet.info.short-title

		_putEnglishText(
			dictionary, "javax.portlet.info.short-title", getShortTitles());

		// javax.portlet.info.title

		Map<String, String> titles = getTitles();

		if (titles.isEmpty()) {
			dictionary.put("javax.portlet.info.title", getPortletName());
		}
		else {
			_putEnglishText(
				dictionary, "javax.portlet.info.title", titles,
				getPortletName());
		}

		// javax.portlet.multipart

		if (isMultiPartSupported()) {
			dictionary.put(
				"javax.portlet.multipart.file-size-threshold",
				getMultiPartFileSizeThreshold());

			dictionary.put(
				"javax.portlet.multipart.location", getMultiPartLocation());

			dictionary.put(
				"javax.portlet.multipart.max-file-size",
				getMultiPartMaxFileSize());

			dictionary.put(
				"javax.portlet.multipart.max-request-size",
				getMultiPartMaxRequestSize());
		}

		// javax.portlet.portlet-mode

		Set<String> allPortletModes = new HashSet<>(liferayPortletModes);

		allPortletModes.addAll(beanApp.getCustomPortletModes());

		StringBundler portletModesSB = new StringBundler();

		List<String> supportedPortletModes = new ArrayList<>();

		Map<String, Set<String>> supportedPortletModesMap =
			getSupportedPortletModes();

		for (Map.Entry<String, Set<String>> entry :
				supportedPortletModesMap.entrySet()) {

			portletModesSB.append(entry.getKey());
			portletModesSB.append(";");

			Set<String> portletModes = entry.getValue();

			boolean first = true;

			for (String portletMode : portletModes) {
				if (allPortletModes.contains(portletMode)) {
					if (first) {
						first = false;
					}
					else {
						portletModesSB.append(",");
					}

					portletModesSB.append(portletMode);
				}
			}

			supportedPortletModes.add(portletModesSB.toString());
		}

		if (!supportedPortletModes.isEmpty()) {
			dictionary.put("javax.portlet.portlet-mode", supportedPortletModes);
		}

		// javax.portlet.preferences

		StringBundler portletPreferencesSB = new StringBundler();

		portletPreferencesSB.append("<?xml version=\"1.0\"?>");
		portletPreferencesSB.append("<portlet-preferences>");

		Map<String, Preference> preferences = getPreferences();

		for (Map.Entry<String, Preference> entry : preferences.entrySet()) {
			portletPreferencesSB.append("<preference>");
			portletPreferencesSB.append("<name>");
			portletPreferencesSB.append(entry.getKey());
			portletPreferencesSB.append("</name>");

			Preference preference = entry.getValue();

			for (String value : preference.getValues()) {
				portletPreferencesSB.append("<value>");
				portletPreferencesSB.append(value);
				portletPreferencesSB.append("</value>");
			}

			portletPreferencesSB.append("<read-only>");
			portletPreferencesSB.append(preference.isReadOnly());
			portletPreferencesSB.append("</read-only>");

			portletPreferencesSB.append("</preference>");
		}

		portletPreferencesSB.append("</portlet-preferences>");

		dictionary.put(
			"javax.portlet.preferences", portletPreferencesSB.toString());

		// javax.portlet.preferences-validator

		String preferencesValidator = getPreferencesValidator();

		if (preferencesValidator != null) {
			dictionary.put(
				"javax.portlet.preferences-validator", preferencesValidator);
		}

		// javax.portlet.resource-bundle

		if (Validator.isNotNull(getResourceBundle())) {
			dictionary.put(
				"javax.portlet.resource-bundle", getResourceBundle());
		}

		// javax.portlet.security-role-ref

		StringBundler roleNamesSB = new StringBundler();

		Map<String, String> securityRoleRefs = getSecurityRoleRefs();

		for (Map.Entry<String, String> entry : securityRoleRefs.entrySet()) {
			if (roleNamesSB.length() > 0) {
				roleNamesSB.append(",");
			}

			roleNamesSB.append(entry.getKey());
		}

		if (roleNamesSB.length() > 0) {
			dictionary.put(
				"javax.portlet.security-role-ref", roleNamesSB.toString());
		}

		// javax.portlet.supported-locale

		if (!getSupportedLocales().isEmpty()) {
			dictionary.put(
				"javax.portlet.supported-locale", getSupportedLocales());
		}

		List<String> supportedPublicRenderParameters = new ArrayList<>();

		for (String identifier : getSupportedPublicRenderParameters()) {
			supportedPublicRenderParameters.add(
				_toNameValuePair(
					identifier,
					_getPublicRenderParameterNamespaceURI(
						beanApp, identifier)));
		}

		// javax.portlet.supported-public-render-parameter

		dictionary.put(
			"javax.portlet.supported-public-render-parameter",
			supportedPublicRenderParameters);

		List<String> supportedWindowStates = new ArrayList<>();

		StringBundler windowStatesSB = new StringBundler();

		Map<String, Set<String>> supportedWindowStatesMap =
			getSupportedWindowStates();

		for (Map.Entry<String, Set<String>> entry :
				supportedWindowStatesMap.entrySet()) {

			windowStatesSB.append(entry.getKey());
			windowStatesSB.append(";");

			Set<String> windowStates = entry.getValue();

			boolean first = true;

			for (String windowState : windowStates) {
				if (first) {
					first = false;
				}
				else {
					windowStatesSB.append(",");
				}

				windowStatesSB.append(windowState);
			}

			supportedWindowStates.add(windowStatesSB.toString());
		}

		// javax.portlet.window-state

		if (!supportedWindowStatesMap.isEmpty()) {
			dictionary.put("javax.portlet.window-state", supportedWindowStates);
		}

		// javax.portlet.supported-processing-event

		Set<String> supportedProcessingEvents = new HashSet<>();

		for (QName qName : getSupportedProcessingEvents()) {
			supportedProcessingEvents.add(
				_toNameValuePair(
					qName.getLocalPart(), qName.getNamespaceURI()));
		}

		dictionary.put(
			"javax.portlet.supported-processing-event",
			supportedProcessingEvents);

		// javax.portlet.supported-publishing-event

		Set<String> supportedPublishingEvents = new HashSet<>();

		for (QName qName : getSupportedPublishingEvents()) {
			supportedPublishingEvents.add(
				_toNameValuePair(
					qName.getLocalPart(), qName.getNamespaceURI()));
		}

		if (!supportedPublishingEvents.isEmpty()) {
			dictionary.put(
				"javax.portlet.supported-publishing-event",
				supportedPublishingEvents);
		}

		if (!supportedPublishingEvents.isEmpty()) {
			dictionary.put(
				"javax.portlet.supported-publishing-event",
				supportedPublishingEvents);
		}

		if (!supportedProcessingEvents.isEmpty()) {
			dictionary.put(
				"javax.portlet.supported-processing-event",
				supportedProcessingEvents);
		}

		// javax.portlet.listener

		List<String> portletListeners = new ArrayList<>();

		for (Map.Entry<Integer, String> entry : beanApp.getPortletListeners()) {
			String listenerClassName = entry.getValue();

			String listener = listenerClassName.concat(
				StringPool.SEMICOLON).concat(String.valueOf(entry.getKey()));

			portletListeners.add(listener);
		}

		if (!portletListeners.isEmpty()) {
			dictionary.put("javax.portlet.listener", portletListeners);
		}

		// javax.portlet.version

		dictionary.put("javax.portlet.version", beanApp.getSpecVersion());

		return dictionary;
	}

	protected static final Set<String> liferayPortletModes =
		new HashSet<String>() {
			{
				try {
					for (Field field : LiferayPortletMode.class.getFields()) {
						if (Modifier.isStatic(field.getModifiers()) &&
							(field.getType() == PortletMode.class)) {

							PortletMode portletMode = (PortletMode)field.get(
								null);

							add(portletMode.toString());
						}
					}
				}
				catch (IllegalAccessException iae) {
					throw new ExceptionInInitializerError(iae);
				}
			}
		};

	private static void _putEnglishText(
		Dictionary<String, Object> dictionary, String key,
		Map<String, String> descriptions) {

		_putEnglishText(dictionary, key, descriptions, null);
	}

	private static void _putEnglishText(
		Dictionary<String, Object> dictionary, String key,
		Map<String, String> descriptions, String defaultValue) {

		for (Map.Entry<String, String> entry : descriptions.entrySet()) {
			String locale = entry.getKey();

			if ((locale != null) && locale.startsWith(_ENGLISH_EN)) {
				dictionary.put(key, entry.getValue());

				return;
			}
		}

		if (defaultValue != null) {
			dictionary.put(key, defaultValue);
		}
	}

	private void _addBeanMethod(BeanMethod beanMethod) {
		_beanMethods.compute(
			beanMethod.getType(),
			(methodType, beanMethods) -> {
				if (beanMethods == null) {
					beanMethods = new ArrayList<>();
				}

				if ((methodType == MethodType.HEADER) ||
					(methodType == MethodType.RENDER) ||
					(methodType == MethodType.SERVE_RESOURCE)) {

					int index = Collections.binarySearch(
						beanMethods, beanMethod);

					if (index < 0) {
						index = -index - 1;
					}

					beanMethods.add(index, beanMethod);
				}
				else {
					beanMethods.add(beanMethod);
				}

				return beanMethods;
			});
	}

	private String _getPublicRenderParameterNamespaceURI(
		BeanApp beanApp, String id) {

		Map<String, PublicRenderParameter> publicRenderParameterMap =
			beanApp.getPublicRenderParameters();

		PublicRenderParameter publicRenderParameter =
			publicRenderParameterMap.get(id);

		if (publicRenderParameter == null) {
			return XMLConstants.NULL_NS_URI;
		}

		QName qName = publicRenderParameter.getQName();

		if (qName == null) {
			return XMLConstants.NULL_NS_URI;
		}

		String namespaceURI = qName.getNamespaceURI();

		if (namespaceURI == null) {
			return XMLConstants.NULL_NS_URI;
		}

		return namespaceURI;
	}

	private String _toNameValuePair(String name, String value) {
		if (Validator.isNull(value)) {
			return name;
		}

		return name.concat(StringPool.SEMICOLON).concat(value);
	}

	private static final String _ENGLISH_EN = Locale.ENGLISH.getLanguage();

	private final EnumMap<MethodType, List<BeanMethod>> _beanMethods =
		new EnumMap<>(MethodType.class);
	private final Set<QName> _supportedProcessingEvents = new HashSet<>();
	private final Set<QName> _supportedPublishingEvents = new HashSet<>();

}