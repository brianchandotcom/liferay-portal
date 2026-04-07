/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.TextFragmentEditableElementValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class TextFragmentEditableElementValue
	extends FragmentEditableElementValue implements Cloneable, Serializable {

	public static TextFragmentEditableElementValue toDTO(String json) {
		return TextFragmentEditableElementValueSerDes.toDTO(json);
	}

	public FragmentLinkTextValue getFragmentLinkTextValue() {
		return fragmentLinkTextValue;
	}

	public void setFragmentLinkTextValue(
		FragmentLinkTextValue fragmentLinkTextValue) {

		this.fragmentLinkTextValue = fragmentLinkTextValue;
	}

	public void setFragmentLinkTextValue(
		UnsafeSupplier<FragmentLinkTextValue, Exception>
			fragmentLinkTextValueUnsafeSupplier) {

		try {
			fragmentLinkTextValue = fragmentLinkTextValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentLinkTextValue fragmentLinkTextValue;

	@Override
	public TextFragmentEditableElementValue clone()
		throws CloneNotSupportedException {

		return (TextFragmentEditableElementValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TextFragmentEditableElementValue)) {
			return false;
		}

		TextFragmentEditableElementValue textFragmentEditableElementValue =
			(TextFragmentEditableElementValue)object;

		return Objects.equals(
			toString(), textFragmentEditableElementValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TextFragmentEditableElementValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-217345694