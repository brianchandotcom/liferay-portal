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

package com.liferay.portal.kernel.audit;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Date;

/**
 * @author Amos Fong
 */
public class AuditMessageFactoryUtil {

	public static AuditMessageFactory getAuditMessageFactory() {
		return _auditMessageFactory;
	}

	public AuditMessage getAuditMessage(String message) throws JSONException {
		return getAuditMessageFactory().getAuditMessage(message);
	}

	public AuditMessage getAuditMessage(
		String eventType, long companyId, long userId, String userName) {

		return getAuditMessageFactory().getAuditMessage(
			eventType, companyId, userId, userName);
	}

	public AuditMessage getAuditMessage(
		String eventType, long companyId, long userId, String userName,
		String className, String classPK) {

		return getAuditMessageFactory().getAuditMessage(
			eventType, companyId, userId, userName, className, classPK);
	}

	public AuditMessage getAuditMessage(
		String eventType, long companyId, long userId, String userName,
		String className, String classPK, String message) {

		return getAuditMessageFactory().getAuditMessage(
			eventType, companyId, userId, userName, className, classPK,
			message);
	}

	public AuditMessage getAuditMessage(
		String eventType, long companyId, long userId, String userName,
		String className, String classPK, String message, Date timestamp,
		JSONObject additionalInfo) {

		return getAuditMessageFactory().getAuditMessage(
			eventType, companyId, userId, userName, className, classPK, message,
			timestamp, additionalInfo);
	}

	public AuditMessage getAuditMessage(
		String eventType, long companyId, long userId, String userName,
		String className, String classPK, String message,
		JSONObject additionalInfo) {

		return getAuditMessageFactory().getAuditMessage(
			eventType, companyId, userId, userName, className, classPK, message,
			additionalInfo);
	}

	private static volatile AuditMessageFactory _auditMessageFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			AuditMessageFactory.class, AuditMessageFactoryUtil.class,
			"_auditMessageFactory", false);

}