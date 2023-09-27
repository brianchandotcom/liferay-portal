/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductBundleException extends PortalException {

	public ProductBundleException() {
	}

	public ProductBundleException(String msg) {
		super(msg);
	}

	public ProductBundleException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ProductBundleException(Throwable throwable) {
		super(throwable);
	}

}