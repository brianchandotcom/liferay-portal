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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.SourceFormatBuild;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitAxisTestClassGroup;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class TestrayFactory {

	public static TestrayAttachment newTestrayAttachment(
		TestrayCaseResult testrayCaseResult, String name, String key) {

		return new DefaultTestrayAttachment(testrayCaseResult, name, key);
	}

	public static TestrayAttachmentRecorder newTestrayAttachmentRecorder(
		Build build) {

		TestrayAttachmentRecorder testrayAttachmentRecorder =
			_testrayAttachmentRecorders.get(build);

		if (testrayAttachmentRecorder != null) {
			return testrayAttachmentRecorder;
		}

		testrayAttachmentRecorder = new TestrayAttachmentRecorder(build);

		_testrayAttachmentRecorders.put(build, testrayAttachmentRecorder);

		return testrayAttachmentRecorder;
	}

	public static TestrayAttachmentUploader newTestrayAttachmentUploader(
		Build build, URL testrayServerURL,
		TestrayAttachmentUploader.Type type) {

		String testrayServerURLString = "";

		if (testrayServerURL != null) {
			testrayServerURLString = String.valueOf(testrayServerURL);
		}

		String key = JenkinsResultsParserUtil.combine(
			build.getBuildURL(), "_", testrayServerURLString, "_",
			type.toString());

		TestrayAttachmentUploader testrayAttachmentUploader =
			_testrayAttachmentUploaders.get(key);

		if (testrayAttachmentUploader != null) {
			return testrayAttachmentUploader;
		}

		if (type == TestrayAttachmentUploader.Type.RSYNC) {
			testrayAttachmentUploader = new RsyncTestrayAttachmentUploader(
				build, testrayServerURL);
		}
		else {
			testrayAttachmentUploader = new S3TestrayAttachmentUploader(
				build, testrayServerURL);
		}

		_testrayAttachmentUploaders.put(key, testrayAttachmentUploader);

		return testrayAttachmentUploader;
	}

	public static TestrayCaseResult newTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup, TestClass testClass) {

		if (testrayBuild == null) {
			throw new RuntimeException("Please set a Testray build");
		}

		if (topLevelBuild == null) {
			throw new RuntimeException("Please set a top level build");
		}

		if (axisTestClassGroup == null) {
			throw new RuntimeException("Please set an axis test class group");
		}

		if (testClass != null) {
			if (axisTestClassGroup instanceof FunctionalAxisTestClassGroup) {
				return new FunctionalBatchTestrayCaseResult(
					testrayBuild, topLevelBuild, axisTestClassGroup, testClass);
			}
			else if (axisTestClassGroup instanceof JUnitAxisTestClassGroup) {
				return new JUnitBatchTestrayCaseResult(
					testrayBuild, topLevelBuild, axisTestClassGroup, testClass);
			}
		}

		if (topLevelBuild instanceof SourceFormatBuild) {
			return new SFBatchTestrayCaseResult(
				testrayBuild, topLevelBuild, axisTestClassGroup);
		}

		return new BatchTestrayCaseResult(
			testrayBuild, topLevelBuild, axisTestClassGroup);
	}

	public static TestrayServer newTestrayServer(String testrayServerURL) {
		TestrayServer testrayServer = _testrayServers.get(testrayServerURL);

		if (testrayServer != null) {
			return testrayServer;
		}

		testrayServer = new DefaultTestrayServer(testrayServerURL);

		_testrayServers.put(testrayServerURL, testrayServer);

		return testrayServer;
	}

	private static final Map<Build, TestrayAttachmentRecorder>
		_testrayAttachmentRecorders = new HashMap<>();
	private static final Map<String, TestrayAttachmentUploader>
		_testrayAttachmentUploaders = new HashMap<>();
	private static final Map<String, TestrayServer> _testrayServers =
		new HashMap<>();

}