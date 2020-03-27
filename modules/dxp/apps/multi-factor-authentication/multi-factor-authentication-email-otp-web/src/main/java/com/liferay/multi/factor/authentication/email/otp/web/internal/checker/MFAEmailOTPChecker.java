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
import com.liferay.multi.factor.authentication.email.otp.web.internal.audit.MFAEmailOTPAuditMessageBuilder;
import com.liferay.multi.factor.authentication.email.otp.web.internal.configuration.MFAEmailOTPConfiguration;
import com.liferay.multi.factor.authentication.email.otp.web.internal.constants.MFAEmailOTPWebKeys;
import com.liferay.multi.factor.authentication.email.otp.web.internal.system.configuration.MFAEmailOTPSystemConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Time;
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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Arthur Chan
 */
@Component(
	configurationPid = "com.liferay.multi.factor.authentication.email.otp.web.internal.system.configuration.MFAEmailOTPSystemConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = {}
)
public class MFAEmailOTPChecker {

	public void includeBrowserVerification(
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

			return;
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		httpServletRequest.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_CONFIGURATION,
			_getMFAEmailOTPConfiguration(userId));
		httpServletRequest.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_SEND_TO_ADDRESS,
			user.getEmailAddress());
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
				(mfaEmailOTPConfiguration.retryTimeout() * Time.SECOND) +
					lastFailDate.getTime();

			if (time <= System.currentTimeMillis()) {
				_mfaEmailOTPEntryLocalService.resetFailedAttempts(userId);
			}
			else {
				_routeAuditMessage(
					_mfaEmailOTPAuditMessageBuilder.
						buildVerificationFailureAuditMessage(
							user, _getClassName(),
							"Reached maximum allowed attempts"));

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

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		MFAEmailOTPSystemConfiguration mfaEmailOTPSystemConfiguration =
			ConfigurableUtil.createConfigurable(
				MFAEmailOTPSystemConfiguration.class, properties);

		if (mfaEmailOTPSystemConfiguration.disableGlobally()) {
			if (_serviceRegistration != null) {
				deactivate();
			}

			return;
		}

		if (_serviceRegistration != null) {
			return;
		}

		_serviceRegistration = bundleContext.registerService(
			MFAEmailOTPChecker.class, this,
			new HashMapDictionary<>(properties));

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
		if (_serviceRegistration == null) {
			return;
		}

		_serviceRegistration.unregister();

		_serviceRegistration = null;

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

		Object mfaEmailOTPValidatedUserID = httpSession.getAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_USER_ID);

		if (mfaEmailOTPValidatedUserID == null) {
			_routeAuditMessage(
				_mfaEmailOTPAuditMessageBuilder.buildNotVerifiedAuditMessage(
					user, _getClassName(), "Not verified yet"));

			return false;
		}

		if (!Objects.equals(mfaEmailOTPValidatedUserID, userId)) {
			_routeAuditMessage(
				_mfaEmailOTPAuditMessageBuilder.buildNotVerifiedAuditMessage(
					user, _getClassName(), "Not the same user"));

			return false;
		}

		MFAEmailOTPConfiguration mfaEmailOTPConfiguration =
			_getMFAEmailOTPConfiguration(userId);

		if (mfaEmailOTPConfiguration.validationExpirationTime() < 0) {
			return true;
		}

		long time =
			mfaEmailOTPConfiguration.validationExpirationTime() * Time.SECOND;

		time += (long)httpSession.getAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_VALIDATED_AT_TIME);

		if (time > System.currentTimeMillis()) {
			_routeAuditMessage(
				_mfaEmailOTPAuditMessageBuilder.buildVerifiedAuditMessage(
					user, _getClassName()));

			return true;
		}

		_routeAuditMessage(
			_mfaEmailOTPAuditMessageBuilder.buildNotVerifiedAuditMessage(
				user, _getClassName(), "Expired verification"));

		return false;
	}

	private String _getClassName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	private MFAEmailOTPConfiguration _getMFAEmailOTPConfiguration(long userId) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			throw new IllegalStateException(
				"Requested one-time password email verification for " +
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
		MFAEmailOTPChecker.class);

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	private MFAEmailOTPAuditMessageBuilder _mfaEmailOTPAuditMessageBuilder;

	@Reference
	private MFAEmailOTPEntryLocalService _mfaEmailOTPEntryLocalService;

	@Reference
	private Portal _portal;

	private volatile ServiceRegistration<MFAEmailOTPChecker>
		_serviceRegistration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.multi.factor.authentication.email.otp.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}