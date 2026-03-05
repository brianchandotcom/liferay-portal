/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoDefinitionGroupIdException extends PortalException {

	public KaleoDefinitionGroupIdException() {
	}

	public KaleoDefinitionGroupIdException(String msg) {
		super(msg);
	}

	public KaleoDefinitionGroupIdException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public KaleoDefinitionGroupIdException(Throwable throwable) {
		super(throwable);
	}

}