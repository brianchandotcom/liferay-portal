/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.AvgAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregationResult;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.NodeMetric;
import com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0.NodeMetricEntityModel;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.NodeMetricResource;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import jakarta.ws.rs.core.MultivaluedMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/node-metric.properties",
	scope = ServiceScope.PROTOTYPE, service = NodeMetricResource.class
)
public class NodeMetricResourceImpl extends BaseNodeMetricResourceImpl {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Page<NodeMetric> getProcessNodeMetricsPage(
			Long processId, Boolean completed, Date dateEnd, Date dateStart,
			String key, String processVersion, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		String latestProcessVersion = _resourceHelper.getLatestProcessVersion(
			contextCompany.getCompanyId(), processId);

		Map<String, Bucket> taskBuckets = _getTaskBuckets(
			GetterUtil.getBoolean(completed), key, latestProcessVersion,
			processId, processVersion);

		Map<String, NodeMetric> nodeMetrics = _getNodeMetrics(
			key, latestProcessVersion, processId, processVersion,
			taskBuckets.keySet());

		long count = nodeMetrics.size();

		if (count > 0) {
			if (pagination == null) {
				return Page.of(nodeMetrics.values());
			}

			return Page.of(
				_getNodeMetrics(
					GetterUtil.getBoolean(completed), dateEnd, dateStart,
					_toFieldSort(sorts), nodeMetrics, pagination, processId),
				pagination, count);
		}

		return Page.of(Collections.emptyList());
	}

	private BooleanQuery _createBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, long processId,
		Set<String> taskNames) {

		BooleanQuery filterBooleanQuery = QueriesUtil.booleanQuery();

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		BooleanQuery slaTaskResultsBooleanQuery = QueriesUtil.booleanQuery();

		slaTaskResultsBooleanQuery.addFilterQueryClauses(
			QueriesUtil.term(
				"_index",
				_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
					WorkflowMetricsIndexNameConstants.SUFFIX_SLA_TASK_RESULT));
		slaTaskResultsBooleanQuery.addMustQueryClauses(
			_createSLATaskResultsBooleanQuery(
				completed, dateEnd, dateStart, processId, taskNames));

		BooleanQuery tasksBooleanQuery = QueriesUtil.booleanQuery();

		tasksBooleanQuery.addFilterQueryClauses(
			QueriesUtil.term(
				"_index",
				_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
					WorkflowMetricsIndexNameConstants.SUFFIX_TASK));
		tasksBooleanQuery.addMustQueryClauses(
			_createTasksBooleanQuery(
				completed, dateEnd, dateStart, processId, taskNames));

		return filterBooleanQuery.addFilterQueryClauses(
			booleanQuery.addShouldQueryClauses(
				slaTaskResultsBooleanQuery, tasksBooleanQuery));
	}

	private BooleanQuery _createBooleanQuery(
		boolean completed, long processId) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addMustNotQueryClauses(QueriesUtil.term("taskId", 0));

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("completed", completed),
			QueriesUtil.term("deleted", Boolean.FALSE),
			QueriesUtil.term("processId", processId));
	}

	private BooleanQuery _createBooleanQuery(long processId, String version) {
		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("deleted", Boolean.FALSE),
			QueriesUtil.term("processId", processId),
			QueriesUtil.term("version", version));
	}

	private BooleanQuery _createCompletionDateBooleanQuery(
		Date dateEnd, Date dateStart) {

		BooleanQuery shouldBooleanQuery = QueriesUtil.booleanQuery();

		BooleanQuery mustBooleanQuery = QueriesUtil.booleanQuery();

		return shouldBooleanQuery.addShouldQueryClauses(
			mustBooleanQuery.addMustQueryClauses(
				QueriesUtil.rangeTerm(
					"completionDate", true, true,
					_resourceHelper.getDate(dateStart),
					_resourceHelper.getDate(dateEnd)),
				QueriesUtil.term("instanceCompleted", true)),
			QueriesUtil.term("slaDefinitionId", 0));
	}

	private NodeMetric _createNodeMetric(String nodeName) {
		return new NodeMetric() {
			{
				setNode(
					() -> new Node() {
						{
							setLabel(
								() -> _language.get(
									ResourceBundleUtil.
										getModuleAndPortalResourceBundle(
											contextAcceptLanguage.
												getPreferredLocale(),
											NodeMetricResourceImpl.class),
									nodeName));
							setName(() -> nodeName);
						}
					});
			}
		};
	}

	private BooleanQuery _createNodesBooleanQuery(
		String key, String latestProcessVersion, long processId,
		String processVersion, Set<String> taskNames) {

		BooleanQuery filterBooleanQuery = QueriesUtil.booleanQuery();

		if (Validator.isNotNull(key)) {
			filterBooleanQuery.addMustQueryClauses(
				QueriesUtil.wildcard(
					Field.getSortableFieldName("name"),
					"*" + StringUtil.toLowerCase(key) + "*"));
		}

		TermsQuery termsQuery = QueriesUtil.terms("name");

		termsQuery.addValues(taskNames.toArray(new Object[0]));

		if (processVersion != null) {
			filterBooleanQuery.addMustQueryClauses(
				termsQuery, QueriesUtil.term("deleted", false),
				QueriesUtil.term("processId", processId),
				QueriesUtil.term("version", processVersion));
		}
		else {
			filterBooleanQuery.addShouldQueryClauses(
				termsQuery,
				_createBooleanQuery(processId, latestProcessVersion));
		}

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addFilterQueryClauses(filterBooleanQuery);

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("companyId", contextCompany.getCompanyId()),
			QueriesUtil.term("type", "TASK"));
	}

	private BooleanQuery _createSLATaskResultsBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, long processId,
		Set<String> taskNames) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		if (completed) {
			if ((dateEnd != null) && (dateStart != null)) {
				booleanQuery.addMustQueryClauses(
					_createCompletionDateBooleanQuery(dateEnd, dateStart));
			}
		}
		else {
			booleanQuery.addMustNotQueryClauses(
				QueriesUtil.exists("completionDate"),
				QueriesUtil.term(
					"status", WorkflowMetricsSLAStatus.COMPLETED.name()));
			booleanQuery.addMustQueryClauses(
				QueriesUtil.term("instanceCompleted", Boolean.FALSE));
		}

		TermsQuery termsQuery = QueriesUtil.terms("taskName");

		termsQuery.addValues(taskNames.toArray(new Object[0]));

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("companyId", contextCompany.getCompanyId()),
			QueriesUtil.term("deleted", Boolean.FALSE),
			QueriesUtil.term("processId", processId), termsQuery);
	}

	private BooleanQuery _createTasksBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, long processId,
		Set<String> taskNames) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addMustNotQueryClauses(QueriesUtil.term("taskId", "0"));

		if (completed && (dateEnd != null) && (dateStart != null)) {
			booleanQuery.addMustQueryClauses(
				QueriesUtil.rangeTerm(
					"completionDate", true, true,
					_resourceHelper.getDate(dateStart),
					_resourceHelper.getDate(dateEnd)));
		}

		if (!taskNames.isEmpty()) {
			TermsQuery termsQuery = QueriesUtil.terms("name");

			termsQuery.addValues(taskNames.toArray(new Object[0]));

			booleanQuery.addMustQueryClauses(termsQuery);
		}

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("active", true),
			QueriesUtil.term("companyId", contextCompany.getCompanyId()),
			QueriesUtil.term("completed", completed),
			QueriesUtil.term("deleted", Boolean.FALSE),
			QueriesUtil.term("instanceCompleted", completed),
			QueriesUtil.term("processId", processId));
	}

	private BooleanQuery _createTasksBooleanQuery(
		boolean completed, String key, String latestProcessVersion,
		long processId, String processVersion) {

		BooleanQuery filterBooleanQuery = QueriesUtil.booleanQuery();

		if (Validator.isNotNull(key)) {
			filterBooleanQuery.addMustQueryClauses(
				QueriesUtil.wildcard(
					Field.getSortableFieldName("name"),
					"*" + StringUtil.toLowerCase(key) + "*"));
		}

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		if (processVersion != null) {
			booleanQuery.addMustNotQueryClauses(QueriesUtil.term("taskId", 0));

			booleanQuery.addMustQueryClauses(
				QueriesUtil.term("instanceCompleted", completed));

			filterBooleanQuery.addMustQueryClauses(
				QueriesUtil.term("processId", processId),
				QueriesUtil.term("version", processVersion));
		}
		else {
			filterBooleanQuery.addShouldQueryClauses(
				_createBooleanQuery(completed, processId),
				_createBooleanQuery(processId, latestProcessVersion));
		}

		booleanQuery.addFilterQueryClauses(filterBooleanQuery);

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("active", Boolean.TRUE),
			QueriesUtil.term("companyId", contextCompany.getCompanyId()),
			QueriesUtil.term("deleted", Boolean.FALSE));
	}

	private double _getAvgAggregationResultValue(
		AvgAggregationResult avgAggregationResult) {

		if (Double.isInfinite(avgAggregationResult.getValue())) {
			return 0D;
		}

		return avgAggregationResult.getValue();
	}

	private Collection<NodeMetric> _getNodeMetrics(
		boolean completed, Date dateEnd, Date dateStart, FieldSort fieldSort,
		Map<String, NodeMetric> nodeMetrics, Pagination pagination,
		long processId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"taskName", null);

		termsAggregation.setScript(
			Scripts.INSTANCE.script(
				"doc.containsKey('name') ? doc.name.value : " +
					"doc.taskName.value"));

		FilterAggregation breachedFilterAggregation = _aggregations.filter(
			"breached",
			_resourceHelper.createInstanceCompletedBooleanQuery(completed));

		breachedFilterAggregation.addChildAggregation(
			_resourceHelper.createBreachedScriptedMetricAggregation());

		FilterAggregation countFilterAggregation = _aggregations.filter(
			"countFilter",
			_resourceHelper.createTasksBooleanQuery(
				contextCompany.getCompanyId(), completed));

		countFilterAggregation.addChildrenAggregations(
			_aggregations.avg("durationAvg", "duration"),
			_aggregations.valueCount("instanceCount", "instanceId"));

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime",
			_resourceHelper.createInstanceCompletedBooleanQuery(completed));

		onTimeFilterAggregation.addChildAggregation(
			_resourceHelper.createOnTimeScriptedMetricAggregation());

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue",
			_resourceHelper.createInstanceCompletedBooleanQuery(completed));

		overdueFilterAggregation.addChildAggregation(
			_resourceHelper.createOverdueScriptedMetricAggregation());

		termsAggregation.addChildrenAggregations(
			breachedFilterAggregation, countFilterAggregation,
			onTimeFilterAggregation, overdueFilterAggregation);

		termsAggregation.addPipelineAggregation(
			_resourceHelper.createBucketScriptPipelineAggregation());

		if (fieldSort != null) {
			termsAggregation.addPipelineAggregation(
				_resourceHelper.createBucketSortPipelineAggregation(
					fieldSort, pagination));
		}

		termsAggregation.setSize(nodeMetrics.size());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK,
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_TASK_RESULT);
		searchSearchRequest.setQuery(
			_createBooleanQuery(
				completed, dateEnd, dateStart, processId,
				nodeMetrics.keySet()));

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("taskName");

		return transform(
			termsAggregationResult.getBuckets(),
			bucket -> {
				NodeMetric nodeMetric = nodeMetrics.remove(bucket.getKey());

				_populateNodeMetricWithSLAMetrics(
					bucket, completed, nodeMetric);
				_setDurationAvg(bucket, nodeMetric);
				_setInstanceCount(bucket, nodeMetric);

				return nodeMetric;
			});
	}

	private Map<String, NodeMetric> _getNodeMetrics(
		String key, String latestProcessVersion, long processId,
		String processVersion, Set<String> taskNames) {

		Map<String, NodeMetric> nodeMetricsMap = new LinkedHashMap<>();

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms("name", "name");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE);

		searchSearchRequest.setQuery(
			_createNodesBooleanQuery(
				key, latestProcessVersion, processId, processVersion,
				taskNames));

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("name");

		List<NodeMetric> nodeMetrics = transform(
			termsAggregationResult.getBuckets(),
			bucket -> _createNodeMetric(bucket.getKey()));

		nodeMetrics.sort(
			new Comparator<NodeMetric>() {

				@Override
				public int compare(
					NodeMetric nodeMetric1, NodeMetric nodeMetric2) {

					Node node1 = nodeMetric1.getNode();
					Node node2 = nodeMetric2.getNode();

					String nodeName1 = node1.getName();

					return nodeName1.compareTo(node2.getName());
				}

			});

		for (NodeMetric nodeMetric : nodeMetrics) {
			Node node = nodeMetric.getNode();

			nodeMetricsMap.put(node.getName(), nodeMetric);
		}

		return nodeMetricsMap;
	}

	private Sort _getSort(String fieldName, boolean reverse, Sort[] sorts) {
		Sort sort = new Sort(fieldName, reverse);

		if (sorts != null) {
			sort = sorts[0];
		}

		return sort;
	}

	private Map<String, Bucket> _getTaskBuckets(
		boolean completed, String key, String latestProcessVersion,
		long processId, String processVersion) {

		Map<String, Bucket> taskBucketsMap = new LinkedHashMap<>();

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms("name", "name");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK);

		searchSearchRequest.setQuery(
			_createTasksBooleanQuery(
				completed, key, latestProcessVersion, processId,
				processVersion));

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("name");

		for (Bucket bucket : termsAggregationResult.getBuckets()) {
			taskBucketsMap.put(bucket.getKey(), bucket);
		}

		return taskBucketsMap;
	}

	private boolean _isOrderByDurationAvg(String fieldName) {
		if (StringUtil.equals(fieldName, "durationAvg") ||
			StringUtil.equals(fieldName, "countFilter>durationAvg")) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByInstanceCount(String fieldName) {
		if (StringUtil.equals(fieldName, "instanceCount") ||
			StringUtil.equals(fieldName, "countFilter>instanceCount")) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByOnTimeInstanceCount(String fieldName) {
		if (StringUtil.equals(fieldName, "onTimeInstanceCount") ||
			StringUtil.equals(fieldName, "onTime>instanceCount.value")) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByOverdueInstanceCount(String fieldName) {
		if (StringUtil.equals(fieldName, "overdueInstanceCount") ||
			StringUtil.equals(fieldName, "overdue>instanceCount.value")) {

			return true;
		}

		return false;
	}

	private void _populateNodeMetricWithSLAMetrics(
		Bucket bucket, boolean completed, NodeMetric nodeMetric) {

		if (completed) {
			_setBreachedInstanceCount(bucket, nodeMetric);
			_setBreachedInstancePercentage(bucket, nodeMetric);
		}
		else {
			_setOnTimeInstanceCount(bucket, nodeMetric);
			_setOverdueInstanceCount(bucket, nodeMetric);
		}
	}

	private void _setBreachedInstanceCount(
		Bucket bucket, NodeMetric nodeMetric) {

		if (bucket == null) {
			return;
		}

		nodeMetric.setBreachedInstanceCount(
			() -> _resourceHelper.getBreachedInstanceCount(bucket));
	}

	private void _setBreachedInstancePercentage(
		Bucket bucket, NodeMetric nodeMetric) {

		if (bucket == null) {
			return;
		}

		nodeMetric.setBreachedInstancePercentage(
			() -> _resourceHelper.getBreachedInstancePercentage(bucket));
	}

	private void _setDurationAvg(Bucket bucket, NodeMetric nodeMetric) {
		if (bucket == null) {
			return;
		}

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"countFilter");

		AvgAggregationResult avgAggregationResult =
			(AvgAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"durationAvg");

		nodeMetric.setDurationAvg(
			() -> GetterUtil.getLong(
				_getAvgAggregationResultValue(avgAggregationResult)));
	}

	private void _setInstanceCount(Bucket bucket, NodeMetric nodeMetric) {
		if (bucket == null) {
			return;
		}

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"countFilter");

		ValueCountAggregationResult valueCountAggregationResult =
			(ValueCountAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"instanceCount");

		nodeMetric.setInstanceCount(
			() -> GetterUtil.getLong(valueCountAggregationResult.getValue()));
	}

	private void _setOnTimeInstanceCount(Bucket bucket, NodeMetric nodeMetric) {
		if (bucket == null) {
			return;
		}

		nodeMetric.setOnTimeInstanceCount(
			() -> _resourceHelper.getOnTimeInstanceCount(bucket));
	}

	private void _setOverdueInstanceCount(
		Bucket bucket, NodeMetric nodeMetric) {

		if (bucket == null) {
			return;
		}

		nodeMetric.setOverdueInstanceCount(
			() -> _resourceHelper.getOverdueInstanceCount(bucket));
	}

	private FieldSort _toFieldSort(Sort[] sorts) {
		Sort sort = _getSort("instanceCount", false, sorts);

		String fieldName = sort.getFieldName();

		if (_isOrderByDurationAvg(fieldName)) {
			fieldName = "countFilter>durationAvg";
		}
		else if (_isOrderByInstanceCount(fieldName)) {
			fieldName = "countFilter>instanceCount";
		}
		else if (_isOrderByOnTimeInstanceCount(fieldName) ||
				 _isOrderByOverdueInstanceCount(fieldName)) {

			fieldName =
				StringUtil.extractFirst(fieldName, "InstanceCount") +
					">instanceCount.value";
		}

		FieldSort fieldSort = _sorts.field(fieldName);

		fieldSort.setSortOrder(
			sort.isReverse() ? SortOrder.DESC : SortOrder.ASC);

		return fieldSort;
	}

	private static final EntityModel _entityModel = new NodeMetricEntityModel();

	@Reference
	private Aggregations _aggregations;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Language _language;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private Sorts _sorts;

}