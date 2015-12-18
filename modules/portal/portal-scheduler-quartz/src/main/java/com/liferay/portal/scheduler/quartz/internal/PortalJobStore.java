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

package com.liferay.portal.scheduler.quartz.internal;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.quartz.impl.jdbcjobstore.DB2v8Delegate;
import org.quartz.impl.jdbcjobstore.DriverDelegate;
import org.quartz.impl.jdbcjobstore.HSQLDBDelegate;
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.impl.jdbcjobstore.MSSQLDelegate;
import org.quartz.impl.jdbcjobstore.NoSuchDelegateException;
import org.quartz.impl.jdbcjobstore.PostgreSQLDelegate;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;
import org.quartz.impl.jdbcjobstore.SybaseDelegate;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalJobStore extends JobStoreTX {

	@Override
	protected DriverDelegate getDelegate() throws NoSuchDelegateException {
		if (_driverDelegate != null) {
			return _driverDelegate;
		}

		try {
			Class<?> driverDelegateClass = StdJDBCDelegate.class;

			DB db = DBManagerUtil.getDB();

			DBType dbType = db.getDBType();

			if (dbType == DBType.DB2) {
				driverDelegateClass = DB2v8Delegate.class;
			}
			else if (dbType == DBType.HYPERSONIC) {
				driverDelegateClass = HSQLDBDelegate.class;
			}
			else if (dbType == DBType.POSTGRESQL) {
				driverDelegateClass = PostgreSQLDelegate.class;
			}
			else if (dbType == DBType.SQLSERVER) {
				driverDelegateClass = MSSQLDelegate.class;
			}
			else if (dbType == DBType.SYBASE) {
				driverDelegateClass = SybaseDelegate.class;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Instantiating " + driverDelegateClass);
			}

			setDriverDelegateClass(driverDelegateClass.getName());

			_driverDelegate = super.getDelegate();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Using driver delegate " +
						_driverDelegate.getClass().getName());
			}

			return _driverDelegate;
		}
		catch (NoSuchDelegateException nsde) {
			throw nsde;
		}
		catch (Exception e) {
			throw new NoSuchDelegateException(e.getMessage());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(PortalJobStore.class);

	private DriverDelegate _driverDelegate;

}