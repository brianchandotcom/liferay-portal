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

package com.liferay.portal.configuration.persistence;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.io.ReaderInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.IOException;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.apache.felix.cm.PersistenceManager;
import org.apache.felix.cm.file.ConfigurationHandler;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true, property = {
		Constants.SERVICE_RANKING + ":Integer=" +
			ConfigurationPersistenceManager.ALMOST_MAX_VALUE
	}
)
public class ConfigurationPersistenceManager implements PersistenceManager {

	public static final int ALMOST_MAX_VALUE = (Integer.MAX_VALUE - 1000);

	@Override
	public void delete(String pid) throws IOException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = _dataSource.getConnection();

			ps = con.prepareStatement(_DELETE_CONFIGURATION_SQL);

			ps.setString(1, pid);

			ps.executeUpdate();
		}
		catch (SQLException se) {
			throw new IOException(se);
		}
		finally {
			cleanUp(con, ps, null);
		}
	}

	@Override
	public boolean exists(String pid) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;

		try {
			con = _dataSource.getConnection();

			ps = con.prepareStatement(_COUNT_CONFIGURATION_SQL);

			ps.setString(1, pid);

			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

			if (count > 0) {
				return true;
			}

			return false;
		}
		catch (SQLException se) {
			return ReflectionUtil.throwException(se);
		}
		finally {
			cleanUp(con, ps, rs);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Enumeration getDictionaries() throws IOException {
		try {
			final Connection con = _dataSource.getConnection();

			final PreparedStatement ps = con.prepareStatement(
				_RETRIEVE_ALL_CONFIGURATION_SQL, ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);

			final ResultSet rs = ps.executeQuery();

			return new Enumeration() {

				@Override
				public boolean hasMoreElements() {
					return next;
				}

				@Override
				public Object nextElement() {
					try {
						return load(rs.getClob(1));
					}
					catch (IOException | SQLException e) {
						return ReflectionUtil.throwException(e);
					}
					finally {
						try {
							next = rs.next();
						}
						catch (SQLException se) {
							ReflectionUtil.throwException(se);
						}
						finally {
							if (!next) {
								cleanUp(con, ps, null);
							}
						}
					}
				}

				private boolean next = rs.next();

			};
		}
		catch (SQLException se) {
			return ReflectionUtil.throwException(se);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Dictionary load(String pid) throws IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = _dataSource.getConnection();

			ps = con.prepareStatement(_RETRIEVE_CONFIGURATION_SQL);

			ps.setString(1, pid);

			rs = ps.executeQuery();

			if (rs.next()) {
				return load(rs.getClob(1));
			}

			return _EMPTY_DICTIONARY;
		}
		catch (SQLException se) {
			return ReflectionUtil.throwException(se);
		}
		finally {
			cleanUp(con, ps, rs);
		}
	}

	@Override
	public void store(
			String pid, @SuppressWarnings("rawtypes") Dictionary dictionary)
		throws IOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = _dataSource.getConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(
				_RETRIEVE_CONFIGURATION_SQL_FOR_UPDATE,
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

			ps.setString(1, pid);

			rs = ps.executeQuery();

			if (rs.next()) {
				store(rs, dictionary);

				rs.updateRow();
			}
			else {
				rs.moveToInsertRow();
				rs.updateString(1, pid);

				store(rs, dictionary);

				rs.insertRow();
				rs.moveToCurrentRow();
			}

			con.commit();
		}
		catch (SQLException se) {
			ReflectionUtil.throwException(se);
		}
		finally {
			cleanUp(con, ps, rs);
		}
	}

	@Activate
	protected void activate() {
		if (configurationTableExists()) {
			return;
		}

		createConfigurationTable();
	}

	protected void cleanUp(
		Connection connection, Statement statement, ResultSet resultSet) {

		try {
			if (resultSet != null) {
				resultSet.close();
			}
		}
		catch (SQLException se) {
			ReflectionUtil.throwException(se);
		}
		finally {
			try {
				if (statement != null) {
					statement.close();
				}
			}
			catch (SQLException se) {
				ReflectionUtil.throwException(se);
			}
			finally {
				try {
					if (connection != null) {
						connection.close();
					}
				}
				catch (SQLException se) {
					ReflectionUtil.throwException(se);
				}
			}
		}
	}

	protected boolean configurationTableExists() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;

		try {
			con = _dataSource.getConnection();

			ps = con.prepareStatement(_TEST_CONFIGURATION_TABLE_EXISTS);

			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

			if (count >= 0) {
				return true;
			}

			return false;
		}
		catch (SQLException se) {
			return false;
		}
		finally {
			cleanUp(con, ps, rs);
		}
	}

	protected void createConfigurationTable() {
		Connection con = null;
		Statement s = null;
		ResultSet rs = null;

		try {
			con = _dataSource.getConnection();

			s = con.createStatement();

			s.executeUpdate(SQLTransformer.transform(_TABLE_SQL_CREATE));
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
		finally {
			cleanUp(con, s, rs);
		}
	}

	@SuppressWarnings("rawtypes")
	protected Dictionary load(Clob clob) throws IOException, SQLException {
		ReaderInputStream readerInputStream = new ReaderInputStream(
			clob.getCharacterStream());

		Dictionary dictionary = ConfigurationHandler.read(readerInputStream);

		clob.free();

		return dictionary;
	}

	@Reference(target = "(bean.id=liferayDataSource)")
	protected void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	protected void store(
			ResultSet resultSet,
			@SuppressWarnings("rawtypes") Dictionary dictionary)
		throws IOException, SQLException {

		UnsyncByteArrayOutputStream byteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(byteArrayOutputStream, dictionary);

		resultSet.updateBytes(2, byteArrayOutputStream.toByteArray());
	}

	private static final String _COUNT_CONFIGURATION_SQL =
		"select count(*) from Configuration_ where configurationId = ?";

	private static final String _DELETE_CONFIGURATION_SQL =
		"delete from Configuration_ where configurationId = ?";

	private static final Dictionary<?, ?> _EMPTY_DICTIONARY = new Hashtable<>();

	private static final String _RETRIEVE_ALL_CONFIGURATION_SQL =
		"select dictionary from Configuration_ ORDER BY " +
			"Configuration_.configurationId ASC";

	private static final String _RETRIEVE_CONFIGURATION_SQL =
		"select dictionary from Configuration_ where configurationId = ?";

	private static final String _RETRIEVE_CONFIGURATION_SQL_FOR_UPDATE =
		"select * from Configuration_ where configurationId = ?";

	private static final String _TABLE_SQL_CREATE =
		"create table Configuration_ (configurationId VARCHAR(255) not null " +
			"primary key, dictionary TEXT)";

	private static final String _TEST_CONFIGURATION_TABLE_EXISTS =
		"select count(*) from Configuration_";

	private DataSource _dataSource;

}