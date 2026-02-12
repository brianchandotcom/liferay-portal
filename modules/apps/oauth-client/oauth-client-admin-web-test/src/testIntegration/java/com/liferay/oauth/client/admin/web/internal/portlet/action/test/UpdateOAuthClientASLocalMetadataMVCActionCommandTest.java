/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.exception.OAuthClientASLocalMetadataLocalWellKnownURIException;
import com.liferay.oauth.client.persistence.exception.OAuthClientASLocalMetadataMetadataJSONException;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.portlet.MockPortletSession;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import com.nimbusds.oauth2.sdk.GrantType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.as.AuthorizationServerMetadata;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.SubjectType;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.net.URI;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Alvaro Saugar
 */
@FeatureFlag("LPD-63415")
@RunWith(Arquillian.class)
public class UpdateOAuthClientASLocalMetadataMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);
	}

	@AfterClass
	public static void tearDownClass() throws PortalException {
		_companyLocalService.deleteCompany(_company);
	}

	@Test
	public void testCreateAndUpdateOAuthClientASLocalMetadata()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(
				HashMapBuilder.put(
					"authorization_endpoint",
					new String[] {RandomTestUtil.randomString()}
				).build());

		Assert.assertFalse(
			_editMVCActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));

		Assert.assertTrue(
			SessionErrors.contains(
				mockLiferayPortletActionRequest,
				OAuthClientASLocalMetadataLocalWellKnownURIException.class));

		mockLiferayPortletActionRequest = _getMockLiferayPortletActionRequest(
			HashMapBuilder.put(
				"issuer", new String[] {RandomTestUtil.randomString()}
			).build());

		Assert.assertFalse(
			_editMVCActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));

		Assert.assertTrue(
			SessionErrors.contains(
				mockLiferayPortletActionRequest,
				OAuthClientASLocalMetadataLocalWellKnownURIException.class));

		mockLiferayPortletActionRequest = _getMockLiferayPortletActionRequest(
			HashMapBuilder.put(
				"jwks_uri", new String[] {RandomTestUtil.randomString()}
			).build());

		Assert.assertFalse(
			_editMVCActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));

		Assert.assertTrue(
			SessionErrors.contains(
				mockLiferayPortletActionRequest,
				OAuthClientASLocalMetadataLocalWellKnownURIException.class));

		mockLiferayPortletActionRequest = _getMockLiferayPortletActionRequest(
			HashMapBuilder.put(
				"token_endpoint", new String[] {RandomTestUtil.randomString()}
			).build());

		Assert.assertFalse(
			_editMVCActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));

		Assert.assertTrue(
			SessionErrors.contains(
				mockLiferayPortletActionRequest,
				OAuthClientASLocalMetadataLocalWellKnownURIException.class));

		mockLiferayPortletActionRequest = _getMockLiferayPortletActionRequest(
			HashMapBuilder.put(
				"userinfo_endpoint",
				new String[] {RandomTestUtil.randomString()}
			).build());

		Assert.assertFalse(
			_editMVCActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));

		Assert.assertTrue(
			SessionErrors.contains(
				mockLiferayPortletActionRequest,
				OAuthClientASLocalMetadataLocalWellKnownURIException.class));

		String issuer =
			Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString() + ".com";

		mockLiferayPortletActionRequest = _getMockLiferayPortletActionRequest(
			HashMapBuilder.put(
				"issuer", new String[] {issuer}
			).build());

		Assert.assertFalse(
			_editMVCActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));

		Assert.assertTrue(
			SessionErrors.contains(
				mockLiferayPortletActionRequest,
				OAuthClientASLocalMetadataMetadataJSONException.class));

		User user = _userLocalService.getUser(GuestOrUserUtil.getUserId());

		List<OAuthClientASLocalMetadata> oAuthClientASLocalMetadatas =
			_oAuthClientASLocalMetadataLocalService.
				getCompanyOAuthClientASLocalMetadata(user.getCompanyId());

		int numberOfOAuthClientASLocalMetadata =
			oAuthClientASLocalMetadatas.size();

		Assert.assertTrue(
			_editMVCActionCommand.processAction(
				_getMockLiferayPortletActionRequest(
					HashMapBuilder.put(
						"issuer", new String[] {issuer}
					).put(
						"supported_subject_types", new String[] {"public"}
					).build()),
				new MockLiferayPortletActionResponse()));

		oAuthClientASLocalMetadatas =
			_oAuthClientASLocalMetadataLocalService.
				getCompanyOAuthClientASLocalMetadata(user.getCompanyId());

		Assert.assertTrue(
			(numberOfOAuthClientASLocalMetadata + 1) ==
				oAuthClientASLocalMetadatas.size());

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadatas.get(0);

		Assert.assertEquals(issuer, oAuthClientASLocalMetadata.getIssuer());

		long oAuthClientASLocalMetadataId =
			oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId();

		mockLiferayPortletActionRequest = _getMockLiferayPortletActionRequest(
			HashMapBuilder.put(
				"issuer", new String[] {RandomTestUtil.randomString()}
			).put(
				"oAuthClientASLocalMetadataId",
				new String[] {String.valueOf(oAuthClientASLocalMetadataId)}
			).build());

		Assert.assertFalse(
			_editMVCActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));

		Assert.assertTrue(
			SessionErrors.contains(
				mockLiferayPortletActionRequest,
				OAuthClientASLocalMetadataLocalWellKnownURIException.class));

		oAuthClientASLocalMetadata =
			_oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(oAuthClientASLocalMetadataId);

		Assert.assertEquals(issuer, oAuthClientASLocalMetadata.getIssuer());

		String okURL =
			Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString() + ".com";

		String supported = RandomTestUtil.randomString();

		Assert.assertTrue(
			_editMVCActionCommand.processAction(
				_getMockLiferayPortletActionRequest(
					HashMapBuilder.put(
						"authorization_endpoint", new String[] {okURL}
					).put(
						"enabled", new String[] {"true"}
					).put(
						"issuer", new String[] {okURL}
					).put(
						"jwks_uri", new String[] {okURL}
					).put(
						"oAuthClientASLocalMetadataId",
						new String[] {
							String.valueOf(oAuthClientASLocalMetadataId)
						}
					).put(
						"supported-grant-types", new String[] {supported}
					).put(
						"supported-scopes", new String[] {supported}
					).put(
						"supported_subject_types", new String[] {"public"}
					).put(
						"token_endpoint", new String[] {okURL}
					).put(
						"userinfo_endpoint", new String[] {okURL}
					).build()),
				new MockLiferayPortletActionResponse()));

		oAuthClientASLocalMetadata =
			_oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(oAuthClientASLocalMetadataId);

		Assert.assertEquals(okURL, oAuthClientASLocalMetadata.getIssuer());

		Assert.assertTrue(oAuthClientASLocalMetadata.isLocalWellKnownEnabled());

		Assert.assertEquals(
			okURL + "/o/.well-known/oauth-authorization-server",
			oAuthClientASLocalMetadata.getOAuthASLocalWellKnownURI());

		OIDCProviderMetadata oidcProviderMetadata = OIDCProviderMetadata.parse(
			oAuthClientASLocalMetadata.getMetadataJSON());

		Assert.assertEquals(
			Issuer.parse(okURL), oidcProviderMetadata.getIssuer());
		Assert.assertEquals(
			URI.create(okURL),
			oidcProviderMetadata.getAuthorizationEndpointURI());
		Assert.assertEquals(
			URI.create(okURL), oidcProviderMetadata.getTokenEndpointURI());
		Assert.assertEquals(
			URI.create(okURL), oidcProviderMetadata.getUserInfoEndpointURI());
		Assert.assertEquals(
			URI.create(okURL), oidcProviderMetadata.getJWKSetURI());

		List<SubjectType> subjectTypes = oidcProviderMetadata.getSubjectTypes();

		Assert.assertEquals(SubjectType.parse("public"), subjectTypes.get(0));

		Assert.assertEquals(
			Scope.parse(supported), oidcProviderMetadata.getScopes());

		AuthorizationServerMetadata authorizationServerMetadata =
			AuthorizationServerMetadata.parse(
				oAuthClientASLocalMetadata.getOAuthASMetadataJSON());

		Assert.assertEquals(
			Issuer.parse(okURL), authorizationServerMetadata.getIssuer());
		Assert.assertEquals(
			URI.create(okURL),
			authorizationServerMetadata.getAuthorizationEndpointURI());
		Assert.assertEquals(
			URI.create(okURL),
			authorizationServerMetadata.getTokenEndpointURI());
		Assert.assertEquals(
			URI.create(okURL), authorizationServerMetadata.getJWKSetURI());
		Assert.assertEquals(
			Scope.parse(supported), authorizationServerMetadata.getScopes());

		List<GrantType> grantTypes =
			authorizationServerMetadata.getGrantTypes();

		Assert.assertEquals(GrantType.parse(supported), grantTypes.get(0));
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

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private static OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

	private static User _user;

	@Inject
	private static UserLocalService _userLocalService;

	@Inject(
		filter = "mvc.command.name=/oauth_client_admin/update_oauth_client_as_local_metadata"
	)
	private MVCActionCommand _editMVCActionCommand;

}