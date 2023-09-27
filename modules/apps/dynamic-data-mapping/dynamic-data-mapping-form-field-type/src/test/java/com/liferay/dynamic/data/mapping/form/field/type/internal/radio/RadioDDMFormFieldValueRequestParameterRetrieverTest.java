/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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