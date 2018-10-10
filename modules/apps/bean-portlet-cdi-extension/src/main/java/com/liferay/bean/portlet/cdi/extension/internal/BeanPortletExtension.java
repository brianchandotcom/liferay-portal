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

import com.liferay.bean.portlet.LiferayPortletConfiguration;
import com.liferay.bean.portlet.LiferayPortletConfigurations;
import com.liferay.bean.portlet.cdi.extension.internal.annotated.BeanPortletAnnotationImpl;
import com.liferay.bean.portlet.cdi.extension.internal.annotated.type.ApplicationScopedAnnotatedTypeImpl;
import com.liferay.bean.portlet.cdi.extension.internal.annotated.type.PortletConfigAnnotatedTypeImpl;
import com.liferay.bean.portlet.cdi.extension.internal.annotated.type.RequestScopedAnnotatedTypeImpl;
import com.liferay.bean.portlet.cdi.extension.internal.annotated.type.SessionScopedAnnotatedTypeImpl;
import com.liferay.bean.portlet.cdi.extension.internal.scope.JSR362BeanProducer;
import com.liferay.bean.portlet.cdi.extension.internal.scope.PortletRequestBeanContext;
import com.liferay.bean.portlet.cdi.extension.internal.scope.PortletSessionBeanContext;
import com.liferay.bean.portlet.cdi.extension.internal.scope.RenderStateBeanContext;
import com.liferay.bean.portlet.cdi.extension.internal.scope.ScopedBean;
import com.liferay.bean.portlet.cdi.extension.internal.xml.DisplayDescriptorParser;
import com.liferay.bean.portlet.cdi.extension.internal.xml.LiferayDescriptorParser;
import com.liferay.bean.portlet.cdi.extension.internal.xml.PortletDescriptorParser;
import com.liferay.bean.portlet.cdi.extension.internal.xml.PortletQNameUtil;
import com.liferay.bean.portlet.cdi.extension.internal.xml.PortletScannerUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.net.URL;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.annotations.ContextPath;
import javax.portlet.annotations.CustomPortletMode;
import javax.portlet.annotations.CustomWindowState;
import javax.portlet.annotations.EventDefinition;
import javax.portlet.annotations.InitParameter;
import javax.portlet.annotations.Namespace;
import javax.portlet.annotations.PortletApplication;
import javax.portlet.annotations.PortletConfiguration;
import javax.portlet.annotations.PortletConfigurations;
import javax.portlet.annotations.PortletLifecycleFilter;
import javax.portlet.annotations.PortletListener;
import javax.portlet.annotations.PortletName;
import javax.portlet.annotations.PortletPreferencesValidator;
import javax.portlet.annotations.PortletQName;
import javax.portlet.annotations.PortletRequestScoped;
import javax.portlet.annotations.PortletSerializable;
import javax.portlet.annotations.PortletSessionScoped;
import javax.portlet.annotations.PublicRenderParameterDefinition;
import javax.portlet.annotations.RenderStateScoped;
import javax.portlet.annotations.RuntimeOption;
import javax.portlet.annotations.UserAttribute;
import javax.portlet.annotations.WindowId;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.HeaderFilter;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import javax.xml.namespace.QName;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Neil Griffin
 * @author Raymond Augé
 */
public class BeanPortletExtension implements Extension {

	public void step1BeforeBeanDiscovery(
		@Observes BeforeBeanDiscovery beforeBeanDiscovery,
		BeanManager beanManager) {

		if (_log.isDebugEnabled()) {
			_log.debug("Scanning for bean portlets and bean filters");
		}

		beforeBeanDiscovery.addQualifier(ContextPath.class);
		beforeBeanDiscovery.addQualifier(Namespace.class);
		beforeBeanDiscovery.addQualifier(PortletName.class);
		beforeBeanDiscovery.addQualifier(WindowId.class);

		beforeBeanDiscovery.addScope(PortletRequestScoped.class, true, false);
		beforeBeanDiscovery.addScope(PortletSessionScoped.class, true, true);
		beforeBeanDiscovery.addScope(RenderStateScoped.class, true, false);

		AnnotatedType<?> beanAnnotatedType = beanManager.createAnnotatedType(
			JSR362BeanProducer.class);

		beforeBeanDiscovery.addAnnotatedType(beanAnnotatedType, null);
	}

	public <T> void step2ProcessAnnotatedType(
		@Observes ProcessAnnotatedType<T> processAnnotatedType) {

		if (_log.isDebugEnabled()) {
			_log.debug("processAnnotatedType=" + processAnnotatedType);
		}

		AnnotatedType<T> annotatedType =
			processAnnotatedType.getAnnotatedType();

		Class<T> annotatedClass = annotatedType.getJavaClass();

		Set<Type> typeClosures = annotatedType.getTypeClosure();

		if (typeClosures.contains(PortletConfig.class)) {
			annotatedType = new PortletConfigAnnotatedTypeImpl<>(annotatedType);

			processAnnotatedType.setAnnotatedType(annotatedType);
		}

		Set<Class<? extends Annotation>> annotationClasses = new HashSet<>();

		for (Annotation annotation : annotatedType.getAnnotations()) {
			annotationClasses.add(annotation.annotationType());
		}

		if (annotationClasses.contains(RequestScoped.class)) {
			annotatedType = new RequestScopedAnnotatedTypeImpl<>(
				annotatedType, annotationClasses);

			processAnnotatedType.setAnnotatedType(annotatedType);
		}

		if (annotationClasses.contains(SessionScoped.class)) {
			annotatedType = new SessionScopedAnnotatedTypeImpl<>(
				annotatedType, annotationClasses);

			processAnnotatedType.setAnnotatedType(annotatedType);
		}

		if (annotationClasses.contains(RenderStateScoped.class) &&
			!PortletSerializable.class.isAssignableFrom(annotatedClass)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					annotatedClass.getName() + " does not implement " +
						PortletSerializable.class.getName());
			}
		}

		if (annotationClasses.contains(PortletSessionScoped.class)) {
			PortletSessionScoped portletSessionScoped =
				annotatedClass.getAnnotation(PortletSessionScoped.class);

			if ((PortletSession.APPLICATION_SCOPE !=
					portletSessionScoped.value()) &&
				(PortletSession.PORTLET_SCOPE !=
					portletSessionScoped.value())) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"@PortletSessionScoped bean can only be " +
							"PortletSession.APPLICATION_SCOPE or " +
								"PortletSession.PORTLET_SCOPE");
				}
			}
		}

		if (Portlet.class.isAssignableFrom(annotatedClass) &&
			!annotationClasses.contains(ApplicationScoped.class)) {

			annotatedType = new ApplicationScopedAnnotatedTypeImpl<>(
				annotatedType);

			processAnnotatedType.setAnnotatedType(annotatedType);

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Automatically added @ApplicationScoped to " +
						annotatedClass);
			}
		}

		if (annotationClasses.contains(PortletApplication.class)) {
			if (_portletApplicationClass == null) {
				_portletApplicationClass = annotatedClass;
			}
			else {
				_log.error(
					"Found more than one @PortletApplication annotated class");
			}
		}

		if (annotationClasses.contains(PortletConfigurations.class)) {
			_portletConfigurationsClasses.add(annotatedClass);
		}

		if (annotationClasses.contains(PortletConfiguration.class)) {
			_portletConfigurationClasses.add(annotatedClass);
		}

		if (annotationClasses.contains(PortletLifecycleFilter.class)) {
			_portletLifecycleFilterClasses.add(annotatedClass);
		}

		if (annotationClasses.contains(PortletListener.class)) {
			_portletListenerClasses.add(annotatedClass);
		}

		if (annotationClasses.contains(PortletPreferencesValidator.class)) {
			_preferencesValidatorClasses.add(annotatedClass);
		}

		if (annotationClasses.contains(LiferayPortletConfigurations.class)) {
			_liferayPortletConfigurationsClasses.add(annotatedClass);
		}

		if (annotationClasses.contains(LiferayPortletConfiguration.class)) {
			_liferayPortletConfigurationClasses.add(annotatedClass);
		}

		_scanMethods(_scannedMethods, annotatedClass);
	}

	public void step3AfterBeanDiscovery(
		@Observes AfterBeanDiscovery afterBeanDiscovery) {

		afterBeanDiscovery.addContext(new PortletRequestBeanContext());
		afterBeanDiscovery.addContext(new PortletSessionBeanContext());
		afterBeanDiscovery.addContext(new RenderStateBeanContext());
	}

	@SuppressWarnings({"serial", "unchecked"})
	public void step4ApplicationScopedInitialized(
		@Initialized(ApplicationScoped.class)
			@Observes ServletContext servletContext,
		BeanManager beanManager) {

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
			catch (Exception e) {
				_log.error(e, e);
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
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		Function<String, Set<BeanMethod>> portletBeanMethodsFunction =
			_collectPortletBeanMethods(beanManager);

		Map<String, String> preferencesValidators =
			_collectPreferencesValidators();

		Set<String> wildcardPreferencesValidators =
			_collectWildcardPreferencesValidators();

		_addBeanPortletsAndFiltersFromPortletDescriptor(
			bundle, beanManager, portletBeanMethodsFunction,
			preferencesValidators, wildcardPreferencesValidators,
			descriptorDisplayCategories, descriptorLiferayConfigurations);

		_addBeanFiltersFromAnnotatedClasses();

		_addBeanPortletsFromAnnotatedClasses(
			beanManager, portletBeanMethodsFunction, preferencesValidators,
			wildcardPreferencesValidators, descriptorDisplayCategories,
			descriptorLiferayConfigurations);

		_addBeanBeanPortletsFromScannedMethods(
			portletBeanMethodsFunction, descriptorDisplayCategories,
			descriptorLiferayConfigurations);

		if (!descriptorLiferayConfigurations.isEmpty()) {
			_addBeanPortletsFromLiferayDescriptor(
				portletBeanMethodsFunction, descriptorDisplayCategories,
				descriptorLiferayConfigurations);
		}

		List<String> beanPortletIds = (List<String>)servletContext.getAttribute(
			WebKeys.BEAN_PORTLET_IDS);

		if (beanPortletIds == null) {
			beanPortletIds = new ArrayList<>();
		}

		for (BeanPortlet beanPortlet : _beanPortlets.values()) {
			ServiceRegistration<Portlet> portletServiceRegistration =
				RegistrationUtil.registerBeanPortlet(
					bundleContext, _beanApp, beanPortlet, servletContext,
					beanPortletIds);

			if (portletServiceRegistration != null) {
				_portletRegistrations.add(portletServiceRegistration);
			}

			ServiceRegistration<ResourceBundleLoader>
				resourceBundleLoaderserviceRegistration =
					RegistrationUtil.registerResourceBundleLoader(
						bundleContext, beanPortlet, servletContext);

			if (resourceBundleLoaderserviceRegistration != null) {
				_resourceBundleLoaderRegistrations.add(
					resourceBundleLoaderserviceRegistration);
			}
		}

		servletContext.setAttribute(WebKeys.BEAN_PORTLET_IDS, beanPortletIds);

		for (BeanFilter beanFilter : _beanFilters.values()) {
			for (String portletName : beanFilter.getPortletNames()) {
				_filterRegistrations.addAll(
					RegistrationUtil.registerBeanFilter(
						bundleContext, portletName, _beanPortlets.keySet(),
						beanFilter, beanManager, servletContext));
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

		_servletRegistration = bundleContext.registerService(
			Servlet.class, new PortletServlet() {}, properties);

		if (!descriptorLiferayConfigurations.isEmpty() &&
			_log.isWarnEnabled()) {

			for (String portletName :
					descriptorLiferayConfigurations.keySet()) {

				if (_beanPortlets.containsKey(portletName)) {
					continue;
				}

				_log.warn(
					StringBundler.concat(
						"Portlet with the name ", portletName,
						" is described in liferay-portlet.xml but does not ",
						"have a matching entry in portlet.xml or ",
						"@PortletConfiguration annotation"));
			}
		}

		if (displayDescriptorURL != null) {
			try {
				Map<String, String> categoryMap = DisplayDescriptorParser.parse(
					displayDescriptorURL);

				for (String portletName : categoryMap.keySet()) {
					if (_beanPortlets.containsKey(portletName)) {
						continue;
					}

					_log.error(
						"Unknown portlet ID " + portletName +
							" found in liferay-display.xml");
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Discovered ", _beanPortlets.size(), " bean portlets and ",
					_beanFilters.size(), " bean filters for ",
					servletContext.getServletContextName()));
		}
	}

	public void step5SessionScopeBeforeDestroyed(
		@Destroyed(SessionScoped.class) @Observes Object httpSessionObject) {

		HttpSession httpSession = (HttpSession)httpSessionObject;

		Enumeration<String> enumeration = httpSession.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();

			Object value = httpSession.getAttribute(name);

			if (value instanceof ScopedBean) {
				ScopedBean<?> scopedBean = (ScopedBean<?>)value;

				scopedBean.destroy();
			}
		}
	}

	public void step6ApplicationScopedBeforeDestroyed(
		@Destroyed(ApplicationScoped.class) @Observes Object ignore) {

		if (_servletRegistration != null) {
			try {
				_servletRegistration.unregister();
			}
			catch (IllegalStateException ise) {
				if (_log.isWarnEnabled()) {
					_log.warn(ise, ise);
				}
			}
		}

		for (ServiceRegistration<PortletFilter> serviceRegistration :
				_filterRegistrations) {

			serviceRegistration.unregister();
		}

		_filterRegistrations.clear();

		for (ServiceRegistration<Portlet> serviceRegistration :
				_portletRegistrations) {

			serviceRegistration.unregister();
		}

		_portletRegistrations.clear();

		for (ServiceRegistration<ResourceBundleLoader> serviceRegistration :
				_resourceBundleLoaderRegistrations) {

			serviceRegistration.unregister();
		}

		_resourceBundleLoaderRegistrations.clear();
	}

	private void _addBeanBeanPortletsFromScannedMethods(
		Function<String, Set<BeanMethod>> portletBeanMethodsFunction,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations) {

		Set<String> portletNames = new HashSet<>();

		for (ScannedMethod scannedMethod : _scannedMethods) {
			Collections.addAll(portletNames, scannedMethod.getPortletNames());
		}

		for (String portletName : portletNames) {
			if (Objects.equals("*", portletName)) {
				continue;
			}

			BeanPortlet beanPortlet = _beanPortlets.get(portletName);

			if (beanPortlet == null) {
				beanPortlet = new BeanPortletDefaultImpl(
					portletName, portletBeanMethodsFunction.apply(portletName),
					descriptorDisplayCategories.get(portletName),
					descriptorLiferayConfigurations.get(portletName));

				_beanPortlets.put(portletName, beanPortlet);
			}
		}
	}

	private void _addBeanFiltersFromAnnotatedClasses() {
		for (Class<?> annotatedClass : _portletLifecycleFilterClasses) {
			PortletLifecycleFilter portletLifecycleFilter =
				annotatedClass.getAnnotation(PortletLifecycleFilter.class);

			Map<String, String> initParams = new HashMap<>();

			for (InitParameter initParameter :
					portletLifecycleFilter.initParams()) {

				initParams.put(initParameter.name(), initParameter.value());
			}

			Set<String> lifecycles = new LinkedHashSet<>();

			if (ActionFilter.class.isAssignableFrom(annotatedClass)) {
				lifecycles.add(PortletRequest.ACTION_PHASE);
			}

			if (EventFilter.class.isAssignableFrom(annotatedClass)) {
				lifecycles.add(PortletRequest.EVENT_PHASE);
			}

			if (HeaderFilter.class.isAssignableFrom(annotatedClass)) {
				lifecycles.add(PortletRequest.HEADER_PHASE);
			}

			if (RenderFilter.class.isAssignableFrom(annotatedClass)) {
				lifecycles.add(PortletRequest.RENDER_PHASE);
			}

			if (ResourceFilter.class.isAssignableFrom(annotatedClass)) {
				lifecycles.add(PortletRequest.RESOURCE_PHASE);
			}

			Set<String> portletNames = new HashSet<>(
				Arrays.asList(portletLifecycleFilter.portletNames()));

			BeanFilter annotatedBeanFilter = new BeanFilterImpl(
				portletLifecycleFilter.filterName(), annotatedClass,
				portletLifecycleFilter.ordinal(), portletNames, lifecycles,
				initParams);

			BeanFilter descriptorBeanFilter = _beanFilters.get(
				portletLifecycleFilter.filterName());

			if (descriptorBeanFilter == null) {
				_beanFilters.put(
					portletLifecycleFilter.filterName(), annotatedBeanFilter);
			}
			else {
				portletNames.addAll(descriptorBeanFilter.getPortletNames());

				lifecycles.addAll(descriptorBeanFilter.getLifecycles());

				initParams.putAll(descriptorBeanFilter.getInitParams());

				_beanFilters.put(
					portletLifecycleFilter.filterName(),
					new BeanFilterImpl(
						descriptorBeanFilter.getFilterName(),
						descriptorBeanFilter.getFilterClass(),
						descriptorBeanFilter.getOrdinal(), portletNames,
						lifecycles, initParams));
			}
		}
	}

	private void _addBeanPortlet(
		Class<?> beanPortletClass, Set<BeanMethod> beanMethods,
		PortletConfiguration portletConfiguration,
		Map<String, String> preferencesValidators,
		Set<String> wildcardPreferencesValidators,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations) {

		String configuredPortletName = portletConfiguration.portletName();

		if (Validator.isNull(configuredPortletName)) {
			_log.error(
				"Invalid portlet name attribute for " +
					beanPortletClass.getName());

			return;
		}

		PortletApplication portletApplication = null;

		if (_portletApplicationClass != null) {
			portletApplication = _portletApplicationClass.getAnnotation(
				PortletApplication.class);
		}

		if (portletApplication == null) {
			portletApplication = _portletApplication;
		}

		String specVersion = GetterUtil.getString(
			portletApplication.version(), "3.0");

		if (Validator.isNotNull(_beanApp.getSpecVersion())) {
			specVersion = _beanApp.getSpecVersion();
		}

		String defaultNamespace = portletApplication.defaultNamespaceURI();

		if (Validator.isNotNull(_beanApp.getDefaultNamespace())) {
			defaultNamespace = _beanApp.getDefaultNamespace();
		}

		List<Event> events = new ArrayList<>(_beanApp.getEvents());

		for (EventDefinition eventDefinition : portletApplication.events()) {
			String valueType = null;

			Class<?> payloadType = eventDefinition.payloadType();

			if (payloadType != null) {
				valueType = payloadType.getName();
			}

			List<QName> aliasQNames = new ArrayList<>();

			for (PortletQName portletQName : eventDefinition.alias()) {
				aliasQNames.add(PortletQNameUtil.toQName(portletQName));
			}

			events.add(
				new EventImpl(
					PortletQNameUtil.toQName(eventDefinition.qname()),
					valueType, aliasQNames));
		}

		Map<String, PublicRenderParameter> publicRenderParameters =
			new HashMap<>();

		for (PublicRenderParameterDefinition
				publicRenderParameterDefinition:
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

		publicRenderParameters.putAll(_beanApp.getPublicRenderParameters());

		Map<String, List<String>> containerRuntimeOptions = new HashMap<>();

		for (RuntimeOption runtimeOption :
				portletApplication.runtimeOptions()) {

			containerRuntimeOptions.put(
				runtimeOption.name(), Arrays.asList(runtimeOption.values()));
		}

		containerRuntimeOptions.putAll(_beanApp.getContainerRuntimeOptions());

		Set<String> customPortletModes = new LinkedHashSet<>(
			_beanApp.getCustomPortletModes());

		for (CustomPortletMode customPortletMode :
				portletApplication.customPortletModes()) {

			if (!customPortletMode.portalManaged()) {
				customPortletModes.add(customPortletMode.name());
			}
		}

		List<Map.Entry<Integer, String>> portletListeners = new ArrayList<>();

		for (Class<?> portletListenerClass : _portletListenerClasses) {
			PortletListener portletListener =
				portletListenerClass.getAnnotation(PortletListener.class);

			portletListeners.add(
				new AbstractMap.SimpleImmutableEntry<>(
					portletListener.ordinal(), portletListenerClass.getName()));
		}

		portletListeners.addAll(_beanApp.getPortletListeners());

		_beanApp = new BeanAppImpl(
			specVersion, defaultNamespace, events, publicRenderParameters,
			containerRuntimeOptions, customPortletModes, portletListeners);

		String preferencesValidator = preferencesValidators.get(
			configuredPortletName);

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
						configuredPortletName,
						"\" since is already associated with ",
						preferencesValidator));
			}
		}

		BeanPortlet annotatedBeanPortlet = new BeanPortletAnnotationImpl(
			beanMethods, beanPortletClass.getName(), portletConfiguration,
			preferencesValidator,
			_getAnnotatedLiferayConfiguration(configuredPortletName),
			descriptorLiferayConfigurations.get(configuredPortletName),
			descriptorDisplayCategories.get(configuredPortletName));

		BeanPortlet descriptorBeanPortlet = _beanPortlets.get(
			configuredPortletName);

		if (descriptorBeanPortlet == null) {
			_beanPortlets.put(configuredPortletName, annotatedBeanPortlet);
		}
		else {
			BeanPortlet mergedBeanPortlet = new BeanPortletMergedImpl(
				annotatedBeanPortlet, descriptorBeanPortlet);

			_beanPortlets.put(configuredPortletName, mergedBeanPortlet);
		}
	}

	private void _addBeanPortletsAndFiltersFromPortletDescriptor(
		Bundle bundle, BeanManager beanManager,
		Function<String, Set<BeanMethod>> portletBeanMethodsFunction,
		Map<String, String> preferencesValidators,
		Set<String> wildcardPreferencesValidators,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations) {

		URL portletDescriptorURL = bundle.getEntry("/WEB-INF/portlet.xml");

		if (portletDescriptorURL != null) {
			try {
				_beanApp = PortletDescriptorParser.parse(
					_beanFilters, _beanPortlets, bundle, portletDescriptorURL,
					beanManager, portletBeanMethodsFunction,
					preferencesValidators, wildcardPreferencesValidators,
					descriptorDisplayCategories,
					descriptorLiferayConfigurations);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private void _addBeanPortletsFromAnnotatedClasses(
		BeanManager beanManager,
		Function<String, Set<BeanMethod>> portletBeanMethodsFunction,
		Map<String, String> preferencesValidators,
		Set<String> wildcardPreferencesValidators,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations) {

		for (Class<?> clazz : _portletConfigurationsClasses) {
			PortletConfigurations portletConfigurations = clazz.getAnnotation(
				PortletConfigurations.class);

			for (PortletConfiguration portletConfiguration :
					portletConfigurations.value()) {

				Set<BeanMethod> beanMethods = new HashSet<>(
					portletBeanMethodsFunction.apply(
						portletConfiguration.portletName()));

				PortletScannerUtil.scanNonannotatedBeanMethods(
					beanManager, clazz, beanMethods);

				_addBeanPortlet(
					clazz, beanMethods, portletConfiguration,
					preferencesValidators, wildcardPreferencesValidators,
					descriptorDisplayCategories,
					descriptorLiferayConfigurations);
			}
		}

		for (Class<?> clazz : _portletConfigurationClasses) {
			PortletConfiguration portletConfiguration = clazz.getAnnotation(
				PortletConfiguration.class);

			Set<BeanMethod> beanMethods = new HashSet<>(
				portletBeanMethodsFunction.apply(
					portletConfiguration.portletName()));

			PortletScannerUtil.scanNonannotatedBeanMethods(
				beanManager, clazz, beanMethods);

			_addBeanPortlet(
				clazz, beanMethods, portletConfiguration, preferencesValidators,
				wildcardPreferencesValidators, descriptorDisplayCategories,
				descriptorLiferayConfigurations);
		}
	}

	private void _addBeanPortletsFromLiferayDescriptor(
		Function<String, Set<BeanMethod>> portletBeanMethodsFunction,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations) {

		for (Map.Entry<String, Map<String, Set<String>>> entry :
				descriptorLiferayConfigurations.entrySet()) {

			String portletName = entry.getKey();

			BeanPortlet beanPortlet = _beanPortlets.get(portletName);

			if (beanPortlet == null) {
				beanPortlet = new BeanPortletDefaultImpl(
					portletName, portletBeanMethodsFunction.apply(portletName),
					descriptorDisplayCategories.get(portletName),
					entry.getValue());

				_beanPortlets.put(portletName, beanPortlet);
			}
		}
	}

	private Function<String, Set<BeanMethod>> _collectPortletBeanMethods(
		BeanManager beanManager) {

		Set<BeanMethod> wildcardBeanMethods = _collectWildcardBeanMethods(
			beanManager);

		Map<String, Set<BeanMethod>> portletBeanMethods = new HashMap<>();

		for (ScannedMethod scannedMethod : _scannedMethods) {
			String[] portletNames = scannedMethod.getPortletNames();

			if (portletNames == null) {
				_log.error(
					"Portlet names cannot be null for annotated method " +
						scannedMethod.getMethod());
				continue;
			}

			if ((portletNames.length > 0) && "*".equals(portletNames[0])) {
				continue;
			}

			Class<?> clazz = scannedMethod.getClazz();
			Method method = scannedMethod.getMethod();
			int ordinal = scannedMethod.getOrdinal();

			BeanMethod beanMethod = new BeanMethod(
				beanManager, beanManager.resolve(beanManager.getBeans(clazz)),
				scannedMethod.getMethodType(), method, ordinal);

			for (String portletName : portletNames) {
				Set<BeanMethod> beanMethods = portletBeanMethods.get(
					portletName);

				if (beanMethods == null) {
					beanMethods = new HashSet<>();

					portletBeanMethods.put(portletName, beanMethods);
				}

				beanMethods.add(beanMethod);
			}
		}

		return portletName -> {
			Set<BeanMethod> beanMethods = portletBeanMethods.get(portletName);

			if (beanMethods == null) {
				return wildcardBeanMethods;
			}

			return beanMethods;
		};
	}

	private Map<String, String> _collectPreferencesValidators() {
		Map<String, String> preferencesValidators = new HashMap<>();

		for (Class<?> preferencesValidatorClass :
				_preferencesValidatorClasses) {

			PortletPreferencesValidator portletPreferencesValidator =
				preferencesValidatorClass.getAnnotation(
					PortletPreferencesValidator.class);

			String[] portletNames = portletPreferencesValidator.portletNames();

			for (String portletName : portletNames) {
				if (Objects.equals(portletName, "*")) {
					continue;
				}

				if (preferencesValidators.containsKey(portletName)) {
					_log.error(
						StringBundler.concat(
							"Only one @PortletPreferencesValidator annotation ",
							"may be associated with portletName \"",
							portletName, "\""));
				}
				else {
					preferencesValidators.put(
						portletName, preferencesValidatorClass.getName());
				}
			}
		}

		return preferencesValidators;
	}

	private Set<BeanMethod> _collectWildcardBeanMethods(
		BeanManager beanManager) {

		Set<BeanMethod> wildcardBeanMethods = new HashSet<>();

		for (ScannedMethod scannedMethod : _scannedMethods) {
			String[] portletNames = scannedMethod.getPortletNames();

			if ((portletNames.length > 0) && "*".equals(portletNames[0])) {
				Class<?> clazz = scannedMethod.getClazz();
				Method method = scannedMethod.getMethod();
				int ordinal = scannedMethod.getOrdinal();

				BeanMethod beanMethod = new BeanMethod(
					beanManager,
					beanManager.resolve(beanManager.getBeans(clazz)),
					scannedMethod.getMethodType(), method, ordinal);

				wildcardBeanMethods.add(beanMethod);
			}
		}

		return wildcardBeanMethods;
	}

	private Set<String> _collectWildcardPreferencesValidators() {
		Set<String> wildcardPreferencesValidators = new HashSet<>();

		for (Class<?> preferencesValidatorClass :
				_preferencesValidatorClasses) {

			PortletPreferencesValidator portletPreferencesValidator =
				preferencesValidatorClass.getAnnotation(
					PortletPreferencesValidator.class);

			String[] portletNames = portletPreferencesValidator.portletNames();

			for (String portletName : portletNames) {
				if (Objects.equals(portletName, "*")) {
					wildcardPreferencesValidators.add(
						preferencesValidatorClass.getName());
				}
			}
		}

		return wildcardPreferencesValidators;
	}

	private LiferayPortletConfiguration _getAnnotatedLiferayConfiguration(
		String portletName) {

		for (Class<?> clazz : _liferayPortletConfigurationClasses) {
			LiferayPortletConfiguration liferayPortletConfiguration =
				clazz.getAnnotation(LiferayPortletConfiguration.class);

			if (portletName.equals(liferayPortletConfiguration.portletName())) {
				return liferayPortletConfiguration;
			}
		}

		for (Class<?> clazz : _liferayPortletConfigurationsClasses) {
			LiferayPortletConfigurations liferayPortletConfigurations =
				clazz.getAnnotation(LiferayPortletConfigurations.class);

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

	private void _scanMethods(
		List<ScannedMethod> scannedMethods, Class<?> javaClass) {

		for (Method method : javaClass.getMethods()) {
			for (MethodType methodType : MethodType.values()) {
				if (methodType.isMatch(method)) {
					scannedMethods.add(
						new ScannedMethod(javaClass, methodType, method));
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanPortletExtension.class);

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

	private BeanApp _beanApp = new BeanAppImpl(
		"3.0", null, Collections.emptyList(), Collections.emptyMap(),
		Collections.emptyMap(), Collections.emptySet(),
		Collections.emptyList());
	private final Map<String, BeanFilter> _beanFilters = new HashMap<>();
	private final Map<String, BeanPortlet> _beanPortlets = new HashMap<>();
	private final List<ServiceRegistration<PortletFilter>>
		_filterRegistrations = new ArrayList<>();
	private final List<Class<?>> _liferayPortletConfigurationClasses =
		new ArrayList<>();
	private final List<Class<?>> _liferayPortletConfigurationsClasses =
		new ArrayList<>();
	private Class<?> _portletApplicationClass;
	private final List<Class<?>> _portletConfigurationClasses =
		new ArrayList<>();
	private final List<Class<?>> _portletConfigurationsClasses =
		new ArrayList<>();
	private final List<Class<?>> _portletLifecycleFilterClasses =
		new ArrayList<>();
	private final List<Class<?>> _portletListenerClasses = new ArrayList<>();
	private final List<ServiceRegistration<Portlet>> _portletRegistrations =
		new ArrayList<>();
	private final List<Class<?>> _preferencesValidatorClasses =
		new ArrayList<>();
	private final List<ServiceRegistration<ResourceBundleLoader>>
		_resourceBundleLoaderRegistrations = new ArrayList<>();
	private final List<ScannedMethod> _scannedMethods = new ArrayList<>();
	private ServiceRegistration<Servlet> _servletRegistration;

}