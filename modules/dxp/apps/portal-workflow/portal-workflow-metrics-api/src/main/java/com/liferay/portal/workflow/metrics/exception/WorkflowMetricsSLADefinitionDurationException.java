/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowMetricsSLADefinitionDurationException
	extends PortalException {

	public WorkflowMetricsSLADefinitionDurationException() {
	}

	public WorkflowMetricsSLADefinitionDurationException(String msg) {
		super(msg);
	}

	public WorkflowMetricsSLADefinitionDurationException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public WorkflowMetricsSLADefinitionDurationException(Throwable throwable) {
		super(throwable);
	}

}