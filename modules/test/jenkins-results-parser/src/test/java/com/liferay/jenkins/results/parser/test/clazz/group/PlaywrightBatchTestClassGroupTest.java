/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.AntUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.NotificationUtil;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.ReflectionTestUtil;
import com.liferay.jenkins.results.parser.Shell;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Calum Ragan
 */
public class PlaywrightBatchTestClassGroupTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testLoadPlaywrightJSONObjects() throws Exception {
		JSONObject reportJSONObject = new JSONObject(
		).put(
			"config",
			new JSONObject(
			).put(
				"rootDir", RandomTestUtil.randomString()
			)
		);

		_testLoadPlaywrightJSONObjects(
			1, false, reportJSONObject, 0, reportJSONObject);
		_testLoadPlaywrightJSONObjects(
			2, false, reportJSONObject, 1, reportJSONObject);
		_testLoadPlaywrightJSONObjects(
			2, true, new JSONObject(), 2, reportJSONObject);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _testLoadPlaywrightJSONObjects(
			int expectedExecutionRequestCount, boolean expectedNotified,
			JSONObject expectedPlaywrightJSONObject,
			int failedExecutionRequestCount, JSONObject reportJSONObject)
		throws Exception {

		AtomicBoolean playwrightJSONObjectsLoaded =
			ReflectionTestUtil.getFieldValue(
				PlaywrightBatchTestClassGroup.class,
				"_playwrightJSONObjectsLoaded");

		playwrightJSONObjectsLoaded.set(false);

		File portalWorkingDirectory = temporaryFolder.newFolder();

		PortalGitWorkingDirectory portalGitWorkingDirectory = Mockito.mock(
			PortalGitWorkingDirectory.class);

		Mockito.doReturn(
			portalWorkingDirectory
		).when(
			portalGitWorkingDirectory
		).getWorkingDirectory();

		PlaywrightBatchTestClassGroup playwrightBatchTestClassGroup =
			Mockito.mock(PlaywrightBatchTestClassGroup.class);

		Mockito.doCallRealMethod(
		).when(
			playwrightBatchTestClassGroup
		).getPlaywrightBaseDir();

		ReflectionTestUtil.setFieldValue(
			playwrightBatchTestClassGroup, "portalGitWorkingDirectory",
			portalGitWorkingDirectory);

		JenkinsResultsParserUtil.write(
			new File(
				playwrightBatchTestClassGroup.getPlaywrightBaseDir(),
				"build.gradle"),
			"task runPlaywright");

		List<Shell.ExecutionRequest> executionRequests = new ArrayList<>();
		List<File> reportFiles = new ArrayList<>();

		Shell.setInstance(
			Mockito.mock(
				Shell.class,
				invocation -> {
					Shell.ExecutionRequest executionRequest =
						invocation.getArgument(0);

					executionRequests.add(executionRequest);

					String[] commands = executionRequest.getCommands();

					Matcher matcher = _playwrightJSONOutputNamePattern.matcher(
						commands[0]);

					Assert.assertTrue(commands[0], matcher.find());

					File reportFile = new File(matcher.group(1));

					reportFiles.add(reportFile);

					if (executionRequests.size() <=
							failedExecutionRequestCount) {

						throw new TimeoutException();
					}

					JenkinsResultsParserUtil.write(
						reportFile, reportJSONObject.toString());

					return new Shell.ExecutionResult(0, "", "");
				}));

		mockEnvironment(
			Collections.singletonMap(
				"TOP_LEVEL_BUILD_URL",
				JenkinsResultsParserUtil.combine(
					"https://", RandomTestUtil.randomString(), "/job/",
					RandomTestUtil.randomString(), "(release)/",
					String.valueOf(RandomTestUtil.randomInt()))));

		try (MockedStatic<AntUtil> antUtilMockedStatic = Mockito.mockStatic(
				AntUtil.class);
			MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic = Mockito.mockStatic(
					JenkinsResultsParserUtil.class, Mockito.CALLS_REAL_METHODS);
			MockedStatic<NotificationUtil> notificationUtilMockedStatic =
				Mockito.mockStatic(NotificationUtil.class)) {

			jenkinsResultsParserUtilMockedStatic.when(
				() -> JenkinsResultsParserUtil.sleep(Mockito.anyLong())
			).thenAnswer(
				invocation -> null
			);

			ReflectionTestUtil.invoke(
				playwrightBatchTestClassGroup, "_loadPlaywrightJSONObjects",
				new Class<?>[0]);
			ReflectionTestUtil.invoke(
				playwrightBatchTestClassGroup, "_loadPlaywrightJSONObjects",
				new Class<?>[0]);

			notificationUtilMockedStatic.verify(
				() -> NotificationUtil.sendSlackNotification(
					Mockito.anyString(), Mockito.anyString(),
					Mockito.anyString(), Mockito.anyString(),
					Mockito.anyString()),
				getVerificationMode(expectedNotified));
		}

		Assert.assertEquals(
			executionRequests.toString(), expectedExecutionRequestCount,
			executionRequests.size());

		for (Shell.ExecutionRequest executionRequest : executionRequests) {
			Assert.assertEquals(1000 * 60 * 30, executionRequest.getTimeout());
		}

		Assert.assertEquals(
			reportFiles.toString(), reportFiles.size(),
			new HashSet<>(
				reportFiles
			).size());

		String portalWorkingDirectoryPath =
			JenkinsResultsParserUtil.getCanonicalPath(portalWorkingDirectory);

		for (File reportFile : reportFiles) {
			String reportFilePath = JenkinsResultsParserUtil.getCanonicalPath(
				reportFile);

			Assert.assertFalse(reportFilePath, reportFile.exists());
			Assert.assertFalse(
				reportFilePath,
				reportFilePath.startsWith(portalWorkingDirectoryPath));
		}

		JSONObject playwrightJSONObject = ReflectionTestUtil.getFieldValue(
			PlaywrightBatchTestClassGroup.class, "_playwrightJSONObject");

		Assert.assertTrue(
			playwrightJSONObject.toString(),
			expectedPlaywrightJSONObject.similar(playwrightJSONObject));
	}

	private static final Pattern _playwrightJSONOutputNamePattern =
		Pattern.compile("export PLAYWRIGHT_JSON_OUTPUT_NAME=(.+)");

}