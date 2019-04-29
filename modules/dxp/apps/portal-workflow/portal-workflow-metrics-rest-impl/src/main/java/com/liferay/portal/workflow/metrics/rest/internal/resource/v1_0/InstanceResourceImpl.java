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

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Instance.Status;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.InstanceResource;

import java.text.DateFormat;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/instance.properties",
	scope = ServiceScope.PROTOTYPE, service = InstanceResource.class
)
public class InstanceResourceImpl extends BaseInstanceResourceImpl {

	@Override
	public Page<Instance> getProcessInstancesPage(
			Long processId, Pagination pagination)
		throws Exception {

		SearchSearchResponse searchSearchResponse =
			_getInstancesSearchSearchResponse(pagination, processId);

		long count = searchSearchResponse.getCount();

		if (count > 0) {
			return Page.of(
				_getInstances(processId, searchSearchResponse.getSearchHits()),
				pagination, count);
		}

		return Page.of(Collections.emptyList());
	}

	private Instance _createInstance(Document document) {
		return new Instance() {
			{
				assetTitle = document.getString(
					_getLocalizedName("assetTitle"));
				assetType = document.getString(_getLocalizedName("assetType"));
				dateCreated = _toDate(document.getDate("createDate"));
				id = document.getLong("instanceId");
				userName = document.getString("userName");
			}
		};
	}

	private TermsQuery _createInstanceIdTermsQuery(Set<Long> instanceIds) {
		TermsQuery termsQuery = _queries.terms("instanceId");

		Stream<Long> stream = instanceIds.stream();

		termsQuery.addValues(
			stream.map(
				String::valueOf
			).toArray(
				Object[]::new
			));

		return termsQuery;
	}

	private BooleanQuery _createInstancesBooleanQuery(long processId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("instanceId", 0));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", false),
			_queries.term("processId", processId));
	}

	private BooleanQuery _createSLAProcessResultsBooleanQuery(
		Set<Long> instanceIds, long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", false),
			_queries.term("processId", processId),
			_createInstanceIdTermsQuery(instanceIds));
	}

	private Collection<Instance> _getInstances(
			long processId, SearchHits searchHits)
		throws Exception {

		List<Instance> instances = new LinkedList<>();

		Map<Long, Instance> instancesMap = Stream.of(
			searchHits.getSearchHits()
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).map(
			this::_createInstance
		).collect(
			LinkedHashMap::new,
			(map, instance) -> map.put(instance.getId(), instance), Map::putAll
		);

		Map<String, List<String>> taskNames = _getTaskNames(
			instancesMap.keySet(), processId);

		TermsAggregationResult slaTermsAggregationResult =
			_getSLATermsAggregationResult(instancesMap.keySet(), processId);

		instancesMap.forEach(
			(instanceId, instance) -> {
				_setStatus(
					slaTermsAggregationResult.getBucket(
						String.valueOf(instanceId)),
					instance);
				_setTaskNames(
					instance, taskNames.get(String.valueOf(instanceId)));

				instances.add(instance);
			});

		return instances;
	}

	private SearchSearchResponse _getInstancesSearchSearchResponse(
		Pagination pagination, Long processId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-instances");
		searchSearchRequest.setQuery(_createInstancesBooleanQuery(processId));
		searchSearchRequest.setSize(pagination.getPageSize());
		searchSearchRequest.setStart(pagination.getStartPosition());

		return _searchRequestExecutor.executeSearchRequest(searchSearchRequest);
	}

	private String _getLocalizedName(String name) {
		return Field.getLocalizedName(
			contextAcceptLanguage.getPreferredLocale(), name);
	}

	private TermsAggregationResult _getSLATermsAggregationResult(
		Set<Long> instanceIds, long processId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"instanceId", "instanceId");

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime", _resourceHelper.createMustNotBooleanQuery());

		onTimeFilterAggregation.addChildAggregation(
			_resourceHelper.createOnTimeScriptedMetricAggregation());

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue", _resourceHelper.createMustNotBooleanQuery());

		overdueFilterAggregation.addChildAggregation(
			_resourceHelper.createOverdueScriptedMetricAggregation());

		termsAggregation.addChildrenAggregations(
			onTimeFilterAggregation, overdueFilterAggregation);

		termsAggregation.setSize(instanceIds.size());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			"workflow-metrics-sla-process-results");
		searchSearchRequest.setQuery(
			_createSLAProcessResultsBooleanQuery(instanceIds, processId));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		return (TermsAggregationResult)aggregationResultsMap.get("instanceId");
	}

	private List<String> _getTaskNames(Bucket bucket) {
		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)bucket.getChildAggregationResult(
				"taskName");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		Stream<Bucket> stream = buckets.stream();

		return stream.map(
			Bucket::getKey
		).map(
			taskName -> LanguageUtil.get(
				contextAcceptLanguage.getPreferredLocale(), taskName)
		).collect(
			Collectors.toList()
		);
	}

	private Map<String, List<String>> _getTaskNames(
		Set<Long> instanceIds, long processId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation instanceIdTermsAggregation = _aggregations.terms(
			"instanceId", "instanceId");

		TermsAggregation taskNameTermsAggregation = _aggregations.terms(
			"taskName", "taskName");

		taskNameTermsAggregation.setSize(10000);

		instanceIdTermsAggregation.addChildAggregation(
			taskNameTermsAggregation);

		instanceIdTermsAggregation.setSize(instanceIds.size());

		searchSearchRequest.addAggregation(instanceIdTermsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-tokens");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", contextCompany.getCompanyId()),
				_queries.term("completed", false),
				_queries.term("deleted", false),
				_queries.term("processId", processId),
				_createInstanceIdTermsQuery(instanceIds)));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("instanceId");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		Stream<Bucket> stream = buckets.stream();

		return stream.collect(
			LinkedHashMap::new,
			(map, bucket) -> map.put(bucket.getKey(), _getTaskNames(bucket)),
			Map::putAll);
	}

	private boolean _isOnTime(Bucket bucket) {
		if (_resourceHelper.getOnTimeInstanceCount(bucket) > 0) {
			return true;
		}

		return false;
	}

	private boolean _isOverdue(Bucket bucket) {
		if (_resourceHelper.getOverdueInstanceCount(bucket) > 0) {
			return true;
		}

		return false;
	}

	private void _setStatus(Bucket bucket, Instance instance) {
		if (bucket == null) {
			return;
		}

		if (_isOverdue(bucket)) {
			instance.setStatus(Status.OVERDUE);
		}
		else if (_isOnTime(bucket)) {
			instance.setStatus(Status.ON_TIME);
		}
		else {
			instance.setStatus(Status.UNTRACKED);
		}
	}

	private void _setTaskNames(Instance instance, List<String> taskNames) {
		if (taskNames == null) {
			return;
		}

		instance.setTaskNames(taskNames.toArray(new String[taskNames.size()]));
	}

	private Date _toDate(String dateString) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			_INDEX_DATE_FORMAT_PATTERN);

		try {
			return dateFormat.parse(dateString);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return null;
		}
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	private static final Log _log = LogFactoryUtil.getLog(
		InstanceResourceImpl.class);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Queries _queries;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

}