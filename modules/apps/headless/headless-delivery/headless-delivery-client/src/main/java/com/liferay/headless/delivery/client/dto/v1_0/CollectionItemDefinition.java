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
import com.liferay.headless.delivery.client.serdes.v1_0.CollectionItemDefinitionSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CollectionItemDefinition implements Cloneable {

	public Object getCollectionItemConfig() {
		return collectionItemConfig;
	}

	public void setCollectionItemConfig(Object collectionItemConfig) {
		this.collectionItemConfig = collectionItemConfig;
	}

	public void setCollectionItemConfig(
		UnsafeSupplier<Object, Exception> collectionItemConfigUnsafeSupplier) {

		try {
			collectionItemConfig = collectionItemConfigUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object collectionItemConfig;

	@Override
	public CollectionItemDefinition clone() throws CloneNotSupportedException {
		return (CollectionItemDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionItemDefinition)) {
			return false;
		}

		CollectionItemDefinition collectionItemDefinition =
			(CollectionItemDefinition)object;

		return Objects.equals(toString(), collectionItemDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionItemDefinitionSerDes.toJSON(this);
	}

}