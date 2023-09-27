/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.router.internal;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.security.audit.formatter.LogMessageFormatter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(property = "format=JSON", service = LogMessageFormatter.class)
public class JSONLogMessageFormatter implements LogMessageFormatter {

	@Override
	public String format(AuditMessage auditMessage) {
		JSONObject jsonObject = auditMessage.toJSONObject();

		return jsonObject.toString();
	}

}