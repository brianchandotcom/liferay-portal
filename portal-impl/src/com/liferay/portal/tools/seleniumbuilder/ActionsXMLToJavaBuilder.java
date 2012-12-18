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

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionsXMLToJavaBuilder extends SeleniumXMLToJavaBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new ActionsXMLToJavaBuilder(args);
	}

	public ActionsXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		Set<String> fileNames = getFileNames();

		for (String fileName : fileNames) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			importObjects = new TreeSet<String>();

			classType = ClassTypes.ACTIONS;

			generateActions(fileName);
		}
	}

	protected void generateActions(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String actionsName = fileName.substring(x + 1, y) + "Actions";
		String pathsFilePath = fileName.substring(0, x);
		String pathsName = fileName.substring(x + 1, y) + "Paths";

		String pathsPackagePath = StringUtil.replace(
			pathsFilePath, StringPool.SLASH, StringPool.PERIOD);
		String actionsFilePath = StringUtil.replace(
			pathsFilePath, "/paths", "/actions");

		String actionsFileName = actionsFilePath + "/" + actionsName + ".java";

		filePath = actionsFileName;

		String actionsPackagePath = StringUtil.replace(
			actionsFilePath, StringPool.SLASH, StringPool.PERIOD);
		String actionsXMLFileName =
			actionsFilePath + "/" + fileName.substring(x + 1, y) + ".actions";

		StringBundler header = new StringBundler();

		header.append("package ");
		header.append(actionsPackagePath);
		header.append(";\n\n");

		StringBundler imports = new StringBundler();

		imports.append("import com.liferay.portalweb.blocks.base.actions.");
		imports.append("BaseActionsImpl;\n");

		imports.append("import com.liferay.portalweb.blocks.base.actions.");
		imports.append("LiferayActions;\n");

		imports.append("import com.liferay.portalweb.portal.util.");
		imports.append("liferayselenium.LiferaySelenium;\n");

		imports.append("import com.liferay.portalweb.portal.util.");
		imports.append("liferayselenium.LiferaySeleniumHelper;\n");

		imports.append("import ");
		imports.append(pathsPackagePath);
		imports.append(StringPool.PERIOD);
		imports.append(pathsName);
		imports.append(";\n");

		Boolean actionsXMLFileExists = FileUtil.exists(
			basedir + "/" + actionsXMLFileName);

		StringBundler constructor = new StringBundler();

		constructor.append("public class ");
		constructor.append(actionsName);
		constructor.append(" extends BaseActionsImpl implements");
		constructor.append(" LiferayActions {\n\n");

		constructor.append("public ");
		constructor.append(actionsName);
		constructor.append("(LiferaySelenium liferaySelenium) {\n");

		constructor.append("super(liferaySelenium);\n");

		constructor.append("paths = ");
		constructor.append(pathsName);
		constructor.append(".getPaths();\n");

		constructor.append("}\n");

		StringBundler sb = new StringBundler();

		sb.append(header);

		if (actionsXMLFileExists) {
			Element rootElement = getRootElement(actionsXMLFileName);

			String actionDefs = getActionDefs(rootElement);
			imports.append(getImportStatements());

			sb.append(imports);
			sb.append(constructor);
			sb.append(actionDefs);
		}
		else {
			sb.append(imports);
			sb.append(constructor);
		}

		sb.append("}");

		writeFile(actionsFileName, sb.toString(), true);
	}

	protected String getActionDefs(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> actionDefs = rootElement.elements("actiondef");

		for (Element actionDef : actionDefs) {
			String actionDefName = actionDef.attributeValue("name");

			sb.append("public void ");
			sb.append(actionDefName);
			sb.append("(String param1, String param2) throws Exception {\n");

			sb.append("String[] params = getParams(param1, param2);\n\n");

			sb.append(processBlock(actionDef));

			sb.append("}\n");
		}

		return sb.toString();
	}

	private Set<String> getFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\blocks\\**\\*.paths"
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