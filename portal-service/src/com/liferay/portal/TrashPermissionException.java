/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * @author Zsolt Berentey
 */
public class TrashPermissionException extends PortalException {

	public static final int ERROR_DELETE = 1;

	public static final int ERROR_EMPTY_TRASH = 2;

	public static final int ERROR_MOVE = 3;

	public static final int ERROR_RESTORE = 4;

	public static final int ERROR_RESTORE_OVERWRITE = 5;

	public static final int ERROR_RESTORE_RENAME = 6;

	public TrashPermissionException(int type) {
		_type = type;
	}

	public int getType() {
		return _type;
	}

	private int _type;

}