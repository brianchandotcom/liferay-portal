/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumDataUtil {

	public SeleniumDataUtil(Map<String, Object> context) {
		_classNameSetAvailable = (Set<String>)context.get(
			"classNameSetAvailable");
		_fileNameSet = (Set<String>)context.get("fileNameSet");
		_fileNameSetTestCases = (Set<String>)context.get(
			"fileNameSetTestCases");
	}

	public Set<String> addClassName(
		Set<String> classNameSet, String simpleClassName) throws Exception {

		for (String className : _classNameSetAvailable) {
			if (className.endsWith("." + simpleClassName)) {
				classNameSet.add(className);
			}
		}

		return classNameSet;
	}

	public Set<String> getChildClassNameSet(Element element) throws Exception {
		Set<String> classNameSet = new TreeSet<String>();

		Set<String> childSimpleClassNameSet = getChildSimpleClassNameSet(
			element);

		for (String childSimpleClassName : childSimpleClassNameSet) {
			classNameSet = addClassName(classNameSet, childSimpleClassName);
		}

		return classNameSet;
	}

	public Set<String> getChildSimpleClassNameSet(Element element)
		throws Exception {

		return _getChildSimpleClassNameSet(element, new TreeSet<String>());
	}

	public String getFileNameByElement(Element command) throws Exception {
		Element rootElement = getRootElementByElement(command);

		String rootElementName = rootElement.getName();
		String objectName = "";

		if (rootElementName.equals("testcase")) {
			objectName = rootElement.attributeValue("name");
		}
		else {
			objectName = rootElement.attributeValue("object");
		}

		String simpleFileName = objectName + "." + rootElementName;

		for (String fileName : _fileNameSet) {
			if (fileName.endsWith("/" + simpleFileName)) {
				return fileName;
			}
		}

		return "";
	}

	public Element getRootElementByElement(Element element) throws Exception {
		if (element.isRootElement()) {
			return element;
		}
		else {
			return getRootElementByElement(element.getParent());
		}
	}

	public Set<String> getTestCaseSimpleClassNames(String directoryName)
		throws Exception {

		Set<String> treeSet = new TreeSet<String>();

		for (String fileName : _fileNameSetTestCases) {
			if (fileName.startsWith(directoryName)) {
				int x = fileName.lastIndexOf(StringPool.SLASH);
				int y = fileName.indexOf(CharPool.PERIOD);

				String testCaseSimpleClassName =
					fileName.substring(x + 1, y) + "TestCase";

				treeSet.add(testCaseSimpleClassName);
			}
		}

		return treeSet;
	}

	private Set<String> _getChildSimpleClassNameSet(
		Element element, Set<String> simpleClassNameSet) {

		List<Element> children = element.elements();

		if (children.isEmpty()) {
			return simpleClassNameSet;
		}
		else {
			for (Element child : children) {
				String childName = child.getName();
				String childObjectName = child.attributeValue("object");

				if (!(childObjectName == null)) {
					if (childName.equals("action") || childName.equals("if") ||
						childName.equals("while")) {

						simpleClassNameSet.add(childObjectName + "Actions");
					}
					else if (childName.equals("function")) {
						simpleClassNameSet.add(childObjectName + "Functions");
					}
					else if (childName.equals("macro")) {
						simpleClassNameSet.add(childObjectName + "Macros");
					}
				}

				_getChildSimpleClassNameSet(child, simpleClassNameSet);
			}
		}

		return simpleClassNameSet;
	}

	private Set<String> _classNameSetAvailable;
	private Set<String> _fileNameSet;
	private Set<String> _fileNameSetTestCases;

}