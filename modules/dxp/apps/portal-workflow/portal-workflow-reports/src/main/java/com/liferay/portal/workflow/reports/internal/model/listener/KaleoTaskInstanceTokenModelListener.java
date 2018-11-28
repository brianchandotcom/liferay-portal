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
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
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
public class KaleoTaskInstanceTokenModelListener
	extends BaseModelListener<KaleoTaskInstanceToken> {

	@Override
	public void onAfterCreate(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			Map<String, String> properties = createProperties(
				kaleoTaskInstanceToken);

			Date date = kaleoTaskInstanceToken.getCreateDate();

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoTaskInstanceToken.getCompanyId(),
				WorkflowReportsEvent.TOKEN_CREATE.name(),
				kaleoTaskInstanceToken.getUserId(), properties);
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

			Map<String, String> properties = createProperties(
				kaleoTaskInstanceToken);

			OffsetDateTime offsetDateTime = OffsetDateTime.now();

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoTaskInstanceToken.getCompanyId(),
				WorkflowReportsEvent.TOKEN_REMOVE.name(),
				kaleoTaskInstanceToken.getUserId(), properties);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			Map<String, String> properties = createProperties(
				kaleoTaskInstanceToken);

			WorkflowReportsEvent workflowEvent =
				WorkflowReportsEvent.TOKEN_UPDATE;

			Date date = kaleoTaskInstanceToken.getModifiedDate();

			if (kaleoTaskInstanceToken.isCompleted()) {
				workflowEvent = WorkflowReportsEvent.TOKEN_COMPLETE;

				Date createDate = kaleoTaskInstanceToken.getCreateDate();

				date = kaleoTaskInstanceToken.getCompletionDate();

				Duration duration = Duration.between(
					createDate.toInstant(), date.toInstant());

				properties.put("duration", String.valueOf(duration.toMillis()));
			}

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoTaskInstanceToken.getCompanyId(), workflowEvent.name(),
				kaleoTaskInstanceToken.getUserId(), properties);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected Map<String, String> createProperties(
			KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws PortalException {

		Map<String, String> properties = new HashMap<>();

		properties.put("className", kaleoTaskInstanceToken.getClassName());
		properties.put(
			"classPK", String.valueOf(kaleoTaskInstanceToken.getClassPK()));
		properties.put(
			"instanceId",
			String.valueOf(kaleoTaskInstanceToken.getKaleoInstanceId()));
		properties.put(
			"processId",
			String.valueOf(
				kaleoTaskInstanceToken.getKaleoDefinitionVersionId()));
		properties.put(
			"taskId", String.valueOf(kaleoTaskInstanceToken.getKaleoTaskId()));
		properties.put(
			"tokenId",
			String.valueOf(kaleoTaskInstanceToken.getKaleoInstanceTokenId()));

		return properties;
	}

	@Reference
	private WorkflowReportsMessageSender _workflowReportsMessageSender;

}