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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public class SeleniumDataUtil {

	public SeleniumDataUtil(Map<String, Object> context) {
		_classNameSetAvailable = (Set<String>)context.get(
			"classNameSetAvailable");
		_fileNameSet = (Set<String>)context.get("fileNameSet");
		_fileNameSetTestCases = (Set<String>)context.get(
			"fileNameSetTestCases");
	}

	public Set<String> addSimpleClassNameToClassNameSet(
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
			classNameSet = addSimpleClassNameToClassNameSet(
				classNameSet, childSimpleClassName);
		}

		return classNameSet;
	}

	public Set<String> getChildSimpleClassNameSet(Element element)
		throws Exception {

		return _getChildSimpleClassNameSet(new TreeSet<String>(), element);
	}

	public String getFileNameByElement(Element element) throws Exception {
		Element rootElement = getRootElementByElement(element);

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

	public List<String> getNumberListByInteger(int integer) throws Exception {
		List<String> arrayList = new ArrayList<String>();

		if ((integer == 0) || (integer == 1)) {
			arrayList.add("");
		}
		else {
			for (int i = 1; i <= integer; i++) {
				arrayList.add(String.valueOf(i));
			}
		}

		return arrayList;
	}

	public Set<String> getPathsClassNameSet(Element rootElement) {
		Set<String> treeSet = new TreeSet<String>();

		Element bodyElement = rootElement.element("body");
		Element tableElement = bodyElement.element("table");
		Element tbodyElement = tableElement.element("tbody");

		List<Element> targetList = tbodyElement.elements("tr");

		for (Element target : targetList) {
			List<Element> paramList = target.elements("td");

			String targetID = paramList.get(0).getText();
			String targetLocator = paramList.get(1).getText();

			if (targetID.equals("IMPORT_PATH_OBJECT")) {
				for (String className : _classNameSetAvailable) {
					String simpleClassName = "." + targetLocator + "Paths";

					if (className.endsWith(simpleClassName)) {
						treeSet.add(className);
					}
				}
			}
		}

		return treeSet;
	}

	public Element getRootElementByElement(Element element) throws Exception {
		if (element.isRootElement()) {
			return element;
		}
		else {
			return getRootElementByElement(element.getParent());
		}
	}

	public List<String> getTestCaseSimpleClassNameListByDirectory(
		String directoryName) throws Exception {

		List<String> arrayList = new ArrayList<String>();

		for (String fileName : _fileNameSetTestCases) {
			if (fileName.startsWith(directoryName)) {
				int x = fileName.lastIndexOf(StringPool.SLASH);
				int y = fileName.indexOf(CharPool.PERIOD);

				String testCaseSimpleClassName =
					fileName.substring(x + 1, y) + "TestCase";

				arrayList.add(testCaseSimpleClassName);
			}
		}

		return arrayList;
	}

	public Set<String> getTestPlanClassNameSetByElement(Element testSuite) {
		Set<String> treeSet = new TreeSet<String>();

		List<Element> testPlanList = testSuite.elements("testplan");

		for (Element testPlan : testPlanList) {
			String testPlaneClassName = testPlan.attributeValue("class");

			treeSet.add(testPlaneClassName);
		}

		return treeSet;
	}

	public List<String> getTestPlanSimpleClassNameListByElement(
		Element testSuite) {

		List<String> arrayList = new ArrayList<String>();

		List<Element> testPlanList = testSuite.elements("testplan");

		for (Element testPlan : testPlanList) {
			String testPlaneClassName = testPlan.attributeValue("class");

			int x = testPlaneClassName.lastIndexOf(CharPool.PERIOD) + 1;

			arrayList.add(testPlaneClassName.substring(x));
		}

		return arrayList;
	}

	private Set<String> _getChildSimpleClassNameSet(
		Set<String> simpleClassNameSet, Element element) {

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

				_getChildSimpleClassNameSet(simpleClassNameSet, child);
			}
		}

		return simpleClassNameSet;
	}

	private Set<String> _classNameSetAvailable;
	private Set<String> _fileNameSet;
	private Set<String> _fileNameSetTestCases;

}