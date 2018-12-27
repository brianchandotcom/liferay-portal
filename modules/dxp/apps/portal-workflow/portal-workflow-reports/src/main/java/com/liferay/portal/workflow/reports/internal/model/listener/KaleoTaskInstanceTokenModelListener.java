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

package com.liferay.portal.workflow.reports.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexerFieldNames;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsEvent;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessage;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessageSender;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoTaskInstanceTokenModelListener
	extends BaseModelListener<KaleoTaskInstanceToken> {

	@Override
	public void onAfterCreate(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			WorkflowReportsMessage.Builder builder = createBuilder(
				kaleoTaskInstanceToken, WorkflowReportsEvent.TOKEN_CREATE);

			Date date = kaleoTaskInstanceToken.getCreateDate();

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			builder.withProperty(
				WorkflowIndexerFieldNames.DATE, offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(builder.build());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemove(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			if (kaleoTaskInstanceToken.isCompleted()) {
				return;
			}

			WorkflowReportsMessage.Builder builder = createBuilder(
				kaleoTaskInstanceToken, WorkflowReportsEvent.TOKEN_REMOVE);

			OffsetDateTime offsetDateTime = OffsetDateTime.now();

			builder.withProperty(
				WorkflowIndexerFieldNames.DATE, offsetDateTime.toString()
			).withProperty(
				WorkflowIndexerFieldNames.DELETED, true
			);

			_workflowReportsMessageSender.sendMessage(builder.build());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			WorkflowReportsMessage.Builder builder = null;

			Date date = kaleoTaskInstanceToken.getModifiedDate();

			if (kaleoTaskInstanceToken.isCompleted()) {
				builder = createBuilder(
					kaleoTaskInstanceToken,
					WorkflowReportsEvent.TOKEN_COMPLETE);

				Date createDate = kaleoTaskInstanceToken.getCreateDate();

				date = kaleoTaskInstanceToken.getCompletionDate();

				Duration duration = Duration.between(
					createDate.toInstant(), date.toInstant());

				builder.withProperty(
					WorkflowIndexerFieldNames.DURATION,
					String.valueOf(duration.toMillis()));
			}
			else {
				builder = createBuilder(
					kaleoTaskInstanceToken, WorkflowReportsEvent.TOKEN_UPDATE);
			}

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			builder.withProperty(
				WorkflowIndexerFieldNames.DATE, offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(builder.build());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected WorkflowReportsMessage.Builder createBuilder(
		KaleoTaskInstanceToken kaleoTaskInstanceToken,
		WorkflowReportsEvent workflowReportsEvent) {

		WorkflowReportsMessage.Builder builder =
			WorkflowReportsMessage.Builder.newBuilder(
				kaleoTaskInstanceToken.getCompanyId(),
				workflowReportsEvent.name(),
				kaleoTaskInstanceToken.getUserId());

		builder.withProperty(
			WorkflowIndexerFieldNames.CLASS_NAME,
			kaleoTaskInstanceToken.getClassName()
		).withProperty(
			WorkflowIndexerFieldNames.CLASS_PK,
			kaleoTaskInstanceToken.getClassPK()
		).withProperty(
			WorkflowIndexerFieldNames.INSTANCE_ID,
			kaleoTaskInstanceToken.getKaleoInstanceId()
		).withProperty(
			WorkflowIndexerFieldNames.TASK_ID,
			kaleoTaskInstanceToken.getKaleoTaskId()
		).withProperty(
			WorkflowIndexerFieldNames.TOKEN_ID,
			kaleoTaskInstanceToken.getKaleoInstanceTokenId()
		).withProperty(
			WorkflowIndexerFieldNames.PROCESS_ID,
			kaleoTaskInstanceToken.getKaleoDefinitionVersionId()
		);

		return builder;
	}

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private WorkflowReportsMessageSender _workflowReportsMessageSender;

}