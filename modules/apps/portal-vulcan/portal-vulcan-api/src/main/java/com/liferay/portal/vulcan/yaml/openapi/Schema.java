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

import java.util.List;
import java.util.Map;

/**
 * @author Peter Shin
 */
public class Schema {

	public Schema() {
	}

	public Schema(boolean freeFormObject) {
		if (freeFormObject) {
			setType("?");
		}
	}

	public Schema getAdditionalPropertySchema() {
		return _additionalPropertySchema;
	}

	public List<Schema> getAllOfSchemas() {
		return _allOfSchemas;
	}

	public List<Schema> getAnyOfSchemas() {
		return _anyOfSchemas;
	}

	public String getDescription() {
		return _description;
	}

	public List<String> getEnumValues() {
		return _enumValues;
	}

	public String getExample() {
		return _example;
	}

	public String getFormat() {
		return _format;
	}

	public Items getItems() {
		return _items;
	}

	public List<Schema> getOneOfSchemas() {
		return _oneOfSchemas;
	}

	public Map<String, Schema> getPropertySchemas() {
		return _propertySchemas;
	}

	public String getReference() {
		return _reference;
	}

	public List<String> getRequiredPropertySchemaNames() {
		return _requiredPropertySchemaNames;
	}

	public String getType() {
		return _type;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public boolean isWriteOnly() {
		return _writeOnly;
	}

	public void setAdditionalPropertySchema(Schema additionalPropertySchema) {
		_additionalPropertySchema = additionalPropertySchema;
	}

	public void setAllOfSchemas(List<Schema> allOfSchemas) {
		_allOfSchemas = allOfSchemas;
	}

	public void setAnyOfSchemas(List<Schema> anyOfSchemas) {
		_anyOfSchemas = anyOfSchemas;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setEnumValues(List<String> enumValues) {
		_enumValues = enumValues;
	}

	public void setExample(String example) {
		_example = example;
	}

	public void setFormat(String format) {
		_format = format;
	}

	public void setItems(Items items) {
		_items = items;
	}

	public void setOneOfSchemas(List<Schema> oneOfSchemas) {
		_oneOfSchemas = oneOfSchemas;
	}

	public void setPropertySchemas(Map<String, Schema> propertySchemas) {
		_propertySchemas = propertySchemas;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	public void setReference(String reference) {
		_reference = reference;
	}

	public void setRequiredPropertySchemaNames(
		List<String> requiredPropertySchemaNames) {

		_requiredPropertySchemaNames = requiredPropertySchemaNames;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setWriteOnly(boolean writeOnly) {
		_writeOnly = writeOnly;
	}

	private Schema _additionalPropertySchema;
	private List<Schema> _allOfSchemas;
	private List<Schema> _anyOfSchemas;
	private String _description;
	private List<String> _enumValues;
	private String _example;
	private String _format;
	private Items _items;
	private List<Schema> _oneOfSchemas;
	private Map<String, Schema> _propertySchemas;
	private boolean _readOnly;
	private String _reference;
	private List<String> _requiredPropertySchemaNames;
	private String _type;
	private boolean _writeOnly;

}