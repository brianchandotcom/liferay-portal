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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.security.Principal;

/**
 * @author Brian Wing Shun Chan
 */
public class ProtectedPrincipal implements Principal, Serializable {

	public ProtectedPrincipal() {
		this(StringPool.BLANK);
	}

	public ProtectedPrincipal(String name) {
		_name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ProtectedPrincipal)) {
			return false;
		}

		ProtectedPrincipal protectedPrincipal = (ProtectedPrincipal)obj;

		if (protectedPrincipal.getName().equals(_name)) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	@Override
	public String toString() {
		return _name;
	}

	private String _name;

}