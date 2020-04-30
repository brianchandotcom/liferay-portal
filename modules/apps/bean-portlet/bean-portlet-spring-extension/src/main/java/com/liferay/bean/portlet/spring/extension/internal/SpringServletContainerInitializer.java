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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.URL;

import java.util.EventListener;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Neil Griffin
 */
public class SpringServletContainerInitializer
	implements ServletContainerInitializer {

	@Override
	public void onStartup(
		Set<Class<?>> scannedClasses, ServletContext servletContext) {

		try {
			Class<?> discoveredClass = Class.forName(
				"org.springframework.web.context.ContextLoaderListener");

			if (EventListener.class.isAssignableFrom(discoveredClass)) {
				@SuppressWarnings("unchecked")
				Class<? extends EventListener> contextLoaderListenerClass =
					(Class<? extends EventListener>)discoveredClass;

				servletContext.addListener(contextLoaderListenerClass);
			}

			String contextConfigLocation = servletContext.getInitParameter(
				"contextConfigLocation");

			if (contextConfigLocation == null) {
				contextConfigLocation = _CONTEXT_CONFIG_CLASSPATH_LOCATION;

				BundleContext bundleContext =
					(BundleContext)servletContext.getAttribute(
						"osgi-bundlecontext");

				Bundle bundle = bundleContext.getBundle();

				URL applicationContextDescriptorURL = bundle.getEntry(
					_APP_CONTEXT_DESCRIPTOR);

				if (applicationContextDescriptorURL == null) {
					applicationContextDescriptorURL = bundle.getEntry(
						_PORTLET_APP_CONTEXT_DESCRIPTOR);

					if (applicationContextDescriptorURL == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Neither ", _APP_CONTEXT_DESCRIPTOR, " or ",
									_PORTLET_APP_CONTEXT_DESCRIPTOR,
									" were found"));
						}
					}
					else {
						contextConfigLocation = contextConfigLocation.concat(
							" ");
						contextConfigLocation = contextConfigLocation.concat(
							_PORTLET_APP_CONTEXT_DESCRIPTOR);
					}
				}
				else {
					contextConfigLocation = contextConfigLocation.concat(" ");
					contextConfigLocation = contextConfigLocation.concat(
						_APP_CONTEXT_DESCRIPTOR);
				}

				servletContext.setInitParameter(
					"contextConfigLocation", contextConfigLocation);
			}
			else if (!contextConfigLocation.contains(
						_CONTEXT_CONFIG_CLASSPATH_LOCATION)) {

				servletContext.setInitParameter(
					"contextConfigLocation",
					_CONTEXT_CONFIG_CLASSPATH_LOCATION.concat(
						" "
					).concat(
						contextConfigLocation
					));
			}
		}
		catch (ClassNotFoundException classNotFoundException) {
			_log.error(classNotFoundException, classNotFoundException);
		}

		servletContext.addListener(SpringHttpSessionListener.class);
		servletContext.addListener(SpringServletContextListener.class);
	}

	private static final String _APP_CONTEXT_DESCRIPTOR =
		"/WEB-INF/applicationContext.xml";

	private static final String _CONTEXT_CONFIG_CLASSPATH_LOCATION =
		"classpath:/META-INF/springBeanPortletContext.xml";

	private static final String _PORTLET_APP_CONTEXT_DESCRIPTOR =
		"/WEB-INF/spring-context/portlet-application-context.xml";

	private static final Log _log = LogFactoryUtil.getLog(
		SpringServletContainerInitializer.class);

}