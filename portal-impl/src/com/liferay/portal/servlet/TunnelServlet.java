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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.security.ac.AccessControlThreadLocal;
import com.liferay.portal.security.auth.HttpPrincipal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Michael Weisser
 * @author Brian Wing Shun Chan
 */
public class TunnelServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		ObjectInputStream ois;

		try {
			ois = new ObjectInputStream(request.getInputStream());
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}

			return;
		}

		Object returnObj = null;

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		try {
			AccessControlThreadLocal.setRemoteAccess(true);

			ObjectValuePair<HttpPrincipal, MethodHandler> ovp =
				(ObjectValuePair<HttpPrincipal, MethodHandler>)ois.readObject();

			MethodHandler methodHandler = ovp.getValue();

			if (methodHandler != null) {
				MethodKey methodKey = methodHandler.getMethodKey();

				if (!isValidRequest(methodKey.getDeclaringClass())) {
					return;
				}

				returnObj = methodHandler.invoke();
			}
		}
		catch (InvocationTargetException ite) {
			returnObj = ite.getCause();

			if (!(returnObj instanceof PortalException)) {
				_log.error(ite, ite);

				if (returnObj != null) {
					Throwable throwable = (Throwable)returnObj;

					returnObj = new SystemException(throwable.getMessage());
				}
				else {
					returnObj = new SystemException();
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			AccessControlThreadLocal.setRemoteAccess(remoteAccess);
		}

		if (returnObj != null) {
			try (ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream())) {

				oos.writeObject(returnObj);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);

				throw ioe;
			}
		}
	}

	protected boolean isValidRequest(Class<?> clazz) {
		String className = clazz.getName();

		if (className.contains(".service.") &&
			className.endsWith("ServiceUtil") &&
			!className.endsWith("LocalServiceUtil")) {

			return true;
		}
		else {
			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(TunnelServlet.class);

}