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
import org.dom4j.Node;
import org.dom4j.Text;
import org.dom4j.util.NodeComparator;

import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class PoshiElementFactoryTest {

	@Test
	public void testPoshiToReadable() throws Exception {
		String baselineReadableSyntax = FileUtil.read(_READABLE_TEST_FILE_PATH);

		PoshiElement poshiElement =
			(PoshiElement)PoshiNodeFactory.newPoshiNodeFromFile(
				_POSHI_TEST_FILE_PATH);

		String readableSyntax = poshiElement.toReadableSyntax();

		if (!readableSyntax.equals(baselineReadableSyntax)) {
			StringBuilder sb = new StringBuilder();

			sb.append("\n\nBaseline readable syntax:");
			sb.append(baselineReadableSyntax);
			sb.append("\n\nGenerated readable syntax:");
			sb.append(readableSyntax);

			throw new Exception(
				"Poshi syntax does not translate to readable syntax" +
					sb.toString());
		}
	}

	@Test
	public void testPoshiToReadableToXML() throws Exception {
		PoshiElement poshiElement =
			(PoshiElement)PoshiNodeFactory.newPoshiNodeFromFile(
				_POSHI_TEST_FILE_PATH);

		String readableSyntax = poshiElement.toReadableSyntax();

		PoshiNode<?, ?> elementFromReadableSyntax =
			PoshiNodeFactory.newPoshiNode(null, readableSyntax);

		Element baselineElement = _getBaselineElement();

		if (!_areElementsEqual(
				baselineElement, (PoshiElement)elementFromReadableSyntax)) {

			StringBuilder sb = new StringBuilder();

			sb.append("\n\nBaseline XML:");
			sb.append(Dom4JUtil.format(baselineElement));
			sb.append("\n\nXML from readable syntax:");
			sb.append(Dom4JUtil.format(elementFromReadableSyntax));

			throw new Exception(
				"Readable syntax does not translate to XML" + sb.toString());
		}
	}

	@Test
	public void testPoshiToXML() throws Exception {
		Element baselineElement = _getBaselineElement();
		PoshiElement poshiElement =
			(PoshiElement)PoshiNodeFactory.newPoshiNodeFromFile(
				_POSHI_TEST_FILE_PATH);

		if (!_areElementsEqual(baselineElement, poshiElement)) {
			StringBuilder sb = new StringBuilder();

			sb.append("\n\nBaseline XML:");
			sb.append(Dom4JUtil.format(baselineElement));
			sb.append("\n\nGenerated XML:");
			sb.append(Dom4JUtil.format(poshiElement));

			throw new Exception(
				"Poshi syntax does not translate to XML" + sb.toString());
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

		Document document = Dom4JUtil.parse(fileContent);

		Element rootElement = document.getRootElement();

		_removeWhiteSpaceTextNodes(rootElement);

		return rootElement;
	}

	private static void _removeWhiteSpaceTextNodes(Element element) {
		for (Node node : Dom4JUtil.toNodeList(element.content())) {
			if (node instanceof Text) {
				String nodeText = node.getText();

				nodeText = nodeText.trim();

				if (nodeText.length() == 0) {
					node.detach();
				}
			}
		}

		for (Element childElement :
				Dom4JUtil.toElementList(element.elements())) {

			_removeWhiteSpaceTextNodes(childElement);
		}
	}

	private static final String _POSHI_TEST_FILE_PATH =
		"src/test/resources/com/liferay/poshi/runner/dependencies" +
			"/PoshiSyntax.testcase";

	private static final String _READABLE_TEST_FILE_PATH =
		"src/test/resources/com/liferay/poshi/runner/dependencies" +
			"/ReadableSyntax.testcase";

}