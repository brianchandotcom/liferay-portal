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
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.scope.liferay.ApplicationDescriptorLocator;
import com.liferay.oauth2.provider.scope.liferay.ScopeDescriptorLocator;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalService;
import com.liferay.oauth2.provider.service.OAuth2ApplicationService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationService;
import com.liferay.oauth2.provider.web.internal.AssignableScopes;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderPortletKeys;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderWebKeys;
import com.liferay.oauth2.provider.web.internal.display.context.OAuth2ConnectedApplicationsPortletDisplayContext;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @author Stian Sigvartsen
 */
@Component(
	property = {
		"javax.portlet.name=" + OAuth2ProviderPortletKeys.OAUTH2_CONNECTED_APPLICATIONS,
		"mvc.command.name=/", "mvc.command.name=/connected_applications/view"
	},
	service = MVCRenderCommand.class
)
public class ViewConnectedApplicationsMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<OAuth2Authorization> userOAuth2Authorizations =
			Collections.emptyList();

		try {
			userOAuth2Authorizations =
				_oAuth2AuthorizationService.getUserOAuth2Authorizations(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (PortalException pe) {
			_log.error("Unable to load user OAuth 2 authorizations", pe);
		}

		long oAuth2AuthorizationId = ParamUtil.getLong(
			renderRequest, "oAuth2AuthorizationId");

		if (!ListUtil.exists(
				userOAuth2Authorizations,
				userOAuth2Authorization ->
					userOAuth2Authorization.getOAuth2AuthorizationId() ==
						oAuth2AuthorizationId)) {

			OAuth2ConnectedApplicationsPortletDisplayContext
				oAuth2ConnectedApplicationsPortletDisplayContext =
					new OAuth2ConnectedApplicationsPortletDisplayContext(
						renderRequest, _dlurlHelper);

			renderRequest.setAttribute(
				OAuth2ProviderWebKeys.
					OAUTH2_CONNECTED_APPLICATIONS_PORTLET_DISPLAY_CONTEXT,
				oAuth2ConnectedApplicationsPortletDisplayContext);

			return "/connected_applications/view.jsp";
		}

		OAuth2Authorization oAuth2Authorization = null;

		for (OAuth2Authorization userOAuth2Authorization :
				userOAuth2Authorizations) {

			if (userOAuth2Authorization.getOAuth2AuthorizationId() ==
					oAuth2AuthorizationId) {

				oAuth2Authorization = userOAuth2Authorization;

				break;
			}
		}

		AssignableScopes assignableScopes = new AssignableScopes(
			_applicationDescriptorLocator, themeDisplay.getLocale(),
			_scopeDescriptorLocator);

		long oAuth2ApplicationScopeAliasesId =
			oAuth2Authorization.getOAuth2ApplicationScopeAliasesId();

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			_oAuth2ApplicationScopeAliasesLocalService.
				fetchOAuth2ApplicationScopeAliases(
					oAuth2ApplicationScopeAliasesId);

		if (oAuth2ApplicationScopeAliases != null) {
			for (String scopeAlias :
					oAuth2ApplicationScopeAliases.getScopeAliasesList()) {

				assignableScopes.addLiferayOAuth2Scopes(
					_scopeLocator.getLiferayOAuth2Scopes(
						themeDisplay.getCompanyId(), scopeAlias));
			}
		}

		OAuth2ConnectedApplicationsPortletDisplayContext
			oAuth2ConnectedApplicationsPortletDisplayContext =
				new OAuth2ConnectedApplicationsPortletDisplayContext(
					assignableScopes, renderRequest, _oAuth2ApplicationService,
					oAuth2Authorization, _dlurlHelper);

		renderRequest.setAttribute(
			OAuth2ProviderWebKeys.
				OAUTH2_CONNECTED_APPLICATIONS_PORTLET_DISPLAY_CONTEXT,
			oAuth2ConnectedApplicationsPortletDisplayContext);

		return "/connected_applications/view_application.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewConnectedApplicationsMVCRenderCommand.class);

	@Reference
	private ApplicationDescriptorLocator _applicationDescriptorLocator;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private OAuth2ApplicationScopeAliasesLocalService
		_oAuth2ApplicationScopeAliasesLocalService;

	@Reference
	private OAuth2ApplicationService _oAuth2ApplicationService;

	@Reference
	private OAuth2AuthorizationService _oAuth2AuthorizationService;

	@Reference
	private ScopeDescriptorLocator _scopeDescriptorLocator;

	@Reference
	private ScopeLocator _scopeLocator;

}