/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.deploy.hot;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseHotDeployListener implements HotDeployListener {

	public void throwHotDeployException(
			HotDeployEvent event, String msg, Throwable t)
		throws HotDeployException {

		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		throw new HotDeployException(msg + servletContextName, t);
	}

	protected String getClpServletContextName(
			Class<?> clpMessageListenerClass,
			MessageListener clpMessageListener)
		throws Exception {

		Exception e = null;

		try {
			Method servletContextNameMethod = clpMessageListenerClass.getMethod(
				"getServletContextName");

			String clpServletContextName =
				(String)servletContextNameMethod.invoke(null);

			return clpServletContextName;
		}
		catch (Exception e1) {
			e = e1;
		}

		try {
			Field servletContextNameField = clpMessageListenerClass.getField(
				"SERVLET_CONTEXT_NAME");

			Object clpServletContextName = servletContextNameField.get(
				clpMessageListener);

			return clpServletContextName.toString();
		}
		catch (Exception e2) {
		}

		throw e;
	}

	protected Object newInstance(
			ClassLoader portletClassLoader, Class<?> interfaceClass,
			String implClassName)
		throws Exception {

		return ProxyFactory.newInstance(
			portletClassLoader, interfaceClass, implClassName);
	}

	protected Object newInstance(
			ClassLoader portletClassLoader, Class<?>[] interfaceClasses,
			String implClassName)
		throws Exception {

		return ProxyFactory.newInstance(
			portletClassLoader, interfaceClasses, implClassName);
	}

	protected void registerClpMessageListeners(
			ServletContext servletContext, ClassLoader portletClassLoader)
		throws Exception {

		List<MessageListener> clpMessageListeners =
			(List<MessageListener>)servletContext.getAttribute(
				WebKeys.CLP_MESSAGE_LISTENERS);

		if (clpMessageListeners != null) {
			return;
		}

		clpMessageListeners = new ArrayList<MessageListener>();

		Set<String> classNames = ServletContextUtil.getClassNames(
			servletContext);

		for (String className : classNames) {
			if (className.endsWith(".ClpMessageListener")) {
				Class<?> clpMessageListenerClass = portletClassLoader.loadClass(
					className);

				MessageListener clpMessageListener =
					(MessageListener)clpMessageListenerClass.newInstance();

				String clpServletContextName = getClpServletContextName(
					clpMessageListenerClass, clpMessageListener);

				if (clpServletContextName.equals(
						servletContext.getServletContextName())) {

					continue;
				}

				clpMessageListeners.add(clpMessageListener);

				MessageBusUtil.registerMessageListener(
					DestinationNames.HOT_DEPLOY, clpMessageListener);
			}
		}

		servletContext.setAttribute(
			WebKeys.CLP_MESSAGE_LISTENERS, clpMessageListeners);
	}

	protected void unregisterClpMessageListeners(ServletContext servletContext)
		throws Exception {

		List<MessageListener> clpMessageListeners =
			(List<MessageListener>)servletContext.getAttribute(
				WebKeys.CLP_MESSAGE_LISTENERS);

		if (clpMessageListeners != null) {
			servletContext.removeAttribute(WebKeys.CLP_MESSAGE_LISTENERS);

			for (MessageListener clpMessageListener : clpMessageListeners) {
				MessageBusUtil.unregisterMessageListener(
					DestinationNames.HOT_DEPLOY, clpMessageListener);
			}
		}
	}

}