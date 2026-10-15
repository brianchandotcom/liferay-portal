/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.DownstreamBuildReport;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.TopLevelBuildReport;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.Collections;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Michael Hashimoto
 */
public class BatchBuildTestrayCaseResultTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetName() {
		mockEnvironment(
			Collections.singletonMap(
				"WORKSPACE", RandomTestUtil.randomString()));

		AxisTestClassGroup axisTestClassGroup = Mockito.mock(
			AxisTestClassGroup.class);

		String axisName = RandomTestUtil.randomString();

		Mockito.doReturn(
			axisName
		).when(
			axisTestClassGroup
		).getAxisName();

		DownstreamBuildReport downstreamBuildReport = Mockito.mock(
			DownstreamBuildReport.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			downstreamBuildReport
		).getAxisName();

		TopLevelBuildReport topLevelBuildReport = Mockito.mock(
			TopLevelBuildReport.class);

		Mockito.doReturn(
			downstreamBuildReport
		).when(
			topLevelBuildReport
		).getDownstreamBuildReport(
			Mockito.any()
		);

		BatchBuildTestrayCaseResult<?, ?> batchBuildTestrayCaseResult =
			new BatchBuildTestrayCaseResult<>(
				axisTestClassGroup, Mockito.mock(TestrayBuild.class),
				topLevelBuildReport);

		testEquals(axisName, batchBuildTestrayCaseResult.getName());
	}

}