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
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.AggregateKaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelectorRegistry;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = AggregateKaleoTaskAssignmentSelector.class
)
public class AggregateKaleoTaskAssignmentSelectorImpl
	implements AggregateKaleoTaskAssignmentSelector {

	@Override
	public Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
			List<KaleoTaskAssignment> kaleoTaskAssignments,
			ExecutionContext executionContext)
		throws PortalException {

		Comparator<KaleoTaskAssignment> comparator = Comparator.comparing(
			KaleoTaskAssignment::getAssigneeClassPK);

		comparator = comparator.thenComparing(
			KaleoTaskAssignment::getAssigneeClassName);

		Set<KaleoTaskAssignment> kaleoTaskAssignmentsSet = new TreeSet<>(
			comparator);

		for (KaleoTaskAssignment kaleoTaskAssignment : kaleoTaskAssignments) {
			KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector =
				_kaleoTaskAssignmentSelectorRegistry.
					getKaleoTaskAssignmentSelector(
						kaleoTaskAssignment.getAssigneeClassName());

			kaleoTaskAssignmentsSet.addAll(
				kaleoTaskAssignmentSelector.getKaleoTaskAssignments(
					kaleoTaskAssignment, executionContext));
		}

		return kaleoTaskAssignmentsSet;
	}

	@Reference
	private KaleoTaskAssignmentSelectorRegistry
		_kaleoTaskAssignmentSelectorRegistry;

}