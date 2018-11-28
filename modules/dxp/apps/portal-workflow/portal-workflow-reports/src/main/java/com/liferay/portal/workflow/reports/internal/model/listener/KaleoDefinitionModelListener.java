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
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsEvent;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessageSender;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoDefinitionModelListener
	extends BaseModelListener<KaleoDefinition> {

	@Override
	public void onAfterCreate(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		try {
			Map<String, String> properties = createProperties(kaleoDefinition);

			Date date = kaleoDefinition.getCreateDate();

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoDefinition.getCompanyId(),
				WorkflowReportsEvent.PROCESS_CREATE.name(),
				kaleoDefinition.getUserId(), properties);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemove(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		try {
			Map<String, String> properties = createProperties(kaleoDefinition);

			OffsetDateTime offsetDateTime = OffsetDateTime.now();

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoDefinition.getCompanyId(),
				WorkflowReportsEvent.PROCESS_REMOVE.name(),
				kaleoDefinition.getUserId(), properties);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		try {
			Map<String, String> properties = createProperties(kaleoDefinition);

			Date date = kaleoDefinition.getModifiedDate();

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoDefinition.getCompanyId(),
				WorkflowReportsEvent.PROCESS_UPDATE.name(),
				kaleoDefinition.getUserId(), properties);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected Map<String, String> createProperties(
		KaleoDefinition kaleoDefinition) {

		Map<String, String> properties = new HashMap<>();

		properties.put("active", String.valueOf(kaleoDefinition.isActive()));
		properties.put("description", kaleoDefinition.getDescription());
		properties.put(
			"processId",
			String.valueOf(kaleoDefinition.getKaleoDefinitionId()));
		properties.put("title", kaleoDefinition.getTitle());
		properties.put("version", String.valueOf(kaleoDefinition.getVersion()));

		return properties;
	}

	@Reference
	private WorkflowReportsMessageSender _workflowReportsMessageSender;

}