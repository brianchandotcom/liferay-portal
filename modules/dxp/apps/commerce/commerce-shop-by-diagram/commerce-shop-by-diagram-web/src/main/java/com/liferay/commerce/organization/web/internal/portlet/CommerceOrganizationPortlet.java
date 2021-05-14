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

package com.liferay.commerce.shop.by.diagram.web.internal.portlet;

import com.liferay.commerce.organization.constants.CommerceOrganizationPortletKeys;
import com.liferay.commerce.shop.by.diagram.web.internal.display.context.CommerceOrganizationDisplayContext;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocalCloseable;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.user.admin.configuration.UserFileUploadsConfiguration",
	enabled = false, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-organization",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=false",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Commerce Organization Management",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CommerceOrganizationPortletKeys.COMMERCE_ORGANIZATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = {CommerceOrganizationPortlet.class, Portlet.class}
)
public class CommerceOrganizationPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		try (ProxyModeThreadLocalCloseable proxyModeThreadLocalCloseable =
				new ProxyModeThreadLocalCloseable()) {

			ProxyModeThreadLocal.setForceSync(true);

			super.processAction(actionRequest, actionResponse);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CommerceOrganizationDisplayContext commerceOrganizationDisplayContext =
			new CommerceOrganizationDisplayContext(
				_portal.getHttpServletRequest(renderRequest),
				_organizationService, _userFileUploadsConfiguration,
				_userLocalService);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceOrganizationDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private Portal _portal;

	private volatile UserFileUploadsConfiguration _userFileUploadsConfiguration;

	@Reference
	private UserLocalService _userLocalService;

}