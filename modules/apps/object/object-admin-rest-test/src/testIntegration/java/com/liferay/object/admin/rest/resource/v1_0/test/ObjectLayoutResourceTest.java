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
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayoutBox;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayoutColumn;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayoutRow;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayoutTab;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
public class ObjectLayoutResourceTest extends BaseObjectLayoutResourceTestCase {

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
				Collections.emptyList());

		_objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), "Text", false, false,
			null, LocalizedMapUtil.getLocalizedMap("Able"), "able", true,
			"String");
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
	public void testGraphQLGetObjectLayout() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectLayoutNotFound() {
	}

	@Override
	protected ObjectLayout randomObjectLayout() throws Exception {
		ObjectLayout objectLayout = super.randomObjectLayout();

		objectLayout.setDefaultObjectLayout(false);
		objectLayout.setName(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));
		objectLayout.setObjectDefinitionId(
			_objectDefinition.getObjectDefinitionId());
		objectLayout.setObjectLayoutTabs(
			new ObjectLayoutTab[] {_randomObjectLayoutTab()});

		return objectLayout;
	}

	@Override
	protected ObjectLayout testDeleteObjectLayout_addObjectLayout()
		throws Exception {

		return objectLayoutResource.postObjectDefinitionObjectLayout(
			_objectDefinition.getObjectDefinitionId(), randomObjectLayout());
	}

	@Override
	protected Long
			testGetObjectDefinitionObjectLayoutsPage_getObjectDefinitionId()
		throws Exception {

		return _objectDefinition.getObjectDefinitionId();
	}

	@Override
	protected ObjectLayout testGetObjectLayout_addObjectLayout()
		throws Exception {

		return objectLayoutResource.postObjectDefinitionObjectLayout(
			_objectDefinition.getObjectDefinitionId(), randomObjectLayout());
	}

	@Override
	protected ObjectLayout testGraphQLObjectLayout_addObjectLayout()
		throws Exception {

		return objectLayoutResource.postObjectDefinitionObjectLayout(
			_objectDefinition.getObjectDefinitionId(), randomObjectLayout());
	}

	@Override
	protected ObjectLayout testPutObjectLayout_addObjectLayout()
		throws Exception {

		return objectLayoutResource.postObjectDefinitionObjectLayout(
			_objectDefinition.getObjectDefinitionId(), randomObjectLayout());
	}

	private ObjectLayoutBox _randomObjectLayoutBox() {
		return new ObjectLayoutBox() {
			{
				collapsable = RandomTestUtil.randomBoolean();
				name = Collections.singletonMap(
					"en-US", RandomTestUtil.randomString());
				objectLayoutRows = new ObjectLayoutRow[] {
					_randomObjectLayoutRow()
				};
				priority = RandomTestUtil.randomInt();
			}
		};
	}

	private ObjectLayoutColumn _randomObjectLayoutColumn() {
		return new ObjectLayoutColumn() {
			{
				objectFieldId = _objectField.getObjectFieldId();
				priority = RandomTestUtil.randomInt();
				size = RandomTestUtil.randomInt(1, 12);
			}
		};
	}

	private ObjectLayoutRow _randomObjectLayoutRow() {
		return new ObjectLayoutRow() {
			{
				objectLayoutColumns = new ObjectLayoutColumn[] {
					_randomObjectLayoutColumn()
				};
				priority = RandomTestUtil.randomInt();
			}
		};
	}

	private ObjectLayoutTab _randomObjectLayoutTab() {
		return new ObjectLayoutTab() {
			{
				name = Collections.singletonMap(
					"en-US", RandomTestUtil.randomString());
				objectLayoutBoxes = new ObjectLayoutBox[] {
					_randomObjectLayoutBox()
				};
				objectRelationshipId = 0L;
				priority = RandomTestUtil.randomInt();
			}
		};
	}

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectField _objectField;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

}