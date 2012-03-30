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

package com.liferay.portal.util;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ExtRegistry {

	public static Map<String, Set<String>> getConflicts(
			ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Set<String> files = _readExtFiles(
			servletContext, "/WEB-INF/ext-" + servletContextName + ".xml");

		Map<String, Set<String>> conflicts = new HashMap<String, Set<String>>();

		for (Map.Entry<String, Set<String>> entry : _extMap.entrySet()) {
			String curServletContextName = entry.getKey();
			Set<String> curFiles = entry.getValue();

			for (String file : files) {
				if (!curFiles.contains(file)) {
					continue;
				}

				Set<String> conflictFiles = conflicts.get(
					curServletContextName);

				if (conflictFiles == null) {
					conflictFiles = new TreeSet<String>();

					conflicts.put(curServletContextName, conflictFiles);
				}

				conflictFiles.add(file);
			}
		}

		return conflicts;
	}

	public static Set<String> getServletContextNames() {
		return Collections.unmodifiableSet(_extMap.keySet());
	}

	public static boolean isRegistered(String servletContextName) {
		if (_extMap.containsKey(servletContextName)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void registerExt(ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Set<String> files = _readExtFiles(
			servletContext, "/WEB-INF/ext-" + servletContextName + ".xml");

		_extMap.put(servletContextName, files);
	}

	public static void registerPortal(ServletContext servletContext)
		throws Exception {

		Set<String> resourcePaths = servletContext.getResourcePaths("/WEB-INF");

		for (String resourcePath : resourcePaths) {
			if (resourcePath.startsWith("/WEB-INF/ext-") &&
				resourcePath.endsWith("-ext.xml")) {

				String servletContextName = resourcePath.substring(
					13, resourcePath.length() - 4);

				Set<String> files = _readExtFiles(servletContext, resourcePath);

				_extMap.put(servletContextName, files);
			}
		}
	}

	private static Set<String> _readExtFiles(
			ServletContext servletContext, String resourcePath)
		throws Exception {

		Set<String> files = new TreeSet<String>();

		Document document = SAXReaderUtil.read(
			servletContext.getResourceAsStream(resourcePath));

		Element rootElement = document.getRootElement();

		Element filesElement = rootElement.element("files");

		List<Element> fileElements = filesElement.elements("file");

		for (Element fileElement : fileElements) {
			files.add(fileElement.getText());
		}

		return files;
	}

	private static Map<String, Set<String>> _extMap =
		new HashMap<String, Set<String>>();

}