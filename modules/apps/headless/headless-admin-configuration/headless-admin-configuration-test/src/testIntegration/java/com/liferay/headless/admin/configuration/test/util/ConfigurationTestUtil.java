/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.test.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Thiago Buarque
 */
public class ConfigurationTestUtil {

	public static final String TEST_CONFIGURATION_PID =
		"com.liferay.headless.admin.configuration.sample.configuration." +
			"TestConfiguration";

	public static final String TEST_FACTORY_CONFIGURATION_PID =
		"com.liferay.headless.admin.configuration.sample.configuration." +
			"TestFactoryConfiguration";

	public static Map<String, Object> getRandomConfigurationScreenProperties(
		String scopeKey, String scopeValue) {

		return HashMapBuilder.<String, Object>put(
			scopeKey,
			() -> {
				if (scopeKey != null) {
					return scopeValue;
				}

				return scopeValue;
			}
		).put(
			RandomTestUtil.randomString(), RandomTestUtil.randomString()
		).build();
	}

	public static Map<String, Object> getRandomTestConfigurationProperties(
		String scopeKey, String scopeValue) {

		return HashMapBuilder.<String, Object>put(
			scopeKey,
			() -> {
				if (scopeKey != null) {
					return scopeValue;
				}

				return scopeValue;
			}
		).put(
			"arrayKey", new String[] {RandomTestUtil.randomString()}
		).put(
			"passwordStringKey", RandomTestUtil.randomString()
		).put(
			"requiredBooleanKey", RandomTestUtil.randomBoolean()
		).build();
	}

	public static Map<String, Object>
		getRandomTestFactoryConfigurationProperties(
			String scopeKey, String scopeValue) {

		return HashMapBuilder.<String, Object>put(
			scopeKey,
			() -> {
				if (scopeKey != null) {
					return scopeValue;
				}

				return scopeValue;
			}
		).put(
			"stringKey", RandomTestUtil.randomString()
		).build();
	}

}