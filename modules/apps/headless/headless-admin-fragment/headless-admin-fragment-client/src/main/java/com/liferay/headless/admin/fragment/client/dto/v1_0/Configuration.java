/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.dto.v1_0;

import com.liferay.headless.admin.fragment.client.function.UnsafeSupplier;
import com.liferay.headless.admin.fragment.client.serdes.v1_0.ConfigurationSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class Configuration implements Cloneable, Serializable {

	public static Configuration toDTO(String json) {
		return ConfigurationSerDes.toDTO(json);
	}

	public FieldSet[] getFieldSets() {
		return fieldSets;
	}

	public void setFieldSets(FieldSet[] fieldSets) {
		this.fieldSets = fieldSets;
	}

	public void setFieldSets(
		UnsafeSupplier<FieldSet[], Exception> fieldSetsUnsafeSupplier) {

		try {
			fieldSets = fieldSetsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FieldSet[] fieldSets;

	@Override
	public Configuration clone() throws CloneNotSupportedException {
		return (Configuration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Configuration)) {
			return false;
		}

		Configuration configuration = (Configuration)object;

		return Objects.equals(toString(), configuration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ConfigurationSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-409538990