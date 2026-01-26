/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class WorkflowContextUtil {

	public static Map<String, Serializable> toWorkflowContext(
			Map<String, ?> context, HttpServletRequest httpServletRequest,
			String sseEventSinkKey)
		throws PortalException {

		Map<String, Serializable> workflowContext =
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
				ServiceContextFactory.getInstance(httpServletRequest)
			).put(
				"sseEventSinkKey", sseEventSinkKey
			).build();

		if (context == null) {
			return workflowContext;
		}

		for (Map.Entry<String, ?> entry : context.entrySet()) {
			if (entry.getValue() instanceof Serializable value) {
				workflowContext.put(entry.getKey(), value);
			}
		}

		return workflowContext;
	}

}