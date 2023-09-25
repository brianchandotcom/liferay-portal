/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.feature.flag.test.util.FeatureFlagTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Drew Brokke
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class FeatureFlagListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(FeatureFlagListenerTest.class);

		_bundleContext = bundle.getBundleContext();

		_companyId = TestPropsValues.getCompanyId();

		_value1 = FeatureFlagTestUtil.getFeatureFlagValue(
			_companyId, _FEATURE_FLAG_KEY_1);
		_value2 = FeatureFlagTestUtil.getFeatureFlagValue(
			_companyId, _FEATURE_FLAG_KEY_2);

		_valueSystem = FeatureFlagTestUtil.getFeatureFlagValue(
			CompanyConstants.SYSTEM, _FEATURE_FLAG_KEY_SYSTEM);
	}

	@After
	public void tearDown() throws Exception {
		FeatureFlagTestUtil.setFeatureFlagValue(
			_companyId, _value1, _FEATURE_FLAG_KEY_1);
		FeatureFlagTestUtil.setFeatureFlagValue(
			_companyId, _value2, _FEATURE_FLAG_KEY_2);
		FeatureFlagTestUtil.setFeatureFlagValue(
			CompanyConstants.SYSTEM, _valueSystem, _FEATURE_FLAG_KEY_SYSTEM);
	}

	@Test
	public void testRegisterForSystemKey() throws Exception {
		try (TestFeatureFlagListener testFeatureFlagListener =
				new TestFeatureFlagListener(_FEATURE_FLAG_KEY_SYSTEM)) {

			testFeatureFlagListener.assertInvocations(
				_valuesToString(
					CompanyConstants.SYSTEM, _FEATURE_FLAG_KEY_SYSTEM,
					_valueSystem));

			FeatureFlagTestUtil.setFeatureFlagValue(
				CompanyConstants.SYSTEM, !_valueSystem,
				_FEATURE_FLAG_KEY_SYSTEM);

			testFeatureFlagListener.assertInvocations(
				_valuesToString(
					CompanyConstants.SYSTEM, _FEATURE_FLAG_KEY_SYSTEM,
					!_valueSystem));
		}
	}

	@Test
	public void testRegisterWithMultipleKeys() throws Exception {
		try (TestFeatureFlagListener testFeatureFlagListener =
				new TestFeatureFlagListener(
					_FEATURE_FLAG_KEY_1, _FEATURE_FLAG_KEY_2)) {

			testFeatureFlagListener.assertInvocations(
				_valuesToString(_companyId, _FEATURE_FLAG_KEY_1, _value1),
				_valuesToString(_companyId, _FEATURE_FLAG_KEY_2, _value2));

			FeatureFlagTestUtil.setFeatureFlagValue(
				_companyId, !_value1, _FEATURE_FLAG_KEY_1);

			FeatureFlagTestUtil.setFeatureFlagValue(
				_companyId, !_value2, _FEATURE_FLAG_KEY_2);

			FeatureFlagTestUtil.setFeatureFlagValue(
				_companyId, _value1, _FEATURE_FLAG_KEY_1);

			testFeatureFlagListener.assertInvocations(
				_valuesToString(_companyId, _FEATURE_FLAG_KEY_1, !_value1),
				_valuesToString(_companyId, _FEATURE_FLAG_KEY_2, !_value2),
				_valuesToString(_companyId, _FEATURE_FLAG_KEY_1, _value1));
		}
	}

	@Test
	public void testRegisterWithNoKeys() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.feature.flag.web.internal.feature.flag." +
					"FeatureFlagsBagProviderImpl",
				LoggerTestUtil.ERROR);
			TestFeatureFlagListener ignored = new TestFeatureFlagListener()) {

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			String message = logEntry.getMessage();

			Assert.assertTrue(
				message.contains("No featureFlagKey property found for "));
		}
	}

	@Test
	public void testRegisterWithSingleKey() throws Exception {
		try (TestFeatureFlagListener testFeatureFlagListener =
				new TestFeatureFlagListener(_FEATURE_FLAG_KEY_1)) {

			testFeatureFlagListener.assertInvocations(
				_valuesToString(_companyId, _FEATURE_FLAG_KEY_1, _value1));

			FeatureFlagTestUtil.setFeatureFlagValue(
				_companyId, !_value1, _FEATURE_FLAG_KEY_1);

			FeatureFlagTestUtil.setFeatureFlagValue(
				_companyId, RandomTestUtil.randomBoolean(),
				_FEATURE_FLAG_KEY_2);

			testFeatureFlagListener.assertInvocations(
				_valuesToString(_companyId, _FEATURE_FLAG_KEY_1, !_value1));
		}
	}

	private String _valuesToString(
		long companyId, String featureFlagKey, boolean enabled) {

		return String.format(
			"companyId: %d, featureFlagKey: %s, enabled: %b", companyId,
			featureFlagKey, enabled);
	}

	private static final String _FEATURE_FLAG_KEY_1 = "TEST-123";

	private static final String _FEATURE_FLAG_KEY_2 = "TEST-456";

	private static final String _FEATURE_FLAG_KEY_SYSTEM = "TEST-000";

	private static long _companyId;

	private BundleContext _bundleContext;
	private boolean _value1;
	private boolean _value2;
	private boolean _valueSystem;

	private class TestFeatureFlagListener
		implements FeatureFlagListener, SafeCloseable {

		public TestFeatureFlagListener(String... featureFlagKeys) {
			_serviceRegistration = _bundleContext.registerService(
				FeatureFlagListener.class, this,
				HashMapDictionaryBuilder.put(
					"featureFlagKey",
					() -> {
						if (featureFlagKeys.length == 0) {
							return null;
						}
						else if (featureFlagKeys.length == 1) {
							return featureFlagKeys[0];
						}

						return featureFlagKeys;
					}
				).build());
		}

		public void assertInvocations(String... expectedStrings) {
			Assert.assertEquals(
				_strings.toString(), expectedStrings.length, _strings.size());

			for (int i = 0; i < expectedStrings.length; i++) {
				Assert.assertEquals(expectedStrings[i], _strings.get(i));
			}

			_strings.clear();
		}

		@Override
		public void close() {
			_serviceRegistration.unregister();
		}

		@Override
		public void onValue(
			long companyId, String featureFlagKey, boolean enabled) {

			_strings.add(_valuesToString(companyId, featureFlagKey, enabled));
		}

		private final ServiceRegistration<FeatureFlagListener>
			_serviceRegistration;
		private final List<String> _strings = new ArrayList<>();

	}

}