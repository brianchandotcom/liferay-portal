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

package com.liferay.portal.workflow.kaleo.runtime.internal.notification.recipient;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.definition.ScriptLanguage;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.NotificationRecipientBuilder;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.script.NotificationRecipientEvaluator;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.script.constants.ScriptingNotificationRecipientConstants;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "recipient.type=SCRIPT",
	service = NotificationRecipientBuilder.class
)
public class ScriptNotificationRecipientBuilder
	implements NotificationRecipientBuilder {

	@Override
	public void processKaleoNotificationRecipient(
			Set<NotificationRecipient> notificationRecipients,
			KaleoNotificationRecipient kaleoNotificationRecipient,
			NotificationReceptionType notificationReceptionType,
			ExecutionContext executionContext)
		throws Exception {

		Map<String, ?> results = _evaluate(
			kaleoNotificationRecipient, executionContext);

		Map<String, Serializable> resultsWorkflowContext =
			(Map<String, Serializable>)results.get(
				WorkflowContextUtil.WORKFLOW_CONTEXT_NAME);

		WorkflowContextUtil.mergeWorkflowContexts(
			executionContext, resultsWorkflowContext);

		User user = (User)results.get(
			ScriptingNotificationRecipientConstants.USER_RECIPIENT);

		if (user != null) {
			if (user.isActive()) {
				NotificationRecipient notificationRecipient =
					new NotificationRecipient(user, notificationReceptionType);

				notificationRecipients.add(notificationRecipient);
			}
		}
		else {
			List<Role> roles = (List<Role>)results.get(
				ScriptingNotificationRecipientConstants.ROLES_RECIPIENT);

			for (Role role : roles) {
				_roleNotificationRecipientBuilder.addRoleRecipientAddresses(
					notificationRecipients, role, notificationReceptionType,
					executionContext);
			}
		}
	}

	@Override
	public void processKaleoTaskAssignmentInstance(
			Set<NotificationRecipient> notificationRecipients,
			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
			NotificationReceptionType notificationReceptionType,
			ExecutionContext executionContext)
		throws Exception {
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, NotificationRecipientEvaluator.class,
			"(scripting.language=*)",
			(serviceReference, emitter) -> {
				NotificationRecipientEvaluator notificationRecipientEvaluator =
					bundleContext.getService(serviceReference);

				try {
					emitter.emit(
						_getNotificationRecipientEvaluatorKey(
							GetterUtil.getString(
								serviceReference.getProperty(
									"scripting.language")),
							ClassUtil.getClassName(
								notificationRecipientEvaluator)));
				}
				catch (KaleoDefinitionValidationException
							kaleoDefinitionValidationException) {

					throw new RuntimeException(
						kaleoDefinitionValidationException);
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private Map<String, ?> _evaluate(
			KaleoNotificationRecipient kaleoNotificationRecipient,
			ExecutionContext executionContext)
		throws Exception {

		String notificationRecipientEvaluatorKey =
			_getNotificationRecipientEvaluatorKey(
				kaleoNotificationRecipient.getRecipientScriptLanguage(),
				kaleoNotificationRecipient.getRecipientScript());

		NotificationRecipientEvaluator notificationRecipientEvaluator =
			_serviceTrackerMap.getService(notificationRecipientEvaluatorKey);

		if (notificationRecipientEvaluator == null) {
			throw new IllegalArgumentException(
				"No notification recipient evaluator for script language " +
					notificationRecipientEvaluatorKey);
		}

		return notificationRecipientEvaluator.evaluate(
			kaleoNotificationRecipient, executionContext);
	}

	private String _getNotificationRecipientEvaluatorKey(
			String language, String notificationRecipientEvaluatorClassName)
		throws KaleoDefinitionValidationException {

		ScriptLanguage scriptLanguage = ScriptLanguage.parse(language);

		if (scriptLanguage.equals(ScriptLanguage.JAVA)) {
			return language + StringPool.COLON +
				notificationRecipientEvaluatorClassName;
		}

		return language;
	}

	@Reference
	private RoleNotificationRecipientBuilder _roleNotificationRecipientBuilder;

	private ServiceTrackerMap<String, NotificationRecipientEvaluator>
		_serviceTrackerMap;

}