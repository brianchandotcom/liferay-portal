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

package com.liferay.portal.workflow.reports.internal.search.query;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexConstants;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexerFieldNames;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = WorkflowInstanceQueryExecutor.class)
public class WorkflowInstanceQueryExecutor extends BaseQueryExecutor {

	@Override
	public String getIndexName() {
		return WorkflowIndexConstants.INDEX_NAME_WORKFLOW_INSTANCES;
	}

	public Hits search(
		long companyId, long processId, int start, int end, Sort... sorts) {

		BooleanFilter preBooleanFilter = createBooleanFilter(companyId);

		preBooleanFilter.addRequiredTerm(
			WorkflowIndexerFieldNames.PROCESS_ID, processId);

		return search(createBooleanQuery(preBooleanFilter), start, end, sorts);
	}

	public long searchCount(long companyId, List<Long> processIds) {
		BooleanFilter preBooleanFilter = createBooleanFilter(companyId);

		TermsFilter processIdsTermsFilter = new TermsFilter(
			WorkflowIndexerFieldNames.PROCESS_ID);

		for (Long processId : processIds) {
			processIdsTermsFilter.addValue(String.valueOf(processId));
		}

		preBooleanFilter.add(processIdsTermsFilter, BooleanClauseOccur.MUST);

		return searchCount(createBooleanQuery(preBooleanFilter));
	}

	public long searchCount(long companyId, long processId) {
		BooleanFilter preBooleanFilter = createBooleanFilter(companyId);

		preBooleanFilter.addRequiredTerm(
			WorkflowIndexerFieldNames.PROCESS_ID, processId);

		return searchCount(createBooleanQuery(preBooleanFilter));
	}

	protected BooleanFilter createBooleanFilter(long companyId) {
		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.addRequiredTerm(
			WorkflowIndexerFieldNames.COMPANY_ID, companyId);
		booleanFilter.addRequiredTerm(
			WorkflowIndexerFieldNames.COMPLETED, false);
		booleanFilter.addRequiredTerm(WorkflowIndexerFieldNames.DELETED, false);

		return booleanFilter;
	}

}