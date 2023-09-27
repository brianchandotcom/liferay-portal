/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoDefinitionNameException extends PortalException {

	public KaleoDefinitionNameException() {
	}

	public KaleoDefinitionNameException(String msg) {
		super(msg);
	}

	public KaleoDefinitionNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public KaleoDefinitionNameException(Throwable throwable) {
		super(throwable);
	}

}