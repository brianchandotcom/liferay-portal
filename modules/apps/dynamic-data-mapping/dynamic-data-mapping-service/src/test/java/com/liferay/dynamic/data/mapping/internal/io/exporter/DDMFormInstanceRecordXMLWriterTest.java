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

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class DDMFormInstanceRecordXMLWriterTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpPropsUtil();
		setUpSAXReaderUtil();
	}

	@Test
	public void testAddFieldElement() {
		DDMFormInstanceRecordXMLWriter ddmFormInstanceRecordXMLWriter =
			new DDMFormInstanceRecordXMLWriter();

		Element element = mock(Element.class);

		Element fieldElement = mock(Element.class);

		Element labelElement = mock(Element.class);

		Element valueElement = mock(Element.class);

		when(
			element.addElement("field")
		).thenReturn(
			fieldElement
		);

		when(
			fieldElement.addElement("label")
		).thenReturn(
			labelElement
		);

		when(
			fieldElement.addElement("value")
		).thenReturn(
			valueElement
		);

		ddmFormInstanceRecordXMLWriter.addFieldElement(
			element, "Label 1", "Value 1");

		InOrder inOrder = Mockito.inOrder(
			element, fieldElement, labelElement, valueElement);

		inOrder.verify(
			element, Mockito.times(1)
		).addElement(
			"field"
		);
		inOrder.verify(
			fieldElement, Mockito.times(1)
		).addElement(
			"label"
		);
		inOrder.verify(
			labelElement, Mockito.times(1)
		).addText(
			"Label 1"
		);
		inOrder.verify(
			fieldElement, Mockito.times(1)
		).addElement(
			"value"
		);
		inOrder.verify(
			valueElement, Mockito.times(1)
		).addText(
			"Value 1"
		);
	}

	@Test
	public void testAddFieldElements() {
		DDMFormInstanceRecordXMLWriter ddmFormInstanceRecordXMLWriter = mock(
			DDMFormInstanceRecordXMLWriter.class);

		Element element = mock(Element.class);

		Map<String, String> ddmFormFieldsLabel =
			new LinkedHashMap<String, String>() {
				{
					put("field1", "Field 1");
					put("field2", "Field 2");
				}
			};

		Map<String, String> ddmFormFieldsValue =
			new LinkedHashMap<String, String>() {
				{
					put("field1", "Value 1");
					put("field2", "Value 2");
				}
			};

		Mockito.doCallRealMethod(
		).when(
			ddmFormInstanceRecordXMLWriter
		).addFieldElements(
			element, ddmFormFieldsLabel, ddmFormFieldsValue
		);

		Mockito.doNothing(
		).when(
			ddmFormInstanceRecordXMLWriter
		).addFieldElement(
			Matchers.any(Element.class), Matchers.anyString(),
			Matchers.anyString()
		);

		ddmFormInstanceRecordXMLWriter.addFieldElements(
			element, ddmFormFieldsLabel, ddmFormFieldsValue);

		InOrder inOrder = Mockito.inOrder(ddmFormInstanceRecordXMLWriter);

		inOrder.verify(
			ddmFormInstanceRecordXMLWriter, Mockito.times(1)
		).addFieldElement(
			element, "Field 1", "Value 1"
		);

		inOrder.verify(
			ddmFormInstanceRecordXMLWriter, Mockito.times(1)
		).addFieldElement(
			element, "Field 2", "Value 2"
		);
	}

	@Test
	public void testWrite() throws Exception {
		DDMFormInstanceRecordXMLWriter ddmFormInstanceRecordXMLWriter = mock(
			DDMFormInstanceRecordXMLWriter.class);

		Map<String, String> ddmFormFieldsLabel =
			new LinkedHashMap<String, String>() {
				{
					put("field1", "Field 1");
					put("field2", "Field 2");
					put("field3", "Field 3");
					put("field4", "Field 4");
				}
			};

		List<Map<String, String>> ddmFormFieldValues =
			new ArrayList<Map<String, String>>() {
				{
					Map<String, String> map1 = new HashMap<String, String>() {
						{
							put("field1", "2");
							put("field2", "esta é uma 'string'");
							put("field3", "false");
							put("field4", "11.7");
						}
					};

					add(map1);

					Map<String, String> map2 = new HashMap<String, String>() {
						{
							put("field1", "1");
							put("field2", "esta é uma 'string'");
							put("field3", "");
							put("field4", "10");
						}
					};

					add(map2);
				}
			};

		DDMFormInstanceRecordWriterRequest.Builder builder =
			DDMFormInstanceRecordWriterRequest.Builder.newBuilder(
				ddmFormFieldsLabel, ddmFormFieldValues);

		Document document = mock(Document.class);

		when(
			_saxReader.createDocument()
		).thenReturn(
			document
		);

		Element rootElement = mock(Element.class);

		when(
			document.addElement("root")
		).thenReturn(
			rootElement
		);

		when(
			document.asXML()
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.doNothing(
		).when(
			ddmFormInstanceRecordXMLWriter
		).addFieldElements(
			Matchers.any(Element.class), Matchers.anyMap(), Matchers.anyMap()
		);

		DDMFormInstanceRecordWriterRequest ddmFormInstanceRecordWriterRequest =
			builder.build();

		when(
			ddmFormInstanceRecordXMLWriter.write(
				ddmFormInstanceRecordWriterRequest)
		).thenCallRealMethod();

		ddmFormInstanceRecordXMLWriter.write(
			ddmFormInstanceRecordWriterRequest);

		InOrder inOrder = Mockito.inOrder(
			_saxReader, document, rootElement, ddmFormInstanceRecordXMLWriter);

		inOrder.verify(
			_saxReader, Mockito.times(1)
		).createDocument();
		inOrder.verify(
			document, Mockito.times(1)
		).addElement(
			"root"
		);
		inOrder.verify(
			rootElement, Mockito.times(1)
		).addElement(
			"fields"
		);

		inOrder.verify(
			ddmFormInstanceRecordXMLWriter, Mockito.times(1)
		).addFieldElements(
			Matchers.any(Element.class), Matchers.anyMap(), Matchers.anyMap()
		);

		inOrder.verify(
			rootElement, Mockito.times(1)
		).addElement(
			"fields"
		);

		inOrder.verify(
			ddmFormInstanceRecordXMLWriter, Mockito.times(1)
		).addFieldElements(
			Matchers.any(Element.class), Matchers.anyMap(), Matchers.anyMap()
		);

		inOrder.verify(
			document, Mockito.times(1)
		).asXML();
	}

	protected void setUpPropsUtil() {
		PropsTestUtil.setProps(
			PropsKeys.XML_SECURITY_ENABLED, Boolean.TRUE.toString());
	}

	protected void setUpSAXReaderUtil() {
		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		saxReaderUtil.setSAXReader(_saxReader);
	}

	@Mock
	private SAXReader _saxReader;

}