/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
public class CommercePaymentEngineException extends PortalException {

	public CommercePaymentEngineException() {
	}

	public CommercePaymentEngineException(String msg) {
		super(msg);
	}

	public CommercePaymentEngineException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CommercePaymentEngineException(Throwable throwable) {
		super(throwable);
	}

	public static class MustSetPrimaryCurrency
		extends CommercePaymentEngineException {

		public MustSetPrimaryCurrency() {
			super("Unable to get primary currency");
		}

	}

}