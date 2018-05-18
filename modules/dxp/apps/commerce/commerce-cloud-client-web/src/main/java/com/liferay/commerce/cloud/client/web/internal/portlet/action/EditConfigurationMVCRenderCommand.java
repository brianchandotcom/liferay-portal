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

package com.liferay.commerce.cloud.client.web.internal.portlet.action;

import com.liferay.commerce.cloud.client.constants.CommerceCloudClientConstants;
import com.liferay.commerce.cloud.client.util.CommerceCloudClient;
import com.liferay.commerce.cloud.client.web.internal.display.context.EditConfigurationDisplayContext;
import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"configurationPid=" + CommerceCloudClientConstants.CONFIGURATION_PID,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/edit_configuration"
	}
)
public class EditConfigurationMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			EditConfigurationDisplayContext editConfigurationDisplayContext =
				new EditConfigurationDisplayContext(
					_commerceCloudClient,
					_commerceCloudClientResourceBundleLoader,
					_commerceOrderResourceBundleLoader, _configurationProvider,
					httpServletRequest, _jsonFactory, _portal, renderRequest,
					renderResponse);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				editConfigurationDisplayContext);

			_jspRenderer.renderJSP(
				_servletContext, httpServletRequest, httpServletResponse,
				"/edit_configuration.jsp");

			return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
		}
		catch (Exception e) {
			throw new PortletException(
				"Unable to include edit_configuration.jsp", e);
		}
	}

	@Reference
	private CommerceCloudClient _commerceCloudClient;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.commerce.cloud.client.api)"
	)
	private ResourceBundleLoader _commerceCloudClientResourceBundleLoader;

	@Reference(target = "(bundle.symbolic.name=com.liferay.commerce.order.web)")
	private ResourceBundleLoader _commerceOrderResourceBundleLoader;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.cloud.client.web)"
	)
	private ServletContext _servletContext;

}