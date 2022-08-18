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
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectViewColumn;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectViewSortColumn;
import com.liferay.object.admin.rest.resource.v1_0.util.NameMapUtil;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ObjectViewResourceTest extends BaseObjectViewResourceTestCase {

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

		_objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, false, null,
			LocalizedMapUtil.getLocalizedMap("Able"), "able", true, false,
			Collections.emptyList());
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
	public void testGraphQLGetObjectView() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectViewNotFound() {
	}

	@Override
	@Test
	public void testPostObjectViewCopy() throws Exception {
		ObjectView objectView = testGetObjectView_addObjectView();

		objectView.setDefaultObjectView(true);

		ObjectView copiedObjectView = objectViewResource.postObjectViewCopy(
			objectView.getId());

		Assert.assertTrue(
			Objects.deepEquals(
				objectView.getActions(), copiedObjectView.getActions()));
		Assert.assertFalse(copiedObjectView.getDefaultObjectView());
		Assert.assertEquals(
			objectView.getObjectDefinitionId(),
			copiedObjectView.getObjectDefinitionId());

		ObjectViewColumn[] objectViewColumns =
			objectView.getObjectViewColumns();
		ObjectViewColumn[] copiedObjectViewColumns =
			copiedObjectView.getObjectViewColumns();

		for (int i = 0; i < objectViewColumns.length; i++) {
			ObjectViewColumn objectViewColumn = objectViewColumns[i];
			ObjectViewColumn copiedObjectViewColumn =
				copiedObjectViewColumns[i];

			Assert.assertEquals(
				objectViewColumn.getObjectFieldName(),
				copiedObjectViewColumn.getObjectFieldName());
			Assert.assertEquals(
				objectViewColumn.getPriority(),
				copiedObjectViewColumn.getPriority());
		}

		ObjectViewSortColumn[] objectViewSortColumns =
			objectView.getObjectViewSortColumns();
		ObjectViewSortColumn[] copiedObjectViewSortColumns =
			copiedObjectView.getObjectViewSortColumns();

		for (int i = 0; i < objectViewSortColumns.length; i++) {
			ObjectViewSortColumn objectViewSortColumn =
				objectViewSortColumns[i];
			ObjectViewSortColumn objectViewSortColumnCopy =
				copiedObjectViewSortColumns[i];

			Assert.assertEquals(
				objectViewSortColumn.getObjectFieldName(),
				objectViewSortColumnCopy.getObjectFieldName());
			Assert.assertEquals(
				objectViewSortColumn.getPriority(),
				objectViewSortColumnCopy.getPriority());
			Assert.assertEquals(
				objectViewSortColumn.getSortOrderAsString(),
				objectViewSortColumnCopy.getSortOrderAsString());
		}

		Assert.assertTrue(
			equals(
				NameMapUtil.copy(objectView.getName()),
				(Map)copiedObjectView.getName()));

		assertValid(copiedObjectView);
	}

	@Override
	protected ObjectView randomObjectView() throws Exception {
		ObjectView objectView = super.randomObjectView();

		objectView.setDefaultObjectView(false);
		objectView.setName(
			Collections.singletonMap("en_US", RandomTestUtil.randomString()));
		objectView.setObjectDefinitionId(
			_objectDefinition.getObjectDefinitionId());
		objectView.setObjectViewColumns(
			new ObjectViewColumn[] {_randomObjectViewColumn()});
		objectView.setObjectViewSortColumns(
			new ObjectViewSortColumn[] {_randomObjectViewSortColumn()});

		return objectView;
	}

	@Override
	protected ObjectView testDeleteObjectView_addObjectView() throws Exception {
		return objectViewResource.postObjectDefinitionObjectView(
			_objectDefinition.getObjectDefinitionId(), randomObjectView());
	}

	@Override
	protected Long
		testGetObjectDefinitionObjectViewsPage_getObjectDefinitionId() {

		return _objectDefinition.getObjectDefinitionId();
	}

	@Override
	protected ObjectView testGetObjectView_addObjectView() throws Exception {
		return objectViewResource.postObjectDefinitionObjectView(
			_objectDefinition.getObjectDefinitionId(), randomObjectView());
	}

	@Override
	protected ObjectView testGraphQLObjectView_addObjectView()
		throws Exception {

		return objectViewResource.postObjectDefinitionObjectView(
			_objectDefinition.getObjectDefinitionId(), randomObjectView());
	}

	@Override
	protected ObjectView testPutObjectView_addObjectView() throws Exception {
		return objectViewResource.postObjectDefinitionObjectView(
			_objectDefinition.getObjectDefinitionId(), randomObjectView());
	}

	private ObjectViewColumn _randomObjectViewColumn() {
		return new ObjectViewColumn() {
			{
				label = Collections.singletonMap(
					"en_US", RandomTestUtil.randomString());
				objectFieldName = _objectField.getName();
				priority = RandomTestUtil.randomInt();
			}
		};
	}

	private ObjectViewSortColumn _randomObjectViewSortColumn() {
		return new ObjectViewSortColumn() {
			{
				objectFieldName = _objectField.getName();
				priority = RandomTestUtil.randomInt();
				sortOrder = SortOrder.ASC;
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