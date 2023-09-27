/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport;

/**
 * @author Marcos Martins
 */
public interface AnalyticsDXPEntityBatchExporter {

	public void export(long companyId, String[] dispatchTriggerNames)
		throws Exception;

	public void refreshExportTriggers(
			long companyId, String[] dispatchTriggerNames)
		throws Exception;

	public void scheduleExportTriggers(
			long companyId, String[] dispatchTriggerNames)
		throws Exception;

	public void unscheduleExportTriggers(
			long companyId, String[] dispatchTriggerNames)
		throws Exception;

}