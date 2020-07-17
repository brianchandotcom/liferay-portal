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
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

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
@GraphQLName("FormPageContext")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FormPageContext")
public class FormPageContext {

	public static FormPageContext toDTO(String json) {
		return ObjectMapperUtil.readValue(FormPageContext.class, json);
	}

	@Schema
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	public void setEnabled(
		UnsafeSupplier<Boolean, Exception> enabledUnsafeSupplier) {

		try {
			enabled = enabledUnsafeSupplier.get();
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
	protected Boolean enabled;

	@Schema(description = "https://www.schema.org/FormFieldContext")
	@Valid
	public FormFieldContext[] getFormFieldContexts() {
		return formFieldContexts;
	}

	public void setFormFieldContexts(FormFieldContext[] formFieldContexts) {
		this.formFieldContexts = formFieldContexts;
	}

	@JsonIgnore
	public void setFormFieldContexts(
		UnsafeSupplier<FormFieldContext[], Exception>
			formFieldContextsUnsafeSupplier) {

		try {
			formFieldContexts = formFieldContextsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "https://www.schema.org/FormFieldContext")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FormFieldContext[] formFieldContexts;

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
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (enabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enabled\": ");

			sb.append(enabled);
		}

		if (formFieldContexts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formFieldContexts\": ");

			sb.append("[");

			for (int i = 0; i < formFieldContexts.length; i++) {
				sb.append(String.valueOf(formFieldContexts[i]));

				if ((i + 1) < formFieldContexts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (showRequiredFieldsWarning != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showRequiredFieldsWarning\": ");

			sb.append(showRequiredFieldsWarning);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.form.dto.v1_0.FormPageContext",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(value);
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}