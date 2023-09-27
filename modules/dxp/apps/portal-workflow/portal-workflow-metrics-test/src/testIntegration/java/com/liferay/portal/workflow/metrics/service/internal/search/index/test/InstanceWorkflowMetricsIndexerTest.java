/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
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
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
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
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
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
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
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
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
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

		String indexName = _indexNameBuilder.getIndexName(
			workflowDefinition.getCompanyId());

		assertReindex(
			new String[] {
				indexName + WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE
			},
			new String[] {"WorkflowMetricsInstanceType"}, "companyId",
			kaleoInstance.getCompanyId(), "instanceId",
			kaleoInstance.getKaleoInstanceId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());
	}

	@Test
	public void testUpdateTaskAssignee() throws Exception {
		KaleoInstance kaleoInstance = getKaleoInstance(addBlogsEntry());

		String indexName = _indexNameBuilder.getIndexName(
			workflowDefinition.getCompanyId());

		retryAssertCount(
			booleanQuery -> booleanQuery.addMustQueryClauses(
				queries.nested(
					"tasks",
					queries.term("tasks.assigneeType", Role.class.getName()))),
			1, indexName + WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
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
			1, indexName + WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
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
			1, indexName + WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", false,
			"deleted", false, "instanceId", kaleoInstance.getKaleoInstanceId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"version", "1.0");
	}

	@Inject
	private IndexNameBuilder _indexNameBuilder;

	@Inject
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Inject
	private UserLocalService _userLocalService;

}