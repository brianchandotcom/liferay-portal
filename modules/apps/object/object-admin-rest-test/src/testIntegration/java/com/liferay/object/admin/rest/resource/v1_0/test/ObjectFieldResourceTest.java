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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

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
	public void setUp() throws Exception {
		super.setUp();

		String value = "A" + RandomTestUtil.randomString();

		_objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(value), value, null, null,
				LocalizedMapUtil.getLocalizedMap(value), "company",
				Collections.<com.liferay.object.model.ObjectField>emptyList());
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		if (_objectField != null) {
			ObjectFieldLocalServiceUtil.deleteObjectField(_objectField.getId());
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
			Collections.singletonMap("en-US", "a" + objectField.getName()));
		objectField.setName("a" + objectField.getName());
		objectField.setType("String");

		return objectField;
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
	private ObjectField _objectField;

}