/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.PermissionDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class PermissionDefinition
	extends BulkActionDefinition implements Cloneable, Serializable {

	public static PermissionDefinition toDTO(String json) {
		return PermissionDefinitionSerDes.toDTO(json);
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
	public PermissionDefinition clone() throws CloneNotSupportedException {
		return (PermissionDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PermissionDefinition)) {
			return false;
		}

		PermissionDefinition permissionDefinition =
			(PermissionDefinition)object;

		return Objects.equals(toString(), permissionDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PermissionDefinitionSerDes.toJSON(this);
	}

}