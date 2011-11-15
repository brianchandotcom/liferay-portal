/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

/**
 * @author Brian Wing Shun Chan
 */
public class LongType implements CompositeUserType, Serializable {

	public static final Long DEFAULT_VALUE = Long.valueOf(0);

	public Object assemble(
		Serializable cached, SessionImplementor session, Object owner) {

		return cached;
	}

	public Object deepCopy(Object obj) {
		return obj;
	}

	public Serializable disassemble(Object value, SessionImplementor session) {
		return (Serializable)value;
	}

	public boolean equals(Object x, Object y) {
		if (x == y) {
			return true;
		}
		else if ((x == null) || (y == null)) {
			return false;
		}
		else {
			return x.equals(y);
		}
	}

	public String[] getPropertyNames() {
		return new String[0];
	}

	public Type[] getPropertyTypes() {
		return new Type[] {StandardBasicTypes.LONG};
	}

	public Object getPropertyValue(Object component, int property) {
		return component;
	}

	public int hashCode(Object x) {
		return x.hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	public Object nullSafeGet(
			ResultSet rs, String[] names, SessionImplementor session,
			Object owner)
		throws SQLException {

		Object value = null;

		try {
			value = StandardBasicTypes.LONG.nullSafeGet(rs, names[0], session);
		}
		catch (SQLException sqle1) {

			// Some JDBC drivers do not know how to convert a VARCHAR column
			// with a blank entry into a BIGINT

			try {
				value = new Long(
					GetterUtil.getLong(
						StandardBasicTypes.STRING.nullSafeGet(
							rs, names[0], session)));
			}
			catch (SQLException sqle2) {
				throw sqle1;
			}
		}

		if (value == null) {
			return DEFAULT_VALUE;
		}
		else {
			return value;
		}
	}

	public void nullSafeSet(
			PreparedStatement ps, Object target, int index,
			SessionImplementor session)
		throws SQLException {

		if (target == null) {
			target = DEFAULT_VALUE;
		}

		ps.setLong(index, (Long)target);
	}

	public Object replace(
		Object original, Object target, SessionImplementor session,
		Object owner) {

		return original;
	}

	public Class<Long> returnedClass() {
		return Long.class;
	}

	public void setPropertyValue(Object component, int property, Object value) {
	}

}