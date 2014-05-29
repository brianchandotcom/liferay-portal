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

package com.liferay.portal.service;

import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
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
public class RepositoryLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateLocalRepositoryFromInexistingFileEntryId()
		throws Exception {

		long fileEntryId = 42L;

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(0, fileEntryId, 0);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateLocalRepositoryFromInexistingFileVersionId()
		throws Exception {

		long fileVersionId = 42L;

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(0, 0, fileVersionId);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateLocalRepositoryFromInvalidFolderId()
		throws Exception {

		long folderId = 42L;

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(folderId, 0, 0);
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFileEntryId()
		throws Exception {

		DLFolder folder = DLTestUtil.addFolder(_group.getGroupId());

		DLFileEntry fileEntry = DLTestUtil.addFileEntry(folder);

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			0, fileEntry.getFileEntryId(), 0);
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFileVersionId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addFileEntry(dlFolder);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			0, 0, dlFileVersion.getFileVersionId());
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFolderId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			dlFolder.getFolderId(), 0, 0);
	}

	@Test
	public void testCreateLocalRepositoryFromExistingRepositoryId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			dlFolder.getRepositoryId());
	}

	@Test(expected = RepositoryException.class)
	public void testCreateLocalRepositoryFromInexistingRepositoryId()
		throws Exception {

		long repositoryId = 42L;

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(repositoryId);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateRepositoryFromInexistingFileEntryId()
		throws Exception {

		long fileEntryId = 42L;

		RepositoryLocalServiceUtil.getRepositoryImpl(0, fileEntryId, 0);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateRepositoryFromInexistingFileVersionId()
		throws Exception {

		long fileVersionId = 42L;

		RepositoryLocalServiceUtil.getRepositoryImpl(0, 0, fileVersionId);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateRepositoryFromInexistingFolderId() throws Exception {
		long folderId = 42L;

		RepositoryLocalServiceUtil.getRepositoryImpl(folderId, 0, 0);
	}

	@Test
	public void testCreateRepositoryFromExistingFileEntryId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addFileEntry(dlFolder);

		RepositoryLocalServiceUtil.getRepositoryImpl(
			0, dlFileEntry.getFileEntryId(), 0);
	}

	@Test
	public void testCreateRepositoryFromExistingFileVersionId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addFileEntry(dlFolder);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		RepositoryLocalServiceUtil.getRepositoryImpl(
			0, 0, dlFileVersion.getFileVersionId());
	}

	@Test
	public void testCreateRepositoryFromExistingFolderId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getRepositoryImpl(
			dlFolder.getFolderId(), 0, 0);
	}

	@Test
	public void testCreateRepositoryFromExistingRepositoryId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getRepositoryImpl(
			dlFolder.getRepositoryId());
	}

	@Test(expected = RepositoryException.class)
	public void testCreateRepositoryFromInexistingRepositoryId()
		throws Exception {

		long repositoryId = 42L;

		RepositoryLocalServiceUtil.getRepositoryImpl(repositoryId);
	}

	private Group _group;

}