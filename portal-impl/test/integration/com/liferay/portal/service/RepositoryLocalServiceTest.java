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
	public void testCreatingALocalRepositoryOnAFakeFileEntryFails()
		throws Exception {

		long fileEntryId = 42L;

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(0, fileEntryId, 0);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreatingALocalRepositoryOnAFakeFileVersionFails()
		throws Exception {

		long fileVersionId = 42L;

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(0, 0, fileVersionId);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreatingALocalRepositoryOnAFakeFolderFails()
		throws Exception {

		long folderId = 42L;

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(folderId, 0, 0);
	}

	@Test
	public void testCreatingALocalRepositoryOnAnExistingFileEntrySucceeds()
		throws Exception {

		DLFolder folder = DLTestUtil.addFolder(_group.getGroupId());
		DLFileEntry fileEntry = DLTestUtil.addFileEntry(folder);

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			0, fileEntry.getFileEntryId(), 0);
	}

	@Test
	public void testCreatingALocalRepositoryOnAnExistingFileVersionSucceeds()
		throws Exception {

		DLFolder folder = DLTestUtil.addFolder(_group.getGroupId());
		DLFileEntry fileEntry = DLTestUtil.addFileEntry(folder);
		DLFileVersion fileVersion = fileEntry.getLatestFileVersion(true);

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			0, 0, fileVersion.getFileVersionId());
	}

	@Test
	public void testCreatingALocalRepositoryOnAnExistingFolderSucceeds()
		throws Exception {

		DLFolder folder = DLTestUtil.addFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			folder.getFolderId(), 0, 0);
	}

	@Test
	public void testCreatingALocalRepositoryOnAnExistingRepositorySucceeds()
		throws Exception {

		DLFolder folder = DLTestUtil.addFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			folder.getRepositoryId());
	}

	@Test(expected = RepositoryException.class)
	public void testCreatingALocalRepositoryThatDoesntExistFails()
		throws Exception {

		long repositoryId = 42L;

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(repositoryId);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreatingARepositoryOnAFakeFileEntryFails()
		throws Exception {

		long fileEntryId = 42L;

		RepositoryLocalServiceUtil.getRepositoryImpl(0, fileEntryId, 0);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreatingARepositoryOnAFakeFileVersionFails()
		throws Exception {

		long fileVersionId = 42L;

		RepositoryLocalServiceUtil.getRepositoryImpl(0, 0, fileVersionId);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreatingARepositoryOnAFakeFolderFails() throws Exception {
		long folderId = 42L;

		RepositoryLocalServiceUtil.getRepositoryImpl(folderId, 0, 0);
	}

	@Test
	public void testCreatingARepositoryOnAnExistingFileEntrySucceeds()
		throws Exception {

		DLFolder folder = DLTestUtil.addFolder(_group.getGroupId());
		DLFileEntry fileEntry = DLTestUtil.addFileEntry(folder);

		RepositoryLocalServiceUtil.getRepositoryImpl(
			0, fileEntry.getFileEntryId(), 0);
	}

	@Test
	public void testCreatingARepositoryOnAnExistingFileVersionSucceeds()
		throws Exception {

		DLFolder folder = DLTestUtil.addFolder(_group.getGroupId());
		DLFileEntry fileEntry = DLTestUtil.addFileEntry(folder);
		DLFileVersion fileVersion = fileEntry.getLatestFileVersion(true);

		RepositoryLocalServiceUtil.getRepositoryImpl(
			0, 0, fileVersion.getFileVersionId());
	}

	@Test
	public void testCreatingARepositoryOnAnExistingFolderSucceeds()
		throws Exception {

		DLFolder folder = DLTestUtil.addFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getRepositoryImpl(
			folder.getFolderId(), 0, 0);
	}

	@Test
	public void testCreatingARepositoryOnAnExistingRepositorySucceeds()
		throws Exception {

		DLFolder folder = DLTestUtil.addFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getRepositoryImpl(folder.getRepositoryId());
	}

	@Test(expected = RepositoryException.class)
	public void testCreatingARepositoryThatDoesntExistFails() throws Exception {
		long repositoryId = 42L;

		RepositoryLocalServiceUtil.getRepositoryImpl(repositoryId);
	}

	private Group _group;

}