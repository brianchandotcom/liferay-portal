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

package com.liferay.portal.workflow.metrics.service.internal.search.index.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.metrics.service.test.base.BaseWorkflowMetricsIndexerTestCase;

import java.time.Duration;

import java.util.Date;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class TokenWorkflowMetricsIndexerTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddToken() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			"workflow-metrics-tokens", "WorkflowMetricsTokenType", "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "deleted",
			false, "processId", kaleoDefinition.getKaleoDefinitionId(),
			"taskId", kaleoTaskInstanceToken.getKaleoTaskId(), "taskName",
			"review", "tokenId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), "version",
			"1.0");
	}

	@Test
	public void testAssignToken() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			"workflow-metrics-tokens", "WorkflowMetricsTokenType", "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "deleted",
			false, "processId", kaleoDefinition.getKaleoDefinitionId(),
			"taskId", kaleoTaskInstanceToken.getKaleoTaskId(), "taskName",
			"review", "tokenId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), "version",
			"1.0");

		kaleoTaskInstanceToken = assignKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);

		retryAssertCount(
			"workflow-metrics-tokens", "WorkflowMetricsTokenType", "assigneeId",
			TestPropsValues.getUserId(), "companyId",
			kaleoDefinition.getCompanyId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "taskId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "taskName", "review",
			"tokenId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
	}

	@Test
	public void testCompleteToken() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			"workflow-metrics-tokens", "WorkflowMetricsTokenType", "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "deleted",
			false, "processId", kaleoDefinition.getKaleoDefinitionId(),
			"taskId", kaleoTaskInstanceToken.getKaleoTaskId(), "taskName",
			"review", "tokenId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), "version",
			"1.0");

		kaleoTaskInstanceToken = completeKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);

		Date completionDate = kaleoTaskInstanceToken.getCompletionDate();

		Date createDate = kaleoTaskInstanceToken.getCreateDate();

		Duration duration = Duration.between(
			createDate.toInstant(), completionDate.toInstant());

		retryAssertCount(
			"workflow-metrics-tokens", "WorkflowMetricsTokenType", "assigneeId",
			TestPropsValues.getUserId(), "companyId",
			kaleoDefinition.getCompanyId(), "duration", duration.toMillis(),
			"processId", kaleoDefinition.getKaleoDefinitionId(), "taskId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "taskName", "review",
			"tokenId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
	}

	@Test
	public void testDeleteToken() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		deleteKaleoTaskInstanceToken(kaleoTaskInstanceToken);

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			"workflow-metrics-tokens", "WorkflowMetricsTokenType", "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "deleted", true,
			"processId", kaleoDefinition.getKaleoDefinitionId(), "taskId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "taskName", "review",
			"tokenId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			"version", "1.0");
	}

	@Test
	public void testReindex() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		assertReindex(
			_tokenWorkflowMetricsIndexer,
			new String[] {"workflow-metrics-tokens"},
			new String[] {"WorkflowMetricsTokenType"}, "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "taskId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "taskName", "review",
			"tokenId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			"version", "1.0");
	}

	@Inject(
		filter = "(&(objectClass=com.liferay.portal.workflow.metrics.internal.search.index.TokenWorkflowMetricsIndexer))"
	)
	private Indexer<Object> _tokenWorkflowMetricsIndexer;

}