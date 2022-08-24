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
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.DuplicateObjectRelationshipException;
import com.liferay.object.exception.ObjectRelationshipParameterObjectFieldIdException;
import com.liferay.object.exception.ObjectRelationshipTypeException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.system.BaseSystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class ObjectRelationshipLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_systemObjectDefinition1 =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, 1,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

		Bundle bundle = FrameworkUtil.getBundle(
			ObjectRelationshipLocalServiceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		bundleContext.registerService(
			SystemObjectDefinitionMetadata.class,
			new TestSystemObjectDefinitionMetadata(
				_systemObjectDefinition1.getModelClass(),
				_systemObjectDefinition1.getName()),
			new HashMapDictionary<>());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(
			_systemObjectDefinition1);
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService,
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", RandomTestUtil.randomString(),
					StringUtil.randomId())));

		_objectDefinition1 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId());

		_objectDefinition2 = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService,
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", RandomTestUtil.randomString(),
					StringUtil.randomId())));

		_objectDefinition2 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition2.getObjectDefinitionId());

		_systemObjectDefinition2 =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, 1,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));
	}

	@Test
	public void testAddObjectRelationship() throws Exception {
		//_testAddObjectRelationship(
		//	ObjectRelationshipConstants.TYPE_ONE_TO_ONE);
		_testAddObjectRelationship(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		String name = StringUtil.randomId();

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				name, ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		Assert.assertEquals(
			StringBundler.concat(
				"R_", objectRelationship.getCompanyId(),
				_objectDefinition1.getShortName(), "_",
				_objectDefinition2.getShortName(), "_", name),
			objectRelationship.getDBTableName());
		Assert.assertTrue(
			_hasColumn(
				objectRelationship.getDBTableName(),
				_objectDefinition1.getPKObjectFieldDBColumnName()));
		Assert.assertTrue(
			_hasColumn(
				objectRelationship.getDBTableName(),
				_objectDefinition2.getPKObjectFieldDBColumnName()));

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		Assert.assertFalse(_hasTable(objectRelationship.getDBTableName()));
	}

	@Test
	public void testAddSystemObjectRelationship() throws Exception {
		ObjectRelationship objectRelationship =
			_testAddSystemObjectRelationship(
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		Assert.assertNotNull(objectRelationship);

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		objectRelationship = _testAddSystemObjectRelationship(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertNotNull(objectRelationship);

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		try {
			_testAddSystemObjectRelationship(
				ObjectRelationshipConstants.TYPE_ONE_TO_ONE);

			Assert.fail();
		}
		catch (ObjectRelationshipTypeException
					objectRelationshipTypeException) {

			String message = objectRelationshipTypeException.getMessage();

			Assert.assertTrue(
				message.contains("Invalid type for system object definition"));
		}

		_testSystemObjectRelationshipOneToMany();
		_testSystemObjectRelationshipManyToMany();
	}

	@Test
	public void testUpdateObjectRelationship() throws Exception {
		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap("Able"), StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Able"),
			objectRelationship.getLabelMap());

		objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(), 0,
				objectRelationship.getDeletionType(),
				LocalizedMapUtil.getLocalizedMap("Baker"));

		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Baker"),
			objectRelationship.getLabelMap());
	}

	private boolean _hasColumn(String tableName, String columnName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasColumn(tableName, columnName);
		}
	}

	private boolean _hasTable(String tableName) throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasTable(tableName);
		}
	}

	private void _testAddObjectRelationship(String type) throws Exception {
		String name = StringUtil.randomId();

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				name, type);

		String objectFieldNamePrefix = "r_" + name + "_";

		Assert.assertTrue(
			_hasColumn(
				_objectDefinition2.getExtensionDBTableName(),
				objectFieldNamePrefix +
					_objectDefinition1.getPKObjectFieldName()));
		Assert.assertNotNull(
			_objectFieldLocalService.fetchObjectField(
				_objectDefinition2.getObjectDefinitionId(),
				objectFieldNamePrefix +
					_objectDefinition1.getPKObjectFieldName()));

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		Assert.assertFalse(
			_hasColumn(
				_objectDefinition1.getExtensionDBTableName(),
				objectFieldNamePrefix +
					_objectDefinition2.getPKObjectFieldName()));
		Assert.assertNull(
			_objectFieldLocalService.fetchObjectField(
				_objectDefinition2.getObjectDefinitionId(),
				objectFieldNamePrefix +
					_objectDefinition1.getPKObjectFieldName()));

		try {
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(),
				RandomTestUtil.randomLong(),
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				name, type);
		}
		catch (ObjectRelationshipParameterObjectFieldIdException
					objectRelationshipParameterObjectFieldIdException) {

			Assert.assertEquals(
				"Object definition " + _objectDefinition1.getName() +
					" does not allow a parameter object field ID",
				objectRelationshipParameterObjectFieldIdException.getMessage());
		}
	}

	private ObjectRelationship _testAddSystemObjectRelationship(String type)
		throws Exception {

		return _objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			_systemObjectDefinition2.getObjectDefinitionId(),
			_objectDefinition1.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), type);
	}

	private void _testSystemObjectRelationshipManyToMany() throws Exception {
		for (String deletionType :
				Arrays.asList(
					ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
					ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
					ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE)) {

			String relationshipName = StringUtil.randomId();

			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.addObjectRelationship(
					TestPropsValues.getUserId(),
					_objectDefinition1.getObjectDefinitionId(),
					_systemObjectDefinition2.getObjectDefinitionId(), 0,
					deletionType,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					relationshipName,
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

			Assert.assertEquals(
				StringBundler.concat(
					"R_", objectRelationship.getCompanyId(),
					_objectDefinition1.getShortName(), "_",
					_systemObjectDefinition2.getShortName(), "_",
					relationshipName),
				objectRelationship.getDBTableName());
			Assert.assertTrue(
				_hasColumn(
					objectRelationship.getDBTableName(),
					_objectDefinition1.getPKObjectFieldDBColumnName()));
			Assert.assertTrue(
				_hasColumn(
					objectRelationship.getDBTableName(),
					_systemObjectDefinition2.getPKObjectFieldDBColumnName()));

			_objectRelationshipLocalService.deleteObjectRelationship(
				objectRelationship);

			Assert.assertFalse(_hasTable(objectRelationship.getDBTableName()));
		}

		try {
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"able", ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		}
		catch (DuplicateObjectRelationshipException
					duplicateObjectRelationshipException) {

			Assert.assertEquals(
				"Duplicate name able",
				duplicateObjectRelationshipException.getMessage());
		}

		try {
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_systemObjectDefinition1.getObjectDefinitionId(),
				_objectDefinition1.getObjectDefinitionId(),
				RandomTestUtil.randomLong(),
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		}
		catch (ObjectRelationshipParameterObjectFieldIdException
					objectRelationshipParameterObjectFieldIdException) {

			Assert.assertEquals(
				"Object relationship type " +
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY +
						" does not allow a parameter object field ID",
				objectRelationshipParameterObjectFieldIdException.getMessage());
		}

		try {
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition1.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		}
		catch (ObjectRelationshipTypeException
					objectRelationshipTypeException) {

			Assert.assertEquals(
				"Many to many self relationships are not allowed",
				objectRelationshipTypeException.getMessage());
		}

		try {
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_systemObjectDefinition2.getObjectDefinitionId(),
				_systemObjectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		}
		catch (ObjectRelationshipTypeException
					objectRelationshipTypeException) {

			Assert.assertEquals(
				"Many to many self relationships are not allowed",
				objectRelationshipTypeException.getMessage());
		}

		_objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(),
			_systemObjectDefinition2.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
	}

	private void _testSystemObjectRelationshipOneToMany() throws Exception {
		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_systemObjectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		String objectFieldNamePrefix =
			"r_" + objectRelationship.getName() + "_";

		Assert.assertTrue(
			_hasColumn(
				_systemObjectDefinition2.getExtensionDBTableName(),
				objectFieldNamePrefix +
					_objectDefinition1.getPKObjectFieldName()));
		Assert.assertNotNull(
			_objectFieldLocalService.fetchObjectField(
				_systemObjectDefinition2.getObjectDefinitionId(),
				objectFieldNamePrefix +
					_objectDefinition1.getPKObjectFieldName()));

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);

		Assert.assertNull(
			_objectFieldLocalService.fetchObjectField(
				_systemObjectDefinition2.getObjectDefinitionId(),
				objectFieldNamePrefix +
					_objectDefinition1.getPKObjectFieldName()));

		try {
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_systemObjectDefinition1.getObjectDefinitionId(),
				_objectDefinition1.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		}
		catch (ObjectRelationshipParameterObjectFieldIdException
					objectRelationshipParameterObjectFieldIdException) {

			Assert.assertEquals(
				"Object definition " + _systemObjectDefinition1.getName() +
					" requires a parameter object field ID",
				objectRelationshipParameterObjectFieldIdException.getMessage());
		}

		long parameterObjectFieldId = RandomTestUtil.randomLong();

		try {
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_systemObjectDefinition1.getObjectDefinitionId(),
				_objectDefinition1.getObjectDefinitionId(),
				parameterObjectFieldId,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		}
		catch (ObjectRelationshipParameterObjectFieldIdException
					objectRelationshipParameterObjectFieldIdException) {

			Assert.assertEquals(
				"Parameter object field ID " + parameterObjectFieldId +
					" does not exist",
				objectRelationshipParameterObjectFieldIdException.getMessage());
		}

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition2.getObjectDefinitionId());

		ObjectField objectField = objectFields.get(0);

		try {
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_systemObjectDefinition1.getObjectDefinitionId(),
				_objectDefinition1.getObjectDefinitionId(),
				objectField.getObjectFieldId(),
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		}
		catch (ObjectRelationshipParameterObjectFieldIdException
					objectRelationshipParameterObjectFieldIdException) {

			Assert.assertEquals(
				StringBundler.concat(
					"Parameter object field ID ",
					objectField.getObjectFieldId(),
					" does not belong to object definition ",
					_objectDefinition1.getName()),
				objectRelationshipParameterObjectFieldIdException.getMessage());
		}

		objectFields = _objectFieldLocalService.getObjectFields(
			_objectDefinition1.getObjectDefinitionId());

		objectField = objectFields.get(0);

		try {
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_systemObjectDefinition1.getObjectDefinitionId(),
				_objectDefinition1.getObjectDefinitionId(),
				objectField.getObjectFieldId(),
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		}
		catch (ObjectRelationshipParameterObjectFieldIdException
					objectRelationshipParameterObjectFieldIdException) {

			Assert.assertEquals(
				"Parameter object field ID " + objectField.getObjectFieldId() +
					" does not belong to a relationship object field",
				objectRelationshipParameterObjectFieldIdException.getMessage());
		}

		String objectRelationshipName = StringUtil.randomId();

		_objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			objectRelationshipName,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		objectField = _objectFieldLocalService.getObjectField(
			_objectDefinition2.getObjectDefinitionId(),
			StringBundler.concat(
				"r_", objectRelationshipName, "_",
				_objectDefinition1.getPKObjectFieldName()));

		_objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			_systemObjectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(),
			objectField.getObjectFieldId(),
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
	}

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	private static ObjectDefinition _systemObjectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _systemObjectDefinition2;

	private static class TestSystemObjectDefinitionMetadata
		extends BaseSystemObjectDefinitionMetadata {

		public TestSystemObjectDefinitionMetadata(
			Class<?> modelClass, String name) {

			_modelClass = modelClass;
			_name = name;
		}

		@Override
		public Map<Locale, String> getLabelMap() {
			return null;
		}

		@Override
		public Class<?> getModelClass() {
			return _modelClass;
		}

		@Override
		public String getModelClassName() {
			return null;
		}

		@Override
		public String getName() {
			return _name;
		}

		@Override
		public List<ObjectField> getObjectFields() {
			return null;
		}

		@Override
		public Map<Locale, String> getPluralLabelMap() {
			return null;
		}

		@Override
		public Column<?, Long> getPrimaryKeyColumn() {
			return null;
		}

		@Override
		public String getRESTContextPath() {
			return "/o/test-endpoint/rel/{relId}/entries";
		}

		@Override
		public String getScope() {
			return null;
		}

		@Override
		public Table getTable() {
			return null;
		}

		@Override
		public int getVersion() {
			return 1;
		}

		private final Class<?> _modelClass;
		private final String _name;

	}

}