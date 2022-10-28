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

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.util.Dom4JUtil;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.PropsUtil;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.util.NodeComparator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class PoshiElementFactoryTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		String[] poshiFileNames = {"**/*.function"};

		String poshiFileDir =
			"../poshi-runner-resources/src/main/resources/default" +
				"/testFunctional/functions";

		PropsUtil.clear();

		PropsUtil.set("test.base.dir.name", poshiFileDir);

		PoshiContext.readFiles(true, poshiFileNames, poshiFileDir);

		PoshiContext.setFunctionFileNames("WaitForSPARefresh");

		String[] macroFileNames = {
			"Alert", "AlloyEditor", "Blogs", "FormFields", "KaleoDesigner",
			"Navigator", "PortalInstances", "ProductMenu", "SignIn", "Smoke",
			"TableEcho", "TestCase", "User"
		};

		PoshiContext.setMacroFileNames(macroFileNames);
	}

	@Test
	public void testConditionalPoshiScriptLineNumbers() throws Exception {
		PoshiElement rootPoshiElement = _getPoshiElement(
			"ConditionalPoshiScript.macro");

		PoshiElement commandElement = (PoshiElement)rootPoshiElement.element(
			"command");

		Element ifElement = commandElement.element("if");

		AndPoshiElement andPoshiElement = (AndPoshiElement)ifElement.element(
			"and");

		Assert.assertEquals(3, andPoshiElement.getPoshiScriptLineNumber());

		int[] expectedLineNumbers = {4, 6};

		PoshiElement thenPoshiElement = (PoshiElement)ifElement.element("then");

		int i = 0;

		for (Node node : Dom4JUtil.toNodeList(thenPoshiElement.content())) {
			PoshiElement childPoshiElement = (PoshiElement)node;

			Assert.assertEquals(
				"The the expected line number does not match",
				expectedLineNumbers[i],
				childPoshiElement.getPoshiScriptLineNumber());

			i++;
		}
	}

	@Test
	public void testPoshiScriptFunctionToXML() throws Exception {
		PoshiElement actualElement = _getPoshiElement(
			"PoshiScriptFunction.function");
		Element expectedElement = _getDom4JElement(
			"PoshiSyntaxFunction.function");

		_assertEqualElements(
			actualElement, expectedElement,
			"Poshi script syntax does not translate to Poshi XML");
	}

	@Test
	public void testPoshiScriptLineNumbers() throws Exception {
		PoshiElement rootPoshiElement = _getPoshiElement(
			"PoshiScriptMacro.macro");

		int[] expectedLineNumbers = {
			4, 9, 11, 17, 19, 28, 29, 30, 32, 34, 38, 42, 46, 50, 54, 58
		};

		int i = 0;

		for (Node node : Dom4JUtil.toNodeList(rootPoshiElement.content())) {
			if (node instanceof PoshiElement) {
				PoshiElement poshiElement = (PoshiElement)node;

				for (Node childNode :
						Dom4JUtil.toNodeList(poshiElement.content())) {

					PoshiNode<?, ?> childPoshiNode = (PoshiNode<?, ?>)childNode;

					Assert.assertEquals(
						"The the expected line number does not match",
						expectedLineNumbers[i],
						childPoshiNode.getPoshiScriptLineNumber());

					i++;
				}
			}
		}
	}

	@Test
	public void testPoshiScriptMacroToXML() throws Exception {
		PoshiElement actualElement = _getPoshiElement("PoshiScriptMacro.macro");
		Element expectedElement = _getDom4JElement("PoshiSyntaxMacro.macro");

		_assertEqualElements(
			actualElement, expectedElement,
			"Poshi script syntax does not translate to Poshi XML");
	}

	@Test
	public void testPoshiScriptTestToPoshiXML() throws Exception {
		PoshiElement actualElement = _getPoshiElement("PoshiScript.testcase");
		Element expectedElement = _getDom4JElement("PoshiSyntax.testcase");

		_assertEqualElements(
			actualElement, expectedElement,
			"Poshi script syntax does not translate to Poshi XML");
	}

	@Test
	public void testPoshiScriptToPoshiXMLToPoshiScript() throws Exception {
		PoshiElement actualElement = _getPoshiElement("PoshiScript.testcase");

		String expected = FileUtil.read(_getFile("PoshiScript.testcase"));

		String actual = actualElement.toPoshiScript();

		_assertEqualStrings(
			actual, expected,
			"Poshi XML syntax does not translate to Poshi script syntax");
	}

	@Test
	public void testPoshiXMLFunctionToPoshiScript() throws Exception {
		String expected = FileUtil.read(
			_getFile("PoshiScriptFunction.function"));

		PoshiElement poshiElement = _getPoshiElement(
			"PoshiSyntaxFunction.function");

		String actual = poshiElement.toPoshiScript();

		_assertEqualStrings(
			actual, expected,
			"Poshi XML syntax does not translate to Poshi script syntax");
	}

	@Test
	public void testPoshiXMLMacroAlternate() throws Exception {
		PoshiElement actualElement = _getPoshiElement(
			"AlternatePoshiScript.macro");
		Element expectedElement = _getDom4JElement("PoshiSyntaxMacro.macro");

		_assertEqualElements(
			actualElement, expectedElement,
			"Poshi script syntax does not translate to Poshi XML");
	}

	@Test
	public void testPoshiXMLMacroFormat() throws Exception {
		PoshiElement actualElement = _getPoshiElement(
			"UnformattedPoshiScript.macro");
		Element expectedElement = _getDom4JElement("PoshiSyntaxMacro.macro");

		_assertEqualElements(
			actualElement, expectedElement,
			"Poshi script syntax does not translate to Poshi XML");
	}

	@Test
	public void testPoshiXMLMacroToPoshiScript() throws Exception {
		String expected = FileUtil.read(_getFile("PoshiScriptMacro.macro"));

		PoshiElement poshiElement = _getPoshiElement("PoshiSyntaxMacro.macro");

		String actual = poshiElement.toPoshiScript();

		_assertEqualStrings(
			actual, expected,
			"Poshi XML syntax does not translate to Poshi script syntax");
	}

	@Test
	public void testPoshiXMLTestToPoshiScript() throws Exception {
		String expected = FileUtil.read(_getFile("PoshiScript.testcase"));

		PoshiElement poshiElement = _getPoshiElement("PoshiSyntax.testcase");

		String actual = poshiElement.toPoshiScript();

		_assertEqualStrings(
			actual, expected,
			"Poshi XML syntax does not translate to Poshi script syntax");
	}

	@Test
	public void testPoshiXMLTestToPoshiScriptToPoshiXML() throws Exception {
		String fileName = "PoshiSyntax.testcase";

		PoshiElement poshiElement = _getPoshiElement(fileName);

		String poshiScript = poshiElement.toPoshiScript();

		PoshiElement actualElement =
			(PoshiElement)PoshiNodeFactory.newPoshiNode(
				poshiScript, FileUtil.getURL(_getFile(fileName)));

		Element expectedElement = _getDom4JElement("PoshiSyntax.testcase");

		_assertEqualElements(
			actualElement, expectedElement,
			"Poshi XML syntax is not preserved in full translation");
	}

	@Test
	public void testPoshiXMLTestToXML() throws Exception {
		PoshiElement actualElement = _getPoshiElement("PoshiSyntax.testcase");
		Element expectedElement = _getDom4JElement("PoshiSyntax.testcase");

		_assertEqualElements(
			actualElement, expectedElement,
			"Poshi XML syntax does not translate to XML");
	}

	private void _assertEqualElements(
			Element actualElement, Element expectedElement, String errorMessage)
		throws Exception {

		NodeComparator nodeComparator = new NodeComparator();

		int compare = nodeComparator.compare(actualElement, expectedElement);

		if (compare != 0) {
			String actual = Dom4JUtil.format(actualElement);
			String expected = Dom4JUtil.format(expectedElement);

			errorMessage = _getErrorMessage(actual, expected, errorMessage);

			throw new Exception(errorMessage);
		}
	}

	private void _assertEqualStrings(
			String actual, String expected, String errorMessage)
		throws Exception {

		if (!actual.equals(expected)) {
			errorMessage = _getErrorMessage(actual, expected, errorMessage);

			throw new Exception(errorMessage);
		}
	}

	private Element _getDom4JElement(String fileName) throws Exception {
		String fileContent = FileUtil.read(_BASE_DIR + fileName);

		Document document = Dom4JUtil.parse(fileContent);

		Element rootElement = document.getRootElement();

		Dom4JUtil.removeWhiteSpaceTextNodes(rootElement);

		return rootElement;
	}

	private String _getErrorMessage(
			String actual, String expected, String errorMessage)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(errorMessage);
		sb.append("\n\nExpected:\n");
		sb.append(expected);
		sb.append("\n\nActual:\n");
		sb.append(actual);

		return sb.toString();
	}

	private File _getFile(String fileName) {
		return new File(_BASE_DIR + fileName);
	}

	private PoshiElement _getPoshiElement(String fileName) throws Exception {
		return (PoshiElement)PoshiNodeFactory.newPoshiNodeFromFile(
			FileUtil.getURL(_getFile(fileName)));
	}

	private static final String _BASE_DIR =
		"src/test/resources/com/liferay/poshi/core/dependencies/elements/";

}