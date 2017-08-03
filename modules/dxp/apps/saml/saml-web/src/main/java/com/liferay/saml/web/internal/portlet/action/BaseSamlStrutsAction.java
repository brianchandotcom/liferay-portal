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

package com.liferay.saml.web.internal.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.StatusException;
import com.liferay.saml.util.JspUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mika Koivisto
 */
public abstract class BaseSamlStrutsAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		if (!isEnabled()) {
			return "/common/referer_js.jsp";
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			Class<? extends BaseSamlStrutsAction> clazz = getClass();

			currentThread.setContextClassLoader(clazz.getClassLoader());

			return doExecute(request, response);
		}
		catch (Exception e) {
			_log.error(e, e);

			Class<?> clazz = e.getClass();

			SessionErrors.add(request, clazz.getName());

			if (e instanceof StatusException) {
				StatusException statusException = (StatusException)e;

				SessionErrors.add(
					request, "statusCodeURI", statusException.getMessage());
			}

			JspUtil.dispatch(
				request, response, JspUtil.PATH_PORTAL_SAML_ERROR, "status");
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		return null;
	}

	public boolean isEnabled() {
		return samlProviderConfigurationHelper.isEnabled();
	}

	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		this.samlProviderConfigurationHelper = samlProviderConfigurationHelper;
	}

	protected abstract String doExecute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception;

	protected SamlProviderConfigurationHelper samlProviderConfigurationHelper;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSamlStrutsAction.class);

}