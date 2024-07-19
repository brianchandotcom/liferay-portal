/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;

/**
 * @author Inácio Nery
 */
public class WorkflowMetricsSLADefinitionVersionIdComparator
	extends OrderByComparator<WorkflowMetricsSLADefinitionVersion> {

	public static WorkflowMetricsSLADefinitionVersionIdComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion1,
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion2) {

		int value = Long.compare(
			workflowMetricsSLADefinitionVersion1.
				getWorkflowMetricsSLADefinitionVersionId(),
			workflowMetricsSLADefinitionVersion2.
				getWorkflowMetricsSLADefinitionVersionId());

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private WorkflowMetricsSLADefinitionVersionIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final WorkflowMetricsSLADefinitionVersionIdComparator
		_INSTANCE_ASCENDING =
			new WorkflowMetricsSLADefinitionVersionIdComparator(true);

	private static final WorkflowMetricsSLADefinitionVersionIdComparator
		_INSTANCE_DESCENDING =
			new WorkflowMetricsSLADefinitionVersionIdComparator(false);

	private static final String _ORDER_BY_ASC =
		"WorkflowMetricsSLADefinitionVersion." +
			"workflowMetricsSLADefinitionVersionId ASC";

	private static final String _ORDER_BY_DESC =
		"WorkflowMetricsSLADefinitionVersion." +
			"workflowMetricsSLADefinitionVersionId DESC";

	private static final String[] _ORDER_BY_FIELDS = {
		"workflowMetricsSLADefinitionVersionId"
	};

	private final boolean _ascending;

}