/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilderFileUtil {

	public SeleniumBuilderFileUtil(String baseDir) {
		_baseDir = baseDir;
	}

	public List<Element> getAllChildElements(
		Element element, String elementName) {

		List<Element> allChildElements = new ArrayList<Element>();

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			return allChildElements;
		}

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (childElementName.equals(elementName)) {
				allChildElements.add(childElement);
			}

			allChildElements.addAll(
				getAllChildElements(childElement, elementName));
		}

		return allChildElements;
	}

	public String getBaseDir() {
		return _baseDir;
	}

	public Set<String> getChildElementAttributeValues(
		Element element, String attributeName) {

		Set<String> childElementAttributeValues = new TreeSet<String>();

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			return childElementAttributeValues;
		}

		for (Element childElement : childElements) {
			String childElementName = childElement.attributeValue(
				attributeName);

			if (childElementName != null) {
				int x = childElementName.lastIndexOf(StringPool.POUND);

				if (x != -1) {
					childElementAttributeValues.add(
						childElementName.substring(0, x));
				}
			}

			childElementAttributeValues.addAll(
				getChildElementAttributeValues(childElement, attributeName));
		}

		return childElementAttributeValues;
	}

	public String getClassName(String fileName) {
		String classSuffix = getClassSuffix(fileName);

		return getClassName(fileName, classSuffix);
	}

	public String getClassName(String fileName, String classSuffix) {
		return
			getPackageName(fileName) + "." +
				getSimpleClassName(fileName, classSuffix);
	}

	public String getClassSimpleClassName(String className) {
		int x = className.lastIndexOf(CharPool.PERIOD);

		return className.substring(x + 1);
	}

	public String getClassSuffix(String fileName) {
		int x = fileName.indexOf(CharPool.PERIOD);

		String classSuffix = StringUtil.upperCaseFirstLetter(
			fileName.substring(x + 1));

		if (classSuffix.equals("Testcase")) {
			classSuffix = "TestCase";
		}
		else if (classSuffix.equals("Testsuite")) {
			classSuffix = "TestSuite";
		}

		return classSuffix;
	}

	public String getHTMLFileName(String fileName) {
		String javaFileName = getJavaFileName(fileName);

		return StringUtil.replace(javaFileName, ".java", ".html");
	}

	public String getJavaFileName(String fileName) {
		String classSuffix = getClassSuffix(fileName);

		return getJavaFileName(fileName, classSuffix);
	}

	public String getJavaFileName(String fileName, String classSuffix) {
		return
			getPackagePath(fileName) + "/" +
				getSimpleClassName(fileName, classSuffix) + ".java";
	}

	public int getLocatorCount(Element rootElement) {
		String xml = rootElement.asXML();

		for (int i = 1;; i++) {
			if (xml.contains("${locator" + i + "}")) {
				continue;
			}

			if (i > 1) {
				i--;
			}

			return i;
		}
	}

	public String getName(String fileName) {
		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.lastIndexOf(CharPool.PERIOD);

		return fileName.substring(x + 1, y);
	}

	public String getNormalizedContent(String fileName) throws Exception {
		String content = readFile(fileName);

		if (fileName.endsWith(".path")) {
			int x = content.indexOf("<tbody>");
			int y = content.indexOf("</tbody>");

			if ((x == -1) || (y == -1)) {
				throwValidationException(1002, fileName, "tbody");
			}

			String pathTbody = content.substring(x, y + 8);

			Map<String, Object> context = new HashMap<String, Object>();

			context.put("pathName", getName(fileName));
			context.put("pathTbody", pathTbody);

			String newContent = processTemplate("path_xml.ftl", context);

			if (!content.equals(newContent)) {
				content = newContent;

				writeFile(getBaseDir(), fileName, newContent, false);
			}
		}

		StringBundler sb = new StringBundler();

		int lineNumber = 1;

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			Pattern pattern = Pattern.compile("<[a-z\\-]+");

			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				for (String reservedTag : _reservedTags) {
					if (line.contains("<" + reservedTag)) {
						line = StringUtil.replace(
							line, matcher.group(),
							matcher.group() + " line-number=\"" + lineNumber +
								"\"");

						break;
					}
				}
			}

			sb.append(line);

			lineNumber++;
		}

		content = sb.toString();

		if (content != null) {
			content = content.trim();
			content = StringUtil.replace(content, "\n", "");
			content = StringUtil.replace(content, "\r\n", "");
			content = StringUtil.replace(content, "\t", " ");
			content = content.replaceAll(" +", " ");
		}

		return content;
	}

	public String getObjectName(String name) {
		return StringUtil.upperCaseFirstLetter(name);
	}

	public String getPackageName(String fileName) {
		String packagePath = getPackagePath(fileName);

		return StringUtil.replace(
			packagePath, StringPool.SLASH, StringPool.PERIOD);
	}

	public String getPackagePath(String fileName) {
		int x = fileName.lastIndexOf(StringPool.SLASH);

		return fileName.substring(0, x);
	}

	public Set<String> getPathLocatorKeys(Element rootElement) {
		Set<String> pathLocatorKeys = new HashSet<String>();

		Element bodyElement = rootElement.element("body");

		Element tableElement = bodyElement.element("table");

		Element tbodyElement = tableElement.element("tbody");

		List<Element> trElements = tbodyElement.elements();

		for (Element trElement : trElements) {
			Element tdElement = trElement.element("td");

			pathLocatorKeys.add(tdElement.getText());
		}

		return pathLocatorKeys;
	}

	public String getReturnType(String name) {
		if (name.startsWith("Is")) {
			return "boolean";
		}

		return "void";
	}

	public Element getRootElement(String fileName) throws Exception {
		String content = getNormalizedContent(fileName);

		try {
			Document document = SAXReaderUtil.read(content, true);

			Element rootElement = document.getRootElement();

			validate(fileName, rootElement);

			return rootElement;
		}
		catch (DocumentException de) {
			throwValidationException(1007, fileName, de);
		}

		return null;
	}

	public String getSimpleClassName(String fileName) {
		String classSuffix = getClassSuffix(fileName);

		return getSimpleClassName(fileName, classSuffix);
	}

	public String getSimpleClassName(String fileName, String classSuffix) {
		return getName(fileName) + classSuffix;
	}

	public String getVariableName(String name) {
		return TextFormatter.format(name, TextFormatter.I);
	}

	public String normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
	}

	public String readFile(String fileName) throws Exception {
		return FileUtil.read(getBaseDir() + "/" + fileName);
	}

	public void writeFile(String fileName, String content, boolean format)
		throws Exception {

		writeFile(getBaseDir() + "-generated", fileName, content, format);
	}

	public void writeFile(
			String baseDir, String fileName, String content, boolean format)
		throws Exception {

		File file = new File(baseDir + "/" + fileName);

		if (format) {
			ServiceBuilder.writeFile(file, content);
		}
		else {
			System.out.println("Writing " + file);

			FileUtil.write(file, content);
		}
	}

	protected String processTemplate(String name, Map<String, Object> context)
		throws Exception {

		return StringUtil.strip(
			FreeMarkerUtil.process(_TPL_ROOT + name, context), '\r');
	}

	protected void throwValidationException(int errorCode, String fileName) {
		throwValidationException(
			errorCode, fileName, null, null, null, null, null);
	}

	protected void throwValidationException(
		int errorCode, String fileName, Element element) {

		throwValidationException(
			errorCode, fileName, element, null, null, null, null);
	}

	protected void throwValidationException(
		int errorCode, String fileName, Element element, String string1) {

		throwValidationException(
			errorCode, fileName, element, null, string1, null, null);
	}

	protected void throwValidationException(
		int errorCode, String fileName, Element element, String string1,
		String string2) {

		throwValidationException(
			errorCode, fileName, element, null, string1, string2, null);
	}

	protected void throwValidationException(
		int errorCode, String fileName, Element element, String[] array) {

		throwValidationException(
			errorCode, fileName, element, array, null, null, null);
	}

	protected void throwValidationException(
		int errorCode, String fileName, Element element, String[] array,
		String string1, String string2, Exception e) {

		String prefix = "Error " + errorCode + ": ";
		String suffix = fileName;

		if (element != null) {
			suffix += ":" + element.attributeValue("line-number");
		}

		if (errorCode == 1000) {
			throw new IllegalArgumentException(
				prefix + "Invalid root element in " + suffix);
		}
		else if (errorCode == 1001) {
			throw new IllegalArgumentException(
				prefix + "Missing (" + StringUtil.merge(array, "|") +
					") child element in " + suffix);
		}
		else if (errorCode == 1002) {
			throw new IllegalArgumentException(
				prefix + "Invalid " + string1 + " element in " + suffix);
		}
		else if (errorCode == 1003) {
			throw new IllegalArgumentException(
				prefix + "Missing " + string1 + " attribute in " + suffix);
		}
		else if (errorCode == 1004) {
			throw new IllegalArgumentException(
				prefix + "Missing (" + StringUtil.merge(array, "|") +
					") attribute in " + suffix);
		}
		else if (errorCode == 1005) {
			throw new IllegalArgumentException(
				prefix + "Invalid " + string1 + " attribute in " + suffix);
		}
		else if (errorCode == 1006) {
			throw new IllegalArgumentException(
				prefix + "Invalid " + string1 + " attribute value in " +
					suffix);
		}
		else if (errorCode == 1007) {
			throw new IllegalArgumentException(
				prefix + "Poorly formed XML in " + suffix, e);
		}
		else if (errorCode == 1008) {
			throw new IllegalArgumentException(
				prefix + "Duplicate file name " + string1 + " at " + suffix);
		}
		else if (errorCode == 1009) {
			throw new IllegalArgumentException(
				prefix + "Duplicate command name " + string1 + " at " + suffix);
		}
		else if (errorCode == 1010) {
			throw new IllegalArgumentException(
				prefix + "Invalid locator-key " + string1 + " at " + suffix);
		}
		else if (errorCode == 1011) {
			throw new IllegalArgumentException(
				prefix + "Invalid " + string1 + " name " + string2 + " at " +
					suffix);
		}
		else if (errorCode == 1012) {
			throw new IllegalArgumentException(
				prefix + "Invalid " + string1 + " command " + string2 + " at " +
					suffix);
		}
		else if (errorCode == 2000) {
			throw new IllegalArgumentException(
				prefix + "Too many child elements in the " + string1 +
					" element in " + suffix);
		}
		else if (errorCode == 2001) {
			throw new IllegalArgumentException(
				prefix + "Action command " + string1 +
					" does not match a function name at " + suffix);
		}
		else if (errorCode == 2002) {
			throw new IllegalArgumentException(
				prefix + "Missing matching " + string1 + ".path for " + suffix);
		}
		else {
			throw new IllegalArgumentException(prefix + suffix);
		}
	}

	protected void throwValidationException(
		int errorCode, String fileName, Exception e) {

		throwValidationException(
			errorCode, fileName, null, null, null, null, e);
	}

	protected void throwValidationException(
		int errorCode, String fileName, String string1) {

		throwValidationException(
			errorCode, fileName, null, null, string1, null, null);
	}

	protected void validate(String fileName, Element rootElement)
		throws Exception {

		if (fileName.endsWith(".action")) {
			validateActionDocument(fileName, rootElement);
		}
		else if (fileName.endsWith(".function")) {
			validateFunctionDocument(fileName, rootElement);
		}
		else if (fileName.endsWith(".macro")) {
			validateMacroDocument(fileName, rootElement);
		}
		else if (fileName.endsWith(".path")) {
			validatePathDocument(fileName, rootElement);
		}
		else if (fileName.endsWith(".testcase")) {
			validateTestCaseDocument(fileName, rootElement);
		}
		else if (fileName.endsWith(".testsuite")) {
			validateTestSuiteDocument(fileName, rootElement);
		}
	}

	protected void validateActionCommandElement(
		String fileName, Element commandElement,
		String[] allowedBlockChildElementNames,
		String[] allowedExecuteAttributeNames,
		String[] allowedExecuteChildElementNames) {

		List<Element> elements = commandElement.elements();

		if (elements.isEmpty()) {
			throwValidationException(
				1001, fileName, commandElement,
				new String[] {"case", "default"});
		}

		for (Element element : elements) {
			List<Element> childElements = element.elements();

			String elementName = element.getName();

			if (childElements.size() > 1) {
				throwValidationException(
					2000, fileName, childElements.get(1), elementName);
			}

			if (elementName.equals("case")) {
				List<Attribute> attributes = element.attributes();

				boolean hasNeededAttributeName = false;

				for (Attribute attribute : attributes) {
					String attributeName = attribute.getName();

					if (attributeName.equals("comparator")) {
						String attributeValue = attribute.getValue();

						if (!attributeValue.equals("contains") &&
							!attributeValue.equals("endsWith") &&
							!attributeValue.equals("equals") &&
							!attributeValue.equals("startsWith")) {

							throwValidationException(
								1006, fileName, element, attributeName);
						}
					}
					else if (attributeName.startsWith("locator") ||
							 attributeName.startsWith("locator-key")) {

						String attributeValue = attribute.getValue();

						if (Validator.isNull(attributeValue)) {
							throwValidationException(
								1006, fileName, element, attributeName);
						}

						hasNeededAttributeName = true;
					}

					if (!attributeName.equals("comparator") &&
						!attributeName.equals("line-number") &&
						!attributeName.startsWith("locator") &&
						!attributeName.startsWith("locator-key")) {

						throwValidationException(
							1005, fileName, element, attributeName);
					}

					if (attributeName.equals("locator") ||
						attributeName.equals("locator-key")) {

						throwValidationException(
							1005, fileName, element, attributeName);
					}
				}

				if (!hasNeededAttributeName) {
					throwValidationException(
						1004, fileName, element,
						new String[] {"locator1", "locator-key1"});
				}

				validateBlockElement(
					fileName, element, new String[] {"execute"},
					new String[] {"function"}, new String[0], new String[0]);
			}
			else if (elementName.equals("default")) {
				List<Attribute> attributes = element.attributes();

				if (attributes.size() != 1) {
					Attribute attribute = attributes.get(1);

					String attributeName = attribute.getName();

					throwValidationException(
						1005, fileName, element, attributeName);
				}

				validateBlockElement(
					fileName, element, new String[] {"execute"},
					new String[] {"function"}, new String[0], new String[0]);
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}
	}

	protected void validateActionDocument(
		String fileName, Element rootElement) {

		if (!Validator.equals(rootElement.getName(), "definition")) {
			throwValidationException(1000, fileName, rootElement);
		}

		List<Element> elements = rootElement.elements();

		if (elements.isEmpty()) {
			throwValidationException(
				1001, fileName, rootElement, new String[] {"command"});
		}

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("command")) {
				String attributeValue = element.attributeValue("name");

				if (attributeValue == null) {
					throwValidationException(1003, fileName, element, "name");
				}
				else if (Validator.isNull(attributeValue)) {
					throwValidationException(1006, fileName, element, "name");
				}

				validateActionCommandElement(
					fileName, element, new String[] {"execute"},
					new String[] {"function"}, new String[0]);
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}
	}

	protected void validateBlockElement(
		String fileName, Element commandElement,
		String[] allowedBlockChildElementNames,
		String[] allowedExecuteAttributeNames,
		String[] allowedExecuteChildElementNames,
		String[] allowedIfConditionElementNames) {

		List<Element> elements = commandElement.elements();

		if (elements.isEmpty()) {
			throwValidationException(
				1001, fileName, commandElement, allowedBlockChildElementNames);
		}

		for (Element element : elements) {
			String elementName = element.getName();

			if (!ArrayUtil.contains(
					allowedBlockChildElementNames, elementName)) {

				throwValidationException(1002, fileName, element, elementName);
			}

			if (elementName.equals("echo") || elementName.equals("fail")) {
				validateSimpleElement(
					fileName, element, new String[] {"message"});
			}
			else if (elementName.equals("execute")) {
				validateExecuteElement(
					fileName, element, allowedExecuteAttributeNames, ".+",
					allowedExecuteChildElementNames);
			}
			else if (elementName.equals("if")) {
				validateIfElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames,
					allowedIfConditionElementNames);
			}
			else if (elementName.equals("var")) {
				validateSimpleElement(
					fileName, element, new String[] {"name", "value"});
			}
			else if (elementName.equals("while")) {
				validateWhileElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames,
					allowedIfConditionElementNames);
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}
	}

	protected void validateExecuteElement(
		String fileName, Element executeElement,
		String[] allowedExecuteAttributeNames,
		String allowedExecuteAttributeValuesRegex,
		String[] allowedExecuteChildElementNames) {

		boolean hasAllowedAttributeName = false;

		List<Attribute> attributes = executeElement.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (ArrayUtil.contains(
					allowedExecuteAttributeNames, attributeName)) {

				hasAllowedAttributeName = true;

				break;
			}
		}

		if (!hasAllowedAttributeName) {
			throwValidationException(
				1004, fileName, executeElement, allowedExecuteAttributeNames);
		}

		String action = executeElement.attributeValue("action");
		String function = executeElement.attributeValue("function");
		String macro = executeElement.attributeValue("macro");
		String selenium = executeElement.attributeValue("selenium");
		String testCase = executeElement.attributeValue("test-case");
		String testClass = executeElement.attributeValue("test-class");
		String testSuite = executeElement.attributeValue("test-suite");

		if (action != null) {
			if (Validator.isNull(action) ||
				!action.matches(allowedExecuteAttributeValuesRegex)) {

				throwValidationException(
					1006, fileName, executeElement, "action");
			}

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("action") &&
					!attributeName.equals("line-number") &&
					!attributeName.startsWith("locator") &&
					!attributeName.startsWith("locator-key") &&
					!attributeName.startsWith("value")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}

				if (attributeName.equals("locator") ||
					attributeName.equals("locator-key") ||
					attributeName.equals("value")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}
			}
		}
		else if (function != null) {
			if (Validator.isNull(function) ||
				!function.matches(allowedExecuteAttributeValuesRegex)) {

				throwValidationException(
					1006, fileName, executeElement, "function");
			}

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("function") &&
					!attributeName.equals("line-number") &&
					!attributeName.startsWith("locator") &&
					!attributeName.startsWith("value")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}

				if (attributeName.equals("locator") ||
					attributeName.equals("value")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}
			}
		}
		else if (macro != null) {
			if (Validator.isNull(macro) ||
				!macro.matches(allowedExecuteAttributeValuesRegex)) {

				throwValidationException(
					1006, fileName, executeElement, "macro");
			}

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("macro") &&
					!attributeName.equals("line-number")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}
			}
		}
		else if (selenium != null) {
			if (Validator.isNull(selenium) ||
				!selenium.matches(allowedExecuteAttributeValuesRegex)) {

				throwValidationException(
					1006, fileName, executeElement, "selenium");
			}

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("argument1") &&
					!attributeName.equals("argument2") &&
					!attributeName.equals("line-number") &&
					!attributeName.equals("selenium")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}
			}
		}
		else if (testCase != null) {
			if (Validator.isNull(testCase) ||
				!testCase.matches(allowedExecuteAttributeValuesRegex)) {

				throwValidationException(
					1006, fileName, executeElement, "test-case");
			}

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("line-number") &&
					!attributeName.equals("test-case")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}
			}
		}
		else if (testClass != null) {
			if (Validator.isNull(testClass) ||
				!testClass.matches(allowedExecuteAttributeValuesRegex)) {

				throwValidationException(
					1006, fileName, executeElement, "test-class");
			}

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("line-number") &&
					!attributeName.equals("test-class")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}
			}
		}
		else if (testSuite != null) {
			if (Validator.isNull(testSuite) ||
				!testSuite.matches(allowedExecuteAttributeValuesRegex)) {

				throwValidationException(
					1006, fileName, executeElement, "test-suite");
			}

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("line-number") &&
					!attributeName.equals("test-suite")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}
			}
		}
		else {
			throwValidationException(0, fileName);
		}

		List<Element> elements = executeElement.elements();

		if (allowedExecuteChildElementNames.length == 0) {
			if (!elements.isEmpty()) {
				Element element = elements.get(0);

				String elementName = element.getName();

				throwValidationException(1002, fileName, element, elementName);
			}
		}
		else {
			String executeElementName = executeElement.getName();

			for (Element element : elements) {
				String elementName = element.getName();

				if (executeElementName.equals("condition")) {
					throwValidationException(
						1002, fileName, element, elementName);
				}

				if (elementName.equals("var")) {
					validateSimpleElement(
						fileName, element, new String[] {"name", "value"});
				}
				else {
					throwValidationException(
						1002, fileName, element, elementName);
				}
			}
		}
	}

	protected void validateFunctionDocument(
		String fileName, Element rootElement) {

		if (!Validator.equals(rootElement.getName(), "definition")) {
			throwValidationException(1000, fileName, rootElement);
		}

		List<Element> elements = rootElement.elements();

		if (elements.isEmpty()) {
			throwValidationException(
				1001, fileName, rootElement, new String[] {"command"});
		}

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("command")) {
				String attributeValue = element.attributeValue("name");

				if (attributeValue == null) {
					throwValidationException(1003, fileName, element, "name");
				}
				else if (Validator.isNull(attributeValue)) {
					throwValidationException(1006, fileName, element, "name");
				}

				validateBlockElement(
					fileName, element, new String[] {"execute", "if"},
					new String[] {"function", "selenium"}, new String[0],
					new String[] {"condition"});
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}
	}

	protected void validateIfElement(
		String fileName, Element ifElement,
		String[] allowedBlockChildElementNames,
		String[] allowedExecuteAttributeNames,
		String[] allowedExecuteChildElementNames,
		String[] allowedIfConditionElementNames) {

		List<Element> elements = ifElement.elements();

		Set<String> elementNames = new HashSet<String>();

		boolean hasAllowedIfConditionElementNames = false;

		for (Element element : elements) {
			String elementName = element.getName();

			elementNames.add(elementName);

			if (ArrayUtil.contains(
					allowedIfConditionElementNames, elementName)) {

				hasAllowedIfConditionElementNames = true;
			}

			if (elementName.equals("condition")) {
				validateExecuteElement(
					fileName, element, allowedExecuteAttributeNames,
					".*(is|Is).+", allowedExecuteChildElementNames);
			}
			else if (elementName.equals("contains")) {
				validateSimpleElement(
					fileName, element, new String[] {"string", "substring"});
			}
			else if (elementName.equals("else") || elementName.equals("then")) {
				validateBlockElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames,
					allowedIfConditionElementNames);
			}
			else if (elementName.equals("elseif")) {
				validateIfElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames,
					allowedIfConditionElementNames);
			}
			else if (elementName.equals("equals")) {
				validateSimpleElement(
					fileName, element, new String[] {"arg1", "arg2"});
			}
			else if (elementName.equals("isset")) {
				validateSimpleElement(fileName, element, new String[] {"var"});
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}

		if (!hasAllowedIfConditionElementNames) {
			throwValidationException(
				1001, fileName, ifElement, allowedIfConditionElementNames);
		}

		if (!elementNames.contains("then")) {
			throwValidationException(
				1001, fileName, ifElement, new String[] {"then"});
		}
	}

	protected void validateMacroDocument(String fileName, Element rootElement) {
		if (!Validator.equals(rootElement.getName(), "definition")) {
			throwValidationException(1000, fileName, rootElement);
		}

		List<Element> elements = rootElement.elements();

		if (elements.isEmpty()) {
			throwValidationException(
				1001, fileName, rootElement, new String[] {"command", "var"});
		}

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("command")) {
				String attributeValue = element.attributeValue("name");

				if (attributeValue == null) {
					throwValidationException(1003, fileName, element, "name");
				}
				else if (Validator.isNull(attributeValue)) {
					throwValidationException(1006, fileName, element, "name");
				}

				validateBlockElement(
					fileName, element,
					new String[] {
						"echo", "execute", "fail", "if", "var","while"
					},
					new String[] {"action", "macro"}, new String[] {"var"},
					new String[] {"condition", "contains", "equals", "isset"});
			}
			else if (elementName.equals("var")) {
				validateSimpleElement(
					fileName, element, new String[] {"name", "value"});
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}
	}

	protected void validatePathDocument(String fileName, Element rootElement) {
		Element headElement = rootElement.element("head");

		Element titleElement = headElement.element("title");

		String title = titleElement.getText();

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.lastIndexOf(CharPool.PERIOD);

		String shortFileName = fileName.substring(x + 1, y);

		if ((title == null) || !shortFileName.equals(title)) {
			throwValidationException(0, fileName);
		}

		Element bodyElement = rootElement.element("body");

		Element tableElement = bodyElement.element("table");

		Element theadElement = tableElement.element("thead");

		Element trElement = theadElement.element("tr");

		Element tdElement = trElement.element("td");

		String tdText = tdElement.getText();

		if ((tdText == null) || !shortFileName.equals(tdText)) {
			throwValidationException(0, fileName);
		}

		Element tbodyElement = tableElement.element("tbody");

		List<Element> elements = tbodyElement.elements();

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("tr")) {
				validatePathTrElement(fileName, element);
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}
	}

	protected void validatePathTrElement(String fileName, Element trElement) {
		List<Element> elements = trElement.elements();

		for (Element element : elements) {
			String elementName = element.getName();

			if (!elementName.equals("td")) {
				throwValidationException(1002, fileName, element, elementName);
			}
		}

		if (elements.size() < 3) {
			throwValidationException(
				1001, fileName, trElement, new String[] {"td"});
		}

		if (elements.size() > 3) {
			Element element = elements.get(3);

			String elementName = element.getName();

			throwValidationException(1002, fileName, element, elementName);
		}
	}

	protected void validateSimpleElement(
		String fileName, Element element, String[] neededAttributes) {

		Map<String, Boolean> hasNeededAttributes =
			new HashMap<String, Boolean>();

		for (String neededAttribute : neededAttributes) {
			hasNeededAttributes.put(neededAttribute, false);
		}

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();
			String attributeValue = attribute.getValue();

			if (attributeValue == null) {
				throwValidationException(
					1006, fileName, element, attributeName);
			}

			if (hasNeededAttributes.containsKey(attributeName)) {
				hasNeededAttributes.put(attributeName, true);
			}

			if (!attributeName.equals("line-number") &&
				!hasNeededAttributes.containsKey(attributeName)) {

				throwValidationException(
					1005, fileName, element, attributeName);
			}
		}

		for (String neededAttribute : neededAttributes) {
			if (!hasNeededAttributes.get(neededAttribute)) {
				if (!neededAttribute.equals("value")) {
					throwValidationException(
						1004, fileName, element, neededAttributes);
				}
				else {
					if (Validator.isNull(element.getText())) {
						throwValidationException(
							1004, fileName, element, neededAttributes);
					}
				}
			}
		}

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			Element childElement = childElements.get(0);

			String childElementName = childElement.getName();

			throwValidationException(
				1002, fileName, childElement, childElementName);
		}
	}

	protected void validateTestCaseDocument(
		String fileName, Element rootElement) {

		if (!Validator.equals(rootElement.getName(), "definition")) {
			throwValidationException(1000, fileName, rootElement);
		}

		List<Element> elements = rootElement.elements();

		if (elements.isEmpty()) {
			throwValidationException(
				1001, fileName, rootElement, new String[] {"command"});
		}

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("command")) {
				String attributeValue = element.attributeValue("name");

				if (attributeValue == null) {
					throwValidationException(1003, fileName, element, "name");
				}
				else if (Validator.isNull(attributeValue)) {
					throwValidationException(1006, fileName, element, "name");
				}

				validateBlockElement(
					fileName, element, new String[] {"execute", "var"},
					new String[] {"action", "macro"}, new String[] {"var"},
					new String[0]);
			}
			else if (elementName.equals("set-up") ||
					 elementName.equals("tear-down")) {

				List<Attribute> attributes = element.attributes();

				for (Attribute attribute : attributes) {
					String attributeName = attribute.getName();

					if (!attributeName.equals("line-number")) {
						throwValidationException(
							1005, fileName, element, attributeName);
					}
				}

				validateBlockElement(
					fileName, element, new String[] {"execute", "var"},
					new String[] {"action", "macro"}, new String[] {"var"},
					new String[0]);
			}
			else if (elementName.equals("var")) {
				validateSimpleElement(
					fileName, element, new String[] {"name", "value"});
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}
	}

	protected void validateTestSuiteDocument(
		String fileName, Element rootElement) {

		if (!Validator.equals(rootElement.getName(), "definition")) {
			throwValidationException(1000, fileName, rootElement);
		}

		List<Element> elements = rootElement.elements();

		if (elements.isEmpty()) {
			throwValidationException(
				1001, fileName, rootElement, new String[] {"execute"});
		}

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("execute")) {
				validateExecuteElement(
					fileName, element,
					new String[] {"test-case", "test-class", "test-suite"},
					".+", new String[0]);
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}
	}

	protected void validateWhileElement(
		String fileName, Element whileElement,
		String[] allowedBlockChildElementNames,
		String[] allowedExecuteAttributeNames,
		String[] allowedExecuteChildElementNames,
		String[] allowedIfConditionElementNames) {

		List<Element> elements = whileElement.elements();

		Set<String> elementNames = new HashSet<String>();

		for (Element element : elements) {
			String elementName = element.getName();

			elementNames.add(elementName);

			if (elementName.equals("condition")) {
				validateExecuteElement(
					fileName, element, allowedExecuteAttributeNames,
					".*(is|Is).+", allowedExecuteChildElementNames);
			}
			else if (elementName.equals("then")) {
				validateBlockElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames,
					allowedIfConditionElementNames);
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}

		if (!elementNames.contains("condition") ||
			!elementNames.contains("then")) {

			throwValidationException(
				1001, fileName, whileElement,
				new String[] {"condition", "then"});
		}
	}

	private static final String _TPL_ROOT =
		"com/liferay/portal/tools/seleniumbuilder/dependencies/";

	private static List<String> _reservedTags = ListUtil.fromArray(
		new String[] {
			"case", "command", "condition", "contains", "default", "definition",
			"echo", "else", "elseif", "equals", "execute", "fail", "if",
			"isset", "set-up", "td", "tear-down", "then", "tr", "while", "var"
		});

	private String _baseDir;

}