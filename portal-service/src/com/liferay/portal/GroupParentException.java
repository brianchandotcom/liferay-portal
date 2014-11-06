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
public class GroupParentException extends PortalException {

	public static final int CHILD_DESCENDANT = 3;

	public static final int SELF_DESCENDANT = 1;

	public static final int STAGING_DESCENDANT = 2;

	public GroupParentException() {
		_type = 0;
	}

	public GroupParentException(int type) {
		_type = type;
	}

	public GroupParentException(String msg) {
		super(msg);

		_type = 0;
	}

	public GroupParentException(String msg, Throwable cause) {
		super(msg, cause);

		_type = 0;
	}

	public GroupParentException(Throwable cause) {
		super(cause);

		_type = 0;
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}