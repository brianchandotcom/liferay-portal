/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.NestedWidgetSectionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class NestedWidgetSection implements Cloneable, Serializable {

	public static NestedWidgetSection toDTO(String json) {
		return NestedWidgetSectionSerDes.toDTO(json);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String id;

	public WidgetPageWidgetInstance[] getWidgetPageWidgetInstances() {
		return widgetPageWidgetInstances;
	}

	public void setWidgetPageWidgetInstances(
		WidgetPageWidgetInstance[] widgetPageWidgetInstances) {

		this.widgetPageWidgetInstances = widgetPageWidgetInstances;
	}

	public void setWidgetPageWidgetInstances(
		UnsafeSupplier<WidgetPageWidgetInstance[], Exception>
			widgetPageWidgetInstancesUnsafeSupplier) {

		try {
			widgetPageWidgetInstances =
				widgetPageWidgetInstancesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WidgetPageWidgetInstance[] widgetPageWidgetInstances;

	@Override
	public NestedWidgetSection clone() throws CloneNotSupportedException {
		return (NestedWidgetSection)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NestedWidgetSection)) {
			return false;
		}

		NestedWidgetSection nestedWidgetSection = (NestedWidgetSection)object;

		return Objects.equals(toString(), nestedWidgetSection.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return NestedWidgetSectionSerDes.toJSON(this);
	}

}