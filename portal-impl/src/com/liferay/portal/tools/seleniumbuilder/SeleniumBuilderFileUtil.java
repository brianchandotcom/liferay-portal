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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;

import java.io.File;

import java.util.HashSet;
import java.util.List;
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
		int y = fileName.indexOf(CharPool.PERIOD);

		return fileName.substring(x + 1, y);
	}

	public String getNormalizedContent(String fileName) throws Exception {
		String content = readFile(fileName);

		StringBundler sb = new StringBundler();

		int lineNumber = 1;

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			Pattern pattern = Pattern.compile("<[a-z\\-]+");

			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				line = StringUtil.replace(
					line, matcher.group(),
					matcher.group() + " line-number=\"" + lineNumber + "\"");
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

	public String getReturnType(String name) {
		if (name.startsWith("Is")) {
			return "boolean";
		}

		return "void";
	}

	public Element getRootElement(String fileName) throws Exception {
		String content = getNormalizedContent(fileName);

		Document document = SAXReaderUtil.read(content, true);

		Element rootElement = document.getRootElement();

		validate(fileName, rootElement);

		return rootElement;
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

		File file = new File(getBaseDir() + "-generated/" + fileName);

		if (format) {
			ServiceBuilder.writeFile(file, content);
		}
		else {
			System.out.println("Writing " + file);

			FileUtil.write(file, content);
		}
	}

	protected void throwValidationException(int errorCode, String fileName) {
		throwValidationException(errorCode, fileName, null, null, null);
	}

	protected void throwValidationException(
		int errorCode, String fileName, Element element) {

		throwValidationException(errorCode, fileName, element, null, null);
	}

	protected void throwValidationException(
		int errorCode, String fileName, Element element, String string) {

		throwValidationException(errorCode, fileName, element, null, string);
	}

	protected void throwValidationException(
		int errorCode, String fileName, Element element, String[] array) {

		throwValidationException(errorCode, fileName, element, array, null);
	}

	protected void throwValidationException(
		int errorCode, String fileName, Element element, String[] array,
		String string) {

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
				prefix + "Invalid " + string + " element in " + suffix);
		}
		else if (errorCode == 1003) {
			throw new IllegalArgumentException(
				prefix + "Missing " + string + " attribute in " + suffix);
		}
		else if (errorCode == 1004) {
			throw new IllegalArgumentException(
				prefix + "Missing (" + StringUtil.merge(array, "|") +
					") attribute in " + suffix);
		}
		else if (errorCode == 1005) {
			throw new IllegalArgumentException(
				prefix + "Invalid " + string + " attribute in " + suffix);
		}
		else if (errorCode == 1006) {
			throw new IllegalArgumentException(
				prefix + "Invalid " + string + " attribute value in " + suffix);
		}
		else if (errorCode == 2000) {
			throw new IllegalArgumentException(
				prefix + "Too many child elements in the " + string +
					" element in " + suffix);
		}
		else {
			throw new IllegalArgumentException(prefix + suffix);
		}
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
					new String[] {"function"}, new String[0]);
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
					new String[] {"function"}, new String[0]);
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
		String[] allowedExecuteChildElementNames) {

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

			if (elementName.equals("execute")) {
				validateExecuteElement(
					fileName, element, allowedExecuteAttributeNames, ".+",
					allowedExecuteChildElementNames);
			}
			else if (elementName.equals("if")) {
				validateIfElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames);
			}
			else if (elementName.equals("var")) {
				validateVarElement(fileName, element);
			}
			else if (elementName.equals("while")) {
				validateWhileElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames);
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
		String testSuite = executeElement.attributeValue("test-suite");

		if (Validator.isNotNull(action) &&
			action.matches(allowedExecuteAttributeValuesRegex)) {

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("action") &&
					!attributeName.equals("line-number") &&
					!attributeName.startsWith("locator") &&
					!attributeName.startsWith("locator-key") &&
					!attributeName.startsWith("value")) {

					throwValidationException(0, fileName);
				}

				if (attributeName.equals("locator") ||
					attributeName.equals("locator-key") ||
					attributeName.equals("value")) {

					throwValidationException(0, fileName);
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
		else if (Validator.isNotNull(macro) &&
				 macro.matches(allowedExecuteAttributeValuesRegex)) {

			if (attributes.size() != 2) {
				throwValidationException(0, fileName);
			}
		}
		else if (selenium != null) {
			if (Validator.isNull(selenium) &&
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
		else if (Validator.isNotNull(testCase) &&
				 testCase.matches(allowedExecuteAttributeValuesRegex)) {

			if (attributes.size() != 2) {
				throwValidationException(0, fileName);
			}
		}
		else if (Validator.isNotNull(testSuite) &&
				 testSuite.matches(allowedExecuteAttributeValuesRegex)) {

			if (attributes.size() != 2) {
				throwValidationException(0, fileName);
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
			for (Element element : elements) {
				String elementName = element.getName();

				if (elementName.equals("var")) {
					validateVarElement(fileName, element);
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
					new String[] {"function", "selenium"}, new String[0]);
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
		String[] allowedExecuteChildElementNames) {

		List<Element> elements = ifElement.elements();

		Set<String> elementNames = new HashSet<String>();

		for (Element element : elements) {
			String elementName = element.getName();

			elementNames.add(elementName);

			if (elementName.equals("condition")) {
				validateExecuteElement(
					fileName, element, allowedExecuteAttributeNames,
					".*(is|Is).+", allowedExecuteChildElementNames);
			}
			else if (elementName.equals("else") || elementName.equals("then")) {
				validateBlockElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames);
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}

		if (!elementNames.contains("condition") ||
			!elementNames.contains("then")) {

			throwValidationException(
				1001, fileName, ifElement, new String[] {"condition", "then"});
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
				if (Validator.isNull(element.attributeValue("name"))) {
					throwValidationException(1003, fileName, element, "name");
				}

				validateBlockElement(
					fileName, element,
					new String[] {"execute", "if", "var", "while"},
					new String[] {"action", "macro"}, new String[] {"var"});
			}
			else if (elementName.equals("var")) {
				validateVarElement(fileName, element);
			}
			else {
				throwValidationException(
					1002, fileName, rootElement, elementName);
			}
		}
	}

	protected void validatePathDocument(String fileName, Element rootElement) {
		Element headElement = rootElement.element("head");

		Element titleElement = headElement.element("title");

		String title = titleElement.getText();

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

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
	}

	protected void validateVarElement(String fileName, Element varElement) {
		List<Attribute> attributes = varElement.attributes();

		if (attributes.isEmpty()) {
			throwValidationException(0, fileName);
		}

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (!attributeName.equals("line-number") &&
				!attributeName.equals("name") &&
				!attributeName.equals("value")) {

				throwValidationException(0, fileName);
			}
		}

		List<Element> elements = varElement.elements();

		if (!elements.isEmpty()) {
			throwValidationException(0, fileName);
		}
	}

	protected void validateWhileElement(
		String fileName, Element whileElement,
		String[] allowedBlockChildElementNames,
		String[] allowedExecuteAttributeNames,
		String[] allowedExecuteChildElementNames) {

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
					allowedExecuteChildElementNames);
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

	private String _baseDir;

}