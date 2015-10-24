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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormTransformerTest {

	@Test
	public void testGetFields() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(new DDMFormField("FirstName", "text"));
		ddmForm.addDDMFormField(new DDMFormField("LastName", "text"));

		Map<String, String> renderedDDMFormFieldsMap = new HashMap<>();

		renderedDDMFormFieldsMap.put("FirstName", "Joe");
		renderedDDMFormFieldsMap.put("LastName", "Bloggs");

		DDMFormTransformer ddmFormTransformer = new DDMFormTransformer(
			ddmForm, renderedDDMFormFieldsMap);

		List<String> fields = ddmFormTransformer.getFields();

		Assert.assertEquals("Joe", fields.get(0));
		Assert.assertEquals("Bloggs", fields.get(1));
	}

}