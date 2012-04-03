/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;

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
@SuppressWarnings("deprecation")
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

		try {
			ObjectValuePair<HttpPrincipal, Object> ovp =
				(ObjectValuePair<HttpPrincipal, Object>)ois.readObject();

			HttpPrincipal httpPrincipal = ovp.getKey();
			Object ovpValue = ovp.getValue();

			MethodHandler methodHandler = null;
			MethodWrapper methodWrapper = null;

			if (ovpValue instanceof MethodHandler) {
				methodHandler = (MethodHandler)ovpValue;
			}
			else {
				methodWrapper = (MethodWrapper)ovpValue;
			}

			if (methodHandler != null) {
				if (!isValidRequest(methodHandler.getClassName())) {
					return;
				}
			}
			else {
				if (!isValidRequest(methodWrapper.getClassName())) {
					return;
				}
			}

			long companyId = PortalInstances.getCompanyId(request);

			if (Validator.isNotNull(httpPrincipal.getLogin())) {
				User user = null;

				try {
					user = UserLocalServiceUtil.getUserByEmailAddress(
						companyId, httpPrincipal.getLogin());
				}
				catch (NoSuchUserException nsue) {
				}

				if (user == null) {
					try {
						user = UserLocalServiceUtil.getUserByScreenName(
							companyId, httpPrincipal.getLogin());
					}
					catch (NoSuchUserException nsue) {
					}
				}

				if (user == null) {
					try {
						user = UserLocalServiceUtil.getUserById(
							GetterUtil.getLong(httpPrincipal.getLogin()));
					}
					catch (NoSuchUserException nsue) {
					}
				}

				if (user != null) {
					PrincipalThreadLocal.setName(user.getUserId());

					PermissionChecker permissionChecker =
						PermissionCheckerFactoryUtil.create(user);

					PermissionThreadLocal.setPermissionChecker(
						permissionChecker);
				}
			}

			if (returnObj == null) {
				if (methodHandler != null) {
					returnObj = methodHandler.invoke(true);
				}
				else {
					returnObj = MethodInvoker.invoke(methodWrapper);
				}
			}
		}
		catch (InvocationTargetException ite) {
			returnObj = ite.getCause();

			if (!(returnObj instanceof PortalException)) {
				ite.printStackTrace();

				returnObj = new SystemException();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (returnObj != null) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());

				oos.writeObject(returnObj);

				oos.flush();
				oos.close();
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);

				throw ioe;
			}
		}
	}

	protected boolean isValidRequest(String className) {
		if (className.contains(".service.") &&
			className.endsWith("ServiceUtil") &&
			!className.endsWith("LocalServiceUtil")) {

			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TunnelServlet.class);

}