/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.db.index;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.internal.dao.db.ObjectDBManagerUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTableFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.sql.Connection;

import java.util.Collections;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Mario Gomes
 */
public class ObjectIndexUpdaterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_objectIndexUpdater, "_companyLocalService", _companyLocalService);
		ReflectionTestUtil.setFieldValue(
			_objectIndexUpdater, "_objectDefinitionLocalService",
			_objectDefinitionLocalService);
		ReflectionTestUtil.setFieldValue(
			_objectIndexUpdater, "_objectFieldLocalService",
			_objectFieldLocalService);
		ReflectionTestUtil.setFieldValue(
			_objectIndexUpdater, "_objectRelationshipLocalService",
			_objectRelationshipLocalService);

		Mockito.doAnswer(
			invocation -> {
				UnsafeConsumer<Long, Exception> unsafeConsumer =
					invocation.getArgument(0);

				unsafeConsumer.accept(_COMPANY_ID);

				return null;
			}
		).when(
			_companyLocalService
		).forEachCompanyId(
			Mockito.<UnsafeConsumer<Long, Exception>>any()
		);
	}

	@Test
	public void testUpdateIndexes() throws Exception {
		_testUpdateIndexesWithManyToManyObjectRelationship();

		_testUpdateIndexesWithOneToManyObjectRelationship();
	}

	private Column<DynamicObjectRelationshipMappingTable, Long> _mockColumn(
		String name) {

		Column<DynamicObjectRelationshipMappingTable, Long> column =
			Mockito.mock(Column.class);

		Mockito.when(
			column.getName()
		).thenReturn(
			name
		);

		return column;
	}

	private DynamicObjectRelationshipMappingTable
		_mockDynamicObjectRelationshipMappingTable(
			MockedStatic<DynamicObjectRelationshipMappingTableFactory>
				dynamicObjectRelationshipMappingTableFactoryMockedStatic,
			ObjectRelationship objectRelationship) {

		DynamicObjectRelationshipMappingTable
			dynamicObjectRelationshipMappingTable = Mockito.mock(
				DynamicObjectRelationshipMappingTable.class);

		Column<DynamicObjectRelationshipMappingTable, Long> primaryKeyColumn1 =
			_mockColumn(RandomTestUtil.randomString());

		Mockito.when(
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn1()
		).thenReturn(
			primaryKeyColumn1
		);

		Column<DynamicObjectRelationshipMappingTable, Long> primaryKeyColumn2 =
			_mockColumn(RandomTestUtil.randomString());

		Mockito.when(
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn2()
		).thenReturn(
			primaryKeyColumn2
		);

		dynamicObjectRelationshipMappingTableFactoryMockedStatic.when(
			() -> DynamicObjectRelationshipMappingTableFactory.create(
				objectRelationship)
		).thenReturn(
			dynamicObjectRelationshipMappingTable
		);

		return dynamicObjectRelationshipMappingTable;
	}

	private ObjectRelationship _mockManyToManyObjectRelationship(
		ObjectDefinition objectDefinition) {

		ObjectRelationship objectRelationship = Mockito.mock(
			ObjectRelationship.class);

		Mockito.when(
			objectRelationship.getDBTableName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		long objectDefinitionId = objectDefinition.getObjectDefinitionId();

		Mockito.when(
			objectRelationship.getObjectDefinitionId2()
		).thenReturn(
			objectDefinitionId
		);

		Mockito.when(
			_objectRelationshipLocalService.getObjectRelationships(
				objectDefinitionId, false,
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)
		).thenReturn(
			Collections.singletonList(objectRelationship)
		);

		return objectRelationship;
	}

	private ObjectDefinition _mockObjectDefinition() {
		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.getObjectDefinitionId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_objectDefinitionLocalService.getObjectDefinitions(
				_COMPANY_ID, WorkflowConstants.STATUS_APPROVED)
		).thenReturn(
			Collections.singletonList(objectDefinition)
		);

		return objectDefinition;
	}

	private void _testUpdateIndexesWithManyToManyObjectRelationship()
		throws Exception {

		ObjectDefinition objectDefinition = _mockObjectDefinition();

		ObjectRelationship objectRelationship =
			_mockManyToManyObjectRelationship(objectDefinition);

		try (MockedStatic<DataAccess> dataAccessMockedStatic =
				Mockito.mockStatic(DataAccess.class);
			MockedStatic<DynamicObjectRelationshipMappingTableFactory>
				dynamicObjectRelationshipMappingTableFactoryMockedStatic =
					Mockito.mockStatic(
						DynamicObjectRelationshipMappingTableFactory.class);
			MockedStatic<ObjectDBManagerUtil> objectDBManagerUtilMockedStatic =
				Mockito.mockStatic(ObjectDBManagerUtil.class)) {

			dataAccessMockedStatic.when(
				DataAccess::getConnection
			).thenReturn(
				_connection
			);

			DynamicObjectRelationshipMappingTable
				dynamicObjectRelationshipMappingTable =
					_mockDynamicObjectRelationshipMappingTable(
						dynamicObjectRelationshipMappingTableFactoryMockedStatic,
						objectRelationship);

			Column<DynamicObjectRelationshipMappingTable, Long>
				primaryKeyColumn1 =
					dynamicObjectRelationshipMappingTable.
						getPrimaryKeyColumn1();
			Column<DynamicObjectRelationshipMappingTable, Long>
				primaryKeyColumn2 =
					dynamicObjectRelationshipMappingTable.
						getPrimaryKeyColumn2();

			_objectIndexUpdater.updateIndexes();

			objectDBManagerUtilMockedStatic.verify(
				() -> ObjectDBManagerUtil.createIndexMetadata(
					_connection, objectRelationship.getDBTableName(), false,
					primaryKeyColumn1.getName()));
			objectDBManagerUtilMockedStatic.verify(
				() -> ObjectDBManagerUtil.createIndexMetadata(
					_connection, objectRelationship.getDBTableName(), false,
					primaryKeyColumn2.getName()));
		}
	}

	private void _testUpdateIndexesWithOneToManyObjectRelationship()
		throws Exception {

		ObjectDefinition objectDefinition = _mockObjectDefinition();

		Mockito.when(
			_objectRelationshipLocalService.getObjectRelationships(
				objectDefinition.getObjectDefinitionId(), false,
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)
		).thenReturn(
			Collections.emptyList()
		);

		ObjectField objectField = Mockito.mock(ObjectField.class);

		String dbColumnName = RandomTestUtil.randomString();

		Mockito.when(
			objectField.getDBColumnName()
		).thenReturn(
			dbColumnName
		);

		String dbTableName = RandomTestUtil.randomString();

		Mockito.when(
			objectField.getDBTableName()
		).thenReturn(
			dbTableName
		);

		Mockito.when(
			_objectFieldLocalService.getObjectFieldsByBusinessType(
				objectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)
		).thenReturn(
			Collections.singletonList(objectField)
		);

		try (MockedStatic<DataAccess> dataAccessMockedStatic =
				Mockito.mockStatic(DataAccess.class);
			MockedStatic<ObjectDBManagerUtil> objectDBManagerUtilMockedStatic =
				Mockito.mockStatic(ObjectDBManagerUtil.class)) {

			dataAccessMockedStatic.when(
				DataAccess::getConnection
			).thenReturn(
				_connection
			);

			_objectIndexUpdater.updateIndexes();

			objectDBManagerUtilMockedStatic.verify(
				() -> ObjectDBManagerUtil.createIndexMetadata(
					_connection, dbTableName, false, dbColumnName));
		}
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private final CompanyLocalService _companyLocalService = Mockito.mock(
		CompanyLocalService.class);
	private final Connection _connection = Mockito.mock(Connection.class);
	private final ObjectDefinitionLocalService _objectDefinitionLocalService =
		Mockito.mock(ObjectDefinitionLocalService.class);
	private final ObjectFieldLocalService _objectFieldLocalService =
		Mockito.mock(ObjectFieldLocalService.class);
	private final ObjectIndexUpdater _objectIndexUpdater =
		new ObjectIndexUpdater();
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService = Mockito.mock(
			ObjectRelationshipLocalService.class);

}