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

package com.liferay.dynamic.data.mapping.form.field.type.internal.radio;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class RadioDDMFormFieldValueRequestParameterRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_radioDDMFormFieldValueRequestParameterRetriever =
			new RadioDDMFormFieldValueRequestParameterRetriever();

		ReflectionTestUtil.setFieldValue(
			_radioDDMFormFieldValueRequestParameterRetriever, "_jsonFactory",
			new JSONFactoryImpl());
	}

	@Test
	public void testEmptySubmitWithoutPredefinedValue() {
		String fieldValue =
			_radioDDMFormFieldValueRequestParameterRetriever.get(
				new MockHttpServletRequest(), "radio0", "[]");

		Assert.assertEquals(StringPool.BLANK, fieldValue);
	}

	private RadioDDMFormFieldValueRequestParameterRetriever
		_radioDDMFormFieldValueRequestParameterRetriever;

}