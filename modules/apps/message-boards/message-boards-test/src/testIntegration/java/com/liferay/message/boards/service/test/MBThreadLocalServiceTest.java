/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.service.MBThreadLocalServiceUtil;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@RunWith(Arquillian.class)
public class MBThreadLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddThreadTitleWhenAddingRootMessage() throws Exception {
		MBMessage rootMessage = _addMessage(null);

		MBThread thread = MBThreadLocalServiceUtil.getThread(
			rootMessage.getThreadId());

		Assert.assertEquals(rootMessage.getSubject(), thread.getTitle());

		MBMessage childMessage = _addMessage(rootMessage);

		Assert.assertNotEquals(childMessage.getSubject(), thread.getTitle());
	}

	@Test
	public void testAttachmentsWhenSplittingThread() throws Exception {
		MBMessage rootMessage = _addMessage(null);

		MBMessage splitMessage = _addMessage(rootMessage);

		MBMessage childMessage = _addMessage(splitMessage);

		Assert.assertEquals(
			rootMessage.getThreadId(), splitMessage.getThreadId());

		Assert.assertEquals(1, rootMessage.getAttachmentsFileEntriesCount());
		Assert.assertEquals(1, splitMessage.getAttachmentsFileEntriesCount());
		Assert.assertEquals(1, childMessage.getAttachmentsFileEntriesCount());

		MBThreadLocalServiceUtil.splitThread(
			TestPropsValues.getUserId(), splitMessage.getMessageId(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

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
	public void testDeleteThreadWithExpandoMessages() throws Exception {
		int expandoCount = ExpandoValueLocalServiceUtil.getExpandoValuesCount();

		String expandoName = StringUtil.randomString();
		String expandoValue = StringUtil.randomString();

		MBMessage message = _addMessageWithExpando(expandoName, expandoValue);

		ExpandoBridge expandoBridge = message.getExpandoBridge();

		String attributeValue = GetterUtil.getString(
			expandoBridge.getAttribute(expandoName));

		Assert.assertEquals(expandoValue, attributeValue);

		Assert.assertEquals(
			expandoCount + 1,
			ExpandoValueLocalServiceUtil.getExpandoValuesCount());

		MBThreadLocalServiceUtil.deleteThread(message.getThreadId());

		Assert.assertEquals(
			expandoCount, ExpandoValueLocalServiceUtil.getExpandoValuesCount());

		ExpandoTableLocalServiceUtil.deleteTables(
			PortalUtil.getDefaultCompanyId(), MBMessage.class.getName());
	}

	@Test
	public void testNotUpdateThreadTitleWhenUpdatingChildMessage()
		throws Exception {

		MBMessage rootMessage = _addMessage(null);

		MBMessage childMessage = _addMessage(rootMessage);

		MBThread thread = MBThreadLocalServiceUtil.getThread(
			rootMessage.getThreadId());

		Assert.assertEquals(rootMessage.getSubject(), thread.getTitle());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		String newSubject = RandomTestUtil.randomString();

		MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), childMessage.getMessageId(),
			newSubject, RandomTestUtil.randomString(), inputStreamOVPs, 0.0,
			false, serviceContext);

		thread = MBThreadLocalServiceUtil.getThread(rootMessage.getThreadId());

		Assert.assertEquals(rootMessage.getSubject(), thread.getTitle());
		Assert.assertNotEquals(newSubject, thread.getTitle());
	}

	@Test
	public void testUpdateThreadTitleWhenSplittingMessage() throws Exception {
		MBMessage rootMessage = _addMessage(null);

		MBMessage splitMessage = _addMessage(rootMessage);

		MBThread thread = MBThreadLocalServiceUtil.getThread(
			rootMessage.getThreadId());

		Assert.assertEquals(rootMessage.getSubject(), thread.getTitle());

		Assert.assertNotEquals(splitMessage.getSubject(), thread.getTitle());

		MBThreadLocalServiceUtil.splitThread(
			TestPropsValues.getUserId(), splitMessage.getMessageId(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		rootMessage = MBMessageLocalServiceUtil.getMBMessage(
			rootMessage.getMessageId());

		thread = MBThreadLocalServiceUtil.getThread(rootMessage.getThreadId());

		Assert.assertEquals(rootMessage.getSubject(), thread.getTitle());

		splitMessage = MBMessageLocalServiceUtil.getMBMessage(
			splitMessage.getMessageId());

		MBThread splitThread = MBThreadLocalServiceUtil.getThread(
			splitMessage.getThreadId());

		Assert.assertEquals(splitMessage.getSubject(), splitThread.getTitle());
	}

	@Test
	public void testUpdateThreadTitleWhenUpdatingRootMessage()
		throws Exception {

		MBMessage rootMessage = _addMessage(null);

		MBThread thread = MBThreadLocalServiceUtil.getThread(
			rootMessage.getThreadId());

		Assert.assertEquals(rootMessage.getSubject(), thread.getTitle());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		String newSubject = RandomTestUtil.randomString();

		MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), rootMessage.getMessageId(), newSubject,
			RandomTestUtil.randomString(), inputStreamOVPs, 0.0, false,
			serviceContext);

		thread = MBThreadLocalServiceUtil.getThread(rootMessage.getThreadId());

		Assert.assertEquals(newSubject, thread.getTitle());
	}

	private MBMessage _addMessage(MBMessage parentMessage) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		long categoryId = BeanPropertiesUtil.getLong(
			parentMessage, "categoryId");
		long threadId = BeanPropertiesUtil.getLong(parentMessage, "threadId");
		long parentMessageId = BeanPropertiesUtil.getLong(
			parentMessage, "messageId");

		return MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_group.getGroupId(), categoryId, threadId, parentMessageId,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			MBMessageConstants.DEFAULT_FORMAT,
			MBTestUtil.getInputStreamOVPs(
				"attachment.txt", getClass(), StringPool.BLANK),
			false, 0.0, false, serviceContext);
	}

	private MBMessage _addMessageWithExpando(String name, String value)
		throws Exception {

		ExpandoTable expandoTable =
			ExpandoTableLocalServiceUtil.addDefaultTable(
				PortalUtil.getDefaultCompanyId(), MBMessage.class.getName());

		ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), name, ExpandoColumnConstants.STRING,
			StringPool.BLANK);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setExpandoBridgeAttributes(
			HashMapBuilder.<String, Serializable>put(
				name, value
			).build());

		return MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			0L, MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			MBMessageConstants.DEFAULT_FORMAT, null, false, 0.0, false,
			serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

}