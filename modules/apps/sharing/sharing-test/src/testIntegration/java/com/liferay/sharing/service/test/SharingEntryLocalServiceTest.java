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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertEquals(_group.getCompanyId(), sharingEntry.getCompanyId());
		Assert.assertEquals(_group.getGroupId(), sharingEntry.getGroupId());
		Assert.assertEquals(
			_fromUser.getUserId(), sharingEntry.getFromUserId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
		Assert.assertEquals(className, sharingEntry.getClassName());
		Assert.assertEquals(classPK, sharingEntry.getClassPK());
	}

	@Test
	public void testAddSharingEntryActionIds() throws Exception {
		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertEquals(1, sharingEntry.getActionIds());

		className = StringUtil.randomString();
		classPK = RandomTestUtil.randomLong();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.UPDATE),
			serviceContext);

		Assert.assertEquals(2, sharingEntry.getActionIds());

		className = StringUtil.randomString();
		classPK = RandomTestUtil.randomLong();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(SharingEntryActionKey.ADD_DISCUSSION),
			serviceContext);

		Assert.assertEquals(4, sharingEntry.getActionIds());

		className = StringUtil.randomString();
		classPK = RandomTestUtil.randomLong();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW, SharingEntryActionKey.UPDATE),
			serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());

		className = StringUtil.randomString();
		classPK = RandomTestUtil.randomLong();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());

		className = StringUtil.randomString();
		classPK = RandomTestUtil.randomLong();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW,
				SharingEntryActionKey.ADD_DISCUSSION),
			serviceContext);

		Assert.assertEquals(5, sharingEntry.getActionIds());

		className = StringUtil.randomString();
		classPK = RandomTestUtil.randomLong();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE,
				SharingEntryActionKey.ADD_DISCUSSION),
			serviceContext);

		Assert.assertEquals(6, sharingEntry.getActionIds());

		className = StringUtil.randomString();
		classPK = RandomTestUtil.randomLong();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW, SharingEntryActionKey.UPDATE,
				SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());

		className = StringUtil.randomString();
		classPK = RandomTestUtil.randomLong();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
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

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(), Collections.emptyList(), serviceContext);
	}

	@Test(expected = InvalidSharingEntryUserException.class)
	public void testAddSharingEntryWithSameFromUserAndToUser()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _fromUser.getUserId(), className, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionKeyException.class)
	public void testAddSharingEntryWithSharingEntryActionKeysContainingOneNullElement()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		List<SharingEntryActionKey> sharingEntryActionKeys = new ArrayList<>();

		sharingEntryActionKeys.add(SharingEntryActionKey.VIEW);
		sharingEntryActionKeys.add(null);

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(), sharingEntryActionKeys, serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionKeyException.class)
	public void testAddSharingEntryWithSharingEntryActionKeysContainingOnlyNullElement()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		List<SharingEntryActionKey> sharingEntryActionKeys = new ArrayList<>();

		sharingEntryActionKeys.add(null);

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(), sharingEntryActionKeys, serviceContext);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteNonExistingSharingEntry() throws Exception {
		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		_sharingEntryLocalService.deleteSharingEntry(
			_toUser.getUserId(), className, classPK);
	}

	@Test
	public void testDeleteSharingEntry() throws Exception {
		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertNotNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry.getSharingEntryId()));

		_sharingEntryLocalService.deleteSharingEntry(
			_toUser.getUserId(), className, classPK);

		Assert.assertNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry.getSharingEntryId()));
	}

	@Test
	public void testHasSharingPermissionWithUpdateAndAddSharingEntryActionKey()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE,
				SharingEntryActionKey.ADD_DISCUSSION),
			serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.VIEW));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
	}

	@Test
	public void testHasSharingPermissionWithUpdateSharingEntryActionKey()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.UPDATE),
			serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.VIEW));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.UPDATE));
	}

	@Test
	public void testHasSharingPermissionWithUpdateViewSharingEntryActionKey()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.VIEW));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
	}

	@Test
	public void testHasSharingPermissionWithUpdateViewSharingEntryActionKeyFromUserId()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.UPDATE, SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), className, classPK,
				SharingEntryActionKey.VIEW));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), className, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), className, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
	}

	@Test
	public void testHasSharingPermissionWithUserNotHavingSharingEntryActionKey()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.VIEW));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
	}

	@Test
	public void testHasSharingPermissionWithViewAddDiscussionSharingEntryActionKey()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW,
				SharingEntryActionKey.ADD_DISCUSSION),
			serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.VIEW));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
	}

	@Test
	public void testHasSharingPermissionWithViewSharingEntryActionKey()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(), Arrays.asList(SharingEntryActionKey.VIEW),
			serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.VIEW));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.UPDATE));
	}

	@Test
	public void testHasSharingPermissionWithViewUpdateSharingEntryActionKey()
		throws Exception {

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), className, classPK,
			_group.getGroupId(),
			Arrays.asList(
				SharingEntryActionKey.VIEW, SharingEntryActionKey.UPDATE),
			serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.VIEW));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), className, classPK,
				SharingEntryActionKey.ADD_DISCUSSION));
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