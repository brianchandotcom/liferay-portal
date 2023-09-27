/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.feature.flag;

import com.liferay.feature.flag.web.internal.manager.FeatureFlagPreferencesManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsUtil;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Thiago Buarque
 */
public class FeatureFlagsBagProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_featureFlagsBagProvider = new FeatureFlagsBagProvider();

		ReflectionTestUtil.setFieldValue(
			_featureFlagsBagProvider, "_featureFlagPreferencesManager",
			_featureFlagPreferencesManager);
		ReflectionTestUtil.setFieldValue(
			_featureFlagsBagProvider, "_language", _language);
	}

	@Before
	public void setUp() {
		com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testFeatureFlagSelfDependency() {
		long companyId = 1L;

		String key = "LPS-1011";

		PropsUtil.set(FeatureFlagConstants.getKey(key), "true");
		PropsUtil.set(FeatureFlagConstants.getKey(key, "dependencies"), key);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				FeatureFlagsBagProvider.class.getName(), Level.SEVERE)) {

			_featureFlagsBagProvider.getOrCreateFeatureFlagsBag(companyId);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				String.format(
					"A feature flag cannot depend on itself: %s", key),
				logEntry.getMessage());
		}
	}

	@Test
	public void testNonsystemFeatureFlags() {
		long companyId = 2L;

		String key1 = "LPS-123";

		PropsUtil.set(FeatureFlagConstants.getKey(key1), "true");
		PropsUtil.set(FeatureFlagConstants.getKey(key1, "system"), "true");

		String key2 = "LPS-456";

		PropsUtil.set(FeatureFlagConstants.getKey(key2), "true");
		PropsUtil.set(FeatureFlagConstants.getKey(key2, "system"), "false");

		String key3 = "LPS-789";

		PropsUtil.set(FeatureFlagConstants.getKey(key3), "true");

		FeatureFlagsBag featureFlagsBag =
			_featureFlagsBagProvider.getOrCreateFeatureFlagsBag(companyId);

		List<String> keys = Arrays.asList(key1, key2, key3);

		List<FeatureFlag> featureFlags = featureFlagsBag.getFeatureFlags(
			featureFlag -> keys.contains(featureFlag.getKey()));

		Assert.assertTrue(featureFlags.size() == 2);

		List<String> nonsystemKeys = Arrays.asList(key2, key3);

		FeatureFlag featureFlag = featureFlags.get(0);

		Assert.assertTrue(nonsystemKeys.contains(featureFlag.getKey()));

		featureFlag = featureFlags.get(1);

		Assert.assertTrue(nonsystemKeys.contains(featureFlag.getKey()));
	}

	@Test
	public void testSystemFeatureFlagDependencyOnNonsystemFeatureFlag() {
		long companyId = CompanyConstants.SYSTEM;

		String key1 = "LPS-123";

		PropsUtil.set(FeatureFlagConstants.getKey(key1), "true");
		PropsUtil.set(FeatureFlagConstants.getKey(key1, "system"), "true");

		String key2 = "LPS-456";

		PropsUtil.set(FeatureFlagConstants.getKey(key1, "dependencies"), key2);

		PropsUtil.set(FeatureFlagConstants.getKey(key2), "true");

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				FeatureFlagsBagProvider.class.getName(), Level.SEVERE)) {

			_featureFlagsBagProvider.getOrCreateFeatureFlagsBag(companyId);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"The system feature flag ", key1,
					" cannot depend on the nonsystem feature flag ", key2),
				logEntry.getMessage());
		}
	}

	@Test
	public void testSystemFeatureFlags() {
		long companyId = CompanyConstants.SYSTEM;

		String key1 = "LPS-123";

		PropsUtil.set(FeatureFlagConstants.getKey(key1), "true");
		PropsUtil.set(FeatureFlagConstants.getKey(key1, "system"), "true");

		String key2 = "LPS-456";

		PropsUtil.set(FeatureFlagConstants.getKey(key2), "true");
		PropsUtil.set(FeatureFlagConstants.getKey(key2, "system"), "false");

		FeatureFlagsBag featureFlagsBag =
			_featureFlagsBagProvider.getOrCreateFeatureFlagsBag(companyId);

		List<String> keys = Arrays.asList(key1, key2);

		List<FeatureFlag> featureFlags = featureFlagsBag.getFeatureFlags(
			featureFlag -> keys.contains(featureFlag.getKey()));

		Assert.assertTrue(featureFlags.size() == 1);

		FeatureFlag featureFlag = featureFlags.get(0);

		Assert.assertEquals(key1, featureFlag.getKey());
	}

	private static final FeatureFlagPreferencesManager
		_featureFlagPreferencesManager = Mockito.mock(
			FeatureFlagPreferencesManager.class);
	private static FeatureFlagsBagProvider _featureFlagsBagProvider;
	private static final Language _language = Mockito.mock(Language.class);

}