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
import com.liferay.object.exception.DefaultObjectLayoutException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectLayout;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.object.model.ObjectLayoutColumn;
import com.liferay.object.model.ObjectLayoutRow;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.persistence.ObjectLayoutBoxPersistence;
import com.liferay.object.service.persistence.ObjectLayoutColumnPersistence;
import com.liferay.object.service.persistence.ObjectLayoutRowPersistence;
import com.liferay.object.service.persistence.ObjectLayoutTabPersistence;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class ObjectLayoutLocalServiceTest {

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
				ObjectDefinitionConstants.SCOPE_COMPANY, null);
	}

	@Test
	public void testAddObjectLayout() throws Exception {
		try {
			ObjectLayoutTab objectLayoutTab1 =
				_objectLayoutTabPersistence.create(0);

			objectLayoutTab1.setNameMap(
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));
			objectLayoutTab1.setObjectLayoutBoxes(
				Arrays.asList(_addObjectLayoutBox(), _addObjectLayoutBox()));

			ObjectLayoutTab objectLayoutTab2 =
				_objectLayoutTabPersistence.create(0);

			objectLayoutTab2.setNameMap(
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));
			objectLayoutTab2.setObjectLayoutBoxes(
				Arrays.asList(_addObjectLayoutBox(), _addObjectLayoutBox()));

			_objectLayoutLocalService.addObjectLayout(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Arrays.asList(objectLayoutTab1, objectLayoutTab2));

			Assert.fail();
		}
		catch (DefaultObjectLayoutException defaultObjectLayoutException) {
			String message = defaultObjectLayoutException.getMessage();

			Assert.assertTrue(
				message.contains(
					"All required object fields must be associated to the " +
						"first tab of a default object layout"));
		}

		_deleteObjectFields();

		ObjectLayout objectLayout = null;

		try {
			ObjectLayoutTab objectLayoutTab =
				_objectLayoutTabPersistence.create(0);

			objectLayoutTab.setNameMap(
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));
			objectLayoutTab.setObjectLayoutBoxes(
				Arrays.asList(_addObjectLayoutBox(), _addObjectLayoutBox()));

			objectLayout = _objectLayoutLocalService.addObjectLayout(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Collections.singletonList(objectLayoutTab));

			_objectLayoutLocalService.addObjectLayout(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Collections.singletonList(objectLayoutTab));
		}
		catch (DefaultObjectLayoutException defaultObjectLayoutException) {
			String message = defaultObjectLayoutException.getMessage();

			Assert.assertTrue(
				message.contains(
					"There can only be one default object layout"));
		}

		_deleteObjectFields();
		_objectLayoutLocalService.deleteObjectLayout(
			objectLayout.getObjectLayoutId());

		try {
			ObjectLayoutTab objectLayoutTab =
				_objectLayoutTabPersistence.create(0);

			objectLayoutTab.setNameMap(
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));
			objectLayoutTab.setPriority(0);
			objectLayoutTab.setObjectLayoutBoxes(
				Arrays.asList(_addObjectLayoutBox(), _addObjectLayoutBox()));

			List<ObjectLayoutBox> objectLayoutBoxes =
				objectLayoutTab.getObjectLayoutBoxes();

			ObjectLayoutBox objectLayoutBox = objectLayoutBoxes.get(0);

			List<ObjectLayoutRow> objectLayoutRows =
				objectLayoutBox.getObjectLayoutRows();

			ObjectLayoutRow objectLayoutRow = objectLayoutRows.get(0);

			List<ObjectLayoutColumn> objectLayoutColumns =
				objectLayoutRow.getObjectLayoutColumns();

			ObjectLayoutColumn objectLayoutColumn = objectLayoutColumns.get(0);

			objectLayoutColumn.setSize(13);

			_objectLayoutLocalService.addObjectLayout(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Collections.singletonList(objectLayoutTab));

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			String message = runtimeException.getMessage();

			Assert.assertTrue(
				message.contains(
					"Object layout column size must be more than 0 and less " +
						"than 12"));
		}

		objectLayout = _addObjectLayout();

		_assertObjectLayout(objectLayout);

		_deleteObjectFields();
		_objectLayoutLocalService.deleteObjectLayout(
			objectLayout.getObjectLayoutId());
	}

	@Test
	public void testGetObjectLayout() throws Exception {
		_addObjectLayout();

		List<ObjectLayout> objectLayouts =
			_objectLayoutLocalService.getObjectLayouts(
				_objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		ObjectLayout objectLayout = objectLayouts.get(0);

		Assert.assertNull(objectLayout.getObjectLayoutTabs());

		objectLayout = _objectLayoutLocalService.getObjectLayout(
			objectLayout.getObjectLayoutId());

		_assertObjectLayout(objectLayout);

		_objectLayoutLocalService.deleteObjectLayout(
			objectLayout.getObjectLayoutId());
	}

	private long _addObjectField() throws Exception {
		String name = RandomTestUtil.randomString();

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), false, false, null,
			LocalizedMapUtil.getLocalizedMap(name), StringUtil.randomId(), true,
			"String");

		return objectField.getObjectFieldId();
	}

	private ObjectLayout _addObjectLayout() throws Exception {
		ObjectLayoutTab objectLayoutTab = _objectLayoutTabPersistence.create(0);

		objectLayoutTab.setNameMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
		objectLayoutTab.setPriority(0);
		objectLayoutTab.setObjectLayoutBoxes(
			Arrays.asList(_addObjectLayoutBox(), _addObjectLayoutBox()));

		return _objectLayoutLocalService.addObjectLayout(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Collections.singletonList(objectLayoutTab));
	}

	private ObjectLayoutBox _addObjectLayoutBox() throws Exception {
		ObjectLayoutBox objectLayoutBox = _objectLayoutBoxPersistence.create(0);

		objectLayoutBox.setCollapsable(false);
		objectLayoutBox.setNameMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
		objectLayoutBox.setPriority(0);
		objectLayoutBox.setObjectLayoutRows(
			Arrays.asList(
				_addObjectLayoutRow(), _addObjectLayoutRow(),
				_addObjectLayoutRow()));

		return objectLayoutBox;
	}

	private ObjectLayoutColumn _addObjectLayoutColumn() throws Exception {
		ObjectLayoutColumn objectLayoutColumn =
			_objectLayoutColumnPersistence.create(0);

		objectLayoutColumn.setObjectFieldId(_addObjectField());
		objectLayoutColumn.setPriority(0);

		return objectLayoutColumn;
	}

	private ObjectLayoutRow _addObjectLayoutRow() throws Exception {
		ObjectLayoutRow objectLayoutRow = _objectLayoutRowPersistence.create(0);

		objectLayoutRow.setPriority(0);
		objectLayoutRow.setObjectLayoutColumns(
			Arrays.asList(
				_addObjectLayoutColumn(), _addObjectLayoutColumn(),
				_addObjectLayoutColumn(), _addObjectLayoutColumn()));

		return objectLayoutRow;
	}

	private void _assertObjectLayout(ObjectLayout objectLayout) {
		List<ObjectLayoutTab> objectLayoutTabs =
			objectLayout.getObjectLayoutTabs();

		Assert.assertEquals(
			objectLayoutTabs.toString(), 1, objectLayoutTabs.size());

		ObjectLayoutTab objectLayoutTab = objectLayoutTabs.get(0);

		List<ObjectLayoutBox> objectLayoutBoxes =
			objectLayoutTab.getObjectLayoutBoxes();

		Assert.assertEquals(
			objectLayoutBoxes.toString(), 2, objectLayoutBoxes.size());

		ObjectLayoutBox objectLayoutBox = objectLayoutBoxes.get(0);

		List<ObjectLayoutRow> objectLayoutRows =
			objectLayoutBox.getObjectLayoutRows();

		Assert.assertEquals(
			objectLayoutRows.toString(), 3, objectLayoutRows.size());

		ObjectLayoutRow objectLayoutRow = objectLayoutRows.get(0);

		List<ObjectLayoutColumn> objectLayoutColumns =
			objectLayoutRow.getObjectLayoutColumns();

		Assert.assertEquals(
			objectLayoutColumns.toString(), 4, objectLayoutColumns.size());
	}

	private void _deleteObjectFields() throws Exception {
		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId());

		for (ObjectField objectField : objectFields) {
			_objectFieldLocalService.deleteObjectField(objectField);
		}
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectLayoutBoxPersistence _objectLayoutBoxPersistence;

	@Inject
	private ObjectLayoutColumnPersistence _objectLayoutColumnPersistence;

	@Inject
	private ObjectLayoutLocalService _objectLayoutLocalService;

	@Inject
	private ObjectLayoutRowPersistence _objectLayoutRowPersistence;

	@Inject
	private ObjectLayoutTabPersistence _objectLayoutTabPersistence;

}