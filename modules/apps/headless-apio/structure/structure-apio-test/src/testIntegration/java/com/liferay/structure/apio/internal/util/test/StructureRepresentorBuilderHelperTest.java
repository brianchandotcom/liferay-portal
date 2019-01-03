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

package com.liferay.structure.apio.internal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.structure.apio.architect.util.StructureRepresentorBuilderHelper;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class StructureRepresentorBuilderHelperTest {

	@Test
	public void testBooleanFieldDataType() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("MyBoolean", "checkbox");

		ddmFormField.setDataType("boolean");

		String dataType = _getDDMFormFieldDataType(ddmFormField);

		Assert.assertEquals("boolean", dataType);
	}

	@Test
	public void testBooleanFieldInputControl() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("MyBoolean", "checkbox");

		String inputControl = _getDDMFormFieldInputControl(ddmFormField);

		Assert.assertEquals("checkbox", inputControl);
	}

	private String _getDDMFormFieldDataType(DDMFormField ddmFormField)
		throws Exception {

		StructureRepresentorBuilderHelper structureRepresentorBuilderHelper =
			_getStructureRepresentorBuilderHelper();

		Class<? extends StructureRepresentorBuilderHelper> clazz =
			structureRepresentorBuilderHelper.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getDDMFormFieldDataType", DDMFormField.class);

		method.setAccessible(true);

		return (String)method.invoke(
			structureRepresentorBuilderHelper, ddmFormField);
	}

	private String _getDDMFormFieldInputControl(DDMFormField ddmFormField)
		throws Exception {

		StructureRepresentorBuilderHelper structureRepresentorBuilderHelper =
			_getStructureRepresentorBuilderHelper();

		Class<? extends StructureRepresentorBuilderHelper> clazz =
			structureRepresentorBuilderHelper.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getDDMFormFieldInputControl", DDMFormField.class);

		method.setAccessible(true);

		return (String)method.invoke(
			structureRepresentorBuilderHelper, ddmFormField);
	}

	private StructureRepresentorBuilderHelper
			_getStructureRepresentorBuilderHelper()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Collection<StructureRepresentorBuilderHelper> collection =
			registry.getServices(
				StructureRepresentorBuilderHelper.class,
				"(component.name=com.liferay.structure.apio.internal.util." +
					"StructureRepresentorBuilderHelperImpl)");

		Iterator<StructureRepresentorBuilderHelper> iterator =
			collection.iterator();

		return iterator.next();
	}

}