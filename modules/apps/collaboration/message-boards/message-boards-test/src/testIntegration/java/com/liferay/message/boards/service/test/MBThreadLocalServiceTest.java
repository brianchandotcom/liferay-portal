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

package com.liferay.message.boards.service.test;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageConstants;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.kernel.service.MBThreadLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.io.InputStream;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class MBThreadLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAttachmentsWhenSplittingThread() throws Exception {
		MBMessage rootMessage = addMessage(null, true);

		MBMessage splitMessage = addMessage(rootMessage, true);

		MBMessage childMessage = addMessage(splitMessage, true);

		Assert.assertEquals(
			rootMessage.getThreadId(), splitMessage.getThreadId());

		Assert.assertEquals(1, rootMessage.getAttachmentsFileEntriesCount());
		Assert.assertEquals(1, splitMessage.getAttachmentsFileEntriesCount());
		Assert.assertEquals(1, childMessage.getAttachmentsFileEntriesCount());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBThreadLocalServiceUtil.splitThread(
			TestPropsValues.getUserId(), splitMessage.getMessageId(),
			RandomTestUtil.randomString(), serviceContext);

		rootMessage = MBMessageLocalServiceUtil.getMBMessage(
			rootMessage.getMessageId());

		splitMessage = MBMessageLocalServiceUtil.getMBMessage(
			splitMessage.getMessageId());

		childMessage = MBMessageLocalServiceUtil.getMBMessage(
			childMessage.getMessageId());

		Assert.assertNotEquals(
			rootMessage.getThreadId(), splitMessage.getThreadId());
		Assert.assertEquals(1, rootMessage.getAttachmentsFileEntriesCount());
		Assert.assertEquals(1, splitMessage.getAttachmentsFileEntriesCount());
		Assert.assertEquals(1, childMessage.getAttachmentsFileEntriesCount());
	}

	@Test
	public void testGetNoAssetThreads() throws Exception {
		addMessage(null, false);

		MBMessage message = addMessage(null, false);

		MBThread thread = message.getThread();

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			MBThread.class.getName(), thread.getThreadId());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<MBThread> threads = MBThreadLocalServiceUtil.getNoAssetThreads();

		Assert.assertEquals(threads.toString(), 1, threads.size());
		Assert.assertEquals(thread, threads.get(0));
	}

	protected MBMessage addMessage(
			MBMessage parentMessage, boolean addAttachments)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
		long threadId = 0;
		long parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;

		if (parentMessage != null) {
			categoryId = parentMessage.getCategoryId();
			threadId = parentMessage.getThreadId();
			parentMessageId = parentMessage.getMessageId();
		}

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		if (addAttachments) {
			inputStreamOVPs = MBTestUtil.getInputStreamOVPs(
				"attachment.txt", getClass(), StringPool.BLANK);
		}

		return MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_group.getGroupId(), categoryId, threadId, parentMessageId,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false, 0.0,
			false, serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

}