/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.xml.Dom4jUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WebXML23Converter {

	public static void main(String[] args) {
		ToolDependencies.wireBasic();

		if (args.length == 2) {
			new WebXML23Converter(args[0], args[1]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public WebXML23Converter(String input, String output) {
		try {
			String webXML24 = FileUtil.read(input);

			Document document = UnsecureSAXReaderUtil.read(webXML24);

			Element rootElement = document.getRootElement();

			double version = GetterUtil.getDouble(
				rootElement.attributeValue("version"));

			if (version == 2.4) {
				System.out.println("Convert web.xml because it is Servlet 2.4");
			}
			else {
				System.out.println(
					"Do not convert web.xml because it is not Servlet 2.4");

				return;
			}

			List<Element> filterMappingElements = rootElement.elements(
				"filter-mapping");

			for (Element filterMappingElement : filterMappingElements) {
				List<Element> dispatcherElements =
					filterMappingElement.elements("dispatcher");

				for (Element dispatcherElement : dispatcherElements) {
					dispatcherElement.detach();
				}
			}

			String webXML23 = document.formattedString();

			int x = webXML23.indexOf("<web-app");

			int y = webXML23.indexOf(">", x);

			webXML23 = StringBundler.concat(
				webXML23.substring(0, x),
				"<!DOCTYPE web-app PUBLIC \"-//Sun Microsystems, Inc.//DTD ",
				"Web Application 2.3//EN\" ",
				"\"http://java.sun.com/dtd/web-app_2_3.dtd\"><web-app>",
				webXML23.substring(y + 1));

			webXML23 = StringUtil.removeSubstrings(
				webXML23, "<jsp-config>", "</jsp-config>");

			webXML23 = Dom4jUtil.toString(webXML23);

			FileUtil.write(output, webXML23);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebXML23Converter.class);

}