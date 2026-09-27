/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.dto.v1_0;

import com.liferay.headless.admin.fragment.client.function.UnsafeSupplier;
import com.liferay.headless.admin.fragment.client.serdes.v1_0.ItemSelectorTypeOptionsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ItemSelectorTypeOptions implements Cloneable, Serializable {

	public static ItemSelectorTypeOptions toDTO(String json) {
		return ItemSelectorTypeOptionsSerDes.toDTO(json);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setClassName(
		UnsafeSupplier<String, Exception> classNameUnsafeSupplier) {

		try {
			className = classNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String className;

	public Map<String, Dependency> getDependency() {
		return dependency;
	}

	public void setDependency(Map<String, Dependency> dependency) {
		this.dependency = dependency;
	}

	public void setDependency(
		UnsafeSupplier<Map<String, Dependency>, Exception>
			dependencyUnsafeSupplier) {

		try {
			dependency = dependencyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Dependency> dependency;

	public Boolean getEnableSelectTemplate() {
		return enableSelectTemplate;
	}

	public void setEnableSelectTemplate(Boolean enableSelectTemplate) {
		this.enableSelectTemplate = enableSelectTemplate;
	}

	public void setEnableSelectTemplate(
		UnsafeSupplier<Boolean, Exception> enableSelectTemplateUnsafeSupplier) {

		try {
			enableSelectTemplate = enableSelectTemplateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean enableSelectTemplate;

	public String getItemSubtype() {
		return itemSubtype;
	}

	public void setItemSubtype(String itemSubtype) {
		this.itemSubtype = itemSubtype;
	}

	public void setItemSubtype(
		UnsafeSupplier<String, Exception> itemSubtypeUnsafeSupplier) {

		try {
			itemSubtype = itemSubtypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String itemSubtype;

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setItemType(
		UnsafeSupplier<String, Exception> itemTypeUnsafeSupplier) {

		try {
			itemType = itemTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String itemType;

	public String[] getMimeTypes() {
		return mimeTypes;
	}

	public void setMimeTypes(String[] mimeTypes) {
		this.mimeTypes = mimeTypes;
	}

	public void setMimeTypes(
		UnsafeSupplier<String[], Exception> mimeTypesUnsafeSupplier) {

		try {
			mimeTypes = mimeTypesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] mimeTypes;

	@Override
	public ItemSelectorTypeOptions clone() throws CloneNotSupportedException {
		return (ItemSelectorTypeOptions)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ItemSelectorTypeOptions)) {
			return false;
		}

		ItemSelectorTypeOptions itemSelectorTypeOptions =
			(ItemSelectorTypeOptions)object;

		return Objects.equals(toString(), itemSelectorTypeOptions.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ItemSelectorTypeOptionsSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-466106407