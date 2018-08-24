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

package com.liferay.source.formatter.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Peter Shin
 */
public class ModulesPropertiesUtil {

	public static String getContent(File portalDir) throws IOException {
		StringBundler sb = new StringBundler();

		Map<String, String> bundleVersionsMap = getBundleVersionsMap(portalDir);

		for (Map.Entry<String, String> entry : bundleVersionsMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(entry.getValue());
			sb.append(StringPool.NEW_LINE);
		}

		if (!bundleVersionsMap.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	protected static Map<String, String> getBundleVersionsMap(File portalDir)
		throws IOException {

		if (portalDir == null) {
			return Collections.emptyMap();
		}

		final List<File> files = new ArrayList<>();

		Files.walkFileTree(
			portalDir.toPath(), EnumSet.noneOf(FileVisitOption.class), 15,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					String dirName = String.valueOf(dirPath.getFileName());

					if (ArrayUtil.contains(_SKIP_DIR_NAMES, dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path path = dirPath.resolve("bnd.bnd");

					if (Files.exists(path)) {
						files.add(path.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		Map<String, String> bundleVersionsMap = new TreeMap<>();

		for (File file : files) {
			String content = FileUtil.read(file);

			String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
				content, "Bundle-SymbolicName");

			if (Validator.isNull(bundleSymbolicName)) {
				continue;
			}

			String path = SourceUtil.getAbsolutePath(file);

			if (path.endsWith("/portal-impl/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.portal.impl";
			}
			else if (path.endsWith("/portal-kernel/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.portal.kernel";
			}
			else if (path.endsWith("/portal-test-integration/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.portal.test.integration";
			}
			else if (path.endsWith("/portal-test/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.portal.test";
			}
			else if (path.endsWith("/portal-support-tomcat/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.support.tomcat";
			}
			else if (path.endsWith("/util-bridges/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.util.bridges";
			}
			else if (path.endsWith("/util-java/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.util.java";
			}
			else if (path.endsWith("/util-slf4j/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.util.slf4j";
			}
			else if (path.endsWith("/util-taglib/bnd.bnd")) {
				bundleSymbolicName = "com.liferay.util.taglib";
			}

			if (!bundleSymbolicName.startsWith("com.liferay.")) {
				continue;
			}

			String bundleVersion = BNDSourceUtil.getDefinitionValue(
				content, "Bundle-Version");

			if (Validator.isNull(bundleVersion)) {
				continue;
			}

			bundleVersionsMap.put(bundleSymbolicName, bundleVersion);
		}

		return bundleVersionsMap;
	}

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".settings", "bin", "build",
		"classes", "dependencies", "node_modules", "private", "sql", "src",
		"test", "test-classes", "test-coverage", "test-results", "tmp"
	};

}