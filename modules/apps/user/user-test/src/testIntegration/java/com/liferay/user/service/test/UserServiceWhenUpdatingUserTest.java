/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class UserServiceWhenUpdatingUserTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testShouldNotRemoveChildGroupAssociation() throws Exception {
		_user = UserTestUtil.addUser(true);

		List<Group> groups = new ArrayList<>();

		Group parentGroup = GroupTestUtil.addGroup();

		groups.add(parentGroup);

		Group childGroup = GroupTestUtil.addGroup(parentGroup.getGroupId());

		groups.add(childGroup);

		try {
			childGroup.setMembershipRestriction(
				GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS);

			childGroup = _groupLocalService.updateGroup(childGroup);

			_groupLocalService.addUserGroups(_user.getUserId(), groups);

			_user = _updateUser(_user);

			Assert.assertEquals(groups, _user.getGroups());
		}
		finally {
			_groupLocalService.deleteGroup(childGroup);
			_groupLocalService.deleteGroup(parentGroup);
		}
	}

	private User _updateUser(User user) throws Exception {
		Contact contact = user.getContact();

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(contact.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DATE);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;

		return _userService.updateUser(
			user.getUserId(), user.getPassword(), StringPool.BLANK,
			StringPool.BLANK, user.isPasswordReset(),
			user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
			user.getScreenName(), user.getEmailAddress(), user.getLanguageId(),
			user.getTimeZoneId(), user.getGreeting(), user.getComments(),
			contact.getFirstName(), contact.getMiddleName(),
			contact.getLastName(), contact.getPrefixListTypeId(),
			contact.getSuffixListTypeId(), contact.isMale(), birthdayMonth,
			birthdayDay, birthdayYear, contact.getSmsSn(),
			contact.getFacebookSn(), contact.getJabberSn(),
			contact.getSkypeSn(), contact.getTwitterSn(), contact.getJobTitle(),
			groupIds, organizationIds, roleIds, userGroupRoles, userGroupIds,
			new ServiceContext());
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserService _userService;

}