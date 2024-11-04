/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchCPConfigurationListException extends NoSuchModelException {

	public NoSuchCPConfigurationListException() {
	}

	public NoSuchCPConfigurationListException(String msg) {
		super(msg);
	}

	public NoSuchCPConfigurationListException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchCPConfigurationListException(Throwable throwable) {
		super(throwable);
	}

}