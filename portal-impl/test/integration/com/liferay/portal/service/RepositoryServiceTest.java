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

import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.util.test.DLTestUtil;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class RepositoryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFileEntryId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		RepositoryServiceUtil.getLocalRepositoryImpl(
			0, dlFileEntry.getFileEntryId(), 0);
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFileVersionId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		RepositoryServiceUtil.getLocalRepositoryImpl(
			0, 0, dlFileVersion.getFileVersionId());
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFolderId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryServiceUtil.getLocalRepositoryImpl(
			dlFolder.getFolderId(), 0, 0);
	}

	@Test
	public void testCreateLocalRepositoryFromExistingRepositoryId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryServiceUtil.getLocalRepositoryImpl(
			dlFolder.getRepositoryId());
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateLocalRepositoryFromNonexistentFileEntryId()
		throws Exception {

		long fileEntryId = RandomTestUtil.randomLong();

		RepositoryServiceUtil.getLocalRepositoryImpl(0, fileEntryId, 0);
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateLocalRepositoryFromNonexistentFileVersionId()
		throws Exception {

		long fileVersionId = RandomTestUtil.randomLong();

		RepositoryServiceUtil.getLocalRepositoryImpl(0, 0, fileVersionId);
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateLocalRepositoryFromNonexistentFolderId()
		throws Exception {

		long folderId = RandomTestUtil.randomLong();

		RepositoryServiceUtil.getLocalRepositoryImpl(folderId, 0, 0);
	}

	@Test(expected = RepositoryException.class)
	public void testCreateLocalRepositoryFromNonexistentRepositoryId()
		throws Exception {

		long repositoryId = RandomTestUtil.randomLong();

		RepositoryServiceUtil.getLocalRepositoryImpl(repositoryId);
	}

	@Test
	public void testCreateRepositoryFromExistingFileEntryId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		RepositoryServiceUtil.getRepositoryImpl(
			0, dlFileEntry.getFileEntryId(), 0);
	}

	@Test
	public void testCreateRepositoryFromExistingFileVersionId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		RepositoryServiceUtil.getRepositoryImpl(
			0, 0, dlFileVersion.getFileVersionId());
	}

	@Test
	public void testCreateRepositoryFromExistingFolderId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryServiceUtil.getRepositoryImpl(dlFolder.getFolderId(), 0, 0);
	}

	@Test(expected = PrincipalException.class)
	public void testCreateRepositoryFromExistingFolderWithoutPermissions()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				new SimplePermissionChecker() {

					@Override
					public boolean hasOwnerPermission(
						long companyId, String name, String primKey,
						long ownerId, String actionId) {

						return false;
					}

					@Override
					public boolean hasPermission(
						long groupId, String name, String primKey,
						String actionId) {

						return false;
					}

					@Override
					public boolean hasUserPermission(
						long groupId, String name, String primKey,
						String actionId, boolean checkAdmin) {

						return false;
					}

				});

			RepositoryServiceUtil.getRepositoryImpl(
				dlFolder.getFolderId(), 0, 0);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testCreateRepositoryFromExistingRepositoryId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryServiceUtil.getRepositoryImpl(dlFolder.getRepositoryId());
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateRepositoryFromNonexistentFileEntryId()
		throws Exception {

		long fileEntryId = RandomTestUtil.randomLong();

		RepositoryServiceUtil.getRepositoryImpl(0, fileEntryId, 0);
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateRepositoryFromNonexistentFileVersionId()
		throws Exception {

		long fileVersionId = RandomTestUtil.randomLong();

		RepositoryServiceUtil.getRepositoryImpl(0, 0, fileVersionId);
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateRepositoryFromNonexistentFolderId() throws Exception {
		long folderId = RandomTestUtil.randomLong();

		RepositoryServiceUtil.getRepositoryImpl(folderId, 0, 0);
	}

	@Test(expected = RepositoryException.class)
	public void testCreateRepositoryFromNonexistentRepositoryId()
		throws Exception {

		long repositoryId = RandomTestUtil.randomLong();

		RepositoryServiceUtil.getRepositoryImpl(repositoryId);
	}

	@DeleteAfterTestRun
	private Group _group;

}