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

package com.liferay.data.engine.rest.client.dto.v1_0;

import com.liferay.data.engine.rest.client.function.UnsafeSupplier;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataDefinitionFieldSerDes;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinitionField {

	public Map<String, Object> getCustomProperties() {
		return customProperties;
	}

	public void setCustomProperties(Map<String, Object> customProperties) {
		this.customProperties = customProperties;
	}

	public void setCustomProperties(
		UnsafeSupplier<Map<String, Object>, Exception>
			customPropertiesUnsafeSupplier) {

		try {
			customProperties = customPropertiesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> customProperties;

	public Map<String, Object> getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Map<String, Object> defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setDefaultValue(
		UnsafeSupplier<Map<String, Object>, Exception>
			defaultValueUnsafeSupplier) {

		try {
			defaultValue = defaultValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> defaultValue;

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public void setFieldType(
		UnsafeSupplier<String, Exception> fieldTypeUnsafeSupplier) {

		try {
			fieldType = fieldTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String fieldType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public Boolean getIndexable() {
		return indexable;
	}

	public void setIndexable(Boolean indexable) {
		this.indexable = indexable;
	}

	public void setIndexable(
		UnsafeSupplier<Boolean, Exception> indexableUnsafeSupplier) {

		try {
			indexable = indexableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean indexable;

	public Map<String, Object> getLabel() {
		return label;
	}

	public void setLabel(Map<String, Object> label) {
		this.label = label;
	}

	public void setLabel(
		UnsafeSupplier<Map<String, Object>, Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> label;

	public Boolean getLocalizable() {
		return localizable;
	}

	public void setLocalizable(Boolean localizable) {
		this.localizable = localizable;
	}

	public void setLocalizable(
		UnsafeSupplier<Boolean, Exception> localizableUnsafeSupplier) {

		try {
			localizable = localizableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean localizable;

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

	public DataDefinitionField[] getNestedDataDefinitionFields() {
		return nestedDataDefinitionFields;
	}

	public void setNestedDataDefinitionFields(
		DataDefinitionField[] nestedDataDefinitionFields) {

		this.nestedDataDefinitionFields = nestedDataDefinitionFields;
	}

	public void setNestedDataDefinitionFields(
		UnsafeSupplier<DataDefinitionField[], Exception>
			nestedDataDefinitionFieldsUnsafeSupplier) {

		try {
			nestedDataDefinitionFields =
				nestedDataDefinitionFieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DataDefinitionField[] nestedDataDefinitionFields;

	public Boolean getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(Boolean repeatable) {
		this.repeatable = repeatable;
	}

	public void setRepeatable(
		UnsafeSupplier<Boolean, Exception> repeatableUnsafeSupplier) {

		try {
			repeatable = repeatableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean repeatable;

	public Map<String, Object> getTip() {
		return tip;
	}

	public void setTip(Map<String, Object> tip) {
		this.tip = tip;
	}

	public void setTip(
		UnsafeSupplier<Map<String, Object>, Exception> tipUnsafeSupplier) {

		try {
			tip = tipUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> tip;

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
		return DataDefinitionFieldSerDes.toJSON(this);
	}

}