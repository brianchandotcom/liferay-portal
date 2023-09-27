/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.batch.engine;

import java.util.Set;

/**
 * @author Javier de Arcos
 */
public interface VulcanBatchEngineTaskItemDelegateRegistry {

	public Set<String> getEntityClassNames(long companyId);

	public VulcanBatchEngineTaskItemDelegate
		getVulcanBatchEngineTaskItemDelegate(
			long companyId, String entityClassName);

	public boolean isBatchPlannerExportEnabled(
		long companyId, String entityClassName);

	public boolean isBatchPlannerImportEnabled(
		long companyId, String entityClassName);

}