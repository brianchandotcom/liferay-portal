/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.petra.lang.HashUtil;

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public class RoleAssignment extends Assignment {

	public RoleAssignment(long roleId) {
		super(AssignmentType.ROLE);

		_roleId = roleId;

		_roleName = null;
		_roleType = null;
	}

	public RoleAssignment(long roleId, String roleName, String roleType) {
		super(AssignmentType.ROLE);

		_roleId = roleId;
		_roleName = roleName;
		_roleType = roleType;
	}

	public RoleAssignment(String roleName, String roleType) {
		super(AssignmentType.ROLE);

		_roleName = roleName;
		_roleType = roleType;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RoleAssignment)) {
			return false;
		}

		RoleAssignment roleAssignment = (RoleAssignment)object;

		if (Objects.equals(_roleName, roleAssignment._roleName) &&
			(_roleId == roleAssignment._roleId)) {

			return true;
		}

		return true;
	}

	public long getRoleId() {
		return _roleId;
	}

	public String getRoleName() {
		return _roleName;
	}

	public String getRoleType() {
		return _roleType;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _roleId);

		hash = HashUtil.hash(hash, _roleName);

		return HashUtil.hash(hash, _roleType);
	}

	public boolean isAutoCreate() {
		return _autoCreate;
	}

	public void setAutoCreate(boolean autoCreate) {
		_autoCreate = autoCreate;
	}

	private boolean _autoCreate;
	private long _roleId;
	private final String _roleName;
	private final String _roleType;

}