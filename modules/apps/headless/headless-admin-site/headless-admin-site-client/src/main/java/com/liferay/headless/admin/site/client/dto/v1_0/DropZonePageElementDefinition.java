/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.DropZonePageElementDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class DropZonePageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static DropZonePageElementDefinition toDTO(String json) {
		return DropZonePageElementDefinitionSerDes.toDTO(json);
	}

	public Boolean getAddNewFragmentEntries() {
		return addNewFragmentEntries;
	}

	public void setAddNewFragmentEntries(Boolean addNewFragmentEntries) {
		this.addNewFragmentEntries = addNewFragmentEntries;
	}

	public void setAddNewFragmentEntries(
		UnsafeSupplier<Boolean, Exception>
			addNewFragmentEntriesUnsafeSupplier) {

		try {
			addNewFragmentEntries = addNewFragmentEntriesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean addNewFragmentEntries;

	public FragmentReference[] getAllowedFragmentReferences() {
		return allowedFragmentReferences;
	}

	public void setAllowedFragmentReferences(
		FragmentReference[] allowedFragmentReferences) {

		this.allowedFragmentReferences = allowedFragmentReferences;
	}

	public void setAllowedFragmentReferences(
		UnsafeSupplier<FragmentReference[], Exception>
			allowedFragmentReferencesUnsafeSupplier) {

		try {
			allowedFragmentReferences =
				allowedFragmentReferencesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentReference[] allowedFragmentReferences;

	@Override
	public DropZonePageElementDefinition clone()
		throws CloneNotSupportedException {

		return (DropZonePageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DropZonePageElementDefinition)) {
			return false;
		}

		DropZonePageElementDefinition dropZonePageElementDefinition =
			(DropZonePageElementDefinition)object;

		return Objects.equals(
			toString(), dropZonePageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DropZonePageElementDefinitionSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1831982471