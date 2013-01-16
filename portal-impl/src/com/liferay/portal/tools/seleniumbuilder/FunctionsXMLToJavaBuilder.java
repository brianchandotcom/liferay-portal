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
 * @author Brian Wing Shun Chan
 */
public class FunctionsXMLToJavaBuilder extends BaseXMLToJavaBuilder {

	public FunctionsXMLToJavaBuilder(Map<String, Object> context)
		throws Exception {

		super(context);

		_basedir = (String)context.get("basedir");
		_functionParameterNumberMap = (Map<String, Integer>)context.get(
			"functionParameterNumberMap");
		_functionReturnTypeMap = (Map<String, String>)context.get(
			"functionReturnTypeMap");
		_seleniumDataUtil = new SeleniumDataUtil(context);

		_seleniumFileUtil = new SeleniumFileUtil(_basedir);

		_validCommands = new TreeSet<String>();

		_validCommands.add("else");
		_validCommands.add("function");
		_validCommands.add("if");
		_validCommands.add("selenium");
		_validCommands.add("while");
	}

	public void generateFunctions(String fileName) throws Exception {
		if (fileName.length() > 161) {
			System.out.println(
				"Exceeds 177 characters: portal-web/test/" + fileName);
		}

		if (!FileUtil.exists(_basedir + "/" + fileName)) {
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

		Element rootElement = _seleniumFileUtil.getRootElementByFileName(
			fileName);

		_seleniumFileUtil.isValidName(fileName, rootElement);

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

		_seleniumFileUtil.writeFile(functionsFileName, sb.toString(), true);
	}

	private String _processFunctionDefs(Element functions) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> functionDefs = functions.elements("functiondef");

		String functionObjectName = functions.attributeValue("object");

		String functionReturnType = _functionReturnTypeMap.get(
			functionObjectName);

		for (Element functionDef : functionDefs) {
			String functionDefCommand = functionDef.attributeValue("command");

			sb.append("public ");
			sb.append(functionReturnType);
			sb.append(" ");

			sb.append(functionDefCommand);

			int functionParams = _functionParameterNumberMap.get(
				functionObjectName);

			List<String> functionSuffixList =
				_seleniumDataUtil.getNumberListByInteger(functionParams);

			String paramList = "";

			for (String suffix : functionSuffixList) {
				String paramPair = "String target, String value, ";

				paramPair = StringUtil.replace(
					paramPair, "target", "target" + suffix);
				paramPair = StringUtil.replace(
					paramPair, "value", "value" + suffix);

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

	private String _basedir;
	private Map<String, Integer> _functionParameterNumberMap;
	private Map<String, String> _functionReturnTypeMap;
	private SeleniumDataUtil _seleniumDataUtil;
	private SeleniumFileUtil _seleniumFileUtil;
	private Set<String> _validCommands;

}