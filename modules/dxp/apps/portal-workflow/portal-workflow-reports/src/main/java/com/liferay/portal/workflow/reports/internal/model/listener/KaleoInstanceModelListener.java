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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsEvent;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessageSender;

import java.time.Duration;
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
public class KaleoInstanceModelListener
	extends BaseModelListener<KaleoInstance> {

	@Override
	public void onAfterCreate(KaleoInstance kaleoInstance)
		throws ModelListenerException {

		try {
			Map<String, String> properties = createProperties(kaleoInstance);

			Date date = kaleoInstance.getCreateDate();

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoInstance.getCompanyId(),
				WorkflowReportsEvent.INSTANCE_CREATE.name(),
				kaleoInstance.getUserId(), properties);
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

			Map<String, String> properties = createProperties(kaleoInstance);

			OffsetDateTime offsetDateTime = OffsetDateTime.now();

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoInstance.getCompanyId(),
				WorkflowReportsEvent.INSTANCE_REMOVE.name(),
				kaleoInstance.getUserId(), properties);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(KaleoInstance kaleoInstance)
		throws ModelListenerException {

		try {
			Map<String, String> properties = createProperties(kaleoInstance);

			WorkflowReportsEvent workflowEvent =
				WorkflowReportsEvent.INSTANCE_UPDATE;

			Date date = kaleoInstance.getModifiedDate();

			if (kaleoInstance.isCompleted()) {
				workflowEvent = WorkflowReportsEvent.INSTANCE_COMPLETE;

				Date createDate = kaleoInstance.getCreateDate();

				date = kaleoInstance.getCompletionDate();

				Duration duration = Duration.between(
					createDate.toInstant(), date.toInstant());

				properties.put("duration", String.valueOf(duration.toMillis()));
			}

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoInstance.getCompanyId(), workflowEvent.name(),
				kaleoInstance.getUserId(), properties);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected Map<String, String> createProperties(KaleoInstance kaleoInstance)
		throws PortalException {

		Map<String, String> properties = new HashMap<>();

		properties.put("className", kaleoInstance.getClassName());
		properties.put("classPK", String.valueOf(kaleoInstance.getClassPK()));
		properties.put(
			"instanceId", String.valueOf(kaleoInstance.getKaleoInstanceId()));
		properties.put(
			"processId",
			String.valueOf(kaleoInstance.getKaleoDefinitionVersionId()));

		return properties;
	}

	@Reference
	private WorkflowReportsMessageSender _workflowReportsMessageSender;

}