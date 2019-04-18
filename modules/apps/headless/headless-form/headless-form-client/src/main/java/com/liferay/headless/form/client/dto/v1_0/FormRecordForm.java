/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.form.client.dto.v1_0;

import com.liferay.headless.form.client.function.UnsafeSupplier;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormRecordForm {

	public Boolean getDraft() {
		return draft;
	}

	public void setDraft(Boolean draft) {
		this.draft = draft;
	}

	public void setDraft(
		UnsafeSupplier<Boolean, Exception> draftUnsafeSupplier) {

		try {
			draft = draftUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean draft;

	public String getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(String fieldValues) {
		this.fieldValues = fieldValues;
	}

	public void setFieldValues(
		UnsafeSupplier<String, Exception> fieldValuesUnsafeSupplier) {

		try {
			fieldValues = fieldValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String fieldValues;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormRecordForm)) {
			return false;
		}

		FormRecordForm formRecordForm = (FormRecordForm)object;

		return Objects.equals(toString(), formRecordForm.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"draft\": ");

		if (draft == null) {
			sb.append("null");
		}
		else {
			sb.append(draft);
		}

		sb.append(", ");

		sb.append("\"fieldValues\": ");

		if (fieldValues == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(fieldValues);
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

}