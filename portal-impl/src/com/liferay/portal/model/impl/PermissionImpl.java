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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.Permission;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionImpl implements Permission, Serializable {

	public PermissionImpl() {
	}

	@Override
	public String getActionId() {
		return _actionId;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getPrimKey() {
		return _primKey;
	}

	@Override
	public int getScope() {
		return _scope;
	}

	@Override
	public void setActionId(String actionId) {
		_actionId = actionId;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPrimKey(String primKey) {
		_primKey = primKey;
	}

	@Override
	public void setScope(int scope) {
		_scope = scope;
	}

	private String _actionId;
	private String _name;
	private String _primKey;
	private int _scope;

}