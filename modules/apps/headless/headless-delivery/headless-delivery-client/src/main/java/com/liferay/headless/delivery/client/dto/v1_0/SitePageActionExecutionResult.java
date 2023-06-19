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
import com.liferay.headless.delivery.client.serdes.v1_0.SitePageActionExecutionResultSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SitePageActionExecutionResult implements Cloneable, Serializable {

	public static SitePageActionExecutionResult toDTO(String json) {
		return SitePageActionExecutionResultSerDes.toDTO(json);
	}

	public ClassFieldsReference getItemReference() {
		return itemReference;
	}

	public void setItemReference(ClassFieldsReference itemReference) {
		this.itemReference = itemReference;
	}

	public void setItemReference(
		UnsafeSupplier<ClassFieldsReference, Exception>
			itemReferenceUnsafeSupplier) {

		try {
			itemReference = itemReferenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ClassFieldsReference itemReference;

	@Override
	public SitePageActionExecutionResult clone()
		throws CloneNotSupportedException {

		return (SitePageActionExecutionResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SitePageActionExecutionResult)) {
			return false;
		}

		SitePageActionExecutionResult sitePageActionExecutionResult =
			(SitePageActionExecutionResult)object;

		return Objects.equals(
			toString(), sitePageActionExecutionResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SitePageActionExecutionResultSerDes.toJSON(this);
	}

}