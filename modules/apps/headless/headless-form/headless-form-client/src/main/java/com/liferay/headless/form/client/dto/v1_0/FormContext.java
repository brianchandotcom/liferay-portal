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
public class FormContext implements Cloneable {

	public FormFieldValue[] getFormFieldValues() {
		return formFieldValues;
	}

	public void setFormFieldValues(FormFieldValue[] formFieldValues) {
		this.formFieldValues = formFieldValues;
	}

	public void setFormFieldValues(
		UnsafeSupplier<FormFieldValue[], Exception>
			formFieldValuesUnsafeSupplier) {

		try {
			formFieldValues = formFieldValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FormFieldValue[] formFieldValues;

	public FormPageContext[] getFormPageContexts() {
		return formPageContexts;
	}

	public void setFormPageContexts(FormPageContext[] formPageContexts) {
		this.formPageContexts = formPageContexts;
	}

	public void setFormPageContexts(
		UnsafeSupplier<FormPageContext[], Exception>
			formPageContextsUnsafeSupplier) {

		try {
			formPageContexts = formPageContextsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FormPageContext[] formPageContexts;

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
	public FormContext clone() throws CloneNotSupportedException {
		return (FormContext)super.clone();
	}

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