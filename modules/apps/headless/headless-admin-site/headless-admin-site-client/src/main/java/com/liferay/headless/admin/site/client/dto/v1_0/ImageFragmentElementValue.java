/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.ImageFragmentElementValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ImageFragmentElementValue
	extends FragmentElementValue implements Cloneable, Serializable {

	public static ImageFragmentElementValue toDTO(String json) {
		return ImageFragmentElementValueSerDes.toDTO(json);
	}

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

	public FragmentLink getFragmentLink() {
		return fragmentLink;
	}

	public void setFragmentLink(FragmentLink fragmentLink) {
		this.fragmentLink = fragmentLink;
	}

	public void setFragmentLink(
		UnsafeSupplier<FragmentLink, Exception> fragmentLinkUnsafeSupplier) {

		try {
			fragmentLink = fragmentLinkUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentLink fragmentLink;

	@Override
	public ImageFragmentElementValue clone() throws CloneNotSupportedException {
		return (ImageFragmentElementValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ImageFragmentElementValue)) {
			return false;
		}

		ImageFragmentElementValue imageFragmentElementValue =
			(ImageFragmentElementValue)object;

		return Objects.equals(toString(), imageFragmentElementValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ImageFragmentElementValueSerDes.toJSON(this);
	}

}