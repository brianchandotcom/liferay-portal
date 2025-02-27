/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.timebased.otp.web.internal.display.context;

import com.liferay.multi.factor.authentication.timebased.otp.web.internal.constants.MFATimeBasedOTPWebKeys;
import com.liferay.multi.factor.authentication.timebased.otp.web.internal.util.MFATimeBasedOTPUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Christian Moura
 */
public class MFATimeBasedOTPCheckerDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws PortalException {
		_setupMockCompany();
		_setupMockDisplays();
		_setupMockUser();

		_setupMockHttpServletRequest();
	}

	@Test
	public void testGetContext() throws PortalException {
		MFATimeBasedOTPCheckerDisplayContext displayContext =
			new MFATimeBasedOTPCheckerDisplayContext(_mockHttpServletRequest);

		Map<String, Object> context = displayContext.getContext();

		Assert.assertEquals(
			_mfaTimeBasedOTPSharedSecret, context.get("secret"));
		Assert.assertEquals(
			MFATimeBasedOTPUtil.MFA_TIMEBASED_OTP_DIGITS,
			context.get("digits"));
		Assert.assertEquals(
			MFATimeBasedOTPUtil.MFA_TIMEBASED_OTP_COUNTER,
			context.get("counter"));
		Assert.assertEquals(_mockCompany.getName(), context.get("issuer"));
		Assert.assertEquals(
			_mockUser.getEmailAddress(), context.get("account"));
		Assert.assertEquals("SHA1", context.get("algorithm"));
		Assert.assertEquals("test-namespaceqrcode", context.get("containerId"));
	}

	private static void _setupMockCompany() {
		_mockCompany = Mockito.mock(Company.class);

		Mockito.when(
			_mockCompany.getName()
		).thenReturn(
			"Liferay DXP"
		);
	}

	private static void _setupMockDisplays() {
		PortletDisplay mockPortletDisplay = Mockito.mock(PortletDisplay.class);
		_mockThemeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			mockPortletDisplay.getNamespace()
		).thenReturn(
			"test-namespace"
		);

		Mockito.when(
			_mockThemeDisplay.getPortletDisplay()
		).thenReturn(
			mockPortletDisplay
		);
	}

	private static void _setupMockHttpServletRequest() {
		_mockHttpServletRequest = new MockHttpServletRequest();

		_mfaTimeBasedOTPSharedSecret = MFATimeBasedOTPUtil.generateSharedSecret(
			20);

		_mockHttpServletRequest.setAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_ALGORITHM, "SHA1");
		_mockHttpServletRequest.setAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_COMPANY_NAME,
			_mockCompany.getName());
		_mockHttpServletRequest.setAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_DIGITS,
			MFATimeBasedOTPUtil.MFA_TIMEBASED_OTP_DIGITS);
		_mockHttpServletRequest.setAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_SHARED_SECRET,
			_mfaTimeBasedOTPSharedSecret);
		_mockHttpServletRequest.setAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_TIME_COUNTER,
			MFATimeBasedOTPUtil.MFA_TIMEBASED_OTP_COUNTER);
		_mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _mockThemeDisplay);
	}

	private static void _setupMockUser() throws PortalException {
		_mockUser = Mockito.mock(User.class);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(Mockito.mock(Portal.class));

		Mockito.when(
			PortalUtil.getSelectedUser(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			_mockUser
		);

		Mockito.when(
			_mockUser.getEmailAddress()
		).thenReturn(
			"test@liferay.com"
		);
	}

	private static String _mfaTimeBasedOTPSharedSecret;
	private static Company _mockCompany;
	private static HttpServletRequest _mockHttpServletRequest;
	private static ThemeDisplay _mockThemeDisplay;
	private static User _mockUser;

}