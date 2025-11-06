/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.oauth.client.persistence.exception.DuplicateOAuthClientEntryException;
import com.liferay.oauth.client.persistence.exception.OAuthClientEntryAuthRequestParametersJSONException;
import com.liferay.oauth.client.persistence.exception.OAuthClientEntryAuthServerWellKnownURIException;
import com.liferay.oauth.client.persistence.exception.OAuthClientEntryOIDCUserInfoMapperJSONException;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christian Moura
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class OAuthClientEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_infoJSON = JSONUtil.put(
			"client_id", RandomTestUtil.randomString()
		).put(
			"client_name", "Client to Google"
		).put(
			"client_secret", RandomTestUtil.randomString()
		).put(
			"grant_types",
			JSONUtil.putAll("authorization_code", "refresh_token")
		).put(
			"response_types", JSONUtil.putAll("code")
		).put(
			"scope", "openid email profile"
		).toString();
	}

	@Test
	public void testAddOAuthClientEntry() throws Exception {
		OAuthClientEntry oAuthClientEntry =
			_oAuthClientEntryLocalService.addOAuthClientEntry(
				TestPropsValues.getUserId(), _AUTH_REQUEST_PARAMETERS_JSON,
				_AUTH_SERVER_WELL_KNOWN_URI, _CUSTOM_CLAIMS_JSON, _infoJSON,
				OAuthClientEntryConstants.METADATA_CACHE_TIME_DEFAULT,
				OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON,
				_TOKEN_REQUEST_PARAMETERS_JSON);

		Assert.assertEquals(
			OAuthClientEntryConstants.METADATA_CACHE_TIME_DEFAULT,
			oAuthClientEntry.getMetadataCacheTime());
		Assert.assertEquals(
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON,
			oAuthClientEntry.getOIDCUserInfoMapperJSON());
		Assert.assertEquals(
			_AUTH_REQUEST_PARAMETERS_JSON,
			oAuthClientEntry.getAuthRequestParametersJSON());
		Assert.assertEquals(
			_CUSTOM_CLAIMS_JSON, oAuthClientEntry.getCustomClaimsJSON());
		Assert.assertEquals(
			_TOKEN_REQUEST_PARAMETERS_JSON,
			oAuthClientEntry.getTokenRequestParametersJSON());
		Assert.assertEquals(
			oAuthClientEntry.getUserId(), TestPropsValues.getUserId());
	}

	@Test(expected = DuplicateOAuthClientEntryException.class)
	public void testAddOAuthClientEntryDuplicatedEntry() throws Exception {
		_oAuthClientEntryLocalService.addOAuthClientEntry(
			TestPropsValues.getUserId(), _AUTH_REQUEST_PARAMETERS_JSON,
			_AUTH_SERVER_WELL_KNOWN_URI, _CUSTOM_CLAIMS_JSON, _infoJSON,
			OAuthClientEntryConstants.METADATA_CACHE_TIME_DEFAULT,
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON,
			_TOKEN_REQUEST_PARAMETERS_JSON);
		_oAuthClientEntryLocalService.addOAuthClientEntry(
			TestPropsValues.getUserId(), _AUTH_REQUEST_PARAMETERS_JSON,
			_AUTH_SERVER_WELL_KNOWN_URI, _CUSTOM_CLAIMS_JSON, _infoJSON,
			OAuthClientEntryConstants.METADATA_CACHE_TIME_DEFAULT,
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON,
			_TOKEN_REQUEST_PARAMETERS_JSON);
	}

	@Test(expected = OAuthClientEntryAuthRequestParametersJSONException.class)
	public void testAddOAuthClientEntryWithInvalidAuthRequestParameters()
		throws PortalException {

		_oAuthClientEntryLocalService.addOAuthClientEntry(
			TestPropsValues.getUserId(),
			"{\"scope\":\"\"openid email profile\",\"response_type\":\"\"}",
			_AUTH_SERVER_WELL_KNOWN_URI, _CUSTOM_CLAIMS_JSON, _infoJSON,
			OAuthClientEntryConstants.METADATA_CACHE_TIME_DEFAULT,
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON,
			_TOKEN_REQUEST_PARAMETERS_JSON);
	}

	@Test(expected = OAuthClientEntryOIDCUserInfoMapperJSONException.class)
	public void testAddOAuthClientEntryWithInvalidOidcUserInfoMapper()
		throws PortalException {

		_oAuthClientEntryLocalService.addOAuthClientEntry(
			TestPropsValues.getUserId(), _AUTH_REQUEST_PARAMETERS_JSON,
			_AUTH_SERVER_WELL_KNOWN_URI, _CUSTOM_CLAIMS_JSON, _infoJSON,
			OAuthClientEntryConstants.METADATA_CACHE_TIME_DEFAULT,
			JSONUtil.put(
				"user", JSONUtil.put("emailAddress", "")
			).toString(),
			_TOKEN_REQUEST_PARAMETERS_JSON);
	}

	@Test(expected = OAuthClientEntryAuthServerWellKnownURIException.class)
	public void testAddOAuthClientEntryWithUnreachableWellKnownURI()
		throws PortalException {

		_oAuthClientEntryLocalService.addOAuthClientEntry(
			TestPropsValues.getUserId(), _AUTH_REQUEST_PARAMETERS_JSON,
			"http://172.17.0.3:18080/auth/realms/master/." +
				"well-known/openid-configuration",
			_CUSTOM_CLAIMS_JSON, _infoJSON,
			OAuthClientEntryConstants.METADATA_CACHE_TIME_DEFAULT,
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON,
			_TOKEN_REQUEST_PARAMETERS_JSON);
	}

	private static final String _AUTH_REQUEST_PARAMETERS_JSON =
		"{\"scope\":\"openid email profile\",\"response_type\":\"code\"}";

	private static final String _AUTH_SERVER_WELL_KNOWN_URI =
		"https://accounts.google.com/.well-known/openid-configuration";

	private static final String _CUSTOM_CLAIMS_JSON = "{}";

	private static final String _TOKEN_REQUEST_PARAMETERS_JSON =
		"{\"grant_type\":\"authorization_code\"," +
			"\"scope\":\"openid email profile\"}";

	private static String _infoJSON;

	@Inject
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

}