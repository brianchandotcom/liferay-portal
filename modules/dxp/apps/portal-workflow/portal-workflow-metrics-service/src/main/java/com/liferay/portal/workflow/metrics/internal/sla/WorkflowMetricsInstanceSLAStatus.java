/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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