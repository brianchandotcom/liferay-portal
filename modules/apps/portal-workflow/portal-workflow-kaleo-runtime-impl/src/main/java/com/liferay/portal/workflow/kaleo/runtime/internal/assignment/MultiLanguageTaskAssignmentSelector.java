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

package com.liferay.portal.workflow.kaleo.runtime.internal.assignment;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.definition.ScriptLanguage;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.BaseTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "assignee.class.name=SCRIPT",
	service = TaskAssignmentSelector.class
)
public class MultiLanguageTaskAssignmentSelector
	extends BaseTaskAssignmentSelector {

	@Override
	public Collection<KaleoTaskAssignment> calculateTaskAssignments(
			KaleoTaskAssignment kaleoTaskAssignment,
			ExecutionContext executionContext)
		throws PortalException {

		String assigneeClassName = kaleoTaskAssignment.getAssigneeClassName();

		TaskAssignmentSelector taskAssignmentSelector = null;

		if (assigneeClassName.equals(ResourceAction.class.getName())) {
			taskAssignmentSelector = _taskAssignmentSelectors.get(
				assigneeClassName);
		}
		else {
			String taskAssignmentSelectorKey = getTaskAssignmentSelectKey(
				kaleoTaskAssignment.getAssigneeScriptLanguage(),
				StringUtil.trim(kaleoTaskAssignment.getAssigneeScript()));

			taskAssignmentSelector = _taskAssignmentSelectors.get(
				taskAssignmentSelectorKey);
		}

		if (taskAssignmentSelector == null) {
			throw new IllegalArgumentException(
				"No task assignment selector found for " +
					kaleoTaskAssignment.toXmlString());
		}

		Collection<KaleoTaskAssignment> taskAssignments =
			taskAssignmentSelector.calculateTaskAssignments(
				kaleoTaskAssignment, executionContext);

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		_kaleoInstanceLocalService.updateKaleoInstance(
			kaleoInstanceToken.getKaleoInstanceId(),
			executionContext.getWorkflowContext(),
			executionContext.getServiceContext());

		return taskAssignments;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(scripting.language=*)"
	)
	protected void addTaskAssignmentSelector(
		TaskAssignmentSelector taskAssignmentSelector,
		Map<String, Object> properties) {

		String[] scriptingLanguages = getScriptingLanguages(
			taskAssignmentSelector, properties);

		for (String scriptingLanguage : scriptingLanguages) {
			String taskAssignmentSelectorKey = getTaskAssignmentSelectKey(
				scriptingLanguage,
				ClassUtil.getClassName(taskAssignmentSelector));

			_taskAssignmentSelectors.put(
				taskAssignmentSelectorKey, taskAssignmentSelector);
		}
	}

	protected String[] getScriptingLanguages(
		TaskAssignmentSelector taskAssignmentSelector,
		Map<String, Object> properties) {

		Object value = properties.get("scripting.language");

		String[] scriptingLanguages = GetterUtil.getStringValues(
			value, new String[] {String.valueOf(value)});

		if (ArrayUtil.isEmpty(scriptingLanguages)) {
			throw new IllegalArgumentException(
				"The property \"scripting.language\" is invalid for " +
					ClassUtil.getClassName(taskAssignmentSelector));
		}

		return scriptingLanguages;
	}

	protected String getTaskAssignmentSelectKey(
		String language, String taskAssignmentSelectorClassName) {

		ScriptLanguage scriptLanguage = ScriptLanguage.parse(language);

		if (scriptLanguage.equals(ScriptLanguage.JAVA)) {
			return language + StringPool.COLON +
				taskAssignmentSelectorClassName;
		}

		return language;
	}

	protected void removeTaskAssignmentSelector(
		TaskAssignmentSelector taskAssignmentSelector,
		Map<String, Object> properties) {

		String[] scriptingLanguages = getScriptingLanguages(
			taskAssignmentSelector, properties);

		for (String scriptingLanguage : scriptingLanguages) {
			String taskAssignmentSelectorKey = getTaskAssignmentSelectKey(
				scriptingLanguage,
				ClassUtil.getClassName(taskAssignmentSelector));

			_taskAssignmentSelectors.remove(taskAssignmentSelectorKey);
		}
	}

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	private final Map<String, TaskAssignmentSelector> _taskAssignmentSelectors =
		new HashMap<>();

}