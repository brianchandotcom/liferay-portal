/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.CollectionDisplayListStyleSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public abstract class CollectionDisplayListStyle
	implements Cloneable, Serializable {

	public static CollectionDisplayListStyle toDTO(String json) {
		return CollectionDisplayListStyleSerDes.toDTO(json);
	}

	public CollectionDisplayListStyleType getCollectionDisplayListStyleType() {
		return collectionDisplayListStyleType;
	}

	public String getCollectionDisplayListStyleTypeAsString() {
		if (collectionDisplayListStyleType == null) {
			return null;
		}

		return collectionDisplayListStyleType.toString();
	}

	public void setCollectionDisplayListStyleType(
		CollectionDisplayListStyleType collectionDisplayListStyleType) {

		this.collectionDisplayListStyleType = collectionDisplayListStyleType;
	}

	public void setCollectionDisplayListStyleType(
		UnsafeSupplier<CollectionDisplayListStyleType, Exception>
			collectionDisplayListStyleTypeUnsafeSupplier) {

		try {
			collectionDisplayListStyleType =
				collectionDisplayListStyleTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CollectionDisplayListStyleType collectionDisplayListStyleType;

	@Override
	public CollectionDisplayListStyle clone()
		throws CloneNotSupportedException {

		return (CollectionDisplayListStyle)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionDisplayListStyle)) {
			return false;
		}

		CollectionDisplayListStyle collectionDisplayListStyle =
			(CollectionDisplayListStyle)object;

		return Objects.equals(
			toString(), collectionDisplayListStyle.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionDisplayListStyleSerDes.toJSON(this);
	}

	public static enum CollectionDisplayListStyleType {

		LIST_STYLE("ListStyle"), TEMPLATE("Template");

		public static CollectionDisplayListStyleType create(String value) {
			for (CollectionDisplayListStyleType collectionDisplayListStyleType :
					values()) {

				if (Objects.equals(
						collectionDisplayListStyleType.getValue(), value) ||
					Objects.equals(
						collectionDisplayListStyleType.name(), value)) {

					return collectionDisplayListStyleType;
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

		private CollectionDisplayListStyleType(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:-1805949896