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

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.util.Dom4JUtil;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.PropsUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;

import java.util.LinkedList;
import java.util.List;

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
		String actualFileName = "PoshiScriptFunction.function";
		String expectedFileName = "PoshiSyntaxFunction.function";

		PoshiElement actualElement = _getPoshiElement(
			"PoshiScriptFunction.function");
		Element expectedElement = _getDom4JElement(
			"PoshiSyntaxFunction.function");

		_assertEqualElements(
			actualFileName, actualElement, expectedFileName, expectedElement,
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
		String actualFileName = "PoshiScriptMacro.macro";
		String expectedFileName = "PoshiSyntaxMacro.macro";

		PoshiElement actualElement = _getPoshiElement(actualFileName);
		Element expectedElement = _getDom4JElement(expectedFileName);

		_assertEqualElements(
			actualFileName, actualElement, expectedFileName, expectedElement,
			"Poshi script syntax does not translate to Poshi XML");
	}

	@Test
	public void testPoshiScriptTestToPoshiXML() throws Exception {
		String actualFileName = "PoshiScript.testcase";
		String expectedFileName = "PoshiSyntax.testcase";

		PoshiElement actualElement = _getPoshiElement(actualFileName);
		Element expectedElement = _getDom4JElement(expectedFileName);

		_assertEqualElements(
			actualFileName, actualElement, expectedFileName, expectedElement,
			"Poshi script syntax does not translate to Poshi XML");
	}

	@Test
	public void testPoshiScriptToPoshiXMLToPoshiScript() throws Exception {
		String fileName = "PoshiScript.testcase";

		PoshiElement actualElement = _getPoshiElement(fileName);

		String actualContent = actualElement.toPoshiScript();

		String expectedContent = FileUtil.read(_getFile(fileName));

		_assertEqualStrings(
			fileName, actualContent, fileName, expectedContent,
			"Poshi XML syntax does not translate to Poshi script syntax");
	}

	@Test
	public void testPoshiXMLFunctionToPoshiScript() throws Exception {
		String expectedFileName = "PoshiScriptFunction.function";

		String expectedContent = FileUtil.read(_getFile(expectedFileName));

		String actualFileName = "PoshiSyntaxFunction.function";

		PoshiElement poshiElement = _getPoshiElement(actualFileName);

		String actualContent = poshiElement.toPoshiScript();

		_assertEqualStrings(
			actualFileName, actualContent, expectedFileName, expectedContent,
			"Poshi XML syntax does not translate to Poshi script syntax");
	}

	@Test
	public void testPoshiXMLMacroAlternate() throws Exception {
		String actualFileName = "AlternatePoshiScript.macro";
		String expectedFileName = "PoshiSyntaxMacro.macro";

		PoshiElement actualElement = _getPoshiElement(actualFileName);
		Element expectedElement = _getDom4JElement(expectedFileName);

		_assertEqualElements(
			actualFileName, actualElement, expectedFileName, expectedElement,
			"Poshi XML syntax does not translate to XML");
	}

	@Test
	public void testPoshiXMLMacroFormat() throws Exception {
		String actualFileName = "UnformattedPoshiScript.macro";
		String expectedFileName = "PoshiSyntaxMacro.macro";

		PoshiElement actualElement = _getPoshiElement(actualFileName);
		Element expectedElement = _getDom4JElement(expectedFileName);

		_assertEqualElements(
			actualFileName, actualElement, expectedFileName, expectedElement,
			"Poshi XML syntax does not translate to XML");
	}

	@Test
	public void testPoshiXMLMacroToPoshiScript() throws Exception {
		String actualFileName = "PoshiSyntaxMacro.macro";

		PoshiElement poshiElement = _getPoshiElement(actualFileName);

		String actualContent = poshiElement.toPoshiScript();

		String expectedFileName = "PoshiScriptMacro.macro";

		String expectedContent = FileUtil.read(_getFile(expectedFileName));

		_assertEqualStrings(
			actualFileName, actualContent, expectedFileName, expectedContent,
			"Poshi XML syntax does not translate to Poshi script syntax");
	}

	@Test
	public void testPoshiXMLTestToPoshiScript() throws Exception {
		String actualFileName = "PoshiSyntax.testcase";

		PoshiElement poshiElement = _getPoshiElement(actualFileName);

		String actualContent = poshiElement.toPoshiScript();

		String expectedFileName = "PoshiScript.testcase";

		String expectedContent = FileUtil.read(_getFile(expectedFileName));

		_assertEqualStrings(
			actualFileName, actualContent, expectedFileName, expectedContent,
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

		Element expectedElement = _getDom4JElement(fileName);

		_assertEqualElements(
			fileName, actualElement, fileName, expectedElement,
			"Poshi XML syntax does not translate to XML");
	}

	@Test
	public void testPoshiXMLTestToXML() throws Exception {
		String fileName = "PoshiSyntax.testcase";

		PoshiElement actualElement = _getPoshiElement(fileName);

		Element expectedElement = _getDom4JElement(fileName);

		_assertEqualElements(
			fileName, actualElement, fileName, expectedElement,
			"Poshi XML syntax does not translate to XML");
	}

	private void _assertEqualElements(
			String actualFileName, Element actualElement,
			String expectedFileName, Element expectedElement,
			String errorMessage)
		throws Exception {

		NodeComparator nodeComparator = new NodeComparator();

		int compare = 1;

		try {
			compare = nodeComparator.compare(actualElement, expectedElement);
		}
		catch (NullPointerException nullPointerException) {
			System.out.println("Unable to compare nodes");
		}

		if (compare != 0) {
			_printDiffs(
				expectedFileName, Dom4JUtil.format(expectedElement),
				actualFileName, Dom4JUtil.format(actualElement));

			throw new Exception(errorMessage);
		}
	}

	private void _assertEqualStrings(
			String actualFileName, String actualContent,
			String expectedFileName, String expectedContent,
			String errorMessage)
		throws Exception {

		if (!actualContent.equals(expectedContent)) {
			_printDiffs(
				expectedFileName, expectedContent, actualFileName,
				actualContent);

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

	private File _getFile(String fileName) {
		return new File(_BASE_DIR + fileName);
	}

	private List<String> _getLines(String s) throws Exception {
		List<String> lines = new LinkedList<>();

		String line = null;

		BufferedReader bufferedReader = new BufferedReader(new StringReader(s));

		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}

		bufferedReader.close();

		return lines;
	}

	private PoshiElement _getPoshiElement(String fileName) throws Exception {
		return (PoshiElement)PoshiNodeFactory.newPoshiNodeFromFile(
			FileUtil.getURL(_getFile(fileName)));
	}

	private void _printDiffs(
			String expectedFileName, String expectedContent,
			String actualFileName, String actualContent)
		throws Exception {

		Patch<String> patch = DiffUtils.diff(
			_getLines(expectedContent), _getLines(actualContent));

		List<String> unifiedDiffLines = UnifiedDiffUtils.generateUnifiedDiff(
			expectedFileName, "Generated from " + actualFileName,
			_getLines(expectedContent), patch, 0);

		for (String line : unifiedDiffLines) {
			if (line.startsWith("+++") || line.startsWith("---")) {
				System.out.println(line);

				continue;
			}

			if (line.startsWith("+")) {
				System.out.println("\033[32m" + line + "\033[0m");

				continue;
			}

			if (line.startsWith("-")) {
				System.out.println("\033[31m" + line + "\033[0m");

				continue;
			}

			System.out.println(line);
		}
	}

	private static final String _BASE_DIR =
		"src/test/resources/com/liferay/poshi/core/dependencies/elements/";

}