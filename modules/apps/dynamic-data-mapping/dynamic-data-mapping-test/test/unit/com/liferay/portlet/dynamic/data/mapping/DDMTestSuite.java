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

package com.liferay.portlet.dynamic.data.mapping;

import com.liferay.portlet.dynamic.data.mapping.io.DDMFormJSONDeserializerTest;
import com.liferay.portlet.dynamic.data.mapping.io.DDMFormJSONSerializerTest;
import com.liferay.portlet.dynamic.data.mapping.io.DDMFormLayoutJSONDeserializerTest;
import com.liferay.portlet.dynamic.data.mapping.io.DDMFormLayoutJSONSerializerTest;
import com.liferay.portlet.dynamic.data.mapping.io.DDMFormValuesJSONDeserializerTest;
import com.liferay.portlet.dynamic.data.mapping.io.DDMFormValuesJSONSerializerTest;
import com.liferay.portlet.dynamic.data.mapping.io.DDMFormXSDDeserializerTest;
import com.liferay.portlet.dynamic.data.mapping.model.impl.DDMStructureImplTest;
import com.liferay.portlet.dynamic.data.mapping.render.DDMFormFieldValueRendererTest;
import com.liferay.portlet.dynamic.data.mapping.storage.DDMFormFieldValueTest;
import com.liferay.portlet.dynamic.data.mapping.storage.DDMFormValuesTest;
import com.liferay.portlet.dynamic.data.mapping.storage.DDMFormValuesValidatorTest;
import com.liferay.portlet.dynamic.data.mapping.storage.GeolocationFieldRendererTest;
import com.liferay.portlet.dynamic.data.mapping.storage.LocalizedValueTest;
import com.liferay.portlet.dynamic.data.mapping.storage.UnlocalizedValueTest;
import com.liferay.portlet.dynamic.data.mapping.util.DDMFormFactoryTest;
import com.liferay.portlet.dynamic.data.mapping.util.DDMFormTemplateSynchonizerTest;
import com.liferay.portlet.dynamic.data.mapping.util.DDMFormValuesToFieldsConverterTest;
import com.liferay.portlet.dynamic.data.mapping.util.DDMFormValuesTransformerTest;
import com.liferay.portlet.dynamic.data.mapping.util.DDMImplTest;
import com.liferay.portlet.dynamic.data.mapping.util.DDMXMLImplTest;
import com.liferay.portlet.dynamic.data.mapping.util.FieldsToDDMFormValuesConverterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
@RunWith(Suite.class)
@SuiteClasses(
	{
		DDMFormFactoryTest.class, DDMFormFieldValueRendererTest.class,
		DDMFormFieldValueTest.class, DDMFormJSONDeserializerTest.class,
		DDMFormJSONSerializerTest.class,
		DDMFormLayoutJSONDeserializerTest.class,
		DDMFormLayoutJSONSerializerTest.class,
		DDMFormTemplateSynchonizerTest.class,
		DDMFormValuesJSONDeserializerTest.class,
		DDMFormValuesJSONSerializerTest.class, DDMFormValuesTest.class,
		DDMFormValuesToFieldsConverterTest.class,
		DDMFormValuesTransformerTest.class, DDMFormValuesValidatorTest.class,
		DDMFormXSDDeserializerTest.class, DDMFormXSDDeserializerTest.class,
		DDMImplTest.class, DDMStructureImplTest.class, DDMXMLImplTest.class,
		FieldsToDDMFormValuesConverterTest.class,
		GeolocationFieldRendererTest.class, LocalizedValueTest.class,
		UnlocalizedValueTest.class
	}
)
public class DDMTestSuite {
}