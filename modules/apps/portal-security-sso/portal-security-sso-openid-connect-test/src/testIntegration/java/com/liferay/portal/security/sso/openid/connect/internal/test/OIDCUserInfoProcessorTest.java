/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
		String email = RandomTestUtil.randomString() + "@liferay.com";
		String uuid = PortalUUIDUtil.generate();

		String userInfoJSON = JSONUtil.put(
			"birthdate",
			RandomTestUtil.nextDate(
			).toString()
		).put(
			"email", email
		).put(
			"email_verified", true
		).put(
			"family_name", StringUtil.randomString()
		).put(
			"given_name", StringUtil.randomString()
		).put(
			"groups", new String[] {"group1"}
		).put(
			"middle_name", StringUtil.randomString()
		).put(
			"name", StringUtil.randomString()
		).put(
			"preferred_username", StringUtil.randomString()
		).put(
			"sub", uuid
		).toString();

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAttribute(
			"oAuthClientEntryId", RandomTestUtil.randomLong());

		UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
			group.getCompanyId(), "group1");

		Assert.assertNull(userGroup);

		long userId = ReflectionTestUtil.invoke(
			_oidcUserInfoProcessor, "processUserInfo",
			new Class<?>[] {
				long.class, String.class, ServiceContext.class, String.class,
				String.class
			},
			group.getCompanyId(), StringUtil.randomString(), serviceContext,
			userInfoJSON, OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);

		User user = _userLocalService.getUser(userId);

		userGroup = _userGroupLocalService.fetchUserGroup(
			group.getCompanyId(), "group1");

		Assert.assertNotNull(userGroup);

		Assert.assertEquals(
			1, _userGroupLocalService.getUserUserGroupsCount(user.getUserId()));

		userInfoJSON = JSONUtil.put(
			"birthdate",
			RandomTestUtil.nextDate(
			).toString()
		).put(
			"email", email
		).put(
			"email_verified", true
		).put(
			"family_name", StringUtil.randomString()
		).put(
			"given_name", StringUtil.randomString()
		).put(
			"groups", new String[] {"group1", "group2"}
		).put(
			"middle_name", StringUtil.randomString()
		).put(
			"name", StringUtil.randomString()
		).put(
			"preferred_username", StringUtil.randomString()
		).put(
			"sub", uuid
		).toString();

		userId = ReflectionTestUtil.invoke(
			_oidcUserInfoProcessor, "processUserInfo",
			new Class<?>[] {
				long.class, String.class, ServiceContext.class, String.class,
				String.class
			},
			group.getCompanyId(), StringUtil.randomString(), serviceContext,
			userInfoJSON, OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);

		user = _userLocalService.getUser(userId);

		userGroup = _userGroupLocalService.fetchUserGroup(
			group.getCompanyId(), "group2");

		Assert.assertNotNull(userGroup);

		Assert.assertEquals(
			2, _userGroupLocalService.getUserUserGroupsCount(user.getUserId()));
	}

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