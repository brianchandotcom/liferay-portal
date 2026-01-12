/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FragmentLinkTextValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FragmentLinkTextValue implements Cloneable, Serializable {

	public static FragmentLinkTextValue toDTO(String json) {
		return FragmentLinkTextValueSerDes.toDTO(json);
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
	public FragmentLinkTextValue clone() throws CloneNotSupportedException {
		return (FragmentLinkTextValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentLinkTextValue)) {
			return false;
		}

		FragmentLinkTextValue fragmentLinkTextValue =
			(FragmentLinkTextValue)object;

		return Objects.equals(toString(), fragmentLinkTextValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentLinkTextValueSerDes.toJSON(this);
	}

}