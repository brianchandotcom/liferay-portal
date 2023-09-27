/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

/**
 * @author Marcellus Tavares
 */
public class WorkflowDefinitionFileException extends WorkflowException {

	public WorkflowDefinitionFileException() {
	}

	public WorkflowDefinitionFileException(String msg) {
		super(msg);
	}

	public WorkflowDefinitionFileException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public WorkflowDefinitionFileException(Throwable throwable) {
		super(throwable);
	}

}