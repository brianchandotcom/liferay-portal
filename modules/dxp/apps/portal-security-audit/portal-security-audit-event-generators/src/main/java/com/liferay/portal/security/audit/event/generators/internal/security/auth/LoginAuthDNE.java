/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.event.generators.internal.security.auth;

import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AuthDNE;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nikoletta Buza
 */
@Component(service = AuthDNE.class)
public class LoginAuthDNE implements AuthDNE {

	@Override
	public void onDoesNotExist(
		long companyId, String authType, String login,
		Map<String, String[]> headerMap, Map<String, String[]> parameterMap) {

		try {
			AuditMessage auditMessage = new AuditMessage(
				EventTypes.LOGIN_DNE, companyId, 0, null, User.class.getName(),
				"0", null,
				JSONUtil.put(
					"authType", authType
				).put(
					"headers", _jsonFactory.serialize(headerMap)
				).put(
					"reason", "User does not exist"
				));

			auditMessage.setUserLogin(login);

			if (auditMessage == null) {
				return;
			}

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

	private static final Log _log = LogFactoryUtil.getLog(LoginAuthDNE.class);

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private JSONFactory _jsonFactory;

}