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
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.InitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class MacrosXMLToHTMLBuilder extends SeleniumXMLToJavaBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new MacrosXMLToHTMLBuilder(args);
	}

	public MacrosXMLToHTMLBuilder(String[] args) throws Exception {
		super(args);

		Set<String> fileNames = getFileNames();

		for (String fileName : fileNames) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			allMacroDefs = new ArrayList();

			generateMacrosHTML(fileName);
		}

		generateAPIFile();
	}

protected void createMacroEntry(
		String macrosName, String macrosFilePath, String description,
		Element rootElement) throws Exception {

		String[] macroData = new String[5];

		allMacros.put(macrosName, macroData);

		allMacros.get(macrosName)[0] = description;

		allMacros.get(macrosName)[1] = macrosFilePath;

		allMacros.get(macrosName)[2] = getObjectsReferenced(rootElement);

		allMacros.get(macrosName)[3] = getOutlineLink(
											macrosFilePath, macrosName);

		allMacros.get(macrosName)[4] = getMethodOutline(rootElement);
	}

	protected String formatDescription(String description) {
		String formatted = description;

		formatted = formatted.replace("{", "<strong>");

		formatted = formatted.replace("}", "</strong>]");

		formatted = formatted.replace("$", "[input of parameter ");

		return formatted;
	}

	protected void generateAPIFile() throws Exception {

		String root = "com/liferay/portalweb/blocks/styles/templates/";

		StringBundler sb = new StringBundler();

		String headerTemplate = readFile(root + "macroapi/head.html");

		sb.append(headerTemplate);

		for (String key : allMacros.keySet()) {
			String blockTemplate = readFile(root + "macroapi/block.html");

			String macrosName = key;
			String description = allMacros.get(macrosName)[0];
			String macrosFilePath = allMacros.get(macrosName)[1];
			String objectsReferenced = allMacros.get(macrosName)[2];
			String outlineLink = allMacros.get(macrosName)[3];
			String methodOutline = allMacros.get(macrosName)[4];

			blockTemplate = blockTemplate.replace("${macroname}", macrosName);

			blockTemplate = blockTemplate.replace(
				"${description}", description);

			blockTemplate = blockTemplate.replace(
				"${outlinelink}", outlineLink);

			int x = macrosFilePath.indexOf("blocks");

			String location = macrosFilePath.substring(x + 7);

			String[] dirs = location.split("/");

			StringBundler locationFinal = new StringBundler();

			int tabs = 1;

			for (String dir : dirs) {
				for (int j = 0; j < tabs; j++) {
					locationFinal.append("&rarr; ");
				}

				locationFinal.append("/" + dir);

				locationFinal.append("<br>");

				tabs++;
			}

			blockTemplate = blockTemplate.replace(
				"${location}", locationFinal.toString());

			blockTemplate = blockTemplate.replace(
				"${methodoutline}", methodOutline);

			blockTemplate = blockTemplate.replace(
				"${pageobjects}", objectsReferenced);

			sb.append(blockTemplate);

			sb.append("</body>\n");
			sb.append("</html>\n");
		}

		String macroAPIPath = "com/liferay/portalweb/blocks/MacroAPI.html";

		writeFile(macroAPIPath, sb.toString(), false);
	}

	protected String generateHeader(String fileName, Element rootElement)
		throws Exception {

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String macrosFilePath = fileName.substring(0, x);

		String macrosName = fileName.substring(x + 1, y) + "Macros";

		macrosFileName = macrosName;

		String macrosFileName = macrosFilePath + "/" + macrosName + ".java";

		String root = "com/liferay/portalweb/blocks/styles/";

		String template = readFile(root + "templates/header.html");

		template = template.replace("${title}", macrosName);

		String styledir = getDependenciesPath(fileName);

		template = template.replace("${styledir}", styledir);

		template = template.replace("${macroname}", macrosName);

		String description = rootElement.attributeValue("description");

		if (description == null) {
			description = "Description Pending";
		}

		template = template.replace("${macrodescription}", description);

		String tableofcontents = getTableOfContents();

		template = template.replace("${tableofcontents}", tableofcontents);

		createMacroEntry(macrosName, macrosFilePath, description, rootElement);

		return template;
	}

	protected void generateMacrosHTML(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		StringBundler sb = new StringBundler();

		Element rootElement = getRootElement(fileName);

		List<Element> runBlocks = rootElement.elements();

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String macrosFilePath = fileName.substring(0, x);

		String macrosName = fileName.substring(x + 1, y) + "Macros";

		String htmlFileName = macrosFilePath + "/" + macrosName + ".html";

		String macroDefs = getMacroDefs(rootElement);

		String header = generateHeader(fileName, rootElement);

		sb.append(header);

		sb.append(macroDefs);

		writeFile(htmlFileName, sb.toString(), false);
	}

	protected String getDependenciesPath(String localPath) throws Exception {
		String stylesPath = "com/liferay/portalweb/blocks/styles";

		int x = localPath.indexOf("blocks");

		String pathFromPortalWeb = localPath.substring(x);

		String[] split = pathFromPortalWeb.split("/");

		int numDirectories = split.length - 1;

		StringBundler sb = new StringBundler();

		for (int i = 0; i < numDirectories; i++) {
			sb.append("../");
		}

		sb.append("blocks/styles/");

		return sb.toString();
	}

	protected String getMacroDefs(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		String root = "com/liferay/portalweb/blocks/styles/";

		String template = readFile(root + "templates/block.html");

		List<Element> macroDefs = rootElement.elements("macrodef");

		for (Element macroDef : macroDefs) {
			String block = template;

			String macroDefName = macroDef.attributeValue("name");

			allMacroDefs.add(macroDefName);

			String description = macroDef.attributeValue("description");

			block = block.replace("${macrodefname}", macroDefName);

			block = block.replace("${usage}", getUsage(macroDef));

			StringBundler parameters = new StringBundler();

			if (macroDef.attributeValue("params") != null) {
				String[] params = macroDef.attributeValue("params").split(",");

				for (String param : params) {
					parameters.append("<li>" + param + "</li>\n");
				}
			}
			else {
				parameters.append("n/a");
			}

			block = block.replace("${parameters}", parameters.toString());

			description = formatDescription(description);

			block = block.replace("${description}", description);

			sb.append(block);
		}

		return sb.toString();
	}

	protected String getMethodOutline(Element rootElement) {
		List<Element> macroDefs = rootElement.elements("macrodef");

		StringBundler sb = new StringBundler();

		for (Element macroDef : macroDefs) {
			String macroName = macroDef.attributeValue("name");
			String params = macroDef.attributeValue("params");

			sb.append("<li>");
			sb.append(macroName);
			sb.append("(" + params + ")");
			sb.append("</li>\n");
		}

		return sb.toString();
	}

	protected String getObjectsReferenced(Element rootElement)
		throws Exception {

		List<Element> macroDefs = rootElement.elements("macrodef");

		Set<String> objects = new TreeSet<String>();

		for (Element macroDef : macroDefs) {
			Set<String> newSet = new TreeSet<String>();

			objects.addAll(getAllObjects(macroDef, newSet));
		}

		StringBundler sb = new StringBundler();

		for (String object : objects) {
			sb.append("<li>");
			sb.append(object);
			sb.append("</li>\n");
		}

		return sb.toString();
	}

	protected String getOutlineLink(String macrosFilePath, String macrosName) {

		int x = macrosFilePath.lastIndexOf("blocks");

		String path = macrosFilePath.substring(x + 7);

		return path + "/" + macrosName + ".html";
	}

	protected String getTableOfContents() {
		StringBundler sb = new StringBundler();

		for (String macroDef : allMacroDefs) {
			sb.append("<li>");
			sb.append("<a href='#" + macroDef + "'>");
			sb.append(macroDef);
			sb.append("</a></li>\n");
		}

		return sb.toString();
	}

	protected String getUsage(Element macroDef) {
		String macroDefName = macroDef.attributeValue("name");

		StringBundler sb = new StringBundler();

		sb.append("&lt;macro name='");
		sb.append(macrosFileName);
		sb.append("' command='");
		sb.append(macroDefName);
		sb.append("' ");

		if (macroDef.attributeValue("params") != null) {
			String[] params = macroDef.attributeValue("params").split(",");

			for (String param : params) {
				sb.append(param);
				sb.append("='[insert sample argument]' ");
			}
		}

		sb.append("/&gt;");

		return sb.toString();
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

	private List<String> allMacroDefs = new ArrayList();

	private Map<String, String[]> allMacros = new HashMap<String, String[]>();

	private String macrosFileName = "";

}