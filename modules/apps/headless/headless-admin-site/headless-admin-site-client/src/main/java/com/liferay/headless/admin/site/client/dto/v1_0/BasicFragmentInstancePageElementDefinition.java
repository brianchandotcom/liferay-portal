/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.BasicFragmentInstancePageElementDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class BasicFragmentInstancePageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static BasicFragmentInstancePageElementDefinition toDTO(
		String json) {

		return BasicFragmentInstancePageElementDefinitionSerDes.toDTO(json);
	}

	public FragmentInstance getFragmentInstance() {
		return fragmentInstance;
	}

	public void setFragmentInstance(FragmentInstance fragmentInstance) {
		this.fragmentInstance = fragmentInstance;
	}

	public void setFragmentInstance(
		UnsafeSupplier<FragmentInstance, Exception>
			fragmentInstanceUnsafeSupplier) {

		try {
			fragmentInstance = fragmentInstanceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentInstance fragmentInstance;

	@Override
	public BasicFragmentInstancePageElementDefinition clone()
		throws CloneNotSupportedException {

		return (BasicFragmentInstancePageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BasicFragmentInstancePageElementDefinition)) {
			return false;
		}

		BasicFragmentInstancePageElementDefinition
			basicFragmentInstancePageElementDefinition =
				(BasicFragmentInstancePageElementDefinition)object;

		return Objects.equals(
			toString(), basicFragmentInstancePageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return BasicFragmentInstancePageElementDefinitionSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:417586475