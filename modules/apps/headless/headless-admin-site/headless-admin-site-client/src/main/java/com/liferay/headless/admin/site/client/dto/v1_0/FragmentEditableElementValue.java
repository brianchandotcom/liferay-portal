/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FragmentEditableElementValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public abstract class FragmentEditableElementValue
	implements Cloneable, Serializable {

	public static FragmentEditableElementValue toDTO(String json) {
		return FragmentEditableElementValueSerDes.toDTO(json);
	}

	public Type getType() {
		return type;
	}

	public String getTypeAsString() {
		if (type == null) {
			return null;
		}

		return type.toString();
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<Type, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Type type;

	@Override
	public FragmentEditableElementValue clone()
		throws CloneNotSupportedException {

		return (FragmentEditableElementValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentEditableElementValue)) {
			return false;
		}

		FragmentEditableElementValue fragmentEditableElementValue =
			(FragmentEditableElementValue)object;

		return Objects.equals(
			toString(), fragmentEditableElementValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentEditableElementValueSerDes.toJSON(this);
	}

	public static enum Type {

		ACTION("Action"), BACKGROUND_IMAGE("BackgroundImage"), HTML("HTML"),
		IMAGE("Image"), LINK("Link"), RICH_TEXT("RichText"), TEXT("Text");

		public static Type create(String value) {
			for (Type type : values()) {
				if (Objects.equals(type.getValue(), value) ||
					Objects.equals(type.name(), value)) {

					return type;
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

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

}