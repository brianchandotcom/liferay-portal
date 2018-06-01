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

package com.liferay.portal.security.audit.router.internal;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.security.audit.formatter.LogMessageFormatter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true, property = "format=JSON",
	service = LogMessageFormatter.class
)
public class JSONLogMessageFormatter implements LogMessageFormatter {

	@Override
	public String format(AuditMessage auditMessage) {
		JSONObject jsonObject = auditMessage.toJSONObject();

		return jsonObject.toString();
	}

}