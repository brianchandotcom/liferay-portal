/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.search.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.workflow.metrics.model.Assignment;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public interface TaskWorkflowMetricsIndexer {

	public Document addTask(
		Map<Locale, String> assetTitleMap, Map<Locale, String> assetTypeMap,
		List<Assignment> assignments, String className, long classPK,
		long companyId, boolean completed, Date completionDate,
		Long completionUserId, Date createDate, boolean instanceCompleted,
		Date instanceCompletionDate, long instanceId, Date modifiedDate,
		String name, long nodeId, long processId, String processVersion,
		long taskId, long userId);

	public Document completeTask(
		long companyId, Date completionDate, long completionUserId,
		long duration, Date modifiedDate, long taskId, long userId);

	public void deleteTask(long companyId, long taskId);

	public Document updateTask(
		Map<Locale, String> assetTitleMap, Map<Locale, String> assetTypeMap,
		List<Assignment> assignments, long companyId, Date modifiedDate,
		long taskId, long userId);

}