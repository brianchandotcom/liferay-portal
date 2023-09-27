/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.scripting.internal.notification.recipient.script;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.script.NotificationRecipientEvaluator;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.script.constants.ScriptingNotificationRecipientConstants;
import com.liferay.portal.workflow.kaleo.runtime.scripting.internal.util.KaleoScriptingEvaluator;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "scripting.language=groovy",
	service = NotificationRecipientEvaluator.class
)
public class GroovyNotificationRecipientEvaluator
	implements NotificationRecipientEvaluator {

	@Override
	public Map<String, ?> evaluate(
			KaleoNotificationRecipient kaleoNotificationRecipient,
			ExecutionContext executionContext)
		throws PortalException {

		return _kaleoScriptingEvaluator.execute(
			executionContext, _outputNames,
			kaleoNotificationRecipient.getRecipientScriptLanguage(),
			kaleoNotificationRecipient.getRecipientScript());
	}

	private static final Set<String> _outputNames = new HashSet<>(
		Arrays.asList(
			ScriptingNotificationRecipientConstants.ROLES_RECIPIENT,
			ScriptingNotificationRecipientConstants.USER_RECIPIENT,
			WorkflowContextUtil.WORKFLOW_CONTEXT_NAME));

	@Reference
	private KaleoScriptingEvaluator _kaleoScriptingEvaluator;

}