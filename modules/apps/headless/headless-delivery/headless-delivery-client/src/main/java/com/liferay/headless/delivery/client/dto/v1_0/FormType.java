/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.FormTypeSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormType implements Cloneable, Serializable {

	public static FormType toDTO(String json) {
		return FormTypeSerDes.toDTO(json);
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

	@Override
	public FormType clone() throws CloneNotSupportedException {
		return (FormType)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormType)) {
			return false;
		}

		FormType formType = (FormType)object;

		return Objects.equals(toString(), formType.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormTypeSerDes.toJSON(this);
	}

}