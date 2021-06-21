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

package com.liferay.aspectj.hibernate.unexpected.row.count;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Field;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.SuppressAjWarnings;

import org.hibernate.engine.jdbc.batch.internal.BatchingBatch;

/**
 * @author Preston Crary
 */
@Aspect
@SuppressAjWarnings("adviceDidNotMatch")
public class HibernateUnexpectedRowCountAspect {

	@Before(
		"handler(java.lang.RuntimeException) &&" +
			"withincode(void org.hibernate.engine.jdbc.batch.internal.BatchingBatch." +
				"doExecuteBatch()) && args(runtimeException) && this(batchingBatch)"
	)
	public void logUpdateSQL(
		BatchingBatch batchingBatch, RuntimeException runtimeException) {

		try {
			_log.error(
				"currentStatementSql = " +
					_currentStatementSQLField.get(batchingBatch),
				runtimeException);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			runtimeException.addSuppressed(reflectiveOperationException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HibernateUnexpectedRowCountAspect.class);

	private static final Field _currentStatementSQLField;

	static {
		try {
			_currentStatementSQLField = ReflectionUtil.getDeclaredField(
				BatchingBatch.class, "currentStatementSql");
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

}