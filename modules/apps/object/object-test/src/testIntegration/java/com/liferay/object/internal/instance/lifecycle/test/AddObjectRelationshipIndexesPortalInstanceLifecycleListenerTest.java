/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.instance.lifecycle.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTableFactory;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import org.junit.After;
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
public class AddObjectRelationshipIndexesPortalInstanceLifecycleListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_originalDatabaseIndexesUpdateOnStartup =
			PropsValues.DATABASE_INDEXES_UPDATE_ON_STARTUP;
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "DATABASE_INDEXES_UPDATE_ON_STARTUP",
			_originalDatabaseIndexesUpdateOnStartup);
	}

	@Test
	public void testPortalInstanceRegistered() throws Exception {

		// Database indexes update on startup disabled

		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition();
		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition();

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "DATABASE_INDEXES_UPDATE_ON_STARTUP", false);

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

		_portalInstanceLifecycleListener.portalInstanceRegistered(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		Assert.assertFalse(_hasIndex(columnName, tableName));

		// Many to many

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "DATABASE_INDEXES_UPDATE_ON_STARTUP", true);

		objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
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

		tableName = objectRelationship.getDBTableName();

		Assert.assertTrue(_hasIndex(primaryKeyColumn1.getName(), tableName));
		Assert.assertTrue(_hasIndex(primaryKeyColumn2.getName(), tableName));

		_dropIndex(primaryKeyColumn1.getName(), tableName);
		_dropIndex(primaryKeyColumn2.getName(), tableName);

		Assert.assertFalse(_hasIndex(primaryKeyColumn1.getName(), tableName));
		Assert.assertFalse(_hasIndex(primaryKeyColumn2.getName(), tableName));

		_portalInstanceLifecycleListener.portalInstanceRegistered(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		Assert.assertTrue(_hasIndex(primaryKeyColumn1.getName(), tableName));
		Assert.assertTrue(_hasIndex(primaryKeyColumn2.getName(), tableName));

		// One to many

		objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService, _objectDefinition1,
			_objectDefinition2,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			StringUtil.randomId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		columnName = objectField.getDBColumnName();
		tableName = objectField.getDBTableName();

		Assert.assertTrue(_hasIndex(columnName, tableName));

		_dropIndex(columnName, tableName);

		Assert.assertFalse(_hasIndex(columnName, tableName));

		_portalInstanceLifecycleListener.portalInstanceRegistered(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

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

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private boolean _originalDatabaseIndexesUpdateOnStartup;

	@Inject(
		filter = "component.name=com.liferay.object.internal.instance.lifecycle.AddObjectRelationshipIndexesPortalInstanceLifecycleListener"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

}