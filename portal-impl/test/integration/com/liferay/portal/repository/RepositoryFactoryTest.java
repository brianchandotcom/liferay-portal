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
import com.liferay.portal.kernel.repository.RepositoryFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.AdvancedPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.ResetDatabaseExecutionTestListener;
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
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		ResetDatabaseExecutionTestListener.class,
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class RepositoryFactoryTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCreateRepositoryFromExistingFileEntryId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addFileEntry(
			dlFolder.getFolderId());

		RepositoryFactoryUtil.create(0, dlFileEntry.getFileEntryId(), 0);
	}

	@Test
	public void testCreateRepositoryFromExistingFileVersionId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addFileEntry(
			dlFolder.getFolderId());

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		RepositoryFactoryUtil.create(0, 0, dlFileVersion.getFileVersionId());
	}

	@Test
	public void testCreateRepositoryFromExistingFolderId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryFactoryUtil.create(dlFolder.getFolderId(), 0, 0);
	}

	@Test(expected = PrincipalException.class)
	public void testCreateRepositoryFromExistingFolderWithoutPermissions()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				ALWAYS_FAILING_PERMISSION_CHECKER);

			RepositoryFactoryUtil.create(dlFolder.getFolderId(), 0, 0);
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

		RepositoryFactoryUtil.create(dlFolder.getRepositoryId());
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateRepositoryFromInexistingFileEntryId()
		throws Exception {

		long fileEntryId = 42L;

		RepositoryFactoryUtil.create(0, fileEntryId, 0);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateRepositoryFromInexistingFileVersionId()
		throws Exception {

		long fileVersionId = 42L;

		RepositoryFactoryUtil.create(0, 0, fileVersionId);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testCreateRepositoryFromInexistingFolderId() throws Exception {
		long folderId = 42L;

		RepositoryFactoryUtil.create(folderId, 0, 0);
	}

	@Test
	public void testCreateRepositoryFromInexistingRepositoryId()
		throws Exception {

		long repositoryId = 42L;

		RepositoryFactoryUtil.create(repositoryId);
	}

	private static final PermissionChecker ALWAYS_FAILING_PERMISSION_CHECKER =
		new AdvancedPermissionChecker() {

			@Override
			public boolean hasOwnerPermission(
				long companyId, String name, String primKey, long ownerId,
				String actionId) {

				return false;
			}

			@Override
			public boolean hasUserPermission(
				long groupId, String name, String primKey, String actionId,
				boolean checkAdmin) {

				return false;
			}

			@Override
			public boolean hasPermission(
				long groupId, String name, String primKey, String actionId) {

				return false;
			}

		};

	private Group _group;

}