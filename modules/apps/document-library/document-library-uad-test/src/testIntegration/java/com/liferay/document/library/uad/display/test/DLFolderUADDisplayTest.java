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

package com.liferay.document.library.uad.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.uad.test.DLFileEntryUADTestUtil;
import com.liferay.document.library.uad.test.DLFolderUADTestUtil;
import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.test.util.BaseUADDisplayTestCase;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class DLFolderUADDisplayTest extends BaseUADDisplayTestCase<DLFolder> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetOnlyManuallyManagedFolders() throws Exception {
		MBMessage mbMessage = null;

		try {
			Assert.assertEquals(0, _uadDisplay.count(user.getUserId()));

			addBaseModel(user.getUserId());

			Assert.assertEquals(1, _uadDisplay.count(user.getUserId()));

			mbMessage = MBTestUtil.addMessage(
				_group.getGroupId(), user.getUserId(),
				RandomTestUtil.randomString(50),
				RandomTestUtil.randomString(50));

			mbMessage.addAttachmentsFolder();

			Assert.assertEquals(1, _uadDisplay.count(user.getUserId()));

			_mbMessageLocalService.getTempAttachmentNames(
				_group.getGroupId(), user.getUserId(),
				MBMessageConstants.TEMP_FOLDER_NAME);

			Assert.assertEquals(1, _uadDisplay.count(user.getUserId()));
		}
		finally {
			if (mbMessage != null) {
				_mbThreadLocalService.deleteThread(mbMessage.getThreadId());
			}

			Repository mbRepository = _repositoryLocalService.fetchRepository(
				_group.getGroupId(), MBConstants.SERVICE_NAME,
				MBConstants.SERVICE_NAME);

			if (mbRepository != null) {
				_dlFolderLocalService.deleteAllByRepository(
					mbRepository.getRepositoryId());

				_repositoryLocalService.deleteRepository(mbRepository);
			}

			Repository tempFilesRepository =
				_repositoryLocalService.fetchRepository(
					_group.getGroupId(), TempFileEntryUtil.class.getName(),
					null);

			if (tempFilesRepository != null) {
				_dlFolderLocalService.deleteAllByRepository(
					tempFilesRepository.getRepositoryId());

				_repositoryLocalService.deleteRepository(tempFilesRepository);
			}
		}
	}

	@Test
	public void testGetParentContainerId() throws Exception {
		assertParentContainerId(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		DLFolder dlFolder = _addFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertParentContainerId(dlFolder.getFolderId());
	}

	@Test
	public void testGetTopLevelContainer() throws Exception {
		DLFileEntry dlFileEntry = _addFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		// A file entry that is an immediate child of the given parent should
		// return null

		Assert.assertNull(
			_getTopLevelContainer(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, dlFileEntry));

		// A folder that is an immediate child of the given parent should return
		// itself

		DLFolder dlFolderA = _addFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			dlFolderA,
			_getTopLevelContainer(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, dlFolderA));

		DLFolder dlFolderAA = _addFolder(dlFolderA.getFolderId());

		Assert.assertEquals(
			dlFolderAA,
			_getTopLevelContainer(dlFolderA.getFolderId(), dlFolderAA));

		// A folder that is the parent should return null

		Assert.assertNull(
			_getTopLevelContainer(dlFolderA.getFolderId(), dlFolderA));

		// A file entry whose folder is an immediate child of the given parent
		// should return its own folder

		DLFileEntry dlFileEntryA = _addFileEntry(dlFolderA.getFolderId());

		Assert.assertEquals(
			dlFolderA,
			_getTopLevelContainer(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, dlFileEntryA));

		// A folder that is not a descendant of the given parent should return
		// null

		DLFolder dlFolderB = _addFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		DLFolder dlFolderBA = _addFolder(dlFolderB.getFolderId());

		Assert.assertNull(
			_getTopLevelContainer(dlFolderA.getFolderId(), dlFolderBA));

		// A file entry that is not a descendant of the given parent should
		// return null

		DLFileEntry dlFileEntryBA = _addFileEntry(dlFolderBA.getFolderId());

		Assert.assertNull(
			_getTopLevelContainer(dlFolderA.getFolderId(), dlFileEntryBA));

		// A folder that is a nonimmediate descendant of the given parent
		// should return its highest ancestor below the given parent

		DLFolder dlFolderBAA = _addFolder(dlFolderBA.getFolderId());

		DLFolder dlFolderBAAA = _addFolder(dlFolderBAA.getFolderId());

		Assert.assertEquals(
			dlFolderBA,
			_getTopLevelContainer(dlFolderB.getFolderId(), dlFolderBAAA));

		// A file entry that is a nonimmediate descendant of the given parent
		// should return its highest ancestor below the given parent

		DLFileEntry dlFileEntryBAAA = _addFileEntry(dlFolderBAAA.getFolderId());

		Assert.assertEquals(
			dlFolderB,
			_getTopLevelContainer(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, dlFileEntryBAAA));

		Assert.assertEquals(
			dlFolderBA,
			_getTopLevelContainer(dlFolderB.getFolderId(), dlFileEntryBAAA));
	}

	@Override
	protected DLFolder addBaseModel(long userId) throws Exception {
		return DLFolderUADTestUtil.addDLFolder(
			_dlAppLocalService, _dlFolderLocalService, userId,
			_group.getGroupId());
	}

	protected void assertParentContainerId(long dlFolderId) throws Exception {
		DLFolder dlFolder = DLFolderUADTestUtil.addDLFolder(
			_dlAppLocalService, _dlFolderLocalService,
			TestPropsValues.getUserId(), _group.getGroupId(), dlFolderId);

		Serializable parentContainerId = _uadDisplay.getParentContainerId(
			dlFolder);

		Assert.assertEquals(dlFolderId, (long)parentContainerId);
	}

	@Override
	protected UADDisplay<DLFolder> getUADDisplay() {
		return _uadDisplay;
	}

	private DLFileEntry _addFileEntry(long dlFolderId) throws Exception {
		return DLFileEntryUADTestUtil.addDLFileEntry(
			_dlAppLocalService, _dlFileEntryLocalService, dlFolderId,
			TestPropsValues.getUserId(), _group.getGroupId());
	}

	private DLFolder _addFolder(long parentFolderId) throws Exception {
		return DLFolderUADTestUtil.addDLFolder(
			_dlAppLocalService, _dlFolderLocalService,
			TestPropsValues.getUserId(), _group.getGroupId(), parentFolderId);
	}

	private DLFolder _getTopLevelContainer(
		Serializable dlFolderId, Object childObject) {

		return _uadDisplay.getTopLevelContainer(
			DLFolder.class, dlFolderId, childObject);
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLFolderLocalService _dlFolderLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MBMessageLocalService _mbMessageLocalService;

	@Inject
	private MBThreadLocalService _mbThreadLocalService;

	@Inject
	private RepositoryLocalService _repositoryLocalService;

	@Inject(filter = "component.name=*.DLFolderUADDisplay")
	private UADDisplay<DLFolder> _uadDisplay;

}