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

package com.liferay.portal.kernel.deploy.hot;

import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.ProxyFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

			return (String)servletContextNameMethod.invoke(null);
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

}