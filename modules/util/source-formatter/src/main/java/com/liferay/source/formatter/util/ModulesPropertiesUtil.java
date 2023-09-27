/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;

/**
 * @author Peter Shin
 */
public class ModulesPropertiesUtil {

	public static String getContent(File portalDir) throws IOException {
		StringBundler sb = new StringBundler();

		Map<String, String> moduleInformationMap = _getModuleInformationMap(
			portalDir);

		for (Map.Entry<String, String> entry :
				moduleInformationMap.entrySet()) {

			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(entry.getValue());
			sb.append(StringPool.NEW_LINE);
		}

		if (!moduleInformationMap.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static String _getBundleSymbolicName(
		String bndContent, String absolutePath) {

		if (absolutePath.endsWith("/portal-impl/bnd.bnd")) {
			return "com.liferay.portal.impl";
		}

		if (absolutePath.endsWith("/portal-kernel/bnd.bnd")) {
			return "com.liferay.portal.kernel";
		}

		if (absolutePath.endsWith("/portal-test-integration/bnd.bnd")) {
			return "com.liferay.portal.test.integration";
		}

		if (absolutePath.endsWith("/portal-test/bnd.bnd")) {
			return "com.liferay.portal.test";
		}

		if (absolutePath.endsWith("/portal-support-tomcat/bnd.bnd")) {
			return "com.liferay.support.tomcat";
		}

		if (absolutePath.endsWith("/util-bridges/bnd.bnd")) {
			return "com.liferay.util.bridges";
		}

		if (absolutePath.endsWith("/util-java/bnd.bnd")) {
			return "com.liferay.util.java";
		}

		if (absolutePath.endsWith("/util-slf4j/bnd.bnd")) {
			return "com.liferay.util.slf4j";
		}

		if (absolutePath.endsWith("/util-taglib/bnd.bnd")) {
			return "com.liferay.util.taglib";
		}

		String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
			bndContent, "Bundle-SymbolicName");

		if (Validator.isNotNull(bundleSymbolicName) &&
			bundleSymbolicName.startsWith("com.liferay.")) {

			return bundleSymbolicName;
		}

		return null;
	}

	private static Map<String, String> _getModuleInformationMap(File portalDir)
		throws IOException {

		if (portalDir == null) {
			return Collections.emptyMap();
		}

		final Map<String, String> moduleInformationMap = new TreeMap<>();

		Files.walkFileTree(
			portalDir.toPath(), EnumSet.noneOf(FileVisitOption.class), 15,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String dirName = String.valueOf(dirPath.getFileName());

					if (ArrayUtil.contains(_SKIP_DIR_NAMES, dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path path = dirPath.resolve(".gitrepo");

					if (Files.exists(path)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path bndPath = dirPath.resolve("bnd.bnd");

					if (!Files.exists(bndPath)) {
						return FileVisitResult.CONTINUE;
					}

					String bndContent = FileUtil.read(bndPath.toFile());

					String bundleSymbolicName = _getBundleSymbolicName(
						bndContent, SourceUtil.getAbsolutePath(bndPath));

					if (bundleSymbolicName == null) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					String bundleVersion = BNDSourceUtil.getDefinitionValue(
						bndContent, "Bundle-Version");

					if (Validator.isNotNull(bundleVersion)) {
						moduleInformationMap.put(
							"bundle.version[" + bundleSymbolicName + "]",
							bundleVersion);
					}

					String absolutePath = SourceUtil.getAbsolutePath(dirPath);

					int x = absolutePath.indexOf("/modules/");

					if (x != -1) {
						moduleInformationMap.put(
							"project.name[" + bundleSymbolicName + "]",
							StringUtil.replace(
								absolutePath.substring(x + 8), CharPool.SLASH,
								CharPool.COLON));
					}

					Path packageJSONPath = dirPath.resolve("package.json");

					if (!Files.exists(packageJSONPath)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					JSONObject jsonObject = new JSONObject(
						FileUtil.read(packageJSONPath.toFile()));

					if (!jsonObject.isNull("name")) {
						moduleInformationMap.put(
							"bundle.symbolic.name[" +
								jsonObject.getString("name") + "]",
							bundleSymbolicName);
					}

					return FileVisitResult.SKIP_SUBTREE;
				}

			});

		return moduleInformationMap;
	}

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".settings", "bin", "build",
		"classes", "dependencies", "node_modules", "node_modules_cache",
		"private", "sdk", "sql", "src", "test-classes", "test-coverage",
		"test-results", "tmp"
	};

}