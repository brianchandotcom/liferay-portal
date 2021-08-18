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
import com.liferay.headless.delivery.client.serdes.v1_0.CollectionConfigSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CollectionConfig implements Cloneable, Serializable {

	public static CollectionConfig toDTO(String json) {
		return CollectionConfigSerDes.toDTO(json);
	}

	public Object getCollectionReference() {
		return collectionReference;
	}

	public void setCollectionReference(Object collectionReference) {
		this.collectionReference = collectionReference;
	}

	public void setCollectionReference(
		UnsafeSupplier<Object, Exception> collectionReferenceUnsafeSupplier) {

		try {
			collectionReference = collectionReferenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object collectionReference;

	public CollectionType getCollectionType() {
		return collectionType;
	}

	public String getCollectionTypeAsString() {
		if (collectionType == null) {
			return null;
		}

		return collectionType.toString();
	}

	public void setCollectionType(CollectionType collectionType) {
		this.collectionType = collectionType;
	}

	public void setCollectionType(
		UnsafeSupplier<CollectionType, Exception>
			collectionTypeUnsafeSupplier) {

		try {
			collectionType = collectionTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CollectionType collectionType;

	@Override
	public CollectionConfig clone() throws CloneNotSupportedException {
		return (CollectionConfig)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionConfig)) {
			return false;
		}

		CollectionConfig collectionConfig = (CollectionConfig)object;

		return Objects.equals(toString(), collectionConfig.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionConfigSerDes.toJSON(this);
	}

	public static enum CollectionType {

		COLLECTION("Collection"), COLLECTION_PROVIDER("CollectionProvider");

		public static CollectionType create(String value) {
			for (CollectionType collectionType : values()) {
				if (Objects.equals(collectionType.getValue(), value) ||
					Objects.equals(collectionType.name(), value)) {

					return collectionType;
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

		private CollectionType(String value) {
			_value = value;
		}

		private final String _value;

	}

}