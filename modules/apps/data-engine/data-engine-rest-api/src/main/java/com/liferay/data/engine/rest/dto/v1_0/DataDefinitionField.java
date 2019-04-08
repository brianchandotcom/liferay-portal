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

package com.liferay.data.engine.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@GraphQLName("DataDefinitionField")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DataDefinitionField")
public class DataDefinitionField {

	public CustomProperty[] getCustomProperties() {
		return customProperties;
	}

	public void setCustomProperties(CustomProperty[] customProperties) {
		this.customProperties = customProperties;
	}

	@JsonIgnore
	public void setCustomProperties(
		UnsafeSupplier<CustomProperty[], Exception>
			customPropertiesUnsafeSupplier) {

		try {
			customProperties = customPropertiesUnsafeSupplier.get();
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
	protected CustomProperty[] customProperties;

	public LocalizedValue[] getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(LocalizedValue[] defaultValue) {
		this.defaultValue = defaultValue;
	}

	@JsonIgnore
	public void setDefaultValue(
		UnsafeSupplier<LocalizedValue[], Exception>
			defaultValueUnsafeSupplier) {

		try {
			defaultValue = defaultValueUnsafeSupplier.get();
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
	protected LocalizedValue[] defaultValue;

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	@JsonIgnore
	public void setFieldType(
		UnsafeSupplier<String, Exception> fieldTypeUnsafeSupplier) {

		try {
			fieldType = fieldTypeUnsafeSupplier.get();
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
	protected String fieldType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
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
	protected Long id;

	public Boolean getIndexable() {
		return indexable;
	}

	public void setIndexable(Boolean indexable) {
		this.indexable = indexable;
	}

	@JsonIgnore
	public void setIndexable(
		UnsafeSupplier<Boolean, Exception> indexableUnsafeSupplier) {

		try {
			indexable = indexableUnsafeSupplier.get();
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
	protected Boolean indexable;

	public LocalizedValue[] getLabel() {
		return label;
	}

	public void setLabel(LocalizedValue[] label) {
		this.label = label;
	}

	@JsonIgnore
	public void setLabel(
		UnsafeSupplier<LocalizedValue[], Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
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
	protected LocalizedValue[] label;

	public Boolean getLocalizable() {
		return localizable;
	}

	public void setLocalizable(Boolean localizable) {
		this.localizable = localizable;
	}

	@JsonIgnore
	public void setLocalizable(
		UnsafeSupplier<Boolean, Exception> localizableUnsafeSupplier) {

		try {
			localizable = localizableUnsafeSupplier.get();
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
	protected Boolean localizable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
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
	protected String name;

	public Boolean getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(Boolean repeatable) {
		this.repeatable = repeatable;
	}

	@JsonIgnore
	public void setRepeatable(
		UnsafeSupplier<Boolean, Exception> repeatableUnsafeSupplier) {

		try {
			repeatable = repeatableUnsafeSupplier.get();
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
	protected Boolean repeatable;

	public LocalizedValue[] getTip() {
		return tip;
	}

	public void setTip(LocalizedValue[] tip) {
		this.tip = tip;
	}

	@JsonIgnore
	public void setTip(
		UnsafeSupplier<LocalizedValue[], Exception> tipUnsafeSupplier) {

		try {
			tip = tipUnsafeSupplier.get();
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
	protected LocalizedValue[] tip;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataDefinitionField)) {
			return false;
		}

		DataDefinitionField dataDefinitionField = (DataDefinitionField)object;

		return Objects.equals(toString(), dataDefinitionField.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"customProperties\": ");

		if (customProperties == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < customProperties.length; i++) {
				sb.append(customProperties[i]);

				if ((i + 1) < customProperties.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"defaultValue\": ");

		if (defaultValue == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < defaultValue.length; i++) {
				sb.append(defaultValue[i]);

				if ((i + 1) < defaultValue.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"fieldType\": ");

		sb.append("\"");
		sb.append(fieldType);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"indexable\": ");

		sb.append(indexable);
		sb.append(", ");

		sb.append("\"label\": ");

		if (label == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < label.length; i++) {
				sb.append(label[i]);

				if ((i + 1) < label.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"localizable\": ");

		sb.append(localizable);
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"repeatable\": ");

		sb.append(repeatable);
		sb.append(", ");

		sb.append("\"tip\": ");

		if (tip == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < tip.length; i++) {
				sb.append(tip[i]);

				if ((i + 1) < tip.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}