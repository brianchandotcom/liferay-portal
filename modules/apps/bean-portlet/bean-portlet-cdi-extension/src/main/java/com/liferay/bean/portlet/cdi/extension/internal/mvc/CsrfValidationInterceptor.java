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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import java.lang.reflect.Method;

import javax.annotation.Priority;

import javax.inject.Inject;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import javax.mvc.security.Csrf;
import javax.mvc.security.CsrfProtected;

import javax.portlet.ClientDataRequest;

import javax.ws.rs.core.Configuration;

/**
 * @author Neil Griffin
 */
@CsrfValidationInterceptorBinding
@Interceptor
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
public class CsrfValidationInterceptor implements Serializable {

	@AroundInvoke
	public Object validateMethodInvocation(InvocationContext invocationContext)
		throws Exception {

		Csrf.CsrfOptions csrfOptions = Csrf.CsrfOptions.EXPLICIT;

		Object csrfProtection = _configuration.getProperty(
			Csrf.CSRF_PROTECTION);

		if (csrfProtection != null) {
			if (csrfProtection instanceof Csrf.CsrfOptions) {
				csrfOptions = (Csrf.CsrfOptions)csrfProtection;
			}
			else {
				try {
					csrfOptions = Csrf.CsrfOptions.valueOf(
						csrfProtection.toString());
				}
				catch (IllegalArgumentException illegalArgumentException) {
					_log.error(
						illegalArgumentException.getMessage(),
						illegalArgumentException);
				}
			}
		}

		if (csrfOptions == Csrf.CsrfOptions.OFF) {
			return invocationContext.proceed();
		}

		Method targetMethod = invocationContext.getMethod();

		if ((csrfOptions == Csrf.CsrfOptions.EXPLICIT) &&
			!targetMethod.isAnnotationPresent(CsrfProtected.class)) {

			return invocationContext.proceed();
		}

		boolean proceed = false;

		Object[] args = invocationContext.getParameters();

		if (args.length == 2) {
			if (args[0] instanceof ClientDataRequest) {
				ClientDataRequest clientDataRequest =
					(ClientDataRequest)args[0];

				ThemeDisplay themeDisplay =
					(ThemeDisplay)clientDataRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				String method = StringUtil.toLowerCase(
					clientDataRequest.getMethod());

				if (method.equals("post")) {
					try {
						AuthTokenUtil.checkCSRFToken(
							themeDisplay.getRequest(),
							CsrfValidationInterceptor.class.getName());

						proceed = true;
					}
					catch (PrincipalException principalException) {
						_log.error(
							"The CSRF token is invalid", principalException);
					}
				}
				else {
					proceed = true;
				}
			}
			else {
				_log.error(
					"The first parameter of the method signature must be an " +
						"ActionRequest or ResourceRequest");
			}
		}
		else {
			_log.error(
				"The method signature must include " +
					"(ActionRequest,ActionResponse) or " +
						"(ResourceRequest,ResourceResponse) as parameters");
		}

		if (proceed) {
			return invocationContext.proceed();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CsrfValidationInterceptor.class);

	private static final long serialVersionUID = 1348567603498123441L;

	@Inject
	private Configuration _configuration;

}