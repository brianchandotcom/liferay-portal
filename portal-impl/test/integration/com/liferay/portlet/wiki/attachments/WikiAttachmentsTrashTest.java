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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiAttachmentsTrashTest {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(_group.getGroupId());

		_node = WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(), "",
			serviceContext);

		_page = WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			ServiceTestUtil.randomString(), null, WikiPageConstants.NEW, true,
			serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		if (_group != null) {
			GroupLocalServiceUtil.deleteGroup(_group);

			_group = null;
		}

		if (_page != null) {
			_page = null;
		}

		if (_node != null) {
			_node = null;
		}
	}

	@Test
	public void testTrashAndDelete() throws Exception {
		_trashWikiAttachments(false);
	}

	@Test
	public void testTrashAndRestore() throws Exception {
		_trashWikiAttachments(true);
	}

	private void _trashWikiAttachments(boolean restore) throws Exception {
		int initialNotInTrashCount = _page.getAttachmentsFileEntriesCount();
		int initialTrashEntriesCount =
			_page.getDeletedAttachmentsFileEntriesCount();

		Class<?> clazz = getClass();

		byte[] fileBytes = FileUtil.getBytes(
			clazz.getResourceAsStream("dependencies/OSX_Test.docx"));

		File file = null;

		if ((fileBytes != null) && (fileBytes.length > 0)) {
			file = FileUtil.createTempFile(fileBytes);
		}

		String fileName = ServiceTestUtil.randomString() + ".txt";

		WikiPageLocalServiceUtil.addPageAttachment(
			TestPropsValues.getUserId(), _node.getNodeId(), _page.getTitle(),
			fileName, file);

		Assert.assertEquals(
			initialNotInTrashCount + 1, _page.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount,
			_page.getDeletedAttachmentsFileEntriesCount());

		long fileEntryId = WikiPageLocalServiceUtil.movePageAttachmentToTrash(
			TestPropsValues.getUserId(), _page.getNodeId(), _page.getTitle(),
			fileName);

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			fileEntryId);

		Assert.assertEquals(
			initialNotInTrashCount, _page.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			_page.getDeletedAttachmentsFileEntriesCount());

		if (restore) {
			WikiPageLocalServiceUtil.restorePageAttachmentFromTrash(
				TestPropsValues.getUserId(), _page.getNodeId(),
				_page.getTitle(), fileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount + 1,
				_page.getAttachmentsFileEntriesCount());
			Assert.assertEquals(
				initialTrashEntriesCount,
				_page.getDeletedAttachmentsFileEntriesCount());

			WikiPageLocalServiceUtil.deletePageAttachment(
				_page.getNodeId(), _page.getTitle(), fileName);
		}
		else {
			WikiPageLocalServiceUtil.deletePageAttachment(
				_page.getNodeId(), _page.getTitle(), fileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount, _page.getAttachmentsFileEntriesCount());
			Assert.assertEquals(
				initialTrashEntriesCount,
				_page.getDeletedAttachmentsFileEntriesCount());
		}
	}

	private Group _group;
	private WikiNode _node;
	private WikiPage _page;

}