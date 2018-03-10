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

package com.liferay.jenkins.results.parser;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class GitHubMessageTest extends BuildTest {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		downloadSample(
			"test-jenkins-acceptance-pullrequest_passed", "117",
			"test-jenkins-acceptance-pullrequest", "test-1-17");
		downloadSample(
			"test-plugins-acceptance-pullrequest(ee-6.2.x)_passed", "66",
			"test-plugins-acceptance-pullrequest(ee-6.2.x)", "test-1-8");
		downloadSample(
			"test-portal-acceptance-pullrequest(7.0.x)_unresolved-req-failure",
			"103", "test-portal-acceptance-pullrequest(7.0.x)", "test-1-14");
		downloadSample(
			"test-portal-acceptance-pullrequest(ee-6.2.x)_passed", "337",
			"test-portal-acceptance-pullrequest(ee-6.2.x)", "test-1-17");
		downloadSample(
			"test-portal-acceptance-pullrequest(master)_generic-failure",
			"1375", "test-portal-acceptance-pullrequest(master)", "test-1-1");
		downloadSample(
			"test-portal-acceptance-pullrequest(master)" +
				"_modules-compile-failure",
			"999", "test-portal-acceptance-pullrequest(master)", "test-1-21");
		downloadSample(
			"test-portal-acceptance-pullrequest(master)_passed", "446",
			"test-portal-acceptance-pullrequest(master)", "test-1-8");
		downloadSample(
			"test-portal-acceptance-pullrequest(master)_poshi-test-failure",
			"1268", "test-portal-acceptance-pullrequest(master)", "test-1-9");
		downloadSample(
			"test-portal-acceptance-pullrequest(master)" +
				"_semantic_versioning_failure",
			"2003", "test-portal-acceptance-pullrequest(master)", "test-1-3");
		downloadSample(
			"test-portal-acceptance-pullrequest(master)_source-format-failure",
			"2209", "test-portal-acceptance-pullrequest(master)", "test-1-2");
	}

	@Test
	public void testExpectedGitHubMessage() throws Exception {
		expectedMessageGenerator = new ExpectedMessageGenerator() {

			@Override
			public String getMessage(TestSample testSample) throws Exception {
				Build build = BuildFactory.newBuildFromArchive(
					testSample.getSampleDirName());

				build.setCompareToUpstream(false);

				return Dom4JUtil.format(build.getGitHubMessageElement(), true);
			}

		};

		assertSamples();
	}

	@Override
	protected File getExpectedMessageFile(TestSample testSample) {
		return new File(
			testSample.getSampleDir(), "expected-github-message.html");
	}

}