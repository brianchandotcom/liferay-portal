/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.audit;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Date;

/**
 * @author Amos Fong
 */
public class AuditMessageFactory {

	public AuditMessage getAuditMessage(
		long groupId, long companyId, long userId, String userName,
		Date timestamp, JSONObject additionalInfoJSONObject, String className,
		String classPK, String eventType, String message) {

		return new AuditMessage(
			groupId, companyId, userId, userName, timestamp,
			additionalInfoJSONObject, className, classPK, eventType, message);
	}

	public AuditMessage getAuditMessage(
		long companyId, long userId, String userName, Date timestamp,
		JSONObject additionalInfoJSONObject, String className, String classPK,
		String eventType, String message) {

		return new AuditMessage(
			companyId, userId, userName, timestamp, additionalInfoJSONObject,
			className, classPK, eventType, message);
	}

	public AuditMessage getAuditMessage(
		long companyId, long userId, String userName,
		JSONObject additionalInfoJSONObject, String className, String classPK,
		String eventType, String message) {

		return new AuditMessage(
			companyId, userId, userName, additionalInfoJSONObject, className,
			classPK, eventType, message);
	}

	public AuditMessage getAuditMessage(
		long companyId, long userId, String userName, String eventType) {

		return new AuditMessage(companyId, userId, userName, eventType);
	}

	public AuditMessage getAuditMessage(
		long companyId, long userId, String userName, String className,
		String classPK, String eventType) {

		return new AuditMessage(
			companyId, userId, userName, className, classPK, eventType);
	}

	public AuditMessage getAuditMessage(
		long companyId, long userId, String userName, String className,
		String classPK, String eventType, String message) {

		return new AuditMessage(
			companyId, userId, userName, className, classPK, eventType,
			message);
	}

	public AuditMessage getAuditMessage(String message) throws JSONException {
		return new AuditMessage(message);
	}

}