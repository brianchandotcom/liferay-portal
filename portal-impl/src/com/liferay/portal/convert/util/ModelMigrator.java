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

package com.liferay.portal.convert.util;

import com.liferay.portal.kernel.model.BaseModel;

import java.io.IOException;

import java.sql.SQLException;

import java.util.List;

import javax.sql.DataSource;

/**
 * @author Cristina González
 * @author Miguel Pastor
 */
public interface ModelMigrator {

	public void migrate(
			DataSource dataSource, List<Class<? extends BaseModel<?>>> models)
		throws IOException, SQLException;

}