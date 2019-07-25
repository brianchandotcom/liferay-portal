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

package com.liferay.talend.common.schema.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.avro.Schema;

import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.SchemaConstants;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class RejectSchemaConstants {

	public static final String FIELD_ERROR_CODE = "errorCode";

	public static final String FIELD_ERROR_MESSAGE = "errorMessage";

	public static final List<Schema.Field> rejectFields =
		Collections.unmodifiableList(
			new ArrayList<Schema.Field>() {
				{
					Schema.Field field = new Schema.Field(
						FIELD_ERROR_MESSAGE,
						AvroUtils.wrapAsNullable(AvroUtils._string()), null,
						(Object)null);

					field.addProp(
						SchemaConstants.TALEND_COLUMN_DB_LENGTH, "255");
					field.addProp(
						SchemaConstants.TALEND_FIELD_GENERATED, "true");
					field.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

					add(field);

					field = new Schema.Field(
						FIELD_ERROR_CODE,
						AvroUtils.wrapAsNullable(AvroUtils._int()), null,
						(Object)null);

					field.addProp(
						SchemaConstants.TALEND_FIELD_GENERATED, "true");
					field.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

					add(field);
				}
			});

}