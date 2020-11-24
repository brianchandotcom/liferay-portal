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

package com.liferay.multi.factor.authentication.fido2.web.internal.audit;

import com.liferay.multi.factor.authentication.fido2.web.internal.constants.MFAFIDO2EventTypes;
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
@Component(service = MFAFIDO2AuditMessageBuilder.class)
public class MFAFIDO2AuditMessageBuilder {

	public AuditMessage buildNonexistentUserVerificationFailureAuditMessage(
		long companyId, long userId, String checkerClassName) {

		return new AuditMessage(
			MFAFIDO2EventTypes.MFA_FIDO2_VERIFICATION_FAILURE, companyId,
			userId, "Nonexistent", checkerClassName, String.valueOf(userId),
			null, JSONUtil.put("reason", "Nonexistent User"));
	}

	public AuditMessage buildNotVerifiedAuditMessage(
		User user, String checkerClassName, String reason) {

		return new AuditMessage(
			MFAFIDO2EventTypes.MFA_FIDO2_NOT_VERIFIED, user.getCompanyId(),
			user.getUserId(), user.getFullName(), checkerClassName,
			String.valueOf(user.getPrimaryKey()), null,
			JSONUtil.put("reason", reason));
	}

	public AuditMessage buildUnconfiguredUserVerificationFailureAuditMessage(
		long companyId, User user, String checkerClassName) {

		return new AuditMessage(
			MFAFIDO2EventTypes.MFA_FIDO2_VERIFICATION_FAILURE, companyId,
			user.getUserId(), "Unconfigured", checkerClassName, null, null,
			JSONUtil.put("reason", "Unconfigured for User"));
	}

	public AuditMessage buildVerificationFailureAuditMessage(
		User user, String checkerClassName, String reason) {

		return new AuditMessage(
			MFAFIDO2EventTypes.MFA_FIDO2_VERIFICATION_FAILURE,
			user.getCompanyId(), user.getUserId(), user.getFullName(),
			checkerClassName, String.valueOf(user.getPrimaryKey()), null,
			JSONUtil.put("reason", reason));
	}

	public AuditMessage buildVerifiedAuditMessage(
		User user, String checkerClassName) {

		return new AuditMessage(
			MFAFIDO2EventTypes.MFA_FIDO2_VERIFIED, user.getCompanyId(),
			user.getUserId(), user.getFullName(), checkerClassName,
			String.valueOf(user.getPrimaryKey()), null, null);
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
				_log.debug(exception, exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MFAFIDO2AuditMessageBuilder.class);

	@Reference
	private AuditRouter _auditRouter;

}