/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FormFragmentInstancePageElementDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FormFragmentInstancePageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static FormFragmentInstancePageElementDefinition toDTO(String json) {
		return FormFragmentInstancePageElementDefinitionSerDes.toDTO(json);
	}

	public String getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}

	public void setFieldKey(
		UnsafeSupplier<String, Exception> fieldKeyUnsafeSupplier) {

		try {
			fieldKey = fieldKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String fieldKey;

	public FragmentInstance getFragmentInstance() {
		return fragmentInstance;
	}

	public void setFragmentInstance(FragmentInstance fragmentInstance) {
		this.fragmentInstance = fragmentInstance;
	}

	public void setFragmentInstance(
		UnsafeSupplier<FragmentInstance, Exception>
			fragmentInstanceUnsafeSupplier) {

		try {
			fragmentInstance = fragmentInstanceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentInstance fragmentInstance;

	public Map<String, String> getHelpText_i18n() {
		return helpText_i18n;
	}

	public void setHelpText_i18n(Map<String, String> helpText_i18n) {
		this.helpText_i18n = helpText_i18n;
	}

	public void setHelpText_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			helpText_i18nUnsafeSupplier) {

		try {
			helpText_i18n = helpText_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> helpText_i18n;

	public Map<String, String> getLabel_i18n() {
		return label_i18n;
	}

	public void setLabel_i18n(Map<String, String> label_i18n) {
		this.label_i18n = label_i18n;
	}

	public void setLabel_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			label_i18nUnsafeSupplier) {

		try {
			label_i18n = label_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> label_i18n;

	public Boolean getMarkAsRequired() {
		return markAsRequired;
	}

	public void setMarkAsRequired(Boolean markAsRequired) {
		this.markAsRequired = markAsRequired;
	}

	public void setMarkAsRequired(
		UnsafeSupplier<Boolean, Exception> markAsRequiredUnsafeSupplier) {

		try {
			markAsRequired = markAsRequiredUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean markAsRequired;

	public Boolean getReadOnlyField() {
		return readOnlyField;
	}

	public void setReadOnlyField(Boolean readOnlyField) {
		this.readOnlyField = readOnlyField;
	}

	public void setReadOnlyField(
		UnsafeSupplier<Boolean, Exception> readOnlyFieldUnsafeSupplier) {

		try {
			readOnlyField = readOnlyFieldUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean readOnlyField;

	public Boolean getShowHelpText() {
		return showHelpText;
	}

	public void setShowHelpText(Boolean showHelpText) {
		this.showHelpText = showHelpText;
	}

	public void setShowHelpText(
		UnsafeSupplier<Boolean, Exception> showHelpTextUnsafeSupplier) {

		try {
			showHelpText = showHelpTextUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean showHelpText;

	public Boolean getShowLabel() {
		return showLabel;
	}

	public void setShowLabel(Boolean showLabel) {
		this.showLabel = showLabel;
	}

	public void setShowLabel(
		UnsafeSupplier<Boolean, Exception> showLabelUnsafeSupplier) {

		try {
			showLabel = showLabelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean showLabel;

	@Override
	public FormFragmentInstancePageElementDefinition clone()
		throws CloneNotSupportedException {

		return (FormFragmentInstancePageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormFragmentInstancePageElementDefinition)) {
			return false;
		}

		FormFragmentInstancePageElementDefinition
			formFragmentInstancePageElementDefinition =
				(FormFragmentInstancePageElementDefinition)object;

		return Objects.equals(
			toString(), formFragmentInstancePageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormFragmentInstancePageElementDefinitionSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-82705661