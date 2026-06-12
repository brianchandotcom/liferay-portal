/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.db.index.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTableFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.portal.db.index.IndexUpdaterUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.db.index.IndexUpdater;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yuri Monteiro
 */
@RunWith(Arquillian.class)
public class ObjectIndexUpdaterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition();
		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition();
	}

	@Test
	public void testUpdateAllIndexes() throws Exception {
		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _objectDefinition1,
				_objectDefinition2,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		String columnName = objectField.getDBColumnName();
		String tableName = objectField.getDBTableName();

		_dropIndex(columnName, tableName);

		Assert.assertFalse(_hasIndex(columnName, tableName));

		IndexUpdaterUtil.updateAllIndexes();

		Assert.assertTrue(_hasIndex(columnName, tableName));
	}

	@Test
	public void testUpdateIndexes() throws Exception {

		// Many to many relationship

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _objectDefinition1,
				_objectDefinition2,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		DynamicObjectRelationshipMappingTable
			dynamicObjectRelationshipMappingTable =
				DynamicObjectRelationshipMappingTableFactory.create(
					objectRelationship);

		Column<DynamicObjectRelationshipMappingTable, Long> primaryKeyColumn1 =
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn1();
		Column<DynamicObjectRelationshipMappingTable, Long> primaryKeyColumn2 =
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn2();

		String tableName = objectRelationship.getDBTableName();

		Assert.assertTrue(_hasIndex(primaryKeyColumn1.getName(), tableName));
		Assert.assertTrue(_hasIndex(primaryKeyColumn2.getName(), tableName));

		_dropIndex(primaryKeyColumn1.getName(), tableName);
		_dropIndex(primaryKeyColumn2.getName(), tableName);

		Assert.assertFalse(_hasIndex(primaryKeyColumn1.getName(), tableName));
		Assert.assertFalse(_hasIndex(primaryKeyColumn2.getName(), tableName));

		_indexUpdater.updateIndexes();

		Assert.assertTrue(_hasIndex(primaryKeyColumn1.getName(), tableName));
		Assert.assertTrue(_hasIndex(primaryKeyColumn2.getName(), tableName));

		ObjectDefinition userObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				TestPropsValues.getCompanyId(), User.class.getName());

		objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService, userObjectDefinition,
			_objectDefinition1,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			StringUtil.randomId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		dynamicObjectRelationshipMappingTable =
			DynamicObjectRelationshipMappingTableFactory.create(
				objectRelationship);

		primaryKeyColumn1 =
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn1();
		primaryKeyColumn2 =
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn2();

		tableName = objectRelationship.getDBTableName();

		_dropIndex(primaryKeyColumn1.getName(), tableName);
		_dropIndex(primaryKeyColumn2.getName(), tableName);

		Assert.assertFalse(_hasIndex(primaryKeyColumn1.getName(), tableName));
		Assert.assertFalse(_hasIndex(primaryKeyColumn2.getName(), tableName));

		_indexUpdater.updateIndexes();

		Assert.assertTrue(_hasIndex(primaryKeyColumn1.getName(), tableName));
		Assert.assertTrue(_hasIndex(primaryKeyColumn2.getName(), tableName));

		// One to many relationship

		objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService, _objectDefinition1,
			_objectDefinition2,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			StringUtil.randomId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		String columnName = objectField.getDBColumnName();

		tableName = objectField.getDBTableName();

		Assert.assertTrue(_hasIndex(columnName, tableName));

		_dropIndex(columnName, tableName);

		Assert.assertFalse(_hasIndex(columnName, tableName));

		_indexUpdater.updateIndexes();

		Assert.assertTrue(_hasIndex(columnName, tableName));
	}

	private void _dropIndex(String columnName, String tableName)
		throws Exception {

		IndexMetadata indexMetadata =
			IndexMetadataFactoryUtil.createIndexMetadata(
				false, tableName, columnName);

		try (Connection connection = DataAccess.getConnection()) {
			DB db = DBManagerUtil.getDB();

			db.runSQL(connection, indexMetadata.getDropSQL());
		}
	}

	private boolean _hasIndex(String columnName, String tableName)
		throws Exception {

		IndexMetadata indexMetadata =
			IndexMetadataFactoryUtil.createIndexMetadata(
				false, tableName, columnName);

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasIndex(
				tableName, indexMetadata.getIndexName());
		}
	}

	@Inject(
		filter = "component.name=com.liferay.object.internal.db.index.ObjectIndexUpdater"
	)
	private IndexUpdater _indexUpdater;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}