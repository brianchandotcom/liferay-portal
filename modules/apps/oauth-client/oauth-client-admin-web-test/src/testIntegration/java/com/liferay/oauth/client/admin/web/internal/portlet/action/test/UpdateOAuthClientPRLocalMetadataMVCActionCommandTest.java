/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.exception.OAuthClientPRLocalMetadataMetadataJSONException;
import com.liferay.oauth.client.persistence.exception.OAuthClientPRLocalMetadataProtectedResourceURIException;
import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.portlet.MockPortletSession;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jorge García Jiménez
 */
@FeatureFlag("LPD-63415")
@RunWith(Arquillian.class)
public class UpdateOAuthClientPRLocalMetadataMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_user = UserTestUtil.addUser(_company);
	}

	@After
	public void tearDown() throws Exception {
		for (OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata :
				_oAuthClientPRLocalMetadataLocalService.
					getCompanyOAuthClientPRLocalMetadata(
						TestPropsValues.getCompanyId())) {

			_oAuthClientPRLocalMetadataLocalService.
				deleteOAuthClientPRLocalMetadata(
					oAuthClientPRLocalMetadata.
						getOAuthClientPRLocalMetadataId());
		}
	}

	@Test
	public void testProcessAction() throws Exception {
		_testProcessAction(
			OAuthClientPRLocalMetadataProtectedResourceURIException.class,
			HashMapBuilder.put(
				"protectedResourceURI",
				new String[] {RandomTestUtil.randomString()}
			).build());

		String protectedResourceURI1 =
			Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString() + ".com";

		_testProcessAction(
			OAuthClientPRLocalMetadataMetadataJSONException.class,
			HashMapBuilder.put(
				"protectedResourceURI", new String[] {protectedResourceURI1}
			).build());
		_testProcessAction(
			OAuthClientPRLocalMetadataMetadataJSONException.class,
			HashMapBuilder.put(
				"authorizationServers",
				new String[] {RandomTestUtil.randomString()}
			).put(
				"protectedResourceURI", new String[] {protectedResourceURI1}
			).build());

		List<OAuthClientPRLocalMetadata> oAuthClientPRLocalMetadatas =
			_oAuthClientPRLocalMetadataLocalService.
				getCompanyOAuthClientPRLocalMetadata(_user.getCompanyId());

		int count = oAuthClientPRLocalMetadatas.size();

		String authorizationServer1 =
			Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString() + ".com";

		Assert.assertTrue(
			_updateMVCActionCommand.processAction(
				_getMockLiferayPortletActionRequest(
					HashMapBuilder.put(
						"authorizationServers",
						new String[] {authorizationServer1}
					).put(
						"protectedResourceURI",
						new String[] {protectedResourceURI1}
					).put(
						"resourceName",
						new String[] {RandomTestUtil.randomString()}
					).put(
						"scopesSupported",
						new String[] {RandomTestUtil.randomString()}
					).build()),
				new MockLiferayPortletActionResponse()));

		oAuthClientPRLocalMetadatas =
			_oAuthClientPRLocalMetadataLocalService.
				getCompanyOAuthClientPRLocalMetadata(_user.getCompanyId());

		Assert.assertEquals(
			oAuthClientPRLocalMetadatas.toString(), count + 1,
			oAuthClientPRLocalMetadatas.size());

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadatas.get(0);

		Assert.assertEquals(
			protectedResourceURI1,
			oAuthClientPRLocalMetadata.getProtectedResourceURI());

		long oAuthClientPRLocalMetadataId =
			oAuthClientPRLocalMetadata.getOAuthClientPRLocalMetadataId();

		String authorizationServer2 =
			Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString() + ".com";
		String protectedResourceURI2 =
			Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString() + ".com";
		String resourceName = RandomTestUtil.randomString();
		String scope = RandomTestUtil.randomString();

		Assert.assertTrue(
			_updateMVCActionCommand.processAction(
				_getMockLiferayPortletActionRequest(
					HashMapBuilder.put(
						"authorizationServers",
						new String[] {authorizationServer2}
					).put(
						"bearerMethodsSupported", new String[] {""}
					).put(
						"localWellKnownEnabled", new String[] {"true"}
					).put(
						"oAuthClientPRLocalMetadataId",
						new String[] {
							String.valueOf(oAuthClientPRLocalMetadataId)
						}
					).put(
						"protectedResourceURI",
						new String[] {protectedResourceURI2}
					).put(
						"resourceName", new String[] {resourceName}
					).put(
						"scopesSupported", new String[] {scope}
					).build()),
				new MockLiferayPortletActionResponse()));

		oAuthClientPRLocalMetadata =
			_oAuthClientPRLocalMetadataLocalService.
				getOAuthClientPRLocalMetadata(oAuthClientPRLocalMetadataId);

		Assert.assertEquals(
			protectedResourceURI2 + "/.well-known/oauth-protected-resource",
			oAuthClientPRLocalMetadata.getLocalWellKnownURI());
		Assert.assertEquals(
			protectedResourceURI2,
			oAuthClientPRLocalMetadata.getProtectedResourceURI());
		Assert.assertTrue(oAuthClientPRLocalMetadata.isLocalWellKnownEnabled());

		JSONObject metadataJSONObject = JSONFactoryUtil.createJSONObject(
			oAuthClientPRLocalMetadata.getMetadataJSON());

		Assert.assertEquals(
			protectedResourceURI2, metadataJSONObject.getString("resource"));
		Assert.assertEquals(
			resourceName, metadataJSONObject.getString("resource_name"));

		JSONArray authorizationServersJSONArray =
			metadataJSONObject.getJSONArray("authorization_servers");

		Assert.assertEquals(
			authorizationServer2, authorizationServersJSONArray.getString(0));

		JSONArray bearerMethodsSupportedJSONArray =
			metadataJSONObject.getJSONArray("bearer_methods_supported");

		Assert.assertEquals(
			"header", bearerMethodsSupportedJSONArray.getString(0));

		JSONArray scopesSupportedJSONArray = metadataJSONObject.getJSONArray(
			"scopes_supported");

		Assert.assertEquals(scope, scopesSupportedJSONArray.getString(0));
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Map<String, String[]> parameters)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			mockLiferayPortletActionRequest.setParameter(
				entry.getKey(), entry.getValue());
		}

		mockLiferayPortletActionRequest.setPortletSession(
			new MockPortletSession());

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRequest(new MockHttpServletRequest());
		themeDisplay.setUser(_user);

		return themeDisplay;
	}

	private void _testProcessAction(
			Class<? extends PortalException> clazz,
			Map<String, String[]> parameters)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(parameters);

		Assert.assertFalse(
			_updateMVCActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));
		Assert.assertTrue(
			SessionErrors.contains(mockLiferayPortletActionRequest, clazz));
	}

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static User _user;

	@Inject
	private OAuthClientPRLocalMetadataLocalService
		_oAuthClientPRLocalMetadataLocalService;

	@Inject(
		filter = "mvc.command.name=/oauth_client_admin/update_oauth_client_pr_local_metadata"
	)
	private MVCActionCommand _updateMVCActionCommand;

}