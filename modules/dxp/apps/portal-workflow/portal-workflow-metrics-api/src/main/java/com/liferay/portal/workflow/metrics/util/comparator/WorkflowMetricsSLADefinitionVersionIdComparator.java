/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;

/**
 * @author Inácio Nery
 */
public class WorkflowMetricsSLADefinitionVersionIdComparator
	extends OrderByComparator<WorkflowMetricsSLADefinitionVersion> {

	public WorkflowMetricsSLADefinitionVersionIdComparator() {
		this(false);
	}

	public WorkflowMetricsSLADefinitionVersionIdComparator(boolean ascending) {
		_ascending = ascending;
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