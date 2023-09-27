/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.query.BooleanQuery;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseSLAWorkflowMetricsIndexer
	extends BaseWorkflowMetricsIndexer {

	public void deleteDocuments(long companyId, long instanceId) {
		BooleanQuery booleanQuery = queries.booleanQuery();

		_deleteDocuments(
			companyId,
			booleanQuery.addMustQueryClauses(
				queries.term("companyId", companyId),
				queries.term("instanceId", instanceId)));
	}

	public void deleteDocuments(
		long companyId, long processId, long slaDefinitionId) {

		BooleanQuery booleanQuery = queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(
			queries.term("instanceCompleted", Boolean.TRUE));

		_deleteDocuments(
			companyId,
			booleanQuery.addMustQueryClauses(
				queries.term("companyId", companyId),
				queries.term("processId", processId),
				queries.term("slaDefinitionId", slaDefinitionId)));
	}

	private void _deleteDocuments(long companyId, BooleanQuery booleanQuery) {
		updateDocuments(
			companyId,
			HashMapBuilder.<String, Object>put(
				"deleted", Boolean.TRUE
			).build(),
			booleanQuery);
	}

}