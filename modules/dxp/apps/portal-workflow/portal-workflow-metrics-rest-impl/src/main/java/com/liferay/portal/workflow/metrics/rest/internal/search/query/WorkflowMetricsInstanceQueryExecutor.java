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

package com.liferay.portal.workflow.metrics.rest.internal.search.query;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = WorkflowMetricsInstanceQueryExecutor.class
)
public class WorkflowMetricsInstanceQueryExecutor extends BaseQueryExecutor {

	@Override
	public String getIndexName() {
		return "workflow-metrics-instances";
	}

	public int searchCount(long companyId, List<Long> processIds) {
		BooleanFilter preBooleanFilter = new BooleanFilter();

		preBooleanFilter.addRequiredTerm("companyId", companyId);
		preBooleanFilter.addRequiredTerm("complete", false);
		preBooleanFilter.addRequiredTerm("deleted", false);

		TermsFilter processIdsTermsFilter = new TermsFilter("processId");

		for (Long processId : processIds) {
			processIdsTermsFilter.addValue(String.valueOf(processId));
		}

		preBooleanFilter.add(processIdsTermsFilter, BooleanClauseOccur.MUST);

		return searchCount(createBooleanQuery(preBooleanFilter));
	}

}