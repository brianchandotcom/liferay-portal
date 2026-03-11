/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_4_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.net.URI;

import java.security.MessageDigest;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuele Castro
 */
@RunWith(Arquillian.class)
public class OAuthClientEntryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		if (Validator.isNotNull(_pid1)) {
			ConfigurationTestUtil.deleteConfiguration(_pid1);
		}

		if (Validator.isNotNull(_pid2)) {
			ConfigurationTestUtil.deleteConfiguration(_pid2);
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		String clientId1 = RandomTestUtil.randomString();
		String discoveryEndpoint =
			"https://accounts.google.com/.well-known/openid-configuration";

		_pid1 = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.portal.security.sso.openid.connect.internal." +
				"configuration.OpenIdConnectProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"discoveryEndpoint", discoveryEndpoint
			).put(
				"matcherField", "screenName"
			).put(
				"openIdConnectClientId", clientId1
			).build());

		OAuthClientEntry oAuthClientEntry1 =
			_oAuthClientEntryLocalService.fetchOAuthClientEntry(
				TestPropsValues.getCompanyId(), discoveryEndpoint, clientId1);

		oAuthClientEntry1.setMatcherField(null);

		oAuthClientEntry1 =
			_oAuthClientEntryLocalService.updateOAuthClientEntry(
				oAuthClientEntry1);

		String clientId2 = RandomTestUtil.randomString();
		String issuerURL = "http://test/openid-connect";
		String tokenEndpoint = "http://test/openid-connect/token";

		_pid2 = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.portal.security.sso.openid.connect.internal." +
				"configuration.OpenIdConnectProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"authorizationEndpoint", "http://test/openid-connect/auth"
			).put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"issuerURL", issuerURL
			).put(
				"jwksURI", "http://test/openid-connect/certs"
			).put(
				"matcherField", "screenName"
			).put(
				"openIdConnectClientId", clientId2
			).put(
				"subjectTypes", new String[] {"public"}
			).put(
				"tokenEndpoint", tokenEndpoint
			).put(
				"userInfoEndpoint", "http://test/openid-connect/userinfo"
			).build());

		OAuthClientEntry oAuthClientEntry2 =
			_oAuthClientEntryLocalService.fetchOAuthClientEntry(
				TestPropsValues.getCompanyId(),
				_generateLocalWellKnownURI(issuerURL, tokenEndpoint),
				clientId2);

		oAuthClientEntry2.setMatcherField(null);

		oAuthClientEntry2 =
			_oAuthClientEntryLocalService.updateOAuthClientEntry(
				oAuthClientEntry2);

		_runUpgrade();

		oAuthClientEntry1 = _oAuthClientEntryLocalService.fetchOAuthClientEntry(
			oAuthClientEntry1.getOAuthClientEntryId());

		Assert.assertEquals("screenName", oAuthClientEntry1.getMatcherField());

		oAuthClientEntry2 = _oAuthClientEntryLocalService.fetchOAuthClientEntry(
			oAuthClientEntry2.getOAuthClientEntryId());

		Assert.assertEquals("screenName", oAuthClientEntry2.getMatcherField());
	}

	private String _generateLocalWellKnownURI(
			String issuerURL, String tokenEndpoint)
		throws Exception {

		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		URI issuerURI = URI.create(issuerURL);

		return StringBundler.concat(
			issuerURI.getScheme(), "://", issuerURI.getAuthority(),
			"/.well-known/openid-configuration", issuerURI.getPath(), '/',
			Base64.encodeToURL(messageDigest.digest(tokenEndpoint.getBytes())),
			"/local");
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();

		_multiVMPool.clear();
	}

	private static final String _CLASS_NAME =
		"com.liferay.oauth.client.persistence.internal.upgrade.v1_4_1." +
			"OAuthClientEntryUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.oauth.client.persistence.internal.upgrade.registry.OAuthClientPersistenceServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

	private String _pid1;
	private String _pid2;

}