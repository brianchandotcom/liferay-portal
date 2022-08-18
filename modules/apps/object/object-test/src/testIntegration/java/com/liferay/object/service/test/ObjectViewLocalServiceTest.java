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
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.exception.DefaultObjectViewException;
import com.liferay.object.exception.ObjectViewColumnFieldNameException;
import com.liferay.object.exception.ObjectViewFilterColumnException;
import com.liferay.object.exception.ObjectViewSortColumnException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectView;
import com.liferay.object.model.ObjectViewColumn;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.model.ObjectViewSortColumn;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.service.persistence.ObjectViewColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewFilterColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewSortColumnPersistence;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

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
public class ObjectViewLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				TestPropsValues.getUserId(),
				Collections.singletonMap(LocaleUtil.US, "Countries"));

		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			listTypeDefinition.getListTypeDefinitionId(), StringUtil.randomId(),
			Collections.singletonMap(LocaleUtil.US, "Brazil"));

		ObjectField objectField = ObjectFieldUtil.createObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
			ObjectFieldConstants.DB_TYPE_STRING, "country");

		objectField.setListTypeDefinitionId(
			listTypeDefinition.getListTypeDefinitionId());

		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService,
			Arrays.asList(
				objectField,
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "name")));
	}

	@Test
	public void testAddObjectView() throws Exception {
		ObjectView objectView = _objectViewLocalService.addObjectView(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Arrays.asList(
				_createObjectViewColumn("Able", "able"),
				_createObjectViewColumn("Baker", "baker")),
			Arrays.asList(
				_createObjectViewFilterColumn(
					ObjectViewFilterColumnConstants.FILTER_TYPE_INCLUDES,
					"{\"includes\": [\"brazil\"]}", "country"),
				_createObjectViewFilterColumn(null, null, "dateCreated")),
			Arrays.asList(
				_createObjectViewSortColumn("able", "asc"),
				_createObjectViewSortColumn("baker", "asc")));

		_assertFailureAddOrUpdateObjectView(
			true, DefaultObjectViewException.class,
			"There can only be one default object view", null,
			Arrays.asList(
				_createObjectViewColumn("Easy", "easy"),
				_createObjectViewColumn("Fox", "fox")),
			Collections.emptyList(),
			Arrays.asList(
				_createObjectViewSortColumn("easy", "asc"),
				_createObjectViewSortColumn("fox", "asc")));
		_assertFailureAddOrUpdateObjectView(
			false, ObjectViewColumnFieldNameException.class,
			"There is no object field with the name: zebra", null,
			Arrays.asList(
				_createObjectViewColumnWithNonexistentObjectFieldName()),
			Collections.emptyList(), Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			false, ObjectViewColumnFieldNameException.class,
			"There is already an object view column with the object field " +
				"name: roger",
			null, _createObjectViewColumnsWithDuplicateObjectFieldName(),
			Collections.emptyList(), Collections.emptyList());

		_objectViewLocalService.addObjectView(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			_createObjectViewColumnsWithoutLabel(), Collections.emptyList(),
			Collections.emptyList());

		_assertFailureAddOrUpdateObjectView(
			false, ObjectViewFilterColumnException.class,
			"Object field name is null", null, Collections.emptyList(),
			Arrays.asList(_createObjectViewFilterColumn(null, null, null)),
			Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			false, ObjectViewFilterColumnException.class,
			"Object field name \"creator\" is not filterable", null,
			Collections.emptyList(),
			Arrays.asList(_createObjectViewFilterColumn(null, null, "creator")),
			Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			false, ObjectViewFilterColumnException.class,
			"Object field name \"country\" needs to have the filter type and " +
				"JSON specified",
			null, Collections.emptyList(),
			Arrays.asList(
				_createObjectViewFilterColumn(
					null, RandomTestUtil.randomString(), "country")),
			Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			false, ObjectViewFilterColumnException.class,
			"Object field name \"country\" needs to have the filter type and " +
				"JSON specified",
			null, Collections.emptyList(),
			Arrays.asList(
				_createObjectViewFilterColumn(
					RandomTestUtil.randomString(), null, "country")),
			Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			false, ObjectViewFilterColumnException.class,
			"Object field name \"name\" is not filterable", null,
			Collections.emptyList(),
			Arrays.asList(_createObjectViewFilterColumn(null, null, "name")),
			Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			false, ObjectViewSortColumnException.class,
			"There is no object view column with the name: zulu", null,
			Arrays.asList(_createObjectViewColumn("Item", "item")),
			Collections.emptyList(),
			Arrays.asList(
				_createObjectViewSortColumnWithWrongObjectFieldName()));
		_assertFailureAddOrUpdateObjectView(
			false, ObjectViewSortColumnException.class,
			"There is no sort order of type: zulu", null,
			Arrays.asList(_createObjectViewColumn("King", "king")),
			Collections.emptyList(),
			Arrays.asList(_createObjectViewSortColumn("king", "zulu")));

		_deleteObjectFields();

		_objectViewLocalService.deleteObjectView(objectView.getObjectViewId());

		objectView = _addObjectView();

		_assertObjectView(objectView);

		_deleteObjectFields();

		_objectViewLocalService.deleteObjectView(objectView.getObjectViewId());
	}

	@Test
	public void testGetObjectView() throws Exception {
		ObjectView objectView = _addObjectView();

		objectView = _objectViewLocalService.getObjectView(
			objectView.getObjectViewId());

		_assertObjectView(objectView);

		_deleteObjectFields();

		_objectViewLocalService.deleteObjectView(objectView.getObjectViewId());
	}

	@Test
	public void testUpdateObjectView() throws Exception {
		ObjectView objectView = _addObjectView();

		objectView = _objectViewLocalService.updateObjectView(
			objectView.getObjectViewId(), objectView.isDefaultObjectView(),
			objectView.getNameMap(),
			Collections.singletonList(_createObjectViewColumn("Fox", "fox")),
			Collections.emptyList(),
			Collections.singletonList(
				_createObjectViewSortColumn("fox", "desc")));

		List<ObjectViewColumn> objectViewColumns =
			objectView.getObjectViewColumns();

		Assert.assertEquals(
			objectViewColumns.toString(), 1, objectViewColumns.size());

		List<ObjectViewSortColumn> objectViewSortColumns =
			objectView.getObjectViewSortColumns();

		Assert.assertEquals(
			objectViewSortColumns.toString(), 1, objectViewSortColumns.size());

		_assertFailureAddOrUpdateObjectView(
			objectView.isDefaultObjectView(),
			ObjectViewColumnFieldNameException.class,
			"There is already an object view column with the object field " +
				"name: roger",
			objectView, _createObjectViewColumnsWithDuplicateObjectFieldName(),
			Collections.emptyList(), Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			objectView.isDefaultObjectView(),
			ObjectViewColumnFieldNameException.class,
			"There is no object field with the name: zebra", objectView,
			Collections.singletonList(
				_createObjectViewColumnWithNonexistentObjectFieldName()),
			Collections.emptyList(), Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			objectView.isDefaultObjectView(),
			ObjectViewFilterColumnException.class, "Object field name is null",
			objectView, Collections.emptyList(),
			Arrays.asList(_createObjectViewFilterColumn(null, null, null)),
			Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			objectView.isDefaultObjectView(),
			ObjectViewFilterColumnException.class,
			"Object field name \"creator\" is not filterable", objectView,
			Collections.emptyList(),
			Arrays.asList(_createObjectViewFilterColumn(null, null, "creator")),
			Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			objectView.isDefaultObjectView(),
			ObjectViewFilterColumnException.class,
			"Object field name \"country\" needs to have the filter type and " +
				"JSON specified",
			objectView, Collections.emptyList(),
			Arrays.asList(
				_createObjectViewFilterColumn(
					null, RandomTestUtil.randomString(), "country")),
			Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			objectView.isDefaultObjectView(),
			ObjectViewFilterColumnException.class,
			"Object field name \"country\" needs to have the filter type and " +
				"JSON specified",
			objectView, Collections.emptyList(),
			Arrays.asList(
				_createObjectViewFilterColumn(
					RandomTestUtil.randomString(), null, "country")),
			Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			objectView.isDefaultObjectView(),
			ObjectViewFilterColumnException.class,
			"Object field name \"name\" is not filterable", objectView,
			Collections.emptyList(),
			Arrays.asList(_createObjectViewFilterColumn(null, null, "name")),
			Collections.emptyList());
		_assertFailureAddOrUpdateObjectView(
			objectView.isDefaultObjectView(),
			ObjectViewSortColumnException.class,
			"There is no object view column with the name: king", objectView,
			Collections.singletonList(_createObjectViewColumn("Jig", "jig")),
			Collections.emptyList(),
			Collections.singletonList(
				_createObjectViewSortColumn("king", "desc")));
		_assertFailureAddOrUpdateObjectView(
			objectView.isDefaultObjectView(),
			ObjectViewSortColumnException.class,
			"There is no sort order of type: zulu", objectView,
			Collections.singletonList(_createObjectViewColumn("Love", "love")),
			Collections.emptyList(),
			Collections.singletonList(
				_createObjectViewSortColumn("love", "zulu")));

		objectView = _objectViewLocalService.updateObjectView(
			objectView.getObjectViewId(), objectView.isDefaultObjectView(),
			objectView.getNameMap(), Collections.emptyList(),
			Collections.emptyList(), Collections.emptyList());

		objectViewColumns = objectView.getObjectViewColumns();

		Assert.assertEquals(
			objectViewColumns.toString(), 0, objectViewColumns.size());

		objectViewSortColumns = objectView.getObjectViewSortColumns();

		Assert.assertEquals(
			objectViewSortColumns.toString(), 0, objectViewSortColumns.size());

		_objectViewLocalService.updateObjectView(
			objectView.getObjectViewId(), objectView.isDefaultObjectView(),
			objectView.getNameMap(), _createObjectViewColumnsWithoutLabel(),
			Collections.emptyList(), Collections.emptyList());

		_deleteObjectFields();

		_objectViewLocalService.deleteObjectView(objectView.getObjectViewId());
	}

	private String _addObjectField(
			String objectFieldLabel, String objectFieldName)
		throws Exception {

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, false, null,
			LocalizedMapUtil.getLocalizedMap(objectFieldLabel), objectFieldName,
			true, false, Collections.emptyList());

		return objectField.getName();
	}

	private ObjectView _addObjectView() throws Exception {
		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				TestPropsValues.getUserId(),
				Collections.singletonMap(LocaleUtil.US, "Countries"));

		_listTypeEntryLocalService.addListTypeEntry(
			TestPropsValues.getUserId(),
			listTypeDefinition.getListTypeDefinitionId(), StringUtil.randomId(),
			Collections.singletonMap(LocaleUtil.US, "Brazil"));

		ObjectField objectField = ObjectFieldUtil.createObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
			ObjectFieldConstants.DB_TYPE_STRING, "country");

		objectField.setListTypeDefinitionId(
			listTypeDefinition.getListTypeDefinitionId());
		objectField.setObjectDefinitionId(
			_objectDefinition.getObjectDefinitionId());

		_objectFieldLocalService.addObjectField(objectField);

		return _objectViewLocalService.addObjectView(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Arrays.asList(
				_createObjectViewColumn("Able", "able"),
				_createObjectViewColumn("Baker", "baker")),
			Arrays.asList(
				_createObjectViewFilterColumn(
					ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES,
					"{\"excludes\": [3, 4]}", "status"),
				_createObjectViewFilterColumn(
					ObjectViewFilterColumnConstants.FILTER_TYPE_INCLUDES,
					"{\"includes\": [\"brazil\"]}", "country"),
				_createObjectViewFilterColumn(null, null, "dateCreated")),
			Arrays.asList(
				_createObjectViewSortColumn("able", "asc"),
				_createObjectViewSortColumn("baker", "asc")));
	}

	private void _assertFailureAddOrUpdateObjectView(
		boolean defaultObjectView, Class<?> expectedExceptionClass,
		String message, ObjectView objectView,
		List<ObjectViewColumn> objectViewColumns,
		List<ObjectViewFilterColumn> objectViewFilterColumns,
		List<ObjectViewSortColumn> objectViewSortColumns) {

		try {
			if (objectView != null) {
				_objectViewLocalService.updateObjectView(
					objectView.getObjectViewId(), defaultObjectView,
					objectView.getNameMap(), objectViewColumns,
					objectViewFilterColumns, objectViewSortColumns);
			}
			else {
				_objectViewLocalService.addObjectView(
					TestPropsValues.getUserId(),
					_objectDefinition.getObjectDefinitionId(),
					defaultObjectView,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					objectViewColumns, objectViewFilterColumns,
					objectViewSortColumns);
			}

			Assert.fail();
		}
		catch (PortalException portalException) {
			if (expectedExceptionClass.isInstance(portalException)) {
				Assert.assertEquals(message, portalException.getMessage());
			}
		}
	}

	private void _assertObjectView(ObjectView objectView) {
		List<ObjectViewColumn> objectViewColumns =
			objectView.getObjectViewColumns();

		Assert.assertEquals(
			objectViewColumns.toString(), 2, objectViewColumns.size());

		List<ObjectViewFilterColumn> objectViewFilterColumns =
			objectView.getObjectViewFilterColumns();

		Assert.assertEquals(
			objectViewFilterColumns.toString(), 3,
			objectViewFilterColumns.size());

		List<ObjectViewSortColumn> objectViewSortColumns =
			objectView.getObjectViewSortColumns();

		Assert.assertEquals(
			objectViewSortColumns.toString(), 2, objectViewSortColumns.size());
	}

	private ObjectViewColumn _createObjectViewColumn(
			String objectFieldLabel, String objectFieldName)
		throws Exception {

		ObjectViewColumn objectViewColumn = _objectViewColumnPersistence.create(
			0);

		objectViewColumn.setLabelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
		objectViewColumn.setObjectFieldName(
			_addObjectField(objectFieldLabel, objectFieldName));
		objectViewColumn.setPriority(0);

		return objectViewColumn;
	}

	private List<ObjectViewColumn>
			_createObjectViewColumnsWithDuplicateObjectFieldName()
		throws Exception {

		ObjectViewColumn objectViewColumn1 = _createObjectViewColumn(
			"Roger", "roger");

		ObjectViewColumn objectViewColumn2 = _createObjectViewColumn(
			RandomTestUtil.randomString(),
			StringUtil.toLowerCase(RandomStringUtils.randomAlphabetic(5)));

		objectViewColumn2.setObjectFieldName("roger");

		return ListUtil.fromArray(objectViewColumn1, objectViewColumn2);
	}

	private List<ObjectViewColumn> _createObjectViewColumnsWithoutLabel()
		throws Exception {

		ObjectViewColumn objectViewColumn = _createObjectViewColumn(
			RandomTestUtil.randomString(),
			StringUtil.toLowerCase(RandomStringUtils.randomAlphabetic(5)));

		objectViewColumn.setLabelMap(LocalizedMapUtil.getLocalizedMap(""));

		return ListUtil.fromArray(objectViewColumn);
	}

	private ObjectViewColumn
			_createObjectViewColumnWithNonexistentObjectFieldName()
		throws Exception {

		ObjectViewColumn objectViewColumn = _createObjectViewColumn(
			RandomTestUtil.randomString(),
			StringUtil.toLowerCase(RandomStringUtils.randomAlphabetic(5)));

		objectViewColumn.setObjectFieldName("zebra");

		return objectViewColumn;
	}

	private ObjectViewFilterColumn _createObjectViewFilterColumn(
		String filterType, String json, String objectFieldName) {

		ObjectViewFilterColumn objectViewFilterColumn =
			_objectViewFilterColumnPersistence.create(0);

		objectViewFilterColumn.setFilterType(filterType);
		objectViewFilterColumn.setJSON(json);
		objectViewFilterColumn.setObjectFieldName(objectFieldName);

		return objectViewFilterColumn;
	}

	private ObjectViewSortColumn _createObjectViewSortColumn(
		String objectFieldName, String sortOrder) {

		ObjectViewSortColumn objectViewSortColumn =
			_objectViewSortColumnPersistence.create(0);

		objectViewSortColumn.setObjectFieldName(objectFieldName);
		objectViewSortColumn.setPriority(0);
		objectViewSortColumn.setSortOrder(sortOrder);

		return objectViewSortColumn;
	}

	private ObjectViewSortColumn
		_createObjectViewSortColumnWithWrongObjectFieldName() {

		ObjectViewSortColumn objectViewSortColumn = _createObjectViewSortColumn(
			"item", "asc");

		objectViewSortColumn.setObjectFieldName("zulu");

		return objectViewSortColumn;
	}

	private void _deleteObjectFields() throws Exception {
		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId());

		for (ObjectField objectField : objectFields) {
			_objectFieldLocalService.deleteObjectField(objectField);
		}
	}

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectViewColumnPersistence _objectViewColumnPersistence;

	@Inject
	private ObjectViewFilterColumnPersistence
		_objectViewFilterColumnPersistence;

	@Inject
	private ObjectViewLocalService _objectViewLocalService;

	@Inject
	private ObjectViewSortColumnPersistence _objectViewSortColumnPersistence;

}