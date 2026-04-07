/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FormContainerConfigSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FormContainerConfig implements Cloneable, Serializable {

	public static FormContainerConfig toDTO(String json) {
		return FormContainerConfigSerDes.toDTO(json);
	}

	public FormContainerReference getFormContainerReference() {
		return formContainerReference;
	}

	public void setFormContainerReference(
		FormContainerReference formContainerReference) {

		this.formContainerReference = formContainerReference;
	}

	public void setFormContainerReference(
		UnsafeSupplier<FormContainerReference, Exception>
			formContainerReferenceUnsafeSupplier) {

		try {
			formContainerReference = formContainerReferenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FormContainerReference formContainerReference;

	public FormContainerType getFormContainerType() {
		return formContainerType;
	}

	public String getFormContainerTypeAsString() {
		if (formContainerType == null) {
			return null;
		}

		return formContainerType.toString();
	}

	public void setFormContainerType(FormContainerType formContainerType) {
		this.formContainerType = formContainerType;
	}

	public void setFormContainerType(
		UnsafeSupplier<FormContainerType, Exception>
			formContainerTypeUnsafeSupplier) {

		try {
			formContainerType = formContainerTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FormContainerType formContainerType;

	public LocalizationConfig getLocalizationConfig() {
		return localizationConfig;
	}

	public void setLocalizationConfig(LocalizationConfig localizationConfig) {
		this.localizationConfig = localizationConfig;
	}

	public void setLocalizationConfig(
		UnsafeSupplier<LocalizationConfig, Exception>
			localizationConfigUnsafeSupplier) {

		try {
			localizationConfig = localizationConfigUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected LocalizationConfig localizationConfig;

	public Integer getNumberOfSteps() {
		return numberOfSteps;
	}

	public void setNumberOfSteps(Integer numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}

	public void setNumberOfSteps(
		UnsafeSupplier<Integer, Exception> numberOfStepsUnsafeSupplier) {

		try {
			numberOfSteps = numberOfStepsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer numberOfSteps;

	public SuccessFormContainerSubmissionResult
		getSuccessFormContainerSubmissionResult() {

		return successFormContainerSubmissionResult;
	}

	public void setSuccessFormContainerSubmissionResult(
		SuccessFormContainerSubmissionResult
			successFormContainerSubmissionResult) {

		this.successFormContainerSubmissionResult =
			successFormContainerSubmissionResult;
	}

	public void setSuccessFormContainerSubmissionResult(
		UnsafeSupplier<SuccessFormContainerSubmissionResult, Exception>
			successFormContainerSubmissionResultUnsafeSupplier) {

		try {
			successFormContainerSubmissionResult =
				successFormContainerSubmissionResultUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SuccessFormContainerSubmissionResult
		successFormContainerSubmissionResult;

	@Override
	public FormContainerConfig clone() throws CloneNotSupportedException {
		return (FormContainerConfig)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormContainerConfig)) {
			return false;
		}

		FormContainerConfig formContainerConfig = (FormContainerConfig)object;

		return Objects.equals(toString(), formContainerConfig.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormContainerConfigSerDes.toJSON(this);
	}

	public static enum FormContainerType {

		MULTISTEP("Multistep"), SIMPLE("Simple");

		public static FormContainerType create(String value) {
			for (FormContainerType formContainerType : values()) {
				if (Objects.equals(formContainerType.getValue(), value) ||
					Objects.equals(formContainerType.name(), value)) {

					return formContainerType;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private FormContainerType(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:-143131514