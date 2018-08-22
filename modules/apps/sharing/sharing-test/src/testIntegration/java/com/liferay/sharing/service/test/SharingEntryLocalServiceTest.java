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

package com.liferay.sharing.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.exception.InvalidSharingEntryActionKeyException;
import com.liferay.sharing.exception.InvalidSharingEntryUserException;
import com.liferay.sharing.exception.NoSuchEntryException;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class SharingEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_fromUser = UserTestUtil.addUser();
		_toUser = UserTestUtil.addUser();
		_user = UserTestUtil.addUser();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testAddSharingEntry() throws Exception {
		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertEquals(_group.getCompanyId(), sharingEntry.getCompanyId());
		Assert.assertEquals(_group.getGroupId(), sharingEntry.getGroupId());
		Assert.assertEquals(
			_fromUser.getUserId(), sharingEntry.getFromUserId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
		Assert.assertEquals(classNameId, sharingEntry.getClassNameId());
		Assert.assertEquals(classPK, sharingEntry.getClassPK());
	}

	@Test
	public void testAddSharingEntryActionIds() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertEquals(1, sharingEntry.getActionIds());

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW, SharingEntryActionKey.UPDATE),
			serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW,
				SharingEntryActionKey.ADD_DISCUSSION),
			serviceContext);

		Assert.assertEquals(5, sharingEntry.getActionIds());

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.ADD_DISCUSSION,
				SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertEquals(5, sharingEntry.getActionIds());

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW, SharingEntryActionKey.UPDATE,
				SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW, SharingEntryActionKey.UPDATE,
				SharingEntryActionKey.ADD_DISCUSSION,
				SharingEntryActionKey.UPDATE),
			serviceContext);

		Assert.assertEquals(7, sharingEntry.getActionIds());
	}

	@Test(expected = InvalidSharingEntryActionKeyException.class)
	public void testAddSharingEntryWithEmptySharingEntryActionKeys()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), Collections.emptyList(), serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionKeyException.class)
	public void testAddSharingEntryWithoutViewSharingEntryActionKey()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.UPDATE),
			serviceContext);
	}

	@Test(expected = InvalidSharingEntryUserException.class)
	public void testAddSharingEntryWithSameFromUserAndToUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _fromUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionKeyException.class)
	public void testAddSharingEntryWithSharingEntryActionKeysContainingOneNullElement()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		List<SharingEntryActionKey> sharingEntryActionKeys = new ArrayList<>();

		sharingEntryActionKeys.add(SharingEntryActionKey.VIEW);
		sharingEntryActionKeys.add(null);

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), sharingEntryActionKeys, serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionKeyException.class)
	public void testAddSharingEntryWithSharingEntryActionKeysContainingOnlyNullElement()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		List<SharingEntryActionKey> sharingEntryActionKeys = new ArrayList<>();

		sharingEntryActionKeys.add(null);

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), sharingEntryActionKeys, serviceContext);
	}

	@Test
	public void testDeleteGroupSharingEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getGroupSharingEntries(
				_group.getGroupId());

		Assert.assertEquals(
			sharingEntries.toString(), 3, sharingEntries.size());

		_sharingEntryLocalService.deleteGroupSharingEntries(
			_group.getGroupId());

		sharingEntries = _sharingEntryLocalService.getGroupSharingEntries(
			_group.getGroupId());

		Assert.assertEquals(
			sharingEntries.toString(), 0, sharingEntries.size());
	}

	@Test
	public void testDeleteGroupSharingEntriesDoesNotDeleteOtherGroupSharingEntries()
		throws Exception {

		Group group2 = GroupTestUtil.addGroup();

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			_sharingEntryLocalService.addSharingEntry(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
				_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
				serviceContext);

			_sharingEntryLocalService.addSharingEntry(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
				group2.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
				serviceContext);

			List<SharingEntry> groupSharingEntries =
				_sharingEntryLocalService.getGroupSharingEntries(
					_group.getGroupId());

			Assert.assertEquals(
				groupSharingEntries.toString(), 1, groupSharingEntries.size());

			List<SharingEntry> group2SharingEntries =
				_sharingEntryLocalService.getGroupSharingEntries(
					group2.getGroupId());

			Assert.assertEquals(
				group2SharingEntries.toString(), 1,
				group2SharingEntries.size());

			_sharingEntryLocalService.deleteGroupSharingEntries(
				_group.getGroupId());

			groupSharingEntries =
				_sharingEntryLocalService.getGroupSharingEntries(
					_group.getGroupId());

			Assert.assertEquals(
				groupSharingEntries.toString(), 0, groupSharingEntries.size());

			group2SharingEntries =
				_sharingEntryLocalService.getGroupSharingEntries(
					group2.getGroupId());

			Assert.assertEquals(
				group2SharingEntries.toString(), 1,
				group2SharingEntries.size());
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(group2);
		}
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteNonexistingSharingEntry() throws Exception {
		_sharingEntryLocalService.deleteSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());
	}

	@Test
	public void testDeleteSharingEntries() throws Exception {
		long classNameId = RandomTestUtil.randomLong();
		long classPK1 = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			classNameId, classPK1, _group.getGroupId(),
			Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			classNameId, classPK1, _group.getGroupId(),
			Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

		long classPK2 = RandomTestUtil.randomLong();

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			classNameId, classPK2, _group.getGroupId(),
			Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getSharingEntries(classNameId, classPK1);

		Assert.assertEquals(
			sharingEntries.toString(), 2, sharingEntries.size());

		sharingEntries = _sharingEntryLocalService.getSharingEntries(
			classNameId, classPK2);

		Assert.assertEquals(
			sharingEntries.toString(), 1, sharingEntries.size());

		_sharingEntryLocalService.deleteSharingEntries(classNameId, classPK1);

		sharingEntries = _sharingEntryLocalService.getSharingEntries(
			classNameId, classPK1);

		Assert.assertEquals(
			sharingEntries.toString(), 0, sharingEntries.size());

		sharingEntries = _sharingEntryLocalService.getSharingEntries(
			classNameId, classPK2);

		Assert.assertEquals(
			sharingEntries.toString(), 1, sharingEntries.size());

		_sharingEntryLocalService.deleteSharingEntries(classNameId, classPK2);

		sharingEntries = _sharingEntryLocalService.getSharingEntries(
			classNameId, classPK2);

		Assert.assertEquals(
			sharingEntries.toString(), 0, sharingEntries.size());
	}

	@Test
	public void testDeleteSharingEntry() throws Exception {
		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertNotNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry.getSharingEntryId()));

		_sharingEntryLocalService.deleteSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK);

		Assert.assertNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry.getSharingEntryId()));
	}

	@Test
	public void testDeleteSharingEntryDoesNotDeleteOtherSharingEntriesToSameUse()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry1 = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		long userId = RandomTestUtil.randomLong();

		SharingEntry sharingEntry2 = _sharingEntryLocalService.addSharingEntry(
			userId, _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertNotNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry1.getSharingEntryId()));
		Assert.assertNotNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry2.getSharingEntryId()));

		_sharingEntryLocalService.deleteSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK);

		Assert.assertNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry1.getSharingEntryId()));
		Assert.assertNotNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry2.getSharingEntryId()));
	}

	@Test
	public void testDeleteToUserSharingEntries() throws Exception {
		long toUserId = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), toUserId, RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), _group.getGroupId(),
			Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), toUserId, RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), _group.getGroupId(),
			Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), toUserId, RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), _group.getGroupId(),
			Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(toUserId);

		Assert.assertEquals(
			sharingEntries.toString(), 3, sharingEntries.size());

		_sharingEntryLocalService.deleteToUserSharingEntries(toUserId);

		sharingEntries = _sharingEntryLocalService.getToUserSharingEntries(
			toUserId);

		Assert.assertEquals(
			sharingEntries.toString(), 0, sharingEntries.size());
	}

	@Test
	public void testDeleteToUserSharingEntriesDoesNotDeleteFromUserSharingEntries()
		throws Exception {

		long toUserId = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), toUserId, RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), _group.getGroupId(),
			Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

		_sharingEntryLocalService.addSharingEntry(
			toUserId, RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), _group.getGroupId(),
			Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

		List<SharingEntry> toUserSharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(toUserId);

		Assert.assertEquals(
			toUserSharingEntries.toString(), 1, toUserSharingEntries.size());

		List<SharingEntry> fromUserSharingEntries =
			_sharingEntryLocalService.getFromUserSharingEntries(toUserId);

		Assert.assertEquals(
			fromUserSharingEntries.toString(), 1,
			fromUserSharingEntries.size());

		_sharingEntryLocalService.deleteToUserSharingEntries(toUserId);

		toUserSharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(toUserId);

		Assert.assertEquals(
			toUserSharingEntries.toString(), 0, toUserSharingEntries.size());

		fromUserSharingEntries =
			_sharingEntryLocalService.getFromUserSharingEntries(toUserId);

		Assert.assertEquals(
			fromUserSharingEntries.toString(), 1,
			fromUserSharingEntries.size());
	}

	@Test
	public void testHasSharingPermissionWithTwoSharingEntries()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.VIEW));

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), _toUser.getUserId(), classNameId,
			classPK, _group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.VIEW));

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), _toUser.getUserId(), classNameId,
			classPK, _group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.ADD_DISCUSSION,
				SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUpdateViewSharingEntryActionKey()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUpdateViewSharingEntryActionKeyFromUserId()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUserNotHavingSharingEntryActionKey()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithViewAddDiscussionSharingEntryActionKey()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW,
				SharingEntryActionKey.ADD_DISCUSSION),
			serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithViewAndAddDiscussionSharingEntryActionKey()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW,
				SharingEntryActionKey.ADD_DISCUSSION),
			serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithViewSharingEntryActionKey()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithViewUpdateSharingEntryActionKey()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW, SharingEntryActionKey.UPDATE),
			serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryActionKey.VIEW));
	}

	@Test(expected = NoSuchEntryException.class)
	public void testUpdateNonexistingSharingEntry() throws Exception {
		_sharingEntryLocalService.updateSharingEntry(
			RandomTestUtil.randomLong(),
			Arrays.asList(
				SharingEntryActionKey.ADD_DISCUSSION,
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW));
	}

	@Test
	public void testUpdateSharingEntry() throws Exception {
		long classNameId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertEquals(1, sharingEntry.getActionIds());

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW));

		Assert.assertEquals(3, sharingEntry.getActionIds());

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(
				SharingEntryActionKey.ADD_DISCUSSION,
				SharingEntryActionKey.VIEW));

		Assert.assertEquals(5, sharingEntry.getActionIds());

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(
				SharingEntryActionKey.ADD_DISCUSSION,
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW));

		Assert.assertEquals(7, sharingEntry.getActionIds());
	}

	@Test(expected = InvalidSharingEntryActionKeyException.class)
	public void testUpdateSharingEntryWithEmptySharingEntryActionKeys()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		_sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(), Collections.emptyList());
	}

	@Test(expected = InvalidSharingEntryActionKeyException.class)
	public void testUpdateSharingEntryWithoutViewSharingEntryActionKey()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		_sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryActionKey.UPDATE));
	}

	@Test(expected = InvalidSharingEntryActionKeyException.class)
	public void testUpdateSharingEntryWithSharingEntryActionKeysContainingOneNullElement()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		List<SharingEntryActionKey> sharingEntryActionKeys = new ArrayList<>();

		sharingEntryActionKeys.add(SharingEntryActionKey.VIEW);
		sharingEntryActionKeys.add(null);

		_sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(), sharingEntryActionKeys);
	}

	@Test(expected = InvalidSharingEntryActionKeyException.class)
	public void testUpdateSharingEntryWithSharingEntryActionKeysContainingOnlyNullElement()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		List<SharingEntryActionKey> sharingEntryActionKeys = new ArrayList<>();

		sharingEntryActionKeys.add(null);

		_sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(), sharingEntryActionKeys);
	}

	@DeleteAfterTestRun
	private User _fromUser;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@DeleteAfterTestRun
	private User _toUser;

	@DeleteAfterTestRun
	private User _user;

}