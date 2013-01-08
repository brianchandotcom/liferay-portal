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

		_validCommands = new TreeSet<String>();

		_validCommands.add("else");
		_validCommands.add("function");
		_validCommands.add("if");
		_validCommands.add("selenium");
		_validCommands.add("while");

		for (String fileName : fileNameSetFunctions) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

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
		String functionsSimpleClassName =
			fileName.substring(x + 1, y) + "Functions";

		String functionsFileName =
			functionsFilePath + "/" + functionsSimpleClassName + ".java";
		String functionsPackagePath = StringUtil.replace(
			functionsFilePath, StringPool.SLASH, StringPool.PERIOD);

		isValidName(fileName);

		Element rootElement = getRootElement(fileName);

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(functionsPackagePath);
		sb.append(";\n\n");

		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySelenium;\n");

		sb.append("public class ");
		sb.append(functionsSimpleClassName);
		sb.append(" extends BaseFunctions {\n\n");

		sb.append("public ");
		sb.append(functionsSimpleClassName);
		sb.append("(LiferaySelenium liferaySelenium) {\n");

		sb.append("super(liferaySelenium);\n");

		sb.append("}\n");

		sb.append(_processFunctionDefs(rootElement));

		sb.append("}");

		writeFile(functionsFileName, sb.toString(), true);
	}

	private String _processFunctionDefs(Element functions) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> functionDefs = functions.elements("functiondef");

		String functionsObject = functions.attributeValue("object");

		String functionReturnType = functionMethodReturnTypeMap.get(
			functionsObject);

		for (Element functionDef : functionDefs) {
			String functionDefCommand = functionDef.attributeValue("command");

			sb.append("public ");
			sb.append(functionReturnType);
			sb.append(" ");

			sb.append(functionDefCommand);

			List<String> paramSuffixList = functionMethodParamListMap.get(
				functionsObject);

			String paramList = "";

			for (String paramSuffix : paramSuffixList) {
				String paramPair = "String target, String value, ";

				paramPair = StringUtil.replace(
					paramPair, "target", "target" + paramSuffix);
				paramPair = StringUtil.replace(
					paramPair, "value", "value" + paramSuffix);

				paramList += paramPair;
			}

			paramList = paramList.substring(0, paramList.length() - 2);

			sb.append("(");
			sb.append(paramList);
			sb.append(") throws Exception {\n");

			sb.append(processBlockObjectDeclaractions(functionDef));

			sb.append(processBlockCommands(functionDef, _validCommands));

			sb.append("}\n");
		}

		return sb.toString();
	}

	private Set<String> _validCommands = new TreeSet<String>();

}