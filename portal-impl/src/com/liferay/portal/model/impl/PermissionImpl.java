/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.Permission;

/**
 * Represents a permission to perform an action on a resource in permissions
 * versions &lt; 6.
 *
 * @author Brian Wing Shun Chan
 */
public class PermissionImpl extends PermissionModelImpl implements Permission {

	public PermissionImpl() {
	}

	public String getName() {
		return _name;
	}

	public String getPrimKey() {
		return _primKey;
	}

	public int getScope() {
		return _scope;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPrimKey(String primKey) {
		_primKey = primKey;
	}

	public void setScope(int scope) {
		_scope = scope;
	}

	private String _name;
	private String _primKey;
	private int _scope;

}