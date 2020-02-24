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

package com.liferay.depot.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class UpdateMembershipsMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testProcessActionWithAddGroupDepotIds() throws Exception {
		User user = UserTestUtil.addUser();

		Group group = GroupTestUtil.addGroup();

		try {
			_mvcActionCommand.processAction(
				new MockActionRequest(
					_companyLocalService.getCompany(
						TestPropsValues.getCompanyId()),
					_groupLocalService.getGroup(TestPropsValues.getGroupId()),
					user, new long[] {group.getGroupId()}, null),
				null);

			long[] groupIds = _userLocalService.getGroupPrimaryKeys(
				user.getUserId());

			LongStream longStream = Arrays.stream(groupIds);

			Assert.assertTrue(
				longStream.anyMatch(value -> value == group.getGroupId()));
		}
		finally {
			_userLocalService.deleteUser(user);

			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testProcessActionWithDeleteGroupDepotIds() throws Exception {
		User user = UserTestUtil.addUser();

		Group group = GroupTestUtil.addGroup();

		try {
			Contact contact = user.getContact();

			Set<Long> groupIds = new HashSet<>(
				Collections.singleton(user.getGroupId()));

			groupIds.add(group.getGroupId());

			Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

			birthdayCal.setTime(user.getBirthday());

			_userLocalService.updateUser(
				user.getUserId(), user.getPassword(), null, null,
				user.isPasswordReset(), null, null, user.getScreenName(),
				user.getEmailAddress(), user.getFacebookId(), user.getOpenId(),
				true, null, user.getLanguageId(), user.getTimeZoneId(),
				user.getGreeting(), user.getComments(), user.getFirstName(),
				user.getMiddleName(), user.getLastName(), contact.getPrefixId(),
				contact.getSuffixId(), user.isMale(),
				birthdayCal.get(Calendar.MONTH), birthdayCal.get(Calendar.DATE),
				birthdayCal.get(Calendar.YEAR), contact.getSmsSn(),
				contact.getFacebookSn(), contact.getJabberSn(),
				contact.getSkypeSn(), contact.getTwitterSn(),
				user.getJobTitle(), ArrayUtil.toLongArray(groupIds),
				user.getOrganizationIds(), null, null, user.getUserGroupIds(),
				ServiceContextTestUtil.getServiceContext());

			_mvcActionCommand.processAction(
				new MockActionRequest(
					_companyLocalService.getCompany(
						TestPropsValues.getCompanyId()),
					_groupLocalService.getGroup(TestPropsValues.getGroupId()),
					user, null, new long[] {group.getGroupId()}),
				null);

			long[] finalGroupIds = _userLocalService.getGroupPrimaryKeys(
				user.getUserId());

			LongStream longStream = Arrays.stream(finalGroupIds);

			Assert.assertFalse(
				longStream.anyMatch(value -> value == group.getGroupId()));
		}
		finally {
			_userLocalService.deleteUser(user);

			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testProcessActionWithNullParameters() throws Exception {
		User user = UserTestUtil.addUser();

		try {
			long[] initialGroupIds = _userLocalService.getGroupPrimaryKeys(
				user.getUserId());

			_mvcActionCommand.processAction(
				new MockActionRequest(
					_companyLocalService.getCompany(
						TestPropsValues.getCompanyId()),
					_groupLocalService.getGroup(TestPropsValues.getGroupId()),
					user, null, null),
				null);

			long[] actualGroupIds = _userLocalService.getGroupPrimaryKeys(
				user.getUserId());

			Assert.assertEquals(
				Arrays.toString(actualGroupIds), initialGroupIds.length,
				actualGroupIds.length);
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject(
		filter = "mvc.command.name=/depot/update_memberships",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private UserLocalService _userLocalService;

	private static class MockActionRequest
		extends MockLiferayPortletActionRequest {

		public MockActionRequest(
			Company company, Group group, User user, long[] addDepotGroupIds,
			long[] deleteGroupIds) {

			_company = company;
			_group = group;

			_user = user;

			_parametes = HashMapBuilder.put(
				"addDepotGroupIds",
				new String[] {
					Arrays.stream(
						Optional.ofNullable(
							addDepotGroupIds
						).orElse(
							new long[0]
						)
					).mapToObj(
						String::valueOf
					).collect(
						Collectors.joining()
					)
				}
			).put(
				"deleteDepotGroupIds",
				new String[] {
					Arrays.stream(
						Optional.ofNullable(
							deleteGroupIds
						).orElse(
							new long[0]
						)
					).mapToObj(
						String::valueOf
					).collect(
						Collectors.joining()
					)
				}
			).put(
				"p_u_i_d", new String[] {String.valueOf(_user.getUserId())}
			).build();
		}

		@Override
		public Object getAttribute(String name) {
			if (Objects.equals(name, WebKeys.THEME_DISPLAY)) {
				try {
					return _getThemeDisplay();
				}
				catch (Exception exception) {
					throw new AssertionError(exception);
				}
			}

			return null;
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.setParameters(getParameterMap());

			return mockHttpServletRequest;
		}

		@Override
		public String getParameter(String name) {
			return Optional.ofNullable(
				_parametes.get(name)
			).map(
				parameter -> parameter[0]
			).orElse(
				null
			);
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return _parametes;
		}

		private ThemeDisplay _getThemeDisplay() throws Exception {
			ThemeDisplay themeDisplay = new ThemeDisplay();

			themeDisplay.setCompany(_company);

			themeDisplay.setUser(_user);

			themeDisplay.setScopeGroupId(_group.getGroupId());

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

			themeDisplay.setPermissionChecker(permissionChecker);

			return themeDisplay;
		}

		private final Company _company;
		private final Group _group;
		private final Map<String, String[]> _parametes;
		private final User _user;

	}

}