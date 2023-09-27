/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowMetricsSLADefinitionDuplicateNameException
	extends PortalException {

	public WorkflowMetricsSLADefinitionDuplicateNameException() {
	}

	public WorkflowMetricsSLADefinitionDuplicateNameException(String msg) {
		super(msg);
	}

	public WorkflowMetricsSLADefinitionDuplicateNameException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public WorkflowMetricsSLADefinitionDuplicateNameException(
		Throwable throwable) {

		super(throwable);
	}

}