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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class UserPasswordException extends PortalException {

	public static final int PASSWORD_ALREADY_USED = 1;

	public static final int PASSWORD_CONTAINS_TRIVIAL_WORDS = 2;

	public static final int PASSWORD_INVALID = 3;

	public static final int PASSWORD_LENGTH = 4;

	public static final int PASSWORD_NOT_CHANGEABLE = 5;

	public static final int PASSWORD_SAME_AS_CURRENT = 6;

	public static final int PASSWORD_TOO_TRIVIAL = 8;

	public static final int PASSWORD_TOO_YOUNG = 9;

	public static final int PASSWORDS_DO_NOT_MATCH = 10;

	public UserPasswordException(int type) {
		_type = type;
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}