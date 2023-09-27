/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchCPOptionCategoryException extends NoSuchModelException {

	public NoSuchCPOptionCategoryException() {
	}

	public NoSuchCPOptionCategoryException(String msg) {
		super(msg);
	}

	public NoSuchCPOptionCategoryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchCPOptionCategoryException(Throwable throwable) {
		super(throwable);
	}

}