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

	@Test
	public void testGetContext() throws PortalException {
		Company mockCompany = Mockito.mock(Company.class);
		HttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();
		PortletDisplay mockPortletDisplay = Mockito.mock(PortletDisplay.class);
		ThemeDisplay mockThemeDisplay = Mockito.mock(ThemeDisplay.class);

		User mockUser = Mockito.mock(User.class);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(Mockito.mock(Portal.class));

		Mockito.when(
			mockCompany.getName()
		).thenReturn(
			"Liferay DXP"
		);

		Mockito.when(
			PortalUtil.getSelectedUser(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			mockUser
		);

		Mockito.when(
			mockPortletDisplay.getNamespace()
		).thenReturn(
			"test-namespace"
		);

		Mockito.when(
			mockThemeDisplay.getPortletDisplay()
		).thenReturn(
			mockPortletDisplay
		);

		Mockito.when(
			mockUser.getEmailAddress()
		).thenReturn(
			"test@liferay.com"
		);

		String mfaTimeBasedOTPSharedSecret =
			MFATimeBasedOTPUtil.generateSharedSecret(20);

		mockHttpServletRequest.setAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_ALGORITHM, "SHA1");
		mockHttpServletRequest.setAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_COMPANY_NAME,
			mockCompany.getName());
		mockHttpServletRequest.setAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_DIGITS,
			MFATimeBasedOTPUtil.MFA_TIMEBASED_OTP_DIGITS);
		mockHttpServletRequest.setAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_SHARED_SECRET,
			mfaTimeBasedOTPSharedSecret);
		mockHttpServletRequest.setAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_TIME_COUNTER,
			MFATimeBasedOTPUtil.MFA_TIMEBASED_OTP_COUNTER);
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, mockThemeDisplay);

		MFATimeBasedOTPCheckerDisplayContext displayContext =
			new MFATimeBasedOTPCheckerDisplayContext(mockHttpServletRequest);

		Map<String, Object> context = displayContext.getContext();

		Assert.assertEquals(mfaTimeBasedOTPSharedSecret, context.get("secret"));
		Assert.assertEquals(
			MFATimeBasedOTPUtil.MFA_TIMEBASED_OTP_DIGITS,
			context.get("digits"));
		Assert.assertEquals(
			MFATimeBasedOTPUtil.MFA_TIMEBASED_OTP_COUNTER,
			context.get("counter"));
		Assert.assertEquals(mockCompany.getName(), context.get("issuer"));
		Assert.assertEquals(mockUser.getEmailAddress(), context.get("account"));
		Assert.assertEquals("SHA1", context.get("algorithm"));
		Assert.assertEquals("test-namespaceqrcode", context.get("containerId"));
	}

}