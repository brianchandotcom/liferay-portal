/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
public class UserModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();

		_groupUser1 = UserTestUtil.addGroupUser(
			_group1, RoleConstants.POWER_USER);
		_groupUser2 = UserTestUtil.addGroupUser(
			_group1, RoleConstants.POWER_USER);
	}

	@Test
	@TestInfo("LPD-48130")
	public void testAddUser() throws Exception {
		String emailAddress1 = RandomTestUtil.randomString() + "@liferay.com";
		String emailAddress2 = RandomTestUtil.randomString() + "@liferay.com";

		Ticket ticket1 = _addInviteCollaboratorTicket(emailAddress1);
		Ticket ticket2 = _addInviteCollaboratorTicket(emailAddress1);
		Ticket ticket3 = _addInviteCollaboratorTicket(emailAddress2);

		_group2 = GroupTestUtil.addGroup();

		SharingEntry sharingEntry1 = _addToTicketIdSharingEntry(
			_group1.getGroupId(), ticket1.getTicketId());
		SharingEntry sharingEntry2 = _addToTicketIdSharingEntry(
			_group2.getGroupId(), ticket2.getTicketId());
		SharingEntry sharingEntry3 = _addToTicketIdSharingEntry(
			_group1.getGroupId(), ticket3.getTicketId());

		User user1 = _addUser(emailAddress1);

		Assert.assertNull(
			_ticketLocalService.fetchTicket(ticket1.getTicketId()));
		Assert.assertNull(
			_ticketLocalService.fetchTicket(ticket2.getTicketId()));

		sharingEntry1 = _sharingEntryLocalService.getSharingEntry(
			sharingEntry1.getSharingEntryId());

		Assert.assertEquals(0, sharingEntry1.getToTicketId());
		Assert.assertEquals(user1.getUserId(), sharingEntry1.getToUserId());

		sharingEntry2 = _sharingEntryLocalService.getSharingEntry(
			sharingEntry2.getSharingEntryId());

		Assert.assertEquals(0, sharingEntry2.getToTicketId());
		Assert.assertEquals(user1.getUserId(), sharingEntry2.getToUserId());

		ticket3 = _ticketLocalService.fetchTicket(ticket3.getTicketId());

		sharingEntry3 = _sharingEntryLocalService.getSharingEntry(
			sharingEntry3.getSharingEntryId());

		Assert.assertEquals(
			ticket3.getTicketId(), sharingEntry3.getToTicketId());
		Assert.assertEquals(0, sharingEntry3.getToUserId());

		// With Case Insensitive Email Address

		emailAddress1 = RandomTestUtil.randomString() + "@liferay.com";

		ticket1 = _addInviteCollaboratorTicket(
			StringUtil.toUpperCase(emailAddress1));

		sharingEntry1 = _addToTicketIdSharingEntry(
			_group1.getGroupId(), ticket1.getTicketId());

		User user2 = _addUser(emailAddress1);

		Assert.assertNull(
			_ticketLocalService.fetchTicket(ticket1.getTicketId()));

		sharingEntry1 = _sharingEntryLocalService.getSharingEntry(
			sharingEntry1.getSharingEntryId());

		Assert.assertEquals(0, sharingEntry1.getToTicketId());
		Assert.assertEquals(user2.getUserId(), sharingEntry1.getToUserId());

		// With Duplicate Sharing Entry

		emailAddress1 = RandomTestUtil.randomString() + "@liferay.com";

		ticket1 = _addInviteCollaboratorTicket(emailAddress1);
		ticket2 = _addInviteCollaboratorTicket(emailAddress1);

		_addToTicketIdSharingEntry(_group1.getGroupId(), ticket1.getTicketId());
		_addToTicketIdSharingEntry(_group1.getGroupId(), ticket2.getTicketId());

		User user3 = _addUser(emailAddress1);

		Assert.assertNull(
			_ticketLocalService.fetchTicket(ticket1.getTicketId()));
		Assert.assertNull(
			_ticketLocalService.fetchTicket(ticket2.getTicketId()));

		List<SharingEntry> toUserSharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(
				user3.getUserId());

		Assert.assertEquals(
			toUserSharingEntries.toString(), 1, toUserSharingEntries.size());

		sharingEntry1 = toUserSharingEntries.get(0);

		Assert.assertEquals(0, sharingEntry1.getToTicketId());
	}

	@Test
	@TestInfo("LPD-48130")
	public void testAddUserWithWorkflow() throws Exception {
		String emailAddress1 = RandomTestUtil.randomString() + "@liferay.com";
		String emailAddress2 = RandomTestUtil.randomString() + "@liferay.com";

		Ticket ticket1 = _addInviteCollaboratorTicket(emailAddress1);
		Ticket ticket2 = _addInviteCollaboratorTicket(emailAddress1);
		Ticket ticket3 = _addInviteCollaboratorTicket(emailAddress2);

		_group2 = GroupTestUtil.addGroup();

		SharingEntry sharingEntry1 = _addToTicketIdSharingEntry(
			_group1.getGroupId(), ticket1.getTicketId());
		SharingEntry sharingEntry2 = _addToTicketIdSharingEntry(
			_group2.getGroupId(), ticket2.getTicketId());
		SharingEntry sharingEntry3 = _addToTicketIdSharingEntry(
			_group1.getGroupId(), ticket3.getTicketId());

		User user = _addUserWithWorkflow(emailAddress1);

		Assert.assertNull(
			_ticketLocalService.fetchTicket(ticket1.getTicketId()));
		Assert.assertNull(
			_ticketLocalService.fetchTicket(ticket2.getTicketId()));

		sharingEntry1 = _sharingEntryLocalService.getSharingEntry(
			sharingEntry1.getSharingEntryId());

		Assert.assertEquals(0, sharingEntry1.getToTicketId());
		Assert.assertEquals(user.getUserId(), sharingEntry1.getToUserId());

		sharingEntry2 = _sharingEntryLocalService.getSharingEntry(
			sharingEntry2.getSharingEntryId());

		Assert.assertEquals(0, sharingEntry2.getToTicketId());
		Assert.assertEquals(user.getUserId(), sharingEntry2.getToUserId());

		ticket3 = _ticketLocalService.fetchTicket(ticket3.getTicketId());

		sharingEntry3 = _sharingEntryLocalService.getSharingEntry(
			sharingEntry3.getSharingEntryId());

		Assert.assertEquals(
			ticket3.getTicketId(), sharingEntry3.getToTicketId());
		Assert.assertEquals(0, sharingEntry3.getToUserId());
	}

	@Test
	public void testDeletingUserSharedDeletesSharingEntries() throws Exception {
		long classPK = _group1.getGroupId();

		_sharingEntryLocalService.addSharingEntry(
			null, TestPropsValues.getUserId(), 0, 0, _groupUser1.getUserId(),
			_classNameLocalService.getClassNameId(Group.class.getName()),
			classPK, _group1.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(
				_group1.getGroupId(), TestPropsValues.getUserId()));

		List<SharingEntry> toUserSharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(
				_groupUser1.getUserId());

		Assert.assertEquals(
			toUserSharingEntries.toString(), 1, toUserSharingEntries.size());

		_userLocalService.deleteUser(_groupUser1.getUserId());

		toUserSharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(
				_groupUser1.getUserId());

		Assert.assertEquals(
			toUserSharingEntries.toString(), 0, toUserSharingEntries.size());
	}

	@Test
	public void testDeletingUserSharingDoesNotDeleteSharingEntries()
		throws Exception {

		long classPK = _group1.getGroupId();

		_sharingEntryLocalService.addSharingEntry(
			null, TestPropsValues.getUserId(), 0, 0, _groupUser1.getUserId(),
			_classNameLocalService.getClassNameId(Group.class.getName()),
			classPK, _group1.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(
				_group1.getGroupId(), TestPropsValues.getUserId()));

		List<SharingEntry> toUserSharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(
				_groupUser1.getUserId());

		Assert.assertEquals(
			toUserSharingEntries.toString(), 1, toUserSharingEntries.size());

		_userLocalService.deleteUser(_groupUser2.getUserId());

		toUserSharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(
				_groupUser1.getUserId());

		Assert.assertEquals(
			toUserSharingEntries.toString(), 1, toUserSharingEntries.size());
	}

	private Ticket _addInviteCollaboratorTicket(String emailAddress)
		throws Exception {

		return _ticketLocalService.addTicket(
			TestPropsValues.getCompanyId(), Group.class.getName(),
			_group1.getGroupId(), TicketConstants.TYPE_INVITE_COLLABORATOR,
			JSONUtil.put(
				"emailAddress", emailAddress
			).toString(),
			new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(48)),
			new ServiceContext());
	}

	private SharingEntry _addToTicketIdSharingEntry(
			long classPK, long toTicketId)
		throws Exception {

		return _sharingEntryLocalService.addSharingEntry(
			null, TestPropsValues.getUserId(), toTicketId, 0, 0,
			_classNameLocalService.getClassNameId(Group.class.getName()),
			classPK, _group1.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(
				_group1.getGroupId(), TestPropsValues.getUserId()));
	}

	private User _addUser(String emailAddress) throws Exception {
		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringPool.BLANK, emailAddress, RandomTestUtil.randomString(),
			LocaleUtil.getDefault(), "Test", "User", null,
			ServiceContextTestUtil.getServiceContext(
				_group1.getGroupId(), TestPropsValues.getUserId()));

		_users.add(user);

		return user;
	}

	private User _addUserWithWorkflow(String emailAddress) throws Exception {
		User user = _userLocalService.addUserWithWorkflow(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(), true,
			null, null, false, RandomTestUtil.randomString(), emailAddress,
			LocaleUtil.getDefault(), "Test", StringPool.BLANK, "User", 0, 0,
			true, Calendar.JANUARY, 1, 1970, StringPool.BLANK,
			UserConstants.TYPE_REGULAR, null, null, null, null, false,
			ServiceContextTestUtil.getServiceContext(
				_group1.getGroupId(), TestPropsValues.getUserId()));

		_users.add(user);

		return user;
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	private User _groupUser1;
	private User _groupUser2;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@Inject
	private TicketLocalService _ticketLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}