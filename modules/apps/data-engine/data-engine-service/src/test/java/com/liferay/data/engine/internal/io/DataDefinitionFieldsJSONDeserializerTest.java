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

import com.liferay.data.engine.exception.DataDefinitionFieldsDeserializerException;
import com.liferay.data.engine.io.DataDefinitionFieldsDeserializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionFieldsDeserializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.data.engine.model.DataDefinitionField;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionFieldsJSONDeserializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		String json = read("data-definition-fields-deserializer.json");

		DataDefinitionFieldsDeserializerApplyRequest
			dataDefinitionFieldsDeserializerApplyRequest =
				DataDefinitionFieldsDeserializerApplyRequest.Builder.of(json);

		DataDefinitionFieldsJSONDeserializer
			dataDefinitionFieldsJSONDeserializer =
				new DataDefinitionFieldsJSONDeserializer();

		dataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		DataDefinitionFieldsDeserializerApplyResponse
			dataDefinitionFieldsDeserializerApplyResponse =
				dataDefinitionFieldsJSONDeserializer.apply(
					dataDefinitionFieldsDeserializerApplyRequest);

		List<DataDefinitionField> dataDefinitionFields =
			dataDefinitionFieldsDeserializerApplyResponse.
				getDataDefinitionFields();

		Assert.assertEquals(
			dataDefinitionFields.toString(), 2, dataDefinitionFields.size());

		DataDefinitionField dataDefinitionField = dataDefinitionFields.get(0);

		Assert.assertEquals(false, dataDefinitionField.isIndexable());
		Assert.assertEquals(true, dataDefinitionField.isLocalizable());
		Assert.assertEquals(false, dataDefinitionField.isRepeatable());
		Assert.assertEquals("name", dataDefinitionField.getName());

		Assert.assertEquals(
			DataDefinitionColumnType.STRING, dataDefinitionField.getType());

		Map<String, String> labels = dataDefinitionField.getLabel();

		Assert.assertEquals("Name", labels.get("en_US"));
		Assert.assertEquals("Nome", labels.get("pt_BR"));

		dataDefinitionField = dataDefinitionFields.get(1);

		Assert.assertEquals(true, dataDefinitionField.isIndexable());
		Assert.assertEquals(false, dataDefinitionField.isLocalizable());
		Assert.assertEquals(true, dataDefinitionField.isRepeatable());
		Assert.assertEquals("email", dataDefinitionField.getName());

		Assert.assertEquals(
			DataDefinitionColumnType.STRING, dataDefinitionField.getType());

		labels = dataDefinitionField.getLabel();

		Assert.assertEquals("Email Address", labels.get("en_US"));
		Assert.assertEquals("Endereço de Email", labels.get("pt_BR"));

		Map<String, String> tips = dataDefinitionField.getTip();

		Assert.assertEquals("Enter an email address", tips.get("en_US"));
		Assert.assertEquals("Informe um endereço de email", tips.get("pt_BR"));
	}

	@Test(expected = DataDefinitionFieldsDeserializerException.class)
	public void testInvalidLabel() throws Exception {
		String json = read(
			"data-definition-fields-deserializer-invalid-label.json");

		DataDefinitionFieldsDeserializerApplyRequest
			dataDefinitionFieldsDeserializerApplyRequest =
				DataDefinitionFieldsDeserializerApplyRequest.Builder.of(json);

		DataDefinitionFieldsJSONDeserializer
			dataDefinitionFieldsJSONDeserializer =
				new DataDefinitionFieldsJSONDeserializer();

		dataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		dataDefinitionFieldsJSONDeserializer.apply(
			dataDefinitionFieldsDeserializerApplyRequest);
	}

	@Test(expected = DataDefinitionFieldsDeserializerException.class)
	public void testInvalidTip() throws Exception {
		String json = read(
			"data-definition-fields-deserializer-invalid-tip.json");

		DataDefinitionFieldsDeserializerApplyRequest
			dataDefinitionFieldsDeserializerApplyRequest =
				DataDefinitionFieldsDeserializerApplyRequest.Builder.of(json);

		DataDefinitionFieldsJSONDeserializer
			dataDefinitionFieldsJSONDeserializer =
				new DataDefinitionFieldsJSONDeserializer();

		dataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		dataDefinitionFieldsJSONDeserializer.apply(
			dataDefinitionFieldsDeserializerApplyRequest);
	}

	@Test(expected = DataDefinitionFieldsDeserializerException.class)
	public void testRequiredName() throws Exception {
		String json = read(
			"data-definition-fields-deserializer-required-name.json");

		DataDefinitionFieldsDeserializerApplyRequest
			dataDefinitionFieldsDeserializerApplyRequest =
				DataDefinitionFieldsDeserializerApplyRequest.Builder.of(json);

		DataDefinitionFieldsJSONDeserializer
			dataDefinitionFieldsJSONDeserializer =
				new DataDefinitionFieldsJSONDeserializer();

		dataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		dataDefinitionFieldsJSONDeserializer.apply(
			dataDefinitionFieldsDeserializerApplyRequest);
	}

	@Test(expected = DataDefinitionFieldsDeserializerException.class)
	public void testRequiredType() throws Exception {
		String json = read(
			"data-definition-fields-deserializer-required-type.json");

		DataDefinitionFieldsDeserializerApplyRequest
			dataDefinitionFieldsDeserializerApplyRequest =
				DataDefinitionFieldsDeserializerApplyRequest.Builder.of(json);

		DataDefinitionFieldsJSONDeserializer
			dataDefinitionFieldsJSONDeserializer =
				new DataDefinitionFieldsJSONDeserializer();

		dataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		dataDefinitionFieldsJSONDeserializer.apply(
			dataDefinitionFieldsDeserializerApplyRequest);
	}

}