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
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.StatusException;
import com.liferay.saml.util.JspUtil;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
public abstract class BaseSamlMVCResourceCommand
	extends BaseMVCResourceCommand {

	public boolean isEnabled() {
		return samlProviderConfigurationHelper.isEnabled();
	}

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		//PortletRequestDispatcher portletRequestDispatcher =
		//	getPortletRequestDispatcher(
		//		resourceRequest, JspUtil.PATH_PORTAL_SAML_ERROR);

		if (!isEnabled()) {
			try {
				//portletRequestDispatcher.forward(
				//	resourceRequest, resourceResponse);

				include(
					resourceRequest, resourceResponse,
					JspUtil.PATH_PORTAL_SAML_ERROR);

				return false;
			}
			catch (IOException ioException) {
				throw new PortletException(ioException);
			}
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			Class<? extends BaseSamlMVCResourceCommand> clazz = getClass();

			currentThread.setContextClassLoader(clazz.getClassLoader());

			doServeResource(resourceRequest, resourceResponse);

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

			SessionErrors.add(resourceRequest, clazz.getName());

			if (exception instanceof StatusException) {
				StatusException statusException = (StatusException)exception;

				SessionErrors.add(
					resourceRequest, "statusCodeURI",
					statusException.getMessage());
			}

			try {
				include(
					resourceRequest, resourceResponse,
					JspUtil.PATH_PORTAL_SAML_ERROR);
				//portletRequestDispatcher.forward(
				//	resourceRequest, resourceResponse);

				return false;
			}
			catch (IOException ioException) {
				throw new PortletException(ioException);
			}
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

	protected abstract void doServeResource(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest =
			portal.getOriginalServletRequest(
				portal.getHttpServletRequest(resourceRequest));

		HttpServletResponse httpServletResponse = portal.getHttpServletResponse(
			resourceResponse);

		httpServletRequest.setAttribute(
			WebKeys.RESOURCE_BUNDLE_LOADER, resourceBundleLoader);

		doServeResource(httpServletRequest, httpServletResponse);
	}

	protected Portal portal;

	@Reference(target = "(bundle.symbolic.name=com.liferay.saml.web)")
	protected ResourceBundleLoader resourceBundleLoader;

	protected SamlProviderConfigurationHelper samlProviderConfigurationHelper;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSamlMVCResourceCommand.class);

}