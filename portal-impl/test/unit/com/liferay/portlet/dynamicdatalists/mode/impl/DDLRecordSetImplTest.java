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

package com.liferay.portlet.dynamicdatalists.mode.impl;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetImpl;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTest;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDLRecordSetImplTest extends BaseDDMTest {

	@Before
	@Override
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testGetDDMStructure() throws Exception {
		Document document = createDocument("Text 1", "Text 2", "Text 3");

		DDMStructure ddmStructure = createStructure(
			"Test Structure", "Text 1", "Text 2", "Text 3");

		DDLRecordSet recordSet = createRecordSet(
			ddmStructure.getStructureId(), "Test Record Set");

		Element rootElement = document.getRootElement();

		Element dynamicElement = rootElement.element("dynamic-element");

		rootElement.remove(dynamicElement);

		DDMTemplate template = createTemplate(
			ddmStructure.getStructureId(), "Test Form Template",
			document.asXML());

		Set<String> fieldNames = ddmStructure.getFieldNames();

		DDMStructure recordSetDDMStructure = recordSet.getDDMStructure(
			template.getTemplateId());

		if (fieldNames.equals(recordSetDDMStructure.getFieldNames())) {
			Assert.fail();
		}

		recordSetDDMStructure = recordSet.getDDMStructure();

		if (!fieldNames.equals(recordSetDDMStructure.getFieldNames())) {
			Assert.fail();
		}
	}

	protected DDLRecordSet createRecordSet(long ddmStructureId, String name) {
		DDLRecordSet recordSet = new DDLRecordSetImpl();

		recordSet.setDDMStructureId(ddmStructureId);
		recordSet.setName(name);

		return recordSet;
	}

}