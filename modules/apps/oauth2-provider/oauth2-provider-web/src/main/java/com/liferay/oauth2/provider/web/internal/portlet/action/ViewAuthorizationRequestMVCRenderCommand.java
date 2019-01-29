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
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.scope.liferay.ApplicationDescriptorLocator;
import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;
import com.liferay.oauth2.provider.scope.liferay.ScopeDescriptorLocator;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalService;
import com.liferay.oauth2.provider.service.OAuth2ApplicationService;
import com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalService;
import com.liferay.oauth2.provider.web.internal.AssignableScopes;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderPortletKeys;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderWebKeys;
import com.liferay.oauth2.provider.web.internal.display.context.OAuth2AuthorizePortletDisplayContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"javax.portlet.name=" + OAuth2ProviderPortletKeys.OAUTH2_AUTHORIZE,
		"mvc.command.name=/",
		"mvc.command.name=/authorize/view_authorization_request"
	},
	service = MVCRenderCommand.class
)
public class ViewAuthorizationRequestMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest request = _portal.getOriginalServletRequest(
			_portal.getHttpServletRequest(renderRequest));

		Map<String, String> oAuth2Parameters = getOAuth2Parameters(request);

		String error = oAuth2Parameters.get("error");

		if (StringUtil.equals(error, "invalid_client")) {
			SessionErrors.add(renderRequest, "clientIdInvalid");

			return "/authorize/error.jsp";
		}

		String redirectURI = oAuth2Parameters.get("redirect_uri");

		if (Validator.isBlank(redirectURI)) {
			SessionErrors.add(renderRequest, "redirectURIMissing");

			return "/authorize/error.jsp";
		}

		String clientId = oAuth2Parameters.get("client_id");

		try {
			OAuth2Application oAuth2Application =
				_oAuth2ApplicationService.getOAuth2Application(
					themeDisplay.getCompanyId(), clientId);
			OAuth2AuthorizePortletDisplayContext
				oAuth2AuthorizePortletDisplayContext =
					new OAuth2AuthorizePortletDisplayContext(
						themeDisplay, _dlurlHelper);

			oAuth2AuthorizePortletDisplayContext.setOAuth2Application(
				oAuth2Application);
			oAuth2AuthorizePortletDisplayContext.setOAuth2Parameters(
				oAuth2Parameters);

			AssignableScopes assignableScopes = new AssignableScopes(
				_applicationDescriptorLocator, themeDisplay.getLocale(),
				_scopeDescriptorLocator);

			if (oAuth2Application.getOAuth2ApplicationScopeAliasesId() > 0) {
				OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
					_oAuth2ApplicationScopeAliasesLocalService.
						getOAuth2ApplicationScopeAliases(
							oAuth2Application.
								getOAuth2ApplicationScopeAliasesId());

				String[] requestedScopeAliases = StringUtil.split(
					oAuth2Parameters.get("scope"), StringPool.SPACE);

				populateAssignableScopes(
					assignableScopes, oAuth2ApplicationScopeAliases,
					requestedScopeAliases);
			}

			oAuth2AuthorizePortletDisplayContext.setAssignableScopes(
				assignableScopes);

			renderRequest.setAttribute(
				OAuth2ProviderWebKeys.OAUTH2_AUTHORIZE_PORTLET_DISPLAY_CONTEXT,
				oAuth2AuthorizePortletDisplayContext);
		}
		catch (PortalException pe) {
			throw new PortletException(pe);
		}

		return "/authorize/authorize.jsp";
	}

	protected Map<String, String> getOAuth2Parameters(
		HttpServletRequest request) {

		Map<String, String> oAuth2Parameters = new HashMap<>();

		Enumeration<String> names = request.getParameterNames();

		while (names.hasMoreElements()) {
			String name = names.nextElement();

			if (name.startsWith("oauth2_")) {
				oAuth2Parameters.put(
					name.substring("oauth2_".length()),
					ParamUtil.getString(request, name));
			}
		}

		return oAuth2Parameters;
	}

	protected void populateAssignableScopes(
		AssignableScopes assignableScopes,
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases,
		String[] requestedScopeAliases) {

		Collection<LiferayOAuth2Scope> liferayOAuth2Scopes = new HashSet<>();

		List<String> scopeAliasesList =
			oAuth2ApplicationScopeAliases.getScopeAliasesList();

		for (String requestedScopeAlias : requestedScopeAliases) {
			if (!scopeAliasesList.contains(requestedScopeAlias)) {
				continue;
			}

			liferayOAuth2Scopes.addAll(
				_scopeFinderLocator.getLiferayOAuth2Scopes(
					oAuth2ApplicationScopeAliases.getCompanyId(),
					requestedScopeAlias));
		}

		liferayOAuth2Scopes =
			_oAuth2ScopeGrantLocalService.getFilteredLiferayOAuth2Scopes(
				oAuth2ApplicationScopeAliases.
					getOAuth2ApplicationScopeAliasesId(),
				liferayOAuth2Scopes);

		assignableScopes.addLiferayOAuth2Scopes(liferayOAuth2Scopes);
	}

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
	private OAuth2ScopeGrantLocalService _oAuth2ScopeGrantLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ScopeDescriptorLocator _scopeDescriptorLocator;

	@Reference
	private ScopeLocator _scopeFinderLocator;

}