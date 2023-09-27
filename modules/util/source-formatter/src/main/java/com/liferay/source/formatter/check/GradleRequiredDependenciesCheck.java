/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
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

		for (int i = 0; i < (getMaxDirLevel() - 1); i++) {
			File file = new File(getBaseDirName() + moduleAppsDirLocation);

			if (!file.exists()) {
				moduleAppsDirLocation = "../" + moduleAppsDirLocation;

				continue;
			}

			List<String> buildGradleFileNames =
				SourceFormatterUtil.scanForFileNames(
					getBaseDirName() + moduleAppsDirLocation,
					new String[] {
						"**/required-dependencies/required-dependencies" +
							"/build.gradle"
					},
					new String[] {"**/build.gradle"},
					getSourceFormatterExcludes(), false);

			for (String buildGradleFileName : buildGradleFileNames) {
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