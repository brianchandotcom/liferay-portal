/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import org.hibernate.engine.spi.SharedSessionContractImplementor;
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
	public boolean equals(Object object1, Object object2) {
		if (Objects.equals(object1, object2)) {
			return true;
		}
		else if (((object1 == null) || object1.equals(StringPool.BLANK)) &&
				 ((object2 == null) || object2.equals(StringPool.BLANK))) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode(Object object) throws HibernateException {
		return object.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(
			ResultSet resultSet, String[] names,
			SharedSessionContractImplementor sharedSessionContractImplementor,
			Object owner)
		throws HibernateException, SQLException {

		Reader reader = resultSet.getCharacterStream(names[0]);

		if (reader == null) {
			return null;
		}

		StringBuilder stringBuilder = new StringBuilder(4096);

		try {
			char[] chars = new char[4096];

			for (int i = reader.read(chars); i > 0; i = reader.read(chars)) {
				stringBuilder.append(chars, 0, i);
			}
		}
		catch (IOException ioException) {
			throw new SQLException(ioException.getMessage());
		}

		return stringBuilder.toString();
	}

	@Override
	public void nullSafeSet(
			PreparedStatement preparedStatement, Object value, int index,
			SharedSessionContractImplementor sharedSessionContractImplementor)
		throws HibernateException, SQLException {

		if (value != null) {
			String string = (String)value;

			StringReader stringReader = new StringReader(string);

			preparedStatement.setCharacterStream(
				index, stringReader, string.length());
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