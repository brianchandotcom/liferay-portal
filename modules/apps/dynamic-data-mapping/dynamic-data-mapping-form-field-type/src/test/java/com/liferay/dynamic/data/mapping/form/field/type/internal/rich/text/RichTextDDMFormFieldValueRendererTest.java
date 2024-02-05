/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.rich.text;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class RichTextDDMFormFieldValueRendererTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_richTextDDMFormFieldValueRenderer, "_htmlParser", _htmlParser);
	}

	@After
	public void tearDown() {
		_htmlUtilMockedStatic.close();
	}

	@Test
	public void testRender() {
		String value = RandomTestUtil.randomString();

		Mockito.when(
			_htmlParser.extractText(Mockito.anyString())
		).thenReturn(
			value
		);

		String escapedValue = RandomTestUtil.randomString();

		_htmlUtilMockedStatic.when(
			() -> HtmlUtil.escape(value)
		).thenReturn(
			escapedValue
		);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setValue(
			new UnlocalizedValue(RandomTestUtil.randomString()));

		Assert.assertEquals(
			escapedValue,
			_richTextDDMFormFieldValueRenderer.render(ddmFormFieldValue, null));
	}

	private final HtmlParser _htmlParser = Mockito.mock(HtmlParser.class);
	private final MockedStatic<HtmlUtil> _htmlUtilMockedStatic =
		Mockito.mockStatic(HtmlUtil.class);
	private final RichTextDDMFormFieldValueRenderer
		_richTextDDMFormFieldValueRenderer =
			new RichTextDDMFormFieldValueRenderer();

}