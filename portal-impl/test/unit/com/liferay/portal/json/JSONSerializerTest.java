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

package com.liferay.portal.json;

import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.LocalizationImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Igor Spasic
 */
@RunWith(PowerMockRunner.class)
public class JSONSerializerTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		LocalizationUtil localizationUtil = new LocalizationUtil();

		localizationUtil.setLocalization(new LocalizationImpl());

		setUpDDMStructure();
	}

	@Test
	public void testSerializeDDMStructure() {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.exclude("*.class");

		String json = jsonSerializer.serialize(_ddmStructure);

		Assert.assertTrue(json.contains("\"definition\":\"value\""));
	}

	@Test
	public void testSerializeHits() {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		Hits hits = new HitsImpl();

		String json = jsonSerializer.serialize(hits);

		json = json.replace(StringPool.SPACE, StringPool.BLANK);

		Assert.assertTrue(json.contains("\"docs\":[]"));
		Assert.assertFalse(json.contains("\"query\""));
		Assert.assertTrue(json.contains("\"queryTerms\":null"));
		Assert.assertTrue(json.contains("\"scores\":"));
		Assert.assertTrue(json.contains("\"snippets\":["));
		Assert.assertTrue(json.contains("\"start\":\"0\""));
		Assert.assertTrue(json.contains("\"length\":0"));
	}

	@Test
	public void testSerializeServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		String[] groupPermissions = new String[] {"VIEW"};

		serviceContext.setAttribute("groupPermissions", groupPermissions);
		serviceContext.setGroupPermissions(groupPermissions);

		String json = JSONFactoryUtil.serialize(serviceContext);

		ServiceContext deserializedServiceContext =
			(ServiceContext)JSONFactoryUtil.deserialize(json);

		Assert.assertNotNull(deserializedServiceContext.getGroupPermissions());
	}

	@Test
	public void testSerializeTwice() {
		ServiceContext serviceContext = new ServiceContext();

		String[] groupPermissions = new String[] {"VIEW"};

		serviceContext.setAttribute("groupPermissions", groupPermissions);
		serviceContext.setGroupPermissions(groupPermissions);

		String json1 = JSONFactoryUtil.serialize(serviceContext);

		ServiceContext deserializedServiceContext =
			(ServiceContext)JSONFactoryUtil.deserialize(json1);

		String json2 = JSONFactoryUtil.serialize(deserializedServiceContext);

		Assert.assertEquals(json1, json2);
	}

	protected void setUpDDMStructure() {
		when(
			_ddmStructure.getDefinition()
		).thenReturn(
			"value"
		);
	}

	@Mock
	private DDMStructure _ddmStructure;

}