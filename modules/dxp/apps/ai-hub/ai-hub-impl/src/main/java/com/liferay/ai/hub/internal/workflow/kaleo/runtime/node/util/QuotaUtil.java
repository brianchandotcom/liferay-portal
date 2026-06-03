/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.ai.hub.quota.QuotaManager;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.workflow.kaleo.runtime.constants.WorkflowInstanceDestinationNames;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class QuotaUtil {

	public static boolean hasExceededQuota(
			long companyId, String nodeName, QuotaManager quotaManager,
			String text, long userId, Map<String, Serializable> workflowContext,
			long workflowInstanceId)
		throws PortalException {

		try {
			quotaManager.checkUsage(companyId, userId);

			return false;
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Message message = new Message();

			message.put("exception", unsupportedOperationException);
			message.put("workflowInstanceId", workflowInstanceId);

			MessageBusUtil.sendMessage(
				WorkflowInstanceDestinationNames.WORKFLOW_INSTANCE, message);

			SseUtil.send(
				unsupportedOperationException.getMessage(),
				GetterUtil.getString(workflowContext.get("outBoundEventName")),
				nodeName,
				GetterUtil.getString(workflowContext.get("sseEventSinkKey")));
		}

		return true;
	}

}