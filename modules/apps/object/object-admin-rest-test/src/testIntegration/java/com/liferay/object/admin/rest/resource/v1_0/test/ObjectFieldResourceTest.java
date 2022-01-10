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
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectField;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;

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
public class ObjectFieldResourceTest extends BaseObjectFieldResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		String value = "A" + RandomTestUtil.randomString();

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(value), value, null, null,
				LocalizedMapUtil.getLocalizedMap(value),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.<com.liferay.object.model.ObjectField>emptyList());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_objectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition.getObjectDefinitionId());
		}
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectFieldNotFound() {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"label"};
	}

	@Override
	protected ObjectField randomObjectField() throws Exception {
		ObjectField objectField = super.randomObjectField();

		objectField.setIndexedAsKeyword(false);
		objectField.setLabel(
			Collections.singletonMap(
				LocaleUtil.US.toString(), "a" + objectField.getName()));
		objectField.setName("a" + objectField.getName());
		objectField.setType(ObjectField.Type.create("String"));

		return objectField;
	}

	@Override
	protected ObjectField testDeleteObjectField_addObjectField()
		throws Exception {

		return _addObjectField();
	}

	@Override
	protected Long
		testGetObjectDefinitionObjectFieldsPage_getObjectDefinitionId() {

		return _objectDefinition.getObjectDefinitionId();
	}

	@Override
	protected ObjectField testGetObjectField_addObjectField() throws Exception {
		return _addObjectField();
	}

	@Override
	protected ObjectField testGraphQLObjectField_addObjectField()
		throws Exception {

		return _addObjectField();
	}

	@Override
	protected ObjectField testPatchObjectField_addObjectField()
		throws Exception {

		return _addObjectField();
	}

	@Override
	protected ObjectField testPutObjectField_addObjectField() throws Exception {
		return _addObjectField();
	}

	private ObjectField _addObjectField() throws Exception {
		_objectField = objectFieldResource.postObjectDefinitionObjectField(
			_objectDefinition.getObjectDefinitionId(), randomObjectField());

		return _objectField;
	}

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectField _objectField;

}