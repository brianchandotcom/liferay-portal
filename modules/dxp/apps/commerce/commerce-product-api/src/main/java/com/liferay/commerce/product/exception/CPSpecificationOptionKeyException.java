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

package com.liferay.commerce.product.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CPSpecificationOptionKeyException extends PortalException {

	public CPSpecificationOptionKeyException() {
	}

	public CPSpecificationOptionKeyException(String msg) {
		super(msg);
	}

	public CPSpecificationOptionKeyException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CPSpecificationOptionKeyException(Throwable cause) {
		super(cause);
	}

	public static class MustNotBeDuplicate
		extends CPSpecificationOptionKeyException {

		public MustNotBeDuplicate(String key) {
			super("Duplicate key " + key);
		}

	}

	public static class MustNotBeNull
		extends CPSpecificationOptionKeyException {

		public MustNotBeNull() {
			super("Key must not be null");
		}

	}

}