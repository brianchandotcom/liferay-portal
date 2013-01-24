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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public class TestSuitesXMLToJavaBuilder extends BaseXMLToJavaBuilder {

	public TestSuitesXMLToJavaBuilder(Map<String, Object> context)
		throws Exception {

		super(context);

		_basedir = (String)context.get("basedir");
		_seleniumDataUtil = new SeleniumDataUtil(context);

		_seleniumFileUtil = new SeleniumFileUtil(_basedir);
	}

	public void generateTestSuite(String fileName) throws Exception {
		if (fileName.length() > 161) {
			System.out.println(
				"Exceeds 177 characters: portal-web/test/" + fileName);
		}

		if (!FileUtil.exists(_basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String testSuiteFilePath = fileName.substring(0, x);
		String testSuiteSimpleClassName =
			fileName.substring(x + 1, y) + "TestSuite";

		String testSuiteFileName =
			testSuiteFilePath + "/" + testSuiteSimpleClassName + ".java";
		String testSuitePackagePath = StringUtil.replace(
			testSuiteFilePath, StringPool.SLASH, StringPool.PERIOD);

		Element rootElement = _seleniumFileUtil.getRootElementByFileName(
			fileName);

		_seleniumFileUtil.isValidName(fileName, rootElement);

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(testSuitePackagePath);
		sb.append(";\n\n");

		sb.append("import com.liferay.portalweb.portal.BaseTestSuite;\n");
		sb.append("import com.liferay.portalweb.portal.StopSeleniumTest;\n\n");

		Set<String> testPlanClassNameSet =
			_seleniumDataUtil.getTestPlanClassNameSetByElement(rootElement);

		for (String testPlanClassName : testPlanClassNameSet) {
			sb.append("import ");
			sb.append(testPlanClassName);
			sb.append(";\n");
		}

		sb.append(processBlockImports(rootElement));

		sb.append("import junit.framework.Test;\n");
		sb.append("import junit.framework.TestSuite;\n");

		sb.append("public class " + testSuiteSimpleClassName);
		sb.append(" extends BaseTestSuite {\n");

		sb.append("public static Test suite() {\n");
		sb.append("TestSuite testSuite = new TestSuite();\n\n");

		List<String> testPlanSimpleClassNameList =
			_seleniumDataUtil.getTestPlanSimpleClassNameListByElement(
				rootElement);

		for (String testPlanSimpleClassName : testPlanSimpleClassNameList) {
			sb.append("testSuite.addTest(");
			sb.append(testPlanSimpleClassName);
			sb.append(".suite());\n");
		}

		sb.append("\n");
		sb.append("testSuite.addTestSuite(StopSeleniumTest.class);\n\n");
		sb.append("return testSuite;");
		sb.append("}\n");

		sb.append("}\n");

		_seleniumFileUtil.writeFile(testSuiteFileName, sb.toString(), true);
	}

	private String _basedir;
	private SeleniumDataUtil _seleniumDataUtil;
	private SeleniumFileUtil _seleniumFileUtil;
	private Set<String> _validCommands = new TreeSet<String>();

}