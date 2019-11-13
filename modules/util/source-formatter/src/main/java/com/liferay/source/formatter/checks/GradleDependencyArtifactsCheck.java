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
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class GradleDependencyArtifactsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _renameDependencyNames(absolutePath, content);

		return _enforceDependencyVersions(absolutePath, content);
	}

	private String _enforceDependencyVersions(
		String absolutePath, String content) {

		List<String> enforceVersionArtifacts = getAttributeValues(
			_ENFORCE_VERSION_ARTIFACTS_KEY, absolutePath);

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

	private String _getArtifactString(String artifact) {
		String[] array = StringUtil.split(artifact, CharPool.COLON);

		if (array.length != 2) {
			return null;
		}

		return StringBundler.concat(
			"group: \"", array[0], "\", name: \"", array[1], "\"");
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

}