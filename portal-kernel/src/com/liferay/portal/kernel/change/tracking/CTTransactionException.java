/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.change.tracking;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Preston Crary
 */
public class CTTransactionException extends SystemException {

	public CTTransactionException() {
	}

	public CTTransactionException(String msg) {
		super(msg);
	}

	public CTTransactionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CTTransactionException(Throwable throwable) {
		super(throwable);
	}

}