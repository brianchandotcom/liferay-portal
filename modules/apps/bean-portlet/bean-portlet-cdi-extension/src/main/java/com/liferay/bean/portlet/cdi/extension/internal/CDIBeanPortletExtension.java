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

import com.liferay.bean.portlet.cdi.extension.internal.annotated.type.ModifiedAnnotatedType;
import com.liferay.bean.portlet.cdi.extension.internal.scope.JSR362CDIBeanProducer;
import com.liferay.bean.portlet.cdi.extension.internal.scope.PortletRequestBeanContext;
import com.liferay.bean.portlet.cdi.extension.internal.scope.PortletSessionBeanContext;
import com.liferay.bean.portlet.cdi.extension.internal.scope.RenderStateBeanContext;
import com.liferay.bean.portlet.cdi.extension.internal.scope.ScopedBeanManager;
import com.liferay.bean.portlet.cdi.extension.internal.scope.ScopedBeanManagerThreadLocal;
import com.liferay.bean.portlet.cdi.extension.internal.scope.ServletContextProducer;
import com.liferay.bean.portlet.extension.BeanFilterMethod;
import com.liferay.bean.portlet.extension.BeanFilterMethodInvoker;
import com.liferay.bean.portlet.extension.BeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodInvoker;
import com.liferay.bean.portlet.extension.BeanPortletMethodType;
import com.liferay.bean.portlet.extension.ScopedBean;
import com.liferay.bean.portlet.registration.BeanPortletRegistrar;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.async.PortletAsyncListenerFactory;
import com.liferay.portal.kernel.portlet.async.PortletAsyncScopeManagerFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.Validator;

import java.io.PrintWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import javax.portlet.ActionParameters;
import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletAsyncListener;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.annotations.ContextPath;
import javax.portlet.annotations.Namespace;
import javax.portlet.annotations.PortletName;
import javax.portlet.annotations.PortletRequestScoped;
import javax.portlet.annotations.PortletSerializable;
import javax.portlet.annotations.PortletSessionScoped;
import javax.portlet.annotations.RenderStateScoped;
import javax.portlet.annotations.WindowId;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Neil Griffin
 * @author Raymond Augé
 */
public class CDIBeanPortletExtension implements Extension {

	public void step1BeforeBeanDiscovery(
		@Observes BeforeBeanDiscovery beforeBeanDiscovery,
		BeanManager beanManager) {

		if (_log.isDebugEnabled()) {
			_log.debug("Scanning for bean portlets and bean filters");
		}

		beforeBeanDiscovery.addAnnotatedType(
			beanManager.createAnnotatedType(JSR362CDIBeanProducer.class), null);
		beforeBeanDiscovery.addAnnotatedType(
			beanManager.createAnnotatedType(ServletContextProducer.class),
			null);
		beforeBeanDiscovery.addQualifier(ContextPath.class);
		beforeBeanDiscovery.addQualifier(Namespace.class);
		beforeBeanDiscovery.addQualifier(PortletName.class);
		beforeBeanDiscovery.addQualifier(WindowId.class);
		beforeBeanDiscovery.addScope(PortletRequestScoped.class, true, false);
		beforeBeanDiscovery.addScope(PortletSessionScoped.class, true, true);
		beforeBeanDiscovery.addScope(RenderStateScoped.class, true, false);
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
				annotatedType.getAnnotation(PortletRequestScoped.class));
			annotations.remove(
				annotatedType.getAnnotation(PortletSessionScoped.class));
			annotations.remove(
				annotatedType.getAnnotation(RequestScoped.class));
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

		_discoveredClasses.add(discoveredClass);
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

		Dictionary<String, Object> properties = new HashMapDictionary<>();

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
						catch (Exception exception) {
							throw new PortletException(
								"Unable to create an instance of " +
									clazz.getName(),
								exception);
						}
					}

				},
				properties));

		BeanFilterMethodInvoker beanFilterMethodInvoker =
			new BeanFilterMethodInvoker() {

				@Override
				public void invokeWithActiveScopes(
						BeanFilterMethod beanFilterMethod, Object filterChain,
						PortletRequest portletRequest,
						PortletResponse portletResponse)
					throws PortletException {

					ScopedBeanManagerThreadLocal.invokeWithScopedBeanManager(
						() -> new ScopedBeanManager(
							null, portletRequest, portletResponse),
						() -> _invokePortletFilterMethod(
							beanFilterMethod, portletRequest, portletResponse,
							filterChain));
				}

				private void _invokePortletFilterMethod(
						BeanFilterMethod beanFilterMethod,
						PortletRequest portletRequest,
						PortletResponse portletResponse, Object filterChain)
					throws PortletException {

					try {
						beanFilterMethod.invoke(
							portletRequest, portletResponse, filterChain);
					}
					catch (IllegalAccessException illegalAccessException) {
						throw new PortletException(illegalAccessException);
					}
					catch (ReflectiveOperationException
								reflectiveOperationException) {

						Throwable cause =
							reflectiveOperationException.getCause();

						if (cause instanceof PortletException) {
							throw (PortletException)cause;
						}

						throw new PortletException(cause);
					}
				}

			};

		BeanPortletMethodInvoker beanPortletMethodInvoker =
			new BeanPortletMethodInvoker() {

				@Override
				public void invokeWithActiveScopes(
						List<BeanPortletMethod> beanMethods,
						PortletConfig portletConfig,
						PortletRequest portletRequest,
						PortletResponse portletResponse)
					throws PortletException {

					ScopedBeanManagerThreadLocal.invokeWithScopedBeanManager(
						() -> new ScopedBeanManager(
							portletConfig, portletRequest, portletResponse),
						() -> _invokePortletBeanMethods(
							beanMethods, portletRequest, portletResponse,
							portletConfig));
				}

				private void _invokePortletBeanMethods(
						List<BeanPortletMethod> beanPortletMethods,
						PortletRequest portletRequest,
						PortletResponse portletResponse,
						PortletConfig portletConfig)
					throws PortletException {

					for (BeanPortletMethod beanPortletMethod :
							beanPortletMethods) {

						_invokeBeanPortletMethod(
							beanPortletMethod, portletConfig, portletRequest,
							portletResponse);
					}
				}

			};

		_serviceRegistrations.addAll(
			_beanPortletRegistrar.register(
				new CDIBeanFilterMethodFactory(beanManager),
				beanFilterMethodInvoker,
				new CDIBeanPortletMethodFactory(beanManager),
				beanPortletMethodInvoker, _discoveredClasses, servletContext));
	}

	public void step4ApplicationScopedInitializedSync(
		@Initialized(ApplicationScoped.class) @Observes
			ServletContext servletContext,
		BeanManager beanManager, Event<ServletContext> servletContextEvent) {

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

		_beanPortletRegistrar.unregister(
			_serviceRegistrations, (ServletContext)contextObject);

		_serviceRegistrations.clear();
	}

	private void _invokeBeanPortletMethod(
			BeanPortletMethod beanPortletMethod, PortletConfig portletConfig,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		try {
			String include = null;
			Method method = beanPortletMethod.getMethod();

			BeanPortletMethodType beanPortletMethodType =
				beanPortletMethod.getBeanPortletMethodType();

			if (beanPortletMethodType == BeanPortletMethodType.ACTION) {
				ActionRequest actionRequest = (ActionRequest)portletRequest;

				ActionParameters actionParameters =
					actionRequest.getActionParameters();

				String actionName = actionParameters.getValue(
					ActionRequest.ACTION_NAME);

				String beanMethodActionName = beanPortletMethod.getActionName();

				if (Validator.isNull(beanMethodActionName) ||
					beanMethodActionName.equals(actionName)) {

					beanPortletMethod.invoke(portletRequest, portletResponse);
				}
			}
			else if ((beanPortletMethodType == BeanPortletMethodType.HEADER) ||
					 (beanPortletMethodType == BeanPortletMethodType.RENDER)) {

				PortletMode portletMode = portletRequest.getPortletMode();

				PortletMode beanMethodPortletMode =
					beanPortletMethod.getPortletMode();

				if ((beanMethodPortletMode == null) ||
					portletMode.equals(beanMethodPortletMode)) {

					if (method.getParameterCount() == 0) {
						String markup = (String)beanPortletMethod.invoke();

						if (markup != null) {
							MimeResponse mimeResponse =
								(MimeResponse)portletResponse;

							PrintWriter printWriter = mimeResponse.getWriter();

							printWriter.write(markup);
						}
					}
					else {
						beanPortletMethod.invoke(
							portletRequest, portletResponse);
					}

					include = beanPortletMethodType.getInclude(method);
				}
			}
			else if (beanPortletMethodType ==
						BeanPortletMethodType.SERVE_RESOURCE) {

				ResourceRequest resourceRequest =
					(ResourceRequest)portletRequest;

				String resourceID = resourceRequest.getResourceID();

				String beanMethodResourceID = beanPortletMethod.getResourceID();

				if (Validator.isNull(beanMethodResourceID) ||
					beanMethodResourceID.equals(resourceID)) {

					ResourceResponse resourceResponse =
						(ResourceResponse)portletResponse;

					String contentType = beanPortletMethodType.getContentType(
						method);

					if (Validator.isNotNull(contentType) &&
						!Objects.equals(contentType, "*/*")) {

						resourceResponse.setContentType(contentType);
					}

					String characterEncoding =
						beanPortletMethodType.getCharacterEncoding(method);

					if (Validator.isNotNull(characterEncoding)) {
						resourceResponse.setCharacterEncoding(
							characterEncoding);
					}

					if (method.getParameterCount() == 0) {
						String markup = (String)beanPortletMethod.invoke();

						if (Validator.isNotNull(markup)) {
							PrintWriter printWriter =
								resourceResponse.getWriter();

							printWriter.write(markup);
						}
					}
					else {
						beanPortletMethod.invoke(
							resourceRequest, resourceResponse);
					}

					include = beanPortletMethodType.getInclude(method);
				}
			}
			else {
				beanPortletMethod.invoke(portletRequest, portletResponse);
			}

			PortletMode beanMethodPortletMode =
				beanPortletMethod.getPortletMode();

			if (Validator.isNotNull(include) &&
				((beanMethodPortletMode == null) ||
				 beanMethodPortletMode.equals(
					 portletRequest.getPortletMode()))) {

				PortletContext portletContext =
					portletConfig.getPortletContext();

				PortletRequestDispatcher requestDispatcher =
					portletContext.getRequestDispatcher(include);

				if (requestDispatcher == null) {
					_log.error(
						"Unable to acquire dispatcher to include " + include);
				}
				else {
					requestDispatcher.include(portletRequest, portletResponse);
				}
			}
		}
		catch (InvocationTargetException invocationTargetException) {
			Throwable cause = invocationTargetException.getCause();

			if (cause instanceof PortletException) {
				throw (PortletException)cause;
			}

			throw new PortletException(cause);
		}
		catch (PortletException portletException) {
			throw portletException;
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CDIBeanPortletExtension.class);

	private static final Annotation _applicationScoped =
		new ApplicationScoped() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return ApplicationScoped.class;
			}

		};

	private static volatile BeanPortletRegistrar _beanPortletRegistrar =
		ServiceProxyFactory.newServiceTrackedInstance(
			BeanPortletRegistrar.class, CDIBeanPortletExtension.class,
			"_beanPortletRegistrar", true);

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

	private final Set<Class<?>> _discoveredClasses = new HashSet<>();
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}