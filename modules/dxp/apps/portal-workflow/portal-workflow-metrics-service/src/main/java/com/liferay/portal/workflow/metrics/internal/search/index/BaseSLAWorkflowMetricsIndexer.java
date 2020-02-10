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

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.query.BooleanQuery;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseSLAWorkflowMetricsIndexer
	extends BaseWorkflowMetricsIndexer {

	public void deleteDocuments(long companyId, long instanceId) {
		BooleanQuery booleanQuery = queries.booleanQuery();

		_deleteDocuments(
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
			booleanQuery.addMustQueryClauses(
				queries.term("companyId", companyId),
				queries.term("processId", processId),
				queries.term("slaDefinitionId", slaDefinitionId)));
	}

	private void _deleteDocuments(BooleanQuery booleanQuery) {
		updateDocuments(
			document -> new DocumentImpl() {
				{
					addKeyword("deleted", true);
					addKeyword(Field.UID, document.getString(Field.UID));
				}
			},
			booleanQuery);
	}

}