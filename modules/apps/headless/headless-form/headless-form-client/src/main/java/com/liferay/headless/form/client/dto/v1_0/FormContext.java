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
import com.liferay.headless.form.client.serdes.v1_0.FormContextSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormContext {

	public FieldValue[] getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(FieldValue[] fieldValues) {
		this.fieldValues = fieldValues;
	}

	public void setFieldValues(
		UnsafeSupplier<FieldValue[], Exception> fieldValuesUnsafeSupplier) {

		try {
			fieldValues = fieldValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FieldValue[] fieldValues;

	public PageContext[] getPageContexts() {
		return pageContexts;
	}

	public void setPageContexts(PageContext[] pageContexts) {
		this.pageContexts = pageContexts;
	}

	public void setPageContexts(
		UnsafeSupplier<PageContext[], Exception> pageContextsUnsafeSupplier) {

		try {
			pageContexts = pageContextsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PageContext[] pageContexts;

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setReadOnly(
		UnsafeSupplier<Boolean, Exception> readOnlyUnsafeSupplier) {

		try {
			readOnly = readOnlyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean readOnly;

	public Boolean getShowRequiredFieldsWarning() {
		return showRequiredFieldsWarning;
	}

	public void setShowRequiredFieldsWarning(
		Boolean showRequiredFieldsWarning) {

		this.showRequiredFieldsWarning = showRequiredFieldsWarning;
	}

	public void setShowRequiredFieldsWarning(
		UnsafeSupplier<Boolean, Exception>
			showRequiredFieldsWarningUnsafeSupplier) {

		try {
			showRequiredFieldsWarning =
				showRequiredFieldsWarningUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean showRequiredFieldsWarning;

	public Boolean getShowSubmitButton() {
		return showSubmitButton;
	}

	public void setShowSubmitButton(Boolean showSubmitButton) {
		this.showSubmitButton = showSubmitButton;
	}

	public void setShowSubmitButton(
		UnsafeSupplier<Boolean, Exception> showSubmitButtonUnsafeSupplier) {

		try {
			showSubmitButton = showSubmitButtonUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean showSubmitButton;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormContext)) {
			return false;
		}

		FormContext formContext = (FormContext)object;

		return Objects.equals(toString(), formContext.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormContextSerDes.toJSON(this);
	}

}