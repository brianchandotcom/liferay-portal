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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.StatusException;
import com.liferay.saml.util.JspUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Arthur Chan
 */
public abstract class BaseSamlMVCRenderCommand implements MVCRenderCommand {

	public boolean isEnabled() {
		return samlProviderConfigurationHelper.isEnabled();
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		if (!isEnabled()) {
			return "/common/referer_js.jsp";
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			Class<? extends BaseSamlMVCRenderCommand> clazz = getClass();

			currentThread.setContextClassLoader(clazz.getClassLoader());

			return doRender(renderRequest, renderResponse);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
			else {
				_log.error(exception.getMessage());
			}

			Class<?> clazz = exception.getClass();

			SessionErrors.add(renderRequest, clazz.getName());

			if (exception instanceof StatusException) {
				StatusException statusException = (StatusException)exception;

				SessionErrors.add(
					renderRequest, "statusCodeURI",
					statusException.getMessage());
			}

			return JspUtil.PATH_PORTAL_SAML_ERROR;
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

	protected abstract String doRender(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	protected String doRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		HttpServletRequest httpServletRequest =
			portal.getOriginalServletRequest(
				portal.getHttpServletRequest(renderRequest));

		HttpServletResponse httpServletResponse = portal.getHttpServletResponse(
			renderResponse);

		httpServletRequest.setAttribute(
			WebKeys.RESOURCE_BUNDLE_LOADER, resourceBundleLoader);

		return doRender(httpServletRequest, httpServletResponse);
	}

	protected Portal portal;

	@Reference(target = "(bundle.symbolic.name=com.liferay.saml.web)")
	protected ResourceBundleLoader resourceBundleLoader;

	protected SamlProviderConfigurationHelper samlProviderConfigurationHelper;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSamlMVCRenderCommand.class);

}