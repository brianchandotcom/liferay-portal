/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderImporterTypeException extends PortalException {

	public CommerceOrderImporterTypeException() {
	}

	public CommerceOrderImporterTypeException(String msg) {
		super(msg);
	}

	public CommerceOrderImporterTypeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CommerceOrderImporterTypeException(Throwable throwable) {
		super(throwable);
	}

}