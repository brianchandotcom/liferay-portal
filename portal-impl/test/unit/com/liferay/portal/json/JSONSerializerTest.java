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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.model.UserTrackerPath;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.UserTrackerPathImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.LocalizationImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Spasic
 */
public class JSONSerializerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		LocalizationUtil localizationUtil = new LocalizationUtil();

		localizationUtil.setLocalization(new LocalizationImpl());
	}

	@Test
	public void testSerializeDDMStructure() {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.exclude("*.class");

		TestClass testClass = new TestClass();

		testClass.setName("test name");

		String json = jsonSerializer.serialize(testClass);

		Assert.assertTrue(json, json.contains("\"name\":\"test name\""));
	}

	@Test
	public void testSerializeEntityWithGetOriginal() {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		UserTrackerPath userTrackerPath = new UserTrackerPathImpl();

		String json = jsonSerializer.serialize(userTrackerPath);

		Assert.assertTrue(
			json, json.contains("\"originalUserTrackerId\":\"0\""));

		userTrackerPath.setUserTrackerId(1L);

		userTrackerPath.resetOriginalValues();

		json = jsonSerializer.serialize(userTrackerPath);

		Assert.assertTrue(
			json, json.contains("\"originalUserTrackerId\":\"1\""));
	}

	@Test
	public void testSerializeHits() {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		Hits hits = new HitsImpl();

		String json = jsonSerializer.serialize(hits);

		json = StringUtil.replace(json, CharPool.SPACE, StringPool.BLANK);

		Assert.assertTrue(json, json.contains("\"docs\":[]"));
		Assert.assertFalse(json, json.contains("\"query\""));
		Assert.assertTrue(json, json.contains("\"queryTerms\":null"));
		Assert.assertTrue(json, json.contains("\"scores\":"));
		Assert.assertTrue(json, json.contains("\"snippets\":["));
		Assert.assertTrue(json, json.contains("\"start\":\"0\""));
		Assert.assertTrue(json, json.contains("\"length\":0"));
	}

	@Test
	public void testSerializeServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		String[] groupPermissions = {"VIEW"};

		serviceContext.setAttribute("groupPermissions", groupPermissions);

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, null);

		serviceContext.setModelPermissions(modelPermissions);

		String json = JSONFactoryUtil.serialize(serviceContext);

		ServiceContext deserializedServiceContext =
			(ServiceContext)JSONFactoryUtil.deserialize(json);

		ModelPermissions deserializedModelPermissions =
			deserializedServiceContext.getModelPermissions();

		Assert.assertArrayEquals(
			groupPermissions,
			deserializedModelPermissions.getActionIds(
				RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE));
	}

	@Test
	public void testSerializeTwice() {
		ServiceContext serviceContext = new ServiceContext();

		String[] groupPermissions = {"VIEW"};

		serviceContext.setAttribute("groupPermissions", groupPermissions);

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, null);

		serviceContext.setModelPermissions(modelPermissions);

		String json1 = JSONFactoryUtil.serialize(serviceContext);

		ServiceContext deserializedServiceContext =
			(ServiceContext)JSONFactoryUtil.deserialize(json1);

		String json2 = JSONFactoryUtil.serialize(deserializedServiceContext);

		Assert.assertEquals(json1, json2);
	}

	private class BaseTestClass {

		public String getName() {
			return _name;
		}

		public void setName(String name) {
			_name = name;
		}

		private String _name;

	}

	private class TestClass extends BaseTestClass {

		@Override
		public void setName(String name) {
			super.setName(name);
		}

	}

}