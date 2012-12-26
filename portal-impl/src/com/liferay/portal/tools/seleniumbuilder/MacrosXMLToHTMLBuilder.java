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
public class MacrosXMLToHTMLBuilder extends SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new MacrosXMLToHTMLBuilder(args);
	}

	public MacrosXMLToHTMLBuilder(String[] args) throws Exception {
		super(args);

		Set<String> fileNames = _getFileNames();

		for (String fileName : fileNames) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			_allMacroDefs = new ArrayList();

			generateMacrosHTML(fileName);
		}

		generateAPIFile();
	}

	protected void createMacroEntry(
			String macrosName, String macrosFilePath, String description,
			Element rootElement)
		throws Exception {

		String[] macroData = new String[5];

		_allMacros.put(macrosName, macroData);

		_allMacros.get(macrosName)[0] = description;

		_allMacros.get(macrosName)[1] = macrosFilePath;

		_allMacros.get(macrosName)[2] = getObjectsReferenced(rootElement);

		_allMacros.get(macrosName)[3] = getOutlineLink(
											macrosFilePath, macrosName);

		_allMacros.get(macrosName)[4] = getMethodOutline(rootElement);
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

		for (String key : _allMacros.keySet()) {
			String blockTemplate = readFile(root + "macroapi/block.html");

			String macrosName = key;
			String description = _allMacros.get(macrosName)[0];
			String macrosFilePath = _allMacros.get(macrosName)[1];
			String objectsReferenced = _allMacros.get(macrosName)[2];
			String outlineLink = _allMacros.get(macrosName)[3];
			String methodOutline = _allMacros.get(macrosName)[4];

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

		_macrosFileName = macrosName;

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

			_allMacroDefs.add(macroDefName);

			String description = macroDef.attributeValue("description");

			block = block.replace("${macrodefname}", macroDefName);

			block = block.replace("${usage}", getUsage(macroDef));

			StringBundler parameters = new StringBundler();

			Element paramsElement = macroDef.element("params");

			List<Element> params = paramsElement.elements("param");

			if (params.size() == 0) {
				parameters.append("n/a");
			}
			else {
				for (Element param : params) {
					String name = param.attributeValue("name");
					String paramDescription = param.attributeValue(
						"description");
					String example = param.attributeValue("example");

					parameters.append("<li>");
					parameters.append(name);
					parameters.append(" &rarr; <i>");
					parameters.append(paramDescription + "</i>");
					parameters.append("</li>");
				}
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

			sb.append("<li>");
			sb.append(macroName);

			Element paramsElement = macroDef.element("params");

			List<Element> params = paramsElement.elements("param");

			String paramsString = "";

			if (params.size() > 0) {
				for (Element param : params) {
					String name = param.attributeValue("name");

					paramsString += name + ",";
				}

				paramsString = paramsString.substring(
					0, paramsString.length() - 1);
			}

			sb.append("(" + paramsString + ")");
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

		for (String macroDef : _allMacroDefs) {
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
		sb.append(_macrosFileName);
		sb.append("' command='");
		sb.append(macroDefName);
		sb.append("' ");

		Element paramsElement = macroDef.element("params");

		List<Element> params = paramsElement.elements("param");

		if (params.size() > 0) {
			for (Element param : params) {
				String name = param.attributeValue("name");
				String example = param.attributeValue("example");

				sb.append(name);
				sb.append("='");
				sb.append(example);
				sb.append("' ");
			}
		}

		sb.append("/&gt;");

		return sb.toString();
	}

	private Set<String> _getFileNames() throws Exception {
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

	private List<String> _allMacroDefs = new ArrayList();
	private Map<String, String[]> _allMacros = new HashMap<String, String[]>();
	private String _macrosFileName = "";

}