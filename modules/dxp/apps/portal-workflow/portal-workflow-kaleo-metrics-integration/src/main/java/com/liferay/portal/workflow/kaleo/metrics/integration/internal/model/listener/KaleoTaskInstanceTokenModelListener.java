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

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;

import java.time.Duration;

import java.util.Date;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoTaskInstanceTokenModelListener
	extends BaseKaleoModelListener<KaleoTaskInstanceToken> {

	@Override
	public void onAfterCreate(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(
				kaleoTaskInstanceToken.getKaleoDefinitionVersionId());

		if (Objects.isNull(kaleoDefinitionVersion)) {
			return;
		}

		_taskWorkflowMetricsIndexer.addTask(
			null, kaleoTaskInstanceToken.getClassName(),
			kaleoTaskInstanceToken.getClassPK(),
			kaleoTaskInstanceToken.getCompanyId(), false, null, null,
			kaleoTaskInstanceToken.getCreateDate(), false,
			kaleoTaskInstanceToken.getKaleoInstanceId(),
			kaleoTaskInstanceToken.getModifiedDate(),
			kaleoTaskInstanceToken.getKaleoTaskName(),
			kaleoTaskInstanceToken.getKaleoTaskId(),
			kaleoTaskInstanceToken.getKaleoDefinitionId(),
			kaleoDefinitionVersion.getVersion(),
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			kaleoTaskInstanceToken.getUserId());
	}

	@Override
	public void onAfterUpdate(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
					_kaleoTaskAssignmentInstanceLocalService.
						fetchFirstKaleoTaskAssignmentInstance(
							kaleoTaskInstanceToken.
								getKaleoTaskInstanceTokenId(),
							User.class.getName(), null);

				if (kaleoTaskAssignmentInstance == null) {
					return null;
				}

				_taskWorkflowMetricsIndexer.updateTask(
					kaleoTaskAssignmentInstance.getAssigneeClassPK(),
					kaleoTaskInstanceToken.getCompanyId(),
					kaleoTaskInstanceToken.getModifiedDate(),
					kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
					kaleoTaskInstanceToken.getUserId());

				return null;
			});
	}

	@Override
	public void onBeforeRemove(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		_taskWorkflowMetricsIndexer.deleteTask(
			kaleoTaskInstanceToken.getCompanyId(),
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
	}

	@Override
	public void onBeforeUpdate(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		KaleoTaskInstanceToken currentKaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.fetchKaleoTaskInstanceToken(
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		if (currentKaleoTaskInstanceToken.isCompleted() ||
			!kaleoTaskInstanceToken.isCompleted()) {

			return;
		}

		Date createDate = kaleoTaskInstanceToken.getCreateDate();
		Date completionDate = kaleoTaskInstanceToken.getCompletionDate();

		Duration duration = Duration.between(
			createDate.toInstant(), completionDate.toInstant());

		_taskWorkflowMetricsIndexer.completeTask(
			kaleoTaskInstanceToken.getCompanyId(), completionDate,
			kaleoTaskInstanceToken.getCompletionUserId(), duration.toMillis(),
			kaleoTaskInstanceToken.getModifiedDate(),
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			kaleoTaskInstanceToken.getUserId());
	}

	@Reference
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private TaskWorkflowMetricsIndexer _taskWorkflowMetricsIndexer;

}