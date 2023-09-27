/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
public class CPSpecificationOptionKeyException extends PortalException {

	public CPSpecificationOptionKeyException() {
	}

	public CPSpecificationOptionKeyException(String msg) {
		super(msg);
	}

	public CPSpecificationOptionKeyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CPSpecificationOptionKeyException(Throwable throwable) {
		super(throwable);
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