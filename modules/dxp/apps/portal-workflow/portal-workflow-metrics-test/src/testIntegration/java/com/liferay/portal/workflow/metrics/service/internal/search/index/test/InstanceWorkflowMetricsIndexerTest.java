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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsIndexerTestCase;

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
public class InstanceWorkflowMetricsIndexerTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddInstance() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			"workflow-metrics-instances", "WorkflowMetricsInstanceType",
			"className", kaleoInstance.getClassName(), "classPK",
			kaleoInstance.getClassPK(), "companyId",
			kaleoInstance.getCompanyId(), "completed", false, "deleted", false,
			"instanceId", kaleoInstance.getKaleoInstanceId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version", "1.0");
	}

	@Test
	public void testCompleteInstance() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			"workflow-metrics-instances", "WorkflowMetricsInstanceType",
			"className", kaleoInstance.getClassName(), "classPK",
			kaleoInstance.getClassPK(), "companyId",
			kaleoInstance.getCompanyId(), "completed", false, "deleted", false,
			"instanceId", kaleoInstance.getKaleoInstanceId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version", "1.0");

		kaleoInstance = completeKaleoInstance(kaleoInstance);

		Date completionDate = kaleoInstance.getCompletionDate();

		Date createDate = kaleoInstance.getCreateDate();

		Duration duration = Duration.between(
			createDate.toInstant(), completionDate.toInstant());

		retryAssertCount(
			"workflow-metrics-instances", "WorkflowMetricsInstanceType",
			"className", kaleoInstance.getClassName(), "classPK",
			kaleoInstance.getClassPK(), "companyId",
			kaleoInstance.getCompanyId(), "completed", true, "deleted", false,
			"duration", duration.toMillis(), "instanceId",
			kaleoInstance.getKaleoInstanceId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version", "1.0");
	}

	@Test
	public void testDeleteInstance() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		deleteKaleoInstance(kaleoInstance);

		retryAssertCount(
			"workflow-metrics-instances", "WorkflowMetricsInstanceType",
			"className", kaleoInstance.getClassName(), "classPK",
			kaleoInstance.getClassPK(), "companyId",
			kaleoInstance.getCompanyId(), "completed", false, "deleted", true,
			"instanceId", kaleoInstance.getKaleoInstanceId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version", "1.0");
	}

	@Test
	public void testReindex() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();
		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		assertReindex(
			_instanceWorkflowMetricsIndexer,
			new String[] {"workflow-metrics-instances"},
			new String[] {"WorkflowMetricsInstanceType"}, "companyId",
			kaleoInstance.getCompanyId(), "instanceId",
			kaleoInstance.getKaleoInstanceId(), "processId",
			kaleoDefinition.getKaleoDefinitionId());
	}

	@Inject(
		filter = "(&(objectClass=com.liferay.portal.workflow.metrics.internal.search.index.InstanceWorkflowMetricsIndexer))"
	)
	private Indexer<Object> _instanceWorkflowMetricsIndexer;

}