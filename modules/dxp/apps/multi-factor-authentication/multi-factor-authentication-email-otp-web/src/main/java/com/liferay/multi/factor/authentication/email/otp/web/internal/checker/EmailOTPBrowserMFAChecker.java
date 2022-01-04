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

import com.liferay.multi.factor.authentication.email.otp.configuration.MFAEmailOTPConfiguration;
import com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry;
import com.liferay.multi.factor.authentication.email.otp.service.MFAEmailOTPEntryLocalService;
import com.liferay.multi.factor.authentication.email.otp.web.internal.audit.MFAEmailOTPAuditMessageBuilder;
import com.liferay.multi.factor.authentication.email.otp.web.internal.constants.MFAEmailOTPWebKeys;
import com.liferay.multi.factor.authentication.spi.checker.browser.BrowserMFAChecker;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Arthur Chan
 * @author Marta Medio
 */
@Component(
	configurationPid = "com.liferay.multi.factor.authentication.email.otp.configuration.MFAEmailOTPConfiguration.scoped",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	service = BrowserMFAChecker.class
)
public class EmailOTPBrowserMFAChecker implements BrowserMFAChecker {

	@Override
	public void includeBrowserVerification(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long userId)
		throws IOException, ServletException {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested one-time password email verification for " +
						"nonexistent user " + userId);
			}

			return;
		}

		if (_isMaximumAllowedAttemptsReached(user.getUserId())) {
			httpServletRequest.setAttribute(
				MFAEmailOTPWebKeys.MFA_EMAIL_OTP_FAILED_ATTEMPTS_RETRY_TIMEOUT,
				_mfaEmailOTPConfiguration.retryTimeout());
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		httpServletRequest.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_SEND_TO_ADDRESS_OBFUSCATED,
			obfuscateEmailAddress(user.getEmailAddress()));
		httpServletRequest.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_SET_AT_TIME,
			GetterUtil.getLong(
				httpSession.getAttribute(
					MFAEmailOTPWebKeys.MFA_EMAIL_OTP_SET_AT_TIME),
				Long.MIN_VALUE));

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/mfa_email_otp_checker/verify_browser.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);

		httpSession.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_PHASE, "verify");
		httpSession.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_USER_ID, userId);
	}

	@Override
	public boolean isBrowserVerified(
		HttpServletRequest httpServletRequest, long userId) {

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession(false);

		if (_isVerified(httpSession, userId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean verifyBrowserRequest(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long userId)
		throws Exception {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested one-time password email verification for " +
						"nonexistent user " + userId);
			}

			_routeAuditMessage(
				_mfaEmailOTPAuditMessageBuilder.
					buildNonexistentUserVerificationFailureAuditMessage(
						CompanyThreadLocal.getCompanyId(), userId,
						_getClassName()));

			return false;
		}

		MFAEmailOTPEntry mfaEmailOTPEntry =
			_mfaEmailOTPEntryLocalService.fetchMFAEmailOTPEntryByUserId(userId);

		if (mfaEmailOTPEntry == null) {
			_mfaEmailOTPEntryLocalService.addMFAEmailOTPEntry(userId);
		}

		if (_isMaximumAllowedAttemptsReached(userId)) {
			_routeAuditMessage(
				_mfaEmailOTPAuditMessageBuilder.
					buildVerificationFailureAuditMessage(
						user, _getClassName(),
						"Reached maximum allowed attempts"));

			return false;
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

			_routeAuditMessage(
				_mfaEmailOTPAuditMessageBuilder.
					buildVerificationSuccessAuditMessage(
						user, _getClassName()));

			return true;
		}

		_routeAuditMessage(
			_mfaEmailOTPAuditMessageBuilder.
				buildVerificationFailureAuditMessage(
					user, _getClassName(),
					"Incorrect email one-time password"));

		_mfaEmailOTPEntryLocalService.updateAttempts(
			userId, originalHttpServletRequest.getRemoteAddr(), false);

		return false;
	}

	protected static String obfuscateEmailAddress(String emailAddress) {
		String alias = emailAddress.substring(0, emailAddress.indexOf('@'));

		int maskLength = Math.max(
			(int)Math.ceil(alias.length() / 2.0), Math.min(3, alias.length()));

		int startIndex = (int)Math.ceil((alias.length() - maskLength) / 2.0);

		int endIndex = startIndex + maskLength;

		char[] chars = emailAddress.toCharArray();

		for (int i = startIndex; i < endIndex; i++) {
			chars[i] = '*';
		}

		return new String(chars);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_mfaEmailOTPConfiguration = ConfigurableUtil.createConfigurable(
			MFAEmailOTPConfiguration.class, properties);

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

	private String _getClassName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	private boolean _isMaximumAllowedAttemptsReached(long userId) {
		try {
			MFAEmailOTPEntry mfaEmailOTPEntry =
				_mfaEmailOTPEntryLocalService.fetchMFAEmailOTPEntryByUserId(
					userId);

			if (mfaEmailOTPEntry == null) {
				return false;
			}

			if ((_mfaEmailOTPConfiguration.failedAttemptsAllowed() >= 0) &&
				(_mfaEmailOTPConfiguration.failedAttemptsAllowed() <=
					mfaEmailOTPEntry.getFailedAttempts()) &&
				(_mfaEmailOTPConfiguration.retryTimeout() >= 0)) {

				Date lastFailDate = mfaEmailOTPEntry.getLastFailDate();

				long time =
					(_mfaEmailOTPConfiguration.retryTimeout() * Time.SECOND) +
						lastFailDate.getTime();

				if (time <= System.currentTimeMillis()) {
					_mfaEmailOTPEntryLocalService.resetFailedAttempts(userId);
				}
				else {
					return true;
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return false;
	}

	private boolean _isVerified(HttpSession httpSession, long userId) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested one-time password email verification for " +
						"nonexistent user " + userId);
			}

			_routeAuditMessage(
				_mfaEmailOTPAuditMessageBuilder.
					buildNonexistentUserVerificationFailureAuditMessage(
						CompanyThreadLocal.getCompanyId(), userId,
						_getClassName()));

			return false;
		}

		if (httpSession == null) {
			_routeAuditMessage(
				_mfaEmailOTPAuditMessageBuilder.buildNotVerifiedAuditMessage(
					user, _getClassName(), "Empty session"));

			return false;
		}

		Object mfaEmailOTPValidatedUserId = httpSession.getAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_USER_ID);

		if (mfaEmailOTPValidatedUserId == null) {
			_routeAuditMessage(
				_mfaEmailOTPAuditMessageBuilder.buildNotVerifiedAuditMessage(
					user, _getClassName(), "Not verified yet"));

			return false;
		}

		if (!Objects.equals(mfaEmailOTPValidatedUserId, userId)) {
			_routeAuditMessage(
				_mfaEmailOTPAuditMessageBuilder.buildNotVerifiedAuditMessage(
					user, _getClassName(), "Not the same user"));

			return false;
		}

		return true;
	}

	private void _routeAuditMessage(AuditMessage auditMessage) {
		if (_mfaEmailOTPAuditMessageBuilder != null) {
			_mfaEmailOTPAuditMessageBuilder.routeAuditMessage(auditMessage);
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
		EmailOTPBrowserMFAChecker.class);

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	private MFAEmailOTPAuditMessageBuilder _mfaEmailOTPAuditMessageBuilder;

	private MFAEmailOTPConfiguration _mfaEmailOTPConfiguration;

	@Reference
	private MFAEmailOTPEntryLocalService _mfaEmailOTPEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.multi.factor.authentication.email.otp.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}