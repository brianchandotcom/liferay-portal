/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.ItemValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ItemValue implements Cloneable, Serializable {

	public static ItemValue toDTO(String json) {
		return ItemValueSerDes.toDTO(json);
	}

	public ItemExternalReference getItem() {
		return item;
	}

	public void setItem(ItemExternalReference item) {
		this.item = item;
	}

	public void setItem(
		UnsafeSupplier<ItemExternalReference, Exception> itemUnsafeSupplier) {

		try {
			item = itemUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ItemExternalReference item;

	public TemplateReference getTemplate() {
		return template;
	}

	public void setTemplate(TemplateReference template) {
		this.template = template;
	}

	public void setTemplate(
		UnsafeSupplier<TemplateReference, Exception> templateUnsafeSupplier) {

		try {
			template = templateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected TemplateReference template;

	@Override
	public ItemValue clone() throws CloneNotSupportedException {
		return (ItemValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ItemValue)) {
			return false;
		}

		ItemValue itemValue = (ItemValue)object;

		return Objects.equals(toString(), itemValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ItemValueSerDes.toJSON(this);
	}

}