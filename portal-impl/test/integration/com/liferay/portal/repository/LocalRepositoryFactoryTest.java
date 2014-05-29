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

package com.liferay.portal.repository;

import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.repository.LocalRepositoryFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.util.test.DLTestUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LocalRepositoryFactoryTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateRepositoryFromInexistingFileEntryId()
		throws Exception {

		long fileEntryId = 42L;

		LocalRepositoryFactoryUtil.create(0, fileEntryId, 0);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateRepositoryFromInexistingFileVersionId()
		throws Exception {

		long fileVersionId = 42L;

		LocalRepositoryFactoryUtil.create(0, 0, fileVersionId);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateRepositoryFromInexistingFolderId() throws Exception {
		long folderId = 42L;

		LocalRepositoryFactoryUtil.create(folderId, 0, 0);
	}

	@Test
	public void testCreateRepositoryFromExistingFileEntryId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addFileEntry(dlFolder);

		LocalRepositoryFactoryUtil.create(0, dlFileEntry.getFileEntryId(), 0);
	}

	@Test
	public void testCreateRepositoryFromExistingFileVersionId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addFileEntry(dlFolder);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		LocalRepositoryFactoryUtil.create(
			0, 0, dlFileVersion.getFileVersionId());
	}

	@Test
	public void testCreateRepositoryFromExistingFolderId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		LocalRepositoryFactoryUtil.create(dlFolder.getFolderId(), 0, 0);
	}

	@Test
	public void testCreateRepositoryFromExistingRepositoryId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		LocalRepositoryFactoryUtil.create(dlFolder.getRepositoryId());
	}

	@Test
	public void testCreateRepositoryFromInexistingRepositoryId()
		throws Exception {

		long repositoryId = RandomTestUtil.nextLong();

		LocalRepositoryFactoryUtil.create(repositoryId);
	}

	private Group _group;

}