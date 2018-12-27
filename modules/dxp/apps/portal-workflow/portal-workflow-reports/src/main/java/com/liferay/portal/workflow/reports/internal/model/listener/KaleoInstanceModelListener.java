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
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
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
public class KaleoInstanceModelListener
	extends BaseModelListener<KaleoInstance> {

	@Override
	public void onAfterCreate(KaleoInstance kaleoInstance)
		throws ModelListenerException {

		try {
			WorkflowReportsMessage.Builder builder = createBuilder(
				kaleoInstance, WorkflowReportsEvent.INSTANCE_CREATE);

			Date date = kaleoInstance.getCreateDate();

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
	public void onAfterRemove(KaleoInstance kaleoInstance)
		throws ModelListenerException {

		try {
			if (kaleoInstance.isCompleted()) {
				return;
			}

			WorkflowReportsMessage.Builder builder = createBuilder(
				kaleoInstance, WorkflowReportsEvent.INSTANCE_REMOVE);

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
	public void onAfterUpdate(KaleoInstance kaleoInstance)
		throws ModelListenerException {

		try {
			WorkflowReportsMessage.Builder builder = null;

			Date date = kaleoInstance.getModifiedDate();

			if (kaleoInstance.isCompleted()) {
				builder = createBuilder(
					kaleoInstance, WorkflowReportsEvent.INSTANCE_COMPLETE);

				Date createDate = kaleoInstance.getCreateDate();

				date = kaleoInstance.getCompletionDate();

				Duration duration = Duration.between(
					createDate.toInstant(), date.toInstant());

				builder.withProperty(
					WorkflowIndexerFieldNames.DURATION, duration.toMillis());
			}
			else {
				builder = createBuilder(
					kaleoInstance, WorkflowReportsEvent.INSTANCE_UPDATE);
			}

			builder.withProperty(
				WorkflowIndexerFieldNames.COMPLETED,
				kaleoInstance.isCompleted());

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
		KaleoInstance kaleoInstance,
		WorkflowReportsEvent workflowReportsEvent) {

		WorkflowReportsMessage.Builder builder =
			WorkflowReportsMessage.Builder.newBuilder(
				kaleoInstance.getCompanyId(), workflowReportsEvent.name(),
				kaleoInstance.getUserId());

		builder.withProperty(
			WorkflowIndexerFieldNames.CLASS_NAME, kaleoInstance.getClassName()
		).withProperty(
			WorkflowIndexerFieldNames.CLASS_PK, kaleoInstance.getClassPK()
		).withProperty(
			WorkflowIndexerFieldNames.INSTANCE_ID,
			kaleoInstance.getKaleoInstanceId()
		).withProperty(
			WorkflowIndexerFieldNames.PROCESS_ID,
			kaleoInstance.getKaleoDefinitionVersionId()
		);

		return builder;
	}

	@Reference
	private WorkflowReportsMessageSender _workflowReportsMessageSender;

}