/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FragmentCompositionInstancePageElementDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FragmentCompositionInstancePageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static FragmentCompositionInstancePageElementDefinition toDTO(
		String json) {

		return FragmentCompositionInstancePageElementDefinitionSerDes.toDTO(
			json);
	}

	public ItemExternalReference getFragmentComposition() {
		return fragmentComposition;
	}

	public void setFragmentComposition(
		ItemExternalReference fragmentComposition) {

		this.fragmentComposition = fragmentComposition;
	}

	public void setFragmentComposition(
		UnsafeSupplier<ItemExternalReference, Exception>
			fragmentCompositionUnsafeSupplier) {

		try {
			fragmentComposition = fragmentCompositionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ItemExternalReference fragmentComposition;

	@Override
	public FragmentCompositionInstancePageElementDefinition clone()
		throws CloneNotSupportedException {

		return (FragmentCompositionInstancePageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				FragmentCompositionInstancePageElementDefinition)) {

			return false;
		}

		FragmentCompositionInstancePageElementDefinition
			fragmentCompositionInstancePageElementDefinition =
				(FragmentCompositionInstancePageElementDefinition)object;

		return Objects.equals(
			toString(),
			fragmentCompositionInstancePageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentCompositionInstancePageElementDefinitionSerDes.toJSON(
			this);
	}

}