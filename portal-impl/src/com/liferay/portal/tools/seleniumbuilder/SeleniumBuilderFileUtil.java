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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilderFileUtil {

	public SeleniumBuilderFileUtil(String baseDirName, String projectDirName) {
		_baseDirName = baseDirName;

		Properties properties = new Properties();

		try {
			String content = FileUtil.read(projectDirName + "/test.properties");

			InputStream inputStream = new ByteArrayInputStream(
				content.getBytes());

			properties.load(inputStream);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		_componentNames = ListUtil.fromArray(
			StringUtil.split(properties.getProperty("component.names")));
		_testcaseAvailablePropertyNames = ListUtil.fromArray(
			StringUtil.split(
				properties.getProperty("testcase.available.property.names")));
		_testrayAvailableComponentNames = ListUtil.fromArray(
			StringUtil.split(
				properties.getProperty("testray.available.component.names")));
	}

	public String escapeHtml(String input) {
		return StringEscapeUtils.escapeHtml(input);
	}

	public String escapeJava(String input) {
		return StringEscapeUtils.escapeJava(input);
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

	public String getBaseDirName() {
		return _baseDirName;
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

	public Set<String> getChildElementLineNumbers(Element element) {
		Set<String> childElementLineNumbers = new TreeSet<String>();

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			return childElementLineNumbers;
		}

		for (Element childElement : childElements) {
			String childElementLineNumber = childElement.attributeValue(
				"line-number");

			if (childElementLineNumber != null) {
				childElementLineNumbers.add(childElementLineNumber);
			}

			childElementLineNumbers.addAll(
				getChildElementLineNumbers(childElement));
		}

		return childElementLineNumbers;
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

		return classSuffix;
	}

	public List<String> getComponentNames() {
		return _componentNames;
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

				writeFile(getBaseDirName(), fileName, newContent, false);
			}
		}

		StringBundler sb = new StringBundler();

		int lineNumber = 1;

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			Matcher matcher = _tagPattern.matcher(line);

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
		return FileUtil.read(getBaseDirName() + "/" + fileName);
	}

	public void writeFile(String fileName, String content, boolean format)
		throws Exception {

		writeFile(getBaseDirName() + "-generated", fileName, content, format);
	}

	public void writeFile(
			String baseDirName, String fileName, String content, boolean format)
		throws Exception {

		File file = new File(baseDirName + "/" + fileName);

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
		String string1) {

		throwValidationException(
			errorCode, fileName, element, array, string1, null, null);
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
		else if (errorCode == 1013) {
			throw new IllegalArgumentException(
				prefix + "Invalid method " + string1 + " at " + suffix);
		}
		else if (errorCode == 1014) {
			throw new IllegalArgumentException(
				prefix + "Invalid path " + string1 + " at " + suffix);
		}
		else if (errorCode == 1015) {
			throw new IllegalArgumentException(
				prefix + "Poorly formed test case command " + string1 + " at " +
					suffix);
		}
		else if (errorCode == 1016) {
			throw new IllegalArgumentException(
				prefix + "Invalid " + string1 + " attribute value " + string2 +
					" in " + suffix);
		}
		else if (errorCode == 1017) {
			throw new IllegalArgumentException(
				prefix + "Description '" + string1 +
					"' must end with a '.' in " + suffix);
		}
		else if (errorCode == 1018) {
			throw new IllegalArgumentException(
				prefix + "Missing (" + StringUtil.merge(array, "|") +
					") in attribute " + string1 + " at " + suffix);
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
		else if (errorCode == 2003) {
			throw new IllegalArgumentException(
				prefix + "Illegal XPath " + string1 + " in " + suffix);
		}
		else if (errorCode == 2004) {
			throw new IllegalArgumentException(
				prefix + "Description '" + string1 +
					"' must title convention in " + suffix);
		}
		else if (errorCode == 3001) {
			throw new IllegalArgumentException(
				prefix + "The property '" + string1 +
					"' has an invalid component name '" + string2 + "' in " +
						suffix);
		}
		else if (errorCode == 3002) {
			throw new IllegalArgumentException(
				prefix + "Missing property '" + string1 + "' for " + suffix);
		}
		else if (errorCode == 3003) {
			throw new IllegalArgumentException(
				prefix + "Invalid property " + string1 + " at " + suffix);
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
			List<Element> descriptionElements = element.elements("description");

			String elementName = element.getName();

			if (descriptionElements.size() > 1) {
				throwValidationException(
					2000, fileName, descriptionElements.get(1), elementName);
			}

			List<Element> executeChildElements = element.elements("execute");

			if (executeChildElements.size() > 1) {
				throwValidationException(
					2000, fileName, executeChildElements.get(1), elementName);
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
					else if (attributeName.equals("locator1") ||
							 attributeName.equals("locator2") ||
							 attributeName.equals("locator-key1") ||
							 attributeName.equals("locator-key2") ||
							 attributeName.equals("value1") ||
							 attributeName.equals("value2")) {

						String attributeValue = attribute.getValue();

						if (Validator.isNull(attributeValue)) {
							throwValidationException(
								1006, fileName, element, attributeName);
						}

						hasNeededAttributeName = true;
					}

					if (!attributeName.equals("comparator") &&
						!attributeName.equals("line-number") &&
						!attributeName.equals("locator1") &&
						!attributeName.equals("locator2") &&
						!attributeName.equals("locator-key1") &&
						!attributeName.equals("locator-key2") &&
						!attributeName.equals("value1") &&
						!attributeName.equals("value2")) {

						throwValidationException(
							1005, fileName, element, attributeName);
					}

					if (attributeName.equals("locator") ||
						attributeName.equals("locator-key") ||
						attributeName.equals("value")) {

						throwValidationException(
							1005, fileName, element, attributeName);
					}
				}

				if (!hasNeededAttributeName) {
					throwValidationException(
						1004, fileName, element,
						new String[] {"locator1", "locator-key1", "value1"});
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
					fileName, element, new String[] {"description", "execute"},
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

			if (elementName.equals("description")) {
				validateSimpleElement(
					fileName, element, new String[] {"message"});

				String message = element.attributeValue("message");

				if (!message.endsWith(".")) {
					throwValidationException(
						1017, fileName, commandElement, message);
				}
			}
			else if (elementName.equals("echo") || elementName.equals("fail")) {
				validateSimpleElement(
					fileName, element, new String[] {"message"});
			}
			else if (elementName.equals("execute")) {
				validateExecuteElement(
					fileName, element, allowedExecuteAttributeNames, ".+",
					allowedExecuteChildElementNames);
			}
			else if (elementName.equals("for")) {
				validateForElement(
					fileName, element, new String[] {"list", "param"},
					allowedBlockChildElementNames, allowedExecuteAttributeNames,
					allowedExecuteChildElementNames,
					allowedIfConditionElementNames);
			}
			else if (elementName.equals("if") || elementName.equals("while")) {
				validateIfElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames,
					allowedIfConditionElementNames);
			}
			else if (elementName.equals("property")) {
				validatePropertyElement(fileName, element);
			}
			else if (elementName.equals("take-screenshot")) {
				validateSimpleElement(fileName, element, new String[0]);
			}
			else if (elementName.equals("var")) {
				validateVarElement(fileName, element);
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
		String testCaseCommand = executeElement.attributeValue(
			"test-case-command");
		String testClass = executeElement.attributeValue("test-class");

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
					!attributeName.equals("locator1") &&
					!attributeName.equals("locator2") &&
					!attributeName.equals("locator-key1") &&
					!attributeName.equals("locator-key2") &&
					!attributeName.equals("value1") &&
					!attributeName.equals("value2")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}

				if (attributeName.equals("locator") ||
					attributeName.equals("locator-key") ||
					attributeName.equals("value")) {

					throwValidationException(
						1005, fileName, executeElement, attributeName);
				}

				String attributeValue = attribute.getValue();

				if (attributeName.equals("value1") &&
					attributeValue.contains("move-file")) {

					if (!attributeValue.contains("-Dfile") ||
						!attributeValue.contains("-Dtofile")) {

						throwValidationException(
							1018, fileName, executeElement,
							new String[] {"-Dfile", "-Dtofile"}, "value1");
					}
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
					!attributeName.equals("ignore-javascript-error") &&
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

			if (testCase.contains("#")) {
				int x = testCase.lastIndexOf("#");

				if (x == -1) {
					throwValidationException(
						1015, fileName, executeElement, testCaseCommand);
				}

				String testCaseName = testCase.substring(0, x);

				String testCaseCommandName = testCase.substring(x + 1);

				if (Validator.isNull(testCaseCommandName) ||
					Validator.isNull(testCaseName) ||
					!testCaseName.equals("super")) {

					throwValidationException(
						1015, fileName, executeElement, testCase);
				}
			}
			else {
				throwValidationException(
					1015, fileName, executeElement, testCase);
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
		else if (testCaseCommand != null) {
			if (Validator.isNull(testCaseCommand) ||
				!testCaseCommand.matches(allowedExecuteAttributeValuesRegex)) {

				throwValidationException(
					1006, fileName, executeElement, "test-case-command");
			}

			if (testCaseCommand.contains("#")) {
				int x = testCaseCommand.lastIndexOf("#");

				String testCaseName = testCaseCommand.substring(0, x);

				String testCaseCommandName = testCaseCommand.substring(x + 1);

				if (Validator.isNull(testCaseCommandName) ||
					Validator.isNull(testCaseName)) {

					throwValidationException(
						1015, fileName, executeElement, testCaseCommand);
				}
			}
			else {
				throwValidationException(
					1015, fileName, executeElement, testCaseCommand);
			}

			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (!attributeName.equals("line-number") &&
					!attributeName.equals("test-case-command")) {

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
					validateVarElement(fileName, element);
				}
				else {
					throwValidationException(
						1002, fileName, element, elementName);
				}
			}
		}
	}

	protected void validateForElement(
		String fileName, Element forElement, String[] neededAttributes,
		String[] allowedBlockChildElementNames,
		String[] allowedExecuteAttributeNames,
		String[] allowedExecuteChildElementNames,
		String[] allowedIfConditionElementNames) {

		Map<String, Boolean> hasNeededAttributes =
			new HashMap<String, Boolean>();

		for (String neededAttribute : neededAttributes) {
			hasNeededAttributes.put(neededAttribute, false);
		}

		List<Attribute> attributes = forElement.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();
			String attributeValue = attribute.getValue();

			if (!_allowedNullAttributes.contains(attributeName) &&
				Validator.isNull(attributeValue)) {

				throwValidationException(
					1006, fileName, forElement, attributeName);
			}

			if (hasNeededAttributes.containsKey(attributeName)) {
				hasNeededAttributes.put(attributeName, true);
			}

			if (!attributeName.equals("line-number") &&
				!hasNeededAttributes.containsKey(attributeName)) {

				throwValidationException(
					1005, fileName, forElement, attributeName);
			}
		}

		for (String neededAttribute : neededAttributes) {
			if (!hasNeededAttributes.get(neededAttribute)) {
				throwValidationException(
					1004, fileName, forElement, neededAttributes);
			}
		}

		validateBlockElement(
			fileName, forElement, allowedBlockChildElementNames,
			allowedExecuteAttributeNames, allowedExecuteChildElementNames,
			allowedIfConditionElementNames);
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

			String ifElementName = ifElement.getName();

			if (elementName.equals("and") || elementName.equals("not") ||
				elementName.equals("or")) {

				validateIfElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames,
					allowedIfConditionElementNames);
			}
			else if (elementName.equals("condition")) {
				validateExecuteElement(
					fileName, element, allowedExecuteAttributeNames,
					".*(is|Is).+", allowedExecuteChildElementNames);
			}
			else if (elementName.equals("contains")) {
				validateSimpleElement(
					fileName, element, new String[] {"string", "substring"});
			}
			else if (elementName.equals("else")) {
				if (ifElementName.equals("while")) {
					throwValidationException(
						1002, fileName, element, elementName);
				}

				validateBlockElement(
					fileName, element, allowedBlockChildElementNames,
					allowedExecuteAttributeNames,
					allowedExecuteChildElementNames,
					allowedIfConditionElementNames);
			}
			else if (elementName.equals("elseif")) {
				if (ifElementName.equals("while")) {
					throwValidationException(
						1002, fileName, element, elementName);
				}

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

		if (!hasAllowedIfConditionElementNames) {
			throwValidationException(
				1001, fileName, ifElement, allowedIfConditionElementNames);
		}

		if (Validator.equals(ifElement.getName(), "and") ||
			Validator.equals(ifElement.getName(), "not") ||
			Validator.equals(ifElement.getName(), "or")) {

			return;
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

		String extendsName = rootElement.attributeValue("extends");

		if (elements.isEmpty() && (extendsName == null)) {
			throwValidationException(
				1001, fileName, rootElement, new String[] {"command", "var"});
		}
		else if (extendsName != null) {
			if (Validator.isNull(extendsName)) {
				throwValidationException(
					1006, fileName, rootElement, "extends");
			}
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
						"description", "echo", "execute", "fail", "for", "if",
						"take-screenshot", "var", "while",
					},
					new String[] {"action", "macro"}, new String[] {"var"},
					new String[] {
						"and", "condition", "contains", "equals", "isset",
						"not", "or"
					});
			}
			else if (elementName.equals("var")) {
				validateVarElement(fileName, element);
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

		Element locatorElement = elements.get(1);

		String locator = locatorElement.getText();

		locator = locator.replace("${","");
		locator = locator.replace("}","");
		locator = locator.replace("/-/","/");

		if (locator.endsWith("/")) {
			locator = locator.substring(0, locator.length() - 1);
		}

		if (!locator.equals("") && !locator.startsWith("link=") &&
			!locator.startsWith("title=") && !locator.contains(".png")) {

			try {
				XPathFactory xPathFactory = XPathFactory.newInstance();

				XPath xPath = xPathFactory.newXPath();

				xPath.compile(locator);
			}
			catch (Exception e) {
				throwValidationException(2003, fileName, locator);
			}
		}

		Element keyElement = elements.get(0);

		String key = keyElement.getText();

		Element descriptionElement = elements.get(2);

		String description = descriptionElement.getText();

		if (!key.equals("") && !key.equals("EXTEND_ACTION_PATH") &&
			!key.equals("PAGE_NAME") && !description.equals("")) {

			if (description.endsWith(".")) {
				throwValidationException(2004, fileName, description);
			}

			Matcher statmentMatcher = _pathTrElementStatementPattern.matcher(
				description);

			if (!statmentMatcher.find()) {
				throwValidationException(2004, fileName, description);
			}

			Matcher wordMatcher1 = _pathTrElementWordPattern1.matcher(
				description);

			while (wordMatcher1.find()) {
				String word = wordMatcher1.group();

				if (word.equals("a") || word.equals("and") ||
					word.equals("as") || word.equals("at") ||
					word.equals("by") || word.equals("for") ||
					word.equals("from") || word.equals("in") ||
					word.equals("of") || word.equals("the") ||
					word.equals("to")) {

					continue;
				}

				Matcher wordMatcher2 = _pathTrElementWordPattern2.matcher(word);

				if (!wordMatcher2.find()) {
					throwValidationException(2004, fileName, description);
				}
			}
		}
	}

	protected void validatePropertyElement(
		String fileName, Element propertyElement) {

		List<Attribute> attributes = propertyElement.attributes();

		String propertyName = propertyElement.attributeValue("name");

		if (!_testcaseAvailablePropertyNames.contains(propertyName)) {
			throwValidationException(
				3003, fileName, propertyElement, propertyName);
		}

		if (propertyName.equals("ignore.errors")) {
			String propertyDelimiter = propertyElement.attributeValue(
				"delimiter");

			String propertyValue = propertyElement.attributeValue("value");

			if (propertyDelimiter != null) {
				if (!propertyValue.contains(propertyDelimiter)) {
					throwValidationException(
						1006, fileName, propertyElement, "delimiter");
				}
			}

			if (Validator.isNull(propertyValue)) {
				throwValidationException(
					1006, fileName, propertyElement, "value");
			}
		}

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (attributeName.equals("delimiter") &&
				propertyName.equals("ignore.errors")) {

				continue;
			}
			else if (attributeName.equals("line-number") ||
					 attributeName.equals("name") ||
					 attributeName.equals("value")) {

				continue;
			}
			else {
				throwValidationException(
					1005, fileName, propertyElement, attributeName);
			}
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

			if (!_allowedNullAttributes.contains(attributeName) &&
				Validator.isNull(attributeValue)) {

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
				throwValidationException(
					1004, fileName, element, neededAttributes);
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

		String extendedTestCase = rootElement.attributeValue("extends");

		if (extendedTestCase != null) {
			if (Validator.isNull(extendedTestCase)) {
				throwValidationException(
					1006, fileName, rootElement, "extends");
			}
		}

		String componentName = rootElement.attributeValue("component-name");

		if (componentName == null) {
			throwValidationException(
				1003, fileName, rootElement, "component-name");
		}

		if ((componentName != null) &&
			!_componentNames.contains(componentName)) {

			throwValidationException(
				1006, fileName, rootElement, "component-name");
		}

		List<Element> elements = rootElement.elements();

		if (Validator.isNull(extendedTestCase)) {
			if (elements.isEmpty()) {
				throwValidationException(
					1001, fileName, rootElement, new String[] {"command"});
			}
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

				String priorityValue = element.attributeValue("priority");

				if (priorityValue == null) {
					throwValidationException(
						1003, fileName, element, "priority");
				}
				else if (!(priorityValue.equals("1") ||
						   priorityValue.equals("2") ||
						   priorityValue.equals("3") ||
						   priorityValue.equals("4") ||
						   priorityValue.equals("5"))) {

					throwValidationException(
						1006, fileName, element, "priority");
				}

				validateBlockElement(
					fileName, element,
					new String[] {
						"description", "echo", "execute", "fail", "for", "if",
						"property", "take-screenshot", "var", "while"
					},
					new String[] {"action", "macro", "test-case"},
					new String[] {"var"},
					new String[] {
						"and", "condition", "contains", "equals", "isset",
						"not", "or"
					});
			}
			else if (elementName.equals("property")) {
				validatePropertyElement(fileName, element);
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
					fileName, element,
					new String[] {
						"description", "echo", "execute", "fail", "if",
						"take-screenshot", "var", "while"
					},
					new String[] {"action", "macro", "test-case"},
					new String[] {"var"},
					new String[] {
						"and", "condition", "contains", "equals", "isset",
						"not", "or"
					});
			}
			else if (elementName.equals("var")) {
				validateVarElement(fileName, element);
			}
			else {
				throwValidationException(1002, fileName, element, elementName);
			}
		}

		elements = getAllChildElements(rootElement, "property");

		for (Element element : elements) {
			String name = element.attributeValue("name");
			String value = element.attributeValue("value");

			if (name.equals("testray.component.names")) {
				List<String> testrayComponentNames = ListUtil.fromArray(
					StringUtil.split(value));

				for (String testrayComponentName : testrayComponentNames) {
					if (!_testrayAvailableComponentNames.contains(
							testrayComponentName)) {

						throwValidationException(
							3001, fileName, element, name,
							testrayComponentName);
					}
				}
			}
			else if (name.equals("testray.main.component.name")) {
				if (!_testrayAvailableComponentNames.contains(value)) {
					throwValidationException(
						3001, fileName, element, name, value);
				}
			}
		}

		elements = rootElement.elements("property");

		boolean rootTestrayMainComponentNameFound = false;

		for (Element element : elements) {
			String name = element.attributeValue("name");

			if (name.equals("testray.main.component.name")) {
				rootTestrayMainComponentNameFound = true;

				break;
			}
		}

		if (!rootTestrayMainComponentNameFound) {
			elements = rootElement.elements("command");

			for (Element element : elements) {
				List<Element> propertyElements = getAllChildElements(
					element, "property");

				boolean commandTestrayMainComponentNameFound = false;

				for (Element propertyElement : propertyElements) {
					String propertyName = propertyElement.attributeValue(
						"name");

					if (propertyName.equals("testray.main.component.name")) {
						commandTestrayMainComponentNameFound = true;

						break;
					}
				}

				if (!commandTestrayMainComponentNameFound) {
					throwValidationException(
						3002, fileName, element, "testray.main.component.name");
				}
			}
		}
	}

	protected void validateVarElement(String fileName, Element element) {
		List<Attribute> attributes = element.attributes();

		Map<String, String> attributeMap = new HashMap<String, String>();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();
			String attributeValue = attribute.getValue();

			if (!attributeName.equals("value") &&
				Validator.isNull(attributeValue)) {

				throwValidationException(
					1006, fileName, element, attributeName);
			}

			if (!_allowedVarAttributes.contains(attributeName)) {
				throwValidationException(
					1005, fileName, element, attributeName);
			}

			attributeMap.put(attributeName, attributeValue);
		}

		if (!attributeMap.containsKey("name")) {
			throwValidationException(
				1004, fileName, element, new String[] {"name"});
		}
		else {
			String nameValue = attributeMap.get("name");

			if (Validator.isNull(nameValue)) {
				throwValidationException(1006, fileName, element, "name");
			}
		}

		if (attributeMap.containsKey("locator")) {
			String[] disallowedAttributes = {"locator-key", "path", "value"};

			for (String disallowedAttribute : disallowedAttributes) {
				if (attributeMap.containsKey(disallowedAttribute)) {
					throwValidationException(
						1005, fileName, element, disallowedAttribute);
				}
			}
		}
		else if (attributeMap.containsKey("locator-key") &&
				 attributeMap.containsKey("path")) {

			if (attributeMap.containsKey("value")) {
				throwValidationException(1005, fileName, element, "value");
			}
		}
		else if (attributeMap.containsKey("locator-key")) {
			throwValidationException(
				1004, fileName, element, new String [] {"path"});
		}
		else if (attributeMap.containsKey("path")) {
			throwValidationException(
				1004, fileName, element, new String [] {"locator-key"});
		}

		String varText = element.getText();

		if (attributeMap.containsKey("locator") ||
			attributeMap.containsKey("locator-key") ||
			attributeMap.containsKey("path")) {

			if (!Validator.isNull(varText)) {
				throwValidationException(1005, fileName, element, "value");
			}
		}

		if (attributeMap.containsKey("method")) {
			String methodValue = attributeMap.get("method");

			if (!methodValue.startsWith("MathUtil") &&
				!methodValue.startsWith("selenium") &&
				!methodValue.startsWith("StringUtil")) {

				throwValidationException(1005, fileName, element, "method");
			}

			if (!methodValue.contains("#")) {
				throwValidationException(1005, fileName, element, "method");
			}
		}

		if (!attributeMap.containsKey("property-value") &&
			!attributeMap.containsKey("value") && Validator.isNull(varText)) {

			if (!attributeMap.containsKey("group") &&
				!attributeMap.containsKey("input") &&
				!attributeMap.containsKey("locator") &&
				!attributeMap.containsKey("locator-key") &&
				!attributeMap.containsKey("method") &&
				!attributeMap.containsKey("path") &&
				!attributeMap.containsKey("pattern")) {

				throwValidationException(
					1004, fileName, element, new String [] {"value"});
			}
		}
		else {
			String varValue = attributeMap.get("value");

			if (Validator.isNull(varValue)) {
				varValue = varText;
			}

			Matcher matcher = _varElementPattern.matcher(varValue);

			while (matcher.find()) {
				String statement = matcher.group(1);

				Matcher statementMatcher = _varElementStatementPattern.matcher(
					statement);

				if (statementMatcher.find()) {
					String operand = statementMatcher.group(1);

					String method = statementMatcher.group(2);

					if (operand.equals("") || method.equals("")) {
						throwValidationException(
							1006, fileName, element, "value");
					}

					if (!_methodNames.contains(method)) {
						throwValidationException(
							1013, fileName, element, method);
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

	private static final String _TPL_ROOT =
		"com/liferay/portal/tools/seleniumbuilder/dependencies/";

	private static List<String> _allowedNullAttributes = ListUtil.fromArray(
		new String[] {
			"arg1", "arg2", "delimiter", "message", "string", "substring",
			"value"
		});
	private static List<String> _allowedVarAttributes = ListUtil.fromArray(
		new String[] {
			"attribute", "group", "input", "line-number", "locator",
			"locator-key", "method", "name", "path", "pattern",
			"property-value", "value"
		});
	private static List<String> _componentNames;
	private static List<String> _methodNames = ListUtil.fromArray(
		new String[] {
			"getFirstNumber", "getIPAddress", "increment", "length",
			"lowercase", "replace", "uppercase"
		});
	private static List<String> _reservedTags = ListUtil.fromArray(
		new String[] {
			"and", "case", "command", "condition", "contains", "default",
			"definition", "delimiter", "description", "echo", "else", "elseif",
			"equals", "execute", "fail", "for", "if", "isset", "not", "or",
			"property", "set-up", "take-screenshot", "td", "tear-down", "then",
			"tr", "while", "var"
		});
	private static List<String> _testcaseAvailablePropertyNames;
	private static List<String> _testrayAvailableComponentNames;

	private String _baseDirName;
	private Pattern _pathTrElementStatementPattern = Pattern.compile(
		"[A-Z0-9].*");
	private Pattern _pathTrElementWordPattern1 = Pattern.compile(
		"[A-Za-z0-9\\-]+");
	private Pattern _pathTrElementWordPattern2 = Pattern.compile(
		"[A-Z0-9][A-Za-z0-9\\-]*");
	private Pattern _tagPattern = Pattern.compile("<[a-z\\-]+");
	private Pattern _varElementPattern = Pattern.compile("\\$\\{([^\\}]*?)\\}");
	private Pattern _varElementStatementPattern = Pattern.compile(
		"(.*)\\?(.*)\\(([^\\)]*?)\\)");

}