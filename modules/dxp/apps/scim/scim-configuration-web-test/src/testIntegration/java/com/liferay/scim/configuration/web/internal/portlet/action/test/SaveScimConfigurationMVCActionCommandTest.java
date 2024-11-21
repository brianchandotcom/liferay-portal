/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.scim.rest.util.ScimClientUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christian Moura
 */
@RunWith(Arquillian.class)
public class SaveScimConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOAuth2TokenIsIssuedToClientCredentialUser()
		throws Exception {

		Company company = _companyLocalService.getCompanyById(
			TestPropsValues.getCompanyId());

		User adminUser = UserTestUtil.getAdminUser(company.getCompanyId());

		_user = UserTestUtil.addCompanyAdminUser(company);

		Assert.assertNotEquals(adminUser, _user);

		String oAuth2ApplicationName = RandomTestUtil.randomString();

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.scim.rest.internal.configuration." +
				"ScimClientOAuth2ApplicationConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"matcherField", "email"
			).put(
				"oAuth2ApplicationName", oAuth2ApplicationName
			).put(
				"userId", _user.getUserId()
			).build());

		try {
			String scimClientId = ScimClientUtil.generateScimClientId(
				oAuth2ApplicationName);

			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.getOAuth2Application(
					TestPropsValues.getCompanyId(), scimClientId);

			Assert.assertEquals(
				_user.getUserId(),
				oAuth2Application.getClientCredentialUserId());

			MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
				new MockLiferayPortletActionRequest();

			mockLiferayPortletActionRequest.addParameter(
				Constants.CMD, "generate");
			mockLiferayPortletActionRequest.addParameter(
				"oAuth2ApplicationName", oAuth2ApplicationName);

			ThemeDisplay themeDisplay = new ThemeDisplay();

			themeDisplay.setCompany(company);
			themeDisplay.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_user));
			themeDisplay.setUser(_user);

			mockLiferayPortletActionRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			oAuth2Application.setClientCredentialUserId(adminUser.getUserId());

			oAuth2Application =
				_oAuth2ApplicationLocalService.updateOAuth2Application(
					oAuth2Application);

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			List<OAuth2Authorization> oAuth2Authorizations =
				_oAuth2AuthorizationLocalService.getOAuth2Authorizations(
					oAuth2Application.getOAuth2ApplicationId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				oAuth2Authorizations.toString(), 2,
				oAuth2Authorizations.size());

			OAuth2Authorization oAuth2Authorization = oAuth2Authorizations.get(
				0);

			Assert.assertEquals(
				_user.getUserId(), oAuth2Authorization.getUserId());

			oAuth2Authorization = oAuth2Authorizations.get(1);

			Assert.assertEquals(
				adminUser.getUserId(), oAuth2Authorization.getUserId());
		}
		finally {
			ConfigurationTestUtil.deleteConfiguration(pid);
		}
	}

	@DeleteAfterTestRun
	private static User _user;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "mvc.command.name=/scim_configuration/save_scim_configuration",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

}