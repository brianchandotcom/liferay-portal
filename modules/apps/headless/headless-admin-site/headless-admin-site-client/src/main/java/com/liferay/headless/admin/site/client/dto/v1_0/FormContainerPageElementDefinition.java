/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FormContainerPageElementDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FormContainerPageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static FormContainerPageElementDefinition toDTO(String json) {
		return FormContainerPageElementDefinitionSerDes.toDTO(json);
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

	public FormContainerConfig getFormContainerConfig() {
		return formContainerConfig;
	}

	public void setFormContainerConfig(
		FormContainerConfig formContainerConfig) {

		this.formContainerConfig = formContainerConfig;
	}

	public void setFormContainerConfig(
		UnsafeSupplier<FormContainerConfig, Exception>
			formContainerConfigUnsafeSupplier) {

		try {
			formContainerConfig = formContainerConfigUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FormContainerConfig formContainerConfig;

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

	public Boolean getIndexed() {
		return indexed;
	}

	public void setIndexed(Boolean indexed) {
		this.indexed = indexed;
	}

	public void setIndexed(
		UnsafeSupplier<Boolean, Exception> indexedUnsafeSupplier) {

		try {
			indexed = indexedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean indexed;

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public void setLayout(
		UnsafeSupplier<Layout, Exception> layoutUnsafeSupplier) {

		try {
			layout = layoutUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Layout layout;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	@Override
	public FormContainerPageElementDefinition clone()
		throws CloneNotSupportedException {

		return (FormContainerPageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormContainerPageElementDefinition)) {
			return false;
		}

		FormContainerPageElementDefinition formContainerPageElementDefinition =
			(FormContainerPageElementDefinition)object;

		return Objects.equals(
			toString(), formContainerPageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormContainerPageElementDefinitionSerDes.toJSON(this);
	}

}