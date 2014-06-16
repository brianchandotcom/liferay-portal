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

import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImplTest;
import com.liferay.portlet.dynamicdatamapping.storage.GeolocationFieldRendererTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormJSONDeserializerTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormJSONSerializerTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormXSDDeserializerTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLImplTest;
import com.liferay.portlet.dynamicdatamapping.util.DDMXSDImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
@RunWith(Suite.class)
@SuiteClasses(
	{
		DDMFormJSONDeserializerTest.class, DDMFormJSONSerializerTest.class,
		DDMFormXSDDeserializerTest.class, DDMFormXSDDeserializerTest.class,
		DDMStructureImplTest.class, DDMXMLImplTest.class, DDMXSDImplTest.class,
		GeolocationFieldRendererTest.class
	})
public class DDMTestSuite {
}