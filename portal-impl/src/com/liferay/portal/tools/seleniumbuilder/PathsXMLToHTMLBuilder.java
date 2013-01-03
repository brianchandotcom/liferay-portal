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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class PathsXMLToHTMLBuilder extends SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new PathsXMLToHTMLBuilder(args);
	}

	public PathsXMLToHTMLBuilder(String[] args) throws Exception {
		super(args);

		Set<String> fileNames = getFileNames();

		for (String fileName : fileNames) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			populatePathsList(fileName);
		}

		generateAPIFile();
	}

	protected void createPathEntry(
		String pathName, String pathFilePath, String description,
		Element rootElement) throws Exception {

		String[] pathData = new String[3];

		allPaths.put(pathName, pathData);

		allPaths.get(pathName)[0] = description;

		allPaths.get(pathName)[1] = pathFilePath;

		allPaths.get(pathName)[2] = "";
	}

	protected void generateAPIFile() throws Exception {

		String root = "com/liferay/portalweb/blocks/styles/templates/";

		StringBundler sb = new StringBundler();

		String headerTemplate = readFile(root + "pathapi/head.html");

		sb.append(headerTemplate);

		for (String key : allPaths.keySet()) {
			String blockTemplate = readFile(root + "pathapi/block.html");

			String pathName = key;

			String description = allPaths.get(pathName)[0];
			String pathFilePath = allPaths.get(pathName)[1];
			String identifiers = allPaths.get(pathName)[2];

			blockTemplate = blockTemplate.replace("${pathname}", pathName);

			blockTemplate = blockTemplate.replace(
				"${description}", description);

			int x = pathFilePath.indexOf("blocks");

			String location = pathFilePath.substring(x + 7);

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

			if (identifiers.equals("")) {
				identifiers = "none";
			}

			blockTemplate = blockTemplate.replace(
				"${identifiers}", identifiers);

			sb.append(blockTemplate);

			sb.append("</body>\n");
			sb.append("</html>\n");
		}

		String pathsAPIPath = "com/liferay/portalweb/blocks/PathsAPI.html";

		writeFile(pathsAPIPath, sb.toString(), false);
	}

	protected void getPaths(Element rootElement, String pathsName)
		throws Exception {
		StringBundler sb = new StringBundler();

		Element bodyElement = rootElement.element("body");
		Element tableElement = bodyElement.element("table");
		Element tbodyElement = tableElement.element("tbody");

		List<Element> elementList = tbodyElement.elements("tr");

		StringBundler identifiers = new StringBundler();

		for (Element element : elementList) {
			List<Element> paramList = element.elements("td");

			String key = paramList.get(0).getText();
			String locator = paramList.get(1).getText();
			String value = paramList.get(2).getText();

			if (key.equals("PAGE_NAME")) {
				allPaths.get(pathsName)[0] = value;
			}
			else if (!key.equals("")) {
				identifiers.append("<li>");
				identifiers.append(key);
				identifiers.append("<br><input type='text' size='50' value=");
				identifiers.append('"' + locator + '"' + ">");
				identifiers.append("</input>");
				identifiers.append("</li>\n");
			}
		}

		allPaths.get(pathsName)[2] = identifiers.toString();
	}

	protected void populatePathsList(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String pathsFilePath = fileName.substring(0, x);
		String pathsName = fileName.substring(x + 1, y) + "Paths";

		Element rootElement = getRootElement(fileName);

		String description = rootElement.attributeValue("description");

		if (description == null) {
			description = "Description Pending";
		}

		createPathEntry(pathsName, pathsFilePath, description, rootElement);

		getPaths(rootElement, pathsName);
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

	private Map<String, String[]> allPaths = new HashMap<String, String[]>();

}