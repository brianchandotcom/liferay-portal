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

package com.liferay.portal.workflow.metrics.internal.sla;

import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public enum WorkflowMetricsInstanceSLAStatus {

	ON_TIME("OnTime"), OVERDUE("Overdue"), UNTRACKED("Untracked");

	public static WorkflowMetricsInstanceSLAStatus create(String value) {
		for (WorkflowMetricsInstanceSLAStatus slaStatus : values()) {
			if (Objects.equals(slaStatus.getValue(), value)) {
				return slaStatus;
			}
		}

		return null;
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private WorkflowMetricsInstanceSLAStatus(String value) {
		_value = value;
	}

	private final String _value;

}