/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.ListStyleSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ListStyle
	extends CollectionDisplayListStyle implements Cloneable, Serializable {

	public static ListStyle toDTO(String json) {
		return ListStyleSerDes.toDTO(json);
	}

	public ListStyleDefinition getListStyleDefinition() {
		return listStyleDefinition;
	}

	public void setListStyleDefinition(
		ListStyleDefinition listStyleDefinition) {

		this.listStyleDefinition = listStyleDefinition;
	}

	public void setListStyleDefinition(
		UnsafeSupplier<ListStyleDefinition, Exception>
			listStyleDefinitionUnsafeSupplier) {

		try {
			listStyleDefinition = listStyleDefinitionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ListStyleDefinition listStyleDefinition;

	public ListStyleType getListStyleType() {
		return listStyleType;
	}

	public String getListStyleTypeAsString() {
		if (listStyleType == null) {
			return null;
		}

		return listStyleType.toString();
	}

	public void setListStyleType(ListStyleType listStyleType) {
		this.listStyleType = listStyleType;
	}

	public void setListStyleType(
		UnsafeSupplier<ListStyleType, Exception> listStyleTypeUnsafeSupplier) {

		try {
			listStyleType = listStyleTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ListStyleType listStyleType;

	@Override
	public ListStyle clone() throws CloneNotSupportedException {
		return (ListStyle)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ListStyle)) {
			return false;
		}

		ListStyle listStyle = (ListStyle)object;

		return Objects.equals(toString(), listStyle.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ListStyleSerDes.toJSON(this);
	}

	public static enum ListStyleType {

		GRID("Grid"), FLEX_COLUMN("FlexColumn"), FLEX_ROW("FlexRow");

		public static ListStyleType create(String value) {
			for (ListStyleType listStyleType : values()) {
				if (Objects.equals(listStyleType.getValue(), value) ||
					Objects.equals(listStyleType.name(), value)) {

					return listStyleType;
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

		private ListStyleType(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:2001143322