/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

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
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				StringUtil.randomId()
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).required(
				RandomTestUtil.randomBoolean()
			).build());
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
				StringUtil.randomId(), RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}
	}

	@Test
	public void testDeleteObjectFieldSetting() throws Exception {
		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.addObjectFieldSetting(
				TestPropsValues.getUserId(), _objectField.getObjectFieldId(),
				"position", RandomTestUtil.randomString());

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
				"position", "First");

		Assert.assertEquals("position", objectFieldSetting.getName());
		Assert.assertEquals("First", objectFieldSetting.getValue());

		objectFieldSetting =
			_objectFieldSettingLocalService.updateObjectFieldSetting(
				objectFieldSetting.getObjectFieldSettingId(), "Second");

		Assert.assertEquals("position", objectFieldSetting.getName());
		Assert.assertEquals("Second", objectFieldSetting.getValue());
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