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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelector;

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
	immediate = true, service = CompositeKaleoTaskAssignmentSelector.class
)
public class CompositeKaleoTaskAssignmentSelector
	implements KaleoTaskAssignmentSelector {

	@Override
	public Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
			KaleoTaskAssignment kaleoTaskAssignment,
			ExecutionContext executionContext)
		throws PortalException {

		String assigneeClassName = kaleoTaskAssignment.getAssigneeClassName();

		KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector =
			_kaleoTaskAssignmentSelectors.get(assigneeClassName);

		if (kaleoTaskAssignmentSelector == null) {
			throw new IllegalArgumentException(
				"No task assignment selector found for class name " +
					assigneeClassName);
		}

		return kaleoTaskAssignmentSelector.getKaleoTaskAssignments(
			kaleoTaskAssignment, executionContext);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(assignee.class.name=*)"
	)
	protected void addKaleoTaskAssignmentSelector(
		KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector,
		Map<String, Object> properties) {

		String[] assigneeClassNames = _getAssigneeClassNames(
			kaleoTaskAssignmentSelector, properties);

		for (String assigneeClassName : assigneeClassNames) {
			_kaleoTaskAssignmentSelectors.put(
				assigneeClassName, kaleoTaskAssignmentSelector);
		}
	}

	protected void removeKaleoTaskAssignmentSelector(
		KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector,
		Map<String, Object> properties) {

		String[] assigneeClassNames = _getAssigneeClassNames(
			kaleoTaskAssignmentSelector, properties);

		for (String assigneeClassName : assigneeClassNames) {
			_kaleoTaskAssignmentSelectors.remove(assigneeClassName);
		}
	}

	private String[] _getAssigneeClassNames(
		KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector,
		Map<String, Object> properties) {

		Object value = properties.get("assignee.class.name");

		String[] assigneeClassNames = GetterUtil.getStringValues(
			value, new String[] {String.valueOf(value)});

		if (ArrayUtil.isEmpty(assigneeClassNames)) {
			throw new IllegalArgumentException(
				"The property \"assignee.class.name\" is invalid for " +
					ClassUtil.getClassName(kaleoTaskAssignmentSelector));
		}

		return assigneeClassNames;
	}

	private final Map<String, KaleoTaskAssignmentSelector>
		_kaleoTaskAssignmentSelectors = new HashMap<>();

}