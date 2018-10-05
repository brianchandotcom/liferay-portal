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

package com.liferay.portal.osgi.web.portlet.container.upload.servlet.request.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.UploadServletRequestImpl;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class UploadServletRequestWhenGettingTempDirTest {

	@ClassRule
	@Rule
	public static final TestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldNotReturnPreferencesValueWhenModified()
		throws IOException {

		File tempDir = UploadServletRequestImpl.getTempDir();

		try {
			TemporaryFolder temporaryFolder = new TemporaryFolder();

			temporaryFolder.create();

			File newTempDir = temporaryFolder.getRoot();

			UploadServletRequestImpl.setTempDir(newTempDir);

			File currentTempDir = UploadServletRequestImpl.getTempDir();

			Assert.assertEquals(temporaryFolder.getRoot(), currentTempDir);
		}
		finally {
			UploadServletRequestImpl.setTempDir(tempDir);
		}
	}

	@Test
	public void testShouldReturnPreferencesValue() {
		File tempDir = UploadServletRequestImpl.getTempDir();

		File expectedTempDir = new File(
			UploadServletRequestConfigurationHelperUtil.getTempDir());

		Assert.assertEquals(expectedTempDir, tempDir);
	}

}