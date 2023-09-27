/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upload.internal;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;

import org.apache.commons.fileupload.FileItem;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Manuel de la Peña
 */
public class LiferayFileItemFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testConstructor() throws Exception {
		LiferayFileItemFactory liferayFileItemFactory =
			new LiferayFileItemFactory(temporaryFolder.getRoot(), 0, null);

		Assert.assertNotNull(liferayFileItemFactory);
	}

	@Test
	public void testCreateItem() throws Exception {
		LiferayFileItemFactory liferayFileItemFactory =
			new LiferayFileItemFactory(temporaryFolder.getRoot(), 0, null);

		FileItem fileItem = liferayFileItemFactory.createItem(
			"fieldName", "contentType", false, "fileName");

		Assert.assertNotNull(fileItem);
	}

	@Test
	public void testCreateItemWithInvalidTempDir() throws Exception {
		LiferayFileItemFactory liferayFileItemFactory =
			new LiferayFileItemFactory(new File("file://foo"), 0, null);

		FileItem fileItem = liferayFileItemFactory.createItem(
			"fieldName", "contentType", false, "fileName");

		Assert.assertNotNull(fileItem);
	}

	@Test
	public void testCreateItemWithNullTempDir() throws Exception {
		LiferayFileItemFactory liferayFileItemFactory =
			new LiferayFileItemFactory(null, 0, null);

		FileItem fileItem = liferayFileItemFactory.createItem(
			"fieldName", "contentType", false, "fileName");

		Assert.assertNotNull(fileItem);
	}

	@Test
	public void testCreateItemWithNullValues() throws Exception {
		LiferayFileItemFactory liferayFileItemFactory =
			new LiferayFileItemFactory(temporaryFolder.getRoot(), 0, null);

		FileItem fileItem = liferayFileItemFactory.createItem(
			null, null, false, null);

		Assert.assertNotNull(fileItem);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

}