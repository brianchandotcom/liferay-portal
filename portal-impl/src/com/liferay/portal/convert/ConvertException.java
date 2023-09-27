/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.convert;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alexander Chow
 */
public class ConvertException extends PortalException {

	public ConvertException() {
	}

	public ConvertException(String msg) {
		super(msg);
	}

	public ConvertException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ConvertException(Throwable throwable) {
		super(throwable);
	}

}