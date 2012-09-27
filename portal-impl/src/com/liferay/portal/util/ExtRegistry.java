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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.File;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

	public static final List<String> EXT_PLUGIN_JARS_GLOBAL_CL =
		Arrays.asList(new String[] {
			"ext-service"
		});

	public static final List<String> EXT_PLUGIN_JARS_PORTAL_CL =
		Arrays.asList(new String[] {
			"ext-impl", "ext-util-bridges", "ext-util-java", "ext-util-taglib"
		});

	public static final List<String> IGNORED_FILES =
		Arrays.asList(new String[] {
			"log4j.dtd", "service.xml", "sql"+File.separator
		});

	public static final List<String> SUPPORTED_MERGING_FILES =
		Arrays.asList(new String[] {
			"ext-model-hints.xml", "ext-spring.xml", "ext-hbm.xml",
			"portal-log4j-ext.xml", "content/Language-ext",
			"portal-ext.properties", "tiles-defs-ext.xml",
			"struts-config-ext.xml", "liferay-portlet-ext.xml",
			"liferay-look-and-feel-ext.xml", "liferay-layout-templates-ext.xml",
			"portlet-ext.xml", "liferay-display-ext.xml",
			"remoting-servlet-ext.xml", "web.xml"
		});

	public static Map<String, Set<String>> getConflicts(
			ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Set<String> files = _readExtFiles(
			servletContext, "/WEB-INF/ext-" + servletContextName + ".xml");

		Map<String, Set<String>> conflicts = new HashMap<String, Set<String>>();

		for (String curServletContextName : _extMap.keySet()) {
			ExtRegistryInfo extRegistryInfo = _extMap.get(
				curServletContextName);

			Set<String> curFiles = extRegistryInfo.getFiles();

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

	public static Set<String> getFiles(String servletContextName) {
		return Collections.unmodifiableSet(_extMap.get(servletContextName)
			.getFiles());
	}

	public static Set<String> getServletContextNames() {
		return Collections.unmodifiableSet(_extMap.keySet());
	}

	public static Set<ServletContext> getServletContexts() {
		Set<ServletContext> result = new HashSet<ServletContext>(
			_extMap.size());

		for (ExtRegistryInfo info : _extMap.values()) {
			if (info.getServletContext() != null) {
				result.add(info.getServletContext());
			}
		}

		return Collections.unmodifiableSet(result);
	}

	public static boolean isIgnoredFile(String name) {
		if (isMergedFile(name)) {
			return true;
		}

		for (String ignoredFile : IGNORED_FILES) {
			if (name.contains(ignoredFile)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isMergedFile(String name) {
		for (String mergedFile : SUPPORTED_MERGING_FILES) {
			if (name.contains(mergedFile)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isRegistered(String servletContextName) {
		if (_extMap.containsKey(servletContextName)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isRestartPending(String servletContextName) {
		ExtRegistryInfo extRegistryInfo = _extMap.get(servletContextName);
		if (extRegistryInfo == null) {
			throw new IllegalStateException(
				"Ext plugin is not registered: " + servletContextName);
		}

		return extRegistryInfo.isRestartPending();
	}

	public static boolean isStarted(String servletContextName) {
		ExtRegistryInfo extRegistryInfo = _extMap.get(servletContextName);
		if (extRegistryInfo == null) {
			throw new IllegalStateException(
				"Ext plugin is not registered: " + servletContextName);
		}

		if (extRegistryInfo.getServletContext() == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public static boolean isUndeployed(String servletContextName) {
		ExtRegistryInfo extRegistryInfo = _extMap.get(servletContextName);
		if (extRegistryInfo == null) {
			throw new IllegalStateException(
				"Ext plugin is not registered: " + servletContextName);
		}

		return extRegistryInfo.isUndeployed();
	}

	public static void registerExt(ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Set<String> files = _readExtFiles(
			servletContext, "/WEB-INF/ext-" + servletContextName + ".xml");

		_extMap.put(
			servletContextName, new ExtRegistryInfo(servletContext, files));
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

				Set<String> files = _readExtFiles(servletContext, resourcePath);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Registering installed ext plugin for " +
							servletContextName);
				}

				_extMap.put(
					servletContextName, new ExtRegistryInfo(null, files));
			}
		}
	}

	public static void setRestartPending(String servletContextName) {
		ExtRegistryInfo extRegistryInfo = _extMap.get(servletContextName);
		if (extRegistryInfo == null) {
			throw new IllegalStateException(
				"Ext plugin is not registered: " + servletContextName);
		}

		extRegistryInfo.setRestartPending(true);
	}

	public static void setStarted(ServletContext servletContext) {
		String servletContextName = servletContext.getServletContextName();
		ExtRegistryInfo extRegistryInfo = _extMap.get(servletContextName);
		if (extRegistryInfo == null) {
			throw new IllegalStateException(
				"Ext plugin is not registered: " + servletContextName);
		}

		extRegistryInfo.setRestartPending(false);
		extRegistryInfo.setServletContext(servletContext);
		extRegistryInfo.setUndeployed(false);
	}

	public static void setUndeployed(String servletContextName) {
		ExtRegistryInfo extRegistryInfo = _extMap.get(servletContextName);
		if (extRegistryInfo == null) {
			throw new IllegalStateException(
				"Ext plugin is not registered: " + servletContextName);
		}

		extRegistryInfo.setServletContext(null);
		extRegistryInfo.setUndeployed(true);
	}

	public static void unregisterExt(String servletContextName) {
		_extMap.remove(servletContextName);
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
			String fileName = fileElement.getText();
			if (!isIgnoredFile(fileName)) {
				files.add(fileName);
			}
		}

		return files;
	}

	private static Log _log = LogFactoryUtil.getLog(ExtRegistry.class);

	private static Map<String, ExtRegistryInfo> _extMap =
		Collections.synchronizedMap(new HashMap<String, ExtRegistryInfo>());

	private static class ExtRegistryInfo {

		public ExtRegistryInfo(
			ServletContext servletContext, Set<String> files) {

			this._servletContext = servletContext;
			this._files = files;
		}

		public boolean isRestartPending() {
			return this._restartPending;
		}

		public boolean isUndeployed() {
			return this._undeployed;
		}

		public Set<String> getFiles() {
			return _files;
		}

		public ServletContext getServletContext() {
			return _servletContext;
		}

		public void setFiles(Set<String> files) {
			this._files = files;
		}

		public void setRestartPending(boolean restartPending) {
			this._restartPending = restartPending;
		}

		public void setServletContext(ServletContext servletContext) {
			this._servletContext = servletContext;
		}

		public void setUndeployed(boolean undeployed) {
			this._undeployed = undeployed;
		}

		private Set<String> _files;
		private ServletContext _servletContext;
		private boolean _undeployed;
		private boolean _restartPending;
	}

}