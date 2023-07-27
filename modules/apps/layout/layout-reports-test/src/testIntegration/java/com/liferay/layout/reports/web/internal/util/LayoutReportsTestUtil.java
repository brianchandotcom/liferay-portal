/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.reports.web.internal.util;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.GroupConfigurationTemporarySwapper;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

/**
 * @author AlejandroTardín
 */
public class LayoutReportsTestUtil {

	public static void withLayoutReportsGooglePageSpeedCompanyConfiguration(
			long companyId, boolean enabled,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						companyId,
						"com.liferay.layout.reports.web.internal." +
							"configuration.LayoutReportsGooglePageSpeed" +
								"CompanyConfiguration",
						HashMapDictionaryBuilder.<String, Object>put(
							"enabled", enabled
						).build())) {

			unsafeRunnable.run();
		}
	}

	public static void withLayoutReportsGooglePageSpeedConfiguration(
			boolean enabled, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", enabled
					).build())) {

			unsafeRunnable.run();
		}
	}

	public static void withLayoutReportsGooglePageSpeedGroupConfiguration(
			String apiKey, boolean enabled, long groupId,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						groupId,
						"com.liferay.layout.reports.web.internal." +
							"configuration.LayoutReportsGooglePageSpeed" +
								"GroupConfiguration",
						HashMapDictionaryBuilder.<String, Object>put(
							"apiKey", apiKey
						).put(
							"enabled", enabled
						).build())) {

			unsafeRunnable.run();
		}
	}

}