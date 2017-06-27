package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.servlet.MetaInfoCacheServletResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import org.eclipse.core.runtime.Assert;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * @author Alberto Chaparro
 */
public class DBInspectorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_con = DataAccess.getConnection();

		_dbInspector = new DBInspector(_con);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		DataAccess.cleanUp(_con);
	}

	@Test
	public void testHasColumn() throws Exception {
		Assert.isTrue(
			_dbInspector.hasColumn(EXISTING_TABLE_NAME, EXISTING_COLUMN_NAME));
	}

	@Test
	public void testHasColumnLowerCase() throws Exception {
		DatabaseMetaData databaseMetaData = _con.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesLowerCaseIdentifiers());

		Assert.isTrue(
			_dbInspector.hasColumn(
				EXISTING_TABLE_NAME, EXISTING_COLUMN_NAME.toLowerCase()));
	}

	@Test
	public void testHasColumnUpperCase() throws Exception {
		DatabaseMetaData databaseMetaData = _con.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesUpperCaseIdentifiers());

		Assert.isTrue(
			_dbInspector.hasColumn(
				EXISTING_TABLE_NAME, EXISTING_COLUMN_NAME.toUpperCase()));
	}

	@Test
	public void testHasNoColumn() throws Exception {
		Assert.isTrue(
			!_dbInspector.hasColumn(
				EXISTING_TABLE_NAME, NON_EXISTING_COLUMN_NAME));
	}

	@Test
	public void testHasNoTable() throws Exception {
		Assert.isTrue(!_dbInspector.hasTable(NON_EXISTING_TABLE_NAME));
	}

	@Test
	public void testHasTable() throws Exception {
		Assert.isTrue(_dbInspector.hasTable(EXISTING_TABLE_NAME));
	}

	@Test
	public void testHasTableLowerCase() throws Exception {
		DatabaseMetaData databaseMetaData = _con.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesLowerCaseIdentifiers());

		Assert.isTrue(_dbInspector.hasTable(EXISTING_TABLE_NAME.toLowerCase()));
	}

	@Test
	public void testHasTableUpperCase() throws Exception {
		DatabaseMetaData databaseMetaData = _con.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesUpperCaseIdentifiers());

		Assert.isTrue(_dbInspector.hasTable(EXISTING_TABLE_NAME.toUpperCase()));
	}

	private static final String EXISTING_TABLE_NAME = "Release_";
	private static final String EXISTING_COLUMN_NAME = "releaseId";

	private static final String NON_EXISTING_TABLE_NAME = "NonExistingTable";
	private static final String NON_EXISTING_COLUMN_NAME = "NonExistingColumn";

	private static Connection _con;
	private static DBInspector _dbInspector;
}
