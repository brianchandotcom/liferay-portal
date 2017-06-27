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

package com.liferay.poshi.runner;

import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerValidationTest extends TestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		PoshiRunnerContext.readFiles();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		PoshiRunnerContext.clear();
	}

	@Test
	public void testGetPrimaryAttributeName() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute("function", "AssertTextPresent");
		element.addAttribute("value1", "hello world");

		String primaryAttributeName =
			PoshiRunnerValidation.getPrimaryAttributeName(
				element, Arrays.asList("function", "selenium"),
				"GetPrimaryAttributeName.macro");

		Assert.assertEquals(
			"getPrimaryAttributeName is failing", "function",
			primaryAttributeName);
		Assert.assertNotEquals(
			"getPrimaryAttributeName is failing", "value1",
			primaryAttributeName);
	}

	@Test
	public void testValidateClassCommandName() {
		String classCommandName = "ValidateClassCommandName#classCommandName";

		Element element = PoshiRunnerContext.getTestCaseCommandElement(
			classCommandName);

		String filePath = getFilePath("ValidateClassCommandName.testcase");

		PoshiRunnerValidation.validateClassCommandName(
			element, classCommandName, "test-case", filePath);

		Assert.assertEquals(
			"validateClassCommandName is failing", "", getExceptionMessage());

		PoshiRunnerValidation.validateClassCommandName(
			element, "ValidateClassCommandName#fail", "test-case", filePath);

		Assert.assertEquals(
			"validateClassCommandName is failing",
			"Invalid test-case command ValidateClassCommandName#fail",
			getExceptionMessage());
	}

	@Test
	public void testValidateCommandElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("command");

		element.addAttribute("name", "validateCommandElement");
		element.addAttribute("summary", "This is a test");

		PoshiRunnerValidation.validateCommandElement(
			element, "ValidateCommandElement.macro");

		Assert.assertEquals(
			"validateCommandElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("command");

		element.addAttribute("summary", "This is a test");

		PoshiRunnerValidation.validateCommandElement(
			element, "ValidateCommandElement.macro");

		Assert.assertEquals(
			"validateCommandElement is failing", "Missing name attribute",
			getExceptionMessage());
	}

	@Test
	public void testValidateConditionElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("and");

		Element childElement1 = element.addElement("contains");

		childElement1.addAttribute("line-number", "1");
		childElement1.addAttribute("string", "hello world");
		childElement1.addAttribute("substring", "hello");

		Element childElement2 = element.addElement("equals");

		childElement2.addAttribute("arg1", "true");
		childElement2.addAttribute("arg2", "true");
		childElement2.addAttribute("line-number", "1");

		PoshiRunnerValidation.validateConditionElement(
			element, "ValidateConditionElement.macro");

		Assert.assertEquals(
			"validateConditionElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("and");

		childElement1 = element.addElement("contains");

		childElement1.addAttribute("line-number", "1");
		childElement1.addAttribute("string", "hello world");
		childElement1.addAttribute("substring", "hello");

		PoshiRunnerValidation.validateConditionElement(
			element, "ValidateConditionElement.macro");

		Assert.assertEquals(
			"validateConditionElement is failing", "Too few child elements",
			getExceptionMessage());
	}

	@Test
	public void testValidateDefinitionElement() throws Exception {
		URL url = getURL("ValidateDefinitionElement.macro");

		Element rootElement = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		PoshiRunnerValidation.validateDefinitionElement(
			rootElement, url.getFile());

		Assert.assertEquals(
			"validateDefinitionElement is failing", "", getExceptionMessage());

		url = getURL("ValidateDefinitionElement2.macro");

		rootElement = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		PoshiRunnerValidation.validateDefinitionElement(
			rootElement, url.getFile());

		Assert.assertEquals(
			"validateDefinitionElement is failing",
			"Root element name must be definition", getExceptionMessage());
	}

	@Test
	public void testValidateElementName() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute("function", "AssertTextPresent");
		element.addAttribute("value1", "hello world");

		List<String> possibleElementNames = Arrays.asList("command", "execute");

		PoshiRunnerValidation.validateElementName(
			element, possibleElementNames, "ValidateElementName.macro");

		Assert.assertEquals(
			"validateElementName is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("poshi");

		element.addAttribute("function", "AssertTextPresent");
		element.addAttribute("value1", "hello world");

		PoshiRunnerValidation.validateElementName(
			element, possibleElementNames, "ValidateElementName.macro");

		Assert.assertEquals(
			"validateElementName is failing",
			"Missing " + possibleElementNames + " element",
			getExceptionMessage());
	}

	@Test
	public void testValidateElseElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("if");

		Element elseElement1 = element.addElement("else");

		Element executeElement1 = elseElement1.addElement("execute");

		executeElement1.addAttribute("function", "Click");
		executeElement1.addAttribute("locator1", "//else element");

		PoshiRunnerValidation.validateElseElement(
			element, "ValidateElseElement.macro");

		Assert.assertEquals(
			"validateElseElement is failing", "", getExceptionMessage());

		Element elseElement2 = element.addElement("else");

		Element executeElement2 = elseElement2.addElement("execute");

		executeElement2.addAttribute("function", "Click");
		executeElement2.addAttribute("locator1", "//else element");

		PoshiRunnerValidation.validateElseElement(
			element, "ValidateElseElement.macro");

		Assert.assertEquals(
			"validateElseElement is failing", "Too many else elements",
			getExceptionMessage());
	}

	@Test
	public void testValidateElseIfElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("elseif");

		Element conditionElement = element.addElement("equals");

		conditionElement.addAttribute("arg1", "true");
		conditionElement.addAttribute("arg2", "true");
		conditionElement.addAttribute("line-number", "12");

		Element thenElement = element.addElement("then");

		Element executeElement1 = thenElement.addElement("execute");

		executeElement1.addAttribute("function", "Click");
		executeElement1.addAttribute("line-number", "15");
		executeElement1.addAttribute("locator1", "//else if element");

		PoshiRunnerValidation.validateElseIfElement(
			element, "ValidateElseIfElement.macro");

		Assert.assertEquals(
			"validateElseIfElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("elseif");

		Element executeElement2 = element.addElement("execute");

		executeElement2.addAttribute("function", "Click");
		executeElement2.addAttribute("locator1", "//else if element");

		thenElement = element.addElement("then");

		executeElement1 = thenElement.addElement("execute");

		executeElement1.addAttribute("function", "Click");
		executeElement1.addAttribute("line-number", "15");
		executeElement1.addAttribute("locator1", "//else if element");

		PoshiRunnerValidation.validateElseIfElement(
			element, "ValidateElseIfElement.macro");

		Assert.assertEquals(
			"validateElseIfElement is failing", "Invalid execute element",
			getExceptionMessage());
	}

	@Test
	public void testValidateExecuteElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");

		Element childElement = element.addElement("var");

		childElement.addAttribute("line-number", "1");
		childElement.addAttribute("name", "name");
		childElement.addAttribute("value", "value");

		PoshiRunnerValidation.validateExecuteElement(
			element, "ValidateExecuteElement.macro");

		Assert.assertEquals(
			"validateExecuteElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");

		childElement = element.addElement("execute");

		childElement.addAttribute("function", "Click");
		childElement.addAttribute("locator1", "//here");

		PoshiRunnerValidation.validateExecuteElement(
			element, "ValidateExecuteElement.macro");

		Assert.assertEquals(
			"validateExecuteElement is failing", "Invalid child element",
			getExceptionMessage());
	}

	@Test
	public void testValidateForElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("for");

		element.addAttribute("line-number", "1");
		element.addAttribute("list", "1,2,3");
		element.addAttribute("param", "i");

		Element childElement = element.addElement("execute");

		childElement.addAttribute("function", "Click");
		childElement.addAttribute("locator1", "//here");
		childElement.addAttribute("value1", "${i}");

		PoshiRunnerValidation.validateForElement(
			element, "ValidateForElement.macro");

		Assert.assertEquals(
			"validateForElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("for");

		element.addAttribute("line-number", "1");
		element.addAttribute("list", "1,2,3");

		childElement = element.addElement("execute");

		childElement.addAttribute("function", "Click");
		childElement.addAttribute("locator1", "//here");
		childElement.addAttribute("value1", "${i}");

		PoshiRunnerValidation.validateForElement(
			element, "ValidateForElement.macro");

		Assert.assertEquals(
			"validateForElement is failing", "Missing param attribute",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("for");

		element.addAttribute("line-number", "1");
		element.addAttribute("list", "1,2,3");
		element.addAttribute("param", "i");

		PoshiRunnerValidation.validateForElement(
			element, "ValidateForElement.macro");

		Assert.assertEquals(
			"validateForElement is failing", "Missing child elements",
			getExceptionMessage());
	}

	@Test
	public void testValidateFunctionContext() {
		String filePath = getFilePath("ValidateFunctionContext.macro");

		Element element = PoshiRunnerContext.getMacroCommandElement(
			"ValidateFunctionContext#validateFunctionContextPass");

		List<Element> functionElements = element.elements("execute");

		for (Element functionElement : functionElements) {
			PoshiRunnerValidation.validateFunctionContext(
				functionElement, filePath);
		}

		Assert.assertEquals(
			"ValidateFunctionContext is failing", "", getExceptionMessage());

		element = PoshiRunnerContext.getMacroCommandElement(
			"ValidateFunctionContext#validateFunctionContextFail1");

		functionElements = element.elements("execute");

		for (Element functionElement : functionElements) {
			PoshiRunnerValidation.validateFunctionContext(
				functionElement, filePath);
		}

		Assert.assertEquals(
			"validateFunctionContext is failing", "Invalid path name ClickAt",
			getExceptionMessage());

		element = PoshiRunnerContext.getMacroCommandElement(
			"ValidateFunctionContext#validateFunctionContextFail2");

		functionElements = element.elements("execute");

		for (Element functionElement : functionElements) {
			PoshiRunnerValidation.validateFunctionContext(
				functionElement, filePath);
		}

		Assert.assertEquals(
			"validateFunctionContext is failing",
			"Invalid path locator Click#CLICK_THERE", getExceptionMessage());
	}

	@Test
	public void testValidateFunctionFile() throws Exception {
		Document document = DocumentHelper.createDocument();

		Element rootElement = document.addElement("definition");

		rootElement.addAttribute("default", "close");

		Element commandElement = rootElement.addElement("command");

		commandElement.addAttribute("name", "close");

		Element executeElement1 = commandElement.addElement("execute");

		executeElement1.addAttribute("argument1", "null");
		executeElement1.addAttribute("selenium", "selectWindow");

		Element executeElement2 = commandElement.addElement("execute");

		executeElement2.addAttribute("argument1", "relative=top");
		executeElement2.addAttribute("selenium", "selectFrame");

		PoshiRunnerValidation.validateFunctionFile(
			rootElement, "Close.function");

		Assert.assertEquals(
			"validateFunctionFile is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		rootElement = document.addElement("definition");

		rootElement.addAttribute("default", "open");

		commandElement = rootElement.addElement("command");

		commandElement.addAttribute("name", "open");

		executeElement1 = commandElement.addElement("execute");

		executeElement1.addAttribute("argument1", "//hello");
		executeElement1.addAttribute("selenium", "open");

		executeElement2 = commandElement.addElement("execute");

		executeElement2.addAttribute("macro", "MacroFileName#macroCommandName");

		PoshiRunnerValidation.validateFunctionFile(
			rootElement, "Open.function");

		Assert.assertEquals(
			"validateFunctionFile is failing", "Invalid or missing attribute",
			getExceptionMessage());
	}

	@Test
	public void testValidateHasChildElements() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");
		element.addAttribute("value", "value");

		Element childElement = element.addElement("var");

		childElement.addAttribute("name", "hello");
		childElement.addAttribute("value", "world");

		PoshiRunnerValidation.validateHasChildElements(
			element, "ValidateHasChildElements.macro");

		Assert.assertEquals(
			"validateHasChildElements is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");
		element.addAttribute("value", "value");

		PoshiRunnerValidation.validateHasChildElements(
			element, "ValidateHasChildElements.macro");

		Assert.assertEquals(
			"validateHasChildElements is failing", "Missing child elements",
			getExceptionMessage());
	}

	@Test
	public void testValidateHasMultiplePrimaryAttributeNames() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("selenium", "click");

		List<String> attributeNames = new ArrayList<>();

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			attributeNames.add(attribute.getName());
		}

		PoshiRunnerValidation.validateHasMultiplePrimaryAttributeNames(
			element, attributeNames, Arrays.asList("function", "selenium"),
			"ValidateHasMultiplePrimaryAttributeNames.macro");

		Assert.assertEquals(
			"validateHasMultiplePrimaryAttributeNames is failing", "",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");

		attributeNames = new ArrayList<>();

		attributes = element.attributes();

		for (Attribute attribute : attributes) {
			attributeNames.add(attribute.getName());
		}

		PoshiRunnerValidation.validateHasMultiplePrimaryAttributeNames(
			element, attributeNames, Arrays.asList("function", "selenium"),
			"ValidateHasMultiplePrimaryAttributeNames.macro");

		Assert.assertEquals(
			"validateHasMultiplePrimaryAttributeNames is failing",
			"Too many attributes", getExceptionMessage());
	}

	@Test
	public void testValidateHasNoAttributes() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("if");

		Element childElement = element.addElement("equals");

		childElement.addAttribute("arg1", "hello");
		childElement.addAttribute("arg2", "world");

		PoshiRunnerValidation.validateHasNoAttributes(
			element, "ValidateHasNoAttributes.macro");

		Assert.assertEquals(
			"validateHasNoAttributes is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("while");

		element.addAttribute("then", "Click");

		PoshiRunnerValidation.validateHasNoAttributes(
			element, "ValidateHasNoAttributes.macro");

		Assert.assertEquals(
			"validateHasNoAttributes is failing", "Invalid then attribute",
			getExceptionMessage());
	}

	@Test
	public void testValidateHasNoChildElements() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");

		PoshiRunnerValidation.validateHasNoChildElements(
			element, "ValidateHasNoChildElements.macro");

		Assert.assertEquals(
			"validateHasNoChildElements is failing", "", getExceptionMessage());

		Element childElement = element.addElement("var");

		childElement.addAttribute("name", "hello");
		childElement.addAttribute("value", "world");

		PoshiRunnerValidation.validateHasNoChildElements(
			element, "ValidateHasNoChildElements.macro");

		Assert.assertEquals(
			"validateHasNoChildElements is failing", "Invalid child elements",
			getExceptionMessage());
	}

	@Test
	public void testValidateHasPrimaryAttributeName() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");

		PoshiRunnerValidation.validateHasPrimaryAttributeName(
			element, Arrays.asList("function", "macro"),
			"ValidateHasPrimaryAttributeName.macro");

		Assert.assertEquals(
			"validateHasPrimaryAttributeName is failing", "",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("selenium", "click");

		PoshiRunnerValidation.validateHasPrimaryAttributeName(
			element, Arrays.asList("function", "selenium"),
			"ValidateHasPrimaryAttributeName.macro");

		Assert.assertEquals(
			"validateHasPrimaryAttributeName is failing", "Too many attributes",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		PoshiRunnerValidation.validateHasPrimaryAttributeName(
			element, Arrays.asList("function", "selenium"),
			"ValidateHasPrimaryAttributeName.macro");

		Assert.assertEquals(
			"validateHasPrimaryAttributeName is failing",
			"Invalid or missing attribute", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("selenium", "click");

		PoshiRunnerValidation.validateHasPrimaryAttributeName(
			element, Arrays.asList("function", "selenium"),
			Arrays.asList("function", "selenium"),
			"ValidateHasPrimaryAttributeName.macro");

		Assert.assertEquals(
			"validateHasPrimaryAttributeName is failing", "",
			getExceptionMessage());
	}

	@Test
	public void testValidateIfElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("if");

		Element conditionElement = element.addElement("equals");

		conditionElement.addAttribute("arg1", "true");
		conditionElement.addAttribute("arg2", "true");
		conditionElement.addAttribute("line-number", "2");

		Element thenElement = element.addElement("then");

		Element thenChildElement = thenElement.addElement("execute");

		thenChildElement.addAttribute("function", "Click");
		thenChildElement.addAttribute("locator1", "//here");

		PoshiRunnerValidation.validateIfElement(
			element, "ValidateIfElement.macro");

		Assert.assertEquals(
			"validateIfElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("if");

		conditionElement = element.addElement("execute");

		conditionElement.addAttribute("function", "Click");
		conditionElement.addAttribute("selenium", "click");

		thenElement = element.addElement("then");

		thenChildElement = thenElement.addElement("execute");

		thenChildElement.addAttribute("function", "Click");
		thenChildElement.addAttribute("locator1", "//here");

		PoshiRunnerValidation.validateIfElement(
			element, "ValidateIfElement.macro");

		Assert.assertEquals(
			"validateIfElement is failing",
			"Missing or invalid if condition element", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("if");

		conditionElement = element.addElement("equals");

		conditionElement.addAttribute("arg1", "true");
		conditionElement.addAttribute("arg2", "true");
		conditionElement.addAttribute("line-number", "2");

		thenElement = element.addElement("then");

		thenChildElement = thenElement.addElement("execute");

		thenChildElement.addAttribute("function", "Click");
		thenChildElement.addAttribute("locator1", "//here");

		PoshiRunnerValidation.validateIfElement(
			element, "ValidateIfElement.function");

		Assert.assertEquals(
			"validateIfElement is failing",
			"Missing or invalid if condition element", getExceptionMessage());
	}

	@Test
	public void testValidateMacroContext() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute(
			"macro", "ValidateMacroContext#validateMacroContextPass");

		Element childElement = element.addElement("var");

		childElement.addAttribute("name", "varName");
		childElement.addAttribute("value", "varValue");

		PoshiRunnerValidation.validateMacroContext(
			element, "macro", "ValidateMacroContext.macro");

		Assert.assertEquals(
			"validateMacroContext is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		element.addAttribute("macro", "ValidateMacroContext#fail");

		childElement = element.addElement("var");

		childElement.addAttribute("name", "varName");
		childElement.addAttribute("value", "varValue");

		PoshiRunnerValidation.validateMacroContext(
			element, "macro", "ValidateMacroContext.macro");

		Assert.assertEquals(
			"validateMacroContext is failing",
			"Invalid macro command ValidateMacroContext#fail",
			getExceptionMessage());
	}

	@Test
	public void testValidateMacroFile() throws Exception {
		Document document = DocumentHelper.createDocument();

		Element rootElement = document.addElement("definition");

		Element commandElement = rootElement.addElement("command");

		commandElement.addAttribute("name", "validateMacroFile");

		Element echoElement = commandElement.addElement("echo");

		echoElement.addAttribute("message", "hello world");

		PoshiRunnerValidation.validateMacroFile(
			rootElement, "ValidateMacroFile.macro");

		Assert.assertEquals(
			"validateMacroFile is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		rootElement = document.addElement("definition");

		commandElement = rootElement.addElement("command");

		commandElement.addAttribute("name", "validateMacroFile");

		Element containsElement = commandElement.addElement("contains");

		containsElement.addAttribute("string", "string");

		PoshiRunnerValidation.validateMacroFile(
			rootElement, "ValidateMacroFile.macro");

		Assert.assertEquals(
			"validateMacroFile is failing", "Invalid contains element",
			getExceptionMessage());
	}

	@Test
	public void testValidateMessageElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("echo");

		element.addAttribute("message", "This test passed");

		PoshiRunnerValidation.validateMessageElement(
			element, "ValidateMessageElement.macro");

		Assert.assertEquals(
			"validateMessageElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("echo");

		PoshiRunnerValidation.validateMessageElement(
			element, "ValidateMessageElement.macro");

		Assert.assertEquals(
			"validateMessageElement is failing", "Missing message attribute",
			getExceptionMessage());
	}

	@Test
	public void testValidateMethodExecuteElement() {
		String filePath = "validateMethodExecuteElement.macro";
		String invalidClassName = "com.liferay.poshi.runner.util.FakeUtil";
		String invalidMethodName = "FakeMethod";
		String validClassName = "com.liferay.poshi.runner.util.StringUtil";
		String validMethodName = "add";

		List<String> testClassNames = new ArrayList<>();

		testClassNames.add(invalidClassName);
		testClassNames.add(validClassName);

		List<String> testMethodNames = new ArrayList<>();

		testMethodNames.add(validMethodName);
		testMethodNames.add(invalidMethodName);

		List<List<String>> testArguments = new ArrayList<>();

		testArguments.add(new ArrayList<String>());
		testArguments.add(new ArrayList<String>());

		List<String> expectedMessages = new ArrayList<>();

		expectedMessages.add("Unable to find class " + invalidClassName);
		expectedMessages.add(
			"Unable to find method " + validClassName + "#" +
				invalidMethodName);

		for (int i = 0; i < testClassNames.size(); i++) {
			Document document = DocumentHelper.createDocument();

			Element executeElement = document.addElement("execute");

			executeElement.addAttribute("class", testClassNames.get(i));
			executeElement.addAttribute("method", testMethodNames.get(i));

			for (String argument : testArguments.get(i)) {
				Element argElement = executeElement.addElement("arg");

				argElement.addAttribute("value", argument);
			}

			PoshiRunnerValidation.validateMethodExecuteElement(
				executeElement, filePath);

			Assert.assertEquals(
				"validateMethodExecuteElement is failing",
				expectedMessages.get(i), getExceptionMessage());
		}
	}

	@Test
	public void testValidateNumberOfAttributes() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute("function", "Open");
		element.addAttribute("locator1", "http://www.liferay.com");

		PoshiRunnerValidation.validateNumberOfAttributes(
			element, 2, "ValidateNumberOfAttributes.macro");

		Assert.assertEquals(
			"validateNumberOfAttributes is failing", "", getExceptionMessage());

		PoshiRunnerValidation.validateNumberOfAttributes(
			element, 1, "ValidateNumberOfAttributes.macro");

		Assert.assertEquals(
			"validateNumberOfAttributes is failing", "Too many attributes",
			getExceptionMessage());

		PoshiRunnerValidation.validateNumberOfAttributes(
			element, 3, "ValidateNumberOfAttributes.macro");

		Assert.assertEquals(
			"validateNumberOfAttributes is failing", "Too few attributes",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		PoshiRunnerValidation.validateNumberOfAttributes(
			element, 2, "ValidateNumberOfAttributes.macro");

		Assert.assertEquals(
			"validateNumberOfAttributes is failing", "Missing attributes",
			getExceptionMessage());
	}

	@Test
	public void testValidateNumberOfChildElements() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		Element childElement1 = element.addElement("var");

		childElement1.addAttribute("name", "varName");
		childElement1.addAttribute("value", "varValue");

		PoshiRunnerValidation.validateNumberOfChildElements(
			element, 1, "ValidateNumberOfChildElements.macro");

		Assert.assertEquals(
			"validateNumberofChildElements is failing", "",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		PoshiRunnerValidation.validateNumberOfChildElements(
			element, 1, "ValidateNumberOfChildElements.macro");

		Assert.assertEquals(
			"validateNumberofChildElements is failing",
			"Missing child elements", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		childElement1 = element.addElement("var");

		childElement1.addAttribute("name", "varName");
		childElement1.addAttribute("value", "varValue");

		Element childElement2 = element.addElement("var");

		childElement2.addAttribute("name", "varName");
		childElement2.addAttribute("value", "varValue");

		PoshiRunnerValidation.validateNumberOfChildElements(
			element, 1, "ValidateNumberOfChildElements.macro");

		Assert.assertEquals(
			"validateNumberofChildElements is failing",
			"Too many child elements", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		childElement1 = element.addElement("var");

		childElement1.addAttribute("name", "varName");
		childElement1.addAttribute("value", "varValue");

		PoshiRunnerValidation.validateNumberOfChildElements(
			element, 2, "ValidateNumberOfChildElements.macro");

		Assert.assertEquals(
			"validateNumberofChildElements is failing",
			"Too few child elements", getExceptionMessage());
	}

	@Test
	public void testValidatePathFile() throws Exception {
		URL url = getURL("Click.path");

		Element element = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		PoshiRunnerValidation.validatePathFile(element, url.getFile());

		Assert.assertEquals(
			"validatePathFile is failing", "", getExceptionMessage());

		url = getURL("ValidatePathFile1.path");

		element = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		PoshiRunnerValidation.validatePathFile(element, url.getFile());

		Assert.assertEquals(
			"validatePathFile is failing", "Invalid definition element",
			getExceptionMessage());

		url = getURL("ValidatePathFile2.path");

		element = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		PoshiRunnerValidation.validatePathFile(element, url.getFile());

		Assert.assertEquals(
			"validatePathFile is failing", "Missing locator",
			getExceptionMessage());

		url = getURL("ValidatePathFile3.path");

		element = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		PoshiRunnerValidation.validatePathFile(element, url.getFile());

		Assert.assertEquals(
			"validatePathFile is failing", "Missing thead class name",
			getExceptionMessage());

		url = getURL("ValidatePathFile4.path");

		element = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		PoshiRunnerValidation.validatePathFile(element, url.getFile());

		Assert.assertEquals(
			"validatePathFile is failing",
			"Thead class name does not match file name", getExceptionMessage());

		url = getURL("ValidatePathFile5.path");

		element = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		PoshiRunnerValidation.validatePathFile(element, url.getFile());

		Assert.assertEquals(
			"validatePathFile is failing", "File name and title are different",
			getExceptionMessage());
	}

	@Test
	public void testValidatePossibleAttributeNames() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");
		element.addAttribute("value1", "there");

		PoshiRunnerValidation.validatePossibleAttributeNames(
			element, Arrays.asList("function", "locator1", "value1"),
			"ValidatePossibleAttributeNames.macro");

		Assert.assertEquals(
			"validatePossibleAttributeNames is failing", "",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");
		element.addAttribute("value", "there");

		PoshiRunnerValidation.validatePossibleAttributeNames(
			element, Arrays.asList("function", "locator1", "value1"),
			"ValidatePossibleAttributeNames.macro");

		Assert.assertEquals(
			"validatePossibleAttributeNames is failing",
			"Invalid value attribute", getExceptionMessage());
	}

	@Test
	public void testValidatePropertyElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("property");

		element.addAttribute("line-number", "1");
		element.addAttribute("name", "testray.main.component.name");
		element.addAttribute("value", "Tools");

		PoshiRunnerValidation.validatePropertyElement(
			element, "ValidatePossibleAttributeNames.macro");

		Assert.assertEquals(
			"validatePropertyElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("property");

		element.addAttribute("line-number", "1");
		element.addAttribute("name", "property.name");
		element.addAttribute("value", "Tools");

		PoshiRunnerValidation.validatePropertyElement(
			element, "ValidatePossibleAttributeNames.macro");

		Assert.assertEquals(
			"validatePropertyElement is failing",
			"Invalid property name property.name", getExceptionMessage());
	}

	@Test
	public void testValidateRequiredAttributeNames() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("property");

		element.addAttribute("line-number", "1");
		element.addAttribute("name", "testray.main.component.name");
		element.addAttribute("value", "Tools");

		PoshiRunnerValidation.validateRequiredAttributeNames(
			element, Arrays.asList("line-number", "name", "value"),
			"ValidateRequiredAttributeNames.macro");

		Assert.assertEquals(
			"validateRequiredAttributeNames is failing", "",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("property");

		element.addAttribute("name", "testray.main.component.name");
		element.addAttribute("value", "Tools");

		PoshiRunnerValidation.validateRequiredAttributeNames(
			element, Arrays.asList("line-number", "name", "value"),
			"ValidateRequiredAttributeNames.macro");

		Assert.assertEquals(
			"validateRequiredAttributeNames is failing",
			"Missing line-number attribute", getExceptionMessage());
	}

	@Test
	public void testValidateRequiredChildElementName() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");

		Element childElement = element.addElement("var");

		childElement.addAttribute("name", "varName");
		childElement.addAttribute("value", "varValue");

		String requiredChildElementName = "var";

		PoshiRunnerValidation.validateRequiredChildElementName(
			element, requiredChildElementName,
			"ValidateRequiredChildElementName,macro");

		Assert.assertEquals(
			"validateRequiredChildElementName is failing", "",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("execute");

		element.addAttribute("function", "Click");
		element.addAttribute("locator1", "//here");

		PoshiRunnerValidation.validateRequiredChildElementName(
			element, requiredChildElementName,
			"ValidateRequiredChildElementName,macro");

		Assert.assertEquals(
			"validateRequiredChildElementName is failing",
			"Missing required var child element", getExceptionMessage());
	}

	@Test
	public void testValidateRequiredChildElementNames() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("and");

		Element childElement1 = element.addElement("condition");

		childElement1.addAttribute("argument", "//here");
		childElement1.addAttribute("selenium", "isElementPresent");

		Element childElement2 = element.addElement("contains");

		childElement2.addAttribute("string", "name");
		childElement2.addAttribute("substring", "value");

		PoshiRunnerValidation.validateRequiredChildElementNames(
			element, Arrays.asList("condition", "contains"),
			"ValidateRequiredChildElementNames.macro");

		Assert.assertEquals(
			"validateRequiredChildElementNames is failing", "",
			getExceptionMessage());

		PoshiRunnerValidation.validateRequiredChildElementNames(
			element, Arrays.asList("condition", "contains", "equals"),
			"ValidateRequiredChildElementNames.macro");

		Assert.assertEquals(
			"validateRequiredChildElementNames is failing",
			"Missing required equals child element", getExceptionMessage());
	}

	@Test
	public void testValidateTestCaseFile() throws Exception {
		URL url = getURL("ValidateTestCaseFile1.testcase");

		Element element = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		PoshiRunnerValidation.validateTestCaseFile(element, url.getFile());

		Assert.assertEquals(
			"validateTestCaseFile is failing", "", getExceptionMessage());

		url = getURL("ValidateTestCaseFile2.testcase");

		element = PoshiRunnerGetterUtil.getRootElementFromURL(url);

		PoshiRunnerValidation.validateTestCaseFile(element, url.getFile());

		Assert.assertEquals(
			"validateTestCaseFile is failing", "Invalid execute element",
			getExceptionMessage());
	}

	@Test
	public void testValidateTestName() {
		PoshiRunnerValidation.validateTestName("ValidateTestName#testName");

		Assert.assertEquals(
			"validateTestName is failing", "", getExceptionMessage());

		PoshiRunnerValidation.validateTestName("ValidateTestName#fail");

		Assert.assertEquals(
			"validateTestName is failing", "Invalid test case command fail",
			getExceptionMessage());
	}

	@Test
	public void testValidateThenElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("if");

		Element thenElement = element.addElement("then");

		Element thenChildElement = thenElement.addElement("execute");

		thenChildElement.addAttribute("function", "Click");
		thenChildElement.addAttribute("locator1", "//here");

		PoshiRunnerValidation.validateThenElement(
			element, "ValidateThenElement.macro");

		Assert.assertEquals(
			"validateThenElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("if");

		PoshiRunnerValidation.validateThenElement(
			element, "ValidateThenElement.macro");

		Assert.assertEquals(
			"validateThenElement is failing", "Missing then element",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("if");

		Element thenElement1 = element.addElement("then");

		Element thenChildElement1 = thenElement1.addElement("execute");

		thenChildElement1.addAttribute("function", "Click");
		thenChildElement1.addAttribute("locator1", "//here");

		Element thenElement2 = element.addElement("then");

		Element thenChildElement2 = thenElement2.addElement("execute");

		thenChildElement2.addAttribute("function", "Click");
		thenChildElement2.addAttribute("locator1", "//here");

		PoshiRunnerValidation.validateThenElement(
			element, "ValidateThenElement.macro");

		Assert.assertEquals(
			"validateThenElement is failing", "Too many then elements",
			getExceptionMessage());
	}

	@Test
	public void testValidateToggleElement() {
		Document document = DocumentHelper.createDocument();

		Element toggleElement = document.addElement("toggle");

		toggleElement.addAttribute("name", "LRQA-TOGGLE");
		toggleElement.addAttribute("line-number", "1");

		Element onElement = toggleElement.addElement("on");

		onElement.addAttribute("line-number", "2");

		Element onChildElement = onElement.addElement("execute");

		onChildElement.addAttribute("function", "Click");
		onChildElement.addAttribute("locator1", "//here");
		onChildElement.addAttribute("line-number", "3");

		PoshiRunnerValidation.validateToggleElement(
			toggleElement, "ValidateToggleElement.macro");

		Assert.assertEquals(
			"ValidateToggleElement is failing", "", getExceptionMessage());

		Element onElement2 = toggleElement.addElement("on");

		onElement2.addAttribute("line-number", "5");

		Element onChildElement2 = onElement2.addElement("execute");

		onChildElement2.addAttribute("function", "Click");
		onChildElement2.addAttribute("locator1", "//here");
		onChildElement2.addAttribute("line-number", "6");

		PoshiRunnerValidation.validateToggleElement(
			toggleElement, "ValidateToggleElement.macro");

		Assert.assertEquals(
			"ValidateToggleElement is failing", "Too many on elements",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		toggleElement = document.addElement("toggle");

		toggleElement.addAttribute("name", "LRQA-TOGGLE");
		toggleElement.addAttribute("line-number", "1");

		Element offElement = toggleElement.addElement("off");

		offElement.addAttribute("line-number", "2");

		Element offChildElement = offElement.addElement("execute");

		offChildElement.addAttribute("function", "Click");
		offChildElement.addAttribute("locator1", "//here");
		offChildElement.addAttribute("line-number", "3");

		PoshiRunnerValidation.validateToggleElement(
			toggleElement, "ValidateToggleElement.macro");

		Assert.assertEquals(
			"ValidateToggleElement is failing", "", getExceptionMessage());

		Element offElement2 = toggleElement.addElement("off");

		offElement2.addAttribute("line-number", "5");

		Element offChildElement2 = offElement2.addElement("execute");

		offChildElement2.addAttribute("function", "Click");
		offChildElement2.addAttribute("locator1", "//here");
		offChildElement2.addAttribute("line-number", "6");

		PoshiRunnerValidation.validateToggleElement(
			toggleElement, "ValidateToggleElement.macro");

		Assert.assertEquals(
			"ValidateToggleElement is failing", "Too many off elements",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		toggleElement = document.addElement("toggle");

		toggleElement.addAttribute("name", "LRQA-TOGGLE");
		toggleElement.addAttribute("line-number", "1");

		PoshiRunnerValidation.validateToggleElement(
			toggleElement, "ValidateToggleElement.macro");

		Assert.assertEquals(
			"ValidateToggleElement is failing", "Missing child elements",
			getExceptionMessage());

		document = DocumentHelper.createDocument();

		toggleElement = document.addElement("toggle");

		toggleElement.addAttribute("line-number", "1");

		onElement = toggleElement.addElement("on");

		onElement.addAttribute("line-number", "2");

		onChildElement = onElement.addElement("execute");

		onChildElement.addAttribute("function", "Click");
		onChildElement.addAttribute("locator1", "//here");
		onChildElement.addAttribute("line-number", "3");

		PoshiRunnerValidation.validateToggleElement(
			toggleElement, "ValidateToggleElement.macro");

		Assert.assertEquals(
			"ValidateToggleElement is failing", "Missing name attribute",
			getExceptionMessage());
	}

	@Test
	public void testValidateVarElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("var");

		element.addAttribute("line-number", "1");
		element.addAttribute("name", "name");
		element.addAttribute("value", "value");

		PoshiRunnerValidation.validateVarElement(
			element, "ValidateVarElement.macro");

		Assert.assertEquals(
			"validateVarElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("var");

		element.addAttribute("line-number", "1");
		element.addAttribute("name", "name");
		element.addText("value");

		PoshiRunnerValidation.validateVarElement(
			element, "ValidateVarElement.macro");

		Assert.assertEquals(
			"validateVarElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("var");

		element.addAttribute("line-number", "1");
		element.addAttribute("name", "name");

		PoshiRunnerValidation.validateVarElement(
			element, "ValidateVarElement.macro");

		Assert.assertEquals(
			"validateVarElement is failing", "Missing value attribute",
			getExceptionMessage());
	}

	@Test
	public void testValidateWhileElement() {
		Document document = DocumentHelper.createDocument();

		Element element = document.addElement("while");

		Element childElement = element.addElement("condition");

		childElement.addAttribute("function", "isElementPresent");
		childElement.addAttribute("locator1", "//here");

		Element thenElement = element.addElement("then");

		Element executeElement = thenElement.addElement("execute");

		executeElement.addAttribute("function", "Click");
		executeElement.addAttribute("locator1", "//else if element");

		PoshiRunnerValidation.validateWhileElement(element, "While.macro");

		Assert.assertEquals(
			"validateWhileElement is failing", "", getExceptionMessage());

		document = DocumentHelper.createDocument();

		element = document.addElement("while");

		thenElement = element.addElement("then");

		executeElement = thenElement.addElement("execute");

		executeElement.addAttribute("function", "Click");
		executeElement.addAttribute("locator1", "//else if element");

		PoshiRunnerValidation.validateWhileElement(element, "While.macro");

		Assert.assertEquals(
			"validateWhileElement is failing",
			"Missing while condition element", getExceptionMessage());
	}

	protected String getExceptionMessage() {
		Set<Exception> exceptions = PoshiRunnerValidation.getExceptions();

		StringBuilder sb = new StringBuilder();

		for (Exception exception : exceptions) {
			String message = exception.getMessage();

			int x = message.indexOf("\n");

			if (x == -1) {
				sb.append(message);
			}
			else {
				sb.append(message.substring(0, x));
			}
		}

		PoshiRunnerValidation.clearExceptions();

		return sb.toString();
	}

	protected String getFilePath(String fileName) {
		String filePath =
			PropsValues.TEST_BASE_DIR_NAME +
				"resources/com/liferay/poshi/runner/dependencies/validation/" +
					fileName;

		if (OSDetector.isWindows()) {
			filePath = filePath.replace("/", "\\");
		}

		return filePath;
	}

	protected URL getURL(String fileName) throws MalformedURLException {
		return new URL("file:" + getFilePath(fileName));
	}

}