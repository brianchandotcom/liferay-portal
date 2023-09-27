/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alessio Antonio Rendina
 */
public class NoSuchCSDiagramPinException extends NoSuchModelException {

	public NoSuchCSDiagramPinException() {
	}

	public NoSuchCSDiagramPinException(String msg) {
		super(msg);
	}

	public NoSuchCSDiagramPinException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchCSDiagramPinException(Throwable throwable) {
		super(throwable);
	}

}