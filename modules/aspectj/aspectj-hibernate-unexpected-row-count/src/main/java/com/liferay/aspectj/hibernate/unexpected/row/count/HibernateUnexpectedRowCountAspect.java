/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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