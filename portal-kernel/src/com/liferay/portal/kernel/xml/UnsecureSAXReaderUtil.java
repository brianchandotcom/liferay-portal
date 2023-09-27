/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return _saxReader.read(file);
	}

	public static Document read(File file, boolean validate)
		throws DocumentException {

		return _saxReader.read(file, validate);
	}

	public static Document read(InputStream inputStream)
		throws DocumentException {

		return _saxReader.read(inputStream);
	}

	public static Document read(InputStream inputStream, boolean validate)
		throws DocumentException {

		return _saxReader.read(inputStream, validate);
	}

	public static Document read(Reader reader) throws DocumentException {
		return _saxReader.read(reader);
	}

	public static Document read(Reader reader, boolean validate)
		throws DocumentException {

		return _saxReader.read(reader, validate);
	}

	public static Document read(String xml) throws DocumentException {
		return _saxReader.read(xml);
	}

	public static Document read(String xml, boolean validate)
		throws DocumentException {

		return _saxReader.read(xml, validate);
	}

	public static Document read(String xml, XMLSchema xmlSchema)
		throws DocumentException {

		return _saxReader.read(xml, xmlSchema);
	}

	public static Document read(URL url) throws DocumentException {
		return _saxReader.read(url);
	}

	public static Document read(URL url, boolean validate)
		throws DocumentException {

		return _saxReader.read(url, validate);
	}

	public static Document readURL(String url)
		throws DocumentException, MalformedURLException {

		return _saxReader.readURL(url);
	}

	public static Document readURL(String url, boolean validate)
		throws DocumentException, MalformedURLException {

		return _saxReader.readURL(url, validate);
	}

	public void setSAXReader(SAXReader saxReader) {
		_saxReader = saxReader;
	}

	private static SAXReader _saxReader;

}