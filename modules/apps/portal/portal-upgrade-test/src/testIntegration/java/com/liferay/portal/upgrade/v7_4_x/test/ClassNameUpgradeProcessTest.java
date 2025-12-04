/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.processor.RawMetadataProcessor;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.ClassNameUpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Galluzzi
 */
@RunWith(Arquillian.class)
public class ClassNameUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeleteClassName() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			long classNameId = _addClassName(_CLASS_NAME, connection);

			long structureId = _addDDMStructure(classNameId, connection);

			try {
				runUpgrade();

				Assert.assertEquals(
					0, _getClassNameId(_CLASS_NAME, connection));

				Assert.assertEquals(
					_getClassNameId(
						RawMetadataProcessor.class.getName(), connection),
					_getDDMStructureClassNameId(structureId, connection));
			}
			finally {
				_deleteDDMStructure(structureId, connection);
				_deleteClassName(classNameId, connection);
			}
		}
	}

	@Test
	public void testUpdateClassName() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			long classNameId = _getClassNameId(
				RawMetadataProcessor.class.getName(), connection);

			_updateClassNameValue(
				classNameId, RawMetadataProcessor.class.getName(), connection);

			try {
				runUpgrade();

				Assert.assertEquals(
					RawMetadataProcessor.class.getName(),
					_getClassNameValue(classNameId, connection));
			}
			finally {
				_updateClassNameValue(
					classNameId, RawMetadataProcessor.class.getName(),
					connection);
			}
		}
	}

	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = new ClassNameUpgradeProcess();

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	private long _addClassName(String className, Connection connection)
		throws Exception {

		long classNameId = RandomTestUtil.nextLong();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into ClassName_ (mvccVersion, classNameId, value) " +
					"values (?, ?, ?)")) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, classNameId);
			preparedStatement.setString(3, className);

			preparedStatement.executeUpdate();
		}

		return classNameId;
	}

	private long _addDDMStructure(long classNameId, Connection connection)
		throws Exception {

		long structureId = RandomTestUtil.nextLong();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into DDMStructure (mvccVersion, ctcollectionid, " +
					"structureid, classNameId, structurekey) values (?, ?, " +
						"?, ?, ?)")) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, 0);
			preparedStatement.setLong(3, structureId);
			preparedStatement.setLong(4, classNameId);
			preparedStatement.setString(5, RandomTestUtil.randomString());

			preparedStatement.executeUpdate();
		}

		return structureId;
	}

	private void _deleteClassName(long classNameId, Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from ClassName_ where classNameId = ?")) {

			preparedStatement.setLong(1, classNameId);

			preparedStatement.execute();
		}
	}

	private void _deleteDDMStructure(long structureId, Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from DDMStructure where structureId = ?")) {

			preparedStatement.setLong(1, structureId);

			preparedStatement.execute();
		}
	}

	private long _getClassNameId(String value, Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select classNameId from ClassName_ where value = ?")) {

			preparedStatement.setString(1, value);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getLong("classNameId");
			}
		}

		return 0;
	}

	private String _getClassNameValue(long classNameId, Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select value from ClassName_ where classNameId = ?")) {

			preparedStatement.setLong(1, classNameId);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getString("value");
			}
		}

		return null;
	}

	private long _getDDMStructureClassNameId(
			long structureId, Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select classNameId from DDMStructure where structureId = ?")) {

			preparedStatement.setLong(1, structureId);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getLong("classNameId");
			}
		}

		return 0;
	}

	private void _updateClassNameValue(
			long classNameId, String value, Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ClassName_ set value = ? where classNameId = ? ")) {

			preparedStatement.setString(1, value);
			preparedStatement.setLong(2, classNameId);

			preparedStatement.executeUpdate();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.document.library.kernel.util.RawMetadataProcessor";

	@Inject
	private EntityCache _entityCache;

	@Inject
	private MultiVMPool _multiVMPool;

}