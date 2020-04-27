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

package com.liferay.bean.portlet.registration.internal;

import com.liferay.bean.portlet.LiferayPortletConfiguration;
import com.liferay.bean.portlet.LiferayPortletConfigurations;
import com.liferay.bean.portlet.extension.BeanFilterMethodFactory;
import com.liferay.bean.portlet.extension.BeanFilterMethodInvoker;
import com.liferay.bean.portlet.extension.BeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodFactory;
import com.liferay.bean.portlet.extension.BeanPortletMethodInvoker;
import com.liferay.bean.portlet.extension.BeanPortletMethodType;
import com.liferay.bean.portlet.registration.BeanPortletRegistrar;
import com.liferay.bean.portlet.registration.internal.util.BeanMethodIndexUtil;
import com.liferay.bean.portlet.registration.internal.util.PortletScannerUtil;
import com.liferay.bean.portlet.registration.internal.xml.DisplayDescriptorParser;
import com.liferay.bean.portlet.registration.internal.xml.LiferayDescriptorParser;
import com.liferay.bean.portlet.registration.internal.xml.PortletDescriptorParser;
import com.liferay.bean.portlet.registration.internal.xml.PortletQNameUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import javax.portlet.Portlet;
import javax.portlet.PortletRequest;
import javax.portlet.annotations.CustomPortletMode;
import javax.portlet.annotations.CustomWindowState;
import javax.portlet.annotations.Dependency;
import javax.portlet.annotations.EventDefinition;
import javax.portlet.annotations.InitParameter;
import javax.portlet.annotations.LocaleString;
import javax.portlet.annotations.Multipart;
import javax.portlet.annotations.PortletApplication;
import javax.portlet.annotations.PortletConfiguration;
import javax.portlet.annotations.PortletConfigurations;
import javax.portlet.annotations.PortletLifecycleFilter;
import javax.portlet.annotations.PortletListener;
import javax.portlet.annotations.PortletPreferencesValidator;
import javax.portlet.annotations.PortletQName;
import javax.portlet.annotations.PublicRenderParameterDefinition;
import javax.portlet.annotations.RuntimeOption;
import javax.portlet.annotations.SecurityRoleRef;
import javax.portlet.annotations.Supports;
import javax.portlet.annotations.UserAttribute;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.HeaderFilter;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

import javax.xml.namespace.QName;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Neil Griffin
 */
@Component(service = BeanPortletRegistrar.class)
public class BeanPortletRegistrarImpl implements BeanPortletRegistrar {

	@Override
	public List<ServiceRegistration<?>> register(
		BeanFilterMethodFactory beanFilterMethodFactory,
		BeanFilterMethodInvoker beanFilterMethodInvoker,
		BeanPortletMethodFactory beanPortletMethodFactory,
		BeanPortletMethodInvoker beanPortletMethodInvoker,
		Set<Class<?>> discoveredClasses, ServletContext servletContext) {

		BundleContext bundleContext =
			(BundleContext)servletContext.getAttribute("osgi-bundlecontext");

		Bundle bundle = bundleContext.getBundle();

		URL displayDescriptorURL = bundle.getEntry(
			"WEB-INF/liferay-display.xml");

		Map<String, String> descriptorDisplayCategories =
			Collections.emptyMap();

		if (displayDescriptorURL != null) {
			try {
				descriptorDisplayCategories = DisplayDescriptorParser.parse(
					displayDescriptorURL);
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}

		URL liferayDescriptorURL = bundle.getEntry(
			"WEB-INF/liferay-portlet.xml");

		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations =
			Collections.emptyMap();

		if (liferayDescriptorURL != null) {
			try {
				descriptorLiferayConfigurations = LiferayDescriptorParser.parse(
					liferayDescriptorURL);
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}

		List<DiscoveredBeanMethod> discoveredBeanMethods = new ArrayList<>();

		for (Class<?> discoveredClass : discoveredClasses) {
			for (Method method : discoveredClass.getMethods()) {
				for (BeanPortletMethodType beanPortletMethodType :
						BeanPortletMethodType.values()) {

					if (beanPortletMethodType.isMatch(method)) {
						discoveredBeanMethods.add(
							new DiscoveredBeanMethod(
								discoveredClass, beanPortletMethodType,
								method));
					}
				}
			}
		}

		Function<String, Set<BeanPortletMethod>> portletBeanMethodsFunction =
			_collectPortletBeanMethods(
				beanPortletMethodFactory, discoveredBeanMethods);

		Function<String, String> preferencesValidatorFunction =
			_collectPreferencesValidators(discoveredClasses);

		URL portletDescriptorURL = bundle.getEntry("/WEB-INF/portlet.xml");

		Map<String, BeanFilter> beanFilters = new HashMap<>();
		Map<String, BeanPortlet> beanPortlets = new HashMap<>();
		BeanApp beanApp = null;

		if (portletDescriptorURL != null) {
			try {
				beanApp = PortletDescriptorParser.parse(
					beanFilters, beanPortletMethodFactory, beanPortlets, bundle,
					descriptorDisplayCategories,
					descriptorLiferayConfigurations, portletBeanMethodsFunction,
					portletDescriptorURL, preferencesValidatorFunction);
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}

		if (beanApp == null) {
			beanApp = new BeanAppImpl(
				Collections.emptyMap(), Collections.emptySet(), null,
				Collections.emptyList(), Collections.emptyList(),
				Collections.emptyMap(), "3.0");
		}

		_addBeanFiltersFromDiscoveredClasses(beanFilters, discoveredClasses);

		_addBeanPortletsFromDiscoveredClasses(
			beanApp, beanPortletMethodFactory, beanPortlets,
			descriptorDisplayCategories, descriptorLiferayConfigurations,
			discoveredClasses, portletBeanMethodsFunction,
			preferencesValidatorFunction);

		_addBeanPortletsFromScannedMethods(
			beanPortlets, discoveredBeanMethods, descriptorDisplayCategories,
			descriptorLiferayConfigurations, portletBeanMethodsFunction);

		_addBeanPortletsFromLiferayDescriptor(
			beanPortlets, descriptorDisplayCategories,
			descriptorLiferayConfigurations, portletBeanMethodsFunction);

		@SuppressWarnings("unchecked")
		List<String> beanPortletIds = (List<String>)servletContext.getAttribute(
			WebKeys.BEAN_PORTLET_IDS);

		if (beanPortletIds == null) {
			beanPortletIds = new ArrayList<>();

			servletContext.setAttribute(
				WebKeys.BEAN_PORTLET_IDS, beanPortletIds);
		}

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		for (BeanPortlet beanPortlet : beanPortlets.values()) {
			ServiceRegistration<Portlet> portletServiceRegistration =
				RegistrationUtil.registerBeanPortlet(
					beanApp, beanPortlet, beanPortletIds,
					beanPortletMethodInvoker, bundleContext, servletContext);

			if (portletServiceRegistration != null) {
				serviceRegistrations.add(portletServiceRegistration);
			}

			ServiceRegistration<ResourceBundleLoader>
				resourceBundleLoaderserviceRegistration =
					RegistrationUtil.registerResourceBundleLoader(
						beanPortlet, bundleContext, servletContext);

			if (resourceBundleLoaderserviceRegistration != null) {
				serviceRegistrations.add(
					resourceBundleLoaderserviceRegistration);
			}
		}

		for (BeanFilter beanFilter : beanFilters.values()) {
			for (String portletName : beanFilter.getPortletNames()) {
				RegistrationUtil.registerBeanFilter(
					beanPortlets.keySet(), beanFilter, beanFilterMethodFactory,
					beanFilterMethodInvoker, bundleContext, portletName,
					serviceRegistrations, servletContext);
			}
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			servletContext.getServletContextName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			PortletServlet.class.getName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/portlet-servlet/*");

		serviceRegistrations.add(
			bundleContext.registerService(
				Servlet.class, new PortletServlet(), properties));

		Set<String> portletNames = descriptorDisplayCategories.keySet();

		portletNames.removeAll(beanPortlets.keySet());

		if (!portletNames.isEmpty()) {
			_log.error(
				"Unknown portlet IDs " + portletNames +
					" found in liferay-display.xml");
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Registered ", beanPortlets.size(), " bean portlets and ",
					beanFilters.size(), " bean filters for ",
					servletContext.getServletContextName()));
		}

		return serviceRegistrations;
	}

	@Override
	public void unregister(
		List<ServiceRegistration<?>> serviceRegistrations,
		ServletContext servletContext) {

		int totalBeanPortlets = 0;
		int totalBeanFilters = 0;

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			ServiceReference<?> serviceReference =
				serviceRegistration.getReference();

			String[] serviceClasses = (String[])serviceReference.getProperty(
				Constants.OBJECTCLASS);

			if (ArrayUtil.contains(serviceClasses, "javax.portlet.Portlet")) {
				totalBeanPortlets++;
			}
			else if (ArrayUtil.contains(
						serviceClasses, "javax.portlet.filter.PortletFilter")) {

				totalBeanFilters++;
			}

			try {
				serviceRegistration.unregister();
			}
			catch (IllegalStateException illegalStateException) {

				// Ignore since the service has been unregistered

			}
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Unregistered ", totalBeanPortlets, " bean portlets and ",
					totalBeanFilters, " bean filters for ",
					servletContext.getServletContextName()));
		}
	}

	private void _addBeanFiltersFromDiscoveredClasses(
		Map<String, BeanFilter> beanFilters, Set<Class<?>> discoveredClasses) {

		for (Class<?> discoveredClass : discoveredClasses) {
			PortletLifecycleFilter portletLifecycleFilter =
				discoveredClass.getAnnotation(PortletLifecycleFilter.class);

			if (portletLifecycleFilter == null) {
				continue;
			}

			if (!PortletFilter.class.isAssignableFrom(discoveredClass)) {
				_log.error(
					StringBundler.concat(
						"Ignoring ", discoveredClass, ". It has ",
						PortletLifecycleFilter.class,
						" but does not implement ", PortletFilter.class));

				continue;
			}

			Map<String, String> initParams = new HashMap<>();

			for (InitParameter initParameter :
					portletLifecycleFilter.initParams()) {

				initParams.put(initParameter.name(), initParameter.value());
			}

			Set<String> lifecycles = new LinkedHashSet<>();

			if (ActionFilter.class.isAssignableFrom(discoveredClass)) {
				lifecycles.add(PortletRequest.ACTION_PHASE);
			}

			if (EventFilter.class.isAssignableFrom(discoveredClass)) {
				lifecycles.add(PortletRequest.EVENT_PHASE);
			}

			if (HeaderFilter.class.isAssignableFrom(discoveredClass)) {
				lifecycles.add(PortletRequest.HEADER_PHASE);
			}

			if (RenderFilter.class.isAssignableFrom(discoveredClass)) {
				lifecycles.add(PortletRequest.RENDER_PHASE);
			}

			if (ResourceFilter.class.isAssignableFrom(discoveredClass)) {
				lifecycles.add(PortletRequest.RESOURCE_PHASE);
			}

			Set<String> portletNames = new HashSet<>(
				Arrays.asList(portletLifecycleFilter.portletNames()));

			String filterName = portletLifecycleFilter.filterName();

			BeanFilter beanFilter = beanFilters.get(filterName);

			if (beanFilter == null) {
				beanFilter = new BeanFilterImpl(
					discoveredClass.asSubclass(PortletFilter.class), filterName,
					initParams, lifecycles, portletLifecycleFilter.ordinal(),
					portletNames);
			}
			else {
				portletNames.addAll(beanFilter.getPortletNames());

				lifecycles.addAll(beanFilter.getLifecycles());

				initParams.putAll(beanFilter.getInitParams());

				beanFilter = new BeanFilterImpl(
					beanFilter.getFilterClass(), beanFilter.getFilterName(),
					initParams, lifecycles, beanFilter.getOrdinal(),
					portletNames);
			}

			beanFilters.put(filterName, beanFilter);
		}
	}

	private void _addBeanPortlet(
		BeanApp beanApp,
		Map<BeanPortletMethodType, List<BeanPortletMethod>> beanMethodMap,
		Map<String, BeanPortlet> beanPortlets,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations,
		Class<?> discoveredClass, Set<Class<?>> discoveredClasses,
		PortletConfiguration portletConfiguration,
		Function<String, String> preferencesValidatorFunction) {

		String configuredPortletName = portletConfiguration.portletName();

		if (Validator.isNull(configuredPortletName)) {
			_log.error(
				"Invalid portlet name attribute for " +
					discoveredClass.getName());

			return;
		}

		PortletApplication portletApplication = null;

		for (Class<?> clazz : discoveredClasses) {
			PortletApplication discoveredPortletApplication =
				clazz.getAnnotation(PortletApplication.class);

			if (discoveredPortletApplication != null) {
				if (portletApplication == null) {
					portletApplication = discoveredPortletApplication;
				}
				else {
					_log.error(
						"Found more than one @PortletApplication annotation");
				}
			}
		}

		if (portletApplication == null) {
			portletApplication = _portletApplication;
		}

		String specVersion = GetterUtil.getString(
			portletApplication.version(), "3.0");

		if (Validator.isNotNull(beanApp.getSpecVersion())) {
			specVersion = beanApp.getSpecVersion();
		}

		String defaultNamespace = portletApplication.defaultNamespaceURI();

		if (Validator.isNotNull(beanApp.getDefaultNamespace())) {
			defaultNamespace = beanApp.getDefaultNamespace();
		}

		List<Event> events = new ArrayList<>(beanApp.getEvents());

		for (EventDefinition eventDefinition : portletApplication.events()) {
			String valueType = null;

			Class<?> payloadClass = eventDefinition.payloadType();

			if (payloadClass != null) {
				valueType = payloadClass.getName();
			}

			List<QName> aliasQNames = new ArrayList<>();

			for (PortletQName portletQName : eventDefinition.alias()) {
				aliasQNames.add(PortletQNameUtil.toQName(portletQName));
			}

			events.add(
				new EventImpl(
					aliasQNames,
					PortletQNameUtil.toQName(eventDefinition.qname()),
					valueType));
		}

		Map<String, PublicRenderParameter> publicRenderParameters =
			new HashMap<>();

		for (PublicRenderParameterDefinition publicRenderParameterDefinition :
				portletApplication.publicParams()) {

			PortletQName portletQName = publicRenderParameterDefinition.qname();

			PublicRenderParameter publicRenderParameter =
				new PublicRenderParameterImpl(
					publicRenderParameterDefinition.identifier(),
					new QName(
						portletQName.namespaceURI(), portletQName.localPart()));

			publicRenderParameters.put(
				publicRenderParameter.getIdentifier(), publicRenderParameter);
		}

		publicRenderParameters.putAll(beanApp.getPublicRenderParameters());

		Map<String, List<String>> containerRuntimeOptions = new HashMap<>();

		for (RuntimeOption runtimeOption :
				portletApplication.runtimeOptions()) {

			containerRuntimeOptions.put(
				runtimeOption.name(), Arrays.asList(runtimeOption.values()));
		}

		containerRuntimeOptions.putAll(beanApp.getContainerRuntimeOptions());

		Set<String> customPortletModes = new LinkedHashSet<>(
			beanApp.getCustomPortletModes());

		for (CustomPortletMode customPortletMode :
				portletApplication.customPortletModes()) {

			if (!customPortletMode.portalManaged()) {
				customPortletModes.add(customPortletMode.name());
			}
		}

		List<Map.Entry<Integer, String>> portletListeners = new ArrayList<>();

		for (Class<?> clazz : discoveredClasses) {
			PortletListener portletListener = clazz.getAnnotation(
				PortletListener.class);

			if (portletListener == null) {
				continue;
			}

			portletListeners.add(
				new AbstractMap.SimpleImmutableEntry<>(
					portletListener.ordinal(), clazz.getName()));
		}

		portletListeners.addAll(beanApp.getPortletListeners());

		beanApp.setSpecVersion(specVersion);
		beanApp.setDefaultNamespace(defaultNamespace);
		beanApp.setEvents(events);
		beanApp.setPublicRenderParameters(publicRenderParameters);
		beanApp.setContainerRuntimeOptions(containerRuntimeOptions);
		beanApp.setCustomPortletModes(customPortletModes);
		beanApp.setPortletListeners(portletListeners);

		String preferencesValidator = preferencesValidatorFunction.apply(
			configuredPortletName);

		Map<String, String> displayNames = new HashMap<>();

		for (LocaleString localeString : portletConfiguration.displayName()) {
			displayNames.put(localeString.locale(), localeString.value());
		}

		Map<String, String> initParams = new HashMap<>();

		for (InitParameter initParameter : portletConfiguration.initParams()) {
			String value = initParameter.value();

			if (value != null) {
				initParams.put(initParameter.name(), value);
			}
		}

		Map<String, Set<String>> supportedPortletModes = new HashMap<>();
		Map<String, Set<String>> supportedWindowStates = new HashMap<>();

		for (Supports supports : portletConfiguration.supports()) {
			supportedPortletModes.put(
				supports.mimeType(),
				new LinkedHashSet<>(Arrays.asList(supports.portletModes())));
			supportedWindowStates.put(
				supports.mimeType(),
				new LinkedHashSet<>(Arrays.asList(supports.windowStates())));
		}

		Map<String, String> titles = new HashMap<>();

		for (LocaleString localeString : portletConfiguration.title()) {
			titles.put(localeString.locale(), localeString.value());
		}

		Map<String, String> shortTitles = new HashMap<>();

		for (LocaleString localeString : portletConfiguration.shortTitle()) {
			shortTitles.put(localeString.locale(), localeString.value());
		}

		Map<String, String> keywords = new HashMap<>();

		for (LocaleString localeString : portletConfiguration.keywords()) {
			keywords.put(localeString.locale(), localeString.value());
		}

		Map<String, String> descriptions = new HashMap<>();

		for (LocaleString localeString : portletConfiguration.description()) {
			descriptions.put(localeString.locale(), localeString.value());
		}

		Map<String, Preference> preferences = new HashMap<>();

		for (javax.portlet.annotations.Preference preference :
				portletConfiguration.prefs()) {

			preferences.put(
				preference.name(),
				new Preference(
					preference.isReadOnly(),
					Arrays.asList(preference.values())));
		}

		Map<String, String> securityRoleRefs = new HashMap<>();

		for (SecurityRoleRef securityRoleRef :
				portletConfiguration.roleRefs()) {

			securityRoleRefs.put(
				securityRoleRef.roleName(), securityRoleRef.roleLink());
		}

		Set<PortletDependency> portletDependencies = new HashSet<>();

		for (Dependency dependency : portletConfiguration.dependencies()) {
			portletDependencies.add(
				new PortletDependency(
					dependency.name(), dependency.scope(),
					dependency.version()));
		}

		Multipart multipart = portletConfiguration.multipart();

		MultipartConfig multipartConfig = MultipartConfig.UNSUPPORTED;

		if (multipart.supported()) {
			multipartConfig = new MultipartConfig(
				multipart.fileSizeThreshold(), multipart.location(),
				multipart.maxFileSize(), multipart.maxRequestSize());
		}

		String displayCategory = descriptorDisplayCategories.get(
			configuredPortletName);

		LiferayPortletConfiguration liferayPortletConfiguration =
			_getAnnotatedLiferayConfiguration(
				discoveredClasses, configuredPortletName);

		String[] propertyNames = null;

		if (liferayPortletConfiguration != null) {
			propertyNames = liferayPortletConfiguration.properties();
		}

		Map<String, Set<String>> liferayConfiguration = new HashMap<>();

		if ((propertyNames != null) && (propertyNames.length > 0)) {
			for (String propertyString : propertyNames) {
				String propertyName = null;
				String propertyValue = null;

				int equalsPos = propertyString.indexOf(CharPool.EQUAL);

				if (equalsPos > 0) {
					propertyValue = propertyString.substring(equalsPos + 1);

					propertyName = propertyString.substring(0, equalsPos);

					if (Validator.isNull(displayCategory) &&
						propertyName.equals(
							"com.liferay.portlet.display-category")) {

						displayCategory = propertyValue;

						continue;
					}
				}

				Set<String> values = liferayConfiguration.get(propertyName);

				if (values == null) {
					values = new LinkedHashSet<>();

					liferayConfiguration.put(propertyName, values);
				}

				values.add(propertyValue);
			}
		}

		Map<String, Set<String>> descriptorLiferayConfiguration =
			descriptorLiferayConfigurations.get(configuredPortletName);

		if (descriptorLiferayConfiguration != null) {
			liferayConfiguration.putAll(descriptorLiferayConfiguration);
		}

		BeanPortlet descriptorBeanPortlet = beanPortlets.get(
			configuredPortletName);

		Set<String> supportedLocales = new LinkedHashSet<>(
			Arrays.asList(portletConfiguration.supportedLocales()));

		Set<QName> supportedProcessingEvents = new HashSet<>();
		Set<QName> supportedPublishingEvents = new HashSet<>();

		BeanMethodIndexUtil.scanSupportedEvents(
			beanMethodMap, supportedProcessingEvents,
			supportedPublishingEvents);

		Set<String> supportedPublicRenderParameters = new LinkedHashSet<>(
			Arrays.asList(portletConfiguration.publicParams()));

		containerRuntimeOptions = new HashMap<>();

		for (RuntimeOption runtimeOption :
				portletConfiguration.runtimeOptions()) {

			containerRuntimeOptions.put(
				runtimeOption.name(), Arrays.asList(runtimeOption.values()));
		}

		if (descriptorBeanPortlet == null) {
			BeanPortlet annotatedBeanPortlet = new BeanPortletImpl(
				portletConfiguration.asyncSupported(), beanMethodMap,
				containerRuntimeOptions, descriptions, displayCategory,
				displayNames, portletConfiguration.cacheExpirationTime(),
				initParams, keywords, liferayConfiguration, multipartConfig,
				discoveredClass.getName(), portletDependencies,
				portletConfiguration.portletName(), preferences,
				preferencesValidator, portletConfiguration.resourceBundle(),
				securityRoleRefs, shortTitles, supportedLocales,
				supportedPortletModes, supportedProcessingEvents,
				supportedPublicRenderParameters, supportedPublishingEvents,
				supportedWindowStates, titles);

			beanPortlets.put(configuredPortletName, annotatedBeanPortlet);

			return;
		}

		String portletName = descriptorBeanPortlet.getPortletName();

		if (Validator.isNull(portletName)) {
			portletName = portletConfiguration.portletName();
		}

		Set<BeanPortletMethod> beanPortletMethods = new HashSet<>();

		for (Map.Entry<BeanPortletMethodType, List<BeanPortletMethod>> entry :
				beanMethodMap.entrySet()) {

			beanPortletMethods.addAll(entry.getValue());
		}

		Map<BeanPortletMethodType, List<BeanPortletMethod>>
			descriptorBeanMethodMap = descriptorBeanPortlet.getBeanMethods();

		for (List<BeanPortletMethod> curBeanPortletMethods :
				descriptorBeanMethodMap.values()) {

			beanPortletMethods.addAll(curBeanPortletMethods);
		}

		beanMethodMap = BeanMethodIndexUtil.indexBeanMethods(
			beanPortletMethods);

		BeanMethodIndexUtil.scanSupportedEvents(
			beanMethodMap, supportedProcessingEvents,
			supportedPublishingEvents);

		displayNames.putAll(descriptorBeanPortlet.getDisplayNames());

		String portletClassName = descriptorBeanPortlet.getPortletClassName();

		if (Validator.isNull(portletClassName)) {
			portletClassName = discoveredClass.getName();
		}

		initParams.putAll(descriptorBeanPortlet.getInitParams());

		int expirationCache = descriptorBeanPortlet.getExpirationCache();

		if (expirationCache <= 0) {
			expirationCache = portletConfiguration.cacheExpirationTime();
		}

		supportedPortletModes.putAll(
			descriptorBeanPortlet.getSupportedPortletModes());

		supportedWindowStates.putAll(
			descriptorBeanPortlet.getSupportedWindowStates());

		supportedLocales.addAll(descriptorBeanPortlet.getSupportedLocales());

		String resourceBundle = descriptorBeanPortlet.getResourceBundle();

		if (Validator.isNull(resourceBundle)) {
			resourceBundle = portletConfiguration.resourceBundle();
		}

		titles.putAll(descriptorBeanPortlet.getTitles());

		shortTitles.putAll(descriptorBeanPortlet.getShortTitles());

		keywords.putAll(descriptorBeanPortlet.getKeywords());

		descriptions.putAll(descriptorBeanPortlet.getDescriptions());

		preferences.putAll(descriptorBeanPortlet.getPreferences());

		if (Validator.isNotNull(
				descriptorBeanPortlet.getPreferencesValidator())) {

			preferencesValidator =
				descriptorBeanPortlet.getPreferencesValidator();
		}

		securityRoleRefs.putAll(descriptorBeanPortlet.getSecurityRoleRefs());

		supportedPublicRenderParameters.addAll(
			descriptorBeanPortlet.getSupportedPublicRenderParameters());

		Map<String, List<String>> descriptorContainerRuntimeOptions =
			descriptorBeanPortlet.getContainerRuntimeOptions();

		for (Map.Entry<String, List<String>> entry :
				descriptorContainerRuntimeOptions.entrySet()) {

			if (entry.getValue() != null) {
				String optionName = entry.getKey();

				List<String> existingOptionValues = containerRuntimeOptions.get(
					optionName);

				if (existingOptionValues == null) {
					containerRuntimeOptions.put(optionName, entry.getValue());
				}
				else {
					List<String> mergedOptions = new ArrayList<>(
						existingOptionValues);

					mergedOptions.addAll(entry.getValue());

					containerRuntimeOptions.put(optionName, mergedOptions);
				}
			}
		}

		portletDependencies.addAll(
			descriptorBeanPortlet.getPortletDependencies());

		boolean asyncSupport = false;

		if (portletConfiguration.asyncSupported() ||
			descriptorBeanPortlet.isAsyncSupported()) {

			asyncSupport = true;
		}

		multipartConfig = multipartConfig.merge(
			descriptorBeanPortlet.getMultipartConfig());

		if (descriptorBeanPortlet.getDisplayCategory() != null) {
			displayCategory = descriptorBeanPortlet.getDisplayCategory();
		}

		descriptorLiferayConfiguration =
			descriptorBeanPortlet.getLiferayConfiguration();

		if (descriptorLiferayConfiguration != null) {
			liferayConfiguration.putAll(descriptorLiferayConfiguration);
		}

		BeanPortlet mergedBeanPortlet = new BeanPortletImpl(
			asyncSupport, beanMethodMap, containerRuntimeOptions, descriptions,
			displayCategory, displayNames, expirationCache, initParams,
			keywords, liferayConfiguration, multipartConfig, portletClassName,
			portletDependencies, portletName, preferences, preferencesValidator,
			resourceBundle, securityRoleRefs, shortTitles, supportedLocales,
			supportedPortletModes, supportedProcessingEvents,
			supportedPublicRenderParameters, supportedPublishingEvents,
			supportedWindowStates, titles);

		beanPortlets.put(configuredPortletName, mergedBeanPortlet);
	}

	private void _addBeanPortletsFromDiscoveredClasses(
		BeanApp beanApp, BeanPortletMethodFactory beanPortletMethodFactory,
		Map<String, BeanPortlet> beanPortlets,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations,
		Set<Class<?>> discoveredClasses,
		Function<String, Set<BeanPortletMethod>> portletBeanMethodsFunction,
		Function<String, String> preferencesValidatorFunction) {

		for (Class<?> discoveredClass : discoveredClasses) {
			PortletConfigurations portletConfigurations =
				discoveredClass.getAnnotation(PortletConfigurations.class);

			if (portletConfigurations == null) {
				continue;
			}

			for (PortletConfiguration portletConfiguration :
					portletConfigurations.value()) {

				Set<BeanPortletMethod> beanPortletMethods = new HashSet<>(
					portletBeanMethodsFunction.apply(
						portletConfiguration.portletName()));

				PortletScannerUtil.scanNonannotatedBeanMethods(
					discoveredClass, beanPortletMethodFactory,
					beanPortletMethods);

				Map<BeanPortletMethodType, List<BeanPortletMethod>>
					beanMethodMap = BeanMethodIndexUtil.indexBeanMethods(
						beanPortletMethods);

				_addBeanPortlet(
					beanApp, beanMethodMap, beanPortlets,
					descriptorDisplayCategories,
					descriptorLiferayConfigurations, discoveredClass,
					discoveredClasses, portletConfiguration,
					preferencesValidatorFunction);
			}
		}

		for (Class<?> discoveredClass : discoveredClasses) {
			PortletConfiguration portletConfiguration =
				discoveredClass.getAnnotation(PortletConfiguration.class);

			if (portletConfiguration == null) {
				continue;
			}

			Set<BeanPortletMethod> beanPortletMethods = new HashSet<>(
				portletBeanMethodsFunction.apply(
					portletConfiguration.portletName()));

			PortletScannerUtil.scanNonannotatedBeanMethods(
				discoveredClass, beanPortletMethodFactory, beanPortletMethods);

			Map<BeanPortletMethodType, List<BeanPortletMethod>> beanMethodMap =
				BeanMethodIndexUtil.indexBeanMethods(beanPortletMethods);

			_addBeanPortlet(
				beanApp, beanMethodMap, beanPortlets,
				descriptorDisplayCategories, descriptorLiferayConfigurations,
				discoveredClass, discoveredClasses, portletConfiguration,
				preferencesValidatorFunction);
		}
	}

	private void _addBeanPortletsFromLiferayDescriptor(
		Map<String, BeanPortlet> beanPortlets,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations,
		Function<String, Set<BeanPortletMethod>> portletBeanMethodsFunction) {

		for (Map.Entry<String, Map<String, Set<String>>> entry :
				descriptorLiferayConfigurations.entrySet()) {

			String portletName = entry.getKey();

			BeanPortlet beanPortlet = beanPortlets.get(portletName);

			if (beanPortlet == null) {
				Set<QName> supportedProcessingEvents = new HashSet<>();
				Set<QName> supportedPublishingEvents = new HashSet<>();

				Map<BeanPortletMethodType, List<BeanPortletMethod>>
					beanMethodMap = BeanMethodIndexUtil.indexBeanMethods(
						portletBeanMethodsFunction.apply(portletName));

				BeanMethodIndexUtil.scanSupportedEvents(
					beanMethodMap, supportedProcessingEvents,
					supportedPublishingEvents);

				beanPortlet = new BeanPortletImpl(
					beanMethodMap, descriptorDisplayCategories.get(portletName),
					entry.getValue(), portletName, supportedProcessingEvents,
					supportedPublishingEvents);

				beanPortlets.put(portletName, beanPortlet);
			}
		}
	}

	private void _addBeanPortletsFromScannedMethods(
		Map<String, BeanPortlet> beanPortlets,
		List<DiscoveredBeanMethod> discoveredBeanMethods,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations,
		Function<String, Set<BeanPortletMethod>> portletBeanMethodsFunction) {

		Set<String> portletNames = new HashSet<>();

		for (DiscoveredBeanMethod discoveredBeanMethod :
				discoveredBeanMethods) {

			Collections.addAll(
				portletNames, discoveredBeanMethod.getPortletNames());
		}

		for (String portletName : portletNames) {
			if (Objects.equals("*", portletName)) {
				continue;
			}

			BeanPortlet beanPortlet = beanPortlets.get(portletName);

			if (beanPortlet == null) {
				Map<BeanPortletMethodType, List<BeanPortletMethod>>
					beanMethodMap = BeanMethodIndexUtil.indexBeanMethods(
						portletBeanMethodsFunction.apply(portletName));

				Set<QName> supportedProcessingEvents = new HashSet<>();
				Set<QName> supportedPublishingEvents = new HashSet<>();

				BeanMethodIndexUtil.scanSupportedEvents(
					beanMethodMap, supportedProcessingEvents,
					supportedPublishingEvents);

				beanPortlet = new BeanPortletImpl(
					beanMethodMap, descriptorDisplayCategories.get(portletName),
					descriptorLiferayConfigurations.get(portletName),
					portletName, supportedProcessingEvents,
					supportedPublishingEvents);

				beanPortlets.put(portletName, beanPortlet);
			}
		}
	}

	private Function<String, Set<BeanPortletMethod>> _collectPortletBeanMethods(
		BeanPortletMethodFactory beanPortletMethodFactory,
		List<DiscoveredBeanMethod> discoveredBeanMethods) {

		Set<BeanPortletMethod> wildcardBeanPortletMethods = new HashSet<>();

		Map<String, Set<BeanPortletMethod>> portletBeanMethods =
			new HashMap<>();

		for (DiscoveredBeanMethod discoveredBeanMethod :
				discoveredBeanMethods) {

			String[] portletNames = discoveredBeanMethod.getPortletNames();

			if (portletNames == null) {
				_log.error(
					"Portlet names cannot be null for annotated method " +
						discoveredBeanMethod);

				continue;
			}

			BeanPortletMethod beanPortletMethod =
				beanPortletMethodFactory.create(
					discoveredBeanMethod.getBeanType(),
					discoveredBeanMethod.getMethodType(),
					discoveredBeanMethod.getMethod());

			if ((portletNames.length > 0) &&
				Objects.equals(portletNames[0], "*")) {

				wildcardBeanPortletMethods.add(beanPortletMethod);
			}
			else {
				for (String portletName : portletNames) {
					Set<BeanPortletMethod> beanPortletMethods =
						portletBeanMethods.get(portletName);

					if (beanPortletMethods == null) {
						beanPortletMethods = new HashSet<>();

						portletBeanMethods.put(portletName, beanPortletMethods);
					}

					beanPortletMethods.add(beanPortletMethod);
				}
			}
		}

		return portletName -> {
			Set<BeanPortletMethod> beanPortletMethods = portletBeanMethods.get(
				portletName);

			if (beanPortletMethods == null) {
				return wildcardBeanPortletMethods;
			}

			beanPortletMethods = new HashSet<>(beanPortletMethods);

			beanPortletMethods.addAll(wildcardBeanPortletMethods);

			return beanPortletMethods;
		};
	}

	private Function<String, String> _collectPreferencesValidators(
		Set<Class<?>> discoveredClasses) {

		String wildcardPreferencesValidator = null;

		Map<String, String> preferencesValidators = new HashMap<>();

		for (Class<?> discoveredClass : discoveredClasses) {
			PortletPreferencesValidator portletPreferencesValidator =
				discoveredClass.getAnnotation(
					PortletPreferencesValidator.class);

			if (portletPreferencesValidator == null) {
				continue;
			}

			String[] portletNames = portletPreferencesValidator.portletNames();

			for (String portletName : portletNames) {
				if (Objects.equals(portletName, "*")) {
					if (wildcardPreferencesValidator == null) {
						wildcardPreferencesValidator =
							discoveredClass.getName();
					}
				}
				else {
					if (preferencesValidators.containsKey(portletName)) {
						_log.error(
							StringBundler.concat(
								"Only one @PortletPreferencesValidator ",
								"annotation may be associated with ",
								"portletName \"", portletName, "\""));
					}
					else {
						preferencesValidators.put(
							portletName, discoveredClass.getName());
					}
				}
			}
		}

		String defaultPreferencesValidator = wildcardPreferencesValidator;

		return portletName -> preferencesValidators.getOrDefault(
			portletName, defaultPreferencesValidator);
	}

	private LiferayPortletConfiguration _getAnnotatedLiferayConfiguration(
		Set<Class<?>> discoveredClasses, String portletName) {

		for (Class<?> discoveredClass : discoveredClasses) {
			LiferayPortletConfiguration liferayPortletConfiguration =
				discoveredClass.getAnnotation(
					LiferayPortletConfiguration.class);

			if (liferayPortletConfiguration == null) {
				continue;
			}

			if (portletName.equals(liferayPortletConfiguration.portletName())) {
				return liferayPortletConfiguration;
			}
		}

		for (Class<?> discoveredClass : discoveredClasses) {
			LiferayPortletConfigurations liferayPortletConfigurations =
				discoveredClass.getAnnotation(
					LiferayPortletConfigurations.class);

			if (liferayPortletConfigurations == null) {
				continue;
			}

			for (LiferayPortletConfiguration liferayPortletConfiguration :
					liferayPortletConfigurations.value()) {

				if (portletName.equals(
						liferayPortletConfiguration.portletName())) {

					return liferayPortletConfiguration;
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanPortletRegistrarImpl.class);

	private static final PortletApplication _portletApplication =
		new PortletApplication() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return PortletApplication.class;
			}

			@Override
			public CustomPortletMode[] customPortletModes() {
				return _customPortletModes;
			}

			@Override
			public CustomWindowState[] customWindowStates() {
				return _customWindowStates;
			}

			@Override
			public String defaultNamespaceURI() {
				return "";
			}

			@Override
			public EventDefinition[] events() {
				return _eventDefinitions;
			}

			@Override
			public PublicRenderParameterDefinition[] publicParams() {
				return _publicRenderParameterDefinitions;
			}

			@Override
			public String resourceBundle() {
				return "";
			}

			@Override
			public RuntimeOption[] runtimeOptions() {
				return _runtimeOptions;
			}

			@Override
			public UserAttribute[] userAttributes() {
				return _userAttributes;
			}

			@Override
			public String version() {
				return "3.0";
			}

			private final CustomPortletMode[] _customPortletModes = {};
			private final CustomWindowState[] _customWindowStates = {};
			private final EventDefinition[] _eventDefinitions = {};
			private final PublicRenderParameterDefinition[]
				_publicRenderParameterDefinitions = {};
			private final RuntimeOption[] _runtimeOptions = {};
			private final UserAttribute[] _userAttributes = {};

		};

}