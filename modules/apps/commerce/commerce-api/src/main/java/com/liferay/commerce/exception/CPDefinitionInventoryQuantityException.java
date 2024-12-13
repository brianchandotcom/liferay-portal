/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionInventoryQuantityException extends PortalException {

	public CPDefinitionInventoryQuantityException() {
	}

	public CPDefinitionInventoryQuantityException(String msg) {
		super(msg);
	}

	public CPDefinitionInventoryQuantityException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CPDefinitionInventoryQuantityException(Throwable throwable) {
		super(throwable);
	}

}