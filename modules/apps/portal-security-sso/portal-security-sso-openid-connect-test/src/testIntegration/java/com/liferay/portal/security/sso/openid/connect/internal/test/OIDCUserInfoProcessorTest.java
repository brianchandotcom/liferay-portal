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
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_emailAddress = StringUtil.toLowerCase(
			RandomTestUtil.randomString() + "@liferay.com");
		_oAuthClientEntryId = RandomTestUtil.randomLong();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		_serviceContext.setAttribute("oAuthClientEntryId", _oAuthClientEntryId);

		_uuid = PortalUUIDUtil.generate();
	}

	@Test
	public void testProcessUserInfo() throws Exception {
		_testProcessUserInfo(new String[0], new String[0]);
		_testProcessUserInfo(new String[] {"group1"}, new String[] {"group1"});

		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			StringPool.BLANK, TestPropsValues.getUserId(),
			TestPropsValues.getCompanyId(), "group2", StringPool.BLANK,
			_serviceContext);

		User user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), _emailAddress);

		_userGroupLocalService.addUserUserGroups(
			user.getUserId(), new long[] {userGroup.getUserGroupId()});

		_testProcessUserInfo(
			new String[] {"group1", "group2", "group3"},
			new String[] {"group1", "group3"});
		_testProcessUserInfo(
			new String[] {"group1", "group2"}, new String[] {"group1"});
	}

	private void _assertExpandoValue(String className, long classPK)
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
		Assert.assertEquals(_oAuthClientEntryId, expandoValue.getLong());
	}

	private void _testProcessUserInfo(
			String[] expectedUserGroupNames, String[] userGroupNames)
		throws Exception {

		boolean newUser = true;
		User user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), _emailAddress);

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
			_serviceContext,
			JSONUtil.put(
				"birthdate", String.valueOf(RandomTestUtil.nextDate())
			).put(
				"email", _emailAddress
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
				"sub", _uuid
			).toString(),
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);

		user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), _emailAddress);

		Assert.assertEquals(_emailAddress, user.getEmailAddress());
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

		for (String expectedUserGroupName : expectedUserGroupNames) {
			Assert.assertNotNull(
				_userGroupLocalService.fetchUserGroup(
					TestPropsValues.getCompanyId(), expectedUserGroupName));
		}

		if (newUser) {
			_assertExpandoValue(User.class.getName(), userId);
		}

		for (String userGroupName : newUserGroupNames) {
			UserGroup userGroup = _userGroupLocalService.getUserGroup(
				TestPropsValues.getCompanyId(), userGroupName);

			_assertExpandoValue(
				UserGroup.class.getName(), userGroup.getUserGroupId());
		}
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private String _emailAddress;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private ExpandoValueLocalService _expandoValueLocalService;

	private long _oAuthClientEntryId;

	@Inject(
		filter = "component.name=com.liferay.portal.security.sso.openid.connect.internal.OIDCUserInfoProcessor",
		type = Inject.NoType.class
	)
	private Object _oidcUserInfoProcessor;

	private ServiceContext _serviceContext;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserLocalService _userLocalService;

	private String _uuid;

}