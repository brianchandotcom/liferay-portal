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

import com.liferay.talend.runtime.apio.form.Property;
import com.liferay.talend.runtime.apio.jsonld.ApioForm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;

import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.NameUtil;

/**
 * @author Zoltán Takács
 */
public class ExpectedFormSchemaInferrer {

	public static Schema inferSchemaByFormProperties(ApioForm apioForm) {
		List<Property> properties = apioForm.getSupportedProperties();

		Stream<Property> stream = properties.stream();

		List<String> fieldNames = stream.filter(
			Property::isWriteable
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

		List<Field> schemaFields = new ArrayList<>(size);

		Set<String> filedNames = new HashSet<>();

		for (int i = 0; i < size; i++) {
			String fieldName = NameUtil.correct(
				fieldNames.get(i), i, filedNames);

			filedNames.add(fieldName);

			Field designField = new Field(
				fieldName, AvroUtils.wrapAsNullable(AvroUtils._string()), null,
				(Object)null);

			schemaFields.add(i, designField);
		}

		Schema schema = Schema.createRecord(
			"Runtime", null, null, false, schemaFields);

		return schema;
	}

}