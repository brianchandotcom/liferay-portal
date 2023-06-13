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
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationRegistry;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectLayoutBoxConstants;
import com.liferay.object.exception.DefaultObjectLayoutException;
import com.liferay.object.exception.ObjectDefinitionModifiableException;
import com.liferay.object.exception.ObjectLayoutBoxCategorizationTypeException;
import com.liferay.object.exception.ObjectLayoutColumnSizeException;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
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
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

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
@FeatureFlags("LPS-167253")
@RunWith(Arquillian.class)
public class ObjectLayoutLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.object.service"));

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService);
	}

	@Test
	public void testAddObjectLayout() throws Exception {
		AssertUtils.assertFailure(
			DefaultObjectLayoutException.class,
			"All required object fields must be associated to the first tab " +
				"of a default object layout",
			() -> _objectLayoutLocalService.addObjectLayout(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Arrays.asList(_addObjectLayoutTab(), _addObjectLayoutTab())));

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		_objectDefinition =
			ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				_objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		AssertUtils.assertFailure(
			ObjectDefinitionModifiableException.class,
			"A modifiable object definition is required",
			() -> _objectLayoutLocalService.addObjectLayout(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Collections.singletonList(_addObjectLayoutTab())));

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService);

		_objectDefinition.setStorageType(RandomTestUtil.randomString());

		_objectDefinitionLocalService.updateObjectDefinition(_objectDefinition);

		AssertUtils.assertFailure(
			ObjectLayoutBoxCategorizationTypeException.class,
			"Categorization layout box can only be used in object " +
				"definitions with a default storage type",
			() -> {
				ObjectLayoutTab objectLayoutTab =
					_objectLayoutTabPersistence.create(0);

				objectLayoutTab.setNameMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()));
				objectLayoutTab.setPriority(0);
				objectLayoutTab.setObjectLayoutBoxes(
					Arrays.asList(
						_addObjectLayoutBox(),
						_addObjectLayoutBox(
							ObjectLayoutBoxConstants.TYPE_CATEGORIZATION)));

				return _objectLayoutLocalService.addObjectLayout(
					TestPropsValues.getUserId(),
					_objectDefinition.getObjectDefinitionId(), false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					Collections.singletonList(objectLayoutTab));
			});

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService);

		_objectDefinition.setEnableCategorization(false);

		_objectDefinitionLocalService.updateObjectDefinition(_objectDefinition);

		AssertUtils.assertFailure(
			ObjectLayoutBoxCategorizationTypeException.class,
			"Categorization layout box must be enabled to be used",
			() -> {
				ObjectLayoutTab objectLayoutTab =
					_objectLayoutTabPersistence.create(0);

				objectLayoutTab.setNameMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()));
				objectLayoutTab.setObjectLayoutBoxes(
					Arrays.asList(
						_addObjectLayoutBox(),
						_addObjectLayoutBox(
							ObjectLayoutBoxConstants.TYPE_CATEGORIZATION)));
				objectLayoutTab.setPriority(0);

				return _objectLayoutLocalService.addObjectLayout(
					TestPropsValues.getUserId(),
					_objectDefinition.getObjectDefinitionId(), false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					Collections.singletonList(objectLayoutTab));
			});

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService);

		AssertUtils.assertFailure(
			ObjectLayoutBoxCategorizationTypeException.class,
			"Categorization layout box must not have layout rows",
			() -> {
				ObjectLayoutTab objectLayoutTab =
					_objectLayoutTabPersistence.create(0);

				objectLayoutTab.setNameMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()));
				objectLayoutTab.setPriority(0);

				ObjectLayoutBox objectLayoutBox = _addObjectLayoutBox(
					ObjectLayoutBoxConstants.TYPE_CATEGORIZATION);

				objectLayoutBox.setObjectLayoutRows(
					Collections.singletonList(_addObjectLayoutRow()));

				objectLayoutTab.setObjectLayoutBoxes(
					Arrays.asList(_addObjectLayoutBox(), objectLayoutBox));

				return _objectLayoutLocalService.addObjectLayout(
					TestPropsValues.getUserId(),
					_objectDefinition.getObjectDefinitionId(), false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					Collections.singletonList(objectLayoutTab));
			});

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService);

		AssertUtils.assertFailure(
			ObjectLayoutBoxCategorizationTypeException.class,
			"Object layout box must have a type",
			() -> {
				ObjectLayoutTab objectLayoutTab =
					_objectLayoutTabPersistence.create(0);

				objectLayoutTab.setNameMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()));
				objectLayoutTab.setPriority(0);
				objectLayoutTab.setObjectLayoutBoxes(
					Arrays.asList(
						_addObjectLayoutBox(), _addObjectLayoutBox(null)));

				return _objectLayoutLocalService.addObjectLayout(
					TestPropsValues.getUserId(),
					_objectDefinition.getObjectDefinitionId(), false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					Collections.singletonList(objectLayoutTab));
			});

		AssertUtils.assertFailure(
			ObjectLayoutColumnSizeException.class,
			"Object layout column size must be more than 0 and less than 12",
			() -> {
				ObjectLayoutTab objectLayoutTab = _addObjectLayoutTab();

				List<ObjectLayoutBox> objectLayoutBoxes =
					objectLayoutTab.getObjectLayoutBoxes();

				ObjectLayoutBox objectLayoutBox = objectLayoutBoxes.get(0);

				List<ObjectLayoutRow> objectLayoutRows =
					objectLayoutBox.getObjectLayoutRows();

				ObjectLayoutRow objectLayoutRow = objectLayoutRows.get(0);

				List<ObjectLayoutColumn> objectLayoutColumns =
					objectLayoutRow.getObjectLayoutColumns();

				ObjectLayoutColumn objectLayoutColumn = objectLayoutColumns.get(
					0);

				objectLayoutColumn.setSize(13);

				return _objectLayoutLocalService.addObjectLayout(
					TestPropsValues.getUserId(),
					_objectDefinition.getObjectDefinitionId(), false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					Collections.singletonList(objectLayoutTab));
			});

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService);

		AssertUtils.assertFailure(
			ObjectLayoutBoxCategorizationTypeException.class,
			"There can only be one categorization layout box per layout",
			() -> {
				ObjectLayoutTab objectLayoutTab1 =
					_objectLayoutTabPersistence.create(0);

				objectLayoutTab1.setNameMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()));
				objectLayoutTab1.setPriority(0);
				objectLayoutTab1.setObjectLayoutBoxes(
					Arrays.asList(
						_addObjectLayoutBox(),
						_addObjectLayoutBox(
							ObjectLayoutBoxConstants.TYPE_CATEGORIZATION)));

				ObjectLayoutTab objectLayoutTab2 =
					_objectLayoutTabPersistence.create(0);

				objectLayoutTab2.setNameMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()));
				objectLayoutTab2.setObjectLayoutBoxes(
					Arrays.asList(
						_addObjectLayoutBox(),
						_addObjectLayoutBox(
							ObjectLayoutBoxConstants.TYPE_CATEGORIZATION)));

				return _objectLayoutLocalService.addObjectLayout(
					TestPropsValues.getUserId(),
					_objectDefinition.getObjectDefinitionId(), false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					Arrays.asList(objectLayoutTab1, objectLayoutTab2));
			});

		_deleteObjectFields();

		AssertUtils.assertFailure(
			DefaultObjectLayoutException.class,
			"There can only be one default object layout",
			() -> {
				ObjectLayoutTab objectLayoutTab = _addObjectLayoutTab();

				_objectLayoutLocalService.addObjectLayout(
					TestPropsValues.getUserId(),
					_objectDefinition.getObjectDefinitionId(), true,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					Collections.singletonList(objectLayoutTab));

				return _objectLayoutLocalService.addObjectLayout(
					TestPropsValues.getUserId(),
					_objectDefinition.getObjectDefinitionId(), true,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					Collections.singletonList(objectLayoutTab));
			});

		ObjectLayout objectLayout = _addObjectLayout();

		_assertObjectLayout(objectLayout);

		_deleteObjectFields();

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		_objectDefinition =
			ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				_objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		objectLayout = _addObjectLayout();

		_assertObjectLayout(objectLayout);

		_objectLayoutLocalService.deleteObjectLayout(
			objectLayout.getObjectLayoutId());
	}

	@Test
	public void testDeleteObjectLayout() throws Exception {
		ObjectLayout objectLayout = _addObjectLayout();

		List<ObjectLayoutTab> objectLayoutTabs =
			_objectLayoutTabPersistence.findByObjectLayoutId(
				objectLayout.getObjectLayoutId());

		Assert.assertFalse(objectLayoutTabs.isEmpty());

		for (ObjectLayoutTab objectLayoutTab : objectLayoutTabs) {
			List<ObjectLayoutBox> objectLayoutBoxes =
				_objectLayoutBoxPersistence.findByObjectLayoutTabId(
					objectLayoutTab.getObjectLayoutTabId());

			objectLayoutTab.setObjectLayoutBoxes(objectLayoutBoxes);

			Assert.assertFalse(objectLayoutBoxes.isEmpty());

			for (ObjectLayoutBox objectLayoutBox : objectLayoutBoxes) {
				List<ObjectLayoutRow> objectLayoutRows =
					_objectLayoutRowPersistence.findByObjectLayoutBoxId(
						objectLayoutBox.getObjectLayoutBoxId());

				Assert.assertFalse(objectLayoutRows.isEmpty());
			}
		}

		_objectLayoutLocalService.deleteObjectLayout(objectLayout);

		for (ObjectLayoutTab objectLayoutTab : objectLayoutTabs) {
			List<ObjectLayoutBox> objectLayoutBoxes =
				_objectLayoutBoxPersistence.findByObjectLayoutTabId(
					objectLayoutTab.getObjectLayoutTabId());

			Assert.assertTrue(objectLayoutBoxes.isEmpty());

			for (ObjectLayoutBox objectLayoutBox :
					objectLayoutTab.getObjectLayoutBoxes()) {

				List<ObjectLayoutRow> objectLayoutRows =
					_objectLayoutRowPersistence.findByObjectLayoutBoxId(
						objectLayoutBox.getObjectLayoutBoxId());

				Assert.assertTrue(objectLayoutRows.isEmpty());
			}
		}
	}

	@Test
	public void testGetObjectLayout() throws Exception {
		_addObjectLayout();

		List<ObjectLayout> objectLayouts =
			_objectLayoutLocalService.getObjectLayouts(
				_objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		ObjectLayout objectLayout = objectLayouts.get(0);

		_assertObjectLayout(objectLayout);

		_objectLayoutLocalService.deleteObjectLayout(
			objectLayout.getObjectLayoutId());
	}

	@Test
	public void testUpdateObjectLayout() throws Exception {
		List<ScreenNavigationCategory> screenNavigationCategories =
			_screenNavigationRegistry.getScreenNavigationCategories(
				_objectDefinition.getClassName(), TestPropsValues.getUser(),
				null);

		Assert.assertTrue(screenNavigationCategories.isEmpty());

		ObjectLayoutTab objectLayoutTab1 = _addObjectLayoutTab();

		ObjectLayout objectLayout = _objectLayoutLocalService.addObjectLayout(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Collections.singletonList(objectLayoutTab1));

		screenNavigationCategories =
			_screenNavigationRegistry.getScreenNavigationCategories(
				_objectDefinition.getClassName(), TestPropsValues.getUser(),
				null);

		Assert.assertEquals(
			screenNavigationCategories.toString(), 1,
			screenNavigationCategories.size());

		_addObjectLayout();

		screenNavigationCategories =
			_screenNavigationRegistry.getScreenNavigationCategories(
				_objectDefinition.getClassName(), TestPropsValues.getUser(),
				null);

		Assert.assertEquals(
			screenNavigationCategories.toString(), 1,
			screenNavigationCategories.size());

		_objectLayoutLocalService.updateObjectLayout(
			objectLayout.getObjectLayoutId(), false, objectLayout.getNameMap(),
			Arrays.asList(objectLayoutTab1));

		screenNavigationCategories =
			_screenNavigationRegistry.getScreenNavigationCategories(
				_objectDefinition.getClassName(), TestPropsValues.getUser(),
				null);

		Assert.assertTrue(screenNavigationCategories.isEmpty());
	}

	private long _addObjectField() throws Exception {
		String name = RandomTestUtil.randomString();

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(name)
			).name(
				StringUtil.randomId()
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).required(
				true
			).build());

		return objectField.getObjectFieldId();
	}

	private ObjectLayout _addObjectLayout() throws Exception {
		return _objectLayoutLocalService.addObjectLayout(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Collections.singletonList(_addObjectLayoutTab()));
	}

	private ObjectLayoutBox _addObjectLayoutBox() throws Exception {
		return _addObjectLayoutBox(ObjectLayoutBoxConstants.TYPE_REGULAR);
	}

	private ObjectLayoutBox _addObjectLayoutBox(String type) throws Exception {
		ObjectLayoutBox objectLayoutBox = _objectLayoutBoxPersistence.create(0);

		objectLayoutBox.setCollapsable(false);
		objectLayoutBox.setNameMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
		objectLayoutBox.setPriority(0);
		objectLayoutBox.setType(type);

		if (StringUtil.equals(type, ObjectLayoutBoxConstants.TYPE_REGULAR)) {
			objectLayoutBox.setObjectLayoutRows(
				Arrays.asList(
					_addObjectLayoutRow(), _addObjectLayoutRow(),
					_addObjectLayoutRow()));
		}

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

	private ObjectLayoutTab _addObjectLayoutTab() throws Exception {
		ObjectLayoutTab objectLayoutTab = _objectLayoutTabPersistence.create(0);

		objectLayoutTab.setNameMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
		objectLayoutTab.setObjectLayoutBoxes(
			Arrays.asList(_addObjectLayoutBox(), _addObjectLayoutBox()));

		return objectLayoutTab;
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
				_objectDefinition.getObjectDefinitionId(), false);

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

	@Inject
	private ScreenNavigationRegistry _screenNavigationRegistry;

}