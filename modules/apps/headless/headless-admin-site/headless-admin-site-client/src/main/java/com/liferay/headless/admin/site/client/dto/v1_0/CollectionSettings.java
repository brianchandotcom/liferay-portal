/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.CollectionSettingsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class CollectionSettings implements Cloneable, Serializable {

	public static CollectionSettings toDTO(String json) {
		return CollectionSettingsSerDes.toDTO(json);
	}

	public Map<String, Object> getCollectionConfig() {
		return collectionConfig;
	}

	public void setCollectionConfig(Map<String, Object> collectionConfig) {
		this.collectionConfig = collectionConfig;
	}

	public void setCollectionConfig(
		UnsafeSupplier<Map<String, Object>, Exception>
			collectionConfigUnsafeSupplier) {

		try {
			collectionConfig = collectionConfigUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> collectionConfig;

	public CollectionReference getCollectionReference() {
		return collectionReference;
	}

	public void setCollectionReference(
		CollectionReference collectionReference) {

		this.collectionReference = collectionReference;
	}

	public void setCollectionReference(
		UnsafeSupplier<CollectionReference, Exception>
			collectionReferenceUnsafeSupplier) {

		try {
			collectionReference = collectionReferenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CollectionReference collectionReference;

	@Override
	public CollectionSettings clone() throws CloneNotSupportedException {
		return (CollectionSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionSettings)) {
			return false;
		}

		CollectionSettings collectionSettings = (CollectionSettings)object;

		return Objects.equals(toString(), collectionSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionSettingsSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1221138654