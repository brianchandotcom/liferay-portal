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

package com.liferay.lcs.messaging;

/**
 * @author Ivica Cardic
 */
public class SignOffCommandMessage extends CommandMessage {

	public boolean isDeregister() {
		return _deregister;
	}

	public boolean isInvalidateToken() {
		return _invalidateToken;
	}

	public void setDeregister(boolean deregister) {
		_deregister = deregister;
	}

	public void setInvalidateToken(boolean invalidateToken) {
		_invalidateToken = invalidateToken;
	}

	private boolean _deregister;
	private boolean _invalidateToken;

}