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
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.exception.NoSuchObjectValidationRuleException;
import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.exception.ObjectValidationRuleNameException;
import com.liferay.object.exception.ObjectValidationRuleScriptException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcela Cunha
 */
@RunWith(Arquillian.class)
public class ObjectValidationRuleLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						"textField")));
	}

	@Test
	public void testAddObjectValidationRule() throws Exception {

		// Engine "abcdefghijklmnopqrstuvwxyz" does not exist

		try {
			_testAddObjectValidationRule(
				"abcdefghijklmnopqrstuvwxyz", "Test",
				"isEmailAddress(textField)");

			Assert.fail();
		}
		catch (ObjectValidationRuleEngineException
					objectValidationRuleEngineException) {

			Assert.assertEquals(
				"Engine \"abcdefghijklmnopqrstuvwxyz\" does not exist",
				objectValidationRuleEngineException.getMessage());
		}

		// Engine is null

		try {
			_testAddObjectValidationRule(
				"", "Test", "isEmailAddress(textField)");

			Assert.fail();
		}
		catch (ObjectValidationRuleEngineException
					objectValidationRuleEngineException) {

			Assert.assertEquals(
				"Engine is null",
				objectValidationRuleEngineException.getMessage());
		}

		// Name is null

		try {
			_testAddObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM, "",
				"isEmailAddress(textField)");

			Assert.fail();
		}
		catch (ObjectValidationRuleNameException
					objectValidationRuleNameException) {

			Assert.assertEquals(
				"Name is null for locale " + LocaleUtil.US.getDisplayName(),
				objectValidationRuleNameException.getMessage());
		}

		// Script is null

		try {
			_testAddObjectValidationRule(
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM, "Test", "");

			Assert.fail();
		}
		catch (ObjectValidationRuleScriptException
					objectValidationRuleScriptException) {

			Assert.assertEquals(
				"Script is null",
				objectValidationRuleScriptException.getMessage());
		}

		ObjectValidationRule objectValidationRule =
			_objectValidationRuleLocalService.addObjectValidationRule(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap(
					"Field must be an email address"),
				LocalizedMapUtil.getLocalizedMap("Email Address Validation"),
				"isEmailAddress(textField)");

		Assert.assertTrue(objectValidationRule.isActive());
		Assert.assertEquals(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			objectValidationRule.getEngine());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Field must be an email address"),
			objectValidationRule.getErrorLabelMap());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Email Address Validation"),
			objectValidationRule.getNameMap());
		Assert.assertEquals(
			"isEmailAddress(textField)", objectValidationRule.getScript());
	}

	@Test
	public void testDeleteObjectValidationRule() throws Exception {
		ObjectValidationRule objectValidationRule =
			_objectValidationRuleLocalService.addObjectValidationRule(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"isEmailAddress(textField)");

		objectValidationRule =
			_objectValidationRuleLocalService.fetchObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId());

		Assert.assertNotNull(objectValidationRule);

		_objectValidationRuleLocalService.deleteObjectValidationRule(
			objectValidationRule.getObjectValidationRuleId());

		objectValidationRule =
			_objectValidationRuleLocalService.fetchObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId());

		Assert.assertNull(objectValidationRule);
	}

	@Test
	public void testUpdateObjectValidationRule() throws Exception {
		ObjectValidationRule objectValidationRule =
			_objectValidationRuleLocalService.addObjectValidationRule(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap(
					"Field must be an email address"),
				LocalizedMapUtil.getLocalizedMap("Email Address Validation"),
				"isEmailAddress(textField)");

		Assert.assertTrue(objectValidationRule.isActive());
		Assert.assertEquals(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			objectValidationRule.getEngine());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Field must be an email address"),
			objectValidationRule.getErrorLabelMap());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Email Address Validation"),
			objectValidationRule.getNameMap());
		Assert.assertEquals(
			"isEmailAddress(textField)", objectValidationRule.getScript());

		try {
			objectValidationRule =
				_objectValidationRuleLocalService.updateObjectValidationRule(
					RandomTestUtil.randomLong(), false,
					ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"isEmailAddress(textField)");

			Assert.fail();
		}
		catch (NoSuchObjectValidationRuleException
					noSuchObjectValidationRuleException) {

			Assert.assertNotNull(noSuchObjectValidationRuleException);
		}

		objectValidationRule =
			_objectValidationRuleLocalService.updateObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId(), true,
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap("Field must be an URL"),
				LocalizedMapUtil.getLocalizedMap("URL Validation"),
				"isURL(textField)");

		Assert.assertTrue(objectValidationRule.isActive());
		Assert.assertEquals(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			objectValidationRule.getEngine());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Field must be an URL"),
			objectValidationRule.getErrorLabelMap());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("URL Validation"),
			objectValidationRule.getNameMap());
		Assert.assertEquals(
			"isURL(textField)", objectValidationRule.getScript());
	}

	private void _testAddObjectValidationRule(
			String engine, String name, String script)
		throws Exception {

		ObjectValidationRule objectValidationRule = null;

		try {
			objectValidationRule =
				_objectValidationRuleLocalService.addObjectValidationRule(
					TestPropsValues.getUserId(),
					_objectDefinition.getObjectDefinitionId(), true, engine,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					LocalizedMapUtil.getLocalizedMap(name), script);
		}
		finally {
			if (objectValidationRule != null) {
				_objectValidationRuleLocalService.deleteObjectValidationRule(
					objectValidationRule);
			}
		}
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

}