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

package com.liferay.commerce.user.web.internal.portlet;

import com.liferay.commerce.user.service.CommerceUserService;
import com.liferay.commerce.user.web.internal.constants.CommerceUserPortletKeys;
import com.liferay.commerce.user.web.internal.display.context.CommerceOrganizationDetailDisplayContext;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.Map;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	configurationPid = "com.liferay.user.admin.configuration.UserFileUploadsConfiguration",
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.autopropagated-parameters=currentOrganizationId",
		"com.liferay.portlet.autopropagated-parameters=currentUserId",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-user-admin",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.layout-cacheable=false",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Commerce Account Management",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CommerceUserPortletKeys.COMMERCE_ORGANIZATION_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {CommerceOrganizationPortlet.class, Portlet.class}
)
public class CommerceOrganizationPortlet extends MVCPortlet {

	@Override
	public void render(
				RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		HttpServletRequest httpServletRequest =
			_portal.getHttpServletRequest(renderRequest);

		CommerceOrganizationDetailDisplayContext commerceOrganizationDetailDisplayContext =
			new CommerceOrganizationDetailDisplayContext(
				httpServletRequest, _commerceUserService, _organizationService,
					_userFileUploadsConfiguration);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceOrganizationDetailDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

	@Reference
	private Portal _portal;

	@Reference
	private CommerceUserService _commerceUserService;

	@Reference
	private OrganizationService _organizationService;

	private volatile UserFileUploadsConfiguration _userFileUploadsConfiguration;
}