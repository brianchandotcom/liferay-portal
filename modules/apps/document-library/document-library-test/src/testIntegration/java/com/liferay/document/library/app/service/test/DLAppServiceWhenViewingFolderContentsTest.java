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

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import jodd.util.MimeTypes;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLAppServiceWhenViewingFolderContentsTest
	extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldCountDraftsIfOwner() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		DLAppServiceUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		DLAppServiceUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, serviceContext);

		int foldersAndFileEntriesAndFileShortcutsCount =
			DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
				group.getGroupId(), parentFolder.getFolderId(),
				WorkflowConstants.STATUS_APPROVED, false);

		Assert.assertEquals(2, foldersAndFileEntriesAndFileShortcutsCount);
	}

	@Test
	public void testShouldNotCountDraftsIfNotOwner() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		DLAppServiceUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		DLAppServiceUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, serviceContext);

		User user = UserTestUtil.addGroupUser(group, "User");

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user)) {

			int foldersAndFileEntriesAndFileShortcutsCount =
				DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
					group.getGroupId(), parentFolder.getFolderId(),
					WorkflowConstants.STATUS_APPROVED, false);

			Assert.assertEquals(1, foldersAndFileEntriesAndFileShortcutsCount);
		}
		finally {
			UserLocalServiceUtil.deleteUser(user.getUserId());
		}
	}

	@Test
	public void testShouldNotReturnDraftsIfNotOwner() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		DLAppServiceUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		DLAppServiceUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, serviceContext);

		User user = UserTestUtil.addGroupUser(group, "User");

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user)) {

			List<Object> foldersAndFileEntriesAndFileShortcuts =
				DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
					group.getGroupId(), parentFolder.getFolderId(),
					WorkflowConstants.STATUS_APPROVED, false, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			Assert.assertEquals(
				foldersAndFileEntriesAndFileShortcuts.toString(), 1,
				foldersAndFileEntriesAndFileShortcuts.size());
		}
		finally {
			UserLocalServiceUtil.deleteUser(user.getUserId());
		}
	}

	@Test
	public void testShouldReturnDraftsIfOwner() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		DLAppServiceUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		DLAppServiceUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, serviceContext);

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				group.getGroupId(), parentFolder.getFolderId(),
				WorkflowConstants.STATUS_APPROVED, false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			foldersAndFileEntriesAndFileShortcuts.toString(), 2,
			foldersAndFileEntriesAndFileShortcuts.size());
	}

}