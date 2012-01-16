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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class PluginsSummaryBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		File pluginsDir = new File(System.getProperty("plugins.dir"));

		new PluginsSummaryBuilder(pluginsDir);
	}

	public PluginsSummaryBuilder(File pluginsDir) {
		try {
			_createPluginsSummary(pluginsDir);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _createPluginsSummary(File pluginsDir) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("<plugins-summary>\n");

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(pluginsDir);
		ds.setIncludes(
			new String[] {
				"**\\liferay-plugin-package.properties",
				"**\\liferay-plugin-package.xml"
			});

		ds.scan();

		String[] files = ds.getIncludedFiles();

		Arrays.sort(files);

		for (String file : files) {
			_createPluginSummary(file, sb);
		}

		for (String author : _distinctAuthors) {
			sb.append("\t<author>");
			sb.append(author);
			sb.append("</author>\n");
		}

		for (String license : _distinctLicenses) {
			sb.append("\t<license>");
			sb.append(license);
			sb.append("</license>\n");
		}

		sb.append("</plugins-summary>");

		FileUtil.write(
			pluginsDir + File.separator + "summary.xml", sb.toString());
	}

	private void _createPluginSummary(String file, StringBundler sb)
		throws Exception {

		String content = FileUtil.read(file);

		int x = file.indexOf(File.separatorChar);

		String type = file.substring(0, x);

		if (type.endsWith("s")) {
			type = type.substring(0, type.length() - 1);
		}

		x = file.indexOf(File.separator, x) + 1;
		int y = file.indexOf(File.separator, x);

		String artifactId = file.substring(x, y);

		String name = StringPool.BLANK;
		String tags = StringPool.BLANK;
		String shortDescription = StringPool.BLANK;
		String changeLog = StringPool.BLANK;
		String pageURL = StringPool.BLANK;
		String author = StringPool.BLANK;
		String licenses = StringPool.BLANK;

		if (file.endsWith(".properties")) {
			Properties properties = PropertiesUtil.load(content);

			name = _readProperty(properties, "name");
			tags = _readProperty(properties, "tags");
			shortDescription = _readProperty(properties, "short-description");
			changeLog = _readProperty(properties, "change-log");
			pageURL = _readProperty(properties, "page-url");
			author = _readProperty(properties, "author");
			licenses = _readProperty(properties, "licenses");
		}
		else {
			Document doc = SAXReaderUtil.read(content);

			Element root = doc.getRootElement();

			name = root.elementText("name");
			tags = _readList(root.element("tags"), "tag");
			shortDescription = root.elementText("short-description");
			changeLog = root.elementText("change-log");
			pageURL = root.elementText("page-url");
			author = root.elementText("author");
			licenses = _readList(root.element("licenses"), "license");
		}

		_distinctAuthors.add(author);
		_distinctLicenses.add(licenses);

		sb.append("\t<plugin>\n");
		sb.append("\t\t<artifact-id>");
		sb.append(artifactId);
		sb.append("</artifact-id>\n");
		sb.append("\t\t<name>");
		sb.append(name);
		sb.append("</name>\n");
		sb.append("\t\t<type>");
		sb.append(type);
		sb.append("</type>\n");
		sb.append("\t\t<tags>");
		sb.append(tags);
		sb.append("</tags>\n");
		sb.append("\t\t<short-description>");
		sb.append(shortDescription);
		sb.append("</short-description>\n");
		sb.append("\t\t<change-log>");
		sb.append(changeLog);
		sb.append("</change-log>\n");
		sb.append("\t\t<page-url>");
		sb.append(pageURL);
		sb.append("</page-url>\n");
		sb.append("\t\t<author>");
		sb.append(author);
		sb.append("</author>\n");
		sb.append("\t\t<licenses>");
		sb.append(licenses);
		sb.append("</licenses>\n");
		sb.append("\t</plugin>\n");
	}

	private String _readList(Element parentEl, String name) {
		if ((parentEl == null) || parentEl.elements(name).isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			parentEl.elements(name).size() * 2 - 1);

		Iterator<Element> itr = parentEl.elements(name).iterator();

		while (itr.hasNext()) {
			Element el = itr.next();

			String text = el.getText().trim();

			sb.append(text);

			if (itr.hasNext()) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

	private String _readProperty(Properties properties, String key) {
		return GetterUtil.getString(properties.getProperty(key));
	}

	private Set<String> _distinctAuthors = new TreeSet<String>();
	private Set<String> _distinctLicenses = new TreeSet<String>();

}