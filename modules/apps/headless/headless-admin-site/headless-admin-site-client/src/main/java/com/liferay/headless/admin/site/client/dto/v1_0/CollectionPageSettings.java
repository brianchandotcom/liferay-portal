/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.CollectionPageSettingsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class CollectionPageSettings
	extends PageSettings implements Cloneable, Serializable {

	public static CollectionPageSettings toDTO(String json) {
		return CollectionPageSettingsSerDes.toDTO(json);
	}

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
	public CollectionPageSettings clone() throws CloneNotSupportedException {
		return (CollectionPageSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionPageSettings)) {
			return false;
		}

		CollectionPageSettings collectionPageSettings =
			(CollectionPageSettings)object;

		return Objects.equals(toString(), collectionPageSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionPageSettingsSerDes.toJSON(this);
	}

}