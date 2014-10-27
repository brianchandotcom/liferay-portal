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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONDeserializerTest;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONSerializerTest;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONDeserializerTest;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONSerializerTest;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerTest;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImplTest;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldValueRendererTest;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValuesTest;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValuesValidatorTest;
import com.liferay.portlet.dynamicdatamapping.storage.GeolocationFieldRendererTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormTemplateSynchonizerTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormValuesToFieldsConverterTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormValuesTransformerTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMImplTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLImplTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMXSDImplTest;
import com.liferay.portlet.dynamicdatamapping.util.FieldsToDDMFormValuesConverterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
@RunWith(Suite.class)
@SuiteClasses(
	{
		DDMFormFieldValueRendererTest.class, DDMFormJSONDeserializerTest.class,
		DDMFormJSONSerializerTest.class, DDMFormTemplateSynchonizerTest.class,
		DDMFormValuesJSONDeserializerTest.class,
		DDMFormValuesJSONSerializerTest.class, DDMFormValuesTest.class,
		DDMFormValuesToFieldsConverterTest.class,
		DDMFormValuesTransformerTest.class, DDMFormValuesValidatorTest.class,
		DDMFormXSDDeserializerTest.class, DDMFormXSDDeserializerTest.class,
		DDMImplTest.class, DDMStructureImplTest.class, DDMXMLImplTest.class,
		DDMXSDImplTest.class, FieldsToDDMFormValuesConverterTest.class,
		GeolocationFieldRendererTest.class
	})
public class DDMTestSuite {
}