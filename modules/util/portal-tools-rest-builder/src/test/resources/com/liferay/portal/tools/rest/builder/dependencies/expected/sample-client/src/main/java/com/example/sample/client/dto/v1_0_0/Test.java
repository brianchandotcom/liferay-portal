/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.dto.v1_0_0;

import com.example.sample.client.function.UnsafeSupplier;
import com.example.sample.client.serdes.v1_0_0.TestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class Test implements Cloneable, Serializable {

	public static Test toDTO(String json) {
		return TestSerDes.toDTO(json);
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

	public String getJsonProperty() {
		return jsonProperty;
	}

	public void setJsonProperty(String jsonProperty) {
		this.jsonProperty = jsonProperty;
	}

	public void setJsonProperty(
		UnsafeSupplier<String, Exception> jsonPropertyUnsafeSupplier) {

		try {
			jsonProperty = jsonPropertyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String jsonProperty;

	public String getPropertyWithHyphens() {
		return propertyWithHyphens;
	}

	public void setPropertyWithHyphens(String propertyWithHyphens) {
		this.propertyWithHyphens = propertyWithHyphens;
	}

	public void setPropertyWithHyphens(
		UnsafeSupplier<String, Exception> propertyWithHyphensUnsafeSupplier) {

		try {
			propertyWithHyphens = propertyWithHyphensUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String propertyWithHyphens;

	@Override
	public Test clone() throws CloneNotSupportedException {
		return (Test)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Test)) {
			return false;
		}

		Test test = (Test)object;

		return Objects.equals(toString(), test.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TestSerDes.toJSON(this);
	}

}