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
import com.liferay.headless.delivery.client.serdes.v1_0.WidgetInstanceSerDes;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WidgetInstance implements Cloneable {

	public Map<String, Object> getWidgetConfig() {
		return widgetConfig;
	}

	public void setWidgetConfig(Map<String, Object> widgetConfig) {
		this.widgetConfig = widgetConfig;
	}

	public void setWidgetConfig(
		UnsafeSupplier<Map<String, Object>, Exception>
			widgetConfigUnsafeSupplier) {

		try {
			widgetConfig = widgetConfigUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> widgetConfig;

	public String getWidgetInstanceId() {
		return widgetInstanceId;
	}

	public void setWidgetInstanceId(String widgetInstanceId) {
		this.widgetInstanceId = widgetInstanceId;
	}

	public void setWidgetInstanceId(
		UnsafeSupplier<String, Exception> widgetInstanceIdUnsafeSupplier) {

		try {
			widgetInstanceId = widgetInstanceIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String widgetInstanceId;

	public String getWidgetName() {
		return widgetName;
	}

	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
	}

	public void setWidgetName(
		UnsafeSupplier<String, Exception> widgetNameUnsafeSupplier) {

		try {
			widgetName = widgetNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String widgetName;

	public WidgetPermission[] getWidgetPermissions() {
		return widgetPermissions;
	}

	public void setWidgetPermissions(WidgetPermission[] widgetPermissions) {
		this.widgetPermissions = widgetPermissions;
	}

	public void setWidgetPermissions(
		UnsafeSupplier<WidgetPermission[], Exception>
			widgetPermissionsUnsafeSupplier) {

		try {
			widgetPermissions = widgetPermissionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WidgetPermission[] widgetPermissions;

	@Override
	public WidgetInstance clone() throws CloneNotSupportedException {
		return (WidgetInstance)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WidgetInstance)) {
			return false;
		}

		WidgetInstance widgetInstance = (WidgetInstance)object;

		return Objects.equals(toString(), widgetInstance.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WidgetInstanceSerDes.toJSON(this);
	}

}