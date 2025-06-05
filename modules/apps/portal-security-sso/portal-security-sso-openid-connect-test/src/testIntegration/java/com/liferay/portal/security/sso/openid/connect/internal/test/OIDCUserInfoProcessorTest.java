/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge García Jiménez
 */
@RunWith(Arquillian.class)
public class OIDCUserInfoProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testProcessUserInfo() throws Exception {
		String emailAddress = StringUtil.toLowerCase(
			RandomTestUtil.randomString() + "@liferay.com");
		long oAuthClientEntryId = RandomTestUtil.randomLong();
		String uuid = PortalUUIDUtil.generate();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAttribute("oAuthClientEntryId", oAuthClientEntryId);

		_testProcessUserInfo(
			emailAddress, new String[0], oAuthClientEntryId, serviceContext,
			new String[0], uuid);
		_testProcessUserInfo(
			emailAddress, new String[] {"group1"}, oAuthClientEntryId,
			serviceContext, new String[] {"group1"}, uuid);

		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			StringPool.BLANK, TestPropsValues.getUserId(),
			TestPropsValues.getCompanyId(), "group2", StringPool.BLANK,
			serviceContext);

		User user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), emailAddress);

		_userGroupLocalService.addUserUserGroups(
			user.getUserId(), new long[] {userGroup.getUserGroupId()});

		_testProcessUserInfo(
			emailAddress, new String[] {"group1", "group2", "group3"},
			oAuthClientEntryId, serviceContext,
			new String[] {"group1", "group3"}, uuid);
		_testProcessUserInfo(
			emailAddress, new String[] {"group1", "group2"}, oAuthClientEntryId,
			serviceContext, new String[] {"group1"}, uuid);
	}

	private void _assertExpandoValue(
			String className, long classPK, long oAuthClientEntryId)
		throws Exception {

		ExpandoTable expandoTable = _expandoTableLocalService.getTable(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(className),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		ExpandoColumn expandoColumn = _expandoColumnLocalService.getColumn(
			expandoTable.getTableId(), "idpId");

		ExpandoValue expandoValue = _expandoValueLocalService.getValue(
			expandoColumn.getTableId(), expandoColumn.getColumnId(), classPK);

		Assert.assertNotNull(expandoValue);
		Assert.assertEquals(oAuthClientEntryId, expandoValue.getLong());
	}

	private void _testProcessUserInfo(
			String emailAddress, String[] expectedUserGroupNames,
			long oAuthClientEntryId, ServiceContext serviceContext,
			String[] userGroupNames, String uuid)
		throws Exception {

		boolean newUser = true;
		User user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), emailAddress);

		if (user != null) {
			newUser = false;
		}

		List<String> newUserGroupNames = new ArrayList<>();

		for (String userGroupName : userGroupNames) {
			UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
				TestPropsValues.getCompanyId(), userGroupName);

			if (userGroup != null) {
				continue;
			}

			newUserGroupNames.add(userGroupName);
		}

		long userId = ReflectionTestUtil.invoke(
			_oidcUserInfoProcessor, "processUserInfo",
			new Class<?>[] {
				long.class, String.class, ServiceContext.class, String.class,
				String.class
			},
			TestPropsValues.getCompanyId(), StringUtil.randomString(),
			serviceContext,
			JSONUtil.put(
				"birthdate", String.valueOf(RandomTestUtil.nextDate())
			).put(
				"email", emailAddress
			).put(
				"email_verified", true
			).put(
				"family_name", StringUtil.randomString()
			).put(
				"given_name", StringUtil.randomString()
			).put(
				"groups", userGroupNames
			).put(
				"middle_name", StringUtil.randomString()
			).put(
				"name", StringUtil.randomString()
			).put(
				"preferred_username", StringUtil.randomString()
			).put(
				"sub", uuid
			).toString(),
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);

		user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), emailAddress);

		Assert.assertEquals(emailAddress, user.getEmailAddress());
		Assert.assertEquals(userId, user.getUserId());
		Assert.assertEquals(
			expectedUserGroupNames.length,
			_userGroupLocalService.getUserUserGroupsCount(user.getUserId()));

		List<UserGroup> userUserGroups =
			_userGroupLocalService.getUserUserGroups(user.getUserId());

		for (UserGroup userUserGroup : userUserGroups) {
			Assert.assertTrue(
				ArrayUtil.contains(
					expectedUserGroupNames, userUserGroup.getName()));
		}

		if (newUser) {
			_assertExpandoValue(
				User.class.getName(), userId, oAuthClientEntryId);
		}

		for (String userGroupName : newUserGroupNames) {
			UserGroup userGroup = _userGroupLocalService.getUserGroup(
				TestPropsValues.getCompanyId(), userGroupName);

			_assertExpandoValue(
				UserGroup.class.getName(), userGroup.getUserGroupId(),
				oAuthClientEntryId);
		}
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private ExpandoValueLocalService _expandoValueLocalService;

	@Inject(
		filter = "component.name=com.liferay.portal.security.sso.openid.connect.internal.OIDCUserInfoProcessor",
		type = Inject.NoType.class
	)
	private Object _oidcUserInfoProcessor;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserLocalService _userLocalService;

}