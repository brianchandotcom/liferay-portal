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

package com.liferay.poshi.runner.elements;

import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.FileUtil;

import java.io.BufferedReader;
import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.Element;

import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class PoshiElementFactoryTest {

	@Test
	public void testPoshiToReadableToXML() throws Exception {
		DefinitionElement element =
			(DefinitionElement)PoshiElementFactory.newPoshiElementFromFile(
				_TEST_FILE_PATH);

		String dom4JElementString = _getCompressedXMLTest();

		String readableSyntax = element.toReadableSyntax();

		Element fromReadableSyntax = PoshiElementFactory.newPoshiElement(
			readableSyntax);

		String fromReadableSyntaxElementString = Dom4JUtil.format(
			fromReadableSyntax, false);

		if (!fromReadableSyntaxElementString.equals(dom4JElementString)) {
			System.out.println("Expected:" + dom4JElementString);
			System.out.println("Actual:  " + fromReadableSyntaxElementString);

			throw new Exception("Readable syntax does not translate to XML");
		}
	}

	@Test
	public void testPoshiToXML() throws Exception {
		Element element = PoshiElementFactory.newPoshiElementFromFile(
			_TEST_FILE_PATH);

		String dom4JElementString = _getCompressedXMLTest();

		String poshiElementString = Dom4JUtil.format(element, false);

		if (!poshiElementString.equals(dom4JElementString)) {
			System.out.println("Expected:" + dom4JElementString);
			System.out.println("Actual:  " + poshiElementString);

			throw new Exception("Poshi syntax does not translate to XML");
		}
	}

	private static String _getCompressedXMLTest() throws Exception {
		String fileContent = FileUtil.read(_TEST_FILE_PATH);

		Document document = Dom4JUtil.parse(fileContent);

		Element dom4JElement = document.getRootElement();

		String dom4JElementString = Dom4JUtil.format(dom4JElement, false);

		return _removeWhitespace(dom4JElementString);
	}

	private static String _removeWhitespace(String s) {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader bufferedReader = new BufferedReader(
				new StringReader(s))) {

			String line = bufferedReader.readLine();

			while (line != null) {
				sb.append(line.trim());

				line = bufferedReader.readLine();
			}

			return sb.toString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final String _TEST_FILE_PATH =
		"src/test/resources/com/liferay/poshi/runner/dependencies" +
			"/PoshiSyntax.testcase";

}