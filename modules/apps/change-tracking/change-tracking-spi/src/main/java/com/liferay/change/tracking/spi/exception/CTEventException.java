/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.spi.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Shuyang Zhou
 * @see    com.liferay.change.tracking.spi.listener.CTEventListener
 */
public class CTEventException extends PortalException {

	public CTEventException() {
	}

	public CTEventException(String msg) {
		super(msg);
	}

	public CTEventException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CTEventException(Throwable throwable) {
		super(throwable);
	}

}