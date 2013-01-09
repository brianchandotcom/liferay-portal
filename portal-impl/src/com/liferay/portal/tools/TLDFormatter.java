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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class TLDFormatter {

	public static void main(String[] args) {
		try {
			InitUtil.initWithSpring();

			_formatTLD();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void _formatTLD() throws Exception {
		String basedir = "./util-taglib/src/META-INF/";

		if (!FileUtil.exists(basedir)) {
			return;
		}

		List<String> list = new ArrayList<String>();

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setExcludes(new String[] {"**\\liferay-portlet-ext.tld"});
		ds.setIncludes(new String[] {"**\\*.tld"});

		ds.scan();

		list.addAll(ListUtil.fromArray(ds.getIncludedFiles()));

		String[] files = list.toArray(new String[list.size()]);

		for (int i = 0; i < files.length; i++) {
			File file = new File(basedir + files[i]);

			String content = FileUtil.read(file);

			Document document = SAXReaderUtil.read(
				new UnsyncStringReader(
					StringUtil.replace(
						content, "xml/ns/j2ee/web-jsptaglibrary_2_0.xsd",
						"dtd/web-jsptaglibrary_1_2.dtd")));

			Element root = document.getRootElement();

			_sortElements(root, "tag", "name");

			List<Element> tagEls = root.elements("tag");

			for (Element tagEl : tagEls) {
				_sortElements(tagEl, "attribute", "name");

				Element dynamicAttributesEl = tagEl.element(
					"dynamic-attributes");

				if (dynamicAttributesEl != null) {
					dynamicAttributesEl.detach();

					tagEl.add(dynamicAttributesEl);
				}
			}

			String newContent = document.formattedString();

			int x = newContent.indexOf("<tlib-version");
			int y = newContent.indexOf("</taglib>");

			newContent = newContent.substring(x, y);

			x = content.indexOf("<tlib-version");
			y = content.indexOf("</taglib>");

			newContent =
				content.substring(0, x) + newContent + content.substring(y);

			if (!content.equals(newContent)) {
				FileUtil.write(file, newContent);

				System.out.println(file);
			}
		}
	}

	private static void _sortElements(
		Element parentElement, String name, String sortBy) {

		Map<String, Element> map = new TreeMap<String, Element>();

		List<Element> elements = parentElement.elements(name);

		for (Element element : elements) {
			map.put(element.elementText(sortBy), element);

			element.detach();
		}

		for (Map.Entry<String, Element> entry : map.entrySet()) {
			Element element = entry.getValue();

			parentElement.add(element);
		}
	}

}