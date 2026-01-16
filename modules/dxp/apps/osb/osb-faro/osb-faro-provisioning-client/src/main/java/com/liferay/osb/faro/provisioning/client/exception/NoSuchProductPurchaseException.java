/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.provisioning.client.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marcos Martins
 */
public class NoSuchProductPurchaseException extends NoSuchModelException {

	public NoSuchProductPurchaseException() {
	}

	public NoSuchProductPurchaseException(String msg) {
		super(msg);
	}

	public NoSuchProductPurchaseException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchProductPurchaseException(Throwable throwable) {
		super(throwable);
	}

}