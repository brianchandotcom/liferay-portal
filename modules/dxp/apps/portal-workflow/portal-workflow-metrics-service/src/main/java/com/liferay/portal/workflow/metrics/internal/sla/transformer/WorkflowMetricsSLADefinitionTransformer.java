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

package com.liferay.portal.workflow.metrics.internal.sla.transformer;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregationResult;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = WorkflowMetricsSLADefinitionTransformer.class
)
public class WorkflowMetricsSLADefinitionTransformer {

	public void transform(
			long companyId, String latestProcessVersion, long processId)
		throws PortalException {

		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions =
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinitions(
					companyId, true, processId, latestProcessVersion,
					WorkflowConstants.STATUS_APPROVED);

		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				workflowMetricsSLADefinitions) {

			_transform(latestProcessVersion, workflowMetricsSLADefinition);
		}
	}

	private BooleanQuery _createNodeBooleanQuery(
		String currentProcessVersion, String latestProcessVersion,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		TermsQuery termsQuery = _queries.terms("version");

		termsQuery.addValues(currentProcessVersion, latestProcessVersion);

		return booleanQuery.addMustQueryClauses(
			_queries.term(
				"companyId", workflowMetricsSLADefinition.getCompanyId()),
			_queries.term(
				"processId", workflowMetricsSLADefinition.getProcessId()),
			termsQuery);
	}

	private String _getNodeId(
		String processVersion,
		TermsAggregationResult versionTermsAggregationResult) {

		Bucket processVersionBucket = versionTermsAggregationResult.getBucket(
			processVersion);

		TopHitsAggregationResult topHitsAggregationResult =
			(TopHitsAggregationResult)
				processVersionBucket.getChildAggregationResult("topHits");

		return Stream.of(
			topHitsAggregationResult.getSearchHits()
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::parallelStream
		).map(
			SearchHit::getSourcesMap
		).findFirst(
		).map(
			sourceMap -> MapUtil.getString(sourceMap, "nodeId")
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	private Map<String, String> _getNodeIdMap(
		String currentProcessVersion, String latestProcessVersion,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation nameTermsAggregation = _aggregations.terms(
			"name", "name");

		nameTermsAggregation.setSize(10000);

		TermsAggregation versionTermsAggregation = _aggregations.terms(
			"version", "version");

		TopHitsAggregation topHitsAggregation = _aggregations.topHits(
			"topHits");

		topHitsAggregation.setSize(2);

		versionTermsAggregation.addChildAggregation(topHitsAggregation);

		versionTermsAggregation.setSize(10000);

		nameTermsAggregation.addChildAggregation(versionTermsAggregation);

		searchSearchRequest.addAggregation(nameTermsAggregation);

		searchSearchRequest.setIndexNames(
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowMetricsSLADefinition.getCompanyId()));
		searchSearchRequest.setQuery(
			_createNodeBooleanQuery(
				currentProcessVersion, latestProcessVersion,
				workflowMetricsSLADefinition));
		searchSearchRequest.setSize(0);

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult nameTermsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("name");

		return Stream.of(
			nameTermsAggregationResult.getBuckets()
		).flatMap(
			Collection::parallelStream
		).map(
			bucket -> (TermsAggregationResult)bucket.getChildAggregationResult(
				"version")
		).filter(
			versionTermsAggregationResult -> {
				Collection<Bucket> versionBuckets =
					versionTermsAggregationResult.getBuckets();

				return versionBuckets.size() == 2;
			}
		).collect(
			Collectors.toMap(
				versionTermsAggregationResult -> _getNodeId(
					currentProcessVersion, versionTermsAggregationResult),
				versionTermsAggregationResult -> _getNodeId(
					latestProcessVersion, versionTermsAggregationResult))
		);
	}

	private void _transform(
			String latestProcessVersion,
			WorkflowMetricsSLADefinition workflowMetricsSLADefinition)
		throws PortalException {

		Map<String, String> nodeIdMap = _getNodeIdMap(
			workflowMetricsSLADefinition.getProcessVersion(),
			latestProcessVersion, workflowMetricsSLADefinition);

		String[] pauseNodeKeys = _transformNodeKeys(
			nodeIdMap,
			StringUtil.split(workflowMetricsSLADefinition.getPauseNodeKeys()));
		String[] startNodeKeys = _transformNodeKeys(
			nodeIdMap,
			StringUtil.split(workflowMetricsSLADefinition.getStartNodeKeys()));
		String[] stopNodeKeys = _transformNodeKeys(
			nodeIdMap,
			StringUtil.split(workflowMetricsSLADefinition.getStopNodeKeys()));

		int status = WorkflowConstants.STATUS_APPROVED;

		if (ArrayUtil.isEmpty(startNodeKeys) ||
			ArrayUtil.isEmpty(stopNodeKeys)) {

			status = WorkflowConstants.STATUS_DRAFT;
		}

		_workflowMetricsSLADefinitionLocalService.
			updateWorkflowMetricsSLADefinition(
				workflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId(),
				workflowMetricsSLADefinition.getCalendarKey(),
				workflowMetricsSLADefinition.getDescription(),
				workflowMetricsSLADefinition.getDuration(),
				workflowMetricsSLADefinition.getName(), pauseNodeKeys,
				startNodeKeys, stopNodeKeys, status,
				new ServiceContext() {
					{
						setCompanyId(
							workflowMetricsSLADefinition.getCompanyId());
						setScopeGroupId(
							workflowMetricsSLADefinition.getGroupId());
						setUserId(workflowMetricsSLADefinition.getUserId());
					}
				});
	}

	private String[] _transformNodeKeys(
		Map<String, String> nodeIdMap, List<String> oldNodeKeys) {

		List<String> newNodeKeys = new ArrayList<>();

		for (String oldNodeKey : oldNodeKeys) {
			List<String> parts = StringUtil.split(oldNodeKey, CharPool.COLON);

			String oldNodeId = parts.get(0);

			if (!nodeIdMap.containsKey(oldNodeId)) {
				continue;
			}

			if (parts.size() == 1) {
				newNodeKeys.add(nodeIdMap.get(oldNodeId));
			}
			else {
				newNodeKeys.add(
					StringBundler.concat(
						nodeIdMap.get(oldNodeId), CharPool.COLON,
						parts.get(1)));
			}
		}

		return newNodeKeys.toArray(new String[0]);
	}

	@Reference
	private Aggregations _aggregations;

	@Reference(target = "(workflow.metrics.index.entity.name=node)")
	private WorkflowMetricsIndexNameBuilder
		_nodeWorkflowMetricsIndexNameBuilder;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}