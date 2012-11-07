/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.attachments;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
			EnvironmentExecutionTestListener.class,
			TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiAttachmentsTrashTest {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();
	}

	@Transactional
	public void testTrashAndDelete() throws Exception {
		trashWikiAttachments(false);
	}

	@Transactional
	public void testTrashAndRestore() throws Exception {
		trashWikiAttachments(true);
	}

	private void trashWikiAttachments(boolean restore) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(_group.getGroupId());

		WikiNode wikiNode = WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(), "",
			serviceContext);

		WikiPage wikiPage = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(), wikiNode.getNodeId(),
			ServiceTestUtil.randomString(), null, WikiPageConstants.NEW, true,
			serviceContext);

		int initialNotInTrashCount = wikiPage.getAttachmentsFilesCount();

		int initialTrashEntriesCount =
			wikiPage.getDeletedAttachmentsFilesCount();

		Class<?> clazz = getClass();

		byte[] fileBytes = FileUtil.getBytes(
			clazz.getResourceAsStream("dependencies/OSX_Test.docx"));

		File file = null;

		if ((fileBytes != null) && (fileBytes.length > 0)) {
			file = FileUtil.createTempFile(fileBytes);
		}

		String fileName = ServiceTestUtil.randomString() + ".txt";

		WikiPageLocalServiceUtil.addPageAttachment(
			TestPropsValues.getUserId(), wikiNode.getNodeId(),
			wikiPage.getTitle(), fileName, file);

		Assert.assertEquals(
			initialNotInTrashCount + 1, wikiPage.getAttachmentsFilesCount());

		Assert.assertEquals(
			initialTrashEntriesCount,
			wikiPage.getDeletedAttachmentsFilesCount());

		long fileEntryId = WikiPageLocalServiceUtil.movePageAttachmentToTrash(
			TestPropsValues.getUserId(), wikiPage.getNodeId(),
			wikiPage.getTitle(), fileName);

		DLFileEntry dlFileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			fileEntryId);

		Assert.assertEquals(
			initialNotInTrashCount, wikiPage.getAttachmentsFilesCount());

		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			wikiPage.getDeletedAttachmentsFilesCount());

		if (restore) {
			WikiPageLocalServiceUtil.restorePageAttachmentFromTrash(
				TestPropsValues.getUserId(), wikiPage.getNodeId(),
				wikiPage.getTitle(), dlFileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount + 1,
				wikiPage.getAttachmentsFilesCount());

			Assert.assertEquals(
				initialTrashEntriesCount,
				wikiPage.getDeletedAttachmentsFilesCount());
		}
		else {
			WikiPageLocalServiceUtil.deletePageAttachment(
				wikiPage.getNodeId(), wikiPage.getTitle(),
				dlFileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount, wikiPage.getAttachmentsFilesCount());

			Assert.assertEquals(
				initialTrashEntriesCount,
				wikiPage.getDeletedAttachmentsFilesCount());
		}
	}

	private Group _group;

}