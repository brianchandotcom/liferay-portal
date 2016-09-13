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

import com.liferay.portal.kernel.util.StringBundler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 */
public class StripDoctypeXMLReaderTest {

	@Test
	public void testInternalBufferWithInputStream() throws Exception {
		String prologue = "<?xml version=\"1.0\"?>";

		String xml = prologue + "<root />";

		byte[] bytes = new byte[prologue.length() + 1];

		InputStream is = new ByteArrayInputStream(xml.getBytes());

		StripDoctypeFilter stripDoctypeFilter = new StripDoctypeFilter(is);

		StringBundler sb = new StringBundler();
		int length;
		while ((length = stripDoctypeFilter.read(bytes, 0, bytes.length)) > 0) {
			sb.append(new String(bytes, 0, length));
		}

		String result = sb.toString();

		Assert.assertEquals(xml, result);
	}

	@Test
	public void testInternalBufferWithReader() throws Exception {
		String prologue = "<?xml version=\"1.0\"?>";

		String xml = prologue + "<root />";

		char[] chars = new char[prologue.length() + 1];

		Reader reader = new StringReader(xml);

		StripDoctypeFilter stripDoctypeFilter = new StripDoctypeFilter(reader);

		StringBundler sb = new StringBundler();
		int length;
		while ((length = stripDoctypeFilter.read(chars, 0, chars.length)) > 0) {
			sb.append(new String(chars, 0, length));
		}

		String result = sb.toString();

		Assert.assertEquals(xml, result);
	}

	@Test
	public void testReadInputStream() throws Exception {
		byte[] buff = new byte[4096];

		for (int i = 0; i < _ORIGINAL_XML.length; i++) {
			String xml = _ORIGINAL_XML[i];

			InputStream is = new ByteArrayInputStream(xml.getBytes());

			StripDoctypeFilter stripDoctypeFilter = new StripDoctypeFilter(is);

			int length = stripDoctypeFilter.read(buff, 0, buff.length);

			String result = new String(buff, 0, length);

			Assert.assertEquals(_SANITIZED_XML[i], result);
		}
	}

	@Test
	public void testReadReader() throws Exception {
		char[] chars = new char[4096];

		for (int i = 0; i < _ORIGINAL_XML.length; i++) {
			String xml = _ORIGINAL_XML[i];

			Reader reader = new StringReader(xml);

			StripDoctypeFilter stripDoctypeFilter = new StripDoctypeFilter(
				reader);

			int length = stripDoctypeFilter.read(chars, 0, chars.length);

			String result = new String(chars, 0, length);

			Assert.assertEquals(_SANITIZED_XML[i], result);
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