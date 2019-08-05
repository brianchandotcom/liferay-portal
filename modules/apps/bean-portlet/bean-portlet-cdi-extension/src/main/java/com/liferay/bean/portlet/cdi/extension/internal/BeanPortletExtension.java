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
import com.liferay.bean.portlet.cdi.extension.internal.annotated.type.ModifiedAnnotatedType;
import com.liferay.bean.portlet.cdi.extension.internal.scope.JSR362BeanProducer;
import com.liferay.bean.portlet.cdi.extension.internal.scope.PortletRequestBeanContext;
import com.liferay.bean.portlet.cdi.extension.internal.scope.PortletSessionBeanContext;
import com.liferay.bean.portlet.cdi.extension.internal.scope.RenderStateBeanContext;
import com.liferay.bean.portlet.cdi.extension.internal.scope.ScopedBean;
import com.liferay.bean.portlet.cdi.extension.internal.scope.ServletContextProducer;
import com.liferay.bean.portlet.cdi.extension.internal.util.BeanMethodIndexUtil;
import com.liferay.bean.portlet.cdi.extension.internal.util.PortletScannerUtil;
import com.liferay.bean.portlet.cdi.extension.internal.xml.DisplayDescriptorParser;
import com.liferay.bean.portlet.cdi.extension.internal.xml.LiferayDescriptorParser;
import com.liferay.bean.portlet.cdi.extension.internal.xml.PortletDescriptorParser;
import com.liferay.bean.portlet.cdi.extension.internal.xml.PortletQNameUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.async.PortletAsyncListenerFactory;
import com.liferay.portal.kernel.portlet.async.PortletAsyncScopeManagerFactory;
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
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import javax.portlet.Portlet;
import javax.portlet.PortletAsyncListener;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.annotations.ContextPath;
import javax.portlet.annotations.CustomPortletMode;
import javax.portlet.annotations.CustomWindowState;
import javax.portlet.annotations.Dependency;
import javax.portlet.annotations.EventDefinition;
import javax.portlet.annotations.InitParameter;
import javax.portlet.annotations.LocaleString;
import javax.portlet.annotations.Multipart;
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
import javax.portlet.annotations.SecurityRoleRef;
import javax.portlet.annotations.Supports;
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

		beforeBeanDiscovery.addAnnotatedType(
			beanManager.createAnnotatedType(JSR362BeanProducer.class), null);

		beforeBeanDiscovery.addAnnotatedType(
			beanManager.createAnnotatedType(ServletContextProducer.class),
			null);
	}

	public <T> void step2ProcessAnnotatedType(
		@Observes ProcessAnnotatedType<T> processAnnotatedType) {

		if (_log.isDebugEnabled()) {
			_log.debug("processAnnotatedType=" + processAnnotatedType);
		}

		AnnotatedType<T> annotatedType =
			processAnnotatedType.getAnnotatedType();

		Class<T> discoveredClass = annotatedType.getJavaClass();

		if (annotatedType.isAnnotationPresent(RenderStateScoped.class) &&
			!PortletSerializable.class.isAssignableFrom(discoveredClass)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					discoveredClass.getName() + " does not implement " +
						PortletSerializable.class.getName());
			}
		}

		if (_log.isWarnEnabled()) {
			PortletSessionScoped portletSessionScoped =
				annotatedType.getAnnotation(PortletSessionScoped.class);

			if ((portletSessionScoped != null) &&
				(PortletSession.APPLICATION_SCOPE !=
					portletSessionScoped.value()) &&
				(PortletSession.PORTLET_SCOPE !=
					portletSessionScoped.value())) {

				_log.warn(
					"@PortletSessionScoped bean can only be " +
						"PortletSession.APPLICATION_SCOPE or " +
							"PortletSession.PORTLET_SCOPE");
			}
		}

		Set<Annotation> annotations = new HashSet<>(
			annotatedType.getAnnotations());

		if (annotations.remove(
				annotatedType.getAnnotation(RequestScoped.class)) &&
			!annotatedType.isAnnotationPresent(PortletRequestScoped.class)) {

			annotations.add(_portletRequestScoped);
		}

		if (annotations.remove(
				annotatedType.getAnnotation(SessionScoped.class)) &&
			!annotatedType.isAnnotationPresent(PortletSessionScoped.class)) {

			annotations.add(_portletSessionScoped);
		}

		if (Portlet.class.isAssignableFrom(discoveredClass) &&
			!annotatedType.isAnnotationPresent(ApplicationScoped.class)) {

			annotations.remove(
				annotatedType.getAnnotation(ConversationScoped.class));
			annotations.remove(
				annotatedType.getAnnotation(RequestScoped.class));
			annotations.remove(
				annotatedType.getAnnotation(PortletRequestScoped.class));
			annotations.remove(
				annotatedType.getAnnotation(PortletSessionScoped.class));
			annotations.remove(
				annotatedType.getAnnotation(SessionScoped.class));

			annotations.add(_applicationScoped);

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Automatically added @ApplicationScoped to " +
						discoveredClass);
			}
		}

		Set<Type> typeClosures = new HashSet<>(annotatedType.getTypeClosure());

		if (typeClosures.remove(PortletConfig.class) ||
			!annotations.equals(annotatedType.getAnnotations())) {

			processAnnotatedType.setAnnotatedType(
				new ModifiedAnnotatedType<>(
					annotatedType, annotations, typeClosures));
		}

		if (annotatedType.isAnnotationPresent(PortletApplication.class)) {
			if (_portletApplicationClass == null) {
				_portletApplicationClass = discoveredClass;
			}
			else {
				_log.error(
					"Found more than one @PortletApplication annotated class");
			}
		}

		_discoveredClasses.add(discoveredClass);

		for (Method method : discoveredClass.getMethods()) {
			for (MethodType methodType : MethodType.values()) {
				if (methodType.isMatch(method)) {
					_beanMethodFactories.add(
						new BeanMethodFactory(
							discoveredClass, method, methodType));
				}
			}
		}
	}

	public void step3AfterBeanDiscovery(
		@Observes AfterBeanDiscovery afterBeanDiscovery) {

		afterBeanDiscovery.addContext(new PortletRequestBeanContext());
		afterBeanDiscovery.addContext(new PortletSessionBeanContext());
		afterBeanDiscovery.addContext(new RenderStateBeanContext());
	}

	@SuppressWarnings({"serial", "unchecked"})
	public void step4ApplicationScopedInitializedAsync(
		@ObservesAsync ServletContext servletContext, BeanManager beanManager,
		BundleContext bundleContext) {

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

		Function<String, String> preferencesValidatorFunction =
			_collectPreferencesValidators();

		URL portletDescriptorURL = bundle.getEntry("/WEB-INF/portlet.xml");

		if (portletDescriptorURL != null) {
			try {
				_beanApp = PortletDescriptorParser.parse(
					_beanFilters, _beanPortlets, bundle, portletDescriptorURL,
					beanManager, portletBeanMethodsFunction,
					preferencesValidatorFunction, descriptorDisplayCategories,
					descriptorLiferayConfigurations);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		_addBeanFiltersFromDiscoveredClasses();

		_addBeanPortletsFromDiscoveredClasses(
			beanManager, portletBeanMethodsFunction,
			preferencesValidatorFunction, descriptorDisplayCategories,
			descriptorLiferayConfigurations);

		_addBeanPortletsFromScannedMethods(
			portletBeanMethodsFunction, descriptorDisplayCategories,
			descriptorLiferayConfigurations);

		_addBeanPortletsFromLiferayDescriptor(
			portletBeanMethodsFunction, descriptorDisplayCategories,
			descriptorLiferayConfigurations);

		List<String> beanPortletIds = (List<String>)servletContext.getAttribute(
			WebKeys.BEAN_PORTLET_IDS);

		if (beanPortletIds == null) {
			beanPortletIds = new ArrayList<>();

			servletContext.setAttribute(
				WebKeys.BEAN_PORTLET_IDS, beanPortletIds);
		}

		for (BeanPortlet beanPortlet : _beanPortlets.values()) {
			ServiceRegistration<Portlet> portletServiceRegistration =
				RegistrationUtil.registerBeanPortlet(
					bundleContext, _beanApp, beanPortlet, servletContext,
					beanPortletIds);

			if (portletServiceRegistration != null) {
				_serviceRegistrations.add(portletServiceRegistration);
			}

			ServiceRegistration<ResourceBundleLoader>
				resourceBundleLoaderserviceRegistration =
					RegistrationUtil.registerResourceBundleLoader(
						bundleContext, beanPortlet, servletContext);

			if (resourceBundleLoaderserviceRegistration != null) {
				_serviceRegistrations.add(
					resourceBundleLoaderserviceRegistration);
			}
		}

		for (BeanFilter beanFilter : _beanFilters.values()) {
			for (String portletName : beanFilter.getPortletNames()) {
				RegistrationUtil.registerBeanFilter(
					_serviceRegistrations, bundleContext, portletName,
					_beanPortlets.keySet(), beanFilter, beanManager,
					servletContext);
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

		_serviceRegistrations.add(
			bundleContext.registerService(
				Servlet.class,
				new PortletServlet() {
				},
				properties));

		Set<String> portletNames = descriptorDisplayCategories.keySet();

		portletNames.removeAll(_beanPortlets.keySet());

		if (!portletNames.isEmpty()) {
			_log.error(
				"Unknown portlet IDs " + portletNames +
					" found in liferay-display.xml");
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Registered ", _beanPortlets.size(), " bean portlets and ",
					_beanFilters.size(), " bean filters for ",
					servletContext.getServletContextName()));
		}

		properties = new HashMapDictionary<>();

		properties.put(
			"servlet.context.name", servletContext.getServletContextName());

		_serviceRegistrations.add(
			bundleContext.registerService(
				PortletAsyncScopeManagerFactory.class,
				PortletAsyncScopeManagerImpl::new, properties));

		_serviceRegistrations.add(
			bundleContext.registerService(
				PortletAsyncListenerFactory.class,
				new PortletAsyncListenerFactory() {

					@Override
					public <T extends PortletAsyncListener> T
							getPortletAsyncListener(Class<T> clazz)
						throws PortletException {

						Bean<?> bean = beanManager.resolve(
							beanManager.getBeans(clazz));

						if (bean == null) {
							throw new PortletException(
								"Unable to create an instance of " +
									clazz.getName());
						}

						try {
							return clazz.cast(
								beanManager.getReference(
									bean, bean.getBeanClass(),
									beanManager.createCreationalContext(bean)));
						}
						catch (Exception e) {
							throw new PortletException(
								"Unable to create an instance of " +
									clazz.getName(),
								e);
						}
					}

				},
				properties));
	}

	public void step4ApplicationScopedInitializedSync(
		@Initialized(ApplicationScoped.class) @Observes
			ServletContext servletContext,
		BeanManager beanManager,
		javax.enterprise.event.Event<ServletContext> servletContextEvent) {

		servletContextEvent.fireAsync(servletContext);
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
		@Destroyed(ApplicationScoped.class) @Observes Object contextObject) {

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (IllegalStateException ise) {

				// Ignore since the service has been unregistered

			}
		}

		_serviceRegistrations.clear();

		if (_log.isInfoEnabled()) {
			ServletContext servletContext = (ServletContext)contextObject;

			_log.info(
				StringBundler.concat(
					"Unregistered ", _beanPortlets.size(),
					" bean portlets and ", _beanFilters.size(),
					" bean filters for ",
					servletContext.getServletContextName()));
		}
	}

	private void _addBeanFiltersFromDiscoveredClasses() {
		for (Class<?> discoveredClass : _discoveredClasses) {
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

			BeanFilter beanFilter = _beanFilters.get(filterName);

			if (beanFilter == null) {
				beanFilter = new BeanFilterImpl(
					filterName, discoveredClass.asSubclass(PortletFilter.class),
					portletLifecycleFilter.ordinal(), portletNames, lifecycles,
					initParams);
			}
			else {
				portletNames.addAll(beanFilter.getPortletNames());

				lifecycles.addAll(beanFilter.getLifecycles());

				initParams.putAll(beanFilter.getInitParams());

				beanFilter = new BeanFilterImpl(
					beanFilter.getFilterName(), beanFilter.getFilterClass(),
					beanFilter.getOrdinal(), portletNames, lifecycles,
					initParams);
			}

			_beanFilters.put(filterName, beanFilter);
		}
	}

	private void _addBeanPortlet(
		Class<?> beanPortletClass,
		Map<MethodType, List<BeanMethod>> beanMethodMap,
		PortletConfiguration portletConfiguration,
		Function<String, String> preferencesValidatorFunction,
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

		for (Class<?> discoveredClass : _discoveredClasses) {
			PortletListener portletListener = discoveredClass.getAnnotation(
				PortletListener.class);

			if (portletListener == null) {
				continue;
			}

			portletListeners.add(
				new AbstractMap.SimpleImmutableEntry<>(
					portletListener.ordinal(), discoveredClass.getName()));
		}

		portletListeners.addAll(_beanApp.getPortletListeners());

		_beanApp = new BeanAppImpl(
			specVersion, defaultNamespace, events, publicRenderParameters,
			containerRuntimeOptions, customPortletModes, portletListeners);

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
					Arrays.asList(preference.values()),
					preference.isReadOnly()));
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
			_getAnnotatedLiferayConfiguration(configuredPortletName);

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
					propertyName = propertyString.substring(0, equalsPos);

					propertyValue = propertyString.substring(equalsPos + 1);

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

		BeanPortlet descriptorBeanPortlet = _beanPortlets.get(
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
				portletConfiguration.portletName(), beanMethodMap, displayNames,
				beanPortletClass.getName(), initParams,
				portletConfiguration.cacheExpirationTime(),
				supportedPortletModes, supportedWindowStates, supportedLocales,
				portletConfiguration.resourceBundle(), titles, shortTitles,
				keywords, descriptions, preferences, preferencesValidator,
				securityRoleRefs, supportedProcessingEvents,
				supportedPublishingEvents, supportedPublicRenderParameters,
				containerRuntimeOptions, portletDependencies,
				portletConfiguration.asyncSupported(), multipartConfig,
				displayCategory, liferayConfiguration);

			_beanPortlets.put(configuredPortletName, annotatedBeanPortlet);

			return;
		}

		String portletName = descriptorBeanPortlet.getPortletName();

		if (Validator.isNull(portletName)) {
			portletName = portletConfiguration.portletName();
		}

		Set<BeanMethod> beanMethods = new HashSet<>();

		for (Map.Entry<MethodType, List<BeanMethod>> entry :
				beanMethodMap.entrySet()) {

			beanMethods.addAll(entry.getValue());
		}

		Map<MethodType, List<BeanMethod>> descriptorBeanMethodMap =
			descriptorBeanPortlet.getBeanMethods();

		for (Map.Entry<MethodType, List<BeanMethod>> entry :
				descriptorBeanMethodMap.entrySet()) {

			beanMethods.addAll(entry.getValue());
		}

		beanMethodMap = BeanMethodIndexUtil.indexBeanMethods(beanMethods);

		BeanMethodIndexUtil.scanSupportedEvents(
			beanMethodMap, supportedProcessingEvents,
			supportedPublishingEvents);

		displayNames.putAll(descriptorBeanPortlet.getDisplayNames());

		String portletClassName = descriptorBeanPortlet.getPortletClassName();

		if (Validator.isNull(portletClassName)) {
			portletClassName = beanPortletClass.getName();
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
			portletConfiguration.portletName(), beanMethodMap, displayNames,
			beanPortletClass.getName(), initParams,
			portletConfiguration.cacheExpirationTime(), supportedPortletModes,
			supportedWindowStates, supportedLocales,
			portletConfiguration.resourceBundle(), titles, shortTitles,
			keywords, descriptions, preferences, preferencesValidator,
			securityRoleRefs, supportedProcessingEvents,
			supportedPublishingEvents, supportedPublicRenderParameters,
			containerRuntimeOptions, portletDependencies, asyncSupport,
			multipartConfig, displayCategory, liferayConfiguration);

		_beanPortlets.put(configuredPortletName, mergedBeanPortlet);
	}

	private void _addBeanPortletsFromDiscoveredClasses(
		BeanManager beanManager,
		Function<String, Set<BeanMethod>> portletBeanMethodsFunction,
		Function<String, String> preferencesValidatorFunction,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations) {

		for (Class<?> discoveredClass : _discoveredClasses) {
			PortletConfigurations portletConfigurations =
				discoveredClass.getAnnotation(PortletConfigurations.class);

			if (portletConfigurations == null) {
				continue;
			}

			for (PortletConfiguration portletConfiguration :
					portletConfigurations.value()) {

				Set<BeanMethod> beanMethods = new HashSet<>(
					portletBeanMethodsFunction.apply(
						portletConfiguration.portletName()));

				PortletScannerUtil.scanNonannotatedBeanMethods(
					beanManager, discoveredClass, beanMethods);

				Map<MethodType, List<BeanMethod>> beanMethodMap =
					BeanMethodIndexUtil.indexBeanMethods(beanMethods);

				_addBeanPortlet(
					discoveredClass, beanMethodMap, portletConfiguration,
					preferencesValidatorFunction, descriptorDisplayCategories,
					descriptorLiferayConfigurations);
			}
		}

		for (Class<?> discoveredClass : _discoveredClasses) {
			PortletConfiguration portletConfiguration =
				discoveredClass.getAnnotation(PortletConfiguration.class);

			if (portletConfiguration == null) {
				continue;
			}

			Set<BeanMethod> beanMethods = new HashSet<>(
				portletBeanMethodsFunction.apply(
					portletConfiguration.portletName()));

			PortletScannerUtil.scanNonannotatedBeanMethods(
				beanManager, discoveredClass, beanMethods);

			Map<MethodType, List<BeanMethod>> beanMethodMap =
				BeanMethodIndexUtil.indexBeanMethods(beanMethods);

			_addBeanPortlet(
				discoveredClass, beanMethodMap, portletConfiguration,
				preferencesValidatorFunction, descriptorDisplayCategories,
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
				Set<QName> supportedProcessingEvents = new HashSet<>();
				Set<QName> supportedPublishingEvents = new HashSet<>();

				Map<MethodType, List<BeanMethod>> beanMethodMap =
					BeanMethodIndexUtil.indexBeanMethods(
						portletBeanMethodsFunction.apply(portletName));

				BeanMethodIndexUtil.scanSupportedEvents(
					beanMethodMap, supportedProcessingEvents,
					supportedPublishingEvents);

				beanPortlet = new BeanPortletImpl(
					portletName, beanMethodMap, supportedProcessingEvents,
					supportedPublishingEvents,
					descriptorDisplayCategories.get(portletName),
					entry.getValue());

				_beanPortlets.put(portletName, beanPortlet);
			}
		}
	}

	private void _addBeanPortletsFromScannedMethods(
		Function<String, Set<BeanMethod>> portletBeanMethodsFunction,
		Map<String, String> descriptorDisplayCategories,
		Map<String, Map<String, Set<String>>> descriptorLiferayConfigurations) {

		Set<String> portletNames = new HashSet<>();

		for (BeanMethodFactory beanMethodFactory : _beanMethodFactories) {
			Collections.addAll(
				portletNames, beanMethodFactory.getPortletNames());
		}

		for (String portletName : portletNames) {
			if (Objects.equals("*", portletName)) {
				continue;
			}

			BeanPortlet beanPortlet = _beanPortlets.get(portletName);

			if (beanPortlet == null) {
				Map<MethodType, List<BeanMethod>> beanMethodMap =
					BeanMethodIndexUtil.indexBeanMethods(
						portletBeanMethodsFunction.apply(portletName));

				Set<QName> supportedProcessingEvents = new HashSet<>();
				Set<QName> supportedPublishingEvents = new HashSet<>();

				BeanMethodIndexUtil.scanSupportedEvents(
					beanMethodMap, supportedProcessingEvents,
					supportedPublishingEvents);

				beanPortlet = new BeanPortletImpl(
					portletName, beanMethodMap, supportedProcessingEvents,
					supportedPublishingEvents,
					descriptorDisplayCategories.get(portletName),
					descriptorLiferayConfigurations.get(portletName));

				_beanPortlets.put(portletName, beanPortlet);
			}
		}
	}

	private Function<String, Set<BeanMethod>> _collectPortletBeanMethods(
		BeanManager beanManager) {

		Set<BeanMethod> wildcardBeanMethods = new HashSet<>();

		Map<String, Set<BeanMethod>> portletBeanMethods = new HashMap<>();

		for (BeanMethodFactory beanMethodFactory : _beanMethodFactories) {
			String[] portletNames = beanMethodFactory.getPortletNames();

			if (portletNames == null) {
				_log.error(
					"Portlet names cannot be null for annotated method " +
						beanMethodFactory);

				continue;
			}

			BeanMethod beanMethod = beanMethodFactory.create(beanManager);

			if ((portletNames.length > 0) &&
				Objects.equals(portletNames[0], "*")) {

				wildcardBeanMethods.add(beanMethod);
			}
			else {
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
		}

		return portletName -> {
			Set<BeanMethod> beanMethods = portletBeanMethods.get(portletName);

			if (beanMethods == null) {
				return wildcardBeanMethods;
			}

			return beanMethods;
		};
	}

	private Function<String, String> _collectPreferencesValidators() {
		String wildcardPreferencesValidator = null;

		Map<String, String> preferencesValidators = new HashMap<>();

		for (Class<?> discoveredClass : _discoveredClasses) {
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
		String portletName) {

		for (Class<?> discoveredClass : _discoveredClasses) {
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

		for (Class<?> discoveredClass : _discoveredClasses) {
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
		BeanPortletExtension.class);

	private static final Annotation _applicationScoped =
		new ApplicationScoped() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return ApplicationScoped.class;
			}

		};

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

	private static final Annotation _portletRequestScoped =
		new PortletRequestScoped() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return PortletRequestScoped.class;
			}

		};

	private static final Annotation _portletSessionScoped =
		new PortletSessionScoped() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return PortletSessionScoped.class;
			}

			@Override
			public int value() {
				return PortletSession.PORTLET_SCOPE;
			}

		};

	private BeanApp _beanApp = new BeanAppImpl(
		"3.0", null, Collections.emptyList(), Collections.emptyMap(),
		Collections.emptyMap(), Collections.emptySet(),
		Collections.emptyList());
	private final Map<String, BeanFilter> _beanFilters = new HashMap<>();
	private final List<BeanMethodFactory> _beanMethodFactories =
		new ArrayList<>();
	private final Map<String, BeanPortlet> _beanPortlets = new HashMap<>();
	private final List<Class<?>> _discoveredClasses = new ArrayList<>();
	private Class<?> _portletApplicationClass;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}