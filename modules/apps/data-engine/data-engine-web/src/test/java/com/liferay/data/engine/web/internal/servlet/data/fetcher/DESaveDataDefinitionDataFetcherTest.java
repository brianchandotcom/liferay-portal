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
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinitionFieldType;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinitionType;
import com.liferay.data.engine.web.internal.graphql.model.LocalizedValueType;
import com.liferay.data.engine.web.internal.graphql.model.SaveDataDefinitionType;

import graphql.schema.DataFetchingEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class DESaveDataDefinitionDataFetcherTest {

	@Test
	public void testCreateDataDefinitionFields() {
		List<Map<String, Object>> labelList1 = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Name");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Nome");
						}
					});
			}
		};

		List<Map<String, Object>> tipList1 = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Enter a name");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Entre com o nome");
						}
					});
			}
		};

		Map<String, Object> fieldProperties1 = new TreeMap() {
			{
				put("name", "name");
				put("type", "text");
				put("defaultValue", "Default Name");
				put("indexable", true);
				put("labels", labelList1);
				put("localizable", false);
				put("repeatable", false);
				put("required", true);
				put("tips", tipList1);
			}
		};

		List<Map<String, Object>> labelList2 = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Description");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Descrição");
						}
					});
			}
		};

		List<Map<String, Object>> tipList2 = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Enter a description");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Entre com a descrição");
						}
					});
			}
		};

		Map<String, Object> fieldProperties2 = new TreeMap() {
			{
				put("name", "description");
				put("type", "text");
				put("defaultValue", "Default Description");
				put("indexable", true);
				put("labels", labelList2);
				put("localizable", true);
				put("repeatable", true);
				put("required", true);
				put("tips", tipList2);
			}
		};

		DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher =
			new DESaveDataDefinitionDataFetcher();

		List<DEDataDefinitionField> deDataDefinitionFields =
			deSaveDataDefinitionDataFetcher.createDEDataDefinitionFields(
				Arrays.asList(fieldProperties1, fieldProperties2));

		DEDataDefinitionField expectedDEDataDefinitionField1 =
			new DEDataDefinitionField("name", "text");

		expectedDEDataDefinitionField1.setDefaultValue("Default Name");
		expectedDEDataDefinitionField1.setIndexable(true);
		expectedDEDataDefinitionField1.setLabel(
			new TreeMap() {
				{
					put("en_US", "Name");
					put("pt_BR", "Nome");
				}
			});
		expectedDEDataDefinitionField1.setLocalizable(false);
		expectedDEDataDefinitionField1.setRepeatable(false);
		expectedDEDataDefinitionField1.setRequired(true);
		expectedDEDataDefinitionField1.setTip(
			new TreeMap() {
				{
					put("en_US", "Enter a name");
					put("pt_BR", "Entre com o nome");
				}
			});

		Assert.assertEquals(
			expectedDEDataDefinitionField1, deDataDefinitionFields.get(0));

		DEDataDefinitionField expectedDEDataDefinitionField2 =
			new DEDataDefinitionField("description", "text");

		expectedDEDataDefinitionField2.setDefaultValue("Default Description");
		expectedDEDataDefinitionField2.setIndexable(true);
		expectedDEDataDefinitionField2.setLabel(
			new TreeMap() {
				{
					put("en_US", "Description");
					put("pt_BR", "Descrição");
				}
			});
		expectedDEDataDefinitionField2.setLocalizable(true);
		expectedDEDataDefinitionField2.setRepeatable(true);
		expectedDEDataDefinitionField2.setRequired(true);
		expectedDEDataDefinitionField2.setTip(
			new TreeMap() {
				{
					put("en_US", "Enter a description");
					put("pt_BR", "Entre com a descrição");
				}
			});

		Assert.assertEquals(
			expectedDEDataDefinitionField2, deDataDefinitionFields.get(1));
	}

	@Test
	public void testCreateDataDefinitionFieldsType() {
		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("field1", "text");

		deDataDefinitionField1.setDefaultValue("Nothing 1");
		deDataDefinitionField1.setIndexable(false);
		deDataDefinitionField1.setLabel(
			new TreeMap() {
				{
					put("en_US", "Field 1");
					put("pt_BR", "Campo 1");
				}
			});
		deDataDefinitionField1.setLocalizable(true);
		deDataDefinitionField1.setRepeatable(false);
		deDataDefinitionField1.setRequired(true);

		DEDataDefinitionField deDataDefinitionField2 =
			new DEDataDefinitionField("field2", "numeric");

		deDataDefinitionField2.setDefaultValue("Nothing 2");
		deDataDefinitionField2.setIndexable(true);
		deDataDefinitionField2.setLabel(
			new TreeMap() {
				{
					put("en_US", "Field 2");
					put("pt_BR", "Campo 2");
				}
			});
		deDataDefinitionField2.setLocalizable(false);
		deDataDefinitionField2.setRepeatable(true);
		deDataDefinitionField2.setRequired(false);

		List<DEDataDefinitionField> deDataDefinitionFields = Arrays.asList(
			deDataDefinitionField1, deDataDefinitionField2);

		DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher =
			new DESaveDataDefinitionDataFetcher();

		List<DataDefinitionFieldType> dataDefinitionFieldTypes =
			deSaveDataDefinitionDataFetcher.createDataDefinitionFieldTypes(
				deDataDefinitionFields);

		DataDefinitionFieldType dataDefinitionFieldType =
			dataDefinitionFieldTypes.get(0);

		Assert.assertEquals("field1", dataDefinitionFieldType.getName());
		Assert.assertEquals("text", dataDefinitionFieldType.getType());
		Assert.assertEquals(
			"Nothing 1", dataDefinitionFieldType.getDefaultValue());
		Assert.assertFalse(dataDefinitionFieldType.isIndexable());
		Assert.assertTrue(dataDefinitionFieldType.isLocalizable());
		Assert.assertFalse(dataDefinitionFieldType.isRepeatable());
		Assert.assertTrue(dataDefinitionFieldType.isRequired());

		List<LocalizedValueType> labels = dataDefinitionFieldType.getLabels();

		LocalizedValueType localizedValueType = labels.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Field 1", localizedValueType.getValue());

		localizedValueType = labels.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals("Campo 1", localizedValueType.getValue());

		dataDefinitionFieldType = dataDefinitionFieldTypes.get(1);

		Assert.assertEquals("field2", dataDefinitionFieldType.getName());
		Assert.assertEquals("numeric", dataDefinitionFieldType.getType());
		Assert.assertEquals(
			"Nothing 2", dataDefinitionFieldType.getDefaultValue());
		Assert.assertTrue(dataDefinitionFieldType.isIndexable());
		Assert.assertFalse(dataDefinitionFieldType.isLocalizable());
		Assert.assertTrue(dataDefinitionFieldType.isRepeatable());
		Assert.assertFalse(dataDefinitionFieldType.isRequired());

		labels = dataDefinitionFieldType.getLabels();

		localizedValueType = labels.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Field 2", localizedValueType.getValue());

		localizedValueType = labels.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals("Campo 2", localizedValueType.getValue());
	}

	@Test
	public void testCreateDataDefinitionType() {
		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("field1", "text");

		deDataDefinitionField1.setDefaultValue("Nothing 1");
		deDataDefinitionField1.setIndexable(false);
		deDataDefinitionField1.setLabel(
			new TreeMap() {
				{
					put("en_US", "Field 1");
					put("pt_BR", "Campo 1");
				}
			});
		deDataDefinitionField1.setLocalizable(true);
		deDataDefinitionField1.setRepeatable(false);
		deDataDefinitionField1.setRequired(true);

		DEDataDefinitionField deDataDefinitionField2 =
			new DEDataDefinitionField("field2", "numeric");

		deDataDefinitionField2.setDefaultValue("Nothing 2");
		deDataDefinitionField2.setIndexable(true);
		deDataDefinitionField2.setLabel(
			new TreeMap() {
				{
					put("en_US", "Field 2");
					put("pt_BR", "Campo 2");
				}
			});
		deDataDefinitionField2.setLocalizable(false);
		deDataDefinitionField2.setRepeatable(true);
		deDataDefinitionField2.setRequired(false);

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
		deDataDefinition.setDescription(
			new TreeMap() {
				{
					put("en_US", "Description");
					put("pt_BR", "Descrição");
				}
			});

		DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher =
			new DESaveDataDefinitionDataFetcher();

		DataDefinitionType dataDefinitionType =
			deSaveDataDefinitionDataFetcher.createDataDefinitionType(
				1, deDataDefinition);

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

		List<LocalizedValueType> descriptions =
			dataDefinitionType.getDescriptions();

		localizedValueType = descriptions.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Description", localizedValueType.getValue());

		localizedValueType = descriptions.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals("Descrição", localizedValueType.getValue());

		List<DataDefinitionFieldType> fields = dataDefinitionType.getFields();

		DataDefinitionFieldType dataDefinitionFieldType = fields.get(0);

		Assert.assertEquals("field1", dataDefinitionFieldType.getName());
		Assert.assertEquals("text", dataDefinitionFieldType.getType());
		Assert.assertEquals(
			"Nothing 1", dataDefinitionFieldType.getDefaultValue());
		Assert.assertFalse(dataDefinitionFieldType.isIndexable());
		Assert.assertTrue(dataDefinitionFieldType.isLocalizable());
		Assert.assertFalse(dataDefinitionFieldType.isRepeatable());
		Assert.assertTrue(dataDefinitionFieldType.isRequired());

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
		Assert.assertEquals(
			"Nothing 2", dataDefinitionFieldType.getDefaultValue());
		Assert.assertTrue(dataDefinitionFieldType.isIndexable());
		Assert.assertFalse(dataDefinitionFieldType.isLocalizable());
		Assert.assertTrue(dataDefinitionFieldType.isRepeatable());
		Assert.assertFalse(dataDefinitionFieldType.isRequired());

		labels = dataDefinitionFieldType.getLabels();

		localizedValueType = labels.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Field 2", localizedValueType.getValue());

		localizedValueType = labels.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals("Campo 2", localizedValueType.getValue());
	}

	@Test
	public void testGet() throws Exception {
		DataFetchingEnvironment dataFetchingEnvironment = Mockito.mock(
			DataFetchingEnvironment.class);

		Mockito.when(
			dataFetchingEnvironment.getArgument("userId")
		).thenReturn(
			"2"
		);

		Mockito.when(
			dataFetchingEnvironment.getArgument("groupId")
		).thenReturn(
			"1"
		);

		List<Map<String, Object>> names = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Data Definition");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Definição de Dados");
						}
					});
			}
		};

		List<Map<String, Object>> descriptions = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Data Definition Description");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Descrição da Definição de Dados");
						}
					});
			}
		};

		List<Map<String, Object>> labels1 = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Contact");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Contato");
						}
					});
			}
		};

		List<Map<String, Object>> tips1 = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Enter a contact");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Entre com o contato");
						}
					});
			}
		};

		Map<String, Object> fieldProperties1 = new TreeMap() {
			{
				put("name", "contact");
				put("type", "text");
				put("defaultValue", "Default Contact");
				put("indexable", true);
				put("labels", labels1);
				put("localizable", false);
				put("repeatable", false);
				put("required", false);
				put("tips", tips1);
			}
		};

		List<Map<String, Object>> labels2 = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Address");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Endereço");
						}
					});
			}
		};

		List<Map<String, Object>> tips2 = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Enter an address");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Entre com o endereço");
						}
					});
			}
		};

		Map<String, Object> fieldProperties2 = new TreeMap() {
			{
				put("name", "address");
				put("type", "text");
				put("defaultValue", "Default Address");
				put("indexable", true);
				put("labels", labels2);
				put("localizable", true);
				put("repeatable", true);
				put("required", true);
				put("tips", tips2);
			}
		};

		List<Map<String, Object>> fields = Arrays.asList(
			fieldProperties1, fieldProperties2);

		Map<String, Object> dataDefinition = new TreeMap() {
			{
				put("descriptions", descriptions);
				put("fields", fields);
				put("names", names);
				put("storageType", "xml");
			}
		};

		Mockito.when(
			dataFetchingEnvironment.getArgument("dataDefinition")
		).thenReturn(
			dataDefinition
		);

		DEDataDefinitionService deDataDefinitionService = Mockito.mock(
			DEDataDefinitionService.class);

		DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
			DEDataDefinitionSaveResponse.Builder.newBuilder(
				1
			).build();

		Mockito.when(
			deDataDefinitionService.execute(
				Matchers.any(DEDataDefinitionSaveRequest.class))
		).thenReturn(
			deDataDefinitionSaveResponse
		);

		DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher =
			new DESaveDataDefinitionDataFetcher();

		deSaveDataDefinitionDataFetcher.deDataDefinitionService =
			deDataDefinitionService;

		SaveDataDefinitionType saveDataDefinitionType =
			deSaveDataDefinitionDataFetcher.get(dataFetchingEnvironment);

		DataDefinitionType dataDefinitionType =
			saveDataDefinitionType.getDataDefinition();

		Assert.assertEquals("1", dataDefinitionType.getDataDefinitionId());
		Assert.assertEquals("xml", dataDefinitionType.getStorageType());

		List<LocalizedValueType> nameList = dataDefinitionType.getNames();

		LocalizedValueType localizedValueType = nameList.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Data Definition", localizedValueType.getValue());

		localizedValueType = nameList.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals(
			"Definição de Dados", localizedValueType.getValue());

		List<LocalizedValueType> descriptionList =
			dataDefinitionType.getDescriptions();

		localizedValueType = descriptionList.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals(
			"Data Definition Description", localizedValueType.getValue());

		localizedValueType = descriptionList.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals(
			"Descrição da Definição de Dados", localizedValueType.getValue());

		List<DataDefinitionFieldType> fieldList =
			dataDefinitionType.getFields();

		DataDefinitionFieldType dataDefinitionFieldType = fieldList.get(0);

		Assert.assertEquals("contact", dataDefinitionFieldType.getName());
		Assert.assertEquals("text", dataDefinitionFieldType.getType());
		Assert.assertEquals(
			"Default Contact", dataDefinitionFieldType.getDefaultValue());
		Assert.assertTrue(dataDefinitionFieldType.isIndexable());
		Assert.assertFalse(dataDefinitionFieldType.isLocalizable());
		Assert.assertFalse(dataDefinitionFieldType.isRepeatable());
		Assert.assertFalse(dataDefinitionFieldType.isRequired());

		List<LocalizedValueType> labelList =
			dataDefinitionFieldType.getLabels();

		localizedValueType = labelList.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Contact", localizedValueType.getValue());

		localizedValueType = labelList.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals("Contato", localizedValueType.getValue());

		List<LocalizedValueType> tipList = dataDefinitionFieldType.getTips();

		localizedValueType = tipList.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Enter a contact", localizedValueType.getValue());

		localizedValueType = tipList.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals(
			"Entre com o contato", localizedValueType.getValue());

		dataDefinitionFieldType = fieldList.get(1);

		Assert.assertEquals("address", dataDefinitionFieldType.getName());
		Assert.assertEquals("text", dataDefinitionFieldType.getType());
		Assert.assertEquals(
			"Default Address", dataDefinitionFieldType.getDefaultValue());
		Assert.assertTrue(dataDefinitionFieldType.isIndexable());
		Assert.assertTrue(dataDefinitionFieldType.isLocalizable());
		Assert.assertTrue(dataDefinitionFieldType.isRepeatable());
		Assert.assertTrue(dataDefinitionFieldType.isRequired());

		labelList = dataDefinitionFieldType.getLabels();

		localizedValueType = labelList.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Address", localizedValueType.getValue());

		localizedValueType = labelList.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals("Endereço", localizedValueType.getValue());

		tipList = dataDefinitionFieldType.getTips();

		localizedValueType = tipList.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Enter an address", localizedValueType.getValue());

		localizedValueType = tipList.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals(
			"Entre com o endereço", localizedValueType.getValue());
	}

	@Test
	public void testGetLocalizedValues() {
		List<Map<String, Object>> values = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Name");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Nome");
						}
					});
			}
		};

		DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher =
			new DESaveDataDefinitionDataFetcher();

		Map<String, String> localizedValues =
			deSaveDataDefinitionDataFetcher.getLocalizedValues(values);

		Assert.assertEquals("Name", localizedValues.get("en_US"));
		Assert.assertEquals("Nome", localizedValues.get("pt_BR"));
	}

	@Test
	public void testGetLocalizedValuesType() {
		Map<String, String> values = new TreeMap() {
			{
				put("en_US", "English");
				put("pt_BR", "Português");
			}
		};

		DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher =
			new DESaveDataDefinitionDataFetcher();

		List<LocalizedValueType> localizedValueTypes =
			deSaveDataDefinitionDataFetcher.getLocalizedValuesType(values);

		LocalizedValueType localizedValueType = localizedValueTypes.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("English", localizedValueType.getValue());

		localizedValueType = localizedValueTypes.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals("Português", localizedValueType.getValue());
	}

	@Test
	public void testMapDEDataDefinitionField() {
		DEDataDefinitionField deDataDefinitionField = new DEDataDefinitionField(
			"description", "text");

		deDataDefinitionField.setDefaultValue("Nothing");
		deDataDefinitionField.setIndexable(false);
		deDataDefinitionField.setLabel(
			new TreeMap() {
				{
					put("en_US", "Description");
					put("pt_BR", "Descrição");
				}
			});
		deDataDefinitionField.setLocalizable(true);
		deDataDefinitionField.setRepeatable(false);
		deDataDefinitionField.setRequired(true);
		deDataDefinitionField.setTip(
			new TreeMap() {
				{
					put("en_US", "Enter a description");
					put("pt_BR", "Entre com a descrição");
				}
			});

		DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher =
			new DESaveDataDefinitionDataFetcher();

		DataDefinitionFieldType dataDefinitionFieldType =
			deSaveDataDefinitionDataFetcher.map(deDataDefinitionField);

		Assert.assertEquals("description", dataDefinitionFieldType.getName());
		Assert.assertEquals("text", dataDefinitionFieldType.getType());
		Assert.assertEquals(
			"Nothing", dataDefinitionFieldType.getDefaultValue());
		Assert.assertFalse(dataDefinitionFieldType.isIndexable());
		Assert.assertTrue(dataDefinitionFieldType.isLocalizable());
		Assert.assertFalse(dataDefinitionFieldType.isRepeatable());
		Assert.assertTrue(dataDefinitionFieldType.isRequired());

		List<LocalizedValueType> labels = dataDefinitionFieldType.getLabels();

		LocalizedValueType localizedValueType1 = labels.get(0);

		Assert.assertEquals("en_US", localizedValueType1.getKey());
		Assert.assertEquals("Description", localizedValueType1.getValue());

		localizedValueType1 = labels.get(1);

		Assert.assertEquals("pt_BR", localizedValueType1.getKey());
		Assert.assertEquals("Descrição", localizedValueType1.getValue());

		List<LocalizedValueType> tips = dataDefinitionFieldType.getTips();

		LocalizedValueType localizedValueType2 = tips.get(0);

		Assert.assertEquals("en_US", localizedValueType2.getKey());
		Assert.assertEquals(
			"Enter a description", localizedValueType2.getValue());

		localizedValueType2 = tips.get(1);

		Assert.assertEquals("pt_BR", localizedValueType2.getKey());
		Assert.assertEquals(
			"Entre com a descrição", localizedValueType2.getValue());
	}

	@Test
	public void testMapFieldProperties() {
		List<Map<String, Object>> labelList = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Name");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Nome");
						}
					});
			}
		};

		List<Map<String, Object>> tipList = new ArrayList() {
			{
				add(
					new TreeMap() {
						{
							put("key", "en_US");
							put("value", "Enter a name");
						}
					});

				add(
					new TreeMap() {
						{
							put("key", "pt_BR");
							put("value", "Entre com o nome");
						}
					});
			}
		};

		Map<String, Object> fieldProperties = new TreeMap() {
			{
				put("name", "name");
				put("type", "text");
				put("defaultValue", "Default Name");
				put("indexable", true);
				put("labels", labelList);
				put("localizable", false);
				put("repeatable", false);
				put("required", true);
				put("tips", tipList);
			}
		};

		DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher =
			new DESaveDataDefinitionDataFetcher();

		DEDataDefinitionField deDataDefinitionField =
			deSaveDataDefinitionDataFetcher.map(fieldProperties);

		Assert.assertEquals("name", deDataDefinitionField.getName());
		Assert.assertEquals("text", deDataDefinitionField.getType());
		Assert.assertEquals(
			"Default Name", deDataDefinitionField.getDefaultValue());
		Assert.assertTrue(deDataDefinitionField.isIndexable());
		Assert.assertFalse(deDataDefinitionField.isLocalizable());
		Assert.assertFalse(deDataDefinitionField.isRepeatable());
		Assert.assertTrue(deDataDefinitionField.isRequired());

		Map<String, String> labels = deDataDefinitionField.getLabel();

		Assert.assertEquals("Name", labels.get("en_US"));
		Assert.assertEquals("Nome", labels.get("pt_BR"));

		Map<String, String> tips = deDataDefinitionField.getTip();

		Assert.assertEquals("Enter a name", tips.get("en_US"));
		Assert.assertEquals("Entre com o nome", tips.get("pt_BR"));
	}

	@Test
	public void testMapLocalizedValue() {
		Map<String, String> localizedValues = new TreeMap() {
			{
				put("en_US", "Contact");
				put("pt_BR", "Contato");
			}
		};

		DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher =
			new DESaveDataDefinitionDataFetcher();

		Set<Map.Entry<String, String>> entries = localizedValues.entrySet();

		Stream<Map.Entry<String, String>> stream = entries.stream();

		List<LocalizedValueType> localizedValueTypes = stream.map(
			deSaveDataDefinitionDataFetcher::map
		).collect(
			Collectors.toList()
		);

		LocalizedValueType localizedValueType = localizedValueTypes.get(0);

		Assert.assertEquals("en_US", localizedValueType.getKey());
		Assert.assertEquals("Contact", localizedValueType.getValue());

		localizedValueType = localizedValueTypes.get(1);

		Assert.assertEquals("pt_BR", localizedValueType.getKey());
		Assert.assertEquals("Contato", localizedValueType.getValue());
	}

}