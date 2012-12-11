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
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class TestXMLToJavaBuilder extends SeleniumXMLToJavaBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new TestXMLToJavaBuilder(args);
	}

	public TestXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		Set<String> fileNames = getFileNames();

		for (String fileName : fileNames) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			importObjects = new TreeSet<String>();

			classType = classTypes.TEST;

			generateTest(fileName);
		}
	}

	protected void generateTest(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String testName = fileName.substring(x + 1, y) + "Test";

		String testFilePath = fileName.substring(0, x) + "/rc";

		String testFileName = testFilePath + "/" + testName + ".java";

		filePath = testFileName;

		String testPackagePath = StringUtil.replace(
			testFilePath, StringPool.SLASH, StringPool.PERIOD);

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(testPackagePath);
		sb.append(";\n");

		sb.append("import com.liferay.portalweb.blocks.portal.portlet.signin.");
		sb.append("macros.PortletSignInUserMacros;\n");

		sb.append("import com.liferay.portalweb.portal.BaseTestCase;\n");
		sb.append("import com.liferay.portalweb.portal.util.SeleniumUtil;\n");

		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySeleniumHelper;\n");

		Element rootElement = getRootElement(fileName);

		String header = "public class " + testName + " extends BaseTestCase {";

		Element setupBlock = rootElement.element("setup");
		Element stepsBlock = rootElement.element("steps");
		Element teardownBlock = rootElement.element("teardown");

		String setup = getSetup(setupBlock);
		String steps = getSteps(stepsBlock);
		String teardown = getTeardown(teardownBlock);

		String importStatements = getImportStatements();

		sb.append(importStatements);
		sb.append(header);
		sb.append(setup);
		sb.append(steps);
		sb.append(teardown);

		sb.append("}");

		writeFile(testFileName, sb.toString(), true);
	}

	protected String getSetup(Element setupElement) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("@Override\n");
		sb.append("public void setUp() throws Exception {");
		sb.append("selenium = SeleniumUtil.getSelenium();");

		sb.append(processBlock(setupElement));

		sb.append("}");

		return sb.toString();
	}

	protected String getSteps(Element stepsElement) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("public void test() throws Exception {");

		sb.append(processBlock(stepsElement));

		sb.append("}");

		return sb.toString();
	}

	protected String getTeardown(Element teardownElement) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("@Override\n");
		sb.append("public void tearDown() throws Exception {");

		sb.append(processBlock(teardownElement));
		sb.append("portletSignInUserMacros.signOut();");

		sb.append("}");

		return sb.toString();
	}

	private Set<String> getFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\**\\*.test"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

}