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

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.io.internal.DDMFormXSDDeserializerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import org.junit.Before;

/**
 * @author Pablo Carvalho
 */
public class DDMFormXSDDeserializerTest
	extends BaseDDMFormDeserializerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpHtmlUtil();
		setUpPropsUtil();
		setUpSAXReaderUtil();
	}

	@Override
	protected DDMForm deserialize(String serializedDDMForm)
		throws PortalException {

		return _ddmFormXSDDeserializer.deserialize(serializedDDMForm);
	}

	@Override
	protected String getDeserializerType() {
		return "xsd";
	}

	@Override
	protected String getTestFileExtension() {
		return ".xml";
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	protected void setUpPropsUtil() {
		Props props = mock(Props.class);

		when(
			props.get(PropsKeys.XML_SECURITY_ENABLED)
		).thenReturn(
			Boolean.TRUE.toString()
		);

		PropsUtil.setProps(props);
	}

	protected void setUpSAXReaderUtil() {
		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		SAXReaderImpl secureSAXReader = new SAXReaderImpl();

		secureSAXReader.setSecure(true);

		saxReaderUtil.setSAXReader(secureSAXReader);

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		SAXReaderImpl unsecureSAXReader = new SAXReaderImpl();

		unsecureSAXReaderUtil.setSAXReader(unsecureSAXReader);
	}

	private final DDMFormXSDDeserializer _ddmFormXSDDeserializer =
		new DDMFormXSDDeserializerImpl();

}