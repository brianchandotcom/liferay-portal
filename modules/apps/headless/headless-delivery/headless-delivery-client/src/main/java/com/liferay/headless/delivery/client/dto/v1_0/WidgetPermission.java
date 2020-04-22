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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.WidgetPermissionSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WidgetPermission implements Cloneable {

	public String[] getActionKeys() {
		return actionKeys;
	}

	public void setActionKeys(String[] actionKeys) {
		this.actionKeys = actionKeys;
	}

	public void setActionKeys(
		UnsafeSupplier<String[], Exception> actionKeysUnsafeSupplier) {

		try {
			actionKeys = actionKeysUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] actionKeys;

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public void setRoleKey(
		UnsafeSupplier<String, Exception> roleKeyUnsafeSupplier) {

		try {
			roleKey = roleKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String roleKey;

	@Override
	public WidgetPermission clone() throws CloneNotSupportedException {
		return (WidgetPermission)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WidgetPermission)) {
			return false;
		}

		WidgetPermission widgetPermission = (WidgetPermission)object;

		return Objects.equals(toString(), widgetPermission.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WidgetPermissionSerDes.toJSON(this);
	}

}