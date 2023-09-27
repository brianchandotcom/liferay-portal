/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Micha Kiener
 * @author Brian Wing Shun Chan
 */
public class WorkflowException extends PortalException {

	public WorkflowException() {
	}

	public WorkflowException(String msg) {
		super(msg);
	}

	public WorkflowException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public WorkflowException(Throwable throwable) {
		super(throwable);
	}

}