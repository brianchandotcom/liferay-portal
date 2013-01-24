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

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public class TestCasesXMLToJavaBuilder extends BaseXMLToJavaBuilder {

	public TestCasesXMLToJavaBuilder(Map<String, Object> context)
		throws Exception {

		super(context);

		_basedir = (String)context.get("basedir");
		_seleniumDataUtil = new SeleniumDataUtil(context);

		_seleniumFileUtil = new SeleniumFileUtil(_basedir);

		_validCommands = new TreeSet<String>();

		_validCommands.add("action");
		_validCommands.add("macro");
		_validCommands.add("window");
	}

	public void generateTestCase(String fileName) throws Exception {
		if (fileName.length() > 161) {
			System.out.println(
				"Exceeds 177 characters: portal-web/test/" + fileName);
		}

		if (!FileUtil.exists(_basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String testCaseFilePath = fileName.substring(0, x);
		String testCaseSimpleClassName =
			fileName.substring(x + 1, y) + "TestCase";

		String testCaseFileName =
			testCaseFilePath + "/" + testCaseSimpleClassName + ".java";
		String testCasePackagePath = StringUtil.replace(
			testCaseFilePath, StringPool.SLASH, StringPool.PERIOD);

		Element rootElement = _seleniumFileUtil.getRootElementByFileName(
			fileName);

		Element setupBlock = rootElement.element("setup");
		Element stepsBlock = rootElement.element("steps");
		Element teardownBlock = rootElement.element("teardown");

		_seleniumFileUtil.isValidName(fileName, rootElement);

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(testCasePackagePath);
		sb.append(";\n");

		sb.append("import com.liferay.portalweb.base.actions.");
		sb.append("LiferayActionsHelper;\n");

		sb.append("import com.liferay.portalweb.portal.BaseTestCase;\n");
		sb.append("import com.liferay.portalweb.portal.util.SeleniumUtil;\n");

		sb.append(processBlockImports(rootElement));

		sb.append("public class ");
		sb.append(testCaseSimpleClassName);
		sb.append(" extends BaseTestCase {");

		sb.append(_processSetupBlock(setupBlock));
		sb.append(_processStepsBlock(stepsBlock));
		sb.append(_processTeardownBlock(teardownBlock));

		sb.append("}");

		_seleniumFileUtil.writeFile(testCaseFileName, sb.toString(), true);
	}

	protected String processBlockImports(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		Set<String> classNameImportSet = _seleniumDataUtil.getChildClassNameSet(
			rootElement);

		classNameImportSet = _seleniumDataUtil.addSimpleClassNameToClassNameSet(
			classNameImportSet, "UserMacros");

		for (String className : classNameImportSet) {
			sb.append("import ");
			sb.append(className);
			sb.append(";\n");
		}

		return sb.toString();
	}

	protected String processBlockObjectDeclaractions(Element element)
		throws Exception {

		StringBundler sb = new StringBundler();

		Set<String> simpleClassNameSet =
			_seleniumDataUtil.getChildSimpleClassNameSet(element);

		String elementName = element.getName();

		if (elementName.equals("setup")) {
			simpleClassNameSet.add("UserMacros");
		}

		for (String simpleClassName : simpleClassNameSet) {
			sb.append(simpleClassName);
			sb.append(" ");
			sb.append(StringUtil.lowerCaseFirstLetter(simpleClassName));
			sb.append(" = new ");
			sb.append(simpleClassName);
			sb.append("(selenium);\n");
		}

		sb.append("\n");

		return sb.toString();
	}

	private String _processSetupBlock(Element setup) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("@Override\n");
		sb.append("public void setUp() throws Exception {");
		sb.append("selenium = SeleniumUtil.getSelenium();");

		sb.append(processBlockObjectDeclaractions(setup));

		sb.append("userMacros.pgSignInSA();");

		sb.append(processBlockCommands(setup, _validCommands));

		sb.append("}");

		return sb.toString();
	}

	private String _processStepsBlock(Element steps) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("public void test() throws Exception {");

		sb.append(processBlockObjectDeclaractions(steps));

		sb.append(processBlockCommands(steps, _validCommands));

		sb.append("}");

		return sb.toString();
	}

	private String _processTeardownBlock(Element teardown) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("@Override\n");
		sb.append("public void tearDown() throws Exception {");

		sb.append(processBlockObjectDeclaractions(teardown));

		sb.append(processBlockCommands(teardown, _validCommands));

		sb.append("}");

		return sb.toString();
	}

	private String _basedir;
	private SeleniumDataUtil _seleniumDataUtil;
	private SeleniumFileUtil _seleniumFileUtil;
	private Set<String> _validCommands = new TreeSet<String>();

}