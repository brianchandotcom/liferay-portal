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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class GradleRequiredDependenciesCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith(
				"/required-dependencies/required-dependencies/build.gradle")) {

			return content;
		}

		List<String> buildGradleContents = _getBuildGradleContents();

		Matcher matcher = _dependencyNamePattern.matcher(content);

		while (matcher.find()) {
			_checkDependency(
				fileName, content, matcher.group(1), matcher.group(2),
				buildGradleContents);
		}

		return content;
	}

	private void _checkDependency(
		String fileName, String content, String dependency,
		String dependencyName, List<String> buildGradleContents) {

		int count = 0;

		for (String buildGradleContent : buildGradleContents) {
			if (!buildGradleContent.contains(dependency)) {
				continue;
			}

			count++;

			if (count > 1) {
				return;
			}
		}

		int lineNumber = getLineNumber(content, content.indexOf(dependency));

		if (count == 0) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Remove dependency '", dependencyName,
					"' since it is not used by any module"),
				lineNumber);
		}
		else {
			addMessage(
				fileName,
				StringBundler.concat(
					"Remove dependency '", dependencyName,
					"' since it is only used by 1 module"),
				lineNumber);
		}
	}

	private List<String> _getBuildGradleContents() throws IOException {
		List<String> buildGradleContents = new ArrayList<>();

		String moduleAppsDirLocation = "modules/apps/";

		for (int i = 0; i < (ToolsUtil.PORTAL_MAX_DIR_LEVEL - 1); i++) {
			File file = new File(getBaseDirName() + moduleAppsDirLocation);

			if (!file.exists()) {
				moduleAppsDirLocation = "../" + moduleAppsDirLocation;

				continue;
			}

			List<String> buildGradleFileNames =
				SourceFormatterUtil.scanForFiles(
					getBaseDirName() + moduleAppsDirLocation,
					new String[] {
						"**/required-dependencies/required-dependencies" +
							"/build.gradle"
					},
					new String[] {"**/build.gradle"},
					getSourceFormatterExcludes(), false);

			for (String buildGradleFileName : buildGradleFileNames) {
				buildGradleFileName = StringUtil.replace(
					buildGradleFileName, CharPool.BACK_SLASH, CharPool.SLASH);

				buildGradleContents.add(
					FileUtil.read(new File(buildGradleFileName)));
			}

			break;
		}

		return buildGradleContents;
	}

	private static final Pattern _dependencyNamePattern = Pattern.compile(
		"compileOnly group: (\".* name: \"(.*?)\".*)");

}