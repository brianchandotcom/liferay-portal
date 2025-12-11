/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.configuration.admin.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Thiago Buarque
 */
@RunWith(Arquillian.class)
public class LanguagesSiteSettingsConfigurationScreenWrapperTest {

	@Test
	public void testExportImportProperties() {
		Bundle bundle = FrameworkUtil.getBundle(
			LanguagesSiteSettingsConfigurationScreenWrapperTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		try (ServiceTrackerMap<String, ConfigurationScreen> serviceTrackerMap =
				ServiceTrackerMapFactory.openSingleValueMap(
					bundleContext, ConfigurationScreen.class, null,
					(serviceReference, emitter) -> {
						ConfigurationScreen configurationScreen =
							bundleContext.getService(serviceReference);

						emitter.emit(configurationScreen.getKey());
					})) {

			Group group = GroupTestUtil.addGroup();

			String locales = StringUtil.merge(
				LocaleUtil.toLanguageIds(LanguageUtil.getAvailableLocales()));

			Dictionary<String, Object> properties =
				HashMapDictionaryBuilder.<String, Object>put(
					GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES, "true"
				).put(
					PropsKeys.LOCALES, locales
				).put(
					"languageId", "en_US"
				).build();

			ConfigurationScreen configurationScreen =
				serviceTrackerMap.getService("site-configuration-languages");

			configurationScreen.importProperties(
				properties, group.getGroupId());

			properties = configurationScreen.exportProperties(
				group.getGroupId());

			Assert.assertEquals(
				"true",
				properties.get(
					GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES));
			Assert.assertEquals(locales, properties.get(PropsKeys.LOCALES));
			Assert.assertEquals("en_US", properties.get("languageId"));

			properties.put("languageId", "invalid_language");

			configurationScreen.importProperties(
				properties, group.getGroupId());
		}
		catch (Exception exception) {
			Assert.assertTrue(exception instanceof LocaleException);

			LocaleException localeException = (LocaleException)exception;

			Set<Locale> sourceAvailableLocales =
				(Set<Locale>)localeException.getSourceAvailableLocales();

			Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

			ArrayUtil.equalsIgnoreCase(
				ArrayUtil.toStringArray(availableLocales.toArray()),
				ArrayUtil.toStringArray(sourceAvailableLocales.toArray()));
		}
	}

}