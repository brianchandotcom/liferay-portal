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

package com.liferay.multi.factor.authentication.timebased.otp.web.internal.audit;

import com.liferay.multi.factor.authentication.timebased.otp.web.internal.constants.MFATimeBasedOTPEventTypes;
import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(service = MFATimeBasedOTPAuditMessageBuilder.class)
public class MFATimeBasedOTPAuditMessageBuilder {

	public AuditMessage buildNonexistentUserVerificationFailureAuditMessage(
		long companyId, long userId, String checkerClassName) {

		return new AuditMessage(
			MFATimeBasedOTPEventTypes.MFA_TIMEBASED_OTP_VERIFICATION_FAILURE,
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

	public AuditMessage buildUnconfiguredUserVerificationFailureAuditMessage(
		long companyId, User user, String checkerClassName) {

		return new AuditMessage(
			MFATimeBasedOTPEventTypes.MFA_TIMEBASED_OTP_VERIFICATION_FAILURE,
			companyId, user.getUserId(), "Unconfigured", checkerClassName, null,
			null, JSONUtil.put("reason", "Unconfigured for User"));
	}

	public AuditMessage buildVerificationFailureAuditMessage(
		User user, String checkerClassName, String reason) {

		return new AuditMessage(
			MFATimeBasedOTPEventTypes.MFA_TIMEBASED_OTP_VERIFICATION_FAILURE,
			user.getCompanyId(), user.getUserId(), user.getFullName(),
			checkerClassName, String.valueOf(user.getPrimaryKey()), null,
			JSONUtil.put("reason", reason));
	}

	public AuditMessage buildVerificationSuccessAuditMessage(
		User user, String checkerClassName) {

		return new AuditMessage(
			MFATimeBasedOTPEventTypes.MFA_TIMEBASED_OTP_VERIFICATION_SUCCESS,
			user.getCompanyId(), user.getUserId(), user.getFullName(),
			checkerClassName, String.valueOf(user.getPrimaryKey()), null, null);
	}

	public AuditMessage buildVerifiedAuditMessage(
		User user, String checkerClassName) {

		return new AuditMessage(
			MFATimeBasedOTPEventTypes.MFA_TIMEBASED_OTP_VERIFIED,
			user.getCompanyId(), user.getUserId(), user.getFullName(),
			checkerClassName, String.valueOf(user.getPrimaryKey()), null, null);
	}

	public void routeAuditMessage(AuditMessage auditMessage) {
		try {
			_auditRouter.route(auditMessage);
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

	private static final Log _log = LogFactoryUtil.getLog(
		MFATimeBasedOTPAuditMessageBuilder.class);

	@Reference
	private AuditRouter _auditRouter;

}