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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTest;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.core.classloader.annotations.PrepareForTest;
@PrepareForTest({DDMStructureLocalServiceUtil.class})
public class DDMStructureImplTest extends BaseDDMTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		setUpDDMStructureLocalServiceUtil();
	}

	@Test
	public final void testHasField() throws Exception {

		DDMStructureImpl parentStructure = _createStructure("parent","field01",
		"field02");

		parentStructure.setDocument(_document);

		DDMStructureImpl childStructure = _createStructure("child","field03",
		"field04");

		childStructure.setDocument(_document);
		childStructure.setParentStructureId(parentStructure.getStructureId());

		Assert.assertTrue(parentStructure.hasField("field01"));
		Assert.assertTrue(parentStructure.hasField("field02"));
		Assert.assertFalse(parentStructure.hasField("field03"));
		Assert.assertFalse(parentStructure.hasField("field04"));

		Assert.assertTrue(childStructure.hasField("field01"));
		Assert.assertTrue(childStructure.hasField("field02"));
		Assert.assertTrue(childStructure.hasField("field03"));
		Assert.assertTrue(childStructure.hasField("field04"));
		Assert.assertFalse(childStructure.hasField("fieldNotFound"));
	}

	protected void setUpDDMStructureLocalServiceUtil() throws PortalException,
	SystemException {

		mockStatic(DDMStructureLocalServiceUtil.class);

		when(
			DDMStructureLocalServiceUtil.getStructure(Matchers.anyLong())
		).then(
			new Answer<DDMStructure>() {
				@Override
				public DDMStructure answer(InvocationOnMock invocation)
					throws Throwable {

					Object[] args = invocation.getArguments();

					Long structureId = (Long)args[0];

					return structuresMap.get(structureId);
				}
			}
		);
	}

	private Map<String, Map<String, Map<String, String>>>
		_createSampleLocalizedFields(String... fieldNames) {

		Map<String, Map<String, Map<String, String>>> fieldsMap =
			new HashMap<String, Map<String, Map<String, String>>>();

		Map<String, Map<String, String>> defaultLocaleFields =
			new HashMap<String, Map<String, String>>();

		fieldsMap.put(LocaleUtil.getSiteDefault().toString(),
		defaultLocaleFields);

		if (fieldNames != null) {
			for (String fieldName : fieldNames) {
				defaultLocaleFields.put(fieldName, null);
			}
		}

		return fieldsMap;
	}

	private DDMStructureImpl _createStructure(String name, String... fieldNames)
	{

		DDMStructureImpl structure = new DDMStructureImpl();

		structure.setName(name);
		structure.setStructureId(System.currentTimeMillis());
		structure.setLocalizedFieldsMap(_createSampleLocalizedFields(fieldNames)
			);

		structuresMap.put(structure.getStructureId(), structure);

		return structure;
	}

	private Map<Long, DDMStructure> structuresMap =
		new HashMap<Long, DDMStructure>();
}