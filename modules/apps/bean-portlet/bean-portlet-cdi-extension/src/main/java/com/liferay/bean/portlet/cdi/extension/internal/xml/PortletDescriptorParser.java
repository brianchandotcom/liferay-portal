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
import com.liferay.bean.portlet.cdi.extension.internal.BeanAppImpl;
import com.liferay.bean.portlet.cdi.extension.internal.BeanFilter;
import com.liferay.bean.portlet.cdi.extension.internal.BeanFilterImpl;
import com.liferay.bean.portlet.cdi.extension.internal.BeanMethod;
import com.liferay.bean.portlet.cdi.extension.internal.BeanPortlet;
import com.liferay.bean.portlet.cdi.extension.internal.BeanPortletImpl;
import com.liferay.bean.portlet.cdi.extension.internal.Event;
import com.liferay.bean.portlet.cdi.extension.internal.EventImpl;
import com.liferay.bean.portlet.cdi.extension.internal.MethodType;
import com.liferay.bean.portlet.cdi.extension.internal.MultipartConfig;
import com.liferay.bean.portlet.cdi.extension.internal.PortletDependency;
import com.liferay.bean.portlet.cdi.extension.internal.Preference;
import com.liferay.bean.portlet.cdi.extension.internal.PublicRenderParameter;
import com.liferay.bean.portlet.cdi.extension.internal.PublicRenderParameterImpl;
import com.liferay.bean.portlet.cdi.extension.internal.util.BeanMethodIndexUtil;
import com.liferay.bean.portlet.cdi.extension.internal.util.PortletScannerUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.io.IOException;

import java.net.URL;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.enterprise.inject.spi.BeanManager;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import javax.portlet.filter.PortletFilter;

import javax.xml.namespace.QName;

import org.osgi.framework.Bundle;

/**
 * @author Neil Griffin
 */
public class PortletDescriptorParser {

	public static BeanApp parse(
			Map<String, BeanFilter> beanFilters,
			Map<String, BeanPortlet> beanPortlets, Bundle bundle,
			URL portletDescriptorURL, BeanManager beanManager,
			Function<String, Set<BeanMethod>> portletBeanMethodsFunction,
			Function<String, String> preferencesValidatorFunction,
			Map<String, String> displayDescriptorCategories,
			Map<String, Map<String, Set<String>>> liferayConfigurations)
		throws DocumentException, IOException {

		Document document = UnsecureSAXReaderUtil.read(
			portletDescriptorURL, _PORTLET_XML_VALIDATE);

		Element rootElement = document.getRootElement();

		BeanApp beanApp = _readBeanApp(rootElement);

		_populateBeanFilters(beanFilters, bundle, rootElement);

		_populateBeanPortlets(
			beanPortlets, bundle, rootElement, beanApp, beanManager,
			portletBeanMethodsFunction, preferencesValidatorFunction,
			displayDescriptorCategories, liferayConfigurations);

		return beanApp;
	}

	private static boolean _isCustomPortletMode(String portletModeName) {
		return PortalUtil.isCustomPortletMode(new PortletMode(portletModeName));
	}

	private static void _populateBeanFilters(
		Map<String, BeanFilter> beanFilters, Bundle bundle,
		Element rootElement) {

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
			String filterClassName = filterElement.elementText("filter-class");

			Class<?> filterClass = null;

			try {
				filterClass = bundle.loadClass(filterClassName);

				if (!PortletFilter.class.isAssignableFrom(filterClass)) {
					_log.error(
						StringBundler.concat(
							"Ignoring ", filterClass,
							". Because it does not implement ",
							PortletFilter.class));

					continue;
				}
			}
			catch (ClassNotFoundException cnfe) {
				_log.error("Unable to load filter-class " + filterClassName);

				continue;
			}

			String filterName = filterElement.elementText("filter-name");

			Set<String> portletNames = filterMappings.get(filterName);

			beanFilters.put(
				filterName,
				_readBeanFilter(
					filterElement, filterName,
					filterClass.asSubclass(PortletFilter.class), portletNames));
		}
	}

	private static void _populateBeanPortlets(
		Map<String, BeanPortlet> beanPortlets, Bundle bundle,
		Element rootElement, BeanApp beanApp, BeanManager beanManager,
		Function<String, Set<BeanMethod>> portletBeanMethodsFunction,
		Function<String, String> preferencesValidatorFunction,
		Map<String, String> displayDescriptorCategories,
		Map<String, Map<String, Set<String>>> liferayConfigurations) {

		for (Element portletElement : rootElement.elements("portlet")) {
			String portletClassName = GetterUtil.getString(
				portletElement.elementText("portlet-class"));

			Class<?> portletClass = null;

			try {
				portletClass = bundle.loadClass(portletClassName);
			}
			catch (ClassNotFoundException cnfe) {
				_log.error("Unable to load portlet-class " + portletClassName);

				continue;
			}

			String portletName = portletElement.elementText("portlet-name");

			Set<BeanMethod> beanMethods = new HashSet<>(
				portletBeanMethodsFunction.apply(portletName));

			PortletScannerUtil.scanNonannotatedBeanMethods(
				beanManager, portletClass, beanMethods);

			Map<MethodType, List<BeanMethod>> beanMethodMap =
				BeanMethodIndexUtil.indexBeanMethods(beanMethods);

			String preferencesValidator = preferencesValidatorFunction.apply(
				portletName);

			String categoryName = displayDescriptorCategories.get(portletName);

			Map<String, Set<String>> liferayConfiguration =
				liferayConfigurations.get(portletName);

			beanPortlets.put(
				portletName,
				_readBeanPortlet(
					portletElement, portletClassName, portletName, beanApp,
					beanMethodMap, preferencesValidator, categoryName,
					liferayConfiguration));
		}
	}

	private static BeanApp _readBeanApp(Element rootElement) {
		String specVersion = GetterUtil.get(
			rootElement.attributeValue("version"), "3.0");

		String defaultNamespace = rootElement.elementText("default-namespace");

		List<Event> events = new ArrayList<>();

		for (Element eventDefinitionElement :
				rootElement.elements("event-definition")) {

			Element qNameElement = eventDefinitionElement.element("qname");
			Element nameElement = eventDefinitionElement.element("name");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, defaultNamespace);

			String valueType = eventDefinitionElement.elementText("value-type");

			List<Element> aliases = eventDefinitionElement.elements("alias");

			List<QName> aliasQNames = new ArrayList<>(aliases.size());

			for (Element alias : aliases) {
				aliasQNames.add(
					PortletQNameUtil.getQName(alias, null, defaultNamespace));
			}

			events.add(new EventImpl(qName, valueType, aliasQNames));
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
				new PublicRenderParameterImpl(identifier, qName);

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

		List<Map.Entry<Integer, String>> portletListeners = new ArrayList<>();

		for (Element listenerElement : rootElement.elements("listener")) {
			int ordinal = GetterUtil.getInteger(
				listenerElement.elementText("ordinal"));

			String listenerClassName = listenerElement.elementText(
				"listener-class");

			portletListeners.add(
				new AbstractMap.SimpleImmutableEntry<>(
					ordinal, listenerClassName));
		}

		return new BeanAppImpl(
			specVersion, defaultNamespace, events, publicRenderParameters,
			containerRuntimeOptions, validCustomPortletModes, portletListeners);
	}

	private static BeanFilter _readBeanFilter(
		Element filterElement, String filterName,
		Class<? extends PortletFilter> filterClass, Set<String> portletNames) {

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

		return new BeanFilterImpl(
			filterName, filterClass, ordinal, portletNames, lifecycles,
			initParams);
	}

	private static BeanPortlet _readBeanPortlet(
		Element portletElement, String portletClassName, String portletName,
		BeanApp beanApp, Map<MethodType, List<BeanMethod>> beanMethodMap,
		String preferencesValidator, String categoryName,
		Map<String, Set<String>> liferayConfiguration) {

		Map<String, String> displayNames = _toLocaleMap(
			portletElement.elements("display-name"));

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

			mimeTypePortletModes.add(PortletMode.VIEW.toString());

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

			mimeTypeWindowStates.add(WindowState.NORMAL.toString());

			List<Element> windowStateElements = supportsElement.elements(
				"window-state");

			if (windowStateElements.isEmpty()) {
				mimeTypeWindowStates.add(WindowState.MAXIMIZED.toString());
				mimeTypeWindowStates.add(WindowState.MINIMIZED.toString());
				mimeTypeWindowStates.add(
					LiferayWindowState.EXCLUSIVE.toString());
				mimeTypeWindowStates.add(LiferayWindowState.POP_UP.toString());
			}
			else {
				for (Element windowStateElement : windowStateElements) {
					mimeTypeWindowStates.add(
						StringUtil.toLowerCase(
							windowStateElement.getTextTrim()));
				}
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

		Map<String, String> titles = Collections.emptyMap();
		Map<String, String> shortTitles = Collections.emptyMap();
		Map<String, String> keywords = Collections.emptyMap();
		Map<String, String> descriptions = Collections.emptyMap();

		if (portletInfoElement != null) {
			titles = _toLocaleMap(portletInfoElement.elements("title"));
			shortTitles = _toLocaleMap(
				portletInfoElement.elements("short-title"));
			keywords = _toLocaleMap(portletInfoElement.elements("keywords"));
			descriptions = _toLocaleMap(
				portletInfoElement.elements("description"));
		}

		Map<String, Preference> preferences = new HashMap<>();

		Element portletPreferencesElement = portletElement.element(
			"portlet-preferences");

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

			String xmlPreferencesValidator =
				portletPreferencesElement.elementText("preferences-validator");

			if (xmlPreferencesValidator != null) {
				preferencesValidator = xmlPreferencesValidator;
			}
		}

		Map<String, String> securityRoleRefs = new HashMap<>();

		for (Element roleElement :
				portletElement.elements("security-role-ref")) {

			securityRoleRefs.put(
				roleElement.elementText("role-name"),
				roleElement.elementText("role-link"));
		}

		Set<QName> supportedProcessingEvents = new HashSet<>();

		for (Element supportedProcessingEventElement :
				portletElement.elements("supported-processing-event")) {

			Element qNameElement = supportedProcessingEventElement.element(
				"qname");
			Element nameElement = supportedProcessingEventElement.element(
				"name");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, beanApp.getDefaultNamespace());

			supportedProcessingEvents.add(qName);
		}

		Set<QName> supportedPublishingEvents = new HashSet<>();

		for (Element supportedPublishingEventElement :
				portletElement.elements("supported-publishing-event")) {

			Element qNameElement = supportedPublishingEventElement.element(
				"qname");
			Element nameElement = supportedPublishingEventElement.element(
				"name");

			QName qName = PortletQNameUtil.getQName(
				qNameElement, nameElement, beanApp.getDefaultNamespace());

			supportedPublishingEvents.add(qName);
		}

		BeanMethodIndexUtil.scanSupportedEvents(
			beanMethodMap, supportedProcessingEvents,
			supportedPublishingEvents);

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

		MultipartConfig multipartConfig = MultipartConfig.UNSUPPORTED;

		Element multipartConfigElement = portletElement.element(
			"multipart-config");

		if (multipartConfigElement != null) {
			multipartConfig = new MultipartConfig(
				GetterUtil.getInteger(
					multipartConfigElement.elementText("file-size-threshold")),
				multipartConfigElement.elementText("location"),
				GetterUtil.getLong(
					multipartConfigElement.elementText("max-file-size"), -1),
				GetterUtil.getLong(
					multipartConfigElement.elementText("max-request-size"),
					-1));
		}

		return new BeanPortletImpl(
			portletName, beanMethodMap, displayNames, portletClassName,
			initParams, expirationCache, supportedPortletModes,
			supportedWindowStates, supportedLocales, resourceBundle, titles,
			shortTitles, keywords, descriptions, preferences,
			preferencesValidator, securityRoleRefs, supportedProcessingEvents,
			supportedPublishingEvents, supportedPublicRenderParameters,
			containerRuntimeOptions, portletDependencies, asyncSupported,
			multipartConfig, categoryName, liferayConfiguration);
	}

	private static Map<String, String> _toLocaleMap(List<Element> elements) {
		Map<String, String> localeMap = new HashMap<>();

		for (Element element : elements) {
			String lang = element.attributeValue("lang");

			if (lang == null) {
				lang = _ENGLISH_EN;
			}

			localeMap.put(lang, GetterUtil.getString(element.getText()));
		}

		return localeMap;
	}

	private static final String _ENGLISH_EN = LocaleUtil.ENGLISH.getLanguage();

	private static final boolean _PORTLET_XML_VALIDATE = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.PORTLET_XML_VALIDATE));

	private static final Log _log = LogFactoryUtil.getLog(
		PortletDescriptorParser.class);

}