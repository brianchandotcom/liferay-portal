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

import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class TestPlansJavaBuilder extends BaseJavaBuilder {

	public TestPlansJavaBuilder(Map<String, Object> context) throws Exception {
		super();

		_basedir = (String)context.get("basedir");
		_seleniumDataUtil = new SeleniumDataUtil(context);

		_seleniumFileUtil = new SeleniumFileUtil(_basedir);
	}

	public void generateTestPlan(String directoryName) throws Exception {
		if (!FileUtil.exists(_basedir + "/" + directoryName)) {
			return;
		}

		int x = directoryName.lastIndexOf(StringPool.SLASH);

		String objectName = StringUtil.upperCaseFirstLetter(
			directoryName.substring(x + 1));
		String testPackagePath = StringUtil.replace(
			directoryName, StringPool.SLASH, StringPool.PERIOD);

		String testPlanSimpleClassName = objectName + "TestPlan";

		String testPlanFileName =
			directoryName + "/" + testPlanSimpleClassName + ".java";

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(testPackagePath);
		sb.append(";\n\n");

		sb.append("import com.liferay.portalweb.portal.BaseTestSuite;\n\n");

		sb.append("import junit.framework.Test;\n");
		sb.append("import junit.framework.TestSuite;\n");

		sb.append("public class " + testPlanSimpleClassName);
		sb.append(" extends BaseTestSuite {\n");

		sb.append("public static Test suite() {\n");
		sb.append("TestSuite testSuite = new TestSuite();\n\n");

		List<String> testCaseSimpleClassNameList =
			_seleniumDataUtil.getTestCaseSimpleClassNameListByDirectory(
				directoryName);

		for (String testCaseSimpleClassName : testCaseSimpleClassNameList) {
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