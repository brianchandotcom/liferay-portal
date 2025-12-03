/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FragmentDirectImageValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FragmentDirectImageValue
	extends FragmentImageValue implements Cloneable, Serializable {

	public static FragmentDirectImageValue toDTO(String json) {
		return FragmentDirectImageValueSerDes.toDTO(json);
	}

	public Map<String, ImageValue> getValue_i18n() {
		return value_i18n;
	}

	public void setValue_i18n(Map<String, ImageValue> value_i18n) {
		this.value_i18n = value_i18n;
	}

	public void setValue_i18n(
		UnsafeSupplier<Map<String, ImageValue>, Exception>
			value_i18nUnsafeSupplier) {

		try {
			value_i18n = value_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, ImageValue> value_i18n;

	@Override
	public FragmentDirectImageValue clone() throws CloneNotSupportedException {
		return (FragmentDirectImageValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentDirectImageValue)) {
			return false;
		}

		FragmentDirectImageValue fragmentDirectImageValue =
			(FragmentDirectImageValue)object;

		return Objects.equals(toString(), fragmentDirectImageValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentDirectImageValueSerDes.toJSON(this);
	}

}