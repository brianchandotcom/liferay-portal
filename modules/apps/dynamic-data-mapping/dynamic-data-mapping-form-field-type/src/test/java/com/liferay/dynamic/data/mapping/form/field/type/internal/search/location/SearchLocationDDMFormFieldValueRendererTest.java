/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			_searchLocationDDMFormFieldValueRenderer, "_html", new HtmlImpl());
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