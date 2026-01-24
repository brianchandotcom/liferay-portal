/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent.util;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
public class WorkflowAgentUtil {

	public static void complete(
		Map<String, Serializable> workflowContext, long workflowInstanceId) {

		CompletableFuture<Map<String, Serializable>> completableFuture =
			_completableFutures.get(workflowInstanceId);

		if (completableFuture == null) {
			return;
		}

		completableFuture.complete(workflowContext);
	}

	public static String getOutput(WorkflowInstance workflowInstance)
		throws Exception {

		CompletableFuture<Map<String, Serializable>> completableFuture =
			new CompletableFuture<>();

		_completableFutures.put(
			workflowInstance.getWorkflowInstanceId(), completableFuture);

		return MapUtil.getString(
			completableFuture.get(15, TimeUnit.SECONDS), "output");
	}

	private static final ConcurrentMap
		<Long, CompletableFuture<Map<String, Serializable>>>
			_completableFutures = new ConcurrentHashMap<>();

}