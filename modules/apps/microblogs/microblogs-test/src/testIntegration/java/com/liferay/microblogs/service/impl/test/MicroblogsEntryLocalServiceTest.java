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

package com.liferay.microblogs.service.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.model.MicroblogsEntryConstants;
import com.liferay.microblogs.service.MicroblogsEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.social.kernel.service.SocialActivityLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian I. Kim
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class MicroblogsEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user1 = UserTestUtil.addGroupAdminUser(_group);
		_user2 = UserTestUtil.addGroupAdminUser(_group);
		_user3 = UserTestUtil.addGroupAdminUser(_group);
	}

	@Test
	public void testDeleteParentMicroblogsEntryWithReplyAndRepostDeletesMicroblogsEntry()
		throws Exception {

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user3.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		MicroblogsEntry repostMicroblogsEntry1 = addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPOST,
			parentMicroblogsEntry.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			repostMicroblogsEntry1.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user3.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			repostMicroblogsEntry1.getMicroblogsEntryId());

		Stream<MicroblogsEntry> stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNotNull(
				_microblogsEntryLocalService.fetchMicroblogsEntry(
					microblogsEntry.getMicroblogsEntryId())));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			parentMicroblogsEntry);

		stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNull(
				_microblogsEntryLocalService.fetchMicroblogsEntry(
					microblogsEntry.getMicroblogsEntryId())));
	}

	@Test
	public void testDeleteParentMicroblogsEntryWithReplyAndRepostDeletesMicroblogsEntryAssetEntry()
		throws Exception {

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user3.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		MicroblogsEntry repostMicroblogsEntry1 = addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPOST,
			parentMicroblogsEntry.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			repostMicroblogsEntry1.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user3.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			repostMicroblogsEntry1.getMicroblogsEntryId());

		Stream<MicroblogsEntry> stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNotNull(
				_assetEntryLocalService.fetchEntry(
					MicroblogsEntry.class.getName(),
					microblogsEntry.getPrimaryKey())));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			parentMicroblogsEntry);

		stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNull(
				_assetEntryLocalService.fetchEntry(
					MicroblogsEntry.class.getName(),
					microblogsEntry.getPrimaryKey())));
	}

	@Test
	public void testDeleteParentMicroblogsEntryWithReplyAndRepostDeletesMicroblogsEntrySocialActivities()
		throws Exception {

		int initialCount = _socialActivityLocalService.getActivitiesCount(
			MicroblogsEntry.class.getName());

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user3.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		MicroblogsEntry repostMicroblogsEntry1 = addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPOST,
			parentMicroblogsEntry.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			repostMicroblogsEntry1.getMicroblogsEntryId());

		addMicroblogsEntry(
			_user3.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			repostMicroblogsEntry1.getMicroblogsEntryId());

		Assert.assertEquals(
			initialCount + 11,
			_socialActivityLocalService.getActivitiesCount(
				MicroblogsEntry.class.getName()));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			parentMicroblogsEntry);

		Assert.assertEquals(
			initialCount,
			_socialActivityLocalService.getActivitiesCount(
				MicroblogsEntry.class.getName()));
	}

	@Test
	public void testDeleteParentMicroblogsEntryWithReplyDeletesMicroblogsEntry()
		throws Exception {

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		Stream<MicroblogsEntry> stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNotNull(
				_microblogsEntryLocalService.fetchMicroblogsEntry(
					microblogsEntry.getMicroblogsEntryId())));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			parentMicroblogsEntry);

		stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNull(
				_microblogsEntryLocalService.fetchMicroblogsEntry(
					microblogsEntry.getMicroblogsEntryId())));
	}

	@Test
	public void testDeleteParentMicroblogsEntryWithReplyDeletesMicroblogsEntryAssetEntry()
		throws Exception {

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		Stream<MicroblogsEntry> stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNotNull(
				_assetEntryLocalService.fetchEntry(
					MicroblogsEntry.class.getName(),
					microblogsEntry.getPrimaryKey())));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			parentMicroblogsEntry);

		stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNull(
				_assetEntryLocalService.fetchEntry(
					MicroblogsEntry.class.getName(),
					microblogsEntry.getPrimaryKey())));
	}

	@Test
	public void testDeleteParentMicroblogsEntryWithReplyDeletesMicroblogsEntrySocialActivities()
		throws Exception {

		int initialCount = _socialActivityLocalService.getActivitiesCount(
			MicroblogsEntry.class.getName());

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		Assert.assertEquals(
			initialCount + 3,
			_socialActivityLocalService.getActivitiesCount(
				MicroblogsEntry.class.getName()));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			parentMicroblogsEntry);

		Assert.assertEquals(
			initialCount,
			_socialActivityLocalService.getActivitiesCount(
				MicroblogsEntry.class.getName()));
	}

	@Test
	public void testDeleteParentMicroblogsEntryWithRepostDeletesMicroblogsEntry()
		throws Exception {

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPOST,
			parentMicroblogsEntry.getMicroblogsEntryId());

		Stream<MicroblogsEntry> stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNotNull(
				_microblogsEntryLocalService.fetchMicroblogsEntry(
					microblogsEntry.getMicroblogsEntryId())));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			parentMicroblogsEntry);

		stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNull(
				_microblogsEntryLocalService.fetchMicroblogsEntry(
					microblogsEntry.getMicroblogsEntryId())));
	}

	@Test
	public void testDeleteParentMicroblogsEntryWithRepostDeletesMicroblogsEntryAssetEntry()
		throws Exception {

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPOST,
			parentMicroblogsEntry.getMicroblogsEntryId());

		Stream<MicroblogsEntry> stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNotNull(
				_assetEntryLocalService.fetchEntry(
					MicroblogsEntry.class.getName(),
					microblogsEntry.getPrimaryKey())));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			parentMicroblogsEntry);

		stream = _microblogsEntries.stream();

		stream.forEach(
			microblogsEntry -> Assert.assertNull(
				_assetEntryLocalService.fetchEntry(
					MicroblogsEntry.class.getName(),
					microblogsEntry.getPrimaryKey())));
	}

	@Test
	public void testDeleteParentMicroblogsEntryWithRepostDeletesMicroblogsEntrySocialActivities()
		throws Exception {

		int initialCount = _socialActivityLocalService.getActivitiesCount(
			MicroblogsEntry.class.getName());

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPOST,
			parentMicroblogsEntry.getMicroblogsEntryId());

		Assert.assertEquals(
			initialCount + 3,
			_socialActivityLocalService.getActivitiesCount(
				MicroblogsEntry.class.getName()));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			parentMicroblogsEntry);

		Assert.assertEquals(
			initialCount,
			_socialActivityLocalService.getActivitiesCount(
				MicroblogsEntry.class.getName()));
	}

	@Test
	public void testDeleteReplyMicroblogsEntryDoesNotDeleteParentMicroblogsEntry()
		throws Exception {

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		MicroblogsEntry microblogsEntryReply = addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		Assert.assertNotNull(
			_microblogsEntryLocalService.fetchMicroblogsEntry(
				parentMicroblogsEntry.getMicroblogsEntryId()));

		Assert.assertNotNull(
			_microblogsEntryLocalService.fetchMicroblogsEntry(
				microblogsEntryReply.getMicroblogsEntryId()));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			microblogsEntryReply);

		Assert.assertNotNull(
			_microblogsEntryLocalService.fetchMicroblogsEntry(
				parentMicroblogsEntry.getMicroblogsEntryId()));

		Assert.assertNull(
			_microblogsEntryLocalService.fetchMicroblogsEntry(
				microblogsEntryReply.getMicroblogsEntryId()));
	}

	@Test
	public void testDeleteReplyMicroblogsEntryDoesNotDeleteParentMicroblogsEntryAssetEntry()
		throws Exception {

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		MicroblogsEntry microblogsEntryReply = addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		Assert.assertNotNull(
			_assetEntryLocalService.fetchEntry(
				MicroblogsEntry.class.getName(),
				parentMicroblogsEntry.getPrimaryKey()));

		Assert.assertNotNull(
			_assetEntryLocalService.fetchEntry(
				MicroblogsEntry.class.getName(),
				microblogsEntryReply.getPrimaryKey()));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			microblogsEntryReply);

		Assert.assertNotNull(
			_assetEntryLocalService.fetchEntry(
				MicroblogsEntry.class.getName(),
				parentMicroblogsEntry.getPrimaryKey()));

		Assert.assertNull(
			_assetEntryLocalService.fetchEntry(
				MicroblogsEntry.class.getName(),
				microblogsEntryReply.getPrimaryKey()));
	}

	@Test
	public void testDeleteReplyMicroblogsEntryDoesNotDeleteParentMicroblogsEntrySocialActivities()
		throws Exception {

		int initialCount = _socialActivityLocalService.getActivitiesCount(
			MicroblogsEntry.class.getName());

		MicroblogsEntry parentMicroblogsEntry = addMicroblogsEntry(
			_user1.getUserId(), MicroblogsEntryConstants.TYPE_EVERYONE, 0);

		MicroblogsEntry microblogsEntryReply = addMicroblogsEntry(
			_user2.getUserId(), MicroblogsEntryConstants.TYPE_REPLY,
			parentMicroblogsEntry.getMicroblogsEntryId());

		Assert.assertEquals(
			initialCount + 3,
			_socialActivityLocalService.getActivitiesCount(
				MicroblogsEntry.class.getName()));

		_microblogsEntryLocalService.deleteMicroblogsEntry(
			microblogsEntryReply);

		Assert.assertEquals(
			initialCount + 1,
			_socialActivityLocalService.getActivitiesCount(
				MicroblogsEntry.class.getName()));
	}

	protected MicroblogsEntry addMicroblogsEntry(
			long userId, int type, long parentMicroblogsEntryId)
		throws Exception {

		String content = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, userId);

		MicroblogsEntry microblogsEntry =
			_microblogsEntryLocalService.addMicroblogsEntry(
				userId, content, type, parentMicroblogsEntryId, 0,
				serviceContext);

		_microblogsEntries.add(microblogsEntry);

		return microblogsEntry;
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private final List<MicroblogsEntry> _microblogsEntries = new ArrayList<>();

	@Inject
	private MicroblogsEntryLocalService _microblogsEntryLocalService;

	@Inject
	private SocialActivityLocalService _socialActivityLocalService;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

	@DeleteAfterTestRun
	private User _user3;

}