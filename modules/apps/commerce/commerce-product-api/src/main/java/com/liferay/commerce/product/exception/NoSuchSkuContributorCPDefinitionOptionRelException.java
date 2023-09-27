/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchSkuContributorCPDefinitionOptionRelException
	extends NoSuchModelException {

	public NoSuchSkuContributorCPDefinitionOptionRelException() {
	}

	public NoSuchSkuContributorCPDefinitionOptionRelException(String msg) {
		super(msg);
	}

	public NoSuchSkuContributorCPDefinitionOptionRelException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchSkuContributorCPDefinitionOptionRelException(
		Throwable throwable) {

		super(throwable);
	}

}