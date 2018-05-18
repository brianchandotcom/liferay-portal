/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CommercePaymentEngineException extends PortalException {

	public CommercePaymentEngineException() {
	}

	public CommercePaymentEngineException(String msg) {
		super(msg);
	}

	public CommercePaymentEngineException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CommercePaymentEngineException(Throwable cause) {
		super(cause);
	}

	public static class MustSetPrimaryCurrency
		extends CommercePaymentEngineException {

		public MustSetPrimaryCurrency() {
			super("Unable to get primary currency");
		}

	}

}