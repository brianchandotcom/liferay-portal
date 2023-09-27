/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

/**
 * @author Inácio Nery
 */
public class WorkflowDefinitionTitleException extends WorkflowException {

	public WorkflowDefinitionTitleException() {
	}

	public WorkflowDefinitionTitleException(String msg) {
		super(msg);
	}

	public WorkflowDefinitionTitleException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public WorkflowDefinitionTitleException(Throwable throwable) {
		super(throwable);
	}

}