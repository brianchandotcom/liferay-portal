/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class CPConfigurationListMasterException extends PortalException {

	public CPConfigurationListMasterException() {
	}

	public CPConfigurationListMasterException(String msg) {
		super(msg);
	}

	public CPConfigurationListMasterException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CPConfigurationListMasterException(Throwable throwable) {
		super(throwable);
	}

}