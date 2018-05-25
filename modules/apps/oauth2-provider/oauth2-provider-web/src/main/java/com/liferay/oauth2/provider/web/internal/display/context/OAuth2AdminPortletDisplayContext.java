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

import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalServiceUtil;
import com.liferay.oauth2.provider.service.OAuth2ApplicationService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2AdminPortletDisplayContext
	extends BaseOAuth2PortletDisplayContext {

	public static String generateRandomSecret() {
		int size = 16;

		int count = (int)Math.ceil((double)size / 8);

		byte[] buffer = new byte[count * 8];

		for (int i = 0; i < count; i++) {
			BigEndianCodec.putLong(buffer, i * 8, SecureRandomUtil.nextLong());
		}

		StringBundler sb = new StringBundler(size);

		for (int i = 0; i < size; i++) {
			sb.append(Integer.toHexString(0xFF & buffer[i]));
		}

		Matcher matcher = _baseIdPattern.matcher(sb.toString());

		return matcher.replaceFirst("secret-$1-$2-$3-$4-$5");
	}

	public OAuth2AdminPortletDisplayContext(
		OAuth2ApplicationService oAuth2ApplicationService,
		OAuth2ProviderConfiguration oAuth2ProviderConfiguration,
		PortletRequest portletRequest, ThemeDisplay themeDisplay) {

		_oAuth2ProviderConfiguration = oAuth2ProviderConfiguration;

		super.oAuth2ApplicationService = oAuth2ApplicationService;
		super.portletRequest = portletRequest;
		super.themeDisplay = themeDisplay;
	}

	public List<GrantType> getGrantTypes(
		PortletPreferences portletPreferences) {

		String[] oAuth2Grants = StringUtil.split(
			portletPreferences.getValue("oAuth2Grants", StringPool.BLANK));

		List<GrantType> result = new ArrayList<>();

		for (String oAuth2Grant : oAuth2Grants) {
			result.add(GrantType.valueOf(oAuth2Grant));
		}

		if (result.isEmpty()) {
			Collections.addAll(result, GrantType.values());
		}

		if (!_oAuth2ProviderConfiguration.allowAuthorizationCodeGrant()) {
			result.remove(GrantType.AUTHORIZATION_CODE);
		}

		if (!_oAuth2ProviderConfiguration.allowAuthorizationCodePKCEGrant()) {
			result.remove(GrantType.AUTHORIZATION_CODE_PKCE);
		}

		if (!_oAuth2ProviderConfiguration.allowClientCredentialsGrant()) {
			result.remove(GrantType.CLIENT_CREDENTIALS);
		}

		if (!_oAuth2ProviderConfiguration.allowRefreshTokenGrant()) {
			result.remove(GrantType.REFRESH_TOKEN);
		}

		if (!_oAuth2ProviderConfiguration.
				allowResourceOwnerPasswordCredentialsGrant()) {

			result.remove(GrantType.RESOURCE_OWNER_PASSWORD);
		}

		return result;
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

	public int getScopeAliasesSize(OAuth2Application oAuth2Application)
		throws PortalException {

		long oAuth2ApplicationScopeAliasesId =
			oAuth2Application.getOAuth2ApplicationScopeAliasesId();

		if (oAuth2ApplicationScopeAliasesId <= 0) {
			return 0;
		}

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			OAuth2ApplicationScopeAliasesLocalServiceUtil.
				getOAuth2ApplicationScopeAliases(
					oAuth2ApplicationScopeAliasesId);

		List<String> scopeAliasesList =
			oAuth2ApplicationScopeAliases.getScopeAliasesList();

		return scopeAliasesList.size();
	}

	private static final Pattern _baseIdPattern = Pattern.compile(
		"(.{8})(.{4})(.{4})(.{4})(.*)");

	private final OAuth2ProviderConfiguration _oAuth2ProviderConfiguration;

}