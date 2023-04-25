/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.Field;
import com.liferay.osb.faro.engine.client.model.FieldMapping;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class FieldMappingValuesDisplay extends FieldMappingDisplay {

	public FieldMappingValuesDisplay() {
	}

	public FieldMappingValuesDisplay(FieldMapping fieldMapping, Field field) {
		super(fieldMapping);

		if (field == null) {
			return;
		}

		_values = Collections.singletonList(field.getValue());
	}

	public FieldMappingValuesDisplay(
		FieldMapping fieldMapping, List<Field> fields) {

		super(fieldMapping);

		if (fields == null) {
			return;
		}

		_values = TransformUtil.transform(fields, Field::getValue);
	}

	private List<String> _values = new ArrayList<>();

}