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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.action.JSONServiceAction;
import com.liferay.portal.jsonwebservice.action.JSONWebServiceInvokerAction;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceServiceAction extends JSONServiceAction {

	public JSONWebServiceServiceAction(
		String servletContextPath, ClassLoader classLoader) {

		_jsonWebServiceConfigurator = new JSONWebServiceConfigurator(
			servletContextPath);

		_jsonWebServiceConfigurator.clean();

		if (PropsValues.JSON_WEB_SERVICE_ENABLED) {
			try {
				_jsonWebServiceConfigurator.configure(classLoader);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("JSON web service is disabled");
			}
		}
	}

	public void destroy() {
		_jsonWebServiceConfigurator.clean();
	}

	@Override
	public String getJSON(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		JSONWebServiceAction jsonWebServiceAction = null;

		String path = GetterUtil.getString(request.getPathInfo());

		try {
			if (path.equals("/invoke")) {
				jsonWebServiceAction = new JSONWebServiceInvokerAction(request);
			}
			else {
				jsonWebServiceAction =
					JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
						request);
			}

			JSONWebServiceActionMapping jsonWebServiceActionMapping =
				jsonWebServiceAction.getJSONWebServiceActionMapping();

			String actionMethodName = null;

			if (jsonWebServiceActionMapping != null) {

				Method actionMethod =
					jsonWebServiceActionMapping.getActionMethod();

				actionMethodName = actionMethod.getName();
			}

			checkMethodPublicAccess(
				request, actionMethodName,
				PropsValues.JSONWS_WEB_SERVICE_PUBLIC_METHODS);

			Object returnObj = jsonWebServiceAction.invoke();

			if (returnObj != null) {
				return getReturnValue(returnObj);
			}
			else {
				return JSONFactoryUtil.getNullJSON();
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			return JSONFactoryUtil.serializeException(e);
		}
	}

	@Override
	protected String getReroutePath() {
		return _REROUTE_PATH;
	}

	private static final String _REROUTE_PATH = "/jsonws";

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceServiceAction.class);

	private JSONWebServiceConfigurator _jsonWebServiceConfigurator;

}