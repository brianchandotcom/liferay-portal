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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.InputStream;

import java.text.DateFormat;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jonathan McCann
 * @author Sergio González
 */
public class MBMessageLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteAttachmentsWhenUpdatingMessageAndTrashDisabled()
		throws Exception {

		TrashUtil.disableTrash(_group);

		User user = TestPropsValues.getUser();
		List<ObjectValuePair<String, InputStream>> objectValuePairs =
			MBTestUtil.getInputStreamOVPs(
				"attachment.txt", getClass(), StringPool.BLANK);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			user.getUserId(), user.getFullName(), _group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			MBMessageConstants.DEFAULT_FORMAT, objectValuePairs, false, 0,
			false, serviceContext);

		MBMessageLocalServiceUtil.updateMessage(
			user.getUserId(), message.getMessageId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			Collections.<ObjectValuePair<String, InputStream>>emptyList(),
			Collections.<String>emptyList(), 0, false, serviceContext);

		Assert.assertEquals(
			0,
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				message.getGroupId(), message.getAttachmentsFolderId()));

		MBTestUtil.addMessage(_group.getGroupId());
	}

	@Test
	public void testDeleteAttachmentsWhenUpdatingMessageAndTrashEnabled()
		throws Exception {

		User user = TestPropsValues.getUser();
		List<ObjectValuePair<String, InputStream>> objectValuePairs =
			MBTestUtil.getInputStreamOVPs(
				"attachment.txt", getClass(), StringPool.BLANK);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			user.getUserId(), user.getFullName(), _group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			MBMessageConstants.DEFAULT_FORMAT, objectValuePairs, false, 0,
			false, serviceContext);

		MBMessageLocalServiceUtil.updateMessage(
			user.getUserId(), message.getMessageId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			Collections.<ObjectValuePair<String, InputStream>>emptyList(),
			Collections.<String>emptyList(), 0, false, serviceContext);

		List<FileEntry> fileEntries =
			PortletFileRepositoryUtil.getPortletFileEntries(
				message.getGroupId(), message.getAttachmentsFolderId());

		Assert.assertEquals(1, fileEntries.size());

		FileEntry fileEntry = fileEntries.get(0);

		DLFileEntry dlFileEntry = ((DLFileEntry)fileEntry.getModel());

		Assert.assertEquals(
			WorkflowConstants.STATUS_IN_TRASH, dlFileEntry.getStatus());
	}

	@Test
	public void testGetNoAssetMessages() throws Exception {
		MBTestUtil.addMessage(_group.getGroupId());

		MBMessage message = MBTestUtil.addMessage(_group.getGroupId());

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			MBMessage.class.getName(), message.getMessageId());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<MBMessage> messages =
			MBMessageLocalServiceUtil.getNoAssetMessages();

		Assert.assertEquals(1, messages.size());
		Assert.assertEquals(message, messages.get(0));
	}

	@Test
	public void testThreadLastPostDate() throws Exception {
		MBMessage parentMessage = MBTestUtil.addMessage(_group.getGroupId());

		Thread.sleep(2000);

		MBMessage firstReplyMessage = MBTestUtil.addMessage(
			_group.getGroupId(), parentMessage.getCategoryId(),
			parentMessage.getThreadId(), parentMessage.getMessageId());

		Thread.sleep(2000);

		MBMessage secondReplyMessage = MBTestUtil.addMessage(
			_group.getGroupId(), parentMessage.getCategoryId(),
			parentMessage.getThreadId(), parentMessage.getMessageId());

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			PropsValues.INDEX_DATE_FORMAT_PATTERN);

		MBThread mbThread = parentMessage.getThread();

		Assert.assertEquals(
			dateFormat.format(mbThread.getLastPostDate()),
			dateFormat.format(secondReplyMessage.getModifiedDate()));

		MBMessageLocalServiceUtil.deleteMessage(
			secondReplyMessage.getMessageId());

		mbThread = parentMessage.getThread();

		Assert.assertEquals(
			dateFormat.format(mbThread.getLastPostDate()),
			dateFormat.format(firstReplyMessage.getModifiedDate()));
	}

	@DeleteAfterTestRun
	private Group _group;

}