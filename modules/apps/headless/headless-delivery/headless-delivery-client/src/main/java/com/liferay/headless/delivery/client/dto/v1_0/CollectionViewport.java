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
import com.liferay.headless.delivery.client.serdes.v1_0.CollectionViewportSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CollectionViewport implements Cloneable, Serializable {

	public static CollectionViewport toDTO(String json) {
		return CollectionViewportSerDes.toDTO(json);
	}

	public CollectionViewportDefinition getCollectionViewportDefinition() {
		return collectionViewportDefinition;
	}

	public void setCollectionViewportDefinition(
		CollectionViewportDefinition collectionViewportDefinition) {

		this.collectionViewportDefinition = collectionViewportDefinition;
	}

	public void setCollectionViewportDefinition(
		UnsafeSupplier<CollectionViewportDefinition, Exception>
			collectionViewportDefinitionUnsafeSupplier) {

		try {
			collectionViewportDefinition =
				collectionViewportDefinitionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CollectionViewportDefinition collectionViewportDefinition;

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

	@Override
	public CollectionViewport clone() throws CloneNotSupportedException {
		return (CollectionViewport)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionViewport)) {
			return false;
		}

		CollectionViewport collectionViewport = (CollectionViewport)object;

		return Objects.equals(toString(), collectionViewport.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionViewportSerDes.toJSON(this);
	}

}