/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.email.otp.web.internal.checker;

import com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry;
import com.liferay.multi.factor.authentication.email.otp.service.MFAEmailOTPEntryLocalService;
import com.liferay.multi.factor.authentication.email.otp.web.internal.configuration.MFAEmailOTPConfiguration;
import com.liferay.multi.factor.authentication.email.otp.web.internal.constants.MFAEmailOTPWebKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(service = MFAEmailOTPChecker.class)
public class MFAEmailOTPChecker {

	public void includeBrowserVerification(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long userId)
		throws Exception {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested one-time password email verification for a " +
						"nonexistent user " + userId);
			}

			return;
		}

		httpServletRequest.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_CONFIGURATION,
			_getMFAEmailOTPConfiguration(userId));
		httpServletRequest.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_SEND_TO_ADDRESS,
			user.getEmailAddress());

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/mfa_email_otp_checker/verify_browser.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		httpSession.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_PHASE, "verify");
		httpSession.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_USER_ID, userId);
	}

	public boolean isBrowserVerified(
		HttpServletRequest httpServletRequest, long userId) {

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession(false);

		if (isVerified(httpSession, userId)) {
			return true;
		}

		return false;
	}

	public boolean verifyBrowserRequest(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long userId)
		throws Exception {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested one-time password email verification for a " +
						"nonexistent user " + userId);
			}

			return false;
		}

		MFAEmailOTPEntry mfaEmailOTPEntry =
			_mfaEmailOTPEntryLocalService.fetchMFAEmailOTPEntryByUserId(userId);

		if (mfaEmailOTPEntry == null) {
			mfaEmailOTPEntry =
				_mfaEmailOTPEntryLocalService.addMFAEmailOTPEntry(userId);
		}

		MFAEmailOTPConfiguration mfaEmailOTPConfiguration =
			_getMFAEmailOTPConfiguration(userId);

		if ((mfaEmailOTPConfiguration.failedAttemptsAllowed() >= 0) &&
			(mfaEmailOTPConfiguration.failedAttemptsAllowed() <=
				mfaEmailOTPEntry.getFailedAttempts()) &&
			(mfaEmailOTPConfiguration.retryTimeout() >= 0)) {

			Date lastFailDate = mfaEmailOTPEntry.getLastFailDate();

			long time =
				mfaEmailOTPConfiguration.retryTimeout() +
					lastFailDate.getTime();

			if (time <= System.currentTimeMillis()) {
				_mfaEmailOTPEntryLocalService.resetFailedAttempts(userId);
			}
			else {
				return false;
			}
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		String otp = ParamUtil.getString(httpServletRequest, "otp");

		if (_verify(httpSession, otp)) {
			httpSession.setAttribute(
				MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_AT_TIME,
				System.currentTimeMillis());
			httpSession.setAttribute(
				MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_USER_ID, userId);

			_mfaEmailOTPEntryLocalService.updateAttempts(
				userId, originalHttpServletRequest.getRemoteAddr(), true);

			return true;
		}

		_mfaEmailOTPEntryLocalService.updateAttempts(
			userId, originalHttpServletRequest.getRemoteAddr(), false);

		return false;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		if (!PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {
			return;
		}

		List<String> sessionPhishingProtectedAttributes = new ArrayList<>(
			Arrays.asList(PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES));

		sessionPhishingProtectedAttributes.add(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_AT_TIME);
		sessionPhishingProtectedAttributes.add(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_USER_ID);

		PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES =
			sessionPhishingProtectedAttributes.toArray(new String[0]);
	}

	@Deactivate
	protected void deactivate() {
		if (!PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {
			return;
		}

		List<String> sessionPhishingProtectedAttributes = new ArrayList<>(
			Arrays.asList(PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES));

		sessionPhishingProtectedAttributes.remove(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_AT_TIME);
		sessionPhishingProtectedAttributes.remove(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_USER_ID);

		PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES =
			sessionPhishingProtectedAttributes.toArray(new String[0]);
	}

	protected boolean isVerified(HttpSession httpSession, long userId) {
		if (httpSession == null) {
			return false;
		}

		if (!Objects.equals(
				httpSession.getAttribute(
					MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_USER_ID),
				userId)) {

			return false;
		}

		MFAEmailOTPConfiguration mfaEmailOTPConfiguration =
			_getMFAEmailOTPConfiguration(userId);

		if (mfaEmailOTPConfiguration.validationExpirationTime() < 0) {
			return true;
		}

		long mfaEmailOTPValidatedAtTime = (long)httpSession.getAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_AT_TIME);

		long time =
			(mfaEmailOTPConfiguration.validationExpirationTime() * 1000) +
				mfaEmailOTPValidatedAtTime;

		if (time > System.currentTimeMillis()) {
			return true;
		}

		return false;
	}

	private MFAEmailOTPConfiguration _getMFAEmailOTPConfiguration(long userId) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			throw new IllegalStateException(
				"Requested one-time password email verification for a " +
					"nonexistent user " + userId);
		}

		try {
			return ConfigurationProviderUtil.getCompanyConfiguration(
				MFAEmailOTPConfiguration.class, user.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			throw new IllegalStateException(configurationException);
		}
	}

	private boolean _verify(HttpSession httpSession, String otp) {
		String expectedMFAEmailOTP = (String)httpSession.getAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP);

		if ((expectedMFAEmailOTP == null) || !expectedMFAEmailOTP.equals(otp)) {
			return false;
		}

		httpSession.removeAttribute(MFAEmailOTPWebKeys.MFA_EMAIL_OTP);
		httpSession.removeAttribute(MFAEmailOTPWebKeys.MFA_EMAIL_OTP_PHASE);
		httpSession.removeAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_SET_AT_TIME);
		httpSession.removeAttribute(MFAEmailOTPWebKeys.MFA_EMAIL_OTP_USER_ID);

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MFAEmailOTPChecker.class);

	@Reference
	private MFAEmailOTPEntryLocalService _mfaEmailOTPEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.multi.factor.authentication.checker.email.otp.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}