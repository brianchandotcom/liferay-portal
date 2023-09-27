/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class JavaUpgradeConnectionCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		if (absolutePath.contains("/test/")) {
			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		String className = javaClass.getName();

		if ((className.contains("Upgrade") || className.contains("Verify")) &&
			_extendsPortalKernelUpgradeProcess(absolutePath, fileContent)) {

			_checkDataAccessGetConnection(fileName, fileContent, javaClass);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkDataAccessGetConnection(
		String fileName, String fileContent, JavaClass javaClass) {

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String methodName = javaMethod.getName();

			if (methodName.equals("upgrade") &&
				javaMethod.hasAnnotation("Override")) {

				continue;
			}

			String methodContent = javaMethod.getContent();

			int x = methodContent.indexOf("DataAccess.getConnection");

			if (x != -1) {
				addMessage(
					fileName,
					"Use existing connection field instead of " +
						"DataAccess.getConnection",
					getLineNumber(fileContent, x));
			}
		}
	}

	private boolean _extendsPortalKernelUpgradeProcess(
			String absolutePath, String fileContent)
		throws IOException {

		String upgradeAbsolutePath = absolutePath;
		String upgradeContent = fileContent;

		while (Validator.isNotNull(upgradeAbsolutePath)) {
			if (!absolutePath.equals(upgradeAbsolutePath)) {
				upgradeContent = _getUpgradeContent(upgradeAbsolutePath);
			}

			upgradeAbsolutePath = StringPool.BLANK;

			if (Validator.isNull(upgradeContent)) {
				return false;
			}

			Matcher matcher = _extendedClassPattern.matcher(upgradeContent);

			if (matcher.find()) {
				String extendedClassName = matcher.group(1);

				String relativePath = StringPool.BLANK;

				if (extendedClassName.contains(StringPool.PERIOD)) {
					relativePath = StringUtil.replace(
						extendedClassName, CharPool.PERIOD, CharPool.SLASH);
				}
				else {
					String fullyQualifiedName = StringPool.BLANK;

					List<String> importNames = JavaSourceUtil.getImportNames(
						upgradeContent);

					for (String importName : importNames) {
						if (importName.endsWith(
								CharPool.PERIOD + extendedClassName)) {

							fullyQualifiedName = importName;

							break;
						}
					}

					if (Validator.isNotNull(fullyQualifiedName)) {
						relativePath = StringUtil.replace(
							fullyQualifiedName, CharPool.PERIOD,
							CharPool.SLASH);
					}
					else {
						String fileLocation = absolutePath.substring(
							0, absolutePath.lastIndexOf(CharPool.SLASH) + 1);

						relativePath = fileLocation + extendedClassName;
					}
				}

				for (String s : _getUpgradeAbsolutePaths()) {
					if (s.endsWith(relativePath + ".java")) {
						upgradeAbsolutePath = s;

						break;
					}
				}
			}

			if (upgradeAbsolutePath.endsWith(
					"com/liferay/portal/kernel/upgrade/UpgradeProcess.java")) {

				return true;
			}
		}

		return false;
	}

	private List<String> _getUpgradeAbsolutePaths() throws IOException {
		if (_upgradeAbsolutePaths != null) {
			return _upgradeAbsolutePaths;
		}

		File portalDir = getPortalDir();

		if (portalDir == null) {
			_upgradeAbsolutePaths = Collections.emptyList();

			return _upgradeAbsolutePaths;
		}

		final List<String> upgradeAbsolutePaths = new ArrayList<>();

		Files.walkFileTree(
			portalDir.toPath(), EnumSet.noneOf(FileVisitOption.class), 20,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					String dirName = String.valueOf(dirPath.getFileName());

					if (ArrayUtil.contains(_SKIP_DIR_NAMES, dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					String absolutePath = SourceUtil.getAbsolutePath(filePath);
					String fileName = String.valueOf(filePath.getFileName());

					if (absolutePath.contains("/upgrade/") &&
						fileName.endsWith(".java")) {

						upgradeAbsolutePaths.add(absolutePath);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		_upgradeAbsolutePaths = upgradeAbsolutePaths;

		return _upgradeAbsolutePaths;
	}

	private String _getUpgradeContent(String absolutePath) throws IOException {
		if (_upgradeContentsMap.containsKey(absolutePath)) {
			return _upgradeContentsMap.get(absolutePath);
		}

		File file = new File(absolutePath);

		if (!file.exists()) {
			return StringPool.BLANK;
		}

		_upgradeContentsMap.put(absolutePath, FileUtil.read(file));

		return _upgradeContentsMap.get(absolutePath);
	}

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".settings", "bin", "build",
		"classes", "dependencies", "node_modules", "node_modules_cache", "sdk",
		"sql", "test", "test-classes", "test-coverage", "test-results", "tmp"
	};

	private static final Pattern _extendedClassPattern = Pattern.compile(
		"\\sextends\\s+([\\w\\.]+)\\W");

	private List<String> _upgradeAbsolutePaths;
	private final Map<String, String> _upgradeContentsMap = new HashMap<>();

}