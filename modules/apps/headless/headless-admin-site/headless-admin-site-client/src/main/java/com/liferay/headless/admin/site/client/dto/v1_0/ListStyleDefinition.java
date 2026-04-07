/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.ListStyleDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ListStyleDefinition implements Cloneable, Serializable {

	public static ListStyleDefinition toDTO(String json) {
		return ListStyleDefinitionSerDes.toDTO(json);
	}

	public Boolean getGutters() {
		return gutters;
	}

	public void setGutters(Boolean gutters) {
		this.gutters = gutters;
	}

	public void setGutters(
		UnsafeSupplier<Boolean, Exception> guttersUnsafeSupplier) {

		try {
			gutters = guttersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean gutters;

	public VerticalAlignment getVerticalAlignment() {
		return verticalAlignment;
	}

	public String getVerticalAlignmentAsString() {
		if (verticalAlignment == null) {
			return null;
		}

		return verticalAlignment.toString();
	}

	public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public void setVerticalAlignment(
		UnsafeSupplier<VerticalAlignment, Exception>
			verticalAlignmentUnsafeSupplier) {

		try {
			verticalAlignment = verticalAlignmentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected VerticalAlignment verticalAlignment;

	@Override
	public ListStyleDefinition clone() throws CloneNotSupportedException {
		return (ListStyleDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ListStyleDefinition)) {
			return false;
		}

		ListStyleDefinition listStyleDefinition = (ListStyleDefinition)object;

		return Objects.equals(toString(), listStyleDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ListStyleDefinitionSerDes.toJSON(this);
	}

	public static enum VerticalAlignment {

		BOTTOM("Bottom"), MIDDLE("Middle"), TOP("Top");

		public static VerticalAlignment create(String value) {
			for (VerticalAlignment verticalAlignment : values()) {
				if (Objects.equals(verticalAlignment.getValue(), value) ||
					Objects.equals(verticalAlignment.name(), value)) {

					return verticalAlignment;
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

		private VerticalAlignment(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:-1352575224