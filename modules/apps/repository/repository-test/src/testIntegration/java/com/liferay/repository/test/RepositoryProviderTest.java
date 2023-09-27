/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.repository.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class RepositoryProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

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

		RepositoryProviderUtil.getFileEntryLocalRepository(
			dlFileEntry.getFileEntryId());
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFileVersionId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		RepositoryProviderUtil.getFileVersionLocalRepository(
			dlFileVersion.getFileVersionId());
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFolderId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryProviderUtil.getFolderLocalRepository(dlFolder.getFolderId());
	}

	@Test
	public void testCreateLocalRepositoryFromExistingRepositoryId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryProviderUtil.getLocalRepository(dlFolder.getRepositoryId());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testCreateLocalRepositoryFromNonexistentFileEntryId()
		throws Exception {

		long fileEntryId = RandomTestUtil.randomLong();

		RepositoryProviderUtil.getFileEntryLocalRepository(fileEntryId);
	}

	@Test(expected = NoSuchFileVersionException.class)
	public void testCreateLocalRepositoryFromNonexistentFileVersionId()
		throws Exception {

		long fileVersionId = RandomTestUtil.randomLong();

		RepositoryProviderUtil.getFileVersionLocalRepository(fileVersionId);
	}

	@Test(expected = NoSuchFolderException.class)
	public void testCreateLocalRepositoryFromNonexistentFolderId()
		throws Exception {

		long folderId = RandomTestUtil.randomLong();

		RepositoryProviderUtil.getFolderLocalRepository(folderId);
	}

	@Test(expected = RepositoryException.class)
	public void testCreateLocalRepositoryFromNonexistentRepositoryId()
		throws Exception {

		long repositoryId = RandomTestUtil.randomLong();

		RepositoryProviderUtil.getLocalRepository(repositoryId);
	}

	@Test
	public void testCreateRepositoryFromExistingFileEntryId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		RepositoryProviderUtil.getFileEntryLocalRepository(
			dlFileEntry.getFileEntryId());
	}

	@Test
	public void testCreateRepositoryFromExistingFileVersionId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		RepositoryProviderUtil.getFileVersionLocalRepository(
			dlFileVersion.getFileVersionId());
	}

	@Test
	public void testCreateRepositoryFromExistingFolderId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryProviderUtil.getFolderLocalRepository(dlFolder.getFolderId());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testCreateRepositoryFromExistingFolderWithoutPermissions()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				new SimplePermissionChecker() {

					@Override
					public boolean hasOwnerPermission(
						long companyId, String name, String primKey,
						long ownerId, String actionId) {

						return false;
					}

					@Override
					protected boolean hasPermission(String actionId) {
						return false;
					}

				};

			permissionChecker.init(originalPermissionChecker.getUser());

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			RepositoryProviderUtil.getFolderRepository(dlFolder.getFolderId());
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

		RepositoryProviderUtil.getRepository(dlFolder.getRepositoryId());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testCreateRepositoryFromNonexistentFileEntryId()
		throws Exception {

		long fileEntryId = RandomTestUtil.randomLong();

		RepositoryProviderUtil.getFileEntryRepository(fileEntryId);
	}

	@Test(expected = NoSuchFileVersionException.class)
	public void testCreateRepositoryFromNonexistentFileVersionId()
		throws Exception {

		long fileVersionId = RandomTestUtil.randomLong();

		RepositoryProviderUtil.getFileVersionRepository(fileVersionId);
	}

	@Test(expected = NoSuchFolderException.class)
	public void testCreateRepositoryFromNonexistentFolderId() throws Exception {
		long folderId = RandomTestUtil.randomLong();

		RepositoryProviderUtil.getFolderRepository(folderId);
	}

	@Test(expected = RepositoryException.class)
	public void testCreateRepositoryFromNonexistentRepositoryId()
		throws Exception {

		long repositoryId = RandomTestUtil.randomLong();

		RepositoryProviderUtil.getRepository(repositoryId);
	}

	@DeleteAfterTestRun
	private Group _group;

}