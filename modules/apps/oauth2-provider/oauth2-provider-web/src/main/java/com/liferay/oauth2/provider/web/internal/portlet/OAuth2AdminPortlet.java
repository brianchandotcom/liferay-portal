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

package com.liferay.oauth2.provider.web.internal.portlet;

import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.service.OAuth2ApplicationService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationService;
import com.liferay.oauth2.provider.web.constants.OAuth2ProviderPortletKeys;
import com.liferay.oauth2.provider.web.constants.OAuth2ProviderWebKeys;
import com.liferay.oauth2.provider.web.internal.display.context.OAuth2AdminPortletDisplayContext;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-oauth2-provider-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.preferences-company-wide=true",
		"javax.portlet.display-name=OAuth2 Admin",
		"javax.portlet.init-param.portlet-title-based-navigation=true",
		"javax.portlet.init-param.template-path=/admin/",
		"javax.portlet.init-param.view-template=/admin/view.jsp",
		"javax.portlet.name=" + OAuth2ProviderPortletKeys.OAUTH2_ADMIN,
		"javax.portlet.preferences=classpath:/META-INF/portlet-preferences/default-portlet-preferences.xml",
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class OAuth2AdminPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		OAuth2AdminPortletDisplayContext oAuth2AdminPortletDisplayContext =
			new OAuth2AdminPortletDisplayContext(
				_oAuth2ApplicationService, _oAuth2ProviderConfiguration,
				renderRequest, getThemeDisplay(renderRequest));

		renderRequest.setAttribute(
			OAuth2ProviderWebKeys.OAUTH2_ADMIN_PORTLET_DISPLAY_CONTEXT,
			oAuth2AdminPortletDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_oAuth2ProviderConfiguration = ConfigurableUtil.createConfigurable(
			OAuth2ProviderConfiguration.class, properties);
	}

	protected ThemeDisplay getThemeDisplay(PortletRequest portletRequest) {
		return (ThemeDisplay)portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2AdminPortlet.class);

	@Reference
	private OAuth2ApplicationService _oAuth2ApplicationService;

	@Reference
	private OAuth2AuthorizationService _oAuth2AuthorizationService;

	private OAuth2ProviderConfiguration _oAuth2ProviderConfiguration;

}