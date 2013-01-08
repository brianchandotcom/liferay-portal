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
public class MacrosXMLToJavaBuilder extends SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new MacrosXMLToJavaBuilder(args);
	}

	public MacrosXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		_validCommands = new TreeSet<String>();

		_validCommands.add("action");
		_validCommands.add("else");
		_validCommands.add("if");
		_validCommands.add("macro");
		_validCommands.add("while");
		_validCommands.add("window");

		for (String fileName : fileNameSetMacros) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			generateMacros(fileName);
		}
	}

	protected void generateMacros(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String macrosFilePath = fileName.substring(0, x);
		String macrosSimpleClassName = fileName.substring(x + 1, y) + "Macros";

		String macrosFileName =
			macrosFilePath + "/" + macrosSimpleClassName + ".java";
		String macrosPackagePath = StringUtil.replace(
			macrosFilePath, StringPool.SLASH, StringPool.PERIOD);

		isValidName(fileName);

		Element rootElement = getRootElement(fileName);

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(macrosPackagePath);
		sb.append(";\n");

		sb.append("import com.liferay.portalweb.base.macros.BaseMacros;\n");

		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySelenium;\n");

		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySeleniumHelper;\n");

		sb.append(processBlocksImports(rootElement));

		sb.append("public class ");
		sb.append(macrosSimpleClassName);
		sb.append(" extends BaseMacros {");

		sb.append("public ");
		sb.append(macrosSimpleClassName);
		sb.append("(LiferaySelenium liferaySelenium) {");
		sb.append("super(liferaySelenium);");
		sb.append("}");

		sb.append(_processMacroDefs(rootElement));

		sb.append("}");

		writeFile(macrosFileName, sb.toString(), true);
	}

	private String _processMacroDefs(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> macroDefs = rootElement.elements("macrodef");

		for (Element macroDef : macroDefs) {
			String macroDefCommand = macroDef.attributeValue("command");

			Element params = macroDef.element("params");
			Element stepsBlock = macroDef.element("steps");

			sb.append("public void ");
			sb.append(macroDefCommand);
			sb.append("(");
			sb.append(_processParameterList(params));
			sb.append(") throws Exception {");

			sb.append(processBlockObjectDeclaractions(macroDef));

			sb.append(processBlockCommands(stepsBlock, _validCommands));

			sb.append("}");
		}

		return sb.toString();
	}

	private String _processParameterList(Element params) throws Exception {
		StringBundler sb = new StringBundler();

		String paramsString = "";

		List<Element> paramList = params.elements("param");

		for (Element param : paramList) {
			String paramName = param.attributeValue("name");

			sb.append("String ");
			sb.append(paramName);
			sb.append(", ");
		}

		paramsString = sb.toString();

		if (paramsString.length() > 0) {
			paramsString = paramsString.substring(0, paramsString.length() - 2);
		}

		return paramsString;
	}

	private Set<String> _validCommands = new TreeSet<String>();

}