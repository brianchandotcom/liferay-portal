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

package com.liferay.portal.upgrade.test.model.impl;

import java.sql.Types;

/**
 * @author Preston Crary
 */
public class BuildAutoUpgradeTestEntityModelImpl {

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final Object[][] TABLE_COLUMNS = {
		{"id_", Types.BIGINT}, {"data_", Types.VARCHAR}
	};

	public static final String TABLE_NAME = "BuildAutoUpgradeTestEntity";

	public static final String TABLE_SQL_CREATE =
		"create table BuildAutoUpgradeTestEntity (id_ LONG not null primary " +
			"key, data_ VARCHAR(75) null);";

}