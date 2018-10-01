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

package com.liferay.portal.service.user;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 * @author Drew Brokke
 */
public class UserServiceWhenUpdatingUserTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void shouldNotRemoveChildGroupAssociation() throws Exception {
		User user = UserTestUtil.addUser(true);

		List<Group> groups = new ArrayList<>();

		Group parentGroup = GroupTestUtil.addGroup();

		groups.add(parentGroup);

		Group childGroup = GroupTestUtil.addGroup(parentGroup.getGroupId());

		childGroup.setMembershipRestriction(
			GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS);

		GroupLocalServiceUtil.updateGroup(childGroup);

		groups.add(childGroup);

		GroupLocalServiceUtil.addUserGroups(user.getUserId(), groups);

		user = _updateUser(user);

		Assert.assertEquals(groups, user.getGroups());
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
		ServiceContext serviceContext = new ServiceContext();

		return UserServiceUtil.updateUser(
			user.getUserId(), user.getPassword(), StringPool.BLANK,
			StringPool.BLANK, user.isPasswordReset(),
			user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
			user.getScreenName(), user.getEmailAddress(), user.getFacebookId(),
			user.getOpenId(), user.getLanguageId(), user.getTimeZoneId(),
			user.getGreeting(), user.getComments(), contact.getFirstName(),
			contact.getMiddleName(), contact.getLastName(),
			contact.getPrefixId(), contact.getSuffixId(), contact.isMale(),
			birthdayMonth, birthdayDay, birthdayYear, contact.getSmsSn(),
			contact.getFacebookSn(), contact.getJabberSn(),
			contact.getSkypeSn(), contact.getTwitterSn(), contact.getJobTitle(),
			groupIds, organizationIds, roleIds, userGroupRoles, userGroupIds,
			serviceContext);
	}

}