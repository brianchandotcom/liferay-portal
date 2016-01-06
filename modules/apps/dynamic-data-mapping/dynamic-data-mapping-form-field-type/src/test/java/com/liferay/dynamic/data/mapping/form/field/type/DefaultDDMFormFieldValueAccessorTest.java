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

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DefaultDDMFormFieldValueAccessorTest {

	@Test
	public void testGetWithLocalizedValue() {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("Text");

		Value value = new LocalizedValue(LocaleUtil.US);

		value.addString(LocaleUtil.BRAZIL, "Portuguese value");
		value.addString(LocaleUtil.US, "English value");

		ddmFormFieldValue.setValue(value);

		Assert.assertEquals(
			"Portuguese value",
			_defaultDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.BRAZIL));

		Assert.assertEquals(
			"English value",
			_defaultDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.US));
	}

	@Test
	public void testGetWithUnlocalizedValue() {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("Text");
		ddmFormFieldValue.setValue(new UnlocalizedValue("Scott Joplin"));

		Assert.assertEquals(
			"Scott Joplin",
			_defaultDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.US));
	}

	private final DefaultDDMFormFieldValueAccessor
		_defaultDDMFormFieldValueAccessor =
			new DefaultDDMFormFieldValueAccessor();

}