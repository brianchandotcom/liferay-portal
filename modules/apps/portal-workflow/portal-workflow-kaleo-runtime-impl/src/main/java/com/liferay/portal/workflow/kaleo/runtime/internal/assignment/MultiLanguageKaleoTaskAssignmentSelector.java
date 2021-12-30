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
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.BaseKaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelector;
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
	service = KaleoTaskAssignmentSelector.class
)
public class MultiLanguageKaleoTaskAssignmentSelector
	extends BaseKaleoTaskAssignmentSelector {

	@Override
	public Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
			KaleoTaskAssignment kaleoTaskAssignment,
			ExecutionContext executionContext)
		throws PortalException {

		String assigneeClassName = kaleoTaskAssignment.getAssigneeClassName();

		KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector = null;

		if (assigneeClassName.equals(ResourceAction.class.getName())) {
			kaleoTaskAssignmentSelector = _kaleoTaskAssignmentSelectors.get(
				assigneeClassName);
		}
		else {
			String kaleoTaskAssignmentSelectorKey =
				_getKaleoTaskAssignmentSelectKey(
					kaleoTaskAssignment.getAssigneeScriptLanguage(),
					StringUtil.trim(kaleoTaskAssignment.getAssigneeScript()));

			kaleoTaskAssignmentSelector = _kaleoTaskAssignmentSelectors.get(
				kaleoTaskAssignmentSelectorKey);
		}

		if (kaleoTaskAssignmentSelector == null) {
			throw new IllegalArgumentException(
				"No task assignment selector found for " +
					kaleoTaskAssignment.toXmlString());
		}

		Collection<KaleoTaskAssignment> kaleoTaskAssignments =
			kaleoTaskAssignmentSelector.getKaleoTaskAssignments(
				kaleoTaskAssignment, executionContext);

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		_kaleoInstanceLocalService.updateKaleoInstance(
			kaleoInstanceToken.getKaleoInstanceId(),
			executionContext.getWorkflowContext(),
			executionContext.getServiceContext());

		return kaleoTaskAssignments;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(scripting.language=*)"
	)
	protected void addKaleoTaskAssignmentSelector(
			KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector,
			Map<String, Object> properties)
		throws KaleoDefinitionValidationException {

		String[] scriptingLanguages = _getScriptingLanguages(
			kaleoTaskAssignmentSelector, properties);

		for (String scriptingLanguage : scriptingLanguages) {
			String kaleoTaskAssignmentSelectKey =
				_getKaleoTaskAssignmentSelectKey(
					scriptingLanguage,
					ClassUtil.getClassName(kaleoTaskAssignmentSelector));

			_kaleoTaskAssignmentSelectors.put(
				kaleoTaskAssignmentSelectKey, kaleoTaskAssignmentSelector);
		}
	}

	protected void removeKaleoTaskAssignmentSelector(
			KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector,
			Map<String, Object> properties)
		throws KaleoDefinitionValidationException {

		String[] scriptingLanguages = _getScriptingLanguages(
			kaleoTaskAssignmentSelector, properties);

		for (String scriptingLanguage : scriptingLanguages) {
			String kaleoTaskAssignmentSelectKey =
				_getKaleoTaskAssignmentSelectKey(
					scriptingLanguage,
					ClassUtil.getClassName(kaleoTaskAssignmentSelector));

			_kaleoTaskAssignmentSelectors.remove(kaleoTaskAssignmentSelectKey);
		}
	}

	private String _getKaleoTaskAssignmentSelectKey(
			String language, String kaleoTaskAssignmentSelectorClassName)
		throws KaleoDefinitionValidationException {

		ScriptLanguage scriptLanguage = ScriptLanguage.parse(language);

		if (scriptLanguage.equals(ScriptLanguage.JAVA)) {
			return language + StringPool.COLON +
				kaleoTaskAssignmentSelectorClassName;
		}

		return language;
	}

	private String[] _getScriptingLanguages(
		KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector,
		Map<String, Object> properties) {

		Object value = properties.get("scripting.language");

		String[] scriptingLanguages = GetterUtil.getStringValues(
			value, new String[] {String.valueOf(value)});

		if (ArrayUtil.isEmpty(scriptingLanguages)) {
			throw new IllegalArgumentException(
				"The property \"scripting.language\" is invalid for " +
					ClassUtil.getClassName(kaleoTaskAssignmentSelector));
		}

		return scriptingLanguages;
	}

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	private final Map<String, KaleoTaskAssignmentSelector>
		_kaleoTaskAssignmentSelectors = new HashMap<>();

}