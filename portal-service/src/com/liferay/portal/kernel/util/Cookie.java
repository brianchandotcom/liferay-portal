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

package com.liferay.portal.kernel.util;

/**
 * @author Yuxing Wu
 */
public interface Cookie {

	public javax.servlet.http.Cookie deserialize(byte[] bytes);

	public boolean equals(
		javax.servlet.http.Cookie cookie1, javax.servlet.http.Cookie cookie2);

	public byte[] serialize(javax.servlet.http.Cookie cookie);

	public String toString(javax.servlet.http.Cookie cookie);

}