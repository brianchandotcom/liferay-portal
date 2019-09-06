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

package com.liferay.fragment.util;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryConfigUtilTest {

	@BeforeClass
	public static void setUpClass() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testGetConfigurationDefaultValuesJSONObject() throws Exception {
		JSONObject configurationDefaultValuesJSONObject =
			FragmentEntryConfigUtil.getConfigurationDefaultValuesJSONObject(
				_read("configuration.json"));

		JSONObject expectedConfigurationDefaultValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_read("expected-configuration-default-values.json"));

		Assert.assertEquals(
			expectedConfigurationDefaultValuesJSONObject.toJSONString(),
			configurationDefaultValuesJSONObject.toJSONString());
	}

	@Test
	public void testTranslateConfigurationEn() throws Exception {
		_testTranslateConfiguration("en");
	}

	@Test
	public void testTranslateConfigurationEs() throws Exception {
		_testTranslateConfiguration("es");
	}

	private ResourceBundle _getResourceBundle(String language) {
		Class<?> clazz = getClass();

		Package pkg = clazz.getPackage();

		return ResourceBundleUtil.getBundle(
			pkg.getName() + ".dependencies.content.Language",
			new Locale(language), clazz);
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _testTranslateConfiguration(String language) throws Exception {
		JSONObject configurationJSONOjbect = JSONFactoryUtil.createJSONObject(
			_read("configuration_untranslated.json"));

		JSONObject expectedConfigurationTranslatedJSONObject =
			JSONFactoryUtil.createJSONObject(
				_read(
					String.format(
						"expected_configuration_translated_%s.json",
						language)));

		Assert.assertEquals(
			expectedConfigurationTranslatedJSONObject.toJSONString(),
			FragmentEntryConfigUtil.translateConfiguration(
				configurationJSONOjbect, _getResourceBundle(language)));
	}

}