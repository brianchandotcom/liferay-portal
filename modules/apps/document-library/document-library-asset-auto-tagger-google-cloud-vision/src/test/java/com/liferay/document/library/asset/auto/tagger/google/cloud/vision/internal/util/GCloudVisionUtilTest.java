/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Tardín
 */
public class GCloudVisionUtilTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetAnnotateImagePayload() throws Exception {
		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			_fileVersion
		);

		String randomString = RandomTestUtil.randomString();

		Mockito.when(
			_fileVersion.getContentStream(false)
		).thenReturn(
			new ByteArrayInputStream(randomString.getBytes())
		);

		Assert.assertEquals(
			StringBundler.concat(
				"{\"requests\":[{\"features\":",
				"[{\"type\":\"LABEL_DETECTION\"}],\"image\":{\"content\":\"",
				Base64.encode(randomString.getBytes()), "\"}}]}"),
			GCloudVisionUtil.getAnnotateImagePayload(_fileEntry));
	}

	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);

}