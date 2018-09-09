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
import com.liferay.bean.portlet.cdi.extension.internal.annotated.BeanFilterAnnotationImpl;
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
import com.liferay.bean.portlet.cdi.extension.internal.xml.LiferayDescriptor;
import com.liferay.bean.portlet.cdi.extension.internal.xml.LiferayDescriptorParser;
import com.liferay.bean.portlet.cdi.extension.internal.xml.PortletDescriptor;
import com.liferay.bean.portlet.cdi.extension.internal.xml.PortletDescriptorParser;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import javax.portlet.annotations.CustomPortletMode;
import javax.portlet.annotations.CustomWindowState;
import javax.portlet.annotations.DestroyMethod;
import javax.portlet.annotations.EventDefinition;
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
import javax.portlet.annotations.PublicRenderParameterDefinition;
import javax.portlet.annotations.RenderMethod;
import javax.portlet.annotations.RenderStateScoped;
import javax.portlet.annotations.RuntimeOption;
import javax.portlet.annotations.ServeResourceMethod;
import javax.portlet.annotations.UserAttribute;
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

					for (BeanPortlet beanPortlet :
							portletDescriptor.getBeanPortlets()) {

						_beanPortlets.put(
							beanPortlet.getPortletName(), beanPortlet);

						scanBeanPortletClass(
							loadBeanPortletClass(
								beanPortlet.getPortletClassName()),
							beanPortlet.getPortletName());
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			for (Class<?> clazz : _portletConfigurationsClasses) {
				PortletConfigurations portletConfigurations =
					clazz.getAnnotation(PortletConfigurations.class);

				for (PortletConfiguration portletConfiguration :
						portletConfigurations.value()) {

					addBeanPortlet(clazz, portletConfiguration);
					scanBeanPortletClass(
						clazz, portletConfiguration.portletName());
				}
			}

			for (Class<?> clazz : _portletConfigurationClasses) {
				PortletConfiguration portletConfiguration = clazz.getAnnotation(
					PortletConfiguration.class);

				addBeanPortlet(clazz, portletConfiguration);
				scanBeanPortletClass(clazz, portletConfiguration.portletName());
			}

			for (Class<?> annotatedClass : _portletLifecycleFilterClasses) {
				_beanFilters.add(new BeanFilterAnnotationImpl(annotatedClass));
			}

			for (Class<?> portletListenerClass : _portletListenerClasses) {
				PortletListener portletListener =
					portletListenerClass.getAnnotation(PortletListener.class);

				URLGenerationListener urlGenerationListener =
					new URLGenerationListener(
						portletListener.ordinal(),
						portletListenerClass.getName());

				for (BeanPortlet beanPortlet : _beanPortlets.values()) {
					BeanApp beanApp = beanPortlet.getBeanApp();

					List<URLGenerationListener> urlGenerationListeners =
						beanApp.getURLGenerationListeners();

					urlGenerationListeners.add(urlGenerationListener);
				}
			}

			URL liferayDescriptorURL = bundle.getEntry(
				"WEB-INF/liferay-portlet.xml");

			if (liferayDescriptorURL != null) {
				try {
					LiferayDescriptor liferayDescriptor =
						LiferayDescriptorParser.parse(liferayDescriptorURL);

					for (String portletName :
							liferayDescriptor.getPortletNames()) {

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
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			URL displayDescriptorURL = bundle.getEntry(
				"WEB-INF/liferay-display.xml");

			if (displayDescriptorURL != null) {
				try {
					Map<String, String> map = DisplayDescriptorParser.parse(
						displayDescriptorURL);

					for (Map.Entry<String, String> entry : map.entrySet()) {
						BeanPortlet beanPortlet = _beanPortlets.get(
							entry.getKey());

						if (beanPortlet != null) {
							beanPortlet.addLiferayConfiguration(
								"com.liferay.portlet.display-category",
								entry.getValue());
						}
					}
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

		_portletRegistrations = new ArrayList<>();
		_resourceBundleLoaderRegistrations = new ArrayList<>();

		for (BeanPortlet beanPortlet : _beanPortlets.values()) {
			ServiceRegistration<Portlet> portletServiceRegistration =
				RegistrationUtil.registerBeanPortlet(
					bundleContext, beanPortlet, servletContext);

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

		for (BeanFilter beanFilter : _beanFilters) {
			for (String portletName : beanFilter.getPortletNames()) {
				_filterRegistrations.addAll(
					RegistrationUtil.registerBeanFilter(
						bundleContext, portletName, _beanPortlets.keySet(),
						beanFilter, beanManager, servletContext));
			}
		}

		URL liferayDescriptorURL = bundle.getEntry(
			"WEB-INF/liferay-portlet.xml");

		if ((liferayDescriptorURL != null) && _log.isWarnEnabled()) {
			try {
				LiferayDescriptor liferayDescriptor =
					LiferayDescriptorParser.parse(liferayDescriptorURL);

				for (String portletName : liferayDescriptor.getPortletNames()) {
					if (_beanPortlets.containsKey(portletName)) {
						continue;
					}

					_log.warn(
						StringBundler.concat(
							"Portlet with the name ", portletName,
							" is described in liferay-portlet.xml but does ",
							"not have a matching entry in portlet.xml or ",
							"@PortletConfiguration annotation"));
				}
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

		_beanPortlets.putIfAbsent(
			configuredPortletName,
			new BeanPortletAnnotationImpl(
				portletApplication, portletConfiguration,
				getLiferayPortletConfiguration(configuredPortletName),
				beanPortletClass.getName()));
	}

	protected void applicationScopedBeforeDestroyed(
		@Destroyed(ApplicationScoped.class) @Observes Object ignore) {

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

				for (BeanPortlet beanPortlet : _beanPortlets.values()) {
					if (beanClassName.equals(
							beanPortlet.getPortletClassName())) {

						beanPortlet.addBeanMethod(beanMethod);
					}
				}
			}
			else if ((portletNames.length > 0) && "*".equals(portletNames[0])) {
				for (BeanPortlet beanPortlet : _beanPortlets.values()) {
					beanPortlet.addBeanMethod(beanMethod);
				}
			}
			else {
				for (String portletName : portletNames) {
					BeanPortlet beanPortlet = _beanPortlets.get(portletName);

					if (beanPortlet == null) {
						beanPortlet = new BeanPortletDefaultImpl(portletName);

						_beanPortlets.put(portletName, beanPortlet);
					}

					beanPortlet.addBeanMethod(beanMethod);
				}
			}
		}
	}

	protected LiferayPortletConfiguration getLiferayPortletConfiguration(
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

	protected Class<?> loadBeanPortletClass(String className) {
		try {
			return Class.forName(className);
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

		List<ScannedMethod> scannedMethods = new ArrayList<>();

		for (Method method : javaClass.getMethods()) {
			if ((method.getAnnotation(annotationClass) != null) &&
				methodSignature.isMatch(method)) {

				scannedMethods.add(
					ScannedMethod.create(javaClass, methodType, method));
			}
		}

		return scannedMethods;
	}

	protected void sessionScopeBeforeDestroyed(
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