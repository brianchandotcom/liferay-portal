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
	property = "assignee.class.name=SCRIPT",
	service = KaleoTaskAssignmentSelector.class
)
public class MultiLanguageKaleoTaskAssignmentSelector
	extends BaseKaleoTaskAssignmentSelector {

	@Override
	public Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
			KaleoTaskAssignment kaleoTaskAssignment,
			ExecutionContext executionContext)
		throws PortalException {

		KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector =
			_kaleoTaskAssignmentSelectors.get(
				_getKaleoTaskAssignmentSelectKey(
					kaleoTaskAssignment.getAssigneeScriptLanguage(),
					StringUtil.trim(kaleoTaskAssignment.getAssigneeScript())));

		if (kaleoTaskAssignmentSelector == null) {
			throw new IllegalArgumentException(
				"No task assignment selector found for " +
					kaleoTaskAssignment.toString());
		}

		Collection<KaleoTaskAssignment> kaleoTaskAssignments =
			kaleoTaskAssignmentSelector.getKaleoTaskAssignments(
				kaleoTaskAssignment, executionContext);

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		_kaleoInstanceLocalService.updateKaleoInstance(
			kaleoInstanceToken.getKaleoInstanceId(),
			executionContext.getWorkflowContext());

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

		_kaleoTaskAssignmentSelectors.put(
			_getKaleoTaskAssignmentSelectKey(
				GetterUtil.getString(properties.get("scripting.language")),
				ClassUtil.getClassName(kaleoTaskAssignmentSelector)),
			kaleoTaskAssignmentSelector);
	}

	protected void removeKaleoTaskAssignmentSelector(
			KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector,
			Map<String, Object> properties)
		throws KaleoDefinitionValidationException {

		_kaleoTaskAssignmentSelectors.remove(
			_getKaleoTaskAssignmentSelectKey(
				GetterUtil.getString(properties.get("scripting.language")),
				ClassUtil.getClassName(kaleoTaskAssignmentSelector)));
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

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	private final Map<String, KaleoTaskAssignmentSelector>
		_kaleoTaskAssignmentSelectors = new HashMap<>();

}