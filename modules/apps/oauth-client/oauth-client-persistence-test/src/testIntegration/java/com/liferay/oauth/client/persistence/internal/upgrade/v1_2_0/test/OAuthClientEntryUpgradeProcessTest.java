/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientEntryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Istvan Sajtos
 */
@RunWith(Arquillian.class)
public class OAuthClientEntryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule integrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		long discoveryEndpointCacheInMillis = RandomTestUtil.randomLong();
		String openIdConnectClientId = RandomTestUtil.randomString();

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.portal.security.sso.openid.connect.internal." +
				"configuration.OpenIdConnectProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"discoveryEndPoint",
				"https://accounts.google.com/.well-known/openid-configuration"
			).put(
				"discoveryEndpointCacheInMillis", discoveryEndpointCacheInMillis
			).put(
				"openIdConnectClientId", openIdConnectClientId
			).put(
				"openIdConnectClientSecret", RandomTestUtil.randomString()
			).put(
				"providerName",
				"OAuthClientEntryUpgradeProcessTest_" +
					RandomTestUtil.randomString()
			).build());

		try {
			OAuthClientEntry oAuthClientEntry1 =
				_oAuthClientEntryLocalService.getOAuthClientEntry(
					TestPropsValues.getCompanyId(),
					"https://accounts.google.com/.well-known" +
						"/openid-configuration",
					openIdConnectClientId);
			OAuthClientEntry oAuthClientEntry2 =
				_oAuthClientEntryLocalService.addOAuthClientEntry(
					TestPropsValues.getUserId(), StringPool.BLANK,
					"https://accounts.google.com/.well-known" +
						"/openid-configuration",
					null,
					JSONUtil.put(
						"client_id", RandomTestUtil.randomString()
					).put(
						"client_name", RandomTestUtil.randomString()
					).put(
						"client_secret", RandomTestUtil.randomString()
					).put(
						"redirect_uris", JSONUtil.put("")
					).put(
						"scope", "openid email profile"
					).put(
						"subject_type", "public"
					).toString(),
					0, OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON,
					StringPool.BLANK);

			_updateMetadataCacheTime(oAuthClientEntry1);
			_updateMetadataCacheTime(oAuthClientEntry2);

			_runUpgrade();

			oAuthClientEntry1 =
				_oAuthClientEntryLocalService.getOAuthClientEntry(
					oAuthClientEntry1.getOAuthClientEntryId());

			Assert.assertEquals(
				discoveryEndpointCacheInMillis,
				oAuthClientEntry1.getMetadataCacheTime());

			oAuthClientEntry2 =
				_oAuthClientEntryLocalService.getOAuthClientEntry(
					oAuthClientEntry2.getOAuthClientEntryId());

			Assert.assertEquals(
				OAuthClientEntryConstants.METADATA_CACHE_TIME_DEFAULT,
				oAuthClientEntry2.getMetadataCacheTime());
		}
		finally {
			ConfigurationTestUtil.deleteConfiguration(pid);
		}
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.oauth.client.persistence.internal.upgrade.v1_2_0." +
				"OAuthClientEntryUpgradeProcess");

		upgradeProcess.upgrade();
	}

	private void _updateMetadataCacheTime(OAuthClientEntry oAuthClientEntry)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update OAuthClientEntry set metadataCacheTime = null where " +
					"oAuthClientEntryId = ?");

			preparedStatement.setLong(
				1, oAuthClientEntry.getOAuthClientEntryId());

			preparedStatement.execute();
		}

		OAuthClientEntryUtil.clearCache();
	}

	@Inject
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.oauth.client.persistence.internal.upgrade.registry.OAuthClientPersistenceServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}