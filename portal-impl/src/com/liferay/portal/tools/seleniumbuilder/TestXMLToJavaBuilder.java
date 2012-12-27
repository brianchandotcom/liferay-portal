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
import com.liferay.portal.util.InitUtil;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class TestXMLToJavaBuilder extends SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new TestXMLToJavaBuilder(args);
	}

	public TestXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		for (String fileName : fileNameSetTests) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			classType = ClassTypes.TEST;

			generateTest(fileName);
		}
	}

	protected void generateTest(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String testFilePath = fileName.substring(0, x) + "/rc";
		String testName = fileName.substring(x + 1, y) + "Test";

		String testFileName = testFilePath + "/" + testName + ".java";
		String testPackagePath = StringUtil.replace(
			testFilePath, StringPool.SLASH, StringPool.PERIOD);

		Element rootElement = getRootElement(fileName);
		Element setupBlock = rootElement.element("setup");
		Element stepsBlock = rootElement.element("steps");
		Element teardownBlock = rootElement.element("teardown");

		filePath = testFileName;

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(testPackagePath);
		sb.append(";\n");

		sb.append("import com.liferay.portalweb.portal.BaseTestCase;\n");
		sb.append("import com.liferay.portalweb.portal.util.SeleniumUtil;\n");
		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySeleniumHelper;\n");

		sb.append(processRootElementImports(rootElement));

		sb.append("public class " + testName + " extends BaseTestCase {");

		sb.append(_processSetupBlock(setupBlock));
		sb.append(_processStepsBlock(stepsBlock));
		sb.append(_processTeardownBlock(teardownBlock));

		sb.append("}");

		writeFile(testFileName, sb.toString(), true);
	}

	protected String processBlockObjectDeclaractions(Element element)
		throws Exception {

		StringBundler sb = new StringBundler();

		Set<String> simpleClassNameSet = getSimpleClassNameSet(element);

		String elementName = element.getName();

		if (elementName.equals("setup") || elementName.equals("teardown")) {
			simpleClassNameSet.add("SignInUserMacros");
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

	protected String processRootElementImports(Element rootElement)
		throws Exception {

		StringBundler sb = new StringBundler();

		Set<String> classNameImportSet = getClassNameSet(rootElement);

		classNameImportSet = addClassName(
			classNameImportSet, "SignInUserMacros");

		for (String className : classNameImportSet) {
			sb.append("import ");
			sb.append(className);
			sb.append(";\n");
		}

		return sb.toString();
	}

	private String _processSetupBlock(Element setup) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("@Override\n");
		sb.append("public void setUp() throws Exception {");
		sb.append("selenium = SeleniumUtil.getSelenium();");

		sb.append(processBlockObjectDeclaractions(setup));

		sb.append("signInUserMacros.signIn(");
		sb.append("\"test@liferay.com\", \"test\");");

		sb.append(processBlock(setup));

		sb.append("}");

		return sb.toString();
	}

	private String _processStepsBlock(Element steps) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("public void test() throws Exception {");

		sb.append(processBlockObjectDeclaractions(steps));

		sb.append(processBlock(steps));

		sb.append("}");

		return sb.toString();
	}

	private String _processTeardownBlock(Element teardown) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("@Override\n");
		sb.append("public void tearDown() throws Exception {");

		sb.append(processBlockObjectDeclaractions(teardown));

		sb.append(processBlock(teardown));

		sb.append("signInUserMacros.signOut();");

		sb.append("}");

		return sb.toString();
	}

}