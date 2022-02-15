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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.UserResourceDTOConverter;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.captcha.CaptchaSettings;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Pei-Jung Lan
 */
@PrepareForTest({CaptchaUtil.class, UsersAdminUtil.class})
@RunWith(PowerMockRunner.class)
public class UserAccountResourceImplWhenPostingUserAccountAsGuestTest
	extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		mockStatic(CaptchaUtil.class);
		mockStatic(UsersAdminUtil.class);

		_setUpUserAccountResourceImpl();
	}

	@Test
	public void testShouldCallCaptcha() throws Exception {
		when(
			_captchaSettings.isCreateAccountCaptchaEnabled()
		).thenReturn(
			true
		);

		_userAccountResourceImpl.postUserAccount(mock(UserAccount.class));

		verifyStatic();
		CaptchaUtil.check(Mockito.any(HttpServletRequest.class));
	}

	@Test
	public void testShouldNotCallCaptcha() throws Exception {
		when(
			_captchaSettings.isCreateAccountCaptchaEnabled()
		).thenReturn(
			false
		);

		_userAccountResourceImpl.postUserAccount(mock(UserAccount.class));

		verifyZeroInteractions(CaptchaUtil.class);
	}

	@Test(expected = CaptchaException.class)
	public void testShouldThrowCaptchaException() throws Exception {
		when(
			_captchaSettings.isCreateAccountCaptchaEnabled()
		).thenReturn(
			true
		);

		when(
			CaptchaUtil.class, "check", Matchers.anyObject()
		).thenThrow(
			CaptchaException.class
		);

		_userAccountResourceImpl.postUserAccount(mock(UserAccount.class));
	}

	private void _setUpUserAccountResourceImpl() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_userAccountResourceImpl, "_captchaSettings", _captchaSettings);

		ReflectionTestUtil.setFieldValue(
			_userAccountResourceImpl, "contextAcceptLanguage",
			mock(AcceptLanguage.class));

		ReflectionTestUtil.setFieldValue(
			_userAccountResourceImpl, "contextCompany", mock(Company.class));

		User user = mock(User.class);

		when(
			user.isDefaultUser()
		).thenReturn(
			true
		);

		ReflectionTestUtil.setFieldValue(
			_userAccountResourceImpl, "contextUser", user);

		PermissionCheckerFactory permissionCheckerFactory = mock(
			PermissionCheckerFactory.class);

		when(
			permissionCheckerFactory.create(Mockito.any(User.class))
		).thenReturn(
			null
		);

		ReflectionTestUtil.setFieldValue(
			_userAccountResourceImpl, "_permissionCheckerFactory",
			permissionCheckerFactory);

		ReflectionTestUtil.setFieldValue(
			_userAccountResourceImpl, "_userResourceDTOConverter",
			mock(UserResourceDTOConverter.class));

		UserService userService = mock(UserService.class);

		when(
			userService.addUser(
				Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyString(),
				Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(),
				Mockito.any(long[].class), Mockito.any(long[].class),
				Mockito.any(long[].class), Mockito.any(long[].class),
				Mockito.anyBoolean(), Mockito.any(ServiceContext.class))
		).thenReturn(
			mock(User.class)
		);

		ReflectionTestUtil.setFieldValue(
			_userAccountResourceImpl, "_userService", userService);
	}

	@Mock
	private CaptchaSettings _captchaSettings;

	private final UserAccountResourceImpl _userAccountResourceImpl =
		new UserAccountResourceImpl();

}