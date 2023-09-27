/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.storage;

import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Roberto Díaz
 */
public class AMStoreUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetFileVersionPath() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		Mockito.when(
			fileVersion.getFileEntryId()
		).thenReturn(
			3L
		);

		Mockito.when(
			fileVersion.getFileVersionId()
		).thenReturn(
			4L
		);

		Mockito.when(
			fileVersion.getGroupId()
		).thenReturn(
			1L
		);

		Mockito.when(
			fileVersion.getRepositoryId()
		).thenReturn(
			2L
		);

		String configurationUuid = RandomTestUtil.randomString();

		String fileVersionPath = AMStoreUtil.getFileVersionPath(
			fileVersion, configurationUuid);

		Assert.assertEquals(
			"adaptive/" + configurationUuid + "/1/2/3/4/", fileVersionPath);
	}

}