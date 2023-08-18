/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.shortcut.internal.instance.lifecycle;

import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2ApplicationTable;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class RemotePublicationsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					OAuth2ApplicationTable.INSTANCE
				).from(
					OAuth2ApplicationTable.INSTANCE
				).where(
					OAuth2ApplicationTable.INSTANCE.companyId.eq(
						company.getCompanyId()
					).and(
						OAuth2ApplicationTable.INSTANCE.name.eq(
							_APPLICATION_NAME)
					)
				));

		if (!oAuth2Applications.isEmpty()) {
			return;
		}

		User user = _userLocalService.fetchUserByScreenName(
			company.getCompanyId(), _SCREEN_NAME);

		if (user == null) {
			Role role = _roleLocalService.getRole(
				company.getCompanyId(), RoleConstants.PUBLICATIONS_USER);

			user = _userLocalService.addUser(
				UserConstants.USER_ID_DEFAULT, company.getCompanyId(), true,
				null, null, false, _SCREEN_NAME,
				_SCREEN_NAME + StringPool.AT + company.getMx(),
				LocaleUtil.fromLanguageId(PropsValues.COMPANY_DEFAULT_LOCALE),
				"Publications", StringPool.BLANK, "Service Account", 0, 0, true,
				Calendar.JANUARY, 1, 1970, StringPool.BLANK,
				UserConstants.TYPE_SERVICE_ACCOUNT, null, null,
				new long[] {role.getRoleId()}, null, false,
				new ServiceContext());

			user.setEmailAddressVerified(true);

			user = _userLocalService.updateUser(user);
		}

		_oAuth2ApplicationLocalService.addOAuth2Application(
			company.getCompanyId(), user.getUserId(), user.getScreenName(),
			new ArrayList<GrantType>() {
				{
					add(GrantType.CLIENT_CREDENTIALS);
					add(GrantType.JWT_BEARER);
				}
			},
			"client_secret_post", user.getUserId(),
			OAuth2SecureRandomGenerator.generateClientId(),
			ClientProfile.HEADLESS_SERVER.id(),
			OAuth2SecureRandomGenerator.generateClientSecret(), null,
			new ArrayList<String>() {
				{
					add("token.introspection");
				}
			},
			null, 0, null, _APPLICATION_NAME, null, null, false, false,
			builder -> builder.forApplication(
				"Liferay.Change.Tracking.REST",
				"com.liferay.change.tracking.rest.impl",
				applicationScopeAssigner ->
					applicationScopeAssigner.assignScope(
						"DELETE", "GET", "PATCH", "POST", "PUT"
					).mapToScopeAlias(
						"Liferay.Change.Tracking.REST.everything"
					)),
			new ServiceContext());
	}

	private static final String _APPLICATION_NAME =
		"Remote Publications Headless Server";

	private static final String _SCREEN_NAME = "publications-service-account";

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}