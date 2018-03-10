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
public class ValidationGitHubMessageTest extends BuildTest {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		downloadSample(
			"test-portal-acceptance-pullrequest(7.0.x-private)" +
				"_validation-compile-failure",
			"94", "test-portal-acceptance-pullrequest(7.0.x-private)",
			"test-1-5");
		downloadSample(
			"test-portal-acceptance-pullrequest(7.0.x-private)" +
				"_validation-no-unit",
			"70", "test-portal-acceptance-pullrequest(7.0.x-private)",
			"test-1-13");
		downloadSample(
			"test-portal-acceptance-pullrequest(7.0.x-private)" +
				"_validation-unit-failure",
			"78", "test-portal-acceptance-pullrequest(7.0.x-private)",
			"test-1-10");
	}

	@Test
	public void testExpectedValidationMessage() throws Exception {
		expectedMessageGenerator = new ExpectedMessageGenerator() {

			@Override
			public String getMessage(TestSample testSample) throws Exception {
				TopLevelBuild topLevelBuild =
					(TopLevelBuild)BuildFactory.newBuildFromArchive(
						testSample.getSampleDirName());

				topLevelBuild.setCompareToUpstream(false);

				return Dom4JUtil.format(
					topLevelBuild.getValidationGitHubMessageElement(), true);
			}

		};

		assertSamples();
	}

	protected File getExpectedMessageFile(TestSample testSample) {
		return new File(
			testSample.getSampleDir(), "expected-validation-message.html");
	}

}