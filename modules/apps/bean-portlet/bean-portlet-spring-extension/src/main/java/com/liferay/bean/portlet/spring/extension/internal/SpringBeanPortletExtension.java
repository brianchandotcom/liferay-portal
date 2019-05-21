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

package com.liferay.bean.portlet.spring.extension.internal;

import com.liferay.bean.portlet.extension.BeanFilterMethod;
import com.liferay.bean.portlet.extension.BeanFilterMethodInvoker;
import com.liferay.bean.portlet.extension.BeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodDecorator;
import com.liferay.bean.portlet.extension.BeanPortletMethodInvoker;
import com.liferay.bean.portlet.extension.BeanPortletMethodType;
import com.liferay.bean.portlet.extension.ScopedBean;
import com.liferay.bean.portlet.extension.ViewRenderer;
import com.liferay.bean.portlet.registration.BeanPortletRegistrar;
import com.liferay.bean.portlet.spring.extension.internal.scope.SpringPortletRequestScope;
import com.liferay.bean.portlet.spring.extension.internal.scope.SpringPortletSessionScope;
import com.liferay.bean.portlet.spring.extension.internal.scope.SpringRedirectScope;
import com.liferay.bean.portlet.spring.extension.internal.scope.SpringRenderStateScope;
import com.liferay.bean.portlet.spring.extension.internal.scope.SpringScopedBeanManager;
import com.liferay.bean.portlet.spring.extension.internal.scope.SpringScopedBeanManagerThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.async.PortletAsyncListenerFactory;
import com.liferay.portal.kernel.portlet.async.PortletAsyncScopeManagerFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.io.PrintWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionParameters;
import javax.portlet.ActionRequest;
import javax.portlet.EventPortlet;
import javax.portlet.HeaderPortlet;
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
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;
import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.DestroyMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.RenderMethod;
import javax.portlet.annotations.ServeResourceMethod;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.HeaderFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author Neil Griffin
 */
public class SpringBeanPortletExtension {

	public SpringBeanPortletExtension(ApplicationContext applicationContext) {
		_applicationContext = applicationContext;
	}

	public void step1RegisterScopes(
		ConfigurableBeanFactory configurableBeanFactory) {

		configurableBeanFactory.registerScope(
			"portletRequest", new SpringPortletRequestScope());

		configurableBeanFactory.registerScope(
			"portletAppSession",
			new SpringPortletSessionScope(PortletSession.APPLICATION_SCOPE));

		configurableBeanFactory.registerScope(
			"portletSession",
			new SpringPortletSessionScope(PortletSession.PORTLET_SCOPE));

		configurableBeanFactory.registerScope(
			"portletRenderState", new SpringRenderStateScope());

		configurableBeanFactory.registerScope(
			"portletRedirect", new SpringRedirectScope());

		_configurableBeanFactory = configurableBeanFactory;
	}

	public void step2ProcessAnnotatedType(String className) {
		try {
			Class<?> clazz = Class.forName(className);

			if (_isAnnotatedType(clazz)) {
				_annotatedClasses.add(clazz);
			}
		}
		catch (ClassNotFoundException cnfe) {
			_log.error(cnfe, cnfe);
		}
	}

	public void step3ApplicationScopeInitialized(
		ServletContext servletContext) {

		BundleContext bundleContext =
			(BundleContext)servletContext.getAttribute("osgi-bundlecontext");

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"servlet.context.name", servletContext.getServletContextName());

		_serviceRegistrations.add(
			bundleContext.registerService(
				PortletAsyncScopeManagerFactory.class,
				SpringPortletAsyncScopeManager::new, properties));

		_serviceRegistrations.add(
			bundleContext.registerService(
				PortletAsyncListenerFactory.class,
				new PortletAsyncListenerFactory() {

					@Override
					public <T extends PortletAsyncListener> T
							getPortletAsyncListener(Class<T> clazz)
						throws PortletException {

						T bean = _applicationContext.getBean(clazz);

						if (bean == null) {
							throw new PortletException(
								"Unable to create an instance of " +
									clazz.getName());
						}

						try {
							return clazz.cast(bean);
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

		BeanFilterMethodInvoker beanFilterMethodInvoker =
			new BeanFilterMethodInvoker() {

				@Override
				public void invokeWithActiveScopes(
						BeanFilterMethod beanFilterMethod,
						PortletRequest portletRequest,
						PortletResponse portletResponse, Object filterChain)
					throws PortletException {

					SpringScopedBeanManagerThreadLocal.
						invokeWithScopedBeanManager(
							() -> _invokePortletFilterMethod(
								beanFilterMethod, portletRequest,
								portletResponse, filterChain),
							() -> new SpringScopedBeanManager(
								portletRequest, portletResponse, null));
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
					catch (IllegalAccessException iae) {
						throw new PortletException(iae);
					}
					catch (ReflectiveOperationException roe) {
						Throwable cause = roe.getCause();

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
						PortletRequest portletRequest,
						PortletResponse portletResponse,
						PortletConfig portletConfig)
					throws PortletException {

					SpringScopedBeanManagerThreadLocal.
						invokeWithScopedBeanManager(
							() -> _invokePortletBeanMethods(
								beanMethods, portletRequest, portletResponse,
								portletConfig),
							() -> new SpringScopedBeanManager(
								portletRequest, portletResponse,
								portletConfig));
				}

				private void _invokePortletBeanMethods(
						List<BeanPortletMethod> beanMethods,
						PortletRequest portletRequest,
						PortletResponse portletResponse,
						PortletConfig portletConfig)
					throws PortletException {

					for (BeanPortletMethod beanMethod : beanMethods) {
						try {

							// MVC

							@SuppressWarnings("unchecked")
							BeanPortletMethodDecorator
								beanPortletMethodDecorator =
									_applicationContext.getBean(
										"beanPortletMethodDecorator",
										BeanPortletMethodDecorator.class);

							beanMethod =
								beanPortletMethodDecorator.getBeanMethod(
									portletRequest, portletResponse,
									portletConfig, beanMethod);

							String include = null;
							Method method = beanMethod.getMethod();

							BeanPortletMethodType beanPortletMethodType =
								beanMethod.getMethodType();

							if (beanPortletMethodType ==
									BeanPortletMethodType.ACTION) {

								ActionRequest actionRequest =
									(ActionRequest)portletRequest;

								ActionParameters actionParameters =
									actionRequest.getActionParameters();

								String actionName = actionParameters.getValue(
									ActionRequest.ACTION_NAME);

								String beanMethodActionName =
									beanMethod.getActionName();

								if (Validator.isNull(beanMethodActionName) ||
									beanMethodActionName.equals(actionName)) {

									beanMethod.invoke(
										portletRequest, portletResponse);
								}
							}
							else if ((beanPortletMethodType ==
										BeanPortletMethodType.HEADER) ||
									 (beanPortletMethodType ==
										 BeanPortletMethodType.RENDER)) {

								PortletMode portletMode =
									portletRequest.getPortletMode();

								PortletMode beanMethodPortletMode =
									beanMethod.getPortletMode();

								if ((beanMethodPortletMode == null) ||
									portletMode.equals(beanMethodPortletMode)) {

									if (method.getParameterCount() == 0) {
										String markup =
											(String)beanMethod.invoke();

										if (markup != null) {
											MimeResponse mimeResponse =
												(MimeResponse)portletResponse;

											PrintWriter printWriter =
												mimeResponse.getWriter();

											printWriter.write(markup);
										}
									}
									else {
										beanMethod.invoke(
											portletRequest, portletResponse);
									}

									include = beanPortletMethodType.getInclude(
										method);
								}
							}
							else if (beanPortletMethodType ==
										BeanPortletMethodType.SERVE_RESOURCE) {

								ResourceRequest resourceRequest =
									(ResourceRequest)portletRequest;

								String resourceID =
									resourceRequest.getResourceID();

								String beanMethodResourceID =
									beanMethod.getResourceID();

								if (Validator.isNull(beanMethodResourceID) ||
									beanMethodResourceID.equals(resourceID)) {

									ResourceResponse resourceResponse =
										(ResourceResponse)portletResponse;

									String contentType =
										beanPortletMethodType.getContentType(
											method);

									if (Validator.isNotNull(contentType) &&
										!Objects.equals(contentType, "*/*")) {

										resourceResponse.setContentType(
											contentType);
									}

									String characterEncoding =
										beanPortletMethodType.
											getCharacterEncoding(method);

									if (Validator.isNotNull(
											characterEncoding)) {

										resourceResponse.setCharacterEncoding(
											characterEncoding);
									}

									if (method.getParameterCount() == 0) {
										String markup =
											(String)beanMethod.invoke();

										if (Validator.isNotNull(markup)) {
											PrintWriter printWriter =
												resourceResponse.getWriter();

											printWriter.write(markup);
										}
									}
									else {
										beanMethod.invoke(
											resourceRequest, resourceResponse);
									}

									include = beanPortletMethodType.getInclude(
										method);
								}
							}
							else {
								beanMethod.invoke(
									portletRequest, portletResponse);
							}

							PortletMode beanMethodPortletMode =
								beanMethod.getPortletMode();

							if (Validator.isNotNull(include) &&
								((beanMethodPortletMode == null) ||
								 beanMethodPortletMode.equals(
									 portletRequest.getPortletMode()))) {

								PortletContext portletContext =
									portletConfig.getPortletContext();

								PortletRequestDispatcher requestDispatcher =
									portletContext.getRequestDispatcher(
										include);

								if (requestDispatcher == null) {
									_log.error(
										"Unable to acquire dispatcher to " +
											"include=" + include);
								}
								else {
									requestDispatcher.include(
										portletRequest, portletResponse);
								}
							}
						}
						catch (InvocationTargetException ite) {
							Throwable cause = ite.getCause();

							if (cause instanceof PortletException) {
								throw (PortletException)cause;
							}

							throw new PortletException(cause);
						}
						catch (PortletException pe) {
							throw pe;
						}
						catch (Exception e) {
							throw new PortletException(e);
						}
					}

					// MVC

					if (portletResponse instanceof RenderResponse ||
						portletResponse instanceof ResourceResponse) {

						ViewRenderer viewRenderer = _applicationContext.getBean(
							"viewRenderer", ViewRenderer.class);

						viewRenderer.render(
							portletRequest, (MimeResponse)portletResponse,
							portletConfig);
					}
				}

			};

		_beanPortletRegistrar = (BeanPortletRegistrar)bundleContext.getService(
			bundleContext.getServiceReference(
				BeanPortletRegistrar.class.getName()));

		_beanPortletRegistrar.register(
			_annotatedClasses, servletContext,
			new SpringBeanFilterMethodFactory(_configurableBeanFactory),
			beanFilterMethodInvoker,
			new SpringBeanPortletMethodFactory(_configurableBeanFactory),
			beanPortletMethodInvoker);
	}

	public void step4SessionScopeBeforeDestroyed(HttpSession httpSession) {
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

	public void step5ApplicationScopeBeforeDestroyed(
		ServletContext servletContext) {

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

		_beanPortletRegistrar.unregister(servletContext);
	}

	private boolean _isAnnotatedType(Class<?> clazz) {
		if (ActionFilter.class.isAssignableFrom(clazz) ||
			EventFilter.class.isAssignableFrom(clazz) ||
			EventPortlet.class.isAssignableFrom(clazz) ||
			HeaderFilter.class.isAssignableFrom(clazz) ||
			HeaderPortlet.class.isAssignableFrom(clazz) ||
			Portlet.class.isAssignableFrom(clazz) ||
			RenderFilter.class.isAssignableFrom(clazz) ||
			ResourceFilter.class.isAssignableFrom(clazz) ||
			ResourceServingPortlet.class.isAssignableFrom(clazz)) {

			return true;
		}

		Method[] methods = null;

		try {
			methods = clazz.getMethods();
		}
		catch (Throwable t) {
			String className = clazz.getName();

			if (!className.startsWith("org.springframework")) {
				_log.error("Unable to discover methods in class: " + clazz);
			}

			return false;
		}

		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();

			for (Annotation annotation : annotations) {
				Class<? extends Annotation> annotationClass =
					annotation.getClass();

				if (ActionMethod.class.isAssignableFrom(annotationClass) ||
					DestroyMethod.class.isAssignableFrom(annotationClass) ||
					EventMethod.class.isAssignableFrom(annotationClass) ||
					RenderMethod.class.isAssignableFrom(annotationClass) ||
					ServeResourceMethod.class.isAssignableFrom(
						annotationClass)) {

					return true;
				}
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SpringBeanPortletExtension.class);

	private Set<Class<?>> _annotatedClasses = new HashSet<>();
	private final ApplicationContext _applicationContext;
	private BeanPortletRegistrar _beanPortletRegistrar;
	private BeanFactory _configurableBeanFactory;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}