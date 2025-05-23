/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FragmentDropZonePageElementDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FragmentDropZonePageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static FragmentDropZonePageElementDefinition toDTO(String json) {
		return FragmentDropZonePageElementDefinitionSerDes.toDTO(json);
	}

	public String getFragmentDropZoneId() {
		return fragmentDropZoneId;
	}

	public void setFragmentDropZoneId(String fragmentDropZoneId) {
		this.fragmentDropZoneId = fragmentDropZoneId;
	}

	public void setFragmentDropZoneId(
		UnsafeSupplier<String, Exception> fragmentDropZoneIdUnsafeSupplier) {

		try {
			fragmentDropZoneId = fragmentDropZoneIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String fragmentDropZoneId;

	@Override
	public FragmentDropZonePageElementDefinition clone()
		throws CloneNotSupportedException {

		return (FragmentDropZonePageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentDropZonePageElementDefinition)) {
			return false;
		}

		FragmentDropZonePageElementDefinition
			fragmentDropZonePageElementDefinition =
				(FragmentDropZonePageElementDefinition)object;

		return Objects.equals(
			toString(), fragmentDropZonePageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentDropZonePageElementDefinitionSerDes.toJSON(this);
	}

}