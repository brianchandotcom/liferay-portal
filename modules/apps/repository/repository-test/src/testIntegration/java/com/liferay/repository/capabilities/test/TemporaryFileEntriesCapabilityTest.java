/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.repository.capabilities.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Iván Zaera
 */
@RunWith(Arquillian.class)
public class TemporaryFileEntriesCapabilityTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testDeleteExpiredTemporaryFileEntries() throws Exception {
		FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), _user.getUserId(),
			RandomTestUtil.randomString(), "image.jpg", getInputStream(),
			ContentTypes.IMAGE_JPEG);

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntry.getFileEntryId());

		dlFileEntry.setCreateDate(
			new Date(System.currentTimeMillis() - Time.WEEK));

		DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			_group.getGroupId(), TempFileEntryUtil.class.getName(),
			TempFileEntryUtil.class.getName());

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(
				repository.getRepositoryId());

		int foldersAndFileEntriesAndFileShortcutsCount =
			getFoldersAndFileEntriesAndFileShortcutsCount(localRepository);

		Assert.assertTrue(foldersAndFileEntriesAndFileShortcutsCount > 0);

		TemporaryFileEntriesCapability temporaryFileEntriesCapability =
			localRepository.getCapability(TemporaryFileEntriesCapability.class);

		temporaryFileEntriesCapability.setTemporaryFileEntriesTimeout(Time.DAY);

		temporaryFileEntriesCapability.deleteExpiredTemporaryFileEntries();

		Assert.assertEquals(
			0, getFoldersAndFileEntriesAndFileShortcutsCount(localRepository));
	}

	protected int getFoldersAndFileEntriesAndFileShortcutsCount(
			LocalRepository localRepository)
		throws Exception {

		int foldersAndFileEntriesAndFileShortcutsCount =
			localRepository.getFoldersCount(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, true);

		foldersAndFileEntriesAndFileShortcutsCount +=
			localRepository.getFileEntriesAndFileShortcutsCount(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY);

		return foldersAndFileEntriesAndFileShortcutsCount;
	}

	protected InputStream getInputStream() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/portal/util/dependencies/test.jpg");
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}