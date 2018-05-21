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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.BaseDBFactory;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBType;

/**
 * @author Shuyang Zhou
 */
public class SybaseDBFactory extends BaseDBFactory {

	@Override
	public DB doCreate(int dbMajorVersion, int dbMinorVersion) {
		return new SybaseDB(dbMajorVersion, dbMinorVersion);
	}

	@Override
	public DBType getDBType() {
		return DBType.SYBASE;
	}

}