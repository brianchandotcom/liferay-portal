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
import com.liferay.headless.delivery.client.serdes.v1_0.LayoutSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Layout {

	public static enum ContainerType {

		FIXED("Fixed"), FLUID("Fluid");

		public static ContainerType create(String value) {
			for (ContainerType containerType : values()) {
				if (Objects.equals(containerType.getValue(), value)) {
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

	public Integer getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(Integer paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	public void setPaddingBottom(
		UnsafeSupplier<Integer, Exception> paddingBottomUnsafeSupplier) {

		try {
			paddingBottom = paddingBottomUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer paddingBottom;

	public Integer getPaddingHorizontal() {
		return paddingHorizontal;
	}

	public void setPaddingHorizontal(Integer paddingHorizontal) {
		this.paddingHorizontal = paddingHorizontal;
	}

	public void setPaddingHorizontal(
		UnsafeSupplier<Integer, Exception> paddingHorizontalUnsafeSupplier) {

		try {
			paddingHorizontal = paddingHorizontalUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer paddingHorizontal;

	public Integer getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(Integer paddingTop) {
		this.paddingTop = paddingTop;
	}

	public void setPaddingTop(
		UnsafeSupplier<Integer, Exception> paddingTopUnsafeSupplier) {

		try {
			paddingTop = paddingTopUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer paddingTop;

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

}