/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.action.JSONServiceAction;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.remote.json.web.service.web.internal.action.JSONWebServiceDiscoverAction;
import com.liferay.portal.remote.json.web.service.web.internal.action.JSONWebServiceInvokerAction;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public class JSONWebServiceServiceAction extends JSONServiceAction {

	@Override
	public String getJSON(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		UploadException uploadException =
			(UploadException)httpServletRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			if (PropsValues.JSON_SERVICE_SERIALIZE_THROWABLE) {
				return JSONFactoryUtil.serializeThrowable(uploadException);
			}

			return JSONFactoryUtil.getNullJSON();
		}

		try {
			JSONWebServiceAction jsonWebServiceAction = getJSONWebServiceAction(
				httpServletRequest);

			Object returnObject = jsonWebServiceAction.invoke();

			if (returnObject != null) {
				return getReturnValue(returnObject);
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

				httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

				if (PropsValues.JSON_SERVICE_SERIALIZE_THROWABLE) {
					return JSONFactoryUtil.serializeThrowable(throwable);
				}

				return JSONFactoryUtil.getNullJSON();
			}
			else if (throwable instanceof PrincipalException ||
					 throwable instanceof SecurityException) {

				if (_log.isDebugEnabled()) {
					_log.debug(getThrowableMessage(throwable), throwable);
				}

				httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);

				if (PropsValues.JSON_SERVICE_SERIALIZE_THROWABLE) {
					return JSONFactoryUtil.serializeThrowable(throwable);
				}

				return JSONFactoryUtil.getNullJSON();
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

			httpServletResponse.setStatus(status);

			if (PropsValues.JSON_SERVICE_SERIALIZE_THROWABLE) {
				return JSONFactoryUtil.serializeThrowable(throwable);
			}

			return JSONFactoryUtil.getNullJSON();
		}
	}

	/**
	 * @see JSONServiceAction#getCSRFOrigin(HttpServletRequest)
	 */
	@Override
	protected String getCSRFOrigin(HttpServletRequest httpServletRequest) {
		String uri = httpServletRequest.getRequestURI();

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
			HttpServletRequest httpServletRequest)
		throws NoSuchJSONWebServiceException {

		String path = GetterUtil.getString(
			httpServletRequest.getAttribute(WebKeys.ORIGINAL_PATH_INFO));

		if (path.equals("/invoke")) {
			return new JSONWebServiceInvokerAction(httpServletRequest);
		}

		if (PropsValues.JSONWS_WEB_SERVICE_API_DISCOVERABLE &&
			(httpServletRequest.getParameter("discover") != null)) {

			return new JSONWebServiceDiscoverAction(httpServletRequest);
		}

		return JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
			httpServletRequest);
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