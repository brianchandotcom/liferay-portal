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

package com.liferay.data.engine.web.internal.servlet.data.fetcher;

import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinitionFieldType;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinitionType;
import com.liferay.data.engine.web.internal.graphql.model.GetDataDefinitionType;
import com.liferay.data.engine.web.internal.graphql.model.LocalizedValueType;

import graphql.schema.DataFetchingEnvironment;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class DEGetDataDefinitionDataFetcherTest {

	@Test
	public void testGet() throws Exception {
		DataFetchingEnvironment dataFetchingEnvironment = Mockito.mock(
			DataFetchingEnvironment.class);

		Mockito.when(
			dataFetchingEnvironment.getArgument("dataDefinitionId")
		).thenReturn(
			"1"
		);

		DEDataDefinitionService deDataDefinitionService = Mockito.mock(
			DEDataDefinitionService.class);

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("field1", "text");

		deDataDefinitionField1.setIndexable(false);
		deDataDefinitionField1.setLabel(
			new TreeMap() {
				{
					put("en_US", "Field 1");
					put("pt_BR", "Campo 1");
				}
			});
		deDataDefinitionField1.setLocalizable(false);
		deDataDefinitionField1.setRepeatable(false);
		deDataDefinitionField1.setRequired(false);

		DEDataDefinitionField deDataDefinitionField2 =
			new DEDataDefinitionField("field2", "numeric");

		deDataDefinitionField2.setIndexable(true);
		deDataDefinitionField2.setLabel(
			new TreeMap() {
				{
					put("en_US", "Field 2");
					put("pt_BR", "Campo 2");
				}
			});
		deDataDefinitionField2.setLocalizable(true);
		deDataDefinitionField2.setRepeatable(true);
		deDataDefinitionField2.setRequired(true);

		List<DEDataDefinitionField> deDataDefinitionFields = Arrays.asList(
			deDataDefinitionField1, deDataDefinitionField2);

		DEDataDefinition deDataDefinition = new DEDataDefinition(
			deDataDefinitionFields);

		deDataDefinition.setName(
			new TreeMap() {
				{
					put("en_US", "Data Definition");
					put("pt_BR", "Definição de Dados");
				}
			});

		DEDataDefinitionGetResponse deDataDefinitionGetResponse =
			DEDataDefinitionGetResponse.Builder.newBuilder(
				deDataDefinition
			).build();

		Mockito.when(
			deDataDefinitionService.execute(
				Matchers.any(DEDataDefinitionGetRequest.class))
		).thenReturn(
			deDataDefinitionGetResponse
		);

		DEGetDataDefinitionDataFetcher deGetDataDefinitionDataFetcher =
			new DEGetDataDefinitionDataFetcher();

		deGetDataDefinitionDataFetcher.deDataDefinitionService =
			deDataDefinitionService;

		GetDataDefinitionType getDataDefinitionType =
			deGetDataDefinitionDataFetcher.get(dataFetchingEnvironment);

		DataDefinitionType dataDefinitionType =
			getDataDefinitionType.getDataDefinition();

		Assert.assertEquals("1", dataDefinitionType.getDataDefinitionId());
		Assert.assertEquals("json", dataDefinitionType.getStorageType());

		List<LocalizedValueType> names = dataDefinitionType.getNames();

		LocalizedValueType localizedValueType = names.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Data Definition", localizedValueType.getValue());

		localizedValueType = names.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals(
			"Definição de Dados", localizedValueType.getValue());

		List<DataDefinitionFieldType> fields = dataDefinitionType.getFields();

		DataDefinitionFieldType dataDefinitionFieldType = fields.get(0);

		Assert.assertEquals("field1", dataDefinitionFieldType.getName());
		Assert.assertEquals("text", dataDefinitionFieldType.getType());
		Assert.assertFalse(dataDefinitionFieldType.isIndexable());
		Assert.assertFalse(dataDefinitionFieldType.isLocalizable());
		Assert.assertFalse(dataDefinitionFieldType.isRepeatable());
		Assert.assertFalse(dataDefinitionFieldType.isRequired());

		List<LocalizedValueType> labels = dataDefinitionFieldType.getLabels();

		localizedValueType = labels.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Field 1", localizedValueType.getValue());

		localizedValueType = labels.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals("Campo 1", localizedValueType.getValue());

		dataDefinitionFieldType = fields.get(1);

		Assert.assertEquals("field2", dataDefinitionFieldType.getName());
		Assert.assertEquals("numeric", dataDefinitionFieldType.getType());
		Assert.assertTrue(dataDefinitionFieldType.isIndexable());
		Assert.assertTrue(dataDefinitionFieldType.isLocalizable());
		Assert.assertTrue(dataDefinitionFieldType.isRepeatable());
		Assert.assertTrue(dataDefinitionFieldType.isRequired());

		labels = dataDefinitionFieldType.getLabels();

		localizedValueType = labels.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Field 2", localizedValueType.getValue());

		localizedValueType = labels.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals("Campo 2", localizedValueType.getValue());
	}

}