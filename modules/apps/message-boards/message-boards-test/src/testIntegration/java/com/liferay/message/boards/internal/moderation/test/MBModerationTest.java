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

package com.liferay.message.boards.internal.moderation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class MBModerationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = TestPropsValues.getUser();
	}

	@Test
	public void testAddMessageWithModerationDisabled() throws Exception {
		MBTestUtil.addMessage(
			_group.getGroupId(), _user.getUserId(),
			RandomTestUtil.randomString(50), RandomTestUtil.randomString(50));

		int groupMessagesCount = _mbMessageLocalService.getGroupMessagesCount(
			_group.getGroupId(), _user.getUserId(),
			WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(1, groupMessagesCount);
	}

	@Test
	public void testAddMessageWithModerationEnabledAndEnoughPublishedMessages()
		throws Exception {

		MBTestUtil.addMessage(
			_group.getGroupId(), _user.getUserId(),
			RandomTestUtil.randomString(50), RandomTestUtil.randomString(50));

		_withModerationEnabled(
			() -> {
				MBTestUtil.addMessage(
					_group.getGroupId(), _user.getUserId(),
					RandomTestUtil.randomString(50),
					RandomTestUtil.randomString(50));

				int groupMessagesCount =
					_mbMessageLocalService.getGroupMessagesCount(
						_group.getGroupId(), _user.getUserId(),
						WorkflowConstants.STATUS_APPROVED);

				Assert.assertEquals(2, groupMessagesCount);
			});
	}

	@Test
	public void testAddMessageWithModerationEnabledAndNotEnoughPublishedMessages()
		throws Exception {

		_withModerationEnabled(
			() -> {
				MBTestUtil.addMessage(
					_group.getGroupId(), _user.getUserId(),
					RandomTestUtil.randomString(50),
					RandomTestUtil.randomString(50));

				int groupMessagesCount =
					_mbMessageLocalService.getGroupMessagesCount(
						_group.getGroupId(), _user.getUserId(),
						WorkflowConstants.STATUS_APPROVED);

				Assert.assertEquals(0, groupMessagesCount);
			});
	}

	private void _withModerationEnabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", _group.getCompanyId()
			).put(
				"enableMessageBoardsModeration", true
			).put(
				"minimumContributedMessages", 1
			).build();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.message.boards.moderation.configuration." +
						"MBModerationGroupConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MBMessageLocalService _mbMessageLocalService;

	private User _user;

}