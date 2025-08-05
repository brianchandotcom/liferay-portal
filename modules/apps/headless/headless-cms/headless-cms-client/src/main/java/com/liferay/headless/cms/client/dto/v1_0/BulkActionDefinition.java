/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.BulkActionDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public abstract class BulkActionDefinition implements Cloneable, Serializable {

	public static BulkActionDefinition toDTO(String json) {
		return BulkActionDefinitionSerDes.toDTO(json);
	}

	public CMSEntryDefinition[] getCmsEntryDefinitions() {
		return cmsEntryDefinitions;
	}

	public void setCmsEntryDefinitions(
		CMSEntryDefinition[] cmsEntryDefinitions) {

		this.cmsEntryDefinitions = cmsEntryDefinitions;
	}

	public void setCmsEntryDefinitions(
		UnsafeSupplier<CMSEntryDefinition[], Exception>
			cmsEntryDefinitionsUnsafeSupplier) {

		try {
			cmsEntryDefinitions = cmsEntryDefinitionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CMSEntryDefinition[] cmsEntryDefinitions;

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
	public BulkActionDefinition clone() throws CloneNotSupportedException {
		return (BulkActionDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BulkActionDefinition)) {
			return false;
		}

		BulkActionDefinition bulkActionDefinition =
			(BulkActionDefinition)object;

		return Objects.equals(toString(), bulkActionDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return BulkActionDefinitionSerDes.toJSON(this);
	}

	public static enum Type {

		CATEGORY("Category"), DELETE("Delete"), MOVE("Move"),
		PERMISSION("Permission"), STATUS("Status"), TAG("Tag");

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