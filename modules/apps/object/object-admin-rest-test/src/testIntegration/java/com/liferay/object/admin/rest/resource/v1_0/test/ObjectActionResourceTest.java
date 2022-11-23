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
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectAction;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
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
public class ObjectActionResourceTest extends BaseObjectActionResourceTestCase {

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
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList());

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-166918", "true"
			).build());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_objectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition.getObjectDefinitionId());
		}

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-166918", "false"
			).build());
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectAction() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectActionNotFound() {
	}

	@Override
	protected ObjectAction randomObjectAction() throws Exception {
		return new ObjectAction() {
			{
				active = RandomTestUtil.randomBoolean();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				errorMessage = Collections.singletonMap(
					"en_US", RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				label = Collections.singletonMap(
					"en_US", RandomTestUtil.randomString());
				name = RandomTestUtil.randomString();
				objectActionExecutorKey =
					ObjectActionExecutorConstants.KEY_GROOVY;
				objectActionTriggerKey =
					ObjectActionTriggerConstants.KEY_STANDALONE;
				parameters = UnicodePropertiesBuilder.put(
					"script", "println \"Hello World\""
				).build();
			}
		};
	}

	@Override
	protected ObjectAction testDeleteObjectAction_addObjectAction()
		throws Exception {

		return _addObjectAction();
	}

	@Override
	protected ObjectAction testGetObjectAction_addObjectAction()
		throws Exception {

		return _addObjectAction();
	}

	@Override
	protected Long
			testGetObjectDefinitionObjectActionsPage_getObjectDefinitionId()
		throws Exception {

		return _objectDefinition.getObjectDefinitionId();
	}

	@Override
	protected ObjectAction testGraphQLObjectAction_addObjectAction()
		throws Exception {

		return _addObjectAction();
	}

	@Override
	protected ObjectAction testPatchObjectAction_addObjectAction()
		throws Exception {

		return _addObjectAction();
	}

	@Override
	protected ObjectAction testPutObjectAction_addObjectAction()
		throws Exception {

		return _addObjectAction();
	}

	private ObjectAction _addObjectAction() throws Exception {
		return objectActionResource.postObjectDefinitionObjectAction(
			_objectDefinition.getObjectDefinitionId(), randomObjectAction());
	}

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}