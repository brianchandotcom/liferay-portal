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
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class FunctionsXMLToJavaBuilder extends SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new FunctionsXMLToJavaBuilder(args);
	}

	public FunctionsXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		for (String fileName : fileNameSetFunctions) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			importObjects = new TreeSet<String>();

			classType = ClassTypes.FUNCTIONS;

			generateFunctions(fileName);
		}
	}

	protected void generateFunctions(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String functionsFilePath = fileName.substring(0, x);
		String functionsName = fileName.substring(x + 1, y) + "Functions";

		String functionsFileName =
			functionsFilePath + "/" + functionsName + ".java";
		String functionsPackagePath = StringUtil.replace(
			functionsFilePath, StringPool.SLASH, StringPool.PERIOD);
		String functionsXMLFileName =
			functionsFilePath + "/" + fileName.substring(x + 1, y) +
				".functions";

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(functionsPackagePath);
		sb.append(";\n\n");

		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySelenium;\n");

		sb.append("public class ");
		sb.append(functionsName);
		sb.append(" extends BaseFunctions {\n\n");

		sb.append("public ");
		sb.append(functionsName);
		sb.append("(LiferaySelenium liferaySelenium) {\n");

		sb.append("super(liferaySelenium);\n");

		sb.append("}\n");

		Element rootElement = getRootElement(functionsXMLFileName);

		sb.append(_getFunctionDefs(rootElement));

		sb.append("}");

		writeFile(functionsFileName, sb.toString(), true);
	}

	private String _getFunctionDefs(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> functionDefs = rootElement.elements("functiondef");

		for (Element functionDef : functionDefs) {
			String functionDefCommand = functionDef.attributeValue("command");

			sb.append("public void ");
			sb.append(functionDefCommand);

			if (functionDefCommand.equals("dragAndDrop")) {
				sb.append("(String path1, String value1, String path2, ");
				sb.append("String value2)");
			}
			else {
				sb.append("(String param1, String param2)");
			}

			sb.append(" throws Exception {\n");

			sb.append(processBlock(functionDef));

			sb.append("}\n");
		}

		return sb.toString();
	}

}