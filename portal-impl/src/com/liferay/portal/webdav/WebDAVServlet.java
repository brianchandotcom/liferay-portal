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

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.kernel.webdav.methods.MethodFactory;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Fabio Pezzutto
 */
public class WebDAVServlet extends HttpServlet {

	@Override
	public void service(
		HttpServletRequest request, HttpServletResponse response) {

		int status = HttpServletResponse.SC_PRECONDITION_FAILED;

		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

		if (_log.isDebugEnabled()) {
			_log.debug("User agent " + userAgent);
		}

		try {
			if (isIgnoredResource(request)) {
				status = HttpServletResponse.SC_NOT_FOUND;

				return;
			}

			WebDAVStorage storage = getStorage(request);

			if (storage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Invalid WebDAV path " + request.getPathInfo());
				}

				return;
			}

			// Set the path only if it has not already been set. This works if
			// and only if the servlet is not mapped to more than one URL.

			if (storage.getRootPath() == null) {
				storage.setRootPath(getRootPath(request));
			}

			PermissionChecker permissionChecker = null;

			String remoteUser = request.getRemoteUser();

			if (remoteUser != null) {
				PrincipalThreadLocal.setName(remoteUser);

				long userId = GetterUtil.getLong(remoteUser);

				User user = UserLocalServiceUtil.getUserById(userId);

				permissionChecker = PermissionCheckerFactoryUtil.create(user);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);
			}

			// Get the method instance

			MethodFactory methodFactory = storage.getMethodFactory();

			Method method = methodFactory.create(request);

			// Process the method

			try {
				WebDAVRequest webDAVRequest = new WebDAVRequestImpl(
					storage, request, response, userAgent, permissionChecker);

				status = method.process(webDAVRequest);
			}
			catch (WebDAVException wdave) {
				boolean logError = false;

				Throwable cause = wdave;

				while (cause != null) {
					if (cause instanceof PrincipalException) {
						logError = true;
					}

					cause = cause.getCause();
				}

				if (logError) {
					_log.error(wdave, wdave);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(wdave, wdave);
				}

				status = HttpServletResponse.SC_PRECONDITION_FAILED;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			response.setStatus(status);

			if (_log.isInfoEnabled()) {
				String xLitmus = GetterUtil.getString(
					request.getHeader("X-Litmus"));

				if (Validator.isNotNull(xLitmus)) {
					xLitmus += " ";
				}

				_log.info(
					xLitmus + request.getMethod() + " " +
						request.getRequestURI() + " " + status);
			}
		}
	}

	protected String getRootPath(HttpServletRequest request) {
		String contextPath = HttpUtil.fixPath(
			PortalUtil.getPathContext(request), false, true);
		String ServletPath = HttpUtil.fixPath(
			request.getServletPath(), false, true);

		return contextPath.concat(ServletPath);
	}

	protected WebDAVStorage getStorage(HttpServletRequest request) {
		String pathInfo = WebDAVUtil.stripManualCheckInRequiredPath(
			request.getPathInfo());

		pathInfo = WebDAVUtil.stripOfficeExtension(pathInfo);

		String[] pathArray = WebDAVUtil.getPathArray(pathInfo, true);

		WebDAVStorage storage = null;

		if (pathArray.length == 0) {
			storage = (WebDAVStorage)InstancePool.get(
				CompanyWebDAVStorageImpl.class.getName());
		}
		else if (pathArray.length == 1) {
			storage = (WebDAVStorage)InstancePool.get(
				GroupWebDAVStorageImpl.class.getName());
		}
		else if (pathArray.length >= 2) {
			storage = WebDAVUtil.getStorage(pathArray[1]);
		}

		return storage;
	}

	protected boolean isIgnoredResource(HttpServletRequest request) {
		String[] pathArray = WebDAVUtil.getPathArray(
			request.getPathInfo(), true);

		if (ArrayUtil.isEmpty(pathArray)) {
			return false;
		}

		for (String ignore : PropsValues.WEBDAV_IGNORE) {
			String[] ignoreArray = ignore.split(StringPool.SLASH);

			if (ignoreArray.length > pathArray.length) {
				continue;
			}

			boolean match = true;

			for (int i = 1; i <= ignoreArray.length; i++) {
				if (!pathArray[pathArray.length - i].equals(
						ignoreArray[ignoreArray.length - i])) {

					match = false;

					break;
				}
			}

			if (match) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Skipping over " + request.getMethod() + " " +
							request.getPathInfo());
				}

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(WebDAVServlet.class);

}