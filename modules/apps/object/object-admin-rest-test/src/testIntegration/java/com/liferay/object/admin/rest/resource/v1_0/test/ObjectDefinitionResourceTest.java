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
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectField;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionResourceTest
	extends BaseObjectDefinitionResourceTestCase {

	@After
	@Override
	public void tearDown() {
		if (_objectDefinition != null) {
			try {
				_objectDefinitionLocalService.deleteObjectDefinition(
					_objectDefinition.getId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectDefinitionNotFound() {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected ObjectDefinition randomObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition = super.randomObjectDefinition();

		objectDefinition.setLabel(
			Collections.singletonMap(
				"en_US", "A" + objectDefinition.getName()));
		objectDefinition.setName("A" + objectDefinition.getName());
		objectDefinition.setPluralLabel(
			Collections.singletonMap(
				"en_US", "A" + objectDefinition.getName()));
		objectDefinition.setObjectFields(
			new ObjectField[] {
				new ObjectField() {
					{
						setLabel(Collections.singletonMap("en_US", "Column"));
						setName("column");
						setType(ObjectField.Type.create("String"));
					}
				}
			});
		objectDefinition.setScope(ObjectDefinitionConstants.SCOPE_COMPANY);

		return objectDefinition;
	}

	@Override
	protected ObjectDefinition testDeleteObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testGetObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testGetObjectDefinitionsPage_addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _addObjectDefinition(objectDefinition);
	}

	@Override
	protected ObjectDefinition testGraphQLObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testPatchObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testPostObjectDefinition_addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _addObjectDefinition(objectDefinition);
	}

	@Override
	protected ObjectDefinition
			testPostObjectDefinitionPublish_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testPutObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	private ObjectDefinition _addObjectDefinition(
			ObjectDefinition randomObjectDefinition)
		throws Exception {

		_objectDefinition = objectDefinitionResource.postObjectDefinition(
			randomObjectDefinition);

		return _objectDefinition;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionResourceTest.class);

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}