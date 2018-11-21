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

package com.liferay.portal.util;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
public class ExtRegistry {

	public static Map<String, Set<String>> getConflicts(
			ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Set<String> fileNames = _readExtFileNames(
			servletContext, "/WEB-INF/ext-" + servletContextName + ".xml");

		Map<String, Set<String>> conflicts = new HashMap<>();

		for (Map.Entry<String, Set<String>> entry : _extMap.entrySet()) {
			String curServletContextName = entry.getKey();
			Set<String> curFileNames = entry.getValue();

			for (String fileName : fileNames) {
				if (!curFileNames.contains(fileName)) {
					continue;
				}

				Set<String> conflictFileNames = conflicts.get(
					curServletContextName);

				if (conflictFileNames == null) {
					conflictFileNames = new TreeSet<>();

					conflicts.put(curServletContextName, conflictFileNames);
				}

				conflictFileNames.add(fileName);
			}
		}

		return conflicts;
	}

	public static Set<String> getServletContextNames() {
		return Collections.unmodifiableSet(_extMap.keySet());
	}

	public static boolean isIgnoredFileName(String fileName) {
		if (isMergedFileName(fileName)) {
			return true;
		}

		for (String ignoredFileName : _IGNORED_FILE_NAMES) {
			if (fileName.contains(ignoredFileName)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isMergedFileName(String fileName) {
		for (String mergedFileName : _SUPPORTED_MERGING_FILE_NAMES) {
			if (fileName.contains(mergedFileName)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isRegistered(String servletContextName) {
		if (_extMap.containsKey(servletContextName)) {
			return true;
		}

		return false;
	}

	public static void registerExt(ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Set<String> fileNames = _readExtFileNames(
			servletContext, "/WEB-INF/ext-" + servletContextName + ".xml");

		_extMap.put(servletContextName, fileNames);
	}

	public static void registerPortal(ServletContext servletContext)
		throws Exception {

		Set<String> resourcePaths = servletContext.getResourcePaths("/WEB-INF");

		if ((resourcePaths == null) || resourcePaths.isEmpty()) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			if (resourcePath.startsWith("/WEB-INF/ext-") &&
				resourcePath.endsWith("-ext.xml")) {

				String servletContextName = resourcePath.substring(
					13, resourcePath.length() - 4);

				Set<String> fileNames = _readExtFileNames(
					servletContext, resourcePath);

				_extMap.put(servletContextName, fileNames);
			}
		}
	}

	private static Set<String> _readExtFileNames(
			ServletContext servletContext, String resourcePath)
		throws Exception {

		Set<String> fileNames = new TreeSet<>();

		Document document = UnsecureSAXReaderUtil.read(
			servletContext.getResourceAsStream(resourcePath));

		Element rootElement = document.getRootElement();

		Element filesElement = rootElement.element("files");

		List<Element> fileElements = filesElement.elements("file");

		for (Element fileElement : fileElements) {
			String fileName = fileElement.getText();

			if (!isIgnoredFileName(fileName)) {
				fileNames.add(fileName);
			}
		}

		return fileNames;
	}

	private static final String[] _IGNORED_FILE_NAMES = {
		"log4j.dtd", "service.xml", "sql/"
	};

	private static final String[] _SUPPORTED_MERGING_FILE_NAMES = {
		"content/Language-ext", "ext-hbm.xml", "ext-model-hints.xml",
		"ext-spring.xml", "portal-log4j-ext.xml"
	};

	private static final Map<String, Set<String>> _extMap = new HashMap<>();

}