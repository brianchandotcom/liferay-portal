/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAttribute(
			"oAuthClientEntryId", RandomTestUtil.randomLong());

		Assert.assertNull(
			_userGroupLocalService.fetchUserGroup(
				TestPropsValues.getCompanyId(), "group1"));

		_testProcessUserInfo(
			email, serviceContext, new String[] {"group1"}, uuid);

		Assert.assertNotNull(
			_userGroupLocalService.fetchUserGroup(
				TestPropsValues.getCompanyId(), "group1"));

		_userGroupLocalService.addUserGroup(
			StringPool.BLANK, TestPropsValues.getUserId(),
			TestPropsValues.getCompanyId(), "group2", StringPool.BLANK,
			serviceContext);

		_testProcessUserInfo(
			email, serviceContext, new String[] {"group1", "group2"}, uuid);
	}

	private void _testProcessUserInfo(
			String emailAddress, ServiceContext serviceContext,
			String[] userGroupNames, String uuid)
		throws Exception {

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

		User user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), emailAddress);

		Assert.assertEquals(emailAddress, user.getEmailAddress());
		Assert.assertEquals(userId, user.getUserId());
		Assert.assertEquals(
			userGroupNames.length,
			_userGroupLocalService.getUserUserGroupsCount(user.getUserId()));
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