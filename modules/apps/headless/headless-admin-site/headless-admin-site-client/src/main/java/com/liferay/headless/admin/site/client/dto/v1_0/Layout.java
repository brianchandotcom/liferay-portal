/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.LayoutSerDes;

import java.io.Serializable;

import java.util.Objects;

import jakarta.annotation.Generated;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class Layout implements Cloneable, Serializable {

	public static Layout toDTO(String json) {
		return LayoutSerDes.toDTO(json);
	}

	public ContainerType getContainerType() {
		return containerType;
	}

	public String getContainerTypeAsString() {
		if (containerType == null) {
			return null;
		}

		return containerType.toString();
	}

	public void setContainerType(ContainerType containerType) {
		this.containerType = containerType;
	}

	public void setContainerType(
		UnsafeSupplier<ContainerType, Exception> containerTypeUnsafeSupplier) {

		try {
			containerType = containerTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ContainerType containerType;

	public FlexWrap getFlexWrap() {
		return flexWrap;
	}

	public String getFlexWrapAsString() {
		if (flexWrap == null) {
			return null;
		}

		return flexWrap.toString();
	}

	public void setFlexWrap(FlexWrap flexWrap) {
		this.flexWrap = flexWrap;
	}

	public void setFlexWrap(
		UnsafeSupplier<FlexWrap, Exception> flexWrapUnsafeSupplier) {

		try {
			flexWrap = flexWrapUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FlexWrap flexWrap;

	public WidthType getWidthType() {
		return widthType;
	}

	public String getWidthTypeAsString() {
		if (widthType == null) {
			return null;
		}

		return widthType.toString();
	}

	public void setWidthType(WidthType widthType) {
		this.widthType = widthType;
	}

	public void setWidthType(
		UnsafeSupplier<WidthType, Exception> widthTypeUnsafeSupplier) {

		try {
			widthType = widthTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WidthType widthType;

	@Override
	public Layout clone() throws CloneNotSupportedException {
		return (Layout)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Layout)) {
			return false;
		}

		Layout layout = (Layout)object;

		return Objects.equals(toString(), layout.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LayoutSerDes.toJSON(this);
	}

	public static enum ContainerType {

		FIXED("Fixed"), FLUID("Fluid");

		public static ContainerType create(String value) {
			for (ContainerType containerType : values()) {
				if (Objects.equals(containerType.getValue(), value) ||
					Objects.equals(containerType.name(), value)) {

					return containerType;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private ContainerType(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum FlexWrap {

		NO_WRAP("NoWrap"), WRAP("Wrap"), WRAP_REVERSE("WrapReverse");

		public static FlexWrap create(String value) {
			for (FlexWrap flexWrap : values()) {
				if (Objects.equals(flexWrap.getValue(), value) ||
					Objects.equals(flexWrap.name(), value)) {

					return flexWrap;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private FlexWrap(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum WidthType {

		FIXED("Fixed"), FLUID("Fluid");

		public static WidthType create(String value) {
			for (WidthType widthType : values()) {
				if (Objects.equals(widthType.getValue(), value) ||
					Objects.equals(widthType.name(), value)) {

					return widthType;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private WidthType(String value) {
			_value = value;
		}

		private final String _value;

	}

}