/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.ImageFragmentEditableElementValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ImageFragmentEditableElementValue
	extends FragmentEditableElementValue implements Cloneable, Serializable {

	public static ImageFragmentEditableElementValue toDTO(String json) {
		return ImageFragmentEditableElementValueSerDes.toDTO(json);
	}

	public FragmentEditableElementValueFragmentLink
		getFragmentEditableElementValueFragmentLink() {

		return fragmentEditableElementValueFragmentLink;
	}

	public void setFragmentEditableElementValueFragmentLink(
		FragmentEditableElementValueFragmentLink
			fragmentEditableElementValueFragmentLink) {

		this.fragmentEditableElementValueFragmentLink =
			fragmentEditableElementValueFragmentLink;
	}

	public void setFragmentEditableElementValueFragmentLink(
		UnsafeSupplier<FragmentEditableElementValueFragmentLink, Exception>
			fragmentEditableElementValueFragmentLinkUnsafeSupplier) {

		try {
			fragmentEditableElementValueFragmentLink =
				fragmentEditableElementValueFragmentLinkUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentEditableElementValueFragmentLink
		fragmentEditableElementValueFragmentLink;

	public FragmentImage getFragmentImage() {
		return fragmentImage;
	}

	public void setFragmentImage(FragmentImage fragmentImage) {
		this.fragmentImage = fragmentImage;
	}

	public void setFragmentImage(
		UnsafeSupplier<FragmentImage, Exception> fragmentImageUnsafeSupplier) {

		try {
			fragmentImage = fragmentImageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentImage fragmentImage;

	@Override
	public ImageFragmentEditableElementValue clone()
		throws CloneNotSupportedException {

		return (ImageFragmentEditableElementValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ImageFragmentEditableElementValue)) {
			return false;
		}

		ImageFragmentEditableElementValue imageFragmentEditableElementValue =
			(ImageFragmentEditableElementValue)object;

		return Objects.equals(
			toString(), imageFragmentEditableElementValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ImageFragmentEditableElementValueSerDes.toJSON(this);
	}

}