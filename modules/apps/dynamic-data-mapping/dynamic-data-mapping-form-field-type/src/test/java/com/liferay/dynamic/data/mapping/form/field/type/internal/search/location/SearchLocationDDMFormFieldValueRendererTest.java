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

package com.liferay.dynamic.data.mapping.form.field.type.internal.search.location;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.HtmlImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rodrigo Paulino
 */
public class SearchLocationDDMFormFieldValueRendererTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpHtmlUtil();
	}

	@Before
	public void setUp() {
		_searchLocationDDMFormFieldValueRenderer =
			new SearchLocationDDMFormFieldValueRenderer();

		ReflectionTestUtil.setFieldValue(
			_searchLocationDDMFormFieldValueRenderer, "_jsonFactory",
			new JSONFactoryImpl());
	}

	@Test
	public void testRenderWithDDMFormFieldName() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field",
				DDMFormValuesTestUtil.createLocalizedValue(
					JSONUtil.put(
						"city", "Recife"
					).toString(),
					LocaleUtil.US));

		Assert.assertEquals(
			"{&#34;city&#34;:&#34;Recife&#34;}",
			_searchLocationDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));
	}

	@Test
	public void testRenderWithDDMFormFieldNameAndCityValue() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field",
				DDMFormValuesTestUtil.createLocalizedValue(
					JSONUtil.put(
						"city", "Recife"
					).toString(),
					LocaleUtil.US));

		Assert.assertEquals(
			"Recife",
			_searchLocationDDMFormFieldValueRenderer.render(
				"city", ddmFormFieldValue, LocaleUtil.US));
	}

	@Test
	public void testRenderWithDDMFormFieldNameAndWithoutCityValue() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field",
				DDMFormValuesTestUtil.createLocalizedValue(
					JSONUtil.put(
						"city", StringPool.BLANK
					).toString(),
					LocaleUtil.US));

		Assert.assertEquals(
			StringPool.BLANK,
			_searchLocationDDMFormFieldValueRenderer.render(
				"city", ddmFormFieldValue, LocaleUtil.US));
	}

	@Test
	public void testRenderWithDDMFormFieldNameAndWithoutValue() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field",
				DDMFormValuesTestUtil.createLocalizedValue("", LocaleUtil.US));

		Assert.assertEquals(
			StringPool.BLANK,
			_searchLocationDDMFormFieldValueRenderer.render(
				"city", ddmFormFieldValue, LocaleUtil.US));
	}

	private static void _setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	private SearchLocationDDMFormFieldValueRenderer
		_searchLocationDDMFormFieldValueRenderer;

}