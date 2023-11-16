/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.jdbc.util.ConnectionWrapper;
import com.liferay.portal.dao.jdbc.util.DataSourceWrapper;
import com.liferay.portal.dao.jdbc.util.StatementWrapper;
import com.liferay.portal.db.partition.sql.DBPartitionDB;
import com.liferay.portal.db.partition.sql.DBPartitionMySQLDB;
import com.liferay.portal.db.partition.sql.DBPartitionPostgreSQLDB;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.framework.ThrowableCollector;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.hibernate.DialectDetector;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

/**
 * @author Alberto Chaparro
 */
public class DBPartitionUtil {

	public static boolean addDBPartition(long companyId)
		throws PortalException {

		if (!DBPartition.isPartitionEnabled() ||
			(companyId == _defaultCompanyId)) {

			return false;
		}

		Connection connection = CurrentConnectionUtil.getConnection(
			InfrastructureUtil.getDataSource());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				_dbPartitionDB.getCreatePartitionSQL(
					connection, _getPartitionName(companyId)))) {

			preparedStatement.executeUpdate();

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			try (ResultSet resultSet = databaseMetaData.getTables(
					dbInspector.getCatalog(), dbInspector.getSchema(), null,
					new String[] {"TABLE"});
				Statement statement = connection.createStatement()) {

				while (resultSet.next()) {
					String tableName = resultSet.getString("TABLE_NAME");

					if (dbInspector.isObjectTable(
							_getCompanyIds(), tableName)) {

						continue;
					}

					if (dbInspector.isControlTable(
							_getCompanyIds(), tableName)) {

						statement.executeUpdate(
							_getCreateViewSQL(
								_defaultPartitionName,
								_getPartitionName(companyId), tableName));
					}
					else {
						statement.executeUpdate(
							_dbPartitionDB.getCreateTableSQL(
								_defaultPartitionName,
								_getPartitionName(companyId), tableName));

						if (dbInspector.isPartitionedControlTable(tableName)) {
							statement.executeUpdate(
								_getCopyDataSQL(
									_defaultPartitionName,
									_getPartitionName(companyId), tableName,
									StringPool.BLANK));
						}
					}
				}
			}

			connection.commit();
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}

		_companyIds.add(companyId);

		return true;
	}

	public static boolean extractDBPartition(long companyId)
		throws PortalException {

		if (!DBPartition.isPartitionEnabled() ||
			(companyId == _defaultCompanyId)) {

			return false;
		}

		_extractDBPartition(companyId);

		return true;
	}

	public static void forEachCompanyId(
			UnsafeConsumer<Long, Exception> unsafeConsumer)
		throws Exception {

		if (!DBPartition.isPartitionEnabled()) {
			unsafeConsumer.accept(null);

			return;
		}

		if (CompanyThreadLocal.isLocked()) {
			unsafeConsumer.accept(CompanyThreadLocal.getCompanyId());

			return;
		}

		if (_DATABASE_PARTITION_THREAD_POOL_ENABLED) {
			_forEachCompanyIdConcurrently(unsafeConsumer);

			return;
		}

		List<Long> companyIds = _getCompanyIds();

		if (companyIds.isEmpty()) {
			unsafeConsumer.accept(null);
		}
		else {
			for (long companyId : companyIds) {
				try (SafeCloseable safeCloseable = CompanyThreadLocal.lock(
						companyId)) {

					unsafeConsumer.accept(companyId);
				}
			}
		}
	}

	public static long getCurrentCompanyId() {
		long companyId = CompanyThreadLocal.getCompanyId();

		if (!DBPartition.isPartitionEnabled()) {
			return companyId;
		}

		if (companyId == CompanyConstants.SYSTEM) {
			companyId = _defaultCompanyId;
		}

		return companyId;
	}

	public static boolean insertDBPartition(long companyId)
		throws PortalException {

		if (!DBPartition.isPartitionEnabled()) {
			return false;
		}

		_insertDBPartition(companyId);

		return true;
	}

	public static boolean removeDBPartition(long companyId)
		throws PortalException {

		if (!DBPartition.isPartitionEnabled() ||
			(companyId == _defaultCompanyId)) {

			return false;
		}

		_dropDBPartition(companyId);

		return true;
	}

	public static void replaceByTable(Connection connection, String viewName)
		throws Exception {

		long companyId = getCurrentCompanyId();

		if (companyId == _defaultCompanyId) {
			return;
		}

		try (Statement statement = connection.createStatement()) {
			statement.execute(
				_getDropViewSQL(_getPartitionName(companyId), viewName));

			statement.execute(
				_dbPartitionDB.getCreateTableSQL(
					_defaultPartitionName, _getPartitionName(companyId),
					viewName));

			statement.executeUpdate(
				_getCopyDataSQL(
					_defaultPartitionName, _getPartitionName(companyId),
					viewName, StringPool.BLANK));
		}
	}

	public static void setDefaultCompanyId(Connection connection)
		throws SQLException {

		if (DBPartition.isPartitionEnabled()) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select companyId from Company where webId = '" +
							PropsValues.COMPANY_DEFAULT_WEB_ID + "'");
				ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {
					_defaultCompanyId = resultSet.getLong(1);
				}
			}
		}
	}

	public static void setDefaultCompanyId(long companyId) {
		if (DBPartition.isPartitionEnabled()) {
			_defaultCompanyId = companyId;
		}
	}

	public static DataSource wrapDataSource(DataSource dataSource)
		throws SQLException {

		if (!DBPartition.isPartitionEnabled()) {
			return dataSource;
		}

		DB db = DBManagerUtil.getDB(
			DBManagerUtil.getDBType(DialectDetector.getDialect(dataSource)),
			dataSource);

		if (!db.isSupportsDBPartition()) {
			throw new Error(
				"Database partition is not supported for " + db.getDBType());
		}

		if (db.getDBType() == DBType.MYSQL) {
			_dbPartitionDB = new DBPartitionMySQLDB();
		}
		else if (db.getDBType() == DBType.POSTGRESQL) {
			_dbPartitionDB = new DBPartitionPostgreSQLDB();
		}

		try (Connection connection = dataSource.getConnection()) {
			_defaultPartitionName = _dbPartitionDB.getDefaultPartitionName(
				connection);
		}

		return new DataSourceWrapper(dataSource) {

			@Override
			public Connection getConnection() throws SQLException {
				return _getConnectionWrapper(super.getConnection());
			}

			@Override
			public Connection getConnection(String userName, String password)
				throws SQLException {

				return _getConnectionWrapper(super.getConnection());
			}

		};
	}

	private static void _deleteCompanyData(
			long companyId, String tableName, String fromPartitionName,
			Statement statement)
		throws Exception {

		statement.executeUpdate(
			StringBundler.concat(
				"delete from ", fromPartitionName, StringPool.PERIOD, tableName,
				" where companyId = ", companyId));
	}

	private static void _dropDBPartition(long companyId)
		throws PortalException {

		Connection connection = CurrentConnectionUtil.getConnection(
			InfrastructureUtil.getDataSource());

		DBInspector dbInspector = new DBInspector(connection);

		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			try (ResultSet resultSet = databaseMetaData.getTables(
					_defaultPartitionName, dbInspector.getSchema(), null,
					new String[] {"TABLE"});
				Statement statement = connection.createStatement()) {

				while (resultSet.next()) {
					String tableName = resultSet.getString("TABLE_NAME");

					if (dbInspector.isControlTable(
							_getCompanyIds(), tableName) &&
						dbInspector.hasColumn(tableName, "companyId")) {

						statement.executeUpdate(
							StringBundler.concat(
								"delete from ", _defaultPartitionName,
								StringPool.PERIOD, tableName,
								" where companyId = ", companyId));
					}
				}

				statement.executeUpdate(
					_getDropPartitionSQL(_getPartitionName(companyId)));
			}
		}
		catch (Exception exception) {
			throw new PortalException(
				"Unable to drop database partition", exception);
		}

		_companyIds.remove(companyId);
	}

	private static void _extractDBPartition(long companyId)
		throws PortalException {

		Connection connection = CurrentConnectionUtil.getConnection(
			InfrastructureUtil.getDataSource());
		List<String> controlTableNames = new ArrayList<>();

		DBInspector dbInspector = new DBInspector(connection);

		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			try (ResultSet resultSet = databaseMetaData.getTables(
					_defaultPartitionName, dbInspector.getSchema(), null,
					new String[] {"TABLE"});
				Statement statement = connection.createStatement()) {

				while (resultSet.next()) {
					String tableName = resultSet.getString("TABLE_NAME");

					if (dbInspector.isControlTable(
							_getCompanyIds(), tableName)) {

						controlTableNames.add(tableName);

						_extractTable(
							companyId, tableName, statement, dbInspector);
					}
				}
			}
		}
		catch (Exception exception1) {
			if (ListUtil.isEmpty(controlTableNames)) {
				throw new PortalException(exception1);
			}

			try {
				for (String tableName : controlTableNames) {
					try (Statement statement = connection.createStatement()) {
						_restoreTable(
							companyId, tableName, statement, dbInspector);
					}
				}
			}
			catch (Exception exception2) {
				throw new PortalException(
					StringBundler.concat(
						"Unable to rollback the extraction of database ",
						"partition. Recover a backup of the database ",
						"partition ", _getPartitionName(companyId), "."),
					exception2);
			}

			throw new PortalException(
				"Removal of database partition extraction was rolled back",
				exception1);
		}

		_companyIds.remove(companyId);
	}

	private static void _extractTable(
			long companyId, String tableName, Statement statement,
			DBInspector dbInspector)
		throws Exception {

		statement.executeUpdate(
			_getDropViewSQL(_getPartitionName(companyId), tableName));

		statement.executeUpdate(
			_dbPartitionDB.getCreateTableSQL(
				_defaultPartitionName, _getPartitionName(companyId),
				tableName));

		if (dbInspector.hasColumn(tableName, "companyId")) {
			_moveCompanyData(
				companyId, _defaultPartitionName, _getPartitionName(companyId),
				tableName, statement);
		}
		else {
			statement.executeUpdate(
				_getCopyDataSQL(
					_defaultPartitionName, _getPartitionName(companyId),
					tableName, StringPool.BLANK));
		}
	}

	private static void _forEachCompanyIdConcurrently(
			UnsafeConsumer<Long, Exception> unsafeConsumer)
		throws Exception {

		ExecutorService executorService = Executors.newWorkStealingPool();

		List<Future<Void>> futures = new ArrayList<>();

		ThrowableCollector throwableCollector = new ThrowableCollector();

		try {
			List<Long> companyIds = _getCompanyIds();

			if (companyIds.isEmpty()) {
				unsafeConsumer.accept(null);
			}
			else {
				for (long companyId : companyIds) {
					if (companyId == _defaultCompanyId) {
						try (SafeCloseable safeCloseable =
								CompanyThreadLocal.lock(companyId)) {

							unsafeConsumer.accept(companyId);
						}
					}
					else {
						Future<Void> future = executorService.submit(
							() -> {
								try (SafeCloseable safeCloseable =
										CompanyThreadLocal.lock(companyId)) {

									unsafeConsumer.accept(companyId);
								}
								catch (Exception exception) {
									throwableCollector.collect(exception);
								}

								return null;
							});

						futures.add(future);
					}
				}
			}
		}
		finally {
			executorService.shutdown();

			for (Future<Void> future : futures) {
				future.get();
			}
		}

		Throwable throwable = throwableCollector.getThrowable();

		if (throwable != null) {
			ReflectionUtil.throwException(throwable);
		}
	}

	private static List<Long> _getCompanyIds() throws SQLException {
		if (_companyIds.isEmpty()) {
			for (long companyId : PortalInstances.getCompanyIdsBySQL()) {
				_companyIds.add(companyId);
			}
		}

		return _companyIds;
	}

	private static Connection _getConnectionWrapper(Connection connection) {
		return new ConnectionWrapper(connection) {

			@Override
			public Statement createStatement() throws SQLException {
				_setPartition();

				return _wrapStatement(super.createStatement());
			}

			@Override
			public Statement createStatement(
					int resultSetType, int resultSetConcurrency)
				throws SQLException {

				_setPartition();

				return _wrapStatement(
					super.createStatement(resultSetType, resultSetConcurrency));
			}

			@Override
			public Statement createStatement(
					int resultSetType, int resultSetConcurrency,
					int resultSetHoldability)
				throws SQLException {

				_setPartition();

				return _wrapStatement(
					super.createStatement(
						resultSetType, resultSetConcurrency,
						resultSetHoldability));
			}

			@Override
			public String getCatalog() throws SQLException {
				return _dbPartitionDB.getCatalog(
					connection,
					_getPartitionName(CompanyThreadLocal.getCompanyId()));
			}

			@Override
			public String getSchema() {
				return _dbPartitionDB.getSchema(
					connection,
					_getPartitionName(CompanyThreadLocal.getCompanyId()));
			}

			@Override
			public PreparedStatement prepareStatement(String sql)
				throws SQLException {

				_setPartition();

				return super.prepareStatement(sql);
			}

			@Override
			public PreparedStatement prepareStatement(
					String sql, int autoGeneratedKeys)
				throws SQLException {

				_setPartition();

				return super.prepareStatement(sql, autoGeneratedKeys);
			}

			@Override
			public PreparedStatement prepareStatement(
					String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {

				_setPartition();

				return super.prepareStatement(
					sql, resultSetType, resultSetConcurrency);
			}

			@Override
			public PreparedStatement prepareStatement(
					String sql, int resultSetType, int resultSetConcurrency,
					int resultSetHoldability)
				throws SQLException {

				_setPartition();

				return super.prepareStatement(
					sql, resultSetType, resultSetConcurrency,
					resultSetHoldability);
			}

			@Override
			public PreparedStatement prepareStatement(
					String sql, int[] columnIndexes)
				throws SQLException {

				_setPartition();

				return super.prepareStatement(sql, columnIndexes);
			}

			@Override
			public PreparedStatement prepareStatement(
					String sql, String[] columnNames)
				throws SQLException {

				_setPartition();

				return super.prepareStatement(sql, columnNames);
			}

			private void _setPartition() throws SQLException {
				long companyId = CompanyThreadLocal.getCompanyId();

				String partitionName = _getPartitionName(companyId);

				_dbPartitionDB.setPartition(connection, partitionName);

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Using database partition ", partitionName,
							" and company ", companyId));
				}
			}

		};
	}

	private static String _getCopyDataSQL(
		String fromPartitionName, String toPartitionName, String tableName,
		String whereClause) {

		return StringBundler.concat(
			"insert into ", toPartitionName, StringPool.PERIOD, tableName,
			" select * from ", fromPartitionName, StringPool.PERIOD, tableName,
			whereClause);
	}

	private static String _getCreateViewSQL(
		String fromPartitionName, String toPartitionName, String viewName) {

		return StringBundler.concat(
			"create or replace view ", toPartitionName, StringPool.PERIOD,
			viewName, " as select * from ", fromPartitionName,
			StringPool.PERIOD, viewName);
	}

	private static String _getDropPartitionSQL(String partitionName) {
		return "drop schema " + partitionName;
	}

	private static String _getDropTableSQL(
		String partitionName, String tableName) {

		return StringBundler.concat(
			"drop table if exists ", partitionName, StringPool.PERIOD,
			tableName);
	}

	private static String _getDropViewSQL(
		String partitionName, String viewName) {

		return StringBundler.concat(
			"drop view if exists ", partitionName, StringPool.PERIOD, viewName);
	}

	private static String _getPartitionName(long companyId) {
		if ((companyId == CompanyConstants.SYSTEM) ||
			(companyId == _defaultCompanyId)) {

			return _defaultPartitionName;
		}

		return _DATABASE_PARTITION_SCHEMA_NAME_PREFIX + companyId;
	}

	private static void _insertDBPartition(long companyId)
		throws PortalException {

		List<String> companyIdControlTableNames = new ArrayList<>();

		Connection connection = CurrentConnectionUtil.getConnection(
			InfrastructureUtil.getDataSource());

		try (Statement statement = connection.createStatement()) {
			DBInspector dbInspector = new DBInspector(connection);

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			try (ResultSet resultSet = databaseMetaData.getTables(
					_defaultPartitionName, dbInspector.getSchema(), null,
					new String[] {"TABLE"})) {

				while (resultSet.next()) {
					String tableName = resultSet.getString("TABLE_NAME");

					if (dbInspector.isControlTable(
							_getCompanyIds(), tableName)) {

						if (dbInspector.hasColumn(tableName, "companyId")) {
							statement.executeUpdate(
								_getCopyDataSQL(
									_getPartitionName(companyId),
									_defaultPartitionName, tableName,
									" where companyId = " + companyId));

							companyIdControlTableNames.add(tableName);
						}

						statement.executeUpdate(
							_getDropTableSQL(
								_getPartitionName(companyId), tableName));

						statement.executeUpdate(
							_getCreateViewSQL(
								_defaultPartitionName,
								_getPartitionName(companyId), tableName));
					}
				}
			}
		}
		catch (Exception exception1) {
			try (Statement statement = connection.createStatement()) {
				for (String companyIdControlTable :
						companyIdControlTableNames) {

					_deleteCompanyData(
						companyId, companyIdControlTable, _defaultPartitionName,
						statement);
				}
			}
			catch (Exception exception2) {
				throw new PortalException(
					StringBundler.concat(
						"Unable to roll back the data inserted into the ",
						"default schema for tables ",
						companyIdControlTableNames, " and company ID ",
						companyId),
					exception2);
			}

			throw new PortalException(
				StringBundler.concat(
					"Unable to roll back the insertion of database partition. ",
					"Recover a backup of the database schema ",
					_getPartitionName(companyId), "."),
				exception1);
		}

		_companyIds.add(companyId);
	}

	private static boolean _isSkip(Connection connection, String tableName)
		throws SQLException {

		try {
			DBInspector dbInspector = new DBInspector(connection);

			if (dbInspector.isControlTable(_getCompanyIds(), tableName) &&
				!(CompanyThreadLocal.getCompanyId() == _defaultCompanyId)) {

				return true;
			}
		}
		catch (Exception exception) {
			throw new SQLException(
				"Unable to check if the table " + tableName +
					" is a control table",
				exception);
		}

		return false;
	}

	private static void _moveCompanyData(
			long companyId, String fromPartitionName, String toPartitionName,
			String tableName, Statement statement)
		throws Exception {

		statement.executeUpdate(
			_getCopyDataSQL(
				_defaultPartitionName, toPartitionName, tableName,
				" where companyId = " + companyId));

		_deleteCompanyData(companyId, tableName, fromPartitionName, statement);
	}

	private static void _restoreTable(
			long companyId, String tableName, Statement statement,
			DBInspector dbInspector)
		throws Exception {

		if (dbInspector.hasColumn(tableName, "companyId")) {
			_moveCompanyData(
				companyId, _getPartitionName(companyId), _defaultPartitionName,
				tableName, statement);
		}

		statement.executeUpdate(
			_getDropTableSQL(_getPartitionName(companyId), tableName));

		statement.executeUpdate(
			_getCreateViewSQL(
				_defaultPartitionName, _getPartitionName(companyId),
				tableName));
	}

	private static Statement _wrapStatement(Statement statement) {
		return new StatementWrapper(statement) {

			@Override
			public int executeUpdate(String sql) throws SQLException {
				Connection connection = statement.getConnection();

				String lowerCaseSQL = StringUtil.toLowerCase(sql);

				String[] query = sql.split(StringPool.SPACE);

				if ((StringUtil.startsWith(lowerCaseSQL, "alter table") &&
					 _isSkip(connection, query[2])) ||
					((StringUtil.startsWith(lowerCaseSQL, "create index") ||
					  StringUtil.startsWith(lowerCaseSQL, "drop index")) &&
					 _isSkip(connection, query[4])) ||
					(StringUtil.startsWith(
						lowerCaseSQL, "create unique index") &&
					 _isSkip(connection, query[5]))) {

					return 0;
				}

				int returnValue = super.executeUpdate(sql);

				if (!StringUtil.startsWith(lowerCaseSQL, "alter table")) {
					return returnValue;
				}

				try {
					DBInspector dbInspector = new DBInspector(connection);
					String tableName = query[2];

					if (!dbInspector.isControlTable(
							_getCompanyIds(), tableName)) {

						return returnValue;
					}

					for (long companyId : _getCompanyIds()) {
						if (companyId == _defaultCompanyId) {
							continue;
						}

						super.execute(
							_getCreateViewSQL(
								_defaultPartitionName,
								_getPartitionName(companyId), tableName));
					}

					return returnValue;
				}
				catch (Exception exception) {
					throw new SQLException(exception);
				}
			}

		};
	}

	private static final String _DATABASE_PARTITION_SCHEMA_NAME_PREFIX =
		GetterUtil.get(
			PropsUtil.get("database.partition.schema.name.prefix"),
			"lpartition_");

	private static final boolean _DATABASE_PARTITION_THREAD_POOL_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get("database.partition.thread.pool.enabled"), true);

	private static final Log _log = LogFactoryUtil.getLog(
		DBPartitionUtil.class);

	private static final List<Long> _companyIds = new CopyOnWriteArrayList<>();
	private static DBPartitionDB _dbPartitionDB;
	private static volatile long _defaultCompanyId;
	private static String _defaultPartitionName;

}