/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.BaseDDLServiceTestCase;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DDLRecordSetImplTest extends BaseDDLServiceTestCase {

	@Test
	public void testGetDDMStructure() throws Exception {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", "en_US");
		rootElement.addAttribute("default-locale", "en_US");

		addTextElement(rootElement, ServiceTestUtil.randomString(), "Text 1");
		addTextElement(rootElement, ServiceTestUtil.randomString(), "Text 2");
		addTextElement(rootElement, ServiceTestUtil.randomString(), "Text 3");

		DDMStructure ddmStructure = addStructure(
			PortalUtil.getClassNameId(DDLRecordSet.class), null,
			"Test Structure", document.asXML(), StorageType.XML.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);

		DDLRecordSet ddlRecordSet = addRecordSet(ddmStructure.getStructureId());

		// Retrieve first element

		Element dynamicElement = rootElement.element("dynamic-element");

		rootElement.remove(dynamicElement);

		DDMTemplate ddmTemplate = addFormTemplate(
			ddmStructure.getStructureId(), "Test Form Template",
			document.asXML());

		Set<String> fieldNames = ddmStructure.getFieldNames();

		DDMStructure ddlRecordSetStructure = ddlRecordSet.getDDMStructure(
			ddmTemplate.getTemplateId());

		if (fieldNames.equals(ddlRecordSetStructure.getFieldNames())) {
			Assert.fail();
		}

		ddlRecordSetStructure = ddlRecordSet.getDDMStructure();

		if (!fieldNames.equals(ddlRecordSetStructure.getFieldNames())) {
			Assert.fail();
		}
	}

	protected void addTextElement(Element element, String name, String label) {
		Element dynamicElement = element.addElement("dynamic-element");

		dynamicElement.addAttribute("dataType", "string");
		dynamicElement.addAttribute("name", name);
		dynamicElement.addAttribute("type", "text");

		Element metadataElement = dynamicElement.addElement("meta-data");

		metadataElement.addAttribute("locale", "en_US");

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", "label");
		entryElement.setText(label);
	}

}