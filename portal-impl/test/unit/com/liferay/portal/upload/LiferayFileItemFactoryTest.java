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

package com.liferay.portal.upload;

import java.io.File;

import org.apache.commons.fileupload.FileItem;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Manuel de la Peña
 */
public class LiferayFileItemFactoryTest {

	@Test
	public void testConstructor() throws Exception {
		LiferayFileItemFactory liferayFileItemFactory =
			new LiferayFileItemFactory(temporaryFolder.getRoot());

		Assert.assertNotNull(liferayFileItemFactory);
	}

	@Test
	public void testCreateItem() throws Exception {
		LiferayFileItemFactory liferayFileItemFactory =
			new LiferayFileItemFactory(temporaryFolder.getRoot());

		FileItem fileItem = liferayFileItemFactory.createItem(
			"fieldName", "contentType", false, "fileName");

		Assert.assertNotNull(fileItem);
	}

	@Test
	public void testCreateItemFromInvalidTempDir() throws Exception {
		LiferayFileItemFactory liferayFileItemFactory =
			new LiferayFileItemFactory(new File("file://foo"));

		FileItem fileItem = liferayFileItemFactory.createItem(
			"fieldName", "contentType", false, "fileName");

		Assert.assertNotNull(fileItem);
	}

	@Test
	public void testCreateItemFromNullTempDir() throws Exception {
		LiferayFileItemFactory liferayFileItemFactory =
			new LiferayFileItemFactory(null);

		FileItem fileItem = liferayFileItemFactory.createItem(
			"fieldName", "contentType", false, "fileName");

		Assert.assertNotNull(fileItem);
	}

	@Test
	public void testCreateItemWithNullValues() throws Exception {
		LiferayFileItemFactory liferayFileItemFactory =
			new LiferayFileItemFactory(temporaryFolder.getRoot());

		FileItem fileItem = liferayFileItemFactory.createItem(
			null, null, false, null);

		Assert.assertNotNull(fileItem);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

}