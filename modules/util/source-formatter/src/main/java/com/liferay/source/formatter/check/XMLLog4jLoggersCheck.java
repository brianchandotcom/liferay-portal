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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.File;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Kevin Lee
 */
public class XMLLog4jLoggersCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (isPortalSource() &&
			(fileName.endsWith("-log4j-ext.xml") ||
			 fileName.endsWith("-log4j.xml"))) {

			_checkLoggers(fileName, content);
		}

		return content;
	}

	private void _checkLoggers(String fileName, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		Set<String> srcPaths = _getSrcPaths();

		for (Element loggersElement :
				(List<Element>)rootElement.elements("Loggers")) {

			for (Element loggerElement :
					(List<Element>)loggersElement.elements("Logger")) {

				String name = loggerElement.attributeValue("name");

				if (!name.startsWith("com.liferay")) {
					continue;
				}

				String path = StringUtil.replace(
					name, CharPool.PERIOD, CharPool.SLASH);

				if (_srcPaths.contains(path)) {
					continue;
				}

				boolean exists = false;

				for (String srcPath : srcPaths) {
					if (srcPath.startsWith(path)) {
						exists = true;

						break;
					}
				}

				if (!exists) {
					addMessage(
						fileName, "Class/package does not exist: " + name);
				}
			}
		}
	}

	private Set<String> _getSrcPaths() throws Exception {
		if (!_srcPaths.isEmpty()) {
			return _srcPaths;
		}

		File file = getPortalDir();

		List<String> fileNames = getFileNames(
			file.getAbsolutePath(), new String[0],
			new String[] {"**/com/liferay/**/*.java"});

		for (String fileName : fileNames) {
			int x = fileName.indexOf("com/liferay/");

			_srcPaths.add(fileName.substring(x));
		}

		return _srcPaths;
	}

	private final Set<String> _srcPaths = new HashSet<>();

}