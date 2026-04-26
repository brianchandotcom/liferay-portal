/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.DateFragmentEditableElementValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class DateFragmentEditableElementValue
	extends FragmentEditableElementValue implements Cloneable, Serializable {

	public static DateFragmentEditableElementValue toDTO(String json) {
		return DateFragmentEditableElementValueSerDes.toDTO(json);
	}

	public FragmentMappedValue getDate() {
		return date;
	}

	public void setDate(FragmentMappedValue date) {
		this.date = date;
	}

	public void setDate(
		UnsafeSupplier<FragmentMappedValue, Exception> dateUnsafeSupplier) {

		try {
			date = dateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentMappedValue date;

	public FragmentInlineValue getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(FragmentInlineValue dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setDateFormat(
		UnsafeSupplier<FragmentInlineValue, Exception>
			dateFormatUnsafeSupplier) {

		try {
			dateFormat = dateFormatUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentInlineValue dateFormat;

	@Override
	public DateFragmentEditableElementValue clone()
		throws CloneNotSupportedException {

		return (DateFragmentEditableElementValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DateFragmentEditableElementValue)) {
			return false;
		}

		DateFragmentEditableElementValue dateFragmentEditableElementValue =
			(DateFragmentEditableElementValue)object;

		return Objects.equals(
			toString(), dateFragmentEditableElementValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DateFragmentEditableElementValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-622273254