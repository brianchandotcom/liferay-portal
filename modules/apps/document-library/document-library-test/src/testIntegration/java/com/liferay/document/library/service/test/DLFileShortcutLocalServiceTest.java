/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class DLFileShortcutLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@Test
	public void testAddFileShortcut() throws Exception {
		DLFileEntry dlFileEntry = _addDLFileEntry();

		DLFileShortcut dlFileShortcut =
			_dlFileShortcutLocalService.addFileShortcut(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				dlFileEntry.getFileEntryId(), _serviceContext);

		Assert.assertEquals(
			dlFileShortcut.getToFileEntryId(), dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			dlFileShortcut.getFolderId());
	}

	@Test
	public void testAddFileShortcutWithFolder() throws Exception {
		DLFileEntry dlFileEntry = _addDLFileEntry();

		DLFileShortcut dlFileShortcut =
			_dlFileShortcutLocalService.addFileShortcut(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_group.getGroupId(), dlFileEntry.getFolderId(),
				dlFileEntry.getFileEntryId(), _serviceContext);

		Assert.assertEquals(
			dlFileEntry.getFileEntryId(), dlFileShortcut.getToFileEntryId());

		Assert.assertEquals(
			dlFileEntry.getFolderId(), dlFileShortcut.getFolderId());
	}

	@Test(expected = NoSuchFolderException.class)
	public void testAddFileShortcutWithFolderFromOtherGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		DLFolder dlFolder = DLTestUtil.addDLFolder(group.getGroupId());

		DLFileEntry dlFileEntry = _addDLFileEntry();

		_dlFileShortcutLocalService.addFileShortcut(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), dlFolder.getFolderId(),
			dlFileEntry.getFileEntryId(), _serviceContext);
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testAddFileShortcutWithInvalidFileEntry() throws Exception {
		_dlFileShortcutLocalService.addFileShortcut(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, -1,
			_serviceContext);
	}

	@Test
	public void testAddFileShortcutWithInvalidFolder() throws Exception {
		DLFileEntry dlFileEntry = _addDLFileEntry();

		DLFileShortcut dlFileShortcut =
			_dlFileShortcutLocalService.addFileShortcut(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_group.getGroupId(), -1, dlFileEntry.getFileEntryId(),
				_serviceContext);

		Assert.assertEquals(
			dlFileShortcut.getToFileEntryId(), dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			dlFileShortcut.getFolderId());
	}

	private DLFileEntry _addDLFileEntry() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		return DLTestUtil.addDLFileEntry(dlFolder.getFolderId());
	}

	@Inject
	private DLFileShortcutLocalService _dlFileShortcutLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}