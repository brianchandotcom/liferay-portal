/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.BackgroundImageFragmentEditableElementValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class BackgroundImageFragmentEditableElementValue
	extends FragmentEditableElementValue implements Cloneable, Serializable {

	public static BackgroundImageFragmentEditableElementValue toDTO(
		String json) {

		return BackgroundImageFragmentEditableElementValueSerDes.toDTO(json);
	}

	public FragmentImageValue getBackgroundFragmentImageValue() {
		return backgroundFragmentImageValue;
	}

	public void setBackgroundFragmentImageValue(
		FragmentImageValue backgroundFragmentImageValue) {

		this.backgroundFragmentImageValue = backgroundFragmentImageValue;
	}

	public void setBackgroundFragmentImageValue(
		UnsafeSupplier<FragmentImageValue, Exception>
			backgroundFragmentImageValueUnsafeSupplier) {

		try {
			backgroundFragmentImageValue =
				backgroundFragmentImageValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentImageValue backgroundFragmentImageValue;

	@Override
	public BackgroundImageFragmentEditableElementValue clone()
		throws CloneNotSupportedException {

		return (BackgroundImageFragmentEditableElementValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BackgroundImageFragmentEditableElementValue)) {
			return false;
		}

		BackgroundImageFragmentEditableElementValue
			backgroundImageFragmentEditableElementValue =
				(BackgroundImageFragmentEditableElementValue)object;

		return Objects.equals(
			toString(), backgroundImageFragmentEditableElementValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return BackgroundImageFragmentEditableElementValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:559233236