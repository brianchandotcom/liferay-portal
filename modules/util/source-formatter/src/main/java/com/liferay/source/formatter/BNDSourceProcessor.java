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

package com.liferay.source.formatter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Hugo Huijser
 */
public class BNDSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws IOException {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected File format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		file = super.format(file, fileName, absolutePath, content);

		_processPortalModulesBundleVersion(absolutePath, content);

		return file;
	}

	@Override
	protected void postFormat() throws IOException {
		_processPortalModulesBundleVersions();
	}

	@Override
	protected void preFormat() throws IOException {
		_initPortalModulesVersionsMap();
	}

	private boolean _checkModulesPropertiesFile() {
		if (!isPortalSource()) {
			return false;
		}

		SourceFormatterArgs sourceFormatterArgs = getSourceFormatterArgs();

		List<String> fileExtensions = sourceFormatterArgs.getFileExtensions();

		if (!fileExtensions.contains("bnd")) {
			return false;
		}

		return true;
	}

	private String _getBundleSymbolicName(String absolutePath, String content) {
		if (absolutePath.endsWith("/portal-impl/bnd.bnd")) {
			return "com.liferay.portal.impl";
		}
		else if (absolutePath.endsWith("/portal-kernel/bnd.bnd")) {
			return "com.liferay.portal.kernel";
		}
		else if (absolutePath.endsWith("/portal-test-integration/bnd.bnd")) {
			return "com.liferay.portal.test.integration";
		}
		else if (absolutePath.endsWith("/portal-test/bnd.bnd")) {
			return "com.liferay.portal.test";
		}
		else if (absolutePath.endsWith("/portal-support-tomcat/bnd.bnd")) {
			return "com.liferay.support.tomcat";
		}
		else if (absolutePath.endsWith("/util-bridges/bnd.bnd")) {
			return "com.liferay.util.bridges";
		}
		else if (absolutePath.endsWith("/util-java/bnd.bnd")) {
			return "com.liferay.util.java";
		}
		else if (absolutePath.endsWith("/util-slf4j/bnd.bnd")) {
			return "com.liferay.util.slf4j";
		}
		else if (absolutePath.endsWith("/util-taglib/bnd.bnd")) {
			return "com.liferay.util.taglib";
		}

		return BNDSourceUtil.getDefinitionValue(content, "Bundle-SymbolicName");
	}

	private void _initPortalModulesVersionsMap() throws IOException {
		_portalModulesBundleVersionsMap = new TreeMap<>();

		if (!_checkModulesPropertiesFile()) {
			return;
		}

		File modulesPropertiesFile = new File(
			getPortalDir(), "modules/modules.properties");

		if (!modulesPropertiesFile.exists()) {
			return;
		}

		String content = FileUtil.read(modulesPropertiesFile);

		for (String line : StringUtil.splitLines(content)) {
			String[] array = StringUtil.split(line, StringPool.EQUAL);

			if (array.length == 2) {
				_portalModulesBundleVersionsMap.put(array[0], array[1]);
			}
		}
	}

	private synchronized void _processPortalModulesBundleVersion(
		String absolutePath, String content) {

		if (!_checkModulesPropertiesFile()) {
			return;
		}

		if (!absolutePath.endsWith("/bnd.bnd") ||
			absolutePath.contains("/private/") ||
			absolutePath.contains("/test/") ||
			absolutePath.contains("/test-integration/")) {

			return;
		}

		String bundleSymbolicName = _getBundleSymbolicName(
			absolutePath, content);

		if (Validator.isNull(bundleSymbolicName)) {
			return;
		}

		if (!bundleSymbolicName.startsWith("com.liferay.")) {
			return;
		}

		String bundleVersion = BNDSourceUtil.getDefinitionValue(
			content, "Bundle-Version");

		if (Validator.isNull(bundleVersion)) {
			return;
		}

		_portalModulesBundleVersionsMap.put(bundleSymbolicName, bundleVersion);
	}

	private void _processPortalModulesBundleVersions() throws IOException {
		if (!_checkModulesPropertiesFile()) {
			return;
		}

		List<String> modulesProperties = new ArrayList<>();

		for (Map.Entry<String, String> entry :
				_portalModulesBundleVersionsMap.entrySet()) {

			modulesProperties.add(entry.getKey() + "=" + entry.getValue());
		}

		File modulesPropertiesFile = new File(
			getPortalDir(), "modules/modules.properties");

		String newContent = StringUtil.merge(modulesProperties, "\n");
		String oldContent = FileUtil.read(modulesPropertiesFile);

		if (!newContent.equals(oldContent)) {
			FileUtil.write(modulesPropertiesFile, newContent);

			System.out.println("Updated 'modules.properties'");
		}
	}

	private static final String[] _INCLUDES = {"**/*.bnd"};

	private Map<String, String> _portalModulesBundleVersionsMap;

}