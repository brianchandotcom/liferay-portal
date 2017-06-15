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

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.util.NodeComparator;

import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class PoshiElementFactoryTest {

	@Test
	public void testPoshiToReadable() throws Exception {
		String baselineReadableSyntax = FileUtil.read(_READABLE_TEST_FILE_PATH);

		PoshiElement poshiElement = PoshiElementFactory.newPoshiElementFromFile(
			_POSHI_TEST_FILE_PATH);

		String readableSyntax = poshiElement.toReadableSyntax();

		if (!readableSyntax.equals(baselineReadableSyntax)) {
			throw new Exception(
				"Poshi syntax does not translate to readable syntax.");
		}
	}

	@Test
	public void testPoshiToReadableToXML() throws Exception {
		PoshiElement poshiElement = PoshiElementFactory.newPoshiElementFromFile(
			_POSHI_TEST_FILE_PATH);

		String readableSyntax = poshiElement.toReadableSyntax();

		PoshiElement elementFromReadableSyntax =
			PoshiElementFactory.newPoshiElement(readableSyntax);

		Element baselineElement = _getBaselineElement();

		if (!_areElementsEqual(baselineElement, elementFromReadableSyntax)) {
			throw new Exception("Readable syntax does not translate to XML.");
		}
	}

	@Test
	public void testPoshiToXML() throws Exception {
		Element baselineElement = _getBaselineElement();
		PoshiElement poshiElement = PoshiElementFactory.newPoshiElementFromFile(
			_POSHI_TEST_FILE_PATH);

		if (!_areElementsEqual(baselineElement, poshiElement)) {
			throw new Exception("Poshi syntax does not translate to XML.");
		}
	}

	private static boolean _areElementsEqual(Element element1, Element element2)
		throws Exception {

		NodeComparator nodeComparator = new NodeComparator();

		int compare = nodeComparator.compare(element1, element2);

		if (compare == 0) {
			return true;
		}

		return false;
	}

	private static Element _getBaselineElement() throws Exception {
		String fileContent = FileUtil.read(_POSHI_TEST_FILE_PATH);

		fileContent = _removeWhitespace(fileContent);

		Document document = Dom4JUtil.parse(fileContent);

		return document.getRootElement();
	}

	private static String _removeWhitespace(String s) {
		StringBuilder sb = new StringBuilder();

		for (String line : s.split("\n")) {
			sb.append(line.trim());
		}

		return sb.toString();
	}

	private static final String _POSHI_TEST_FILE_PATH =
		"src/test/resources/com/liferay/poshi/runner/dependencies" +
			"/PoshiSyntax.testcase";

	private static final String _READABLE_TEST_FILE_PATH =
		"src/test/resources/com/liferay/poshi/runner/dependencies" +
			"/ReadableSyntax.testcase";

}