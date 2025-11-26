/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentFieldDateSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentFieldDate implements Cloneable, Serializable {

	public static FragmentFieldDate toDTO(String json) {
		return FragmentFieldDateSerDes.toDTO(json);
	}

	public Object getDate() {
		return date;
	}

	public void setDate(Object date) {
		this.date = date;
	}

	public void setDate(UnsafeSupplier<Object, Exception> dateUnsafeSupplier) {
		try {
			date = dateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object date;

	public Object getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(Object dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setDateFormat(
		UnsafeSupplier<Object, Exception> dateFormatUnsafeSupplier) {

		try {
			dateFormat = dateFormatUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object dateFormat;

	@Override
	public FragmentFieldDate clone() throws CloneNotSupportedException {
		return (FragmentFieldDate)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentFieldDate)) {
			return false;
		}

		FragmentFieldDate fragmentFieldDate = (FragmentFieldDate)object;

		return Objects.equals(toString(), fragmentFieldDate.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentFieldDateSerDes.toJSON(this);
	}

}