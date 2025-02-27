/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_4_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.model.impl.ReleaseImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portlet.PortalPreferencesWrapper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class LayoutPrivateLayoutsUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgrade() throws Exception {
		_entityCache.clearCache();
		_multiVMPool.clear();

		String originalFeatureFlagValue = _updateFeatureFlagValue(
			Boolean.FALSE.toString());
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(", Constants.SERVICE_PID,
				"=com.liferay.release.feature.flag.web.internal.configuration.",
				"ReleaseFeatureFlagConfiguration)"));
		Release release = _getReleaseInfo();

		try {
			_removeReleaseInfo();

			if (configurations != null) {
				ConfigurationTestUtil.deleteConfiguration(configurations[0]);
			}

			_runUpgrade();

			_assertFeatureFlagValue(Boolean.TRUE.toString());

			_insertReleaseInfo(_newRelease());

			_runUpgrade();

			_assertFeatureFlagValue(Boolean.FALSE.toString());

			ConfigurationTestUtil.saveConfiguration(
				_configurationAdmin.getConfiguration(_PID, StringPool.QUESTION),
				HashMapDictionaryBuilder.<String, Object>put(
					"disabledReleaseFeatureFlags",
					new String[] {_DISABLE_PRIVATE_LAYOUTS}
				).build());

			_runUpgrade();

			_assertFeatureFlagValue(Boolean.FALSE.toString());
		}
		finally {
			_removeReleaseInfo();

			if (release != null) {
				_insertReleaseInfo(release);
			}

			_updateFeatureFlagValue(originalFeatureFlagValue);

			ConfigurationTestUtil.deleteConfiguration(_PID);

			if (configurations != null) {
				Configuration configuration = configurations[0];

				ConfigurationTestUtil.saveConfiguration(
					_configurationAdmin.getConfiguration(
						_PID, StringPool.QUESTION),
					configuration.getProperties());
			}
		}
	}

	private void _assertFeatureFlagValue(String value) throws Exception {
		PortalPreferencesWrapper portalPreferencesWrapper =
			(PortalPreferencesWrapper)
				_portalPreferencesLocalService.getPreferences(
					TestPropsValues.getCompanyId(),
					PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		PortalPreferences portalPreferences =
			portalPreferencesWrapper.getPortalPreferencesImpl();

		Assert.assertEquals(
			value,
			portalPreferences.getValue(
				FeatureFlagConstants.PREFERENCE_NAMESPACE, "LPD-38869", null));
	}

	private Release _getReleaseInfo() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select * from Release_ where servletContextName = 'com." +
					"liferay.release.feature.flag.web'")) {

			if (!resultSet.next()) {
				return null;
			}

			Release release = new ReleaseImpl();

			release.setMvccVersion(resultSet.getLong("mvccVersion"));
			release.setReleaseId(resultSet.getLong("releaseId"));
			release.setCreateDate(resultSet.getDate("createDate"));
			release.setModifiedDate(resultSet.getDate("modifiedDate"));
			release.setServletContextName(
				resultSet.getString("servletContextName"));
			release.setSchemaVersion(resultSet.getString("schemaVersion"));
			release.setBuildNumber(resultSet.getInt("buildNumber"));
			release.setBuildDate(resultSet.getDate("buildDate"));
			release.setVerified(resultSet.getBoolean("verified"));
			release.setState(resultSet.getInt("state_"));
			release.setTestString(resultSet.getString("testString"));

			return release;
		}
	}

	private void _insertReleaseInfo(Release release) throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into Release_(mvccVersion, releaseId, createDate, ",
					"modifiedDate, servletContextName, schemaVersion, ",
					"buildNumber, buildDate, verified, state_, testString) ",
					"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, release.getMvccVersion());
			preparedStatement.setLong(2, release.getReleaseId());

			java.util.Date createDate = release.getCreateDate();

			preparedStatement.setDate(3, new Date(createDate.getTime()));

			java.util.Date modifiedDate = release.getModifiedDate();

			preparedStatement.setDate(4, new Date(modifiedDate.getTime()));

			preparedStatement.setString(5, release.getServletContextName());
			preparedStatement.setString(6, release.getSchemaVersion());
			preparedStatement.setInt(7, release.getBuildNumber());

			java.util.Date buildDate = release.getBuildDate();

			Date date = null;

			if (buildDate != null) {
				date = new Date(buildDate.getTime());
			}

			preparedStatement.setDate(8, date);

			preparedStatement.setBoolean(9, release.isVerified());
			preparedStatement.setInt(10, release.getState());
			preparedStatement.setString(11, release.getTestString());

			preparedStatement.executeUpdate();
		}
	}

	private Release _newRelease() {
		Release release = new ReleaseImpl();

		release.setMvccVersion(RandomTestUtil.randomLong());
		release.setReleaseId(RandomTestUtil.randomLong());
		release.setCreateDate(RandomTestUtil.nextDate());
		release.setModifiedDate(RandomTestUtil.nextDate());
		release.setServletContextName("com.liferay.release.feature.flag.web");
		release.setSchemaVersion("1.0.0");
		release.setBuildNumber(RandomTestUtil.randomInt());
		release.setVerified(false);
		release.setState(0);

		return release;
	}

	private void _removeReleaseInfo() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from Release_ where servletContextName = 'com." +
					"liferay.release.feature.flag.web'")) {

			preparedStatement.execute();
		}
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeSteps = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(1, 4, 4));

		UpgradeProcess upgradeProcess = upgradeSteps[0];

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	private String _updateFeatureFlagValue(String value) throws Exception {
		PortalPreferencesWrapper portalPreferencesWrapper =
			(PortalPreferencesWrapper)
				_portalPreferencesLocalService.getPreferences(
					TestPropsValues.getCompanyId(),
					PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		PortalPreferences portalPreferences =
			portalPreferencesWrapper.getPortalPreferencesImpl();

		String previousValue = portalPreferences.getValue(
			FeatureFlagConstants.PREFERENCE_NAMESPACE, "LPD-38869", null);

		portalPreferences = portalPreferencesWrapper.getPortalPreferencesImpl();

		portalPreferences.setValue(
			FeatureFlagConstants.PREFERENCE_NAMESPACE, "LPD-38869", value);

		_portalPreferencesLocalService.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY, portalPreferences);

		_assertFeatureFlagValue(value);

		return previousValue;
	}

	private static final String _DISABLE_PRIVATE_LAYOUTS =
		"DISABLE_PRIVATE_LAYOUTS";

	private static final String _PID =
		"com.liferay.release.feature.flag.web.internal.configuration." +
			"ReleaseFeatureFlagConfiguration";

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject(
		filter = "(&(component.name=com.liferay.layout.internal.upgrade.registry.LayoutServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private PortalPreferencesLocalService _portalPreferencesLocalService;

}