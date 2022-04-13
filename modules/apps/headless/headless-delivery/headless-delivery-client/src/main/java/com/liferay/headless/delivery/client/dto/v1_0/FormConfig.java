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
import com.liferay.headless.delivery.client.serdes.v1_0.FormConfigSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormConfig implements Cloneable, Serializable {

	public static FormConfig toDTO(String json) {
		return FormConfigSerDes.toDTO(json);
	}

	public Object getFormReference() {
		return formReference;
	}

	public void setFormReference(Object formReference) {
		this.formReference = formReference;
	}

	public void setFormReference(
		UnsafeSupplier<Object, Exception> formReferenceUnsafeSupplier) {

		try {
			formReference = formReferenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object formReference;

	@Override
	public FormConfig clone() throws CloneNotSupportedException {
		return (FormConfig)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormConfig)) {
			return false;
		}

		FormConfig formConfig = (FormConfig)object;

		return Objects.equals(toString(), formConfig.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormConfigSerDes.toJSON(this);
	}

}