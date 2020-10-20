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

package com.liferay.commerce.health.status.web.internal.portlet;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceHealthStatusConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.health.status.CommerceHealthHttpStatusRegistry;
import com.liferay.commerce.health.status.web.internal.display.context.CommerceHealthStatusDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-health-check",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Health Check",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_HEALTH_CHECK,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = {CommerceHealthCheckPortlet.class, Portlet.class}
)
public class CommerceHealthCheckPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CommerceHealthStatusDisplayContext commerceHealthStatusDisplayContext =
			new CommerceHealthStatusDisplayContext(
				_commerceHealthHttpStatusRegistry, _portletResourcePermission,
				renderRequest, renderResponse,
				CommerceHealthStatusConstants.
					COMMERCE_HEALTH_STATUS_TYPE_VIRTUAL_INSTANCE);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceHealthStatusDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private CommerceHealthHttpStatusRegistry _commerceHealthHttpStatusRegistry;

	@Reference(
		target = "(resource.name=" + CommerceConstants.HEALTH_RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}