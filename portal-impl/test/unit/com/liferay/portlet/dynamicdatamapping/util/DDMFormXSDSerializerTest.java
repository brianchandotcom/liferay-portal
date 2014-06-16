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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Pablo Carvalho
 */
@PrepareForTest({DDMFormXSDSerializerUtil.class, StringUtil.class})
public class DDMFormXSDSerializerTest extends BaseDDMFormSerializer {

	@Before
	@Override
	public void setUp() {
		super.setUp();

		setUpDDMFormToXSDSerializer();
		setUpStringUtil();
	}

	@Test
	public void testDDMFormSerialization() throws Exception {
		DDMForm ddmForm = createDDMForm();

		String xsd = DDMFormXSDSerializerUtil.serialize(ddmForm);

		testXSDMatchesExpected(xsd);
	}

	protected void setUpDDMFormToXSDSerializer() {
		spy(DDMFormXSDSerializerUtil.class);

		when(
			DDMFormXSDSerializerUtil.getDDMFormXSDSerializer()
		).thenReturn(
			_ddmFormXSDSerializer
		);
	}

	protected void setUpStringUtil() {
		spy(StringUtil.class);

		when(
			StringUtil.randomId()
		).thenReturn(
			"1234"
		);
	}

	protected void testXSDMatchesExpected(String actualXSD) throws Exception {
		String expectedXSD = read("ddm-form-xsd-serializer-test-data.xml");

		Map<String, Map<String, String>> expectedMap =
			DDMStructureTestUtil.getXSDMap(expectedXSD);

		Map<String, Map<String, String>> actualMap =
			DDMStructureTestUtil.getXSDMap(actualXSD);

		Assert.assertEquals(expectedMap, actualMap);
	}

	private DDMFormXSDSerializer _ddmFormXSDSerializer =
		new DDMFormXSDSerializerImpl();

}