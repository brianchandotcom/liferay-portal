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

package com.liferay.data.engine.taglib.internal.servlet.taglib.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcela Cunha
 */
public class DataLayoutTaglibUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			}
		};

		ReflectionTestUtil.setFieldValue(
			_dataLayoutTaglibUtil, "_dataLayoutTaglibUtil",
			_dataLayoutTaglibUtil);

		ReflectionTestUtil.setFieldValue(
			_dataLayoutTaglibUtil, "_jsonFactory", new JSONFactoryImpl());

		ReflectionTestUtil.setFieldValue(
			_dataLayoutTaglibUtil, "_dataDefinitionResourceFactory",
			_dataDefinitionResourceFactory);

		ReflectionTestUtil.setFieldValue(
			_dataLayoutTaglibUtil, "_portal", _portal);

		_setUpDataDefinitionResource();
	}

	@Test
	public void testGetFieldTypesJSONArrayWithSearchableFieldsDisabled()
		throws Exception {

		JSONArray actualResultJSONArray =
			_dataLayoutTaglibUtil.getFieldTypesJSONArray(
				_httpServletRequest, Collections.singleton(_JOURNAL), true);

		Assert.assertEquals(
			_objectMapper.readTree(
				read(
					"data-definition-field-types-journal-scope-searchable-" +
						"fields-disabled.json")),
			_objectMapper.readTree(actualResultJSONArray.toString()));
	}

	@Test
	public void testGetFieldTypesJSONArrayWithSearchableFieldsEnabled()
		throws Exception {

		JSONArray actualResultJSONArray =
			_dataLayoutTaglibUtil.getFieldTypesJSONArray(
				_httpServletRequest, Collections.singleton(_JOURNAL), false);

		Assert.assertEquals(
			_objectMapper.readTree(
				read(
					"data-definition-field-types-journal-scope-searchable-" +
						"fields-enabled.json")),
			_objectMapper.readTree(actualResultJSONArray.toString()));
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private void _setUpDataDefinitionResource() throws Exception {
		Mockito.when(
			_dataDefinitionResourceFactory.create()
		).thenReturn(
			_dataDefinitionResourceBuilder
		);

		Mockito.when(
			_dataDefinitionResourceBuilder.httpServletRequest(
				_httpServletRequest)
		).thenReturn(
			_dataDefinitionResourceBuilder
		);

		Mockito.when(
			_dataDefinitionResourceBuilder.user(
				_portal.getUser(_httpServletRequest))
		).thenReturn(
			_dataDefinitionResourceBuilder
		);

		DataDefinitionResource dataDefinitionResource = Mockito.mock(
			DataDefinitionResource.class);

		Mockito.when(
			_dataDefinitionResourceBuilder.build()
		).thenReturn(
			dataDefinitionResource
		);

		Mockito.when(
			dataDefinitionResource.
				getDataDefinitionDataDefinitionFieldFieldTypes()
		).thenReturn(
			read("data-definition-field-types.json")
		);
	}

	private static final String _JOURNAL = "journal";

	private final DataDefinitionResource.Builder
		_dataDefinitionResourceBuilder = Mockito.mock(
			DataDefinitionResource.Builder.class);
	private final DataDefinitionResource.Factory
		_dataDefinitionResourceFactory = Mockito.mock(
			DataDefinitionResource.Factory.class);
	private final DataLayoutTaglibUtil _dataLayoutTaglibUtil =
		new DataLayoutTaglibUtil();
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private ObjectMapper _objectMapper;
	private final Portal _portal = Mockito.mock(Portal.class);

}