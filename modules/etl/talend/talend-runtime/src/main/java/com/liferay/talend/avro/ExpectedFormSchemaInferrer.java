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

package com.liferay.talend.avro;

import com.liferay.talend.avro.constants.AvroConstants;
import com.liferay.talend.runtime.apio.form.Property;
import com.liferay.talend.runtime.apio.jsonld.ApioForm;
import com.liferay.talend.runtime.apio.operation.Operation;
import com.liferay.talend.tliferayoutput.Action;
import com.liferay.talend.utils.SchemaUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.avro.Schema;

import org.talend.components.common.SchemaProperties;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.NameUtil;
import org.talend.daikon.avro.SchemaConstants;

/**
 * @author Zoltán Takács
 */
public class ExpectedFormSchemaInferrer {

	public static Schema inferSchemaByFormOperation(
		Operation operation, ApioForm apioForm) {

		String methodName = operation.getMethod();
		Schema schema = SchemaProperties.EMPTY_SCHEMA;

		if (methodName.equals(Action.Delete.getMethodName())) {
			schema = _getDeleteSchema();
		}
		else {
			schema = _getSchema(apioForm, operation);
		}

		return schema;
	}

	private static Schema _getDeleteSchema() {
		List<Schema.Field> schemaFields = new ArrayList<>(1);

		Schema.Field designField = new Schema.Field(
			AvroConstants.ID, AvroUtils._string(), null, (Object)null);

		designField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

		schemaFields.add(designField);

		Schema schema = Schema.createRecord(
			"Runtime", null, null, false, schemaFields);

		return schema;
	}

	private static Schema _getSchema(ApioForm apioForm, Operation operation) {
		List<Property> properties = new ArrayList<>(
			apioForm.getSupportedProperties());

		if (operation.isSingleModel()) {
			properties.add(_ID_PROPERTY);
		}

		Predicate<Property> optionalPredicate = property -> {
			return property.isWriteable() && !property.isRequired();
		};
		Predicate<Property> requiredPredicate = property -> {
			return property.isWriteable() && property.isRequired();
		};

		Schema optionalSchema = _getSchemaFromProperties(
			properties, optionalPredicate, true);
		Schema requiredSchema = _getSchemaFromProperties(
			properties, requiredPredicate, false);

		List<Schema.Field> optionalSchemaFields = optionalSchema.getFields();

		return SchemaUtils.appendFields(requiredSchema, optionalSchemaFields);
	}

	private static Schema _getSchemaFromProperties(
		List<Property> properties, Predicate<Property> propertyPredicate,
		boolean modifiable) {

		Stream<Property> stream = properties.stream();

		List<String> fieldNames = stream.filter(
			propertyPredicate
		).map(
			property -> {
				String name = property.getName();

				if (name.startsWith("#")) {
					return name.substring(1);
				}

				return name;
			}
		).collect(
			Collectors.toList()
		);

		int size = fieldNames.size();

		List<Schema.Field> schemaFields = new ArrayList<>(size);

		Set<String> filedNames = new HashSet<>();

		for (int i = 0; i < size; i++) {
			String fieldName = NameUtil.correct(
				fieldNames.get(i), i, filedNames);

			filedNames.add(fieldName);

			Schema.Field designField = new Schema.Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
				(Object)null);

			if (!modifiable) {
				designField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");
			}

			schemaFields.add(i, designField);
		}

		return Schema.createRecord("Runtime", null, null, false, schemaFields);
	}

	private static final Property _ID_PROPERTY = new Property(
		AvroConstants.ID, true, false, true);

}