/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.TextFragmentInlineValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class TextFragmentInlineValue
	extends TextFragmentValue implements Cloneable, Serializable {

	public static TextFragmentInlineValue toDTO(String json) {
		return TextFragmentInlineValueSerDes.toDTO(json);
	}

	public FragmentInlineValue getFragmentInlineValue() {
		return fragmentInlineValue;
	}

	public void setFragmentInlineValue(
		FragmentInlineValue fragmentInlineValue) {

		this.fragmentInlineValue = fragmentInlineValue;
	}

	public void setFragmentInlineValue(
		UnsafeSupplier<FragmentInlineValue, Exception>
			fragmentInlineValueUnsafeSupplier) {

		try {
			fragmentInlineValue = fragmentInlineValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentInlineValue fragmentInlineValue;

	@Override
	public TextFragmentInlineValue clone() throws CloneNotSupportedException {
		return (TextFragmentInlineValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TextFragmentInlineValue)) {
			return false;
		}

		TextFragmentInlineValue textFragmentInlineValue =
			(TextFragmentInlineValue)object;

		return Objects.equals(toString(), textFragmentInlineValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TextFragmentInlineValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1530846637