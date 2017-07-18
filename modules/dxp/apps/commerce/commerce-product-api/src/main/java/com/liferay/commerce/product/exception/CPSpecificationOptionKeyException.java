/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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