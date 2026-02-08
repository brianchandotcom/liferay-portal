/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FormStepContainerPageElementDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FormStepContainerPageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static FormStepContainerPageElementDefinition toDTO(String json) {
		return FormStepContainerPageElementDefinitionSerDes.toDTO(json);
	}

	public BackgroundImageValue getBackgroundImageValue() {
		return backgroundImageValue;
	}

	public void setBackgroundImageValue(
		BackgroundImageValue backgroundImageValue) {

		this.backgroundImageValue = backgroundImageValue;
	}

	public void setBackgroundImageValue(
		UnsafeSupplier<BackgroundImageValue, Exception>
			backgroundImageValueUnsafeSupplier) {

		try {
			backgroundImageValue = backgroundImageValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BackgroundImageValue backgroundImageValue;

	public String[] getCssClasses() {
		return cssClasses;
	}

	public void setCssClasses(String[] cssClasses) {
		this.cssClasses = cssClasses;
	}

	public void setCssClasses(
		UnsafeSupplier<String[], Exception> cssClassesUnsafeSupplier) {

		try {
			cssClasses = cssClassesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] cssClasses;

	public FragmentViewport[] getFragmentViewports() {
		return fragmentViewports;
	}

	public void setFragmentViewports(FragmentViewport[] fragmentViewports) {
		this.fragmentViewports = fragmentViewports;
	}

	public void setFragmentViewports(
		UnsafeSupplier<FragmentViewport[], Exception>
			fragmentViewportsUnsafeSupplier) {

		try {
			fragmentViewports = fragmentViewportsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentViewport[] fragmentViewports;

	@Override
	public FormStepContainerPageElementDefinition clone()
		throws CloneNotSupportedException {

		return (FormStepContainerPageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormStepContainerPageElementDefinition)) {
			return false;
		}

		FormStepContainerPageElementDefinition
			formStepContainerPageElementDefinition =
				(FormStepContainerPageElementDefinition)object;

		return Objects.equals(
			toString(), formStepContainerPageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormStepContainerPageElementDefinitionSerDes.toJSON(this);
	}

}