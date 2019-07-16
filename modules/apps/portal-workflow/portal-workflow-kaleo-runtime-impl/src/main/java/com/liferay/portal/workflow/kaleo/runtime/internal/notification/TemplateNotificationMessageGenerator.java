/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.notification;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationMessageGenerationException;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationMessageGenerator;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"template.language=freemarker", "template.language=soy",
		"template.language=velocity"
	},
	service = NotificationMessageGenerator.class
)
public class TemplateNotificationMessageGenerator
	implements NotificationMessageGenerator {

	@Override
	public String generateMessage(
			String kaleoClassName, long kaleoClassPK, String notificationName,
			String notificationTemplateLanguage, String notificationTemplate,
			ExecutionContext executionContext)
		throws NotificationMessageGenerationException {

		String templateManagerName = _templateManagerNames.get(
			notificationTemplateLanguage);

		if (Validator.isNull(templateManagerName)) {
			throw new NotificationMessageGenerationException(
				"Unsupported notification template language " +
					notificationTemplateLanguage);
		}

		try {
			String templateId =
				notificationName + kaleoClassName + kaleoClassPK;

			Template template = TemplateManagerUtil.getTemplate(
				templateManagerName,
				new StringTemplateResource(templateId, notificationTemplate),
				false);

			populateContextVariables(template, executionContext);

			StringWriter stringWriter = new StringWriter();

			template.processTemplate(stringWriter);

			return stringWriter.toString();
		}
		catch (Exception e) {
			throw new NotificationMessageGenerationException(
				"Unable to generate notification message", e);
		}
	}

	@Activate
	protected void activate() {
		_templateManagerNames.put(
			"freemarker", TemplateConstants.LANG_TYPE_FTL);
		_templateManagerNames.put("soy", TemplateConstants.LANG_TYPE_SOY);
		_templateManagerNames.put("velocity", TemplateConstants.LANG_TYPE_VM);
	}

	protected void populateContextVariables(
			Template template, ExecutionContext executionContext)
		throws Exception {

		Map<String, Serializable> workflowContext =
			executionContext.getWorkflowContext();

		if (workflowContext == null) {
			KaleoInstanceToken kaleoInstanceToken =
				executionContext.getKaleoInstanceToken();

			KaleoInstance kaleoInstance = kaleoInstanceToken.getKaleoInstance();

			workflowContext = WorkflowContextUtil.convert(
				kaleoInstance.getWorkflowContext());
		}

		for (Map.Entry<String, Serializable> entry :
				workflowContext.entrySet()) {

			template.put(entry.getKey(), entry.getValue());
		}

		template.put(
			"kaleoInstanceToken", executionContext.getKaleoInstanceToken());

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			executionContext.getKaleoTaskInstanceToken();

		if (kaleoTaskInstanceToken != null) {
			KaleoTask kaleoTask = kaleoTaskInstanceToken.getKaleoTask();

			template.put("kaleoTaskInstanceToken", kaleoTaskInstanceToken);
			template.put("taskName", kaleoTask.getName());

			ServiceContext serviceContext =
				executionContext.getServiceContext();

			User user = _userLocalService.getUser(
				serviceContext.getGuestOrUserId());

			template.put("userId", user.getUserId());
			template.put("userName", user.getFullName());

			template.put(
				"workflowTaskAssignees",
				_kaleoWorkflowModelConverter.getWorkflowTaskAssignees(
					kaleoTaskInstanceToken));
		}
		else {
			KaleoInstanceToken kaleoInstanceToken =
				executionContext.getKaleoInstanceToken();

			template.put("userId", kaleoInstanceToken.getUserId());
			template.put("userName", kaleoInstanceToken.getUserName());
		}

		KaleoTimerInstanceToken kaleoTimerInstanceToken =
			executionContext.getKaleoTimerInstanceToken();

		if (kaleoTimerInstanceToken != null) {
			template.put("kaleoTimerInstanceToken", kaleoTimerInstanceToken);
		}
	}

	@Reference
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

	private final Map<String, String> _templateManagerNames = new HashMap<>();

	@Reference
	private UserLocalService _userLocalService;

}