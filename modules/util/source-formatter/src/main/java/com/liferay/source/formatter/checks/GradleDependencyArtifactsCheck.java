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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class GradleDependencyArtifactsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = _renameDependencyNames(absolutePath, content);

		List<String> enforceVersionArtifacts = getAttributeValues(
			_ENFORCE_VERSION_ARTIFACTS_KEY, absolutePath);

		content = _enforceDependencyVersions(content, enforceVersionArtifacts);

		Matcher matcher = _artifactPattern.matcher(content);

		while (matcher.find()) {
			String name = matcher.group(3);
			String version = matcher.group(4);

			content = _formatVersion(
				fileName, absolutePath, content, matcher.group(2), name,
				version, matcher.start(3), enforceVersionArtifacts);

			if ((isSubrepository() || absolutePath.contains("/modules/apps/") ||
				 absolutePath.contains("/modules/dxp/apps/") ||
				 absolutePath.contains("/modules/private/apps/")) &&
				!_isTestUtilModule(absolutePath)) {

				content = _fixMicroVersion(
					fileName, content, matcher.group(1), name, version);
			}
		}

		return content;
	}

	private String _enforceDependencyVersions(
		String content, List<String> enforceVersionArtifacts) {

		for (String artifact : enforceVersionArtifacts) {
			String[] artifactParts = StringUtil.split(
				artifact, StringPool.COLON);

			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"(group: \"", artifactParts[0], "\", name: \"",
					artifactParts[1], "\",.* version: \").*?(\")"));

			Matcher matcher = pattern.matcher(content);

			content = matcher.replaceAll("$1" + artifactParts[2] + "$2");
		}

		return content;
	}

	private String _fixMicroVersion(
			String fileName, String content, String line, String name,
			String version)
		throws IOException {

		if (!line.startsWith("compileOnly ") && !line.startsWith("provided ")) {
			return content;
		}

		if (!name.startsWith("com.liferay.") ||
			!version.matches("[0-9]+\\.[0-9]+\\.[1-9][0-9]*")) {

			return content;
		}

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		String bndFileLocation = fileName.substring(0, pos + 1) + "bnd.bnd";

		File bndFile = new File(bndFileLocation);

		if (bndFile.exists()) {
			String bndFileContent = FileUtil.read(bndFile);

			Matcher matcher = _bndConditionalPackagePattern.matcher(
				bndFileContent);

			if (matcher.find()) {
				String conditionalPackageContent = matcher.group();

				if (conditionalPackageContent.contains(name)) {
					return content;
				}
			}
		}

		pos = version.lastIndexOf(".");

		String newLine = StringUtil.replaceFirst(
			line, "version: \"" + version + "\"",
			"version: \"" + version.substring(0, pos + 1) + "0\"");

		return StringUtil.replaceFirst(content, line, newLine);
	}

	private String _formatVersion(
			String fileName, String absolutePath, String content,
			String dependency, String name, String version, int pos,
			List<String> enforceVersionArtifacts)
		throws IOException {

		if (isSubrepository() || !fileName.endsWith("build.gradle") ||
			absolutePath.contains("/modules/apps/static/portal-osgi-web/") ||
			absolutePath.contains("/modules/util/")) {

			return content;
		}

		if (!name.equals("com.liferay.portal.impl") &&
			!name.equals("com.liferay.portal.kernel") &&
			!name.equals("com.liferay.portal.test") &&
			!name.equals("com.liferay.portal.test.integration") &&
			!name.equals("com.liferay.util.bridges") &&
			!name.equals("com.liferay.util.java") &&
			!name.equals("com.liferay.util.taglib")) {

			if (version.equals("default")) {
				addMessage(
					fileName, "Do not use 'default' version for '" + name + "'",
					"gradle_versioning.markdown", getLineNumber(content, pos));
			}
			else if (name.startsWith("com.liferay") &&
					 !name.startsWith("com.liferay.gradle") &&
					 !_isMasterOnlyFile(absolutePath)) {

				for (String enforceVersionArtifact : enforceVersionArtifacts) {
					if (enforceVersionArtifact.startsWith(name + ":")) {
						return content;
					}
				}

				Map<String, String> projectNamesMap = _getProjectNamesMap(
					absolutePath);

				if (projectNamesMap.containsKey(name)) {
					return StringUtil.replace(
						content, dependency,
						"project(\"" + projectNamesMap.get(name) + "\")");
				}

				addMessage(
					fileName, "Incorrect dependency '" + name + "'",
					getLineNumber(content, pos));
			}
		}
		else if (!version.equals("default") &&
				 !_isMasterOnlyFile(absolutePath)) {

			return StringUtil.replaceFirst(content, version, "default", pos);
		}

		return content;
	}

	private String _getArtifactString(String artifact) {
		String[] array = StringUtil.split(artifact, CharPool.COLON);

		if (array.length != 2) {
			return null;
		}

		return StringBundler.concat(
			"group: \"", array[0], "\", name: \"", array[1], "\"");
	}

	private synchronized Map<String, String> _getProjectNamesMap(
			String absolutePath)
		throws IOException {

		if (_projectNamesMap != null) {
			return _projectNamesMap;
		}

		_projectNamesMap = new HashMap<>();

		String content = getModulesPropertiesContent(absolutePath);

		if (Validator.isNull(content)) {
			return _projectNamesMap;
		}

		List<String> lines = ListUtil.fromString(content);

		for (String line : lines) {
			String[] array = StringUtil.split(line, StringPool.EQUAL);

			if (array.length != 2) {
				continue;
			}

			String key = array[0];

			if (key.startsWith("project.name[")) {
				_projectNamesMap.put(
					key.substring(13, key.length() - 1), array[1]);
			}
		}

		return _projectNamesMap;
	}

	private boolean _isMasterOnlyFile(String absolutePath) {
		int x = absolutePath.length();

		while (true) {
			x = absolutePath.lastIndexOf(StringPool.SLASH, x - 1);

			if (x == -1) {
				return false;
			}

			File file = new File(
				absolutePath.substring(0, x + 1) + ".lfrbuild-master-only");

			if (file.exists()) {
				return true;
			}
		}
	}

	private boolean _isTestUtilModule(String absolutePath) {
		int x = absolutePath.lastIndexOf(StringPool.SLASH);

		int y = absolutePath.lastIndexOf(StringPool.SLASH, x - 1);

		String moduleName = absolutePath.substring(y + 1, x);

		if (!moduleName.endsWith("-test-util")) {
			return false;
		}

		return true;
	}

	private String _renameDependencyNames(String absolutePath, String content) {
		List<String> renameArtifacts = getAttributeValues(
			_RENAME_ARTIFACTS_KEY, absolutePath);

		for (String renameArtifact : renameArtifacts) {
			String[] renameArtifactArray = StringUtil.split(
				renameArtifact, "->");

			if (renameArtifactArray.length != 2) {
				continue;
			}

			String newArtifactString = _getArtifactString(
				renameArtifactArray[1]);
			String oldArtifactString = _getArtifactString(
				renameArtifactArray[0]);

			if ((newArtifactString != null) && (oldArtifactString != null)) {
				content = StringUtil.replace(
					content, oldArtifactString, newArtifactString);
			}
		}

		return content;
	}

	private static final String _ENFORCE_VERSION_ARTIFACTS_KEY =
		"enforceVersionArtifacts";

	private static final String _RENAME_ARTIFACTS_KEY = "renameArtifacts";

	private static final Pattern _artifactPattern = Pattern.compile(
		"\n\t*(.* (group: \"[^\"]+\", name: \"([^\"]+)\", " +
			"version: \"([^\"]+)\"))");
	private static final Pattern _bndConditionalPackagePattern =
		Pattern.compile(
			"-conditionalpackage:(.*[^\\\\])(\n|\\Z)", Pattern.DOTALL);

	private Map<String, String> _projectNamesMap;

}