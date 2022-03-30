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

package com.liferay.cookies.banner.web.internal.portlet;

import com.liferay.cookies.banner.web.internal.constants.CookiesBannerPortletKeys;
import com.liferay.cookies.banner.web.internal.constants.CookiesBannerWebKeys;
import com.liferay.cookies.banner.web.internal.display.context.CookiesBannerConfigurationDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-cookies-banner-configuration",
		"com.liferay.portlet.display-category=category.tools",
		"com.liferay.portlet.header-portlet-css=/cookies_banner_configuration/css/main.css",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.show-portlet-access-denied=false",
		"com.liferay.portlet.show-portlet-inactive=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Cookies Banner Configuration",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/cookies_banner_configuration/view.jsp",
		"javax.portlet.name=" + CookiesBannerPortletKeys.COOKIES_BANNER_CONFIGURATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CookiesBannerConfigurationPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CookiesBannerConfigurationDisplayContext
			cookiesBannerConfigurationDisplayContext =
				new CookiesBannerConfigurationDisplayContext(
					renderRequest, renderResponse);

		renderRequest.setAttribute(
			CookiesBannerWebKeys.COOKIES_BANNER_CONFIGURATION_DISPLAY_CONTEXT,
			cookiesBannerConfigurationDisplayContext);

		super.render(renderRequest, renderResponse);
	}

}