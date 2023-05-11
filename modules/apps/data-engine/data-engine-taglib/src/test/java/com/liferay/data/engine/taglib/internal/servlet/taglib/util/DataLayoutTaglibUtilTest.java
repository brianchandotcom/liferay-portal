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
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Marcela Cunha
 */
public class DataLayoutTaglibUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_frameworkUtilMockedStatic.when(
			() -> FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		_portalUtilMockedStatic.when(
			() -> PortalUtil.getUser(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			Mockito.mock(User.class)
		);

		_setUpDataDefinitionResource();
	}

	@AfterClass
	public static void tearDownClass() {
		_frameworkUtilMockedStatic.close();
		_portalUtilMockedStatic.close();
	}

	@Test
	public void testGetFieldTypesJSONArrayWithSearchableFieldsDisabled()
		throws Exception {

		JSONArray actualResultJSONArray =
			DataLayoutTaglibUtil.getFieldTypesJSONArray(
				_httpServletRequest, Collections.singleton("journal"), true);

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"data-definition-field-types-journal-scope-searchable-" +
						"fields-disabled.json")),
			_objectMapper.readTree(actualResultJSONArray.toString()));
	}

	@Test
	public void testGetFieldTypesJSONArrayWithSearchableFieldsEnabled()
		throws Exception {

		JSONArray actualResultJSONArray =
			DataLayoutTaglibUtil.getFieldTypesJSONArray(
				_httpServletRequest, Collections.singleton("journal"), false);

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"data-definition-field-types-journal-scope-searchable-" +
						"fields-enabled.json")),
			_objectMapper.readTree(actualResultJSONArray.toString()));
	}

	private static String _read(String fileName) throws Exception {
		InputStream inputStream =
			DataLayoutTaglibUtilTest.class.getResourceAsStream(
				"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private static void _setUpDataDefinitionResource() throws Exception {
		DataDefinitionResource dataDefinitionResource = Mockito.mock(
			DataDefinitionResource.class);

		Mockito.when(
			dataDefinitionResource.
				getDataDefinitionDataDefinitionFieldFieldTypes()
		).thenReturn(
			_read("data-definition-field-types.json")
		);

		DataDefinitionResource.Builder dataDefinitionResourceBuilder =
			Mockito.mock(DataDefinitionResource.Builder.class);

		Mockito.when(
			dataDefinitionResourceBuilder.build()
		).thenReturn(
			dataDefinitionResource
		);

		Mockito.when(
			dataDefinitionResourceBuilder.httpServletRequest(
				_httpServletRequest)
		).thenReturn(
			dataDefinitionResourceBuilder
		);

		Mockito.when(
			dataDefinitionResourceBuilder.user(Mockito.any())
		).thenReturn(
			dataDefinitionResourceBuilder
		);

		DataDefinitionResource.Factory dataDefinitionResourceFactory =
			Mockito.mock(DataDefinitionResource.Factory.class);

		Mockito.when(
			dataDefinitionResourceFactory.create()
		).thenReturn(
			dataDefinitionResourceBuilder
		);

		Snapshot<DataDefinitionResource.Factory>
			dataDefinitionResourceFactorySnapshot = Mockito.mock(
				(Class<Snapshot<DataDefinitionResource.Factory>>)
					(Class<?>)Snapshot.class);

		Mockito.when(
			dataDefinitionResourceFactorySnapshot.get()
		).thenReturn(
			dataDefinitionResourceFactory
		);

		ReflectionTestUtil.setFieldValue(
			DataLayoutTaglibUtil.class,
			"_dataDefinitionResourceFactorySnapshot",
			dataDefinitionResourceFactorySnapshot);
	}

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private static final MockedStatic<PortalUtil> _portalUtilMockedStatic =
		Mockito.mockStatic(PortalUtil.class);

	private final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
		}
	};

}