/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alessio Antonio Rendina
 */
public class NoSuchCSDiagramSettingException extends NoSuchModelException {

	public NoSuchCSDiagramSettingException() {
	}

	public NoSuchCSDiagramSettingException(String msg) {
		super(msg);
	}

	public NoSuchCSDiagramSettingException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchCSDiagramSettingException(Throwable throwable) {
		super(throwable);
	}

}