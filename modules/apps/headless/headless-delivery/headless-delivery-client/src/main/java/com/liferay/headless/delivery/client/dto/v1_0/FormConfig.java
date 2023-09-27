/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.FormConfigSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormConfig implements Cloneable, Serializable {

	public static FormConfig toDTO(String json) {
		return FormConfigSerDes.toDTO(json);
	}

	public Object getFormReference() {
		return formReference;
	}

	public void setFormReference(Object formReference) {
		this.formReference = formReference;
	}

	public void setFormReference(
		UnsafeSupplier<Object, Exception> formReferenceUnsafeSupplier) {

		try {
			formReference = formReferenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object formReference;

	public Object getFormSuccessSubmissionResult() {
		return formSuccessSubmissionResult;
	}

	public void setFormSuccessSubmissionResult(
		Object formSuccessSubmissionResult) {

		this.formSuccessSubmissionResult = formSuccessSubmissionResult;
	}

	public void setFormSuccessSubmissionResult(
		UnsafeSupplier<Object, Exception>
			formSuccessSubmissionResultUnsafeSupplier) {

		try {
			formSuccessSubmissionResult =
				formSuccessSubmissionResultUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object formSuccessSubmissionResult;

	@Override
	public FormConfig clone() throws CloneNotSupportedException {
		return (FormConfig)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormConfig)) {
			return false;
		}

		FormConfig formConfig = (FormConfig)object;

		return Objects.equals(toString(), formConfig.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormConfigSerDes.toJSON(this);
	}

}