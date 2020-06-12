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

package com.liferay.portal.vulcan.yaml.openapi;

import java.beans.Transient;

import java.util.List;
import java.util.Map;

/**
 * @author Peter Shin
 */
public class Schema {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Schema() {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Schema(boolean freeFormObject) {
		if (freeFormObject) {
			setType("?");
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Schema getAdditionalPropertySchema() {
		return _additionalPropertySchema;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public List<Schema> getAllOfSchemas() {
		return _allOfSchemas;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public List<Schema> getAnyOfSchemas() {
		return _anyOfSchemas;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getDefault() {
		return _default;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getDescription() {
		return _description;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public List<String> getEnumValues() {
		return _enumValues;
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Transient
	public String getExample() {
		return _example;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getFormat() {
		return _format;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Items getItems() {
		return _items;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Double getMaximum() {
		return _maximum;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Double getMinimum() {
		return _minimum;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public List<Schema> getOneOfSchemas() {
		return _oneOfSchemas;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Map<String, Schema> getPropertySchemas() {
		return _propertySchemas;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getReference() {
		return _reference;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public List<String> getRequiredPropertySchemaNames() {
		return _requiredPropertySchemaNames;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getType() {
		return _type;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public boolean isDeprecated() {
		return _deprecated;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public boolean isReadOnly() {
		return _readOnly;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public boolean isWriteOnly() {
		return _writeOnly;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setAdditionalPropertySchema(Schema additionalPropertySchema) {
		_additionalPropertySchema = additionalPropertySchema;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setAllOfSchemas(List<Schema> allOfSchemas) {
		_allOfSchemas = allOfSchemas;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setAnyOfSchemas(List<Schema> anyOfSchemas) {
		_anyOfSchemas = anyOfSchemas;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setDefault(String d) {
		_default = d;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setDeprecated(boolean deprecated) {
		_deprecated = deprecated;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setDescription(String description) {
		_description = description;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setEnumValues(List<String> enumValues) {
		_enumValues = enumValues;
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Transient
	public void setExample(String example) {
		_example = example;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setFormat(String format) {
		_format = format;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setItems(Items items) {
		_items = items;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setMaximum(Double maximum) {
		_maximum = maximum;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setMinimum(Double minimum) {
		_minimum = minimum;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setOneOfSchemas(List<Schema> oneOfSchemas) {
		_oneOfSchemas = oneOfSchemas;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setPropertySchemas(Map<String, Schema> propertySchemas) {
		_propertySchemas = propertySchemas;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setReference(String reference) {
		_reference = reference;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setRequiredPropertySchemaNames(
		List<String> requiredPropertySchemaNames) {

		_requiredPropertySchemaNames = requiredPropertySchemaNames;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setType(String type) {
		_type = type;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setWriteOnly(boolean writeOnly) {
		_writeOnly = writeOnly;
	}

	private Schema _additionalPropertySchema;
	private List<Schema> _allOfSchemas;
	private List<Schema> _anyOfSchemas;
	private String _default;
	private boolean _deprecated;
	private String _description;
	private List<String> _enumValues;
	private String _example;
	private String _format;
	private Items _items;
	private Double _maximum;
	private Double _minimum;
	private List<Schema> _oneOfSchemas;
	private Map<String, Schema> _propertySchemas;
	private boolean _readOnly;
	private String _reference;
	private List<String> _requiredPropertySchemaNames;
	private String _type;
	private boolean _writeOnly;

}