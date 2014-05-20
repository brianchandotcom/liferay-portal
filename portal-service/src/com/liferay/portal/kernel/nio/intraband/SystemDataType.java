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

package com.liferay.portal.kernel.nio.intraband;

/**
 * @author Shuyang Zhou
 */
public enum SystemDataType {

	MAILBOX((byte)3), MESSAGE((byte)2), PORTAL_CACHE((byte)1), PROXY((byte)4),
	RPC((byte)0);

	public byte getValue() {
		return _value;
	}

	private SystemDataType(byte value) {
		_value = value;
	}

	private byte _value;

}