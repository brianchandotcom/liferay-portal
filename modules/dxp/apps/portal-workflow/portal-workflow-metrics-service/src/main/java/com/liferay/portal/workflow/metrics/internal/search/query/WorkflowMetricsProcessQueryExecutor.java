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

package com.liferay.portal.workflow.metrics.internal.search.query;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = WorkflowMetricsProcessQueryExecutor.class
)
public class WorkflowMetricsProcessQueryExecutor extends BaseQueryExecutor {

	@Override
	public String getIndexName() {
		return "workflow-metrics-processes";
	}

	public List<Long> getProcessIds(long companyId, String definitionName) {
		BooleanFilter preBooleanFilter = createBooleanFilter(companyId, true);

		preBooleanFilter.addRequiredTerm("name", definitionName);

		Facet processIdFacet = createFacet("processId");

		search(createBooleanQuery(preBooleanFilter), processIdFacet, 0, 0);

		FacetCollector processIdFacetCollector =
			processIdFacet.getFacetCollector();

		List<TermCollector> processIdTermCollectors =
			processIdFacetCollector.getTermCollectors();

		List<Long> processIds = new ArrayList<>(processIdTermCollectors.size());

		for (TermCollector processIdTermCollector : processIdTermCollectors) {
			processIds.add(
				GetterUtil.getLong(processIdTermCollector.getTerm()));
		}

		return processIds;
	}

	public Map<String, Hits> search(
		long companyId, String title, boolean active, int start, int size,
		String groupByFieldName, int groupBySize, Sort... groupdBySorts) {

		BooleanFilter preBooleanFilter = createBooleanFilter(companyId, active);

		BooleanQuery booleanQuery = createBooleanQuery(preBooleanFilter);

		try {
			if (Validator.isNotNull(title)) {
				booleanQuery.addTerm("title", title);
			}
		}
		catch (ParseException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		GroupBy groupBy = new GroupBy(groupByFieldName);

		groupBy.setSize(groupBySize);
		groupBy.setSorts(groupdBySorts);

		return search(booleanQuery, groupBy, start, size);
	}

	public int searchCount(long companyId, String title, boolean active) {
		BooleanFilter preBooleanFilter = createBooleanFilter(companyId, active);

		BooleanQuery booleanQuery = createBooleanQuery(preBooleanFilter);

		try {
			if (Validator.isNotNull(title)) {
				booleanQuery.addTerm("title", title);
			}
		}
		catch (ParseException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return searchCount(booleanQuery, createFacet("name"));
	}

	protected BooleanFilter createBooleanFilter(
		long companyId, boolean active) {

		BooleanFilter preBooleanFilter = new BooleanFilter();

		preBooleanFilter.addRequiredTerm("active", active);
		preBooleanFilter.addRequiredTerm("companyId", companyId);
		preBooleanFilter.addRequiredTerm("deleted", false);

		return preBooleanFilter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowMetricsProcessQueryExecutor.class);

}