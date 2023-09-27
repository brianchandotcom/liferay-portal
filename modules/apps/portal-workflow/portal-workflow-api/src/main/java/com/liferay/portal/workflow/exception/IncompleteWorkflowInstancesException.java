/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.exception;

import com.liferay.portal.kernel.workflow.WorkflowException;

/**
 * @author Rafael Praxedes
 */
public class IncompleteWorkflowInstancesException extends WorkflowException {

	public IncompleteWorkflowInstancesException() {
	}

	public IncompleteWorkflowInstancesException(int workflowInstancesCount) {
		_workflowInstancesCount = workflowInstancesCount;
	}

	public IncompleteWorkflowInstancesException(String msg) {
		super(msg);
	}

	public IncompleteWorkflowInstancesException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public IncompleteWorkflowInstancesException(Throwable throwable) {
		super(throwable);
	}

	public int getWorkflowInstancesCount() {
		return _workflowInstancesCount;
	}

	private int _workflowInstancesCount;

}