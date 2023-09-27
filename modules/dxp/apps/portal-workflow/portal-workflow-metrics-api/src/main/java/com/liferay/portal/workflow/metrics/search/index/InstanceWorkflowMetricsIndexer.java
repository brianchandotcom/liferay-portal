/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		boolean active, Map<Locale, String> assetTitleMap,
		Map<Locale, String> assetTypeMap, long companyId, long instanceId,
		Date modifiedDate);

}