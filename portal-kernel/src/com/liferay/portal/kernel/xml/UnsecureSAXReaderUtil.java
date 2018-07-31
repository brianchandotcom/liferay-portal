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

package com.liferay.portal.kernel.xml;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Tomas Polesovsky
 */
public class UnsecureSAXReaderUtil {

	public static SAXReader getSAXReader() {
		return _saxReader;
	}

	public static Document read(File file) throws DocumentException {
		return getSAXReader().read(file);
	}

	public static Document read(File file, boolean validate)
		throws DocumentException {

		return getSAXReader().read(file, validate);
	}

	public static Document read(InputStream is) throws DocumentException {
		return getSAXReader().read(is);
	}

	public static Document read(InputStream is, boolean validate)
		throws DocumentException {

		return getSAXReader().read(is, validate);
	}

	public static Document read(Reader reader) throws DocumentException {
		return getSAXReader().read(reader);
	}

	public static Document read(Reader reader, boolean validate)
		throws DocumentException {

		return getSAXReader().read(reader, validate);
	}

	public static Document read(String xml) throws DocumentException {
		return getSAXReader().read(xml);
	}

	public static Document read(String xml, boolean validate)
		throws DocumentException {

		return getSAXReader().read(xml, validate);
	}

	public static Document read(String xml, XMLSchema xmlSchema)
		throws DocumentException {

		return getSAXReader().read(xml, xmlSchema);
	}

	public static Document read(URL url) throws DocumentException {
		return getSAXReader().read(url);
	}

	public static Document read(URL url, boolean validate)
		throws DocumentException {

		return getSAXReader().read(url, validate);
	}

	public static Document readURL(String url)
		throws DocumentException, MalformedURLException {

		return getSAXReader().readURL(url);
	}

	public static Document readURL(String url, boolean validate)
		throws DocumentException, MalformedURLException {

		return getSAXReader().readURL(url, validate);
	}

	public void setSAXReader(SAXReader saxReader) {
		_saxReader = saxReader;
	}

	private static SAXReader _saxReader;

}