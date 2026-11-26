/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.internal.osgi.commands.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge García Jiménez
 */
@RunWith(Arquillian.class)
public class FIPSOSGiCommandsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(
			_KEY_MANAGER_CONFIGURATION_PID);
	}

	@Test
	public void testCheck() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"PASSWORDS_DEFAULT_POLICY_MIN_LENGTH", 13)) {

			String message = _check();

			Assert.assertFalse(message, message.contains("key encryption key"));
			Assert.assertTrue(
				message,
				message.endsWith(
					"Only the FIPS boot checks the JVM, including the " +
						"security providers, the SecureRandom provider, and " +
							"the TLS and certificate revocation properties"));
			Assert.assertTrue(
				message,
				message.contains(
					StringBundler.concat(
						"\tPasswords: FIPS mode requires the property \"",
						PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_LENGTH,
						"\" to be at least 14")));
		}

		ConfigurationTestUtil.saveConfiguration(
			_KEY_MANAGER_CONFIGURATION_PID,
			HashMapDictionaryBuilder.<String, Object>put(
				"companyKEKIdentifier", RandomTestUtil.randomString()
			).build());

		String message = _check();

		Assert.assertTrue(
			message,
			message.contains(
				StringBundler.concat(
					"\tKey Encryption Key: Unable to reach the key encryption ",
					"key of company ", TestPropsValues.getCompanyId(), ": ")));
	}

	private String _check() {
		return ReflectionTestUtil.invoke(
			_fipsOSGiCommands, "check", new Class<?>[0]);
	}

	private static final String _KEY_MANAGER_CONFIGURATION_PID =
		"com.liferay.portal.security.key.internal.profile.configuration." +
			"KeyManagerConfiguration";

	@Inject(
		filter = "component.name=com.liferay.portal.security.fips.internal.osgi.commands.FIPSOSGiCommands",
		type = Inject.NoType.class
	)
	private Object _fipsOSGiCommands;

}