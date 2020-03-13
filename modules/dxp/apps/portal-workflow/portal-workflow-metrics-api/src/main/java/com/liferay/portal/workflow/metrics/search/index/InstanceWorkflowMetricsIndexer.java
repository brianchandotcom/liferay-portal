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

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public interface InstanceWorkflowMetricsIndexer {

	public Document addInstance(
		Map<Locale, String> assetTitleMap, Map<Locale, String> assetTypeMap,
		String className, long classPK, long companyId, Date completionDate,
		Date createDate, long instanceId, Date modifiedDate, long processId,
		String processVersion, long userId, String userName);

	public Document completeInstance(
		long companyId, Date completionDate, long duration, long instanceId,
		Date modifiedDate);

	public void deleteInstance(long companyId, long instanceId);

	public Document updateInstance(
		Map<Locale, String> assetTitleMap, Map<Locale, String> assetTypeMap,
		long companyId, long instanceId, Date modifiedDate);

}