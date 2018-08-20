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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventPortlet;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderPortlet;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;
import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.ContextPath;
import javax.portlet.annotations.DestroyMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.HeaderMethod;
import javax.portlet.annotations.InitMethod;
import javax.portlet.annotations.Namespace;
import javax.portlet.annotations.PortletApplication;
import javax.portlet.annotations.PortletConfiguration;
import javax.portlet.annotations.PortletConfigurations;
import javax.portlet.annotations.PortletLifecycleFilter;
import javax.portlet.annotations.PortletListener;
import javax.portlet.annotations.PortletName;
import javax.portlet.annotations.PortletRequestScoped;
import javax.portlet.annotations.PortletSerializable;
import javax.portlet.annotations.PortletSessionScoped;
import javax.portlet.annotations.RenderMethod;
import javax.portlet.annotations.RenderStateScoped;
import javax.portlet.annotations.ServeResourceMethod;
import javax.portlet.annotations.WindowId;
import javax.portlet.filter.PortletFilter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Neil Griffin
 * @author Raymond Augé
 */
public class BeanPortletExtension implements Extension {

	public void afterBeanDiscovery(
		@Observes AfterBeanDiscovery afterBeanDiscovery) {

		try {
			Bundle bundle = FrameworkUtil.getBundle(BeanPortletExtension.class);

			URL portletDescriptorURL = bundle.getEntry("/WEB-INF/portlet.xml");

			if (portletDescriptorURL != null) {
				try {
					PortletDescriptor portletDescriptor =
						PortletDescriptorParser.parse(portletDescriptorURL);

					_beanFilters.addAll(portletDescriptor.getBeanFilters());

					List<BeanPortlet> portletDescriptorBeanPortlets =
						portletDescriptor.getBeanPortlets();

					portletDescriptorBeanPortlets.forEach(
						beanPortlet -> {
							_beanPortlets.put(
								beanPortlet.getPortletName(), beanPortlet);
							scanBeanPortletClass(
								loadBeanPortletClass(
									beanPortlet.getPortletClass()),
								beanPortlet.getPortletName());
						});
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			_portletConfigurationsClasses.forEach(
				clazz -> {
					PortletConfigurations portletConfigurations =
						clazz.getAnnotation(PortletConfigurations.class);

					Stream<PortletConfiguration> portletConfigurationStream =
						Arrays.stream(portletConfigurations.value());

					portletConfigurationStream.forEach(
						portletConfiguration -> {
							addBeanPortlet(clazz, portletConfiguration);
							scanBeanPortletClass(
								clazz, portletConfiguration.portletName());
						});
				});

			_portletConfigurationClasses.forEach(
				clazz -> {
					PortletConfiguration portletConfiguration =
						clazz.getAnnotation(PortletConfiguration.class);

					addBeanPortlet(clazz, portletConfiguration);
					scanBeanPortletClass(
						clazz, portletConfiguration.portletName());
				});

			Stream<Class<?>> portletLifecycleFilterClassesStream =
				_portletLifecycleFilterClasses.stream();

			_beanFilters.addAll(
				portletLifecycleFilterClassesStream.map(
					annotatedClass -> BeanFilterFactory.create(
						annotatedClass,
						annotatedClass.getAnnotation(
							PortletLifecycleFilter.class))
				).collect(
					Collectors.toList()
				)
			);

			Stream<Class<?>> beanPortletListenerClassesStream =
				_portletListenerClasses.stream();

			beanPortletListenerClassesStream.map(
				portletListenerClass -> {
					PortletListener portletListenerAnnotation =
						portletListenerClass.getAnnotation(
							PortletListener.class);

					return new URLGenerationListener(
						portletListenerAnnotation.ordinal(),
						portletListenerClass.getName());
				}
			).forEach(
				urlGenerationListener -> {
					Set<Map.Entry<String, BeanPortlet>> beanPortletEntrySet =
						_beanPortlets.entrySet();

					Stream<Map.Entry<String, BeanPortlet>>
						beanPortletEntrySetStream =
							beanPortletEntrySet.stream();

					beanPortletEntrySetStream.forEach(
						entry -> {
							BeanPortlet beanPortlet = entry.getValue();

							BeanApp beanApp = beanPortlet.getBeanApp();

							List<URLGenerationListener> urlGenerationListeners =
								beanApp.getURLGenerationListeners();

							urlGenerationListeners.add(urlGenerationListener);
						});
				}
			);

			URL liferayDescriptorURL = bundle.getEntry(
				"WEB-INF/liferay-portlet.xml");

			if (liferayDescriptorURL != null) {
				try {
					LiferayDescriptor liferayDescriptor =
						LiferayDescriptorParser.parse(liferayDescriptorURL);

					Set<String> portletNames =
						liferayDescriptor.getPortletNames();

					Stream<String> portletNamesStream = portletNames.stream();

					portletNamesStream.forEach(
						portletName -> {
							BeanPortlet beanPortlet = _beanPortlets.get(
								portletName);

							if (beanPortlet == null) {
								beanPortlet = new BeanPortletDefaultImpl(
									portletName);

								_beanPortlets.put(portletName, beanPortlet);
							}

							beanPortlet.addLiferayConfiguration(
								liferayDescriptor.getPortletConfiguration(
									portletName));
						});
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			URL displayDescriptorURL = bundle.getEntry(
				"WEB-INF/liferay-display.xml");

			if (displayDescriptorURL != null) {
				try {
					Map<String, String> categoryMap =
						DisplayDescriptorParser.parse(displayDescriptorURL);

					Set<Map.Entry<String, String>> categoryEntrySet =
						categoryMap.entrySet();

					Stream<Map.Entry<String, String>> categoryEntryStream =
						categoryEntrySet.stream();

					categoryEntryStream.filter(
						entry -> _beanPortlets.containsKey(entry.getKey())
					).forEach(
						entry -> {
							BeanPortlet beanPortlet = _beanPortlets.get(
								entry.getKey());

							beanPortlet.addLiferayConfiguration(
								"com.liferay.portlet.display-category",
								entry.getValue());
						}
					);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			afterBeanDiscovery.addContext(new PortletRequestBeanContext());
			afterBeanDiscovery.addContext(new PortletSessionBeanContext());
			afterBeanDiscovery.addContext(new RenderStateBeanContext());
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}
	}

	public void applicationScopedInitialized(
		@Initialized(ApplicationScoped.class)
			@Observes ServletContext servletContext,
		BeanManager beanManager) {

		associateMethods(beanManager, MethodType.ACTION, _actionMethods);
		associateMethods(beanManager, MethodType.DESTROY, _destroyMethods);
		associateMethods(beanManager, MethodType.EVENT, _eventMethods);
		associateMethods(beanManager, MethodType.HEADER, _headerMethods);
		associateMethods(beanManager, MethodType.INIT, _initMethods);
		associateMethods(beanManager, MethodType.RENDER, _renderMethods);
		associateMethods(
			beanManager, MethodType.SERVE_RESOURCE, _serveResourceMethods);

		Bundle bundle = FrameworkUtil.getBundle(BeanPortletExtension.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Set<Map.Entry<String, BeanPortlet>> beanPortletEntrySet =
			_beanPortlets.entrySet();

		Stream<Map.Entry<String, BeanPortlet>> beanPortletEntrySetStream =
			beanPortletEntrySet.stream();

		_portletRegistrations = beanPortletEntrySetStream.map(
			entry -> RegistrationUtil.registerBeanPortlet(
				bundleContext, entry.getValue(), servletContext)
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);

		beanPortletEntrySetStream = beanPortletEntrySet.stream();

		_resourceBundleLoaderRegistrations = beanPortletEntrySetStream.map(
			entry -> RegistrationUtil.registerResourceBundleLoader(
				bundleContext, entry.getValue(), servletContext)
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);

		_beanFilters.forEach(
			beanFilter -> beanFilter.getPortletNames().forEach(
				portletName -> _filterRegistrations.addAll(
					RegistrationUtil.registerBeanFilter(
						bundleContext, portletName, _beanPortlets.keySet(),
						beanFilter, beanManager, servletContext))));

		URL liferayDescriptorURL = bundle.getEntry(
			"WEB-INF/liferay-portlet.xml");

		if (liferayDescriptorURL != null) {
			try {
				LiferayDescriptor liferayDescriptor =
					LiferayDescriptorParser.parse(liferayDescriptorURL);

				Set<String> portletNames = liferayDescriptor.getPortletNames();

				Stream<String> portletNamesStream = portletNames.stream();

				portletNamesStream.filter(
					portletName -> !_beanPortlets.containsKey(portletName)
				).forEach(
					portletName -> _log.warn(
						StringBundler.concat(
							"Portlet with the name ", portletName,
							" is described in liferay-portlet.xml but does ",
							"not have a matching entry in portlet.xml or ",
							"@PortletConfiguration annotation"))
				);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		URL displayDescriptorURL = bundle.getEntry(
			"WEB-INF/liferay-display.xml");

		if (displayDescriptorURL != null) {
			try {
				Map<String, String> categoryMap = DisplayDescriptorParser.parse(
					displayDescriptorURL);

				Set<Map.Entry<String, String>> categoryEntrySet =
					categoryMap.entrySet();

				Stream<Map.Entry<String, String>> categoryEntrySetStream =
					categoryEntrySet.stream();

				categoryEntrySetStream.filter(
					entry -> !_beanPortlets.containsKey(entry.getKey())
				).forEach(
					entry -> _log.error(
						"Unknown portletId " + entry.getKey() +
							" found in liferay-display.xml")
				);
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

	public void beforeBeanDiscovery(
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

	public <T> void processAnnotatedType(
		@Observes ProcessAnnotatedType<T> processAnnotatedType) {

		if (_log.isDebugEnabled()) {
			_log.debug("processAnnotatedType=" + processAnnotatedType);
		}

		AnnotatedType<T> annotatedType =
			processAnnotatedType.getAnnotatedType();

		Class<T> annotatedClass = annotatedType.getJavaClass();

		Set<Type> typeClosure = annotatedType.getTypeClosure();

		if (typeClosure.contains(PortletConfig.class)) {
			annotatedType = new AnnotatedTypePortletConfigImpl(annotatedType);

			processAnnotatedType.setAnnotatedType(annotatedType);
		}

		Set<Annotation> annotations = annotatedType.getAnnotations();

		Stream<Annotation> annotationsStream = annotations .stream();

		Set<Class<? extends Annotation>> annotationClasses =
			annotationsStream.map(
				Annotation::annotationType
			).collect(
				Collectors.toSet()
			);

		if (annotationClasses.contains(RequestScoped.class)) {
			annotatedType = new AnnotatedTypeRequestScopedImpl(
				annotatedType, annotationClasses);

			processAnnotatedType.setAnnotatedType(annotatedType);
		}

		if (annotationClasses.contains(SessionScoped.class)) {
			annotatedType = new AnnotatedTypeSessionScopedImpl<>(
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

			annotatedType = new AnnotatedTypeApplicationScopedImpl(
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

		if (annotationClasses.contains(LiferayPortletConfigurations.class)) {
			_liferayPortletConfigurationsClasses.add(annotatedClass);
		}

		if (annotationClasses.contains(LiferayPortletConfiguration.class)) {
			_liferayPortletConfigurationClasses.add(annotatedClass);
		}

		_actionMethods.addAll(
			scanMethods(
				annotatedClass, MethodType.ACTION, ActionMethod.class,
				MethodSignature.ACTION));

		_destroyMethods.addAll(
			scanMethods(
				annotatedClass, MethodType.DESTROY, DestroyMethod.class,
				MethodSignature.DESTROY));

		_eventMethods.addAll(
			scanMethods(
				annotatedClass, MethodType.EVENT, EventMethod.class,
				MethodSignature.EVENT));

		_headerMethods.addAll(
			scanMethods(
				annotatedClass, MethodType.HEADER, HeaderMethod.class,
				MethodSignature.HEADER));

		_initMethods.addAll(
			scanMethods(
				annotatedClass, MethodType.INIT, InitMethod.class,
				MethodSignature.INIT));

		_renderMethods.addAll(
			scanMethods(
				annotatedClass, MethodType.RENDER, RenderMethod.class,
				MethodSignature.RENDER));

		_serveResourceMethods.addAll(
			scanMethods(
				annotatedClass, MethodType.SERVE_RESOURCE,
				ServeResourceMethod.class, MethodSignature.SERVE_RESOURCE));
	}

	protected void addBeanPortlet(
		Class<?> beanPortletClass, PortletConfiguration portletConfiguration) {

		String configuredPortletName = portletConfiguration.portletName();

		if (Validator.isNotNull(configuredPortletName)) {
			if (_portletApplicationClass == null) {
				_beanPortlets.putIfAbsent(
					configuredPortletName,
					BeanPortletFactory.create(
						portletConfiguration,
						getLiferayPortletConfiguration(configuredPortletName),
						beanPortletClass.getName()));
			}
			else {
				_beanPortlets.putIfAbsent(
					configuredPortletName,
					BeanPortletFactory.create(
						_portletApplicationClass.getAnnotation(
							PortletApplication.class),
						portletConfiguration,
						getLiferayPortletConfiguration(configuredPortletName),
						beanPortletClass.getName()));
			}
		}
		else {
			_log.error(
				"Invalid portletName attribute for " +
					beanPortletClass.getName());
		}
	}

	protected void applicationScopedBeforeDestroyed(
		@Destroyed(ApplicationScoped.class) @Observes Object ignore) {

		_filterRegistrations.removeIf(
			serviceRegistration -> {
				serviceRegistration.unregister();

				return true;
			});

		_portletRegistrations.removeIf(
			serviceRegistration -> {
				serviceRegistration.unregister();

				return true;
			});

		_resourceBundleLoaderRegistrations.removeIf(
			serviceRegistration -> {
				serviceRegistration.unregister();

				return true;
			});
	}

	protected void associateMethods(
		BeanManager beanManager, MethodType beanMethodType,
		List<ScannedMethod> scannedMethods) {

		for (ScannedMethod scannedMethod : scannedMethods) {
			Class<?> clazz = scannedMethod.getClazz();
			Method method = scannedMethod.getMethod();
			String[] portletNames = scannedMethod.getPortletNames();
			int ordinal = scannedMethod.getOrdinal();

			BeanMethod beanMethod = new BeanMethod(
				beanManager, beanMethodType, clazz, method, ordinal);

			Class<?> beanClass = beanMethod.getBeanClass();

			if (portletNames == null) {
				String beanClassName = beanClass.getName();

				Set<Map.Entry<String, BeanPortlet>> beanPortletEntries =
					_beanPortlets.entrySet();

				Stream<Map.Entry<String, BeanPortlet>>
					beanPortletEntriesStream = beanPortletEntries.stream();

				beanPortletEntriesStream.map(
					Map.Entry::getValue
				).filter(
					beanPortlet -> beanClassName.equals(
						beanPortlet.getPortletClass())
				).forEach(
					beanPortlet -> beanPortlet.addBeanMethod(beanMethod)
				);
			}
			else if ((portletNames.length > 0) && "*".equals(portletNames[0])) {
				Set<String> beanPortletNames = _beanPortlets.keySet();

				beanPortletNames.forEach(
					portletName -> {
						BeanPortlet beanPortlet = _beanPortlets.get(
							portletName);

						beanPortlet.addBeanMethod(beanMethod);
					});
			}
			else {
				Stream<String> portletNamesStream = Arrays.stream(portletNames);

				portletNamesStream.forEach(
					portletName -> {
						BeanPortlet beanPortlet = _beanPortlets.get(
							portletName);

						if (beanPortlet == null) {
							beanPortlet = new BeanPortletDefaultImpl(
								portletName);

							_beanPortlets.put(portletName, beanPortlet);
						}

						beanPortlet.addBeanMethod(beanMethod);
					});
			}
		}
	}

	protected LiferayPortletConfiguration getLiferayPortletConfiguration(
		String portletName) {

		for (Class<?> annotatedClass : _liferayPortletConfigurationClasses) {
			LiferayPortletConfiguration liferayPortletConfiguration =
				annotatedClass.getAnnotation(LiferayPortletConfiguration.class);

			if (portletName.equals(liferayPortletConfiguration.portletName())) {
				return liferayPortletConfiguration;
			}
		}

		for (Class<?> annotatedClass : _liferayPortletConfigurationsClasses) {
			LiferayPortletConfigurations liferayPortletConfigurations =
				annotatedClass.getAnnotation(
					LiferayPortletConfigurations.class);

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

	protected Class<?> loadBeanPortletClass(String portletClass) {
		try {
			return Class.forName(portletClass);
		}
		catch (ClassNotFoundException cnfe) {
			_log.error(cnfe, cnfe);

			return null;
		}
	}

	protected void scanBeanPortletClass(
		Class<?> beanPortletClass, String portletName) {

		if (Portlet.class.isAssignableFrom(beanPortletClass)) {
			try {
				Method processActionMethod = beanPortletClass.getMethod(
					"processAction", ActionRequest.class, ActionResponse.class);

				if (!processActionMethod.isAnnotationPresent(
						ActionMethod.class)) {

					_actionMethods.add(
						new ScannedMethod(
							beanPortletClass, MethodType.ACTION,
							processActionMethod, portletName));
				}

				Method destroyMethod = beanPortletClass.getMethod("destroy");

				if (!destroyMethod.isAnnotationPresent(DestroyMethod.class)) {
					_destroyMethods.add(
						new ScannedMethod(
							beanPortletClass, MethodType.DESTROY, destroyMethod,
							portletName));
				}

				Method initMethod = beanPortletClass.getMethod(
					"init", PortletConfig.class);

				if (!initMethod.isAnnotationPresent(InitMethod.class)) {
					_initMethods.add(
						new ScannedMethod(
							beanPortletClass, MethodType.INIT, initMethod,
							portletName));
				}

				Method renderMethod = beanPortletClass.getMethod(
					"render", RenderRequest.class, RenderResponse.class);

				if (!renderMethod.isAnnotationPresent(RenderMethod.class)) {
					_renderMethods.add(
						new ScannedMethod(
							beanPortletClass, MethodType.RENDER, renderMethod,
							portletName));
				}
			}
			catch (NoSuchMethodException nsme) {
				_log.error(nsme, nsme);
			}
		}

		if (EventPortlet.class.isAssignableFrom(beanPortletClass)) {
			try {
				Method eventMethod = beanPortletClass.getMethod(
					"processEvent", EventRequest.class, EventResponse.class);

				if (!eventMethod.isAnnotationPresent(EventMethod.class)) {
					_eventMethods.add(
						new ScannedMethod(
							beanPortletClass, MethodType.EVENT, eventMethod,
							portletName));
				}
			}
			catch (NoSuchMethodException nsme) {
				_log.error(nsme, nsme);
			}
		}

		if (HeaderPortlet.class.isAssignableFrom(beanPortletClass)) {
			try {
				Method renderHeadersMethod = beanPortletClass.getMethod(
					"renderHeaders", HeaderRequest.class, HeaderResponse.class);

				if (!renderHeadersMethod.isAnnotationPresent(
						HeaderMethod.class)) {

					_headerMethods.add(
						new ScannedMethod(
							beanPortletClass, MethodType.HEADER,
							renderHeadersMethod, portletName));
				}
			}
			catch (NoSuchMethodException nsme) {
				_log.error(nsme, nsme);
			}
		}

		if (ResourceServingPortlet.class.isAssignableFrom(beanPortletClass)) {
			try {
				Method serveResourceMethod = beanPortletClass.getMethod(
					"serveResource", ResourceRequest.class,
					ResourceResponse.class);

				if (!serveResourceMethod.isAnnotationPresent(
						ServeResourceMethod.class)) {

					_serveResourceMethods.add(
						new ScannedMethod(
							beanPortletClass, MethodType.SERVE_RESOURCE,
							serveResourceMethod, portletName));
				}
			}
			catch (NoSuchMethodException nsme) {
				_log.error(nsme, nsme);
			}
		}
	}

	protected List<ScannedMethod> scanMethods(
		Class<?> javaClass, MethodType methodType,
		Class<? extends Annotation> annotationClass,
		MethodSignature methodSignature) {

		Stream<Method> methodsStream = Arrays.stream(javaClass.getMethods());

		return methodsStream.filter(
			method -> (method.getAnnotation(annotationClass) != null) &&
			methodSignature.isMatch(method)
		).map(
			method -> ScannedMethod.create(javaClass, methodType, method)
		).collect(
			Collectors.toList()
		);
	}

	protected void sessionScopeBeforeDestroyed(
		@Destroyed(SessionScoped.class) @Observes Object httpSessionObject) {

		HttpSession httpSession = (HttpSession)httpSessionObject;

		ArrayList<String> sessionAttributeNames = Collections.list(
			httpSession.getAttributeNames());

		Stream<String> sessionAttributeNamesStream =
			sessionAttributeNames.stream();

		sessionAttributeNamesStream.map(
			httpSession::getAttribute
		).filter(
			Objects::nonNull
		).filter(
			attributeValue -> attributeValue instanceof ScopedBean
		).forEach(
			scopedBean -> ((ScopedBean)scopedBean).destroy()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanPortletExtension.class);

	private final List<ScannedMethod> _actionMethods = new ArrayList<>();
	private final List<BeanFilter> _beanFilters = new ArrayList<>();
	private final Map<String, BeanPortlet> _beanPortlets = new HashMap<>();
	private final List<ScannedMethod> _destroyMethods = new ArrayList<>();
	private final List<ScannedMethod> _eventMethods = new ArrayList<>();
	private final List<ServiceRegistration<PortletFilter>>
		_filterRegistrations = new ArrayList<>();
	private final List<ScannedMethod> _headerMethods = new ArrayList<>();
	private final List<ScannedMethod> _initMethods = new ArrayList<>();
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
	private List<ServiceRegistration<Portlet>> _portletRegistrations;
	private final List<ScannedMethod> _renderMethods = new ArrayList<>();
	private List<ServiceRegistration<ResourceBundleLoader>>
		_resourceBundleLoaderRegistrations = new ArrayList<>();
	private final List<ScannedMethod> _serveResourceMethods = new ArrayList<>();

}