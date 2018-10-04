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
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
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
import com.liferay.sharing.exception.InvalidSharingEntryActionException;
import com.liferay.sharing.exception.InvalidSharingEntryExpirationDateException;
import com.liferay.sharing.exception.InvalidSharingEntryUserException;
import com.liferay.sharing.exception.NoSuchEntryException;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.util.comparator.SharingEntryModifiedDateComparator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
	public void testAddOrUpdateSharingEntryAddsNewSharingEntry()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.plus(2, ChronoUnit.DAYS));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry =
			_sharingEntryLocalService.addOrUpdateSharingEntry(
				_fromUser.getUserId(), _toUser.getUserId(), classNameId,
				classPK, _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), expirationDate,
				serviceContext);

		Assert.assertEquals(_group.getCompanyId(), sharingEntry.getCompanyId());
		Assert.assertEquals(_group.getGroupId(), sharingEntry.getGroupId());
		Assert.assertEquals(
			_fromUser.getUserId(), sharingEntry.getFromUserId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
		Assert.assertEquals(classNameId, sharingEntry.getClassNameId());
		Assert.assertEquals(classPK, sharingEntry.getClassPK());
		Assert.assertTrue(sharingEntry.isShareable());
		Assert.assertEquals(expirationDate, sharingEntry.getExpirationDate());
	}

	@Test
	public void testAddOrUpdateSharingEntryUpdatesSharingEntry()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.plus(2, ChronoUnit.DAYS));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry addSharingEntry =
			_sharingEntryLocalService.addSharingEntry(
				_fromUser.getUserId(), _toUser.getUserId(), classNameId,
				classPK, _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), expirationDate,
				serviceContext);

		Assert.assertTrue(addSharingEntry.isShareable());
		Assert.assertEquals(1, addSharingEntry.getActionIds());
		Assert.assertEquals(
			expirationDate, addSharingEntry.getExpirationDate());

		expirationDate = Date.from(instant.plus(3, ChronoUnit.DAYS));

		SharingEntry updateSharingEntry =
			_sharingEntryLocalService.addOrUpdateSharingEntry(
				_fromUser.getUserId(), _toUser.getUserId(), classNameId,
				classPK, _group.getGroupId(), false,
				Arrays.asList(
					SharingEntryAction.VIEW, SharingEntryAction.UPDATE),
				expirationDate, serviceContext);

		Assert.assertFalse(updateSharingEntry.isShareable());
		Assert.assertEquals(3, updateSharingEntry.getActionIds());
		Assert.assertEquals(
			expirationDate, updateSharingEntry.getExpirationDate());

		Assert.assertEquals(
			addSharingEntry.getSharingEntryId(),
			updateSharingEntry.getSharingEntryId());
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddOrUpdateSharingEntryWithEmptySharingEntryActions()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Collections.emptyList(), null,
			serviceContext);
	}

	@Test(expected = InvalidSharingEntryExpirationDateException.class)
	public void testAddOrUpdateSharingEntryWithExpirationDateInThePast()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getClassPK();

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.minus(2, ChronoUnit.DAYS));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			expirationDate, serviceContext);
	}

	@Test
	public void testAddSharingEntry() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertEquals(_group.getCompanyId(), sharingEntry.getCompanyId());
		Assert.assertEquals(_group.getGroupId(), sharingEntry.getGroupId());
		Assert.assertEquals(
			_fromUser.getUserId(), sharingEntry.getFromUserId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
		Assert.assertEquals(classNameId, sharingEntry.getClassNameId());
		Assert.assertEquals(classPK, sharingEntry.getClassPK());
		Assert.assertTrue(sharingEntry.isShareable());
	}

	@Test
	public void testAddSharingEntryActionIds() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getClassPK();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertEquals(1, sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertEquals(5, sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertEquals(5, sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.UPDATE, SharingEntryAction.VIEW,
				SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
				SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertEquals(7, sharingEntry.getActionIds());
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithEmptySharingEntryActions()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Collections.emptyList(), null,
			serviceContext);
	}

	@Test
	public void testAddSharingEntryWithExpirationDateInTheFuture()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.plus(2, ChronoUnit.DAYS));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			expirationDate, serviceContext);

		Assert.assertEquals(_group.getCompanyId(), sharingEntry.getCompanyId());
		Assert.assertEquals(_group.getGroupId(), sharingEntry.getGroupId());
		Assert.assertEquals(
			_fromUser.getUserId(), sharingEntry.getFromUserId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
		Assert.assertEquals(classNameId, sharingEntry.getClassNameId());
		Assert.assertEquals(classPK, sharingEntry.getClassPK());
		Assert.assertTrue(sharingEntry.isShareable());
		Assert.assertEquals(expirationDate, sharingEntry.getExpirationDate());
	}

	@Test(expected = InvalidSharingEntryExpirationDateException.class)
	public void testAddSharingEntryWithExpirationDateInThePast()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.minus(2, ChronoUnit.DAYS));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			expirationDate, serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithoutViewSharingEntryAction()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.UPDATE),
			null, serviceContext);
	}

	@Test(expected = InvalidSharingEntryUserException.class)
	public void testAddSharingEntryWithSameFromUserAndToUser()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _fromUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithSharingEntryActionsContainingOneNullElement()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		List<SharingEntryAction> sharingEntryActions = new ArrayList<>();

		sharingEntryActions.add(SharingEntryAction.VIEW);
		sharingEntryActions.add(null);

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, sharingEntryActions, null,
			serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithSharingEntryActionsContainingOnlyNullElement()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		List<SharingEntryAction> sharingEntryActions = new ArrayList<>();

		sharingEntryActions.add(null);

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, sharingEntryActions, null,
			serviceContext);
	}

	@Test
	public void testDeleteExpiredEntries() throws Exception {
		Group group2 = GroupTestUtil.addGroup();

		try (DisableSchedulerDestination disableSchedulerDestination =
				new DisableSchedulerDestination()) {

			long classNameId = _classNameLocalService.getClassNameId(
				Group.class.getName());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			_sharingEntryLocalService.addSharingEntry(
				_fromUser.getUserId(), _toUser.getUserId(), classNameId,
				_group.getGroupId(), _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

			SharingEntry sharingEntry =
				_sharingEntryLocalService.addSharingEntry(
					_fromUser.getUserId(), _toUser.getUserId(), classNameId,
					group2.getGroupId(), group2.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW), null,
					serviceContext);

			_expireSharingEntry(sharingEntry);

			Assert.assertEquals(
				2,
				_sharingEntryLocalService.getFromUserSharingEntriesCount(
					_fromUser.getUserId()));

			_sharingEntryLocalService.deleteExpiredEntries();

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getFromUserSharingEntriesCount(
					_fromUser.getUserId()));
		}
		finally {
			_groupLocalService.deleteGroup(group2);
		}
	}

	@Test
	public void testDeleteGroupSharingEntries() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		for (int i = 0; i < 3; i++) {
			Group group = GroupTestUtil.addGroup();

			try {
				_sharingEntryLocalService.addSharingEntry(
					_fromUser.getUserId(), _toUser.getUserId(), classNameId,
					group.getGroupId(), _group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW), null,
					serviceContext);
			}
			finally {
				_groupLocalService.deleteGroup(group);
			}
		}

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
			long classNameId = _classNameLocalService.getClassNameId(
				Group.class.getName());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			_sharingEntryLocalService.addSharingEntry(
				_fromUser.getUserId(), _toUser.getUserId(), classNameId,
				_group.getGroupId(), _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

			_sharingEntryLocalService.addSharingEntry(
				_fromUser.getUserId(), _toUser.getUserId(), classNameId,
				group2.getGroupId(), group2.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

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
			_groupLocalService.deleteGroup(group2);
		}
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteNonexistingSharingEntry() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		_sharingEntryLocalService.deleteSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK);
	}

	@Test
	public void testDeleteSharingEntries() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK1 = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK1,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _toUser.getUserId(), classNameId, classPK1,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		Group group2 = GroupTestUtil.addGroup();

		try {
			long classPK2 = group2.getGroupId();

			_sharingEntryLocalService.addSharingEntry(
				_fromUser.getUserId(), _toUser.getUserId(), classNameId,
				classPK2, _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

			List<SharingEntry> sharingEntries =
				_sharingEntryLocalService.getSharingEntries(
					classNameId, classPK1);

			Assert.assertEquals(
				sharingEntries.toString(), 2, sharingEntries.size());

			sharingEntries = _sharingEntryLocalService.getSharingEntries(
				classNameId, classPK2);

			Assert.assertEquals(
				sharingEntries.toString(), 1, sharingEntries.size());

			_sharingEntryLocalService.deleteSharingEntries(
				classNameId, classPK1);

			sharingEntries = _sharingEntryLocalService.getSharingEntries(
				classNameId, classPK1);

			Assert.assertEquals(
				sharingEntries.toString(), 0, sharingEntries.size());

			sharingEntries = _sharingEntryLocalService.getSharingEntries(
				classNameId, classPK2);

			Assert.assertEquals(
				sharingEntries.toString(), 1, sharingEntries.size());

			_sharingEntryLocalService.deleteSharingEntries(
				classNameId, classPK2);

			sharingEntries = _sharingEntryLocalService.getSharingEntries(
				classNameId, classPK2);

			Assert.assertEquals(
				sharingEntries.toString(), 0, sharingEntries.size());
		}
		finally {
			_groupLocalService.deleteGroup(group2);
		}
	}

	@Test
	public void testDeleteSharingEntry() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

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

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry1 = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		long userId = _user.getUserId();

		SharingEntry sharingEntry2 = _sharingEntryLocalService.addSharingEntry(
			userId, _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

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
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());

		long fromUserId = _fromUser.getUserId();
		long toUserId = _toUser.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		for (int i = 0; i < 3; i++) {
			Group group = GroupTestUtil.addGroup();

			try {
				_sharingEntryLocalService.addSharingEntry(
					fromUserId, toUserId, classNameId, group.getGroupId(),
					_group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW), null,
					serviceContext);
			}
			finally {
				_groupLocalService.deleteGroup(group);
			}
		}

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

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());

		long fromUserId = _fromUser.getUserId();
		long toUserId = _toUser.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			fromUserId, toUserId, classNameId, _group.getGroupId(),
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		Group group2 = GroupTestUtil.addGroup();

		try {
			_sharingEntryLocalService.addSharingEntry(
				toUserId, fromUserId, classNameId, group2.getGroupId(),
				_group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

			List<SharingEntry> toUserSharingEntries =
				_sharingEntryLocalService.getToUserSharingEntries(toUserId);

			Assert.assertEquals(
				toUserSharingEntries.toString(), 1,
				toUserSharingEntries.size());

			List<SharingEntry> fromUserSharingEntries =
				_sharingEntryLocalService.getFromUserSharingEntries(toUserId);

			Assert.assertEquals(
				fromUserSharingEntries.toString(), 1,
				fromUserSharingEntries.size());

			_sharingEntryLocalService.deleteToUserSharingEntries(toUserId);

			toUserSharingEntries =
				_sharingEntryLocalService.getToUserSharingEntries(toUserId);

			Assert.assertEquals(
				toUserSharingEntries.toString(), 0,
				toUserSharingEntries.size());

			fromUserSharingEntries =
				_sharingEntryLocalService.getFromUserSharingEntries(toUserId);

			Assert.assertEquals(
				fromUserSharingEntries.toString(), 1,
				fromUserSharingEntries.size());
		}
		finally {
			_groupLocalService.deleteGroup(group2);
		}
	}

	@Test
	public void testGetUniqueToUserIdSharingEntries() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		List<User> users = new ArrayList<>();

		try {
			for (int i = 0; i < 5; i++) {
				User fromUser = UserTestUtil.addUser();

				users.add(fromUser);

				_sharingEntryLocalService.addSharingEntry(
					fromUser.getUserId(), _toUser.getUserId(), classNameId,
					classPK, _group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW),
					Date.from(Instant.now().plus(2, ChronoUnit.DAYS)),
					serviceContext);
			}

			List<SharingEntry> sharingEntries =
				_sharingEntryLocalService.getUniqueToUserSharingEntries(
					_toUser.getUserId(), classNameId, 0,
					_sharingEntryLocalService.
						getUniqueToUserSharingEntriesCount(
							classNameId, _toUser.getUserId()),
					new SharingEntryModifiedDateComparator());

			Assert.assertEquals(
				sharingEntries.toString(), 1, sharingEntries.size());
		}
		finally {
			for (User user : users) {
				UserLocalServiceUtil.deleteUser(user);
			}
		}
	}

	@Test
	public void testHasShareableSharingPermissionWithShareableAddDiscussionAndViewSharingEntryAction()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasShareableSharingPermissionWithUnshareableAddDiscussionAndViewSharingEntryAction()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), false,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithAddDiscussionAndViewSharingEntryAction()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithTwoSharingEntries()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUpdateAndViewSharingEntryAction()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUpdateAndViewSharingEntryActionFromUserId()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), classNameId, classPK,
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), classNameId, classPK,
				SharingEntryAction.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), classNameId, classPK,
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUpdateViewSharingEntryActionFromUserId()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), _toUser.getUserId(), classNameId,
			classPK, _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.VIEW));

		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomLong(), _toUser.getUserId(), classNameId,
			classPK, _group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUserNotHavingSharingEntryAction()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithViewSharingEntryAction()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), classNameId, classPK,
				SharingEntryAction.VIEW));
	}

	@Test(expected = NoSuchEntryException.class)
	public void testUpdateNonexistingSharingEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.updateSharingEntry(
			RandomTestUtil.randomLong(),
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
				SharingEntryAction.VIEW),
			true, null, serviceContext);
	}

	@Test
	public void testUpdateSharingEntry() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertEquals(1, sharingEntry.getActionIds());
		Assert.assertEquals(true, sharingEntry.isShareable());
		Assert.assertNull(sharingEntry.getExpirationDate());

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			false, null, serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());
		Assert.assertEquals(false, sharingEntry.isShareable());
		Assert.assertNull(sharingEntry.getExpirationDate());

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.plus(2, ChronoUnit.DAYS));

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			true, expirationDate, serviceContext);

		Assert.assertEquals(5, sharingEntry.getActionIds());
		Assert.assertEquals(true, sharingEntry.isShareable());
		Assert.assertEquals(expirationDate, sharingEntry.getExpirationDate());

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
				SharingEntryAction.VIEW),
			true, null, serviceContext);

		Assert.assertEquals(7, sharingEntry.getActionIds());
		Assert.assertEquals(true, sharingEntry.isShareable());
		Assert.assertNull(sharingEntry.getExpirationDate());
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testUpdateSharingEntryWithEmptySharingEntryActions()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		_sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(), Collections.emptyList(), true,
			null, serviceContext);
	}

	@Test(expected = InvalidSharingEntryExpirationDateException.class)
	public void testUpdateSharingEntryWithExpirationDateInThePast()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.minus(2, ChronoUnit.DAYS));

		_sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.VIEW), true, expirationDate,
			serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testUpdateSharingEntryWithoutViewSharingEntryAction()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		_sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.UPDATE), true, null,
			serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testUpdateSharingEntryWithSharingEntryActionsContainingOneNullElement()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		List<SharingEntryAction> sharingEntryActions = new ArrayList<>();

		sharingEntryActions.add(SharingEntryAction.VIEW);
		sharingEntryActions.add(null);

		_sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(), sharingEntryActions, true, null,
			serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testUpdateSharingEntryWithSharingEntryActionsContainingOnlyNullElement()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		List<SharingEntryAction> sharingEntryActions = new ArrayList<>();

		sharingEntryActions.add(null);

		_sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(), sharingEntryActions, true, null,
			serviceContext);
	}

	private void _expireSharingEntry(SharingEntry sharingEntry) {
		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.minus(1, ChronoUnit.DAYS));

		sharingEntry.setExpirationDate(expirationDate);

		_sharingEntryLocalService.updateSharingEntry(sharingEntry);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private User _fromUser;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@DeleteAfterTestRun
	private User _toUser;

	@DeleteAfterTestRun
	private User _user;

	private static final class DisableSchedulerDestination
		implements AutoCloseable {

		public DisableSchedulerDestination() {
			MessageBus messageBus = MessageBusUtil.getMessageBus();

			_destination = messageBus.removeDestination(
				DestinationNames.SCHEDULER_DISPATCH, false);
		}

		@Override
		public void close() {
			MessageBusUtil.addDestination(_destination);
		}

		private final Destination _destination;

	}

}