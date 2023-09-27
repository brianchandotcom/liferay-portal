/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				_log.debug(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MFAFIDO2AuditMessageBuilder.class);

	@Reference
	private AuditRouter _auditRouter;

}