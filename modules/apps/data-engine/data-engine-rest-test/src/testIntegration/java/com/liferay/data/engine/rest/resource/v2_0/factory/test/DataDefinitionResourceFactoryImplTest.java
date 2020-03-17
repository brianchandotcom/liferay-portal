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

package com.liferay.data.engine.rest.resource.v2_0.factory.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.dto.v2_0.builder.DataDefinitionBuilder;
import com.liferay.data.engine.rest.dto.v2_0.builder.DataEngineBuilderFactory;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.ClientErrorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class DataDefinitionResourceFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAcceptLanguage1() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).httpServletRequest(
				new MockHttpServletRequest() {

					@Override
					public String getHeader(String name) {
						if (StringUtil.equals(name, "Accept-Language")) {
							return "pt-BR";
						}

						return super.getHeader(name);
					}

				}
			).user(
				_user
			).build();

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			dataDefinitionResource.
				getDataDefinitionDataDefinitionFieldFieldTypes());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Assert.assertEquals(
				"pt_BR",
				JSONUtil.getValue(
					jsonObject, "JSONObject/settingsContext", "JSONArray/pages",
					"Object/0", "JSONArray/rows", "Object/0",
					"JSONArray/columns", "Object/0", "JSONArray/fields",
					"Object/0", "Object/locale"));
		}
	}

	@Test(expected = ClientErrorException.class)
	public void testAcceptLanguage2() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).httpServletRequest(
				new MockHttpServletRequest() {

					@Override
					public String getHeader(String name) {
						if (StringUtil.equals(name, "Accept-Language")) {
							return "INVALID";
						}

						return super.getHeader(name);
					}

				}
			).user(
				_user
			).build();

		dataDefinitionResource.getDataDefinitionDataDefinitionFieldFieldTypes();
	}

	@Test(expected = PortalException.class)
	public void testDeleteDataDefinition() throws Exception {
		String dataDefinitionKey = StringUtil.randomString(6);

		DataDefinition dataDefinition = _postDataDefinition(
			dataDefinitionKey,
			HashMapBuilder.<String, Object>put(
				"en_US", "description"
			).put(
				"pt_BR", "descrição"
			).build(),
			new String[] {"text1"},
			HashMapBuilder.<String, Object>put(
				"en_US", "name"
			).put(
				"pt_BR", "nome"
			).build(),
			new LocalizedValue() {
				{
					addString(LocaleUtil.US, "Page Description");
					addString(LocaleUtil.BRAZIL, "Descrição Página");
				}
			},
			new LocalizedValue() {
				{
					addString(LocaleUtil.US, "Page Title");
					addString(LocaleUtil.BRAZIL, "Título Página");
				}
			});

		DataDefinitionResource dataDefinitionResource =
			_getDataDefinitionResource();

		dataDefinitionResource.deleteDataDefinition(dataDefinition.getId());

		dataDefinitionResource.getDataDefinition(dataDefinition.getId());
	}

	@Test(expected = NullPointerException.class)
	public void testHttpServletRequest() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).user(
				_user
			).build();

		dataDefinitionResource.getDataDefinitionDataDefinitionFieldFieldTypes();
	}

	@Test
	public void testPostDataDefinition() throws Exception {
		String dataDefinitionKey = StringUtil.randomString(6);

		Map<String, Object> description = HashMapBuilder.<String, Object>put(
			"en_US", "description"
		).put(
			"pt_BR", "descrição"
		).build();

		String[] fieldNames = {"text1", "text2"};

		Map<String, Object> name = HashMapBuilder.<String, Object>put(
			"en_US", "name"
		).put(
			"pt_BR", "nome"
		).build();

		LocalizedValue pageDescription = new LocalizedValue() {
			{
				addString(LocaleUtil.US, "Page Description");
				addString(LocaleUtil.BRAZIL, "Descrição Página");
			}
		};
		LocalizedValue pageTitle = new LocalizedValue() {
			{
				addString(LocaleUtil.US, "Page");
				addString(LocaleUtil.BRAZIL, "Página");
			}
		};

		DataDefinition dataDefinition = _postDataDefinition(
			dataDefinitionKey, description, fieldNames, name, pageDescription,
			pageTitle);

		Assert.assertArrayEquals(
			new String[] {"en_US", "pt_BR"},
			dataDefinition.getAvailableLanguageIds());
		Assert.assertEquals(
			StringUtil.toUpperCase(dataDefinitionKey),
			dataDefinition.getDataDefinitionKey());
		Assert.assertEquals(
			LanguageUtil.getLanguageId(LocaleUtil.BRAZIL),
			dataDefinition.getDefaultLanguageId());
		Assert.assertEquals(description, dataDefinition.getDescription());
		Assert.assertNotNull(dataDefinition.getId());
		Assert.assertEquals(name, dataDefinition.getName());

		DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

		Assert.assertNotNull(dataLayout);
		Assert.assertEquals(
			dataDefinition.getDataDefinitionKey(),
			dataLayout.getDataLayoutKey());
		Assert.assertNotNull(dataLayout.getId());
		Assert.assertEquals(name, dataLayout.getName());
		Assert.assertEquals(
			DDMFormLayout.SINGLE_PAGE_MODE, dataLayout.getPaginationMode());

		DataLayoutPage[] dataLayoutPages = dataLayout.getDataLayoutPages();

		Assert.assertEquals(
			Arrays.toString(dataLayoutPages), 1, dataLayoutPages.length);
		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"en_US", "Page"
			).put(
				"pt_BR", "Página"
			).build(),
			dataLayoutPages[0].getTitle());
		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"en_US", "Page Description"
			).put(
				"pt_BR", "Descrição Página"
			).build(),
			dataLayoutPages[0].getDescription());

		DataLayoutRow[] dataLayoutRows = dataLayoutPages[0].getDataLayoutRows();

		Assert.assertEquals(
			Arrays.toString(dataLayoutRows), 1, dataLayoutRows.length);

		DataLayoutColumn[] dataLayoutColumns =
			dataLayoutRows[0].getDataLayoutColumns();

		Assert.assertEquals(
			Arrays.toString(dataLayoutColumns), 1, dataLayoutColumns.length);
		Assert.assertEquals(
			Integer.valueOf(12), dataLayoutColumns[0].getColumnSize());
		Assert.assertArrayEquals(
			fieldNames, dataLayoutColumns[0].getFieldNames());
	}

	@Test
	public void testPutDataDefinition() throws Exception {
		String dataDefinitionKey = StringUtil.randomString(6);

		Map<String, Object> description = HashMapBuilder.<String, Object>put(
			"en_US", "description"
		).put(
			"pt_BR", "descrição"
		).build();

		String[] fieldNames = {"text1", "text2"};

		Map<String, Object> name = HashMapBuilder.<String, Object>put(
			"en_US", "name"
		).put(
			"pt_BR", "nome"
		).build();

		DataDefinition dataDefinition = _postDataDefinition(
			dataDefinitionKey, description, fieldNames, name,
			new LocalizedValue() {
				{
					addString(LocaleUtil.US, "Page Description");
					addString(LocaleUtil.BRAZIL, "Descrição Página");
				}
			},
			new LocalizedValue() {
				{
					addString(LocaleUtil.US, "Page");
					addString(LocaleUtil.BRAZIL, "Página");
				}
			});

		dataDefinition.setDescription(
			HashMapBuilder.<String, Object>put(
				"en_US", "description updated"
			).put(
				"pt_BR", "descrição alterada"
			).build());

		dataDefinition.setName(
			HashMapBuilder.<String, Object>put(
				"en_US", "name updated"
			).put(
				"pt_BR", "nome alterado"
			).build());

		dataDefinition.setDataDefinitionFields(
			new DataDefinitionField[] {
				new DataDefinitionField() {
					{
						setFieldType("text");
						setLabel(
							HashMapBuilder.<String, Object>put(
								"en_US", "New Field"
							).put(
								"pt_BR", "Novo Campo"
							).build());
						setName("newField");
					}
				}
			});

		DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

		dataLayout.setDescription(
			HashMapBuilder.<String, Object>put(
				"en_US", "description updated"
			).put(
				"pt_BR", "descrição alterada"
			).build());

		dataLayout.setName(
			HashMapBuilder.<String, Object>put(
				"en_US", "name updated"
			).put(
				"pt_BR", "nome alterado"
			).build());

		DataLayoutPage[] dataLayoutPages = dataLayout.getDataLayoutPages();

		dataLayoutPages[0].setDescription(
			HashMapBuilder.<String, Object>put(
				"en_US", "Page Description updated"
			).put(
				"pt_BR", "Descrição Página alterada"
			).build());

		dataLayoutPages[0].setTitle(
			HashMapBuilder.<String, Object>put(
				"en_US", "Page updated"
			).put(
				"pt_BR", "Página alterada"
			).build());

		DataLayoutRow[] dataLayoutRows = dataLayoutPages[0].getDataLayoutRows();

		dataLayoutRows[0].setDataLayoutColumns(
			new DataLayoutColumn[] {
				new DataLayoutColumn() {
					{
						setColumnSize(12);
						setFieldNames(new String[] {"newField"});
					}
				}
			});

		DataDefinitionResource dataDefinitionResource =
			_getDataDefinitionResource();

		dataDefinition = dataDefinitionResource.putDataDefinition(
			dataDefinition.getId(), dataDefinition);

		Assert.assertArrayEquals(
			new String[] {"en_US", "pt_BR"},
			dataDefinition.getAvailableLanguageIds());
		Assert.assertEquals(
			StringUtil.toUpperCase(dataDefinitionKey),
			dataDefinition.getDataDefinitionKey());
		Assert.assertEquals(
			LanguageUtil.getLanguageId(LocaleUtil.BRAZIL),
			dataDefinition.getDefaultLanguageId());
		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"en_US", "description updated"
			).put(
				"pt_BR", "descrição alterada"
			).build(),
			dataDefinition.getDescription());
		Assert.assertNotNull(dataDefinition.getId());
		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"en_US", "name updated"
			).put(
				"pt_BR", "nome alterado"
			).build(),
			dataDefinition.getName());

		dataLayout = dataDefinition.getDefaultDataLayout();

		Assert.assertNotNull(dataLayout);
		Assert.assertEquals(
			dataDefinition.getDataDefinitionKey(),
			dataLayout.getDataLayoutKey());
		Assert.assertNotNull(dataLayout.getId());
		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"en_US", "name updated"
			).put(
				"pt_BR", "nome alterado"
			).build(),
			dataLayout.getName());
		Assert.assertEquals(
			DDMFormLayout.SINGLE_PAGE_MODE, dataLayout.getPaginationMode());

		dataLayoutPages = dataLayout.getDataLayoutPages();

		Assert.assertEquals(
			Arrays.toString(dataLayoutPages), 1, dataLayoutPages.length);
		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"en_US", "Page updated"
			).put(
				"pt_BR", "Página alterada"
			).build(),
			dataLayoutPages[0].getTitle());
		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"en_US", "Page Description updated"
			).put(
				"pt_BR", "Descrição Página alterada"
			).build(),
			dataLayoutPages[0].getDescription());

		dataLayoutRows = dataLayoutPages[0].getDataLayoutRows();

		Assert.assertEquals(
			Arrays.toString(dataLayoutRows), 1, dataLayoutRows.length);

		DataLayoutColumn[] dataLayoutColumns =
			dataLayoutRows[0].getDataLayoutColumns();

		Assert.assertEquals(
			Arrays.toString(dataLayoutColumns), 1, dataLayoutColumns.length);
		Assert.assertEquals(
			Integer.valueOf(12), dataLayoutColumns[0].getColumnSize());
		Assert.assertArrayEquals(
			new String[] {"newField"}, dataLayoutColumns[0].getFieldNames());

		DataDefinitionField[] dataDefinitionFields =
			dataDefinition.getDataDefinitionFields();

		Assert.assertEquals(
			Arrays.toString(dataDefinitionFields), 1,
			dataDefinitionFields.length);

		Assert.assertEquals("newField", dataDefinitionFields[0].getName());

		Assert.assertEquals("text", dataDefinitionFields[0].getFieldType());
	}

	private DataDefinitionResource _getDataDefinitionResource() {
		return DataDefinitionResource.builder(
		).checkPermissions(
			false
		).httpServletRequest(
			new MockHttpServletRequest() {

				@Override
				public String getHeader(String name) {
					if (StringUtil.equals(name, "Accept-Language")) {
						return "pt-BR";
					}

					return super.getHeader(name);
				}

			}
		).user(
			_user
		).build();
	}

	private DataDefinition _postDataDefinition(
			String dataDefinitionKey, Map<String, Object> description,
			String[] fieldNames, Map<String, Object> name,
			LocalizedValue pageDescription, LocalizedValue pageTitle)
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			SetUtil.fromArray(new Locale[] {LocaleUtil.BRAZIL, LocaleUtil.US}),
			LocaleUtil.BRAZIL);

		DDMFormTestUtil.addTextDDMFormFields(ddmForm, fieldNames);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(
				DDMFormSerializerSerializeRequest.Builder.newBuilder(
					ddmForm
				).build());

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage() {
			{
				setDDMFormLayoutRows(
					ListUtil.fromArray(
						new DDMFormLayoutRow() {
							{
								setDDMFormLayoutColumns(
									ListUtil.fromArray(
										new DDMFormLayoutColumn() {
											{
												setDDMFormFieldNames(
													ListUtil.fromArray(
														fieldNames));
												setSize(12);
											}
										}));
							}
						}));
				setDescription(pageDescription);
				setTitle(pageTitle);
			}
		};

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutSerializer.serialize(
					DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
						new DDMFormLayout() {
							{
								setDDMFormLayoutPages(
									ListUtil.fromArray(ddmFormLayoutPage));
								setPaginationMode(
									DDMFormLayout.SINGLE_PAGE_MODE);
							}
						}
					).build());

		DataDefinitionBuilder dataDefinitionBuilder =
			_dataEngineBuilderFactory.createDataDefinitionBuilder();

		DataDefinitionResource dataDefinitionResource =
			_getDataDefinitionResource();

		DataDefinition dataDefinition =
			dataDefinitionResource.postDataDefinitionByContentType(
				"app-builder",
				dataDefinitionBuilder.dataDefinitionKey(
					dataDefinitionKey
				).dataLayoutDefinition(
					ddmFormLayoutSerializerSerializeResponse.getContent()
				).definition(
					ddmFormSerializerSerializeResponse.getContent()
				).description(
					description
				).name(
					name
				).build());

		return dataDefinitionResource.getDataDefinition(dataDefinition.getId());
	}

	@Inject
	private DataEngineBuilderFactory _dataEngineBuilderFactory;

	@Inject
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Inject(filter = "ddm.form.layout.serializer.type=json")
	private DDMFormLayoutSerializer _ddmFormLayoutSerializer;

	@Inject(filter = "ddm.form.serializer.type=json")
	private DDMFormSerializer _ddmFormSerializer;

	@Inject
	private JSONFactory _jsonFactory;

	@DeleteAfterTestRun
	private User _user;

}