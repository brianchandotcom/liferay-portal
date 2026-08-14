/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.notifications;

import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Luis Ortiz
 */
public class PortalInstancesNotificationPayload {

	public static final String COMPANY_ID = "companyId";

	public static final String ERROR_MESSAGE = "errorMessage";

	public static final String OPERATION_TYPE = "operationType";

	public static final String SCHEMA_NAME = "schemaName";

	public static final String STATUS = "status";

	public static final String WEB_ID = "webId";

	public static JSONObject build(
		long companyId, String errorMessage,
		PortalInstancesOperationType portalInstancesOperationType,
		String schemaName, int status, String webId) {

		return JSONUtil.put(
			COMPANY_ID, companyId
		).put(
			ERROR_MESSAGE, errorMessage
		).put(
			OPERATION_TYPE, portalInstancesOperationType.getValue()
		).put(
			SCHEMA_NAME, schemaName
		).put(
			STATUS, BackgroundTaskConstants.getStatusLabel(status)
		).put(
			WEB_ID, webId
		);
	}

}