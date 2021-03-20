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

import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelectorRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, service = KaleoTaskAssignmentSelectorRegistry.class
)
public class KaleoTaskAssignmentSelectorTracker
	implements KaleoTaskAssignmentSelectorRegistry {

	public KaleoTaskAssignmentSelector getKaleoTaskAssignmentSelector(
		String assigneeClassName) {

		return _kaleoTaskAssignmentSelectors.get(assigneeClassName);
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

		Object assigneeClassName = properties.get("assignee.class.name");

		_kaleoTaskAssignmentSelectors.put(
			assigneeClassName.toString(), kaleoTaskAssignmentSelector);
	}

	protected void removeKaleoTaskAssignmentSelector(
		KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector,
		Map<String, Object> properties) {

		String assigneeClassName = (String)properties.get(
			"assignee.class.name");

		_kaleoTaskAssignmentSelectors.remove(assigneeClassName);
	}

	private final Map<String, KaleoTaskAssignmentSelector>
		_kaleoTaskAssignmentSelectors = new ConcurrentHashMap<>();

}