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
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.StatusException;
import com.liferay.saml.util.JspUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Arthur Chan
 */
public abstract class BaseSamlMVCActionCommand extends BaseMVCActionCommand {

	public boolean isEnabled() {
		return samlProviderConfigurationHelper.isEnabled();
	}

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		if (!isEnabled()) {
			try {
				redirectToLogin(actionRequest, actionResponse);
			}
			catch (IOException ioException) {
				if (_log.isInfoEnabled()) {
					_log.info(ioException, ioException);
				}
			}

			return false;
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			Class<? extends BaseSamlMVCActionCommand> clazz = getClass();

			currentThread.setContextClassLoader(clazz.getClassLoader());

			doProcessAction(actionRequest, actionResponse);

			return true;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
			else {
				_log.error(exception.getMessage());
			}

			Class<?> clazz = exception.getClass();

			SessionErrors.add(actionRequest, clazz.getName());

			if (exception instanceof StatusException) {
				StatusException statusException = (StatusException)exception;

				SessionErrors.add(
					actionRequest, "statusCodeURI",
					statusException.getMessage());
			}

			actionResponse.setRenderParameter(
				"mvcPath", JspUtil.PATH_PORTAL_SAML_ERROR);

			return false;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void setPortal(Portal portal) {
		this.portal = portal;
	}

	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		this.samlProviderConfigurationHelper = samlProviderConfigurationHelper;
	}

	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest httpServletRequest =
			portal.getOriginalServletRequest(
				portal.getHttpServletRequest(actionRequest));

		HttpServletResponse httpServletResponse = portal.getHttpServletResponse(
			actionResponse);

		httpServletRequest.setAttribute(
			WebKeys.RESOURCE_BUNDLE_LOADER, resourceBundleLoader);

		doProcessAction(httpServletRequest, httpServletResponse);
	}

	protected abstract void doProcessAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	protected Portal portal;

	@Reference(target = "(bundle.symbolic.name=com.liferay.saml.web)")
	protected ResourceBundleLoader resourceBundleLoader;

	protected SamlProviderConfigurationHelper samlProviderConfigurationHelper;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSamlMVCActionCommand.class);

}