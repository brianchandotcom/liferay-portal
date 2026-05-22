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
			companyId, userId, "Nonexistent",
			JSONUtil.put("reason", "Nonexistent User"), checkerClassName,
			String.valueOf(userId),
			MFAFIDO2EventTypes.MFA_FIDO2_VERIFICATION_FAILURE, null);
	}

	public AuditMessage buildNotVerifiedAuditMessage(
		User user, String checkerClassName, String reason) {

		return new AuditMessage(
			user.getCompanyId(), user.getUserId(), user.getFullName(),
			JSONUtil.put("reason", reason), checkerClassName,
			String.valueOf(user.getPrimaryKey()),
			MFAFIDO2EventTypes.MFA_FIDO2_NOT_VERIFIED, null);
	}

	public AuditMessage buildUnconfiguredUserVerificationFailureAuditMessage(
		long companyId, User user, String checkerClassName) {

		return new AuditMessage(
			companyId, user.getUserId(), "Unconfigured",
			JSONUtil.put("reason", "Unconfigured for User"), checkerClassName,
			null, MFAFIDO2EventTypes.MFA_FIDO2_VERIFICATION_FAILURE, null);
	}

	public AuditMessage buildVerificationFailureAuditMessage(
		User user, String checkerClassName, String reason) {

		return new AuditMessage(
			user.getCompanyId(), user.getUserId(), user.getFullName(),
			JSONUtil.put("reason", reason), checkerClassName,
			String.valueOf(user.getPrimaryKey()),
			MFAFIDO2EventTypes.MFA_FIDO2_VERIFICATION_FAILURE, null);
	}

	public AuditMessage buildVerifiedAuditMessage(
		User user, String checkerClassName) {

		return new AuditMessage(
			user.getCompanyId(), user.getUserId(), user.getFullName(), null,
			checkerClassName, String.valueOf(user.getPrimaryKey()),
			MFAFIDO2EventTypes.MFA_FIDO2_VERIFIED, null);
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