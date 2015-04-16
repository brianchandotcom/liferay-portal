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

package com.liferay.portal.security.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

import org.testng.Assert;

/**
 * @author Tomas Polesovsky
 */
public class StripDoctypeXMLReaderTest {

	@Test
	public void testReadInputStream() throws Exception {
		byte[] buff = new byte[4096];

		for (int i = 0; i< _ORIGINAL_XML.length; i++) {
			String xml = _ORIGINAL_XML[i];

			InputStream is = new ByteArrayInputStream(xml.getBytes());

			StripDoctypeFilter filter = new StripDoctypeFilter(is);

			int length = filter.read(buff, 0, buff.length);

			String result = new String(buff, 0, length);

			Assert.assertEquals(result, _SANITIZED_XML[i]);
		}
	}

	@Test
	public void testReadReader() throws Exception {
		char[] chars = new char[4096];

		for (int i = 0; i< _ORIGINAL_XML.length; i++) {
			String xml = _ORIGINAL_XML[i];

			Reader reader = new StringReader(xml);

			StripDoctypeFilter filter = new StripDoctypeFilter(reader);

			int length = filter.read(chars, 0, chars.length);

			String result = new String(chars, 0, length);

			Assert.assertEquals(result, _SANITIZED_XML[i]);
		}
	}

	private static final String[] _ORIGINAL_XML = new String[] {
		"<?xml version=\"1.0\"?><!DOCTYPE root><root />",
		"<!DOCTYPE root [<!ELEMENT root ANY >]><root />",
		"<!-- comment --><!DOCTYPE root [<!ELEMENT root ANY >]><root />",
		"<?xml version=\"1.0\"?><!-- comment --><!DOCTYPE root [" +
			"<!ELEMENT root ANY >]><root />",
		"<?xml version=\"1.0\"?><root />",
		"<?xml version=\"1.0\"?><root attribute=\"<!DOCTYPE root>\"/>"
	};

	private static final String[] _SANITIZED_XML = new String[] {
		"<?xml version=\"1.0\"?><root />", "<root />",
		"<!-- comment --><root />",
		"<?xml version=\"1.0\"?><!-- comment --><root />",
		"<?xml version=\"1.0\"?><root />",
		"<?xml version=\"1.0\"?><root attribute=\"<!DOCTYPE root>\"/>"
	};

}