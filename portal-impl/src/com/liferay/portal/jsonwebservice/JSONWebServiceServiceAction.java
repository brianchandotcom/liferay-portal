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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.action.JSONServiceAction;
import com.liferay.portal.jsonwebservice.action.JSONWebServiceDiscoverAction;
import com.liferay.portal.jsonwebservice.action.JSONWebServiceInvokerAction;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.WebKeys;

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

		JSONWebServiceAction jsonWebServiceAction = null;

		try {
			jsonWebServiceAction = getJSONWebServiceAction(request);

			Object returnObj = jsonWebServiceAction.invoke();

			if (returnObj != null) {
				return getReturnValue(returnObj);
			}
			else {
				return JSONFactoryUtil.getNullJSON();
			}
		}
		catch (InvocationTargetException ite) {
			Throwable throwable = ite.getCause();

			if (throwable instanceof SecurityException) {
				throw (SecurityException)throwable;
			}

			_log.error(throwable, throwable);

			return JSONFactoryUtil.serializeThrowable(throwable);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return JSONFactoryUtil.serializeThrowable(e);
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
		HttpServletRequest request) {

		String path = GetterUtil.getString(request.getPathInfo());

		if (path.equals("/invoke")) {
			return new JSONWebServiceInvokerAction(request);
		}

		if (request.getParameter("discover") != null) {
			return new JSONWebServiceDiscoverAction(request);
		}

		return JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
			request);
	}

	@Override
	protected String getReroutePath() {
		return _REROUTE_PATH;
	}

	private static final String _REROUTE_PATH = "/jsonws";

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceServiceAction.class);

}