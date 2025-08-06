/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.SelectionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class Selection implements Cloneable, Serializable {

	public static Selection toDTO(String json) {
		return SelectionSerDes.toDTO(json);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getModelClassName() {
		return modelClassName;
	}

	public void setModelClassName(String modelClassName) {
		this.modelClassName = modelClassName;
	}

	public void setModelClassName(
		UnsafeSupplier<String, Exception> modelClassNameUnsafeSupplier) {

		try {
			modelClassName = modelClassNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String modelClassName;

	@Override
	public Selection clone() throws CloneNotSupportedException {
		return (Selection)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Selection)) {
			return false;
		}

		Selection selection = (Selection)object;

		return Objects.equals(toString(), selection.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SelectionSerDes.toJSON(this);
	}

}