/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.dao.db.DBManagerImpl;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;

import org.junit.Before;

/**
 * @author Miguel Pastor
 */
public abstract class BaseSQLTransformerTestCase {

	@Before
	public void setUp() {
		DBManagerUtil.setDBManager(new DBManagerImpl());

		DBManagerUtil.setDB(getDBType(), null);
	}

	protected abstract DBType getDBType();

	protected String transformSQL(String sql) {
		return SQLTransformer.transform(sql);
	}

}