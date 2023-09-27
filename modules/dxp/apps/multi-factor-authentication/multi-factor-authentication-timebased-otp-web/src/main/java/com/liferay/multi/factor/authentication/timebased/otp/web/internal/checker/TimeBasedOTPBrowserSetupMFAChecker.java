/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.timebased.otp.web.internal.checker;

import com.liferay.multi.factor.authentication.spi.checker.browser.BrowserMFAChecker;
import com.liferay.multi.factor.authentication.spi.checker.setup.SetupMFAChecker;
import com.liferay.multi.factor.authentication.timebased.otp.model.MFATimeBasedOTPEntry;
import com.liferay.multi.factor.authentication.timebased.otp.service.MFATimeBasedOTPEntryLocalService;
import com.liferay.multi.factor.authentication.timebased.otp.web.internal.configuration.MFATimeBasedOTPConfiguration;
import com.liferay.multi.factor.authentication.timebased.otp.web.internal.constants.MFATimeBasedOTPEventTypes;
import com.liferay.multi.factor.authentication.timebased.otp.web.internal.constants.MFATimeBasedOTPWebKeys;
import com.liferay.multi.factor.authentication.timebased.otp.web.internal.util.MFATimeBasedOTPUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @author Marta Medio
 */
@Component(
	configurationPid = "com.liferay.multi.factor.authentication.timebased.otp.web.internal.configuration.MFATimeBasedOTPConfiguration.scoped",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class TimeBasedOTPBrowserSetupMFAChecker
	implements BrowserMFAChecker, SetupMFAChecker {

	@Override
	public void includeBrowserVerification(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long userId)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/mfa_timebased_otp_checker/verify_browser.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	@Override
	public void includeSetup(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long userId)
		throws Exception {

		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry =
			_mfaTimeBasedOTPEntryLocalService.fetchMFATimeBasedOTPEntryByUserId(
				userId);

		if (mfaTimeBasedOTPEntry != null) {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/mfa_timebased_otp_checker/setup_completed.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		else {
			Company company = _portal.getCompany(httpServletRequest);
			String mfaTimeBasedOTPSharedSecret =
				MFATimeBasedOTPUtil.generateSharedSecret(
					_mfaTimeBasedOTPConfiguration.algorithmKeySize());

			httpServletRequest.setAttribute(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_ALGORITHM, "SHA1");
			httpServletRequest.setAttribute(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_COMPANY_NAME,
				company.getName());
			httpServletRequest.setAttribute(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_DIGITS,
				MFATimeBasedOTPUtil.MFA_TIMEBASED_OTP_DIGITS);
			httpServletRequest.setAttribute(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_SHARED_SECRET,
				mfaTimeBasedOTPSharedSecret);
			httpServletRequest.setAttribute(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_TIME_COUNTER,
				MFATimeBasedOTPUtil.MFA_TIMEBASED_OTP_COUNTER);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/mfa_timebased_otp_checker/setup.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);

			HttpServletRequest originalHttpServletRequest =
				_portal.getOriginalServletRequest(httpServletRequest);

			HttpSession httpSession = originalHttpServletRequest.getSession();

			httpSession.setAttribute(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_SHARED_SECRET,
				mfaTimeBasedOTPSharedSecret);
		}
	}

	@Override
	public boolean isAvailable(long userId) {
		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry =
			_mfaTimeBasedOTPEntryLocalService.fetchMFATimeBasedOTPEntryByUserId(
				userId);

		if (mfaTimeBasedOTPEntry != null) {
			return true;
		}

		return false;
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
	public void removeExistingSetup(long userId) {
		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry =
			_mfaTimeBasedOTPEntryLocalService.fetchMFATimeBasedOTPEntryByUserId(
				userId);

		if (mfaTimeBasedOTPEntry != null) {
			_mfaTimeBasedOTPEntryLocalService.deleteMFATimeBasedOTPEntry(
				mfaTimeBasedOTPEntry);
		}
	}

	@Override
	public boolean setUp(HttpServletRequest httpServletRequest, long userId) {
		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		String mfaTimeBasedOTPSharedSecret = (String)httpSession.getAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_SHARED_SECRET);

		String mfaTimeBasedOTP = ParamUtil.getString(
			httpServletRequest, "mfaTimeBasedOTP");

		try {
			if (MFATimeBasedOTPUtil.verifyTimeBasedOTP(
					_mfaTimeBasedOTPConfiguration.clockSkew(),
					mfaTimeBasedOTPSharedSecret, mfaTimeBasedOTP)) {

				MFATimeBasedOTPEntry timeBasedOTPEntry =
					_mfaTimeBasedOTPEntryLocalService.addTimeBasedOTPEntry(
						userId, mfaTimeBasedOTPSharedSecret);

				if (timeBasedOTPEntry != null) {
					return true;
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(
				StringBundler.concat(
					"Unable to generate time-based one-time password for user ",
					userId, ": ", portalException.getMessage()),
				portalException);
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
					"Requested one-time password time-based verification for " +
						"nonexistent user " + userId);
			}

			_mfaTimeBasedOTPAuditMessageBuilder.routeAuditMessage(
				_mfaTimeBasedOTPAuditMessageBuilder.
					buildNonexistentUserVerificationFailureAuditMessage(
						CompanyThreadLocal.getCompanyId(), userId,
						_getClassName()));

			return false;
		}

		if (!isAvailable(user.getUserId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested time-based one time password for user" + userId +
						" with incomplete configuration");
			}

			_mfaTimeBasedOTPAuditMessageBuilder.routeAuditMessage(
				_mfaTimeBasedOTPAuditMessageBuilder.
					buildUnconfiguredUserVerificationFailureAuditMessage(
						CompanyThreadLocal.getCompanyId(), user,
						_getClassName()));

			return false;
		}

		String mfaTimeBasedOTP = ParamUtil.getString(
			httpServletRequest, "mfaTimeBasedOTP");

		if (Validator.isBlank(mfaTimeBasedOTP)) {
			return false;
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String remoteAddress = originalHttpServletRequest.getRemoteAddr();

		if (_verify(mfaTimeBasedOTP, user.getUserId())) {
			HttpSession httpSession = originalHttpServletRequest.getSession();

			httpSession.setAttribute(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_VALIDATED_AT_TIME,
				System.currentTimeMillis());
			httpSession.setAttribute(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_VALIDATED_USER_ID,
				userId);

			_mfaTimeBasedOTPEntryLocalService.updateAttempts(
				userId, remoteAddress, true);

			_mfaTimeBasedOTPAuditMessageBuilder.routeAuditMessage(
				_mfaTimeBasedOTPAuditMessageBuilder.
					buildVerificationSuccessAuditMessage(
						user, _getClassName()));

			return true;
		}

		_mfaTimeBasedOTPEntryLocalService.updateAttempts(
			user.getUserId(), remoteAddress, false);

		_mfaTimeBasedOTPAuditMessageBuilder.routeAuditMessage(
			_mfaTimeBasedOTPAuditMessageBuilder.
				buildVerificationFailureAuditMessage(
					user, _getClassName(),
					"Incorrect time-based one-time password"));

		return false;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_mfaTimeBasedOTPConfiguration = ConfigurableUtil.createConfigurable(
			MFATimeBasedOTPConfiguration.class, properties);

		if (!_mfaTimeBasedOTPConfiguration.enabled()) {
			return;
		}

		if (PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {
			List<String> sessionPhishingProtectedAttributes = new ArrayList<>(
				Arrays.asList(
					PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES));

			sessionPhishingProtectedAttributes.add(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_VALIDATED_AT_TIME);
			sessionPhishingProtectedAttributes.add(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_VALIDATED_USER_ID);

			PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES =
				sessionPhishingProtectedAttributes.toArray(new String[0]);
		}

		_serviceRegistration = bundleContext.registerService(
			new String[] {
				BrowserMFAChecker.class.getName(),
				SetupMFAChecker.class.getName()
			},
			this, new HashMapDictionary<>(properties));
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration == null) {
			return;
		}

		_serviceRegistration.unregister();

		if (PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {
			List<String> sessionPhishingProtectedAttributes = new ArrayList<>(
				Arrays.asList(
					PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES));

			sessionPhishingProtectedAttributes.remove(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_VALIDATED_AT_TIME);
			sessionPhishingProtectedAttributes.remove(
				MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_VALIDATED_USER_ID);

			PropsValues.SESSION_PHISHING_PROTECTED_ATTRIBUTES =
				sessionPhishingProtectedAttributes.toArray(new String[0]);
		}
	}

	private String _getClassName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	private boolean _isVerified(HttpSession httpSession, long userId) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Requested one-time password email verification for " +
						"nonexistent user " + userId);
			}

			_mfaTimeBasedOTPAuditMessageBuilder.routeAuditMessage(
				_mfaTimeBasedOTPAuditMessageBuilder.
					buildNonexistentUserVerificationFailureAuditMessage(
						CompanyThreadLocal.getCompanyId(), userId,
						_getClassName()));

			return false;
		}

		if (httpSession == null) {
			_mfaTimeBasedOTPAuditMessageBuilder.routeAuditMessage(
				_mfaTimeBasedOTPAuditMessageBuilder.
					buildNotVerifiedAuditMessage(
						user, _getClassName(), "Empty session"));

			return false;
		}

		Object mfaTimeBasedOTPValidatedUserId = httpSession.getAttribute(
			MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_VALIDATED_USER_ID);

		if (mfaTimeBasedOTPValidatedUserId == null) {
			_mfaTimeBasedOTPAuditMessageBuilder.routeAuditMessage(
				_mfaTimeBasedOTPAuditMessageBuilder.
					buildNotVerifiedAuditMessage(
						user, _getClassName(), "Not verified yet"));

			return false;
		}

		if (!Objects.equals(mfaTimeBasedOTPValidatedUserId, userId)) {
			_mfaTimeBasedOTPAuditMessageBuilder.routeAuditMessage(
				_mfaTimeBasedOTPAuditMessageBuilder.
					buildNotVerifiedAuditMessage(
						user, _getClassName(), "Not the same user"));

			return false;
		}

		return true;
	}

	private boolean _verify(String timeBasedOtpValue, long userId) {
		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry =
			_mfaTimeBasedOTPEntryLocalService.fetchMFATimeBasedOTPEntryByUserId(
				userId);

		if (mfaTimeBasedOTPEntry != null) {
			return MFATimeBasedOTPUtil.verifyTimeBasedOTP(
				_mfaTimeBasedOTPConfiguration.clockSkew(),
				mfaTimeBasedOTPEntry.getSharedSecret(), timeBasedOtpValue);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TimeBasedOTPBrowserSetupMFAChecker.class);

	private final MFATimeBasedOTPAuditMessageBuilder
		_mfaTimeBasedOTPAuditMessageBuilder =
			new MFATimeBasedOTPAuditMessageBuilder();
	private MFATimeBasedOTPConfiguration _mfaTimeBasedOTPConfiguration;

	@Reference
	private MFATimeBasedOTPEntryLocalService _mfaTimeBasedOTPEntryLocalService;

	@Reference
	private Portal _portal;

	private ServiceRegistration<?> _serviceRegistration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.multi.factor.authentication.timebased.otp.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

	private class MFATimeBasedOTPAuditMessageBuilder {

		public AuditMessage buildNonexistentUserVerificationFailureAuditMessage(
			long companyId, long userId, String checkerClassName) {

			return new AuditMessage(
				MFATimeBasedOTPEventTypes.
					MFA_TIMEBASED_OTP_VERIFICATION_FAILURE,
				companyId, userId, "Nonexistent", checkerClassName,
				String.valueOf(userId), null,
				JSONUtil.put("reason", "Nonexistent User"));
		}

		public AuditMessage buildNotVerifiedAuditMessage(
			User user, String checkerClassName, String reason) {

			return new AuditMessage(
				MFATimeBasedOTPEventTypes.MFA_TIMEBASED_OTP_NOT_VERIFIED,
				user.getCompanyId(), user.getUserId(), user.getFullName(),
				checkerClassName, String.valueOf(user.getPrimaryKey()), null,
				JSONUtil.put("reason", reason));
		}

		public AuditMessage
			buildUnconfiguredUserVerificationFailureAuditMessage(
				long companyId, User user, String checkerClassName) {

			return new AuditMessage(
				MFATimeBasedOTPEventTypes.
					MFA_TIMEBASED_OTP_VERIFICATION_FAILURE,
				companyId, user.getUserId(), "Unconfigured", checkerClassName,
				null, null, JSONUtil.put("reason", "Unconfigured for User"));
		}

		public AuditMessage buildVerificationFailureAuditMessage(
			User user, String checkerClassName, String reason) {

			return new AuditMessage(
				MFATimeBasedOTPEventTypes.
					MFA_TIMEBASED_OTP_VERIFICATION_FAILURE,
				user.getCompanyId(), user.getUserId(), user.getFullName(),
				checkerClassName, String.valueOf(user.getPrimaryKey()), null,
				JSONUtil.put("reason", reason));
		}

		public AuditMessage buildVerificationSuccessAuditMessage(
			User user, String checkerClassName) {

			return new AuditMessage(
				MFATimeBasedOTPEventTypes.
					MFA_TIMEBASED_OTP_VERIFICATION_SUCCESS,
				user.getCompanyId(), user.getUserId(), user.getFullName(),
				checkerClassName, String.valueOf(user.getPrimaryKey()), null,
				null);
		}

		public AuditMessage buildVerifiedAuditMessage(
			User user, String checkerClassName) {

			return new AuditMessage(
				MFATimeBasedOTPEventTypes.MFA_TIMEBASED_OTP_VERIFIED,
				user.getCompanyId(), user.getUserId(), user.getFullName(),
				checkerClassName, String.valueOf(user.getPrimaryKey()), null,
				null);
		}

		public void routeAuditMessage(AuditMessage auditMessage) {
			try {
				AuditRouterUtil.route(auditMessage);
			}
			catch (AuditException auditException) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to route audit message", auditException);
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		private final Log _log = LogFactoryUtil.getLog(
			MFATimeBasedOTPAuditMessageBuilder.class);

	}

}