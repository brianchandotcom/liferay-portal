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

import com.liferay.data.engine.exception.DataDefinitionColumnsDeserializerException;
import com.liferay.data.engine.io.DataDefinitionColumnsDeserializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionColumnsDeserializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionColumnsJSONDeserializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		String json = read("data-definition-columns-deserializer.json");

		DataDefinitionColumnsDeserializerApplyRequest
			dataDefinitionColumnsDeserializerApplyRequest =
				DataDefinitionColumnsDeserializerApplyRequest.Builder.of(json);

		DataDefinitionColumnsJSONDeserializer
			dataDefinitionColumnsJSONDeserializer =
				new DataDefinitionColumnsJSONDeserializer();

		dataDefinitionColumnsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		DataDefinitionColumnsDeserializerApplyResponse
			dataDefinitionColumnsDeserializerApplyResponse =
				dataDefinitionColumnsJSONDeserializer.apply(
					dataDefinitionColumnsDeserializerApplyRequest);

		List<DataDefinitionColumn> dataDefinitionColumns =
			dataDefinitionColumnsDeserializerApplyResponse.
				getDataDefinitionColumns();

		Assert.assertEquals(
			dataDefinitionColumns.toString(), 2, dataDefinitionColumns.size());

		DataDefinitionColumn dataDefinitionColumn = dataDefinitionColumns.get(
			0);

		Assert.assertEquals(false, dataDefinitionColumn.isIndexable());
		Assert.assertEquals(true, dataDefinitionColumn.isLocalizable());
		Assert.assertEquals(false, dataDefinitionColumn.isRepeatable());
		Assert.assertEquals("name", dataDefinitionColumn.getName());

		Assert.assertEquals(
			DataDefinitionColumnType.STRING, dataDefinitionColumn.getType());

		Map<String, String> labels = dataDefinitionColumn.getLabel();

		Assert.assertEquals("Name", labels.get("en_US"));
		Assert.assertEquals("Nome", labels.get("pt_BR"));

		dataDefinitionColumn = dataDefinitionColumns.get(1);

		Assert.assertEquals(true, dataDefinitionColumn.isIndexable());
		Assert.assertEquals(false, dataDefinitionColumn.isLocalizable());
		Assert.assertEquals(true, dataDefinitionColumn.isRepeatable());
		Assert.assertEquals("email", dataDefinitionColumn.getName());

		Assert.assertEquals(
			DataDefinitionColumnType.STRING, dataDefinitionColumn.getType());

		labels = dataDefinitionColumn.getLabel();

		Assert.assertEquals("Email Address", labels.get("en_US"));
		Assert.assertEquals("Endereço de Email", labels.get("pt_BR"));

		Map<String, String> tips = dataDefinitionColumn.getTip();

		Assert.assertEquals("Enter an email address", tips.get("en_US"));
		Assert.assertEquals("Informe um endereço de email", tips.get("pt_BR"));
	}

	@Test
	public void testInvalidLabel() throws Exception {
		String json = read(
			"data-definition-columns-deserializer-invalid-label.json");

		DataDefinitionColumnsDeserializerApplyRequest
			dataDefinitionColumnsDeserializerApplyRequest =
				DataDefinitionColumnsDeserializerApplyRequest.Builder.of(json);

		DataDefinitionColumnsJSONDeserializer
			dataDefinitionColumnsJSONDeserializer =
				new DataDefinitionColumnsJSONDeserializer();

		dataDefinitionColumnsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		try {
			dataDefinitionColumnsJSONDeserializer.apply(
				dataDefinitionColumnsDeserializerApplyRequest);

			Assert.fail(
				"DataDefinitionColumnsDeserializerException should be thrown");
		}
		catch (DataDefinitionColumnsDeserializerException ddcde)
		{
			Assert.assertEquals(
				"Label property must contain localized values",
				ddcde.getMessage());
		}
		catch (Exception e) {
			Assert.fail(
				"DataDefinitionColumnsDeserializerException should be thrown");
		}
	}

	@Test
	public void testInvalidTip() throws Exception {
		String json = read(
			"data-definition-columns-deserializer-invalid-tip.json");

		DataDefinitionColumnsDeserializerApplyRequest
			dataDefinitionColumnsDeserializerApplyRequest =
				DataDefinitionColumnsDeserializerApplyRequest.Builder.of(json);

		DataDefinitionColumnsJSONDeserializer
			dataDefinitionColumnsJSONDeserializer =
				new DataDefinitionColumnsJSONDeserializer();

		dataDefinitionColumnsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		try {
			dataDefinitionColumnsJSONDeserializer.apply(
				dataDefinitionColumnsDeserializerApplyRequest);

			Assert.fail(
				"DataDefinitionColumnsDeserializerException should be thrown");
		}
		catch (DataDefinitionColumnsDeserializerException ddcde)
		{
			Assert.assertEquals(
				"Tip property must contain localized values",
				ddcde.getMessage());
		}
		catch (Exception e) {
			Assert.fail(
				"DataDefinitionColumnsDeserializerException should be thrown");
		}
	}

	@Test
	public void testRequiredName() throws Exception {
		String json = read(
			"data-definition-columns-deserializer-required-name.json");

		DataDefinitionColumnsDeserializerApplyRequest
			dataDefinitionColumnsDeserializerApplyRequest =
				DataDefinitionColumnsDeserializerApplyRequest.Builder.of(json);

		DataDefinitionColumnsJSONDeserializer
			dataDefinitionColumnsJSONDeserializer =
				new DataDefinitionColumnsJSONDeserializer();

		dataDefinitionColumnsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		try {
			dataDefinitionColumnsJSONDeserializer.apply(
				dataDefinitionColumnsDeserializerApplyRequest);

			Assert.fail(
				"DataDefinitionColumnsDeserializerException should be thrown");
		}
		catch (DataDefinitionColumnsDeserializerException ddcde)
		{
			Assert.assertEquals(
				"Name property is required", ddcde.getMessage());
		}
		catch (Exception e) {
			Assert.fail(
				"DataDefinitionColumnsDeserializerException should be thrown");
		}
	}

	@Test
	public void testRequiredType() throws Exception {
		String json = read(
			"data-definition-columns-deserializer-required-type.json");

		DataDefinitionColumnsDeserializerApplyRequest
			dataDefinitionColumnsDeserializerApplyRequest =
				DataDefinitionColumnsDeserializerApplyRequest.Builder.of(json);

		DataDefinitionColumnsJSONDeserializer
			dataDefinitionColumnsJSONDeserializer =
				new DataDefinitionColumnsJSONDeserializer();

		dataDefinitionColumnsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		try {
			dataDefinitionColumnsJSONDeserializer.apply(
				dataDefinitionColumnsDeserializerApplyRequest);

			Assert.fail(
				"DataDefinitionColumnsDeserializerException should be thrown");
		}
		catch (DataDefinitionColumnsDeserializerException ddcde)
		{
			Assert.assertEquals(
				"Type property is required", ddcde.getMessage());
		}
		catch (Exception e) {
			Assert.fail(
				"DataDefinitionColumnsDeserializerException should be thrown");
		}
	}

}