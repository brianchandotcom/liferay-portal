/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.DirectBackgroundImageValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class DirectBackgroundImageValue
	extends BackgroundImageValue implements Cloneable, Serializable {

	public static DirectBackgroundImageValue toDTO(String json) {
		return DirectBackgroundImageValueSerDes.toDTO(json);
	}

	public ImageValue getImageValue() {
		return imageValue;
	}

	public void setImageValue(ImageValue imageValue) {
		this.imageValue = imageValue;
	}

	public void setImageValue(
		UnsafeSupplier<ImageValue, Exception> imageValueUnsafeSupplier) {

		try {
			imageValue = imageValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ImageValue imageValue;

	@Override
	public DirectBackgroundImageValue clone()
		throws CloneNotSupportedException {

		return (DirectBackgroundImageValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DirectBackgroundImageValue)) {
			return false;
		}

		DirectBackgroundImageValue directBackgroundImageValue =
			(DirectBackgroundImageValue)object;

		return Objects.equals(
			toString(), directBackgroundImageValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DirectBackgroundImageValueSerDes.toJSON(this);
	}

}