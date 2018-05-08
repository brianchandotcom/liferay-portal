/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
import com.liferay.portal.security.audit.event.generators.internal.constants.EventTypes;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "key=auth.failure", service = AuthFailure.class
)
public class LoginFailure implements AuthFailure {

	@Override
	public void onFailureByEmailAddress(
		long companyId, String emailAddress, Map<String, String[]> headerMap,
		Map<String, String[]> parameterMap) {

		try {
			User user = _userLocalService.getUserByEmailAddress(
				companyId, emailAddress);

			AuditMessage auditMessage = buildAuditMessage(
				user, headerMap, "Failed to authenticate by email address");

			_auditRouter.route(auditMessage);
		}
		catch (AuditException ae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to route audit message", ae);
			}
		}
		catch (Exception e) {
		}
	}

	@Override
	public void onFailureByScreenName(
		long companyId, String screenName, Map<String, String[]> headerMap,
		Map<String, String[]> parameterMap) {

		try {
			User user = _userLocalService.getUserByScreenName(
				companyId, screenName);

			AuditMessage auditMessage = buildAuditMessage(
				user, headerMap, "Failed to authenticate by screen name");

			_auditRouter.route(auditMessage);
		}
		catch (AuditException ae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to route audit message", ae);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Override
	public void onFailureByUserId(
		long companyId, long userId, Map<String, String[]> headerMap,
		Map<String, String[]> parameterMap) {

		try {
			User user = _userLocalService.getUserById(companyId, userId);

			AuditMessage auditMessage = buildAuditMessage(
				user, headerMap, "Failed to authenticate by user ID");

			_auditRouter.route(auditMessage);
		}
		catch (AuditException ae) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to route audit message", ae);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	protected AuditMessage buildAuditMessage(
		User user, Map<String, String[]> headerMap, String reason) {

		JSONObject additionalInfoJSONObject = _jsonFactory.createJSONObject();

		additionalInfoJSONObject.put(
			"headers", _jsonFactory.serialize(headerMap));
		additionalInfoJSONObject.put("reason", reason);

		AuditMessage auditMessage = new AuditMessage(
			EventTypes.LOGIN_FAILURE, user.getCompanyId(), user.getUserId(),
			user.getFullName(), User.class.getName(),
			String.valueOf(user.getPrimaryKey()), null,
			additionalInfoJSONObject);

		return auditMessage;
	}

	private static final Log _log = LogFactoryUtil.getLog(LoginFailure.class);

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private UserLocalService _userLocalService;

}