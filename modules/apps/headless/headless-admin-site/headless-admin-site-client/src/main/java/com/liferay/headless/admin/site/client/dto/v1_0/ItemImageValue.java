/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.ItemImageValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ItemImageValue
	extends ImageValue implements Cloneable, Serializable {

	public static ItemImageValue toDTO(String json) {
		return ItemImageValueSerDes.toDTO(json);
	}

	public ItemExternalReference getItemExternalReference() {
		return itemExternalReference;
	}

	public void setItemExternalReference(
		ItemExternalReference itemExternalReference) {

		this.itemExternalReference = itemExternalReference;
	}

	public void setItemExternalReference(
		UnsafeSupplier<ItemExternalReference, Exception>
			itemExternalReferenceUnsafeSupplier) {

		try {
			itemExternalReference = itemExternalReferenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ItemExternalReference itemExternalReference;

	@Override
	public ItemImageValue clone() throws CloneNotSupportedException {
		return (ItemImageValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ItemImageValue)) {
			return false;
		}

		ItemImageValue itemImageValue = (ItemImageValue)object;

		return Objects.equals(toString(), itemImageValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ItemImageValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1096389560