/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.JobFactory;
import com.liferay.jenkins.results.parser.PortalAcceptancePullRequestJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.test.batch.DefaultTestBatch;
import com.liferay.jenkins.results.parser.test.batch.TestBatch;
import com.liferay.jenkins.results.parser.test.batch.TestSelector;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Kenji Heigel
 */
public class RelevantRuleValidation {

	public static void evaluate(List<RelevantRule> relevantRules) {
		for (RelevantRule relevantRule : relevantRules) {
			try {
				relevantRule.validate();
			}
			catch (RelevantRuleConfigurationException
						relevantRuleConfigurationException) {

				RelevantRuleConfigurationException.addException(
					relevantRuleConfigurationException);
			}

			for (TestBatch testBatch : relevantRule.getTestBatches()) {
				if (testBatch instanceof DefaultTestBatch) {
					continue;
				}

				TestSelector testSelector = testBatch.getTestSelector();

				try {
					testSelector.validate();
				}
				catch (RelevantRuleConfigurationException
							relevantRuleConfigurationException) {

					RelevantRuleConfigurationException.addException(
						relevantRuleConfigurationException);
				}
			}
		}
	}

	public static void throwExceptions() {
		StringBuilder sb = new StringBuilder();

		int i = 1;

		for (Exception exception :
				RelevantRuleConfigurationException.getExceptions()) {

			if (i == 1) {
				sb.append("The following issues were found:");
			}

			sb.append("\n");
			sb.append(i);
			sb.append(". ");
			sb.append(exception.getMessage());

			i++;
		}

		if (sb.length() > 0) {
			throw new RuntimeException(sb.toString());
		}
	}

	public static void validate(List<RelevantRule> relevantRules) {
		evaluate(relevantRules);

		throwExceptions();
	}

	public static void validate(
			String repositoryName, String upstreamBranchName)
		throws IOException {

		Properties properties = JenkinsResultsParserUtil.getBuildProperties();

		File portalDir = new File(
			properties.getProperty("portal.dir[" + upstreamBranchName + "]"));

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					upstreamBranchName, portalDir, repositoryName);

		PortalAcceptancePullRequestJob portalAcceptancePullRequestJob =
			(PortalAcceptancePullRequestJob)JobFactory.newJob(
				Job.BuildProfile.DXP,
				"test-portal-acceptance-pullrequest(master)", null,
				portalGitWorkingDirectory, upstreamBranchName, null,
				repositoryName, "relevant", upstreamBranchName);

		RelevantRuleEngine.getInstance(portalAcceptancePullRequestJob);

		for (Path testPropertiesPath : _findTestPropertiesPaths(portalDir)) {
			Properties testProperties = JenkinsResultsParserUtil.getProperties(
				testPropertiesPath.toFile());

			String relevantRuleNames = JenkinsResultsParserUtil.getProperty(
				testProperties, "relevant.rule.names");

			if (relevantRuleNames == null) {
				continue;
			}

			List<RelevantRule> relevantRules = new ArrayList<>();

			for (String relevantRuleName : relevantRuleNames.split(",")) {
				RelevantRule relevantRule = new RelevantRule(
					testPropertiesPath.toString(),
					portalAcceptancePullRequestJob, relevantRuleName,
					testProperties);

				relevantRules.add(relevantRule);
			}

			evaluate(relevantRules);
		}

		throwExceptions();
	}

	private static List<Path> _findTestPropertiesPaths(File baseDir)
		throws IOException {

		List<Path> testPropertiesFiles = new ArrayList<>();

		Files.walkFileTree(
			baseDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (JenkinsResultsParserUtil.isFileExcluded(
							JenkinsResultsParserUtil.toPathMatchers(
								baseDir.getCanonicalPath(), _EXCLUDE_GLOBS),
							filePath.toFile())) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path file, BasicFileAttributes basicFileAttributes) {

					if (Objects.equals(
							String.valueOf(file.getFileName()),
							"test.properties")) {

						testPropertiesFiles.add(file);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return testPropertiesFiles;
	}

	private static final String[] _EXCLUDE_GLOBS = {
		"**/.git/**", "**/.gradle/**", "**/.m2/**", "**/.settings/**",
		"**/bin/**", "**/build/**", "**/classes/**",
		"**/jenkins-results-parser/src/test/resources/dependencies/**",
		"**/node_modules/**", "**/test-classes/**", "**/test-coverage/**",
		"**/test-results/**", "**/tmp/**", "**/tools/**",
		"**/WEB-INF/classes/**", "**/work/**"
	};

}