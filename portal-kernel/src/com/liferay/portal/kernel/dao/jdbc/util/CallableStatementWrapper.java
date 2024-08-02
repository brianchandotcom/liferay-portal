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
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Map;

/**
 * @author István András Dézsi
 */
public class CallableStatementWrapper
	extends StatementWrapper implements CallableStatement {

	public CallableStatementWrapper(CallableStatement callableStatement) {
		super(callableStatement);

		_callableStatement = callableStatement;
	}

	@Override
	public void addBatch() throws SQLException {
		_callableStatement.addBatch();
	}

	@Override
	public void clearParameters() throws SQLException {
		_callableStatement.clearParameters();
	}

	@Override
	public boolean execute() throws SQLException {
		return _callableStatement.execute();
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		return _callableStatement.executeQuery();
	}

	@Override
	public int executeUpdate() throws SQLException {
		return _callableStatement.executeUpdate();
	}

	@Override
	public Array getArray(int parameterIndex) throws SQLException {
		return _callableStatement.getArray(parameterIndex);
	}

	@Override
	public Array getArray(String parameterName) throws SQLException {
		return _callableStatement.getArray(parameterName);
	}

	@Override
	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		return _callableStatement.getBigDecimal(parameterIndex);
	}

	@Override
	public BigDecimal getBigDecimal(int parameterIndex, int scale)
		throws SQLException {

		return _callableStatement.getBigDecimal(parameterIndex, scale);
	}

	@Override
	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		return _callableStatement.getBigDecimal(parameterName);
	}

	@Override
	public Blob getBlob(int parameterIndex) throws SQLException {
		return _callableStatement.getBlob(parameterIndex);
	}

	@Override
	public Blob getBlob(String parameterName) throws SQLException {
		return _callableStatement.getBlob(parameterName);
	}

	@Override
	public boolean getBoolean(int parameterIndex) throws SQLException {
		return _callableStatement.getBoolean(parameterIndex);
	}

	@Override
	public boolean getBoolean(String parameterName) throws SQLException {
		return _callableStatement.getBoolean(parameterName);
	}

	@Override
	public byte getByte(int parameterIndex) throws SQLException {
		return _callableStatement.getByte(parameterIndex);
	}

	@Override
	public byte getByte(String parameterName) throws SQLException {
		return _callableStatement.getByte(parameterName);
	}

	@Override
	public byte[] getBytes(int parameterIndex) throws SQLException {
		return _callableStatement.getBytes(parameterIndex);
	}

	@Override
	public byte[] getBytes(String parameterName) throws SQLException {
		return _callableStatement.getBytes(parameterName);
	}

	@Override
	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		return _callableStatement.getCharacterStream(parameterIndex);
	}

	@Override
	public Reader getCharacterStream(String parameterName) throws SQLException {
		return _callableStatement.getCharacterStream(parameterName);
	}

	@Override
	public Clob getClob(int parameterIndex) throws SQLException {
		return _callableStatement.getClob(parameterIndex);
	}

	@Override
	public Clob getClob(String parameterName) throws SQLException {
		return _callableStatement.getClob(parameterName);
	}

	@Override
	public Date getDate(int parameterIndex) throws SQLException {
		return _callableStatement.getDate(parameterIndex);
	}

	@Override
	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		return _callableStatement.getDate(parameterIndex, cal);
	}

	@Override
	public Date getDate(String parameterName) throws SQLException {
		return _callableStatement.getDate(parameterName);
	}

	@Override
	public Date getDate(String parameterName, Calendar cal)
		throws SQLException {

		return _callableStatement.getDate(parameterName, cal);
	}

	@Override
	public double getDouble(int parameterIndex) throws SQLException {
		return _callableStatement.getDouble(parameterIndex);
	}

	@Override
	public double getDouble(String parameterName) throws SQLException {
		return _callableStatement.getDouble(parameterName);
	}

	@Override
	public float getFloat(int parameterIndex) throws SQLException {
		return _callableStatement.getFloat(parameterIndex);
	}

	@Override
	public float getFloat(String parameterName) throws SQLException {
		return _callableStatement.getFloat(parameterName);
	}

	@Override
	public int getInt(int parameterIndex) throws SQLException {
		return _callableStatement.getInt(parameterIndex);
	}

	@Override
	public int getInt(String parameterName) throws SQLException {
		return _callableStatement.getInt(parameterName);
	}

	@Override
	public long getLong(int parameterIndex) throws SQLException {
		return _callableStatement.getLong(parameterIndex);
	}

	@Override
	public long getLong(String parameterName) throws SQLException {
		return _callableStatement.getLong(parameterName);
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return _callableStatement.getMetaData();
	}

	@Override
	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		return _callableStatement.getNCharacterStream(parameterIndex);
	}

	@Override
	public Reader getNCharacterStream(String parameterName)
		throws SQLException {

		return _callableStatement.getNCharacterStream(parameterName);
	}

	@Override
	public NClob getNClob(int parameterIndex) throws SQLException {
		return _callableStatement.getNClob(parameterIndex);
	}

	@Override
	public NClob getNClob(String parameterName) throws SQLException {
		return _callableStatement.getNClob(parameterName);
	}

	@Override
	public String getNString(int parameterIndex) throws SQLException {
		return _callableStatement.getNString(parameterIndex);
	}

	@Override
	public String getNString(String parameterName) throws SQLException {
		return _callableStatement.getNString(parameterName);
	}

	@Override
	public Object getObject(int parameterIndex) throws SQLException {
		return _callableStatement.getObject(parameterIndex);
	}

	@Override
	public <T> T getObject(int parameterIndex, Class<T> type)
		throws SQLException {

		return _callableStatement.getObject(parameterIndex, type);
	}

	@Override
	public Object getObject(int parameterIndex, Map<String, Class<?>> map)
		throws SQLException {

		return _callableStatement.getObject(parameterIndex, map);
	}

	@Override
	public Object getObject(String parameterName) throws SQLException {
		return _callableStatement.getObject(parameterName);
	}

	@Override
	public <T> T getObject(String parameterName, Class<T> type)
		throws SQLException {

		return _callableStatement.getObject(parameterName, type);
	}

	@Override
	public Object getObject(String parameterName, Map<String, Class<?>> map)
		throws SQLException {

		return _callableStatement.getObject(parameterName, map);
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return _callableStatement.getParameterMetaData();
	}

	@Override
	public Ref getRef(int parameterIndex) throws SQLException {
		return _callableStatement.getRef(parameterIndex);
	}

	@Override
	public Ref getRef(String parameterName) throws SQLException {
		return _callableStatement.getRef(parameterName);
	}

	@Override
	public RowId getRowId(int parameterIndex) throws SQLException {
		return _callableStatement.getRowId(parameterIndex);
	}

	@Override
	public RowId getRowId(String parameterName) throws SQLException {
		return _callableStatement.getRowId(parameterName);
	}

	@Override
	public short getShort(int parameterIndex) throws SQLException {
		return _callableStatement.getShort(parameterIndex);
	}

	@Override
	public short getShort(String parameterName) throws SQLException {
		return _callableStatement.getShort(parameterName);
	}

	@Override
	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		return _callableStatement.getSQLXML(parameterIndex);
	}

	@Override
	public SQLXML getSQLXML(String parameterName) throws SQLException {
		return _callableStatement.getSQLXML(parameterName);
	}

	@Override
	public String getString(int parameterIndex) throws SQLException {
		return _callableStatement.getString(parameterIndex);
	}

	@Override
	public String getString(String parameterName) throws SQLException {
		return _callableStatement.getString(parameterName);
	}

	@Override
	public Time getTime(int parameterIndex) throws SQLException {
		return _callableStatement.getTime(parameterIndex);
	}

	@Override
	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		return _callableStatement.getTime(parameterIndex, cal);
	}

	@Override
	public Time getTime(String parameterName) throws SQLException {
		return _callableStatement.getTime(parameterName);
	}

	@Override
	public Time getTime(String parameterName, Calendar cal)
		throws SQLException {

		return _callableStatement.getTime(parameterName, cal);
	}

	@Override
	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		return _callableStatement.getTimestamp(parameterIndex);
	}

	@Override
	public Timestamp getTimestamp(int parameterIndex, Calendar cal)
		throws SQLException {

		return _callableStatement.getTimestamp(parameterIndex, cal);
	}

	@Override
	public Timestamp getTimestamp(String parameterName) throws SQLException {
		return _callableStatement.getTimestamp(parameterName);
	}

	@Override
	public Timestamp getTimestamp(String parameterName, Calendar cal)
		throws SQLException {

		return _callableStatement.getTimestamp(parameterName, cal);
	}

	@Override
	public URL getURL(int parameterIndex) throws SQLException {
		return _callableStatement.getURL(parameterIndex);
	}

	@Override
	public URL getURL(String parameterName) throws SQLException {
		return _callableStatement.getURL(parameterName);
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType)
		throws SQLException {

		_callableStatement.registerOutParameter(parameterIndex, sqlType);
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType, int scale)
		throws SQLException {

		_callableStatement.registerOutParameter(parameterIndex, sqlType, scale);
	}

	@Override
	public void registerOutParameter(
			int parameterIndex, int sqlType, String typeName)
		throws SQLException {

		_callableStatement.registerOutParameter(
			parameterIndex, sqlType, typeName);
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType)
		throws SQLException {

		_callableStatement.registerOutParameter(parameterName, sqlType);
	}

	@Override
	public void registerOutParameter(
			String parameterName, int sqlType, int scale)
		throws SQLException {

		_callableStatement.registerOutParameter(parameterName, sqlType, scale);
	}

	@Override
	public void registerOutParameter(
			String parameterName, int sqlType, String typeName)
		throws SQLException {

		_callableStatement.registerOutParameter(
			parameterName, sqlType, typeName);
	}

	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		_callableStatement.setArray(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream inputStream)
		throws SQLException {

		_callableStatement.setAsciiStream(parameterIndex, inputStream);
	}

	@Override
	public void setAsciiStream(
			int parameterIndex, InputStream inputStream, int length)
		throws SQLException {

		_callableStatement.setAsciiStream(parameterIndex, inputStream, length);
	}

	@Override
	public void setAsciiStream(
			int parameterIndex, InputStream inputStream, long length)
		throws SQLException {

		_callableStatement.setAsciiStream(parameterIndex, inputStream, length);
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream inputStream)
		throws SQLException {

		_callableStatement.setAsciiStream(parameterName, inputStream);
	}

	@Override
	public void setAsciiStream(
			String parameterName, InputStream inputStream, int length)
		throws SQLException {

		_callableStatement.setAsciiStream(parameterName, inputStream, length);
	}

	@Override
	public void setAsciiStream(
			String parameterName, InputStream inputStream, long length)
		throws SQLException {

		_callableStatement.setAsciiStream(parameterName, inputStream, length);
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x)
		throws SQLException {

		_callableStatement.setBigDecimal(parameterIndex, x);
	}

	@Override
	public void setBigDecimal(String parameterName, BigDecimal x)
		throws SQLException {

		_callableStatement.setBigDecimal(parameterName, x);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream inputStream)
		throws SQLException {

		_callableStatement.setBinaryStream(parameterIndex, inputStream);
	}

	@Override
	public void setBinaryStream(
			int parameterIndex, InputStream inputStream, int length)
		throws SQLException {

		_callableStatement.setBinaryStream(parameterIndex, inputStream, length);
	}

	@Override
	public void setBinaryStream(
			int parameterIndex, InputStream inputStream, long length)
		throws SQLException {

		_callableStatement.setBinaryStream(parameterIndex, inputStream, length);
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream inputStream)
		throws SQLException {

		_callableStatement.setBinaryStream(parameterName, inputStream);
	}

	@Override
	public void setBinaryStream(
			String parameterName, InputStream inputStream, int length)
		throws SQLException {

		_callableStatement.setBinaryStream(parameterName, inputStream, length);
	}

	@Override
	public void setBinaryStream(
			String parameterName, InputStream inputStream, long length)
		throws SQLException {

		_callableStatement.setBinaryStream(parameterName, inputStream, length);
	}

	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		_callableStatement.setBlob(parameterIndex, x);
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream)
		throws SQLException {

		_callableStatement.setBlob(parameterIndex, inputStream);
	}

	@Override
	public void setBlob(
			int parameterIndex, InputStream inputStream, long length)
		throws SQLException {

		_callableStatement.setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public void setBlob(String parameterName, Blob x) throws SQLException {
		_callableStatement.setBlob(parameterName, x);
	}

	@Override
	public void setBlob(String parameterName, InputStream inputStream)
		throws SQLException {

		_callableStatement.setBlob(parameterName, inputStream);
	}

	@Override
	public void setBlob(
			String parameterName, InputStream inputStream, long length)
		throws SQLException {

		_callableStatement.setBlob(parameterName, inputStream, length);
	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		_callableStatement.setBoolean(parameterIndex, x);
	}

	@Override
	public void setBoolean(String parameterName, boolean x)
		throws SQLException {

		_callableStatement.setBoolean(parameterName, x);
	}

	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		_callableStatement.setByte(parameterIndex, x);
	}

	@Override
	public void setByte(String parameterName, byte x) throws SQLException {
		_callableStatement.setByte(parameterName, x);
	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		_callableStatement.setBytes(parameterIndex, x);
	}

	@Override
	public void setBytes(String parameterName, byte[] x) throws SQLException {
		_callableStatement.setBytes(parameterName, x);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader)
		throws SQLException {

		_callableStatement.setCharacterStream(parameterIndex, reader);
	}

	@Override
	public void setCharacterStream(
			int parameterIndex, Reader reader, int length)
		throws SQLException {

		_callableStatement.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setCharacterStream(
			int parameterIndex, Reader reader, long length)
		throws SQLException {

		_callableStatement.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader)
		throws SQLException {

		_callableStatement.setCharacterStream(parameterName, reader);
	}

	@Override
	public void setCharacterStream(
			String parameterName, Reader reader, int length)
		throws SQLException {

		_callableStatement.setCharacterStream(parameterName, reader, length);
	}

	@Override
	public void setCharacterStream(
			String parameterName, Reader reader, long length)
		throws SQLException {

		_callableStatement.setCharacterStream(parameterName, reader, length);
	}

	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		_callableStatement.setClob(parameterIndex, x);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		_callableStatement.setClob(parameterIndex, reader);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length)
		throws SQLException {

		_callableStatement.setClob(parameterIndex, reader, length);
	}

	@Override
	public void setClob(String parameterName, Clob x) throws SQLException {
		_callableStatement.setClob(parameterName, x);
	}

	@Override
	public void setClob(String parameterName, Reader reader)
		throws SQLException {

		_callableStatement.setClob(parameterName, reader);
	}

	@Override
	public void setClob(String parameterName, Reader reader, long length)
		throws SQLException {

		_callableStatement.setClob(parameterName, reader, length);
	}

	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		_callableStatement.setDate(parameterIndex, x);
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal)
		throws SQLException {

		_callableStatement.setDate(parameterIndex, x, cal);
	}

	@Override
	public void setDate(String parameterName, Date x) throws SQLException {
		_callableStatement.setDate(parameterName, x);
	}

	@Override
	public void setDate(String parameterName, Date x, Calendar cal)
		throws SQLException {

		_callableStatement.setDate(parameterName, x, cal);
	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		_callableStatement.setDouble(parameterIndex, x);
	}

	@Override
	public void setDouble(String parameterName, double x) throws SQLException {
		_callableStatement.setDouble(parameterName, x);
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		_callableStatement.setFloat(parameterIndex, x);
	}

	@Override
	public void setFloat(String parameterName, float x) throws SQLException {
		_callableStatement.setFloat(parameterName, x);
	}

	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		_callableStatement.setInt(parameterIndex, x);
	}

	@Override
	public void setInt(String parameterName, int x) throws SQLException {
		_callableStatement.setInt(parameterName, x);
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		_callableStatement.setLong(parameterIndex, x);
	}

	@Override
	public void setLong(String parameterName, long x) throws SQLException {
		_callableStatement.setLong(parameterName, x);
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value)
		throws SQLException {

		_callableStatement.setNCharacterStream(parameterIndex, value);
	}

	@Override
	public void setNCharacterStream(
			int parameterIndex, Reader value, long length)
		throws SQLException {

		_callableStatement.setNCharacterStream(parameterIndex, value, length);
	}

	@Override
	public void setNCharacterStream(String parameterName, Reader value)
		throws SQLException {

		_callableStatement.setNCharacterStream(parameterName, value);
	}

	@Override
	public void setNCharacterStream(
			String parameterName, Reader value, long length)
		throws SQLException {

		_callableStatement.setNCharacterStream(parameterName, value, length);
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		_callableStatement.setNClob(parameterIndex, value);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader)
		throws SQLException {

		_callableStatement.setNClob(parameterIndex, reader);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length)
		throws SQLException {

		_callableStatement.setNClob(parameterIndex, reader, length);
	}

	@Override
	public void setNClob(String parameterName, NClob value)
		throws SQLException {

		_callableStatement.setNClob(parameterName, value);
	}

	@Override
	public void setNClob(String parameterName, Reader reader)
		throws SQLException {

		_callableStatement.setNClob(parameterName, reader);
	}

	@Override
	public void setNClob(String parameterName, Reader reader, long length)
		throws SQLException {

		_callableStatement.setNClob(parameterName, reader, length);
	}

	@Override
	public void setNString(int parameterIndex, String value)
		throws SQLException {

		_callableStatement.setNString(parameterIndex, value);
	}

	@Override
	public void setNString(String parameterName, String value)
		throws SQLException {

		_callableStatement.setNString(parameterName, value);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		_callableStatement.setNull(parameterIndex, sqlType);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName)
		throws SQLException {

		_callableStatement.setNull(parameterIndex, sqlType, typeName);
	}

	@Override
	public void setNull(String parameterName, int sqlType) throws SQLException {
		_callableStatement.setNull(parameterName, sqlType);
	}

	@Override
	public void setNull(String parameterName, int sqlType, String typeName)
		throws SQLException {

		_callableStatement.setNull(parameterName, sqlType, typeName);
	}

	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		_callableStatement.setObject(parameterIndex, x);
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType)
		throws SQLException {

		_callableStatement.setObject(parameterIndex, x, targetSqlType);
	}

	@Override
	public void setObject(
			int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
		throws SQLException {

		_callableStatement.setObject(
			parameterIndex, x, targetSqlType, scaleOrLength);
	}

	@Override
	public void setObject(String parameterName, Object x) throws SQLException {
		_callableStatement.setObject(parameterName, x);
	}

	@Override
	public void setObject(String parameterName, Object x, int targetSqlType)
		throws SQLException {

		_callableStatement.setObject(parameterName, x, targetSqlType);
	}

	@Override
	public void setObject(
			String parameterName, Object x, int targetSqlType, int scale)
		throws SQLException {

		_callableStatement.setObject(parameterName, x, targetSqlType, scale);
	}

	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		_callableStatement.setRef(parameterIndex, x);
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		_callableStatement.setRowId(parameterIndex, x);
	}

	@Override
	public void setRowId(String parameterName, RowId x) throws SQLException {
		_callableStatement.setRowId(parameterName, x);
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		_callableStatement.setShort(parameterIndex, x);
	}

	@Override
	public void setShort(String parameterName, short x) throws SQLException {
		_callableStatement.setShort(parameterName, x);
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
		throws SQLException {

		_callableStatement.setSQLXML(parameterIndex, xmlObject);
	}

	@Override
	public void setSQLXML(String parameterName, SQLXML xmlObject)
		throws SQLException {

		_callableStatement.setSQLXML(parameterName, xmlObject);
	}

	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		_callableStatement.setString(parameterIndex, x);
	}

	@Override
	public void setString(String parameterName, String x) throws SQLException {
		_callableStatement.setString(parameterName, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		_callableStatement.setTime(parameterIndex, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal)
		throws SQLException {

		_callableStatement.setTime(parameterIndex, x, cal);
	}

	@Override
	public void setTime(String parameterName, Time x) throws SQLException {
		_callableStatement.setTime(parameterName, x);
	}

	@Override
	public void setTime(String parameterName, Time x, Calendar cal)
		throws SQLException {

		_callableStatement.setTime(parameterName, x, cal);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x)
		throws SQLException {

		_callableStatement.setTimestamp(parameterIndex, x);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
		throws SQLException {

		_callableStatement.setTimestamp(parameterIndex, x, cal);
	}

	@Override
	public void setTimestamp(String parameterName, Timestamp x)
		throws SQLException {

		_callableStatement.setTimestamp(parameterName, x);
	}

	@Override
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
		throws SQLException {

		_callableStatement.setTimestamp(parameterName, x, cal);
	}

	@Override
	public void setUnicodeStream(
			int parameterIndex, InputStream inputStream, int length)
		throws SQLException {

		_callableStatement.setUnicodeStream(
			parameterIndex, inputStream, length);
	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		_callableStatement.setURL(parameterIndex, x);
	}

	@Override
	public void setURL(String parameterName, URL val) throws SQLException {
		_callableStatement.setURL(parameterName, val);
	}

	@Override
	public <T> T unwrap(Class<T> clazz) throws SQLException {
		if (!CallableStatement.class.equals(clazz)) {
			throw new SQLException("Invalid class " + clazz);
		}

		return (T)this;
	}

	@Override
	public boolean wasNull() throws SQLException {
		return _callableStatement.wasNull();
	}

	private volatile CallableStatement _callableStatement;

}