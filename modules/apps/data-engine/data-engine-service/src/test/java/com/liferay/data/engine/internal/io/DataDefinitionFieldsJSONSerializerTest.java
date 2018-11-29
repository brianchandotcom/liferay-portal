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

package com.liferay.data.engine.internal.io;

import com.liferay.data.engine.exception.DataDefinitionFieldsSerializerException;
import com.liferay.data.engine.io.DataDefinitionFieldsSerializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionFieldsSerializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.data.engine.model.DataDefinitionField;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionFieldsJSONSerializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		Map<String, String> nameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DataDefinitionField dataDefinitionField1 =
			DataDefinitionField.Builder.newBuilder(
				"name", DataDefinitionColumnType.STRING
			).required(
				true
			).label(
				nameLabels
			).build();

		Map<String, String> emailLabels = new HashMap() {
			{
				put("pt_BR", "Endereço de Email");
				put("en_US", "Email Address");
			}
		};

		Map<String, String> emailTips = new HashMap() {
			{
				put("en_US", "Enter an email address");
				put("pt_BR", "Informe um endereço de email");
			}
		};

		DataDefinitionField dataDefinitionField2 =
			DataDefinitionField.Builder.newBuilder(
				"email", DataDefinitionColumnType.STRING
			).defaultValue(
				"test@liferay.com"
			).indexable(
				false
			).label(
				emailLabels
			).tip(
				emailTips
			).build();

		DataDefinitionFieldsSerializerApplyRequest.Builder builder =
			DataDefinitionFieldsSerializerApplyRequest.Builder.newBuilder(
				Arrays.asList(dataDefinitionField1, dataDefinitionField2)
			);

		DataDefinitionFieldsJSONSerializer dataDefinitionFieldsJSONSerializer =
			new DataDefinitionFieldsJSONSerializer();

		dataDefinitionFieldsJSONSerializer.jsonFactory = new JSONFactoryImpl();

		DataDefinitionFieldsSerializerApplyResponse
			dataDefinitionFieldsSerializerApplyResponse =
				dataDefinitionFieldsJSONSerializer.apply(builder.build());

		String json = read("data-definition-fields-serializer.json");

		JSONAssert.assertEquals(
			json, dataDefinitionFieldsSerializerApplyResponse.getContent(),
			false);
	}

	@Test(expected = DataDefinitionFieldsSerializerException.class)
	public void testRequiredName()
		throws DataDefinitionFieldsSerializerException {

		DataDefinitionField dataDefinitionField1 =
			DataDefinitionField.Builder.newBuilder(
				null, DataDefinitionColumnType.BOOLEAN
			).build();

		DataDefinitionFieldsSerializerApplyRequest.Builder builder =
			DataDefinitionFieldsSerializerApplyRequest.Builder.newBuilder(
				Arrays.asList(dataDefinitionField1)
			);

		DataDefinitionFieldsJSONSerializer dataDefinitionFieldsJSONSerializer =
			new DataDefinitionFieldsJSONSerializer();

		dataDefinitionFieldsJSONSerializer.jsonFactory = new JSONFactoryImpl();

		dataDefinitionFieldsJSONSerializer.apply(builder.build());
	}

	@Test(expected = DataDefinitionFieldsSerializerException.class)
	public void testRequiredType()
		throws DataDefinitionFieldsSerializerException {

		DataDefinitionField dataDefinitionField1 =
			DataDefinitionField.Builder.newBuilder(
				"name", null
			).build();

		DataDefinitionFieldsSerializerApplyRequest.Builder builder =
			DataDefinitionFieldsSerializerApplyRequest.Builder.newBuilder(
				Arrays.asList(dataDefinitionField1)
			);

		DataDefinitionFieldsJSONSerializer dataDefinitionFieldsJSONSerializer =
			new DataDefinitionFieldsJSONSerializer();

		dataDefinitionFieldsJSONSerializer.jsonFactory = new JSONFactoryImpl();

		dataDefinitionFieldsJSONSerializer.apply(builder.build());
	}

}