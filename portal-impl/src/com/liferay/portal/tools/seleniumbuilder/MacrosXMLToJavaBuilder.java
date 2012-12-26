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
public class MacrosXMLToJavaBuilder extends SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new MacrosXMLToJavaBuilder(args);
	}

	public MacrosXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		for (String fileName : fileNameSetMacros) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			importObjects = new TreeSet<String>();

			classType = ClassTypes.MACROS;

			getObject(fileName);
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
		String macrosName = fileName.substring(x + 1, y) + "Macros";

		String macrosFileName = macrosFilePath + "/" + macrosName + ".java";

		filePath = macrosFileName;

		String macrosPackagePath = StringUtil.replace(
			macrosFilePath, StringPool.SLASH, StringPool.PERIOD);

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(macrosPackagePath);
		sb.append(";\n");

		sb.append("import com.liferay.portalweb.blocks.base.macros.");
		sb.append("BaseMacros;\n");

		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySelenium;\n");

		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySeleniumHelper;\n");

		Element rootElement = getRootElement(fileName);

		List<Element> runBlocks = rootElement.elements();

		String macroDefs = _getMacroDefs(rootElement);

		String importStatements = getImportStatements();

		sb.append(importStatements);

		sb.append("public class ");
		sb.append(macrosName);
		sb.append(" extends BaseMacros {");

		sb.append("public ");
		sb.append(macrosName);
		sb.append("(LiferaySelenium liferaySelenium) {");
		sb.append("super(liferaySelenium);");
		sb.append("}");

		sb.append(macroDefs);

		sb.append("}");

		writeFile(macrosFileName, sb.toString(), true);
	}

	private String _getMacroDefs(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> macroDefs = rootElement.elements("macrodef");

		for (Element macroDef : macroDefs) {
			String macroDefName = macroDef.attributeValue("name");

			Element params = macroDef.element("params");
			Element stepsBlock = macroDef.element("steps");

			sb.append("public void ");
			sb.append(macroDefName);
			sb.append("(");
			sb.append(_getParameterList(params));
			sb.append(") throws Exception {");

			sb.append(processBlock(stepsBlock));

			sb.append("}");
		}

		return sb.toString();
	}

	private String _getParameterList(Element params) throws Exception {
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

}