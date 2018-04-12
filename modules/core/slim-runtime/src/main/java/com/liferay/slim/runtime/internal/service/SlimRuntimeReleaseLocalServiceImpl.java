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

package com.liferay.slim.runtime.internal.service;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.impl.ReleaseLocalServiceImpl;

/**
 * @author Raymond Augé
 */
public class SlimRuntimeReleaseLocalServiceImpl
	extends ReleaseLocalServiceImpl {

	@Override
	public void createTablesAndPopulate() {
		try {
			if (_log.isInfoEnabled()) {
				_log.info("Create tables and populate with default data");
			}

			DB db = DBManagerUtil.getDB();

			db.runSQLTemplate("slim/portal-tables.sql", false);
			db.runSQLTemplate("slim/portal-data-common.sql", false);
			db.runSQLTemplate("slim/portal-data-counter.sql", false);
			db.runSQLTemplate("slim/indexes.sql", false);
			db.runSQLTemplate("slim/sequences.sql", false);

			addReleaseInfo();

			StartupHelperUtil.setDbNew(true);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new SystemException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SlimRuntimeReleaseLocalServiceImpl.class);

}