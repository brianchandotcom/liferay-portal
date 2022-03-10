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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * @author Shuyang Zhou
 */
public class StringClobType implements Serializable, UserType {

	@Override
	public Object assemble(Serializable cached, Object owner)
		throws HibernateException {

		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable)value;
	}

	@Override
	public boolean equals(Object x, Object y) {
		if (Objects.equals(x, y)) {
			return true;
		}
		else if (((x == null) || x.equals(StringPool.BLANK)) &&
				 ((y == null) || y.equals(StringPool.BLANK))) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
		throws HibernateException, SQLException {

		Reader reader = resultSet.getCharacterStream(names[0]);

		if (reader == null) {
			return null;
		}

		StringBuilder result = new StringBuilder(4096);

		try {
			char[] chars = new char[4096];

			for (int i = reader.read(chars); i > 0; i = reader.read(chars)) {
				result.append(chars, 0, i);
			}
		}
		catch (IOException ioException) {
			throw new SQLException(ioException.getMessage());
		}

		return result.toString();
	}

	@Override
	public void nullSafeSet(
			PreparedStatement preparedStatement, Object value, int index)
		throws HibernateException, SQLException {

		if (value != null) {
			String string = (String)value;

			StringReader reader = new StringReader(string);

			preparedStatement.setCharacterStream(
				index, reader, string.length());
		}
		else {
			preparedStatement.setNull(index, sqlTypes()[0]);
		}
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
		throws HibernateException {

		return original;
	}

	@Override
	public Class<String> returnedClass() {
		return String.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] {Types.CLOB};
	}

}