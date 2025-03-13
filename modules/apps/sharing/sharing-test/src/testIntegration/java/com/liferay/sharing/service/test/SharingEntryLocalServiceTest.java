/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.NoSuchUserGroupException;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.sharing.exception.DuplicateSharingEntryException;
import com.liferay.sharing.exception.InvalidSharingEntryActionException;
import com.liferay.sharing.exception.InvalidSharingEntryExpirationDateException;
import com.liferay.sharing.exception.InvalidSharingEntryUserAndUserGroupException;
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
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		_group = GroupTestUtil.addGroup();
		_fromUser = UserTestUtil.addUser();
		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
		_toUser = UserTestUtil.addUser();
		_user = UserTestUtil.addOmniadminUser();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testAddOrUpdateSharingEntryAddsNewSharingEntry()
		throws Exception {

		Assert.assertEquals(
			0,
			_sharingEntryLocalService.getSharingEntriesCount(
				_classNameId, _group.getGroupId()));

		Instant instant = Instant.now();

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW),
			Date.from(instant.plus(2, ChronoUnit.DAYS)), _serviceContext);

		Assert.assertEquals(
			1,
			_sharingEntryLocalService.getSharingEntriesCount(
				_classNameId, _group.getGroupId()));
	}

	@Test
	public void testAddOrUpdateSharingEntryUpdatesSharingEntry()
		throws Exception {

		Assert.assertEquals(
			0,
			_sharingEntryLocalService.getSharingEntriesCount(
				_classNameId, _group.getGroupId()));

		Instant instant = Instant.now();

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW),
			Date.from(instant.plus(2, ChronoUnit.DAYS)), _serviceContext);

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), false,
			Arrays.asList(SharingEntryAction.VIEW, SharingEntryAction.UPDATE),
			Date.from(instant.plus(3, ChronoUnit.DAYS)), _serviceContext);

		Assert.assertEquals(
			1,
			_sharingEntryLocalService.getSharingEntriesCount(
				_classNameId, _group.getGroupId()));
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddOrUpdateSharingEntryWithEmptySharingEntryActions()
		throws Exception {

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Collections.emptyList(), null, _serviceContext);
	}

	@Test(expected = InvalidSharingEntryExpirationDateException.class)
	public void testAddOrUpdateSharingEntryWithExpirationDateInThePast()
		throws Exception {

		Instant instant = Instant.now();

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW),
			Date.from(instant.minus(2, ChronoUnit.DAYS)), _serviceContext);
	}

	@Test
	public void testAddOrUpdateSharingEntryWithExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			externalReferenceCode, _fromUser.getUserId(), 0,
			_toUser.getUserId(), _classNameId, _group.getGroupId(),
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, _serviceContext);

		SharingEntry sharingEntry =
			_sharingEntryLocalService.fetchSharingEntryByExternalReferenceCode(
				externalReferenceCode, _group.getGroupId());

		Assert.assertNotNull(sharingEntry);
		Assert.assertEquals(
			externalReferenceCode, sharingEntry.getExternalReferenceCode());
	}

	@Test
	public void testAddSharingEntry() throws Exception {
		Assert.assertEquals(
			0,
			_sharingEntryLocalService.getSharingEntriesCount(
				_classNameId, _group.getGroupId()));

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		Assert.assertEquals(
			1,
			_sharingEntryLocalService.getSharingEntriesCount(
				_classNameId, _group.getGroupId()));
	}

	@Test
	public void testAddSharingEntryActionIds() throws Exception {
		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		Assert.assertEquals(
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertEquals(
			SharingEntryAction.UPDATE.getBitwiseValue() |
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertEquals(
			SharingEntryAction.ADD_DISCUSSION.getBitwiseValue() |
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
				SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertEquals(
			SharingEntryAction.ADD_DISCUSSION.getBitwiseValue() |
			SharingEntryAction.UPDATE.getBitwiseValue() |
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());
	}

	@Test(expected = NoSuchUserGroupException.class)
	public void testAddSharingEntryToNonexistingUserGroup() throws Exception {
		_sharingEntryLocalService.addSharingEntry(
			RandomTestUtil.randomString(), _fromUser.getUserId(),
			RandomTestUtil.randomLong(), 0, _classNameId, _group.getGroupId(),
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, _serviceContext);
	}

	@Test
	public void testAddSharingEntryToUser() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		_sharingEntryLocalService.addSharingEntry(
			externalReferenceCode, _fromUser.getUserId(), 0,
			_toUser.getUserId(), _classNameId, _group.getGroupId(),
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, _serviceContext);

		SharingEntry sharingEntry =
			_sharingEntryLocalService.fetchSharingEntryByExternalReferenceCode(
				externalReferenceCode, _group.getGroupId());

		Assert.assertNotNull(sharingEntry);
		Assert.assertEquals(0, sharingEntry.getToUserGroupId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
	}

	@Test(expected = InvalidSharingEntryUserAndUserGroupException.class)
	public void testAddSharingEntryToUserAndUserGroup() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		try {
			_sharingEntryLocalService.addSharingEntry(
				RandomTestUtil.randomString(), _fromUser.getUserId(),
				userGroup.getUserGroupId(), _toUser.getUserId(), _classNameId,
				_group.getGroupId(), _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);
		}
		finally {
			if (userGroup != null) {
				_userGroupLocalService.deleteUserGroup(userGroup);
			}
		}
	}

	@Test
	public void testAddSharingEntryToUserGroup() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		try {
			_sharingEntryLocalService.addSharingEntry(
				externalReferenceCode, _fromUser.getUserId(),
				userGroup.getUserGroupId(), 0, _classNameId,
				_group.getGroupId(), _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

			SharingEntry sharingEntry =
				_sharingEntryLocalService.
					fetchSharingEntryByExternalReferenceCode(
						externalReferenceCode, _group.getGroupId());

			Assert.assertNotNull(sharingEntry);
			Assert.assertEquals(
				userGroup.getUserGroupId(), sharingEntry.getToUserGroupId());
			Assert.assertEquals(0, sharingEntry.getToUserId());
		}
		finally {
			if (userGroup != null) {
				_userGroupLocalService.deleteUserGroup(userGroup);
			}
		}
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithEmptySharingEntryActions()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Collections.emptyList(), null, _serviceContext);
	}

	@Test(expected = DuplicateSharingEntryException.class)
	public void testAddSharingEntryWithExistingExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_sharingEntryLocalService.addSharingEntry(
			externalReferenceCode, _fromUser.getUserId(), 0,
			_toUser.getUserId(), _classNameId, _group.getGroupId(),
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, _serviceContext);

		_sharingEntryLocalService.addSharingEntry(
			externalReferenceCode, _fromUser.getUserId(), 0,
			_toUser.getUserId(), _classNameId, _group.getGroupId(),
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, _serviceContext);
	}

	@Test
	public void testAddSharingEntryWithExpirationDateInTheFuture()
		throws Exception {

		Assert.assertEquals(
			0,
			_sharingEntryLocalService.getSharingEntriesCount(
				_classNameId, _group.getGroupId()));

		Instant instant = Instant.now();

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW),
			Date.from(instant.plus(2, ChronoUnit.DAYS)), _serviceContext);

		Assert.assertEquals(
			1,
			_sharingEntryLocalService.getSharingEntriesCount(
				_classNameId, _group.getGroupId()));
	}

	@Test(expected = InvalidSharingEntryExpirationDateException.class)
	public void testAddSharingEntryWithExpirationDateInThePast()
		throws Exception {

		Instant instant = Instant.now();

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW),
			Date.from(instant.minus(2, ChronoUnit.DAYS)), _serviceContext);
	}

	@Test
	public void testAddSharingEntryWithExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_sharingEntryLocalService.addSharingEntry(
			externalReferenceCode, _fromUser.getUserId(), 0,
			_toUser.getUserId(), _classNameId, _group.getGroupId(),
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, _serviceContext);

		SharingEntry sharingEntry =
			_sharingEntryLocalService.fetchSharingEntryByExternalReferenceCode(
				externalReferenceCode, _group.getGroupId());

		Assert.assertNotNull(sharingEntry);
		Assert.assertEquals(
			externalReferenceCode, sharingEntry.getExternalReferenceCode());
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithoutViewSharingEntryAction()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE), null, _serviceContext);
	}

	@Test(expected = InvalidSharingEntryUserException.class)
	public void testAddSharingEntryWithSameFromUserAndToUser()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _fromUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithSharingEntryActionsContainingOneNullElement()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW, null), null,
			_serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithSharingEntryActionsContainingOnlyNullElement()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(new SharingEntryAction[] {null}), null,
			_serviceContext);
	}

	@Test
	public void testDeleteExpiredEntries() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try {
			_sharingEntryLocalService.addSharingEntry(
				null, _fromUser.getUserId(), 0, _toUser.getUserId(),
				_classNameId, _group.getGroupId(), _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

			SharingEntry sharingEntry =
				_sharingEntryLocalService.addSharingEntry(
					null, _fromUser.getUserId(), 0, _toUser.getUserId(),
					_classNameId, group.getGroupId(), group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW), null,
					_serviceContext);

			_expireSharingEntry(sharingEntry);

			Assert.assertEquals(
				2,
				_sharingEntryLocalService.getToUserSharingEntriesCount(
					_toUser.getUserId()));

			_sharingEntryLocalService.deleteExpiredEntries();

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getToUserSharingEntriesCount(
					_toUser.getUserId()));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testDeleteGroupSharingEntries() throws Exception {
		for (int i = 0; i < 3; i++) {
			Group group = GroupTestUtil.addGroup();

			try {
				_sharingEntryLocalService.addSharingEntry(
					null, _fromUser.getUserId(), 0, _toUser.getUserId(),
					_classNameId, group.getGroupId(), _group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW), null,
					_serviceContext);
			}
			finally {
				_groupLocalService.deleteGroup(group);
			}
		}

		Assert.assertEquals(
			3,
			_sharingEntryLocalService.getGroupSharingEntriesCount(
				_group.getGroupId()));

		_sharingEntryLocalService.deleteGroupSharingEntries(
			_group.getGroupId());

		Assert.assertEquals(
			0,
			_sharingEntryLocalService.getGroupSharingEntriesCount(
				_group.getGroupId()));
	}

	@Test
	public void testDeleteGroupSharingEntriesDoesNotDeleteOtherGroupSharingEntries()
		throws Exception {

		Group group1 = _group;
		Group group2 = GroupTestUtil.addGroup();

		try {
			_sharingEntryLocalService.addSharingEntry(
				null, _fromUser.getUserId(), 0, _toUser.getUserId(),
				_classNameId, group1.getGroupId(), group1.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

			_sharingEntryLocalService.addSharingEntry(
				null, _fromUser.getUserId(), 0, _toUser.getUserId(),
				_classNameId, group2.getGroupId(), group2.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getGroupSharingEntriesCount(
					group1.getGroupId()));

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getGroupSharingEntriesCount(
					group2.getGroupId()));

			_sharingEntryLocalService.deleteGroupSharingEntries(
				group1.getGroupId());

			Assert.assertEquals(
				0,
				_sharingEntryLocalService.getGroupSharingEntriesCount(
					group1.getGroupId()));

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getGroupSharingEntriesCount(
					group2.getGroupId()));
		}
		finally {
			_groupLocalService.deleteGroup(group2);
		}
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteNonexistingSharingEntry() throws Exception {
		_sharingEntryLocalService.deleteSharingEntry(
			_toUser.getUserId(), _classNameId, _group.getGroupId());
	}

	@Test
	public void testDeleteSharingEntries() throws Exception {
		long classPK1 = _group.getGroupId();

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			classPK1, _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		Group group = GroupTestUtil.addGroup();

		try {
			long classPK2 = group.getGroupId();

			_sharingEntryLocalService.addSharingEntry(
				null, _fromUser.getUserId(), 0, _toUser.getUserId(),
				_classNameId, classPK2, _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getSharingEntriesCount(
					_classNameId, classPK1));

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getSharingEntriesCount(
					_classNameId, classPK2));

			_sharingEntryLocalService.deleteSharingEntries(
				_classNameId, classPK1);

			Assert.assertEquals(
				0,
				_sharingEntryLocalService.getSharingEntriesCount(
					_classNameId, classPK1));

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getSharingEntriesCount(
					_classNameId, classPK2));

			_sharingEntryLocalService.deleteSharingEntries(
				_classNameId, classPK2);

			Assert.assertEquals(
				0,
				_sharingEntryLocalService.getSharingEntriesCount(
					_classNameId, classPK2));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testDeleteSharingEntry() throws Exception {
		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		Assert.assertNotNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry.getSharingEntryId()));

		_sharingEntryLocalService.deleteSharingEntry(
			_toUser.getUserId(), _classNameId, _group.getGroupId());

		Assert.assertNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry.getSharingEntryId()));
	}

	@Test
	public void testDeleteSharingEntryByExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_sharingEntryLocalService.addSharingEntry(
			externalReferenceCode, _fromUser.getUserId(), 0,
			_toUser.getUserId(), _classNameId, _group.getGroupId(),
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertNotNull(
			_sharingEntryLocalService.fetchSharingEntryByExternalReferenceCode(
				externalReferenceCode, _group.getGroupId()));

		_sharingEntryLocalService.deleteSharingEntryByExternalReferenceCode(
			externalReferenceCode, _group.getGroupId());

		Assert.assertNull(
			_sharingEntryLocalService.fetchSharingEntryByExternalReferenceCode(
				externalReferenceCode, _group.getGroupId()));
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteSharingEntryByExternalReferenceCodeWithNonexistingExternalReferenceCode()
		throws Exception {

		_sharingEntryLocalService.deleteSharingEntryByExternalReferenceCode(
			RandomTestUtil.randomString(), _group.getGroupId());
	}

	@Test
	public void testDeleteToUserSharingEntries() throws Exception {
		for (int i = 0; i < 3; i++) {
			Group group = GroupTestUtil.addGroup();

			try {
				_sharingEntryLocalService.addSharingEntry(
					null, _fromUser.getUserId(), 0, _toUser.getUserId(),
					_classNameId, group.getGroupId(), _group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW), null,
					_serviceContext);
			}
			finally {
				_groupLocalService.deleteGroup(group);
			}
		}

		Assert.assertEquals(
			3,
			_sharingEntryLocalService.getToUserSharingEntriesCount(
				_toUser.getUserId()));

		_sharingEntryLocalService.deleteToUserSharingEntries(
			_toUser.getUserId());

		Assert.assertEquals(
			0,
			_sharingEntryLocalService.getToUserSharingEntriesCount(
				_toUser.getUserId()));
	}

	@Test
	public void testGetUniqueToUserIdSharingEntriesOrder() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try {
			long classPK1 = _group.getGroupId();

			Instant now = Instant.now();

			SharingEntry sharingEntry1 =
				_sharingEntryLocalService.addSharingEntry(
					null, _fromUser.getUserId(), 0, _toUser.getUserId(),
					_classNameId, classPK1, _group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW),
					Date.from(now.plus(2, ChronoUnit.DAYS)), _serviceContext);

			long classPK2 = group.getGroupId();

			now = Instant.now();

			SharingEntry sharingEntry2 =
				_sharingEntryLocalService.addSharingEntry(
					null, _fromUser.getUserId(), 0, _toUser.getUserId(),
					_classNameId, classPK2, _group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW),
					Date.from(now.plus(2, ChronoUnit.DAYS)), _serviceContext);

			List<SharingEntry> ascendingSharingEntries =
				_sharingEntryLocalService.getToUserSharingEntries(
					_toUser.getUserId(), _classNameId, 0, 2,
					SharingEntryModifiedDateComparator.getInstance(true));

			Assert.assertEquals(sharingEntry1, ascendingSharingEntries.get(0));
			Assert.assertEquals(sharingEntry2, ascendingSharingEntries.get(1));

			List<SharingEntry> descendingSharingEntries =
				_sharingEntryLocalService.getToUserSharingEntries(
					_toUser.getUserId(), _classNameId, 0, 2,
					SharingEntryModifiedDateComparator.getInstance(false));

			Assert.assertEquals(sharingEntry2, descendingSharingEntries.get(0));
			Assert.assertEquals(sharingEntry1, descendingSharingEntries.get(1));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testHasShareableSharingPermissionToUserGroup()
		throws Exception {

		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		try {
			_userGroupLocalService.addUserUserGroup(
				_toUser.getUserId(), userGroup);

			SharingEntry sharingEntry =
				_sharingEntryLocalService.addSharingEntry(
					null, _fromUser.getUserId(), userGroup.getUserGroupId(), 0,
					_classNameId, _group.getGroupId(), _group.getGroupId(),
					true,
					Arrays.asList(
						SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
					null, _serviceContext);

			Assert.assertTrue(
				_sharingEntryLocalService.hasShareableSharingPermission(
					_toUser.getUserId(), _classNameId, _group.getGroupId(),
					SharingEntryAction.UPDATE));

			_sharingEntryLocalService.updateSharingEntry(
				_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
				Arrays.asList(SharingEntryAction.VIEW), true, null,
				_serviceContext);

			Assert.assertFalse(
				_sharingEntryLocalService.hasShareableSharingPermission(
					_toUser.getUserId(), _classNameId, _group.getGroupId(),
					SharingEntryAction.UPDATE));

			_sharingEntryLocalService.updateSharingEntry(
				_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
				Arrays.asList(SharingEntryAction.VIEW), false, null,
				_serviceContext);

			Assert.assertFalse(
				_sharingEntryLocalService.hasShareableSharingPermission(
					_toUser.getUserId(), _classNameId, _group.getGroupId(),
					SharingEntryAction.UPDATE));
		}
		finally {
			if (userGroup != null) {
				_userGroupLocalService.deleteUserUserGroup(
					_toUser.getUserId(), userGroup);
				_userGroupLocalService.deleteUserGroup(userGroup);
			}
		}
	}

	@Test
	public void testHasShareableSharingPermissionWithShareableAddDiscussionAndViewSharingEntryAction()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasShareableSharingPermissionWithUnshareableAddDiscussionAndViewSharingEntryAction()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), false,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasShareableSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionToUserGroup() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		try {
			_userGroupLocalService.addUserUserGroup(
				_toUser.getUserId(), userGroup);

			_sharingEntryLocalService.addSharingEntry(
				null, _fromUser.getUserId(), userGroup.getUserGroupId(), 0,
				_classNameId, _group.getGroupId(), _group.getGroupId(), true,
				Arrays.asList(
					SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
				null, _serviceContext);

			Assert.assertTrue(
				_sharingEntryLocalService.hasSharingPermission(
					_toUser.getUserId(), _classNameId, _group.getGroupId(),
					SharingEntryAction.UPDATE));
			Assert.assertTrue(
				_sharingEntryLocalService.hasSharingPermission(
					_toUser.getUserId(), _classNameId, _group.getGroupId(),
					SharingEntryAction.VIEW));

			User user = UserTestUtil.addUser();

			Assert.assertFalse(
				_sharingEntryLocalService.hasSharingPermission(
					user.getUserId(), _classNameId, _group.getGroupId(),
					SharingEntryAction.UPDATE));
			Assert.assertFalse(
				_sharingEntryLocalService.hasSharingPermission(
					user.getUserId(), _classNameId, _group.getGroupId(),
					SharingEntryAction.VIEW));
		}
		finally {
			if (userGroup != null) {
				_userGroupLocalService.deleteUserUserGroup(
					_toUser.getUserId(), userGroup);
				_userGroupLocalService.deleteUserGroup(userGroup);
			}
		}
	}

	@Test
	public void testHasSharingPermissionWithAddDiscussionAndViewSharingEntryAction()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithTwoSharingEntries()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUpdateAndViewSharingEntryAction()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUpdateAndViewSharingEntryActionFromUserId()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_fromUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUpdateViewSharingEntryActionFromUserId()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _user.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.VIEW));

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			null, _user.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, _serviceContext);

		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithUserNotHavingSharingEntryAction()
		throws Exception {

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.UPDATE));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.VIEW));
	}

	@Test
	public void testHasSharingPermissionWithViewSharingEntryAction()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.ADD_DISCUSSION));
		Assert.assertFalse(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.UPDATE));
		Assert.assertTrue(
			_sharingEntryLocalService.hasSharingPermission(
				_toUser.getUserId(), _classNameId, _group.getGroupId(),
				SharingEntryAction.VIEW));
	}

	@Test
	public void testRetrievesUniqueSharedByMeSharingEntries() throws Exception {
		_sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		SharingEntry latestSharingEntry =
			_sharingEntryLocalService.addSharingEntry(
				null, _fromUser.getUserId(), 0, _user.getUserId(), _classNameId,
				_group.getGroupId(), _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		long sharingEntriesCount =
			_sharingEntryLocalService.getFromUserSharingEntriesCount(
				_fromUser.getUserId(), _classNameId);

		Assert.assertEquals(1, sharingEntriesCount);

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getFromUserSharingEntries(
				_fromUser.getUserId(), _classNameId, 0, 2,
				SharingEntryModifiedDateComparator.getInstance(false));

		Assert.assertEquals(
			sharingEntries.toString(), 1, sharingEntries.size());

		SharingEntry sharingEntry = sharingEntries.get(0);

		Assert.assertEquals(latestSharingEntry, sharingEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testUpdateNonexistingSharingEntry() throws Exception {
		_sharingEntryLocalService.updateSharingEntry(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
				SharingEntryAction.VIEW),
			true, null, _serviceContext);
	}

	@Test
	public void testUpdateSharingEntry() throws Exception {
		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		Assert.assertEquals(1, sharingEntry.getActionIds());
		Assert.assertTrue(sharingEntry.isShareable());
		Assert.assertNull(sharingEntry.getExpirationDate());

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			false, null, _serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());
		Assert.assertFalse(sharingEntry.isShareable());
		Assert.assertNull(sharingEntry.getExpirationDate());

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.plus(2, ChronoUnit.DAYS));

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			true, expirationDate, _serviceContext);

		Assert.assertEquals(5, sharingEntry.getActionIds());
		Assert.assertTrue(sharingEntry.isShareable());
		Assert.assertEquals(expirationDate, sharingEntry.getExpirationDate());

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
				SharingEntryAction.VIEW),
			true, null, _serviceContext);

		Assert.assertEquals(7, sharingEntry.getActionIds());
		Assert.assertTrue(sharingEntry.isShareable());
		Assert.assertNull(sharingEntry.getExpirationDate());
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testUpdateSharingEntryWithEmptySharingEntryActions()
		throws Exception {

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		_sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			Collections.emptyList(), true, null, _serviceContext);
	}

	@Test(expected = InvalidSharingEntryExpirationDateException.class)
	public void testUpdateSharingEntryWithExpirationDateInThePast()
		throws Exception {

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.minus(2, ChronoUnit.DAYS));

		_sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.VIEW), true, expirationDate,
			_serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testUpdateSharingEntryWithoutViewSharingEntryAction()
		throws Exception {

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		_sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.UPDATE), true, null,
			_serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testUpdateSharingEntryWithSharingEntryActionsContainingOneNullElement()
		throws Exception {

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		List<SharingEntryAction> sharingEntryActions = new ArrayList<>();

		sharingEntryActions.add(SharingEntryAction.VIEW);
		sharingEntryActions.add(null);

		_sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			sharingEntryActions, true, null, _serviceContext);
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testUpdateSharingEntryWithSharingEntryActionsContainingOnlyNullElement()
		throws Exception {

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, _toUser.getUserId(), _classNameId,
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, _serviceContext);

		_sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			ListUtil.fromArray((SharingEntryAction[])null), true, null,
			_serviceContext);
	}

	private void _expireSharingEntry(SharingEntry sharingEntry) {
		Instant instant = Instant.now();

		sharingEntry.setExpirationDate(
			Date.from(instant.minus(1, ChronoUnit.DAYS)));

		_sharingEntryLocalService.updateSharingEntry(sharingEntry);
	}

	private long _classNameId;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private User _fromUser;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private MessageBus _messageBus;

	private ServiceContext _serviceContext;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@DeleteAfterTestRun
	private User _toUser;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

}