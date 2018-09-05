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

package com.liferay.portal.jsonwebservice;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.action.JSONServiceAction;
import com.liferay.portal.jsonwebservice.action.JSONWebServiceDiscoverAction;
import com.liferay.portal.jsonwebservice.action.JSONWebServiceInvokerAction;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.jsonwebservice.NoSuchJSONWebServiceException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public class JSONWebServiceServiceAction extends JSONServiceAction {

	@Override
	public String getJSON(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		UploadException uploadException = (UploadException)request.getAttribute(
			WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			return JSONFactoryUtil.serializeThrowable(uploadException);
		}

		try {
			JSONWebServiceAction jsonWebServiceAction = getJSONWebServiceAction(
				request);

			Object returnObj = jsonWebServiceAction.invoke();

			if (returnObj != null) {
				return getReturnValue(returnObj);
			}

			return JSONFactoryUtil.getNullJSON();
		}
		catch (Throwable throwable) {
			int status = 0;

			if (throwable instanceof InvocationTargetException) {
				throwable = throwable.getCause();
			}

			if (throwable instanceof NoSuchJSONWebServiceException) {
				status = HttpServletResponse.SC_NOT_FOUND;
			}
			else if (throwable instanceof NoSuchModelException) {
				if (_log.isDebugEnabled()) {
					_log.debug(getThrowableMessage(throwable), throwable);
				}

				response.setStatus(HttpServletResponse.SC_NOT_FOUND);

				return JSONFactoryUtil.serializeThrowable(throwable);
			}
			else if (throwable instanceof PrincipalException ||
					 throwable instanceof SecurityException) {

				if (_log.isDebugEnabled()) {
					_log.debug(getThrowableMessage(throwable), throwable);
				}

				response.setStatus(HttpServletResponse.SC_FORBIDDEN);

				return JSONFactoryUtil.serializeThrowable(throwable);
			}
			else {
				status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(getThrowableMessage(throwable), throwable);
			}
			else {
				_log.error(getThrowableMessage(throwable));
			}

			response.setStatus(status);

			return JSONFactoryUtil.serializeThrowable(throwable);
		}
	}

	/**
	 * @see JSONServiceAction#getCSRFOrigin(HttpServletRequest)
	 */
	@Override
	protected String getCSRFOrigin(HttpServletRequest request) {
		String uri = request.getRequestURI();

		int x = uri.indexOf("jsonws/");

		if (x < 0) {
			return ClassUtil.getClassName(this);
		}

		String path = uri.substring(x + 7);

		String[] pathArray = StringUtil.split(path, CharPool.SLASH);

		if (pathArray.length < 2) {
			return ClassUtil.getClassName(this);
		}

		StringBundler sb = new StringBundler(6);

		sb.append(ClassUtil.getClassName(this));
		sb.append(StringPool.COLON);
		sb.append(StringPool.SLASH);

		String serviceClassName = pathArray[0];

		sb.append(serviceClassName);

		sb.append(StringPool.SLASH);

		String serviceMethodName = pathArray[1];

		sb.append(serviceMethodName);

		return sb.toString();
	}

	protected JSONWebServiceAction getJSONWebServiceAction(
			HttpServletRequest request)
		throws NoSuchJSONWebServiceException {

		String path = GetterUtil.getString(request.getPathInfo());

		if (path.equals("/invoke")) {
			return new JSONWebServiceInvokerAction(request);
		}

		if (PropsValues.JSONWS_WEB_SERVICE_API_DISCOVERABLE &&
			(request.getParameter("discover") != null)) {

			return new JSONWebServiceDiscoverAction(request);
		}

		return JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
			request);
	}

	@Override
	protected String getReroutePath() {
		return _REROUTE_PATH;
	}

	protected String getThrowableMessage(Throwable throwable) {
		String message = throwable.getMessage();

		if (Validator.isNotNull(message)) {
			return message;
		}

		return throwable.toString();
	}

	private static final String _REROUTE_PATH = "/jsonws";

	private static final Log _log = LogFactoryUtil.getLog(
		JSONWebServiceServiceAction.class);

}