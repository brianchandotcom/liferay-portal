/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.PermissionActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class PermissionAction
	extends DocumentBulkAction implements Cloneable, Serializable {

	public static PermissionAction toDTO(String json) {
		return PermissionActionSerDes.toDTO(json);
	}

	public com.liferay.headless.cms.client.permission.Permission[]
		getPermissions() {

		return permissions;
	}

	public void setPermissions(
		com.liferay.headless.cms.client.permission.Permission[] permissions) {

		this.permissions = permissions;
	}

	public void setPermissions(
		UnsafeSupplier
			<com.liferay.headless.cms.client.permission.Permission[], Exception>
				permissionsUnsafeSupplier) {

		try {
			permissions = permissionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected com.liferay.headless.cms.client.permission.Permission[]
		permissions;

	@Override
	public PermissionAction clone() throws CloneNotSupportedException {
		return (PermissionAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PermissionAction)) {
			return false;
		}

		PermissionAction permissionAction = (PermissionAction)object;

		return Objects.equals(toString(), permissionAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PermissionActionSerDes.toJSON(this);
	}

}