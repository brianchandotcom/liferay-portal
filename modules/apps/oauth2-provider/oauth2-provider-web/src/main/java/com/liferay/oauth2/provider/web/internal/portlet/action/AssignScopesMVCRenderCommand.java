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

package com.liferay.oauth2.provider.web.internal.portlet.action;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.scope.liferay.ApplicationDescriptorLocator;
import com.liferay.oauth2.provider.scope.liferay.ScopeDescriptorLocator;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.service.OAuth2ApplicationService;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderPortletKeys;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderWebKeys;
import com.liferay.oauth2.provider.web.internal.display.context.AssignScopesDisplayContext;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = {
		"javax.portlet.name=" + OAuth2ProviderPortletKeys.OAUTH2_ADMIN,
		"mvc.command.name=/admin/assign_scopes"
	},
	service = MVCRenderCommand.class
)
public class AssignScopesMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		AssignScopesDisplayContext assignScopesDisplayContext =
			new AssignScopesDisplayContext(
				_oAuth2ApplicationService, _oAuth2ProviderConfiguration,
				renderRequest, getThemeDisplay(renderRequest),
				_applicationDescriptorLocator, _scopeDescriptorLocator,
				_scopeLocator, _dlurlHelper);

		renderRequest.setAttribute(
			OAuth2ProviderWebKeys.OAUTH2_ADMIN_PORTLET_DISPLAY_CONTEXT,
			assignScopesDisplayContext);

		return "/admin/edit_application.jsp";
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_oAuth2ProviderConfiguration = ConfigurableUtil.createConfigurable(
			OAuth2ProviderConfiguration.class, properties);
	}

	protected ThemeDisplay getThemeDisplay(PortletRequest portletRequest) {
		return (ThemeDisplay)portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	@Reference
	private ApplicationDescriptorLocator _applicationDescriptorLocator;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private OAuth2ApplicationService _oAuth2ApplicationService;

	private OAuth2ProviderConfiguration _oAuth2ProviderConfiguration;

	@Reference
	private ScopeDescriptorLocator _scopeDescriptorLocator;

	@Reference
	private ScopeLocator _scopeLocator;

}