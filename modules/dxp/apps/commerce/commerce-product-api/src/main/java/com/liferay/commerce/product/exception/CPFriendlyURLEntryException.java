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
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CPFriendlyURLEntryException extends PortalException {

	public static final int ADJACENT_SLASHES = 3;

	public static final int ENDS_WITH_SLASH = 2;

	public static final int INVALID_CHARACTERS = 4;

	public static final int TOO_DEEP = 5;

	public static final int TOO_LONG = 1;

	public CPFriendlyURLEntryException(int type) {
		_type = type;
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}