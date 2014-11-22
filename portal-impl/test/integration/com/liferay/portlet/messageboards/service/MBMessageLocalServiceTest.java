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

import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.text.DateFormat;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBMessageLocalServiceTest {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
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