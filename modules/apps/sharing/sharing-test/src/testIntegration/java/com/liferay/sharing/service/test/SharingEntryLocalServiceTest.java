/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
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
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_fromUser = UserTestUtil.addUser();
		_toUser = UserTestUtil.addUser();
		_user = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testAddOrUpdateSharingEntryAddsNewSharingEntry()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Assert.assertEquals(
			0,
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, classPK));

		Instant instant = Instant.now();

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			Date.from(instant.plus(2, ChronoUnit.DAYS)),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			1,
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, classPK));
	}

	@Test
	public void testAddOrUpdateSharingEntryUpdatesSharingEntry()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Assert.assertEquals(
			0,
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, classPK));

		Instant instant = Instant.now();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			Date.from(instant.plus(2, ChronoUnit.DAYS)), serviceContext);

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), false,
			Arrays.asList(SharingEntryAction.VIEW, SharingEntryAction.UPDATE),
			Date.from(instant.plus(3, ChronoUnit.DAYS)), serviceContext);

		Assert.assertEquals(
			1,
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, classPK));
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddOrUpdateSharingEntryWithEmptySharingEntryActions()
		throws Exception {

		long classPK = _group.getGroupId();

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			_classNameLocalService.getClassNameId(Group.class.getName()),
			classPK, _group.getGroupId(), true, Collections.emptyList(), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = InvalidSharingEntryExpirationDateException.class)
	public void testAddOrUpdateSharingEntryWithExpirationDateInThePast()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getClassPK();

		Instant instant = Instant.now();

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			Date.from(instant.minus(2, ChronoUnit.DAYS)),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testAddSharingEntry() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Assert.assertEquals(
			0,
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, classPK));

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			1,
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, classPK));
	}

	@Test
	public void testAddSharingEntryActionIds() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getClassPK();

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			SharingEntryAction.UPDATE.getBitwiseValue() |
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			SharingEntryAction.ADD_DISCUSSION.getBitwiseValue() |
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());

		_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

		sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
				SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			SharingEntryAction.ADD_DISCUSSION.getBitwiseValue() |
			SharingEntryAction.UPDATE.getBitwiseValue() |
			SharingEntryAction.VIEW.getBitwiseValue(),
			sharingEntry.getActionIds());
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithEmptySharingEntryActions()
		throws Exception {

		long classPK = _group.getGroupId();

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			_classNameLocalService.getClassNameId(Group.class.getName()),
			classPK, _group.getGroupId(), true, Collections.emptyList(), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testAddSharingEntryWithExpirationDateInTheFuture()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Assert.assertEquals(
			0,
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, classPK));

		Instant instant = Instant.now();

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			Date.from(instant.plus(2, ChronoUnit.DAYS)),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			1,
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, classPK));
	}

	@Test(expected = InvalidSharingEntryExpirationDateException.class)
	public void testAddSharingEntryWithExpirationDateInThePast()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Instant instant = Instant.now();

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			Date.from(instant.minus(2, ChronoUnit.DAYS)),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithoutViewSharingEntryAction()
		throws Exception {

		long classPK = _group.getGroupId();

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			_classNameLocalService.getClassNameId(Group.class.getName()),
			classPK, _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = InvalidSharingEntryUserException.class)
	public void testAddSharingEntryWithSameFromUserAndToUser()
		throws Exception {

		long classPK = _group.getGroupId();

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _fromUser.getUserId(),
			_classNameLocalService.getClassNameId(Group.class.getName()),
			classPK, _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithSharingEntryActionsContainingOneNullElement()
		throws Exception {

		long classPK = _group.getGroupId();

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			_classNameLocalService.getClassNameId(Group.class.getName()),
			classPK, _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW, null), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddSharingEntryWithSharingEntryActionsContainingOnlyNullElement()
		throws Exception {

		long classPK = _group.getGroupId();

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(),
			_classNameLocalService.getClassNameId(Group.class.getName()),
			classPK, _group.getGroupId(), true,
			Arrays.asList(new SharingEntryAction[] {null}), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testDeleteExpiredEntries() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try {
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
					group.getGroupId(), group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW), null,
					serviceContext);

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
			long classNameId = _classNameLocalService.getClassNameId(
				Group.class.getName());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			_sharingEntryLocalService.addSharingEntry(
				_fromUser.getUserId(), _toUser.getUserId(), classNameId,
				group1.getGroupId(), group1.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

			_sharingEntryLocalService.addSharingEntry(
				_fromUser.getUserId(), _toUser.getUserId(), classNameId,
				group2.getGroupId(), group2.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

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
		long classPK = _group.getGroupId();

		_sharingEntryLocalService.deleteSharingEntry(
			_toUser.getUserId(),
			_classNameLocalService.getClassNameId(Group.class.getName()),
			classPK);
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

		Group group = GroupTestUtil.addGroup();

		try {
			long classPK2 = group.getGroupId();

			_sharingEntryLocalService.addSharingEntry(
				_fromUser.getUserId(), _toUser.getUserId(), classNameId,
				classPK2, _group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getSharingEntriesCount(
					classNameId, classPK1));

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getSharingEntriesCount(
					classNameId, classPK2));

			_sharingEntryLocalService.deleteSharingEntries(
				classNameId, classPK1);

			Assert.assertEquals(
				0,
				_sharingEntryLocalService.getSharingEntriesCount(
					classNameId, classPK1));

			Assert.assertEquals(
				1,
				_sharingEntryLocalService.getSharingEntriesCount(
					classNameId, classPK2));

			_sharingEntryLocalService.deleteSharingEntries(
				classNameId, classPK2);

			Assert.assertEquals(
				0,
				_sharingEntryLocalService.getSharingEntriesCount(
					classNameId, classPK2));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testDeleteSharingEntry() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertNotNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry.getSharingEntryId()));

		_sharingEntryLocalService.deleteSharingEntry(
			_toUser.getUserId(), classNameId, classPK);

		Assert.assertNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry.getSharingEntryId()));
	}

	@Test
	public void testDeleteToUserSharingEntries() throws Exception {
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
			long classNameId = _classNameLocalService.getClassNameId(
				Group.class.getName());
			long classPK1 = _group.getGroupId();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			Instant now = Instant.now();

			SharingEntry sharingEntry1 =
				_sharingEntryLocalService.addSharingEntry(
					_fromUser.getUserId(), _toUser.getUserId(), classNameId,
					classPK1, _group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW),
					Date.from(now.plus(2, ChronoUnit.DAYS)), serviceContext);

			long classPK2 = group.getGroupId();

			now = Instant.now();

			SharingEntry sharingEntry2 =
				_sharingEntryLocalService.addSharingEntry(
					_fromUser.getUserId(), _toUser.getUserId(), classNameId,
					classPK2, _group.getGroupId(), true,
					Arrays.asList(SharingEntryAction.VIEW),
					Date.from(now.plus(2, ChronoUnit.DAYS)), serviceContext);

			List<SharingEntry> ascendingSharingEntries =
				_sharingEntryLocalService.getToUserSharingEntries(
					_toUser.getUserId(), classNameId, 0, 2,
					new SharingEntryModifiedDateComparator(true));

			Assert.assertEquals(sharingEntry1, ascendingSharingEntries.get(0));
			Assert.assertEquals(sharingEntry2, ascendingSharingEntries.get(1));

			List<SharingEntry> descendingSharingEntries =
				_sharingEntryLocalService.getToUserSharingEntries(
					_toUser.getUserId(), classNameId, 0, 2,
					new SharingEntryModifiedDateComparator(false));

			Assert.assertEquals(sharingEntry2, descendingSharingEntries.get(0));
			Assert.assertEquals(sharingEntry1, descendingSharingEntries.get(1));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testHasShareableSharingPermissionWithShareableAddDiscussionAndViewSharingEntryAction()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

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

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), false,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

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

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

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

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

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

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

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

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

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

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

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

		_sharingEntryLocalService.addOrUpdateSharingEntry(
			_user.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

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

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

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
	public void testRetrievesUniqueSharedByMeSharingEntries() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, serviceContext);

		SharingEntry latestSharingEntry =
			_sharingEntryLocalService.addSharingEntry(
				_fromUser.getUserId(), _user.getUserId(), classNameId, classPK,
				_group.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

		long sharingEntriesCount =
			_sharingEntryLocalService.getFromUserSharingEntriesCount(
				_fromUser.getUserId(), classNameId);

		Assert.assertEquals(1, sharingEntriesCount);

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getFromUserSharingEntries(
				_fromUser.getUserId(), classNameId, 0, 2,
				new SharingEntryModifiedDateComparator());

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
			true, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
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
		Assert.assertTrue(sharingEntry.isShareable());
		Assert.assertNull(sharingEntry.getExpirationDate());

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			false, null, serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());
		Assert.assertFalse(sharingEntry.isShareable());
		Assert.assertNull(sharingEntry.getExpirationDate());

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.plus(2, ChronoUnit.DAYS));

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			true, expirationDate, serviceContext);

		Assert.assertEquals(5, sharingEntry.getActionIds());
		Assert.assertTrue(sharingEntry.isShareable());
		Assert.assertEquals(expirationDate, sharingEntry.getExpirationDate());

		sharingEntry = _sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
				SharingEntryAction.VIEW),
			true, null, serviceContext);

		Assert.assertEquals(7, sharingEntry.getActionIds());
		Assert.assertTrue(sharingEntry.isShareable());
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
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			Collections.emptyList(), true, null, serviceContext);
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
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
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
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
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
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			sharingEntryActions, true, null, serviceContext);
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

		_sharingEntryLocalService.updateSharingEntry(
			_fromUser.getUserId(), sharingEntry.getSharingEntryId(),
			ListUtil.fromArray((SharingEntryAction[])null), true, null,
			serviceContext);
	}

	private void _expireSharingEntry(SharingEntry sharingEntry) {
		Instant instant = Instant.now();

		sharingEntry.setExpirationDate(
			Date.from(instant.minus(1, ChronoUnit.DAYS)));

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
	private MessageBus _messageBus;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@DeleteAfterTestRun
	private User _toUser;

	@DeleteAfterTestRun
	private User _user;

}