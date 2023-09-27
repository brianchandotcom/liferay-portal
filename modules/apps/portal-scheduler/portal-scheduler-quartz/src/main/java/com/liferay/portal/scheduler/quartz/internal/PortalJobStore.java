/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				Class<?> clazz = _driverDelegate.getClass();

				_log.info("Using driver delegate " + clazz.getName());
			}

			return _driverDelegate;
		}
		catch (NoSuchDelegateException noSuchDelegateException) {
			throw noSuchDelegateException;
		}
		catch (Exception exception) {
			throw new NoSuchDelegateException(exception.getMessage());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(PortalJobStore.class);

	private DriverDelegate _driverDelegate;

}