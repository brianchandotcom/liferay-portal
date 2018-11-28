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
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
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
public class KaleoTaskModelListener extends BaseModelListener<KaleoTask> {

	@Override
	public void onAfterCreate(KaleoTask kaleoTask)
		throws ModelListenerException {

		try {
			Map<String, String> properties = createProperties(kaleoTask);

			Date date = kaleoTask.getCreateDate();

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoTask.getCompanyId(),
				WorkflowReportsEvent.TASK_CREATE.name(), kaleoTask.getUserId(),
				properties);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemove(KaleoTask kaleoTask)
		throws ModelListenerException {

		try {
			Map<String, String> properties = createProperties(kaleoTask);

			OffsetDateTime offsetDateTime = OffsetDateTime.now();

			properties.put("date", offsetDateTime.toString());

			_workflowReportsMessageSender.sendMessage(
				kaleoTask.getCompanyId(),
				WorkflowReportsEvent.TASK_REMOVE.name(), kaleoTask.getUserId(),
				properties);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected Map<String, String> createProperties(KaleoTask kaleoTask)
		throws PortalException {

		Map<String, String> properties = new HashMap<>();

		properties.put("name", kaleoTask.getName());
		properties.put(
			"processId",
			String.valueOf(kaleoTask.getKaleoDefinitionVersionId()));
		properties.put("taskId", String.valueOf(kaleoTask.getKaleoTaskId()));

		return properties;
	}

	@Reference
	private WorkflowReportsMessageSender _workflowReportsMessageSender;

}