/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.CollectionItemPageElementDefinitionSerDes;

import java.io.Serializable;

import java.util.Objects;

import jakarta.annotation.Generated;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class CollectionItemPageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static CollectionItemPageElementDefinition toDTO(String json) {
		return CollectionItemPageElementDefinitionSerDes.toDTO(json);
	}

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
	public CollectionItemPageElementDefinition clone()
		throws CloneNotSupportedException {

		return (CollectionItemPageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionItemPageElementDefinition)) {
			return false;
		}

		CollectionItemPageElementDefinition
			collectionItemPageElementDefinition =
				(CollectionItemPageElementDefinition)object;

		return Objects.equals(
			toString(), collectionItemPageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionItemPageElementDefinitionSerDes.toJSON(this);
	}

}