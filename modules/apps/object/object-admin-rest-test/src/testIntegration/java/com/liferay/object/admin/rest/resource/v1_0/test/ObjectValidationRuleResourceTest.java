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

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectValidationRule;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.util.PropsUtil;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ObjectValidationRuleResourceTest
	extends BaseObjectValidationRuleResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		PropsUtil.set("feature.flag.LPS-147964", Boolean.TRUE.toString());

		String value = "A" + RandomTestUtil.randomString();

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(value), value, null, null,
				LocalizedMapUtil.getLocalizedMap(value),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.emptyList());

		_objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			false, null, LocalizedMapUtil.getLocalizedMap("Able"), "able", true,
			Collections.emptyList());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		PropsUtil.set("feature.flag.LPS-147964", Boolean.FALSE.toString());

		if (_objectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition.getObjectDefinitionId());
		}
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectValidationRule() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectValidationRuleNotFound() {
	}

	@Override
	protected ObjectValidationRule randomObjectValidationRule()
		throws Exception {

		ObjectValidationRule objectValidationRule =
			super.randomObjectValidationRule();

		objectValidationRule.setActive(true);
		objectValidationRule.setEngine(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM);
		objectValidationRule.setErrorLabel(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));
		objectValidationRule.setName(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));
		objectValidationRule.setObjectDefinitionId(
			_objectDefinition.getObjectDefinitionId());
		objectValidationRule.setScript("isEmailAddress(able)");

		return objectValidationRule;
	}

	@Override
	protected ObjectValidationRule
			testDeleteObjectValidationRule_addObjectValidationRule()
		throws Exception {

		return objectValidationRuleResource.
			postObjectDefinitionObjectValidationRule(
				_objectDefinition.getObjectDefinitionId(),
				randomObjectValidationRule());
	}

	@Override
	protected Long
		testGetObjectDefinitionObjectValidationRulesPage_getObjectDefinitionId() {

		return _objectDefinition.getObjectDefinitionId();
	}

	@Override
	protected ObjectValidationRule
			testGetObjectValidationRule_addObjectValidationRule()
		throws Exception {

		return objectValidationRuleResource.
			postObjectDefinitionObjectValidationRule(
				_objectDefinition.getObjectDefinitionId(),
				randomObjectValidationRule());
	}

	@Override
	protected ObjectValidationRule
			testGraphQLObjectValidationRule_addObjectValidationRule()
		throws Exception {

		return objectValidationRuleResource.
			postObjectDefinitionObjectValidationRule(
				_objectDefinition.getObjectDefinitionId(),
				randomObjectValidationRule());
	}

	@Override
	protected ObjectValidationRule
			testPatchObjectValidationRule_addObjectValidationRule()
		throws Exception {

		return objectValidationRuleResource.
			postObjectDefinitionObjectValidationRule(
				_objectDefinition.getObjectDefinitionId(),
				randomObjectValidationRule());
	}

	@Override
	protected ObjectValidationRule
			testPutObjectValidationRule_addObjectValidationRule()
		throws Exception {

		return objectValidationRuleResource.
			postObjectDefinitionObjectValidationRule(
				_objectDefinition.getObjectDefinitionId(),
				randomObjectValidationRule());
	}

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

}