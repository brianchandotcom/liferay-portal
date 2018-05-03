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

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Marcellus Tavares
 */
public class DefaultDDMFormFieldValueRendererTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		setUpHtmlUtil();
	}

	@Test
	public void testRender() {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("Text");
		ddmFormFieldValue.setValue(
			new UnlocalizedValue(StringUtil.randomString()));

		_defaultDDMFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Mockito.verify(
			_html
		).escape(
			Matchers.anyString()
		);
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(_html);
	}

	private final DefaultDDMFormFieldValueRenderer
		_defaultDDMFormFieldValueRenderer =
			new DefaultDDMFormFieldValueRenderer();

	@Mock
	private Html _html;

}