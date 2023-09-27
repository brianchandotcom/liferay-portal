/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = AggregateKaleoTaskAssignmentSelector.class)
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