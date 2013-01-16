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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPlansJavaBuilder extends BaseJavaBuilder {

	public TestPlansJavaBuilder(Map<String, Object> context) throws Exception {
		super(context);

		_basedir = (String)context.get("basedir");
		_seleniumDataUtil = new SeleniumDataUtil(context);

		_seleniumFileUtil = new SeleniumFileUtil(_basedir);
	}

	public void generateTestPlan(String directoryName) throws Exception {
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

		Set<String> testCaseSimpleClassNameSet =
			_seleniumDataUtil.getTestCaseSimpleClassNames(directoryName);

		for (String testCaseSimpleClassName : testCaseSimpleClassNameSet) {
			sb.append("testSuite.addTestSuite(");
			sb.append(testCaseSimpleClassName);
			sb.append(".class);");
		}

		sb.append("return testSuite;");
		sb.append("}\n");

		sb.append("}\n");

		_seleniumFileUtil.writeFile(testPlanFileName, sb.toString(), true);
	}

	private String _basedir;
	private SeleniumDataUtil _seleniumDataUtil;
	private SeleniumFileUtil _seleniumFileUtil;

}