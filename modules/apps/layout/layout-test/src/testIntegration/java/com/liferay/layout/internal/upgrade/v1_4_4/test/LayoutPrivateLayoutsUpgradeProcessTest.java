/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_4_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portlet.PortalPreferencesWrapper;

import java.util.List;

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
		Configuration[] originalConfigurations =
			_configurationAdmin.listConfigurations(
				StringBundler.concat(
					StringPool.OPEN_PARENTHESIS, Constants.SERVICE_PID,
					StringPool.EQUAL, _PID, StringPool.CLOSE_PARENTHESIS));
		Release originalRelease = _releaseLocalService.fetchRelease(
			_SCHEMA_NAME);
		Release release = null;
		Configuration configuration = null;
		String originalFeatureFlagValue = _updateFeatureFlagValue(
			Boolean.FALSE.toString());

		try {
			if (ArrayUtil.isNotEmpty(originalConfigurations)) {
				ConfigurationTestUtil.deleteConfiguration(
					originalConfigurations[0]);
			}

			if (originalRelease != null) {
				_releaseLocalService.deleteRelease(originalRelease);
			}

			_runUpgrade();

			_assertFeatureFlagValue(Boolean.TRUE.toString());

			release = _releaseLocalService.addRelease(_SCHEMA_NAME, "1.0.0");

			_runUpgrade();

			_assertFeatureFlagValue(Boolean.FALSE.toString());

			configuration = _configurationAdmin.getConfiguration(
				_PID, StringPool.QUESTION);

			ConfigurationTestUtil.saveConfiguration(
				configuration,
				HashMapDictionaryBuilder.<String, Object>put(
					"disabledReleaseFeatureFlags",
					new String[] {_DISABLE_PRIVATE_LAYOUTS}
				).build());

			_runUpgrade();

			_assertFeatureFlagValue(Boolean.FALSE.toString());
		}
		finally {
			if (configuration != null) {
				ConfigurationTestUtil.deleteConfiguration(configuration);
			}

			if (ArrayUtil.isNotEmpty(originalConfigurations)) {
				Configuration originalConfiguration = originalConfigurations[0];

				ConfigurationTestUtil.saveConfiguration(
					originalConfiguration,
					originalConfiguration.getProperties());
			}

			if (release != null) {
				_releaseLocalService.deleteRelease(release);
			}

			if (originalRelease != null) {
				_releaseLocalService.addRelease(originalRelease);
			}

			_updateFeatureFlagValue(originalFeatureFlagValue);
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

	private void _runUpgrade() throws Exception {
		PortalPreferencesWrapper portalPreferencesWrapper =
			(PortalPreferencesWrapper)
				_portalPreferencesLocalService.getPreferences(
					TestPropsValues.getCompanyId(),
					PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		PortalPreferences portalPreferences =
			portalPreferencesWrapper.getPortalPreferencesImpl();

		portalPreferences.setValue(
			FeatureFlagConstants.PREFERENCE_NAMESPACE, "LPD-38869", null);

		_portalPreferencesLocalService.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY, portalPreferences);

		_assertFeatureFlagValue(null);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.layout.internal.upgrade.v1_4_4." +
					"LayoutPrivateLayoutsUpgradeProcess",
				LoggerTestUtil.ERROR)) {

			UpgradeProcess[] upgradeSteps = UpgradeTestUtil.getUpgradeSteps(
				_upgradeStepRegistrator, new Version(1, 4, 4));

			UpgradeProcess upgradeProcess = upgradeSteps[0];

			upgradeProcess.upgrade();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
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

	private static final String _SCHEMA_NAME =
		"com.liferay.release.feature.flag.web";

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject(
		filter = "(&(component.name=com.liferay.layout.internal.upgrade.registry.LayoutServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Inject
	private ReleaseLocalService _releaseLocalService;

}