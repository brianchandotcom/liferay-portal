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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.xml.SAXReaderImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
@PrepareForTest({DDMXMLUtil.class, HtmlUtil.class, SAXReaderUtil.class})
@RunWith(PowerMockRunner.class)
public class JournalConverterUtilTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		spy(DDMXMLUtil.class);

		when(
			DDMXMLUtil.getDDMXML()
		).thenReturn(
			_ddmXML
		);

		spy(HtmlUtil.class);

		when(
			HtmlUtil.getHtml()
		).thenReturn(
			_html
		);

		spy(SAXReaderUtil.class);

		when(
			SAXReaderUtil.getSAXReader()
		).thenReturn(
			_saxReader
		);
	}

	@Test
	public void testJournalStructureToDDMStructure() throws Exception {
		String expectedDDMXSD = readText("ddm-structure.xml");

		DDMStructure expectedDDMStructure = new DDMStructureImpl();

		expectedDDMStructure.setXsd(expectedDDMXSD);

		String journalXSD = readText("journal-structure.xml");

		String actualDDMXSD =
			JournalConverterUtil.journalStructureToDDMStructure(journalXSD);

		DDMStructure actualDDMStructure = new DDMStructureImpl();

		actualDDMStructure.setXsd(actualDDMXSD);

		Assert.assertEquals(
			expectedDDMStructure.getFieldsMap(),
			actualDDMStructure.getFieldsMap());
	}

	protected String readText(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private DDMXMLImpl _ddmXML = new DDMXMLImpl();
	private HtmlImpl _html = new HtmlImpl();
	private SAXReaderImpl _saxReader = new SAXReaderImpl();

}