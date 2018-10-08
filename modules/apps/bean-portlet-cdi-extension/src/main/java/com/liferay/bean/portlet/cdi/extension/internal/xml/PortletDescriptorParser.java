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

package com.liferay.bean.portlet.cdi.extension.internal.xml;

import com.liferay.bean.portlet.cdi.extension.internal.BeanApp;
import com.liferay.bean.portlet.cdi.extension.internal.BeanFilter;
import com.liferay.bean.portlet.cdi.extension.internal.BeanMethod;
import com.liferay.bean.portlet.cdi.extension.internal.BeanPortlet;
import com.liferay.bean.portlet.cdi.extension.internal.Event;
import com.liferay.bean.portlet.cdi.extension.internal.PortletDependency;
import com.liferay.bean.portlet.cdi.extension.internal.Preference;
import com.liferay.bean.portlet.cdi.extension.internal.PublicRenderParameter;
import com.liferay.bean.portlet.cdi.extension.internal.URLGenerationListener;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletQNameUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.spi.BeanManager;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.osgi.framework.Bundle;

/**
 * @author Neil Griffin
 */
public class PortletDescriptorParser {

	public static PortletDescriptor parse(
			Bundle bundle, URL portletDescriptorURL, BeanManager beanManager,
			Map<String, Set<BeanMethod>> portletBeanMethods,
			Set<BeanMethod> wildcardBeanMethods,
			Map<String, String> preferencesValidators,
			Set<String> wildcardPreferencesValidators,
			Map<String, String> displayDescriptorCategories,
			Map<String, Map<String, Set<String>>> liferayConfigurations)
		throws DocumentException, IOException {

		Document document = UnsecureSAXReaderUtil.read(
			portletDescriptorURL, _PORTLET_XML_VALIDATE);

		Element rootElement = document.getRootElement();

		BeanApp beanApp = _readBeanApp(rootElement);

		List<BeanFilter> beanFilters = _readBeanFilters(bundle, rootElement);

		List<BeanPortlet> beanPortlets = _readBeanPortlets(
			bundle, rootElement, beanApp, beanManager, portletBeanMethods,
			wildcardBeanMethods, preferencesValidators,
			wildcardPreferencesValidators, displayDescriptorCategories,
			liferayConfigurations);

		return new PortletDescriptor(beanApp, beanFilters, beanPortlets);
	}

	private static String _getLang(Element element) {
		String lang = element.attributeValue("lang");

		if (lang == null) {
			return _ENGLISH_EN;
		}

		return lang;
	}

	private static boolean _isCustomPortletMode(String portletModeName) {
		return PortalUtil.isCustomPortletMode(new PortletMode(portletModeName));
	}

	private static BeanApp _readBeanApp(Element rootElement) {
		String specVersion = GetterUtil.get(
			rootElement.attributeValue("version"), "3.0");

		String defaultNamespace = rootElement.elementText("default-namespace");

		List<Event> events = new ArrayList<>();

		for (Element eventDefinitionElement :
				rootElement.elements("event-definition")) {

			List<QName> aliasQNames = new ArrayList<>();

			Element qNameElement = eventDefinitionElement.element("qname");
			Element nameElement = eventDefinitionElement.element("name");
			String valueType = eventDefinitionElement.elementText("value-type");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, defaultNamespace);

			List<Element> aliases = eventDefinitionElement.elements("alias");

			for (Element alias : aliases) {
				qName = PortletQNameUtil.getQName(
					alias, null, defaultNamespace);

				aliasQNames.add(qName);
			}

			events.add(new EventDescriptorImpl(qName, valueType, aliasQNames));
		}

		Map<String, PublicRenderParameter> publicRenderParameters =
			new HashMap<>();

		for (Element publicRenderParameterElement :
				rootElement.elements("public-render-parameter")) {

			String identifier = publicRenderParameterElement.elementText(
				"identifier");
			Element qNameElement = publicRenderParameterElement.element(
				"qname");
			Element nameElement = publicRenderParameterElement.element("name");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, defaultNamespace);

			PublicRenderParameter publicRenderParameter =
				new PublicRenderParameterDescriptorImpl(identifier, qName);

			publicRenderParameters.put(identifier, publicRenderParameter);
		}

		Map<String, List<String>> containerRuntimeOptions = new HashMap<>();

		for (Element containerRuntimeOptionElement :
				rootElement.elements("container-runtime-option")) {

			String name = GetterUtil.getString(
				containerRuntimeOptionElement.elementText("name"));

			List<String> values = new ArrayList<>();

			for (Element valueElement :
					containerRuntimeOptionElement.elements("value")) {

				values.add(valueElement.getTextTrim());
			}

			containerRuntimeOptions.put(name, values);
		}

		Set<String> validCustomPortletModes = new HashSet<>();

		for (Element customPortletModeElement :
				rootElement.elements("custom-portlet-mode")) {

			String portletMode = StringUtil.toLowerCase(
				customPortletModeElement.elementTextTrim("portlet-mode"));

			boolean portalManaged = Boolean.valueOf(
				customPortletModeElement.elementText("portal-managed"));

			if (_isCustomPortletMode(portletMode) && portalManaged) {
				continue;
			}

			validCustomPortletModes.add(portletMode);
		}

		List<URLGenerationListener> urlGenerationListeners = new ArrayList<>();

		for (Element listenerElement : rootElement.elements("listener")) {
			String listenerName = listenerElement.elementText("listener-name");
			String listenerClassName = listenerElement.elementText(
				"listener-class");
			int ordinal = GetterUtil.getInteger(
				listenerElement.elementText("ordinal"));

			urlGenerationListeners.add(
				new URLGenerationListener(
					listenerName, ordinal, listenerClassName));
		}

		return new BeanAppDescriptorImpl(
			specVersion, defaultNamespace, events, publicRenderParameters,
			containerRuntimeOptions, validCustomPortletModes,
			urlGenerationListeners);
	}

	private static BeanFilter _readBeanFilter(
		Bundle bundle, Element filterElement,
		Map<String, Set<String>> filterMappings) {

		String filterName = filterElement.elementText("filter-name");
		String filterClassName = filterElement.elementText("filter-class");

		Class<?> filterClass = null;

		try {
			filterClass = bundle.loadClass(filterClassName);
		}
		catch (ClassNotFoundException cnfe) {
			_log.error("Unable to load filter-class " + filterClassName);

			return null;
		}

		int ordinal = GetterUtil.getInteger(
			filterElement.elementText("ordinal"));

		Set<String> lifecycles = new LinkedHashSet<>();

		for (Element lifecycleElement : filterElement.elements("lifecycle")) {
			lifecycles.add(lifecycleElement.getText());
		}

		Map<String, String> initParams = new HashMap<>();

		for (Element initParamElement : filterElement.elements("init-param")) {
			initParams.put(
				initParamElement.elementText("name"),
				initParamElement.elementText("value"));
		}

		return new BeanFilterDescriptorImpl(
			filterName, filterClass, ordinal, filterMappings.get(filterName),
			lifecycles, initParams);
	}

	private static List<BeanFilter> _readBeanFilters(
		Bundle bundle, Element rootElement) {

		List<BeanFilter> beanFilters = new ArrayList<>();

		Map<String, Set<String>> filterMappings = new HashMap<>();

		for (Element filterMappingElement :
				rootElement.elements("filter-mapping")) {

			String filterName = filterMappingElement.elementText("filter-name");

			Set<String> portletNames = new HashSet<>();

			for (Element portletNameElement :
					filterMappingElement.elements("portlet-name")) {

				portletNames.add(portletNameElement.getTextTrim());
			}

			filterMappings.put(filterName, portletNames);
		}

		for (Element filterElement : rootElement.elements("filter")) {
			beanFilters.add(
				_readBeanFilter(bundle, filterElement, filterMappings));
		}

		return beanFilters;
	}

	private static BeanPortlet _readBeanPortlet(
		Bundle bundle, Element portletElement, BeanApp beanApp,
		BeanManager beanManager,
		Map<String, Set<BeanMethod>> portletBeanMethods,
		Set<BeanMethod> wildcardBeanMethods,
		Map<String, String> preferencesValidators,
		Set<String> wildcardPreferencesValidators,
		Map<String, String> displayDescriptorCategories,
		Map<String, Map<String, Set<String>>> liferayConfigurations) {

		String portletName = portletElement.elementText("portlet-name");

		Map<String, String> displayNames = new HashMap<>();

		for (Element displayNameElement :
				portletElement.elements("display-name")) {

			displayNames.put(
				_getLang(displayNameElement),
				GetterUtil.getString(displayNameElement.getText()));
		}

		String portletClassName = GetterUtil.getString(
			portletElement.elementText("portlet-class"));

		Map<String, String> initParams = new HashMap<>();

		for (Element initParamElement : portletElement.elements("init-param")) {
			initParams.put(
				initParamElement.elementText("name"),
				initParamElement.elementText("value"));
		}

		int expirationCache = 0;

		Element expirationCacheElement = portletElement.element(
			"expiration-cache");

		if (expirationCacheElement != null) {
			expirationCache = GetterUtil.getInteger(
				expirationCacheElement.getText());
		}

		Map<String, Set<String>> supportedPortletModes = new HashMap<>();
		Map<String, Set<String>> supportedWindowStates = new HashMap<>();
		Set<String> validCustomPortletModes = beanApp.getCustomPortletModes();

		for (Element supportsElement : portletElement.elements("supports")) {
			String mimeType = supportsElement.elementText("mime-type");

			Set<String> mimeTypePortletModes = new HashSet<>();

			mimeTypePortletModes.add(
				StringUtil.toLowerCase(PortletMode.VIEW.toString()));

			for (Element portletModeElement :
					supportsElement.elements("portlet-mode")) {

				String portletMode = StringUtil.toLowerCase(
					portletModeElement.getTextTrim());

				if (_isCustomPortletMode(portletMode) &&
					!validCustomPortletModes.contains(portletMode)) {

					continue;
				}

				mimeTypePortletModes.add(portletMode);
			}

			supportedPortletModes.put(mimeType, mimeTypePortletModes);

			Set<String> mimeTypeWindowStates = new HashSet<>();

			mimeTypeWindowStates.add(
				StringUtil.toLowerCase(WindowState.NORMAL.toString()));

			List<Element> windowStateElements = supportsElement.elements(
				"window-state");

			if (windowStateElements.isEmpty()) {
				mimeTypeWindowStates.add(
					StringUtil.toLowerCase(WindowState.MAXIMIZED.toString()));
				mimeTypeWindowStates.add(
					StringUtil.toLowerCase(WindowState.MINIMIZED.toString()));
				mimeTypeWindowStates.add(
					StringUtil.toLowerCase(
						LiferayWindowState.EXCLUSIVE.toString()));
				mimeTypeWindowStates.add(
					StringUtil.toLowerCase(
						LiferayWindowState.POP_UP.toString()));
			}

			for (Element windowStateElement : windowStateElements) {
				mimeTypeWindowStates.add(
					StringUtil.toLowerCase(windowStateElement.getTextTrim()));
			}

			supportedWindowStates.put(mimeType, mimeTypeWindowStates);
		}

		Set<String> supportedLocales = new HashSet<>();

		for (Element supportedLocaleElement :
				portletElement.elements("supported-locale")) {

			String supportedLocale = supportedLocaleElement.getText();

			supportedLocales.add(supportedLocale);
		}

		String resourceBundle = portletElement.elementText("resource-bundle");

		Element portletInfoElement = portletElement.element("portlet-info");

		Map<String, String> titles = new HashMap<>();
		Map<String, String> shortTitles = new HashMap<>();
		Map<String, String> keywords = new HashMap<>();
		Map<String, String> descriptions = new HashMap<>();

		if (portletInfoElement != null) {
			for (Element titleElement : portletInfoElement.elements("title")) {
				titles.put(
					_getLang(titleElement),
					GetterUtil.getString(titleElement.getText()));
			}

			for (Element shortTitleElement :
					portletInfoElement.elements("short-title")) {

				shortTitles.put(
					_getLang(shortTitleElement),
					GetterUtil.getString(shortTitleElement.getText()));
			}

			for (Element keywordElement :
					portletInfoElement.elements("keywords")) {

				keywords.put(
					_getLang(keywordElement),
					GetterUtil.getString(keywordElement.getText()));
			}

			for (Element descriptionElement :
					portletInfoElement.elements("description")) {

				descriptions.put(
					_getLang(descriptionElement),
					GetterUtil.getString(descriptionElement.getText()));
			}
		}

		Map<String, Preference> preferences = new HashMap<>();

		Element portletPreferencesElement = portletElement.element(
			"portlet-preferences");

		String preferencesValidator = null;

		if (portletPreferencesElement != null) {
			for (Element preferenceElement :
					portletPreferencesElement.elements("preference")) {

				String name = preferenceElement.elementText("name");

				List<String> values = new ArrayList<>();

				List<Element> valueElements = preferenceElement.elements(
					"value");

				if (valueElements != null) {
					for (Element valueElement : valueElements) {
						values.add(valueElement.getText());
					}
				}

				preferences.put(
					name,
					new Preference(
						values,
						GetterUtil.getBoolean(
							preferenceElement.elementText("read-only"))));
			}

			preferencesValidator = portletPreferencesElement.elementText(
				"preferences-validator");
		}

		if (preferencesValidator == null) {
			preferencesValidator = preferencesValidators.get(portletName);

			for (String wildcardPreferencesValidator :
					wildcardPreferencesValidators) {

				if (preferencesValidator == null) {
					preferencesValidator = wildcardPreferencesValidator;
				}
				else {
					_log.error(
						StringBundler.concat(
							"Unable to associate @PortletPreferencesValidator ",
							wildcardPreferencesValidator, " to portletName \"",
							portletName, "\" since is already associated with ",
							preferencesValidator));
				}
			}
		}

		Map<String, String> securityRoleRefs = new HashMap<>();

		for (Element roleElement :
				portletElement.elements("security-role-ref")) {

			securityRoleRefs.put(
				roleElement.elementText("role-name"),
				roleElement.elementText("role-link"));
		}

		Set<javax.xml.namespace.QName> supportedProcessingEvents =
			new HashSet<>();

		for (Element supportedProcessingEventElement :
				portletElement.elements("supported-processing-event")) {

			Element qNameElement = supportedProcessingEventElement.element(
				"qname");
			Element nameElement = supportedProcessingEventElement.element(
				"name");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, beanApp.getDefaultNamespace());

			supportedProcessingEvents.add(
				new javax.xml.namespace.QName(
					qName.getNamespaceURI(), qName.getLocalPart(),
					qName.getNamespacePrefix()));
		}

		Set<javax.xml.namespace.QName> supportedPublishingEvents =
			new HashSet<>();

		for (Element supportedPublishingEventElement :
				portletElement.elements("supported-publishing-event")) {

			Element qNameElement = supportedPublishingEventElement.element(
				"qname");
			Element nameElement = supportedPublishingEventElement.element(
				"name");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, beanApp.getDefaultNamespace());

			supportedPublishingEvents.add(
				new javax.xml.namespace.QName(
					qName.getNamespaceURI(), qName.getLocalPart(),
					qName.getNamespacePrefix()));
		}

		Map<String, PublicRenderParameter> publicRenderParameters =
			beanApp.getPublicRenderParameters();

		Set<String> supportedPublicRenderParameters = new HashSet<>();

		for (Element supportedPublicRenderParameter :
				portletElement.elements("supported-public-render-parameter")) {

			String identifier = supportedPublicRenderParameter.getTextTrim();

			PublicRenderParameter publicRenderParameter =
				publicRenderParameters.get(identifier);

			if (publicRenderParameter == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Supported public render parameter references " +
							"unknown identifier " + identifier);
				}
			}

			supportedPublicRenderParameters.add(identifier);
		}

		Map<String, List<String>> containerRuntimeOptions = new HashMap<>(
			beanApp.getContainerRuntimeOptions());

		for (Element containerRuntimeOptionElement :
				portletElement.elements("container-runtime-option")) {

			String name = GetterUtil.getString(
				containerRuntimeOptionElement.elementText("name"));

			List<String> values = new ArrayList<>();

			for (Element valueElement :
					containerRuntimeOptionElement.elements("value")) {

				values.add(valueElement.getTextTrim());
			}

			containerRuntimeOptions.put(name, values);
		}

		Set<PortletDependency> portletDependencies = new HashSet<>();

		for (Element dependencyElement :
				portletElement.elements("dependency")) {

			String name = GetterUtil.getString(
				dependencyElement.elementText("name"));
			String scope = GetterUtil.getString(
				dependencyElement.elementText("scope"));
			String version = GetterUtil.getString(
				dependencyElement.elementText("version"));

			portletDependencies.add(
				new PortletDependency(name, scope, version));
		}

		boolean asyncSupported = GetterUtil.getBoolean(
			portletElement.elementText("async-supported"));

		boolean multiPartSupported = false;
		int multiPartFileSizeThreshold = 0;
		String multiPartLocation = null;
		long multiPartMaxFileSize = -1L;
		long multiPartMaxRequestSize = -1L;

		Element multipartConfigElement = portletElement.element(
			"multipart-config");

		if (multipartConfigElement != null) {
			multiPartSupported = true;
			multiPartFileSizeThreshold = GetterUtil.getInteger(
				multipartConfigElement.elementText("file-size-threshold"));
			multiPartLocation = multipartConfigElement.elementText("location");
			multiPartMaxFileSize = GetterUtil.getLong(
				multipartConfigElement.elementText("max-file-size"),
				multiPartMaxFileSize);
			multiPartMaxRequestSize = GetterUtil.getLong(
				multipartConfigElement.elementText("max-request-size"),
				multiPartMaxRequestSize);
		}

		Set<BeanMethod> beanMethods = portletBeanMethods.get(portletName);

		if (beanMethods == null) {
			beanMethods = new HashSet<>();
		}
		else {
			beanMethods = new HashSet<>(beanMethods);
		}

		Class<?> portletClass = null;

		try {
			portletClass = bundle.loadClass(portletClassName);
		}
		catch (ClassNotFoundException cnfe) {
			_log.error("Unable to load portlet-class " + portletClassName);

			return null;
		}

		beanMethods.addAll(
			PortletScannerUtil.getNonannotatedBeanMethods(
				beanManager, portletClass));

		return new BeanPortletDescriptorImpl(
			portletName, beanMethods, wildcardBeanMethods, displayNames,
			portletClassName, initParams, expirationCache,
			supportedPortletModes, supportedWindowStates, supportedLocales,
			resourceBundle, titles, shortTitles, keywords, descriptions,
			preferences, preferencesValidator, securityRoleRefs,
			supportedProcessingEvents, supportedPublishingEvents,
			supportedPublicRenderParameters, containerRuntimeOptions,
			portletDependencies, asyncSupported, multiPartSupported,
			multiPartFileSizeThreshold, multiPartLocation, multiPartMaxFileSize,
			multiPartMaxRequestSize,
			displayDescriptorCategories.get(portletName),
			liferayConfigurations.get(portletName));
	}

	private static List<BeanPortlet> _readBeanPortlets(
		Bundle bundle, Element rootElement, BeanApp beanApp,
		BeanManager beanManager,
		Map<String, Set<BeanMethod>> portletBeanMethods,
		Set<BeanMethod> wildcardBeanMethods,
		Map<String, String> preferencesValidators,
		Set<String> wildcardPreferencesValidators,
		Map<String, String> displayDescriptorCategories,
		Map<String, Map<String, Set<String>>> liferayConfigurations) {

		List<BeanPortlet> beanPortlets = new ArrayList<>();

		for (Element portletElement : rootElement.elements("portlet")) {
			beanPortlets.add(
				_readBeanPortlet(
					bundle, portletElement, beanApp, beanManager,
					portletBeanMethods, wildcardBeanMethods,
					preferencesValidators, wildcardPreferencesValidators,
					displayDescriptorCategories, liferayConfigurations));
		}

		return beanPortlets;
	}

	private static final String _ENGLISH_EN = Locale.ENGLISH.getLanguage();

	private static final boolean _PORTLET_XML_VALIDATE = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.PORTLET_XML_VALIDATE));

	private static final Log _log = LogFactoryUtil.getLog(
		PortletDescriptorParser.class);

}