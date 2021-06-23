/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.reports.web.internal.util;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.GroupConfigurationTemporarySwapper;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
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
						).build(),
						SettingsFactoryUtil.getSettingsFactory())) {

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
						).build(),
						SettingsFactoryUtil.getSettingsFactory())) {

			unsafeRunnable.run();
		}
	}

}