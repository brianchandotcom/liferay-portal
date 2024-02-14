/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.Queries;

/**
 * @author Rafael Praxedes
 */
public interface WorkflowMetricsIndex {

	public static String getIndexName(
		IndexNameBuilder indexNameBuilder, String indexNameSuffix,
		long companyId) {

		return indexNameBuilder.getIndexName(companyId) + indexNameSuffix;
	}

	public boolean createIndex(
			SearchCapabilities searchCapabilities,
			SearchEngineAdapter searchEngineAdapter,
			IndexNameBuilder indexNameBuilder, long companyId)
		throws PortalException;

	public boolean deleteAllDocuments(
			SearchCapabilities searchCapabilities,
			SearchEngineAdapter searchEngineAdapter, Queries queries,
			IndexNameBuilder indexNameBuilder, long companyId)
		throws PortalException;

	public boolean removeIndex(
			SearchCapabilities searchCapabilities,
			SearchEngineAdapter searchEngineAdapter,
			IndexNameBuilder indexNameBuilder, long companyId)
		throws PortalException;

}