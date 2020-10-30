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
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentImageClassPKReferenceSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentImageClassPKReference implements Cloneable, Serializable {

	public static FragmentImageClassPKReference toDTO(String json) {
		return FragmentImageClassPKReferenceSerDes.toDTO(json);
	}

	public FragmentImageConfiguration getImageConfiguration() {
		return imageConfiguration;
	}

	public void setImageConfiguration(
		FragmentImageConfiguration imageConfiguration) {

		this.imageConfiguration = imageConfiguration;
	}

	public void setImageConfiguration(
		UnsafeSupplier<FragmentImageConfiguration, Exception>
			imageConfigurationUnsafeSupplier) {

		try {
			imageConfiguration = imageConfigurationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentImageConfiguration imageConfiguration;

	public Map<String, ClassPKReference> getImageReference() {
		return imageReference;
	}

	public void setImageReference(
		Map<String, ClassPKReference> imageReference) {

		this.imageReference = imageReference;
	}

	public void setImageReference(
		UnsafeSupplier<Map<String, ClassPKReference>, Exception>
			imageReferenceUnsafeSupplier) {

		try {
			imageReference = imageReferenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, ClassPKReference> imageReference;

	@Override
	public FragmentImageClassPKReference clone()
		throws CloneNotSupportedException {

		return (FragmentImageClassPKReference)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentImageClassPKReference)) {
			return false;
		}

		FragmentImageClassPKReference fragmentImageClassPKReference =
			(FragmentImageClassPKReference)object;

		return Objects.equals(
			toString(), fragmentImageClassPKReference.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentImageClassPKReferenceSerDes.toJSON(this);
	}

}