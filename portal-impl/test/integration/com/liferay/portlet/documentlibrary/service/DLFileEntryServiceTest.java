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

import com.liferay.portal.kernel.exception.PortalException;
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
		group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCopyFileEntryWithExtension() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		String sourceFileName = RandomTestUtil.randomString().concat(".pdf");
		String fileEntryTitle = RandomTestUtil.randomString();

		DLFileEntry originalDLFileEntry =
			DLFileEntryLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), group.getGroupId(),
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				sourceFileName, null, fileEntryTitle,
				RandomTestUtil.randomString(), StringPool.BLANK,
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
				null, null, new ByteArrayInputStream(CONTENT.getBytes()), 0,
				serviceContext);

		DLFolder destFolder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), group.getGroupId(), group.getGroupId(),
			false, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntryServiceUtil.copyFileEntry(
			group.getGroupId(), group.getGroupId(),
			originalDLFileEntry.getFileEntryId(), destFolder.getFolderId(),
			serviceContext);
	}

	@Test
	public void testCopyFileEntryWithoutExtension() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		String sourceFileName = RandomTestUtil.randomString();
		String fileEntryTitle = RandomTestUtil.randomString();

		DLFileEntry originalDLFileEntry =
			DLFileEntryLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), group.getGroupId(),
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				sourceFileName, null, fileEntryTitle,
				RandomTestUtil.randomString(), StringPool.BLANK,
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
				null, null, new ByteArrayInputStream(CONTENT.getBytes()), 0,
				serviceContext);

		DLFolder destFolder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), group.getGroupId(), group.getGroupId(),
			false, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntryServiceUtil.copyFileEntry(
			group.getGroupId(), group.getGroupId(),
			originalDLFileEntry.getFileEntryId(), destFolder.getFolderId(),
			serviceContext);
	}

	protected static final String CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	@DeleteAfterTestRun
	protected Group group;

}