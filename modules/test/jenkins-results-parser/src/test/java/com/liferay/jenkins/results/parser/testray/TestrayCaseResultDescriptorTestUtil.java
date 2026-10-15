/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.RandomTestUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Collections;
import java.util.Properties;

import org.mockito.Mockito;

/**
 * @author Michael Hashimoto
 */
public class TestrayCaseResultDescriptorTestUtil {

	public static File newCacheDir() {
		try {
			File cacheDir = File.createTempFile("testray-descriptor-", null);

			cacheDir.delete();

			File jenkinsRepositoryDir = new File(
				cacheDir, JenkinsResultsParserUtil.JENKINS_REPOSITORY_NAME);

			jenkinsRepositoryDir.mkdirs();

			return cacheDir;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static Job newJob(Properties jobProperties) {
		Job job = Mockito.mock(Job.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			job
		).getJobName();

		Mockito.doReturn(
			Collections.singletonList(_newJobPropertiesFile(jobProperties))
		).when(
			job
		).getJobPropertiesFiles();

		return job;
	}

	private static File _newJobPropertiesFile(Properties jobProperties) {
		try {
			File jobPropertiesFile = File.createTempFile(
				"testray-descriptor-", ".properties");

			try (FileWriter fileWriter = new FileWriter(jobPropertiesFile)) {
				jobProperties.store(fileWriter, null);
			}

			return jobPropertiesFile;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

}