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
import com.liferay.headless.form.client.serdes.v1_0.FormPageContextSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormPageContext implements Cloneable {

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setEnabled(
		UnsafeSupplier<Boolean, Exception> enabledUnsafeSupplier) {

		try {
			enabled = enabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean enabled;

	public FormFieldContext[] getFormFieldContexts() {
		return formFieldContexts;
	}

	public void setFormFieldContexts(FormFieldContext[] formFieldContexts) {
		this.formFieldContexts = formFieldContexts;
	}

	public void setFormFieldContexts(
		UnsafeSupplier<FormFieldContext[], Exception>
			formFieldContextsUnsafeSupplier) {

		try {
			formFieldContexts = formFieldContextsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FormFieldContext[] formFieldContexts;

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

	@Override
	public FormPageContext clone() throws CloneNotSupportedException {
		return (FormPageContext)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormPageContext)) {
			return false;
		}

		FormPageContext formPageContext = (FormPageContext)object;

		return Objects.equals(toString(), formPageContext.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormPageContextSerDes.toJSON(this);
	}

}