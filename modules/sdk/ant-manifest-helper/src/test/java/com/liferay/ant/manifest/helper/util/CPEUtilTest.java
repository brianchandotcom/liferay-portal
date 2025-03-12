/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.manifest.helper.util;

import com.liferay.ant.manifest.helper.ManifestHelperTask;
import com.liferay.portal.kernel.util.ReleaseInfo;

import org.apache.tools.ant.Project;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Istvan Sajtos
 * @author Drew Brokke
 */
public class CPEUtilTest extends CPEUtil {

	@After
	public void tearDown() {
		_releaseInfoMockedStatic.close();
	}

	@Test
	public void testGetName() {
		_testGetName(
			"cpe:2.3:a:liferay:dxp:2024.q4:0:*:*:*:*:*:*", "dxp", "7.4.13",
			"2024.Q4.0", ".u129");
		_testGetName(
			"cpe:2.3:a:liferay:dxp:2024.q4:6:*:*:*:*:*:*", "dxp", "7.4.13",
			"2024.Q4.6", ".u129");
		_testGetName(
			"cpe:2.3:a:liferay:dxp:2025.q1:0:*:*:*:*:*:*", "dxp", "7.4.13",
			"2025.Q1.0 LTS", ".u132");
		_testGetName(
			"cpe:2.3:a:liferay:portal:7.4.3.129-ga129:*:*:*:*:*:*:*", "portal",
			"7.4.3.129", "7.4.3.129 CE GA129", "-ga129");
	}

	private void _testGetName(
		String expectedValue, String product, String version,
		String versionDisplayName, String versionFileSuffix) {

		Project project = Mockito.mock(Project.class);

		Mockito.when(
			project.getProperty("release.info.version.file.suffix")
		).thenAnswer(
			invocation -> versionFileSuffix
		);

		_manifestHelperTask.setProject(project);

		_releaseInfoMockedStatic.when(
			ReleaseInfo::getVersion
		).thenAnswer(
			invocation -> version
		);

		_releaseInfoMockedStatic.when(
			ReleaseInfo::getVersionDisplayName
		).thenAnswer(
			invocation -> versionDisplayName
		);

		_releaseInfoMockedStatic.when(
			ReleaseInfo::isDXP
		).thenAnswer(
			invocation -> product.equals("dxp")
		);

		Assert.assertEquals(
			expectedValue, getName(_manifestHelperTask.getProject()));
	}

	private final ManifestHelperTask _manifestHelperTask =
		new ManifestHelperTask();
	private final MockedStatic<ReleaseInfo> _releaseInfoMockedStatic =
		Mockito.mockStatic(ReleaseInfo.class);

}