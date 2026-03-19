/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Kenji Heigel
 */
public class PortalCoreJavaUnitTestRelevantRule extends RelevantRule {

	public PortalCoreJavaUnitTestRelevantRule(
		String filePath, GitWorkingDirectory gitWorkingDirectory, Job job,
		String name, Properties properties) {

		super(filePath, gitWorkingDirectory, job, name, properties);
	}

	@Override
	public List<TestScriptCommand> getTestScriptCommands() {
		List<TestScriptCommand> testScriptCommands = new ArrayList<>();

		Set<String> modifiedDirNames = new HashSet<>();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		String baseFilePath = JenkinsResultsParserUtil.getCanonicalPath(
			gitWorkingDirectory.getWorkingDirectory());

		for (File modifiedFile :
				gitWorkingDirectory.getModifiedFilesList(true)) {

			String modifiedFilePath = JenkinsResultsParserUtil.getCanonicalPath(
				modifiedFile);

			if (modifiedFilePath.startsWith(baseFilePath + "/")) {
				String relativeFilePath = modifiedFilePath.substring(
					baseFilePath.length() + 1);

				int index = relativeFilePath.indexOf('/');

				if (index != -1) {
					String topLevelDirName = relativeFilePath.substring(0, index);

					if (_portalCoreDirs.contains(topLevelDirName)) {
						modifiedDirNames.add(topLevelDirName);
					}
				}
			}
		}

		if (modifiedDirNames.isEmpty()) {
			return testScriptCommands;
		}

		testScriptCommands.add(new TestScriptCommand("ant compile-test", "."));

		for (String modifiedDirName : modifiedDirNames) {
			testScriptCommands.add(
				new TestScriptCommand("ant test-unit", modifiedDirName));
		}

		return testScriptCommands;
	}

	private static final Set<String> _portalCoreDirs = new HashSet<>(
		Arrays.asList(
			"portal-impl", "portal-kernel", "portal-test", "portal-web",
			"support-tomcat", "util-bridges", "util-java", "util-slf4j",
			"util-taglib"));

}