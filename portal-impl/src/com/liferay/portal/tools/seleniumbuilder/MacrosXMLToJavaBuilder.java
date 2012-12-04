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
public class MacrosXMLToJavaBuilder extends SeleniumXMLToJavaBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new MacrosXMLToJavaBuilder(args);
	}

	public MacrosXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		Set<String> fileNames = getFileNames();

		for (String fileName : fileNames) {
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
		String macrosName = fileName.substring(x + 1, y) + "Macros";

		String macrosFileName = macrosFilePath + "/" + macrosName + ".java";
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

		if (isValidMacroDef(rootElement, macrosName)) {
			sb.append(getImportStatements(rootElement));

			sb.append("public class ");
			sb.append(macrosName);
			sb.append(" extends BaseMacros {");

			sb.append("public ");
			sb.append(macrosName);
			sb.append("(LiferaySelenium liferaySelenium) {");
			sb.append("super(liferaySelenium);");
			sb.append("}");

			sb.append(getMacroDefs(rootElement));

			sb.append("}");

			writeFile(macrosFileName, sb.toString(), true);
		}
	}

	protected String getMacroDefs(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> macroDefs = rootElement.elements("macrodef");

		for (Element macroDef : macroDefs) {
			String macroDefName = macroDef.attributeValue("name");

			sb.append("public void ");
			sb.append(macroDefName);
			sb.append("(");
			sb.append(getParameterList(macroDef));
			sb.append(") throws Exception {");

			sb.append(getObjectDeclarations(macroDef));

			sb.append(getCommands(macroDef));

			sb.append("}");
		}

		return sb.toString();
	}

	protected String getParameterList(Element method) throws Exception {
		String paramsList = "";
		String paramsString = method.attributeValue("params");

		if (!(paramsString == null)) {
			if (paramsString.contains(",")) {
				String[] params = paramsString.split(",");

				StringBundler sb = new StringBundler();

				for (String param : params) {
					sb.append("String ");
					sb.append(param);
					sb.append(", ");
				}

				paramsList = sb.toString();

				paramsList = paramsList.substring(0, paramsList.length() - 2);
			}
			else {
				paramsList = "String " + paramsString;
			}
		}

		return paramsList;
	}

	protected boolean isValidMacroDef(Element rootElement, String macrosName)
		throws Exception {
		List<Element> macroDefs = rootElement.elements("macrodef");

		boolean isValid = true;

		for (Element macroDef : macroDefs) {
			String macroName = macroDef.attributeValue("name");

			List<Element> functions = macroDef.elements("functions");
			List<Element> selenium = macroDef.elements("selenium");

			isValid = functions.isEmpty() && selenium.isEmpty();

			if (!isValid) {
				String message = "Invalid tag(s) used in ";

				message += "Macro definition of " + macroName;
				message += " in Macros file " + macrosName + ":\n";

				if (!functions.isEmpty()) {
					String tags = "";

					for (Element function : functions) {
						message += "Function command ";
						message += function.attributeValue("command");
						message += "\n";
					}
				}

				if (!selenium.isEmpty()) {
					for (Element seleniumCommand : selenium) {
						String command = seleniumCommand.attributeValue(
							"command");

						message += "Selenium command ";
						message += command;
						message += "\n";
					}
				}

				throw new Exception(message);
			}
		}

		return isValid;
	}

	private Set<String> getFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\blocks\\**\\*.macros"
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