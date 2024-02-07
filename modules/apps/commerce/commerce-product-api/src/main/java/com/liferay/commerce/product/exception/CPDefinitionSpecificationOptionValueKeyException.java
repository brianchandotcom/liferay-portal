/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class CPDefinitionSpecificationOptionValueKeyException
	extends PortalException {

	public CPDefinitionSpecificationOptionValueKeyException() {
	}

	public CPDefinitionSpecificationOptionValueKeyException(String msg) {
		super(msg);
	}

	public CPDefinitionSpecificationOptionValueKeyException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CPDefinitionSpecificationOptionValueKeyException(
		Throwable throwable) {

		super(throwable);
	}

}