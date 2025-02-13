/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.manifest.helper;

import com.liferay.portal.kernel.util.ReleaseInfo;

import java.io.IOException;

import org.apache.tools.ant.Project;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Istvan Sajtos
 */
public class ManifestHelperTaskTest {

	@Before
	public void setUp() {
		_releaseInfoMockedStatic.when(
			ReleaseInfo::isDXP
		).thenAnswer(
			invocation -> _dxp
		);

		_releaseInfoMockedStatic.when(
			ReleaseInfo::getVersion
		).thenAnswer(
			invocation -> _version
		);

		_releaseInfoMockedStatic.when(
			ReleaseInfo::getVersionDisplayName
		).thenAnswer(
			invocation -> _versionDisplayName
		);

		Project project = Mockito.mock(Project.class);

		Mockito.when(
			project.getProperty("release.info.version.file.suffix")
		).thenAnswer(
			invocation -> _versionFileSuffix
		);

		_manifestHelperTask.setProject(project);
	}

	@After
	public void tearDown() {
		_releaseInfoMockedStatic.close();
	}

	@Test
	public void testGetCpeId() throws IOException {
		_setReleaseProperties(true, "7.4.13", "2024.Q4.0", ".u129");

		Assert.assertEquals(
			"cpe:2.3:a:liferay:dxp:2024.q4:0:*:*:*:*:*:*",
			_manifestHelperTask.getCpeId());

		_setReleaseProperties(true, "7.4.13", "2024.Q4.6", ".u129");

		Assert.assertEquals(
			"cpe:2.3:a:liferay:dxp:2024.q4:6:*:*:*:*:*:*",
			_manifestHelperTask.getCpeId());

		_setReleaseProperties(
			false, "7.4.3.129", "7.4.3.129 CE GA129", "-ga129");

		Assert.assertEquals(
			"cpe:2.3:a:liferay:portal:7.4.3.129-ga129:*:*:*:*:*:*:*",
			_manifestHelperTask.getCpeId());

		_setReleaseProperties(true, "7.4.13", "2025.Q1.0 LTS", ".u132");

		Assert.assertEquals(
			"cpe:2.3:a:liferay:dxp:2025.q1:0:*:*:*:*:*:*",
			_manifestHelperTask.getCpeId());
	}

	private void _setReleaseProperties(
		boolean dxp, String version, String versionDisplayName,
		String versionFileSuffix) {

		_dxp = dxp;
		_version = version;
		_versionDisplayName = versionDisplayName;
		_versionFileSuffix = versionFileSuffix;
	}

	private boolean _dxp;
	private final ManifestHelperTask _manifestHelperTask =
		new ManifestHelperTask();
	private final MockedStatic<ReleaseInfo> _releaseInfoMockedStatic =
		Mockito.mockStatic(ReleaseInfo.class);
	private String _version;
	private String _versionDisplayName;
	private String _versionFileSuffix;

}