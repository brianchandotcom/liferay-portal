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

package com.liferay.portal.messaging.async;

import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.ClassLoaderUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class AsyncRunnable implements Externalizable, Runnable {

	public AsyncRunnable() {
	}

	public AsyncRunnable(MethodInvocation methodInvocation) {
		_methodInvocation = methodInvocation;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_methodHandler = (MethodHandler)objectInput.readObject();
	}

	@Override
	public void run() {
		try {
			if (_methodInvocation != null) {
				_methodInvocation.proceed();
			}
			else {
				AsyncInvokeThreadLocal.setEnabled(true);

				try {
					_methodHandler.invoke(null);
				}
				finally {
					AsyncInvokeThreadLocal.setEnabled(false);
				}
			}
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		MethodHandler methodHandler = _methodHandler;

		if (methodHandler == null) {
			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			String servletContextName = ClassLoaderPool.getContextName(
				contextClassLoader);

			methodHandler = new MethodHandler(
				_methodInvocation.getMethod(),
				_methodInvocation.getArguments());

			Object thisObject = _methodInvocation.getThis();

			IdentifiableBean identifiableBean = (IdentifiableBean)thisObject;

			String beanIdentifier = identifiableBean.getBeanIdentifier();

			methodHandler = new MethodHandler(
				_invokeMethodKey, methodHandler, servletContextName,
				beanIdentifier);
		}

		objectOutput.writeObject(methodHandler);
	}

	@SuppressWarnings("unused")
	private static Object _invoke(
			MethodHandler methodHandler, String servletContextName,
			String beanIdentifier)
		throws Exception {

		if (Validator.isNull(servletContextName)) {
			if (Validator.isNull(beanIdentifier)) {
				return methodHandler.invoke(true);
			}
			else {
				Object bean = PortalBeanLocatorUtil.locate(beanIdentifier);

				return methodHandler.invoke(bean);
			}
		}

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		try {
			ClassLoader classLoader = ClassLoaderPool.getClassLoader(
				servletContextName);

			ClassLoaderUtil.setContextClassLoader(classLoader);

			if (Validator.isNull(beanIdentifier)) {
				return methodHandler.invoke(true);
			}
			else {
				Object bean = PortletBeanLocatorUtil.locate(
					servletContextName, beanIdentifier);

				return methodHandler.invoke(bean);
			}
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}
	}

	private static MethodKey _invokeMethodKey = new MethodKey(
		AsyncRunnable.class, "_invoke", MethodHandler.class, String.class,
		String.class);

	private MethodHandler _methodHandler;
	private MethodInvocation _methodInvocation;

}