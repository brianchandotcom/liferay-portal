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

package com.liferay.portal.workflow.metrics.service.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseWorkflowMetricsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_deployWorkflowDefinition();
	}

	@After
	public void tearDown() throws Exception {
		undeployWorkflowDefinition();
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected String getInitialNodeKey(WorkflowDefinition workflowDefinition)
		throws Exception {

		return _getInitialNodeKey(
			_kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				workflowDefinition.getCompanyId(),
				workflowDefinition.getName()));
	}

	protected String getInitialNodeKey(
			WorkflowDefinition workflowDefinition, String version)
		throws Exception {

		return _getInitialNodeKey(
			_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				workflowDefinition.getCompanyId(), workflowDefinition.getName(),
				version));
	}

	protected String getTaskName(
			WorkflowDefinition workflowDefinition, String taskName)
		throws PortalException {

		KaleoDefinitionVersion latestKaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				workflowDefinition.getCompanyId(),
				workflowDefinition.getName());

		List<KaleoNode> kaleoNodes =
			_kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
				latestKaleoDefinitionVersion.getKaleoDefinitionVersionId());

		Stream<KaleoNode> stream = kaleoNodes.stream();

		return stream.filter(
			kaleoNode -> Objects.equals(kaleoNode.getName(), taskName)
		).map(
			kaleoNode -> {
				try {
					return _kaleoTaskLocalService.getKaleoNodeKaleoTask(
						kaleoNode.getKaleoNodeId());
				}
				catch (PortalException portalException) {
				}

				return null;
			}
		).filter(
			Objects::nonNull
		).findFirst(
		).map(
			KaleoTask::getKaleoTaskId
		).map(
			String::valueOf
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	protected String getTerminalNodeKey(WorkflowDefinition workflowDefinition)
		throws PortalException {

		return _getTerminalNodeKey(
			_kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				workflowDefinition.getCompanyId(),
				workflowDefinition.getName()));
	}

	protected String getTerminalNodeKey(
			WorkflowDefinition workflowDefinition, String version)
		throws PortalException {

		return _getTerminalNodeKey(
			_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				workflowDefinition.getCompanyId(), workflowDefinition.getName(),
				version));
	}

	protected void retryAssertCount(
			long expectedCount, String indexName, String indexType,
			Object... parameters)
		throws Exception {

		if (searchEngineAdapter == null) {
			return;
		}

		if (parameters == null) {
			return;
		}

		if ((parameters.length % 2) != 0) {
			throw new IllegalArgumentException(
				"Parameters length is not an even number");
		}

		CountSearchRequest countSearchRequest = new CountSearchRequest();

		countSearchRequest.setIndexNames(indexName);

		BooleanQuery booleanQuery = queries.booleanQuery();

		for (int i = 0; i < parameters.length; i = i + 2) {
			booleanQuery.addMustQueryClauses(
				queries.term(String.valueOf(parameters[i]), parameters[i + 1]));
		}

		countSearchRequest.setQuery(booleanQuery);

		countSearchRequest.setTypes(indexType);

		CountSearchResponse countSearchResponse = searchEngineAdapter.execute(
			countSearchRequest);

		Assert.assertEquals(
			StringBundler.concat(
				indexName, " ", indexType, " ",
				countSearchResponse.getSearchRequestString()),
			expectedCount, countSearchResponse.getCount());
	}

	protected void retryAssertCount(
			String indexName, String indexType, Object... parameters)
		throws Exception {

		retryAssertCount(1, indexName, indexType, parameters);
	}

	protected void undeployWorkflowDefinition() throws Exception {
		if (workflowDefinition != null) {
			workflowDefinitionManager.updateActive(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				workflowDefinition.getName(), workflowDefinition.getVersion(),
				false);

			workflowDefinitionManager.undeployWorkflowDefinition(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				workflowDefinition.getName(), workflowDefinition.getVersion());

			workflowDefinition = null;
		}
	}

	protected void updateWorkflowDefinition() throws Exception {
		updateWorkflowDefinition(WorkflowDefinitionUtil.getBytes());
	}

	protected void updateWorkflowDefinition(byte[] bytes) throws Exception {
		workflowDefinition = workflowDefinitionManager.deployWorkflowDefinition(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowDefinition.getTitle(), workflowDefinition.getName(), bytes);
	}

	@Inject
	protected Queries queries;

	@Inject(blocking = false, filter = "search.engine.impl=Elasticsearch")
	protected SearchEngineAdapter searchEngineAdapter;

	protected WorkflowDefinition workflowDefinition;

	@Inject
	protected WorkflowDefinitionManager workflowDefinitionManager;

	private void _deployWorkflowDefinition() throws Exception {
		workflowDefinition = workflowDefinitionManager.deployWorkflowDefinition(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringUtil.randomId(), StringUtil.randomId(),
			WorkflowDefinitionUtil.getBytes());
	}

	private String _getInitialNodeKey(
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws Exception {

		List<KaleoNode> kaleoNodes =
			_kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
				kaleoDefinitionVersion.getKaleoDefinitionVersionId());

		Stream<KaleoNode> stream = kaleoNodes.stream();

		return stream.filter(
			KaleoNode::isInitial
		).findFirst(
		).map(
			KaleoNode::getKaleoNodeId
		).map(
			String::valueOf
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	private String _getTerminalNodeKey(
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		List<KaleoNode> kaleoNodes =
			_kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
				kaleoDefinitionVersion.getKaleoDefinitionVersionId());

		Stream<KaleoNode> stream = kaleoNodes.stream();

		return stream.filter(
			KaleoNode::isTerminal
		).findFirst(
		).map(
			KaleoNode::getKaleoNodeId
		).map(
			String::valueOf
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	@Inject
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Inject
	private KaleoTaskLocalService _kaleoTaskLocalService;

}