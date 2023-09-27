/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.model.Permission;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionImpl implements Permission, Serializable {

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