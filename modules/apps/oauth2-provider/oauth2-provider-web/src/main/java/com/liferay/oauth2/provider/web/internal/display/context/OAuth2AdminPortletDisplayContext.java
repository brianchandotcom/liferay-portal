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

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalService;
import com.liferay.oauth2.provider.service.OAuth2ApplicationService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2AdminPortletDisplayContext
	extends BaseOAuth2PortletDisplayContext {

	public OAuth2AdminPortletDisplayContext(
		OAuth2ApplicationService oAuth2ApplicationService,
		OAuth2ApplicationScopeAliasesLocalService
			oAuth2ApplicationScopeAliasesLocalService,
		OAuth2ProviderConfiguration oAuth2ProviderConfiguration,
		PortletRequest portletRequest, ThemeDisplay themeDisplay,
		DLURLHelper dlURLHelper) {

		_oAuth2ProviderConfiguration = oAuth2ProviderConfiguration;

		super.oAuth2ApplicationService = oAuth2ApplicationService;
		super.portletRequest = portletRequest;
		super.themeDisplay = themeDisplay;
		super.dlURLHelper = dlURLHelper;

		this.oAuth2ApplicationScopeAliasesLocalService =
			oAuth2ApplicationScopeAliasesLocalService;
	}

	public List<GrantType> getGrantTypes(
		PortletPreferences portletPreferences) {

		String[] oAuth2Grants = StringUtil.split(
			portletPreferences.getValue("oAuth2Grants", StringPool.BLANK));

		List<GrantType> grantTypes = new ArrayList<>();

		for (String oAuth2Grant : oAuth2Grants) {
			grantTypes.add(GrantType.valueOf(oAuth2Grant));
		}

		if (grantTypes.isEmpty()) {
			Collections.addAll(grantTypes, GrantType.values());
		}

		if (!_oAuth2ProviderConfiguration.allowAuthorizationCodeGrant()) {
			grantTypes.remove(GrantType.AUTHORIZATION_CODE);
		}

		if (!_oAuth2ProviderConfiguration.allowAuthorizationCodePKCEGrant()) {
			grantTypes.remove(GrantType.AUTHORIZATION_CODE_PKCE);
		}

		if (!_oAuth2ProviderConfiguration.allowClientCredentialsGrant()) {
			grantTypes.remove(GrantType.CLIENT_CREDENTIALS);
		}

		if (!_oAuth2ProviderConfiguration.allowRefreshTokenGrant()) {
			grantTypes.remove(GrantType.REFRESH_TOKEN);
		}

		if (!_oAuth2ProviderConfiguration.
				allowResourceOwnerPasswordCredentialsGrant()) {

			grantTypes.remove(GrantType.RESOURCE_OWNER_PASSWORD);
		}

		return grantTypes;
	}

	public int getOAuth2AuthorizationsCount(OAuth2Application oAuth2Application)
		throws PortalException {

		return OAuth2AuthorizationServiceUtil.
			getApplicationOAuth2AuthorizationsCount(
				oAuth2Application.getOAuth2ApplicationId());
	}

	public String[] getOAuth2Features(PortletPreferences portletPreferences) {
		return StringUtil.split(
			portletPreferences.getValue("oAuth2Features", StringPool.BLANK));
	}

	public ClientProfile[] getSortedClientProfiles() {
		ClientProfile[] clientProfiles = ClientProfile.values();

		Arrays.sort(clientProfiles, Comparator.comparingInt(ClientProfile::id));

		return clientProfiles;
	}

	protected final OAuth2ApplicationScopeAliasesLocalService
		oAuth2ApplicationScopeAliasesLocalService;

	private final OAuth2ProviderConfiguration _oAuth2ProviderConfiguration;

}