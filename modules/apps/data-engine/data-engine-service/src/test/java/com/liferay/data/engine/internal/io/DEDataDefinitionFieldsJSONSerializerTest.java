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

import com.liferay.data.engine.exception.DEDataDefinitionFieldsSerializerException;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Leonardo Barros
 */
public class DEDataDefinitionFieldsJSONSerializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		Map<String, String> nameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			DEDataDefinitionField.Builder.newBuilder(
				"name", "string"
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

		DEDataDefinitionField deDataDefinitionField2 =
			DEDataDefinitionField.Builder.newBuilder(
				"email", "string"
			).defaultValue(
				"test@liferay.com"
			).indexable(
				false
			).label(
				emailLabels
			).tip(
				emailTips
			).build();

		DEDataDefinitionFieldsSerializerApplyRequest.Builder builder =
			DEDataDefinitionFieldsSerializerApplyRequest.Builder.newBuilder(
				Arrays.asList(deDataDefinitionField1, deDataDefinitionField2)
			);

		DEDataDefinitionFieldsJSONSerializer
			deDataDefinitionFieldsJSONSerializer =
				new DEDataDefinitionFieldsJSONSerializer();

		deDataDefinitionFieldsJSONSerializer.jsonFactory =
			new JSONFactoryImpl();

		DEDataDefinitionFieldsSerializerApplyResponse
			deDataDefinitionFieldsSerializerApplyResponse =
				deDataDefinitionFieldsJSONSerializer.apply(builder.build());

		String json = read("data-definition-fields-serializer.json");

		JSONAssert.assertEquals(
			json, deDataDefinitionFieldsSerializerApplyResponse.getContent(),
			false);
	}

	@Test(expected = DEDataDefinitionFieldsSerializerException.class)
	public void testRequiredName()
		throws DEDataDefinitionFieldsSerializerException {

		DEDataDefinitionField deDataDefinitionField1 =
			DEDataDefinitionField.Builder.newBuilder(
				null, "boolean"
			).build();

		DEDataDefinitionFieldsSerializerApplyRequest.Builder builder =
			DEDataDefinitionFieldsSerializerApplyRequest.Builder.newBuilder(
				Arrays.asList(deDataDefinitionField1)
			);

		DEDataDefinitionFieldsJSONSerializer
			deDataDefinitionFieldsJSONSerializer =
				new DEDataDefinitionFieldsJSONSerializer();

		deDataDefinitionFieldsJSONSerializer.jsonFactory =
			new JSONFactoryImpl();

		deDataDefinitionFieldsJSONSerializer.apply(builder.build());
	}

	@Test(expected = DEDataDefinitionFieldsSerializerException.class)
	public void testRequiredType()
		throws DEDataDefinitionFieldsSerializerException {

		DEDataDefinitionField deDataDefinitionField1 =
			DEDataDefinitionField.Builder.newBuilder(
				"name", null
			).build();

		DEDataDefinitionFieldsSerializerApplyRequest.Builder builder =
			DEDataDefinitionFieldsSerializerApplyRequest.Builder.newBuilder(
				Arrays.asList(deDataDefinitionField1)
			);

		DEDataDefinitionFieldsJSONSerializer
			deDataDefinitionFieldsJSONSerializer =
				new DEDataDefinitionFieldsJSONSerializer();

		deDataDefinitionFieldsJSONSerializer.jsonFactory =
			new JSONFactoryImpl();

		deDataDefinitionFieldsJSONSerializer.apply(builder.build());
	}

}