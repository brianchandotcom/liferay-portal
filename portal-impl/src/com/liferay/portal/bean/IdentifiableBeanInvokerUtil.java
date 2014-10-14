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

package com.liferay.portal.bean;

import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PortalUtil;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class IdentifiableBeanInvokerUtil {

	public static MethodHandler createMethodHandler(
		MethodInvocation methodInvocation) {

		MethodHandler methodHandler = new MethodHandler(
			methodInvocation.getMethod(), methodInvocation.getArguments());

		String threadContextServletContextName = ClassLoaderPool.getContextName(
			ClassLoaderUtil.getContextClassLoader());

		IdentifiableBean identifiableBean =
			(IdentifiableBean)methodInvocation.getThis();

		Class<?> identifiableBeanClass = identifiableBean.getClass();

		String identifiableBeanServletContextName =
			ClassLoaderPool.getContextName(
				identifiableBeanClass.getClassLoader());

		String beanIdentifier = identifiableBean.getBeanIdentifier();

		return new MethodHandler(
			_invokeMethodKey, methodHandler, threadContextServletContextName,
			identifiableBeanServletContextName, beanIdentifier);
	}

	@SuppressWarnings("unused")
	private static Object _invoke(
			MethodHandler methodHandler, String threadContextServletContextName,
			String identifiableBeanServletContextName, String beanIdentifier)
		throws Exception {

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		ClassLoader classLoader = ClassLoaderPool.getClassLoader(
			threadContextServletContextName);

		ClassLoaderUtil.setContextClassLoader(classLoader);

		try {
			Object bean = null;

			if (identifiableBeanServletContextName.equals(
					PortalUtil.getServletContextName())) {

				bean = PortalBeanLocatorUtil.locate(beanIdentifier);
			}
			else {
				bean = PortletBeanLocatorUtil.locate(
					identifiableBeanServletContextName, beanIdentifier);
			}

			return methodHandler.invoke(bean);
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}
	}

	private static final MethodKey _invokeMethodKey = new MethodKey(
		IdentifiableBeanInvokerUtil.class, "_invoke", MethodHandler.class,
		String.class, String.class, String.class);

}