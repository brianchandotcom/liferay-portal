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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLFileEntryServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCopyFileEntryWithExtensionInFolderToFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFolder folder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntry dlFileEntry = addDLFileEntry(folder.getFolderId(), true);

		DLFolder destFolder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(), destFolder.getFolderId(),
			serviceContext);
	}

	@Test
	public void testCopyFileEntryWithExtensionInFolderToRootFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFolder folder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntry dlFileEntry = addDLFileEntry(folder.getFolderId(), true);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Test
	public void testCopyFileEntryWithExtensionInRootFolderToFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntry dlFileEntry = addDLFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, true);

		DLFolder destFolder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(), destFolder.getFolderId(),
			serviceContext);
	}

	@Test
	public void testCopyFileEntryWithoutExtensionInFolderToFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFolder folder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntry dlFileEntry = addDLFileEntry(folder.getFolderId(), false);

		DLFolder destFolder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(), destFolder.getFolderId(),
			serviceContext);
	}

	@Test
	public void testCopyFileEntryWithoutExtensionInFolderToRootFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFolder folder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntry dlFileEntry = addDLFileEntry(folder.getFolderId(), false);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Test
	public void testCopyFileEntryWithoutExtensionInRootFolderToFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntry dlFileEntry = addDLFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, false);

		DLFolder destFolder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(), destFolder.getFolderId(),
			serviceContext);
	}

	protected DLFileEntry addDLFileEntry(long folderId, boolean appendExtension)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String sourceFileName = RandomTestUtil.randomString();

		if (appendExtension) {
			sourceFileName.concat(".pdf");
		}

		String fileEntryTitle = RandomTestUtil.randomString();

		return  DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), folderId, sourceFileName, null, fileEntryTitle,
			RandomTestUtil.randomString(), StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(_CONTENT.getBytes()), 0,
			serviceContext);
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	@DeleteAfterTestRun
	private Group _group;

}