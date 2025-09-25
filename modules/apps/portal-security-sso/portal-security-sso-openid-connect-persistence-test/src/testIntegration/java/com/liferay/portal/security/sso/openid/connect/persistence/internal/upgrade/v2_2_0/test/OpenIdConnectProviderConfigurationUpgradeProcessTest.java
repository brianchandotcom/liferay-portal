/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v2_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christian Moura
 */
@RunWith(Arquillian.class)
public class OpenIdConnectProviderConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeOpenIdConnectProviderConfiguration()
		throws Exception {

		_addConfiguration();

		try {
			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _UPGRADE_VERSION);

			upgradeProcess.upgrade();

			_assertConfiguration();
		}
		finally {
			_removeConfiguration();
		}
	}

	private void _addConfiguration() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		_properties = HashMapDictionaryBuilder.<String, Object>put(
			"authorizationEndPoint", RandomTestUtil.randomString()
		).put(
			"discoveryEndPoint", RandomTestUtil.randomString()
		).put(
			"discoveryEndPointCacheInMillis", RandomTestUtil.randomLong()
		).put(
			"tokenEndPoint", RandomTestUtil.randomString()
		).put(
			"userInfoEndPoint", RandomTestUtil.randomString()
		).build();

		ConfigurationHandler.write(unsyncByteArrayOutputStream, _properties);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into Configuration_ (configurationId, dictionary) " +
					"values(?, ?)")) {

			preparedStatement.setString(1, _CONFIGURATION_ID);
			preparedStatement.setString(
				2, unsyncByteArrayOutputStream.toString());

			preparedStatement.execute();
		}
	}

	private void _assertConfiguration() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select dictionary from Configuration_ where ",
					"configurationId = '", _CONFIGURATION_ID, "'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				String dictionaryString = resultSet.getString("dictionary");

				Dictionary<String, Object> dictionary =
					ConfigurationHandler.read(
						new UnsyncByteArrayInputStream(
							dictionaryString.getBytes(StringPool.UTF8)));

				Assert.assertEquals(
					_properties.get("discoveryEndPoint"),
					dictionary.get("discoveryEndpoint"));

				Assert.assertEquals(
					_properties.get("discoveryEndPointCacheInMillis"),
					dictionary.get("discoveryEndpointCacheInMillis"));

				Assert.assertEquals(
					_properties.get("authorizationEndPoint"),
					dictionary.get("authorizationEndpoint"));
				Assert.assertEquals(
					_properties.get("tokenEndPoint"),
					dictionary.get("tokenEndpoint"));
				Assert.assertEquals(
					_properties.get("userInfoEndPoint"),
					dictionary.get("userInfoEndpoint"));
			}
		}
	}

	private void _removeConfiguration() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from Configuration_ where configurationId ='" +
				_CONFIGURATION_ID + "'");
	}

	private static final String _CONFIGURATION_ID =
		"com.liferay.portal.security.sso.openid.connect.internal." +
			"configuration.OpenIdConnectProviderConfiguration.scoped~" +
				RandomTestUtil.randomString();

	private static final String _UPGRADE_VERSION =
		"com.liferay.portal.security.sso.openid.connect.persistence.internal." +
			"upgrade.v2_2_0.OpenIdConnectProviderConfigurationUpgradeProcess";

	private HashMapDictionary<String, Object> _properties;

	@Inject(
		filter = "(&(component.name=com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.registry.OpenIdConnectServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}