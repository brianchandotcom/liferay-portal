/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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