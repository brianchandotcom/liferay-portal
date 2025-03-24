/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.dynamic.data.mapping.form.field.type.internal.multi.select.picklist;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Leite
 */
public class MultiselectPicklistDDMFormFieldValueAccessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetValue() throws Exception {
		JSONArray expectedJSONArray = _jsonFactory.createJSONArray("[value]");

		JSONArray actualJSONArray =
			_multiselectPicklistDDMFormFieldValueAccessor.getValue(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					RandomTestUtil.randomString(),
					new UnlocalizedValue(expectedJSONArray.toString())),
				LocaleUtil.US);

		Assert.assertEquals(
			expectedJSONArray.toString(), actualJSONArray.toString());
	}

	@Test
	public void testIsEmpty() {
		Assert.assertTrue(
			_multiselectPicklistDDMFormFieldValueAccessor.isEmpty(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					RandomTestUtil.randomString(), new UnlocalizedValue("[]")),
				LocaleUtil.US));
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private final MultiselectPicklistDDMFormFieldValueAccessor
		_multiselectPicklistDDMFormFieldValueAccessor =
			new MultiselectPicklistDDMFormFieldValueAccessor();

}