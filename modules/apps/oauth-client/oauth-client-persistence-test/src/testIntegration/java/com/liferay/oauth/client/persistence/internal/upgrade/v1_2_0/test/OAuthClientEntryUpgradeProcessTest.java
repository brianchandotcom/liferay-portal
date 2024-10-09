/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientEntryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Dictionary;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Istvan Sajtos
 */
@RunWith(Arquillian.class)
public class OAuthClientEntryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule integrationTestRule =
		new LiferayIntegrationTestRule();

	@AfterClass
	public static void tearDownClass() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(providerName=%s*)(service.factoryPid=%s*))",
				_PROVIDER_NAME_PREFIX, _PID));

		for (Configuration configuration : configurations) {
			configuration.delete();
		}
	}

	@Test
	public void testUpgradeOAuthClientEntriesWithOIDCProviderConfiguration()
		throws Exception {

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				_PID, StringPool.QUESTION);

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"discoveryEndPoint", _AUTH_SERVER_WELL_KNOWN_URI
			).put(
				"discoveryEndpointCacheInMillis", RandomTestUtil.randomLong()
			).put(
				"openIdConnectClientId", RandomTestUtil.randomString()
			).put(
				"openIdConnectClientSecret", RandomTestUtil.randomString()
			).put(
				"providerName",
				_PROVIDER_NAME_PREFIX + RandomTestUtil.randomString()
			).build();

		configuration.update(properties);

		Thread.sleep(500);

		OAuthClientEntry oAuthClientEntry =
			_oAuthClientEntryLocalService.getOAuthClientEntry(
				TestPropsValues.getCompanyId(), _AUTH_SERVER_WELL_KNOWN_URI,
				GetterUtil.getString(properties.get("openIdConnectClientId")));

		_setUpgradePreCondition(oAuthClientEntry);

		_runUpgrade();

		oAuthClientEntry = _oAuthClientEntryLocalService.getOAuthClientEntry(
			oAuthClientEntry.getOAuthClientEntryId());

		Assert.assertEquals(
			GetterUtil.getLong(
				properties.get("discoveryEndpointCacheInMillis")),
			oAuthClientEntry.getMetadataCacheInMillis());
	}

	@Test
	public void testUpgradeOAuthClientEntriesWithoutOIDCProviderConfiguration()
		throws Exception {

		OAuthClientEntry oAuthClientEntry =
			_oAuthClientEntryLocalService.addOAuthClientEntry(
				TestPropsValues.getUserId(), StringPool.BLANK,
				_AUTH_SERVER_WELL_KNOWN_URI,
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

		_setUpgradePreCondition(oAuthClientEntry);

		_runUpgrade();

		oAuthClientEntry = _oAuthClientEntryLocalService.getOAuthClientEntry(
			oAuthClientEntry.getOAuthClientEntryId());

		Assert.assertEquals(
			OAuthClientEntryConstants.METADATA_CACHE_IN_MILLIS_DEFAULT,
			oAuthClientEntry.getMetadataCacheInMillis());
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private void _setUpgradePreCondition(OAuthClientEntry oAuthClientEntry)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update OAuthClientEntry set metadataCacheInMillis = null " +
					"where oAuthClientEntryId = ?");

			preparedStatement.setLong(
				1, oAuthClientEntry.getOAuthClientEntryId());

			preparedStatement.execute();
		}

		OAuthClientEntryUtil.clearCache();
	}

	private static final String _AUTH_SERVER_WELL_KNOWN_URI =
		"https://accounts.google.com/.well-known/openid-configuration";

	private static final String _CLASS_NAME =
		"com.liferay.oauth.client.persistence.internal.upgrade.v1_2_0." +
			"OAuthClientEntryUpgradeProcess";

	private static final String _PID =
		"com.liferay.portal.security.sso.openid.connect.internal." +
			"configuration.OpenIdConnectProviderConfiguration";

	private static final String _PROVIDER_NAME_PREFIX =
		"OAuthClientEntryUpgradeProcessTest_";

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.oauth.client.persistence.internal.upgrade.registry.OAuthClientPersistenceServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}