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

import com.liferay.data.engine.exception.DEDataDefinitionFieldsDeserializerException;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DEDataDefinitionFieldsJSONDeserializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		String json = read("data-definition-fields-deserializer.json");

		DEDataDefinitionFieldsDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionFieldsDeserializerApplyRequest.Builder.of(json);

		DEDataDefinitionFieldsJSONDeserializer
			deDataDefinitionFieldsJSONDeserializer =
				new DEDataDefinitionFieldsJSONDeserializer();

		deDataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		DEDataDefinitionFieldsDeserializerApplyResponse
			deDataDefinitionFieldsDeserializerApplyResponse =
				deDataDefinitionFieldsJSONDeserializer.apply(
					deDataDefinitionFieldsDeserializerApplyRequest);

		List<DEDataDefinitionField> deDataDefinitionFields =
			deDataDefinitionFieldsDeserializerApplyResponse.
				getDeDataDefinitionFields();

		Assert.assertEquals(
			deDataDefinitionFields.toString(), 2,
			deDataDefinitionFields.size());

		DEDataDefinitionField deDataDefinitionField =
			deDataDefinitionFields.get(0);

		Assert.assertEquals(false, deDataDefinitionField.isIndexable());
		Assert.assertEquals(true, deDataDefinitionField.isLocalizable());
		Assert.assertEquals(false, deDataDefinitionField.isRepeatable());
		Assert.assertEquals("name", deDataDefinitionField.getName());

		Assert.assertEquals("string", deDataDefinitionField.getType());

		Map<String, String> labels = deDataDefinitionField.getLabel();

		Assert.assertEquals("Name", labels.get("en_US"));
		Assert.assertEquals("Nome", labels.get("pt_BR"));

		deDataDefinitionField = deDataDefinitionFields.get(1);

		Assert.assertEquals(true, deDataDefinitionField.isIndexable());
		Assert.assertEquals(false, deDataDefinitionField.isLocalizable());
		Assert.assertEquals(true, deDataDefinitionField.isRepeatable());
		Assert.assertEquals("email", deDataDefinitionField.getName());

		Assert.assertEquals("string", deDataDefinitionField.getType());

		labels = deDataDefinitionField.getLabel();

		Assert.assertEquals("Email Address", labels.get("en_US"));
		Assert.assertEquals("Endereço de Email", labels.get("pt_BR"));

		Map<String, String> tips = deDataDefinitionField.getTip();

		Assert.assertEquals("Enter an email address", tips.get("en_US"));
		Assert.assertEquals("Informe um endereço de email", tips.get("pt_BR"));
	}

	@Test(expected = DEDataDefinitionFieldsDeserializerException.class)
	public void testInvalidLabel() throws Exception {
		String json = read(
			"data-definition-fields-deserializer-invalid-label.json");

		DEDataDefinitionFieldsDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionFieldsDeserializerApplyRequest.Builder.of(json);

		DEDataDefinitionFieldsJSONDeserializer
			deDataDefinitionFieldsJSONDeserializer =
				new DEDataDefinitionFieldsJSONDeserializer();

		deDataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		deDataDefinitionFieldsJSONDeserializer.apply(
			deDataDefinitionFieldsDeserializerApplyRequest);
	}

	@Test(expected = DEDataDefinitionFieldsDeserializerException.class)
	public void testInvalidTip() throws Exception {
		String json = read(
			"data-definition-fields-deserializer-invalid-tip.json");

		DEDataDefinitionFieldsDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionFieldsDeserializerApplyRequest.Builder.of(json);

		DEDataDefinitionFieldsJSONDeserializer
			deDataDefinitionFieldsJSONDeserializer =
				new DEDataDefinitionFieldsJSONDeserializer();

		deDataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		deDataDefinitionFieldsJSONDeserializer.apply(
			deDataDefinitionFieldsDeserializerApplyRequest);
	}

	@Test(expected = DEDataDefinitionFieldsDeserializerException.class)
	public void testRequiredName() throws Exception {
		String json = read(
			"data-definition-fields-deserializer-required-name.json");

		DEDataDefinitionFieldsDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionFieldsDeserializerApplyRequest.Builder.of(json);

		DEDataDefinitionFieldsJSONDeserializer
			deDataDefinitionFieldsJSONDeserializer =
				new DEDataDefinitionFieldsJSONDeserializer();

		deDataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		deDataDefinitionFieldsJSONDeserializer.apply(
			deDataDefinitionFieldsDeserializerApplyRequest);
	}

	@Test(expected = DEDataDefinitionFieldsDeserializerException.class)
	public void testRequiredType() throws Exception {
		String json = read(
			"data-definition-fields-deserializer-required-type.json");

		DEDataDefinitionFieldsDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionFieldsDeserializerApplyRequest.Builder.of(json);

		DEDataDefinitionFieldsJSONDeserializer
			deDataDefinitionFieldsJSONDeserializer =
				new DEDataDefinitionFieldsJSONDeserializer();

		deDataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		deDataDefinitionFieldsJSONDeserializer.apply(
			deDataDefinitionFieldsDeserializerApplyRequest);
	}

}