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
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexerFieldNames;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsEvent;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessage;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessageSender;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoDefinitionVersionModelListener
	extends BaseModelListener<KaleoDefinitionVersion> {

	@Override
	public void onAfterCreate(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws ModelListenerException {

		try {
			WorkflowReportsMessage.Builder builder = createBuilder(
				kaleoDefinitionVersion, WorkflowReportsEvent.PROCESS_CREATE);

			Date date = kaleoDefinitionVersion.getCreateDate();

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
	public void onAfterRemove(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws ModelListenerException {

		try {
			WorkflowReportsMessage.Builder builder = createBuilder(
				kaleoDefinitionVersion, WorkflowReportsEvent.PROCESS_REMOVE);

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
	public void onAfterUpdate(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws ModelListenerException {

		try {
			WorkflowReportsMessage.Builder builder = createBuilder(
				kaleoDefinitionVersion, WorkflowReportsEvent.PROCESS_UPDATE);

			Date date = kaleoDefinitionVersion.getModifiedDate();

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
		KaleoDefinitionVersion kaleoDefinitionVersion,
		WorkflowReportsEvent workflowReportsEvent) {

		WorkflowReportsMessage.Builder builder =
			WorkflowReportsMessage.Builder.newBuilder(
				kaleoDefinitionVersion.getCompanyId(),
				workflowReportsEvent.name(),
				kaleoDefinitionVersion.getUserId());

		boolean active = false;

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionVersion.fetchKaleoDefinition();

		if (kaleoDefinition != null) {
			active = kaleoDefinition.isActive();
		}

		builder.withProperty(
			WorkflowIndexerFieldNames.ACTIVE, active
		).withProperty(
			WorkflowIndexerFieldNames.DESCRIPTION,
			kaleoDefinitionVersion.getDescription()
		).withProperty(
			WorkflowIndexerFieldNames.NAME, kaleoDefinitionVersion.getName()
		).withProperty(
			WorkflowIndexerFieldNames.PROCESS_ID,
			kaleoDefinitionVersion.getKaleoDefinitionVersionId()
		).withProperty(
			WorkflowIndexerFieldNames.TITLE,
			kaleoDefinitionVersion.getTitleMap()
		).withProperty(
			WorkflowIndexerFieldNames.VERSION,
			kaleoDefinitionVersion.getVersion()
		);

		return builder;
	}

	@Reference
	private WorkflowReportsMessageSender _workflowReportsMessageSender;

}