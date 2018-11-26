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

import com.liferay.data.engine.exception.DataDefinitionColumnsSerializerException;
import com.liferay.data.engine.io.DataDefinitionColumnsSerializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionColumnsSerializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionColumnsJSONSerializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		Map<String, String> nameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
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

		DataDefinitionColumn dataDefinitionColumn2 =
			DataDefinitionColumn.Builder.newBuilder(
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

		DataDefinitionColumnsSerializerApplyRequest.Builder builder =
			DataDefinitionColumnsSerializerApplyRequest.Builder.newBuilder(
				Arrays.asList(dataDefinitionColumn1, dataDefinitionColumn2)
			);

		DataDefinitionColumnsJSONSerializer
			dataDefinitionColumnsJSONSerializer =
				new DataDefinitionColumnsJSONSerializer();

		dataDefinitionColumnsJSONSerializer.jsonFactory = new JSONFactoryImpl();

		DataDefinitionColumnsSerializerApplyResponse
			dataDefinitionColumnsSerializerApplyResponse =
				dataDefinitionColumnsJSONSerializer.apply(builder.build());

		String json = read("data-definition-columns-serializer.json");

		JSONAssert.assertEquals(
			json, dataDefinitionColumnsSerializerApplyResponse.getContent(),
			false);
	}

	@Test(expected = DataDefinitionColumnsSerializerException.class)
	public void testRequiredName()
		throws DataDefinitionColumnsSerializerException {

		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				null, DataDefinitionColumnType.BOOLEAN
			).build();

		DataDefinitionColumnsSerializerApplyRequest.Builder builder =
			DataDefinitionColumnsSerializerApplyRequest.Builder.newBuilder(
				Arrays.asList(dataDefinitionColumn1)
			);

		DataDefinitionColumnsJSONSerializer
			dataDefinitionColumnsJSONSerializer =
				new DataDefinitionColumnsJSONSerializer();

		dataDefinitionColumnsJSONSerializer.jsonFactory = new JSONFactoryImpl();

		dataDefinitionColumnsJSONSerializer.apply(builder.build());
	}

	@Test(expected = DataDefinitionColumnsSerializerException.class)
	public void testRequiredType()
		throws DataDefinitionColumnsSerializerException {

		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"name", null
			).build();

		DataDefinitionColumnsSerializerApplyRequest.Builder builder =
			DataDefinitionColumnsSerializerApplyRequest.Builder.newBuilder(
				Arrays.asList(dataDefinitionColumn1)
			);

		DataDefinitionColumnsJSONSerializer
			dataDefinitionColumnsJSONSerializer =
				new DataDefinitionColumnsJSONSerializer();

		dataDefinitionColumnsJSONSerializer.jsonFactory = new JSONFactoryImpl();

		dataDefinitionColumnsJSONSerializer.apply(builder.build());
	}

}