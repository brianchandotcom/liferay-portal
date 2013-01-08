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
import com.liferay.portal.util.InitUtil;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPlanBuilder extends SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new TestPlanBuilder(args);
	}

	public TestPlanBuilder(String[] args) throws Exception {
		super(args);

		_basedir = getBasedir();

		Set<String> directoryNames = _getDirectoryNames();

		for (String directoryName : directoryNames) {
			generateTestPlan(directoryName);
		}
	}

	protected void generateTestPlan(String directoryName) throws Exception {
		if (!FileUtil.exists(_basedir + "/" + directoryName)) {
			return;
		}

		int x = directoryName.lastIndexOf(StringPool.SLASH);

		String testPlanFilePath = directoryName;
		String testPlanName = StringUtil.upperCaseFirstLetter(
			directoryName.substring(x + 1)) + "TestPlan";

		String testPlanFileName =
			testPlanFilePath + "/" + testPlanName + ".java";
		String testPackagePath = StringUtil.replace(
			testPlanFilePath, StringPool.SLASH, StringPool.PERIOD);

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(testPackagePath);
		sb.append(";\n\n");

		sb.append("import com.liferay.portalweb.portal.BaseTestSuite;\n\n");

		sb.append("import junit.framework.Test;\n");
		sb.append("import junit.framework.TestSuite;\n");

		sb.append("public class " + testPlanName);
		sb.append(" extends BaseTestSuite {\n");

		sb.append("public static Test suite() {\n");
		sb.append("TestSuite testSuite = new TestSuite();\n\n");

		Set<String> testSimpleClassNameSet = _getTestSimpleClassNames(
			directoryName);

		for (String testSimpleClassName : testSimpleClassNameSet) {
			sb.append("testSuite.addTestSuite(");
			sb.append(testSimpleClassName);
			sb.append(".class);");
		}

		sb.append("return testSuite;");
		sb.append("}\n");

		sb.append("}\n");

		writeFile(testPlanFileName, sb.toString(), true);
	}

	private Set<String> _getDirectoryNames() throws Exception {
		Set<String> treeSet = new TreeSet<String>();

		Set<String> fileNameSetTests = getFileNameSetTests();

		for (String fileName : fileNameSetTests) {
			int x = fileName.lastIndexOf("/");

			treeSet.add(fileName.substring(0, x));
		}

		return treeSet;
	}

	private Set<String> _getTestSimpleClassNames(String directoryName)
		throws Exception {

		Set<String> treeSet = new TreeSet<String>();

		Set<String> fileNameSetTests = getFileNameSetTests();

		for (String fileName : fileNameSetTests) {
			if (fileName.startsWith(directoryName)) {
				int x = fileName.lastIndexOf(StringPool.SLASH);
				int y = fileName.indexOf(CharPool.PERIOD);

				String testSimpleClassName =
					fileName.substring(x + 1, y) + "Test";

				treeSet.add(testSimpleClassName);
			}
		}

		return treeSet;
	}

	private String _basedir;

}