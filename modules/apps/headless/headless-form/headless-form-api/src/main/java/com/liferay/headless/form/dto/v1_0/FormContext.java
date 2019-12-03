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

package com.liferay.headless.form.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("FormContext")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FormContext")
public class FormContext {

	@Schema(description = "https://www.schema.org/FormFieldValue")
	@Valid
	public FormFieldValue[] getFormFieldValues() {
		return formFieldValues;
	}

	public void setFormFieldValues(FormFieldValue[] formFieldValues) {
		this.formFieldValues = formFieldValues;
	}

	@JsonIgnore
	public void setFormFieldValues(
		UnsafeSupplier<FormFieldValue[], Exception>
			formFieldValuesUnsafeSupplier) {

		try {
			formFieldValues = formFieldValuesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "https://www.schema.org/FormFieldValue")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FormFieldValue[] formFieldValues;

	@Schema(description = "https://www.schema.org/FormPageContext")
	@Valid
	public FormPageContext[] getFormPageContexts() {
		return formPageContexts;
	}

	public void setFormPageContexts(FormPageContext[] formPageContexts) {
		this.formPageContexts = formPageContexts;
	}

	@JsonIgnore
	public void setFormPageContexts(
		UnsafeSupplier<FormPageContext[], Exception>
			formPageContextsUnsafeSupplier) {

		try {
			formPageContexts = formPageContextsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "https://www.schema.org/FormPageContext")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FormPageContext[] formPageContexts;

	@Schema
	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	@JsonIgnore
	public void setReadOnly(
		UnsafeSupplier<Boolean, Exception> readOnlyUnsafeSupplier) {

		try {
			readOnly = readOnlyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean readOnly;

	@Schema
	public Boolean getShowRequiredFieldsWarning() {
		return showRequiredFieldsWarning;
	}

	public void setShowRequiredFieldsWarning(
		Boolean showRequiredFieldsWarning) {

		this.showRequiredFieldsWarning = showRequiredFieldsWarning;
	}

	@JsonIgnore
	public void setShowRequiredFieldsWarning(
		UnsafeSupplier<Boolean, Exception>
			showRequiredFieldsWarningUnsafeSupplier) {

		try {
			showRequiredFieldsWarning =
				showRequiredFieldsWarningUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean showRequiredFieldsWarning;

	@Schema
	public Boolean getShowSubmitButton() {
		return showSubmitButton;
	}

	public void setShowSubmitButton(Boolean showSubmitButton) {
		this.showSubmitButton = showSubmitButton;
	}

	@JsonIgnore
	public void setShowSubmitButton(
		UnsafeSupplier<Boolean, Exception> showSubmitButtonUnsafeSupplier) {

		try {
			showSubmitButton = showSubmitButtonUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
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
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (formFieldValues != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formFieldValues\": ");

			sb.append("[");

			for (int i = 0; i < formFieldValues.length; i++) {
				sb.append(String.valueOf(formFieldValues[i]));

				if ((i + 1) < formFieldValues.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formPageContexts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formPageContexts\": ");

			sb.append("[");

			for (int i = 0; i < formPageContexts.length; i++) {
				sb.append(String.valueOf(formPageContexts[i]));

				if ((i + 1) < formPageContexts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (readOnly != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"readOnly\": ");

			sb.append(readOnly);
		}

		if (showRequiredFieldsWarning != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showRequiredFieldsWarning\": ");

			sb.append(showRequiredFieldsWarning);
		}

		if (showSubmitButton != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showSubmitButton\": ");

			sb.append(showSubmitButton);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.form.dto.v1_0.FormContext",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}