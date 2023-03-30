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

package com.liferay.segments.context.vocabulary.internal.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Yurena Cabrera
 */
@RunWith(Arquillian.class)
public class SegmentsContextVocabularyConfigurationTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Test
	public void testAddDuplicatedCompanySegmentsContextVocabularyConfiguration()
		throws Exception {

		Locale themeDisplayLocale = LocaleThreadLocal.getThemeDisplayLocale();

		try {
			LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);

			_configuration1 = _configurationAdmin.createFactoryConfiguration(
				_SEGMENTS_CONTEXT_VOCABULARY_CONFIGURATION_PID,
				StringPool.QUESTION);

			_configuration1.update(_PROPERTIES1);

			Dictionary<String, Object> properties =
				_configuration1.getProperties();

			Assert.assertEquals(
				_PROPERTIES1.get("assetVocabularyName"),
				properties.get("assetVocabularyName"));
			Assert.assertEquals(
				_PROPERTIES1.get("entityFieldName"),
				properties.get("entityFieldName"));
			Assert.assertEquals(
				_PROPERTIES1.get("companyId"), properties.get("companyId"));

			_configuration2 = _configurationAdmin.createFactoryConfiguration(
				_SEGMENTS_CONTEXT_VOCABULARY_CONFIGURATION_PID,
				StringPool.QUESTION);

			try {
				_configuration2.update(_PROPERTIES2);

				Assert.fail();
			}
			catch (ConfigurationModelListenerException
						configurationModelListenerException) {

				Assert.assertEquals(
					StringBundler.concat(
						"This session property is already linked to one ",
						"vocabulary. Remove the linked vocabulary before ",
						"linking it to a new one, or choose another session ",
						"property name."),
					configurationModelListenerException.causeMessage);
			}
		}
		finally {
			LocaleThreadLocal.setThemeDisplayLocale(themeDisplayLocale);

			if (_configuration1 != null) {
				_configuration1.delete();
			}

			if (_configuration2 != null) {
				_configuration2.delete();
			}
		}
	}

	private static final Dictionary<String, Object> _PROPERTIES1 =
		HashMapDictionaryBuilder.<String, Object>put(
			"assetVocabularyName", "assetVocabularyName"
		).put(
			"companyId", "987"
		).put(
			"entityFieldName", "entityFieldName"
		).build();

	private static final Dictionary<String, Object> _PROPERTIES2 =
		HashMapDictionaryBuilder.<String, Object>put(
			"assetVocabularyName", "differentAssetVocabularyName"
		).put(
			"companyId", "987"
		).put(
			"entityFieldName", "entityFieldName"
		).build();

	private static final String _SEGMENTS_CONTEXT_VOCABULARY_CONFIGURATION_PID =
		"com.liferay.segments.context.vocabulary.internal.configuration." +
			"SegmentsContextVocabularyConfiguration";

	private Configuration _configuration1;
	private Configuration _configuration2;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

}