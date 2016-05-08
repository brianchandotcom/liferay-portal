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

package com.liferay.portal.workflow.kaleo.runtime.scripting.internal.notification.recipient.script;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.script.NotificationRecipientEvaluator;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.script.ScriptingNotificationRecipientConstants;
import com.liferay.portal.workflow.kaleo.runtime.util.ScriptingContextBuilder;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"scripting.language=beanshell", "scripting.language=groovy",
		"scripting.language=javascript", "scripting.language=python",
		"scripting.language=ruby"
	},
	service = NotificationRecipientEvaluator.class
)
public class ScriptingNotificationRecipientEvaluator
	implements NotificationRecipientEvaluator {

	@Override
	public Map<String, ?> evaluate(
			KaleoNotificationRecipient kaleoNotificationRecipient,
			ExecutionContext executionContext)
		throws PortalException {

		Map<String, Object> inputObjects =
			_scriptingContextBuilder.buildScriptingContext(executionContext);

		return _scripting.eval(
			null, inputObjects, _outputNames,
			kaleoNotificationRecipient.getRecipientScriptLanguage(),
			kaleoNotificationRecipient.getRecipientScript());
	}

	private static final Set<String> _outputNames = new HashSet<>();

	static {
		_outputNames.add(
			ScriptingNotificationRecipientConstants.ROLES_RECIPIENT);
		_outputNames.add(
			ScriptingNotificationRecipientConstants.USER_RECIPIENT);
		_outputNames.add(WorkflowContextUtil.WORKFLOW_CONTEXT_NAME);
	}

	@Reference
	private Scripting _scripting;

	@Reference
	private ScriptingContextBuilder _scriptingContextBuilder;

}