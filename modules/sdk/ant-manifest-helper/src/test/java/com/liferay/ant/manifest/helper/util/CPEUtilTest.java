/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.manifest.helper.util;

import com.liferay.ant.manifest.helper.ManifestHelperTask;
import com.liferay.portal.kernel.util.ReleaseInfo;

import java.util.Arrays;

import org.apache.tools.ant.Project;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Istvan Sajtos
 * @author Drew Brokke
 */
@RunWith(Parameterized.class)
public class CPEUtilTest extends CPEUtil {

	@Parameterized.Parameters(name = "{1}, {2}, {3}, {4}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(
			new Object[] {
				"cpe:2.3:a:liferay:dxp:2024.q4:0:*:*:*:*:*:*", "dxp", "7.4.13",
				"2024.Q4.0", ".u129"
			},
			new Object[] {
				"cpe:2.3:a:liferay:dxp:2024.q4:6:*:*:*:*:*:*", "dxp", "7.4.13",
				"2024.Q4.6", ".u129"
			},
			new Object[] {
				"cpe:2.3:a:liferay:portal:7.4.3.129-ga129:*:*:*:*:*:*:*",
				"portal", "7.4.3.129", "7.4.3.129 CE GA129", "-ga129"
			},
			new Object[] {
				"cpe:2.3:a:liferay:dxp:2025.q1:0:*:*:*:*:*:*", "dxp", "7.4.13",
				"2025.Q1.0 LTS", ".u132"
			});
	}

	public CPEUtilTest(
		String expectedValue, String product, String version,
		String versionDisplayName, String versionFileSuffix) {

		_expectedValue = expectedValue;
		_product = product;
		_version = version;
		_versionDisplayName = versionDisplayName;
		_versionFileSuffix = versionFileSuffix;
	}

	@Before
	public void setUp() {
		_releaseInfoMockedStatic.when(
			ReleaseInfo::isDXP
		).thenAnswer(
			invocation -> _product.equals("dxp")
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
	public void testGetName() {
		Assert.assertEquals(
			_expectedValue, getName(_manifestHelperTask.getProject()));
	}

	private final String _expectedValue;
	private final ManifestHelperTask _manifestHelperTask =
		new ManifestHelperTask();
	private final String _product;
	private final MockedStatic<ReleaseInfo> _releaseInfoMockedStatic =
		Mockito.mockStatic(ReleaseInfo.class);
	private final String _version;
	private final String _versionDisplayName;
	private final String _versionFileSuffix;

}