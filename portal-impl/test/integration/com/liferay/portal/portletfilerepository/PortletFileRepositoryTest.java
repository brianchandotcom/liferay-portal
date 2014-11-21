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

package com.liferay.portal.portletfilerepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Adolfo Pérez
 */
@RunWith(Enclosed.class)
public class PortletFileRepositoryTest {

	@ExecutionTestListeners(
		listeners = { MainServletExecutionTestListener.class }
	)
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenAddingAFileEntry {

		@Test
		public void shouldCreateApprovedFileEntry() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			FileEntry fileEntry = _addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, dlFileEntry.getStatus());
		}

		@Test(expected = DuplicateFileException.class)
		public void shouldFailIfDuplicateName() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			String name = RandomTestUtil.randomString();

			_addPortletFileEntry(portletId, folder.getFolderId(), name);

			_addPortletFileEntry(portletId, folder.getFolderId(), name);
		}

		@Test
		public void shouldHaveDefaultVersion() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			FileEntry fileEntry = _addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			Assert.assertEquals(
				fileEntry.getVersion(), DLFileEntryConstants.VERSION_DEFAULT);
		}

		@Test
		public void shouldSucceedIfUniqueName() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			_addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			_addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), folder.getFolderId());

			Assert.assertEquals(count, 2);
		}

		@Test
		public void shouldSucceedOnEmptyFolder() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			_addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), folder.getFolderId());

			Assert.assertEquals(count, 1);
		}

	}

	@ExecutionTestListeners(
		listeners = { MainServletExecutionTestListener.class }
	)
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenAddingAFolder {

		@Test(expected = DuplicateFileException.class)
		public void shouldFailIfDuplicateName() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			String name = RandomTestUtil.randomString();

			_addPortletFileEntry(portletId, folder.getFolderId(), name);

			_addPortletFileEntry(portletId, folder.getFolderId(), name);
		}

		@Test
		public void shouldSucceedIfUniqueName() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			_addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			_addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), folder.getFolderId());

			Assert.assertEquals(count, 2);
		}

		@Test
		public void shouldSucceedOnEmptyFolder() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			_addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), folder.getFolderId());

			Assert.assertEquals(count, 1);
		}

	}

	private static FileEntry _addPortletFileEntry(
			String portletId, long folderId, String name)
		throws PortalException {

		return PortletFileRepositoryUtil.addPortletFileEntry(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
			User.class.getName(), TestPropsValues.getUserId(), portletId,
			folderId, RandomTestUtil.randomInputStream(), name,
			ContentTypes.APPLICATION_OCTET_STREAM, false);
	}

	private static Folder _addPortletFolder(String portletId)
		throws PortalException {

		return PortletFileRepositoryUtil.addPortletFolder(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
			portletId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

}