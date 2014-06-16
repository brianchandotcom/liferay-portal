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
public class DuplicateOpenIdException extends PortalException {

	@Deprecated
	public DuplicateOpenIdException() {
		super();
	}

	public DuplicateOpenIdException(String openId, long userId) {
		super("The open ID " + openId + " is already used by user " + userId);

		_openId = openId;
		_userId = userId;
	}

	public String getOpenId() {
		return _openId;
	}

	public long getUserId() {
		return _userId;
	}

	private String _openId;
	private long _userId;

}