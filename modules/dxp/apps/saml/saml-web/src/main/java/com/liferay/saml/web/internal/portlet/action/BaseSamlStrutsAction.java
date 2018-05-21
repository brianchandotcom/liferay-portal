/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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