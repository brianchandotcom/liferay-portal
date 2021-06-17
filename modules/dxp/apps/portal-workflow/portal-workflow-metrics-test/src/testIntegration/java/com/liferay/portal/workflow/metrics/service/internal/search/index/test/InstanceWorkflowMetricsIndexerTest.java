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
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsIndexerTestCase;

import java.time.Duration;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class InstanceWorkflowMetricsIndexerTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@Test
	public void testAddInstance() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		assertCount(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", false,
			"deleted", false, "instanceId", kaleoInstance.getKaleoInstanceId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"version", "1.0");
	}

	@Test
	public void testCompleteInstance() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		assertCount(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", false,
			"deleted", false, "instanceId", kaleoInstance.getKaleoInstanceId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"version", "1.0");

		kaleoInstance = completeKaleoInstance(kaleoInstance);

		Date completionDate = kaleoInstance.getCompletionDate();

		Date createDate = kaleoInstance.getCreateDate();

		Duration duration = Duration.between(
			createDate.toInstant(), completionDate.toInstant());

		assertCount(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", true,
			"deleted", false, "duration", duration.toMillis(), "instanceId",
			kaleoInstance.getKaleoInstanceId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "1.0");
	}

	@Test
	public void testDeleteInstance() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		deleteKaleoInstance(kaleoInstance);

		assertCount(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", false,
			"deleted", true, "instanceId", kaleoInstance.getKaleoInstanceId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"version", "1.0");
	}

	@Test
	public void testReindex() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		assertReindex(
			new String[] {
				_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
					workflowDefinition.getCompanyId())
			},
			new String[] {"WorkflowMetricsInstanceType"}, "companyId",
			kaleoInstance.getCompanyId(), "instanceId",
			kaleoInstance.getKaleoInstanceId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());
	}

	@Test
	public void testUpdateTaskAssignee() throws Exception {
		KaleoInstance kaleoInstance = getKaleoInstance(addBlogsEntry());

		retryAssertCount(
			booleanQuery -> booleanQuery.addMustQueryClauses(
				queries.nested(
					"tasks",
					queries.term("tasks.assigneeType", Role.class.getName()))),
			1,
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", false,
			"deleted", false, "instanceId", kaleoInstance.getKaleoInstanceId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"version", "1.0");

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			LocaleUtil.getDefault(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			new long[] {TestPropsValues.getGroupId()});

		assignKaleoTaskInstanceToken(kaleoInstance, user.getUserId());

		retryAssertCount(
			booleanQuery -> booleanQuery.addMustQueryClauses(
				queries.nested(
					"tasks",
					queries.term("tasks.assigneeName", user.getFullName()))),
			1,
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", false,
			"deleted", false, "instanceId", kaleoInstance.getKaleoInstanceId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"version", "1.0");

		user.setMiddleName(RandomTestUtil.randomString());

		User updatedUser = _userLocalService.updateUser(user);

		retryAssertCount(
			booleanQuery -> booleanQuery.addMustQueryClauses(
				queries.nested(
					"tasks",
					queries.term(
						"tasks.assigneeName", updatedUser.getFullName()))),
			1,
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", false,
			"deleted", false, "instanceId", kaleoInstance.getKaleoInstanceId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"version", "1.0");
	}

	@Inject(filter = "workflow.metrics.index.entity.name=instance")
	private WorkflowMetricsIndexNameBuilder
		_instanceWorkflowMetricsIndexNameBuilder;

	@Inject
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Inject
	private UserLocalService _userLocalService;

}