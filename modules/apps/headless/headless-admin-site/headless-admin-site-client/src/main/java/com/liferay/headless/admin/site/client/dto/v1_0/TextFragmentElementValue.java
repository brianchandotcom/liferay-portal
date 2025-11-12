/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.TextFragmentElementValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class TextFragmentElementValue
	extends FragmentElementValue implements Cloneable, Serializable {

	public static TextFragmentElementValue toDTO(String json) {
		return TextFragmentElementValueSerDes.toDTO(json);
	}

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

	public TextFragmentValue getTextFragmentValue() {
		return textFragmentValue;
	}

	public void setTextFragmentValue(TextFragmentValue textFragmentValue) {
		this.textFragmentValue = textFragmentValue;
	}

	public void setTextFragmentValue(
		UnsafeSupplier<TextFragmentValue, Exception>
			textFragmentValueUnsafeSupplier) {

		try {
			textFragmentValue = textFragmentValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected TextFragmentValue textFragmentValue;

	@Override
	public TextFragmentElementValue clone() throws CloneNotSupportedException {
		return (TextFragmentElementValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TextFragmentElementValue)) {
			return false;
		}

		TextFragmentElementValue textFragmentElementValue =
			(TextFragmentElementValue)object;

		return Objects.equals(toString(), textFragmentElementValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TextFragmentElementValueSerDes.toJSON(this);
	}

}