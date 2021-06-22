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
public interface ProcessWorkflowMetricsIndexer {

	public Document addProcess(
		boolean active, long companyId, Date createDate, String description,
		Date modifiedDate, String name, long processId, String title,
		Map<Locale, String> titleMap, String version);

	public Document addProcess(
		boolean active, long companyId, Date createDate, String description,
		Date modifiedDate, String name, long processId, String title,
		Map<Locale, String> titleMap, String version, String[] versions);

	public void deleteProcess(long companyId, long processId);

	public Document updateProcess(
		Boolean active, long companyId, String description, Date modifiedDate,
		long processId, String title, Map<Locale, String> titleMap,
		String version);

}