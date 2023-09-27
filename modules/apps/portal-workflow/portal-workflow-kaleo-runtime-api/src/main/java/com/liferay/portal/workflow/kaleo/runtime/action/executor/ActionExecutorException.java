/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.action.executor;

import com.liferay.portal.kernel.workflow.WorkflowException;

/**
 * @author Michael C. Han
 */
public class ActionExecutorException extends WorkflowException {

	public ActionExecutorException() {
	}

	public ActionExecutorException(String msg) {
		super(msg);
	}

	public ActionExecutorException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ActionExecutorException(Throwable throwable) {
		super(throwable);
	}

}