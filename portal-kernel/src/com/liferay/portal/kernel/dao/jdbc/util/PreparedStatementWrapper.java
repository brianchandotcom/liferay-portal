/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc.util;

import java.io.InputStream;
import java.io.Reader;

import java.math.BigDecimal;

import java.net.URL;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;

import java.util.Calendar;

/**
 * @author István András Dézsi
 */
public class PreparedStatementWrapper
	extends StatementWrapper implements PreparedStatement {

	public PreparedStatementWrapper(PreparedStatement preparedStatement) {
		super(preparedStatement);

		_preparedStatement = preparedStatement;
	}

	@Override
	public void addBatch() throws SQLException {
		_preparedStatement.addBatch();
	}

	@Override
	public void clearParameters() throws SQLException {
		_preparedStatement.clearParameters();
	}

	@Override
	public boolean execute() throws SQLException {
		return _preparedStatement.execute();
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		return _preparedStatement.executeQuery();
	}

	@Override
	public int executeUpdate() throws SQLException {
		return _preparedStatement.executeUpdate();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return _preparedStatement.getMetaData();
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return _preparedStatement.getParameterMetaData();
	}

	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		_preparedStatement.setArray(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream inputStream)
		throws SQLException {

		_preparedStatement.setAsciiStream(parameterIndex, inputStream);
	}

	@Override
	public void setAsciiStream(
			int parameterIndex, InputStream inputStream, int length)
		throws SQLException {

		_preparedStatement.setAsciiStream(parameterIndex, inputStream, length);
	}

	@Override
	public void setAsciiStream(
			int parameterIndex, InputStream inputStream, long length)
		throws SQLException {

		_preparedStatement.setAsciiStream(parameterIndex, inputStream, length);
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x)
		throws SQLException {

		_preparedStatement.setBigDecimal(parameterIndex, x);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream inputStream)
		throws SQLException {

		_preparedStatement.setBinaryStream(parameterIndex, inputStream);
	}

	@Override
	public void setBinaryStream(
			int parameterIndex, InputStream inputStream, int length)
		throws SQLException {

		_preparedStatement.setBinaryStream(parameterIndex, inputStream, length);
	}

	@Override
	public void setBinaryStream(
			int parameterIndex, InputStream inputStream, long length)
		throws SQLException {

		_preparedStatement.setBinaryStream(parameterIndex, inputStream, length);
	}

	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		_preparedStatement.setBlob(parameterIndex, x);
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream)
		throws SQLException {

		_preparedStatement.setBlob(parameterIndex, inputStream);
	}

	@Override
	public void setBlob(
			int parameterIndex, InputStream inputStream, long length)
		throws SQLException {

		_preparedStatement.setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		_preparedStatement.setBoolean(parameterIndex, x);
	}

	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		_preparedStatement.setByte(parameterIndex, x);
	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		_preparedStatement.setBytes(parameterIndex, x);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader)
		throws SQLException {

		_preparedStatement.setCharacterStream(parameterIndex, reader);
	}

	@Override
	public void setCharacterStream(
			int parameterIndex, Reader reader, int length)
		throws SQLException {

		_preparedStatement.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setCharacterStream(
			int parameterIndex, Reader reader, long length)
		throws SQLException {

		_preparedStatement.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		_preparedStatement.setClob(parameterIndex, x);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		_preparedStatement.setClob(parameterIndex, reader);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length)
		throws SQLException {

		_preparedStatement.setClob(parameterIndex, reader, length);
	}

	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		_preparedStatement.setDate(parameterIndex, x);
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal)
		throws SQLException {

		_preparedStatement.setDate(parameterIndex, x, cal);
	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		_preparedStatement.setDouble(parameterIndex, x);
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		_preparedStatement.setFloat(parameterIndex, x);
	}

	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		_preparedStatement.setInt(parameterIndex, x);
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		_preparedStatement.setLong(parameterIndex, x);
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value)
		throws SQLException {

		_preparedStatement.setNCharacterStream(parameterIndex, value);
	}

	@Override
	public void setNCharacterStream(
			int parameterIndex, Reader value, long length)
		throws SQLException {

		_preparedStatement.setNCharacterStream(parameterIndex, value, length);
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		_preparedStatement.setNClob(parameterIndex, value);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader)
		throws SQLException {

		_preparedStatement.setNClob(parameterIndex, reader);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length)
		throws SQLException {

		_preparedStatement.setNClob(parameterIndex, reader, length);
	}

	@Override
	public void setNString(int parameterIndex, String value)
		throws SQLException {

		_preparedStatement.setNString(parameterIndex, value);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		_preparedStatement.setNull(parameterIndex, sqlType);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName)
		throws SQLException {

		_preparedStatement.setNull(parameterIndex, sqlType, typeName);
	}

	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		_preparedStatement.setObject(parameterIndex, x);
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType)
		throws SQLException {

		_preparedStatement.setObject(parameterIndex, x, targetSqlType);
	}

	@Override
	public void setObject(
			int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
		throws SQLException {

		_preparedStatement.setObject(
			parameterIndex, x, targetSqlType, scaleOrLength);
	}

	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		_preparedStatement.setRef(parameterIndex, x);
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		_preparedStatement.setRowId(parameterIndex, x);
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		_preparedStatement.setShort(parameterIndex, x);
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
		throws SQLException {

		_preparedStatement.setSQLXML(parameterIndex, xmlObject);
	}

	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		_preparedStatement.setString(parameterIndex, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		_preparedStatement.setTime(parameterIndex, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal)
		throws SQLException {

		_preparedStatement.setTime(parameterIndex, x, cal);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x)
		throws SQLException {

		_preparedStatement.setTimestamp(parameterIndex, x);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
		throws SQLException {

		_preparedStatement.setTimestamp(parameterIndex, x, cal);
	}

	@Override
	public void setUnicodeStream(
			int parameterIndex, InputStream inputStream, int length)
		throws SQLException {

		_preparedStatement.setUnicodeStream(
			parameterIndex, inputStream, length);
	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		_preparedStatement.setURL(parameterIndex, x);
	}

	@Override
	public <T> T unwrap(Class<T> clazz) throws SQLException {
		if (!PreparedStatement.class.equals(clazz)) {
			throw new SQLException("Invalid class " + clazz);
		}

		return (T)this;
	}

	private volatile PreparedStatement _preparedStatement;

}