/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marcellus Tavares
 */
public class DDMDataProviderException extends PortalException {

	public DDMDataProviderException() {
	}

	public DDMDataProviderException(String msg) {
		super(msg);
	}

	public DDMDataProviderException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DDMDataProviderException(Throwable throwable) {
		super(throwable);
	}

}