/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.catapult;

import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalService;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jorge García Jiménez
 */
@FeatureFlags(featureFlags = @FeatureFlag("LPD-108193"))
public class UpstreamTokenPortalCatapultHeaderContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_upstreamTokenPortalCatapultHeaderContributor,
			"_oAuthClientEntryLocalService", _oAuthClientEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_upstreamTokenPortalCatapultHeaderContributor,
			"_openIdConnectSessionLocalService",
			_openIdConnectSessionLocalService);

		_setUpMocks();
	}

	@Test
	public void testContribute() {
		_testContribute();
		_testContributeWithDifferentCompanyId();
		_testContributeWithDifferentSecureOrigin();
		_testContributeWithDifferentUserId();
		_testContributeWithExpiredIdToken();
		_testContributeWithInsecureLocation();
		_testContributeWithInvalidIdToken();
		_testContributeWithInvalidLocation();
		_testContributeWithInvalidTokenRequestParametersJSON();
		_testContributeWithMissingAccessTokenExpirationDate();
		_testContributeWithMissingFeature();
		_testContributeWithMissingIdToken();
		_testContributeWithMissingIdTokenExpirationTime();
		_testContributeWithMissingOAuthClientEntry();
		_testContributeWithMissingSession();
		_testContributeWithNearExpiryAccessToken();
		_testContributeWithNearExpiryIdToken();
		_testContributeWithNotAllowedOAuthClientEntry();
	}

	@FeatureFlag(enable = false, value = "LPD-108193")
	@Test
	public void testContributeWithDisabledFeatureFlag() {
		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private static String _createIdToken(Date expirationTime) {
		PlainJWT plainJWT = new PlainJWT(
			new JWTClaimsSet.Builder(
			).expirationTime(
				expirationTime
			).build());

		return plainJWT.serialize();
	}

	private String _getUpstreamIdToken(String location) {
		return _getUpstreamIdToken(
			location, Collections.singletonList("upstream.token.forwarding"));
	}

	private String _getUpstreamIdToken(
		String location, List<String> oAuth2ApplicationFeatures) {

		Map<String, String> headers = new HashMap<>();

		_upstreamTokenPortalCatapultHeaderContributor.contribute(
			_COMPANY_ID, headers, _HOME_PAGE_URL, location,
			oAuth2ApplicationFeatures, _USER_ID);

		return headers.get("X-Upstream-ID-Token");
	}

	private void _resetAndSetUpMocks() {
		Mockito.reset(
			_oAuthClientEntry, _oAuthClientEntryLocalService,
			_openIdConnectSession, _openIdConnectSessionLocalService);

		_setUpMocks();
	}

	private void _setUpMocks() {
		Mockito.when(
			_openIdConnectSessionLocalService.fetchCurrentOpenIdConnectSession()
		).thenReturn(
			_openIdConnectSession
		);

		Mockito.when(
			_openIdConnectSession.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			_openIdConnectSession.getUserId()
		).thenReturn(
			_USER_ID
		);

		Mockito.when(
			_openIdConnectSession.getAccessTokenExpirationDate()
		).thenReturn(
			new Date(System.currentTimeMillis() + Time.HOUR)
		);

		Mockito.when(
			_oAuthClientEntryLocalService.fetchOAuthClientEntry(
				Mockito.anyLong(), Mockito.any(), Mockito.any())
		).thenReturn(
			_oAuthClientEntry
		);

		Mockito.when(
			_oAuthClientEntry.getTokenRequestParametersJSON()
		).thenReturn(
			JSONUtil.put(
				"allow_upstream_token_forwarding", true
			).toString()
		);

		Mockito.when(
			_openIdConnectSession.getIdToken()
		).thenReturn(
			_ID_TOKEN
		);
	}

	private void _testContribute() {
		_resetAndSetUpMocks();

		Assert.assertEquals(_ID_TOKEN, _getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithDifferentCompanyId() {
		_resetAndSetUpMocks();

		Mockito.when(
			_openIdConnectSession.getCompanyId()
		).thenReturn(
			_COMPANY_ID + 1
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithDifferentSecureOrigin() {
		_resetAndSetUpMocks();

		Assert.assertNull(
			_getUpstreamIdToken(_HOME_PAGE_URL + ":9999/o/resource"));
		Assert.assertNull(
			_getUpstreamIdToken(
				"https://" + RandomTestUtil.randomString() +
					".liferay.com/o/resource"));
	}

	private void _testContributeWithDifferentUserId() {
		_resetAndSetUpMocks();

		Mockito.when(
			_openIdConnectSession.getUserId()
		).thenReturn(
			_USER_ID + 1
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithExpiredIdToken() {
		_resetAndSetUpMocks();

		Mockito.when(
			_openIdConnectSession.getIdToken()
		).thenReturn(
			_createIdToken(new Date(System.currentTimeMillis() - Time.HOUR))
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithInsecureLocation() {
		_resetAndSetUpMocks();

		Assert.assertNull(
			_getUpstreamIdToken(
				StringUtil.replace(_HOME_PAGE_URL, Http.HTTPS, Http.HTTP) +
					"/o/resource"));
	}

	private void _testContributeWithInvalidIdToken() {
		_resetAndSetUpMocks();

		Mockito.when(
			_openIdConnectSession.getIdToken()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithInvalidLocation() {
		_resetAndSetUpMocks();

		Assert.assertNull(
			_getUpstreamIdToken(_HOME_PAGE_URL + ":notaport/o/resource"));
	}

	private void _testContributeWithInvalidTokenRequestParametersJSON() {
		_resetAndSetUpMocks();

		Mockito.when(
			_oAuthClientEntry.getTokenRequestParametersJSON()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithMissingAccessTokenExpirationDate() {
		_resetAndSetUpMocks();

		Mockito.when(
			_openIdConnectSession.getAccessTokenExpirationDate()
		).thenReturn(
			null
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithMissingFeature() {
		_resetAndSetUpMocks();

		Assert.assertNull(
			_getUpstreamIdToken(
				_LOCATION,
				Collections.singletonList(RandomTestUtil.randomString())));
	}

	private void _testContributeWithMissingIdToken() {
		_resetAndSetUpMocks();

		Mockito.when(
			_openIdConnectSession.getIdToken()
		).thenReturn(
			null
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithMissingIdTokenExpirationTime() {
		_resetAndSetUpMocks();

		Mockito.when(
			_openIdConnectSession.getIdToken()
		).thenReturn(
			_createIdToken(null)
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithMissingOAuthClientEntry() {
		_resetAndSetUpMocks();

		Mockito.when(
			_oAuthClientEntryLocalService.fetchOAuthClientEntry(
				Mockito.anyLong(), Mockito.any(), Mockito.any())
		).thenReturn(
			null
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithMissingSession() {
		_resetAndSetUpMocks();

		Mockito.when(
			_openIdConnectSessionLocalService.fetchCurrentOpenIdConnectSession()
		).thenReturn(
			null
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithNearExpiryAccessToken() {
		_resetAndSetUpMocks();

		Mockito.when(
			_openIdConnectSession.getAccessTokenExpirationDate()
		).thenReturn(
			new Date(System.currentTimeMillis() + (10 * Time.SECOND))
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithNearExpiryIdToken() {
		_resetAndSetUpMocks();

		Mockito.when(
			_openIdConnectSession.getIdToken()
		).thenReturn(
			_createIdToken(
				new Date(System.currentTimeMillis() + (10 * Time.SECOND)))
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private void _testContributeWithNotAllowedOAuthClientEntry() {
		_resetAndSetUpMocks();

		Mockito.when(
			_oAuthClientEntry.getTokenRequestParametersJSON()
		).thenReturn(
			"{}"
		);

		Assert.assertNull(_getUpstreamIdToken(_LOCATION));
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _HOME_PAGE_URL =
		"https://" + RandomTestUtil.randomString() + ".liferay.com";

	private static final String _ID_TOKEN = _createIdToken(
		new Date(System.currentTimeMillis() + Time.HOUR));

	private static final String _LOCATION = _HOME_PAGE_URL + "/o/resource";

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private final OAuthClientEntry _oAuthClientEntry = Mockito.mock(
		OAuthClientEntry.class);
	private final OAuthClientEntryLocalService _oAuthClientEntryLocalService =
		Mockito.mock(OAuthClientEntryLocalService.class);
	private final OpenIdConnectSession _openIdConnectSession = Mockito.mock(
		OpenIdConnectSession.class);
	private final OpenIdConnectSessionLocalService
		_openIdConnectSessionLocalService = Mockito.mock(
			OpenIdConnectSessionLocalService.class);
	private final UpstreamTokenPortalCatapultHeaderContributor
		_upstreamTokenPortalCatapultHeaderContributor =
			new UpstreamTokenPortalCatapultHeaderContributor();

}