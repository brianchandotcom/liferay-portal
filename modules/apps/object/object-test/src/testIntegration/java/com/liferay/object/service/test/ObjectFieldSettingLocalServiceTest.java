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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class ObjectFieldSettingLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, null);

		_objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), RandomTestUtil.randomBoolean(),
			Collections.emptyList());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testAddObjectFieldSetting() throws Exception {
		try {
			_objectFieldSettingLocalService.addObjectFieldSetting(
				TestPropsValues.getUserId(), RandomTestUtil.randomLong(),
				StringUtil.randomId(), RandomTestUtil.randomBoolean(),
				RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		try {
			_objectFieldSettingLocalService.addObjectFieldSetting(
				TestPropsValues.getUserId(), _objectField.getObjectFieldId(),
				StringUtil.randomId(), true, StringPool.BLANK);

			Assert.fail();
		}
		catch (ObjectFieldSettingValueException
					objectFieldSettingValueException) {

			Assert.assertNotNull(objectFieldSettingValueException);
		}
	}

	@Test
	public void testDeleteObjectFieldSetting() throws Exception {
		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.addObjectFieldSetting(
				TestPropsValues.getUserId(), _objectField.getObjectFieldId(),
				"position", RandomTestUtil.randomBoolean(),
				RandomTestUtil.randomString());

		Assert.assertNotNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldSetting.getObjectFieldId(), "position"));

		_objectFieldSettingLocalService.deleteObjectFieldSetting(
			objectFieldSetting);

		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldSetting.getObjectFieldId(), "position"));
	}

	@Test
	public void testUpdateObjectFieldSetting() throws Exception {
		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.addObjectFieldSetting(
				TestPropsValues.getUserId(), _objectField.getObjectFieldId(),
				"position", false, "First");

		Assert.assertEquals("position", objectFieldSetting.getName());
		Assert.assertEquals("First", objectFieldSetting.getValue());
		Assert.assertFalse(objectFieldSetting.isRequired());

		objectFieldSetting =
			_objectFieldSettingLocalService.updateObjectFieldSetting(
				objectFieldSetting.getObjectFieldSettingId(), "Second");

		Assert.assertEquals("position", objectFieldSetting.getName());
		Assert.assertEquals("Second", objectFieldSetting.getValue());
		Assert.assertFalse(objectFieldSetting.isRequired());
	}

	private static ObjectDefinition _objectDefinition;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	private static ObjectField _objectField;

	@Inject
	private static ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private static ObjectFieldSettingLocalService
		_objectFieldSettingLocalService;

}