/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.shortcut.internal.upgrade.v1_0_0;

import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.constants.OAuth2ProviderActionKeys;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.shortcut.internal.constants.OAuth2ProviderShortcutConstants;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.oauth2.provider.util.builder.OAuth2ScopeBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.InputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Nilton Vieira
 */
public class OAuth2ApplicationAnalyticsCloudUpgradeProcess
	extends UpgradeProcess {

	public OAuth2ApplicationAnalyticsCloudUpgradeProcess(
		CompanyLocalService companyLocalService,
		OAuth2ApplicationLocalService oAuth2ApplicationLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService, UserLocalService userLocalService) {

		_companyLocalService = companyLocalService;
		_oAuth2ApplicationLocalService = oAuth2ApplicationLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> _upgradeCompany(companyId));
	}

	private void _buildAnalyticsCloudScopes(OAuth2ScopeBuilder builder) {
		builder.forApplication(
			OAuth2ProviderShortcutConstants.APPLICATION_NAME,
			"com.liferay.oauth2.provider.shortcut",
			applicationScopeAssigner -> _scopeAliasesList.forEach(
				applicationScopeAssigner::assignScope));
	}

	private void _buildScopes(OAuth2ScopeBuilder builder) {
		_buildAnalyticsCloudScopes(builder);

		_buildSegmentsAsahScopes(builder);
	}

	private void _buildSegmentsAsahScopes(OAuth2ScopeBuilder builder) {
		builder.forApplication(
			"Liferay.Segments.Asah.REST", "com.liferay.segments.asah.rest.impl",
			applicationScopeAssigner -> applicationScopeAssigner.assignScope(
				"DELETE", "GET", "POST"
			).mapToScopeAlias(
				"Liferay.Segments.Asah.REST.everything"
			));
	}

	private void _upgradeCompany(long companyId) throws Exception {
		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					"ANALYTICS-CLOUD", companyId);

		if (oAuth2Application != null) {
			return;
		}

		User user = _userLocalService.getGuestUser(companyId);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select oAuth2ApplicationId from OAuth2Application where ",
					"userId = ? and allowedGrantTypes = ? and ",
					"clientAuthenticationMethod = ? and ",
					"clientCredentialUserId = ? and clientProfile = ? and ",
					"homePageURL = ? and redirectURIs = ?"))) {

			preparedStatement.setLong(1, user.getUserId());
			preparedStatement.setString(2, "AUTHORIZATION_CODE,REFRESH_TOKEN");
			preparedStatement.setString(3, "client_secret_post");
			preparedStatement.setLong(4, user.getUserId());
			preparedStatement.setLong(5, ClientProfile.WEB_APPLICATION.id());
			preparedStatement.setString(6, "https://analytics.liferay.com");
			preparedStatement.setString(
				7, "https://analytics.liferay.com/oauth/receive");

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				_oAuth2ApplicationLocalService.deleteOAuth2Application(
					resultSet.getLong(1));
			}
		}

		user = _userLocalService.getUserByScreenName(
			companyId, UserConstants.SCREEN_NAME_DEFAULT_SERVICE_ACCOUNT);

		oAuth2Application =
			_oAuth2ApplicationLocalService.addOrUpdateOAuth2Application(
				"ANALYTICS-CLOUD", user.getUserId(), user.getScreenName(),
				new ArrayList<GrantType>() {
					{
						add(GrantType.CLIENT_CREDENTIALS);
						add(GrantType.JWT_BEARER);
					}
				},
				"client_secret_post", user.getUserId(),
				OAuth2SecureRandomGenerator.generateClientId(),
				ClientProfile.HEADLESS_SERVER.id(),
				OAuth2SecureRandomGenerator.generateClientSecret(), null, null,
				"https://analytics.liferay.com", 0, null, "Analytics Cloud",
				null,
				Collections.singletonList(
					"https://analytics.liferay.com/oauth/receive"),
				false, false, this::_buildScopes, new ServiceContext());

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/logo.png");

		oAuth2Application = _oAuth2ApplicationLocalService.updateIcon(
			oAuth2Application.getOAuth2ApplicationId(), inputStream);

		Role role = _roleLocalService.fetchRole(
			oAuth2Application.getCompanyId(),
			RoleConstants.ANALYTICS_ADMINISTRATOR);

		if (role == null) {
			return;
		}

		_resourcePermissionLocalService.setResourcePermissions(
			oAuth2Application.getCompanyId(), OAuth2Application.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(oAuth2Application.getPrimaryKey()), role.getRoleId(),
			new String[] {
				ActionKeys.VIEW, OAuth2ProviderActionKeys.CREATE_TOKEN
			});
	}

	private final CompanyLocalService _companyLocalService;
	private final OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final List<String> _scopeAliasesList = ListUtil.fromArray(
		"analytics.read", "analytics.write");
	private final UserLocalService _userLocalService;

}