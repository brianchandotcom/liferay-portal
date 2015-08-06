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

package com.liferay.dynamic.data.mapping.type.text;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class TextDDMFormFieldValueAccessorTest {

	@Test
	public void testGetWithLocalizedValue() {
		Value value = new LocalizedValue(LocaleUtil.US);

		value.addString(LocaleUtil.BRAZIL, "Portuguese value");
		value.addString(LocaleUtil.US, "English value");

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue("Text", value);

		TextDDMFormFieldValueAccessor textDDMFormFieldValueAccessor =
			new TextDDMFormFieldValueAccessor(LocaleUtil.US);

		Assert.assertEquals(
			"English value",
			textDDMFormFieldValueAccessor.get(ddmFormFieldValue));

		textDDMFormFieldValueAccessor = new TextDDMFormFieldValueAccessor(
			LocaleUtil.BRAZIL);

		Assert.assertEquals(
			"Portuguese value",
			textDDMFormFieldValueAccessor.get(ddmFormFieldValue));
	}

	@Test
	public void testGetWithUnlocalizedValue() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Text", new UnlocalizedValue("Scott Joplin"));

		TextDDMFormFieldValueAccessor textDDMFormFieldValueAccessor =
			new TextDDMFormFieldValueAccessor(LocaleUtil.US);

		Assert.assertEquals(
			"Scott Joplin",
			textDDMFormFieldValueAccessor.get(ddmFormFieldValue));
	}

}