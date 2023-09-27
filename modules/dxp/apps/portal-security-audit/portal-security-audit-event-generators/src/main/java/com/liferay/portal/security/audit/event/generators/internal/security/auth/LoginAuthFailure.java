/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.event.generators.internal.security.auth;

import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AuthFailure;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 * @author Stian Sigvartsen
 */
@Component(property = "key=auth.failure", service = AuthFailure.class)
public class LoginAuthFailure implements AuthFailure {

	@Override
	public void onFailureByEmailAddress(
		long companyId, String emailAddress, Map<String, String[]> headerMap,
		Map<String, String[]> parameterMap) {

		try {
			User user = _userLocalService.getUserByEmailAddress(
				companyId, emailAddress);

			AuditMessage auditMessage = _buildAuditMessage(
				user, emailAddress, headerMap,
				"Failed to authenticate by email address");

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

	@Override
	public void onFailureByScreenName(
		long companyId, String screenName, Map<String, String[]> headerMap,
		Map<String, String[]> parameterMap) {

		try {
			User user = _userLocalService.getUserByScreenName(
				companyId, screenName);

			AuditMessage auditMessage = _buildAuditMessage(
				user, screenName, headerMap,
				"Failed to authenticate by screen name");

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

	@Override
	public void onFailureByUserId(
		long companyId, long userId, Map<String, String[]> headerMap,
		Map<String, String[]> parameterMap) {

		try {
			User user = _userLocalService.getUserById(companyId, userId);

			AuditMessage auditMessage = _buildAuditMessage(
				user, String.valueOf(userId), headerMap,
				"Failed to authenticate by user ID");

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

	private AuditMessage _buildAuditMessage(
		User user, String userLogin, Map<String, String[]> headerMap,
		String reason) {

		JSONObject additionalInfoJSONObject = _jsonFactory.createJSONObject();

		additionalInfoJSONObject.put(
			"headers", _jsonFactory.serialize(headerMap)
		).put(
			"reason", reason
		);

		AuditMessage auditMessage = new AuditMessage(
			EventTypes.LOGIN_FAILURE, user.getCompanyId(), user.getUserId(),
			user.getFullName(), User.class.getName(),
			String.valueOf(user.getPrimaryKey()), null,
			additionalInfoJSONObject);

		auditMessage.setUserLogin(userLogin);

		return auditMessage;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoginAuthFailure.class);

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private UserLocalService _userLocalService;

}